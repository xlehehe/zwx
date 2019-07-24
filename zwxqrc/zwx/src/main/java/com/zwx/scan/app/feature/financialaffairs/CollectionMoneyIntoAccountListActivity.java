package com.zwx.scan.app.feature.financialaffairs;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.tablayout.CommonTabLayout;
import com.zwx.library.tablayout.listener.CustomTabEntity;
import com.zwx.library.tablayout.listener.OnTabSelectListener;
import com.zwx.library.tablayout.listener.TabEntity;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.base.adapter.MyPagerAdapter;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.shop.ShopClassFragment;
import com.zwx.scan.app.feature.shop.ShopTelFragment;
import com.zwx.scan.app.widget.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/05/22
 * desc   : 收款到账列表
 * version: 1.0
 **/
public class CollectionMoneyIntoAccountListActivity extends BaseActivity<FinancialAffairsContract.Presenter> implements FinancialAffairsContract.View,View.OnClickListener  {

    @BindView(R.id.iv_back)
    protected ImageView ivBack;
    @BindView(R.id.tv_right)
    protected TextView tv_right;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;


    @BindView(R.id.tv_expected_into_amt)
    protected TextView tv_expected_into_amt;

    @BindView(R.id.tv_no_into_amt)
    protected TextView tv_no_into_amt;

    @BindView(R.id.common)
    protected CommonTabLayout tabLayout;

    @BindView(R.id.view_pager)
    protected NoScrollViewPager viewPager;
    //    protected List<String> mTitles = new ArrayList<>();
    private String[] titles = {"预计明天到账订单", "未到账订单"};
    private ArrayList<Fragment> fragments = new ArrayList<>();

    protected ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    protected int[] mIconUnselectIds = {
            R.drawable.shape_text_normal, R.drawable.shape_text_normal};
    protected int[] mIconSelectIds = {
            R.drawable.shape_btn_blue_pressed,R.drawable.shape_btn_blue_pressed};

    private MyPagerAdapter pagerAdapter;

    protected String userId;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection_money_into_account_list;
    }

    @Override
    protected void initView() {

        DaggerFinancialAffairsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .financialAffairsModule(new FinancialAffairsModule(this))
                .build()
                .inject(this);

        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("到账记录");


        tvTitle.setText("收款到账");

        for (String title : titles) {
            if("预计明天到账订单".equals(title)){
                fragments.add(CollectionExpectToArriveAmtFragment.getInstance("T"));
            }else  if("未到账订单".equals(title)){
                fragments.add(CollectionNoToArriveAmtFragment.getInstance("F"));
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
        userId = SPUtils.getInstance().getString("userId");
        presenter.queryWxPayInfo(this,userId);
    }

    @OnClick({R.id.iv_back,R.id.tv_right})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(CollectionMoneyIntoAccountListActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case R.id.tv_right:
                ActivityUtils.startActivity(CollectionTransferRecordActivity.class,
                        R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(CollectionMoneyIntoAccountListActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
