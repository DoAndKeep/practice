package doandkeep.com.practice.ablum.repository;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

public class Listing<T> {

    private LiveData<PagedList<T>> pagedList;
    private LiveData<NetworkState> networkState;
    private LiveData<NetworkState> refreshState;
    private RefreshCallback refreshCallback;
    private RetryCallback retryCallback;

    public Listing(LiveData<PagedList<T>> pagedList, LiveData<NetworkState> networkState,
                   LiveData<NetworkState> refreshState, RefreshCallback refreshCallback, RetryCallback retryCallback) {
        this.pagedList = pagedList;
        this.networkState = networkState;
        this.refreshState = refreshState;
        this.refreshCallback = refreshCallback;
        this.retryCallback = retryCallback;
    }

    public LiveData<PagedList<T>> getPagedList() {
        return pagedList;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<NetworkState> getRefreshState() {
        return refreshState;
    }

    public RefreshCallback getRefreshCallback() {
        return refreshCallback;
    }

    public RetryCallback getRetryCallback() {
        return retryCallback;
    }
}
