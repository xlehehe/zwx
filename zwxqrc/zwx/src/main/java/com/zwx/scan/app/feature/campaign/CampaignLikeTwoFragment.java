package com.zwx.scan.app.feature.campaign;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CampaignGamesetreward;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.CouponMaterial;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.widget.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * author : lizhilong
 * time   : 2019/04/08
 * desc   :  集赞活动第二步
 * version: 1.0
 **/
public class CampaignLikeTwoFragment extends BaseFragment implements View.OnClickListener,CampaignLikeTwoCouponFragment.ChanageLayoutLisenter {

    @BindView(R.id.btn_add_coupon)
    protected Button btnAddCoupon;

    @BindView(R.id.coupon_view_pager)
    protected CustomViewPager couponViewPager;

    @BindView(R.id.setting_view_pager)
    protected NoScrollViewPager setViewPager;
    @BindView(R.id.dots)
    protected LinearLayout mDotsLayout;
    @BindView(R.id.ll_add_top)
    protected LinearLayout llAddTop;

    @BindView(R.id.ll_bottom_coupon_edit)
    protected LinearLayout llBottom;
    @BindView(R.id.ll_prize_count)
    protected LinearLayout ll_prize_count;

    //集赞数量
    @BindView(R.id.edt_num)
    protected EditText edtNum;

    @BindView(R.id.fab)
    protected ImageButton fab;

    //发放条件
    private int getValidDay  = 0 ;
    public String getValidDayStr ;
    private String validDaysStr = "";
    protected ArrayList<Coupon> couponList = new ArrayList<>();

    protected   String isEditCampaign = "NO";

    protected String isCopyCreate ;
    protected ArrayList<Coupon> couponList1 = new ArrayList<>();
    protected ArrayList<Coupon> couponList2 = new ArrayList<>();
    protected ArrayList<Coupon> couponList3 = new ArrayList<>();
    protected ArrayList<Coupon> couponList4 = new ArrayList<>();

    private String name;

    private String prizeCount; //条件次数
    private Integer count;

    protected CouponPagerAdapter pagerAdapter = null;
    protected CouponSettPagerAdapter setPagerAdapter = null;
    private List<ImageView> mDotsIV = new ArrayList<>();

    private CampaignLikeTwoCouponSettingFragment nextSettingFragment = null;
    private CampaignLikeTwoActivity activity = null;

    protected CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();
    protected  static ArrayList<CampaignCoupon> rewardCouponList = new ArrayList<>();





