package doandkeep.com.practice.network;

/**
 * BOSS接口错误码
 *
 * @see <a href="http://agroup.baidu.com/duer/md/article/449678">agroup</a>
 *
 * Created by longyin01 on 2018/7/10.
 */

public class HttpStatus {

    /**
     * 常用错误
     */
    public static final int STATUS_OK = 0;
    public static final int STATUS_ERROR = 1;
    public static final int STATUS_ERROR_NOT_LOGIN = 2;
    public static final int STATUS_ERROR_NOT_ALLOWED = 3;
    public static final int STATUS_ERROR_DB_WRITE = 4;
    public static final int STATUS_ERROR_NO_DATA = 5;
    public static final int STATUS_ERROR_ALREADY_EXIST = 6;
    public static final int STATUS_ERROR_NOT_FOUND = 7;
    public static final int STATUS_ERROR_HTTP_METHOD = 1000;
    public static final int STATUS_ERROR_PARAM_MISS = 1001;
    public static final int STATUS_ERROR_PARAM_INVALID = 1002;

    /**
     * ral错误
     */
    public static final int RAL_ERROR_GET_TOKEN_FAIL = 1401;
    public static final int RAL_ERROR_ACCESS_DENIED = 1403;

    /**
     * 设备管理接口错误
     */
    public static final int DEVICE_STATUS_ERR_RELATIONSHIP_NOT_FOUND = 1501;
    public static final int DEVICE_STATUS_ERR_ACCESS_TOKEN_NOT_FOUND = 1502;
    public static final int DEVICE_STATUS_ERR_REFRESH_TOKEN_NOT_FOUND = 1503;
    public static final int DEVICE_STATUS_ERR_CLIENTID_INVALID = 1511;
    public static final int DEVICE_STATUS_ERR_ACCESS_TOKEN_INVALID = 1512;
    public static final int DEVICE_STATUS_ERR_REFRESH_TOKEN_INVALID = 1513;
    public static final int DEVICE_STATUS_ERR_RECORD_EXPIRE_TOKEN_FAIL = 1521;
    public static final int DEVICE_STATUS_ERR_TOKEN_CLIENTID_NOT_MATCH = 1522;
    public static final int SIGN_VERIFY_FAIL = 1601;
}
