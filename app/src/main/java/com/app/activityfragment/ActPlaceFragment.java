package com.app.activityfragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.app.adapter.ActPlacerecylerAdapter;
import com.app.bean.ActplaceBean;
import com.app.luooapp.R;
import com.app.urldatas.UrlData;
import com.app.utils.MyUtils;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by LWL on 2016/9/28.
 */
public class ActPlaceFragment extends Fragment {
    RecyclerView recyclerView;
    List<ActplaceBean.DataBean.ItemsBean> data;
    ActPlacerecylerAdapter adapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    try {
                        ActplaceBean ss = (ActplaceBean) msg.obj;
                        data.addAll(ss.getData().getItems());
                        adapter.notifyDataSetChanged();
                    } catch (NullPointerException e) {
                        return;
                    }

                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actity_place, null);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.actplacerecylerviewId);
        initData();
        adapter = new ActPlacerecylerAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        data = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = MyUtils.getJsonStr(UrlData.ACTIVITY_PLACE);
                Gson gson = new Gson();
                ActplaceBean bean = gson.fromJson(str, ActplaceBean.class);
                Message message = new Message();
                message.what = 1;
                message.obj = bean;
                handler.sendMessage(message);

            }
        }).start();
    }
}
