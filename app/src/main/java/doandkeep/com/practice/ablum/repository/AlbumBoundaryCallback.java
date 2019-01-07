package doandkeep.com.practice.ablum.repository;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.paging.PagedList;

/**
 * Created by zhangtao on 2019/1/7.
 */
public class AlbumBoundaryCallback extends PagedList.BoundaryCallback {

    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        Log.e("zzz", "onZeroItemsLoaded");
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull Object itemAtFront) {
        super.onItemAtFrontLoaded(itemAtFront);
        Log.e("zzz", "onItemAtFrontLoaded");
    }

    @Override
    public void onItemAtEndLoaded(@NonNull Object itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        Log.e("zzz", "onItemAtEndLoaded");
    }
}
