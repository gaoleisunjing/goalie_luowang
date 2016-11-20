package com.app.luooapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

/**
 * Created by peiyangyang on 2016/9/29.
 */
public class ArtistActivity extends AppCompatActivity {
private ImageView mArtistIv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        mArtistIv = (ImageView) findViewById(R.id.iv_artist_back);
        mArtistIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
