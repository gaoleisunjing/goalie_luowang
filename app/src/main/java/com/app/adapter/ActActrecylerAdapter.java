package com.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bean.ActActBean;
import com.app.luooapp.R;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by LWL on 2016/9/29.
 */
public class ActActrecylerAdapter extends RecyclerView.Adapter {

    List<ActActBean.DataBean.ItemsBean> list;
    List<String> urlString;
    Context context;

    public ActActrecylerAdapter(List<ActActBean.DataBean.ItemsBean> list, List<String> urlString, Context context) {
        this.list = list;
        this.urlString = urlString;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            BannerViewHolder bannerholder = null;
            bannerholder = new BannerViewHolder(LayoutInflater.from(
                    parent.getContext()).
                    inflate(R.layout.item_activity_acthead, parent, false));

            return bannerholder;
        } else {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_activity_act, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BannerViewHolder) {
             BannerViewHolder holder1= (BannerViewHolder) holder;
            holder1.banner.setPages(new CBViewHolderCreator() {
                @Override
                public NetworkImageHolderView createHolder() {
                    return new NetworkImageHolderView();
                }
            }, urlString)
                    .setPointViewVisible(true)    //设置指示器是否可见
                    .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})   //设置指示器圆点
                    .startTurning(3000)     //设置自动切换（同时设置了切换时间间隔）
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL); //设置指示器位置（左、中、右）
            //.setOnItemClickListener(this); //设置点击监听事件
        } else if(holder instanceof MyViewHolder){
            MyViewHolder holder2 = (MyViewHolder) holder;
            if (position==list.size())return;
            String ss = list.get(position).getPoster();
            Picasso.with(holder2.itemView.getContext()).load(ss).into(holder2.imageView);
            holder2.textView1.setText(list.get(position).getSubject());
            holder2.textView2.setText(list.get(position).getTime());
            holder2.textView3.setText(list.get(position).getPlace_name());
            holder2.textView4.setText(list.get(position).getCity_name());
            holder2.textView5.setText(list.get(position).getFee());
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

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView1, textView2, textView3, textView4, textView5;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.actact_poster);
            textView1 = (TextView) itemView.findViewById(R.id.actact_subject);
            textView2 = (TextView) itemView.findViewById(R.id.actact_time);
            textView3 = (TextView) itemView.findViewById(R.id.actact_place_name);
            textView4 = (TextView) itemView.findViewById(R.id.actact_city_name);
            textView5 = (TextView) itemView.findViewById(R.id.actact_fee);
        }

    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        ConvenientBanner banner;

        public BannerViewHolder(View itemView) {
            super(itemView);
            banner = (ConvenientBanner) itemView.findViewById(R.id.actactConvenientBanner);
        }
    }
}
