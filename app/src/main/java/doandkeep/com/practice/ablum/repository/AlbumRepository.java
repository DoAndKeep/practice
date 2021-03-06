package doandkeep.com.practice.ablum.repository;

import android.util.Log;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import doandkeep.com.practice.ablum.vo.AlbumItem;

public class AlbumRepository {

    public Listing<AlbumItem> albums() {
        Log.e("zzz", "albums");
        final AlbumDataSourceFactory sourceFactory = new AlbumDataSourceFactory();

        PagedList.Config config = new PagedList.Config.Builder()
                // TODO pagesize与接口中传递的关联关系
                .setPageSize(10)
//                .setPrefetchDistance(0)
//                .setInitialLoadSizeHint(0)
                .setEnablePlaceholders(false)
                .build();

        // TODO builder的其他参数
        LiveData<PagedList<AlbumItem>> albumList =
                new LivePagedListBuilder<>(sourceFactory, config)
                        .setBoundaryCallback(new AlbumBoundaryCallback())
                        .build();

        LiveData<NetworkState> networkState = Transformations.switchMap(sourceFactory.getSourceLiveData(),
                new Function<AlbumDataSource, LiveData<NetworkState>>() {
                    @Override
                    public LiveData<NetworkState> apply(AlbumDataSource input) {
                        return input.getNetworkState();
                    }
                });

        LiveData<NetworkState> refreshState = Transformations.switchMap(sourceFactory.getSourceLiveData(),
                new Function<AlbumDataSource, LiveData<NetworkState>>() {
                    @Override
                    public LiveData<NetworkState> apply(AlbumDataSource input) {
                        return input.getInitialLoad();
                    }
                });
        // TODO refresh、retry回调逻辑完善
        return new Listing(albumList, networkState, refreshState,
                new RefreshCallback() {
                    @Override
                    public void refresh() {
                        sourceFactory.getSourceLiveData().getValue().invalidate();
                    }
                },
                new RetryCallback() {
                    @Override
                    public void retry() {
                        sourceFactory.getSourceLiveData().getValue().retryAllFailed();
                    }
                });
    }
}
