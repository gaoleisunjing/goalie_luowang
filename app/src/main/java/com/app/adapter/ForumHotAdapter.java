package com.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bean.HotEntity;
import com.app.luooapp.ArtistActivity;
import com.app.luooapp.HotDetailsActivity;
import com.app.luooapp.R;
import com.app.urldatas.UrlData;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by peiyangyang on 2016/9/28.
 */
public class ForumHotAdapter extends RecyclerView.Adapter<ForumHotAdapter.HotViewHolder> implements View.OnClickListener {

    List<HotEntity.DataBean.ItemsBean> mList;
    Context mContext;
    private static boolean isPlaying = false;//判断是否在播放
    private boolean isFav = false;

    public ForumHotAdapter(List<HotEntity.DataBean.ItemsBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public ForumHotAdapter.HotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HotViewHolder holder = new HotViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.forum_hot_item, parent, false));

        return holder;
    }

    OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClickListener(View view, int position, long id);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public void onBindViewHolder(final ForumHotAdapter.HotViewHolder holder, final int position) {
        final List<HotEntity.DataBean.ItemsBean.PostAudioBean> mAudioBeen
                = mList.get(position).getPost_audio();
        holder.tvUserName.setText(mList.get(position).getUser_name());
        holder.tvContent.setText(mList.get(position).getContent());
        if (mAudioBeen != null) {
            holder.tvMusicName.setText(mAudioBeen.get(0).getSong_name());
            holder.tvMusicArtist.setText(mAudioBeen.get(0).getArtist());
            holder.tvMusicCount.setText(mAudioBeen.size() + "首");
            holder.ivCover.setImageURI(mAudioBeen.get(0).getCover());
        }

        holder.tvVote.setText("" + mList.get(position).getVote_count());
        holder.tvCommet.setText("" + mList.get(position).getComment_count());
        holder.ivUserIcon.setImageURI(mList.get(position).getUser_avatar());

        if (mList.get(position).getPost_img() != null) {
            holder.ivPost.setImageURI(mList.get(position).getPost_img().get(0));
        }


        holder.ivUserIcon.setOnClickListener(this);

        //自定义holder的itemView的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClickListener(holder.itemView, position, position);
                }
            }
        });
        holder.ivMusicPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAudioBeen != null) {
                    String musicString = mAudioBeen.get(0).getUrl();
                    String musicUrl = null;
                    if (musicString.startsWith("http")) {
                        musicUrl = musicString;

                    } else {
                        musicUrl = "http://luoo-mp3.kssws.ks-cdn.com" + musicString;
                    }
                    Log.d("musicSong", "" + musicUrl);
                    if (!isPlaying) {
                        holder.ivMusicPlay.setImageResource(R.drawable.recommend_pause_n);
                        Intent intent1 = new Intent("com.kygo.progress");
                        intent1.putExtra("musicname", musicUrl);
                        intent1.putExtra("musictitle", mAudioBeen.get(0).getSong_name());
                        intent1.putExtra("musiccontent", mAudioBeen.get(0).getArtist());
                        mContext.sendBroadcast(intent1);
                        isPlaying = true;
                    } else {
                        holder.ivMusicPlay.setImageResource(R.drawable.recommend_play_n);
                        Intent intent = new Intent("com.kygo.progress");
                        //发广播让音乐暂停播放
                        intent.putExtra("isPause", true);
                        mContext.sendBroadcast(intent);
                        isPlaying = false;
                    }
                }
            }
        });


    }

    public void skipDetails(int position) {
        Intent intent = new Intent(mContext, HotDetailsActivity.class);
        intent.putExtra("From_Hot", UrlData.FORUM_HOT_ITEM);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView ivUserIcon, ivPost, ivCover;
        private ImageView ivMusicPlay;
        private TextView tvUserName, tvContent, tvMusicName, tvMusicArtist, tvMusicCount, tvVote, tvCommet;

        public HotViewHolder(View itemView) {
            super(itemView);
            ivUserIcon = (SimpleDraweeView) itemView.findViewById(R.id.forum_item_userIcon);
            ivMusicPlay = (ImageView) itemView.findViewById(R.id.forum_item_music_icon);
            ivPost = (SimpleDraweeView) itemView.findViewById(R.id.forum_item_art_iv);
            ivCover = (SimpleDraweeView) itemView.findViewById(R.id.forum_item_music_cover);
            tvUserName = (TextView) itemView.findViewById(R.id.forum_item_userName);
            tvContent = (TextView) itemView.findViewById(R.id.forum_item_art_content);
            tvMusicName = (TextView) itemView.findViewById(R.id.forum_item_music_name);
            tvMusicArtist = (TextView) itemView.findViewById(R.id.forum_item_music_auther);
            tvMusicCount = (TextView) itemView.findViewById(R.id.forum_item_music_count);
            tvVote = (TextView) itemView.findViewById(R.id.forum_item_btvote);
            tvCommet = (TextView) itemView.findViewById(R.id.forum_item_btcommet);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.forum_item_userIcon:
                intent = new Intent(mContext, ArtistActivity.class);
                mContext.startActivity(intent);
                break;
        }
    }

}
