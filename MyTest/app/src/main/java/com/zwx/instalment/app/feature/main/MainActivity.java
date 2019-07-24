package com.zwx.instalment.app.feature.main;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.zwx.instalment.app.R;
import com.zwx.instalment.app.base.BaseActivity;
import com.zwx.instalment.app.widget.tablayout.CommonTabLayout;
import com.zwx.instalment.app.widget.tablayout.listener.CustomTabEntity;
import com.zwx.instalment.app.widget.tablayout.listener.OnTabSelectListener;
import com.zwx.instalment.app.widget.tablayout.listener.TabEntity;
import com.zwx.instalment.app.widget.tablayout.widget.BanViewPager;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] mTitles = {"首页", "商城","借款","生活","我的"};
    private int[] mIconUnselectIds = {
            R.drawable.home_icon_fthome,
            R.drawable.home_icon_ftshop,
            R.drawable.home_icon_ftmoney,
            R.drawable.home_icon_ftlife,
            R.drawable.home_icon_ftmy};
    private int[] mIconSelectIds = {
            R.drawable.home_icon_fthome_active,
            R.drawable.home_icon_ftshop_active,
            R.drawable.home_icon_ftmoney_active,
            R.drawable.home_icon_ftlife_active,
            R.drawable.home_icon_ftmy_active};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private View mDecorView;

    @BindView(R.id.view_pager)
    protected BanViewPager mViewPager;
    @BindView(R.id.tab_layout)
    protected CommonTabLayout mTabLayout;

    private long firstClick = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        initTab();

    }

    @Override
    protected void initData() {

    }

    private void initTab(){
        for (String title : mTitles) {
            if(title == "首页"){
                fragments.add(HomeFragment.getInstance(title));
            }else {
                fragments.add(OtherFragment.getInstance(title));
            }

        }
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mDecorView = getWindow().getDecorView();
        mViewPager.setAdapter(new MyPagerAdapter(fragments,mTitles,getSupportFragmentManager()));
        tabLayout();
    }

    private void tabLayout() {
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
//                    mTabLayout.showMsg(0, mRandom.nextInt(100) + 1);
//                    UnreadMsgUtils.show(mTabLayout.getMsgView(0), mRandom.nextInt(100) + 1);
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
        mTabLayout.setCurrentTab(0);
        /*isIntentFragment = getIntent().getStringExtra("isIntentFragment");
        if("PersonalFragment".equals(isIntentFragment)){
            mTabLayout.setCurrentTab(1);
            mViewPager.setCurrentItem(1);
        }else {
            mTabLayout.setCurrentTab(0);
            mViewPager.setCurrentItem(0);
        }*/
    }


    //双击退出应用
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondClick = System.currentTimeMillis();
            if (secondClick - firstClick > 1000) {
                Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT);
                firstClick = secondClick;
                return true;
            } else {
                //退出应用
//                ActivityUtils.finishAllActivities();
                moveTaskToBack(true);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
