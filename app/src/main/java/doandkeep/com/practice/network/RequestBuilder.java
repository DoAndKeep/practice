package doandkeep.com.practice.network;

import android.text.TextUtils;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.Map;

/**
 * @author zhuziqiang
 * @since 2018/12/4
 */
public class RequestBuilder {

    public Request buildRequest(int type, BaseRequest request) {
        Request.Builder builder = new Request.Builder();

        /** 构建tag */
        builder.tag(request.mTag);

        /** 构建url，进行参数的拼装 */
        Map<String, String> urlParams = request.getUrlParams();
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(request.getUrl());
        if (urlParams != null && urlParams.size() > 0) {
            if (!request.getUrl().contains("?")) {
                urlBuilder.append("?");
            } else {
                urlBuilder.append("&");
            }
            // 添加所有的参数
            for (String key : urlParams.keySet()) {
                urlBuilder.append(key).append("=").append(urlParams.get(key)).append("&");
            }
            urlBuilder.deleteCharAt(urlBuilder.lastIndexOf("&"));
        }
        builder.url(urlBuilder.toString());

        /** 构建headers */
        Map<String, String> headers = request.getHeaders();
        for (String key : headers.keySet()) {
            builder.addHeader(key, headers.get(key));
        }

        /** 构建其他 */
        switch (type) {
            case Method.GET:
                builder.get();
                break;
            case Method.POST:
                // 构建POST请求体
                // 若存在bodyString，使用bodyString
                if (!TextUtils.isEmpty(request.getBodyJson())) {
                    final MediaType jsonMediaType = MediaType.parse("application/json; charset=utf-8");
                    RequestBody requestBody = RequestBody.create(jsonMediaType, request.getBodyJson());
                    builder.post(requestBody);
                } else {
                    if (request.getMultipartBody() != null) {
                        builder.post(request.getMultipartBody());
                    } else {
                        // 否则使用FORM方式
                        FormBody.Builder formBuilder = new FormBody.Builder();
                        Map<String, String> postParams = request.getPostParams();
                        if (postParams != null && postParams.size() > 0) {
                            for (String key : postParams.keySet()) {
                                formBuilder.add(key, postParams.get(key));
                            }
                        }
                        builder.post(formBuilder.build());
                    }
                }
                break;
            default:
                break;
        }

        return builder.build();
    }
}
