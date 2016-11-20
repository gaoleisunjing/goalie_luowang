package com.app.forumfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.adapter.ForumFragmentAdapter;
import com.app.luooapp.LoginActivity;
import com.app.luooapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peiyangyang on 2016/9/26.
 */
public class ForumFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private ForumAttentionFragment mAttentionFragment;
    private ForumHotFragment mHotFragment;
    private ForumNewestFragment mNewestFragment;
    private List<Fragment> fragmentList;
    private ForumFragmentAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum, null);
        initView(view);
        initFrgment();
        initData();
        return view;
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(mAttentionFragment);
        fragmentList.add(mHotFragment);
        fragmentList.add(mNewestFragment);
    }

    private void initFrgment() {
        mAttentionFragment = new ForumAttentionFragment();
        mHotFragment = new ForumHotFragment();
        Log.d("hotFragment",""+000000000);
        mNewestFragment = new ForumNewestFragment();
    }

    private void initView(View view) {

        mViewPager = (ViewPager) view.findViewById(R.id.forum_frgment_viewPager);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.forum_RB_navigation);
        mRadioGroup.setOnCheckedChangeListener(this);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new ForumFragmentAdapter(
                getActivity().getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(1);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                RadioButton radioButton;
                for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
                    radioButton = (RadioButton) mRadioGroup.getChildAt(i);
                    if (i == position) {
                        radioButton.setTextColor(getResources().getColor(R.color.colorTxtSelected));
                    } else {
                        radioButton.setTextColor(getResources().getColor(R.color.colorTxtNormal));
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.forum_RB_attention:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
//                mViewPager.setCurrentItem(0);
                break;
            case R.id.forum_RB_hot:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.forum_RB_newest:
                mViewPager.setCurrentItem(2);
                break;

        }
    }
}
