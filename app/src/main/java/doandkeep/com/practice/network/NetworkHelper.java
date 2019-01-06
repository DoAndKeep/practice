/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package doandkeep.com.practice.network;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import okhttp3.*;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 封闭OkHttp，访问网络，先简单的封装进来可以使用，后面再视业务情况丰富功能
 * 同时考虑使用RXJava来替代
 *
 * @author liuyong05
 * @since 2018-4-18
 */
public class NetworkHelper {
    public static final long TIME_OUT = 8 * 1000L;

    private static volatile NetworkHelper mNetHelper;
    private OkHttpClient.Builder mBuilder;
    private OkHttpClient mOkHttpClient;
    private RequestBuilder mRequestBuilder;

    private NetworkHelper() {
    }

    /**
     * 单例对象
     *
     * @return
     */
    public static NetworkHelper getInstance() {
        if (mNetHelper == null) {
            synchronized (NetworkHelper.class) {
                mNetHelper = new NetworkHelper();
                mNetHelper.init().build();
            }
        }
        return mNetHelper;
    }

    public NetworkHelper init() {
        mBuilder = new OkHttpClient.Builder();
        /** 设置超时，暂时在这里设置 */
        mBuilder.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
        return this;
    }

    public NetworkHelper requestBuilder(RequestBuilder requestBuilder) {
        mRequestBuilder = requestBuilder;
        return this;
    }

    public NetworkHelper networkInterceptor(HttpLoggingInterceptor.Logger logger) {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(logger);
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mBuilder.addInterceptor(logInterceptor);
        return this;
    }

    public NetworkHelper cookieJar(CookieJar cookieJar) {
        mBuilder.cookieJar(cookieJar);
        return this;
    }

    public NetworkHelper addInterceptor(Interceptor interceptor) {
        mBuilder.addInterceptor(interceptor);
        return this;
    }

    public void build() {
        mOkHttpClient = mBuilder.build();
        if (mRequestBuilder == null) {
            mRequestBuilder = new RequestBuilder();
        }
    }

    /**
     * 发起Get请求
     *
     * @param request
     */
    public void get(BaseRequest request) {
        if (mOkHttpClient == null || request == null || TextUtils.isEmpty(request.getUrl())) {
            return;
        }
        execute(Method.GET, request);
    }

    /**
     * 发起POST请求
     *
     * @param request
     */
    public void post(BaseRequest request) {
        if (mOkHttpClient == null || request == null || TextUtils.isEmpty(request.getUrl())) {
            return;
        }
        execute(Method.POST, request);
    }

    public <T> doandkeep.com.practice.network.Response<T> syncGet(BaseRequest<T> request)
            throws IOException, JSONException {
        return syncExecute(Method.GET, request);
    }

    public <T> doandkeep.com.practice.network.Response<T> syncPost(BaseRequest<T> request)
            throws IOException, JSONException {
        return syncExecute(Method.POST, request);
    }

    /**
     * 使用okhttp发起请求
     *
     * @param type
     * @param request
     */
    private <T> void execute(int type, final BaseRequest<T> request) {
        Call call = mOkHttpClient.newCall(buildRequest(type, request));

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (request.mCallback == null) {
                    // 不需要回调
                    return;
                } else {
                    // 错误码自定义
                    request.mCallback.onFail(-1000, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (request.mCallback == null || request.mClass == null) {
                    return;
                }

                if (!response.isSuccessful()) {
                    request.mCallback.onFail(response.code(),
                            new Exception("Response ok, but not successful!"));
                    return;
                }

                // 先只做字符串返回，bytes返回有需求再说
                if (request.mClass == String.class) {
                    // 如果请求的泛型参数为String，不进行JSON解析，直接抛出
                    request.mCallback.onSuccess(new doandkeep.com.practice.network.Response<>(
                            response.code(),
                            response.request().url().toString(),
                            response.body() == null ? null : response.body().string()));
                } else {
                    // JSON解析
                    try {
                        request.mCallback.onSuccess(new doandkeep.com.practice.network.Response<>(
                                response.code(),
                                response.request().url().toString(),
                                response.body() == null ? null : JSON.parseObject(
                                        response.body().string(), request.mClass)));
                    } catch (Exception e) {
                        request.mCallback.onFail(response.code(), e);
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private <T> doandkeep.com.practice.network.Response<T> syncExecute(int type, final BaseRequest<T> request)
            throws IOException, JSONException {
        Call call = mOkHttpClient.newCall(buildRequest(type, request));
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                if (request.mClass == String.class) {
                    return new doandkeep.com.practice.network.Response<>(
                            response.code(),
                            response.request().url().toString(),
                            response.body() == null ? null : (T) response.body().string());
                } else {
                    return new doandkeep.com.practice.network.Response<>(
                            response.code(),
                            response.request().url().toString(),
                            response.body() == null ? null : JSON.parseObject(
                                    response.body().string(), request.mClass));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return null;
    }

    /**
     * 构建发起请求需要的参数
     *
     * @param type    GET or POST
     * @param request 请求对象，封装回调和参数
     * @return
     */
    private Request buildRequest(int type, BaseRequest request) {
        return mRequestBuilder.buildRequest(type, request);
    }

    /**
     * 取消请求
     *
     * @param tag
     */
    public void cancel(Object tag) {
        if (mOkHttpClient == null) {
            return;
        }
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    /**
     * 取消全部请求
     */
    public void cancelAll() {
        if (mOkHttpClient != null) {
            mOkHttpClient.dispatcher().cancelAll();
        }
    }
}
