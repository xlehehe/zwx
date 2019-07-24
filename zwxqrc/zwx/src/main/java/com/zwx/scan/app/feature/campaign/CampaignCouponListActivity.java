package com.zwx.scan.app.feature.campaign;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.formatter.IFillFormatter;
import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.tablayout.SlidingTabLayout;
import com.zwx.library.tablayout.listener.OnTabSelectListener;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.CouponType;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.countanalysis.campaign.CountMoreStoreChartFragment;
import com.zwx.scan.app.feature.couponmanage.GiveCouponNewNextActivity;
import com.zwx.scan.app.feature.couponmanage.GiveCouponNewNextConsumeActivity;
import com.zwx.scan.app.feature.ptmanage.PtNextTwoActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.multibindings.ElementsIntoSet;
/**
 * author : lizhilong
 * time   : 2018/10/22
 * desc   : 优惠券选项列表
 * version: 1.0
 **/
public class CampaignCouponListActivity extends BaseActivity<CampaignsContract.Presenter> implements CampaignsContract.View,View.OnClickListener,CampaignCouponListFragment.UpdateSelectCouponNum {

    @BindView(R.id.iv_back)
    protected ImageView ivBack;
    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.tab_layout)
    protected SlidingTabLayout tabLayout;

    @BindView(R.id.view_pager)
    protected CustomViewPager viewPager;

    @BindView(R.id.tv_selected_count)
    protected TextView tvCount;

    @BindView(R.id.btn_submit)
    protected Button submit;
    public List<CouponType> typeList = new ArrayList<CouponType>();
    protected List<Fragment> mFragments = new ArrayList<>();
    public   TypePagerAdapter pagerAdapter = null;

    private  String userId;
    private String type = "CPC";
    private  ArrayList<Coupon> couponList = new ArrayList<>();
    private  int selectCouponNum = 0;
    private String nextTwoSelectCoupon = "";

    //奖项名称
    private String prizeName;

    List<Coupon> selectCouponList = new ArrayList<>();

    private String ptName;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon_list;
    }

    @Override
    protected void initView() {
        tvTitle.setText("优惠券");
        DaggerCampaignsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignsModule(new CampaignsModule(this))
                .build()
                .inject(this);


        CampaignCouponListFragment.selectNum = 0;
    }




    @Override
    protected void initData() {

        userId = SPUtils.getInstance().getString("userId");
        presenter.queryCouponTypeList(this,userId);

        nextTwoSelectCoupon = getIntent().getStringExtra("nextTwoSelectCoupon");

        //奖项名称
        prizeName = getIntent().getStringExtra("prizeName");
        ptName = getIntent().getStringExtra("ptName");

        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
                CouponType couponType = typeList.get(position);

                if(couponType != null){
                    type = couponType.getType();
//                    EventBus.getDefault().post(new LhPtEventBus(type));
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        /*viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                CouponType couponType = typeList.get(position);
                tabLayout.setCurrentTab(position);
                if(couponType != null){
                    type = couponType.getType();
                    EventBus.getDefault().post(new LhGeEventBus("",type));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
    }


    @OnClick({R.id.iv_back,R.id.btn_submit})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(CampaignCouponListActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.btn_submit:

                selectCouponList =  CampaignCouponListFragment.selectCoupons;
//                remove(selectCouponList);
                if (selectCouponList != null && selectCouponList.size()>0){
                    for (Coupon coupon : selectCouponList){
                        if (coupon != null){
                            coupon.setChecked(true);
                            couponList.add(coupon);
                        }
                    }
                }
                Intent intent = null;

                if("YES".equals(nextTwoSelectCoupon)){  //转发活动第二步
                    intent = new Intent(CampaignCouponListActivity.this,CampaignNewNextTwoActivity.class);
                    int resultCode;
                    intent.putExtra("selectCouponList",couponList);

                    setResult(2222,intent);
                }else if("NO".equals(nextTwoSelectCoupon)) {   //转发活动第三步
                    intent = new Intent(CampaignCouponListActivity.this,CampaignNewNextThreeActivity.class);
                    int resultCode;
                    intent.putExtra("selectCouponList",couponList);

                    setResult(3333,intent);
                }else if("GIVE".equals(nextTwoSelectCoupon)) {   //常规发券
                    String grantType = getIntent().getStringExtra("grantType");

                    if(!"S".equals(grantType)){   //生日礼 会员礼 开卡礼
                        intent = new Intent(CampaignCouponListActivity.this, GiveCouponNewNextActivity.class);
                        intent.putExtra("selectCouponList", couponList);

                        setResult(4444, intent);
                    }else {  //唤醒消费礼
                        intent = new Intent(CampaignCouponListActivity.this, GiveCouponNewNextConsumeActivity.class);
                        intent.putExtra("selectCouponList", couponList);
                        intent.putExtra("prizeName", prizeName);
                        setResult(6666, intent);
                    }


                }else if("lh".equals(nextTwoSelectCoupon)||"ge".equals(nextTwoSelectCoupon)) {  //老虎机或砸金蛋
                    intent = new Intent(CampaignCouponListActivity.this, LaohuPinTuanNextTwoActivity.class);
                    int resultCode;
                    intent.putExtra("selectCouponList", couponList);
                    if("奖项一".equals(prizeName)){
                        intent.putExtra("prizeName", prizeName);
                        setResult(5555, intent);
                    }else if("奖项二".equals(prizeName)){
                        intent.putExtra("prizeName", prizeName);
                        setResult(5555, intent);
                    }else if("奖项三".equals(prizeName)){
                        intent.putExtra("prizeName", prizeName);
                        setResult(5555, intent);
                    }else if("奖项四".equals(prizeName)){
                        intent.putExtra("prizeName", prizeName);
                        setResult(5555, intent);
                    }else if("奖项五".equals(prizeName)){
                        intent.putExtra("prizeName", prizeName);
                        setResult(5555, intent);
                    }else if("奖项六".equals(prizeName)){
                        intent.putExtra("prizeName", prizeName);
                        setResult(5555, intent);
                    }else if("安慰奖".equals(prizeName)){
                        intent.putExtra("prizeName", prizeName);
                        setResult(5555, intent);
                    }

                }else if("pt".equals(nextTwoSelectCoupon)){
                    intent = new Intent(CampaignCouponListActivity.this, PtNextTwoActivity.class);
                    intent.putExtra("selectCouponList", couponList);
                    intent.putExtra("ptName",ptName);
                    setResult(6666, intent);
                }else if("MEMBER_INFO".equals(nextTwoSelectCoupon)){ //会员信息完善
                    intent = new Intent(CampaignCouponListActivity.this, MemberInfoPerfectNextTwoActivity.class);
                    intent.putExtra("selectCouponList", couponList);
                    setResult(8888, intent);
                }else if("JZ".equals(nextTwoSelectCoupon)){
                    intent = new Intent(CampaignCouponListActivity.this, CampaignLikeTwoActivity.class);
                    int resultCode;
                    intent.putExtra("selectCouponList", couponList);
                    if("条件一".equals(prizeName)){
                        intent.putExtra("prizeName", prizeName);
                        setResult(7777, intent);
                    }else if("条件二".equals(prizeName)){
                        intent.putExtra("prizeName", prizeName);
                        setResult(7777, intent);
                    }else if("条件三".equals(prizeName)){
                        intent.putExtra("prizeName", prizeName);
                        setResult(7777, intent);
                    }else if("点赞人奖励".equals(prizeName)){
                        intent.putExtra("prizeName", prizeName);
                        setResult(7777, intent);
                    }
                }
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;


        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(CampaignCouponListActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void setPagerAdapter(){
        pagerAdapter = new TypePagerAdapter(getSupportFragmentManager(),typeList);

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setViewPager(viewPager);
        tabLayout.setCurrentTab(0);
        viewPager.setCurrentItem(0);
        CampaignCouponListFragment.getInstance(type);
        viewPager.setOffscreenPageLimit(typeList.size());

    }


    public   class TypePagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragmentList;
        private FragmentManager mFragmentManager;
        private Bundle bundle  = new Bundle();
        private  List<CouponType> couponTypeList = null;
        public TypePagerAdapter(FragmentManager fm, List<CouponType> typeList) {
            super(fm);
            this.mFragmentManager = fm;
            this.couponTypeList = typeList;
            mFragmentList = new ArrayList<>();
            if(couponTypeList !=null && couponTypeList.size()>0){
                for (CouponType couponType : couponTypeList){
                    String type = couponType.getType();
                    Fragment fragment = CampaignCouponListFragment.getInstance(type);
                    mFragmentList.add(fragment);
                }
            }

            setFragments(mFragmentList);
        }

        public void updateData(List<CouponType> couponTypeList) {
            ArrayList<Fragment> fragments = new ArrayList<>();
            if(couponTypeList !=null && couponTypeList.size()>0){
                for (CouponType couponType : couponTypeList){
                    String typeName = couponType.getType();
                    Fragment fragment = CampaignCouponListFragment.getInstance(typeName);
                    fragments.add(fragment);

                }
            }
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

        @Override
        public CharSequence getPageTitle(int position) {
            return couponTypeList.get(position).getName();
        }
    }


    @Override
    public void updateSelectNum(int selectNum) {
        tvCount.setText(selectNum+"");
    }
}
