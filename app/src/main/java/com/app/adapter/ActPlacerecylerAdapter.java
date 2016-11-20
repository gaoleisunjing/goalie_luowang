package com.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bean.ActplaceBean;


import com.app.luooapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by LWL on 2016/9/28.
 */
public class ActPlacerecylerAdapter extends RecyclerView.Adapter<ActPlacerecylerAdapter.MyViewHolder> {
    List<ActplaceBean.DataBean.ItemsBean> list;

    public ActPlacerecylerAdapter(List<ActplaceBean.DataBean.ItemsBean> list) {
        this.list = list;
    }

    @Override
    public ActPlacerecylerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_actity_place, parent, false));
    }

    @Override
    public void onBindViewHolder(ActPlacerecylerAdapter.MyViewHolder holder, int position) {
        try {
            holder.textView2.setText(list.get(position).getDistance());
            holder.textView1.setText(list.get(position).getName());
            String url = list.get(position).getLogo_url();
            Picasso.with(holder.itemView.getContext()).load(url).into(holder.imageView);

        } catch (Exception e) {
            return;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1, textView2;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.actplace_name);
            textView2 = (TextView) itemView.findViewById(R.id.actplace_distanceId);
            imageView = (ImageView) itemView.findViewById(R.id.actplace_logo_url);
        }
    }
}
