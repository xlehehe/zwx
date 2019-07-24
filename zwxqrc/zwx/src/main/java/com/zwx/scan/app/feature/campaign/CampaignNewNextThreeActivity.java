package com.zwx.scan.app.feature.campaign;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.CouponMaterial;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.http.dialog.HttpUiTips;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.utils.SPObjUtil;
import com.zwx.scan.app.widget.NoScrollViewPager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * author : lizhilong
 * time   : 2018/10/22
 * desc   : 转发活动第三步
 * version: 1.0
 **/
public class CampaignNewNextThreeActivity extends BaseActivity<CampaignsContract.Presenter> implements CampaignsContract.View,View.OnClickListener {
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.btn_add_coupon)
    protected Button btnAddCoupon;


    @BindView(R.id.dots)
    protected LinearLayout mDotsLayout;
    @BindView(R.id.ll_add_top)
    protected LinearLayout llAddTop;

    @BindView(R.id.view_pager)
    protected CustomViewPager couponViewPager;
    @BindView(R.id.setting_view_pager)
    protected NoScrollViewPager setViewPager;

    @BindView(R.id.ll_bottom_coupon_edit)
    protected LinearLayout llBottom;
    @BindView(R.id.fab)
    protected ImageButton fab;

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
    @BindView(R.id.iv_ellipsis_three)
    protected ImageView ivEllipsisThree;
    @BindView(R.id.iv_four)
    protected ImageView ivFour;

    private String title;
    protected CouponPagerAdapter pagerAdapter = null;
    protected CouponSettPagerAdapter setPagerAdapter = null;
    protected  ArrayList<Coupon> couponList = new ArrayList<>();

    private List<ImageView> mDotsIV = new ArrayList<>();
    private String isNextTwoAndThree = "NO";

    private String isShowSelectStorePop = "YES";

    protected  static ArrayList<CampaignCoupon> receiveCouponList = new ArrayList<>();
    protected  static ArrayList<CampaignCoupon> campaignCouponList = new ArrayList<>();

    private  CampaignCouponNextSettingFragment campaignCouponNextSettingFragment = new CampaignCouponNextSettingFragment();
