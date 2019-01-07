package doandkeep.com.practice.ablum.ui;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;
import doandkeep.com.practice.ablum.repository.AlbumRepository;
import doandkeep.com.practice.ablum.repository.Listing;
import doandkeep.com.practice.ablum.repository.NetworkState;
import doandkeep.com.practice.ablum.vo.AlbumItem;

public class AlbumViewModel extends ViewModel {

    // TODO albumResult如何更恰当的初始化

    private AlbumRepository repository = new AlbumRepository();
    private MutableLiveData<Listing<AlbumItem>> albumResult = new MutableLiveData<>();

    private LiveData<PagedList<AlbumItem>> albums = Transformations.switchMap(albumResult,
            new Function<Listing<AlbumItem>, LiveData<PagedList<AlbumItem>>>() {
                @Override
                public LiveData<PagedList<AlbumItem>> apply(Listing<AlbumItem> input) {
                    return input.getPagedList();
                }
            });
    private LiveData<NetworkState> networkState = Transformations.switchMap(albumResult,
            new Function<Listing<AlbumItem>, LiveData<NetworkState>>() {
                @Override
                public LiveData<NetworkState> apply(Listing<AlbumItem> input) {
                    return input.getNetworkState();
                }
            });
    private LiveData<NetworkState> refreshState = Transformations.switchMap(albumResult,
            new Function<Listing<AlbumItem>, LiveData<NetworkState>>() {
                @Override
                public LiveData<NetworkState> apply(Listing<AlbumItem> input) {
                    return input.getRefreshState();
                }
            });

    public AlbumViewModel() {
        albumResult.setValue(repository.albums());
    }

    public void refresh() {
        if (albumResult.getValue() != null && albumResult.getValue().getRefreshCallback() != null) {
            albumResult.getValue().getRefreshCallback().refresh();
        }
    }

    public void retry() {
        if (albumResult.getValue() != null && albumResult.getValue().getRetryCallback() != null) {
            albumResult.getValue().getRetryCallback().retry();
        }
    }

    public LiveData<PagedList<AlbumItem>> getAlbums() {
        return albums;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<NetworkState> getRefreshState() {
        return refreshState;
    }
}
