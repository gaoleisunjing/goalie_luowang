package com.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bean.RecommendBean;
import com.app.luooapp.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by hao on 2016-09-29.
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendHolder> {
    private Context context;
    private List<RecommendBean.DataBean.ItemsBean> items1;
    private boolean isFav;

    public RecommendAdapter(Context context, List<RecommendBean.DataBean.ItemsBean> items1) {
        this.context = context;
        this.items1 = items1;
    }

    @Override
    public RecommendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_recommed, null);
        return new RecommendHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final RecommendHolder holder, int position) {
        final RecommendBean.DataBean.ItemsBean itemsBean = items1.get(position);
        holder.mDraweeView.setImageURI(itemsBean.getCover().getNormal());
        int favs = itemsBean.getFavs();
        holder.mImageViewFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mImageViewFav.setImageResource(R.drawable.bar_fav_on);
                Toast.makeText(context, "已收藏", Toast.LENGTH_SHORT).show();
                isFav = true;
            }
        });
        if (isFav == true) {
            holder.fav.setText((favs + 1) + "");
        } else {
            holder.fav.setText(favs + "");
        }
        holder.comm.setText(itemsBean.getComments() + "");
        holder.content.setText(itemsBean.getRemark());
        holder.kuang.setText(itemsBean.getTags().get(0).getTag_name());
        holder.name.setText(itemsBean.getTitle());
        holder.userName.setText(itemsBean.getPerformer_name());
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.play.setImageResource(R.drawable.recommend_pause_n);
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享

            }
        });

    }

    @Override
    public int getItemCount() {
        return items1.size();
    }

    class RecommendHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mDraweeView;
        ImageView play;
        TextView name, userName, comm, content, kuang, fav;
        ImageView share, mImageViewFav, mImageViewCommm;

        public RecommendHolder(View itemView) {
            super(itemView);
            mDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.fresco_recomm);
            play = (ImageView) itemView.findViewById(R.id.iv_recomm_play);
            name = (TextView) itemView.findViewById(R.id.tv_recomm_name);
            userName = (TextView) itemView.findViewById(R.id.tv_recomm_username);
            content = (TextView) itemView.findViewById(R.id.tv_recomm_content);
            kuang = (TextView) itemView.findViewById(R.id.tv_recomm_kuang);
            comm = (TextView) itemView.findViewById(R.id.tv_recomm_comm);
            fav = (TextView) itemView.findViewById(R.id.tv_recomm_fav);
            share = (ImageView) itemView.findViewById(R.id.iv_recomm_share);
            mImageViewCommm = (ImageView) itemView.findViewById(R.id.iv_recomm_comm);
            mImageViewFav = (ImageView) itemView.findViewById(R.id.iv_recomm_fav);
        }
    }
}
