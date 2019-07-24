package com.zwx.scan.app.feature.campaign;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.tablayout.SlidingTabLayout;
import com.zwx.library.tablayout.listener.OnTabSelectListener;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.LogUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.CampaignCollect;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CampaignGame;
import com.zwx.scan.app.data.bean.CampaignGamesetreward;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.CouponMaterial;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.couponmanage.GiveCouponConsumeConditionTagActivity;
import com.zwx.scan.app.feature.couponmanage.GiveCouponManageActivity;
import com.zwx.scan.app.feature.couponmanage.GiveCouponNewActivity;
import com.zwx.scan.app.utils.ButtonUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/04/08
 * desc   :  集赞活动第二步
 * version: 1.0
 **/
public class CampaignLikeTwoActivity extends BaseActivity<CampaignsContract.Presenter> implements CampaignsContract.View,View.OnClickListener  {
    @BindView(R.id.iv_back)
    protected ImageView ivBack;
    @BindView(R.id.tv_right)
    protected TextView tvRight;
    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_one)
    protected ImageView ivOne;
    @BindView(R.id.iv_ellipsis_one)
    protected ImageView ivEllipsisOne;
    @BindView(R.id.iv_ellipsis_two)
    protected ImageView ivEllipsisTwo;
    @BindView(R.id.iv_two)
    protected ImageView ivTwo;


    @BindView(R.id.iv_three)
    protected ImageView ivThree;
    @BindView(R.id.tab_layout)
    protected SlidingTabLayout tabLayout;


    @BindView(R.id.mViewPager)
    protected CustomViewPager viewPager;


    @BindView(R.id.ll_more_columns)
    protected LinearLayout ll_more_columns;
    @BindView(R.id.rl_column)
    protected RelativeLayout rl_column;

    @BindView(R.id.button_more_columns)
    protected ImageView button_more_columns;

    @BindView(R.id.btn_pre)
    protected Button btnPre;

    @BindView(R.id.btn_next)
    protected Button btnNext;


    protected static ArrayList<Coupon> couponList1 = new ArrayList<>();
    protected static ArrayList<Coupon> couponList2 = new ArrayList<>();
    protected static ArrayList<Coupon> couponList3 = new ArrayList<>();
    protected static ArrayList<Coupon> couponList4 = new ArrayList<>();

