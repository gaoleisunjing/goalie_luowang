package com.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.app.bean.VideoEntity;
import com.app.bean.VideoListEntity;
import com.app.luooapp.R;
import com.app.luooapp.VideoPlayActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by hao on 2016-09-29.
 */
public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListHolder> {
    private Context context;
    private List<VideoListEntity.DataBean.VideosBean> videos;

    public VideoListAdapter(Context context, List<VideoListEntity.DataBean.VideosBean> videos) {
        this.context = context;
        this.videos = videos;
    }

    @Override
    public VideoListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_disover_third, null);
        return new VideoListHolder(inflate);
    }

    @Override
    public void onBindViewHolder(VideoListHolder holder, int position) {
        final VideoListEntity.DataBean.VideosBean videosBean = videos.get(position);
        holder.mDraweeView6.setImageURI(videosBean.getCover());
        holder.name.setText(videosBean.getTitle());
        holder.content.setText(videosBean.getArtist());
        holder.bottom.setText("#" + videosBean.getType_name() + "  " + videosBean.getDuration());
        final String src_480P = videosBean.getSrc().getSrc_480P();
        holder.mFrameLayout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到视屏播放界面
                Intent intent = new Intent();
                intent.putExtra("name", videosBean.getTitle());
                intent.putExtra("artist", videosBean.getArtist());
                intent.putExtra("time", videosBean.getDuration());
                intent.putExtra("content", videosBean.getContent());
                intent.putExtra("type", videosBean.getType_name());
                intent.putExtra("playurl", src_480P);
                intent.putExtra("imageurl", videosBean.getCover());
                intent.setClass(context, VideoPlayActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class VideoListHolder extends RecyclerView.ViewHolder {

        FrameLayout mFrameLayout6;
        SimpleDraweeView mDraweeView6;
        TextView name, content, bottom;

        public VideoListHolder(View itemView) {
            super(itemView);
            mFrameLayout6 = (FrameLayout) itemView.findViewById(R.id.layout_thirditem_play);
            mDraweeView6 = (SimpleDraweeView) itemView.findViewById(R.id.fresco_thirditem);
            name = (TextView) itemView.findViewById(R.id.tv_thirditem_name);
            content = (TextView) itemView.findViewById(R.id.tv_thirditem_content);
            bottom = (TextView) itemView.findViewById(R.id.tv_thirditem_bottom);
        }
    }
}
