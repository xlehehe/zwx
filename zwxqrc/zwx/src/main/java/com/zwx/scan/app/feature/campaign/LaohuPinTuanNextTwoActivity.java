package com.zwx.scan.app.feature.campaign;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.tablayout.SlidingTabLayout;
import com.zwx.library.tablayout.listener.OnTabSelectListener;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.LogUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CampaignGame;
import com.zwx.scan.app.data.bean.CampaignGamesetreward;
import com.zwx.scan.app.data.bean.CampaignNonrewardPic;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.CouponMaterial;
import com.zwx.scan.app.data.bean.MaterialGame;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.utils.ButtonUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2018/12/08
 * desc   :  老虎机/砸金蛋 第二步 奖项 未中奖 添加优惠券
 * version: 1.0
 **/
public class LaohuPinTuanNextTwoActivity extends BaseActivity<CampaignsContract.Presenter> implements CampaignsContract.View,View.OnClickListener {

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
    @BindView(R.id.iv_ellipsis_two)
    protected ImageView ivEllipsisTwo;
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

//    @BindView(R.id.fab)
//    protected ImageButton fab;

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    protected  ArrayList<PrizeBean> prizeBeanList = new ArrayList<>();

    /** 当前选中的栏目*/
    private int columnSelectIndex = 0;
    /** 左阴影部分*/
    public ImageView shade_left;
    /** 右阴影部分 */
    public ImageView shade_right;
    /** 屏幕宽度 */
    private int mScreenWidth = 0;
    /** Item宽度 */
    private int mItemWidth = 0;


    public FPagerAdapter pagerAdapter = null;


    private String name;

    protected static ArrayList<Coupon> couponList1 = new ArrayList<>();
    protected static ArrayList<Coupon> couponList2 = new ArrayList<>();
    protected static ArrayList<Coupon> couponList3 = new ArrayList<>();
    protected static ArrayList<Coupon> couponList4 = new ArrayList<>();
    protected static ArrayList<Coupon> couponList5 = new ArrayList<>();
    protected static ArrayList<Coupon> couponList6 = new ArrayList<>();
    protected static ArrayList<Coupon> couponList7 = new ArrayList<>();
    protected static ArrayList<Coupon> couponList8 = new ArrayList<>();
    protected static String prizePath;
    protected static String prizePath1;
    protected static String prizePath2;
    protected static String prizePath3;
    protected static String prizePath4;
    protected static String prizePath5;
    protected static String prizePath6;
    protected static String prizePath7;
    protected static String prizePath8;
    protected   ArrayList<CampaignCoupon> rewardCouponList = new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList1 =new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList2 =new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList3 =new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList4 =new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList5 =new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList6 =new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList7 =new ArrayList<>();

    protected  static ArrayList<CampaignCoupon> forwardCouponList = new ArrayList<>();

    protected  List<File> fileList = new ArrayList<>();
    protected  ArrayList<Coupon> couponList = new ArrayList<>();

    protected   String isEditCampaign = "NO";

    protected String isCopyCreate ;

    private String rewardType = "2";  //未中奖

    //奖项和安慰奖
    protected  static ArrayList<CampaignGamesetreward> campaignGamesetrewardList = new ArrayList<>();

    //未中奖
    protected  static ArrayList<CampaignNonrewardPic> campaignNonrewardPicList = new ArrayList<>();

    //默认图片
    protected static List<LocalMedia> selectList = new ArrayList<>();
    //从相册上传默认图
    private static final int REQUEST_CODE_TAKE_PHOTO = 1;
    private static final int REQUEST_CODE_ALBUM = 2;
    private static final int REQUEST_CODE_CROUP_PHOTO = 3;
    protected  Intent intent = null;

    protected String title;

    protected static  String isUpAndNext = "YES";

    protected   String storeIdCouponType ;

