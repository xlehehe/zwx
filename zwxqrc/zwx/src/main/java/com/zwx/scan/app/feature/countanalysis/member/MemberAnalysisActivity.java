package com.zwx.scan.app.feature.countanalysis.member;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.CampaignChartBean;
import com.zwx.scan.app.data.bean.CompMember;
import com.zwx.scan.app.data.bean.MemCoupon;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.countanalysis.CountAnalysisHomeActivity;
import com.zwx.scan.app.feature.countanalysis.MoreBrandListAdapter;
import com.zwx.scan.app.feature.countanalysis.MultiAdapter;
import com.zwx.scan.app.feature.countanalysis.OnItemClickLitener;
import com.zwx.scan.app.feature.countanalysis.campaign.CampaignAnalysisActivity;
import com.zwx.scan.app.feature.countanalysis.campaign.CountMoreStoreChartFragment;
import com.zwx.scan.app.feature.countanalysis.staffreward.StaffRewardAnalysisActivity;
import com.zwx.scan.app.feature.home.SelectStoreAdapter;
import com.zwx.scan.app.feature.main.MainActivity;
import com.zwx.scan.app.feature.verificationrecord.ChartFragment;
import com.zwx.scan.app.widget.CustomMarkerView;
import com.zwx.scan.app.widget.CustomPopWindow;
import com.zwx.scan.app.widget.MoreTextView;
import com.zwx.scan.app.widget.PhilExpandableTextView;
import com.zwx.scan.app.widget.SegmentControl;
import com.zwx.scan.app.widget.SpacesItemDecoration;
import com.zwx.scan.app.widget.indicator.MagicIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.multibindings.ElementsIntoSet;

public class MemberAnalysisActivity extends BaseActivity<MemberContract.Presenter> implements MemberContract.View,View.OnClickListener{
    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;


    @BindView(R.id.segment)
    protected SegmentControl segmentControl;

    @BindView(R.id.tv_new_count)
    protected TextView tvNewCount;

    @BindView(R.id.tv_desc)
    protected TextView tvDesc;

    @BindView(R.id.tv_date)
    protected TextView tvCount;


    @BindView(R.id.ll_chart)
    protected LinearLayout llChart;
    @BindView(R.id.ll_member)
    protected LinearLayout llMember;
    @BindView(R.id.rv_member)
    protected RecyclerView recyclerView;

    @BindView(R.id.pager)
    public CustomViewPager viewPager;


//    public FPagerAdapter pageAdapter;
    public MoreFPagerAdapter pageAdapter;

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
    ;
    @BindView(R.id.rv_checkbox)
    protected RecyclerView rvCheckBox;
    @BindView(R.id.dots)
    protected LinearLayout mDotsLayout;

    @BindView(R.id.ll_select_store)
    protected LinearLayout ll_select_store;

    private View decorView;
    List<MemCoupon> memCouponList = new ArrayList<>();
    List<CompMember> compMemberList = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();

    List<Map<String,Object>> countList = new ArrayList<>();

    private String userId;
    private String storeName ;
    private String storeId;
    private String compId;
    public String date = "day";
   public MemberAnalysisAdapter analysisAdapter;

    protected  MultiAdapter multiAdapter;
    private String storeIdArray ;
    private String storeNameArray ;

    private int brandPostion;
    private String brandLogo;

    private String isShowOneStore;


    private boolean isClicked = true;
    //多个店铺
    private SelectStoreAdapter myAdapter;
    private List<Store> storeList = new ArrayList<>();
    public List<MoreStoreBean.BrandStoreBean> branList = new ArrayList<>();

    protected List<CampaignChartBean> memberCountList = new ArrayList<>();
    protected List<CampaignChartBean> campaignChartBeanList = new ArrayList<>();
    protected List<CampaignChartBean> checkStoreList = new ArrayList<>();
    List<String> storeIdList = new ArrayList<>();
    List<String> storeNameList = new ArrayList<>();
    List<Integer> colors = new ArrayList<>();

