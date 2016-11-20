package com.app.luooapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by hao on 2016-09-28.
 */
public class VideoPlayActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back, share, play;
    private View alpha;
    private SimpleDraweeView mSimpleDraweeView;
    private TextView mTextViewtime, mTextViewName, mTextViewArtist, mTextViewType, mTextViewContent;
    private VideoView mVideoView;
    private boolean isPlaying;
    private String playurl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplay);
        initView();
        back.setOnClickListener(this);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String artist = intent.getStringExtra("artist");
        String time = intent.getStringExtra("time");
        String content = intent.getStringExtra("content");
        String type = intent.getStringExtra("type");
        playurl = intent.getStringExtra("playurl");
        String imageurl = intent.getStringExtra("imageurl");
        mSimpleDraweeView.setImageURI(imageurl);
        mTextViewtime.setText(time);
        mTextViewName.setText(name);
        mTextViewArtist.setText(artist);
        mTextViewType.setText("#" + type + " 2016-09-20");
        mTextViewContent.setText(content);
        play.setOnClickListener(this);
        //监听视频播放完成
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.seekTo(0);
                mp.start();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏时全屏播放
//            getSupportActionBar().hide();//隐藏ActionBar
            //设置videoview的宽高以及对齐属性
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            mVideoView.setLayoutParams(params);//设置videoview的布局参数
            //全屏并且隐藏信号栏
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else {
//            getSupportActionBar().show();
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    720, 1080);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            mVideoView.setLayoutParams(params);
            getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE);
        }
        super.onConfigurationChanged(newConfig);
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iv_videoplay_top_back);
        share = (ImageView) findViewById(R.id.iv_videoplay_top_share);
        alpha = findViewById(R.id.view_video_alpha);
        mSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.fresco_videoplay);
        play = (ImageView) findViewById(R.id.iv_videoplay);
        mTextViewtime = (TextView) findViewById(R.id.tv_videoplay_time);
        mTextViewName = (TextView) findViewById(R.id.tv_videoplay_name);
        mTextViewArtist = (TextView) findViewById(R.id.tv_videoplay_artist);
        mTextViewType = (TextView) findViewById(R.id.tv_videoplay_date);
        mTextViewContent = (TextView) findViewById(R.id.tv_videoplay_content);
        mVideoView = (VideoView) findViewById(R.id.vv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_videoplay:
                if (isPlaying) {
                    mVideoView.pause();
                    play.setVisibility(View.VISIBLE);
                    mTextViewtime.setVisibility(View.VISIBLE);
                    alpha.setVisibility(View.VISIBLE);
                } else {
                    mVideoView.setVideoPath(playurl);
                    mVideoView.setMediaController(new MediaController(this));//设置视频播放控制单元
                    mVideoView.start();
                    play.setVisibility(View.INVISIBLE);
                    mTextViewtime.setVisibility(View.INVISIBLE);
                    alpha.setVisibility(View.INVISIBLE);
                    mSimpleDraweeView.setVisibility(View.INVISIBLE);
                }
                isPlaying = !isPlaying;
                break;
            case R.id.iv_videoplay_top_back:
                finish();
                break;

        }
    }
}