    private String userId;
    private String compId;
    protected boolean isInitFlag = false;
    private String campaignTypeId;
    protected static List<MaterialGame>  materialGameList = new ArrayList<>();
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销注册
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_laohu_pin_tuan_next_two;
    }



    @Override
    protected void initView() {
        //注销注册
        EventBus.getDefault().unregister(this);

        DaggerCampaignsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignsModule(new CampaignsModule(this))
                .build()
                .inject(this);

        if("lh".equals(LaohuPinTuanNewActivity.campaignType)){
            title ="老虎机活动设置" ;
            campaignTypeId = "0";
        }else {
            title = "砸金蛋活动设置";
            campaignTypeId = "1";
        }
        tvTitle.setText(title);


        isEditCampaign =  getIntent().getStringExtra("isEditCampaign");
        isCopyCreate = getIntent().getStringExtra("isCopyCreate");
        if("YES".equals(isEditCampaign)||"NEW".equals(isEditCampaign)){  //编辑
            if("YES".equals(isCopyCreate)){  //编辑页面复制并创建
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setText("复制并创建");
            }else {
                tvRight.setVisibility(View.GONE);
            }
        }

        if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
            ll_more_columns.setEnabled(true);
            button_more_columns.setEnabled(true);
        }else {
            ll_more_columns.setEnabled(false);
            button_more_columns.setEnabled(false);
        }
        userId = SPUtils.getInstance().getString("userId");
        compId = SPUtils.getInstance().getString("compId");

        String id = "";

        //模板选择
        id = LaohuPinTuanNewActivity.posterId;
        if(id == null){
            id = "";
        }
        //获取奖项图片
        presenter.queryLhGeTigerPoster(this,userId,"",id,"PrizeTwo",LaohuPinTuanNewActivity.campaignType);
        setSetTep();
        setLisenter();
    }

    @Override
    protected void initData() {

        //奖项
        initCampaignGamesetrewardList();
        //未中奖 若不为空则是未中奖，为空则是安慰奖
        initCampaignNonRewardList();
        initTabColumn();

    }

    protected void initPrizePath(){
        campaignNonrewardPicList = new ArrayList<>();
        campaignGamesetrewardList = new ArrayList<>();
        //奖项
        if(prizeBeanList == null||prizeBeanList.size()==0){
            PrizeBean prizeBean = new PrizeBean();
            prizeBean.setName("奖项一");
            prizeBean.setChecked(true);
            prizeBeanList.add(prizeBean);
            prizeBean = new PrizeBean();
            prizeBean.setName("未中奖");
            prizeBean.setChecked(true);
            prizeBeanList.add(prizeBean);

            CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();
            campaignGamesetreward.setPrizeCount(0);
            campaignGamesetreward.setRewardType("1");
            campaignGamesetreward.setPrizeName("奖项一");
            campaignGamesetreward.setPrizeOrder(1);
            campaignGamesetrewardList.add(campaignGamesetreward);

          /*  CampaignNonrewardPic campaignNonrewardPic = new CampaignNonrewardPic();
            campaignNonrewardPic.setPictureId(null);
            campaignNonrewardPic.setPrizeName("未中奖");
            campaignNonrewardPicList.add(campaignNonrewardPic);*/
        }


    }

    //初始化未中奖奖项
    private void initCampaignNonRewardList(){
        if(campaignNonrewardPicList != null && campaignNonrewardPicList.size()>0){
            PrizeBean prizeBean = new PrizeBean();
            prizeBean = new PrizeBean();
            prizeBean.setName("未中奖");
            prizeBean.setChecked(true);
            prizeBeanList.add(prizeBean);
            for(CampaignNonrewardPic campaignNonrewardPic:campaignNonrewardPicList){
                String picId = campaignNonrewardPic.getPictureId();
                String prizepath = HttpUrls.IMAGE_ULR + picId;
//                String imageId = prizepath.substring(prizepath.indexOf("get.do?id=")+10,prizepath.length());
                LocalMedia localMedia = new LocalMedia();
                localMedia.setCompressPath(prizepath);
                localMedia.setCompressed(true);
                localMedia.setPath(prizepath);
                selectList.add(localMedia);

            }

        }
    }

    private void initTabColumn(){

        if(LaohuPinTuanNextThreeActivity.campaignGame == null){
            LaohuPinTuanNextThreeActivity.campaignGame = new CampaignGame();
        }
        removeRePrizeBeanList();
        if(prizeBeanList != null && prizeBeanList.size()>0){

            for (PrizeBean prizeBean : prizeBeanList){
                String prizeName = prizeBean.getName();

                if("未中奖".equals(prizeName)){
                    LaohuPinTuanNextThreeActivity.campaignGame.setConsolationPrizeFlag("0");
                }else if("安慰奖".equals(prizeName)){
                    LaohuPinTuanNextThreeActivity.campaignGame.setConsolationPrizeFlag("1");
                }
            }
        }else {

            prizeBeanList = new ArrayList<>();
            PrizeBean prizeBean = new PrizeBean();
            prizeBean.setName("奖项一");
            prizeBean.setChecked(true);
            prizeBeanList.add(prizeBean);
            prizeBean = new PrizeBean();
            prizeBean.setName("未中奖");
            prizeBean.setChecked(true);
            LaohuPinTuanNextThreeActivity.campaignGame.setConsolationPrizeFlag("0");
            prizeBeanList.add(prizeBean);
        }
        setPagerAdapter();

    }

    private void setSetTep(){

        ivOne.setBackgroundResource(R.drawable.ic_first_clicked);
        ivEllipsisOne.setBackgroundResource(R.drawable.ic_ellipsis_blue);
        ivTwo.setBackgroundResource(R.drawable.ic_two_clicked);
        ivEllipsisTwo.setBackgroundResource(R.drawable.ic_ellipsis_gray);
        ivThree.setBackgroundResource(R.drawable.ic_three_untclick);

    }


    //初始化中奖
    private void initCampaignGamesetrewardList(){
        prizeBeanList = new ArrayList<>();
        couponList1.clear();
        couponList2.clear();
        couponList3.clear();
        couponList4.clear();
        couponList5.clear();
        couponList6.clear();
        couponList7.clear();
        //默认去缓存若没有 变量
        if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0){
            for(CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList){
                if(campaignGamesetreward != null){
                    String rewardType = campaignGamesetreward.getRewardType();
                    String pictureUrl = campaignGamesetreward.getPrizeImageUrl();
                    Integer rewardOrder = campaignGamesetreward.getPrizeOrder();
                    PrizeBean prizeBean = new PrizeBean();
                    if("1".equals(rewardType)){
                        if(rewardOrder != null){
                            if(rewardOrder == 1){
                                prizePath1  = HttpUrls.IMAGE_ULR+ campaignGamesetreward.getPrizeImageUrl();
                                prizeBean.setName(campaignGamesetreward.getPrizeName());
                                prizeBean.setChecked(true);
                                prizeBeanList.add(prizeBean);
                                campaignCouponList1 = campaignGamesetreward.getList();
                            }else if(rewardOrder == 2){
                                prizePath2 = HttpUrls.IMAGE_ULR+ campaignGamesetreward.getPrizeImageUrl();
                                prizeBean.setName(campaignGamesetreward.getPrizeName());
                                prizeBean.setChecked(true);
                                prizeBeanList.add(prizeBean);
                                campaignCouponList2 = campaignGamesetreward.getList();
                            }else if(rewardOrder == 3){
                                prizePath3 = HttpUrls.IMAGE_ULR+ campaignGamesetreward.getPrizeImageUrl();
                                prizeBean.setName(campaignGamesetreward.getPrizeName());
                                prizeBean.setChecked(true);
                                prizeBeanList.add(prizeBean);
                                campaignCouponList3= campaignGamesetreward.getList();
                            }else if(rewardOrder == 4){
                                prizePath4  = HttpUrls.IMAGE_ULR+ campaignGamesetreward.getPrizeImageUrl();
                                prizeBean.setName(campaignGamesetreward.getPrizeName());
                                prizeBean.setChecked(true);
                                prizeBeanList.add(prizeBean);
                                campaignCouponList4= campaignGamesetreward.getList();
                            }else if(rewardOrder == 5){
                                prizePath5 = HttpUrls.IMAGE_ULR+ campaignGamesetreward.getPrizeImageUrl();
                                prizeBean.setName(campaignGamesetreward.getPrizeName());
                                prizeBean.setChecked(true);
                                prizeBeanList.add(prizeBean);
                                campaignCouponList5= campaignGamesetreward.getList();
                            }else if(rewardOrder == 6){
                                prizePath6 = HttpUrls.IMAGE_ULR+ campaignGamesetreward.getPrizeImageUrl();
                                prizeBean.setName(campaignGamesetreward.getPrizeName());
                                prizeBean.setChecked(true);
                                prizeBeanList.add(prizeBean);
                                campaignCouponList6= campaignGamesetreward.getList();
                            }
                        }
                    }else if("0".equals(rewardType)||rewardType == "0"){ //安慰奖
                        prizePath7  = HttpUrls.IMAGE_ULR+ campaignGamesetreward.getPrizeImageUrl();
                        prizeBean.setName(campaignGamesetreward.getPrizeName());
                        prizeBean.setChecked(true);
                        prizeBeanList.add(prizeBean);
                        campaignCouponList7 = campaignGamesetreward.getList();
                        SPUtils.getInstance().remove("campaignNonrewardPicList");
                        campaignNonrewardPicList = new ArrayList<>();
                        //删除未中奖
                        if(prizeBeanList != null && prizeBeanList.size()>0){
                            for (int i =0;i<prizeBeanList.size();i++){
                                PrizeBean prizeBean1 = prizeBeanList.get(i);
                                String prizeName = prizeBean1.getName();
                                if("未中奖".equals(prizeName)){
                                    prizeBeanList.remove(i);
                                }
                            }
                        }
                    }
                    initCampaignRewardList(campaignGamesetreward);


                }
            }


        }else {
//            presenter.queryLhGeTigerPoster(this,userId,"","","PrizeTwo",LaohuPinTuanNewActivity.campaignType);
            initPrizePath();

        }

    }

    private void initCampaignRewardList(CampaignGamesetreward campaignGamesetreward){
        Integer count = campaignGamesetreward.getPrizeCount();
        String name = campaignGamesetreward.getPrizeName();
        if("奖项一".equals(name)){
            rewardCouponList = campaignCouponList1;
            if(campaignGamesetreward.getPrizeImageUrl()!=null && !"".equals(campaignGamesetreward.getPrizeImageUrl())){
               prizePath1 = HttpUrls.IMAGE_ULR + campaignGamesetreward.getPrizeImageUrl();
            }

        }else if("奖项二".equals(name)){
            rewardCouponList = campaignCouponList2;
            if(campaignGamesetreward.getPrizeImageUrl()!=null && !"".equals(campaignGamesetreward.getPrizeImageUrl())){
               prizePath2 =HttpUrls.IMAGE_ULR +  campaignGamesetreward.getPrizeImageUrl();
            }
        }else if("奖项三".equals(name)){
            rewardCouponList = campaignCouponList3;
            if(campaignGamesetreward.getPrizeImageUrl()!=null && !"".equals(campaignGamesetreward.getPrizeImageUrl())){
               prizePath3 = HttpUrls.IMAGE_ULR + campaignGamesetreward.getPrizeImageUrl();
            }
        }else if("奖项四".equals(name)){
            rewardCouponList = campaignCouponList4;
            if(campaignGamesetreward.getPrizeImageUrl()!=null && !"".equals(campaignGamesetreward.getPrizeImageUrl())){
                prizePath4 = HttpUrls.IMAGE_ULR + campaignGamesetreward.getPrizeImageUrl();
            }

        }else if("奖项五".equals(name)){
            rewardCouponList = campaignCouponList5;
            if(campaignGamesetreward.getPrizeImageUrl()!=null && !"".equals(campaignGamesetreward.getPrizeImageUrl())){
               prizePath5 = HttpUrls.IMAGE_ULR + campaignGamesetreward.getPrizeImageUrl();
            }
        }else if("奖项六".equals(name)){
            rewardCouponList = campaignCouponList6;
            if(campaignGamesetreward.getPrizeImageUrl()!=null && !"".equals(campaignGamesetreward.getPrizeImageUrl())){
                prizePath6 = HttpUrls.IMAGE_ULR + campaignGamesetreward.getPrizeImageUrl();
            }
        }else if("安慰奖".equals(name)){
            rewardCouponList = campaignCouponList7;
            if(campaignGamesetreward.getPrizeImageUrl()!=null && !"".equals(campaignGamesetreward.getPrizeImageUrl())){
                LaohuPinTuanNextTwoActivity.prizePath7 = campaignGamesetreward.getPrizeImageUrl();
            }
        }
        //本地缓存 提取
        if(rewardCouponList!=null && rewardCouponList.size()>0) {
            for (CampaignCoupon campaignCoupon : rewardCouponList) {
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
                if ( "奖项一".equals(name) && name == "奖项一") {
                    couponList1.add(coupon);
                } else if (  "奖项二".equals(name) && name == "奖项二") {
                    couponList2.add(coupon);
                } else if ("奖项三".equals(name) && name == "奖项三") {
                    couponList3.add(coupon);
                } else if ("奖项四".equals(name) && name == "奖项四") {
                    couponList4.add(coupon);
                } else if ("奖项五".equals(name) && name == "奖项五") {
                    couponList5.add(coupon);
                } else if ("奖项六".equals(name) && name == "奖项六") {
                    couponList6.add(coupon);
                } else if ("安慰奖".equals(name) && name == "安慰奖") {
                    couponList7.add(coupon);
                }


            }
        }
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
                    if("未中奖".equals(name)){
                        EventBus.getDefault().post(new LhPtUnPrizeEventBus(name));
                    }else {
                        EventBus.getDefault().post(new LhPtEventBus(name));
                    }
                    //解决卡顿问题 注释掉
                    pagerAdapter.updateData(prizeBeanList);
                    isInitFlag = true;
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
                    if("未中奖".equals(name)){
                        EventBus.getDefault().post(new LhPtUnPrizeEventBus(name));
                    }else {
                        EventBus.getDefault().post(new LhPtEventBus(name));
                    }



                    pagerAdapter.updateData(prizeBeanList);
                    isInitFlag = true;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }




    @OnClick({R.id.iv_back,R.id.button_more_columns,R.id.tv_right,R.id.btn_pre,R.id.btn_next})
    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.iv_back:
