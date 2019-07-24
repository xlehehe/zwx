package com.zwx.instalment.app.feature.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/09/26
 * desc   :
 * version: 1.0
 **/
public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();
    private String[] titles = {};
    public MyPagerAdapter(List<Fragment> fragments, String[] titles, FragmentManager fm) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
