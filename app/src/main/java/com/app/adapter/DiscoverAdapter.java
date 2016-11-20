package com.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.bean.DailyEntity;
import com.app.bean.DiscoverEntity;
import com.app.bean.QikanEntity;
import com.app.bean.VideoEntity;
import com.app.bean.WenZhangEntity;
import com.app.luooapp.DetailActivity;
import com.app.luooapp.QikanActivity;
import com.app.luooapp.R;
import com.app.luooapp.VideoPlayActivity;
import com.app.service.MediaPlayerService;
import com.app.urldatas.UrlData;
import com.app.utils.MyUtils;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hao on 2016-09-26.
 */
public class DiscoverAdapter extends RecyclerView.Adapter implements OnItemClickListener {
    private Context context;
    private List<String> mList;
    private DiscoverEntity mDiscoverEntity;
    private QikanEntity mQikanEntity;
    private VideoEntity mVideoEntity;
    private WenZhangEntity mWenZhangEntity;
    private static final int AD1 = 0;
    private static final int AD2 = 1;
    private static final int AD3 = 2;
    private DailyEntity dailyEntity;
    private boolean left;
    private boolean right;
    private boolean isPlayingleft;//当前是否有音乐在播放
    private boolean isPlayingright;//当前是否有音乐在播放
    private Intent intent;
    private List<DailyEntity.DataBean.ItemsBean> items;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://normal的item
                    dailyEntity = (DailyEntity) msg.obj;
                    DailyEntity.DataBean data = dailyEntity.getData();
                    items = data.getItems();

                    break;
                case 1://视频信息
                    mVideoEntity = (VideoEntity) msg.obj;