//    ArrayList<CampaignCoupon> saveReceiveCouponList = new ArrayList<>();
    private  String isEditCampaign = "NO";

    private String isCopyCreate ;


    private  String storeIdCouponType ;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_campaign_new_next_three;
    }

    @Override
    protected void initView() {
        title = getIntent().getStringExtra("title");

        tvTitle.setText("活动优惠券设置");

        DaggerCampaignsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignsModule(new CampaignsModule(this))
                .build()
                .inject(this);

        setSetTep();
    }

    private void setSetTep(){

        ivOne.setBackgroundResource(R.drawable.ic_first_clicked);
        ivEllipsisOne.setBackgroundResource(R.drawable.ic_ellipsis_blue);
        ivTwo.setBackgroundResource(R.drawable.ic_two_clicked);
        ivEllipsisTwo.setBackgroundResource(R.drawable.ic_ellipsis_blue);
        ivThree.setBackgroundResource(R.drawable.ic_three_clicked);
        ivEllipsisThree.setBackgroundResource(R.drawable.ic_ellipsis_gray);

        ivFour.setBackgroundResource(R.drawable.ic_four_unclick);
    }

    @Override
    protected void initData() {
        SPUtils.getInstance().put("isShowForwardStore","NO");
        setViewPagerAdapter();
        isEditCampaign = getIntent().getStringExtra("isEditCampaign");
        isCopyCreate = getIntent().getStringExtra("isCopyCreate");

        if("YES".equals(isEditCampaign)){  //编辑
            if("YES".equals(isCopyCreate)){  //编辑页面复制并创建
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setText("复制并创建");
            }else {
                tvRight.setVisibility(View.GONE);
            }


        }else {

            /*llAddTop.setVisibility(View.VISIBLE);
            llBottom.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);*/
        }
        initDataCouponList();

        if(CampaignNewActivity.compaignStatus != null && !"".equals(CampaignNewActivity.compaignStatus)){
            if("S".equals(CampaignNewActivity.compaignStatus)|| "NEW".equals(CampaignNewActivity.compaignStatus)){
                if(receiveCouponList !=null && receiveCouponList.size()>0){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
                btnAddCoupon.setVisibility(View.VISIBLE);
            }else {
                fab.setVisibility(View.GONE);
                btnAddCoupon.setVisibility(View.GONE);

            }
        }
    }

    private void initDataCouponList(){

        campaignCouponList = new ArrayList<>();
        couponList = new ArrayList<>();
        if(receiveCouponList!=null && receiveCouponList.size()>0){
            campaignCouponList  = receiveCouponList;
            for (CampaignCoupon campaignCoupon :receiveCouponList){
                List<Store> storeList = campaignCoupon.getStores();
                Coupon coupon = new Coupon();
                if(storeList!=null && storeList.size()>0){
                    Store store1 = storeList.get(0);
                    StringBuilder storeIdSb = new StringBuilder();
                    StringBuilder storeNameSb = new StringBuilder();
                    String brandLogo  = store1.getBrandLogo();

                    if(store1 != null){
                        storeIdCouponType = store1.getStoreSelectType();
                        String storeName = "";
                        String storeIdArr = "";
                        if("A".equals(storeIdCouponType)){
                            storeName = "全部店铺";
                            for (Store store:storeList){
                                store.setChecked(false);

                            }
                            storeIdArr = "";
                        }else if("D".equals(storeIdCouponType)){
                            storeName = "全部自营";
                            for (Store store:storeList){
                                store.setChecked(false);

                            }
                            storeIdArr = "";

                        }else if("J".equals(storeIdCouponType)){
                            storeName = "全部加盟";
                            for (Store store:storeList){
                                store.setChecked(false);
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
                            if(!storeIdA.isEmpty()){
                                storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                            }
                            coupon.setStoreNames(storeName);
                            coupon.setStoreIds(storeIdArr);
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
                coupon.setOther(campaignCoupon.getOther());
                coupon.setLimit(campaignCoupon.getLimit());
                coupon.setNoDate(campaignCoupon.getNoDate());
                coupon.setNoItem(campaignCoupon.getNoItem());
                coupon.setDateCode(campaignCoupon.getDateCode());
                coupon.setStoreSelectType(campaignCoupon.getStoreSelectType());
                coupon.setExpireStartType(campaignCoupon.getExpireStartType());
                coupon.setExpireEndType(campaignCoupon.getExpireEndType());

                coupon.setType(campaignCoupon.getType());
                coupon.setId(campaignCoupon.getCouponId());
                coupon.setTimePeriod(campaignCoupon.getTimePeriod());
                coupon.setExpireStartDate(campaignCoupon.getExpireStartDate());
                coupon.setExpireEndDate(campaignCoupon.getExpireEndDate());
                coupon.setName(campaignCoupon.getCouponName());
                coupon.setExpireStartDay(campaignCoupon.getExpireStartDay()!=null && !"".equals(campaignCoupon.getExpireStartDay())?campaignCoupon.getExpireStartDay():"0");
                coupon.setExpireEndDay(campaignCoupon.getExpireEndDay()!=null && !"".equals(campaignCoupon.getExpireEndDay())?campaignCoupon.getExpireEndDay():"1");
                couponList.add(coupon);
            }

            if(couponList!=null && couponList.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
                setDotLayout(couponList);
                pagerAdapter.updateData(couponList);
                setPagerAdapter.updateData(couponList);
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }

        }else {
            llAddTop.setVisibility(View.VISIBLE);
            llBottom.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);

        }

    }

    @OnClick({R.id.iv_back,R.id.tv_right,R.id.btn_add_coupon,
            R.id.btn_pre,R.id.btn_next,R.id.fab})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.iv_back:
               /* ActivityUtils.finishActivity(CampaignNewNextThreeActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);*/
                SPUtils.getInstance().remove("receiveCouponList");
                SPUtils.getInstance().remove("forwardCouponList");
                ActivityUtils.startActivity(CampaignListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
                break;
            case R.id.btn_add_coupon:
                CampaignCouponListFragment.selectCoupons.clear();
                 intent = new Intent(CampaignNewNextThreeActivity.this,CampaignCouponListActivity.class);
                intent.putExtra("nextTwoSelectCoupon","NO");
                ActivityUtils.startActivityForResult(CampaignNewNextThreeActivity.this,intent,3333,R.anim.slide_in_right,R.anim.slide_out_left);
                break;
            case R.id.btn_pre: //上一步
                if(campaignCouponList != null && campaignCouponList.size()>0){
                    receiveCouponList = campaignCouponList;
                }

                ActivityUtils.finishActivity(CampaignNewNextThreeActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);

                break;

            case R.id.tv_right://复制并创建
                ToastUtils.showCustomShortBottom(getResources().getString(R.string.create_success));
                isCopyCreate = "NO";
                isEditCampaign = "YES";
                CampaignNewActivity.compaignStatus = "NEW";
//                CampaignNewActivity.campaign.setPosterTemplete("");
//                CampaignNewActivity.campaign.setShareTitle("");
//                CampaignNewActivity.campaign.setShareDesc("");
                if(CampaignNewActivity.campaign !=null){
                    CampaignNewActivity.campaign.setCampaignId(null);
                    CampaignNewActivity.campaign.setCampaignName("");
                }

                if(campaignCouponList != null && campaignCouponList.size()>0){
                    receiveCouponList = campaignCouponList;
                }
                Intent intent1 = new Intent(CampaignNewNextThreeActivity.this,CampaignNewActivity.class);
                intent1.putExtra("isCopyCreate",isCopyCreate);
                intent1.putExtra("isEditCampaign",isEditCampaign);
                intent1.putExtra("title",title);
                intent1.putExtra("campaignType","zf");
                intent1.putExtra("compaignStatus",CampaignNewActivity.compaignStatus);
                ActivityUtils.startActivity(intent1,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.btn_next: //下一步
                 intent = new Intent(CampaignNewNextThreeActivity.this,PosterListActivity.class);

                if(campaignCouponList != null && campaignCouponList.size()>0){

                    receiveCouponList = campaignCouponList;

                    for (CampaignCoupon campaignCoupon : receiveCouponList) {
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
                }
                intent.putExtra("title",title);
                intent.putExtra("isCopyCreate",isCopyCreate);
                intent.putExtra("isEditCampaign",isEditCampaign);

                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);

                break;
            case R.id.fab:
                intent = new Intent(CampaignNewNextThreeActivity.this,CampaignCouponListActivity.class);
                intent.putExtra("nextTwoSelectCoupon","NO");
                ActivityUtils.startActivityForResult(CampaignNewNextThreeActivity.this,intent,3333,R.anim.slide_in_right,R.anim.slide_out_left);
                break;




        }
    }

    private void remove(ArrayList<CampaignCoupon> campaignCouponList){
        for(int i=0;i<campaignCouponList.size();i++){
            for(int j=campaignCouponList.size()-1;j>i;j--){
                if((campaignCouponList.get(i).getCouponId().equals(campaignCouponList.get(j).getCouponId()))){
                    String startDate =campaignCouponList.get(i).getExpireStartDate();
                    String endDate =campaignCouponList.get(i).getExpireEndDate();
                    String startDay = campaignCouponList.get(i).getExpireStartDay();
                    String endDay =campaignCouponList.get(i).getExpireEndDay();
                    if("A".equals(campaignCouponNextSettingFragment.expireEndType)){


                    }else if("R1".equals(campaignCouponNextSettingFragment.expireEndType)){

                    }
                    campaignCouponList.remove(j);
                }
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
     /*   ActivityUtils.finishActivity(CampaignNewNextThreeActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);*/
        SPUtils.getInstance().remove("receiveCouponList");
        SPUtils.getInstance().remove("forwardCouponList");
        ActivityUtils.startActivity(CampaignListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
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
                    Fragment fragment = CampaignCouponsFragment.getInstance(coupon,i,isNextTwoAndThree);
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
                    Fragment fragment = CampaignCouponsFragment.getInstance(coupon,i,isNextTwoAndThree);
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


    protected void setViewPagerAdapter(){
        pagerAdapter  = new CouponPagerAdapter(getSupportFragmentManager(),couponList);
        couponViewPager.setAdapter(pagerAdapter);
        couponViewPager.setCurrentItem(0);
        /*carouselViewPager.setOffscreenPageLimit(couponList.size());
        carouselViewPager.setPageTransformer(true,new RotationPageTransformer());
        carouselViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.margin_20));*/

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
        setPagerAdapter = new CouponSettPagerAdapter(getSupportFragmentManager(),couponList);
        setViewPager.setAdapter(setPagerAdapter);
        setViewPager.setCurrentItem(0);

        //活动优惠券底部设置
        setViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                /*if(couponList != null && couponList.size()>0){
                    Coupon coupon = couponList.get(position);
                    if(campaignCouponList!=null && campaignCouponList.size()>0){
                        for(int i=0;i<campaignCouponList.size();i++){
                            CampaignCoupon campaignCoupon = campaignCouponList.get(i);

                            if(position == i){
                                campaignCoupon.setCouponId(coupon.getId());
                                campaignCoupon.setCouponName(coupon.getName());
                                campaignCoupon.setStoreSelectType(campaignCouponNextSettingFragment.couponStoreSelectType);
                                campaignCoupon.setStoreStr(campaignCouponNextSettingFragment.storeId);

                                if("A".equals(campaignCouponNextSettingFragment.expireEndType)){
                                    String startDate = campaignCouponNextSettingFragment.startDate;
                                    String endDate = campaignCouponNextSettingFragment.endDate;

                                    campaignCoupon.setExpireStartDate(startDate!=null?startDate:"");
                                    campaignCoupon.setExpireEndDate(endDate!=null?endDate:"");
                                }else if("R1".equals(campaignCouponNextSettingFragment.expireEndType)){
                                    String startDay= campaignCouponNextSettingFragment.startDay;
                                    String endDay = campaignCouponNextSettingFragment.endDay;
                                    campaignCoupon.setExpireStartDay(startDay != null?startDay:"");
                                    campaignCoupon.setExpireEndDay(endDay != null?endDay:"");

                                }
                            }

                        }
                    }


                }*/
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
    public   class CouponSettPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragmentList;
        private FragmentManager mFragmentManager;
        private Bundle bundle  = new Bundle();
        public CouponSettPagerAdapter(FragmentManager fm, List<Coupon> couponList) {
            super(fm);
            this.mFragmentManager = fm;
            mFragmentList = new ArrayList<>();
            if(couponList !=null && couponList.size()>0){
                for (int i = 0;i<couponList.size();i++){
                    Coupon coupon = couponList.get(i);
                    Fragment fragment = CampaignCouponNextSettingFragment.getInstance(coupon,i,isNextTwoAndThree,setViewPager);
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
                    Fragment fragment = CampaignCouponNextSettingFragment.getInstance(coupon,i,isNextTwoAndThree,setViewPager);
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

    protected void removeSelectCouponList(ArrayList<Coupon> couponList){
        if(couponList != null && couponList.size()>0){
            for(int i=0;i<couponList.size();i++){
                for(int j=couponList.size()-1;j>i;j--){
                    if(String.valueOf(couponList.get(i).getId()).equals(couponList.get(j).getId())){
                        couponList.remove(j);
                    }
                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 3333){
            if(requestCode == 3333){
                CampaignCouponListFragment.selectCoupons.clear();
                ArrayList<Coupon> couponList1 = (ArrayList<Coupon>)data.getSerializableExtra("selectCouponList");
                //2019-05-29 注释 允许添加重复优惠券
//                removeSelectCouponList(couponList1);
                if(couponList1!=null && couponList1.size()>0 ){
                    llAddTop.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.VISIBLE);
                    couponList.addAll(couponList1);
                    setDotLayout(couponList);
                    pagerAdapter.updateData(couponList);
                    setPagerAdapter.updateData(couponList);
                }else {
                    llAddTop.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                }

                if(couponList1 != null && couponList1.size()>0) {
                    for (Coupon coupon : couponList1) {
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
                        campaignCouponList.add(campaignCoupon);
                    }
                }
            }
        }else {
            if(couponList != null&& couponList.size()>0){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
                setDotLayout(couponList);
                pagerAdapter.updateData(couponList);
                setPagerAdapter.updateData(couponList);
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }

        }
    }

    protected void setDotLayout(ArrayList<Coupon> dataList){
        //先删除
        mDotsIV.clear();
        mDotsLayout.removeAllViews();
        for (int i = 0; i < dataList.size(); i++) {
            ImageView dotIV = new ImageView(getApplicationContext());
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

}
