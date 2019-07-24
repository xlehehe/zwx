package com.zwx.scan.app.feature.couponmanage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.tablayout.SlidingTabLayout;
import com.zwx.library.tablayout.listener.OnTabSelectListener;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CampaignGame;
import com.zwx.scan.app.data.bean.CampaignGamesetreward;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.CouponMaterial;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.campaign.CampaignCouponListFragment;
import com.zwx.scan.app.feature.campaign.CampaignListActivity;
import com.zwx.scan.app.feature.campaign.LhPtEventBus;
import com.zwx.scan.app.feature.campaign.PrizeBean;
import com.zwx.scan.app.utils.ButtonUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/03/19
 * desc   :  常规发券 唤醒消费礼
 * version: 1.0
 **/
public class GiveCouponNewNextConsumeActivity extends BaseActivity<GiveCouponContract.Presenter> implements GiveCouponContract.View,View.OnClickListener {


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
    @BindView(R.id.iv_two)
    protected ImageView ivTwo;
    
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

    @BindView(R.id.btn_save)
    protected Button btnSave;
    @BindView(R.id.ll_save)
    protected LinearLayout ll_save;
    @BindView(R.id.btn_save_and_public)
    protected Button btnSavePublic;





    protected static ArrayList<Coupon> couponList1 = new ArrayList<>();
    protected static ArrayList<Coupon> couponList2 = new ArrayList<>();
    protected static ArrayList<Coupon> couponList3 = new ArrayList<>();
    protected static ArrayList<Coupon> couponList4 = new ArrayList<>();

    protected static ArrayList<CampaignCoupon> campaignCouponList =new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList1 =new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList2 =new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList3 =new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList4 =new ArrayList<>();
//    protected  static ArrayList<CampaignCoupon> forwardCouponList = new ArrayList<>();
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
    protected ArrayList<PrizeBean> prizeBeanList = new ArrayList<>();
    protected  static ArrayList<CampaignGamesetreward> campaignGamesetrewardList = new ArrayList<>();
    private  String name;
    private  FPagerAdapter pagerAdapter = null;
    private String btnFlag = "";
    private String grantType = "";
    protected  Intent intent = null;



