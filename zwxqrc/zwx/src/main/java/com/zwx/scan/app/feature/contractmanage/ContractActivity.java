package com.zwx.scan.app.feature.contractmanage;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.zwx.scan.app.data.bean.Contract;
import com.zwx.scan.app.data.bean.ContractBean;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.accountmanage.AccountActivity;
import com.zwx.scan.app.feature.accountmanage.AccountContract;
import com.zwx.scan.app.feature.accountmanage.AccountModule;
import com.zwx.scan.app.feature.accountmanage.AccountNewActivity;
import com.zwx.scan.app.feature.accountmanage.DaggerAccountComponent;
import com.zwx.scan.app.feature.countanalysis.campaign.CampaignAnalysisDetailFragment;
import com.zwx.scan.app.feature.countanalysis.campaign.CampaignAnalysisReportFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ContractActivity extends BaseActivity<ContractContract.Presenter> implements ContractContract.View,View.OnClickListener{

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.tv_right)
    protected TextView tvRight;
    @BindView(R.id.common)
    protected CommonTabLayout tabLayout;

    @BindView(R.id.view_pager)
    protected ViewPager viewPager;

    protected String[] titles = {"签约信息","合同预览"};

    protected ArrayList<Fragment> fragments = new ArrayList<>();
    protected ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    protected int[] mIconUnselectIds = {
            R.drawable.shape_text_normal, R.drawable.shape_text_normal};
    protected int[] mIconSelectIds = {
            R.drawable.shape_btn_blue_pressed,R.drawable.shape_btn_blue_pressed};

    private String storeId;

    private String contractId;
    protected Contract contract = new Contract();
    protected ContractBean contractBean = new ContractBean();
    protected String contractImg;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_contract;
    }

    @Override
    protected void initView() {
        DaggerContractComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .contractModule(new ContractModule(this))
                .build()
                .inject(this);

        tvTitle.setText("合同内容");
//        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("全部合同");
        contractId = getIntent().getStringExtra("contractId");

        if(contractId == null || "".equals(contractId)){
            contractId = "";
        }
        String params = "";
        for (String title : titles) {
            if(title.equals("签约信息")){
                fragments.add(ContractInfoFragment.getInstance(contractId));
            }else  if(title.equals("合同预览")){
                fragments.add(ContractViewFragment.getInstance(params));
            }

        }

        for (int i = 0; i < titles.length; i++) {
            mTabEntities.add(new TabEntity(titles[i], mIconUnselectIds[i], mIconSelectIds[i]));
        }
        viewPager.setAdapter(new MyPagerAdapter(this.getSupportFragmentManager()));
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
                tabLayout.setCurrentTab(position);
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

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
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


    @OnClick({R.id.iv_back,R.id.ll_right})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {

            case R.id.iv_back:
                ActivityUtils.finishActivity(ContractActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);

                break;

            case R.id.ll_right:
                intent = new Intent(ContractActivity.this,ContractListActivity.class);
                intent.putExtra("isDetailAndList","NO");
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                break;


        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(ContractActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
