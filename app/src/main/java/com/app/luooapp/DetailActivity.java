package com.app.luooapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bean.Ad1Entity;
import com.app.service.MediaPlayerService;
import com.app.utils.MyUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hao on 2016-09-26.
 */
public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private SimpleDraweeView mDraweeViewTitle, usericon1, usericon2, usericon3, usericon4, usericon5, usericon6,
            usericon7, usericon8, relative1, relative2, relative3, relative4, mDraweeView;
    private ImageView mPlayView, mBack, mFav, mShare;
    private LinearLayout mLayoutThanks, llRelative1, llRelative2, llRelative3, llRelative4, songsContainer;
    private TextView downall, relative1Txt, relative2Txt, relative3Txt, relative4Txt,
            mComment, mName, mVol, mTag, mContent, mThanks, songnum, mTextViewContent, mTextViewName, mTextViewNum;
    private boolean isFav = false;
    private List<String> mStringList = new ArrayList<>();
    private Context context;
    Intent intent;
    private static boolean isPlaying = false;//判断是否在播放
    static int curProgress = 0;
    private static int curClickPosition = -1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Ad1Entity.DataBean data = (Ad1Entity.DataBean) msg.obj;
                    String name = data.getName();
                    mName.setText(name);
                    String content = data.getContent();
                    mContent.setText(content);
                    int number = data.getNumber();
                    mVol.append(number + "");
                    int comm = data.getComm();
                    mComment.setText(comm + "评论");
                    String tags = data.getTags();
                    mTag.setText("#" + tags);
                    if (data.getEmoluments() != null) {
                        Ad1Entity.DataBean.EmolumentsBean emoluments = data.getEmoluments();
                        int count = emoluments.getCount();
                        mThanks.setText(count + "感谢");
                        if (emoluments.getRows() != null) {
                            List<Ad1Entity.DataBean.EmolumentsBean.RowsBean> rows = emoluments.getRows();
                            for (int i = 0; i < rows.size(); i++) {
                                Ad1Entity.DataBean.EmolumentsBean.RowsBean rowsBean = rows.get(i);
                                String avatar = rowsBean.getAvatar();
                                String avatar_ori = rowsBean.getAvatar_ori();
                                mStringList.add(avatar + File.separator + avatar_ori);
                            }
                            if (mStringList.size() > 0) {
                                usericon1.setImageURI(mStringList.get(0));
                                if (mStringList.size() > 1)
                                    usericon2.setImageURI(mStringList.get(1));
                                if (mStringList.size() > 2)
                                    usericon3.setImageURI(mStringList.get(2));
                                if (mStringList.size() > 3)
                                    usericon4.setImageURI(mStringList.get(3));
                                if (mStringList.size() > 4)
                                    usericon5.setImageURI(mStringList.get(4));
                                if (mStringList.size() > 5)
                                    usericon6.setImageURI(mStringList.get(5));
                                if (mStringList.size() > 6)
                                    usericon7.setImageURI(mStringList.get(6));
                                if (mStringList.size() > 7)
                                    usericon8.setImageURI(mStringList.get(7));
                            }
                        }
                    }

                    final List<Ad1Entity.DataBean.SongsBean> songs = data.getSongs();
                    songnum.setText(songs.size() + "首");
                    for (int i = 0; i < songs.size(); i++) {
                        Ad1Entity.DataBean.SongsBean songsBean = songs.get(i);
                        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_list, null);
                        mDraweeView = (SimpleDraweeView) view.findViewById(R.id.fresco_detail_list);
                        ImageView mImageViewMore = (ImageView) view.findViewById(R.id.iv_detail_list_more);
                        mTextViewContent = (TextView) view.findViewById(R.id.tv_detail_list_content);
                        mTextViewName = (TextView) view.findViewById(R.id.tv_detail_list_name);
                        mTextViewNum = (TextView) view.findViewById(R.id.tv_detail_list_num);
                        if (i < 9) {
                            mTextViewNum.setText("0" + (i + 1));
                        } else {
                            mTextViewNum.setText(i + 1 + "");
                        }
                        mTextViewName.setText(songsBean.getName());
                        mTextViewContent.setText(songsBean.getArtist());
                        mDraweeView.setImageURI(songsBean.getImageurl());
                        view.setClickable(true);
                        view.setTag(i);
                        //音乐列表more图标的点击事件
                        mImageViewMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        songsContainer.addView(view);
                    }
                    //整个item的点击事件,点击播放音乐
                    for (int i = 0; i < songsContainer.getChildCount(); i++) {
                        final View child = songsContainer.getChildAt(i);
                        child.setTag(i);
                        final TextView num = (TextView) child.findViewById(R.id.tv_detail_list_num);
                        final TextView names = (TextView) child.findViewById(R.id.tv_detail_list_name);
                        final TextView contents = (TextView) child.findViewById(R.id.tv_detail_list_content);
                        final int finalI = i;
                        child.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                num.setTextColor(getResources().getColor(R.color.colorTxtSelected));
                                names.setTextColor(getResources().getColor(R.color.colorTxtSelected));
                                contents.setTextColor(getResources().getColor(R.color.colorTxtSelected));
                                if (curClickPosition >= 0) {
                                    View childAt = songsContainer.getChildAt(curClickPosition);
                                    TextView num1 = (TextView) childAt.findViewById(R.id.tv_detail_list_num);
                                    TextView names1 = (TextView) childAt.findViewById(R.id.tv_detail_list_name);
                                    TextView contents1 = (TextView) childAt.findViewById(R.id.tv_detail_list_content);
                                    num1.setTextColor(Color.BLACK);
                                    names1.setTextColor(Color.BLACK);
                                    contents1.setTextColor(Color.BLACK);
                                }
                                Ad1Entity.DataBean.SongsBean songsBean = songs.get(finalI);
                                String playurl_low = songsBean.getPlayurl_low();//音乐url
                                //播放音乐
                                if (!isPlaying) {
                                    Intent intent1 = new Intent("com.kygo.progress");
                                    intent1.putExtra("musicname", playurl_low);
                                    intent1.putExtra("musictitle", songsBean.getName());
                                    intent1.putExtra("musiccontent", songsBean.getArtist());
                                    sendBroadcast(intent1);
                                } else {
                                    Intent intent = new Intent("com.kygo.progress");
                                    //发广播让音乐继续播放
                                    intent.putExtra("isContinue", true);
                                    sendBroadcast(intent);
                                    isPlaying = false;
                                }
                                Log.d("aaaaa", "启动service");
                                curClickPosition = (int) child.getTag();
                            }
                        });
                    }

                    List<Ad1Entity.DataBean.RelativesBean> relatives = data.getRelatives();
                    Ad1Entity.DataBean.RelativesBean relativesBean = relatives.get(0);
                    int number1 = relativesBean.getNumber();
                    relative1Txt.setText("vol." + number1);
                    String name1 = relativesBean.getName();
                    relative1Txt.append(" " + name1);
                    relative1.setImageURI(relativesBean.getImageurl());

                    Ad1Entity.DataBean.RelativesBean relativesBean1 = relatives.get(1);
                    int number2 = relativesBean1.getNumber();
                    relative1Txt.setText("vol." + number2);
                    String name2 = relativesBean1.getName();
                    relative2Txt.append(" " + name2);
                    relative2.setImageURI(relativesBean1.getImageurl());

                    Ad1Entity.DataBean.RelativesBean relativesBean2 = relatives.get(2);
                    int number3 = relativesBean2.getNumber();
                    relative1Txt.setText("vol." + number3);
                    String name3 = relativesBean2.getName();
                    relative3Txt.append(" " + name3);
                    relative3.setImageURI(relativesBean2.getImageurl());

                    Ad1Entity.DataBean.RelativesBean relativesBean3 = relatives.get(3);
                    int number4 = relativesBean3.getNumber();
                    relative1Txt.setText("vol." + number4);
                    String name4 = relativesBean3.getName();
                    relative4Txt.append(" " + name4);
                    relative4.setImageURI(relativesBean3.getImageurl());
                    String imageurl = data.getImageurl();
                    mDraweeViewTitle.setImageURI(imageurl);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        context = this;
        initView();
        intent = new Intent(this, MediaPlayerService.class);
        Intent intent = getIntent();
        if (intent.getStringExtra("webUrl3") != null) {
            String webUrl3 = intent.getStringExtra("webUrl3");
            downLoadJson3(webUrl3);//下载json数据
        }
    }

    private void downLoadJson3(final String webUrl3) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonStr = MyUtils.getJsonStr(webUrl3);
                Gson gson = new Gson();
                Ad1Entity ad1Entity = gson.fromJson(jsonStr, Ad1Entity.class);
                Ad1Entity.DataBean data = ad1Entity.getData();
                Message msg = new Message();
                msg.what = 0;
                msg.obj = data;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    private void initView() {
        //大图片
        mDraweeViewTitle = (SimpleDraweeView) findViewById(R.id.fresco);
        //感谢的头像
        usericon1 = (SimpleDraweeView) findViewById(R.id.fresco_detail_usericon1);
        usericon2 = (SimpleDraweeView) findViewById(R.id.fresco_detail_usericon2);
        usericon3 = (SimpleDraweeView) findViewById(R.id.fresco_detail_usericon3);
        usericon4 = (SimpleDraweeView) findViewById(R.id.fresco_detail_usericon4);
        usericon5 = (SimpleDraweeView) findViewById(R.id.fresco_detail_usericon5);
        usericon6 = (SimpleDraweeView) findViewById(R.id.fresco_detail_usericon6);
        usericon7 = (SimpleDraweeView) findViewById(R.id.fresco_detail_usericon7);
        usericon8 = (SimpleDraweeView) findViewById(R.id.fresco_detail_usericon8);
        //相似期刊的图片
        relative1 = (SimpleDraweeView) findViewById(R.id.fresco_detail_relative1);
        relative2 = (SimpleDraweeView) findViewById(R.id.fresco_detail_relative2);
        relative3 = (SimpleDraweeView) findViewById(R.id.fresco_detail_relative3);
        relative4 = (SimpleDraweeView) findViewById(R.id.fresco_detail_relative4);
        //播放按钮
        mPlayView = (ImageView) findViewById(R.id.iv_play_btn);
        //返回按钮
        mBack = (ImageView) findViewById(R.id.iv_detail_top_back);
        mBack.setOnClickListener(this);
        //收藏按钮
        mFav = (ImageView) findViewById(R.id.iv_detail_top_fav);
        mFav.setOnClickListener(this);
        //分享按钮
        mShare = (ImageView) findViewById(R.id.iv_detail_top_share);
        mShare.setOnClickListener(this);
        //感谢的包裹布局
        mLayoutThanks = (LinearLayout) findViewById(R.id.layout_detail_thanks);
        //相似期刊的包裹布局
        llRelative1 = (LinearLayout) findViewById(R.id.layout_detail_relative1);
        llRelative2 = (LinearLayout) findViewById(R.id.layout_detail_relative2);
        llRelative3 = (LinearLayout) findViewById(R.id.layout_detail_relative3);
        llRelative4 = (LinearLayout) findViewById(R.id.layout_detail_relative4);
        //全部缓存
        downall = (TextView) findViewById(R.id.tv_detail_downall);
        //相似期刊的文字
        relative1Txt = (TextView) findViewById(R.id.tv_detail_relative1);
        relative2Txt = (TextView) findViewById(R.id.tv_detail_relative2);
        relative3Txt = (TextView) findViewById(R.id.tv_detail_relative3);
        relative4Txt = (TextView) findViewById(R.id.tv_detail_relative4);
        //音乐列表
        songsContainer = (LinearLayout) findViewById(R.id.lv_detail);
        //音乐名
        mName = (TextView) findViewById(R.id.tv_detail_name);
        //评论
        mComment = (TextView) findViewById(R.id.tv_detail_top_comment);
        //vol.
        mVol = (TextView) findViewById(R.id.tv_detail_number);
        //tag
        mTag = (TextView) findViewById(R.id.tv_detail_tag);
        //简介
        mContent = (TextView) findViewById(R.id.tv_detail_content);
        //感谢数量
        mThanks = (TextView) findViewById(R.id.tv_detail_thanks);
        //歌曲数量
        songnum = (TextView) findViewById(R.id.tv_detail_songnum);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_detail_top_back:
                finish();
                break;
            case R.id.iv_detail_top_fav:
                if (!isFav){
                    mFav.setImageResource(R.drawable.bar_fav_on);
                    Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
                    isFav = true;
                }else{
                    mFav.setImageResource(R.drawable.bar_fav_off);
                    Toast.makeText(this,"已取消收藏",Toast.LENGTH_SHORT).show();
                    isFav = false;
                }
                break;
            case R.id.iv_detail_top_share:

                break;

        }

    }
}
