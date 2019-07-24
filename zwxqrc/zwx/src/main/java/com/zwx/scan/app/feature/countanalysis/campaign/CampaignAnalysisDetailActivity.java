package com.zwx.scan.app.feature.countanalysis.campaign;


import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.tablayout.SlidingTabLayout;
import com.zwx.library.tablayout.listener.OnTabSelectListener;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.CampaignChartBean;
import com.zwx.scan.app.data.bean.CampaignDetailBean;
import com.zwx.scan.app.data.bean.CampaignTotal;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.countanalysis.CountMoreStoreChartDateFragment;
import com.zwx.scan.app.feature.countanalysis.MultiAdapter;
import com.zwx.scan.app.feature.countanalysis.member.MemberAnalysisDetailActivity;
import com.zwx.scan.app.widget.PhilExpandableTextView;
import com.zwx.scan.app.widget.SegmentControl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class CampaignAnalysisDetailActivity extends BaseActivity<CampaignContract.Presenter> implements CampaignContract.View,View.OnClickListener{

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    /*@BindView(R.id.rv_list)
    protected RecyclerView rvTopList;
*/

    @BindView(R.id.tv_join)
    protected TextView tv_join;

    @BindView(R.id.tv_foward)
    protected TextView tv_foward;

    @BindView(R.id.tv_get)
    protected TextView tv_get;

    @BindView(R.id.tv_send)
    protected TextView tv_send;

    @BindView(R.id.tv_receive)
    protected TextView tv_receive;

    @BindView(R.id.tv_view)
    protected TextView tv_view;

    @BindView(R.id.tv_register)
    protected TextView tv_register;


    @BindView(R.id.rv_coupon_list)
    protected RecyclerView rvCouponList;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;
    @BindView(R.id.segment)
    protected SegmentControl segmentControl;
    @BindView(R.id.tab_layout)
    protected SlidingTabLayout tabLayout;


    @BindView(R.id.tv_chart_name)
    protected TextView tvChartName;
    @BindView(R.id.tv_total)
    protected TextView tvTotal;

    @BindView(R.id.tv_legend)
    protected TextView tvLegend;
    @BindView(R.id.pager)
    protected CustomViewPager viewPager;
    @BindView(R.id.ll_bottom)
    protected LinearLayout linearLayout;
    @BindView(R.id.ll_select_store)
    protected LinearLayout ll_select_store;
    protected  CampaignDetailCouponAdapter couponAdapter;


    public MemberAnalysisDetailActivity.MoreFPagerAdapter pageAdapter;

    @BindView(R.id.tv_select_shop)
    protected TextView tvSelect;

    @BindView(R.id.iv_brand)
    protected ImageView ivBrand;

//    @BindView(R.id.tv_store_name)
//    protected MoreTextView tvStoreNames;
    @BindView(R.id.tv_store_name)
    protected PhilExpandableTextView tvStoreNames;

    @BindView(R.id.iv_arrow)
    protected ImageView iv_arrow;


    @BindView(R.id.dots)
    protected LinearLayout mDotsLayout;

    @BindView(R.id.rv_checkbox)
    protected RecyclerView rvCheckBox;


    List<CampaignTotal> campaignTotals = new ArrayList<>();
    protected ArrayList<Fragment> mFragments = new ArrayList<>();

    protected  List<String> mTitles = new ArrayList<>();

    private  String date  = "day";
    private boolean isLegend = true;
    private String campaignId = "";
    private String campaignWay = "";
    private  MyPagerAdapter mAdapter;
//    protected FPagerAdapter mPagerAdapter;
    protected CampaignPagerAdapter mPagerAdapter;
    protected ArrayList<Map<String,Object>> joinList = new ArrayList<>();
    protected ArrayList<Map<String,Object>> fowardList = new ArrayList<>();

    protected ArrayList<Map<String,Object>> sendList = new ArrayList<>();
    protected ArrayList<Map<String,Object>> getList =new ArrayList<>();

    protected ArrayList<Map<String,Object>> receiveList = new ArrayList<>();

    protected ArrayList<Map<String,Object>> registerList = new ArrayList<>();

    protected ArrayList<Map<String,Object>> viewList = new ArrayList<>();


    protected  List<CampaignChartBean> joinList2 = new ArrayList<>();
    protected  List<CampaignChartBean> fowardList2 = new ArrayList<>();
    protected  List<CampaignChartBean> sendList2 = new ArrayList<>();
    protected  List<CampaignChartBean> getList2 =new ArrayList<>();

    protected  List<CampaignChartBean> receiveList2 = new ArrayList<>();

    protected  List<CampaignChartBean> registerList2 = new ArrayList<>();

    protected  List<CampaignChartBean> viewList2 = new ArrayList<>();

    protected  MultiAdapter multiAdapter;
    protected List<Map<String,Object>> couponCampaignTotalList = new ArrayList<>();

    protected String joinTotal;
    protected String fowardTotal;
    protected String sendTotal;
    protected String getTotal;
    protected String receiveTotal;
    protected String registerTotal;
    protected String viewTotal;



    protected CampaignTotal campaignTotal = new CampaignTotal();
    private String storeName ;
    private String storeId ;
    private String brandLogo;

    protected List<CampaignChartBean> campaignChartBeanList = new ArrayList<>();
    protected  List<Integer> colors = new ArrayList<>();
    protected  ArrayList<CampaignChartBean> checkStoreColorList = new ArrayList<>();

    private String storeIdArray;
    private String storeNameArray;

    protected int tabSelect = 0;

    private String isShowOneStore;

    private String campaignName;
    protected CampaignDetailBean campaignDetail = new CampaignDetailBean();
    String total;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_campaign_analysis_detail;
    }

    private void setBrandLogo(){
        RoundedCorners roundedCorners= new RoundedCorners(8);
        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_image_loading);

        Glide.with(this).load(brandLogo).apply(requestOptions).into(ivBrand);
    }
    @Override
    protected void initView() {


        DaggerCampaignComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignModule(new CampaignModule(this))
                .build()
                .inject(this);

        campaignName=getIntent().getStringExtra("campaignName");
        tvTitle.setText(campaignName);
        //"新增会员数", "浏览量"

        campaignWay=getIntent().getStringExtra("campaignType");
        campaignId =getIntent().getStringExtra("campaignId");

        storeIdArray =getIntent().getStringExtra("storeIdArray");
        storeNameArray =getIntent().getStringExtra("storeNameArray");

        isShowOneStore = getIntent() .getStringExtra("isShowOneStore");
        if (!"YES".equals(isShowOneStore)) {
            tvSelect.setVisibility(View.VISIBLE);
        }else {
            tvSelect.setVisibility(View.GONE);
        }
        ll_select_store.setVisibility(View.VISIBLE);
        rvCheckBox.setVisibility(View.VISIBLE);
        if((storeIdArray !=null &&storeIdArray.length()>0)){
            storeId = storeIdArray;
        }

        if(storeNameArray !=null &&storeNameArray.length()>0){
            storeName = storeNameArray;

        }


        brandLogo = getIntent().getStringExtra("brandLogo");
        setBrandLogo();

        checkStoreColorList = (ArrayList<CampaignChartBean>) getIntent().getSerializableExtra("checkStoreColorList");
        colors= Arrays.asList( getResources().getColor(R.color.blue),
                getResources().getColor(R.color.orange),
                Color.RED,
                getResources().getColor(R.color.green),
                Color.YELLOW,Color.CYAN
                ,Color.MAGENTA,Color.LTGRAY,Color.DKGRAY
        );
        if(storeName!=null){
            tvStoreNames.setText(storeName);
        }
        tvSelect.setVisibility(View.GONE);

        linearLayout.setVisibility(View.VISIBLE);

        segmentControl.setSelectedIndex(0);

        segmentControl.setOnSegmentChangedListener(new SegmentControl.OnSegmentChangedListener() {
            @Override
            public void onSegmentChanged(int newSelectedIndex) {
                if(newSelectedIndex == 0) {
                    date = "day";

                }else if(newSelectedIndex == 1) {
                    date = "week";
                }if(newSelectedIndex == 2){
                    date = "month";
                }
                tabSelect = newSelectedIndex;
                viewPager.setCurrentItem(tabSelect);
                if(tabSelect ==0){
                    tvChartName.setText("活动参与总人数量趋势");
                    total = joinTotal!=null && !"0".equals(joinTotal)?joinTotal +"人":"—";
                    tvTotal.setText("参与总人数："+total);
                    tvLegend.setText("参与人数");
                }else if(tabSelect ==1){
                    tvChartName.setText("活动转发数量趋势");
                    total = fowardTotal!=null && !fowardTotal.equals("0")?fowardTotal + "次":"—";
                    tvTotal.setText("转发数量："+total);
                    tvLegend.setText("转发数量");
                }else if(tabSelect ==2){
                    tvChartName.setText("优惠券发放数量趋势");
                    total = sendTotal!=null && !sendTotal.equals("0")?sendTotal + "张":"—";
                    tvTotal.setText("发放总数："+total);
                    tvLegend.setText("发放数量");
                }else if(tabSelect ==3){
                    tvChartName.setText("优惠券领取数量趋势");
                    total = getTotal!=null && !getTotal.equals("0")?getTotal+"张":"—";
                    tvTotal.setText("领取总数："+total);
                    tvLegend.setText("领取总数");
                }else if(tabSelect ==4){
                    tvChartName.setText("优惠券回收数量趋势");
                    total = receiveTotal!=null && !receiveTotal.equals("0")?receiveTotal+"张":"—";
                    tvTotal.setText("回收总数："+total);
                    tvLegend.setText("回收数量");
                }else if(tabSelect ==5){
                    tvChartName.setText("活动新增会员趋势");
                    total = registerTotal!=null && !registerTotal.equals("0")?registerTotal + "人":"—";
                    tvTotal.setText("新增总数："+total);
                    tvLegend.setText("新增会员数");
                }else if(tabSelect ==6){
                    tvChartName.setText("活动浏览量趋势");
                    total = viewTotal!=null && !viewTotal.equals("0")?viewTotal + "次":"—";
                    tvTotal.setText("浏览总数："+total);
                    tvLegend.setText("浏览数");
                }

                presenter.queryMoreStoreWholeCount(CampaignAnalysisDetailActivity.this,storeId,campaignId,date,false);
            }
        });
        tvChartName.setText("活动参与总人数量趋势");
        total = joinTotal!=null && !"0".equals(joinTotal)?joinTotal +"人":"—";
        tvTotal.setText("参与总人数："+total);
        tvLegend.setText("参与人数");


        mTitles = new ArrayList<>();
        mTitles.add("参与总人数");
        mTitles.add("转发数量");
        mTitles.add("优惠券发放量");
        mTitles.add("优惠券领取数量");
        mTitles.add("优惠券回收数");
        mTitles.add("新增会员数");
        mTitles.add("浏览量");

        setPageAdapter();
        setCheckboxAdapter(0);
        presenter.queryMoreStoreWholeAndCouponCount(CampaignAnalysisDetailActivity.this,storeId,campaignId,date,true);
        rvCouponList.setLayoutManager(new LinearLayoutManager(this));
        rvCouponList.setNestedScrollingEnabled(false);



    }

    public void setPageAdapter(){
        mPagerAdapter = new CampaignPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mPagerAdapter);
        tabLayout.setViewPager(viewPager);
        viewPager.setCurrentItem(0);
        setDotLayout();
        setLisenter();
    }
    private void setLisenter(){
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {


                if(position ==0){
                    tvChartName.setText("活动参与总人数量趋势");
                    total = joinTotal!=null && !"0".equals(joinTotal)?joinTotal +"人":"—";
                    tvTotal.setText("参与总人数："+total);
                    tvLegend.setText("参与人数");
                }else if(position ==1){
                    tvChartName.setText("活动转发数量趋势");
                    total = fowardTotal!=null && !fowardTotal.equals("0")?fowardTotal + "次":"—";
                    tvTotal.setText("转发数量："+total);
                    tvLegend.setText("转发数量");
                }else if(position ==2){
                    tvChartName.setText("优惠券发放数量趋势");
                    total = sendTotal!=null && !sendTotal.equals("0")?sendTotal + "张":"—";
                    tvTotal.setText("发放总数："+total);
                    tvLegend.setText("发放数量");
                }else if(position ==3){
                    tvChartName.setText("优惠券领取数量趋势");
                    total = getTotal!=null && !getTotal.equals("0")?getTotal+"张":"—";
                    tvTotal.setText("领取总数："+total);
                    tvLegend.setText("领取总数");
                }else if(position ==4){
                    tvChartName.setText("优惠券回收数量趋势");
                    total = receiveTotal!=null && !receiveTotal.equals("0")?receiveTotal+"张":"—";
                    tvTotal.setText("回收总数："+total);
                    tvLegend.setText("回收数量");
                }else if(position ==5){
                    tvChartName.setText("活动新增会员趋势");
                    total = registerTotal!=null && !registerTotal.equals("0")?registerTotal + "人":"—";
                    tvTotal.setText("新增总数："+total);
                    tvLegend.setText("新增会员数");
                }else if(position ==6){
                    tvChartName.setText("活动浏览量趋势");
                    total = viewTotal!=null && !viewTotal.equals("0")?viewTotal + "次":"—";
                    tvTotal.setText("浏览总数："+total);
                    tvLegend.setText("浏览数");
                }
                tabSelect = position;

            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {

                String total;
                tabSelect  = position;
                if(position ==0){
                    tvChartName.setText("活动参与总人数量趋势");
                    total = joinTotal!=null && !joinTotal.equals("0")?joinTotal +"人":"—";
                    tvTotal.setText("参与总人数："+total);
                    tvLegend.setText("参与人数");

                }else if(position ==1){
                    tvChartName.setText("活动转发数量趋势");
                    total = fowardTotal!=null && !fowardTotal.equals("0")?fowardTotal + "次":"—";
                    tvTotal.setText("转发数量："+total);
                    tvLegend.setText("转发数量");
                }else if(position ==2){
                    tvChartName.setText("优惠券发放数量趋势");
                    total = sendTotal!=null && !sendTotal.equals("0")?sendTotal + "张":"—";
                    tvTotal.setText("发放总数："+total);
                    tvLegend.setText("发放数量");
                }else if(position ==3){
                    tvChartName.setText("优惠券领取数量趋势");
                    total = getTotal!=null && !getTotal.equals("0")?getTotal+"张":"—";
                    tvTotal.setText("领取总数："+total);
                    tvLegend.setText("领取总数");
                }else if(position ==4){
                    tvChartName.setText("优惠券回收数量趋势");
                    total = receiveTotal!=null && !receiveTotal.equals("0")?receiveTotal+"张":"—";
                    tvTotal.setText("回收总数："+total);
                    tvLegend.setText("回收数量");
                }else if(position ==5){
                    tvChartName.setText("活动新增会员趋势");
                    total = registerTotal!=null && !registerTotal.equals("0")?registerTotal + "人":"—";
                    tvTotal.setText("新增总数："+total);
                    tvLegend.setText("新增会员数");
                }else if(position ==6){
                    tvChartName.setText("活动浏览量趋势");
                    total = viewTotal!=null && !viewTotal.equals("0")?viewTotal + "次":"—";
                    tvTotal.setText("浏览总数："+total);
                    tvLegend.setText("浏览数");
                }
                setMoveDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
    private List<ImageView> mDotsIV = new ArrayList<>();
    private void setMoveDot(int position){
        for (int i = 0; i < mDotsIV.size(); i++) {
            if (i == position) {
                mDotsIV.get(i).setImageResource(R.drawable.dot_focus);
            } else {
                mDotsIV.get(i).setImageResource(R.drawable.dot_blur);
            }
        }
    }

    protected void setDotLayout(){
        //先删除
        mDotsIV.clear();
        mDotsLayout.removeAllViews();
        for (int i = 0; i < 7; i++) {
            ImageView dotIV = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = params.rightMargin = 4;
            mDotsLayout.addView(dotIV, params);
            if (i == 0) {
                dotIV.setImageResource(R.drawable.dot_focus);
            } else {
                dotIV.setImageResource(R.drawable.dot_blur);
            }
            mDotsIV.add(dotIV);
        }

    }

  /*  protected void refresh() {
        mAdapter.notifyDataSetChanged();
    }
    protected  void setViewA(){
        mAdapter.notifyDataSetChanged();
        viewPager.setAdapter(mAdapter);
        tabLayout.setViewPager(viewPager);
    }*/

    protected  void setCouponAdapter(){

        couponAdapter = new CampaignDetailCouponAdapter(this, couponCampaignTotalList, new CommonRecyclerViewHolder.OnItemCommonClickListener() {
            @Override
            public void onItemClickListener(int position) {

            }
        });
        rvCouponList.setAdapter(couponAdapter);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.iv_back,R.id.iv_arrow})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.startActivity(CampaignAnalysisActivity.class,
                        R.anim.slide_in_left,R.anim.slide_out_right);
                ActivityUtils.finishActivity(CampaignAnalysisDetailActivity.class);
                break;
            case R.id.iv_arrow:
                boolean b=tvStoreNames.getExpandableStatus();
                b=!b;
                if(b){
                    iv_arrow.setImageResource(R.drawable.ic_arrow_up);
                }else {
                    iv_arrow.setImageResource(R.drawable.ic_arrow_down);
                }

                tvStoreNames.setExpandable(b);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        ActivityUtils.finishActivity(CampaignAnalysisDetailActivity.class,
//                R.anim.slide_in_left,R.anim.slide_out_right);

        ActivityUtils.startActivity(CampaignAnalysisActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
        ActivityUtils.finishActivity(CampaignAnalysisDetailActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(pageAdapter != null){
            pageAdapter = null;

            if(viewPager != null){
                viewPager.removeAllViews();
            }


        }
    }

    protected class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        private int mChildCount = 0;
        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();

            super.notifyDataSetChanged();

        }



        @Override
        public int getItemPosition(@NonNull Object object) {
            if (mChildCount > 0) {
                mChildCount--;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);

        }


    }




    public class CampaignPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragmentList;
        private FragmentManager mFragmentManager;

        public CampaignPagerAdapter(FragmentManager fm) {
            super(fm);
            this.mFragmentManager = fm;
            mFragmentList = new ArrayList<>();
            for (int i = 0, size = mTitles.size(); i < size; i++) {
                String title = mTitles.get(i);
                if(title.equals("参与总人数")){

                    mFragmentList.add(CountMoreStoreChartDateFragment.getInstance(date,joinList2));
                }else if(title.equals("转发数量")){
                    mFragmentList.add(CountMoreStoreChartDateFragment.getInstance(date,fowardList2));
                }else if(title.equals("优惠券发放量")){
                    mFragmentList.add(CountMoreStoreChartDateFragment.getInstance(date,sendList2));
                }else if(title.equals("优惠券领取数量")){
                    mFragmentList.add(CountMoreStoreChartDateFragment.getInstance(date,getList2));
                }else if(title.equals("优惠券回收数")){
                    mFragmentList.add(CountMoreStoreChartDateFragment.getInstance(date,receiveList2));
                }else if(title.equals("新增会员数")){
                    mFragmentList.add(CountMoreStoreChartDateFragment.getInstance(date,registerList2));
                }else if(title.equals("浏览量")){
                    mFragmentList.add(CountMoreStoreChartDateFragment.getInstance(date,viewList2));
                }


            }
            setFragments(mFragmentList);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        public void updateData() {
            ArrayList<Fragment> fragments = new ArrayList<>();

            for (int i = 0, size = mTitles.size(); i < size; i++) {
                Log.e("FPagerAdapter1", mTitles.get(i).toString());
                String title = mTitles.get(i);
                if(title.equals("参与总人数")){

                    fragments.add(CountMoreStoreChartDateFragment.getInstance(date,joinList2));

                }else if(title.equals("转发数量")){

                    fragments.add(CountMoreStoreChartDateFragment.getInstance(date,fowardList2));
                }else if(title.equals("优惠券发放量")){

                    fragments.add(CountMoreStoreChartDateFragment.getInstance(date,sendList2));
                }else if(title.equals("优惠券领取数量")){

                    fragments.add(CountMoreStoreChartDateFragment.getInstance(date,getList2));
                }else if(title.equals("优惠券回收数")){

                    fragments.add(CountMoreStoreChartDateFragment.getInstance(date,receiveList2));
                }else if(title.equals("新增会员数")){

                    fragments.add(CountMoreStoreChartDateFragment.getInstance(date,registerList2));
                }else if(title.equals("浏览量")){

                    fragments.add(CountMoreStoreChartDateFragment.getInstance(date,viewList2));
                }
            }
            setFragments(fragments);
        }

        /* @Override
         public CharSequence getPageTitle(int position) {
             return mTitles.get(position);
         }*/
        private void setFragments(ArrayList<Fragment> mFragmentList) {
            if(this.mFragmentList != null){
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                for(Fragment f:this.mFragmentList){
                    fragmentTransaction.remove(f);
                }
//                fragmentTransaction.commit();
                fragmentTransaction.commitAllowingStateLoss();   //解决 java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState异常

                if(mFragmentManager.isDestroyed()){

                }else {
                    mFragmentManager.executePendingTransactions();
                }

            }
            this.mFragmentList = mFragmentList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return this.mFragmentList.size();
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
    }

    protected  void setCheckboxAdapter(int position){


        if(position == 0) {
            campaignChartBeanList = joinList2;
        }else if(position == 1){
            campaignChartBeanList = fowardList2;
        }else if(position == 2){
            campaignChartBeanList = sendList2;
        }else if(position == 3){
            campaignChartBeanList = getList2;
        }else if(position == 4){
            campaignChartBeanList = receiveList2;
        }else if(position == 5){
            campaignChartBeanList = registerList2;
        }else if(position == 6){
            campaignChartBeanList = viewList2;
        }

        rvCheckBox.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvCheckBox.setLayoutManager(layoutManager);
        multiAdapter = new MultiAdapter(this,campaignChartBeanList,colors);
        rvCheckBox.setAdapter(multiAdapter);


        multiAdapter.setOnItemClickLitener(new MultiAdapter.OnMultiItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                StringBuilder storeIdSb = new StringBuilder();
                if(!multiAdapter.isSelected.get(position)){
                    multiAdapter.isSelected.put(position, true); // 修改map的值保存状态
                }else {
                    multiAdapter.isSelected.put(position, false);
                }
                multiAdapter.notifyItemChanged(position);
                if(multiAdapter.isSelected != null && multiAdapter.isSelected.size()>0){
                    for(int i=0;i<multiAdapter.isSelected.size();i++){
                        boolean selected= multiAdapter.isSelected.get(i);

                        CampaignChartBean chartBean  = campaignChartBeanList.get(i);

                        if(chartBean!=null){
                            if(selected){
                                chartBean.setChecked(true);
                                storeIdSb.append(chartBean.getStoreId()).append(",");
                            }else {
                                chartBean.setColor(0);
                                chartBean.setChecked(false);
                            }
                        }
                    }
                    String storeIdA= storeIdSb.toString();
                    if(storeIdA != null && !"".equals(storeIdA)){
                        String storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                        presenter.queryMoreStoreWholeCount(CampaignAnalysisDetailActivity.this,storeIdArr,campaignId,date,false);
                    }

                }


            }

        });
    }






}
