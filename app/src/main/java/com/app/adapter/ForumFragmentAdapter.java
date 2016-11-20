package com.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by peiyangyang on 2016/9/28.
 */
public class ForumFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mList;
    public ForumFragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mList = list;
    }


    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
