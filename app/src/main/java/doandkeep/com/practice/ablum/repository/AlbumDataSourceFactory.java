package doandkeep.com.practice.ablum.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import doandkeep.com.practice.ablum.vo.Album;

/**
 * DataSourceFactory，同时提供监测上一个创建的DataSource的途径。
 */
public class AlbumDataSourceFactory extends DataSource.Factory<Long, Album> {

    private MutableLiveData<AlbumDataSource> sourceLiveData = new MutableLiveData<>();

    @Override
    public DataSource<Long, Album> create() {
        AlbumDataSource source = new AlbumDataSource();
        sourceLiveData.postValue(source);
        return source;
    }

    public LiveData<AlbumDataSource> getSourceLiveData() {
        return sourceLiveData;
    }
}
