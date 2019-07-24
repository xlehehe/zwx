package com.zwx.scan.app.feature.campaign;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * author : lizhilong
 * time   : 2019/01/31
 * desc   :
 * version: 1.0
 **/
public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {
    private long baseId = 0;
    private ArrayList<Fragment> fragments;
    private FragmentManager fm;

    public NewsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    public NewsFragmentPagerAdapter(FragmentManager fm,
                                    ArrayList<Fragment> fragments) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;
        setFragments(fragments);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setFragments(ArrayList<Fragment> fragments) {
        if (this.fragments != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragments) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        Object obj = super.instantiateItem(container, position);
        return obj;
    }

    @Override
    public long getItemId(int position) {
        // give an ID different from position when position has been changed
        return baseId + position;
    }

    /**
     * 更新fragment的数量之后，在调用notifyDataSetChanged之前，changeId(1) 改变id，改变tag
     * @param n
     */
    public void changeId(int n) {
        // shift the ID returned by getItemId outside the range of all previous fragments
        baseId += getCount() + n;
    }

}
