package doandkeep.com.practice.ablum.repository;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.ItemKeyedDataSource;
import doandkeep.com.practice.ablum.vo.Album;
import doandkeep.com.practice.ablum.vo.AlbumResult;
import doandkeep.com.practice.network.BaseRequest;
import doandkeep.com.practice.network.ICallback;
import doandkeep.com.practice.network.NetworkHelper;
import doandkeep.com.practice.network.Response;

public class AlbumDataSource extends ItemKeyedDataSource<Long, Album> {

    private String url = "https://api.github.com/search/repositories?q=android&page=";

    // TODO retryCallback的逻辑完善
    private RetryCallback retryCallback;

    private MutableLiveData<NetworkState> networkState = new MutableLiveData<>();
    private MutableLiveData<NetworkState> initialLoad = new MutableLiveData<>();

    @Override
    public void loadInitial(@NonNull final ItemKeyedDataSource.LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Album> callback) {
        Log.e("zzz:", "loadInitial");
        networkState.postValue(new NetworkState(Status.RUNNING));
        initialLoad.postValue(new NetworkState(Status.RUNNING));

        // TODO 使用正确的请求
        BaseRequest<AlbumResult> request = new BaseRequest<>(AlbumResult.class, url, new ICallback<AlbumResult>() {
            @Override
            public void onSuccess(Response<AlbumResult> response) {
                Log.e("zzz:", "loadInitial_onSuccess");
                networkState.postValue(new NetworkState(Status.SUCCESS));
                initialLoad.postValue(new NetworkState(Status.SUCCESS));
                callback.onResult(response.getEntity().items);
                retryCallback = null;
            }

            @Override
            public void onFail(int code, Throwable e) {
                Log.e("zzz:", "loadInitial_onFail,code:" + code);
                e.printStackTrace();
                retryCallback = new RetryCallback() {
                    @Override
                    public void retry() {
                        loadInitial(params, callback);
                    }
                };
                networkState.postValue(new NetworkState(Status.FAILED, "error:" + code));
                initialLoad.postValue(new NetworkState(Status.FAILED, "error:" + code));
            }
        });
        try {
            Response<AlbumResult> r = NetworkHelper.getInstance().syncGet(request);
            networkState.postValue(new NetworkState(Status.SUCCESS));
            initialLoad.postValue(new NetworkState(Status.SUCCESS));
            callback.onResult(r.getEntity().items);
            Log.e("zzz:", "loadInitial_callback");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Album> callback) {
        networkState.postValue(new NetworkState(Status.RUNNING));

        BaseRequest<AlbumResult> request = new BaseRequest<>(AlbumResult.class, url, new ICallback<AlbumResult>() {
            @Override
            public void onSuccess(Response<AlbumResult> response) {
                networkState.postValue(new NetworkState(Status.SUCCESS));
                callback.onResult(response.getEntity().items);
            }

            @Override
            public void onFail(int code, Throwable e) {
                retryCallback = new RetryCallback() {
                    @Override
                    public void retry() {
                        loadAfter(params, callback);
                    }
                };
                networkState.postValue(new NetworkState(Status.FAILED, "error:" + code));
            }
        });
        try {
            Response<AlbumResult> r = NetworkHelper.getInstance().syncGet(request);
            networkState.postValue(new NetworkState(Status.SUCCESS));
            initialLoad.postValue(new NetworkState(Status.SUCCESS));
            callback.onResult(r.getEntity().items);
            Log.e("zzz:", "loadAfter_callback");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Album> callback) {
        // ignored, since we only ever append to our initial load
    }

    @NonNull
    @Override
    public Long getKey(@NonNull Album item) {
        // TODO 使用正确的Key
        Log.e("zzz:", "getKey,key:" + item.id);
        return item.id;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<NetworkState> getInitialLoad() {
        return initialLoad;
    }

    public void retryAllFailed() {
        final RetryCallback preCallback = retryCallback;
        retryCallback = null;
        if (preCallback != null) {
            // TODO 使用线程池来运行
            new Thread(new Runnable() {
                @Override
                public void run() {
                    preCallback.retry();
                }
            }).start();
        }
    }
}
