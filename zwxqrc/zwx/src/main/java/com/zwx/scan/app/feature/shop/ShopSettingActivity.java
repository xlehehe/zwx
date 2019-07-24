package com.zwx.scan.app.feature.shop;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.tablayout.CommonTabLayout;
import com.zwx.library.tablayout.SlidingTabLayout;
import com.zwx.library.tablayout.listener.CustomTabEntity;
import com.zwx.library.tablayout.listener.OnTabSelectListener;
import com.zwx.library.tablayout.listener.TabEntity;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.base.adapter.MyPagerAdapter;
import com.zwx.scan.app.feature.contractmanage.ContractActivity;
import com.zwx.scan.app.feature.contractmanage.ContractInfoFragment;
import com.zwx.scan.app.feature.contractmanage.ContractViewFragment;
import com.zwx.scan.app.feature.member.MemberConsumeListActivity;
import com.zwx.scan.app.widget.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/04/18
 * desc   : 商城基本设置
 * version: 1.0
 **/
public class ShopSettingActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.tv_right)
    protected TextView tvRight;
    @BindView(R.id.common)
    protected CommonTabLayout tabLayout;

    @BindView(R.id.view_pager)
    protected NoScrollViewPager viewPager;
    //    protected List<String> mTitles = new ArrayList<>();
    private String[] titles = {"商品分类设置", "店铺联系电话"};
    private ArrayList<Fragment> fragments = new ArrayList<>();

    protected ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    protected int[] mIconUnselectIds = {
            R.drawable.shape_text_normal, R.drawable.shape_text_normal};
    protected int[] mIconSelectIds = {
            R.drawable.shape_btn_blue_pressed,R.drawable.shape_btn_blue_pressed};

    private MyPagerAdapter pagerAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_setting;
    }

    @Override
    protected void initView() {

        tvTitle.setText("商城基本设置");

        for (String title : titles) {
            if("商品分类设置".equals(title)){
                fragments.add(ShopClassFragment.getInstance(title));
            }else  if("店铺联系电话".equals(title)){
                fragments.add(ShopTelFragment.getInstance(title));
            }

        }

        for (int i = 0; i < titles.length; i++) {
            mTabEntities.add(new TabEntity(titles[i], mIconUnselectIds[i], mIconSelectIds[i]));
        }

        pagerAdapter = new MyPagerAdapter(fragments,titles,getSupportFragmentManager());
        viewPager.setCurrentItem(0);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setTabData(mTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(0);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:

                ActivityUtils.finishActivity(ShopSettingActivity.class,
                        R.anim.slide_in_left,R.anim.slide_out_right);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(ShopSettingActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
