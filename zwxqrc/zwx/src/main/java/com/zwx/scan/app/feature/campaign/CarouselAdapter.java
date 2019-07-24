package com.zwx.scan.app.feature.campaign;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.widget.carouselview.CarouselViewPager;
import com.zwx.scan.app.widget.carouselview.pager.ScaledFrameLayout;

import java.util.ArrayList;

public class CarouselAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
    private float scale;
    private CarouselViewPager carousel;

    private Context context;
    private FragmentManager fragmentManager;
    private ArrayList<Coupon> entities = new ArrayList<Coupon>();
    private ScaledFrameLayout cur = null, next = null;

    private Coupon coupon = new Coupon();
    public final static float BIG_SCALE = 1.0f, SMALL_SCALE = 0.90f, DIFF_SCALE = BIG_SCALE - SMALL_SCALE;

    public CarouselAdapter(Context context, CarouselViewPager carousel, FragmentManager fragmentManager, ArrayList<Coupon> mData) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        this.context = context;
        this.carousel = carousel;
        this.entities = mData;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            scale = BIG_SCALE;
        } else {
            scale = SMALL_SCALE;
        }
        coupon  = entities.get(position);
//        Fragment fragment =CampaignCouponsFragment.getInstance(context,entities.get(position),position,scale);
//        return fragment;
        return null;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return entities.size();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset >= 0f && positionOffset <= 1f) {
            cur = getRootView(position);
            cur.setScaleBoth(BIG_SCALE - DIFF_SCALE * positionOffset);

            if (position < entities.size() - 1) {
                next = getRootView(position + 1);
                next.setScaleBoth(SMALL_SCALE + DIFF_SCALE * positionOffset);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onPageSelected(int position) {
        Log.w("onPageSelected", "position : " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) { }

    private ScaledFrameLayout getRootView(int position) {
//        return (ScaledFrameLayout) fragmentManager.findFragmentByTag(this.getFragmentTag(position)).getView().findViewById(R.id.rootItem);
        return null;

    }

    private String getFragmentTag(int position) {
        return "android:switcher:" + carousel.getId() + ":" + position;
    }
}