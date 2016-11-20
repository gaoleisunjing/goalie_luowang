package com.app.activityfragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.adapter.ActActrecylerAdapter;
import com.app.bean.ActActBean;
import com.app.bean.ActActHeadBean;
import com.app.luooapp.R;
import com.app.urldatas.UrlData;
import com.app.utils.MyUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LWL on 2016/9/28.
 */
public class ActActFragment extends Fragment {
    RecyclerView recyclerView;
    List<ActActHeadBean.DataBean.ItemsBean> headData;
    List<String> urlString;
    List<ActActBean.DataBean.ItemsBean> data;
    ActActrecylerAdapter adapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    try{
                        ActActHeadBean s1 = (ActActHeadBean) msg.obj;
                        headData.addAll(s1.getData().getItems());
                        for (int i = 0; i < headData.size(); i++) {
                            urlString.add(headData.get(i).getImage());
                        }
                        Log.d("log","11==urlString==11"+urlString);
                        getDatas();
                    }catch (NullPointerException e){
                        return;
                    }


                    break;
                case 1:
                    ActActBean s2 = (ActActBean) msg.obj;
                    Log.d("log",s2+"");
                    data.addAll(s2.getData().getItems());
                    Log.d("log","11--data--11"+data);
                    if (urlString.size() != 0) {
                        Log.d("logggg","--data--"+data+"==urlString=="+urlString);
                        adapter = new ActActrecylerAdapter(data, urlString, getActivity().getApplicationContext());
                        LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
                        manager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(manager);

                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        urlString = new ArrayList<>();
        headData = new ArrayList<>();
        data = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_activity_act, null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.actactrecyclerId);
        initData();


    }

    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String headstring = MyUtils.getJsonStr(UrlData.ACTIVITY_ACTHEAD);
                Log.d("lwl", "ActActBean====" + headstring);
                Gson gson = new Gson();
                ActActHeadBean bean = gson.fromJson(headstring, ActActHeadBean.class);
                Message m = new Message();
                m.what = 0;
                m.obj = bean;
                handler.sendMessage(m);
            }
        }).start();



    }
    public void getDatas(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = MyUtils.getJsonStr(UrlData.ACTIVITY_ACT);
                Gson g = new Gson();
                ActActBean bean1 = g.fromJson(str, ActActBean.class);
                Log.d("lwl", "bean1=====" + bean1);
                Message m1 = new Message();
                m1.what = 1;
                m1.obj = bean1;
                handler.sendMessage(m1);
            }
        }).start();
    }
}
