package doandkeep.com.practice.network;

public interface ICallback<T> {

    void onSuccess(Response<T> response);

    void onFail(int code, Throwable e);
}
