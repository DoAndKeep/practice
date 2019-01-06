package doandkeep.com.practice.ablum.ui;

import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import doandkeep.com.practice.ablum.repository.NetworkState;

public class NetworkStateViewHolder extends RecyclerView.ViewHolder {

    // TODO 将retry callback传递进来

    public NetworkStateViewHolder(@NonNull View itemView) {
        super(itemView);
        initListener();
    }

    private void initListener() {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 点击重试...
                Toast.makeText(view.getContext(), "重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void bind(NetworkState networkState) {
        // TODO 展示内容
    }

}
