package com.zwx.scan.app.feature.countanalysis.member;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.expandablelayout.ExpandableLinearLayout;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.CampaignChartBean;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.countanalysis.MoreBrandListAdapter;
import com.zwx.scan.app.feature.countanalysis.MultiAdapter;
import com.zwx.scan.app.feature.countanalysis.OnItemClickLitener;
import com.zwx.scan.app.feature.countanalysis.campaign.CountMoreStoreChartFragment;
import com.zwx.scan.app.feature.home.SelectStoreAdapter;
import com.zwx.scan.app.feature.verificationrecord.ChartFragment;
import com.zwx.scan.app.widget.CustomPopWindow;
import com.zwx.scan.app.widget.MoreTextView;
import com.zwx.scan.app.widget.PhilExpandableTextView;
import com.zwx.scan.app.widget.SegmentControl;
import com.zwx.scan.app.widget.indicator.MagicIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MemberAnalysisDetailActivity extends BaseActivity<MemberContract.Presenter> implements MemberContract.View,View.OnClickListener {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;


    @BindView(R.id.tv_member_count)
    protected TextView tvCount;


    @BindView(R.id.segment)
    protected SegmentControl segmentControl;

    @BindView(R.id.tv_desc)
    protected TextView tvDesc;

    @BindView(R.id.tv_date)
    protected TextView tvDate;

    @BindView(R.id.ll_select_store)
    protected LinearLayout ll_select_store;

    @BindView(R.id.pager)
    public CustomViewPager viewPager;


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

    private String type= "";
    private String date = "";
    private String storeId = "";


    private String userId;
    private String storeName ;
    private String compId;

    protected  MultiAdapter multiAdapter;
    private String storeIdArray ;
    private String storeNameArray ;

    private String brandLogo;

    protected List<CampaignChartBean> memberCountList = new ArrayList<>();
    protected List<Map<String,Object>> countList = new ArrayList<>();


    //多个店铺
    private SelectStoreAdapter myAdapter;
    private List<Store> storeList = new ArrayList<>();
    public List<MoreStoreBean.BrandStoreBean> branList = new ArrayList<>();

    protected List<CampaignChartBean> campaignChartBeanList = new ArrayList<>();
    protected  ArrayList<CampaignChartBean> checkStoreColorList = new ArrayList<>();

    private  List<Integer> colors = new ArrayList<>();
    private String isShowOneStore ;

    private List<ImageView> mDotsIV = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_analysis_detail;
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
        DaggerMemberComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .memberModule(new MemberModule(this))
                .build().inject(this);
        colors= Arrays.asList( getResources().getColor(R.color.blue),
                getResources().getColor(R.color.orange),
                Color.RED,
                getResources().getColor(R.color.green),
                Color.YELLOW,Color.CYAN
                ,Color.MAGENTA,Color.LTGRAY,Color.DKGRAY
        );

        checkStoreColorList = (ArrayList<CampaignChartBean>) getIntent().getSerializableExtra("checkStoreColorList");
        String title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("compTypeId");
        storeId =getIntent().getStringExtra("storeIdArray");
        storeName =getIntent().getStringExtra("storeNameArray");
        brandLogo = getIntent().getStringExtra("brandLogo");
        isShowOneStore = getIntent().getStringExtra("isShowOneStore");

        if (!"YES".equals(isShowOneStore)) {
            tvSelect.setVisibility(View.VISIBLE);
        }else {
            tvSelect.setVisibility(View.GONE);
        }

        ll_select_store.setVisibility(View.VISIBLE);
        rvCheckBox.setVisibility(View.VISIBLE);
        if(storeName!=null){
            tvStoreNames.setText(storeName);
        }

        mDotsLayout.setVisibility(View.GONE);
        if(brandLogo!=null){
           setBrandLogo();
        }

        tvTitle.setText(title);
        tvSelect.setVisibility(View.GONE);
        tvDesc.setText("新增会员趋势图");

        date = "day";
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
                presenter.queryMoreStoreMemTypeCountListDetail(MemberAnalysisDetailActivity.this,storeId,type,date,false);

            }
        });


    }

    @Override
    protected void initData() {
        setCheckboxAdapter();
        setPageAdapter();
        presenter.queryMoreStoreMemTypeCountListDetail(MemberAnalysisDetailActivity.this,storeId,type,date,true);
    }


    public void setPageAdapter(){

      /*  pageAdapter = new FPagerAdapter(getSupportFragmentManager(), countList);
        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(0);*/

        pageAdapter = new MoreFPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {

                setMoveDot(i);
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


    @OnClick({R.id.iv_back,R.id.iv_arrow})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
//                ActivityUtils.finishActivity(MemberAnalysisDetailActivity.class,
//                        R.anim.slide_in_left,R.anim.slide_out_right);

                ActivityUtils.startActivity(MemberAnalysisActivity.class,
                        R.anim.slide_in_left,R.anim.slide_out_right);
                ActivityUtils.finishActivity(MemberAnalysisDetailActivity.class);
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

        ActivityUtils.startActivity(MemberAnalysisActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
        ActivityUtils.finishActivity(MemberAnalysisDetailActivity.class);
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

    public   class MoreFPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragmentList;
        private FragmentManager mFragmentManager;
        private Bundle bundle  = new Bundle();
        private String title = "新增数";
        public MoreFPagerAdapter(FragmentManager fm) {
            super(fm);
            this.mFragmentManager = fm;
            mFragmentList = new ArrayList<>();
            if(memberCountList == null){
                memberCountList = new ArrayList<>();
            }
            Fragment fragment = CountMoreStoreChartFragment.getInstance(memberCountList);
            bundle  = new Bundle();
            bundle.putString("date",date);
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
            setFragments(mFragmentList);
        }

        public void updateData() {
            ArrayList<Fragment> fragments = new ArrayList<>();
            if(memberCountList == null){
                memberCountList = new ArrayList<>();
            }
            Fragment fragment = CountMoreStoreChartFragment.getInstance(memberCountList);
            bundle  = new Bundle();
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
                                chartBean.setChecked(true);
                                storeIdSb.append(chartBean.getStoreId()).append(",");
                            }
                        }
                    }
                    String storeIdA= storeIdSb.toString();
                    if(!TextUtils.isEmpty(storeIdA)){
                        String storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                        presenter.queryMoreStoreMemTypeCountListDetail(MemberAnalysisDetailActivity.this,storeIdArr,type,date,false);
                    }

                }


            }

        });
    }


}
