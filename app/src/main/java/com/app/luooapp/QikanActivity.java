package com.app.luooapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.app.adapter.QikanAdapter;
import com.app.adapter.RecommendAdapter;
import com.app.adapter.VideoListAdapter;
import com.app.bean.QikanBean;
import com.app.bean.RecommendBean;
import com.app.bean.VideoListEntity;
import com.app.utils.MyUtils;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by hao on 2016-09-28.
 */
public class QikanActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private RecyclerView mRecyclerView;
    private Context context;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    List<QikanBean.DataBean.ItemsBean> items = (List<QikanBean.DataBean.ItemsBean>) msg.obj;
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                    mRecyclerView.setAdapter(new QikanAdapter(context, items));
                    break;
                case 1:
                    List<RecommendBean.DataBean.ItemsBean> items1 = (List<RecommendBean.DataBean.ItemsBean>) msg.obj;
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                    mRecyclerView.setAdapter(new RecommendAdapter(context, items1));
                    break;
                case 2:
                    List<VideoListEntity.DataBean.VideosBean> videos = (List<VideoListEntity.DataBean.VideosBean>) msg.obj;
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                    mRecyclerView.setAdapter(new VideoListAdapter(context, videos));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qikan);
        context = this;
        initView();
        back.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent.getStringExtra("qikan") != null) {
            String qikanUrl = intent.getStringExtra("qikan");
            downLoadQikanJson(qikanUrl);
        }
        if (intent.getStringExtra("recomm") != null) {
            String recomm = intent.getStringExtra("recomm");
            downLoadRecommJson(recomm);
        }
        if (intent.getStringExtra("videoUrls") != null) {
            String videoUrls = intent.getStringExtra("videoUrls");
            downLoadVideosJson(videoUrls);
        }
    }

    private void downLoadVideosJson(final String videoUrls) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonStr = MyUtils.getJsonStr(videoUrls);
                Gson gson = new Gson();
                VideoListEntity videoListEntity = gson.fromJson(jsonStr, VideoListEntity.class);
                VideoListEntity.DataBean data = videoListEntity.getData();
                List<VideoListEntity.DataBean.VideosBean> videos = data.getVideos();
                Message msg = new Message();
                msg.what = 2;
                msg.obj = videos;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    private void downLoadRecommJson(final String recomm) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonStr = MyUtils.getJsonStr(recomm);
                Gson gson = new Gson();
                RecommendBean recommendBean = gson.fromJson(jsonStr, RecommendBean.class);
                RecommendBean.DataBean data = recommendBean.getData();
                List<RecommendBean.DataBean.ItemsBean> items = data.getItems();
                Message msg = new Message();
                msg.what = 1;
                msg.obj = items;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    private void downLoadQikanJson(final String qikanUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonStr = MyUtils.getJsonStr(qikanUrl);
                Gson gson = new Gson();
                QikanBean qikanBean = gson.fromJson(jsonStr, QikanBean.class);
                QikanBean.DataBean data = qikanBean.getData();
                List<QikanBean.DataBean.ItemsBean> items = data.getItems();
                Message msg = new Message();
                msg.what = 0;
                msg.obj = items;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iv_qukan_top_back);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_qikan);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_qukan_top_back:
                finish();
                break;
        }
    }
}
