package doandkeep.com.practice.ablum.ui;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import doandkeep.com.practice.R;
import doandkeep.com.practice.ablum.vo.Section;

public class SectionViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public SectionViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.name);
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

    public void bind(Section section) {
        textView.setText(section.title);
    }

}
