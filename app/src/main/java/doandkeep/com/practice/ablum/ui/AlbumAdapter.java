package doandkeep.com.practice.ablum.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import doandkeep.com.practice.R;
import doandkeep.com.practice.ablum.repository.NetworkState;
import doandkeep.com.practice.ablum.repository.RetryCallback;
import doandkeep.com.practice.ablum.repository.Status;
import doandkeep.com.practice.ablum.vo.Album;
import doandkeep.com.practice.ablum.vo.AlbumItem;

import java.util.List;

public class AlbumAdapter extends PagedListAdapter<AlbumItem, RecyclerView.ViewHolder> {

    private RetryCallback retryCallback;

    private NetworkState networkState;

    // TODO 熟悉作用并优化Comparator
    private static DiffUtil.ItemCallback<AlbumItem> ALBUM_COMPARATOR = new DiffUtil.ItemCallback<AlbumItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull AlbumItem album, @NonNull AlbumItem t1) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull AlbumItem album, @NonNull AlbumItem t1) {
            return false;
        }
    };

    public AlbumAdapter(RetryCallback retryCallback) {
        super(ALBUM_COMPARATOR);
        this.retryCallback = retryCallback;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRaw() && position == getItemCount() - 1) {
            return R.layout.network_state_item;
        } else {
            return R.layout.album_picture_item;
        }
    }

    @Override
    public int getItemCount() {
        int extra = hasExtraRaw() ? 1 : 0;
        return super.getItemCount() + extra;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case R.layout.album_picture_item:
                View pictureView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.album_picture_item, viewGroup, false);
                viewHolder = new PictureViewHolder(pictureView);
                break;
            case R.layout.network_state_item:
                View networkStateView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.album_picture_item, viewGroup, false);
                viewHolder = new NetworkStateViewHolder(networkStateView);
                break;
            default:
                // TODO 合理的错误兼容
                throw new IllegalArgumentException("unkown view type");
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.album_picture_item:
                if (viewHolder instanceof PictureViewHolder) {
                    PictureViewHolder pictureViewHolder = (PictureViewHolder) viewHolder;
                    pictureViewHolder.bind((Album) getItem(position));
                }
                break;
            case R.layout.network_state_item:
                if (viewHolder instanceof NetworkStateViewHolder) {
                    NetworkStateViewHolder networkStateViewHolder = (NetworkStateViewHolder) viewHolder;
                    networkStateViewHolder.bind(networkState);
                }
                break;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        // TODO 这个方法的作用
        super.onBindViewHolder(holder, position, payloads);
    }

    /**
     * 是否有额外的行来显示状态
     */
    private boolean hasExtraRaw() {
        return networkState != null && networkState.getStatus() != Status.SUCCESS;
    }

    /**
     * 设置新的状态，并更新状态显示
     */
    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState preState = this.networkState;
        boolean hadExtraRaw = hasExtraRaw();
        this.networkState = newNetworkState;
        boolean hasExtraRaw = hasExtraRaw();
        if (hadExtraRaw != hasExtraRaw) {
            if (hadExtraRaw) {
                notifyItemRemoved(super.getItemCount());
            } else {
                notifyItemInserted(super.getItemCount());
            }
        } else if (hasExtraRaw && preState != newNetworkState) {
            // TODO 这里应该使用equals来进行判断，或者单例，需要重新修改
            notifyItemChanged(getItemCount() - 1);
        }
    }

}
