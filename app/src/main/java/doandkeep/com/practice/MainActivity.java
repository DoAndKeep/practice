package doandkeep.com.practice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import doandkeep.com.practice.ablum.ui.AlbumActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlbum();
            }
        });
    }

    private void showAlbum() {
        Intent intent = new Intent(this, AlbumActivity.class);
        startActivity(intent);
    }

}
