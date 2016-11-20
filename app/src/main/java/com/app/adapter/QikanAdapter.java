package com.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.bean.Ad1Entity;
import com.app.bean.QikanBean;
import com.app.luooapp.DetailActivity;
import com.app.luooapp.R;
import com.app.urldatas.UrlData;
import com.app.utils.MyUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by hao on 2016-09-28.
 */
public class QikanAdapter extends RecyclerView.Adapter<QikanAdapter.QikanHolder> {
    private Context context;
    List<QikanBean.DataBean.ItemsBean> items;
    private Ad1Entity ad1Entity;

    public QikanAdapter(Context context, List<QikanBean.DataBean.ItemsBean> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public QikanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_discover_first, null);
        return new QikanHolder(inflate);
    }

    @Override
    public void onBindViewHolder(QikanHolder holder, int position) {
        QikanBean.DataBean.ItemsBean itemsBean = items.get(position);
        holder.comm.setText(itemsBean.getComm() + "");
        holder.fav.setText(itemsBean.getFav() + "");
        holder.firitem.setImageURI(itemsBean.getImageurl());
        holder.firstKuang.setText("vol.\n" + itemsBean.getNumber());
        holder.name.setText(itemsBean.getName());
        TextView textView = new TextView(context);
        textView.setText(itemsBean.getTags());
        textView.setTextSize(10);
        textView.setBackground(context.getResources().getDrawable(R.drawable.text_bac));
        if (holder.mLayout.getChildCount() == 0) {
            holder.mLayout.addView(textView);
        }
        holder.mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("webUrl3", UrlData.YINYUEQIKAN_1);
                intent.setClass(context, DetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class QikanHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView firitem;
        TextView firstKuang, name, fav, comm;
        LinearLayout mLayout;
        FrameLayout mFrameLayout;

        public QikanHolder(View itemView) {
            super(itemView);
            firitem = (SimpleDraweeView) itemView.findViewById(R.id.fresco_firitem);
            firstKuang = (TextView) itemView.findViewById(R.id.tv_firstitem_kuang);
            name = (TextView) itemView.findViewById(R.id.tv_seconditem_name);
            fav = (TextView) itemView.findViewById(R.id.tv_item_favnum);
            comm = (TextView) itemView.findViewById(R.id.tv_item_commnum);
            mLayout = (LinearLayout) itemView.findViewById(R.id.layout_item_textcontainer);
            mFrameLayout = (FrameLayout) itemView.findViewById(R.id.layout_frame);
        }
    }
}
