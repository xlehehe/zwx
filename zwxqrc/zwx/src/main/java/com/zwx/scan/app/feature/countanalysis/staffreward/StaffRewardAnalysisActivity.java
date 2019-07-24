package com.zwx.scan.app.feature.countanalysis.staffreward;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.CampaignChartBean;
import com.zwx.scan.app.data.bean.CampaignCountSecond;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.StaffCount;
import com.zwx.scan.app.data.bean.StaffCountBean;
import com.zwx.scan.app.data.bean.StaffCountSecond;
import com.zwx.scan.app.data.bean.StaffReward;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.countanalysis.CountAnalysisHomeActivity;
import com.zwx.scan.app.feature.countanalysis.MoreBrandListAdapter;
import com.zwx.scan.app.feature.countanalysis.MultiAdapter;
import com.zwx.scan.app.feature.countanalysis.OnItemClickLitener;
import com.zwx.scan.app.feature.countanalysis.campaign.CampaignAnalysisActivity;
import com.zwx.scan.app.feature.countanalysis.campaign.CountMoreStoreChartFragment;
import com.zwx.scan.app.feature.countanalysis.member.MemberAnalysisActivity;
import com.zwx.scan.app.feature.countanalysis.member.MemberAnalysisDetailActivity;
import com.zwx.scan.app.feature.home.SelectStoreAdapter;
import com.zwx.scan.app.feature.main.MainActivity;
import com.zwx.scan.app.feature.verificationrecord.ChartFragment;
import com.zwx.scan.app.widget.CustomMarkerView;
import com.zwx.scan.app.widget.CustomPopWindow;
import com.zwx.scan.app.widget.MoreTextView;
import com.zwx.scan.app.widget.PhilExpandableTextView;
import com.zwx.scan.app.widget.SegmentControl;
import com.zwx.scan.app.widget.indicator.MagicIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class StaffRewardAnalysisActivity extends BaseActivity<StaffContract.Presenter> implements StaffContract.View,View.OnClickListener{


    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;


    @BindView(R.id.segment)
    protected SegmentControl segmentControl;

//    @BindView(R.id.tv_desc)
//    protected TextView tvCountTile;


//    @BindView(R.id.tv_date)
//    protected TextView tvDate;

    @BindView(R.id.tv_legend)
    protected TextView tvLegend;

    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;

    @BindView(R.id.iv_no_data)
    protected ImageView iv_no_data;

    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;

  /*  @BindView(R.id.chart)
    protected BarChart chart;*/

    private YAxis leftAxis;             //左侧Y轴
    private YAxis rightAxis;            //右侧Y轴
    private XAxis xAxis;                //X轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线

    @BindView(R.id.pager)
    public CustomViewPager viewPager;
//    public FPagerAdapter pageAdapter;

    @BindView(R.id.rv_member)
    protected RecyclerView recyclerView;


    public MoreFPagerAdapter pageAdapter;

    @BindView(R.id.tv_select_shop)
    protected TextView tvSelect;

    @BindView(R.id.iv_brand)
    protected ImageView ivBrand;

    @BindView(R.id.tv_store_name)
    protected PhilExpandableTextView tvStoreNames;

    @BindView(R.id.iv_arrow)
    protected ImageView iv_arrow;


    @BindView(R.id.rv_checkbox)
    protected RecyclerView rvCheckBox;
    @BindView(R.id.dots)
    protected LinearLayout mDotsLayout;

    @BindView(R.id.ll_select_store)
    protected LinearLayout ll_select_store;

    @BindView(R.id.tv_staff_paimin_label)
    protected TextView tv_staff_paimin_label;

    @BindView(R.id.btn_effect)
    protected Button btnEff;

    @BindView(R.id.btn_uneffect)
    protected Button btnUneff;


    @BindView(R.id.tv_desc)
    protected TextView tv_desc;

    @BindView(R.id.tv_date)
    protected TextView tv_date;

    List<StaffReward> staffRewards = new ArrayList<>();
    List<Map<String,Object>> staffCounts = new ArrayList<>();

    private String date = "day";
    public StaffAnalysisAdapter analysisAdapter;


    protected MultiAdapter multiAdapter;
    private String storeIdArray ;
    private String storeNameArray ;

    private int brandPostion;
    private String brandLogo;
    private String storeId;
    private String userId;
    private  List<Integer> colors = new ArrayList<>();
    //多个店铺
    private SelectStoreAdapter myAdapter;
    private List<Store> storeList = new ArrayList<>();
    public List<MoreStoreBean.BrandStoreBean> branList = new ArrayList<>();

    protected List<CampaignChartBean> campaignChartBeanList = new ArrayList<>();
    private List<Map<String,Object>> storeIdList =new ArrayList<>();
    //参与书
    protected List<CampaignChartBean> staffJoinList = new ArrayList<>();
    //拉新成功数
    protected List<CampaignChartBean> staffSuccessList = new ArrayList<>();
    protected List<CampaignChartBean> checkStoreList = new ArrayList<>();
    private  String isShowOneStore;

    private boolean isClicked = true;

    private  String paiMingLabel = "";

    private List<ImageView> mDotsIV = new ArrayList<>();

    protected StaffPagerAdapter staffPagerAdapter = null;

    private String isTitlePager = "拉新参与数";

    private  int positionPager = 0;
    private boolean isLegend = true;
    protected  String joinCount = "—";
    protected  String successCount = "—";
    protected   String rewardType = "P";
    protected  String selectPFlag = "A";
    protected String storeName;
    protected StaffCountBean staffCount = new StaffCountBean();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_staff_reward_analysis;
    }



    @Override
    protected void initView() {
        tvTitle.setText("员工拉新统计报表");
        tvSelect.setText("选择查看店铺");
        tvStoreNames.setText("");
        DaggerStaffComponent.builder().applicationComponent(AppContext.getApplicationComponent())
                .staffModule(new StaffModule(this))
                .build().inject(this);
        colors= Arrays.asList( getResources().getColor(R.color.blue),
                getResources().getColor(R.color.orange),
                Color.RED,
                getResources().getColor(R.color.green),
                Color.YELLOW,Color.CYAN
                ,Color.MAGENTA,Color.LTGRAY,Color.DKGRAY
        );

//        tvCountTile.setText("拉新任务趋势图");
        tvLegend.setText("任务数");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.ic_line_horizontal));
