package com.zwx.scan.app.feature.ptmanage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luck.picture.lib.entity.LocalMedia;
import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.tablayout.CommonTabLayout;
import com.zwx.library.tablayout.SlidingTabLayout;
import com.zwx.library.tablayout.listener.CustomTabEntity;
import com.zwx.library.tablayout.listener.OnTabSelectListener;
import com.zwx.library.tablayout.listener.TabEntity;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.LogUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CampaignGamesetreward;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.CouponMaterial;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.http.ApiConstants;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.data.http.dialog.HttpUiTips;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.campaign.CampaignCouponListActivity;
import com.zwx.scan.app.feature.campaign.CampaignCouponListFragment;
import com.zwx.scan.app.feature.campaign.CampaignListActivity;
import com.zwx.scan.app.feature.campaign.CampaignNewNextThreeActivity;
import com.zwx.scan.app.feature.campaign.CampaignNewNextTwoActivity;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanNewActivity;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanNextConsolePrizeFragment;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanNextThreeActivity;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanNextTwoActivity;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanNextTwoFragment;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanNextUnPrizeFragment;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanPrizeListActivity;
import com.zwx.scan.app.feature.campaign.LhPtEventBus;
import com.zwx.scan.app.feature.campaign.PrizeBean;
import com.zwx.scan.app.utils.ButtonUtils;
import com.zwx.scan.app.utils.SPObjUtil;
import com.zwx.scan.app.widget.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PtNextTwoActivity extends BaseActivity<PtContract.Presenter> implements PtContract.View,View.OnClickListener {
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

//    @BindView(R.id.fab)
//    protected ImageButton fab;

    @BindView(R.id.tab_layout)
    protected CommonTabLayout tabLayout;
    @BindView(R.id.mViewPager)
    protected NoScrollViewPager viewPager;
    protected String[] titles = {"商品设置","成团奖励"};
    protected  static Campaign campaign = new Campaign();
    protected static List<File> fileList = new ArrayList<>();
    protected  ArrayList<Coupon> couponList = new ArrayList<>();

    protected   String isEditCampaign = "NO";

    protected String isCopyCreate ;

    protected Intent intent = null;

    protected String title;

    private List<String> titleList = new ArrayList<>();
    private String name;
    public FPagerAdapter pagerAdapter = null;
    public static ArrayList<CampaignCoupon> campaignCouponList1 =new ArrayList<>();
    public static ArrayList<CampaignCoupon> campaignCouponList2 =new ArrayList<>();

    protected static ArrayList<Coupon> couponList1 = new ArrayList<>();
    protected static ArrayList<Coupon> couponList2 = new ArrayList<>();

    protected ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    protected int[] mIconUnselectIds = {
            R.drawable.shape_text_normal, R.drawable.shape_text_normal};
    protected int[] mIconSelectIds = {
            R.drawable.shape_btn_blue_pressed,R.drawable.shape_btn_blue_pressed};

    protected  static ArrayList<CampaignGamesetreward> campaignGamesetrewardList = new ArrayList<>();
    protected   ArrayList<CampaignCoupon> rewardCouponList = new ArrayList<>();
    protected   String storeIdCouponType ;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pt_next_two;
    }

    @Override
    protected void initView() {
        DaggerPtComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .ptModule(new PtModule(this))
                .build()
                .inject(this);

        tvTitle.setText("拼团设置");

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

        setSetTep();
        setLisenter();
        initCampaignGamesetrewardList();
        initTabColumn();
    }



    @Override
    protected void initData() {

    }

    private void setSetTep(){

        ivOne.setBackgroundResource(R.drawable.ic_first_clicked);
        ivEllipsisOne.setBackgroundResource(R.drawable.ic_ellipsis_blue);
        ivTwo.setBackgroundResource(R.drawable.ic_two_clicked);
        ivEllipsisTwo.setBackgroundResource(R.drawable.ic_ellipsis_gray);
        ivThree.setBackgroundResource(R.drawable.ic_three_untclick);

    }
