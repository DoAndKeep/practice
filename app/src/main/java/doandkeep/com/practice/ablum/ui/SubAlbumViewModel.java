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
import doandkeep.com.practice.ablum.vo.Album;

public class SubAlbumViewModel extends ViewModel {

    private AlbumRepository repository = new AlbumRepository();

    private LiveData<PagedList<Album>> aaa = new MutableLiveData<>();

    private MutableLiveData<Void> ff = new MutableLiveData<>();
    private MutableLiveData<Listing<Album>> albumResult2 = new MutableLiveData<>();
    private LiveData<Listing<Album>> albumResult = Transformations.map(ff, new Function<Void, Listing<Album>>() {
        @Override
        public Listing<Album> apply(Void input) {
            return repository.albums();
        }
    });

    private LiveData<PagedList<Album>> albums = Transformations.switchMap(albumResult,
            new Function<Listing<Album>, LiveData<PagedList<Album>>>() {
                @Override
                public LiveData<PagedList<Album>> apply(Listing<Album> input) {
                    return input.getPagedList();
                }
            });
    private LiveData<NetworkState> networkState = Transformations.switchMap(albumResult,
            new Function<Listing<Album>, LiveData<NetworkState>>() {
                @Override
                public LiveData<NetworkState> apply(Listing<Album> input) {
                    return input.getNetworkState();
                }
            });
    private LiveData<NetworkState> refreshState = Transformations.switchMap(albumResult,
            new Function<Listing<Album>, LiveData<NetworkState>>() {
                @Override
                public LiveData<NetworkState> apply(Listing<Album> input) {
                    return input.getRefreshState();
                }
            });

    public void showSubAlbum() {
        ff.setValue(null);
//        aaa = repository.albums2();
//        albumResult.setValue(repository.albums());
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

    public LiveData<PagedList<Album>> getAaa() {
        return aaa;
    }

    public LiveData<PagedList<Album>> getAlbums() {
        return albums;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<NetworkState> getRefreshState() {
        return refreshState;
    }
}
