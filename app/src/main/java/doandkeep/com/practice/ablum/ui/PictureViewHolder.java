package doandkeep.com.practice.ablum.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import doandkeep.com.practice.R;
import doandkeep.com.practice.ablum.vo.Album;

public class PictureViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;

    public PictureViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.picture);
        initListener();
    }

    private void initListener() {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 点击查看详情...
                Toast.makeText(view.getContext(), "查看详情", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void bind(Album album) {
        // TODO 展示图片的内容
    }

}