    public CampaignLikeTwoFragment() {
    }
    public static CampaignLikeTwoFragment getInstance(String name, ArrayList<Coupon> couponList){
        CampaignLikeTwoFragment nextTwoFragment = new CampaignLikeTwoFragment();
        nextTwoFragment.name = name;
        nextTwoFragment.couponList = couponList;

        return nextTwoFragment;

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //注销注册
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(LhPtEventBus event){

        if(event != null){
            this.name = event.getName();

            if("条件一".equals(name)){ //条件1 ~4
                couponList1  = CampaignLikeTwoActivity.couponList1;
                if(couponList1!=null && couponList1.size()>0 ){
                    llAddTop.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
                    if("S".equals(CampaignLikeNewActivity.compaignStatus)||"NEW".equals(CampaignLikeNewActivity.compaignStatus)){
                        fab.setVisibility(View.VISIBLE);
                    }else {
                        fab.setVisibility(View.GONE);
                    }
                }else {
                    llAddTop.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                }
            }else if("条件二".equals(name)){ //条件1 ~4
                couponList2  = CampaignLikeTwoActivity.couponList2;
                if(couponList2!=null && couponList2.size()>0 ){
                    llAddTop.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
                    if("S".equals(CampaignLikeNewActivity.compaignStatus)||"NEW".equals(CampaignLikeNewActivity.compaignStatus)){
                        fab.setVisibility(View.VISIBLE);
                    }else {
                        fab.setVisibility(View.GONE);
                    }
                }else {
                    llAddTop.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                }
            }else if("条件三".equals(name)){ //条件1 ~4
                couponList3  = CampaignLikeTwoActivity.couponList3;
                if(couponList3!=null && couponList3.size()>0 ){
                    llAddTop.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
                    if("S".equals(CampaignLikeNewActivity.compaignStatus)||"NEW".equals(CampaignLikeNewActivity.compaignStatus)){
                        fab.setVisibility(View.VISIBLE);
                    }else {
                        fab.setVisibility(View.GONE);
                    }

                }else {
                    llAddTop.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                }
            }else if("点赞人奖励".equals(name)){ //条件1 ~4
                couponList4  = CampaignLikeTwoActivity.couponList4;
                if(couponList4!=null && couponList4.size()>0 ){
                    llAddTop.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
                    if("S".equals(CampaignLikeNewActivity.compaignStatus)||"NEW".equals(CampaignLikeNewActivity.compaignStatus)){
                        fab.setVisibility(View.VISIBLE);
                    }else {
                        fab.setVisibility(View.GONE);
                    }
                }else {
                    llAddTop.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                }
            }

//            checkPrizeCount();

        }
    }


    @Override
    protected int getlayoutId() {
        return R.layout.fragment_campaign_like_two;
    }

    @Override
    protected void initInjector() {
      /*  DaggerCampaignsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignsModule(new CampaignsModule(this))
                .build()
                .inject(this);*/
    }




    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        nextSettingFragment = new CampaignLikeTwoCouponSettingFragment();
        activity = (CampaignLikeTwoActivity) getActivity();
        edtNum.setSelection(edtNum.getText().toString().length());

        if("S".equals(CampaignLikeNewActivity.compaignStatus)||"NEW".equals(CampaignLikeNewActivity.compaignStatus)){
            edtNum.setEnabled(true);
        }else {
            edtNum.setEnabled(false);
        }
        if("条件一".equals(name)){ //条件1 ~4
            couponList1 = CampaignLikeTwoActivity.couponList1;
            if(couponList1!=null && couponList1.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(CampaignLikeNewActivity.compaignStatus)||"NEW".equals(CampaignLikeNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
            ll_prize_count.setVisibility(View.VISIBLE);
        }else if("条件二".equals(name)){ //条件1 ~4
            couponList2 = CampaignLikeTwoActivity.couponList2;
            if(couponList2!=null && couponList2.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(CampaignLikeNewActivity.compaignStatus)||"NEW".equals(CampaignLikeNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
            ll_prize_count.setVisibility(View.VISIBLE);
        }else if("条件三".equals(name)){ //条件1 ~4
            couponList3 = CampaignLikeTwoActivity.couponList3;
            if(couponList3!=null && couponList3.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(CampaignLikeNewActivity.compaignStatus)||"NEW".equals(CampaignLikeNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }

            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
            ll_prize_count.setVisibility(View.VISIBLE);
        }else if("点赞人奖励".equals(name)){ //条件1 ~4
            ll_prize_count.setVisibility(View.GONE);
            couponList4 = CampaignLikeTwoActivity.couponList4;
            if(couponList4!=null && couponList4.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(CampaignLikeNewActivity.compaignStatus)||"NEW".equals(CampaignLikeNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
        }

    }

    @Override
    protected void initData() {

        prizeCount = edtNum.getText().toString().trim();
        setViewPagerAdapter();
        addPrizeCountListener();
        initCouponList();

    }


    private void  setCampaignGamesetrewardValue(){

        if(prizeCount!= null && !"".equals(prizeCount)){
            campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
        }else {
            campaignGamesetreward.setPrizeCount(2);
        }
        campaignGamesetreward.setPrizeName(name);

        if("条件一".equals(name)){
            campaignGamesetreward.setRewardType("1");
            campaignGamesetreward.setPrizeOrder(1);
            campaignGamesetreward.setList(CampaignLikeTwoActivity.campaignCouponList1);
        }else if("条件二".equals(name)){
            campaignGamesetreward.setRewardType("1");
            campaignGamesetreward.setPrizeOrder(2);
            campaignGamesetreward.setList(CampaignLikeTwoActivity.campaignCouponList2);
        }else if("条件三".equals(name)){
            campaignGamesetreward.setPrizeOrder(3);
            campaignGamesetreward.setRewardType("1");
            campaignGamesetreward.setList(CampaignLikeTwoActivity.campaignCouponList3);
        }else if("点赞人奖励".equals(name)){
            campaignGamesetreward.setRewardType("0");
            campaignGamesetreward.setPrizeOrder(0);
            campaignGamesetreward.setPrizeCount(null);
            campaignGamesetreward.setList(CampaignLikeTwoActivity.campaignCouponList4);
        }
    }


    private void addPrizeCountListener(){
        edtNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count1) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                prizeCount = edtNum.getText().toString().trim();
                if(prizeCount != null && !"".equals(prizeCount)) {
                    int count = Integer.parseInt(prizeCount);
                    if (count < 2) {
                        ToastUtils.showLong("集赞数量至少为2");
                        return;
                    }
                }else {
                    prizeCount = "2";
                }
                if(activity.campaignGamesetrewardList != null && activity.campaignGamesetrewardList.size()>0){
                    for (CampaignGamesetreward campaignGamesetreward : activity.campaignGamesetrewardList){

                        String prizeName = campaignGamesetreward.getPrizeName();
                        if("条件一".equals(prizeName) && name.equals(prizeName)){
                            if(prizeCount !="" && !"".equals(prizeCount)){
                                campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                            }
                            campaignGamesetreward.setPrizeName(prizeName);
                            campaignGamesetreward.setRewardType("1");
                            campaignGamesetreward.setList(CampaignLikeTwoActivity.campaignCouponList1);
                        }else if("条件二".equals(prizeName)&& name.equals(prizeName)){
                            if(prizeCount !="" && !"".equals(prizeCount)){
                                campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                            }
                            campaignGamesetreward.setPrizeName(prizeName);
                            campaignGamesetreward.setRewardType("1");
                            campaignGamesetreward.setList(CampaignLikeTwoActivity.campaignCouponList2);
                        }else if("条件三".equals(prizeName)&& name.equals(prizeName)){

                            if(prizeCount !="" && !"".equals(prizeCount)){
                                campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                            }
                            campaignGamesetreward.setPrizeName(prizeName);
                            campaignGamesetreward.setRewardType("1");
                            campaignGamesetreward.setList(CampaignLikeTwoActivity.campaignCouponList3);
                        }
                    }

                }
            }
        });
    }
    private  void checkPrizeCount(){
        prizeCount = edtNum.getText().toString().trim();
        if(prizeCount != null && !"".equals(prizeCount)){
            int count = Integer.parseInt(prizeCount);
            if(count<2){
                ToastUtils.showLong("集赞数量至少为2");
                return;
            }

            if("条件一".equals(name) ){
                activity.prizeCount1 = prizeCount;

                if(activity.prizeCount2 != null && !"".equals(activity.prizeCount2)){
                    int pCount2 = Integer.parseInt(activity.prizeCount2);
                    if(pCount2>0){
                        if(count<pCount2){
                        }else {
                            ToastUtils.showLong(R.string.campaign_like_coupon_tip);
                        }
                    }
                }

                if(activity.prizeCount3 != null && !"".equals(activity.prizeCount3)){
                    int pCount3 = Integer.parseInt(activity.prizeCount3);
                    if(pCount3>0){
                        if(count<pCount3){
                        }else {
                            ToastUtils.showLong(R.string.campaign_like_coupon_tip);
                        }
                    }
                }

                if((activity.prizeCount2 != null && !"".equals(activity.prizeCount2)) && (activity.prizeCount3 != null && !"".equals(activity.prizeCount3))){
                    int pCount2 = Integer.parseInt(activity.prizeCount2);
                    int pCount3 = Integer.parseInt(activity.prizeCount3);

                    if(pCount2>0 && pCount3>0){
                        if(count<pCount2 && pCount2<pCount3){
                        }else {
                            ToastUtils.showLong(R.string.campaign_like_coupon_tip);
                        }
                    }

                }
            }else if("条件二".equals(name)){
                activity.prizeCount2 = prizeCount;

                if(activity.prizeCount1 != null && !"".equals(activity.prizeCount1)){
                    int pCount1 = Integer.parseInt(activity.prizeCount1);
                    if(pCount1>0){
                        if(pCount1<count){
                        }else {
                            ToastUtils.showLong(R.string.campaign_like_coupon_tip);
//                                    return;
                        }
                    }
                }

                if(activity.prizeCount3 != null && !"".equals(activity.prizeCount3)){
                    int pCount3 = Integer.parseInt(activity.prizeCount3);
                    if(pCount3>0){
                        if(count<pCount3){
                        }else {
                            ToastUtils.showLong(R.string.campaign_like_coupon_tip);
//                                    return;
                        }
                    }
                }

                if((activity.prizeCount1 != null && !"".equals(activity.prizeCount1)) && (activity.prizeCount3 != null && !"".equals(activity.prizeCount3))){
                    int pCount1 = Integer.parseInt(activity.prizeCount1);
                    int pCount3 = Integer.parseInt(activity.prizeCount3);

                    if(pCount1>0 && pCount3>0){
                        if(pCount1<count && count<pCount3){
                        }else {
                            ToastUtils.showLong(R.string.campaign_like_coupon_tip);
                                    return;
                        }
                    }

                }
            }else if("条件三".equals(name)){
                activity.prizeCount3 = prizeCount;

                if(activity.prizeCount1 != null && !"".equals(activity.prizeCount1)){
                    int pCount1 = Integer.parseInt(activity.prizeCount1);
                    if(pCount1>0){
                        if(pCount1<count){
                        }else {
                            ToastUtils.showLong(R.string.campaign_like_coupon_tip);
//                                    return;
                        }
                    }
                }

                if(activity.prizeCount2 != null && !"".equals(activity.prizeCount2)){
                    int pCount2 = Integer.parseInt(activity.prizeCount2);
                    if(pCount2>0){
                        if(pCount2<count){
                        }else {
                            ToastUtils.showLong(R.string.campaign_like_coupon_tip);
//                                    return;
                        }
                    }
                }

                if((activity.prizeCount1 != null && !"".equals(activity.prizeCount1)) && (activity.prizeCount2 != null && !"".equals(activity.prizeCount2))){
                    int pCount1 = Integer.parseInt(activity.prizeCount1);
                    int pCount2 = Integer.parseInt(activity.prizeCount2);

                    if(pCount1>0 && pCount2>0){
                        if(pCount1<pCount2 && pCount2<count){
                        }else {
                            ToastUtils.showLong(R.string.campaign_like_coupon_tip);
                                    return;
                        }
                    }

                }
            }else if("点赞人奖励".equals(name)){
            }


        }else {
//                    ToastUtils.showLong("集赞数量至少为2");
           /* if("条件一".equals(name) ){
            }else if("条件二".equals(name)){
            }else if("条件三".equals(name)){
            }*/
            return;
        }

       /* if(activity.campaignGamesetrewardList != null && activity.campaignGamesetrewardList.size()>0){
            for (CampaignGamesetreward campaignGamesetreward : activity.campaignGamesetrewardList){

                String prizeName = campaignGamesetreward.getPrizeName();
                if("条件一".equals(prizeName) && name.equals(prizeName)){
                    campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                    campaignGamesetreward.setPrizeName(prizeName);
                    campaignGamesetreward.setRewardType("1");
                    campaignGamesetreward.setList(CampaignLikeTwoActivity.campaignCouponList1);
                }else if("条件二".equals(prizeName)&& name.equals(prizeName)){
                    campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                    campaignGamesetreward.setPrizeName(prizeName);
                    campaignGamesetreward.setRewardType("1");
                    campaignGamesetreward.setList(CampaignLikeTwoActivity.campaignCouponList2);
                }else if("条件三".equals(prizeName)&& name.equals(prizeName)){
                    campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                    campaignGamesetreward.setPrizeName(prizeName);
                    campaignGamesetreward.setRewardType("1");
                    campaignGamesetreward.setList(CampaignLikeTwoActivity.campaignCouponList3);
                }
            }

        }*/
    }

    private void initCouponList(){

        if(CampaignLikeTwoActivity.campaignGamesetrewardList != null && CampaignLikeTwoActivity.campaignGamesetrewardList.size()>0) {
            for (CampaignGamesetreward campaignGamesetreward : CampaignLikeTwoActivity.campaignGamesetrewardList) {
                String prizeName = campaignGamesetreward.getPrizeName();
                count = campaignGamesetreward.getPrizeCount();
                if("条件一".equals(prizeName)&& name.equals(prizeName)){
                    if(count != null && count.intValue()>0){
                        edtNum.setText(String.valueOf(count));
                    }
                    CampaignLikeTwoActivity.campaignCouponList1 = campaignGamesetreward.getList();

                    rewardCouponList =  CampaignLikeTwoActivity.campaignCouponList1;
                }else if("条件二".equals(prizeName)&& name.equals(prizeName)){
                    if(count != null && count.intValue()>0){
                        edtNum.setText(String.valueOf(count));
                    }
                    CampaignLikeTwoActivity.campaignCouponList2 = campaignGamesetreward.getList();
                    rewardCouponList =  CampaignLikeTwoActivity.campaignCouponList2;

                }else if("条件三".equals(prizeName)&& name.equals(prizeName)){
                    if(count != null && count.intValue()>0){
                        edtNum.setText(String.valueOf(count));
                    }

                    CampaignLikeTwoActivity.campaignCouponList3 = campaignGamesetreward.getList();
                    rewardCouponList =  CampaignLikeTwoActivity.campaignCouponList3;

                }else if("点赞人奖励".equals(prizeName)&& name.equals(prizeName)){
                    edtNum.setText("");
                    campaignGamesetreward.setPrizeCount(null);
                    CampaignLikeTwoActivity.campaignCouponList4= campaignGamesetreward.getList();
                    rewardCouponList =  CampaignLikeTwoActivity.campaignCouponList4;
                }
                edtNum.setSelection(edtNum.getText().toString().length());
                //本地缓存 提取
                if(rewardCouponList!=null && rewardCouponList.size()>0){

                    for (int i =0;i<rewardCouponList.size();i++){
                        CampaignCoupon campaignCoupon = rewardCouponList.get(i);
                        List<Store> storeList = campaignCoupon.getStores();
                        Coupon coupon = new Coupon();
                        StringBuilder storeIdSb = new StringBuilder();
                        StringBuilder storeNameSb = new StringBuilder();
                        if(storeList!=null && storeList.size()>0){
                            Store store1 = storeList.get(0);
                            if(store1 != null){
                                String storeIdCouponType = store1.getStoreSelectType();
                                String storeName = "";
                                String storeIdArr = "";
                                if("A".equals(storeIdCouponType)){
                                    storeName = "#全部店铺";
                                    storeIdArr = "";
                                }else if("D".equals(storeIdCouponType)){
                                    storeName = "#全部自营";
                                    storeIdArr = "";
                                }else if("J".equals(storeIdCouponType)){
                                    storeName = "#全部加盟";
                                    storeIdArr = "";
                                }else {
                                    storeIdCouponType = "H";
                                    for (Store store:storeList){
                                        store.setChecked(true);
                                        String storeId = store.getStoreId();
                                        String storeName2 = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                        storeIdSb.append(storeId).append("-");
                                        storeNameSb.append(storeName2);

                                    }
                                    storeName = storeNameSb.toString();

                                    String storeIdA= storeIdSb.toString();
                                    if(storeIdA != null  && !"".equals(storeIdA)){
                                        storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
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
                        coupon.setExpireStartDay(campaignCoupon.getExpireStartDay());
                        coupon.setExpireEndDay(campaignCoupon.getExpireEndDay());
                        /*if ("条件一".equals(prizeName) && name.equals(prizeName)) {

                            couponList1 = couponList;
                        } else if ("条件二".equals(prizeName)&& name.equals(prizeName)) {
                            couponList2 = couponList;
                        } else if ("条件三".equals(prizeName)&& name.equals(prizeName)) {

                            couponList3 = couponList;
                        } else if ("点赞人奖励".equals(prizeName)&& name.equals(prizeName)) {
//                            couponList4.add(coupon);
                            couponList4 = couponList;
                        }*/
                    }

                }
            }
            addCoupontList();
        }

    }


    protected  void initcampaignGamesetreward(){

        setCampaignGamesetrewardValue();
        //未从缓存中获取
        if("条件一".equals(name)){
            campaignGamesetreward.setPrizeOrder(1);
        }else if("条件二".equals(name)){
            campaignGamesetreward.setPrizeOrder(2);
        }else if("条件三".equals(name)){
            campaignGamesetreward.setPrizeOrder(3);
        }else if("点赞人奖励".equals(name)){
            campaignGamesetreward.setPrizeCount(null);
            campaignGamesetreward.setPrizeOrder(0);
        }
        CampaignLikeTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);


    }

    protected void removeCampaignGamesetrewardList(ArrayList<CampaignGamesetreward> campaignGamesetrewardList){
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

    private void addCoupontList(){

        if ("条件一".equals(name)) {
            couponList1 = CampaignLikeTwoActivity.couponList1;

            if(couponList1!=null && couponList1.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(CampaignLikeNewActivity.compaignStatus)||"NEW".equals(CampaignLikeNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
                setDotLayout(couponList1);

                pagerAdapter.updateData(couponList1);
                setPagerAdapter.updateData(couponList1);
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }
        } else if ("条件二".equals(name)) {
            couponList2 = CampaignLikeTwoActivity.couponList2;

            if(couponList2!=null && couponList2.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(CampaignLikeNewActivity.compaignStatus)||"NEW".equals(CampaignLikeNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
                setDotLayout(couponList2);

                pagerAdapter.updateData(couponList2);
                setPagerAdapter.updateData(couponList2);


            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }
        } else if ("条件三".equals(name)) {
            couponList3 = CampaignLikeTwoActivity.couponList3;

            if(couponList3!=null && couponList3.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(CampaignLikeNewActivity.compaignStatus)||"NEW".equals(CampaignLikeNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
                setDotLayout(couponList3);

                pagerAdapter.updateData(couponList3);
                setPagerAdapter.updateData(couponList3);


            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }
        } else if ("点赞人奖励".equals(name)) {
            couponList4 = CampaignLikeTwoActivity.couponList4;

            if(couponList4!=null && couponList4.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(CampaignLikeNewActivity.compaignStatus)||"NEW".equals(CampaignLikeNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
                setDotLayout(couponList4);

                pagerAdapter.updateData(couponList4);
                setPagerAdapter.updateData(couponList4);


            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }
        }
    }


    protected void setViewPagerAdapter() {
        pagerAdapter = new CouponPagerAdapter(getChildFragmentManager(), couponList);
        couponViewPager.setAdapter(pagerAdapter);
        couponViewPager.setCurrentItem(0);

        couponViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < mDotsIV.size(); i++) {
                    if (i == position) {
                        mDotsIV.get(i).setImageResource(R.drawable.dot_focus);
                    } else {
                        mDotsIV.get(i).setImageResource(R.drawable.dot_blur);
                    }
                }
                setViewPager.setCurrentItem(position);


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //底部优惠券设置内容
        setPagerAdapter = new CouponSettPagerAdapter(getChildFragmentManager(), couponList);
        setViewPager.setAdapter(setPagerAdapter);
        setViewPager.setCurrentItem(0);

        //活动优惠券底部设置
        setViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public   class CouponPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragmentList;
        private FragmentManager mFragmentManager;
        public CouponPagerAdapter(FragmentManager fm, List<Coupon> couponList) {
            super(fm);
            this.mFragmentManager = fm;
            mFragmentList = new ArrayList<>();
            if(couponList !=null && couponList.size()>0){
                for (int i = 0;i<couponList.size();i++){
                    Coupon coupon = couponList.get(i);
                    Fragment fragment = CampaignLikeTwoCouponFragment.getInstance(coupon,name);
                    mFragmentList.add(fragment);
                }
            }

            setFragments(mFragmentList);
        }

        public void updateData(List<Coupon> couponList) {
            ArrayList<Fragment> fragments = new ArrayList<>();
            if(couponList !=null && couponList.size()>0) {
                for (int i = 0;i<couponList.size();i++){
                    Coupon coupon = couponList.get(i);
                    Fragment fragment = CampaignLikeTwoCouponFragment.getInstance(coupon,name);
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
            if(mFragmentList.size()>0){
                return mFragmentList.get(position);
            }else {
                return null;
            }

        }


    }

    public   class CouponSettPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragmentList;
        private FragmentManager mFragmentManager;
        private Bundle bundle  = new Bundle();
        private  List<Coupon> couponList = null;
        public CouponSettPagerAdapter(FragmentManager fm, List<Coupon> couponList) {
            super(fm);
            this.mFragmentManager = fm;
            this.couponList = couponList;
            mFragmentList = new ArrayList<>();
            if(couponList !=null && couponList.size()>0){
                for (int i = 0;i<couponList.size();i++){
                    Coupon coupon = couponList.get(i);
                    Fragment fragment = CampaignLikeTwoCouponSettingFragment.getInstance(coupon,i,name,setViewPager);
                    mFragmentList.add(fragment);
                }
            }

            setFragments(mFragmentList);
        }

        public void updateData(List<Coupon> couponList) {
            ArrayList<Fragment> fragments = new ArrayList<>();
            if(couponList !=null && couponList.size()>0){
                for (int i = 0;i<couponList.size();i++){
                    Coupon coupon = couponList.get(i);
                    Fragment fragment = CampaignLikeTwoCouponSettingFragment.getInstance(coupon,i,name,setViewPager);
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


    }



    protected void setDotLayout(ArrayList<Coupon> dataList){
        //先删除
        mDotsIV.clear();
        mDotsLayout.removeAllViews();
        for (int i = 0; i < dataList.size(); i++) {
            ImageView dotIV = new ImageView(getActivity());
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


    private void setCampaignCoupon(int position){

        if("条件一".equals(name)){
            if(couponList1 != null && couponList1.size()>0){
                Coupon coupon = couponList1.get(position);
                if(CampaignLikeTwoActivity.campaignCouponList1!=null && CampaignLikeTwoActivity.campaignCouponList1.size()>0){
                    for(int i=0;i<CampaignLikeTwoActivity.campaignCouponList1.size();i++){
                        CampaignCoupon campaignCoupon = CampaignLikeTwoActivity.campaignCouponList1.get(i);

                        if(position == i){
                            campaignCoupon.setCouponId(coupon.getId());
                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setStoreSelectType(nextSettingFragment.couponStoreSelectType);
                            campaignCoupon.setStoreStr(nextSettingFragment.storeId);

                            if("A".equals(nextSettingFragment.expireEndType)){
                                String startDate = nextSettingFragment.startDate;
                                String endDate = nextSettingFragment.endDate;

                                campaignCoupon.setExpireStartDate(startDate!=null?startDate:"");
                                campaignCoupon.setExpireEndDate(endDate!=null?endDate:"");
                            }else if("R1".equals(nextSettingFragment.expireEndType)){
                                String startDay= nextSettingFragment.startDay;
                                String endDay = nextSettingFragment.endDay;
                                campaignCoupon.setExpireStartDay(startDay != null?startDay:"");
                                campaignCoupon.setExpireEndDay(endDay != null?endDay:"");

                            }
                        }

                    }
                }else {
                    CampaignCoupon campaignCoupon  = new CampaignCoupon();
                    campaignCoupon.setCouponId(coupon.getId());
                    campaignCoupon.setCouponName(coupon.getName());
                    campaignCoupon.setStoreSelectType(nextSettingFragment.couponStoreSelectType);
                    campaignCoupon.setStoreStr(nextSettingFragment.storeId);

                    if("A".equals(nextSettingFragment.expireEndType)){
                        String startDate = nextSettingFragment.startDate;
                        String endDate = nextSettingFragment.endDate;
                        campaignCoupon.setExpireStartDate(startDate!=null?startDate:"");
                        campaignCoupon.setExpireEndDate(endDate!=null?endDate:"");
                        if(nextSettingFragment.storeId!=null && (startDate!=null&& !"".equals(startDate)) && (endDate!=null&& !"".equals(endDate))){

                            CampaignLikeTwoActivity.campaignCouponList1.add(campaignCoupon);
                        }
                    }else if("R1".equals(nextSettingFragment.expireEndType)){
                        String startDay= nextSettingFragment.startDay;
                        String endDay = nextSettingFragment.endDay;
                        campaignCoupon.setExpireStartDay(startDay != null?startDay:"");
                        campaignCoupon.setExpireEndDay(endDay != null?endDay:"");
                        if(nextSettingFragment.storeId!=null && (startDay!=null&& !"".equals(startDay)) && (endDay!=null&& !"".equals(endDay))){
                            CampaignLikeTwoActivity.campaignCouponList1.add(campaignCoupon);
                        }

                    }
                }

            }
        }else if("条件二".equals(name)){
            if(couponList2 != null && couponList2.size()>0){
                Coupon coupon = couponList2.get(position);
                if(CampaignLikeTwoActivity.campaignCouponList2!=null && CampaignLikeTwoActivity.campaignCouponList2.size()>0){
                    for(int i=0;i<CampaignLikeTwoActivity.campaignCouponList1.size();i++){
                        CampaignCoupon campaignCoupon = CampaignLikeTwoActivity.campaignCouponList1.get(i);

                        if(position == i){
                            campaignCoupon.setIsSort(position+"");
                            campaignCoupon.setCouponId(coupon.getId());
                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setStoreSelectType(nextSettingFragment.couponStoreSelectType);
                            campaignCoupon.setStoreStr(nextSettingFragment.storeId);

                            if("A".equals(nextSettingFragment.expireEndType)){
                                String startDate = nextSettingFragment.startDate;
                                String endDate = nextSettingFragment.endDate;

                                campaignCoupon.setExpireStartDate(startDate!=null?startDate:"");
                                campaignCoupon.setExpireEndDate(endDate!=null?endDate:"");
                            }else if("R1".equals(nextSettingFragment.expireEndType)){
                                String startDay= nextSettingFragment.startDay;
                                String endDay = nextSettingFragment.endDay;
                                campaignCoupon.setExpireStartDay(startDay != null?startDay:"");
                                campaignCoupon.setExpireEndDay(endDay != null?endDay:"");

                            }
                        }

                    }
                }else {
                    CampaignCoupon campaignCoupon  = new CampaignCoupon();
                    campaignCoupon.setCouponId(coupon.getId());
                    campaignCoupon.setCouponName(coupon.getName());
                    campaignCoupon.setStoreSelectType(nextSettingFragment.couponStoreSelectType);
                    campaignCoupon.setStoreStr(nextSettingFragment.storeId);

                    if("A".equals(nextSettingFragment.expireEndType)){
                        String startDate = nextSettingFragment.startDate;
                        String endDate = nextSettingFragment.endDate;

                        campaignCoupon.setExpireStartDate(startDate!=null?startDate:"");
                        campaignCoupon.setExpireEndDate(endDate!=null?endDate:"");

                        if(nextSettingFragment.storeId!=null && (startDate!=null&& !"".equals(startDate)) && (endDate!=null&& !"".equals(endDate))){

                            CampaignLikeTwoActivity.campaignCouponList2.add(campaignCoupon);
                        }

                    }else if("R1".equals(nextSettingFragment.expireEndType)){
                        String startDay= nextSettingFragment.startDay;
                        String endDay = nextSettingFragment.endDay;

                        if(nextSettingFragment.storeId!=null && (startDay!=null&& !"".equals(startDay)) && (endDay!=null&& !"".equals(endDay))){
                            CampaignLikeTwoActivity.campaignCouponList2.add(campaignCoupon);
                        }
                    }
                }

            }
        }else if("条件三".equals(name)){

            if(couponList3 != null && couponList3.size()>0){
                Coupon coupon = couponList3.get(position);
                if(CampaignLikeTwoActivity.campaignCouponList3!=null && CampaignLikeTwoActivity.campaignCouponList3.size()>0){
                    for(int i=0;i<CampaignLikeTwoActivity.campaignCouponList1.size();i++){
                        CampaignCoupon campaignCoupon = CampaignLikeTwoActivity.campaignCouponList1.get(i);

                        if(position == i){
                            campaignCoupon.setIsSort(position+"");
                            campaignCoupon.setCouponId(coupon.getId());
                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setStoreSelectType(nextSettingFragment.couponStoreSelectType);
                            campaignCoupon.setStoreStr(nextSettingFragment.storeId);

                            if("A".equals(nextSettingFragment.expireEndType)){
                                String startDate = nextSettingFragment.startDate;
                                String endDate = nextSettingFragment.endDate;

                                campaignCoupon.setExpireStartDate(startDate!=null?startDate:"");
                                campaignCoupon.setExpireEndDate(endDate!=null?endDate:"");
                            }else if("R1".equals(nextSettingFragment.expireEndType)){
                                String startDay= nextSettingFragment.startDay;
                                String endDay = nextSettingFragment.endDay;
                                campaignCoupon.setExpireStartDay(startDay != null?startDay:"");
                                campaignCoupon.setExpireEndDay(endDay != null?endDay:"");

                            }
                        }

                    }
                }else {
                    CampaignCoupon campaignCoupon  = new CampaignCoupon();
                    campaignCoupon.setCouponId(coupon.getId());
                    campaignCoupon.setCouponName(coupon.getName());
                    campaignCoupon.setStoreSelectType(nextSettingFragment.couponStoreSelectType);
                    campaignCoupon.setStoreStr(nextSettingFragment.storeId);

                    if("A".equals(nextSettingFragment.expireEndType)){
                        String startDate = nextSettingFragment.startDate;
                        String endDate = nextSettingFragment.endDate;

                        campaignCoupon.setExpireStartDate(startDate!=null?startDate:"");
                        campaignCoupon.setExpireEndDate(endDate!=null?endDate:"");

                        if(nextSettingFragment.storeId!=null && (startDate!=null&& !"".equals(startDate)) && (endDate!=null&& !"".equals(endDate))){

                            CampaignLikeTwoActivity.campaignCouponList3.add(campaignCoupon);
                        }

                    }else if("R1".equals(nextSettingFragment.expireEndType)){
                        String startDay= nextSettingFragment.startDay;
                        String endDay = nextSettingFragment.endDay;

                        if(nextSettingFragment.storeId!=null && (startDay!=null&& !"".equals(startDay)) && (endDay!=null&& !"".equals(endDay))){
                            CampaignLikeTwoActivity.campaignCouponList3.add(campaignCoupon);
                        }
                    }
                }
            }
        }else if("点赞人奖励".equals(name)){
            if(couponList4 != null && couponList4.size()>0){
                Coupon coupon = couponList4.get(position);
                if(CampaignLikeTwoActivity.campaignCouponList4!=null && CampaignLikeTwoActivity.campaignCouponList4.size()>0){
                    for(int i=0;i<CampaignLikeTwoActivity.campaignCouponList1.size();i++){
                        CampaignCoupon campaignCoupon = CampaignLikeTwoActivity.campaignCouponList1.get(i);

                        if(position == i){
                            campaignCoupon.setIsSort(position+"");
                            campaignCoupon.setCouponId(coupon.getId());
                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setStoreSelectType(nextSettingFragment.couponStoreSelectType);
                            campaignCoupon.setStoreStr(nextSettingFragment.storeId);

                            if("A".equals(nextSettingFragment.expireEndType)){
                                String startDate = nextSettingFragment.startDate;
                                String endDate = nextSettingFragment.endDate;

                                campaignCoupon.setExpireStartDate(startDate!=null?startDate:"");
                                campaignCoupon.setExpireEndDate(endDate!=null?endDate:"");
                            }else if("R1".equals(nextSettingFragment.expireEndType)){
                                String startDay= nextSettingFragment.startDay;
                                String endDay = nextSettingFragment.endDay;
                                campaignCoupon.setExpireStartDay(startDay != null?startDay:"");
                                campaignCoupon.setExpireEndDay(endDay != null?endDay:"");

                            }
                        }

                    }
                }else {
                    CampaignCoupon campaignCoupon  = new CampaignCoupon();
                    campaignCoupon.setCouponId(coupon.getId());
                    campaignCoupon.setCouponName(coupon.getName());
                    campaignCoupon.setStoreSelectType(nextSettingFragment.couponStoreSelectType);
                    campaignCoupon.setStoreStr(nextSettingFragment.storeId);

                    if("A".equals(nextSettingFragment.expireEndType)){
                        String startDate = nextSettingFragment.startDate;
                        String endDate = nextSettingFragment.endDate;

                        campaignCoupon.setExpireStartDate(startDate!=null?startDate:"");
                        campaignCoupon.setExpireEndDate(endDate!=null?endDate:"");

                        if(nextSettingFragment.storeId!=null && (startDate!=null&& !"".equals(startDate)) && (endDate!=null&& !"".equals(endDate))){

                            CampaignLikeTwoActivity.campaignCouponList4.add(campaignCoupon);
                        }

                    }else if("R1".equals(nextSettingFragment.expireEndType)){
                        String startDay= nextSettingFragment.startDay;
                        String endDay = nextSettingFragment.endDay;

                        if(nextSettingFragment.storeId!=null && (startDay!=null&& !"".equals(startDay)) && (endDay!=null&& !"".equals(endDay))){
                            CampaignLikeTwoActivity.campaignCouponList4.add(campaignCoupon);
                        }
                    }
                }
            }
        }

    }


    @OnClick({R.id.btn_add_coupon,R.id.fab})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_add_coupon:
                intent = new Intent(getActivity(),CampaignCouponListActivity.class);
                intent.putExtra("prizeName",name);
                intent.putExtra("nextTwoSelectCoupon", "JZ");
                CampaignCouponListFragment.selectCoupons.clear();
                if("条件一".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,7771,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("条件二".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,7772,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("条件三".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,7773,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("点赞人奖励".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,7774,R.anim.slide_in_right,R.anim.slide_out_left);
                }
                break;

            case R.id.fab:

                intent = new Intent(getActivity(),CampaignCouponListActivity.class);
                intent.putExtra("nextTwoSelectCoupon","JZ");
                intent.putExtra("prizeName",name);
                CampaignCouponListFragment.selectCoupons.clear();
                if("条件一".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,7771,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("条件二".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,7772,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("条件三".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,7773,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("点赞人奖励".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,7774,R.anim.slide_in_right,R.anim.slide_out_left);
                }
                break;



            /*case R.id.ll_cancel:
                rl_edit.setVisibility(View.GONE);
                break;*/

        }
    }





    @Override
    public void chanageLayout(ArrayList<Coupon> couponList, String prizeName) {
        if("条件一".equals(prizeName)){
            if(activity.couponList1.isEmpty()){
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }else {
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            }
//            activity.removeSelectCouponList(activity.couponList1);
            setDotLayout(activity.couponList1);
            if(activity.campaignGamesetrewardList!= null && activity.campaignGamesetrewardList.size()>0){
                for (CampaignGamesetreward campaignGamesetreward : activity.campaignGamesetrewardList){
                    String name = campaignGamesetreward.getPrizeName();

                    if(prizeName.equals(name)){
                        campaignGamesetreward.setList(activity.campaignCouponList1);
                    }
                }
            }
            pagerAdapter.updateData(activity.couponList1);
            setPagerAdapter.updateData(activity.couponList1);

        }else if("条件二".equals(prizeName)){
            if(activity.couponList2.isEmpty()){
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }else {
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            }
//            activity.removeSelectCouponList(activity.couponList2);
            setDotLayout(activity.couponList2);
            if(activity.campaignGamesetrewardList!= null && activity.campaignGamesetrewardList.size()>0){
                for (CampaignGamesetreward campaignGamesetreward : activity.campaignGamesetrewardList){
                    String name = campaignGamesetreward.getPrizeName();

                    if(prizeName.equals(name)){
                        campaignGamesetreward.setList(activity.campaignCouponList2);
                    }
                }
            }
            pagerAdapter.updateData(activity.couponList2);
            setPagerAdapter.updateData(activity.couponList2);

        }else if("条件三".equals(prizeName)){
            if(activity.couponList3.isEmpty()){
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }else {
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            }
//            activity.removeSelectCouponList(activity.couponList3);
            setDotLayout(activity.couponList3);
            if(activity.campaignGamesetrewardList!= null && activity.campaignGamesetrewardList.size()>0){
                for (CampaignGamesetreward campaignGamesetreward : activity.campaignGamesetrewardList){
                    String name = campaignGamesetreward.getPrizeName();

                    if(prizeName.equals(name)){
                        campaignGamesetreward.setList(activity.campaignCouponList3);
                    }
                }
            }
            pagerAdapter.updateData(activity.couponList3);
            setPagerAdapter.updateData(activity.couponList3);

        }else if("点赞人奖励".equals(prizeName)){
            if(activity.couponList4.isEmpty()){
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }else {
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            }
//            activity.removeSelectCouponList(activity.couponList4);
            setDotLayout(activity.couponList4);
            if(activity.campaignGamesetrewardList!= null && activity.campaignGamesetrewardList.size()>0){
                for (CampaignGamesetreward campaignGamesetreward : activity.campaignGamesetrewardList){
                    String name = campaignGamesetreward.getPrizeName();

                    if(prizeName.equals(name)){
                        campaignGamesetreward.setList(activity.campaignCouponList4);
                    }
                }
            }
            pagerAdapter.updateData(activity.couponList4);
            setPagerAdapter.updateData(activity.couponList4);
        }
    }
}
