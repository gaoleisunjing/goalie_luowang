package com.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.adapter.DiscoverAdapter;
import com.app.bean.DiscoverEntity;
import com.app.luooapp.R;
import com.app.urldatas.UrlData;
import com.app.utils.MyUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hao on 2016-09-23.
 */
public class DiscoverFragment extends Fragment implements View.OnClickListener {
    private ImageView mUserIcon;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private Button mRefreshButton;
    private RelativeLayout mRelativeLayout;
    private static final int IMG_URL = 0;
    private static final int DAILY = 833;
    private static final int ERROR = 752;
    private static String jsonString;
    private List<String> mList = new ArrayList<>();
    private List<ImageView> mImageViews = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IMG_URL:
                    mRelativeLayout.setVisibility(View.GONE);
                    DiscoverEntity discoverEntity = (DiscoverEntity) msg.obj;
                    List<DiscoverEntity.DataBean.AdsBean> ads = discoverEntity.getData().getAds();
                    for (int i = 0; i < ads.size(); i++) {
                        String image = ads.get(i).getImage();
                        mList.add(image);
                    }
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRecyclerView.setAdapter(new DiscoverAdapter(getContext(), mList, discoverEntity));
                    break;
                case ERROR:
                    mRelativeLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "请检查网络", Toast.LENGTH_SHORT).show();//提示网络异常
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    public void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                jsonString = MyUtils.getJsonStr(UrlData.DISCOVER);
                if (jsonString == null) {
                    mHandler.sendEmptyMessage(ERROR);
                    return;
                }
                Message msg1 = new Message();
                DiscoverEntity discoverEntity = gson.fromJson(jsonString, DiscoverEntity.class);
                msg1.what = IMG_URL;
                msg1.obj = discoverEntity;
                mHandler.sendMessage(msg1);

            }
        }).start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_discover, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mUserIcon = (ImageView) view.findViewById(R.id.iv_discover_title_user);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_discover_swiperefresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_discover);
        mRefreshButton = (Button) view.findViewById(R.id.btn_refresh);
        mRelativeLayout = (RelativeLayout) view.findViewById(R.id.layout_discover_nonenetwork);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();

    }

    /**
     * 事件监听
     */
    private void initListener() {
        mUserIcon.setOnClickListener(this);
        mRefreshButton.setOnClickListener(this);
    }

    OnIconClickListener mListener;//接口声明

    /**
     * 监听点击
     */
    public interface OnIconClickListener {
        void onIconClickListener();
    }

    /**
     * 提供一个方法，点击头像打开左抽屉
     */
    public void setOnIconClickListener(OnIconClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_discover_title_user:
                mListener.onIconClickListener();//点击头像后在MainAvtivity中触发此方法
                break;
            case R.id.btn_refresh:
                break;
        }
    }
}