/*    private void setTabLayoutListener(){
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

                String name = titleList.get(position);

                if(name != null && !"".equals(name)){
                    name = titleList.get(position);
                    viewPager.setCurrentItem(position);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }*/
    private void initTabColumn(){

        pagerAdapter = new FPagerAdapter(getSupportFragmentManager(),titles);
        viewPager.setAdapter(pagerAdapter);
//        tabLayout.setViewPager(viewPager);
//        viewPager.setCurrentItem(0);
        for (int i = 0; i < titles.length; i++) {
            mTabEntities.add(new TabEntity(titles[i], mIconUnselectIds[i], mIconSelectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);

        viewPager.setCurrentItem(0);
    }



    private void initCampaignGamesetrewardList(){
        //默认去缓存若没有 变量
        couponList1.clear();
        couponList2.clear();
        if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0){   //编辑
            for(CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList){
                if(campaignGamesetreward != null){
//                    String rewardType = campaignGamesetreward.getRewardType();
//                    String pictureUrl = campaignGamesetreward.getPrizeImageUrl();
//                    Integer prizeOrder = campaignGamesetreward.getPrizeOrder();
                    if("商品设置".equals(campaignGamesetreward.getPrizeName())){
                        campaignCouponList1 = campaignGamesetreward.getList();
                    }else if("成团奖励".equals(campaignGamesetreward.getPrizeName())){
                        campaignCouponList2 = campaignGamesetreward.getList();
                    }
                    initCampaignRewardList(campaignGamesetreward);

                }
            }
        }else {    //新建

            campaignGamesetrewardList = new ArrayList<>();
            CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();
            campaignGamesetreward.setPrizeName("商品设置");
            campaignGamesetreward.setRewardType("1");
            campaignGamesetreward.setList(new ArrayList<CampaignCoupon>());
            campaignGamesetrewardList.add(campaignGamesetreward);

            campaignGamesetreward = new CampaignGamesetreward();
            campaignGamesetreward.setPrizeName("成团奖励");
            campaignGamesetreward.setRewardType("0");
            campaignGamesetreward.setList(new ArrayList<CampaignCoupon>());
            campaignGamesetrewardList.add(campaignGamesetreward);

        }

    }

    private void initCampaignRewardList(CampaignGamesetreward campaignGamesetreward) {
        Integer count = campaignGamesetreward.getPrizeCount();
        String prizeName = campaignGamesetreward.getPrizeName();

        rewardCouponList  = campaignGamesetreward.getList();
        //本地缓存 提取
        if (rewardCouponList != null && rewardCouponList.size() > 0) {
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
                                String storeId = store.getStoreId();
                                String storeName2 = store.getStoreName() != null ? "#" + store.getStoreName() + "    " : "";
                                storeIdSb.append(storeId).append("-");
                                storeNameSb.append(storeName2);

                            }
                            storeName = storeNameSb.toString();

                            String storeIdA = storeIdSb.toString();
                            if (storeIdA !=null && !"".equals(storeIdA)) {
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
                if ("商品设置".equals(prizeName)&& prizeName == "商品设置") {
                    couponList1.add(coupon);
                } else if ("成团奖励".equals(prizeName)&& prizeName == "成团奖励") {
                    couponList2.add(coupon);
                }
            }
        }

        LogUtils.e("------------------------------1"+couponList1.toString()+"------------------------------2"+couponList2.toString());
    }
    


    private void setLisenter(){
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);

                String  name = titles[position];

                if(name != null) {
                    EventBus.getDefault().post(new LhPtEventBus(name));
                    pagerAdapter.updateData(titles);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                tabLayout.setCurrentTab(position);

               /* String  name = titles[position];
                if(name != null) {
                    EventBus.getDefault().post(new LhPtEventBus(name));
                    pagerAdapter.updateData(titles);
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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


    @OnClick({R.id.iv_back,R.id.btn_pre,R.id.btn_next,R.id.tv_right})
    @Override
    public void onClick(View v) {

        int curTab = 0;
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.startActivity(PtManageActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
                break;


            case R.id.tv_right:
                ToastUtils.showCustomShortBottom("创建成功");
                //复制并创建
                isCopyCreate = "NO";
                isEditCampaign = "YES";
                PtNewActivity.compaignStatus = "NEW";
//                PtNewActivity.campaign.setPosterTemplete("");
//                PtNewActivity.campaign.setShareTitle("");
//                PtNewActivity.campaign.setShareDesc("");

                if(PtNewActivity.campaign != null){
                    PtNewActivity.campaign.setCampaignName("");
                    PtNewActivity.campaign.setCampaignId(null);
                }
                setSaveCampaignRewardList();
                Intent intent1 = new Intent(PtNextTwoActivity.this,PtNewActivity.class);

                intent1.putExtra("title",title);
                intent1.putExtra("isCopyCreate",isCopyCreate);
                intent1.putExtra("isEditCampaign",isEditCampaign);
                intent1.putExtra("campaignType",PtNewActivity.campaignType);
                intent1.putExtra("compaignStatus",PtNewActivity.compaignStatus);
                ActivityUtils.startActivity(intent1,
                        R.anim.slide_in_left, R.anim.slide_out_right);

                break;

            case R.id.btn_pre:
                setSaveCampaignRewardList();
                ActivityUtils.finishActivity(PtNextTwoActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case R.id.btn_next:

                setIntentActivity();
                break;

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.startActivity(PtManageActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
        finish();

    }

    protected   void setIntentActivity(){

        intent = new Intent(PtNextTwoActivity.this,PtNextThreeActivity.class);
        removeCampaignGamesetrewardList();

        if(campaignCouponList1 == null ||campaignCouponList1.size() == 0){
            setDialog("您在第二步中，设置信息不完善，请补充信息。");
            return;
        }

        if(campaignCouponList2 == null ||campaignCouponList2.size() == 0){
            setDialog("您在第二步中，设置信息不完善，请补充信息。");
            return;
        }
        if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0){
            for (CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList){
                if (campaignGamesetreward != null ){
                    String name = campaignGamesetreward.getPrizeName();
                    if("商品设置".equals(name)){
                        if(campaignCouponList1 != null && campaignCouponList1.size()>0) {
                            for (CampaignCoupon campaignCoupon : campaignCouponList1) {
                                String storeIdCouponType = campaignCoupon.getStoreSelectType();
                                if(storeIdCouponType != null && !"".equals(storeIdCouponType)){

                                }else {
                                    String storeid = campaignCoupon.getStoreStr();
                                    if(storeid!= null && !"".equals(storeid)){
                                        if (storeid.contains(",")) {
                                            campaignCoupon.setStoreStr(storeid.replace(",", "-"));
                                        }
                                    }
                                }

                            }
                        }else {
                            setDialog("您在第二步中，设置信息不完善，请补充信息。");
                            return;
                        }
                        campaignGamesetreward.setList(campaignCouponList1);
                    }else if("成团奖励".equals(name)){
                        if(campaignCouponList2 != null && campaignCouponList2.size()>0){
                            for (CampaignCoupon campaignCoupon : campaignCouponList2){
                                String storeIdCouponType = campaignCoupon.getStoreSelectType();
                                if(storeIdCouponType != null && !"".equals(storeIdCouponType)){

                                }else {
                                    String storeid = campaignCoupon.getStoreStr();
                                    if(storeid!= null && !"".equals(storeid)){
                                        if (storeid.contains(",")) {
                                            campaignCoupon.setStoreStr(storeid.replace(",", "-"));
                                        }
                                    }
                                }
                            }
                        }else {
                            setDialog("您在第二步中，设置信息不完善，请补充信息。");
                            return;
                        }

                        campaignGamesetreward.setList(campaignCouponList2);
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

    protected void removeCampaignCouponList(ArrayList<CampaignCoupon> campaignCouponList){
        if(campaignCouponList != null && campaignCouponList.size()>0){
            for(int i=0;i<campaignCouponList.size();i++){
                for(int j=campaignCouponList.size()-1;j>i;j--){
                    if(campaignCouponList.get(j).getCouponName().equals(campaignCouponList.get(i).getCouponName())){
                        campaignCouponList.remove(j);
                    }
                }
            }
        }

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


    private void setSaveCampaignRewardList(){
        removeCampaignGamesetrewardList();
        if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0){

            for(CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList){
                String prizeName = campaignGamesetreward.getPrizeName();
                if("商品设置".equals(prizeName)){
                    campaignGamesetreward.setList(PtNextTwoActivity.campaignCouponList1);
                }else if("成团奖励".equals(prizeName)){
                    campaignGamesetreward.setList(PtNextTwoActivity.campaignCouponList2);
                }

            }
        }

    }

    public   class FPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragmentList;
        private FragmentManager mFragmentManager;
        private String[] titles = null;
        List<String> tags = new ArrayList<>();
        public FPagerAdapter(FragmentManager fm, String[] titles) {
            super(fm);
            this.mFragmentManager = fm;
            mFragmentList = new ArrayList<>();
            this.titles = titles;
            this.tags = new ArrayList<>();

            if(titles != null && titles.length>0){
                for (int i = 0;i<titles.length;i++){
                    if("商品设置".equals(titles[i])){
                        mFragmentList.add(PtNextTwoFragment.getInstance(titles[i],couponList1));
                    }else if("成团奖励".equals(titles[i])){
                        mFragmentList.add(PtNextTwoFragment.getInstance(titles[i],couponList2));
                    }
                }
            }
            setFragments(mFragmentList);
        }

        public void updateData(String[] titles) {
            ArrayList<Fragment> fragments = new ArrayList<>();


            if(titles != null && titles.length>0){
                for (int i = 0;i<titles.length;i++){
                    if("商品设置".equals(titles[i])){
                        fragments.add(PtNextTwoFragment.getInstance(titles[i],couponList1));
                    }else if("成团奖励".equals(titles[i])){
                        fragments.add(PtNextTwoFragment.getInstance(titles[i],couponList2));
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
            return mFragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 6666) {
            String ptName = data.getStringExtra("ptName");
            CampaignCouponListFragment.selectCoupons.clear();
            if(requestCode == 6661){
                if("goodsSetting".equals(ptName)){
                    ArrayList<Coupon> couponList  = (ArrayList<Coupon>)data.getSerializableExtra("selectCouponList");
                    if(couponList != null && couponList.size()>0) {
                        couponList1.addAll(couponList);
                        for (Coupon coupon : couponList) {
                            CampaignCoupon campaignCoupon = new CampaignCoupon();
                            campaignCoupon.setCouponId(coupon.getId());
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
                            if ("商品设置".equals(prizeName)) {
                                campaignGamesetreward.setList(campaignCouponList1);
                            }
                        }
                    }
                }

                pagerAdapter.updateData(titles);
            }else if(requestCode == 6662){
                if("rewardSetting".equals(ptName)){
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
                            campaignCouponList2.add(campaignCoupon);
                        }
                    }

                    if(campaignGamesetrewardList != null && campaignGamesetrewardList.size()>0) {
                        for (CampaignGamesetreward campaignGamesetreward : campaignGamesetrewardList) {
                            String prizeName = campaignGamesetreward.getPrizeName();
                            if ("成团奖励".equals(prizeName)) {
                                campaignGamesetreward.setList(campaignCouponList2);
                            }
                        }
                    }
                }
                pagerAdapter.updateData(titles);
            }
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
}
