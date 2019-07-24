package com.zwx.scan.app.feature.ptmanage;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.pickerview.picker.view.TimePickerView;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CampaignGamesetreward;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.http.ApiConstants;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.campaign.LhPtEventBus;
import com.zwx.scan.app.widget.NoScrollViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PtRewadSettingFragment extends BaseFragment implements View.OnClickListener,PtCouponFragment.ChanagePtTwoLayoutLisenter {



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


    @BindView(R.id.tv_prize_num)
    protected TextView tv_prize_num;

    @BindView(R.id.edt_goods_num)
    protected EditText edt_goods_num;


    @BindView(R.id.iv_lh_ge)
    protected ImageView iv_lh_ge;
    //奖项名称
    private String name;

    protected CouponPagerAdapter pagerAdapter = null;
    protected CouponSettPagerAdapter setPagerAdapter = null;
    protected ArrayList<Coupon> couponList = new ArrayList<>();
    protected ArrayList<Coupon> couponList1 = new ArrayList<>();
    protected ArrayList<Coupon> couponList2 = new ArrayList<>();

    protected ArrayList<CampaignCoupon> ptCouponList = new ArrayList<>();

    private List<ImageView> mDotsIV = new ArrayList<>();


    private String isNextTwoAndThree = "YES";




    protected   String isEditCampaign = "NO";

    protected String isCopyCreate ;


    protected   String storeIdCouponType ;
    private PtNextSettingFragment nextSettingFragment = new PtNextSettingFragment();

    private String prizePath  ;



    private String prizeCount; //奖项次数
    private String imageId;

    private PtNextTwoActivity activity =null;



    protected  String storeId;
    private String compId;
    //选择多个店铺的品牌位置
    private int brandPostion;
    //品牌logo
    private String brandLogo;

    private String storeIdArray ;
    private String storeNameArray ;
    private boolean isStartDate;

    protected TimePickerView pvCustomTime;

    private int getValidDay  = 0 ;
    private int couponDateDay  = 1 ;

    public PtRewadSettingFragment() {
        // Required empty public constructor
    }

    public static PtRewadSettingFragment getInstance(String name){
        PtRewadSettingFragment nextTwoFragment = new PtRewadSettingFragment();
        nextTwoFragment.name = name;
        return nextTwoFragment;

    }




    @Override
    protected int getlayoutId() {
        return R.layout.fragment_pt_reward_setting;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {

        activity = (PtNextTwoActivity)getActivity();
        edt_goods_num.setSelection(edt_goods_num.getText().toString().length());

        tv_prize_num.setText(name+"，你想发放多少奖品");

        if(couponList!=null && couponList.size()>0 ){
            llAddTop.setVisibility(View.GONE);
            llBottom.setVisibility(View.VISIBLE);
//            fab.setVisibility(View.VISIBLE);
            setDotLayout(couponList);

            setViewPagerAdapter();


        }else {
            llAddTop.setVisibility(View.VISIBLE);

            llBottom.setVisibility(View.GONE);
//            fab.setVisibility(View.GONE);
            setViewPagerAdapter();
        }


        if(PtNewActivity.compaignStatus != null && !"".equals(PtNewActivity.compaignStatus)){
            if("S".equals(PtNewActivity.compaignStatus)|| "NEW".equals(PtNewActivity.compaignStatus)){

                btnAddCoupon.setVisibility(View.VISIBLE);
            }else {
//                fab.setVisibility(View.GONE);
                btnAddCoupon.setVisibility(View.GONE);

            }
        }else {
//            fab.setVisibility(View.GONE);
            btnAddCoupon.setVisibility(View.VISIBLE);
        }

        //添加监听事件
        addPrizeCountListener();
    }

    @Override
    protected void initData() {

        prizeCount = edt_goods_num.getText().toString().trim();
        if(prizePath != null && !"".equals(prizePath)){
            if(prizePath.contains("get.do?id=")){
                String subResult =prizePath;
                imageId = subResult.substring(subResult.indexOf("get.do?id=")+10,subResult.length());

            }
        }else {
            prizePath = HttpUrls.IMAGE_ULR + ApiConstants.PRIZE_DEFAULT;
            if(prizePath.contains("get.do?id=")){
                String subResult =prizePath;
                imageId = subResult.substring(subResult.indexOf("get.do?id=")+10,subResult.length());

            }
        }



        if("S".equals(PtNewActivity.compaignStatus)|| "NEW".equals(PtNewActivity.compaignStatus)){

        }


        initDataCouponList();
    }



    private void addPrizeCountListener(){
        edt_goods_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                prizeCount = edt_goods_num.getText().toString().trim();
                if(prizeCount != null && !"".equals(prizeCount)){

                }else {
                    prizeCount = "0";
                }
                if(activity.campaignGamesetrewardList != null && activity.campaignGamesetrewardList.size()>0){
                    for (CampaignGamesetreward campaignGamesetreward : activity.campaignGamesetrewardList){

                        String prizeName = campaignGamesetreward.getPrizeName();
                        if("奖项一".equals(prizeName)){
                            campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                            campaignGamesetreward.setPrizeName(prizeName);
                            campaignGamesetreward.setPrizeImageUrl(imageId);
                            campaignGamesetreward.setList(PtNextTwoActivity.campaignCouponList1);
                        }else if("奖项二".equals(prizeName)){
                            campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                            campaignGamesetreward.setPrizeName(prizeName);
                            campaignGamesetreward.setPrizeImageUrl(imageId);
                            campaignGamesetreward.setList(PtNextTwoActivity.campaignCouponList2);
                        }
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    //设置奖项图片
    private void setPrizeImage(){


     /*   Glide.with(this).load(prizePath).into(new SimpleTarget<Drawable>(50,50) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_prize.setBackground(resource);
                }
            }
        });*/
    }
    private void initDataCouponList(){

//        setPrizeImage();
        //本地缓存 提取
        if(ptCouponList!=null && ptCouponList.size()>0){
            for (CampaignCoupon campaignCoupon :ptCouponList){
                List<Store> storeList = campaignCoupon.getStores();

                if(storeList!=null && storeList.size()>0){
                    Store store1 = storeList.get(0);
                    StringBuilder storeIdSb = new StringBuilder();
                    StringBuilder storeNameSb = new StringBuilder();

                    if(store1 != null){
                        storeIdCouponType = store1.getStoreSelectType();
                        String storeName = "";
                        String storeIdArr = "";
                        if("A".equals(storeIdCouponType)){
                            storeName = "全部店铺";
                            for (Store store:storeList){
                                store.setChecked(false);
                                String storeId = store.getStoreId();
                                storeIdSb.append(storeId).append("-");

                            }
                            storeIdArr = "";
                        }else if("D".equals(storeIdCouponType)){
                            storeName = "全部自营";
                            for (Store store:storeList){
                                store.setChecked(false);
                                String storeId = store.getStoreId();
                                storeIdSb.append(storeId).append("-");

                            }
                            storeIdArr = "";
                        }else if("J".equals(storeIdCouponType)){
                            storeName = "全部加盟";
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
                                if(statusQuo!=null){
                                    if(statusQuo == 1){
                                        storeIdSb.append(storeId).append("-");
                                        storeNameSb.append(storeName2);
                                    }

                                }

                            }
                            storeName = storeNameSb.toString();

                            String storeIdA= storeIdSb.toString();
                            if(storeIdSb.toString().isEmpty()){
                                ToastUtils.showShort("请选择店铺");
                                return;
                            }

                            storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);

                        }
                        SPUtils.getInstance().put("brandLogoCouponTwo",SPUtils.getInstance().getString("brandLogo"));
                        SPUtils.getInstance().put("storeIdArrayCouponTwo",storeIdArr);
                        SPUtils.getInstance().put("storeNameArrayCouponTwo",storeName);
                        SPUtils.getInstance().put("storeIdCouponTypeTwo",storeIdCouponType);


                    }
                }

                Coupon coupon = new Coupon();
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
                coupon.setId(campaignCoupon.getId());
                coupon.setTimePeriod(campaignCoupon.getTimePeriod());
                coupon.setExpireStartDate(campaignCoupon.getExpireStartDate());
                coupon.setExpireEndDate(campaignCoupon.getExpireEndDate());
                coupon.setName(campaignCoupon.getCouponName());
                coupon.setExpireStartDay(campaignCoupon.getExpireStartDay());
                coupon.setExpireEndDay(campaignCoupon.getExpireEndDay());




            }



            if(couponList!=null && couponList.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                setDotLayout(couponList);

                if(pagerAdapter!= null ){
                    pagerAdapter.updateData(couponList);
                }else {

                }

                if(setPagerAdapter != null){
                    setPagerAdapter.updateData(couponList);
                }

            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);

            }
        }else {

        /*    if("奖项一".equals(name)){
                rewardCouponList = PtNextTwoActivity.campaignCouponList1;
            }else if("奖项二".equals(name)){
                rewardCouponList = PtNextTwoActivity.campaignCouponList2;

            }else if("奖项三".equals(name)){
                rewardCouponList = PtNextTwoActivity.campaignCouponList3;

            }else if("奖项四".equals(name)){
                rewardCouponList = PtNextTwoActivity.campaignCouponList4;
            }else if("奖项五".equals(name)){
                rewardCouponList = PtNextTwoActivity.campaignCouponList5;
            }else if("奖项六".equals(name)){
                rewardCouponList = PtNextTwoActivity.campaignCouponList6;
            }*/

            if(ptCouponList!=null && ptCouponList.size()>0){
                for (CampaignCoupon campaignCoupon :ptCouponList){
                    List<Store> storeList = campaignCoupon.getStores();

                    if(storeList!=null && storeList.size()>0){
                        Store store1 = storeList.get(0);
                        StringBuilder storeIdSb = new StringBuilder();
                        StringBuilder storeNameSb = new StringBuilder();

                        if(store1 != null){
                            storeIdCouponType = store1.getStoreSelectType();
                            String storeName = "";
                            String storeIdArr = "";
                            if("A".equals(storeIdCouponType)){
                                storeName = "全部店铺";
                                for (Store store:storeList){
                                    store.setChecked(false);
                                    String storeId = store.getStoreId();
                                    storeIdSb.append(storeId).append("-");

                                }
                                storeIdArr = "";
                            }else if("D".equals(storeIdCouponType)){
                                storeName = "全部自营";
                                for (Store store:storeList){
                                    store.setChecked(false);
                                    String storeId = store.getStoreId();
                                    storeIdSb.append(storeId).append("-");

                                }
                                storeIdArr = "";
                            }else if("J".equals(storeIdCouponType)){
                                storeName = "全部加盟";
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
                                    if(statusQuo!=null){
                                        if(statusQuo == 1){
                                            storeIdSb.append(storeId).append("-");
                                            storeNameSb.append(storeName2);
                                        }

                                    }

                                }
                                storeName = storeNameSb.toString();

                                String storeIdA= storeIdSb.toString();
                                if(!storeIdSb.toString().isEmpty()){
                                    storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                                }



                            }
                            SPUtils.getInstance().put("brandLogoPt",SPUtils.getInstance().getString("brandLogo"));
                            SPUtils.getInstance().put("storeIdArrayPt",storeIdArr);
                            SPUtils.getInstance().put("storeNameArrayPt",storeName);
                            SPUtils.getInstance().put("storeIdTypePt",storeIdCouponType);


                        }
                    }

                    Coupon coupon = new Coupon();
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
                    coupon.setId(campaignCoupon.getId());
                    coupon.setTimePeriod(campaignCoupon.getTimePeriod());
                    coupon.setExpireStartDate(campaignCoupon.getExpireStartDate());
                    coupon.setExpireEndDate(campaignCoupon.getExpireEndDate());
                    coupon.setName(campaignCoupon.getCouponName());
                    coupon.setExpireStartDay(campaignCoupon.getExpireStartDay());
                    coupon.setExpireEndDay(campaignCoupon.getExpireEndDay());

                }

                if(couponList!=null && couponList.size()>0 ){
                    llAddTop.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
//                    fab.setVisibility(View.VISIBLE);
                    setDotLayout(couponList);
                    pagerAdapter.updateData(couponList);
                    setPagerAdapter.updateData(couponList);
                }else {
                    llAddTop.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.GONE);
//                    fab.setVisibility(View.GONE);
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
           /*     intent = new Intent(getActivity(),CampaignCouponListActivity.class);
                intent.putExtra("prizeName",name);
                intent.putExtra("nextTwoSelectCoupon",PtNewActivity.campaignType);
                CampaignCouponListFragment.selectCoupons.clear();
                if("奖项一".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,5551,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("奖项二".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,5552,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("奖项三".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,5553,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("奖项四".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,5554,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("奖项五".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,5555,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("奖项六".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,5556,R.anim.slide_in_right,R.anim.slide_out_left);
                }*/

                break;





        }
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
                    Fragment fragment = PtCouponFragment.getInstance(coupon,name);
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
                    Fragment fragment = PtCouponFragment.getInstance(coupon,name);
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


    protected void setViewPagerAdapter(){
        pagerAdapter  = new CouponPagerAdapter(getChildFragmentManager(),couponList);
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
        setPagerAdapter = new CouponSettPagerAdapter(getChildFragmentManager(),couponList);
        setViewPager.setAdapter(setPagerAdapter);
        setViewPager.setCurrentItem(0);

        //活动优惠券底部设置
        setViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if(couponList != null && couponList.size()>0){
                    Coupon coupon = couponList.get(position);
                    CampaignCoupon campaignCoupon  = new CampaignCoupon();
                    campaignCoupon.setCouponId(coupon.getId());
                    campaignCoupon.setCouponName(coupon.getName());
                    if(storeIdCouponType!=null && !"".equals(storeIdCouponType)){
                        if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                            campaignCoupon.setStoreStr("");
                            campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
                        }else if("H".equals(storeIdCouponType)){
                            campaignCoupon.setStoreStr(storeId);
                            campaignCoupon.setStoreSelectType("");
                        }
                    }else {
                        campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
                    }
                    campaignCoupon.setStoreSelectType(nextSettingFragment.couponStoreSelectType);
                    campaignCoupon.setStoreStr(nextSettingFragment.storeId);

                    if("A".equals(nextSettingFragment.expireEndType)){
                        String startDate = nextSettingFragment.startDate;
                        String endDate = nextSettingFragment.endDate;

                        campaignCoupon.setExpireStartDate(startDate!=null?startDate:"");
                        campaignCoupon.setExpireEndDate(endDate!=null?endDate:"");

                        if(nextSettingFragment.storeId!=null && (startDate!=null&& !"".equals(startDate)) && (endDate!=null&& !"".equals(endDate))){

                            if("商品设置".equals(name)){
                                PtNextTwoActivity.campaignCouponList1.add(campaignCoupon);


                            }else if("成团奖励".equals(name)){
                                PtNextTwoActivity.campaignCouponList2.add(campaignCoupon);


                            }
                        }

                    }else if("R1".equals(nextSettingFragment.expireEndType)){
                        String startDay= nextSettingFragment.startDay;
                        String endDay = nextSettingFragment.endDay;
                        campaignCoupon.setExpireStartDay(startDay!=null?startDay:"");
                        campaignCoupon.setExpireEndDay(endDay!=null?endDay:"");
                        if("商品设置".equals(name)){
                            PtNextTwoActivity.campaignCouponList1.add(campaignCoupon);


                        }else if("成团奖励".equals(name)){
                            PtNextTwoActivity.campaignCouponList2.add(campaignCoupon);


                        }

                    }
                }

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
        private  List<Coupon> couponList = null;
        public CouponSettPagerAdapter(FragmentManager fm, List<Coupon> couponList) {
            super(fm);
            this.mFragmentManager = fm;
            this.couponList = couponList;
            mFragmentList = new ArrayList<>();
            if(couponList !=null && couponList.size()>0){
                for (int i = 0;i<couponList.size();i++){
                    Coupon coupon = couponList.get(i);
                    Fragment fragment = PtNextSettingFragment.getInstance(coupon,i,name,setViewPager);
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
                    Fragment fragment = PtNextSettingFragment.getInstance(coupon,i,name,setViewPager);
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

    @Override
    public void chanageLayout(ArrayList<Coupon> couponList, String prizeName) {
//        PtNextTwoActivity activity = (PtNextTwoActivity)getActivity();
        /*if("奖项一".equals(prizeName)){
            if(activity.couponList1.isEmpty()){
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }else {
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            }
            remove(activity.couponList1);
            setDotLayout(activity.couponList1);
            pagerAdapter.updateData(activity.couponList1);
            setPagerAdapter.updateData(activity.couponList1);
        }else if("奖项二".equals(prizeName)){
            if(activity.couponList2.isEmpty()){
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }else {
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            }
            remove(activity.couponList2);
            setDotLayout(activity.couponList2);
            pagerAdapter.updateData(activity.couponList2);
            setPagerAdapter.updateData(activity.couponList2);
        }*/
    }


    private void remove(ArrayList<Coupon> selectCouponList){
        for(int i=0;i<selectCouponList.size();i++){
            for(int j=selectCouponList.size()-1;j>i;j--){
                if(selectCouponList.get(i).getId()==selectCouponList.get(j).getId()){
                    selectCouponList.remove(j);
                }
            }
        }
    }

}