//        RecycleViewDivider divider = new RecycleViewDivider(StaffRewardAnalysisActivity.this,LinearLayoutManager.HORIZONTAL,getResources().getDrawable(R.drawable.ic_line_horizontal));
        recyclerView.addItemDecoration(decoration);

        analysisAdapter = new StaffAnalysisAdapter(this, staffRewards);
        recyclerView.setAdapter(analysisAdapter);

        userId = SPUtils.getInstance().getString("userId");
        storeId = SPUtils.getInstance().getString("storeId");
        storeName = SPUtils.getInstance().getString("storeName");
        storeIdArray= SPUtils.getInstance().getString("storeIdArray3");
        storeNameArray= SPUtils.getInstance().getString("storeNameArray3");

        brandLogo = SPUtils.getInstance().getString("brandLogo");
        brandPostion = SPUtils.getInstance().getInt("brandPostion3");
        date = "day";
        isShowOneStore = getIntent() .getStringExtra("isShowOneStore");

        if (!"YES".equals(isShowOneStore)) {
            tvSelect.setVisibility(View.VISIBLE);
            iv_arrow.setVisibility(View.VISIBLE);
        }else {
            tvSelect.setVisibility(View.GONE);
            iv_arrow.setVisibility(View.GONE);
        }
        if((storeIdArray !=null &&storeIdArray.length()>0)){
            storeId = storeIdArray;
        }

        if(storeNameArray !=null &&storeNameArray.length()>0){
            storeName = storeNameArray;

        }else {
            storeName = "#"+storeName;
        }
        ll_select_store.setVisibility(View.VISIBLE);
        rvCheckBox.setVisibility(View.VISIBLE);
        setBrandLogo();
        if(brandPostion == -1){
            brandPostion =0;
        }

        tvStoreNames.setText(storeName);


        tv_desc.setText("拉新参与数");
        tv_date.setText("拉新参与总数： —");
        paiMingLabel = "<font size = '16'color = \'#0486FE\' weight = 'bolder'>今日</font>"+"店铺员工完成拉新任务排名";
        tv_staff_paimin_label.setText(Html.fromHtml(paiMingLabel));
        segmentControl.setSelectedIndex(0);
        segmentControl.setOnSegmentChangedListener(new SegmentControl.OnSegmentChangedListener() {
            @Override
            public void onSegmentChanged(int newSelectedIndex) {
                if(newSelectedIndex == 0) {
                    date = "day";
                    paiMingLabel = "<font size = '16'color = \'#0486FE\' weight = 'bolder'>今日</font>"+"店铺员工完成拉新任务排名";
                }else if(newSelectedIndex == 1) {
                    date = "week";
                    paiMingLabel = "<font size = '16'color = \'#0486FE\' weight = 'bolder'>本周</font>"+"店铺员工完成拉新任务排名";
                }if(newSelectedIndex == 2){
                    date = "month";
                    paiMingLabel = "<font size = '16'color = \'#0486FE\' weight = 'bolder'>本月</font>"+"店铺员工完成拉新任务排名";
                }
                tv_staff_paimin_label.setText(Html.fromHtml(paiMingLabel));
                presenter.queryMoreStoreStaffRewardTotalList(StaffRewardAnalysisActivity.this,userId,storeId,date,false);

            }
        });

        CampaignChartBean chartBean = new CampaignChartBean();
        chartBean.setStoreName(storeName);
        chartBean.setStoreId(storeId);
        int color = colors.get(0);
        chartBean.setColor(color);
        chartBean.setChecked(true);
        checkStoreList.add(chartBean);
        setCheckboxAdapter();
        setPageAdapter();

        presenter.queryMoreStoreStaffRewardTotalList(StaffRewardAnalysisActivity.this,userId,storeId,date,true);



    }

    @Override
    protected void initData() {

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

    @OnClick({R.id.iv_back,R.id.tv_select_shop,R.id.btn_effect,R.id.btn_uneffect,R.id.iv_arrow})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                if("NO".equals(isShowOneStore)){
//                    ActivityUtils.finishActivity(StaffRewardAnalysisActivity.class,
//                            R.anim.slide_in_left,R.anim.slide_out_right);

                    ActivityUtils.startActivity(CountAnalysisHomeActivity.class,
                            R.anim.slide_in_left,R.anim.slide_out_right);

                }else {
                    ActivityUtils.startActivity(MainActivity.class,
                            R.anim.slide_in_left,R.anim.slide_out_right);
                }
                ActivityUtils.finishActivity(StaffRewardAnalysisActivity.class);
                break;
            case R.id.btn_effect:
                btnEff.setBackground(getResources().getDrawable(R.drawable.ic_btn_blue_selected));
                btnEff.setTextColor(getResources().getColor(R.color.white));
                btnUneff.setBackground(getResources().getDrawable(R.drawable.ic_btn_gray_unselect));
                btnUneff.setTextColor(getResources().getColor(R.color.color_gray_deep));
                rewardType = "P";
                presenter.queryMoreStoreStaffRewardRankTotalList(this,storeId,date,rewardType);
                break;

            case R.id.btn_uneffect:
                rewardType = "PS";
                btnUneff.setBackground(getResources().getDrawable(R.drawable.ic_btn_blue_selected));
                btnUneff.setTextColor(getResources().getColor(R.color.white));
                btnEff.setBackground(getResources().getDrawable(R.drawable.ic_btn_gray_unselect));
                btnEff.setTextColor(getResources().getColor(R.color.color_gray_deep));
//                status = "ECI";
                presenter.queryMoreStoreStaffRewardRankTotalList(this,storeId,date,rewardType);
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
//        super.onBackPressed();
//        ActivityUtils.finishActivity(StaffRewardAnalysisActivity.class,
//                R.anim.slide_in_left,R.anim.slide_out_right);
//        ActivityUtils.startActivity(MainActivity.class,
//                R.anim.slide_in_left,R.anim.slide_out_right);
//
//        ActivityUtils.finishActivity(StaffRewardAnalysisActivity.class);

        if("NO".equals(isShowOneStore)){

            ActivityUtils.startActivity(CountAnalysisHomeActivity.class,
                    R.anim.slide_in_left,R.anim.slide_out_right);

        }else {
            ActivityUtils.startActivity(MainActivity.class,
                    R.anim.slide_in_left,R.anim.slide_out_right);
        }
        ActivityUtils.finishActivity(StaffRewardAnalysisActivity.class);
    }

    public void setPageAdapter(){


        staffPagerAdapter = new StaffPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(staffPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {


            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    isTitlePager = "拉新参与数";
                    if(joinCount != null && !"0".equals(joinCount)){
                        tv_date.setText("拉新参与总数："+joinCount);
                    }else {
                        tv_date.setText("拉新参与总数： —");
                    }

                    selectPFlag = "A";
                } else if (i == 1) {
                    isTitlePager = "拉新成功数";
                    if(successCount != null && !"0".equals(successCount)){
                        tv_date.setText("拉新成功总数："+successCount);
                    }else {
                        tv_date.setText("拉新成功总数： —");
                    }
                    selectPFlag = "B";
                }
                positionPager = i;

                setMoveDot(i);

                tv_desc.setText(isTitlePager);

            }

            @Override
            public void onPageScrollStateChanged(int i) {


            }
        });

        setDotLayout();

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
        for (int i = 0; i < 2; i++) {
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



    public  class StaffPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragmentList;
        private FragmentManager mFragmentManager;
        private Bundle bundle  = new Bundle();
        public StaffPagerAdapter(FragmentManager fm) {
            super(fm);
            this.mFragmentManager = fm;
            mFragmentList = new ArrayList<>();
          /*  if(staffCountBean == null){
                staffJoinList = new ArrayList<>();
                staffSuccessList = new ArrayList<>();
            }else{
                staffJoinList = staffCountBean.getpStaffReport();
                staffSuccessList = staffCountBean.getPsStaffReport();
            }*/
            Fragment fragment1 = CountMoreStoreChartFragment.getInstance(staffJoinList);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",true);
            bundle.putString("date",date);
            fragment1.setArguments(bundle);
            Fragment fragment2=CountMoreStoreChartFragment.getInstance(staffSuccessList);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",true);
            bundle.putString("date",date);
            fragment2.setArguments(bundle);

            mFragmentList.add(fragment1);
            mFragmentList.add(fragment2);
            setFragments(mFragmentList);
        }
        public void updateData() {
            ArrayList<Fragment> fragments = new ArrayList<>();
          /*  if(staffCountBean == null){
                staffJoinList = new ArrayList<>();
                staffSuccessList = new ArrayList<>();
            }else{
                staffJoinList = staffCountBean.getpStaffReport();
                staffSuccessList = staffCountBean.getPsStaffReport();
            }*/
            Fragment fragment1 = CountMoreStoreChartFragment.getInstance(staffJoinList);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",true);
            bundle.putString("date",date);
            fragment1.setArguments(bundle);
            Fragment fragment2=CountMoreStoreChartFragment.getInstance(staffSuccessList);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",true);
            bundle.putString("date",date);
            fragment2.setArguments(bundle);

            fragments.add(fragment1);
            fragments.add(fragment2);
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

    public   class MoreFPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragmentList;
        private FragmentManager mFragmentManager;
        private Bundle bundle  = new Bundle();
        public MoreFPagerAdapter(FragmentManager fm, List<CampaignChartBean> memberCountList) {
            super(fm);
            this.mFragmentManager = fm;
            mFragmentList = new ArrayList<>();
            if(memberCountList == null){
                memberCountList = new ArrayList<>();
            }
            Fragment fragment = CountMoreStoreChartFragment.getInstance(memberCountList);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",false);
            bundle.putString("date",date);
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
            setFragments(mFragmentList);
        }

        public void updateData(List<CampaignChartBean> memberCountList) {
            ArrayList<Fragment> fragments = new ArrayList<>();
            if(memberCountList == null){
                memberCountList = new ArrayList<>();
            }
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
//                fragmentTransaction.commit();
                fragmentTransaction.commitAllowingStateLoss();
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
        if(positionPager == 0){
            campaignChartBeanList = staffJoinList;
        }else if(positionPager == 1){
            campaignChartBeanList = staffSuccessList;
        }
        if(campaignChartBeanList!=null && campaignChartBeanList.size()>0){
            for(CampaignChartBean chartBean:campaignChartBeanList){
                Map<String,Object> map = new HashMap<>();

                map.put("storeId",chartBean.getStoreId());
                map.put("storeName",chartBean.getStoreName());
                storeIdList.add(map);

            }
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
                    if(storeIdA!= null && !"".equals(storeIdA)){
                        storeId = storeIdA.substring(0,storeIdA.length() - 1);
                        presenter.queryMoreStoreStaffRewardTotalList(StaffRewardAnalysisActivity.this,userId,storeId,date,false);
                    }

                }

                /*if(storeList!=null && storeList.size()>0) {
                    for (Store store:storeList) {
                        store.setChecked(false);
                    }
                    for (Store store:storeList) {
                        String storeId2 = store.getStoreId();
                        CampaignChartBean chartBean  = campaignChartBeanList.get(position);

                        String storeId = chartBean.getStoreId();
                        if(storeId2.equals(storeId)){
                            store.setChecked(true);
                        }
                    }
                }
                myAdapter.notifyDataSetChanged();*/

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

                }
                String storeIdA= storeIdSb.toString();
                if(storeIdA != null && !"".equals(storeIdA)){
                    storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                }
                storeId = storeIdArr;
                //去掉缓存，再次进入显示与首页店铺选项保持一致
//                SPUtils.getInstance().put("storeIdArray3",storeIdArr);
//                SPUtils.getInstance().put("storeNameArray3",storeNameArr);
                if(branList !=null && branList.size()>0){

                    brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                    if(brandLogo!=null){
                        setBrandLogo();
                    }
                    SPUtils.getInstance().put("brandLogo3",brandLogo);
                }
                campaignChartBeanList = checkStoreList;
                setCheckboxAdapter();
                presenter.queryMoreStoreStaffRewardTotalList(context,userId,storeIdArr,date,true);

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
            SPUtils.getInstance().put("brandLogo3",brandLogo);
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

                        RequestOptions requestOptions = new RequestOptions()
                                .placeholder(R.drawable.ic_banner_default)
                                .error(R.drawable.ic_banner_default)
                                .fallback(R.drawable.ic_banner_default);

                        Glide.with(context).load(brandLogo).apply(requestOptions).into(ivBrand);
                        brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(position).getBrandLogo();
                        SPUtils.getInstance().put("brandLogo3",brandLogo);
                        SPUtils.getInstance().put("brandPostion3",brandPostion);
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

      /*  String storeName = tvStoreNames.getTextView().getText().toString().trim();
        //判断默认选择的店铺为true;否则false
        for(Store store : storeList){
            if(storeName.contains(store.getStoreName())){
                store.setChecked(true);
            }else {
                store.setChecked(false);
            }

        }*/
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
