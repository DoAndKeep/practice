package doandkeep.com.practice.ablum.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import doandkeep.com.practice.R;
import doandkeep.com.practice.ablum.repository.NetworkState;
import doandkeep.com.practice.ablum.repository.RetryCallback;
import doandkeep.com.practice.ablum.repository.Status;
import doandkeep.com.practice.ablum.vo.AlbumItem;

public class AlbumActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private AlbumAdapter adapter;

    private AlbumViewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        recyclerView = findViewById(R.id.recycler_view);

        model = ViewModelProviders.of(this).get(AlbumViewModel.class);
        initAdapter();
        initSwipeToRefresh();
    }

    private void initAdapter() {
        adapter = new AlbumAdapter(new RetryCallback() {
            @Override
            public void retry() {
                model.retry();
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.getItemViewType(position) == R.layout.album_picture_item) {
                    return 1;
                }

                return 3;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        model.getAlbums().observe(this, new Observer<PagedList<AlbumItem>>() {
            @Override
            public void onChanged(@Nullable PagedList<AlbumItem> albums) {
                adapter.submitList(albums);
            }
        });
        model.getNetworkState().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(@Nullable NetworkState networkState) {
                adapter.setNetworkState(networkState);
            }
        });
    }

    private void initSwipeToRefresh() {
        model.getRefreshState().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                if (networkState.getStatus() != Status.RUNNING) {
                    swipeRefreshLayout.setRefreshing(false);
                }
//                swipeRefreshLayout.setRefreshing(networkState.getStatus() == Status.RUNNING);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                model.refresh();
            }
        });
    }
}
