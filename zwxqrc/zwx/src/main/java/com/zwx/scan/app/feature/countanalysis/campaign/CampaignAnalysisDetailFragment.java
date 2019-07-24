package com.zwx.scan.app.feature.countanalysis.campaign;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.zwx.library.tablayout.CommonTabLayout;
import com.zwx.library.tablayout.listener.CustomTabEntity;
import com.zwx.library.tablayout.listener.OnTabSelectListener;
import com.zwx.library.tablayout.listener.TabEntity;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.data.bean.CampaignTotal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CampaignAnalysisDetailFragment extends BaseFragment {


//    @BindView(R.id.tv_date)
//    protected TextView tvDate;

    @BindView(R.id.common)
    protected CommonTabLayout tabLayout;

//    @BindView(R.id.fl_content)
//    protected FrameLayout frameLayout;
@BindView(R.id.view_pager)
protected ViewPager viewPager;

    List<CampaignTotal> campaignTotals = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();

    private String[] titles = {"参与总人数","转发数量","优惠券发放数","优惠券领取数", "优惠券回收数","新增会员数","浏览数"};
    private int[] mIconUnselectIds = {
            R.drawable.shape_text_normal, R.drawable.shape_text_normal,
            R.drawable.shape_text_normal, R.drawable.shape_text_normal,
            R.drawable.shape_text_normal,R.drawable.shape_text_normal,R.drawable.shape_text_normal};
    private int[] mIconSelectIds = {
            R.drawable.shape_btn_blue_pressed,R.drawable.shape_btn_blue_pressed,
            R.drawable.shape_btn_blue_pressed, R.drawable.shape_btn_blue_pressed,
            R.drawable.shape_btn_blue_pressed,R.drawable.shape_btn_blue_pressed,
            R.drawable.shape_btn_blue_pressed,};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private String params;
    public CampaignAnalysisDetailFragment() {
        // Required empty public constructor
    }

    public static CampaignAnalysisDetailFragment getInstance(String params) {
        CampaignAnalysisDetailFragment instance = new CampaignAnalysisDetailFragment();
        instance.params = params;
        return instance;
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_campaign_analysis_detail;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {

        String params2 = "";
        for (String title : titles) {
            if(title.equals("参与总人数")){
                params2 = "join";
                fragments.add(CampaignAnalysisReportFragment.getInstance(params,params2));
            }else  if(title.equals("转发数量")){
                params2 = "foward";
                fragments.add(CampaignAnalysisReportFragment.getInstance(params,params2));
            }else  if(title.equals("优惠券发放数")){
                params2 = "send";
                fragments.add(CampaignAnalysisReportFragment.getInstance(params,params2));
            }else  if(title.equals("优惠券领取数")){
                params2 = "get";
                fragments.add(CampaignAnalysisReportFragment.getInstance(params,params2));
            }else  if(title.equals("优惠券回收数")){
                params2 = "receive";
                fragments.add(CampaignAnalysisReportFragment.getInstance(params,params2));
            }else  if(title.equals("新增会员数")){
                params2 = "register";
                fragments.add(CampaignAnalysisReportFragment.getInstance(params,params2));
            }else  if(title.equals("浏览数")){
                params2 = "view";
                fragments.add(CampaignAnalysisReportFragment.getInstance(params,params2));
            }

        }

        for (int i = 0; i < titles.length; i++) {
            mTabEntities.add(new TabEntity(titles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        viewPager.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager()));

        tl_2();

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

    private void tl_2() {
        Random mRandom = new Random();
        tabLayout.setTabData(mTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                    tabLayout.showMsg(0, mRandom.nextInt(100) + 1);
//                    UnreadMsgUtils.show(mTabLayout_2.getMsgView(0), mRandom.nextInt(100) + 1);
                }
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

        viewPager.setCurrentItem(1);
    }
}
