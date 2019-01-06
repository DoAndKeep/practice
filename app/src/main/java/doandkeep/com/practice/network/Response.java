package doandkeep.com.practice.network;

import androidx.annotation.Nullable;

/**
 * Okhttp Response包装类
 *
 * @author longyin01
 * @since 2018/11/7
 */
public class Response<T> {

    /**
     * Status Code
     */
    private int statusCode;
    /**
     * Response URL，若请求存在重定向，返回重定向后的URL
     */
    private String url;
    /**
     * Response 实体
     */
    private T entity;

    public Response(int statusCode, String url, T entity) {
        this.statusCode = statusCode;
        this.url = url;
        this.entity = entity;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getUrl() {
        return url;
    }

    public @Nullable
    T getEntity() {
        return entity;
    }
}