//    protected static ArrayList<CampaignCoupon> campaignCouponList =new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList1 =new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList2 =new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList3 =new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList4 =new ArrayList<>();
    protected  static ArrayList<CampaignCoupon> forwardCouponList = new ArrayList<>();
    //编辑
    protected   String isEditCampaign = "NO";

    //复制并创建
    protected String isCopyCreate ;

    protected String title;

    protected static  String isUpAndNext = "YES";

    protected   String storeIdCouponType ;

    private String userId;
    private String compId;
    protected  ArrayList<PrizeBean> prizeBeanList = new ArrayList<>();
    protected  static ArrayList<CampaignGamesetreward> campaignGamesetrewardList = new ArrayList<>();
    private  String name;
    private FPagerAdapter pagerAdapter = null;
    private String btnFlag = "";
    private String grantType = "";
    protected  Intent intent = null;

    protected static String  prizeCount1;
    protected static String  prizeCount2;
    protected static String  prizeCount3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_campaign_like_two;
    }

    @Override
    protected void initView() {
        DaggerCampaignsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignsModule(new CampaignsModule(this))
                .build()
                .inject(this);
        //注销注册
        EventBus.getDefault().unregister(this);
        title = "集赞活动";
        tvTitle.setText(title+"设置");
        setSetTep();
        isEditCampaign =  getIntent().getStringExtra("isEditCampaign");
        isCopyCreate = getIntent().getStringExtra("isCopyCreate");
        userId = SPUtils.getInstance().getString("userId");
        compId = SPUtils.getInstance().getString("compId");
        grantType = SPUtils.getInstance().getString("grantType");

        if("YES".equals(isEditCampaign)||"NEW".equals(isEditCampaign)){  //编辑
            if("YES".equals(isCopyCreate)){  //编辑页面复制并创建
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setText("复制并创建");
            }else {
                tvRight.setVisibility(View.GONE);
            }

        }


        if("S".equals(CampaignLikeNewActivity.compaignStatus)||"NEW".equals(CampaignLikeNewActivity.compaignStatus)){

            ll_more_columns.setEnabled(true);
            button_more_columns.setEnabled(true);

        }else {
            ll_more_columns.setEnabled(false);
            button_more_columns.setEnabled(false);

        }
        setLisenter();
    }

    @Override
    protected void initData() {
        //条件
        initCampaignGamesetrewardList();

        initTabColumn();
    }

    private void setSetTep(){
        ivOne.setBackgroundResource(R.drawable.ic_first_clicked);
        ivEllipsisOne.setBackgroundResource(R.drawable.ic_ellipsis_blue);
        ivTwo.setBackgroundResource(R.drawable.ic_two_clicked);
        ivEllipsisTwo.setImageResource(R.drawable.ic_ellipsis_gray);
        ivThree.setBackgroundResource(R.drawable.ic_three_untclick);
    }

    public void setPagerAdapter(){
        pagerAdapter = new FPagerAdapter(getSupportFragmentManager(),prizeBeanList);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setViewPager(viewPager);
        viewPager.setCurrentItem(0);
        tabLayout.setCurrentTab(0);
        viewPager.setOffscreenPageLimit(prizeBeanList.size());

    }

    private void setLisenter(){
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
//                viewPager.setCurrentItem(position);
                PrizeBean prizeBean = prizeBeanList.get(position);
                if(prizeBean != null) {
                    name = prizeBean.getName();
                    EventBus.getDefault().post(new LhPtEventBus(name));
                    pagerAdapter.updateData(prizeBeanList);
                }

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
                PrizeBean prizeBean = prizeBeanList.get(position);
                if(prizeBean != null){
                    name = prizeBean.getName();
                    EventBus.getDefault().post(new LhPtEventBus(name));
                    pagerAdapter.updateData(prizeBeanList);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    //初始化中奖
    private void initCampaignGamesetrewardList(){
        prizeBeanList = new ArrayList<>();
        couponList1.clear();
        couponList2.clear();
        couponList3.clear();
        couponList4.clear();
        //默认去缓存若没有 变量
        if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0){
            for(CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList){
                if(campaignGamesetreward != null){
                    String rewardType = campaignGamesetreward.getRewardType();
                    Integer rewardOrder = campaignGamesetreward.getPrizeOrder();

                    PrizeBean prizeBean = new PrizeBean();
                    if("1".equals(rewardType)){
                        if(rewardOrder != null){
                            if(rewardOrder == 1){
                                prizeBean.setName(campaignGamesetreward.getPrizeName());
                                prizeBean.setChecked(true);
                                prizeBeanList.add(prizeBean);
                                campaignCouponList1  = campaignGamesetreward.getList();

                            }else if(rewardOrder == 2){
                                prizeBean.setName(campaignGamesetreward.getPrizeName());
                                prizeBean.setChecked(true);
                                prizeBeanList.add(prizeBean);
                                campaignCouponList2  = campaignGamesetreward.getList();
                            }else if(rewardOrder == 3){
                                prizeBean.setName(campaignGamesetreward.getPrizeName());
                                prizeBean.setChecked(true);
                                prizeBeanList.add(prizeBean);
                                campaignCouponList3= campaignGamesetreward.getList();
                            }
                        }
                    }else if("0".equals(rewardType)||rewardType == "0"){  //点赞人奖励
                        prizeBean.setName(campaignGamesetreward.getPrizeName());
                        prizeBean.setChecked(false);
                        prizeBeanList.add(prizeBean);
                        campaignCouponList4  = campaignGamesetreward.getList();
                    }
                    initCampaignRewardList(campaignGamesetreward);
                }
            }


        }else {
            initPrizePath();
         
        }
    }

    private void initCampaignRewardList(CampaignGamesetreward campaignGamesetreward){
        String name = campaignGamesetreward.getPrizeName();
        List<CampaignCoupon> forwardCouponList = new ArrayList<>();
        if("条件一".equals(name)){
            forwardCouponList = campaignCouponList1;
        }else if("条件二".equals(name)){
            forwardCouponList = campaignCouponList2;
        }else if("条件三".equals(name)){
            forwardCouponList = campaignCouponList3;
        }else if("点赞人奖励".equals(name)){
            forwardCouponList = campaignCouponList4;
        }
        //本地缓存 提取
        if(forwardCouponList!=null && forwardCouponList.size()>0) {
            for (CampaignCoupon campaignCoupon : forwardCouponList) {
                List<Store> storeList = campaignCoupon.getStores();
                Coupon coupon = new Coupon();
                if (storeList != null && storeList.size() > 0) {
                    Store store1 = storeList.get(0);
                    StringBuilder storeIdSb = new StringBuilder();
                    StringBuilder storeNameSb = new StringBuilder();

                    if (store1 != null) {
                        storeIdCouponType = store1.getStoreSelectType();
                        String storeName = "";
                        String storeIdArr = "";
                        if ("A".equals(storeIdCouponType)) {
                            storeName = "#全部店铺";
                            for (Store store : storeList) {
                                store.setChecked(false);
                                String storeId = store.getStoreId();
                                storeIdSb.append(storeId).append("-");

                            }
                            storeIdArr = "";
                        } else if ("D".equals(storeIdCouponType)) {
                            storeName = "#全部自营";
                            for (Store store : storeList) {
                                store.setChecked(false);
                                String storeId = store.getStoreId();
                                storeIdSb.append(storeId).append("-");

                            }
                            storeIdArr = "";
                        } else if ("J".equals(storeIdCouponType)) {
                            storeName = "#全部加盟";
                            for (Store store : storeList) {
                                store.setChecked(false);
                                String storeId = store.getStoreId();
                                storeIdSb.append(storeId).append("-");

                            }
                            storeIdArr = "";
                        } else {
                            storeIdCouponType = "H";
                            for (Store store : storeList) {
                                store.setChecked(false);
                                Integer statusQuo = store.getStatusQuo();
                                String storeId = store.getStoreId();
                                String storeName2 = store.getStoreName() != null ? "#" + store.getStoreName() + "    " : "";
                                storeIdSb.append(storeId).append("-");
                                storeNameSb.append(storeName2);

                            }
                            storeName = storeNameSb.toString();

                            String storeIdA = storeIdSb.toString();
                            if (storeIdA != null && !"".equals(storeIdA)) {
                                storeIdArr = storeIdA.substring(0, storeIdA.length() - 1);
                            }

                            coupon.setStoreIds(storeIdArr);
                            coupon.setStoreNames(storeName);
                        }

                    }
                }
                CouponMaterial material = new CouponMaterial();
                material.setBackground(campaignCoupon.getBackgroundThumbnail());
                material.setBackgroundThumbnail(campaignCoupon.getBackground());

                coupon.setMaterial(material);
                coupon.setCouponQnt(campaignCoupon.getCouponQnt());
                coupon.setObject(campaignCoupon.getObject());
                coupon.setDiscount(campaignCoupon.getDiscount());
                coupon.setMoney(campaignCoupon.getMoney());
                coupon.setNoDate(campaignCoupon.getNoDate());
                coupon.setNoItem(campaignCoupon.getNoItem());
                coupon.setDateCode(campaignCoupon.getDateCode());
                coupon.setStoreSelectType(campaignCoupon.getStoreSelectType());
                coupon.setExpireStartType(campaignCoupon.getExpireStartType());
                coupon.setExpireEndType(campaignCoupon.getExpireEndType());
                coupon.setOther(campaignCoupon.getOther());
                coupon.setLimit(campaignCoupon.getLimit());
                coupon.setType(campaignCoupon.getType());
                coupon.setId(campaignCoupon.getCouponId());
                coupon.setTimePeriod(campaignCoupon.getTimePeriod());
                coupon.setExpireStartDate(campaignCoupon.getExpireStartDate());
                coupon.setExpireEndDate(campaignCoupon.getExpireEndDate());
                coupon.setName(campaignCoupon.getCouponName());
                coupon.setExpireStartDay(campaignCoupon.getExpireStartDay()!=null && !"".equals(campaignCoupon.getExpireStartDay())?campaignCoupon.getExpireStartDay():"0");
                coupon.setExpireEndDay(campaignCoupon.getExpireEndDay()!=null && !"".equals(campaignCoupon.getExpireEndDay())?campaignCoupon.getExpireEndDay():"1");
                if ("条件一".equals(name)) {
                    couponList1.add(coupon);

                } else if ("条件二".equals(name)) {
                    couponList2.add(coupon);

                } else if ("条件三".equals(name)) {
                    couponList3.add(coupon);

                } else if ("点赞人奖励".equals(name)) {
                    couponList4.add(coupon);

                }


            }
        }
        LogUtils.e("-------------------"+couponList1.toString()+couponList2.toString()+couponList3.toString());
    }

    protected void initPrizePath(){
        //条件
        campaignGamesetrewardList = new ArrayList<>();
        if(prizeBeanList == null||prizeBeanList.size()==0){
            PrizeBean prizeBean = new PrizeBean();
            prizeBean.setName("条件一");
            prizeBean.setChecked(true);
            prizeBeanList.add(prizeBean);
            prizeBean = new PrizeBean();
            prizeBean.setName("点赞人奖励");
            prizeBean.setChecked(false);
            prizeBeanList.add(prizeBean);

            CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();
            campaignGamesetreward.setPrizeCount(2);
            campaignGamesetreward.setRewardType("1");
            campaignGamesetreward.setPrizeName("条件一");
            campaignGamesetreward.setPrizeOrder(1);
            campaignGamesetrewardList.add(campaignGamesetreward);

            campaignGamesetreward = new CampaignGamesetreward();
            campaignGamesetreward.setPrizeCount(0);
            campaignGamesetreward.setRewardType("0");
            campaignGamesetreward.setPrizeName("点赞人奖励");
            campaignGamesetreward.setPrizeOrder(0);
            campaignGamesetrewardList.add(campaignGamesetreward);
        }


    }


    private void initTabColumn(){

        if(prizeBeanList != null && prizeBeanList.size()>0){

        }else {

            prizeBeanList = new ArrayList<>();
            PrizeBean prizeBean = new PrizeBean();
            prizeBean.setName("条件一");
            prizeBean.setChecked(false);
            prizeBeanList.add(prizeBean);
            prizeBean = new PrizeBean();
            prizeBean.setName("点赞人奖励");
            prizeBean.setChecked(false);
            prizeBeanList.add(prizeBean);
        }
        setPagerAdapter();

    }


    @OnClick({R.id.iv_back,R.id.button_more_columns,R.id.tv_right,R.id.btn_pre,R.id.btn_next})
    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.startActivity(CampaignListActivity.class, R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
                break;
            case R.id.tv_right:
                ToastUtils.showCustomShortBottom(getResources().getString(R.string.create_success));
                copyAndCreate();

                break;
            case R.id.button_more_columns:

                for (PrizeBean prizeBean : prizeBeanList) {

                    prizeBean.setChecked(true);

                }
                if (!ButtonUtils.isFastDoubleClick(R.id.btn_save)) {
                    intent = new Intent(CampaignLikeTwoActivity.this, GiveCouponConsumeConditionTagActivity.class);
                    intent.putExtra("prizeBeanList", prizeBeanList);
                    intent.putExtra("selectType", "JZ");
                    ActivityUtils.startActivityForResult(CampaignLikeTwoActivity.this, intent, 7001, R.anim.slide_in_right, R.anim.slide_out_left);

                }

                break;
            case R.id.btn_pre:
               setIntentUpActivity();

                break;
            case R.id.btn_next:
                setIntentNextActivity();

                break;

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 7001){
            if(requestCode == 7001){
                if(data != null){
                    prizeBeanList = (ArrayList<PrizeBean>)data.getSerializableExtra("prizeBeanList");
                }

                ArrayList<CampaignGamesetreward> campaignGamesetrewardListNewData = new ArrayList<>();
                ArrayList<CampaignGamesetreward> campaignGamesetrewardListNewData1 = new ArrayList<>();
                ArrayList<CampaignGamesetreward> campaignGamesetrewardListNewData2 = new ArrayList<>();
                ArrayList<CampaignGamesetreward> campaignGamesetrewardListNewData3 = new ArrayList<>();
                ArrayList<CampaignGamesetreward> campaignGamesetrewardListNewData4 = new ArrayList<>();
                if(prizeBeanList.size()<campaignGamesetrewardList.size()){  //若返回新的条件项比旧的条件项少，则重新赋值，否则新增
                    if(prizeBeanList != null && !"".equals(prizeBeanList)){
                        for (PrizeBean prizeBean:prizeBeanList){
                            String prizeName = prizeBean.getName();
                            if(campaignGamesetrewardList != null && !"".equals(campaignGamesetrewardList)){
                                for (int i=0;i<campaignGamesetrewardList.size();i++){
                                    CampaignGamesetreward campaignGamesetreward = campaignGamesetrewardList.get(i);
                                    if(campaignGamesetreward != null){
                                        String prizeName2 = campaignGamesetreward.getPrizeName();

                                        if(prizeName.equals(prizeName2)){
                                            campaignGamesetrewardListNewData.add(campaignGamesetreward);
                                            break;
                                        }
                                    }
                                }
                            }

                        }

                        campaignGamesetrewardList = campaignGamesetrewardListNewData;

                        //循环重新复优惠券信息
                        if(campaignGamesetrewardList != null && !"".equals(campaignGamesetrewardList)){
                            for (int i=0;i<campaignGamesetrewardList.size();i++){
                                CampaignGamesetreward campaignGamesetreward = campaignGamesetrewardList.get(i);
                                if(campaignGamesetreward != null){
                                    String prizeName2 = campaignGamesetreward.getPrizeName();

                                    if("条件一".equals(prizeName2)){
                                        campaignGamesetreward.setPrizeOrder(1);
                                        campaignGamesetreward.setList(campaignCouponList1);
                                        campaignGamesetrewardListNewData1.add(campaignGamesetreward);
                                    }else  if("条件二".equals(prizeName2)){
                                        campaignGamesetreward.setPrizeOrder(2);
                                        campaignGamesetreward.setList(campaignCouponList2);
                                        campaignGamesetrewardListNewData1.add(campaignGamesetreward);
                                    }else  if("条件三".equals(prizeName2)){
                                        campaignGamesetreward.setPrizeOrder(3);
                                        campaignGamesetreward.setList(campaignCouponList3);
                                        campaignGamesetrewardListNewData3.add(campaignGamesetreward);
                                    }else  if("点赞人奖励".equals(prizeName2)){
                                        campaignGamesetreward.setPrizeOrder(4);
                                        campaignGamesetreward.setList(campaignCouponList4);
                                        campaignGamesetrewardListNewData4.add(campaignGamesetreward);
                                    }
                                }
                            }
                        }

                    }
                }else {   //条件项比旧条件项多，重新添加排序
                    if(campaignGamesetrewardList != null && !"".equals(campaignGamesetrewardList)){
                        for (int i=0;i<campaignGamesetrewardList.size();i++){
                            CampaignGamesetreward campaignGamesetreward = campaignGamesetrewardList.get(i);
                            if(campaignGamesetreward != null){
                                String prizeName2 = campaignGamesetreward.getPrizeName();
                                campaignGamesetreward.setRewardType("1");

                                if("条件一".equals(prizeName2)){
                                    campaignGamesetreward.setPrizeOrder(1);
                                    campaignGamesetreward.setList(campaignCouponList1);
                                    campaignGamesetrewardListNewData1.add(campaignGamesetreward);
                                }else  if("条件二".equals(prizeName2)){
                                    campaignGamesetreward.setPrizeOrder(2);
                                    campaignGamesetreward.setList(campaignCouponList2);
                                    campaignGamesetrewardListNewData2.add(campaignGamesetreward);
                                }else  if("条件三".equals(prizeName2)){
                                    campaignGamesetreward.setPrizeOrder(3);
                                    campaignGamesetreward.setList(campaignCouponList3);
                                    campaignGamesetrewardListNewData3.add(campaignGamesetreward);
                                }else  if("点赞人奖励".equals(prizeName2)){
                                    campaignGamesetreward.setRewardType("0");
                                    campaignGamesetreward.setPrizeOrder(0);
                                    campaignGamesetreward.setPrizeCount(0);
                                    campaignGamesetreward.setList(campaignCouponList4);
                                    campaignGamesetrewardListNewData4.add(campaignGamesetreward);
                                }
                            }
                        }
                    }

                    for (PrizeBean prizeBean:prizeBeanList){
                        String prizeName = prizeBean.getName();
                        if("条件一".equals(prizeName)){
                            if(campaignGamesetrewardListNewData1 != null && campaignGamesetrewardListNewData1.size()>0){
                                campaignGamesetrewardListNewData.addAll(campaignGamesetrewardListNewData1);
                            }else {
                                couponList1 = new ArrayList<>();
                                CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();
                                campaignGamesetreward.setPrizeOrder(1);
                                campaignGamesetreward.setPrizeCount(2);
                                campaignGamesetreward.setPrizeName(prizeName);
                                campaignGamesetreward.setRewardType("1");
                                campaignGamesetrewardListNewData.add(campaignGamesetreward);
                            }
                        }else  if("条件二".equals(prizeName)){
                            if(campaignGamesetrewardListNewData2 != null && campaignGamesetrewardListNewData2.size()>0){
                                campaignGamesetrewardListNewData.addAll(campaignGamesetrewardListNewData2);
                            }else {
                                couponList2= new ArrayList<>();
                                CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();
                                campaignGamesetreward.setPrizeOrder(2);
                                campaignGamesetreward.setPrizeCount(2);
                                campaignGamesetreward.setPrizeName(prizeName);
                                campaignGamesetreward.setRewardType("1");
                                campaignGamesetrewardListNewData.add(campaignGamesetreward);
                            }
                        }else  if("条件三".equals(prizeName)){
                            if(campaignGamesetrewardListNewData3 != null && campaignGamesetrewardListNewData3.size()>0){
                                campaignGamesetrewardListNewData.addAll(campaignGamesetrewardListNewData3);
                            }else {
                                couponList3 = new ArrayList<>();
                                CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();
                                campaignGamesetreward.setPrizeOrder(3);
                                campaignGamesetreward.setPrizeCount(2);
                                campaignGamesetreward.setPrizeName(prizeName);
                                campaignGamesetreward.setRewardType("1");
                                campaignGamesetrewardListNewData.add(campaignGamesetreward);
                            }
                        }else  if("点赞人奖励".equals(prizeName)){
                            if(campaignGamesetrewardListNewData4 != null && campaignGamesetrewardListNewData4.size()>0){
                                campaignGamesetrewardListNewData.addAll(campaignGamesetrewardListNewData4);
                            }else {
                                couponList4 = new ArrayList<>();
                                CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();
                                campaignGamesetreward.setPrizeOrder(0);
                                campaignGamesetreward.setPrizeCount(0);
                                campaignGamesetreward.setPrizeName(prizeName);
                                campaignGamesetreward.setRewardType("0");
                                campaignGamesetrewardListNewData.add(campaignGamesetreward);
                            }
                        }

                    }
                    campaignGamesetrewardList = campaignGamesetrewardListNewData;

                }


                pagerAdapter = new FPagerAdapter(getSupportFragmentManager(),prizeBeanList);

                viewPager.setAdapter(pagerAdapter);
                tabLayout.setViewPager(viewPager);
                viewPager.setCurrentItem(0);
                tabLayout.setCurrentTab(0,true);
                tabLayout.setTextSelectColor(getResources().getColor(R.color.font_color_blue));

            }
        }else if(resultCode == 7777) {
            ArrayList<Coupon>   selectCouponList = new ArrayList<>();
            String name = data.getStringExtra("prizeName");
            CampaignCouponListFragment.selectCoupons.clear();
            if(requestCode == 7771){
                if("条件一".equals(name)){
                    ArrayList<Coupon> couponList  = (ArrayList<Coupon>)data.getSerializableExtra("selectCouponList");
                    if(couponList != null && couponList.size()>0) {
                        couponList1.addAll(couponList);
                        for (Coupon coupon : couponList) {
                            CampaignCoupon campaignCoupon = new CampaignCoupon();
                            campaignCoupon.setStores(null);
                            campaignCoupon.setExpireEndDate("");
                            campaignCoupon.setExpireStartDate("");
                            campaignCoupon.setExpireStartType("R1");
                            campaignCoupon.setExpireEndType("R1");
                            campaignCoupon.setExpireStartDate("");
                            campaignCoupon.setExpireEndDate("");
                            campaignCoupon.setExpireEndDay("1");
                            campaignCoupon.setExpireStartDay("0");
                            campaignCoupon.setStoreStr("");
                            campaignCoupon.setStoreSelectType("A");
                            campaignCoupon.setObject(coupon.getObject());
                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setCouponId(coupon.getId());
                            campaignCoupon.setDiscount(coupon.getDiscount());
                            campaignCoupon.setMoney(coupon.getMoney());
                            campaignCoupon.setNoDate(coupon.getNoDate());
                            campaignCoupon.setNoItem(coupon.getNoItem());
                            campaignCoupon.setDateCode(coupon.getDateCode());
                            campaignCoupon.setOther(coupon.getOther());
                            campaignCoupon.setLimit(coupon.getLimit());
                            campaignCoupon.setType(coupon.getType());
                            campaignCoupon.setTimePeriod(coupon.getTimePeriod());
                            CouponMaterial material = coupon.getMaterial();
                            if(material != null){
                                campaignCoupon.setBackgroundThumbnail(material.getBackgroundThumbnail());
                                campaignCoupon.setBackground(material.getBackground());
                            }
                            campaignCouponList1.add(campaignCoupon);
                        }
                    }

                    if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0) {
                        for (CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList) {
                            String prizeName = campaignGamesetreward.getPrizeName();
                            if ("条件一".equals(prizeName)) {
                                campaignGamesetreward.setList(campaignCouponList1);
                            }
                        }
                    }

                }
                pagerAdapter.updateData(prizeBeanList);
            }else if(requestCode == 7772){
                if("条件二".equals(name)){
                    ArrayList<Coupon>  couponList = (ArrayList<Coupon>)data.getSerializableExtra("selectCouponList");
//                    removeSelectCouponList(couponList2);
                    if(couponList != null && couponList.size()>0) {
                        couponList2.addAll(couponList);
                        for (Coupon coupon : couponList) {
                            CampaignCoupon campaignCoupon = new CampaignCoupon();
                            campaignCoupon.setStores(null);
                            campaignCoupon.setExpireStartType("R1");
                            campaignCoupon.setExpireEndType("R1");
                            campaignCoupon.setExpireStartDate("");
                            campaignCoupon.setExpireEndDate("");
                            campaignCoupon.setExpireEndDay("1");
                            campaignCoupon.setExpireStartDay("0");
                            campaignCoupon.setStoreStr("");
                            campaignCoupon.setStoreSelectType("A");
                            campaignCoupon.setObject(coupon.getObject());
                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setCouponId(coupon.getId());
                            campaignCoupon.setDiscount(coupon.getDiscount());
                            campaignCoupon.setMoney(coupon.getMoney());
                            campaignCoupon.setNoDate(coupon.getNoDate());
                            campaignCoupon.setNoItem(coupon.getNoItem());
                            campaignCoupon.setDateCode(coupon.getDateCode());
                            campaignCoupon.setOther(coupon.getOther());
                            campaignCoupon.setLimit(coupon.getLimit());
                            campaignCoupon.setType(coupon.getType());
                            campaignCoupon.setTimePeriod(coupon.getTimePeriod());
                            CouponMaterial material = coupon.getMaterial();
                            if(material != null){
                                campaignCoupon.setBackgroundThumbnail(material.getBackgroundThumbnail());
                                campaignCoupon.setBackground(material.getBackground());
                            }

                            if(campaignCouponList2 == null || campaignCouponList2.size() ==0){
                                campaignCouponList2 = new ArrayList<>();
                            }
                            campaignCouponList2.add(campaignCoupon);
                        }
                    }

                    if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0) {
                        for (CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList) {
                            String prizeName = campaignGamesetreward.getPrizeName();
                            if ("条件二".equals(prizeName)&&name.equals(prizeName)) {
                                campaignGamesetreward.setList(campaignCouponList2);
                            }
                        }
                    }

                }
                pagerAdapter.updateData(prizeBeanList);
            }else if(requestCode == 7773){
                if("条件三".equals(name)){
                    ArrayList<Coupon> couponList = (ArrayList<Coupon>)data.getSerializableExtra("selectCouponList");
                    if(couponList != null && couponList.size()>0) {
                        couponList3.addAll(couponList);
                        for (Coupon coupon : couponList) {
                            CampaignCoupon campaignCoupon = new CampaignCoupon();
                            campaignCoupon.setStores(null);
                            campaignCoupon.setExpireStartType("R1");
                            campaignCoupon.setExpireEndType("R1");
                            campaignCoupon.setExpireStartDate("");
                            campaignCoupon.setExpireEndDate("");
                            campaignCoupon.setExpireEndDay("1");
                            campaignCoupon.setExpireStartDay("0");
                            campaignCoupon.setStoreStr("");
                            campaignCoupon.setStoreSelectType("A");
                            campaignCoupon.setObject(coupon.getObject());
                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setCouponId(coupon.getId());
                            campaignCoupon.setDiscount(coupon.getDiscount());
                            campaignCoupon.setMoney(coupon.getMoney());
                            campaignCoupon.setNoDate(coupon.getNoDate());
                            campaignCoupon.setNoItem(coupon.getNoItem());
                            campaignCoupon.setDateCode(coupon.getDateCode());
                            campaignCoupon.setOther(coupon.getOther());
                            campaignCoupon.setLimit(coupon.getLimit());
                            campaignCoupon.setType(coupon.getType());
                            campaignCoupon.setTimePeriod(coupon.getTimePeriod());
                            CouponMaterial material = coupon.getMaterial();
                            if(material != null){
                                campaignCoupon.setBackgroundThumbnail(material.getBackgroundThumbnail());
                                campaignCoupon.setBackground(material.getBackground());
                            }
                            if(campaignCouponList3 == null || campaignCouponList3.size() ==0){
                                campaignCouponList3 = new ArrayList<>();
                            }
                            campaignCouponList3.add(campaignCoupon);
                        }
                    }

                    if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0) {
                        for (CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList) {
                            String prizeName = campaignGamesetreward.getPrizeName();
                            if ("条件三".equals(prizeName)) {
                                campaignGamesetreward.setList(campaignCouponList3);
                            }
                        }
                    }

                }
                pagerAdapter.updateData(prizeBeanList);
            }else if(requestCode == 7774){

                if("点赞人奖励".equals(name)){
                    ArrayList<Coupon>  couponList = (ArrayList<Coupon>)data.getSerializableExtra("selectCouponList");
//                    removeSelectCouponList(couponList4);
                    if(couponList != null && couponList.size()>0) {
                        couponList4.addAll(couponList);
                        for (Coupon coupon : couponList) {
                            CampaignCoupon campaignCoupon = new CampaignCoupon();
                            campaignCoupon.setStores(null);
                            campaignCoupon.setExpireEndDate("");
                            campaignCoupon.setExpireStartDate("");
                            campaignCoupon.setExpireEndType("R1");
                            campaignCoupon.setExpireStartType("R1");
                            campaignCoupon.setExpireEndDay("1");
                            campaignCoupon.setExpireStartDay("0");
                            campaignCoupon.setStoreStr("");
                            campaignCoupon.setStoreSelectType("A");
                            campaignCoupon.setObject(coupon.getObject());
                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setCouponId(coupon.getId());
                            campaignCoupon.setDiscount(coupon.getDiscount());
                            campaignCoupon.setMoney(coupon.getMoney());
                            campaignCoupon.setNoDate(coupon.getNoDate());
                            campaignCoupon.setNoItem(coupon.getNoItem());
                            campaignCoupon.setDateCode(coupon.getDateCode());
                            campaignCoupon.setOther(coupon.getOther());
                            campaignCoupon.setLimit(coupon.getLimit());
                            campaignCoupon.setType(coupon.getType());
                            campaignCoupon.setTimePeriod(coupon.getTimePeriod());
                            CouponMaterial material = coupon.getMaterial();
                            if(material != null){
                                campaignCoupon.setBackgroundThumbnail(material.getBackgroundThumbnail());
                                campaignCoupon.setBackground(material.getBackground());
                            }

                            if(campaignCouponList4 == null || campaignCouponList4.size() ==0){
                                campaignCouponList4 = new ArrayList<>();
                            }
                            campaignCouponList4.add(campaignCoupon);
                        }
                    }

                    if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0) {
                        for (CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList) {
                            String prizeName = campaignGamesetreward.getPrizeName();
                            if ("点赞人奖励".equals(prizeName)) {
                                campaignGamesetreward.setList(campaignCouponList4);
                            }
                        }
                    }

                }
                pagerAdapter.updateData(prizeBeanList);
            }

        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.startActivity(CampaignListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
        finish();

    }

    public   class FPagerAdapter extends FragmentPagerAdapter {
        private long baseId = 0;
        private ArrayList<Fragment> mFragmentList;
        private FragmentManager mFragmentManager;
        private List<PrizeBean> prizeBeanList = null;

        private  boolean isAdd = false;
        private List<String> tags;//标示fragment的tag
        public FPagerAdapter(FragmentManager fm, List<PrizeBean> dataList) {
            super(fm);
            this.mFragmentManager = fm;
            mFragmentList = new ArrayList<>();
            this.prizeBeanList = dataList;
            this.tags = new ArrayList<>();
            if(prizeBeanList != null && prizeBeanList.size()>0){
                for (PrizeBean prizeBean : prizeBeanList){
                    String name = prizeBean.getName();
                    if("条件一".equals(name)){
                        mFragmentList.add(CampaignLikeTwoFragment.getInstance(name,couponList1));
                    }else if("条件二".equals(name)){
                        mFragmentList.add(CampaignLikeTwoFragment.getInstance(name,couponList2));
                    }else if("条件三".equals(name)){
                        mFragmentList.add(CampaignLikeTwoFragment.getInstance(name,couponList3));
                    }else if("点赞人奖励".equals(name)){
                        mFragmentList.add(CampaignLikeTwoFragment.getInstance(name,couponList4));
                    }
                }
            }
            setFragments(mFragmentList);
        }

        public void updateData(List<PrizeBean> dataList) {
            ArrayList<Fragment> fragments = new ArrayList<>();
            if(dataList == null){
                dataList = new ArrayList<>();
            }
            if(dataList != null && dataList.size()>0){
                for (PrizeBean prizeBean : dataList){

                    String name = prizeBean.getName();
                    if("条件一".equals(name)){
                        fragments.add(CampaignLikeTwoFragment.getInstance(name,couponList1));
                    }else if("条件二".equals(name)){
                        fragments.add(CampaignLikeTwoFragment.getInstance(name,couponList2));
                    }else if("条件三".equals(name)){
                        fragments.add(CampaignLikeTwoFragment.getInstance(name,couponList3));
                    }else if("点赞人奖励".equals(name)){
                        fragments.add(CampaignLikeTwoFragment.getInstance(name,couponList4));
                    }

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
            super.getItemId(position);
            return mFragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return prizeBeanList.get(position).getName();
        }

        /**
         *
         * 解决切换条件tab页 决定是否重新加载新的Fragment，于是需要在自定的FragmentPagerAdapter中重写getItemId，得到不同的itemId。
         * 不同的Fragment分配的HashCode不同，从而实现刷新adapter中的fragment
         * */
        @Override
        public long getItemId(int position) {
            super.getItemId(position);
            if (mFragmentList != null){
                if(position<mFragmentList.size()){
                    return mFragmentList.get(position).hashCode();
                }
            }
            return super.getItemId(position);

        }
    }

    protected void removeSelectCouponList(ArrayList<Coupon> couponList){
        if(couponList != null && couponList.size()>0){
            for(int i=0;i<couponList.size();i++){
                for(int j=couponList.size()-1;j>i;j--){
                    if(String.valueOf(couponList.get(j).getId()).equals(String.valueOf(couponList.get(i).getId()))){
                        couponList.remove(j);
                    }
                }
            }
        }

    }
    protected  void copyAndCreate(){
        //复制并创建
        isCopyCreate = "NO";
        isEditCampaign = "YES";
        CampaignLikeNewActivity.compaignStatus = "NEW";

        removeCampaignGamesetrewardList();

        if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0){
            for (CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList){
                if (campaignGamesetreward != null ){
                    String name = campaignGamesetreward.getPrizeName();
                    String picPictureId = null;
                    if("条件一".equals(name)){

                        campaignGamesetreward.setRewardType("1");
                        campaignGamesetreward.setPrizeOrder(1);
                        campaignGamesetreward.setPrizeImageUrl(picPictureId);
                        if(campaignCouponList1 != null && campaignCouponList1.size()>0) {
                            for (CampaignCoupon campaignCoupon : campaignCouponList1) {
                                String storeid = campaignCoupon.getStoreStr();

                                if(storeid!= null && !"".equals(storeid)){
                                    if (storeid.contains(",")) {
                                        campaignCoupon.setStoreStr(storeid.replace(",", "-"));
                                    }
                                }

                            }
                        }
                        campaignGamesetreward.setList(campaignCouponList1);
                    }else if("条件二".equals(name)){

                        campaignGamesetreward.setRewardType("1");
                        campaignGamesetreward.setPrizeOrder(2);
                        campaignGamesetreward.setPrizeImageUrl(picPictureId);
                        if(campaignCouponList2 != null && campaignCouponList2.size()>0) {
                            for (CampaignCoupon campaignCoupon : campaignCouponList2) {
                                String storeid = campaignCoupon.getStoreStr();

                                if(storeid!= null && !"".equals(storeid)){
                                    if (storeid.contains(",")) {
                                        campaignCoupon.setStoreStr(storeid.replace(",", "-"));
                                    }
                                }

                            }
                        }
                        campaignGamesetreward.setList(campaignCouponList2);
                    }else if("条件三".equals(name)){

                        campaignGamesetreward.setRewardType("1");
                        campaignGamesetreward.setPrizeOrder(3);
                        campaignGamesetreward.setPrizeImageUrl(picPictureId);
                        if(campaignCouponList3 != null && campaignCouponList3.size()>0) {
                            for (CampaignCoupon campaignCoupon : campaignCouponList3) {
                                String storeid = campaignCoupon.getStoreStr();

                                if(storeid!= null && !"".equals(storeid)){
                                    if (storeid.contains(",")) {
                                        campaignCoupon.setStoreStr(storeid.replace(",", "-"));
                                    }
                                }

                            }
                        }
                        campaignGamesetreward.setList(campaignCouponList3);
                    }else if("点赞人奖励".equals(name)){
                        campaignGamesetreward.setPrizeOrder(null);
                        campaignGamesetreward.setRewardType("0");
                        campaignGamesetreward.setPrizeImageUrl(picPictureId);
                        if(campaignCouponList4 != null && campaignCouponList4.size()>0) {
                            for (CampaignCoupon campaignCoupon : campaignCouponList4) {
                                String storeid = campaignCoupon.getStoreStr();

                                if(storeid!= null && !"".equals(storeid)){
                                    if (storeid.contains(",")) {
                                        campaignCoupon.setStoreStr(storeid.replace(",", "-"));
                                    }
                                }

                            }
                        }

                        campaignGamesetreward.setList(campaignCouponList4);
                    }
                }
            }
        }

        CampaignLikeNewActivity.compaignStatus = "NEW";
     

        if(prizeBeanList != null && prizeBeanList.size()>0){
//            SPUtils.getInstance().putListData("prizeBeanList",prizeBeanList);
        }
        if(CampaignLikeNewActivity.campaign != null){
            CampaignLikeNewActivity.campaign.setCampaignId(null);
            CampaignLikeNewActivity.campaign.setCampaignName("");
        }

        Intent intent1 = new Intent(CampaignLikeTwoActivity.this,CampaignLikeNewActivity.class);

        intent1.putExtra("title",title);
        intent1.putExtra("isCopyCreate",isCopyCreate);
        intent1.putExtra("isEditCampaign",isEditCampaign);
        intent1.putExtra("campaignType",CampaignLikeNewActivity.campaignType);
        intent1.putExtra("compaignStatus",CampaignLikeNewActivity.compaignStatus);
        ActivityUtils.startActivity(intent1,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }
    protected   void setIntentNextActivity() {
        intent = new Intent(CampaignLikeTwoActivity.this,CampaignLikeThreeActivity.class);
        removeCampaignGamesetrewardList();

        if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0){
            for (CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList){
                if (campaignGamesetreward != null ){
                    String name = campaignGamesetreward.getPrizeName();
                    Integer prizeCount = campaignGamesetreward.getPrizeCount();
                    if(prizeCount != null && prizeCount.intValue()>0){
                        if("条件一".equals(name)){
                            prizeCount1 = String.valueOf(prizeCount);

                        }else  if("条件二".equals(name)){
                            prizeCount2= String.valueOf(prizeCount);
                        }else  if("条件三".equals(name)){
                            prizeCount3= String.valueOf(prizeCount);
                        }
                    }else{
                        if("条件一".equals(name)){
                            prizeCount1 = "";

                        }else  if("条件二".equals(name)){
                            prizeCount2= "";
                        }else  if("条件三".equals(name)){
                            prizeCount3="";
                        }
                    }
                  
                    if("条件一".equals(name)){
                      
                        campaignGamesetreward.setRewardType("1");
                        campaignGamesetreward.setPrizeOrder(1);

                        if(campaignCouponList1 != null && campaignCouponList1.size()>0) {
                            for (CampaignCoupon campaignCoupon : campaignCouponList1) {
                                String storeid = campaignCoupon.getStoreStr();

                                if(storeid!= null && !"".equals(storeid)){
                                    if (storeid.contains(",")) {
                                        campaignCoupon.setStoreStr(storeid.replace(",", "-"));
                                    }
                                }

                            }
                        }
                        campaignGamesetreward.setList(campaignCouponList1);
                    }else if("条件二".equals(name)){
                        
                        campaignGamesetreward.setRewardType("1");
                        campaignGamesetreward.setPrizeOrder(2);

                        if(campaignCouponList2 != null && campaignCouponList2.size()>0) {
                            for (CampaignCoupon campaignCoupon : campaignCouponList2) {
                                String storeid = campaignCoupon.getStoreStr();

                                if(storeid!= null && !"".equals(storeid)){
                                    if (storeid.contains(",")) {
                                        campaignCoupon.setStoreStr(storeid.replace(",", "-"));
                                    }
                                }

                            }
                        }
                        campaignGamesetreward.setList(campaignCouponList2);
                    }else if("条件三".equals(name)){
                      
                        campaignGamesetreward.setRewardType("1");
                        campaignGamesetreward.setPrizeOrder(3);
                        if(campaignCouponList3 != null && campaignCouponList3.size()>0) {
                            for (CampaignCoupon campaignCoupon : campaignCouponList3) {
                                String storeid = campaignCoupon.getStoreStr();

                                if(storeid!= null && !"".equals(storeid)){
                                    if (storeid.contains(",")) {
                                        campaignCoupon.setStoreStr(storeid.replace(",", "-"));
                                    }
                                }

                            }
                        }
                        campaignGamesetreward.setList(campaignCouponList3);
                    }else if("点赞人奖励".equals(name)){
                       
                        campaignGamesetreward.setRewardType("0");
                        campaignGamesetreward.setPrizeOrder(0);
                        if(campaignCouponList4!= null && campaignCouponList4.size()>0) {
                            for (CampaignCoupon campaignCoupon : campaignCouponList4) {
                                String storeid = campaignCoupon.getStoreStr();

                                if(storeid!= null && !"".equals(storeid)){
                                    if (storeid.contains(",")) {
                                        campaignCoupon.setStoreStr(storeid.replace(",", "-"));
                                    }
                                }

                            }
                        }
                        campaignGamesetreward.setList(campaignCouponList4);
                    }
                }

            }
            if((prizeCount2 != null && !"".equals(prizeCount2)) && (prizeCount3 != null && !"".equals(prizeCount3))){
                int pCount2 = Integer.parseInt(prizeCount2);
                int pCount3 = Integer.parseInt(prizeCount3);

                if(pCount2>0 && pCount3>0){
                    if(pCount2<pCount3){
                    }else {
                        ToastUtils.showLong(R.string.campaign_like_coupon_tip);
                        return;
                    }
                }

            }

            if((prizeCount1 != null && !"".equals(prizeCount1)) && (prizeCount3 != null && !"".equals(prizeCount3))){
                int pCount1 = Integer.parseInt(prizeCount1);
                int pCount3 = Integer.parseInt(prizeCount3);

                if(pCount1>0 && pCount3>0){
                    if(pCount1<pCount3){
                    }else {
                        ToastUtils.showLong(R.string.campaign_like_coupon_tip);
                        return;
                    }
                }

            }

            if((prizeCount1 != null && !"".equals(prizeCount1)) && (prizeCount2 != null && !"".equals(prizeCount2))){
                int pCount1 = Integer.parseInt(prizeCount1);
                int pCount2 = Integer.parseInt(prizeCount2);

                if(pCount1>0 && pCount2>0){
                    if(pCount1<pCount2){
                    }else {
                        ToastUtils.showLong(R.string.campaign_like_coupon_tip);
                        return;
                    }
                }

            }

        }
        intent.putExtra("title",title);
        intent.putExtra("isCopyCreate",isCopyCreate);
        intent.putExtra("isEditCampaign",isEditCampaign);

        ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);

    }
    protected   void setIntentUpActivity(){
        //删除重复数据
        removeCampaignGamesetrewardList();
        if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0){
            for(CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList){
                String name = campaignGamesetreward.getPrizeName();
                String picPictureId = null;
                if("条件一".equals(name)){
                    if(campaignCouponList1 != null && campaignCouponList1.size()>0){
                        for (CampaignCoupon campaignCoupon : campaignCouponList1) {
                            String storeid = campaignCoupon.getStoreStr();

                            if(storeid!= null && !"".equals(storeid)){
                                if (storeid.contains(",")) {
                                    campaignCoupon.setStoreStr(storeid.replace(",", "-"));
                                }
                            }

                        }
                    }
                    campaignGamesetreward.setRewardType("1");
                    campaignGamesetreward.setPrizeOrder(1);
                    campaignGamesetreward.setList(campaignCouponList1);
                }else if("条件二".equals(name)){

                    campaignGamesetreward.setRewardType("1");
                    campaignGamesetreward.setPrizeOrder(2);
                    if(campaignCouponList2 != null && campaignCouponList2.size()>0){
                        for (CampaignCoupon campaignCoupon : campaignCouponList2) {
                            String storeid = campaignCoupon.getStoreStr();

                            if(storeid!= null && !"".equals(storeid)){
                                if (storeid.contains(",")) {
                                    campaignCoupon.setStoreStr(storeid.replace(",", "-"));
                                }
                            }

                        }
                    }
                    campaignGamesetreward.setList(campaignCouponList2);
                }else if("条件三".equals(name)){

                    campaignGamesetreward.setRewardType("1");
                    campaignGamesetreward.setPrizeOrder(3);
                    if(campaignCouponList3 != null && campaignCouponList3.size()>0){
                        for (CampaignCoupon campaignCoupon : campaignCouponList3) {
                            String storeid = campaignCoupon.getStoreStr();

                            if(storeid!= null && !"".equals(storeid)){
                                if (storeid.contains(",")) {
                                    campaignCoupon.setStoreStr(storeid.replace(",", "-"));
                                }
                            }

                        }
                    }
                    campaignGamesetreward.setList(campaignCouponList3);
                }else if("点赞人奖励".equals(name)){
                    campaignGamesetreward.setRewardType("0");
                    campaignGamesetreward.setPrizeOrder(0);
                    if(campaignCouponList4 != null && campaignCouponList4.size()>0){
                        for (CampaignCoupon campaignCoupon : campaignCouponList4) {
                            String storeid = campaignCoupon.getStoreStr();

                            if(storeid!= null && !"".equals(storeid)){
                                if (storeid.contains(",")) {
                                    campaignCoupon.setStoreStr(storeid.replace(",", "-"));
                                }
                            }

                        }
                    }
                    campaignGamesetreward.setList(campaignCouponList4);
                }

            }


        }
        ActivityUtils.finishActivity(CampaignLikeTwoActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }


    protected void removeCampaignCouponList(ArrayList<CampaignCoupon> campaignCouponList){
        if(campaignCouponList != null && campaignCouponList.size()>0){
            for(int i=0;i<campaignCouponList.size();i++){
                for(int j=campaignCouponList.size()-1;j>i;j--){
                    if((campaignCouponList.get(i).getCouponId().equals(campaignCouponList.get(j).getCouponId()))){
                        campaignCouponList.remove(j);
                    }
                }
            }
        }

    }
    protected void removeCampaignGamesetrewardList(){
        if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0){
            for(int i=0;i<campaignGamesetrewardList.size();i++){
                for(int j=campaignGamesetrewardList.size()-1;j>i;j--){
                    if((campaignGamesetrewardList.get(i).getPrizeName().equals(campaignGamesetrewardList.get(j).getPrizeName()))){
                        campaignGamesetrewardList.remove(j);
                    }
                }
            }
        }

    }
}
