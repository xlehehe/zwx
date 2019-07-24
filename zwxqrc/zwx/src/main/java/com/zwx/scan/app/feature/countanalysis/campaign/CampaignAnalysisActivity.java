package com.zwx.scan.app.feature.countanalysis.campaign;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.CampaignChartBean;
import com.zwx.scan.app.data.bean.CampaignCount;
import com.zwx.scan.app.data.bean.CampaignCountSecond;
import com.zwx.scan.app.data.bean.CampaignDetail;
import com.zwx.scan.app.data.bean.CampaignTotal;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.countanalysis.CountAnalysisHomeActivity;
import com.zwx.scan.app.feature.countanalysis.MoreBrandListAdapter;
import com.zwx.scan.app.feature.countanalysis.MultiAdapter;
import com.zwx.scan.app.feature.countanalysis.OnItemClickLitener;
import com.zwx.scan.app.feature.countanalysis.member.MemberAnalysisActivity;
import com.zwx.scan.app.feature.home.SelectStoreAdapter;
import com.zwx.scan.app.feature.main.MainActivity;
import com.zwx.scan.app.feature.verificationrecord.ChartFragment;
import com.zwx.scan.app.widget.CustomPopWindow;
import com.zwx.scan.app.widget.MoreTextView;
import com.zwx.scan.app.widget.MyListView;
import com.zwx.scan.app.widget.NiceImageView;
import com.zwx.scan.app.widget.PhilExpandableTextView;
import com.zwx.scan.app.widget.SegmentControl;
import com.zwx.scan.app.widget.indicator.MagicIndicator;
import com.zwx.scan.app.widget.indicator.ScaleCircleNavigator;
import com.zwx.scan.app.widget.indicator.ViewPagerHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class CampaignAnalysisActivity extends BaseActivity<CampaignContract.Presenter> implements CampaignContract.View,View.OnClickListener{

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;


    @BindView(R.id.btn_effect)
    protected Button btnEff;

    @BindView(R.id.btn_uneffect)
    protected Button btnUneff;




    @BindView(R.id.tv_join)
    protected TextView tvJoin;

    @BindView(R.id.tv_get)
    protected TextView tvGet;

    @BindView(R.id.tv_receive)
    protected TextView tvReceive;


    @BindView(R.id.tv_date)
    protected TextView tvDate;

    @BindView(R.id.tv_chart_name)
    protected TextView tvChartName;

    @BindView(R.id.segment)
    protected SegmentControl segmentControl;


    @BindView(R.id.lv_campaign)
    protected MyListView lvCampaign;

    private View decorView;

    @BindView(R.id.pager)
    public CustomViewPager viewPager;


    @BindView(R.id.tv_select_shop)
    protected TextView tvSelect;

    @BindView(R.id.iv_brand)
    protected ImageView ivBrand;

//    @BindView(R.id.tv_store_name)
//    protected MoreTextView tvStoreName;

    @BindView(R.id.tv_store_name)
    protected PhilExpandableTextView tvStoreName;

    @BindView(R.id.iv_arrow)
    protected ImageView iv_arrow;

    public  FPagerAdapter pageAdapter;

    @BindView(R.id.rv_checkbox)
    protected RecyclerView rvCheckBox;
//    @BindView(R.id.indicator)
//    protected MagicIndicator indicator;

    @BindView(R.id.dots)
    protected LinearLayout mDotsLayout;

    @BindView(R.id.ll_select_store)
    protected LinearLayout ll_select_store;
    @BindView(R.id.ll_title)
    protected RelativeLayout ll_title;

    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;

    @BindView(R.id.iv_no_data)
    protected ImageView iv_no_data;

    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;

    protected String title;

    private YAxis leftAxis;             //左侧Y轴
    private YAxis rightAxis;            //右侧Y轴
    private XAxis xAxis;                //X轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线

    private boolean isLegend = true;


    protected CampaignCount campaignCount = new CampaignCount();
    protected List<Map<String,Object>> getCountList = new ArrayList<>();

    protected List<Map<String,Object>> fowardCountList = new ArrayList<>();


    protected List<Map<String,Object>> receiveCountList = new ArrayList<>();

    protected List<CampaignChartBean> getCountList2 = new ArrayList<>();

    protected List<CampaignChartBean> fowardCountList2 = new ArrayList<>();


    protected List<CampaignChartBean> receiveCountList2 = new ArrayList<>();

    protected List<Map<String,Object>> couponCampaignTotalList = new ArrayList<>();

    protected List<Map<String,Object>> campaignTotal = new ArrayList<>();
    private  String date  = "day";
    List<CampaignTotal> campaignTotals = new ArrayList<>();

    private String storeId;

    private String isTitlePager1 = "消费者参与活动人数趋势图";
    private String isTitlePager2 = "消费者优惠券领取数量趋势图";

    private String isTitlePager3 = "消费者优惠券回收数量趋势图";



    public List<MoreStoreBean.BrandStoreBean> branList = new ArrayList<>();

    public List<Store> storeList  = new ArrayList<>();
    private List<CampaignChartBean> campaignChartBeanList  = new ArrayList<>();
    protected List<CampaignChartBean> checkStoreList = new ArrayList<>();

    public String brandLogo;

    public   CampaignPagerAdapter campaignPagerAdapter;

    private MultiAdapter multiAdapter;

    protected SelectStoreAdapter myAdapter;
    protected CampaignAnalysisListViewAdapter campaignAnalysisAdapter = null;
    private String storeName ;
    private int posi = 0;
    private int brandPostion = 0;
    private List<Map<String,Object>> storeIdList =new ArrayList<>();

    private String isShowOneStore;
    String  status = "P";


    private  List<Integer> colors = new ArrayList<>();
    boolean isClicked = true;
    private List<ImageView> mDotsIV = new ArrayList<>();

    private String dateStr = "今日";

    private String reportStr = "消费者参与活动人数";

    protected CampaignCountSecond campaignCountSecond = new CampaignCountSecond();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_campaign_analysis;
    }

    @Override
    protected void initView() {
        tvTitle.setText("活动统计报表");


        DaggerCampaignComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignModule(new CampaignModule(this))
                .build()
                .inject(this);

        colors= Arrays.asList( getResources().getColor(R.color.blue),
                getResources().getColor(R.color.orange),
                Color.RED,
                getResources().getColor(R.color.green),
                Color.YELLOW,Color.CYAN
                ,Color.MAGENTA,Color.LTGRAY,Color.DKGRAY
        );
        storeId = SPUtils.getInstance().getString("storeId");
        storeName = SPUtils.getInstance().getString("storeName");
//        storeIdArray= SPUtils.getInstance().getString("storeIdArray1");
//        storeNameArray= SPUtils.getInstance().getString("storeNameArray1");
        btnEff.setBackground(getResources().getDrawable(R.drawable.ic_btn_blue_selected));
        btnEff.setTextColor(getResources().getColor(R.color.white));
        //一个店铺
        brandLogo = SPUtils.getInstance().getString("brandLogo");
        brandPostion = SPUtils.getInstance().getInt("brandPostion1");

        isShowOneStore = getIntent() .getStringExtra("isShowOneStore");
        if (!"YES".equals(isShowOneStore)) {
            tvSelect.setVisibility(View.VISIBLE);
            iv_arrow.setVisibility(View.VISIBLE);
        }else {
            tvSelect.setVisibility(View.GONE);
            iv_arrow.setVisibility(View.GONE);
        }


        if(storeName !=null &&!"".equals(storeName)){
            storeName = "#"+storeName;

        }
        ll_select_store.setVisibility(View.VISIBLE);
        rvCheckBox.setVisibility(View.VISIBLE);

        setBrandLogo();

        tvStoreName.setText(storeName);
        segmentControl.setSelectedIndex(0);
        segmentControl.setOnSegmentChangedListener(new SegmentControl.OnSegmentChangedListener() {
            @Override
            public void onSegmentChanged(int newSelectedIndex) {
                 if(newSelectedIndex == 0) {
                    date = "day";
                     dateStr = "今日";
                    isLegend = false;

                }else if(newSelectedIndex == 1) {
                    isLegend = false;
                    date = "week";
                     dateStr = "本周";
                }if(newSelectedIndex == 2){
                    date = "month";
                    isLegend =true;
                    dateStr = "本月";
                }

                tvChartName.setText(dateStr+reportStr+"趋势图");
                presenter.queryMoreStoreCampaignCountanalysis(CampaignAnalysisActivity.this,storeId,date,true);

            }
        });


        tvChartName.setText("今日消费者参与活动人数趋势图");

        CampaignChartBean chartBean = new CampaignChartBean();
        chartBean.setStoreName(storeName);
        chartBean.setStoreId(storeId);
        int color = colors.get(0);
        chartBean.setColor(color);
        chartBean.setChecked(true);
        checkStoreList.add(chartBean);

        setCheckboxAdapter();
        setPageAdapter();
        setLisenter();


    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.queryMoreStoreCampaignCountanalysis(CampaignAnalysisActivity.this,storeId,date,true);


        presenter.queryMoreSpecificCampaignCountList(CampaignAnalysisActivity.this,storeId,status,date,true,true);
    }

    private void setLisenter(){
        lvCampaign.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(campaignTotals != null && campaignTotals.size()>0){
                    CampaignTotal compMember = campaignTotals.get(position);
                    String campaignId =String.valueOf(compMember.getCampaignId());
                    String campaignName = compMember.getCampaignName();
                    Intent intent =new Intent(CampaignAnalysisActivity.this,CampaignAnalysisDetailActivity.class);

//                intent.putExtra("title",name);
                    intent.putExtra("campaignType",compMember.getCampaignType());
                    intent.putExtra("campaignId",campaignId);
                    intent.putExtra("campaignName",campaignName);
                    intent.putExtra("storeIdArray",storeId);
                    intent.putExtra("isShowOneStore",isShowOneStore);
                    intent.putExtra("storeNameArray",storeName);
                    intent.putExtra("brandLogo",brandLogo);
                    ArrayList<CampaignChartBean> chartBeanArrayList = new  ArrayList<>();
                    chartBeanArrayList.addAll(checkStoreList);
                    intent.putExtra("checkStoreColorList",chartBeanArrayList);
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right,R.anim.slide_out_left);
                }
            }
        });
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
    protected  void setCheckboxAdapter(){

        if(posi == 0){
            campaignChartBeanList = fowardCountList2;
        }else if(posi == 1){
            campaignChartBeanList = getCountList2;
        }else if(posi == 2){
            campaignChartBeanList = receiveCountList2;
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
                StringBuilder storeNameSb = new StringBuilder();
                if(!multiAdapter.isSelected.get(position)){
                    multiAdapter.isSelected.put(position, true); // 修改map的值保存状态
                    multiAdapter.notifyItemChanged(position);
                }else {
                    multiAdapter.isSelected.put(position, false); // 修改map的值保存状态
                    multiAdapter.notifyItemChanged(position);
//                    campaignChartBeanList.get(position).setChecked(false);
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
                    if(storeIdA != null && !"".equals(storeIdA)){
                        storeId = storeIdA.substring(0,storeIdA.length() - 1);
                        presenter.queryMoreStoreCampaignCountanalysis(CampaignAnalysisActivity.this,storeId,date,false);
                    }

                }


            }


        });
    }


    @Override
    protected void initData() {
//        rvCampaign.setLayoutManager(new LinearLayoutManager(this));
//        rvCampaign.setNestedScrollingEnabled(false);
//        rvCampaign.addItemDecoration(new SpacesItemDecoration(30));

    }

    protected  void setRvAdapter(){
        campaignAnalysisAdapter = new CampaignAnalysisListViewAdapter(CampaignAnalysisActivity.this,campaignTotals);
        lvCampaign.setAdapter(campaignAnalysisAdapter);
    }


    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildPosition(view) == 0)
                outRect.top = space;
        }
    }

    @OnClick({R.id.iv_back,R.id.btn_effect,R.id.btn_uneffect,R.id.tv_select_shop,R.id.iv_arrow})
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
                ActivityUtils.finishActivity(CampaignAnalysisActivity.class);
                break;
            case R.id.btn_effect:
                btnEff.setBackground(getResources().getDrawable(R.drawable.ic_btn_blue_selected));
                btnEff.setTextColor(getResources().getColor(R.color.white));
                btnUneff.setBackground(getResources().getDrawable(R.drawable.ic_btn_gray_unselect));
                btnUneff.setTextColor(getResources().getColor(R.color.color_gray_deep));
                status = "P";
                campaignTotals.clear();
                presenter.queryMoreSpecificCampaignCountList(this,storeId,"P",date,true,false);
                break;

            case R.id.btn_uneffect:

                btnUneff.setBackground(getResources().getDrawable(R.drawable.ic_btn_blue_selected));
                btnUneff.setTextColor(getResources().getColor(R.color.white));
                btnEff.setBackground(getResources().getDrawable(R.drawable.ic_btn_gray_unselect));
                btnEff.setTextColor(getResources().getColor(R.color.color_gray_deep));
                status = "ECI";
                campaignTotals.clear();
                presenter.queryMoreSpecificCampaignCountList(this,storeId,"ECI",date,true,false);
                break;
            case R.id.tv_select_shop:
                String userId = SPUtils.getInstance().getString("userId");

                if(storeList!=null && storeList.size()>0){
                    showPopView(this);
                }else {
                    presenter.queryBrandAndStoreList(CampaignAnalysisActivity.this,userId);
                }
                break;
            case R.id.iv_arrow:

                boolean b=tvStoreName.getExpandableStatus();
                b=!b;
                if(b){
                    iv_arrow.setImageResource(R.drawable.ic_arrow_up);
                }else {
                    iv_arrow.setImageResource(R.drawable.ic_arrow_down);
                }

                tvStoreName.setExpandable(b);


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
        ActivityUtils.finishActivity(CampaignAnalysisActivity.class);

    }


    public void setPageAdapter(){

        campaignPagerAdapter = new CampaignPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(campaignPagerAdapter);
        viewPager.setCurrentItem(0);



        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {


            }

            @Override
            public void onPageSelected(int i) {
                //默认消费者参与活动人数
                posi = i;

                setMoveDot(i);

                if(i == 0) {
                    isLegend = false;
                    reportStr = "消费者参与活动人数";
                }else if(i == 1) {
                    isLegend = false;
                    reportStr = "消费者优惠券领取数量";
                }if(i == 2){
                    isLegend =true;
                    reportStr = "消费者优惠券回收数量";
                }
                tvChartName.setText(dateStr+reportStr+"趋势图");

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


    public   class FPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragmentList;
        private FragmentManager mFragmentManager;
        private Bundle bundle  = new Bundle();
        public FPagerAdapter(FragmentManager fm, CampaignDetail campaignDetail) {
            super(fm);
            this.mFragmentManager = fm;
            mFragmentList = new ArrayList<>();
            if(campaignDetail == null){
                getCountList = new ArrayList<>();
                fowardCountList = new ArrayList<>();
                receiveCountList = new ArrayList<>();
            }else{
                getCountList = campaignDetail.getGetList();
                fowardCountList = campaignDetail.getFowardList();
                receiveCountList = campaignDetail.getReceiveList();
            }
            Fragment fragment1 = ChartFragment.getInstance(isTitlePager1,fowardCountList);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",true);
            bundle.putString("date",date);
            bundle.putString("title",isTitlePager1);
            fragment1.setArguments(bundle);
            Fragment fragment2=ChartFragment.getInstance(isTitlePager2,getCountList);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",true);
            bundle.putString("date",date);
            bundle.putString("title",isTitlePager2);
            fragment2.setArguments(bundle);

            Fragment fragment3=ChartFragment.getInstance(isTitlePager3,receiveCountList);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",true);
            bundle.putString("date",date);
            bundle.putString("title",isTitlePager3);
            fragment3.setArguments(bundle);
            mFragmentList.add(fragment1);
            mFragmentList.add(fragment2);
            mFragmentList.add(fragment3);
            setFragments(mFragmentList);
        }
        public void updateData(CampaignCount campaignDetail) {
            ArrayList<Fragment> fragments = new ArrayList<>();
            if(campaignDetail == null){
                getCountList = new ArrayList<>();
                fowardCountList = new ArrayList<>();
                receiveCountList = new ArrayList<>();
            }else{
                getCountList = campaignDetail.getGetCountList();
                fowardCountList = campaignDetail.getFowardCountList();
                receiveCountList = campaignDetail.getReceiveCountList();
            }
            Fragment fragment1 =ChartFragment.getInstance(isTitlePager1,fowardCountList);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",true);
            bundle.putString("date",date);
            bundle.putString("title",isTitlePager1);
            fragment1.setArguments(bundle);
            Fragment fragment2=ChartFragment.getInstance(isTitlePager2,getCountList);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",true);
            bundle.putString("date",date);
            bundle.putString("title",isTitlePager2);
            fragment2.setArguments(bundle);

            Fragment fragment3=ChartFragment.getInstance(isTitlePager3,receiveCountList);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",true);
            bundle.putString("date",date);
            bundle.putString("title",isTitlePager3);
            fragment3.setArguments(bundle);
            fragments.add(fragment1);
            fragments.add(fragment2);
            fragments.add(fragment3);
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


    public  class CampaignPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragmentList;
        private FragmentManager mFragmentManager;
        private Bundle bundle  = new Bundle();
        public CampaignPagerAdapter(FragmentManager fm) {
            super(fm);
            this.mFragmentManager = fm;
            mFragmentList = new ArrayList<>();
            /*if(campaignDetail == null){
                getCountList2 = new ArrayList<>();
                fowardCountList2 = new ArrayList<>();
                receiveCountList2 = new ArrayList<>();
            }else{
                getCountList2 = campaignDetail.getGetCountList();
                fowardCountList2 = campaignDetail.getFowardCountList();
                receiveCountList2 = campaignDetail.getReceiveCountList();
            }*/

            Fragment fragment1 = CountMoreStoreChartFragment.getInstance(fowardCountList2);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",true);
            bundle.putString("date",date);
            fragment1.setArguments(bundle);
            Fragment fragment2=CountMoreStoreChartFragment.getInstance(getCountList2);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",true);
            bundle.putString("date",date);
            fragment2.setArguments(bundle);

            Fragment fragment3=CountMoreStoreChartFragment.getInstance(receiveCountList2);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",true);
            bundle.putString("date",date);
            fragment3.setArguments(bundle);
            mFragmentList.add(fragment1);
            mFragmentList.add(fragment2);
            mFragmentList.add(fragment3);
            setFragments(mFragmentList);
        }
        public void updateData() {
            ArrayList<Fragment> fragments = new ArrayList<>();
         /*   if(campaignDetail == null){
                getCountList2 = new ArrayList<>();
                fowardCountList2 = new ArrayList<>();
                receiveCountList2 = new ArrayList<>();
            }else{
                getCountList2 = campaignDetail.getGetCountList();
                fowardCountList2 = campaignDetail.getFowardCountList();
                receiveCountList2 = campaignDetail.getReceiveCountList();
            }*/
            Fragment fragment1 =CountMoreStoreChartFragment.getInstance(fowardCountList2);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",true);
            bundle.putString("date",date);
            bundle.putString("title",isTitlePager1);
            fragment1.setArguments(bundle);
            Fragment fragment2=CountMoreStoreChartFragment.getInstance(getCountList2);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",true);
            bundle.putString("date",date);
            fragment2.setArguments(bundle);

            Fragment fragment3=CountMoreStoreChartFragment.getInstance(receiveCountList2);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",true);
            bundle.putString("date",date);
            fragment3.setArguments(bundle);
            fragments.add(fragment1);
            fragments.add(fragment2);
            fragments.add(fragment3);
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
                .showAsDropDown(ll_title,0,2);


        ListView lv_left = contentView.findViewById(R.id.lv_menu);
        ListView lv_right = contentView.findViewById(R.id.lv_home);
        Button reset = contentView.findViewById(R.id.reset);
        Button confirm = contentView.findViewById(R.id.confirm);

        TextView tv_all =contentView.findViewById(R.id.tv_all);



        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                                int color = colors.get(i);
                                chartBean.setColor(color);
                                chartBean.setChecked(true);
                                checkStoreList.add(chartBean);
                            }

                        }
                    }
                    tvStoreName.setText(storeNameSb.toString());
                }


                String storeIdA= storeIdSb.toString();
                if(storeIdA!=null&& !"".equals(storeIdA)){
                    storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                    String storeNameA = storeNameSb.toString();
                    storeId = storeIdArr;
                    storeName = storeNameSb.toString();
//                    SPUtils.getInstance().put("storeIdArray1",storeIdArr);
//                    SPUtils.getInstance().put("storeNameArray1",storeNameSb.toString());

                    if(branList !=null && branList.size()>0){

                        brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                        if(brandLogo!=null){
                            setBrandLogo();
                        }
                        SPUtils.getInstance().put("brandLogo1",brandLogo);
                    }

                    if(posi == 0){
                        fowardCountList2 = checkStoreList;
                    }else if(posi ==1){
                        getCountList2 = checkStoreList;
                    }else if(posi ==2){
                        receiveCountList2 = checkStoreList;
                    }
                    setCheckboxAdapter();
                    presenter.queryMoreStoreCampaignCountanalysis(CampaignAnalysisActivity.this,storeId,date,false);
                    popWindow.dissmiss();
                }else {
                    ToastUtils.showShort("请选择店铺");
                    return;
                }




            }
        });




        MoreBrandListAdapter brandListAdapter = new MoreBrandListAdapter(this,branList);
        myAdapter = new SelectStoreAdapter(context);
        if(brandPostion == -1){
            brandPostion = 0;
        }
        brandListAdapter.setSelectItem(brandPostion);

        lv_left.setAdapter(brandListAdapter);
        int selectItem = brandListAdapter.getSelectItem();
        if(branList!=null&&branList.size()>0){
            storeList = branList.get(selectItem).getStoreList();
            brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(selectItem).getBrandLogo();
            SPUtils.getInstance().put("brandLogo1",brandLogo);
            setBrandLogo();
            myAdapter.setDatas(storeList);
        }


        lv_right.setAdapter(myAdapter);

        //判断默认选择店铺
        String storeNameStr = tvStoreName.getText().toString().trim();
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
                        RequestOptions requestOptions = new RequestOptions()
                                .placeholder(R.drawable.ic_banner_default)
                                .error(R.drawable.ic_banner_default)
                                .fallback(R.drawable.ic_banner_default);
                        SPUtils.getInstance().put("brandLogo1",brandLogo);
                        SPUtils.getInstance().put("brandPostion1",position);
                        Glide.with(context).load(brandLogo).apply(requestOptions).into(ivBrand);

                        storeList = branList.get(position).getStoreList();
                        brandPostion = position;
                        brandListAdapter.setSelectItem(position);
                        brandListAdapter.notifyDataSetInvalidated();
                        myAdapter = new SelectStoreAdapter(context);
                        myAdapter.setDatas(storeList);
                        lv_right.setAdapter(myAdapter);
                    }
                }

            }
        });
/*
        String storeName = tvStoreName.getTextView().getText().toString().trim();
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
//            int currentNum = -1;
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
                   /* for(Store store : storeList){
                        store.setChecked(false);
                    }*/
                    storeList.get(position).setChecked(false);
                    currentNum = -1;
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的
                  /*  for(Store store : storeList){
                        store.setChecked(false);
                    }*/
//                    storeList.get(position).setChecked(true);

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
