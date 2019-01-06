package doandkeep.com.practice.ablum.repository;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import doandkeep.com.practice.ablum.vo.Album;

public class AlbumRepository {

    public Listing<Album> albums() {
        Log.e("zzz", "albums");
        final AlbumDataSourceFactory sourceFactory = new AlbumDataSourceFactory();

        PagedList.Config config = new PagedList.Config.Builder()
                // TODO pagesize与接口中传递的关联关系
                .setPageSize(10)
                .setEnablePlaceholders(false)
                .build();

        LiveData<PagedList<Album>> albumList =
                new LivePagedListBuilder<>(sourceFactory, config)
                        .setInitialLoadKey(null)
                        .setBoundaryCallback(new PagedList.BoundaryCallback<Album>() {
                            @Override
                            public void onZeroItemsLoaded() {
                                super.onZeroItemsLoaded();
                            }

                            @Override
                            public void onItemAtFrontLoaded(@NonNull Album itemAtFront) {
                                super.onItemAtFrontLoaded(itemAtFront);
                            }

                            @Override
                            public void onItemAtEndLoaded(@NonNull Album itemAtEnd) {
                                super.onItemAtEndLoaded(itemAtEnd);
                            }
                        })
                        // TODO need provice a executor
//                        .setFetchExecutor(null)
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

    public LiveData<PagedList<Album>> albums2() {
        Log.e("zzz", "albums2");
        final AlbumDataSourceFactory sourceFactory = new AlbumDataSourceFactory();

        PagedList.Config config = new PagedList.Config.Builder()
                // TODO pagesize与接口中传递的关联关系
                .setPageSize(10)
                .setEnablePlaceholders(false)
                .build();

        LiveData<PagedList<Album>> albumList =
                new LivePagedListBuilder<>(sourceFactory, config)
                        .setInitialLoadKey(null)
                        .setBoundaryCallback(new PagedList.BoundaryCallback<Album>() {
                            @Override
                            public void onZeroItemsLoaded() {
                                super.onZeroItemsLoaded();
                            }

                            @Override
                            public void onItemAtFrontLoaded(@NonNull Album itemAtFront) {
                                super.onItemAtFrontLoaded(itemAtFront);
                            }

                            @Override
                            public void onItemAtEndLoaded(@NonNull Album itemAtEnd) {
                                super.onItemAtEndLoaded(itemAtEnd);
                            }
                        })
                        // TODO need provice a executor
//                        .setFetchExecutor(null)
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
        return albumList;
    }
}