    private List<ImageView> mDotsIV = new ArrayList<>();

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_analysis;
    }

    @Override
    protected void initView() {
        DaggerMemberComponent.builder().applicationComponent(AppContext.getApplicationComponent())
                .memberModule(new MemberModule(this))
                .build().inject(this);
        tvTitle.setText("会员统计报表");

        tvDesc.setText("新增会员趋势");
        tvSelect.setText("选择查看店铺");
        storeId = SPUtils.getInstance().getString("storeId");
        compId = SPUtils.getInstance().getString("compId");
        userId  = SPUtils.getInstance().getString("userId");
        date ="day";
        isShowOneStore = getIntent().getStringExtra("isShowOneStore");

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

                presenter.queryMoreStoreMemberCountAnalysis(MemberAnalysisActivity.this,storeId,date,true);

            }
        });

        colors= Arrays.asList( getResources().getColor(R.color.blue),
                getResources().getColor(R.color.orange),
                Color.RED,
                getResources().getColor(R.color.green),
                Color.YELLOW,Color.CYAN
                ,Color.MAGENTA,Color.LTGRAY,Color.DKGRAY
        );

    }


    @Override
    protected void initData() {

        storeName = SPUtils.getInstance().getString("storeName");
        storeIdArray= SPUtils.getInstance().getString("storeIdArray2");
        storeNameArray= SPUtils.getInstance().getString("storeNameArray2");

        brandLogo = SPUtils.getInstance().getString("brandLogo");

        brandPostion = SPUtils.getInstance().getInt("brandPostion2");


        if (!"YES".equals(isShowOneStore)) {
            tvSelect.setVisibility(View.VISIBLE);
            iv_arrow.setVisibility(View.VISIBLE);
        }else {
            tvSelect.setVisibility(View.GONE);
            iv_arrow.setVisibility(View.GONE);
        }
        ll_select_store.setVisibility(View.VISIBLE);
        rvCheckBox.setVisibility(View.VISIBLE);


        if((storeIdArray !=null && !"".equals(storeIdArray))){
            storeId = storeIdArray;
        }

        if(storeNameArray !=null && !"".equals(storeNameArray)){
            storeName = storeNameArray;
        }else {
            storeName = "#"+storeName;
        }

        brandLogo = SPUtils.getInstance().getString("brandLogo");
        setBrandLogo();
        ll_select_store.setVisibility(View.VISIBLE);
        rvCheckBox.setVisibility(View.VISIBLE);

        tvStoreNames.setText(storeName);
//        tvStoreNames.refreshText();

        setPageAdapter();

        CampaignChartBean chartBean = new CampaignChartBean();
        chartBean.setStoreName(storeName);
        chartBean.setStoreId(storeId);
        int color = colors.get(0);
        chartBean.setColor(color);
        chartBean.setChecked(true);
        checkStoreList.add(chartBean);
        setCheckboxAdapter();

        setLisenter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new SpacesItemDecoration(20));
        initRvAdapter();
        presenter.queryMoreStoreMemberCountAnalysis(MemberAnalysisActivity.this,storeId,date,true);


        presenter.queryMoreStoreMemTypeCountList(MemberAnalysisActivity.this,storeId,date,true,false);
    }


    public void setPageAdapter(){

       /* pageAdapter = new FPagerAdapter(getSupportFragmentManager(), countList);
        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(0);*/

        pageAdapter = new MoreFPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(0);
        mDotsLayout.setVisibility(View.GONE);
        setDotLayout();
    }

    protected  void  setLisenter(){

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setMoveDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

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
        for (int i = 0; i < 3; i++) {
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

    public void initRvAdapter(){

        if(compMemberList ==null||compMemberList.size() == 0){
            llMember.setVisibility(View.GONE);

            llChart.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        }else {
            llChart.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            llMember.setVisibility(View.VISIBLE);
        }


        analysisAdapter = new MemberAnalysisAdapter(this, compMemberList, new CommonRecyclerViewHolder.OnItemCommonClickListener() {
            @Override
            public void onItemClickListener(int position) {

                if(compMemberList != null && compMemberList.size() >0){
                    CompMember compMember = compMemberList.get(position);
                    String name =compMember.getMemTypeName();

                    Intent intent =new Intent(MemberAnalysisActivity.this,MemberAnalysisDetailActivity.class);

                    intent.putExtra("title",name);

                    intent.putExtra("storeIdArray",storeId);

                    intent.putExtra("storeNameArray",storeName);

//                    brandLogo = SPUtils.getInstance().getString("brandLogo2");

                    intent.putExtra("brandLogo",brandLogo);
                    intent.putExtra("isShowOneStore",isShowOneStore);

                    intent.putExtra("compTypeId",compMember.getCompMemtypeId());

                    ArrayList<CampaignChartBean> chartBeanArrayList = new  ArrayList<>();
                    chartBeanArrayList.addAll(checkStoreList);
                    intent.putExtra("checkStoreColorList",chartBeanArrayList);
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right,R.anim.slide_out_left);
                }

            }


        });
        recyclerView.setAdapter(analysisAdapter);
    }



    @OnClick({R.id.iv_back,R.id.tv_select_shop,R.id.iv_arrow})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:

                if("NO".equals(isShowOneStore)){

                    ActivityUtils.startActivity(CountAnalysisHomeActivity.class,
                            R.anim.slide_in_left,R.anim.slide_out_right);

                }else {
                    ActivityUtils.startActivity(MainActivity.class,
                            R.anim.slide_in_left,R.anim.slide_out_right);
                }
                ActivityUtils.finishActivity(MemberAnalysisActivity.class);
                break;
            case R.id.tv_select_shop:

                if(storeList !=null && storeList.size()>0){
                    showPopView(this);
                }else {
                    presenter.queryBrandAndStoreList(this,userId);
                }
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

        if("NO".equals(isShowOneStore)){

            ActivityUtils.startActivity(CountAnalysisHomeActivity.class,
                    R.anim.slide_in_left,R.anim.slide_out_right);

        }else {
            ActivityUtils.startActivity(MainActivity.class,
                    R.anim.slide_in_left,R.anim.slide_out_right);
        }
        ActivityUtils.finishActivity(MemberAnalysisActivity.class);

    }

    public   class FPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragmentList;
        private FragmentManager mFragmentManager;
        private Bundle bundle  = new Bundle();
        private String title = "新增数";
        public FPagerAdapter(FragmentManager fm, List<Map<String,Object>> countList) {
            super(fm);
            this.mFragmentManager = fm;
            mFragmentList = new ArrayList<>();
            if(countList == null){
                countList = new ArrayList<>();
            }
            Fragment fragment = ChartFragment.getInstance(title,countList);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",false);
            bundle.putString("date",date);
            bundle.putString("title",title);
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
            setFragments(mFragmentList);
        }

        public void updateData(List<Map<String,Object>> countList) {
            ArrayList<Fragment> fragments = new ArrayList<>();
            if(countList == null){
                countList = new ArrayList<>();
            }
            Fragment fragment = ChartFragment.getInstance(title,countList);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",false);
            bundle.putString("date",date);
            bundle.putString("title",title);

            fragment.setArguments(bundle);
            fragments.add(fragment);
            setFragments(fragments);
        }


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


    private void setBrandLogo(){
        RoundedCorners roundedCorners= new RoundedCorners(8);
        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_image_loading);

        Glide.with(this).load(brandLogo).apply(requestOptions).into(ivBrand);
    }

    public   class MoreFPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragmentList;
        private FragmentManager mFragmentManager;
        private Bundle bundle  = new Bundle();
        private String title = "新增数";
        public MoreFPagerAdapter(FragmentManager fm) {
            super(fm);
            this.mFragmentManager = fm;
            mFragmentList = new ArrayList<>();

            Fragment fragment = CountMoreStoreChartFragment.getInstance(memberCountList);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",false);
            bundle.putString("date",date);
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
            setFragments(mFragmentList);
        }

        public void updateData() {
            ArrayList<Fragment> fragments = new ArrayList<>();

            Fragment fragment = CountMoreStoreChartFragment.getInstance(memberCountList);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",false);
            bundle.putString("date",date);

            fragment.setArguments(bundle);
            fragments.add(fragment);
            setFragments(fragments);
        }


        private void setFragments(ArrayList<Fragment> mFragmentList) {
            if(this.mFragmentList != null){
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                for(Fragment f:this.mFragmentList){
                    fragmentTransaction.remove(f);
                }
                fragmentTransaction.commit();
                mFragmentManager.executePendingTransactions();
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


    protected  void setCheckboxAdapter(){


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
                StringBuilder storeNameSb = new StringBuilder();
                if(!multiAdapter.isSelected.get(position)){
                    multiAdapter.isSelected.put(position, true); // 修改map的值保存状态
                    multiAdapter.notifyItemChanged(position);
//                    campaignChartBeanList.get(position).setChecked(true);
                }else {
                    multiAdapter.isSelected.put(position, false);
                    multiAdapter.notifyItemChanged(position);
                }
                if(multiAdapter.isSelected != null && multiAdapter.isSelected.size()>0){
                    for(int i=0;i<multiAdapter.isSelected.size();i++){
                        boolean selected= multiAdapter.isSelected.get(i);

                        CampaignChartBean chartBean  = campaignChartBeanList.get(i);

                        if(chartBean!=null){
                            if(selected){
                                chartBean.setColor(colors.get(i));
                                chartBean.setChecked(true);
                                storeIdSb.append(chartBean.getStoreId()).append(",");
                            }else {
                                chartBean.setColor(0);
                                chartBean.setChecked(false);
                            }
                        }
                    }
                    String storeIdA= storeIdSb.toString();
                    if(!TextUtils.isEmpty(storeIdA)){
                        storeId = storeIdA.substring(0,storeIdA.length() - 1);
                        presenter.queryMoreStoreMemberCountAnalysis(MemberAnalysisActivity.this,storeId,date,false);
                    }

                }


            }

        });


    }

    private CustomPopWindow popWindow;

    protected void  showPopView(Context context){

        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_select_more_shop,null);
        //创建并显示popWindow
        popWindow= new CustomPopWindow.PopupWindowBuilder(context)
                .setView(contentView)
