package com.zwx.scan.app.feature.couponmanage;


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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CampaignGamesetreward;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.feature.campaign.CampaignCouponListActivity;
import com.zwx.scan.app.feature.campaign.CampaignCouponListFragment;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanNextTwoActivity;
import com.zwx.scan.app.feature.campaign.LhPtEventBus;
import com.zwx.scan.app.widget.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GiveCouponNextConsumeTwoFragment extends BaseFragment<GiveCouponContract.Presenter> implements GiveCouponContract.View,View.OnClickListener,GiveCouponConsumeFragment.ChanageLhPtTwoLayoutLisenter {


    @BindView(R.id.btn_add_coupon)
    protected Button btnAddCoupon;


    @BindView(R.id.ll_day)
    protected LinearLayout ll_day;

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

    //核销后赠券  消费金额
    @BindView(R.id.ll_monetary)
    protected LinearLayout ll_monetary;

    @BindView(R.id.edt_money)
    protected EditText edt_money;
    @BindView(R.id.ll_days)
    protected LinearLayout ll_days;

    //天数加减
    @BindView(R.id.iv_left_subtract)
    protected ImageView ivLeftSubtract;

    @BindView(R.id.edt_day)
    protected EditText edtDay;

    @BindView(R.id.tv_valid_days)
    protected TextView tvValidDays;
    @BindView(R.id.iv_right_add)
    protected ImageView ivRightAdd;

    @BindView(R.id.fab)
    protected ImageButton fab;

    @BindView(R.id.iv_consume)
    protected ImageView iv_consume;

    //发放条件
    private int getValidDay  = 0 ;
    public String getValidDayStr ;
    private String validDaysStr = "";
    protected ArrayList<Coupon> couponList = new ArrayList<>();

    protected   String isEditCampaign = "NO";

    protected String isCopyCreate ;
    protected  ArrayList<Coupon> couponList1 = new ArrayList<>();
    protected  ArrayList<Coupon> couponList2 = new ArrayList<>();
    protected  ArrayList<Coupon> couponList3 = new ArrayList<>();
    protected  ArrayList<Coupon> couponList4 = new ArrayList<>();

//    protected  ArrayList<Coupon> edtCouponList = new ArrayList<>();
    private String name;

    private String prizeCount; //条件次数
    private Integer count;

    protected CouponPagerAdapter pagerAdapter = null;
    protected CouponSettPagerAdapter setPagerAdapter = null;
    private List<ImageView> mDotsIV = new ArrayList<>();
    
    private GiveCouponConsumeSettingFragment nextSettingFragment = null;
    private GiveCouponNewNextConsumeActivity activity = null;

    protected CampaignGamesetreward  campaignGamesetreward = new CampaignGamesetreward();
    protected  static ArrayList<CampaignCoupon> rewardCouponList = new ArrayList<>();



    public GiveCouponNextConsumeTwoFragment() {
    }


    public static GiveCouponNextConsumeTwoFragment getInstance(String name, ArrayList<Coupon> couponList){
        GiveCouponNextConsumeTwoFragment nextTwoFragment = new GiveCouponNextConsumeTwoFragment();
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
                couponList1 = GiveCouponNewNextConsumeActivity.couponList1;
                if(couponList1!=null && couponList1.size()>0 ){
                    llAddTop.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
                    if("S".equals(GiveCouponNewActivity.compaignStatus)||"NEW".equals(GiveCouponNewActivity.compaignStatus)){
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
                couponList2 = GiveCouponNewNextConsumeActivity.couponList2;
                if(couponList2!=null && couponList2.size()>0 ){
                    llAddTop.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
                    if("S".equals(GiveCouponNewActivity.compaignStatus)||"NEW".equals(GiveCouponNewActivity.compaignStatus)){
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
                couponList3 = GiveCouponNewNextConsumeActivity.couponList3;
                if(couponList3!=null && couponList3.size()>0 ){
                    llAddTop.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
                    if("S".equals(GiveCouponNewActivity.compaignStatus)||"NEW".equals(GiveCouponNewActivity.compaignStatus)){
                        fab.setVisibility(View.VISIBLE);
                    }else {
                        fab.setVisibility(View.GONE);
                    }

                }else {
                    llAddTop.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                }
            }else if("条件四".equals(name)){ //条件1 ~4
                couponList4 = GiveCouponNewNextConsumeActivity.couponList4;
                if(couponList4!=null && couponList4.size()>0 ){
                    llAddTop.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
                    if("S".equals(GiveCouponNewActivity.compaignStatus)||"NEW".equals(GiveCouponNewActivity.compaignStatus)){
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
    }


    @Override
    protected int getlayoutId() {
        return R.layout.fragment_give_coupon_next_consume_two;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        nextSettingFragment = new  GiveCouponConsumeSettingFragment();
        activity = (GiveCouponNewNextConsumeActivity)getActivity();
        edtDay.setSelection(edtDay.getText().toString().length());
        edt_money.setSelection(edt_money.getText().toString().length());


        if("S".equals(GiveCouponNewActivity.grantType)){
            iv_consume.setBackgroundResource(R.drawable.ic_give_coupon_panda_s);
        }else if("V".equals(GiveCouponNewActivity.grantType)){
            iv_consume.setBackgroundResource(R.drawable.ic_give_coupon_panda_v);
        }
        if("S".equals(GiveCouponNewActivity.compaignStatus)||"NEW".equals(GiveCouponNewActivity.compaignStatus)){
            edt_money.setEnabled(true);
            edtDay.setEnabled(true);
            tvValidDays.setEnabled(true);
            ivLeftSubtract.setEnabled(true);
            ivRightAdd.setEnabled(true);
//            rl_edit.setVisibility(View.VISIBLE);
        }else {
            edt_money.setEnabled(false);
            edtDay.setEnabled(false);
            tvValidDays.setEnabled(false);
            ivLeftSubtract.setEnabled(false);
            ivRightAdd.setEnabled(false);
//            rl_edit.setVisibility(View.GONE);
        }

        if("V".equals(GiveCouponNewActivity.grantType)) {
            ll_monetary.setVisibility(View.VISIBLE);
            ll_days.setVisibility(View.GONE);
            iv_consume.setBackgroundResource(R.drawable.ic_give_coupon_panda_v);

        }else {
            ll_monetary.setVisibility(View.GONE);
            ll_days.setVisibility(View.VISIBLE);
            iv_consume.setBackgroundResource(R.drawable.ic_give_coupon_panda_s);
        }

        if("条件一".equals(name)){ //条件1 ~4
            couponList1 = GiveCouponNewNextConsumeActivity.couponList1;
            if(couponList1!=null && couponList1.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(GiveCouponNewActivity.compaignStatus)||"NEW".equals(GiveCouponNewActivity.compaignStatus)){
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
            couponList2 = GiveCouponNewNextConsumeActivity.couponList2;
            if(couponList2!=null && couponList2.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(GiveCouponNewActivity.compaignStatus)||"NEW".equals(GiveCouponNewActivity.compaignStatus)){
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
            couponList3 = GiveCouponNewNextConsumeActivity.couponList3;
            if(couponList3!=null && couponList3.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(GiveCouponNewActivity.compaignStatus)||"NEW".equals(GiveCouponNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }

            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
        }else if("条件四".equals(name)){ //条件1 ~4
            couponList4 = GiveCouponNewNextConsumeActivity.couponList4;
            if(couponList4!=null && couponList4.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(GiveCouponNewActivity.compaignStatus)||"NEW".equals(GiveCouponNewActivity.compaignStatus)){
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

        if("V".equals(GiveCouponNewActivity.grantType)) { //核销后赠券
            prizeCount = edt_money.getText().toString().trim();
        }else {  //唤醒消费礼
            prizeCount = edtDay.getText().toString().trim();
        }


        setViewPagerAdapter();
//        initcampaignGamesetreward();
        addPrizeCountListener();
        initCouponList();
    }


    private void  setCampaignGamesetrewardValue(){

        if(prizeCount!= null && !"".equals(prizeCount)){
            campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
        }else {
            campaignGamesetreward.setPrizeCount(0);
        }
        campaignGamesetreward.setPrizeName(name);
        campaignGamesetreward.setRewardType("1");
        campaignGamesetreward.setList(new ArrayList<CampaignCoupon>());
    }


    private void addPrizeCountListener(){

        edt_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                prizeCount = edt_money.getText().toString().trim();
                edt_money.setSelection(edt_money.getText().toString().length());

                if(activity.campaignGamesetrewardList != null && activity.campaignGamesetrewardList.size()>0){
                    for (CampaignGamesetreward campaignGamesetreward : activity.campaignGamesetrewardList){

                        String prizeName = campaignGamesetreward.getPrizeName();
                        if(prizeName != null && !"".equals(prizeName)) {
                            if (name != null && !"".equals(name)) {
                                if("条件一".equals(prizeName) && name.equals(prizeName)){
                                    if(prizeCount != null && !"".equals(prizeCount)){
                                        campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                                    }else {
                                        prizeCount = null;
                                        campaignGamesetreward.setPrizeCount(null);
                                    }

                                    campaignGamesetreward.setPrizeName(prizeName);
                                    campaignGamesetreward.setRewardType("1");
                                    campaignGamesetreward.setList(GiveCouponNewNextConsumeActivity.campaignCouponList1);
                                }else if("条件二".equals(prizeName)&& name.equals(prizeName)){
                                    if(prizeCount != null && !"".equals(prizeCount)){
                                        campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                                    }else {
                                        prizeCount = null;
                                        campaignGamesetreward.setPrizeCount(null);
                                    }
                                    campaignGamesetreward.setPrizeName(prizeName);
                                    campaignGamesetreward.setRewardType("1");
                                    campaignGamesetreward.setList(GiveCouponNewNextConsumeActivity.campaignCouponList2);
                                }else if("条件三".equals(prizeName)&& name.equals(prizeName)){
                                    if(prizeCount != null && !"".equals(prizeCount)){
                                        campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                                    }else {
                                        prizeCount = null;
                                        campaignGamesetreward.setPrizeCount(null);
                                    }
                                    campaignGamesetreward.setPrizeName(prizeName);
                                    campaignGamesetreward.setRewardType("1");
                                    campaignGamesetreward.setList(GiveCouponNewNextConsumeActivity.campaignCouponList3);
                                }else if("条件四".equals(prizeName)&& name.equals(prizeName)){
                                    if(prizeCount != null && !"".equals(prizeCount)){
                                        campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                                    }else {
                                        prizeCount = null;
                                        campaignGamesetreward.setPrizeCount(null);
                                    }
                                    campaignGamesetreward.setPrizeName(prizeName);
                                    campaignGamesetreward.setRewardType("1");
                                    campaignGamesetreward.setList(GiveCouponNewNextConsumeActivity.campaignCouponList4);
                                }
                            }
                        }

                    }

                }
            }
        });
        edtDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                getValidDayStr = edtDay.getText().toString().trim();

                if(getValidDayStr != null && !"".equals(getValidDayStr)){
                    getValidDay = Integer.parseInt(getValidDayStr);

                }else {
                    getValidDay = 0;
                }

                if( getValidDay >365){
                    ToastUtils.showShort("最高有效为365天！");
                    getValidDay = 365;
                    edtDay.setText("");
                    ivRightAdd.setBackgroundResource(R.drawable.ic_gray_add);
                }else {
                    ivRightAdd.setBackgroundResource(R.drawable.ic_blue_added);
                }
                if(getValidDay>=1){
                    validDaysStr = getValidDay+"";
                    ivLeftSubtract.setBackgroundResource(R.drawable.ic_blue_subtracted);
                    tvValidDays.setText(validDaysStr);
                }else {
                    validDaysStr = "当";
                    ivLeftSubtract.setBackgroundResource(R.drawable.ic_gray_unsubtract);
                    tvValidDays.setText(validDaysStr);
                }
                prizeCount = edtDay.getText().toString().trim();
                edtDay.setSelection(edtDay.getText().toString().length());
                if(prizeCount != null && !"".equals(prizeCount)){
                }else {
                    prizeCount = "0";
                }
                if(activity.campaignGamesetrewardList != null && activity.campaignGamesetrewardList.size()>0){
                    for (CampaignGamesetreward campaignGamesetreward : activity.campaignGamesetrewardList){
                        String prizeName = campaignGamesetreward.getPrizeName();
                        if(prizeName != null && !"".equals(prizeName)) {
                            if (name != null && !"".equals(name)) {
                                if("条件一".equals(prizeName) && name.equals(prizeName)){
                                    campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                                    campaignGamesetreward.setPrizeName(prizeName);
                                    campaignGamesetreward.setRewardType("1");
                                    campaignGamesetreward.setList(GiveCouponNewNextConsumeActivity.campaignCouponList1);
                                }else if("条件二".equals(prizeName)&& name.equals(prizeName)){
                                    campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                                    campaignGamesetreward.setPrizeName(prizeName);
                                    campaignGamesetreward.setRewardType("1");
                                    campaignGamesetreward.setList(GiveCouponNewNextConsumeActivity.campaignCouponList2);
                                }else if("条件三".equals(prizeName)&& name.equals(prizeName)){
                                    campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                                    campaignGamesetreward.setPrizeName(prizeName);
                                    campaignGamesetreward.setRewardType("1");
                                    campaignGamesetreward.setList(GiveCouponNewNextConsumeActivity.campaignCouponList3);
                                }else if("条件四".equals(prizeName)&& name.equals(prizeName)){
                                    campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                                    campaignGamesetreward.setPrizeName(prizeName);
                                    campaignGamesetreward.setRewardType("1");
                                    campaignGamesetreward.setList(GiveCouponNewNextConsumeActivity.campaignCouponList4);
                                }
                            }
                        }

                    }

                }
            }
        });
    }

    private void initCouponList(){
        if(GiveCouponNewNextConsumeActivity.campaignGamesetrewardList != null && GiveCouponNewNextConsumeActivity.campaignGamesetrewardList.size()>0) {
            for (CampaignGamesetreward campaignGamesetreward : GiveCouponNewNextConsumeActivity.campaignGamesetrewardList) {
                String prizeName = campaignGamesetreward.getPrizeName();
                count = campaignGamesetreward.getPrizeCount();
                if(prizeName != null && !"".equals(prizeName)){
                    if(name != null && !"".equals(name)){
                        if( "条件一".equals(prizeName)&& name.equals(prizeName)){


                            if("V".equals(GiveCouponNewActivity.grantType)) { //核销后赠券
                                if(count != null && count.intValue()>=0){
                                    edt_money.setText(String.valueOf(count));
                                }else {
                                    edt_money.setText("");
                                }
                            }else {  //唤醒消费礼  S
                                if(count != null && count.intValue()>0){
                                    edtDay.setText(String.valueOf(count));
                                }else {
                                    edtDay.setText("0");
                                }
                            }
                            GiveCouponNewNextConsumeActivity.campaignCouponList1 = campaignGamesetreward.getList();

                            rewardCouponList =  GiveCouponNewNextConsumeActivity.campaignCouponList1;
                        }else if("条件二".equals(prizeName)&& name.equals(prizeName)){
                            if("V".equals(GiveCouponNewActivity.grantType)) { //核销后赠券
                                if(count != null && count.intValue()>=0){
                                    edt_money.setText(String.valueOf(count));
                                }else {
                                    edt_money.setText("");
                                }
                            }else {  //唤醒消费礼  S
                                if(count != null && count.intValue()>0){
                                    edtDay.setText(String.valueOf(count));
                                }else {
                                    edtDay.setText("0");
                                }
                            }

                            GiveCouponNewNextConsumeActivity.campaignCouponList2 = campaignGamesetreward.getList();
                            rewardCouponList =  GiveCouponNewNextConsumeActivity.campaignCouponList2;

                        }else if("条件三".equals(prizeName)&& name.equals(prizeName)){
                            if("V".equals(GiveCouponNewActivity.grantType)) { //核销后赠券
                                if(count != null && count.intValue()>=0){
                                    edt_money.setText(String.valueOf(count));
                                }else {
                                    edt_money.setText("");
                                }
                            }else {  //唤醒消费礼  S
                                if(count != null && count.intValue()>0){
                                    edtDay.setText(String.valueOf(count));
                                }else {
                                    edtDay.setText("0");
                                }
                            }

                            GiveCouponNewNextConsumeActivity.campaignCouponList3 = campaignGamesetreward.getList();
                            rewardCouponList =  GiveCouponNewNextConsumeActivity.campaignCouponList3;

                        }else if("条件四".equals(prizeName)&& name.equals(prizeName)){
                            if("V".equals(GiveCouponNewActivity.grantType)) { //核销后赠券
                                if(count != null && count.intValue()>=0){
                                    edt_money.setText(String.valueOf(count));
                                }else {
                                    edt_money.setText("");
                                }
                            }else {  //唤醒消费礼  S
                                if(count != null && count.intValue()>0){
                                    edtDay.setText(String.valueOf(count));
                                }else {
                                    edtDay.setText("0");
                                }
                            }

                            GiveCouponNewNextConsumeActivity.campaignCouponList4= campaignGamesetreward.getList();
                            rewardCouponList =  GiveCouponNewNextConsumeActivity.campaignCouponList4;
                        }
                        edtDay.setSelection(edtDay.getText().toString().length());
                        //本地缓存 提取
                        if(rewardCouponList!=null && rewardCouponList.size()>0){
                            for (CampaignCoupon campaignCoupon :rewardCouponList){
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

                            }

                        }
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
        }else if("条件四".equals(name)){
            campaignGamesetreward.setPrizeOrder(4);
        }
        GiveCouponNewNextConsumeActivity.campaignGamesetrewardList.add(campaignGamesetreward);

    }

    private void addCoupontList(){

        if ("条件一".equals(name)) {
            couponList1 =  GiveCouponNewNextConsumeActivity.couponList1;
            if(couponList1!=null && couponList1.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(GiveCouponNewActivity.compaignStatus)||"NEW".equals(GiveCouponNewActivity.compaignStatus)){
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
            couponList2 = GiveCouponNewNextConsumeActivity.couponList2;
            if(couponList2!=null && couponList2.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(GiveCouponNewActivity.compaignStatus)||"NEW".equals(GiveCouponNewActivity.compaignStatus)){
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
            couponList3 = GiveCouponNewNextConsumeActivity.couponList3;
            if(couponList3!=null && couponList3.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(GiveCouponNewActivity.compaignStatus)||"NEW".equals(GiveCouponNewActivity.compaignStatus)){
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
        } else if ("条件四".equals(name)) {
            couponList4 = GiveCouponNewNextConsumeActivity.couponList4;
            if(couponList4!=null && couponList4.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(GiveCouponNewActivity.compaignStatus)||"NEW".equals(GiveCouponNewActivity.compaignStatus)){
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

    private void remove(ArrayList<Coupon> couponList){
        for(int i=0;i<couponList.size();i++){
            for(int j=couponList.size()-1;j>i;j--){
                if(String.valueOf(couponList.get(i).getId()).equals(String.valueOf(couponList.get(j).getId()))){
                    couponList.remove(j);
                }
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
//                setCampaignCoupon(position);


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
                    Fragment fragment = GiveCouponConsumeFragment.getInstance(coupon,name);
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
                    Fragment fragment = GiveCouponConsumeFragment.getInstance(coupon,name);
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
                    Fragment fragment = GiveCouponConsumeSettingFragment.getInstance(coupon,i,name,setViewPager);
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
                    Fragment fragment = GiveCouponConsumeSettingFragment.getInstance(coupon,i,name,setViewPager);
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

                    GiveCouponNewNextConsumeActivity.campaignCouponList1.add(campaignCoupon);

                }else if("R1".equals(nextSettingFragment.expireEndType)){
                    String startDay= nextSettingFragment.startDay;
                    String endDay = nextSettingFragment.endDay;
                    campaignCoupon.setExpireStartDay(startDay != null?startDay:"");
                    campaignCoupon.setExpireEndDay(endDay != null?endDay:"");
                    GiveCouponNewNextConsumeActivity.campaignCouponList1.add(campaignCoupon);
                }
            }
        }else if("条件二".equals(name)){
            if(couponList2 != null && couponList2.size()>0){
                Coupon coupon = couponList2.get(position);
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

                        GiveCouponNewNextConsumeActivity.campaignCouponList2.add(campaignCoupon);
                    }

                }else if("R1".equals(nextSettingFragment.expireEndType)){
                    String startDay= nextSettingFragment.startDay;
                    String endDay = nextSettingFragment.endDay;

                    if(nextSettingFragment.storeId!=null && (startDay!=null&& !"".equals(startDay)) && (endDay!=null&& !"".equals(endDay))){
                        GiveCouponNewNextConsumeActivity.campaignCouponList2.add(campaignCoupon);
                    }
                }
            }
        }else if("条件三".equals(name)){

            if(couponList3 != null && couponList3.size()>0){
                Coupon coupon = couponList3.get(position);
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

                        GiveCouponNewNextConsumeActivity.campaignCouponList3.add(campaignCoupon);
                    }

                }else if("R1".equals(nextSettingFragment.expireEndType)){
                    String startDay= nextSettingFragment.startDay;
                    String endDay = nextSettingFragment.endDay;

                    if(nextSettingFragment.storeId!=null && (startDay!=null&& !"".equals(startDay)) && (endDay!=null&& !"".equals(endDay))){
                        GiveCouponNewNextConsumeActivity.campaignCouponList3.add(campaignCoupon);
                    }
                }
            }
        }else if("条件四".equals(name)){
            if(couponList4 != null && couponList4.size()>0){
                Coupon coupon = couponList4.get(position);
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

                        GiveCouponNewNextConsumeActivity.campaignCouponList4.add(campaignCoupon);
                    }

                }else if("R1".equals(nextSettingFragment.expireEndType)){
                    String startDay= nextSettingFragment.startDay;
                    String endDay = nextSettingFragment.endDay;

                    if(nextSettingFragment.storeId!=null && (startDay!=null&& !"".equals(startDay)) && (endDay!=null&& !"".equals(endDay))){
                        GiveCouponNewNextConsumeActivity.campaignCouponList4.add(campaignCoupon);
                    }
                }
            }
        }

    }
    

    @OnClick({R.id.btn_add_coupon,R.id.fab,R.id.iv_left_subtract,R.id.iv_right_add})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_add_coupon:
                intent = new Intent(getActivity(),CampaignCouponListActivity.class);
                intent.putExtra("grantType","S");
                intent.putExtra("prizeName",name);
                intent.putExtra("nextTwoSelectCoupon", "GIVE");
                CampaignCouponListFragment.selectCoupons.clear();
                if("条件一".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,6661,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("条件二".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,6662,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("条件三".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,6663,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("条件四".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,6664,R.anim.slide_in_right,R.anim.slide_out_left);
                }
                break;

            case R.id.fab:

                intent = new Intent(getActivity(),CampaignCouponListActivity.class);
                intent.putExtra("grantType","S");
                intent.putExtra("nextTwoSelectCoupon","GIVE");
                intent.putExtra("prizeName",name);
                CampaignCouponListFragment.selectCoupons.clear();
                if("条件一".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,6661,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("条件二".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,6662,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("条件三".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,6663,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("条件四".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,6664,R.anim.slide_in_right,R.anim.slide_out_left);
                }
                break;

            case R.id.iv_left_subtract: //第一个加减
                getValidDayStr = edtDay.getText().toString().trim();

                if(getValidDayStr != null && !"".equals(getValidDayStr)){
                    getValidDay = Integer.parseInt(getValidDayStr);

                }else {
                    getValidDay = 0;

                }

                getValidDay--;
                if(getValidDay<=0){
                    getValidDay = 0;
                }

                if( getValidDay <= 0){
                    tvValidDays.setText("当");
                    ivLeftSubtract.setBackgroundResource(R.drawable.ic_gray_unsubtract);
                }else {
                    tvValidDays.setText(getValidDay+"");
                    ivLeftSubtract.setBackgroundResource(R.drawable.ic_blue_subtracted);
                }

                edtDay.setText(getValidDay+"");
                break;
            case R.id.iv_right_add:
                getValidDayStr = edtDay.getText().toString().trim();

                if(getValidDayStr != null && !"".equals(getValidDayStr)){
                    getValidDay = Integer.parseInt(getValidDayStr);

                }else {
                    getValidDay = 0;

                }
                getValidDay++;
                if( getValidDay > 365){
                    ToastUtils.showShort("最高有效为365天！");
                    getValidDay = 365;
                    ivRightAdd.setBackgroundResource(R.drawable.ic_gray_add);
                }else {
                    ivRightAdd.setBackgroundResource(R.drawable.ic_blue_added);
                }
                if(getValidDay>=1){
                    ivLeftSubtract.setBackgroundResource(R.drawable.ic_blue_subtracted);
                }

                tvValidDays.setText(getValidDay+"");

                edtDay.setText(getValidDay+"");
                break;

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

        }else if("条件四".equals(prizeName)){
            if(activity.couponList4.isEmpty()){
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }else {
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            }
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
