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
import com.app.luooapp.HotDetailsActivity;
import com.app.luooapp.R;
import com.app.urldatas.UrlData;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by peiyangyang on 2016/10/3.
 */
public class ForumNewsestAdapter extends RecyclerView.Adapter {

    List<HotEntity.DataBean.ItemsBean> mBeanList;
    Context mContext;

    private static final int ONLY_TEXT = 12;
    private static final int ALLABOVE = 110;
    private static final int TEXT_PIC = 13;
    private static final int TEXT_MUSIC = 121;
    private static boolean isPlaying = false;//判断是否在播放

    public ForumNewsestAdapter(List<HotEntity.DataBean.ItemsBean> beanList, Context context) {
        mBeanList = beanList;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ONLY_TEXT) {
            return new OnlyTextViewHolder(LayoutInflater.from(mContext).
                    inflate(R.layout.forum_newsest_text, parent, false));
        } else if (viewType == TEXT_PIC) {
            return new TPViewHolder(LayoutInflater.from(mContext).
                    inflate(R.layout.forum_newsest_pictext, parent, false));
        } else if (viewType == TEXT_MUSIC) {
            return new TMViewHolder(LayoutInflater.from(mContext).
                    inflate(R.layout.forum_newsest_tm, parent, false));
        }
        return new HotViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.forum_hot_item, parent, false));
    }

    OnNewItemClickListener mOnNewItemClickListener;

    public interface OnNewItemClickListener {
        void onNewItemClick(View view, int position, long id);
    }

    public void setOnNewItemClickListener(OnNewItemClickListener onNewItemClickListener) {
        mOnNewItemClickListener = onNewItemClickListener;
    }

    public void skipDetails(int position) {
        Intent intent = new Intent(mContext, HotDetailsActivity.class);
        intent.putExtra("From_Hot", UrlData.FORUM_HOT_ITEM);
        mContext.startActivity(intent);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        List<HotEntity.DataBean.ItemsBean.PostAudioBean> mAudioBeen = null;
        if (mBeanList.get(position).getPost_audio() != null) {
            mAudioBeen = mBeanList.get(position).getPost_audio();
        }
        if (holder instanceof OnlyTextViewHolder) {
            ((OnlyTextViewHolder) holder).mUserIcon.setImageURI(mBeanList.get(position).getUser_avatar());
            ((OnlyTextViewHolder) holder).mUserName.setText(mBeanList.get(position).getUser_name());
            ((OnlyTextViewHolder) holder).mContent.setText(mBeanList.get(position).getContent());
            ((OnlyTextViewHolder) holder).mVote.setText("" + mBeanList.get(position).getVote_count());
            ((OnlyTextViewHolder) holder).mCommet.setText("" + mBeanList.get(position).getComment_count());
        } else if (holder instanceof TPViewHolder) {
            ((TPViewHolder) holder).mUserIcon.setImageURI(mBeanList.get(position).getUser_avatar());
            ((TPViewHolder) holder).mContentPic.setImageURI(mBeanList.get(position).getPost_img().get(0));
            ((TPViewHolder) holder).mUserName.setText(mBeanList.get(position).getUser_name());
            ((TPViewHolder) holder).mContent.setText(mBeanList.get(position).getContent());
            ((TPViewHolder) holder).mVote.setText("" + mBeanList.get(position).getVote_count());
            ((TPViewHolder) holder).mCommet.setText("" + mBeanList.get(position).getComment_count());
        } else if (holder instanceof TMViewHolder) {
            ((TMViewHolder) holder).mUserIcon.setImageURI(mBeanList.get(position).getUser_avatar());
            ((TMViewHolder) holder).tvUserName.setText(mBeanList.get(position).getUser_name());
            ((TMViewHolder) holder).tvContent.setText(mBeanList.get(position).getContent());
            ((TMViewHolder) holder).tvVote.setText("" + mBeanList.get(position).getVote_count());
            ((TMViewHolder) holder).tvCommet.setText("" + mBeanList.get(position).getComment_count());
            ((TMViewHolder) holder).ivCover.setImageURI(mAudioBeen.get(0).getCover());
            ((TMViewHolder) holder).tvMusicName.setText(mAudioBeen.get(0).getSong_name());
            ((TMViewHolder) holder).tvMusicArtist.setText(mAudioBeen.get(0).getArtist());
            ((TMViewHolder) holder).tvMusicCount.setText(mAudioBeen.size() + "首");
            final List<HotEntity.DataBean.ItemsBean.PostAudioBean> finalMAudioBeen = mAudioBeen;
            ((TMViewHolder) holder).ivMusicPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalMAudioBeen != null) {
                        String musicString = finalMAudioBeen.get(0).getUrl();
                        String musicUrl = null;
                        if (musicString.startsWith("http")) {
                            musicUrl = musicString;

                        } else {
                            musicUrl = "http://luoo-mp3.kssws.ks-cdn.com" + musicString;
                        }
                        Log.d("musicSong", "" + musicUrl);
                        if (!isPlaying) {
                            ((TMViewHolder) holder).ivMusicPlay.setImageResource(R.drawable.recommend_pause_n);
                            Intent intent1 = new Intent("com.kygo.progress");
                            intent1.putExtra("musicname", musicUrl);
                            intent1.putExtra("musictitle", finalMAudioBeen.get(0).getSong_name());
                            intent1.putExtra("musiccontent", finalMAudioBeen.get(0).getArtist());
                            mContext.sendBroadcast(intent1);
                            isPlaying = true;
                        } else {
                            ((TMViewHolder) holder).ivMusicPlay.setImageResource(R.drawable.recommend_play_n);
                            Intent intent = new Intent("com.kygo.progress");
                            //发广播让音乐暂停播放
                            intent.putExtra("isPause", true);
                            mContext.sendBroadcast(intent);
                            isPlaying = false;
                        }
                    }
                }
            });
        } else if (holder instanceof HotViewHolder) {
            ((HotViewHolder) holder).tvUserName.setText(mBeanList.get(position).getUser_name());
            ((HotViewHolder) holder).tvContent.setText(mBeanList.get(position).getContent());
            ((HotViewHolder) holder).tvMusicName.setText(mAudioBeen.get(0).getSong_name());
            ((HotViewHolder) holder).tvMusicArtist.setText(mAudioBeen.get(0).getArtist());
            ((HotViewHolder) holder).tvMusicCount.setText(mAudioBeen.size() + "首");
            ((HotViewHolder) holder).tvVote.setText("" + mBeanList.get(position).getVote_count());
            ((HotViewHolder) holder).tvCommet.setText("" + mBeanList.get(position).getComment_count());
            ((HotViewHolder) holder).ivUserIcon.setImageURI(mBeanList.get(position).getUser_avatar());
            ((HotViewHolder) holder).ivPost.setImageURI(mBeanList.get(position).getPost_img().get(0));
            ((HotViewHolder) holder).ivCover.setImageURI(mAudioBeen.get(0).getCover());
            final List<HotEntity.DataBean.ItemsBean.PostAudioBean> finalMAudioBeen1 = mAudioBeen;
            ((HotViewHolder) holder).ivMusicPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (finalMAudioBeen1 != null) {
                        String musicString = finalMAudioBeen1.get(0).getUrl();
                        String musicUrl = null;
                        if (musicString.startsWith("http")) {
                            musicUrl = musicString;

                        } else {
                            musicUrl = "http://luoo-mp3.kssws.ks-cdn.com" + musicString;
                        }
                        Log.d("musicSong", "" + musicUrl);
                        if (!isPlaying) {
                            ((HotViewHolder) holder).ivMusicPlay.setImageResource(R.drawable.recommend_pause_n);
                            Intent intent1 = new Intent("com.kygo.progress");
                            intent1.putExtra("musicname", musicUrl);
                            intent1.putExtra("musictitle", finalMAudioBeen1.get(0).getSong_name());
                            intent1.putExtra("musiccontent", finalMAudioBeen1.get(0).getArtist());
                            mContext.sendBroadcast(intent1);
                            isPlaying = true;
                        } else {
                            ((HotViewHolder) holder).ivMusicPlay.setImageResource(R.drawable.recommend_play_n);
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnNewItemClickListener != null) {
                    mOnNewItemClickListener.onNewItemClick(holder.itemView, position, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (mBeanList.get(position).getPost_audio() == null) {
            if (mBeanList.get(position).getPost_img() == null) {
                return ONLY_TEXT;
            } else {
                return TEXT_PIC;
            }
        } else if (mBeanList.get(position).getPost_img() == null) {
            return TEXT_MUSIC;
        } else {
            return ALLABOVE;
        }
    }

    class OnlyTextViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView mUserIcon;
        private TextView mUserName, mContent, mVote, mCommet;

        public OnlyTextViewHolder(View itemView) {
            super(itemView);
            mUserIcon = (SimpleDraweeView) itemView.findViewById(R.id.forum_newsest_text_userIcon);
            mUserName = (TextView) itemView.findViewById(R.id.forum_newsest_text_userName);
            mContent = (TextView) itemView.findViewById(R.id.forum_newsest_onlytext);
            mVote = (TextView) itemView.findViewById(R.id.forum_item_tbtvote);
            mCommet = (TextView) itemView.findViewById(R.id.forum_item_tbtcommet);

        }
    }

    class TPViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView mUserIcon, mContentPic;
        private TextView mUserName, mContent, mVote, mCommet;

        public TPViewHolder(View itemView) {
            super(itemView);
            mUserIcon = (SimpleDraweeView) itemView.findViewById(R.id.forum_newsest_pictext_userIcon);
            mContentPic = (SimpleDraweeView) itemView.findViewById(R.id.forum_item_pict_iv);
            mUserName = (TextView) itemView.findViewById(R.id.forum_newsest_pictext_userName);
            mContent = (TextView) itemView.findViewById(R.id.forum_newsest_text);
            mVote = (TextView) itemView.findViewById(R.id.forum_item_picbtvote);
            mCommet = (TextView) itemView.findViewById(R.id.forum_item_picbtcommet);
        }
    }

    class TMViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView mUserIcon, ivCover;
        private TextView tvUserName, tvContent, tvMusicName, tvMusicArtist, tvMusicCount, tvVote, tvCommet;
        private ImageView ivMusicPlay;

        public TMViewHolder(View itemView) {
            super(itemView);
            mUserIcon = (SimpleDraweeView) itemView.findViewById(R.id.forum_newsest_tm_userIcon);
            tvContent = (TextView) itemView.findViewById(R.id.forum_newsest_tm);
            ivMusicPlay = (ImageView) itemView.findViewById(R.id.forum_item_tmusic_icon);
            ivCover = (SimpleDraweeView) itemView.findViewById(R.id.forum_item_tmusic_cover);
            tvUserName = (TextView) itemView.findViewById(R.id.forum_newsest_tm_userName);
            tvMusicName = (TextView) itemView.findViewById(R.id.forum_item_tmusic_name);
            tvMusicArtist = (TextView) itemView.findViewById(R.id.forum_item_tmusic_auther);
            tvMusicCount = (TextView) itemView.findViewById(R.id.forum_item_tmusic_count);
            tvVote = (TextView) itemView.findViewById(R.id.forum_item_tmbtvote);
            tvCommet = (TextView) itemView.findViewById(R.id.forum_item_tmbtcommet);

        }
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView ivUserIcon, ivPost, ivCover;
        private TextView tvUserName, tvContent, tvMusicName, tvMusicArtist, tvMusicCount, tvVote, tvCommet;
        private ImageView ivMusicPlay;
        public HotViewHolder(View itemView) {
            super(itemView);
            ivMusicPlay = (ImageView) itemView.findViewById(R.id.forum_item_music_icon);
            ivUserIcon = (SimpleDraweeView) itemView.findViewById(R.id.forum_item_userIcon);
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
}
