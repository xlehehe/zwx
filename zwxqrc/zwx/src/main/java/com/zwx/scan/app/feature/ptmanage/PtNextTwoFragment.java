package com.zwx.scan.app.feature.ptmanage;


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
import android.widget.TextView;

import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CampaignGamesetreward;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.CouponMaterial;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.feature.campaign.CampaignCouponListActivity;
import com.zwx.scan.app.feature.campaign.CampaignCouponListFragment;
import com.zwx.scan.app.feature.campaign.CampaignLikeTwoActivity;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanNewActivity;
import com.zwx.scan.app.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PtNextTwoFragment extends BaseFragment  implements View.OnClickListener,PtCouponFragment.ChanagePtTwoLayoutLisenter{


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


    @BindView(R.id.tv_prize_num)
    protected TextView tv_prize_num;

    @BindView(R.id.edt_goods_num)
    protected EditText edt_goods_num;

    @BindView(R.id.fab)
    protected ImageButton fab;

    @BindView(R.id.tv_goods_reward_label)
    protected TextView tv_goods_reward_label;
    @BindView(R.id.iv_goods_reward)
    protected ImageView iv_goods_reward;
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



    protected   ArrayList<CampaignCoupon> rewardCouponList = new ArrayList<>();
    protected   String isEditCampaign = "NO";

    protected String isCopyCreate ;


    protected   String storeIdCouponType ;
    private PtNextSettingFragment nextSettingFragment = new PtNextSettingFragment();

    private String prizePath  ;

    protected CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();

    private String goodsCount; //商品数量次数
    private Integer count; //商品数量
    private PtNextTwoActivity activity =null;
    public PtNextTwoFragment() {
        // Required empty public constructor
    }

    public static PtNextTwoFragment getInstance(String name,ArrayList<Coupon> couponList){
        PtNextTwoFragment nextTwoFragment = new PtNextTwoFragment();
        nextTwoFragment.name = name;
        nextTwoFragment.couponList = couponList;

        return nextTwoFragment;

    }




    @Override
    protected int getlayoutId() {
        return R.layout.fragment_pt_goods_setting;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {



        activity = (PtNextTwoActivity)getActivity();
        edt_goods_num.setSelection(edt_goods_num.getText().toString().length());


        if(couponList!=null && couponList.size()>0 ){
            llAddTop.setVisibility(View.GONE);
            llBottom.setVisibility(View.VISIBLE);
//            activity.fab.setVisibility(View.VISIBLE);
            setDotLayout(couponList);
        }else {
            llAddTop.setVisibility(View.VISIBLE);

            llBottom.setVisibility(View.GONE);
//            activity.fab.setVisibility(View.GONE);
        }


        if("S".equals(PtNewActivity.compaignStatus)|| "NEW".equals(PtNewActivity.compaignStatus)){
            edt_goods_num.setEnabled(true);
            fab.setVisibility(View.VISIBLE);
            btnAddCoupon.setVisibility(View.VISIBLE);
        }else {
            fab.setVisibility(View.GONE);
            btnAddCoupon.setVisibility(View.GONE);
            edt_goods_num.setEnabled(false);
        }

        if("商品设置".equals(name)){
            ll_prize_count.setVisibility(View.VISIBLE);
            iv_goods_reward.setBackgroundResource(R.drawable.ic_pt_good_setting_panda);
            tv_goods_reward_label.setText("请设置，你要出售的优惠券");

        }else if("成团奖励".equals(name)){
            ll_prize_count.setVisibility(View.GONE);
            tv_goods_reward_label.setText("会员成功组团后，想要奖励给拼团的会员什么优惠券");
            iv_goods_reward.setBackgroundResource(R.drawable.ic_pt_reward_setting_panda);
        }




    }

    @Override
    protected void initData() {

        goodsCount = edt_goods_num.getText().toString().trim();


        if("S".equals(PtNewActivity.compaignStatus)){
            if(goodsCount != null && !"".equals(goodsCount)){
            }else {
                goodsCount = "0";
            }
        }else if("NEW".equals(PtNewActivity.compaignStatus)){

        }

        //添加监听事件
        addPrizeCountListener();
        setViewPagerAdapter();
        initCouponList();
    }

    protected  void initcampaignGamesetreward(){

        setCampaignGamesetrewardValue();

        PtNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);


    }
    private void  setCampaignGamesetrewardValue(){

        if(goodsCount!= null && !"".equals(goodsCount)){
            campaignGamesetreward.setPrizeCount(Integer.parseInt(goodsCount));
        }else {
            campaignGamesetreward.setPrizeCount(0);
        }
        campaignGamesetreward.setPrizeName(name);
        campaignGamesetreward.setPrizeOrder(null);
        if("商品设置".equals(name)){
            campaignGamesetreward.setRewardType("1");
            campaignGamesetreward.setList(PtNextTwoActivity.campaignCouponList1);
        }else if("成团奖励".equals(name)){
            campaignGamesetreward.setRewardType("0");
            campaignGamesetreward.setPrizeCount(null);
            campaignGamesetreward.setList(PtNextTwoActivity.campaignCouponList2);
        }
    }

    private void addPrizeCountListener(){
        edt_goods_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                goodsCount = edt_goods_num.getText().toString().trim();
                if(goodsCount != null && !"".equals(goodsCount) && !"0".equals(goodsCount)){
                    if(activity.campaignGamesetrewardList != null && activity.campaignGamesetrewardList.size()>0){
                        for (CampaignGamesetreward campaignGamesetreward : activity.campaignGamesetrewardList){

                            String prizeName = campaignGamesetreward.getPrizeName();
                            if("商品设置".equals(prizeName)){
                                campaignGamesetreward.setPrizeCount(Integer.parseInt(goodsCount));
                                campaignGamesetreward.setRewardType("1");
                                campaignGamesetreward.setList(PtNextTwoActivity.campaignCouponList1);
                            }else if("成团奖励".equals(prizeName)){
                                campaignGamesetreward.setPrizeCount(0);
                                campaignGamesetreward.setRewardType("0");
                                campaignGamesetreward.setList(PtNextTwoActivity.campaignCouponList2);
                            }

                        }

                    }
                }else {
                    goodsCount = "0";
                   s.clear();
                }


            }
        });
    }
    private void initCouponList(){
        edt_goods_num.setSelection(edt_goods_num.getText().toString().length());
        if(PtNextTwoActivity.campaignGamesetrewardList != null && PtNextTwoActivity.campaignGamesetrewardList.size()>0) {

            for (CampaignGamesetreward campaignGamesetreward : PtNextTwoActivity.campaignGamesetrewardList) {
                String prizeName = campaignGamesetreward.getPrizeName();
                count = campaignGamesetreward.getPrizeCount();
                if ("商品设置".equals(prizeName) && name.equals(prizeName)) {
                    count = campaignGamesetreward.getPrizeCount();
                    if(count != null && count.intValue()>0){
                        edt_goods_num.setText(String.valueOf(count));
                    }
                    PtNextTwoActivity.campaignCouponList1 = campaignGamesetreward.getList();

                    rewardCouponList = PtNextTwoActivity.campaignCouponList1;
                } else if ("成团奖励".equals(prizeName) && name.equals(prizeName)) {
                    PtNextTwoActivity.campaignCouponList2 = campaignGamesetreward.getList();
                    rewardCouponList = PtNextTwoActivity.campaignCouponList2;

                }
                //本地缓存 提取
                if(rewardCouponList!=null && rewardCouponList.size()>0){
                    for (CampaignCoupon campaignCoupon :rewardCouponList){
                        List<Store> storeList = campaignCoupon.getStores();
                        Coupon coupon = new Coupon();
                        if(storeList!=null && storeList.size()>0){
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


                        if ("商品设置".equals(prizeName) && name.equals(prizeName)) {
                            couponList1 = activity.couponList1;
                        } else if ("成团奖励".equals(prizeName)&& name.equals(prizeName)) {
                            couponList2 = activity.couponList2;
                        }
                    }
                }
            }
            addCoupontList();
        }
    }

    private void addCoupontList() {
        if ("商品设置".equals(name)) {
            couponList1 = couponList;

            if (couponList1 != null && couponList1.size() > 0) {
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(PtNewActivity.compaignStatus)|| "NEW".equals(PtNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }

                setDotLayout(couponList1);

                pagerAdapter.updateData(couponList1);
                setPagerAdapter.updateData(couponList1);


            } else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }
        } else if ("成团奖励".equals(name)) {
            couponList2 = couponList;
            if (couponList2 != null && couponList2.size() > 0) {
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(PtNewActivity.compaignStatus)|| "NEW".equals(PtNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
                setDotLayout(couponList2);

                pagerAdapter.updateData(couponList2);
                setPagerAdapter.updateData(couponList2);


            } else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

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
                intent.putExtra("nextTwoSelectCoupon",PtNewActivity.campaignType);
                CampaignCouponListFragment.selectCoupons.clear();
                if("商品设置".equals(name)){
                    intent.putExtra("ptName","goodsSetting");
                    ActivityUtils.startActivityForResult(getActivity(),intent,6661,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("成团奖励".equals(name)){
                    intent.putExtra("ptName","rewardSetting");
                    ActivityUtils.startActivityForResult(getActivity(),intent,6662,R.anim.slide_in_right,R.anim.slide_out_left);
                }


                break;
            case R.id.fab:

                intent = new Intent(getActivity(),CampaignCouponListActivity.class);
                intent.putExtra("nextTwoSelectCoupon",PtNewActivity.campaignType);
                if("商品设置".equals(name)){
                    intent.putExtra("ptName","goodsSetting");
                    ActivityUtils.startActivityForResult((PtNextTwoActivity)getActivity(),intent,6661,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("成团奖励".equals(name)){
                    intent.putExtra("ptName","rewardSetting");
                    ActivityUtils.startActivityForResult((PtNextTwoActivity)getActivity(),intent,6662,R.anim.slide_in_right,R.anim.slide_out_left);
                }
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
//        couponViewPager.setCurrentItem(0);

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
//        setViewPager.setCurrentItem(0);
       /* //添加优惠券指定最后一张
        if(couponList != null && !"".equals(couponList)){
            int po = couponList.size()-1;
            setViewPager.setCurrentItem(po);
            couponViewPager.setCurrentItem(po);

        }*/

        //活动优惠券底部设置
        setViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                /*if("商品设置".equals(name)){
                    if(couponList1 != null && couponList1.size()>0){
                        Coupon coupon = couponList1.get(position);


                        if(PtNextTwoActivity.campaignCouponList1!=null && PtNextTwoActivity.campaignCouponList1.size()>0){
                            for(int i=0;i<PtNextTwoActivity.campaignCouponList1.size();i++){
                                CampaignCoupon campaignCoupon = PtNextTwoActivity.campaignCouponList1.get(i);

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
                        }
                    }

                }else if("成团奖励".equals(name)){

                    if(couponList1 != null && couponList1.size()>0){
                        Coupon coupon = couponList1.get(position);


                        if(PtNextTwoActivity.campaignCouponList2!=null && PtNextTwoActivity.campaignCouponList2.size()>0){
                            for(int i=0;i<PtNextTwoActivity.campaignCouponList2.size();i++){
                                CampaignCoupon campaignCoupon = PtNextTwoActivity.campaignCouponList2.get(i);

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
    public void chanageLayout(ArrayList<Coupon> couponList, String ptName) {
        if("商品设置".equals(ptName)){
            if(activity.couponList1.isEmpty()){
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);

            }else {
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
            }
            setDotLayout(activity.couponList1);
            if(activity.campaignGamesetrewardList!= null && activity.campaignGamesetrewardList.size()>0){
                for (CampaignGamesetreward campaignGamesetreward : activity.campaignGamesetrewardList){
                    String name = campaignGamesetreward.getPrizeName();

                    if(ptName.equals(name)){
                        campaignGamesetreward.setList(activity.campaignCouponList1);
                    }
                }
            }
            pagerAdapter.updateData(activity.couponList1);
            setPagerAdapter.updateData(activity.couponList1);

        }else if("成团奖励".equals(ptName)){
            if(activity.couponList2.isEmpty()){
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);

            }else {
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
            }
            setDotLayout(activity.couponList2);
            if(activity.campaignGamesetrewardList!= null && activity.campaignGamesetrewardList.size()>0){
                for (CampaignGamesetreward campaignGamesetreward : activity.campaignGamesetrewardList){
                    String name = campaignGamesetreward.getPrizeName();

                    if(ptName.equals(name)){
                        campaignGamesetreward.setList(activity.campaignCouponList2);
                    }
                }
            }
            pagerAdapter.updateData(activity.couponList2);
            setPagerAdapter.updateData(activity.couponList2);

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


}
