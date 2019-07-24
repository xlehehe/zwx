package com.zwx.scan.app.feature.verificationrecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragmentActivity;
import com.zwx.scan.app.data.bean.MemCoupon;
import com.zwx.scan.app.data.bean.VerificationRecord;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.main.MainActivity;
import com.zwx.scan.app.feature.modulemore.ModuleMoreListActivity;
import com.zwx.scan.app.feature.verification.DaggerVerificationComponent;
import com.zwx.scan.app.feature.verification.VerificationActivity;
import com.zwx.scan.app.feature.verification.VerificationContract;
import com.zwx.scan.app.feature.verification.VerificationModule;
import com.zwx.scan.app.widget.MoreTextView;
import com.zwx.scan.app.widget.PhilExpandableTextView;
import com.zwx.scan.app.widget.SegmentControl;
import com.zwx.scan.app.widget.indicator.MagicIndicator;
import com.zwx.scan.app.widget.indicator.ScaleCircleNavigator;
import com.zwx.scan.app.widget.indicator.ViewPagerHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class VerificationRecordActivity extends BaseFragmentActivity<VerificationContract.Presenter> implements VerificationContract.View,View.OnClickListener {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;


    /*@BindView(R.id.tab_layout)
    protected SegmentTabLayout tabLayout;

    @BindView(R.id.fl_content)
    protected FrameLayout frameLayout;*/

    @BindView(R.id.rv_content)
    public RecyclerView rvVerificationTotal;
    @BindView(R.id.tv_record_detail)
    protected TextView tvRecordDetail;
    private View decorView;
    public List<MemCoupon> memCouponList = new ArrayList<>();
    public ArrayList<Fragment> fragments = new ArrayList<>();

    private String[] titles = {"今日", "本周", "本月"};


    @BindView(R.id.tv_coupon_total)
    public TextView tvCouponTotal;

    @BindView(R.id.tv_coupon_pen)
    public TextView tvCouponPen;


    @BindView(R.id.tv_ta_label)
    public TextView tvLabel;
    @BindView(R.id.tv_date)
    public TextView tvDate;
    @BindView(R.id.tv_legend)
    public TextView tvLegend;


   /* @BindView(R.id.chart)
    protected BarChart chart;

    @BindView(R.id.ll_left)
    protected LinearLayout llLeft;

    @BindView(R.id.ll_right)
    protected LinearLayout llRight;*/

    @BindView(R.id.segment)
    protected SegmentControl segmentControl;


    @BindView(R.id.pager)
    public CustomViewPager viewPager;
    @BindView(R.id.dots)
    protected LinearLayout mDotsLayout;
    @BindView(R.id.tv_label)
    protected TextView tv_label;
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

    @BindView(R.id.ll_no_data)
    public LinearLayout ll_no_data;

    @BindView(R.id.iv_no_data)
    public ImageView iv_no_data;

    @BindView(R.id.tv_no_data)
    public TextView tv_no_data;


//    public PageAdapter pageAdapter;

//    @BindView(R.id.indicator)
//    protected MagicIndicator indicator;
    public FPagerAdapter mPagerAdapter;

    protected String title;
    protected String date = "day";

    private YAxis leftAxis;             //左侧Y轴
    private YAxis rightAxis;            //右侧Y轴
    private XAxis xAxis;                //X轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线

    private boolean isLegend = true;

    public List<Map<String, Object>> verificationTotalReport = new ArrayList<>();
    public List<Map<String, Object>> penVerificationTotalReport = new ArrayList<>();
    public RecordCouponTotalAdapter recordTotalAdapter = null;
    private String params;
    private String isTitlePager1 = "";
    private String isTitlePager2 = "";
    private List<ImageView> mDotsIV = new ArrayList<>();

    private String couponTitle = "";

    private String storeName ;
    private String brandLogo;
    private String fromHomeFragmentIntent = "NO";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_verification_record;
    }

    @Override
    protected void initView() {

        fromHomeFragmentIntent = getIntent().getStringExtra("fromHomeFragmentIntent");
        tvTitle.setText("核销记录");
        decorView = getWindow().getDecorView();

        DaggerVerificationComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .verificationModule(new VerificationModule(this))
                .build()
                .inject(this);
        storeName = SPUtils.getInstance().getString("storeName");
        brandLogo = SPUtils.getInstance().getString("brandLogo");
        tvStoreName.setText("#"+storeName);
        setBrandLogo();
        couponTitle = "<font size = '16'color = \'#0486FE\' weight = 'bolder'>今日</font>"+getResources().getString(R.string.veri_record_situation);
        tv_label.setText(Html.fromHtml(couponTitle));
        segmentControl.setSelectedIndex(0);
        segmentControl.setOnSegmentChangedListener(new SegmentControl.OnSegmentChangedListener() {
            @Override
            public void onSegmentChanged(int newSelectedIndex) {
                if (newSelectedIndex == 2) {
                    date = "month";
                    isLegend = true;
                    couponTitle = "<font size = '16'color = \'#0486FE\' weight = 'bolder'>本月</font>"+getResources().getString(R.string.veri_record_situation);
                } else if (newSelectedIndex == 0) {
                    date = "day";
                    isLegend = false;

                    couponTitle = "<font size = '16'color = \'#0486FE\' weight = 'bolder'>今日</font>"+getResources().getString(R.string.veri_record_situation);
                } else if (newSelectedIndex == 1) {
                    isLegend = false;
                    date = "week";

                    couponTitle = "<font size = '16'color = \'#0486FE\' weight = 'bolder'>本周</font>"+getResources().getString(R.string.veri_record_situation);
                }


                tv_label.setText(Html.fromHtml(couponTitle));
                String storeId = SPUtils.getInstance().getString("storeId");
                String compId = SPUtils.getInstance().getString("compId");


                presenter.queryVerificationRecord(VerificationRecordActivity.this, storeId, compId, date);

            }
        });

        mPagerAdapter = new FPagerAdapter(getSupportFragmentManager(), new VerificationRecord());
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setCurrentItem(0);
        tvLabel.setText("核销总数趋势");



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
        setDotLayout();

    }

    private void setBrandLogo(){
        RoundedCorners roundedCorners= new RoundedCorners(8);

        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_logo_default)
                .error(R.drawable.ic_logo_default)
                .fallback(R.drawable.ic_logo_default);

        Glide.with(this).load(brandLogo).apply(requestOptions).into(ivBrand);
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

    @Override
    protected void initData() {

        String storeId = SPUtils.getInstance().getString("storeId");
        String compId = SPUtils.getInstance().getString("compId");

        title = "优惠券核销总数";
        date = "day";
        presenter.queryVerificationRecord(VerificationRecordActivity.this, storeId, compId, date);
        isTitlePager1 = "优惠券核销总数";
        isTitlePager2 = "核销桌数";
        tvLegend.setText(title);

//        presenter.queryVerificationList(VerificationRecordActivity.this,storeId,compId);



        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {


            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    tvLabel.setText("核销总数趋势");
                    isTitlePager1 = "优惠券核销总数";
                    tvLegend.setText(isTitlePager1);

                } else if (i == 1) {
                    tvLabel.setText("核销桌数趋势");
                    isTitlePager2 = "核销桌数";
                    tvLegend.setText(isTitlePager2);
                }
                setMoveDot(i);

            }

            @Override
            public void onPageScrollStateChanged(int i) {


            }
        });

    }

    public void setDataAdapter(){
        recordTotalAdapter = new RecordCouponTotalAdapter(this,memCouponList);

        rvVerificationTotal.setLayoutManager(new LinearLayoutManager(VerificationRecordActivity.this));
        rvVerificationTotal.setNestedScrollingEnabled(false);
        rvVerificationTotal.setAdapter(recordTotalAdapter);
    }


    @OnClick({R.id.iv_back, R.id.tv_record_detail})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                if("YES".equals(fromHomeFragmentIntent)){
                    ActivityUtils.startActivity(MainActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
                }else {
                    ActivityUtils.startActivity(ModuleMoreListActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
                }
                ActivityUtils.finishActivity(VerificationRecordActivity.class);
                break;
            case R.id.tv_record_detail:

                Intent intent = new Intent(VerificationRecordActivity.this,VerificationRecordDetailActivity.class);
                intent.putExtra("date",date);
                ActivityUtils.startActivity(intent,
                        R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        if("YES".equals(fromHomeFragmentIntent)){
            ActivityUtils.startActivity(MainActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
        }else {
            ActivityUtils.startActivity(ModuleMoreListActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
        }
        ActivityUtils.finishActivity(VerificationRecordActivity.class);
    }



    public   class FPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragmentList;
        private FragmentManager mFragmentManager;
        private Bundle bundle  = new Bundle();
        public FPagerAdapter(FragmentManager fm, VerificationRecord record) {
            super(fm);
            this.mFragmentManager = fm;
            mFragmentList = new ArrayList<>();
            if(record == null){
                verificationTotalReport = new ArrayList<>();
                penVerificationTotalReport = new ArrayList<>();
            }else{
                verificationTotalReport = record.getVerificationTotalReport();
                penVerificationTotalReport = record.getPenVerificationTotalReport();
            }
            Fragment fragment1 =ChartFragment.getInstance(isTitlePager1,verificationTotalReport);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",true);
            bundle.putString("date",date);
            bundle.putString("title",isTitlePager1);
            fragment1.setArguments(bundle);
            Fragment fragment2=ChartFragment.getInstance(isTitlePager2,penVerificationTotalReport);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",true);
            bundle.putString("date",date);
            bundle.putString("title",isTitlePager2);
            fragment2.setArguments(bundle);
            mFragmentList.add(fragment1);
            mFragmentList.add(fragment2);

            setFragments(mFragmentList);
        }

        /*  public void updateData(List<String> titles,List<Map<String,Object>> dataList) {
              ArrayList<Fragment> fragments = new ArrayList<>();
              for (int i = 0, size = titles.size(); i < size; i++) {
                  Log.e("FPagerAdapter1", titles.get(i).toString());
                  String title = titles.get(i);
                  fragments.add(CommonChartFragment.getInstance(title,date,dataList));
              }
              setFragments(fragments);
          }*/
        public void updateData(VerificationRecord record) {
            ArrayList<Fragment> fragments = new ArrayList<>();
            if(record == null){
                verificationTotalReport = new ArrayList<>();
                penVerificationTotalReport = new ArrayList<>();
            }else{
                verificationTotalReport = record.getVerificationTotalReport();
                penVerificationTotalReport = record.getPenVerificationTotalReport();
            }
            Fragment fragment1 =ChartFragment.getInstance(isTitlePager1,verificationTotalReport);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",true);
            bundle.putString("date",date);
            bundle.putString("title",isTitlePager1);
            fragment1.setArguments(bundle);
            Fragment fragment2=ChartFragment.getInstance(isTitlePager2,penVerificationTotalReport);
            bundle  = new Bundle();
            bundle.putBoolean("isLegend",true);
            bundle.putString("date",date);
            bundle.putString("title",isTitlePager2);
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


}
