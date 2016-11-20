package com.app.activityfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.adapter.ActFragmentPagerAdapter;
import com.app.luooapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-9-22.
 */
public class ActivityFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    RadioGroup radioGroup;
    ViewPager viewPager;
    List<Fragment> list;
    ActFragmentPagerAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager= (ViewPager) view.findViewById(R.id.actviewpagerId);
        radioGroup= (RadioGroup) view.findViewById(R.id.actradiogroupId);
        radioGroup.setOnCheckedChangeListener(this);
        initData();
        adapter=new ActFragmentPagerAdapter(getChildFragmentManager(),list);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                RadioButton button;
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    button= (RadioButton) radioGroup.getChildAt(i);
                    if(i==position){
                       button.setTextColor(Color.RED);
                    }else {
                       button.setTextColor(Color.BLACK);
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        list=new ArrayList<>();
        ActActFragment f1=new ActActFragment();
        ActPlaceFragment f2=new ActPlaceFragment();
        list.add(f1);
        list.add(f2);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.actradiobutton1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.actradiobutton2:
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
