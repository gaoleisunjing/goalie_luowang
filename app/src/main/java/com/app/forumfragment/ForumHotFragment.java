package com.app.forumfragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.adapter.ForumHotAdapter;
import com.app.bean.HotEntity;
import com.app.luooapp.R;
import com.app.manager.MyLinearLayoutManager;
import com.app.urldatas.UrlData;
import com.app.utils.MyUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peiyangyang on 2016/9/28.
 */
public class ForumHotFragment extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    List<HotEntity.DataBean.ItemsBean> mItemsBeen;

    ForumHotAdapter mHotAdapter;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum_hot, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.forum_item_swipe);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.forum_item_recycler);
       //给recyclerview设置分割线
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 101:
                    HotEntity hotEntity = (HotEntity) msg.obj;
                    if (hotEntity != null) {
                        mItemsBeen.addAll(hotEntity.getData().getItems());
                        mHotAdapter.notifyDataSetChanged();

                    }
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mItemsBeen = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonString = MyUtils.getJsonStr(UrlData.FORUM_HOT);
                Log.d("11111-jsonString-111111", "" + jsonString);
                Gson gson = new Gson();
                HotEntity hotEntity = gson.fromJson(jsonString, HotEntity.class);
                //使用handler发送
                Message message = Message.obtain();
                message.what = 101;
                message.obj = hotEntity;
                mHandler.sendMessage(message);
                Log.d("hotEntity", "" + hotEntity);

            }
        }).start();

        mHotAdapter = new ForumHotAdapter(mItemsBeen, getContext());
        //设置详情页的跳转
        mHotAdapter.setOnItemClickListener(new ForumHotAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position, long id) {
                mHotAdapter.skipDetails(position);
            }
        });
        mRecyclerView.setAdapter(mHotAdapter);
        //设置布局管理器
        mRecyclerView.setLayoutManager(new MyLinearLayoutManager(getContext()));
        //设置导航圈
        mSwipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.CYAN, Color.DKGRAY, Color.GRAY, Color.MAGENTA);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                try {
                    Thread.sleep(2000);
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(), "已加载最新数据。", Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //给recyclerview设置分割线，定义的内部类
    class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = context.getResources().getDrawable(R.drawable.line_divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}