                    break;
                case 2://文章信息
                    mWenZhangEntity = (WenZhangEntity) msg.obj;
                    break;
            }
        }
    };

    public DiscoverAdapter(Context context, List<String> imageViews, DiscoverEntity discoverEntity) {
        this.context = context;
        this.mList = imageViews;
        notifyDataSetChanged();
        this.mDiscoverEntity = discoverEntity;
        downLoadDailyjson(UrlData.DAILY_TODAY);
        downLoadVideojson(UrlData.VIDEO);
        downLoadWenzhangjson(UrlData.WENZHANG);
        this.intent = new Intent(context, MediaPlayerService.class);
    }

    private void downLoadWenzhangjson(final String u) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonStr = MyUtils.getJsonStr(u);
                Gson gson = new Gson();
                WenZhangEntity wenZhangEntity = gson.fromJson(jsonStr, WenZhangEntity.class);
                Message msg = new Message();
                msg.what = 2;
                msg.obj = wenZhangEntity;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    private void downLoadVideojson(final String u) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonStr = MyUtils.getJsonStr(u);
                Gson gson = new Gson();
                VideoEntity videoEntity = gson.fromJson(jsonStr, VideoEntity.class);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = videoEntity;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    public void downLoadDailyjson(final String u) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonStr = MyUtils.getJsonStr(u);
                Gson gson = new Gson();
                DailyEntity dailyEntity = gson.fromJson(jsonStr, DailyEntity.class);
                Message msg = new Message();
                msg.what = 0;
                msg.obj = dailyEntity;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 0) {//头部轮播
            view = LayoutInflater.from(context).inflate(R.layout.item_ad, null);
            return new HeadHolder(view);
        } else if (viewType == 1) {//音乐期刊全部
            view = LayoutInflater.from(context).inflate(R.layout.item_discover_divider, null);
            return new Firstholder(view);
        } else if (viewType == 2) {//音乐期刊item
            view = LayoutInflater.from(context).inflate(R.layout.item_discover_first, null);
            return new SecondHolder(view);
        } else if (viewType == 3) {//原始推荐全部
            view = LayoutInflater.from(context).inflate(R.layout.item_discover_divider, null);
            return new ThirdHolder(view);
        } else if (viewType == 4) {//原始推荐2个item
            view = LayoutInflater.from(context).inflate(R.layout.item_discover_second, null);
            return new FourthHolder(view);
        } else if (viewType == 5) {//在线视频全部
            view = LayoutInflater.from(context).inflate(R.layout.item_discover_divider, null);
            return new FifthHolder(view);
        } else if (viewType == 6) {//在线视频item
            view = LayoutInflater.from(context).inflate(R.layout.item_disover_third, null);
            return new SixHolder(view);
        } else if (viewType == 7) {//专栏文章全部
            view = LayoutInflater.from(context).inflate(R.layout.item_discover_divider, null);
            return new SevenHolder(view);
        } else if (viewType == 8) {//专栏文章item，后面2个也是
            view = LayoutInflater.from(context).inflate(R.layout.item_discover_fourth, null);
            return new EightHolder(view);
        } else if (viewType == 9) {
            view = LayoutInflater.from(context).inflate(R.layout.item_discover_fourth, null);
            return new NineHolder(view);
        } else if (viewType == 10) {
            view = LayoutInflater.from(context).inflate(R.layout.item_discover_fourth, null);
            return new TenHolder(view);
        } else if (viewType == 11) {//滚动广告item
            view = LayoutInflater.from(context).inflate(R.layout.item_ad, null);
            return new ElevenHolder(view);
        } else if (viewType == 12) {//每日精选全部
            view = LayoutInflater.from(context).inflate(R.layout.item_discover_divider, null);
            return new TwelveHolder(view);
        }
        //普通item
        view = LayoutInflater.from(context).inflate(R.layout.item_discover_fifth, null);
        return new NormalHoler(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeadHolder) {
            HeadHolder headHolder = (HeadHolder) holder;
            Log.d("ddddd", mList.size() + "");
            headHolder.mConvenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                @Override
                public NetworkImageHolderView createHolder() {
                    return new NetworkImageHolderView();
                }
            }, mList)    //设置需要切换的View
                    .setPointViewVisible(true)    //设置指示器是否可见
                    .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})   //设置指示器圆点
                    .startTurning(3000)     //设置自动切换（同时设置了切换时间间隔）
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL) //设置指示器位置（左、中、右）
                    .setOnItemClickListener(this); //设置点击监听事件
        } else if (holder instanceof Firstholder) {//全部期刊
            Firstholder firstholder = (Firstholder) holder;
            firstholder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到全部期刊页面
                    Intent intent = new Intent();
                    intent.putExtra("qikan", UrlData.YINYUEQIKAN);
                    intent.setClass(context, QikanActivity.class);
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof SecondHolder) {

            SecondHolder secondHolder = (SecondHolder) holder;
            secondHolder.firitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("webUrl3", UrlData.YINYUEQIKAN_1);
                    intent.setClass(context, DetailActivity.class);
                    context.startActivity(intent);
                }
            });
            DiscoverEntity.DataBean data = mDiscoverEntity.getData();
            List<DiscoverEntity.DataBean.VolsBean> vols = data.getVols();
            DiscoverEntity.DataBean.VolsBean volsBean = vols.get(0);
            secondHolder.firitem.setImageURI(volsBean.getImageurl());
            secondHolder.fav.setText(volsBean.getFav() + "");
            secondHolder.comm.setText(volsBean.getComm() + "");
            secondHolder.firstKuang.setText("vol.\n" + volsBean.getNumber());
            secondHolder.name.setText(volsBean.getName());
            TextView textView = new TextView(context);
            textView.setText(volsBean.getTags());
            textView.setTextSize(10);
            textView.setBackground(context.getResources().getDrawable(R.drawable.text_bac));
            if (secondHolder.mLayout.getChildCount() == 0) {
                secondHolder.mLayout.addView(textView);
            }

        } else if (holder instanceof ThirdHolder) {
            ThirdHolder thirdHolder = (ThirdHolder) holder;
            thirdHolder.mTextView3.setText("原创推荐");
            thirdHolder.mRelativeLayout3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到原创推荐界面
                    Intent intent = new Intent();
                    intent.putExtra("recomm", UrlData.RECOMMOEND);
                    intent.setClass(context, QikanActivity.class);
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof FourthHolder) {
            final FourthHolder fourthHolder = (FourthHolder) holder;
            DiscoverEntity.DataBean data = mDiscoverEntity.getData();
            List<DiscoverEntity.DataBean.MusiciansBean> musicians = data.getMusicians();
            final DiscoverEntity.DataBean.MusiciansBean musiciansBean = musicians.get(0);
            fourthHolder.nameLeft.setText(musiciansBean.getName());
            fourthHolder.contenLeft.setText(musiciansBean.getArtist());
            fourthHolder.left.setImageURI(musiciansBean.getImageurl());
            final String playurl_low = musiciansBean.getPlayurl_low();//左边播放音乐的url
            fourthHolder.mImageViewleft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent("com.kygo.progress");
                    intent1.putExtra("musicname", playurl_low);
                    intent1.putExtra("musictitle", musiciansBean.getName());
                    intent1.putExtra("musiccontent", musiciansBean.getArtist());
                    context.sendBroadcast(intent1);
                    if (isPlayingleft) {
                        fourthHolder.mImageViewleft.setImageResource(R.drawable.recommend_play_n);
                        Intent intent2 = new Intent("com.kygo.progress");
                        intent2.putExtra("isPause", true);
                        context.sendBroadcast(intent2);
                    } else {
                        fourthHolder.mImageViewleft.setImageResource(R.drawable.recommend_pause_n);
                        Intent intent3 = new Intent("com.kygo.progress");
                        intent3.putExtra("isContinue", true);
                        context.sendBroadcast(intent3);
                    }
                    isPlayingleft = !isPlayingleft;
                }
            });
            final DiscoverEntity.DataBean.MusiciansBean musiciansBean1 = musicians.get(1);
            fourthHolder.nameRight.setText(musiciansBean1.getName());
            fourthHolder.contentRight.setText(musiciansBean1.getArtist());
            fourthHolder.right.setImageURI(musiciansBean1.getImageurl());
            final String playurl_low1 = musiciansBean1.getPlayurl_low();//右边播放音乐的url
            fourthHolder.mImageViewright.setOnClickListener(new View.OnClickListener() {//点击播放音乐
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent("com.kygo.progress");
                    intent1.putExtra("musicname", playurl_low1);
                    intent1.putExtra("musictitle", musiciansBean1.getName());
                    intent1.putExtra("musiccontent", musiciansBean1.getArtist());
                    context.sendBroadcast(intent1);
                    if (isPlayingright) {
                        fourthHolder.mImageViewright.setImageResource(R.drawable.recommend_play_n);
                        Intent intent2 = new Intent("com.kygo.progress");
                        intent2.putExtra("isPause", true);
                        context.sendBroadcast(intent2);
                    } else {
                        fourthHolder.mImageViewright.setImageResource(R.drawable.recommend_pause_n);
                        Intent intent3 = new Intent("com.kygo.progress");
                        intent3.putExtra("isContinue", true);
                        context.sendBroadcast(intent3);
                    }
                    isPlayingright = !isPlayingright;
                }
            });
        } else if (holder instanceof FifthHolder) {
            FifthHolder fifthHolder = (FifthHolder) holder;
            fifthHolder.mTextView5.setText("在线视频");
            fifthHolder.mLayoutFifth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到视屏列表界面
                    Intent intent = new Intent();
                    intent.putExtra("videoUrls", UrlData.VIDEOLIST);
                    intent.setClass(context, QikanActivity.class);
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof SixHolder) {
            SixHolder sixHolder = (SixHolder) holder;
            if (mVideoEntity != null) {
                VideoEntity.DataBean data = mVideoEntity.getData();
                List<VideoEntity.DataBean.VideosBean> videos = data.getVideos();
                final VideoEntity.DataBean.VideosBean videosBean = videos.get(0);
                sixHolder.mDraweeView6.setImageURI(videosBean.getCover());
                sixHolder.name.setText(videosBean.getTitle());
                sixHolder.content.setText(videosBean.getArtist());
                sixHolder.bottom.setText("#" + videosBean.getType_name() + "  " + videosBean.getDuration());
                final String src_480P = videosBean.getSrc().getSrc_480P();
                sixHolder.mFrameLayout6.setOnClickListener(new View.OnClickListener() {
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
        } else if (holder instanceof SevenHolder) {
            SevenHolder sevenHolder = (SevenHolder) holder;
            sevenHolder.mLayout6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到文章列表界面
                    Intent intent = new Intent();
                    intent.putExtra("wenZhang", UrlData.WENZHANG);

                }
            });
            sevenHolder.mTextView6.setText("专栏文章");
        } else if (holder instanceof EightHolder) {
            EightHolder eightHolder = (EightHolder) holder;
            if (mWenZhangEntity != null) {
                WenZhangEntity.DataBean data = mWenZhangEntity.getData();
                List<WenZhangEntity.DataBean.ItemsBean> items = data.getItems();
                WenZhangEntity.DataBean.ItemsBean itemsBean = items.get(0);
                eightHolder.mDraweeView8.setImageURI(itemsBean.getImageurl());
                eightHolder.mLayout8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到文章详情页面

                    }
                });
                eightHolder.title8.setText(itemsBean.getTitle());
                eightHolder.content8.setText(itemsBean.getSubscribe());
            }
        } else if (holder instanceof NineHolder) {
            NineHolder nineHolder = (NineHolder) holder;
            if (mWenZhangEntity != null) {
                WenZhangEntity.DataBean data = mWenZhangEntity.getData();
                List<WenZhangEntity.DataBean.ItemsBean> items = data.getItems();
                WenZhangEntity.DataBean.ItemsBean itemsBean = items.get(1);
                nineHolder.mDraweeView9.setImageURI(itemsBean.getImageurl());
                nineHolder.mLayout9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到文章详情页面

                    }
                });
                nineHolder.title9.setText(itemsBean.getTitle());
                nineHolder.content9.setText(itemsBean.getSubscribe());
            }
        } else if (holder instanceof TenHolder) {
            TenHolder tenHolder = (TenHolder) holder;
            if (mWenZhangEntity != null) {
                WenZhangEntity.DataBean data = mWenZhangEntity.getData();
                List<WenZhangEntity.DataBean.ItemsBean> items = data.getItems();
                WenZhangEntity.DataBean.ItemsBean itemsBean = items.get(2);
                tenHolder.mDraweeView10.setImageURI(itemsBean.getImageurl());
                tenHolder.mLayout10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到文章详情页面

                    }
                });
                tenHolder.title10.setText(itemsBean.getTitle());
                tenHolder.content10.setText(itemsBean.getSubscribe());
            }
        } else if (holder instanceof ElevenHolder) {
            ElevenHolder elevenHolder = (ElevenHolder) holder;
            List<DiscoverEntity.DataBean.Ads5Bean> ads5 = mDiscoverEntity.getData().getAds5();
            ArrayList<String> strings = new ArrayList<>();
            if (ads5 != null) {
                strings.add(ads5.get(0).getImage());
                strings.add(ads5.get(1).getImage());

            } else {
                elevenHolder.mConvenientBanner11.setVisibility(View.GONE);
            }
            try {
                elevenHolder.mConvenientBanner11.setPages(new CBViewHolderCreator<NetworkImageHolderView1>() {
                    @Override
                    public NetworkImageHolderView1 createHolder() {
                        return new NetworkImageHolderView1();
                    }
                }, strings)//设置需要切换的View
                        .startTurning(2000)     //设置自动切换（同时设置了切换时间间隔）
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {//设置点击监听事件

                            }
                        });
            } catch (Exception e) {
                return;
            }
        } else if (holder instanceof TwelveHolder) {
            TwelveHolder twelveHolder = (TwelveHolder) holder;
            twelveHolder.mLayout12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到社区界面

                }
            });
            twelveHolder.mTextView12.setText("每日精选");
        } else if (holder instanceof NormalHoler) {
            final boolean isPlaying = false;
            final NormalHoler normalHoler = (NormalHoler) holder;
            if (items != null) {
                normalHoler.mLayoutNormal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到每日精选详情界面
                        Intent intent = new Intent();
                        intent.putExtra("dailyUrl", UrlData.DAILY1);

                    }
                });
                normalHoler.mImageViewNormal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击播放音乐，将按钮改变
                        normalHoler.mImageViewNormal.setImageResource(R.drawable.player_bar_pause);
                        List<DailyEntity.DataBean.ItemsBean.PostAudioBean> post_audio = items.get(position - 13).getPost_audio();
                        DailyEntity.DataBean.ItemsBean.PostAudioBean postAudioBean = post_audio.get(0);
                        String url = postAudioBean.getUrl();//音乐url
                        Log.d("sssss", url);
                        Intent intent1 = new Intent("com.kygo.progress");
                        intent1.putExtra("musicname", url);
                        intent1.putExtra("musictitle", postAudioBean.getSong_name());
                        intent1.putExtra("musiccontent", postAudioBean.getArtist());
                        context.sendBroadcast(intent1);

                    }
                });
                normalHoler.contentNormal.setText(items.get(position - 13).getContent());
                List<DailyEntity.DataBean.ItemsBean.PostAudioBean> post_audio = items.get(position - 13).getPost_audio();
                normalHoler.userName.setText(items.get(position - 13).getUser_name() + " 分享" + post_audio.size() + "首歌");
                String user_avatar = items.get(position - 13).getUser_avatar();
                String userUrl = user_avatar.substring(0, user_avatar.lastIndexOf("?"));
                normalHoler.mDraweeViewuser.setImageURI(userUrl);
                normalHoler.mDraweeView.setImageURI(items.get(position - 13).getPost_img().get(0));
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                Intent intent1 = new Intent();
                intent1.putExtra("webUrl3", UrlData.AD1);
                intent1.setClass(context, DetailActivity.class);
                context.startActivity(intent1);
                break;
            case 1:

                break;
            case 2:
                Intent intent = new Intent();
                intent.putExtra("webUrl3", UrlData.AD3);
                intent.setClass(context, DetailActivity.class);
                context.startActivity(intent);
                break;
        }
    }

    class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            imageView.setImageResource(R.mipmap.ic_launcher);
            ImageLoader.getInstance().displayImage(data, imageView);
        }
    }

    class NetworkImageHolderView1 implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            imageView.setImageResource(R.mipmap.ic_launcher);
            ImageLoader.getInstance().displayImage(data, imageView);
        }
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size() + 13;
        }
        return 13;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == 1) {
            return 1;
        } else if (position == 2) {
            return 2;
        } else if (position == 3) {
            return 3;
        } else if (position == 4) {
            return 4;
        } else if (position == 5) {
            return 5;
        } else if (position == 6) {
            return 6;
        } else if (position == 7) {
            return 7;
        } else if (position == 8) {
            return 8;
        } else if (position == 9) {
            return 9;
        } else if (position == 10) {
            return 10;
        } else if (position == 11) {
            return 11;
        } else if (position == 12) {
            return 12;
        }
        return 13;
    }

    class HeadHolder extends RecyclerView.ViewHolder {//头部轮播
        ConvenientBanner mConvenientBanner;

        public HeadHolder(View itemView) {
            super(itemView);
            mConvenientBanner = (ConvenientBanner) itemView.findViewById(R.id.convenientBanner);
        }
    }

    class Firstholder extends RecyclerView.ViewHolder {//音乐期刊全部
        RelativeLayout mRelativeLayout;

        public Firstholder(View itemView) {
            super(itemView);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.layout_itemwhite);
        }
    }

    class SecondHolder extends RecyclerView.ViewHolder {//音乐期刊item
        SimpleDraweeView firitem;
        TextView firstKuang, name, fav, comm;
        LinearLayout mLayout;

        public SecondHolder(View itemView) {
            super(itemView);
            firitem = (SimpleDraweeView) itemView.findViewById(R.id.fresco_firitem);
            firstKuang = (TextView) itemView.findViewById(R.id.tv_firstitem_kuang);
            name = (TextView) itemView.findViewById(R.id.tv_seconditem_name);
            fav = (TextView) itemView.findViewById(R.id.tv_item_favnum);
            comm = (TextView) itemView.findViewById(R.id.tv_item_commnum);
            mLayout = (LinearLayout) itemView.findViewById(R.id.layout_item_textcontainer);
        }
    }

    class ThirdHolder extends RecyclerView.ViewHolder {//原始推荐全部
        RelativeLayout mRelativeLayout3;
        TextView mTextView3;

        public ThirdHolder(View itemView) {
            super(itemView);
            mTextView3 = (TextView) itemView.findViewById(R.id.tv_discover_divider_name);
            mRelativeLayout3 = (RelativeLayout) itemView.findViewById(R.id.layout_itemwhite);
        }
    }

    class FourthHolder extends RecyclerView.ViewHolder {//原始推荐2个item
        RelativeLayout forthHolderleft, forthHolderright;
        SimpleDraweeView left, right;
        TextView nameLeft, nameRight, contenLeft, contentRight;

        ImageView mImageViewleft, mImageViewright;

        public FourthHolder(View itemView) {
            super(itemView);
            forthHolderleft = (RelativeLayout) itemView.findViewById(R.id.layout_seconditem_left);
            forthHolderright = (RelativeLayout) itemView.findViewById(R.id.layout_seconditem_right);
            left = (SimpleDraweeView) itemView.findViewById(R.id.fresco_seconditem_left);
            right = (SimpleDraweeView) itemView.findViewById(R.id.fresco_seconditem_right);
            mImageViewleft = (ImageView) itemView.findViewById(R.id.iv_seconditem_leftplaybtn);
            mImageViewright = (ImageView) itemView.findViewById(R.id.iv_seconditem_rightplaybtn);
            nameLeft = (TextView) itemView.findViewById(R.id.tv_seconditem_left_name);
            nameRight = (TextView) itemView.findViewById(R.id.tv_seconditem_right_name);
            contenLeft = (TextView) itemView.findViewById(R.id.tv_seconditem_left_content);
            contentRight = (TextView) itemView.findViewById(R.id.tv_seconditem_right_content);
        }
    }

    class FifthHolder extends RecyclerView.ViewHolder {//在线视频全部
        RelativeLayout mLayoutFifth;
        TextView mTextView5;

        public FifthHolder(View itemView) {
            super(itemView);
            mTextView5 = (TextView) itemView.findViewById(R.id.tv_discover_divider_name);
            mLayoutFifth = (RelativeLayout) itemView.findViewById(R.id.layout_itemwhite);
        }
    }

    class SixHolder extends RecyclerView.ViewHolder {//在线视频item
        FrameLayout mFrameLayout6;
        SimpleDraweeView mDraweeView6;
        TextView name, content, bottom;

        public SixHolder(View itemView) {
            super(itemView);
            mFrameLayout6 = (FrameLayout) itemView.findViewById(R.id.layout_thirditem_play);
            mDraweeView6 = (SimpleDraweeView) itemView.findViewById(R.id.fresco_thirditem);
            name = (TextView) itemView.findViewById(R.id.tv_thirditem_name);
            content = (TextView) itemView.findViewById(R.id.tv_thirditem_content);
            bottom = (TextView) itemView.findViewById(R.id.tv_thirditem_bottom);
        }
    }

    class SevenHolder extends RecyclerView.ViewHolder {//专栏文章全部
        RelativeLayout mLayout6;
        TextView mTextView6;

        public SevenHolder(View itemView) {
            super(itemView);
            mTextView6 = (TextView) itemView.findViewById(R.id.tv_discover_divider_name);
            mLayout6 = (RelativeLayout) itemView.findViewById(R.id.layout_itemwhite);
        }
    }

    class EightHolder extends RecyclerView.ViewHolder {//专栏文章item，后面2个也是
        SimpleDraweeView mDraweeView8;
        RelativeLayout mLayout8;
        TextView title8, content8;

        public EightHolder(View itemView) {
            super(itemView);
            mLayout8 = (RelativeLayout) itemView.findViewById(R.id.layou_fourthitem_container);
            mDraweeView8 = (SimpleDraweeView) itemView.findViewById(R.id.fresco_fourthitem);
            title8 = (TextView) itemView.findViewById(R.id.tv_fourthitem_title);
            content8 = (TextView) itemView.findViewById(R.id.tv_fourthitem_content);
        }
    }

    class NineHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mDraweeView9;
        RelativeLayout mLayout9;
        TextView title9, content9;

        public NineHolder(View itemView) {
            super(itemView);
            mLayout9 = (RelativeLayout) itemView.findViewById(R.id.layou_fourthitem_container);
            mDraweeView9 = (SimpleDraweeView) itemView.findViewById(R.id.fresco_fourthitem);
            title9 = (TextView) itemView.findViewById(R.id.tv_fourthitem_title);
            content9 = (TextView) itemView.findViewById(R.id.tv_fourthitem_content);
        }
    }

    class TenHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mDraweeView10;
        RelativeLayout mLayout10;
        TextView title10, content10;

        public TenHolder(View itemView) {
            super(itemView);
            mLayout10 = (RelativeLayout) itemView.findViewById(R.id.layou_fourthitem_container);
            mDraweeView10 = (SimpleDraweeView) itemView.findViewById(R.id.fresco_fourthitem);
            title10 = (TextView) itemView.findViewById(R.id.tv_fourthitem_title);
            content10 = (TextView) itemView.findViewById(R.id.tv_fourthitem_content);
        }
    }

    class ElevenHolder extends RecyclerView.ViewHolder {//滚动广告item
        ConvenientBanner mConvenientBanner11;

        public ElevenHolder(View itemView) {
            super(itemView);
            mConvenientBanner11 = (ConvenientBanner) itemView.findViewById(R.id.convenientBanner);
        }
    }

    class TwelveHolder extends RecyclerView.ViewHolder {//每日精选全部
        RelativeLayout mLayout12;
        TextView mTextView12;

        public TwelveHolder(View itemView) {
            super(itemView);
            mTextView12 = (TextView) itemView.findViewById(R.id.tv_discover_divider_name);
            mLayout12 = (RelativeLayout) itemView.findViewById(R.id.layout_itemwhite);
        }
    }

    class NormalHoler extends RecyclerView.ViewHolder {//普通item
        RelativeLayout mLayoutNormal;
        SimpleDraweeView mDraweeView, mDraweeViewuser;
        TextView contentNormal, userName;
        ImageView mImageViewNormal;

        public NormalHoler(View itemView) {
            super(itemView);
            mLayoutNormal = (RelativeLayout) itemView.findViewById(R.id.layout_fifthitem_root);
            mDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.fresco_fifthitem);
            mDraweeViewuser = (SimpleDraweeView) itemView.findViewById(R.id.fresco_firitem_usericon);
            contentNormal = (TextView) itemView.findViewById(R.id.tv_fifthitem_content);
            userName = (TextView) itemView.findViewById(R.id.tv_fifthitem_username);
            mImageViewNormal = (ImageView) itemView.findViewById(R.id.iv_fifthitem_playbtn);
        }
    }

}