//                .enableBackgroundDark(true)   //背景是否变暗
//                 .setBgDarkAlpha(0.7f) //调整亮度
//                .enableOutsideTouchableDissmiss(false)
                .setOutsideTouchable(false)
                .size(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create()
                .showAsDropDown(tvSelect,0,2);


        ListView lv_left = contentView.findViewById(R.id.lv_menu);
        ListView lv_right = contentView.findViewById(R.id.lv_home);
        Button reset = contentView.findViewById(R.id.reset);
        Button confirm = contentView.findViewById(R.id.confirm);


        TextView tv_all =contentView.findViewById(R.id.tv_all);

        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(storeList!=null&& storeList.size()>0){
                    if(isClicked){
                        if(storeList!=null&& storeList.size()>0){
                            for (Store store:storeList){
                                if(store!=null){

                                    store.setChecked(false);
                                }

                            }

                            for (Store store:storeList){
                                if(store!=null){

                                    store.setChecked(true);
                                }

                            }
                        }
                        isClicked = false;
                    }else {
                        if(storeList!=null&& storeList.size()>0){
                            for (Store store:storeList){
                                if(store!=null){
                                    store.setChecked(false);
                                }
                            }
                        }
                        isClicked = true;
                    }

                    myAdapter.notifyDataSetChanged();

                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(storeList!=null&& storeList.size()>0){

                    for (Store store:storeList){
                        store.setChecked(false);
                    }
                    myAdapter.notifyDataSetChanged();
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder storeNameSb = new StringBuilder();
                StringBuilder storeNameSb2 = new StringBuilder();
                StringBuilder storeIdSb = new StringBuilder();
                String storeIdArr = "";
                String storeNameArr = "";
                checkStoreList.clear();
                if(storeList!=null&& storeList.size()>0){

                    for (int i=0;i<storeList.size();i++){
                        Store store = storeList.get(i);
                        CampaignChartBean chartBean = new CampaignChartBean();
                        if(store!=null){
                            if(store.isChecked()){
                                String name = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                storeNameSb.append(name);
                                storeIdSb.append(store.getStoreId()).append("-");
                                chartBean.setStoreName(store.getStoreName());
                                chartBean.setStoreId(store.getStoreId());
                                chartBean.setColor(colors.get(i));
                                chartBean.setChecked(true);
                                checkStoreList.add(chartBean);
                            }

                        }
                    }

                    tvStoreNames.setText(storeNameSb.toString());
                    campaignChartBeanList = checkStoreList;
                    String storeIdA= storeIdSb.toString();
                    if(storeIdA!=null &&!"".equals(storeIdA)){
                        storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                    }

                    storeId = storeIdArr;
                    storeName = storeNameSb.toString();
                    if(branList !=null && branList.size()>0){

                        brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                        if(brandLogo!=null){
                            setBrandLogo();
                        }
                        SPUtils.getInstance().put("brandLogo2",brandLogo);
                    }
                    campaignChartBeanList = checkStoreList;
                    setCheckboxAdapter();
                    presenter.queryMoreStoreMemberCountAnalysis(context,storeIdArr,date,true);
                }



                popWindow.dissmiss();

            }
        });


        MoreBrandListAdapter brandListAdapter = new MoreBrandListAdapter(this,branList);


        lv_left.setAdapter(brandListAdapter);

        if(brandPostion == -1){
            brandPostion = 0;
        }
        brandListAdapter.setSelectItem(brandPostion);

        myAdapter = new SelectStoreAdapter(context);
        int selectItem = brandListAdapter.getSelectItem();
        if(branList!=null&&branList.size()>0){
            storeList = branList.get(selectItem).getStoreList();
            brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(selectItem).getBrandLogo();
            SPUtils.getInstance().put("brandLogo2",brandLogo);
            myAdapter.setDatas(storeList);
        }

        lv_right.setAdapter(myAdapter);


        //判断默认选择店铺
        String storeNameStr = tvStoreNames.getText().toString().trim();
        if(storeList!=null&&storeList.size()>0){

            for (int i = 0;i<storeList.size();i++){
                Store store = storeList.get(i);

                if(storeNameStr!=null){

                    if(storeNameStr.contains(store.getStoreName())){
                        store.setChecked(true);
                    }else {
                        store.setChecked(false);
                    }
                }

            }
        }



        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(branList!=null&&branList.size()>0){
                    if(branList.get(position)!=null){
                        brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(position).getBrandLogo();
                        SPUtils.getInstance().put("brandLogo2",brandLogo);
                        setBrandLogo();
                        SPUtils.getInstance().put("brandPosition2",brandPostion);
                        storeList = branList.get(position).getStoreList();
                        brandPostion = position;
                        brandListAdapter.setSelectItem(position);
                        brandListAdapter.notifyDataSetInvalidated();
                        myAdapter = new SelectStoreAdapter(context);
                        myAdapter.setDatas(storeList);
                        lv_right.setAdapter(myAdapter);
//                        myAdapter.setDatas(storeList);
//                        myAdapter.notifyDataSetChanged();
                    }
                }

            }
        });

        myAdapter.notifyDataSetChanged();
        lv_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int currentNum = -1;
                if(currentNum == -1){ //选中
                    if(storeList.get(position).isChecked()){
                        storeList.get(position).setChecked(false);
                    }else {
                        storeList.get(position).setChecked(true);
                    }

                    currentNum = position;
                }else if(currentNum == position){ //同一个item选中变未选中
                    storeList.get(position).setChecked(false);
                    currentNum = -1;
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的

                    if(storeList.get(position).isChecked()){
                        storeList.get(position).setChecked(false);
                    }else {
                        storeList.get(position).setChecked(true);
                    }
                    currentNum = position;
                }

                myAdapter.notifyDataSetChanged();

            }
        });
    }

}
