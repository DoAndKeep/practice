package doandkeep.com.practice.network;

import okhttp3.MultipartBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求时，传送参数
 *
 * @author liuyong05
 * @since 2018-4-18
 */
public class BaseRequest<T> {
    protected Class<T> mClass;
    protected ICallback mCallback;
    protected String mUrl;
    protected boolean mEnableCookies;
    protected boolean mEnableApppIdCookie;
    protected String mBodyJson;
    protected MultipartBody multipartBody;


    // 用于标识请求的标识，可用来取消网络请求
    protected Object mTag;

    /**
     * url参数，Post请求也会有在链接后拼装参数
     */
    private final Map<String, String> mUrlParams;
    /**
     * post参数
     */
    private final Map<String, String> mPostParams;
    /**
     * Headers
     */
    protected final Map<String, String> mHeaders;

    public BaseRequest(Class<T> cls, String url, ICallback<T> callback) {
        mUrlParams = new HashMap<>();
        mPostParams = new HashMap<>();
        mHeaders = new HashMap<>();
        mEnableCookies = true;

        this.mClass = cls;
        this.mCallback = callback;
        this.mUrl = url;
        initHeader();
    }

    protected void initHeader() {

    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public Object getTag() {
        return this.mTag;
    }

    public void setEnableCookies(boolean enableCookies) {
        this.mEnableCookies = enableCookies;
    }

    public boolean isEnableCookies() {
        return mEnableCookies;
    }

    public boolean isEnableApppIdCookie() {
        return mEnableApppIdCookie;
    }

    public void setEnableApppIdCookie(boolean mEnableApppIdCookie) {
        this.mEnableApppIdCookie = mEnableApppIdCookie;
    }

    public void setBodyJson(String body) {
        this.mBodyJson = body;
    }

    public String getBodyJson() {
        return mBodyJson;
    }

    /**
     * 配置url参数
     *
     * @param key   k
     * @param value v
     */
    public void putUrlParams(String key, String value) {
        mUrlParams.put(key, value);
    }

    /**
     * 配置post参数
     *
     * @param key
     * @param value
     */
    public void putPostParams(String key, String value) {
        mPostParams.put(key, value);
    }

    public void addHeader(String key, String value) {
        mHeaders.put(key, value);
    }

    public Map<String, String> getUrlParams() {
        return mUrlParams;
    }

    public Map<String, String> getPostParams() {
        return mPostParams;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public void setMultipartBody(MultipartBody multipartBody) {
        this.multipartBody = multipartBody;
    }

    public MultipartBody getMultipartBody() {
        return multipartBody;
    }
}