//               removeLaohuCampaignData();
                ActivityUtils.startActivity(CampaignListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
                break;
            case R.id.button_more_columns:

                for (PrizeBean prizeBean : prizeBeanList){
                    prizeBean.setChecked(true);

                }
                if (!ButtonUtils.isFastDoubleClick(R.id.btn_save)) {
                    intent = new Intent(LaohuPinTuanNextTwoActivity.this,LaohuPinTuanPrizeListActivity.class);
                    intent.putExtra("prizeBeanList",prizeBeanList);
                    ActivityUtils.startActivityForResult(LaohuPinTuanNextTwoActivity.this,intent,5001,R.anim.slide_in_right,R.anim.slide_out_left);

                }

                break;

            case R.id.tv_right:
                ToastUtils.showCustomShortBottom(getResources().getString(R.string.create_success));
                isUpAndNext = "COPY";
                if(!ButtonUtils.isFastDoubleClick(R.id.btn_next)){
                    if(prizeBeanList != null && prizeBeanList.size()>0){

                        for (PrizeBean prizeBean : prizeBeanList){
                            String name = prizeBean.getName();
                            List<File> fileList1 = new ArrayList<>();
                            if("奖项一".equals(name)){

                                if(prizePath1 != null && !"".equals(prizePath1)){
                                    if(!prizePath1.contains("get.do?id=")){
                                        if(prizePath1 != null && !"".equals(prizePath1)){
                                            File file = new File(prizePath1);
                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(LaohuPinTuanNextTwoActivity.this,fileList1,name);
                                    }

                                }

                            }else if("奖项二".equals(name)){
                                if(prizePath2!= null && !"".equals(prizePath2)) {
                                    if (!prizePath2.contains("get.do?id=")) {
                                        if (prizePath2 != null && !"".equals(prizePath2)) {
                                            File file = new File(prizePath2);

                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(LaohuPinTuanNextTwoActivity.this,fileList1,name);
                                    }
                                }
                            }else if("奖项三".equals(name)){
                                if(prizePath3 != null && !"".equals(prizePath3)) {
                                    if (!prizePath3.contains("get.do?id=")) {
                                        if (prizePath3 != null && !"".equals(prizePath3)) {
                                            File file = new File(prizePath3);
                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(LaohuPinTuanNextTwoActivity.this,fileList1,name);
                                    }
                                }
                            }else if("奖项四".equals(name)){
                                if(prizePath4 != null && !"".equals(prizePath4)) {
                                    if (!prizePath4.contains("get.do?id=")) {
                                        if (prizePath4 != null && !"".equals(prizePath4)) {
                                            File file = new File(prizePath4);
                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(LaohuPinTuanNextTwoActivity.this,fileList1,name);
                                    }
                                }
                            }else if("奖项五".equals(name)){
                                if(prizePath5 != null && !"".equals(prizePath5)) {
                                    if (!prizePath5.contains("get.do?id=")) {
                                        if (prizePath5 != null && !"".equals(prizePath5)) {
                                            File file = new File(prizePath5);
                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(LaohuPinTuanNextTwoActivity.this,fileList1,name);
                                    }
                                }
                            }else if("奖项六".equals(name)){
                                if(prizePath6 != null && !"".equals(prizePath6)) {
                                    if (!prizePath6.contains("get.do?id=")) {
                                        if (prizePath6 != null && !"".equals(prizePath6)) {
                                            File file = new File(prizePath6);
                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(LaohuPinTuanNextTwoActivity.this,fileList1,name);
                                    }
                                }
                            }else if("安慰奖".equals(name)){
                                rewardType = "0";
                                if(prizePath7 != null && !"".equals(prizePath7)) {
                                    if (!prizePath7.contains("get.do?id=")) {
                                        if (prizePath7 != null && !"".equals(prizePath7)) {
                                            File file = new File(prizePath7);
                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(LaohuPinTuanNextTwoActivity.this,fileList1,name);
                                    }
                                }
                            }else if("未中奖".equals(name)){

                                rewardType = "2";  //表示未中奖
                                fileList.clear();
                                if(selectList != null && selectList.size()>0){
                                    for(LocalMedia localMedia1 : selectList){

                                        String unPrizePath = localMedia1.getPath();
                                        if(!unPrizePath.contains("get.do?id=")){
                                            if(unPrizePath != null && !"".equals(unPrizePath)){
                                                File file = new File(unPrizePath);
                                                fileList.add(file);
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }

                    if("2".equals(rewardType)){
                        if(fileList != null && fileList.size()>0){
                            presenter.uploadFiles(LaohuPinTuanNextTwoActivity.this,fileList,"未中奖");

                        }else {
                            copyAndCreate();
                        }
                    }else if("0".equals(rewardType)){
                        copyAndCreate();
                    }else {
                        copyAndCreate();
                    }


                }
                break;

            case R.id.btn_pre:
                isUpAndNext = "YES";
                if(!ButtonUtils.isFastDoubleClick(R.id.btn_next)){
                    if(prizeBeanList != null && prizeBeanList.size()>0){

                        for (PrizeBean prizeBean : prizeBeanList){
                            String name = prizeBean.getName();
                            List<File> fileList1 = new ArrayList<>();
                            if("奖项一".equals(name)){

                                if(prizePath1 != null && !"".equals(prizePath1)){
                                    if(!prizePath1.contains("get.do?id=")){
                                        if(prizePath1 != null && !"".equals(prizePath1)){
                                            File file = new File(prizePath1);
                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(this,fileList1,name);
                                    }

                                }

                            }else if("奖项二".equals(name)){
                                if(prizePath2!= null && !"".equals(prizePath2)) {
                                    if (!prizePath2.contains("get.do?id=")) {
                                        if (prizePath2 != null && !"".equals(prizePath2)) {
                                            File file = new File(prizePath2);

                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(this,fileList1,name);
                                    }
                                }
                            }else if("奖项三".equals(name)){
                                if(prizePath3 != null && !"".equals(prizePath3)) {
                                    if (!prizePath3.contains("get.do?id=")) {
                                        if (prizePath3 != null && !"".equals(prizePath3)) {
                                            File file = new File(prizePath3);
                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(this,fileList1,name);
                                    }
                                }
                            }else if("奖项四".equals(name)){
                                if(prizePath4 != null && !"".equals(prizePath4)) {
                                    if (!prizePath4.contains("get.do?id=")) {
                                        if (prizePath4 != null && !"".equals(prizePath4)) {
                                            File file = new File(prizePath4);
                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(this,fileList1,name);
                                    }
                                }
                            }else if("奖项五".equals(name)){
                                if(prizePath5 != null && !"".equals(prizePath5)) {
                                    if (!prizePath5.contains("get.do?id=")) {
                                        if (prizePath5 != null && !"".equals(prizePath5)) {
                                            File file = new File(prizePath5);
                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(this,fileList1,name);
                                    }
                                }
                            }else if("奖项六".equals(name)){
                                if(prizePath6 != null && !"".equals(prizePath6)) {
                                    if (!prizePath6.contains("get.do?id=")) {
                                        if (prizePath6 != null && !"".equals(prizePath6)) {
                                            File file = new File(prizePath6);
                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(this,fileList1,name);
                                    }
                                }
                            }else if("安慰奖".equals(name)){
                                rewardType = "0";
                                if(prizePath7 != null && !"".equals(prizePath7)) {
                                    if (!prizePath7.contains("get.do?id=")) {
                                        if (prizePath7 != null && !"".equals(prizePath7)) {
                                            File file = new File(prizePath7);
                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(this,fileList1,name);
                                    }
                                }
                            }else if("未中奖".equals(name)){

                                rewardType = "2";  //表示未中奖
                                fileList.clear();
                                if(selectList != null && selectList.size()>0){
                                    for(LocalMedia localMedia1 : selectList){

                                        String unPrizePath = localMedia1.getPath();
                                        if(!unPrizePath.contains("get.do?id=")){
                                            if(unPrizePath != null && !"".equals(unPrizePath)){
                                                File file = new File(unPrizePath);
                                                fileList.add(file);
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }

                    if("2".equals(rewardType)){
                        if(fileList != null && fileList.size()>0){
                            presenter.uploadFiles(LaohuPinTuanNextTwoActivity.this,fileList,"未中奖");

                        }else {
                            setIntentUpActivity();
                        }
                    }else if("0".equals(rewardType)){
                       setIntentUpActivity();
                    }else {
                        setIntentUpActivity();
                    }


                }
                break;

            case R.id.btn_next:
                isUpAndNext = "NO";
                if(!ButtonUtils.isFastDoubleClick(R.id.btn_next)){
                    if(prizeBeanList != null && prizeBeanList.size()>0){

                        for (PrizeBean prizeBean : prizeBeanList){
                            String name = prizeBean.getName();
                            List<File> fileList1 = new ArrayList<>();
                            if("奖项一".equals(name)){

                                if(prizePath1 != null && !"".equals(prizePath1)){
                                    if(!prizePath1.contains("get.do?id=")){
                                        if(prizePath1 != null && !"".equals(prizePath1)){
                                            File file = new File(prizePath1);
                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(this,fileList1,name);
                                    }

                                }

                            }else if("奖项二".equals(name)){
                                if(prizePath2!= null && !"".equals(prizePath2)) {
                                    if (!prizePath2.contains("get.do?id=")) {
                                        if (prizePath2 != null && !"".equals(prizePath2)) {
                                            File file = new File(prizePath2);

                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(this,fileList1,name);
                                    }
                                }
                            }else if("奖项三".equals(name)){
                                if(prizePath3 != null && !"".equals(prizePath3)) {
                                    if (!prizePath3.contains("get.do?id=")) {
                                        if (prizePath3 != null && !"".equals(prizePath3)) {
                                            File file = new File(prizePath3);
                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(this,fileList1,name);
                                    }
                                }
                            }else if("奖项四".equals(name)){
                                if(prizePath4 != null && !"".equals(prizePath4)) {
                                    if (!prizePath4.contains("get.do?id=")) {
                                        if (prizePath4 != null && !"".equals(prizePath4)) {
                                            File file = new File(prizePath4);
                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(this,fileList1,name);
                                    }
                                }
                            }else if("奖项五".equals(name)){
                                if(prizePath5 != null && !"".equals(prizePath5)) {
                                    if (!prizePath5.contains("get.do?id=")) {
                                        if (prizePath5 != null && !"".equals(prizePath5)) {
                                            File file = new File(prizePath5);
                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(this,fileList1,name);
                                    }
                                }
                            }else if("奖项六".equals(name)){
                                if(prizePath6 != null && !"".equals(prizePath6)) {
                                    if (!prizePath6.contains("get.do?id=")) {
                                        if (prizePath6 != null && !"".equals(prizePath6)) {
                                            File file = new File(prizePath6);
                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(this,fileList1,name);
                                    }
                                }
                            }else if("安慰奖".equals(name)){
                                rewardType = "0";
                                if(prizePath7 != null && !"".equals(prizePath7)) {
                                    if (!prizePath7.contains("get.do?id=")) {
                                        if (prizePath7 != null && !"".equals(prizePath7)) {
                                            File file = new File(prizePath7);
                                            fileList1.add(file);
                                        }
                                    }
                                    if(fileList1 != null && fileList1.size()>0){
                                        presenter.uploadFiles(this,fileList1,name);
                                    }
                                }
                            }else if("未中奖".equals(name)){

                                rewardType = "2";  //表示未中奖
                                fileList.clear();
                                if(selectList != null && selectList.size()>0){
                                    for(LocalMedia localMedia1 : selectList){

                                        String unPrizePath = localMedia1.getPath();
                                        if(!unPrizePath.contains("get.do?id=")){
                                            if(unPrizePath != null && !"".equals(unPrizePath)){
                                                File file = new File(unPrizePath);
                                                fileList.add(file);
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }

                    if("2".equals(rewardType)){
                        if(fileList != null && fileList.size()>0){
                            presenter.uploadFiles(LaohuPinTuanNextTwoActivity.this,fileList,"未中奖");

                        }else {
                            setIntentActivity();
                        }
                    }else if("0".equals(rewardType)){
                        setIntentActivity();
                    }else {
                        setIntentActivity();
                    }


                }


                break;

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        removeLaohuCampaignData();

        ActivityUtils.startActivity(CampaignListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
        finish();

    }
    protected void removeRePrizeBeanList(){
        if(prizeBeanList != null && prizeBeanList.size()>0){
            for(int i=0;i<prizeBeanList.size();i++){
                for(int j=prizeBeanList.size()-1;j>i;j--){
                    if(String.valueOf(prizeBeanList.get(j).getName()).equals(String.valueOf(prizeBeanList.get(i).getName()))){
                        prizeBeanList.remove(j);
                    }
                }
            }
        }

    }


    private void removeLaohuCampaignData(){

        SPUtils.getInstance().remove("brandLogoLHPTOne");
        SPUtils.getInstance().remove("storeIdArrayLHPTOne");
        SPUtils.getInstance().remove("storeNameArrayLHPTOne");
        SPUtils.getInstance().remove("brandPostionLHPTOne");


        SPUtils.getInstance().remove("brandLogoLHP");
        SPUtils.getInstance().remove("brandPostionLHPT");

        LaohuPinTuanNextTwoActivity.campaignGamesetrewardList = new ArrayList<>();
        LaohuPinTuanNextTwoActivity.campaignNonrewardPicList = new ArrayList<>();

        LaohuPinTuanNewActivity.campaign = new Campaign();
        LaohuPinTuanNextThreeActivity.campaignGame = new CampaignGame();
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

    protected void removeCampaignNonrewardPicList(ArrayList<CampaignNonrewardPic> campaignCouponList){
        if(campaignCouponList != null && campaignCouponList.size()>0){
            for(int i=0;i<campaignCouponList.size();i++){
                for(int j=campaignCouponList.size()-1;j>i;j--){
                    if((campaignCouponList.get(i).getPictureId().equals(campaignCouponList.get(j).getPictureId()))){
                        campaignCouponList.remove(j);
                    }
                }
            }
        }

    }

    protected void removeSelectImageList(List<LocalMedia> selectList){
        if(selectList != null && selectList.size()>0){
            for(int i=0;i<selectList.size();i++){
                for(int j=selectList.size()-1;j>i;j--){
                    String prizePath1 = selectList.get(i).getCompressPath();
                    String prizePath2 = selectList.get(j).getCompressPath();
                    if(prizePath1.contains("get.do?")&& prizePath1.contains("get.do?")){
                        String picId1 = prizePath1.substring(prizePath1.indexOf("get.do?id=")+10,prizePath1.length());
                        String picId2 = prizePath1.substring(prizePath2.indexOf("get.do?id=")+10,prizePath2.length());
                        if(picId1.equals(picId2)){
                            selectList.remove(j);
                        }
                    }


                }
            }
        }

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
                    if("奖项一".equals(name)){
                        mFragmentList.add(LaohuPinTuanNextTwoFragment.getInstance(name,couponList1,prizePath1));
                    }else if("奖项二".equals(name)){
                        mFragmentList.add(LaohuPinTuanNextTwoFragment.getInstance(name,couponList2,prizePath2));
                    }else if("奖项三".equals(name)){
                        mFragmentList.add(LaohuPinTuanNextTwoFragment.getInstance(name,couponList3,prizePath3));
                    }else if("奖项四".equals(name)){
                        mFragmentList.add(LaohuPinTuanNextTwoFragment.getInstance(name,couponList4,prizePath4));
                    }else if("奖项五".equals(name)){
                        mFragmentList.add(LaohuPinTuanNextTwoFragment.getInstance(name,couponList5,prizePath5));
                    }else if("奖项六".equals(name)){
                        mFragmentList.add(LaohuPinTuanNextTwoFragment.getInstance(name,couponList6,prizePath6));
                    }else if("安慰奖".equals(name)){
                        mFragmentList.add(LaohuPinTuanNextTwoFragment.getInstance(name, couponList7,prizePath7));
                    }else if("未中奖".equals(name)){
                        mFragmentList.add(LaohuPinTuanNextUnPrizeFragment.getInstance(name,couponList8,prizePath8));
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
                     if("奖项一".equals(name)){
                        fragments.add(LaohuPinTuanNextTwoFragment.getInstance(name,couponList1,prizePath1));
                    }else if("奖项二".equals(name)){
                        fragments.add(LaohuPinTuanNextTwoFragment.getInstance(name,couponList2,prizePath2));
                    }else if("奖项三".equals(name)){
                        fragments.add(LaohuPinTuanNextTwoFragment.getInstance(name,couponList3,prizePath3));
                    }else if("奖项四".equals(name)){
                        fragments.add(LaohuPinTuanNextTwoFragment.getInstance(name,couponList4,prizePath4));
                    }else if("奖项五".equals(name)){
                        fragments.add(LaohuPinTuanNextTwoFragment.getInstance(name,couponList5,prizePath5));
                    }else if("奖项六".equals(name)){
                        fragments.add(LaohuPinTuanNextTwoFragment.getInstance(name,couponList6,prizePath6));
                    }else if("安慰奖".equals(name)){
                         fragments.add(LaohuPinTuanNextTwoFragment.getInstance(name,couponList7,prizePath7));
                     }else if("未中奖".equals(name)){
                         fragments.add(LaohuPinTuanNextUnPrizeFragment.getInstance(name,couponList8,prizePath8));
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
         * 解决切换奖项tab页 决定是否重新加载新的Fragment，于是需要在自定的FragmentPagerAdapter中重写getItemId，得到不同的itemId。
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
         /*  if(isAdd){
//             return baseId + position;
               return position;
            }else {
               return baseId  ;
            }*/
       }

        /**
         * 更新fragment的数量之后，在调用notifyDataSetChanged之前，changeId(1) 改变id，改变tag
         */
   /*     public void changeId(int n) {
//            baseId += getCount()+n;
            if(n>=0){
                isAdd = true;
                baseId += getCount();

            }else {
                isAdd =false;
                baseId += n;
            }

            LogUtils.e("baseId---"+baseId+"getCount()--"+getCount());
        }*/

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 5001){
            if(requestCode == 5001){
                  if(data != null){
                      prizeBeanList = (ArrayList<PrizeBean>)data.getSerializableExtra("prizeBeanList");
                  }

                ArrayList<CampaignGamesetreward> campaignGamesetrewardListNewData = new ArrayList<>();
                ArrayList<CampaignGamesetreward> campaignGamesetrewardListNewData1 = new ArrayList<>();
                ArrayList<CampaignGamesetreward> campaignGamesetrewardListNewData2 = new ArrayList<>();
               ArrayList<CampaignGamesetreward> campaignGamesetrewardListNewData3 = new ArrayList<>();
                ArrayList<CampaignGamesetreward> campaignGamesetrewardListNewData4 = new ArrayList<>();
                ArrayList<CampaignGamesetreward> campaignGamesetrewardListNewData5 = new ArrayList<>();
                ArrayList<CampaignGamesetreward> campaignGamesetrewardListNewData6 = new ArrayList<>();
                ArrayList<CampaignGamesetreward> campaignGamesetrewardListNewData7 = new ArrayList<>();

                if(prizeBeanList.size()<campaignGamesetrewardList.size()){  //若返回新的奖项项比旧的奖项项少，则重新赋值，否则新增
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

                                    if("奖项一".equals(prizeName2)){
                                        campaignGamesetreward.setPrizeOrder(1);
                                        campaignGamesetreward.setList(campaignCouponList1);
                                        campaignGamesetrewardListNewData1.add(campaignGamesetreward);
                                    }else  if("奖项二".equals(prizeName2)){
                                        campaignGamesetreward.setPrizeOrder(2);
                                        campaignGamesetreward.setList(campaignCouponList2);
                                        campaignGamesetrewardListNewData1.add(campaignGamesetreward);
                                    }else  if("奖项三".equals(prizeName2)){
                                        campaignGamesetreward.setPrizeOrder(2);
                                        campaignGamesetreward.setList(campaignCouponList3);
                                        campaignGamesetrewardListNewData3.add(campaignGamesetreward);
                                    }else  if("奖项四".equals(prizeName2)){
                                        campaignGamesetreward.setPrizeOrder(4);
                                        campaignGamesetreward.setList(campaignCouponList4);
                                        campaignGamesetrewardListNewData4.add(campaignGamesetreward);
                                    }else  if("奖项五".equals(prizeName2)){
                                        campaignGamesetreward.setPrizeOrder(5);
                                        campaignGamesetreward.setList(campaignCouponList5);
                                        campaignGamesetrewardListNewData5.add(campaignGamesetreward);
                                    }else  if("奖项六".equals(prizeName2)){
                                        campaignGamesetreward.setPrizeOrder(6);
                                        campaignGamesetreward.setList(campaignCouponList6);
                                        campaignGamesetrewardListNewData6.add(campaignGamesetreward);
                                    }else  if("安慰奖".equals(prizeName2)){
                                        campaignGamesetreward.setPrizeOrder(0);
                                        campaignGamesetreward.setRewardType("0");
                                        campaignGamesetreward.setPrizeCount(0);
                                        campaignGamesetreward.setList(campaignCouponList7);
                                        campaignGamesetrewardListNewData7.add(campaignGamesetreward);
                                    }
                                }
                            }
                        }


                    }



                }else {   //奖项项比旧奖项项多，重新添加排序
                    if(campaignGamesetrewardList != null && !"".equals(campaignGamesetrewardList)){
                        for (int i=0;i<campaignGamesetrewardList.size();i++){
                            CampaignGamesetreward campaignGamesetreward = campaignGamesetrewardList.get(i);
                            if(campaignGamesetreward != null){
                                String prizeName2 = campaignGamesetreward.getPrizeName();
                                campaignGamesetreward.setRewardType("1");

                                if("奖项一".equals(prizeName2)){
                                    campaignGamesetreward.setPrizeOrder(1);
                                    campaignGamesetrewardListNewData1.add(campaignGamesetreward);
                                }else  if("奖项二".equals(prizeName2)){
                                    campaignGamesetreward.setPrizeOrder(2);
                                    campaignGamesetrewardListNewData1.add(campaignGamesetreward);
                                }else  if("奖项三".equals(prizeName2)){
                                    campaignGamesetreward.setPrizeOrder(2);
                                    campaignGamesetrewardListNewData3.add(campaignGamesetreward);
                                }else  if("奖项四".equals(prizeName2)){
                                    campaignGamesetreward.setPrizeOrder(4);
                                    campaignGamesetrewardListNewData4.add(campaignGamesetreward);
                                }else  if("奖项五".equals(prizeName2)){
                                    campaignGamesetreward.setPrizeOrder(5);
                                    campaignGamesetrewardListNewData5.add(campaignGamesetreward);
                                }else  if("奖项六".equals(prizeName2)){
                                    campaignGamesetreward.setPrizeOrder(6);
                                    campaignGamesetrewardListNewData6.add(campaignGamesetreward);
                                }else  if("安慰奖".equals(prizeName2)){
                                    campaignGamesetreward.setPrizeOrder(0);
                                    campaignGamesetreward.setRewardType("0");
                                    campaignGamesetreward.setPrizeCount(0);
                                    campaignGamesetrewardListNewData7.add(campaignGamesetreward);
                                }
                            }
                        }
                    }


                    for (PrizeBean prizeBean:prizeBeanList){
                        String prizeName = prizeBean.getName();
                        if("奖项一".equals(prizeName)){
                            if(campaignGamesetrewardListNewData1 != null && campaignGamesetrewardListNewData1.size()>0){
                                campaignGamesetrewardListNewData.addAll(campaignGamesetrewardListNewData1);
                            }else {
                                couponList1 = new ArrayList<>();
                                CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();
                                campaignGamesetreward.setPrizeOrder(1);
                                campaignGamesetreward.setPrizeCount(0);
                                campaignGamesetreward.setPrizeName(prizeName);
                                campaignGamesetreward.setRewardType("1");
                                campaignGamesetrewardListNewData.add(campaignGamesetreward);
                            }
                        }else  if("奖项二".equals(prizeName)){
                            if(campaignGamesetrewardListNewData2 != null && campaignGamesetrewardListNewData2.size()>0){
                                campaignGamesetrewardListNewData.addAll(campaignGamesetrewardListNewData2);
                            }else {
                                couponList2 = new ArrayList<>();
                                CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();
                                campaignGamesetreward.setPrizeOrder(2);
                                campaignGamesetreward.setPrizeCount(0);
                                campaignGamesetreward.setPrizeName(prizeName);
                                campaignGamesetreward.setRewardType("1");
                                campaignGamesetrewardListNewData.add(campaignGamesetreward);
                            }
                        }else  if("奖项三".equals(prizeName)){
                            if(campaignGamesetrewardListNewData3 != null && campaignGamesetrewardListNewData3.size()>0){
                                campaignGamesetrewardListNewData.addAll(campaignGamesetrewardListNewData3);
                            }else {
                                couponList3 = new ArrayList<>();
                                CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();
                                campaignGamesetreward.setPrizeOrder(3);
                                campaignGamesetreward.setPrizeCount(0);
                                campaignGamesetreward.setPrizeName(prizeName);
                                campaignGamesetreward.setRewardType("1");
                                campaignGamesetrewardListNewData.add(campaignGamesetreward);
                            }
                        }else  if("奖项四".equals(prizeName)){
                            if(campaignGamesetrewardListNewData3 != null && campaignGamesetrewardListNewData3.size()>0){
                                campaignGamesetrewardListNewData.addAll(campaignGamesetrewardListNewData3);
                            }else {
                                couponList4 = new ArrayList<>();
                                CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();
                                campaignGamesetreward.setPrizeOrder(4);
                                campaignGamesetreward.setPrizeCount(0);
                                campaignGamesetreward.setPrizeName(prizeName);
                                campaignGamesetreward.setRewardType("1");
                                campaignGamesetrewardListNewData.add(campaignGamesetreward);
                            }
                        }else  if("奖项五".equals(prizeName)){
                            if(campaignGamesetrewardListNewData3 != null && campaignGamesetrewardListNewData3.size()>0){
                                campaignGamesetrewardListNewData.addAll(campaignGamesetrewardListNewData3);
                            }else {
                                couponList5 = new ArrayList<>();
                                CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();
                                campaignGamesetreward.setPrizeOrder(5);
                                campaignGamesetreward.setPrizeCount(0);
                                campaignGamesetreward.setPrizeName(prizeName);
                                campaignGamesetreward.setRewardType("1");
                                campaignGamesetrewardListNewData.add(campaignGamesetreward);
                            }
                        }else  if("奖项六".equals(prizeName)){
                            if(campaignGamesetrewardListNewData3 != null && campaignGamesetrewardListNewData3.size()>0){
                                campaignGamesetrewardListNewData.addAll(campaignGamesetrewardListNewData3);
                            }else {
                                couponList6 = new ArrayList<>();
                                CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();
                                campaignGamesetreward.setPrizeOrder(6);
                                campaignGamesetreward.setPrizeCount(0);
                                campaignGamesetreward.setPrizeName(prizeName);
                                campaignGamesetreward.setRewardType("1");
                                campaignGamesetrewardListNewData.add(campaignGamesetreward);
                            }
                        }else  if("安慰奖".equals(prizeName)){
                            if(campaignGamesetrewardListNewData4 != null && campaignGamesetrewardListNewData4.size()>0){
                                campaignGamesetrewardListNewData.addAll(campaignGamesetrewardListNewData4);
                            }else {
                                couponList7 = new ArrayList<>();
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

                    pagerAdapter = new FPagerAdapter(getSupportFragmentManager(),prizeBeanList);
                    viewPager.setAdapter(pagerAdapter);
                    tabLayout.setViewPager(viewPager);
                    viewPager.setCurrentItem(0);
                    tabLayout.setCurrentTab(0,true);
                    tabLayout.setTextSelectColor(getResources().getColor(R.color.list_red));
                    tabLayout.setSelected(true);
                    pagerAdapter.updateData(prizeBeanList);
//                    tabLayout.setCurrentTab(0);

                }
      /*          pagerAdapter = new FPagerAdapter(getSupportFragmentManager(),prizeBeanList);
                viewPager.setAdapter(pagerAdapter);
                tabLayout.setViewPager(viewPager);
                viewPager.setCurrentItem(0);
                tabLayout.setCurrentTab(0);*/
            /*    if(prizeBeanList.size()<campaignGamesetrewardList.size()){  //如果新奖项比就将向 少，则tablayout的位置减一，否则0开始
                    tabLayout.setCurrentTab(-1);
                }else {
                    tabLayout.setCurrentTab(0);
                }*/

                isInitFlag = false;

            }
        }else if(resultCode == 5555) {
            ArrayList<Coupon>   selectCouponList = new ArrayList<>();
            String name = data.getStringExtra("prizeName");
            CampaignCouponListFragment.selectCoupons.clear();
            if(requestCode == 5551){
                if("奖项一".equals(name)){
                    ArrayList<Coupon> couponList   = (ArrayList<Coupon>)data.getSerializableExtra("selectCouponList");
                    couponList1.addAll(couponList);
                    if(couponList != null && couponList.size()>0) {
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
                            campaignCoupon.setCouponQnt(coupon.getCouponQnt());
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
                        if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0) {
                            for (CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList) {
                                String prizeName = campaignGamesetreward.getPrizeName();
                                if ("奖项一".equals(prizeName)) {
                                    campaignGamesetreward.setList(campaignCouponList1);
                                }
                            }
                        }

                    }
                }
                pagerAdapter.updateData(prizeBeanList);

            }else if(requestCode == 5552){
                if("奖项二".equals(name)){
                    ArrayList<Coupon> couponList  = (ArrayList<Coupon>)data.getSerializableExtra("selectCouponList");
//                    removeSelectCouponList(couponList2);

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
                            campaignCoupon.setCouponQnt(coupon.getCouponQnt());
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
                        if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0) {
                            for (CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList) {
                                String prizeName = campaignGamesetreward.getPrizeName();
                                if ("奖项二".equals(prizeName)) {
                                    campaignGamesetreward.setList(campaignCouponList2);
                                }
                            }
                        }

                    }
                }
                pagerAdapter.updateData(prizeBeanList);
            }else if(requestCode == 5553){
                if("奖项三".equals(name)){
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
                            campaignCoupon.setCouponQnt(coupon.getCouponQnt());
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
                            if ("奖项三".equals(prizeName)) {
                                campaignGamesetreward.setList(campaignCouponList3);
                            }
                        }
                    }

                }
                pagerAdapter.updateData(prizeBeanList);
            }else if(requestCode == 5554){

                if("奖项四".equals(name)){
                    ArrayList<Coupon> couponList = (ArrayList<Coupon>)data.getSerializableExtra("selectCouponList");
                    if(couponList != null && couponList.size()>0) {
                        couponList4.addAll(couponList);
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

                            if(campaignCouponList4 == null || campaignCouponList4.size() ==0){
                                campaignCouponList4 = new ArrayList<>();
                            }
                            campaignCouponList4.add(campaignCoupon);
                        }
                    }

                    if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0) {
                        for (CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList) {
                            String prizeName = campaignGamesetreward.getPrizeName();
                            if ("奖项四".equals(prizeName)) {
                                campaignGamesetreward.setList(campaignCouponList4);
                            }
                        }
                    }
                }
                pagerAdapter.updateData(prizeBeanList);
            }else if(requestCode == 5555){

                if("奖项五".equals(name)){
                    ArrayList<Coupon> couponList = (ArrayList<Coupon>)data.getSerializableExtra("selectCouponList");
                    if(couponList != null && couponList.size()>0) {
                        couponList5.addAll(couponList);
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
                            if(campaignCouponList5 == null || campaignCouponList5.size() ==0){
                                campaignCouponList5 = new ArrayList<>();
                            }
                            campaignCouponList5.add(campaignCoupon);
                        }
                    }

                    if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0) {
                        for (CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList) {
                            String prizeName = campaignGamesetreward.getPrizeName();
                            if ("奖项五".equals(prizeName)) {
                                campaignGamesetreward.setList(campaignCouponList5);
                            }
                        }
                    }
                }

                pagerAdapter.updateData(prizeBeanList);
            }else if(requestCode == 5556){

                if("奖项六".equals(name)){
                    ArrayList<Coupon> couponList = (ArrayList<Coupon>)data.getSerializableExtra("selectCouponList");
                    if(couponList != null && couponList.size()>0) {
                        couponList6.addAll(couponList);
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

                            if(campaignCouponList6 == null || campaignCouponList6.size() ==0){
                                campaignCouponList6 = new ArrayList<>();
                            }
                            campaignCouponList6.add(campaignCoupon);
                        }
                    }

                    if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0) {
                        for (CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList) {
                            String prizeName = campaignGamesetreward.getPrizeName();
                            if ("奖项六".equals(prizeName)) {
                                campaignGamesetreward.setList(campaignCouponList6);
                            }
                        }
                    }
                }
                pagerAdapter.updateData(prizeBeanList);
            }else if(requestCode == 5557){

                if("安慰奖".equals(name)){
                    ArrayList<Coupon> couponList = (ArrayList<Coupon>)data.getSerializableExtra("selectCouponList");
                    if(couponList != null && couponList.size()>0) {
                        couponList7.addAll(couponList);
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

                            if(campaignCouponList7 == null || campaignCouponList7.size() ==0){
                                campaignCouponList7 = new ArrayList<>();
                            }
                            campaignCouponList7.add(campaignCoupon);
                        }
                    }

                    if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0) {
                        for (CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList) {
                            String prizeName = campaignGamesetreward.getPrizeName();
                            if ("安慰奖".equals(prizeName)) {
                                campaignGamesetreward.setList(campaignCouponList7);
                            }
                        }
                    }
                }
                pagerAdapter.updateData(prizeBeanList);
            }



        }else if (resultCode == RESULT_OK) {   //自定义选择图片
            LocalMedia localMedia = new LocalMedia();
            List<File> fileList1  = new ArrayList<>();
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST1: //奖项一
                    // 图片选择结果回调
                    List<LocalMedia> selectList1 = PictureSelector.obtainMultipleResult(data);
                    if(selectList1 != null && selectList1.size()>0){
                        localMedia = selectList1.get(0);
                        prizePath1 = localMedia.getCompressPath();
                        File file= new File(prizePath1);
                        fileList1.add(file);
                    }
//                    pagerAdapter.updateData(prizeBeanList);
                    if(fileList1 != null && fileList1.size()>0){
                        presenter.uploadFiles(LaohuPinTuanNextTwoActivity.this,fileList1,"奖项一");
                    }
                    break;
                case PictureConfig.CHOOSE_REQUEST2:
                    // 图片选择结果回调
                    List<LocalMedia> selectList2 = PictureSelector.obtainMultipleResult(data);
                    if(selectList2 != null && selectList2.size()>0){
                        localMedia = selectList2.get(0);
                        prizePath2 = localMedia.getCompressPath();
                        File file= new File(prizePath2);
                        fileList1.add(file);
                    }
                    if(fileList1 != null && fileList1.size()>0){
                        presenter.uploadFiles(LaohuPinTuanNextTwoActivity.this,fileList1,"奖项二");
                    }
                    break;
                case PictureConfig.CHOOSE_REQUEST3:
                    // 图片选择结果回调
                    List<LocalMedia> selectList3 = PictureSelector.obtainMultipleResult(data);
                    if(selectList3 != null && selectList3.size()>0){
                        localMedia = selectList3.get(0);
                        prizePath3 = localMedia.getCompressPath();
                        File file= new File(prizePath3);
                        fileList1.add(file);
                    }
//                    EventBus.getDefault().post(new LhGeEventBus(posterId1,"奖项一"));
//                    pagerAdapter.updateData(prizeBeanList);
                    if(fileList1 != null && fileList1.size()>0){
                        presenter.uploadFiles(LaohuPinTuanNextTwoActivity.this,fileList1,"奖项三");
                    }
                    break;
                case PictureConfig.CHOOSE_REQUEST4:
                    // 图片选择结果回调
                    List<LocalMedia> selectList4 = PictureSelector.obtainMultipleResult(data);
                    if(selectList4 != null && selectList4.size()>0){
                        localMedia = selectList4.get(0);
                        prizePath4 = localMedia.getCompressPath();
                        File file= new File(prizePath4);
                        fileList1.add(file);
                    }
                    if(fileList1 != null && fileList1.size()>0){
                        presenter.uploadFiles(LaohuPinTuanNextTwoActivity.this,fileList1,"奖项四");
                    }
                    break;
                case PictureConfig.CHOOSE_REQUEST5:
                    // 图片选择结果回调
                    List<LocalMedia> selectList5 = PictureSelector.obtainMultipleResult(data);
                    if(selectList5 != null && selectList5.size()>0){
                        localMedia = selectList5.get(0);
                        prizePath5 = localMedia.getCompressPath();
                        File file= new File(prizePath5);
                        fileList1.add(file);
                    }
                    if(fileList1 != null && fileList1.size()>0){
                        presenter.uploadFiles(LaohuPinTuanNextTwoActivity.this,fileList1,"奖项五");
                    }
                    break;
                case PictureConfig.CHOOSE_REQUEST6://奖项 六
                    // 图片选择结果回调
                    List<LocalMedia> selectList6 = PictureSelector.obtainMultipleResult(data);
                    if(selectList6 != null && selectList6.size()>0){
                        localMedia = selectList6.get(0);
                        prizePath6 = localMedia.getCompressPath();
                        File file= new File(prizePath6);
                        fileList1.add(file);
                    }
                    if(fileList1 != null && fileList1.size()>0){
                        presenter.uploadFiles(LaohuPinTuanNextTwoActivity.this,fileList1,"奖项六");
                    }
                    break;
                case PictureConfig.CHOOSE_REQUEST7: //安慰奖

                    // 图片选择结果回调
                    List<LocalMedia> selectList7 = PictureSelector.obtainMultipleResult(data);
                    if(selectList7 != null && selectList7.size()>0){
                        localMedia = selectList7.get(0);
                        prizePath7 = localMedia.getCompressPath();
                        File file= new File(prizePath7);
                        fileList1.add(file);
                    }
                    if(fileList1 != null && fileList1.size()>0){
                        presenter.uploadFiles(LaohuPinTuanNextTwoActivity.this,fileList1,"安慰奖");
                    }
                    break;
                case PictureConfig.CHOOSE_REQUEST8: //未中奖
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    if(selectList != null && selectList.size()>0){
                        for(LocalMedia localMedia1 : selectList){

                            String unPrizePath = localMedia1.getPath();
                            if(!unPrizePath.contains("get.do?id=")){
                                if(unPrizePath != null && !"".equals(unPrizePath)){
                                    File file = new File(unPrizePath);
                                    fileList.add(file);
                                }
                            }
                        }

                    }


                    pagerAdapter.updateData(prizeBeanList);
                    break;

            }
        }
    }


    //定义处理接收的方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(LhPtEventBus eventBus){
        //安慰奖
//        selectList = eventBus.getSelectList();
//        pagerAdapter.updateData(prizeBeanList);
//        ToastUtils.showShort(eventBus.toString());


    }


    protected  void copyAndCreate(){
        //复制并创建
        isCopyCreate = "NO";
        isEditCampaign = "YES";
        LaohuPinTuanNewActivity.compaignStatus = "NEW";

        campaignNonrewardPicList = new ArrayList<>();
        if(selectList != null && !"".equals(selectList)){
            for (LocalMedia localMedia:selectList){
                CampaignNonrewardPic campaignNonrewardPic = new CampaignNonrewardPic();
                String unPrizePath = localMedia.getCompressPath();

                if(unPrizePath!= null && !"".equals(unPrizePath)){

                    if(unPrizePath.contains("get.do?id=")){
                        String picPictureId = unPrizePath.substring(unPrizePath.indexOf("get.do?id=")+10,unPrizePath.length());
                        campaignNonrewardPic.setPictureId(picPictureId);
                        campaignNonrewardPicList.add(campaignNonrewardPic);
                    }
                }
            }
        }
        if(campaignNonrewardPicList != null && campaignNonrewardPicList.size()>0){
            removeCampaignNonrewardPicList(campaignNonrewardPicList);

        }
        removeCampaignGamesetrewardList(campaignGamesetrewardList);

        if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0){
            for (CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList){
                if (campaignGamesetreward != null ){
                    String name = campaignGamesetreward.getPrizeName();
                    String picPictureId = null;
                    if("奖项一".equals(name)){
                        if(prizePath1.contains("get.do?id")){
                            picPictureId = prizePath1.substring(prizePath1.indexOf("get.do?id=")+10,prizePath1.length());
                        }
                        campaignGamesetreward.setRewardType("1");
                        campaignGamesetreward.setPrizeImageUrl(picPictureId);
                        campaignGamesetreward.setList(campaignCouponList1);
                    }else if("奖项二".equals(name)){
                        if(prizePath2.contains("get.do?id")){
                            picPictureId = prizePath2.substring(prizePath2.indexOf("get.do?id=")+10,prizePath2.length());
                        }
                        campaignGamesetreward.setRewardType("1");
                        campaignGamesetreward.setPrizeImageUrl(picPictureId);
                        campaignGamesetreward.setList(campaignCouponList2);
                    }else if("奖项三".equals(name)){
                        if(prizePath3.contains("get.do?id")){
                            picPictureId = prizePath3.substring(prizePath3.indexOf("get.do?id=")+10,prizePath3.length());
                        }
                        campaignGamesetreward.setRewardType("1");
                        campaignGamesetreward.setPrizeImageUrl(picPictureId);
                        campaignGamesetreward.setList(campaignCouponList3);
                    }else if("奖项四".equals(name)){
                        if(prizePath4.contains("get.do?id")){
                            picPictureId = prizePath4.substring(prizePath4.indexOf("get.do?id=")+10,prizePath4.length());
                        }
                        campaignGamesetreward.setRewardType("1");
                        campaignGamesetreward.setPrizeImageUrl(picPictureId);
                        campaignGamesetreward.setList(campaignCouponList4);
                    }else if("奖项五".equals(name)){
                        if(prizePath5.contains("get.do?id")){
                            picPictureId = prizePath5.substring(prizePath5.indexOf("get.do?id=")+10,prizePath5.length());
                        }
                        campaignGamesetreward.setRewardType("1");
                        campaignGamesetreward.setPrizeImageUrl(picPictureId);
                        campaignGamesetreward.setList(campaignCouponList5);
                    }else if("奖项六".equals(name)){
                        if(prizePath6.contains("get.do?id")){
                            picPictureId = prizePath6.substring(prizePath6.indexOf("get.do?id=")+10,prizePath6.length());
                        }
                        campaignGamesetreward.setRewardType("1");
                        campaignGamesetreward.setPrizeImageUrl(picPictureId);
                        campaignGamesetreward.setList(campaignCouponList6);
                    }else if("安慰奖".equals(name)){
                        if(prizePath7.contains("get.do?id")){
                            picPictureId = prizePath7.substring(prizePath6.indexOf("get.do?id=")+10,prizePath7.length());
                        }
                        campaignGamesetreward.setRewardType("0");
                        campaignGamesetreward.setPrizeImageUrl(picPictureId);
                        campaignGamesetreward.setList(campaignCouponList7);
                        campaignNonrewardPicList = new ArrayList<>();
                    }
                }

            }
        }

        LaohuPinTuanNewActivity.compaignStatus = "NEW";

        if(LaohuPinTuanNewActivity.campaign != null){
            LaohuPinTuanNewActivity.campaign.setCampaignId(null);
            LaohuPinTuanNewActivity.campaign.setCampaignName("");
        }

        Intent intent1 = new Intent(LaohuPinTuanNextTwoActivity.this,LaohuPinTuanNewActivity.class);

        intent1.putExtra("title",title);
        intent1.putExtra("isCopyCreate",isCopyCreate);
        intent1.putExtra("isEditCampaign",isEditCampaign);
        intent1.putExtra("compaignStatus",LaohuPinTuanNewActivity.compaignStatus);
        intent1.putExtra("campaignType",LaohuPinTuanNewActivity.campaignType);
        ActivityUtils.startActivity(intent1,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }


    protected   void setIntentUpActivity(){
        campaignNonrewardPicList = new ArrayList<>();
        if(selectList != null && !"".equals(selectList)){
            for (LocalMedia localMedia:selectList){
                CampaignNonrewardPic campaignNonrewardPic = new CampaignNonrewardPic();
                String unPrizePath = localMedia.getCompressPath();

                if(unPrizePath!= null && !"".equals(unPrizePath)){

                    if(unPrizePath.contains("get.do?id=")){
                        String picPictureId = unPrizePath.substring(unPrizePath.indexOf("get.do?id=")+10,unPrizePath.length());
                        campaignNonrewardPic.setPictureId(picPictureId);
                        campaignNonrewardPicList.add(campaignNonrewardPic);
                    }
                }
            }
        }
        if(campaignNonrewardPicList != null && campaignNonrewardPicList.size()>0){
            removeCampaignNonrewardPicList(campaignNonrewardPicList);

        }
        //删除重复数据
        removeCampaignGamesetrewardList(campaignGamesetrewardList);
        if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0){
            for(CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList){
                String name = campaignGamesetreward.getPrizeName();
                String picPictureId = null;

                if("奖项一".equals(name)){
                    if(prizePath1!= null && !"".equals(prizePath1) && prizePath1.contains("get.do?id")){
                        picPictureId = prizePath1.substring(prizePath1.indexOf("get.do?id=")+10,prizePath1.length());
                    }
                    campaignGamesetreward.setRewardType("1");
                    campaignGamesetreward.setPrizeOrder(1);
                    campaignGamesetreward.setPrizeImageUrl(picPictureId);
                    campaignGamesetreward.setList(campaignCouponList1);
                }else if("奖项二".equals(name)){
                    if(prizePath2!= null && !"".equals(prizePath2) && prizePath2.contains("get.do?id")){
                        picPictureId = prizePath2.substring(prizePath2.indexOf("get.do?id=")+10,prizePath2.length());
                    }
                    campaignGamesetreward.setRewardType("1");
                    campaignGamesetreward.setPrizeOrder(2);
                    campaignGamesetreward.setPrizeImageUrl(picPictureId);
                    campaignGamesetreward.setList(campaignCouponList2);
                }else if("奖项三".equals(name)){
                    if(prizePath3!= null && !"".equals(prizePath3) && prizePath3.contains("get.do?id")){
                        picPictureId = prizePath3.substring(prizePath3.indexOf("get.do?id=")+10,prizePath3.length());
                    }
                    campaignGamesetreward.setRewardType("1");
                    campaignGamesetreward.setPrizeOrder(3);
                    campaignGamesetreward.setPrizeImageUrl(picPictureId);
                    campaignGamesetreward.setList(campaignCouponList3);
                }else if("奖项四".equals(name)){
                    if(prizePath4!= null && !"".equals(prizePath4) && prizePath4.contains("get.do?id")){
                        picPictureId = prizePath4.substring(prizePath4.indexOf("get.do?id=")+10,prizePath4.length());
                    }
                    campaignGamesetreward.setRewardType("1");
                    campaignGamesetreward.setPrizeOrder(4);
                    campaignGamesetreward.setPrizeImageUrl(picPictureId);
                    campaignGamesetreward.setList(campaignCouponList4);
                }else if("奖项五".equals(name)){
                    if(prizePath5!= null && !"".equals(prizePath5) && prizePath5.contains("get.do?id")){
                        picPictureId = prizePath5.substring(prizePath5.indexOf("get.do?id=")+10,prizePath5.length());
                    }
                    campaignGamesetreward.setRewardType("1");
                    campaignGamesetreward.setPrizeOrder(5);
                    campaignGamesetreward.setPrizeImageUrl(picPictureId);
                    campaignGamesetreward.setList(campaignCouponList5);
                }else if("奖项六".equals(name)){
                    if(prizePath6!= null && !"".equals(prizePath6) && prizePath6.contains("get.do?id")){
                        picPictureId = prizePath6.substring(prizePath6.indexOf("get.do?id=")+10,prizePath6.length());
                    }
                    campaignGamesetreward.setRewardType("1");
                    campaignGamesetreward.setPrizeOrder(6);
                    campaignGamesetreward.setPrizeImageUrl(picPictureId);
                    campaignGamesetreward.setList(campaignCouponList6);
                }else if("安慰奖".equals(name)){
                    if(prizePath7!= null && !"".equals(prizePath7) && prizePath7.contains("get.do?id")){
                        picPictureId = prizePath7.substring(prizePath7.indexOf("get.do?id=")+10,prizePath7.length());
                    }
                    campaignGamesetreward.setRewardType("0");
                    campaignGamesetreward.setPrizeOrder(0);
                    campaignGamesetreward.setPrizeImageUrl(picPictureId);
                    campaignGamesetreward.setList(campaignCouponList7);
                    campaignNonrewardPicList = new ArrayList<>();
                }
            }


        }

        ActivityUtils.finishActivity(LaohuPinTuanNextTwoActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }

    protected   void setIntentActivity(){

        intent = new Intent(LaohuPinTuanNextTwoActivity.this,LaohuPinTuanNextThreeActivity.class);

        campaignNonrewardPicList = new ArrayList<>();
        if(selectList != null && !"".equals(selectList)){
            for (LocalMedia localMedia:selectList){
                CampaignNonrewardPic campaignNonrewardPic = new CampaignNonrewardPic();
                String unPrizePath = localMedia.getCompressPath();

                if(unPrizePath!= null && !"".equals(unPrizePath)){

                    if(unPrizePath.contains("get.do?id=")){
                        String picPictureId = unPrizePath.substring(unPrizePath.indexOf("get.do?id=")+10,unPrizePath.length());
                        campaignNonrewardPic.setPictureId(picPictureId);
                        campaignNonrewardPicList.add(campaignNonrewardPic);
                    }
                }
            }
        }
        if(campaignNonrewardPicList != null && campaignNonrewardPicList.size()>0){
            removeCampaignNonrewardPicList(campaignNonrewardPicList);

        }
        removeCampaignGamesetrewardList(campaignGamesetrewardList);
        if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0){
            for (CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList){
                if (campaignGamesetreward != null ){
                    String name = campaignGamesetreward.getPrizeName();
                    String picPictureId = null;

                    if("奖项一".equals(name)){
                        if(prizePath1!= null && !"".equals(prizePath1) && prizePath1.contains("get.do?id")){
                            picPictureId = prizePath1.substring(prizePath1.indexOf("get.do?id=")+10,prizePath1.length());

                        }
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
                    }else if("奖项二".equals(name)){
                        if(prizePath2!= null && !"".equals(prizePath2) && prizePath2.contains("get.do?id")){
                            picPictureId = prizePath2.substring(prizePath2.indexOf("get.do?id=")+10,prizePath2.length());
                        }
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
                    }else if("奖项三".equals(name)){
                        if(prizePath3!= null && !"".equals(prizePath3) && prizePath3.contains("get.do?id")){
                            picPictureId = prizePath3.substring(prizePath3.indexOf("get.do?id=")+10,prizePath3.length());
                        }
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
                    }else if("奖项四".equals(name)){
                        if(prizePath4!= null && !"".equals(prizePath4) && prizePath4.contains("get.do?id")){
                            picPictureId = prizePath4.substring(prizePath4.indexOf("get.do?id=")+10,prizePath4.length());
                        }
                        campaignGamesetreward.setRewardType("1");
                        campaignGamesetreward.setPrizeOrder(4);
                        campaignGamesetreward.setPrizeImageUrl(picPictureId);
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
                    }else if("奖项五".equals(name)){
                        if(prizePath5!= null && !"".equals(prizePath5) && prizePath5.contains("get.do?id")){
                            picPictureId = prizePath5.substring(prizePath5.indexOf("get.do?id=")+10,prizePath5.length());
                        }
                        campaignGamesetreward.setRewardType("1");
                        campaignGamesetreward.setPrizeOrder(5);
                        campaignGamesetreward.setPrizeImageUrl(picPictureId);
                        if(campaignCouponList5!= null && campaignCouponList5.size()>0) {
                            for (CampaignCoupon campaignCoupon : campaignCouponList5) {
                                String storeid = campaignCoupon.getStoreStr();

                                if(storeid!= null && !"".equals(storeid)){
                                    if (storeid.contains(",")) {
                                        campaignCoupon.setStoreStr(storeid.replace(",", "-"));
                                    }
                                }

                            }
                        }
                        campaignGamesetreward.setList(campaignCouponList5);
                    }else if("奖项六".equals(name)){
                        if(prizePath6!= null && !"".equals(prizePath6) && prizePath6.contains("get.do?id")){
                            picPictureId = prizePath6.substring(prizePath6.indexOf("get.do?id=")+10,prizePath6.length());
                        }
                        campaignGamesetreward.setRewardType("1");
                        campaignGamesetreward.setPrizeOrder(6);
                        campaignGamesetreward.setPrizeImageUrl(picPictureId);
                        if(campaignCouponList6 != null && campaignCouponList6.size()>0) {
                            for (CampaignCoupon campaignCoupon : campaignCouponList6) {
                                String storeid = campaignCoupon.getStoreStr();

                                if(storeid!= null && !"".equals(storeid)){
                                    if (storeid.contains(",")) {
                                        campaignCoupon.setStoreStr(storeid.replace(",", "-"));
                                    }
                                }

                            }
                        }
                        campaignGamesetreward.setList(campaignCouponList6);
                    }else if("安慰奖".equals(name)){
                        if(prizePath7!= null && !"".equals(prizePath7) && prizePath7.contains("get.do?id")){
                            picPictureId = prizePath7.substring(prizePath7.indexOf("get.do?id=")+10,prizePath7.length());
                        }
                        campaignGamesetreward.setRewardType("0");
                        campaignGamesetreward.setPrizeOrder(0);
                        campaignGamesetreward.setPrizeCount(0);
                        campaignGamesetreward.setPrizeImageUrl(picPictureId);
                        if(campaignCouponList7 != null && campaignCouponList7.size()>0) {
                            for (CampaignCoupon campaignCoupon : campaignCouponList7) {
                                String storeid = campaignCoupon.getStoreStr();

                                if(storeid!= null && !"".equals(storeid)){
                                    if (storeid.contains(",")) {
                                        campaignCoupon.setStoreStr(storeid.replace(",", "-"));
                                    }
                                }

                            }
                        }
                        campaignGamesetreward.setList(campaignCouponList7);
                        campaignNonrewardPicList = new ArrayList<>();
                    }
                }

            }
        }
        intent.putExtra("title",title);
        intent.putExtra("isCopyCreate",isCopyCreate);
        intent.putExtra("isEditCampaign",isEditCampaign);

        ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);

        //默认置空，从第二步跳转 默认选中优惠券集合为空
        CampaignCouponListFragment.selectCoupons = new ArrayList<>();
    }







}
