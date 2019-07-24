package com.zwx.scan.app.feature.couponmanage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.zwx.scan.app.data.bean.CampaginGrant;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.CouponMaterial;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.campaign.CampaignCouponListActivity;
import com.zwx.scan.app.feature.campaign.CampaignCouponListFragment;
import com.zwx.scan.app.utils.ButtonUtils;
import com.zwx.scan.app.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/03/19
 * desc   :  常规发券 生日礼 会员日 开卡礼
 * version: 1.0
 **/
public class GiveCouponNewNextActivity extends BaseActivity<GiveCouponContract.Presenter> implements GiveCouponContract.View,View.OnClickListener,GiveCouponFragment.ChanageGiveToLayoutLisenter {

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.btn_add_coupon)
    protected Button btnAddCoupon;

    @BindView(R.id.view_pager)
    protected CustomViewPager couponViewPager;

    @BindView(R.id.setting_view_pager)
    protected NoScrollViewPager setViewPager;
    @BindView(R.id.dots)
    protected LinearLayout mDotsLayout;
    @BindView(R.id.ll_add_top)
    protected LinearLayout llAddTop;

    @BindView(R.id.ll_bottom_coupon_edit)
    protected LinearLayout llBottom;

    @BindView(R.id.fab)
    protected ImageButton fab;

    @BindView(R.id.btn_pre)
    protected Button btnPre;


    @BindView(R.id.ll_save)
    protected LinearLayout ll_save;
    @BindView(R.id.btn_save)
    protected Button btnSave;

    @BindView(R.id.btn_save_and_public)
    protected Button btnSavePublic;

    //标签动态设置
    @BindView(R.id.ll_top_label)
    protected TextView ll_top_label;


    @BindView(R.id.iv_one)
    protected ImageView ivOne;
    @BindView(R.id.iv_ellipsis_one)
    protected ImageView ivEllipsisOne;
    @BindView(R.id.iv_two)
    protected ImageView ivTwo;
    @BindView(R.id.iv_give_coupon_next)
    protected ImageView iv_give_coupon_next;


    protected ArrayList<CampaignCoupon> campaignCouponList =new ArrayList<>();

    protected  static ArrayList<CampaignCoupon> forwardCouponList = new ArrayList<>();
    protected  ArrayList<Coupon> couponList = new ArrayList<>();
    private List<ImageView> mDotsIV = new ArrayList<>();
    private GiveCouponNextFragment giveCouponNextFragment = new GiveCouponNextFragment();
    protected   String isEditCampaign = "NO";

    protected String isCopyCreate ;


    protected   String storeIdGiveType ;

    private String title;

    protected CouponPagerAdapter pagerAdapter = null;
    protected CouponSettPagerAdapter setPagerAdapter = null;

    private String btnFlag = "";

    private String userId = "";
    private String compId = "";
    private String grantType = "";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_campaign_coupon_new_next;
    }

    @Override
    protected void initView() {
        DaggerGiveCouponComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .giveCouponModule(new GiveCouponModule(this))
                .build()
                .inject(this);
        title =getIntent().getStringExtra("title");
        tvTitle.setText(title+getResources().getString(R.string.set_label));

        setSetTep();
    }

    @Override
    protected void initData() {
        setViewPagerAdapter();
        isEditCampaign =  getIntent().getStringExtra("isEditCampaign");
        isCopyCreate = getIntent().getStringExtra("isCopyCreate");
        userId = SPUtils.getInstance().getString("userId");
        compId = SPUtils.getInstance().getString("compId");
        grantType = getIntent().getStringExtra("grantType");
        if("YES".equals(isEditCampaign)){  //编辑
            if("YES".equals(isCopyCreate)){  //编辑页面复制并创建
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setText("复制并创建");
            }else {
                tvRight.setVisibility(View.GONE);
                tvRight.setText("");
            }


        }else {


        }

        initDataCouponList();

        if("S".equals(GiveCouponNewActivity.compaignStatus)||"NEW".equals(GiveCouponNewActivity.compaignStatus)){
            ll_save.setVisibility(View.VISIBLE);
            btnSavePublic.setVisibility(View.VISIBLE);
            btnPre.setVisibility(View.VISIBLE);
            if(forwardCouponList !=null && forwardCouponList.size()>0){
                fab.setVisibility(View.VISIBLE);
            }else {
                fab.setVisibility(View.GONE);
            }
        }else {
//            btnSave.setText("返回");
            ll_save.setVisibility(View.GONE);
            btnSavePublic.setVisibility(View.VISIBLE);
            btnSavePublic.setText("返回");
            btnPre.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
        }
        if("B".equals(grantType)){
            ll_top_label.setText("该会员卡会员，过生日可获得什么优惠券");
            iv_give_coupon_next.setImageResource(R.drawable.ic_coupon_next_shengrili);
        }else if ("K".equals(grantType)){
            ll_top_label.setText(title+","+getText(R.string.camapign_coupon_new_next_add));

            iv_give_coupon_next.setImageResource(R.drawable.ic_coupon_next_kaikali);
        }else if ("M".equals(grantType)){ //会员日
            ll_top_label.setText(getText(R.string.camapign_coupon_new_next_huiyuanri));
            iv_give_coupon_next.setImageResource(R.drawable.ic_give_coupon_panda_m);
        }

        if("S".equals(GiveCouponNewActivity.compaignStatus)|| "NEW".equals(GiveCouponNewActivity.compaignStatus)){
            if(forwardCouponList !=null && forwardCouponList.size()>0){
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

    private void setSetTep(){

        ivOne.setBackgroundResource(R.drawable.ic_first_clicked);
        ivEllipsisOne.setBackgroundResource(R.drawable.ic_ellipsis_blue);
        ivTwo.setBackgroundResource(R.drawable.ic_two_clicked);
    }


    private void initDataCouponList(){
        campaignCouponList = new ArrayList<>();
        couponList.clear();
        if(forwardCouponList !=null && forwardCouponList.size()>0){
            campaignCouponList = forwardCouponList;
            for (CampaignCoupon campaignCoupon :forwardCouponList){

                List<Store> storeList = campaignCoupon.getStores();
                Coupon coupon = new Coupon();
                if(storeList!=null && storeList.size()>0){
                    Store store1 = storeList.get(0);
                    StringBuilder storeIdSb = new StringBuilder();
                    StringBuilder storeNameSb = new StringBuilder();

                    if(store1 != null){
                        storeIdGiveType = store1.getStoreSelectType();
                        String storeName = "";
                        String storeIdArr = "";
                        if("A".equals(storeIdGiveType)){
                            storeName = "#全部店铺";

                            storeIdArr = "";
                        }else if("D".equals(storeIdGiveType)){
                            storeName = "#全部自营";

                            storeIdArr = "";
                        }else if("J".equals(storeIdGiveType)){
                            storeName = "#全部加盟";

                            storeIdArr = "";
                        }else {
                            storeIdGiveType = "H";
                            if(storeList != null && storeList.size()>0){
                                for (Store store:storeList){
                                    store.setChecked(false);
                                    String storeId = store.getStoreId();
                                    String storeName2 = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                    storeIdSb.append(storeId).append("-");
                                    storeNameSb.append(storeName2);

                                }
                                storeName = storeNameSb.toString();

                                String storeIdA= storeIdSb.toString();
                                if(storeIdSb.toString()!=null && !"".equals(storeIdSb.toString())){
                                    storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                                }
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

    private void remove(List<Coupon> couponList){
        for(int i=0;i<couponList.size();i++){
            for(int j=couponList.size()-1;j>i;j--){
                if(String.valueOf(couponList.get(i).getId()).equals(String.valueOf(couponList.get(j).getId()))){
                    couponList.remove(j);
                }
            }
        }
    }

    @OnClick({R.id.iv_back,R.id.tv_right,R.id.btn_add_coupon,
            R.id.btn_pre,R.id.btn_save,R.id.btn_save_and_public,R.id.fab})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        CampaginGrant campaginGrant = null;
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.startActivity(GiveCouponManageActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                finish();

                break;
            case R.id.btn_add_coupon:

                intent = new Intent(GiveCouponNewNextActivity.this,CampaignCouponListActivity.class);
                intent.putExtra("nextTwoSelectCoupon","GIVE");
                ActivityUtils.startActivityForResult(GiveCouponNewNextActivity.this,intent,4444,R.anim.slide_in_right,R.anim.slide_out_left);
                CampaignCouponListFragment.selectCoupons.clear();
                break;
            case R.id.tv_right:
                ToastUtils.showCustomShortBottom(getResources().getString(R.string.create_success));
                isCopyCreate = "NO";
                isEditCampaign = "YES";
                GiveCouponNewActivity.compaignStatus = "NEW";
                if(GiveCouponNewActivity.campaign !=null){
                    GiveCouponNewActivity.campaign.setCampaignId(null);
                    GiveCouponNewActivity.campaign.setCampaignType("zj");
                    GiveCouponNewActivity.campaign.setCampaignName("");
                }


                Intent intent1 = new Intent(GiveCouponNewNextActivity.this,GiveCouponNewActivity.class);

                intent1.putExtra("title",title);
                intent1.putExtra("isCopyCreate",isCopyCreate);
                intent1.putExtra("isEditCampaign",isEditCampaign);
                intent1.putExtra("grantType",grantType);
                intent1.putExtra("compMemTypeId",GiveCouponNewActivity.campaginGrant.getCompMemTypeId());
                intent1.putExtra("compaignStatus",  GiveCouponNewActivity.compaignStatus);
                ActivityUtils.startActivity(intent1,
                        R.anim.slide_in_left, R.anim.slide_out_right);

                break;
            case R.id.btn_pre: //上一步
                if(campaignCouponList != null && campaignCouponList.size()>0){
                   forwardCouponList = campaignCouponList;
//                    SPUtils.getInstance().putListData("forwardCouponList",campaignCouponList);
                }

                ActivityUtils.finishActivity(GiveCouponNewNextActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case R.id.fab:
                intent = new Intent(GiveCouponNewNextActivity.this,CampaignCouponListActivity.class);
                intent.putExtra("nextTwoSelectCoupon","GIVE");
                ActivityUtils.startActivityForResult(GiveCouponNewNextActivity.this,intent,4444,R.anim.slide_in_right,R.anim.slide_out_left);
                CampaignCouponListFragment.selectCoupons.clear();
                break;
            case R.id.btn_save:

                if(campaignCouponList != null && campaignCouponList.size()>0){
                    forwardCouponList = campaignCouponList;
                    for (CampaignCoupon campaignCoupon : forwardCouponList) {
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
                btnFlag = "save";
                if(check()){
                    return;
                }

                if (!ButtonUtils.isFastDoubleClick(R.id.btn_save)) {   //处理防止点击多次
                    presenter.saveCampaignInfo(this,GiveCouponNewActivity.campaign,GiveCouponNewActivity.compMemTypeId,GiveCouponNewActivity.campaginGrant,forwardCouponList,userId,compId,btnFlag);
                }


                break;

            case R.id.btn_save_and_public:

                if(campaignCouponList != null && campaignCouponList.size()>0){
                    forwardCouponList = campaignCouponList;
                    for (CampaignCoupon campaignCoupon : forwardCouponList) {
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
                btnFlag = "saveAndpublic";
                if(check()){
                    return;
                }

                if("S".equals(GiveCouponNewActivity.compaignStatus)||"NEW".equals(GiveCouponNewActivity.compaignStatus)){
                    if (!ButtonUtils.isFastDoubleClick(R.id.btn_save_and_public)) {   //处理防止点击多次
                        presenter.saveCampaignInfo(this,GiveCouponNewActivity.campaign,GiveCouponNewActivity.compMemTypeId,GiveCouponNewActivity.campaginGrant,forwardCouponList,userId,compId,btnFlag);
                    }

                }else {
                    ActivityUtils.startActivity(GiveCouponManageActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                    finish();
                }
                break;



        }
    }


    private boolean check(){

        if("save".equals(btnFlag)){
            if(GiveCouponNewActivity.compMemTypeId == null || "".equals(GiveCouponNewActivity.compMemTypeId)){
                setDialog("您在第一步，会员卡未选择");
                return true;
            }

            if(GiveCouponNewActivity.campaginGrant !=null) {
                String compMemTypeId = GiveCouponNewActivity.campaginGrant.getCompMemTypeId();
                if (compMemTypeId == null || "".equals(compMemTypeId)) {
                    setDialog("您在第一步，会员卡未选择");
                    return true;
                }
            }

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
                setDialog("您在第一步，会员卡未选择");
                return true;
            }

            if(GiveCouponNewActivity.campaginGrant !=null){
                String compMemTypeId = GiveCouponNewActivity.campaginGrant.getCompMemTypeId();
                if(compMemTypeId == null || "".equals(compMemTypeId)){
                    setDialog("您在第一步，会员卡未选择");
                    return true;
                }
                if("M".equals(GiveCouponNewActivity.grantType)||"K".equals(GiveCouponNewActivity.grantType)){
                   /* Integer sendDay =  GiveCouponNewActivity.campaginGrant.getSendDay();
                    if(sendDay == null ||"".equals(sendDay)){
                        setDialog("您在第一步中，设置信息不完善，请补充信息。");
                        return true;
                    }*/
                }
                if("M".equals(GiveCouponNewActivity.grantType)){
                    String memberDay = GiveCouponNewActivity.campaginGrant.getMemberDay();
                    if(memberDay == null ||"".equals(memberDay)){
                        setDialog("您在第一步中，设置信息不完善，请补充信息。");
                        return true;
                    }
                }



            }else {
                setDialog("您在第一步中，设置信息不完善，请补充信息。");
                return true;
            }

            if(forwardCouponList!=null && forwardCouponList.size()>0){
                for(CampaignCoupon campaignCoupon : forwardCouponList){
                    String startDate =campaignCoupon.getExpireStartDate();
                    String endDate = campaignCoupon.getExpireEndDate();
                    String startDay =campaignCoupon.getExpireStartDay();
                    String endDay = campaignCoupon.getExpireEndDay();
                    String couponName =campaignCoupon.getCouponName();
                    String expireType = campaignCoupon.getExpireEndType();
                    if("A".equals(expireType)){
                        if(startDate == null ||"".equals(startDate)){
                            setDialog("您在第二步，设置信息不完善，请补充信息。");

                            return true;
                        }

                        if(endDate == null ||"".equals(endDate)){
                            setDialog("您在第二步，设置信息不完善，请补充信息。");
                            return true;
                        }
                    }else if("R1".equals(expireType)){
                        if(startDay == null ||"".equals(startDay)){
                            setDialog("您在第二步，设置信息不完善，请补充信息。");
                            return true;
                        }

                        if(endDay == null ||"".equals(endDay)){
                            setDialog("您在第二步，设置信息不完善，请补充信息。");
                            return true;
                        }


                    }


                }
            }else {
                setDialog("您在第二步中，必须设置优惠券，请补充信息");
                return true;
            }

        }


        return false;
    }


    public void setDialog(String str2){

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView textView = (TextView)rootView.findViewById(R.id.message);
        textView.setText(str2);
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
        SPUtils.getInstance().remove("forwardCouponList");
        ActivityUtils.startActivity(GiveCouponManageActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);

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
                    Fragment fragment = GiveCouponFragment.getInstance(coupon,i);
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
                    Fragment fragment = GiveCouponFragment.getInstance(coupon,i);
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
        pagerAdapter  = new CouponPagerAdapter(getSupportFragmentManager(),couponList);
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
                                campaignCoupon.setStoreSelectType(giveCouponNextFragment.couponStoreSelectType);
                                campaignCoupon.setStoreStr(giveCouponNextFragment.storeId);

                                if("A".equals(giveCouponNextFragment.expireEndType)){
                                    String startDate = giveCouponNextFragment.startDate;
                                    String endDate = giveCouponNextFragment.endDate;

                                    campaignCoupon.setExpireStartDate(startDate!=null?startDate:"");
                                    campaignCoupon.setExpireEndDate(endDate!=null?endDate:"");
                                }else if("R1".equals(giveCouponNextFragment.expireEndType)){
                                    String startDay= giveCouponNextFragment.startDay;
                                    String endDay = giveCouponNextFragment.endDay;
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
        private  List<Coupon> couponList = null;
        public CouponSettPagerAdapter(FragmentManager fm, List<Coupon> couponList) {
            super(fm);
            this.mFragmentManager = fm;
            this.couponList = couponList;
            mFragmentList = new ArrayList<>();
            if(couponList !=null && couponList.size()>0){
                for (int i = 0;i<couponList.size();i++){
                    Coupon coupon = couponList.get(i);
                    Fragment fragment = GiveCouponNextFragment.getInstance(coupon,i);
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
                    Fragment fragment = GiveCouponNextFragment.getInstance(coupon,i);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == 4444){
            if(requestCode == 4444){

                CampaignCouponListFragment.selectCoupons.clear();
                ArrayList<Coupon> resultCouponList = (ArrayList<Coupon>)data.getSerializableExtra("selectCouponList");

                if(resultCouponList!=null && resultCouponList.size()>0 ){
                    llAddTop.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.VISIBLE);
                    couponList.addAll(resultCouponList);
                    setDotLayout(couponList);
                    pagerAdapter.updateData(couponList);
                    setPagerAdapter.updateData(couponList);

                    if(resultCouponList != null && resultCouponList.size()>0) {
                        for (Coupon coupon : resultCouponList) {
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
                            campaignCoupon.setStores(new ArrayList<>());
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
                }else {
                    if(couponList != null &&couponList.size()>0){
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
        }else {
            if(couponList != null &&couponList.size()>0){
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

    @Override
    public void chanageLayout(ArrayList<Coupon> couponList) {
        if(couponList.isEmpty()){
            llAddTop.setVisibility(View.VISIBLE);
            llBottom.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
        }else {
            llAddTop.setVisibility(View.GONE);
            llBottom.setVisibility(View.VISIBLE);
           fab.setVisibility(View.VISIBLE);
        }
        setDotLayout(couponList);
        pagerAdapter.updateData(couponList);
        setPagerAdapter.updateData(couponList);
       if(couponList != null && "".equals(couponList)){

       }else {
           forwardCouponList.clear();
       }

    }




}