    protected static String  prizeCount1;
    protected static String  prizeCount2;
    protected static String  prizeCount3;
    protected static String  prizeCount4;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销注册
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_give_coupon_new_next_consume;
    }

    @Override
    protected void initView() {
        DaggerGiveCouponComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .giveCouponModule(new GiveCouponModule(this))
                .build()
                .inject(this);


        //注销注册
        EventBus.getDefault().unregister(this);

        if("S".equals(GiveCouponNewActivity.grantType)){
            title = "唤醒消费礼";
        }else if("V".equals(GiveCouponNewActivity.grantType)){
            title = "核销后赠券";
        }
        tvTitle.setText(title);
        setSetTep();
        isEditCampaign =  getIntent().getStringExtra("isEditCampaign");
        isCopyCreate = getIntent().getStringExtra("isCopyCreate");
        userId = SPUtils.getInstance().getString("userId");
        compId = SPUtils.getInstance().getString("compId");
        grantType = getIntent().getStringExtra("grantType");


        if("YES".equals(isEditCampaign)||"NEW".equals(isEditCampaign)){  //编辑
            if("YES".equals(isCopyCreate)){  //编辑页面复制并创建
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setText("复制并创建");
            }else {
                tvRight.setVisibility(View.GONE);
            }

        }

        if("S".equals(GiveCouponNewActivity.compaignStatus)||"NEW".equals(GiveCouponNewActivity.compaignStatus)){
            ll_more_columns.setEnabled(true);
            button_more_columns.setEnabled(true);

            ll_save.setVisibility(View.VISIBLE);
            btnSavePublic.setVisibility(View.VISIBLE);
            btnPre.setVisibility(View.VISIBLE);
        }else {
            ll_more_columns.setEnabled(false);
            button_more_columns.setEnabled(false);

            btnSavePublic.setText("返回");
            btnSavePublic.setVisibility(View.VISIBLE);
            ll_save.setVisibility(View.GONE);
            btnPre.setVisibility(View.VISIBLE);
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
                    ArrayList<CampaignCoupon> list = campaignGamesetreward.getList();
                    if(list != null && list.size()>0){
                        for (CampaignCoupon campaignCoupon : campaignGamesetreward.getList()) {
                            String storeid = campaignCoupon.getStoreStr();

                            if(storeid!= null && !"".equals(storeid)){
                                if (storeid.contains(",")) {
                                    campaignCoupon.setStoreStr(storeid.replace(",", "-"));
                                }
                            }

                        }
                    }
                    campaignGamesetreward.setList(list);
                    if(rewardOrder != null){
                        if(rewardOrder == 1){
                            prizeBean.setName(campaignGamesetreward.getPrizeName());
                            prizeBean.setChecked(true);
                            prizeBeanList.add(prizeBean);
                            campaignCouponList1 = list;

                        }else if(rewardOrder == 2){
                            prizeBean.setName(campaignGamesetreward.getPrizeName());
                            prizeBean.setChecked(true);
                            prizeBeanList.add(prizeBean);
                            campaignCouponList2 = list;
                        }else if(rewardOrder == 3){
                            prizeBean.setName(campaignGamesetreward.getPrizeName());
                            prizeBean.setChecked(true);
                            prizeBeanList.add(prizeBean);
                            campaignCouponList3= campaignGamesetreward.getList();
                        }else if(rewardOrder == 4){
                            prizeBean.setName(campaignGamesetreward.getPrizeName());
                            prizeBean.setChecked(true);
                            prizeBeanList.add(prizeBean);
                            campaignCouponList4 = list;
                        }
                    }

                    initCampaignRewardList(campaignGamesetreward);


                }
            }


        }else {
            initPrizePath();

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
    private void initCampaignRewardList(CampaignGamesetreward campaignGamesetreward){
        Integer count = campaignGamesetreward.getPrizeCount();
        String name = campaignGamesetreward.getPrizeName();
        if("条件一".equals(name)){
            forwardCouponList = campaignCouponList1;
        }else if("条件二".equals(name)){
            forwardCouponList = campaignCouponList2;
        }else if("条件三".equals(name)){
            forwardCouponList = campaignCouponList3;
        }else if("条件四".equals(name)){
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

                    if(store1 != null){
                        storeIdCouponType = store1.getStoreSelectType();
                        String storeName = "";
                        String storeIdArr = "";
                        if("A".equals(storeIdCouponType)){
                            storeName = "#全部店铺";
                            for (Store store:storeList){
                                store.setChecked(false);
                                String storeId = store.getStoreId();
                                storeIdSb.append(storeId).append("-");

                            }
                            storeIdArr = "";
                        }else if("D".equals(storeIdCouponType)){
                            storeName = "#全部自营";
                            for (Store store:storeList){
                                store.setChecked(false);
                                String storeId = store.getStoreId();
                                storeIdSb.append(storeId).append("-");

                            }
                            storeIdArr = "";
                        }else if("J".equals(storeIdCouponType)){
                            storeName = "#全部加盟";
                            for (Store store:storeList){
                                store.setChecked(false);
                                String storeId = store.getStoreId();
                                storeIdSb.append(storeId).append("-");

                            }
                            storeIdArr = "";
                        }else {

                            for (Store store:storeList){
                                store.setChecked(false);
                                Integer statusQuo = store.getStatusQuo();
                                String storeId = store.getStoreId();
                                String storeName2 = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                storeIdSb.append(storeId).append("-");
                                storeNameSb.append(storeName2);

                            }
                            storeName = storeNameSb.toString();

                            String storeIdA= storeIdSb.toString();
                            if(storeIdA != null && !"".equals(storeIdA)){
                                storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                            }
                            coupon.setStoreIds(storeIdArr);
                            coupon.setStoreNames(storeName);
                        }

                    }
                }

                CouponMaterial material = new CouponMaterial();
                material.setBackgroundThumbnail(campaignCoupon.getBackgroundThumbnail());
                material.setBackground(campaignCoupon.getBackground());
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
                if ( "条件一".equals(name)) {
                    couponList1.add(coupon);
                } else if ( "条件二".equals(name)) {
                    couponList2.add(coupon);
                } else if ("条件三".equals(name)) {
                    couponList3.add(coupon);
                } else if ("条件四".equals(name)) {
                    couponList4.add(coupon);
                } 


            }
        }
    }

    protected void initPrizePath(){
        //条件
        campaignGamesetrewardList = new ArrayList<>();
        if(prizeBeanList == null||prizeBeanList.size()==0){
            PrizeBean prizeBean = new PrizeBean();
            prizeBean.setName("条件一");
            prizeBean.setChecked(true);
            prizeBeanList.add(prizeBean);
            CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();
            campaignGamesetreward.setPrizeCount(null);
            campaignGamesetreward.setRewardType("1");
            campaignGamesetreward.setPrizeName("条件一");
            campaignGamesetreward.setPrizeOrder(1);
            campaignGamesetrewardList.add(campaignGamesetreward);
        }


    }


    private void initTabColumn(){

        if(GiveCouponNewActivity.campaignGame == null){
            GiveCouponNewActivity.campaignGame = new CampaignGame();
        }
        if(prizeBeanList != null && prizeBeanList.size()>0){

        }else {

            prizeBeanList = new ArrayList<>();
            PrizeBean prizeBean = new PrizeBean();
            prizeBean.setName("条件一");
            prizeBean.setChecked(false);
            prizeBeanList.add(prizeBean);
            GiveCouponNewActivity.campaignGame.setConsolationPrizeFlag("0");
        }
        setPagerAdapter();

    }


    @OnClick({R.id.iv_back,R.id.tv_right,R.id.button_more_columns,
            R.id.btn_pre,R.id.btn_save,R.id.btn_save_and_public})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.startActivity(GiveCouponManageActivity.class, R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
                break;

            case R.id.button_more_columns:

                for (PrizeBean prizeBean : prizeBeanList){
                    prizeBean.setChecked(true);

                }
                if (!ButtonUtils.isFastDoubleClick(R.id.btn_save)) {
                    intent = new Intent(GiveCouponNewNextConsumeActivity.this,GiveCouponConsumeConditionTagActivity.class);
                    intent.putExtra("prizeBeanList",prizeBeanList);
                    ActivityUtils.startActivityForResult(GiveCouponNewNextConsumeActivity.this,intent,6001,R.anim.slide_in_right,R.anim.slide_out_left);

                }

                break;
            case R.id.tv_right:
                ToastUtils.showCustomShortBottom(getResources().getString(R.string.create_success));
                copyAndCreate();

                break;
            case R.id.btn_pre:
                  setIntentUpActivity();
                break;
            case R.id.btn_save:

                setCampaignRewardList();
                btnFlag = "save";
                if(check()){
                    return;
                }

                if (!ButtonUtils.isFastDoubleClick(R.id.btn_save)) {   //处理防止点击多次
                    presenter.saveCampaignInfoS(this,GiveCouponNewActivity.campaign,GiveCouponNewActivity.compMemTypeId,GiveCouponNewActivity.campaginGrant,new ArrayList<CampaignCoupon>(),campaignGamesetrewardList,userId,compId,btnFlag);
                }

                break;
            case R.id.btn_save_and_public:
                setCampaignRewardList();

                btnFlag = "saveAndpublic";
                if(check()){
                    return;
                }

                if("S".equals(GiveCouponNewActivity.compaignStatus)||"NEW".equals(GiveCouponNewActivity.compaignStatus)){
//
                    if (!ButtonUtils.isFastDoubleClick(R.id.btn_save_and_public)) {   //处理防止点击多次
                        presenter.saveCampaignInfoS(this,GiveCouponNewActivity.campaign,GiveCouponNewActivity.compMemTypeId,GiveCouponNewActivity.campaginGrant,new ArrayList<CampaignCoupon>(),campaignGamesetrewardList,userId,compId,btnFlag);
                    }
                }else {
                    ActivityUtils.startActivity(GiveCouponManageActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                    finish();
                }
                break;
        }
    }


    private void setCampaignRewardList(){
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
                    }else if("条件四".equals(name)){

                        campaignGamesetreward.setRewardType("1");
                        campaignGamesetreward.setPrizeOrder(4);
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
    }
    private boolean check(){

        if(campaignGamesetrewardList!=null && campaignGamesetrewardList.size()>0){
            for(CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList){

                if(campaignGamesetreward != null){
                    String name = campaignGamesetreward.getPrizeName();
                    Integer prizeCount = campaignGamesetreward.getPrizeCount();
                    if(prizeCount != null && prizeCount.intValue()>=0){
                        if("条件一".equals(name)){
                            prizeCount1 = String.valueOf(prizeCount);
                        }else  if("条件二".equals(name)){
                            prizeCount2= String.valueOf(prizeCount);
                        }else  if("条件三".equals(name)){
                            prizeCount3= String.valueOf(prizeCount);
                        }else  if("条件四".equals(name)){
                            prizeCount4= String.valueOf(prizeCount);
                        }
                    }else{
                        if("条件一".equals(name)){
                            prizeCount1 = "";
                        }else  if("条件二".equals(name)){
                            prizeCount2= "";
                        }else  if("条件三".equals(name)){
                            prizeCount3="";
                        }else  if("条件四".equals(name)){
                            prizeCount4="";
                        }
                        setDialog("您在第二步中，"+name+"设置信息不完善，请补充信息。");
                        return true;
                    }
                    List<CampaignCoupon> campaignCouponList = campaignGamesetreward.getList();
                    if(campaignCouponList != null && campaignCouponList.size()>0){
                        for (CampaignCoupon campaignCoupon : campaignCouponList){
                            String startDate =campaignCoupon.getExpireStartDate();
                            String endDate = campaignCoupon.getExpireEndDate();
                            String startDay =campaignCoupon.getExpireStartDay();
                            String endDay = campaignCoupon.getExpireEndDay();
                            String couponName =campaignCoupon.getCouponName();
                            String expireType = campaignCoupon.getExpireEndType();

                            if("A".equals(expireType)){
                                if(startDate == null ||"".equals(startDate)){
                                    setDialog("您在第二步中，"+name+"设置信息不完善，请补充信息。");

                                    return true;
                                }

                                if(endDate == null ||"".equals(endDate)){
                                    setDialog("您在第二步中，"+name+"设置信息不完善，请补充信息。");
                                    return true;
                                }
                            }else if("R1".equals(expireType)){
                                if(startDay == null ||"".equals(startDay)){
                                    setDialog("您在第二步中，"+name+"设置信息不完善，请补充信息。");
                                    return true;
                                }

                                if(endDay == null ||"".equals(endDay)){
                                    setDialog("您在第二步中，"+name+"设置信息不完善，请补充信息。");
                                    return true;
                                }


                            }
                            if(couponName == null ||"".equals(couponName)){
                                setDialog("您在第二步中，"+name+"设置信息不完善，请补充信息。");
                                return true;
                            }
                        }
                    }else{
                        setDialog("您在第二步中，"+name+"必须设置优惠券，请补充信息");
                        return true;
                    }

                }



            }
        }else {
            setDialog("您在第二步中，设置信息不完善，请补充信息。");
            return true;
        }


        if("save".equals(btnFlag)){

        }else {

            if(GiveCouponNewActivity.campaign !=null){
                String campaignName = GiveCouponNewActivity.campaign.getCampaignName();


                if(campaignName == null ||"".equals(campaignName)){
                    setDialog("您在第一步，设置信息不完善，请补充信息。");
                    return true;
                }

                String startDate = GiveCouponNewActivity.campaign.getBeginDate();
                String endDate = GiveCouponNewActivity.campaign.getEndDate();
                if(startDate == null ||"".equals(startDate)){
                    setDialog("您在第一步，设置信息不完善，请补充信息。");
                    return true;
                }

                if(endDate == null ||"".equals(endDate)){
                    setDialog("您在第一步，设置信息不完善，请补充信息。");
                    return true;
                }
            }else {
                setDialog("您在第一步，设置信息不完善，请补充信息。");
                return true;
            }

            if(GiveCouponNewActivity.compMemTypeId == null || "".equals(GiveCouponNewActivity.compMemTypeId)){
                setDialog("您在第一步，设置信息不完善，请补充信息。");
                return true;
            }


            if(GiveCouponNewActivity.campaginGrant !=null){


            }else {
                setDialog("您在第一步中，设置信息不完善，请补充信息。");
                return true;
            }


            if("V".equals(GiveCouponNewActivity.grantType)){
                if((prizeCount2 != null && !"".equals(prizeCount2)) && (prizeCount3 != null && !"".equals(prizeCount3))){
                    int pCount2 = Integer.parseInt(prizeCount2);
                    int pCount3 = Integer.parseInt(prizeCount3);

                    if(pCount2>=0 && pCount3>=0){
                        if(pCount2<pCount3){
                        }else {
                            ToastUtils.showShort(R.string.consume_coupon_tip_);
                            return true;
                        }
                    }

                }

                if((prizeCount1 != null && !"".equals(prizeCount1)) && (prizeCount3 != null && !"".equals(prizeCount3))){
                    int pCount1 = Integer.parseInt(prizeCount1);
                    int pCount3 = Integer.parseInt(prizeCount3);

                    if(pCount1>=0 && pCount3>=0){
                        if(pCount1<pCount3){
                        }else {
                            ToastUtils.showShort(R.string.consume_coupon_tip_);
                            return true;
                        }
                    }

                }

                if((prizeCount1 != null && !"".equals(prizeCount1)) && (prizeCount2 != null && !"".equals(prizeCount2))){
                    int pCount1 = Integer.parseInt(prizeCount1);
                    int pCount2 = Integer.parseInt(prizeCount2);

                    if(pCount1>=0 && pCount2>=0){
                        if(pCount1<pCount2){
                        }else {
                            ToastUtils.showShort(R.string.consume_coupon_tip_);
                            return true;
                        }
                    }

                }

                if((prizeCount1 != null && !"".equals(prizeCount1)) && (prizeCount4 != null && !"".equals(prizeCount4))){
                    int pCount1 = Integer.parseInt(prizeCount1);
                    int pCount4 = Integer.parseInt(prizeCount4);

                    if(pCount1>=0 && pCount4>=0){
                        if(pCount1<pCount4){
                        }else {
                            ToastUtils.showShort(R.string.consume_coupon_tip_);
                            return true;
                        }
                    }

                }

                if((prizeCount2 != null && !"".equals(prizeCount2)) && (prizeCount4 != null && !"".equals(prizeCount4))){
                    int pCount2 = Integer.parseInt(prizeCount2);
                    int pCount4 = Integer.parseInt(prizeCount4);

                    if(pCount2>=0 && pCount4>=0){
                        if(pCount2<pCount4){
                        }else {
                            ToastUtils.showShort(R.string.consume_coupon_tip_);
                            return true;
                        }
                    }

                }

                if((prizeCount3 != null && !"".equals(prizeCount3)) && (prizeCount4 != null && !"".equals(prizeCount4))){
                    int pCount3 = Integer.parseInt(prizeCount3);
                    int pCount4 = Integer.parseInt(prizeCount4);

                    if(pCount3>=0 && pCount4>=0){
                        if(pCount3<pCount4){
                        }else {
                            ToastUtils.showShort(R.string.consume_coupon_tip_);
                            return true;
                        }
                    }

                }
            }


        }

        return false;
    }





    public void setDialog(String tip){

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView textView = (TextView)rootView.findViewById(R.id.message);
        textView.setText(tip);
        rootView.findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        rootView.findViewById(R.id.cancelBtn).setVisibility(View.GONE);

    }

    private void remove(ArrayList<CampaignCoupon> campaignCouponList){
        for(int i=0;i<campaignCouponList.size();i++){
            for(int j=campaignCouponList.size()-1;j>i;j--){
                if((campaignCouponList.get(i).getCouponId().equals(campaignCouponList.get(j).getCouponId()))){
                    campaignCouponList.remove(j);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        removeLaohuCampaignData();
        ActivityUtils.startActivity(GiveCouponManageActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
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
                        mFragmentList.add(GiveCouponNextConsumeTwoFragment.getInstance(name,couponList1));
                    }else if("条件二".equals(name)){
                        mFragmentList.add(GiveCouponNextConsumeTwoFragment.getInstance(name,couponList2));
                    }else if("条件三".equals(name)){
                        mFragmentList.add(GiveCouponNextConsumeTwoFragment.getInstance(name,couponList3));
                    }else if("条件四".equals(name)){
                        mFragmentList.add(GiveCouponNextConsumeTwoFragment.getInstance(name,couponList4));
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
                        fragments.add(GiveCouponNextConsumeTwoFragment.getInstance(name,couponList1));
                    }else if("条件二".equals(name)){
                        fragments.add(GiveCouponNextConsumeTwoFragment.getInstance(name,couponList2));
                    }else if("条件三".equals(name)){
                        fragments.add(GiveCouponNextConsumeTwoFragment.getInstance(name,couponList3));
                    }else if("条件四".equals(name)){
                        fragments.add(GiveCouponNextConsumeTwoFragment.getInstance(name,couponList4));
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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 6001){
            if(requestCode == 6001){

                if(data != null){
                    prizeBeanList = (ArrayList<PrizeBean>)data.getSerializableExtra("prizeBeanList");
                }
                ArrayList<CampaignGamesetreward> campaignGamesetrewardListNewData = new ArrayList<>();
                ArrayList<CampaignGamesetreward> campaignGamesetrewardListNewData1 = new ArrayList<>();
                ArrayList<CampaignGamesetreward> campaignGamesetrewardListNewData2 = new ArrayList<>();
                ArrayList<CampaignGamesetreward> campaignGamesetrewardListNewData3 = new ArrayList<>();
                ArrayList<CampaignGamesetreward> campaignGamesetrewardListNewData4 = new ArrayList<>();

                if(prizeBeanList.size()<campaignGamesetrewardList.size()){  //若返回新的条件项比旧的条件项少，则重新赋值，否则新增
                    campaignGamesetrewardListNewData.clear();
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

                    }
                }else{    //条件项比旧条件项多，重新添加排序
                    if(campaignGamesetrewardList != null && !"".equals(campaignGamesetrewardList)){
                        for (int i=0;i<campaignGamesetrewardList.size();i++){
                            CampaignGamesetreward campaignGamesetreward = campaignGamesetrewardList.get(i);
                            if(campaignGamesetreward != null){
                                String prizeName2 = campaignGamesetreward.getPrizeName();

                                if("条件一".equals(prizeName2)){
                                    campaignGamesetrewardListNewData1.add(campaignGamesetreward);
                                }else  if("条件二".equals(prizeName2)){
                                    campaignGamesetrewardListNewData1.add(campaignGamesetreward);
                                }else  if("条件三".equals(prizeName2)){
                                    campaignGamesetrewardListNewData3.add(campaignGamesetreward);
                                }else  if("条件四".equals(prizeName2)){
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
                                CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();
                                campaignGamesetreward.setPrizeOrder(1);
                                if("S".equals(GiveCouponNewActivity.grantType)){
                                    campaignGamesetreward.setPrizeCount(0);
                                }else if("V".equals(GiveCouponNewActivity.grantType)){
                                    campaignGamesetreward.setPrizeCount(null);
                                }

                                campaignGamesetreward.setPrizeName(prizeName);
                                campaignGamesetreward.setRewardType("1");
                                campaignGamesetrewardListNewData.add(campaignGamesetreward);
                            }
                        }else  if("条件二".equals(prizeName)){
                            if(campaignGamesetrewardListNewData2 != null && campaignGamesetrewardListNewData2.size()>0){
                                campaignGamesetrewardListNewData.addAll(campaignGamesetrewardListNewData2);
                            }else {
                                CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();
                                campaignGamesetreward.setPrizeOrder(2);
                                if("S".equals(GiveCouponNewActivity.grantType)){
                                    campaignGamesetreward.setPrizeCount(0);
                                }else if("V".equals(GiveCouponNewActivity.grantType)){
                                    campaignGamesetreward.setPrizeCount(null);
                                }
                                campaignGamesetreward.setPrizeName(prizeName);
                                campaignGamesetreward.setRewardType("1");
                                campaignGamesetrewardListNewData.add(campaignGamesetreward);
                            }
                        }else  if("条件三".equals(prizeName)){
                            if(campaignGamesetrewardListNewData3 != null && campaignGamesetrewardListNewData3.size()>0){
                                campaignGamesetrewardListNewData.addAll(campaignGamesetrewardListNewData3);
                            }else {
                                CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();
                                campaignGamesetreward.setPrizeOrder(3);
                                if("S".equals(GiveCouponNewActivity.grantType)){
                                    campaignGamesetreward.setPrizeCount(0);
                                }else if("V".equals(GiveCouponNewActivity.grantType)){
                                    campaignGamesetreward.setPrizeCount(null);
                                }
                                campaignGamesetreward.setPrizeName(prizeName);
                                campaignGamesetreward.setRewardType("1");
                                campaignGamesetrewardListNewData.add(campaignGamesetreward);
                            }
                        }else  if("条件四".equals(prizeName)){
                            if(campaignGamesetrewardListNewData4 != null && campaignGamesetrewardListNewData4.size()>0){
                                campaignGamesetrewardListNewData.addAll(campaignGamesetrewardListNewData4);
                            }else {
                                CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();
                                campaignGamesetreward.setPrizeOrder(4);
                                if("S".equals(GiveCouponNewActivity.grantType)){
                                    campaignGamesetreward.setPrizeCount(0);
                                }else if("V".equals(GiveCouponNewActivity.grantType)){
                                    campaignGamesetreward.setPrizeCount(null);
                                }
                                campaignGamesetreward.setPrizeName(prizeName);
                                campaignGamesetreward.setRewardType("1");
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
                tabLayout.setCurrentTab(0);
                tabLayout.notifyDataSetChanged();

            }
        }else if(resultCode == 6666) {
            ArrayList<Coupon>   selectCouponList = new ArrayList<>();
            String name = data.getStringExtra("prizeName");
            CampaignCouponListFragment.selectCoupons.clear();
            if(requestCode == 6661){
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
                            if(campaignCouponList1 == null || campaignCouponList1.size() ==0){
                                campaignCouponList1 = new ArrayList<>();
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

            }else if(requestCode == 6662){
                if("条件二".equals(name)){
                    ArrayList<Coupon> couponList  = (ArrayList<Coupon>)data.getSerializableExtra("selectCouponList");
                    if(couponList != null && couponList.size()>0) {
                        couponList2.addAll(couponList);
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

                            if(campaignCouponList2 == null || campaignCouponList2.size() ==0){
                                campaignCouponList2 = new ArrayList<>();
                            }
                            campaignCouponList2.add(campaignCoupon);
                        }
                    }

                    if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0) {
                        for (CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList) {
                            String prizeName = campaignGamesetreward.getPrizeName();
                            if ("条件二".equals(prizeName)) {
                                campaignGamesetreward.setList(campaignCouponList2);
                            }
                        }
                    }
                }
                pagerAdapter.updateData(prizeBeanList);
            }else if(requestCode == 6663){
                if("条件三".equals(name)){
                    ArrayList<Coupon> couponList  = (ArrayList<Coupon>)data.getSerializableExtra("selectCouponList");
                    if(couponList != null && couponList.size()>0) {
                        couponList3.addAll(couponList);
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
            }else if(requestCode == 6664){

                if("条件四".equals(name)){
                    ArrayList<Coupon> couponList  = (ArrayList<Coupon>)data.getSerializableExtra("selectCouponList");
                    if(couponList != null && couponList.size()>0) {
                        couponList4.addAll(couponList);
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

                            if(campaignCouponList4 == null || campaignCouponList4.size() ==0){
                                campaignCouponList4 = new ArrayList<>();
                            }
                            campaignCouponList4.add(campaignCoupon);
                        }
                    }

                    if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0) {
                        for (CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList) {
                            String prizeName = campaignGamesetreward.getPrizeName();
                            if ("条件四".equals(prizeName)) {
                                campaignGamesetreward.setList(campaignCouponList4);
                            }
                        }
                    }


                }
                pagerAdapter.updateData(prizeBeanList);
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

    protected  void copyAndCreate(){
        //复制并创建
        isCopyCreate = "NO";
        isEditCampaign = "YES";
        GiveCouponNewActivity.compaignStatus = "NEW";
        removeCampaignGamesetrewardList();
        if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0){
            for (CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList){
                if (campaignGamesetreward != null ){
                    String name = campaignGamesetreward.getPrizeName();
                    String picPictureId = null;
                    if("条件一".equals(name)){
                      
                        campaignGamesetreward.setRewardType("1");
                        campaignGamesetreward.setPrizeImageUrl(picPictureId);
                        campaignGamesetreward.setList(campaignCouponList1);
                    }else if("条件二".equals(name)){
                       
                        campaignGamesetreward.setRewardType("1");
                        campaignGamesetreward.setPrizeImageUrl(picPictureId);
                        campaignGamesetreward.setList(campaignCouponList2);
                    }else if("条件三".equals(name)){
                     
                        campaignGamesetreward.setRewardType("1");
                        campaignGamesetreward.setPrizeImageUrl(picPictureId);
                        campaignGamesetreward.setList(campaignCouponList3);
                    }else if("条件四".equals(name)){
                        
                        campaignGamesetreward.setRewardType("1");
                        campaignGamesetreward.setPrizeImageUrl(picPictureId);
                        campaignGamesetreward.setList(campaignCouponList4);
                    }
                }
            }
        }

        GiveCouponNewActivity.compaignStatus = "NEW";

        if(GiveCouponNewActivity.campaign != null){
            GiveCouponNewActivity.campaign.setCampaignId(null);
            GiveCouponNewActivity.campaign.setCampaignType("zj");
            GiveCouponNewActivity.campaign.setCampaignName("");
        }


        Intent intent1 = new Intent(GiveCouponNewNextConsumeActivity.this,GiveCouponNewActivity.class);

        intent1.putExtra("title",title);
        intent1.putExtra("isCopyCreate",isCopyCreate);
        intent1.putExtra("isEditCampaign",isEditCampaign);
        intent1.putExtra("compaignStatus","NEW");
        intent1.putExtra("grantType",GiveCouponNewActivity.grantType);
        ActivityUtils.startActivity(intent1,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }

    protected   void setIntentUpActivity(){

        //删除重复数据
        removeCampaignGamesetrewardList();
        if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0){
            for(CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList){
                String name = campaignGamesetreward.getPrizeName();
                String picPictureId = null;
                if("条件一".equals(name)){
                    
                    campaignGamesetreward.setRewardType("1");
                    campaignGamesetreward.setPrizeOrder(1);
                    campaignGamesetreward.setPrizeImageUrl(picPictureId);
                    campaignGamesetreward.setList(campaignCouponList1);
                }else if("条件二".equals(name)){

                    campaignGamesetreward.setRewardType("1");
                    campaignGamesetreward.setPrizeOrder(2);
                    campaignGamesetreward.setPrizeImageUrl(picPictureId);
                    campaignGamesetreward.setList(campaignCouponList2);
                }else if("条件三".equals(name)){

                    campaignGamesetreward.setRewardType("1");
                    campaignGamesetreward.setPrizeOrder(3);
                    campaignGamesetreward.setPrizeImageUrl(picPictureId);
                    campaignGamesetreward.setList(campaignCouponList3);
                }else if("条件四".equals(name)){
                    campaignGamesetreward.setRewardType("1");
                    campaignGamesetreward.setPrizeOrder(4);
                    campaignGamesetreward.setPrizeImageUrl(picPictureId);
                    campaignGamesetreward.setList(campaignCouponList4);
                }

            }


        }

        ActivityUtils.finishActivity(GiveCouponNewNextConsumeActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }




}
