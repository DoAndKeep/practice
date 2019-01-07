package doandkeep.com.practice.ablum.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import doandkeep.com.practice.GlideApp;
import doandkeep.com.practice.R;
import doandkeep.com.practice.ablum.vo.Album;

public class PictureViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;
    private ImageView imageView;

    public PictureViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.name);
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
        GlideApp.with(imageView.getContext())
                .load("https://www.baidu.com/img/superlogo_c4d7df0a003d3db9b65e9ef0fe6da1ec.png?qua=high&where=super")
                .into(imageView);
        textView.setText(album.owner.login);
    }

}
