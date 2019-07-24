package com.zwx.scan.app.feature.campaign;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.zwx.library.pickerview.picker.builder.TimePickerBuilder;
import com.zwx.library.pickerview.picker.listener.CustomListener;
import com.zwx.library.pickerview.picker.listener.OnTimeSelectListener;
import com.zwx.library.pickerview.picker.view.TimePickerView;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.DateUtils;
import com.zwx.library.utils.LogUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ScreenUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.CouponMaterial;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.countanalysis.MoreBrandListAdapter;
import com.zwx.scan.app.widget.MoreTextView;
import com.zwx.scan.app.widget.NoScrollViewPager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CampaignCouponNextSettingFragment extends BaseFragment<CampaignsContract.Presenter> implements CampaignsContract.View,View.OnClickListener{


    @BindView(R.id.ll_bottom_coupon)
    protected LinearLayout llBottomCoupon;

    @BindView(R.id.tv_coupon_name)
    protected TextView tvCouponName;

    @BindView(R.id.iv_brand)
    protected ImageView ivBrand;

    @BindView(R.id.tv_select_shop)
    protected TextView tvSelectStore;

    //选择店铺
    @BindView(R.id.tv_store_name)
    protected MoreTextView tvStoreNames;

    //按固定日期
    @BindView(R.id.ll_left_dates)
    protected LinearLayout llLeftDates;

    @BindView(R.id.ll_date)
    protected LinearLayout llDate;


    @BindView(R.id.ll_days)
    protected LinearLayout llDays;
    @BindView(R.id.tv_start_time)
    protected TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    protected TextView tvEndTime;

    //
    @BindView(R.id.tv_date_label)
    protected TextView tvDateLabel;
    @BindView(R.id.left_view)
    protected View leftView;


    //按固定市场
    @BindView(R.id.ll_right_durations)
    protected LinearLayout llRight;

    @BindView(R.id.tv_duration_label)
    protected TextView tvDurLaybel;
    @BindView(R.id.right_view)
    protected View rightView;

    @BindView(R.id.rl_top)
    protected RelativeLayout llDaysTop;
    @BindView(R.id.rl_bottom)
    protected RelativeLayout llDaysBottom;

    @BindView(R.id.ll_start)
    protected LinearLayout llStartTime;

    @BindView(R.id.ll_end)
    protected LinearLayout llEndTime;


    //加减
    @BindView(R.id.iv_left_subtract)
    protected ImageView ivLeftSubtract;

    @BindView(R.id.edt_day_one)
    protected EditText edtDayDate;

    @BindView(R.id.tv_valid_days)
    protected TextView tvValidDays;
    @BindView(R.id.iv_right_add)
    protected ImageView ivRightAdd;

    @BindView(R.id.iv_left_subtract2)
    protected ImageView ivLeftSubtract2;
    @BindView(R.id.edt_day_two)
    protected EditText edtDayDate2;
    @BindView(R.id.tv_coupon_days)
    protected TextView tvCouponDays;
    @BindView(R.id.iv_right_add2)
    protected ImageView ivRightAdd2;


    //优惠券张数
    @BindView(R.id.iv_num_left_subtract)
    protected ImageView iv_num_left_subtract;

    @BindView(R.id.edt_coupon_num)
    protected EditText edt_coupon_num;

    @BindView(R.id.iv_num_right_add)
    protected ImageView iv_num_right_add;

    //优惠券张数
    private int getCouponNum  = 1 ;
    private String getCouponNumStr  = "1" ;
    protected String couponQnt;

    private Coupon coupon = new Coupon();
    PopWindow popWindow;

    protected List<Store> storeList = new ArrayList<>();
    public List<MoreStoreBean.BrandStoreBean> branList = new ArrayList<>();
    private  CampaignSelectStoreAdapter myAdapter;

    protected String userId;
    private String storeName ;
    protected  String storeId;
    private String compId;
    //选择多个店铺的品牌位置
    private int brandPostion;
    //品牌logo
    private String brandLogo;

    private String storeIdArray ;
    private String storeNameArray ;
    private String brandLogoCoupon;
    private String isShowOneStore;
    private  boolean isCampaignAndCouponStore = false;  //优惠券店铺
    private  String storeNameArrayCoupon;
    private  String storeIdArrayCoupon;
    private String storeIdCouponType;

    private boolean isStartDate;

    protected TimePickerView pvCustomTime;

    private int getValidDay  = 0 ;
    public String getValidDayStr ;
    private String validDaysStr = "";
    public String getValidDayStr2 ;
    private String validDaysStr2 = "";
    private int couponDateDay  = 1 ;

    private int curPosition = 0;
    protected CampaignCoupon campaignCoupon = new CampaignCoupon();
//    protected  List<CampaignCoupon> campaignCouponList = new ArrayList<>();

    private  String isNextTwoAndThree;

    protected  String couponStoreSelectType;

    protected  String expireEndType = "A";  //A 按固定日期  R1 按固定时长
    protected  String expireStartType = "A";
    private String  isShowForwardStore = "YES";  //默认第二步转发者跳转，否则"NO" 第三步 接受着

    private CampaignNewNextTwoActivity campaignNewNextTwoActivity =null;
    private CampaignNewNextThreeActivity campaignNewNextThreeActivity =null;

    protected  String startDate;
    protected  String endDate;

    protected  String startDay ;
    protected  String endDay;
    protected LinearLayout.LayoutParams linearParams;
    protected String background;

    protected String backgroundThumbnail;
    CouponMaterial material = null;
    protected  ArrayList<CampaignCoupon> campaignCoupons = new ArrayList<>();

    protected NoScrollViewPager setViewPager;

    public CampaignCouponNextSettingFragment() {
        // Required empty public constructor
    }

    public static CampaignCouponNextSettingFragment getInstance(Coupon coupon,int curPosition,String isNextTwoAndThree, NoScrollViewPager setViewPager) {
        CampaignCouponNextSettingFragment fragment = new CampaignCouponNextSettingFragment();
        fragment.coupon = coupon;
        fragment.curPosition = curPosition;
        fragment.isNextTwoAndThree = isNextTwoAndThree;
        fragment.setViewPager = setViewPager;
        return fragment;
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_campaign_coupon_next_setting;
    }

    @Override
    protected void initInjector() {
        DaggerCampaignsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignsModule(new CampaignsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.queryBrandAndStoreList(getActivity(),userId,isCampaignAndCouponStore,true);
    }

    @Override
    protected void initView() {
        userId = SPUtils.getInstance().getString("userId");

        //活动状态 暂存 可以编辑，否则其他状态不可编辑
        if(CampaignNewActivity.compaignStatus != null && !"".equals(CampaignNewActivity.compaignStatus)){
            if("S".equals(CampaignNewActivity.compaignStatus)||"NEW".equals(CampaignNewActivity.compaignStatus)){
                tvSelectStore.setVisibility(View.VISIBLE);
                llStartTime.setEnabled(true);
                llEndTime.setEnabled(true);


                edtDayDate.setEnabled(true);
                edtDayDate2.setEnabled(true);

                ivRightAdd.setEnabled(true);
                ivRightAdd.setEnabled(true);
                ivRightAdd2.setEnabled(true);
                ivRightAdd2.setEnabled(true);
                /*if("YES".equals(isNextTwoAndThree)){
                    campaignNewNextTwoActivity = (CampaignNewNextTwoActivity)getActivity();
                    campaignNewNextTwoActivity.fab.setVisibility(View.VISIBLE);
                }else {
                    campaignNewNextThreeActivity = (CampaignNewNextThreeActivity)getActivity();
                    campaignNewNextThreeActivity.fab.setVisibility(View.VISIBLE);
                }*/

                llLeftDates.setEnabled(true);
                llRight.setEnabled(true);


                ivLeftSubtract.setEnabled(true);
                ivLeftSubtract2.setEnabled(true);
                iv_num_left_subtract.setEnabled(true);
                iv_num_right_add.setEnabled(true);
                edt_coupon_num.setEnabled(true);
            }else {
                tvSelectStore.setVisibility(View.GONE);
                llStartTime.setEnabled(false);
                llEndTime.setEnabled(false);
                ivLeftSubtract.setEnabled(false);
                ivLeftSubtract2.setEnabled(false);
                iv_num_left_subtract.setEnabled(false);
                iv_num_right_add.setEnabled(false);
                edt_coupon_num.setEnabled(false);

                edtDayDate.setEnabled(false);
                edtDayDate2.setEnabled(false);

                ivRightAdd.setEnabled(false);
                ivRightAdd.setEnabled(false);
                ivRightAdd2.setEnabled(false);
                ivRightAdd2.setEnabled(false);
                llLeftDates.setEnabled(false);
                llRight.setEnabled(false);
                /*if("YES".equals(isNextTwoAndThree)){
                    campaignNewNextTwoActivity = (CampaignNewNextTwoActivity)getActivity();
                    campaignNewNextTwoActivity.fab.setVisibility(View.GONE);
                }else {
                    campaignNewNextThreeActivity = (CampaignNewNextThreeActivity)getActivity();
                    campaignNewNextThreeActivity.fab.setVisibility(View.GONE);
                }*/

            }
        }


        /*if("YES".equals(isNextTwoAndThree)){
            campaignNewNextTwoActivity = (CampaignNewNextTwoActivity)getActivity();
            //设置高度

            brandLogo = SPUtils.getInstance().getString("brandLogoCouponTwo");
            storeIdArray= SPUtils.getInstance().getString("storeIdArrayCouponTwo");
            storeNameArray= SPUtils.getInstance().getString("storeNameArrayCouponTwo");

            brandLogoCoupon = SPUtils.getInstance().getString("brandLogoCouponTwo");

            brandPostion = SPUtils.getInstance().getInt("brandPostionCouponTwo");

            storeIdCouponType = SPUtils.getInstance().getString("storeIdCouponTypeTwo");

            if("S".equals(CampaignNewActivity.compaignStatus)){
                storeIdArray = coupon.getStoreIds();
                storeNameArray = coupon.getStoreNames();
            }else if( "NEW".equals(CampaignNewActivity.compaignStatus)){


            }
        }else {
            brandLogo = SPUtils.getInstance().getString("brandLogoCouponThree");
            storeIdArray= SPUtils.getInstance().getString("storeIdArrayCouponThree");
            storeNameArray= SPUtils.getInstance().getString("storeNameArrayCouponThree");


            brandPostion = SPUtils.getInstance().getInt("brandPostionCouponThree");
            if("S".equals(CampaignNewActivity.compaignStatus)){
                storeIdArray = coupon.getStoreIds();
                storeNameArray = coupon.getStoreNames();
            }else if( "NEW".equals(CampaignNewActivity.compaignStatus)){
            }
            storeIdCouponType = SPUtils.getInstance().getString("storeIdCouponTypeThree");
            campaignNewNextThreeActivity = (CampaignNewNextThreeActivity)getActivity();

        }
        isShowForwardStore = SPUtils.getInstance().getString("isShowForwardStore");*/


        if("YES".equals(isNextTwoAndThree)){
            campaignNewNextTwoActivity = (CampaignNewNextTwoActivity)getActivity();
        }else {
            campaignNewNextThreeActivity = (CampaignNewNextThreeActivity)getActivity();
        }


        if((storeIdArray !=null && storeIdArray.length()>0)){
            storeId = storeIdArray;
        }


        if(storeNameArray !=null &&storeNameArray.length()>0){
            storeName = storeNameArray;

        }

        initDatePicker();
        setDateAndDurColor();
        //默认按固定日期标签
        llDate.setVisibility(View.VISIBLE);
        llDays.setVisibility(View.GONE);

        edtDayDate.setSelection(edtDayDate.getText().toString().length());
        edtDayDate2.setSelection(edtDayDate2.getText().toString().length());

        //从编辑页面进入
        if(coupon != null){
            material = coupon.getMaterial();
            if(material != null){
                background = material.getBackground();
                backgroundThumbnail = material.getBackgroundThumbnail();
                campaignCoupon.setBackground(background);
                campaignCoupon.setBackgroundThumbnail(backgroundThumbnail);
            }
            //张数
            if(coupon.getCouponQnt() != null && coupon.getCouponQnt().intValue()>1){
                couponQnt  = String.valueOf(coupon.getCouponQnt().intValue());
                iv_num_left_subtract.setBackgroundResource(R.drawable.ic_blue_subtracted);
            }else {
                couponQnt = "1";
                iv_num_left_subtract.setBackgroundResource(R.drawable.ic_gray_unsubtract);
            }
            edt_coupon_num.setText(couponQnt);

            String couponName = coupon.getName();
            startDate = coupon.getExpireStartDate();
            endDate = coupon.getExpireEndDate();
            startDay = coupon.getExpireStartDay();
            endDay = coupon.getExpireEndDay();
            tvCouponName.setText(couponName!=null?couponName:"");

            expireEndType = coupon.getExpireEndType();

            expireStartType = coupon.getExpireStartType();

            if(expireEndType!=null && "A".equals(expireEndType)){
                if(startDate!=null && !"".equals(startDate)){
                    Date date = DateUtils.parse(startDate,"yyyy-MM-dd");
                    tvStartTime.setText(DateUtils.formatDate(date,"yyyy-MM-dd"));
                }else {
                    tvStartTime.setText("");
                }

                if(endDate!=null && !"".equals(endDate)){
                    Date date = DateUtils.parse(endDate,"yyyy-MM-dd");
                    tvEndTime.setText(DateUtils.formatDate(date,"yyyy-MM-dd"));
                }else {
                    tvEndTime.setText("");
                }

                setDateVisible();
            }else   if(expireEndType!=null && "R1".equals(expireEndType)){
                tvValidDays.setText(startDay!=null &&!"0".equals(startDay)?startDay:"当");
                edtDayDate.setText(startDay!=null &&!"0".equals(startDay)?startDay:"0");
                tvCouponDays.setText(endDay!=null &&!"0".equals(endDay)?endDay:"1");
                edtDayDate2.setText(endDay!=null &&!"0".equals(endDay)?endDay:"1");
                setDaysVisible();
                int startDayNum=0;
                int endDayNum=1;
                if(startDay!=null &&!"0".equals(startDay)){
                    startDayNum = Integer.parseInt(startDay);

                }else {
                    startDayNum = 0;
                }
                if( startDayNum <= 0){
                    tvValidDays.setText("当");
                    ivLeftSubtract.setBackgroundResource(R.drawable.ic_gray_unsubtract);
                }else {
                    tvValidDays.setText(startDayNum+"");
                    ivLeftSubtract.setBackgroundResource(R.drawable.ic_blue_subtracted);
                }

                if(endDay!=null &&!"0".equals(endDay)){
                    endDayNum = Integer.parseInt(endDay);

                }else {
                    endDayNum = 0;
                }
                if( endDayNum <= 1){
                    tvCouponDays.setText("1");
                    ivLeftSubtract2.setBackgroundResource(R.drawable.ic_gray_unsubtract);
                }else {
                    tvCouponDays.setText(couponDateDay+"");
                    ivLeftSubtract2.setBackgroundResource(R.drawable.ic_blue_subtracted);
                }
            }else {
                expireEndType = "A";
                expireStartType = "A";
                setDateVisible();

                if("S".equals(CampaignNewActivity.compaignStatus)||"NEW".equals(CampaignNewActivity.compaignStatus)){

                }else {
                    llLeftDates.setEnabled(false);
                    llRight.setEnabled(false);
                }
            }

            storeIdCouponType = coupon.getStoreSelectType();
            if(storeIdCouponType == null || "".equals(storeIdCouponType)){
                storeIdCouponType = "H";
                storeName = coupon.getStoreNames();
                storeId = coupon.getStoreIds();
            }else {
                storeId = "";
            }
        }
/*
        //设置viewpager高度
        linearParams=(LinearLayout.LayoutParams) setViewPager.getLayoutParams();
        linearParams.height = 2150;
        setViewPager.setLayoutParams(linearParams);*/
      /*  if("YES".equals(isNextTwoAndThree)){
            if("A".equals(expireEndType)){  //固定日期
                linearParams=(LinearLayout.LayoutParams) setViewPager.getLayoutParams();
                linearParams.height = 1600;
                setViewPager.setLayoutParams(linearParams);
            }else if("R1".equals(expireEndType)){  //固定时长
                linearParams=(LinearLayout.LayoutParams) setViewPager.getLayoutParams();
                linearParams.height = 2200;
                setViewPager.setLayoutParams(linearParams);

            }

        }else {
            linearParams=(LinearLayout.LayoutParams) setViewPager.getLayoutParams();
            linearParams.height = 2200;
            setViewPager.setLayoutParams(linearParams);
        }*/

        if(storeIdCouponType !=null && !"".equals(storeIdCouponType)){  //若选择单个店铺或多个店铺  为空

            if("A".equals(storeIdCouponType)){

                tvStoreNames.setText("#全部店铺");
            }else if("D".equals(storeIdCouponType)){
                tvStoreNames.setText("#全部自营");
            }else if("J".equals(storeIdCouponType)){
                tvStoreNames.setText("#全部加盟");
            }else if("H".equals(storeIdCouponType)){  //H为自定义
                if(storeName != null && !"".equals(storeName)){
                    tvStoreNames.setText(storeName);
                }else {
                    storeId = "";
                    storeName = "";
                    storeIdCouponType = "A";
                    tvStoreNames.setText("#全部店铺");
                }

            }else {
                storeName = "#全部店铺";
                storeId = "";
                storeIdCouponType = "A";
                tvStoreNames.setText("#全部店铺");
            }
        }else { //否则自定义 选择
            if("S".equals(CampaignNewActivity.compaignStatus)){
                tvStoreNames.setText(storeName);
            }else {
                storeIdCouponType = "A";
                tvStoreNames.setText("#全部店铺");
            }
        }

        brandLogo = SPUtils.getInstance().getString("brandLogo");
        setBrandLogo();

        startDate = tvStartTime.getText().toString().trim();
        endDate = tvEndTime.getText().toString().trim();

        startDay = edtDayDate.getText().toString().trim();
        endDay = edtDayDate2.getText().toString().trim();
        couponQnt = edt_coupon_num.getText().toString().trim();
//        campaignCoupon.setId(coupon.getId());
        campaignCoupon.setCouponId(coupon.getId());
        campaignCoupon.setCouponName(coupon.getName());

        campaignCoupon.setObject(coupon.getObject());
        campaignCoupon.setDiscount(coupon.getDiscount());
        campaignCoupon.setMoney(coupon.getMoney());
        campaignCoupon.setNoDate(coupon.getNoDate());
        campaignCoupon.setNoItem(coupon.getNoItem());
        campaignCoupon.setDateCode(coupon.getDateCode());

        campaignCoupon.setExpireStartType(coupon.getExpireStartType());
        campaignCoupon.setExpireEndType(coupon.getExpireEndType());
        campaignCoupon.setOther(coupon.getOther());
        campaignCoupon.setLimit(coupon.getLimit());
        campaignCoupon.setType(coupon.getType());
        campaignCoupon.setTimePeriod(coupon.getTimePeriod());


        if(storeIdCouponType!=null && !"".equals(storeIdCouponType)){
            if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                campaignCoupon.setStoreStr("");
                campaignCoupon.setStoreSelectType(storeIdCouponType);
            }else if("H".equals(storeIdCouponType)){
                campaignCoupon.setStoreStr(storeId);
                campaignCoupon.setStoreSelectType("");
            }
        }else {
            campaignCoupon.setStoreStr(storeId);
            campaignCoupon.setStoreSelectType("");
        }
        campaignCoupon.setExpireStartType(expireEndType);
        campaignCoupon.setExpireEndType(expireEndType);
        if("A".equals(expireEndType)){
            campaignCoupon.setExpireStartDate(startDate!=null && !"".equals(startDate)?startDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()):"");
            campaignCoupon.setExpireEndDate(endDate!=null&& !"".equals(endDate)?endDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()):"");
            campaignCoupon.setExpireStartDay("");
            campaignCoupon.setExpireEndDay("");
        }else if("R1".equals(expireEndType)){
            campaignCoupon.setExpireStartDay(startDay);
            campaignCoupon.setExpireEndDay(endDay);
            campaignCoupon.setExpireStartDate("");
            campaignCoupon.setExpireEndDate("");

        }else {
            expireEndType = "A";
            tvValidDays.setText(startDay!=null &&!"0".equals(startDay)?startDay:"当");
            edtDayDate.setText(startDay!=null &&!"0".equals(startDay)?startDay:"0");
            tvCouponDays.setText(endDay!=null &&!"0".equals(endDay)?endDay:"1");
            edtDayDate2.setText(endDay!=null &&!"0".equals(endDay)?endDay:"1");
        }

        if("YES".equals(isNextTwoAndThree)){  //第二步进来
            if(campaignNewNextTwoActivity.campaignCouponList!=null && campaignNewNextTwoActivity.campaignCouponList.size()>0){
                for(int i =0;i<  campaignNewNextTwoActivity.campaignCouponList.size();i++){
                    CampaignCoupon campaignCoupon1 = campaignNewNextTwoActivity.campaignCouponList.get(i);
                    String couponId1 =String.valueOf(campaignCoupon1.getCouponId());
                    String couponId2 =String.valueOf(campaignCoupon.getCouponId());
                    String isPosition = curPosition+"";
                    String isPosition2 = i+"";
                    if(couponId1!=null && couponId2 !=null &&  isPosition.equals(isPosition2)){
                        if(couponId1.equals(couponId2)&& isPosition.equals(isPosition2)){
                            campaignCoupon1 = campaignCoupon;
                        }
                    }
                }
            }
        }else {
            if(campaignNewNextThreeActivity.campaignCouponList!=null && campaignNewNextThreeActivity.campaignCouponList.size()>0){
                for(int i =0;i<  campaignNewNextThreeActivity.campaignCouponList.size();i++){
                    CampaignCoupon campaignCoupon1 = campaignNewNextThreeActivity.campaignCouponList.get(i);
                    String couponId1 =String.valueOf(campaignCoupon1.getCouponId());
                    String couponId2 =String.valueOf(campaignCoupon.getCouponId());
                    String isPosition = curPosition+"";
                    String isPosition2 = i+"";
                    if(couponId1!=null && couponId2 !=null &&  isPosition.equals(isPosition2)){
                        if(couponId1.equals(couponId2)&& isPosition.equals(isPosition2)){
                            campaignCoupon1 = campaignCoupon;
                        }
                    }
                }
            }
        }

        edt_coupon_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                couponQnt = edt_coupon_num.getText().toString().trim();
                startDate = tvStartTime.getText().toString().trim();
                endDate = tvEndTime.getText().toString().trim();
                startDay = edtDayDate.getText().toString().trim();
                endDay = edtDayDate2.getText().toString().trim();
                getCouponQntData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvStoreNames.getTextView().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                couponQnt = edt_coupon_num.getText().toString().trim();
                startDate = tvStartTime.getText().toString().trim();
                endDate = tvEndTime.getText().toString().trim();
                startDay = edtDayDate.getText().toString().trim();
                endDay = edtDayDate2.getText().toString().trim();
                setCouponSelectStore();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtDayDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                couponQnt = edt_coupon_num.getText().toString().trim();
                startDate = tvStartTime.getText().toString().trim();
                endDate = tvEndTime.getText().toString().trim();
                startDay = edtDayDate.getText().toString().trim();
                endDay = edtDayDate2.getText().toString().trim();
                edtDayDate.setSelection(edtDayDate.getText().toString().trim().length());
                if("R1".equals(expireEndType)){
                    getDaysData();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtDayDate2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                couponQnt = edt_coupon_num.getText().toString().trim();
                startDate = tvStartTime.getText().toString().trim();
                endDate = tvEndTime.getText().toString().trim();
                startDay = edtDayDate.getText().toString().trim();
                endDay = edtDayDate2.getText().toString().trim();
                edtDayDate2.setSelection(edtDayDate2.getText().toString().trim().length());
                if("R1".equals(expireEndType)){


                    getDaysData();
                }else {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvStartTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                couponQnt = edt_coupon_num.getText().toString().trim();
                startDate = tvStartTime.getText().toString().trim();
                endDate = tvEndTime.getText().toString().trim();
                if("A".equals(expireEndType)){

                    getDateData();
                }else {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvEndTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                startDate = tvStartTime.getText().toString().trim();
                endDate = tvEndTime.getText().toString().trim();
                couponQnt = edt_coupon_num.getText().toString().trim();
                if("A".equals(expireEndType)){
//                    campaignCoupon.setExpireStartDate(startDate);
//                    campaignCoupon.setExpireEndDate(endDate);
                    campaignCoupon.setStoreSelectType(storeIdCouponType);

                    getDateData();
                }else {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setEdtDayDate();
        edtDayDate.setSelection(edtDayDate.getText().length());
        edtDayDate2.setSelection(edtDayDate2.getText().length());
    }

    private void getCouponQntData(){
        setUpdateCoupon();

        if("YES".equals(isNextTwoAndThree)) { //第二步优惠券店铺信息更新
            if(campaignNewNextTwoActivity.campaignCouponList!=null && campaignNewNextTwoActivity.campaignCouponList.size()>0){
                for (int i=0;i< campaignNewNextTwoActivity.campaignCouponList.size();i++) {
                    CampaignCoupon campaignCoupon = campaignNewNextTwoActivity.campaignCouponList.get(i);
                    String couponId = String.valueOf(campaignCoupon.getCouponId());
                    String couponId2 = String.valueOf(coupon.getId());
                    if (couponId != null && couponId2 != null) {
                        String isSort2 = curPosition +"";
                        String isSort = i+"";
                        if (couponId.equals(couponId2) &&  isSort.equals(isSort2)) {
                            campaignCoupon.setBackground(background);
                            campaignCoupon.setBackgroundThumbnail(backgroundThumbnail);
                            if(couponQnt!= null && !"".equals(couponQnt) && !"0".equals(couponQnt)){
                                campaignCoupon.setCouponQnt(Integer.parseInt(couponQnt));  //新增张数 2019-05-07
                            }else {
                                campaignCoupon.setCouponQnt(1);
                            }

                            if(storeIdCouponType!=null && !"".equals(storeIdCouponType)){
                                if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                                    campaignCoupon.setStoreStr("");
                                    campaignCoupon.setStoreSelectType(storeIdCouponType);
                                }else if("H".equals(storeIdCouponType)){
                                    campaignCoupon.setStoreStr(storeId);
                                    campaignCoupon.setStoreSelectType("");
                                }
                            }else {
                                campaignCoupon.setStoreStr(storeId);
                            }
                            campaignCoupon.setObject(coupon.getObject());

                            if("A".equals(expireEndType)){
                                campaignCoupon.setExpireEndDay("");
                                campaignCoupon.setExpireStartDay("");
                                campaignCoupon.setExpireStartDate(startDate!=null && !"".equals(startDate)?startDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()):"");
                                campaignCoupon.setExpireEndDate(endDate!=null&& !"".equals(endDate)?endDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()):"");
                            }else {
                                campaignCoupon.setExpireEndDay(endDay != null && !"".equals(endDay)?endDay:"");
                                campaignCoupon.setExpireStartDay(startDay != null && !"".equals(startDay)?startDay:"");
                                campaignCoupon.setExpireStartDate("");
                                campaignCoupon.setExpireEndDate("");
                            }
                            campaignCoupon.setExpireEndDay(endDay != null && !"".equals(endDay)?endDay:"");
                            campaignCoupon.setExpireStartDay(startDay != null && !"".equals(startDay)?startDay:"");

                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setId(coupon.getId());
                            campaignCoupon.setExpireStartType(expireEndType);
                            campaignCoupon.setExpireEndType(expireEndType);
                            campaignCoupon.setDiscount(coupon.getDiscount());
                            campaignCoupon.setMoney(coupon.getMoney());
                            campaignCoupon.setNoDate(coupon.getNoDate());
                            campaignCoupon.setNoItem(coupon.getNoItem());
                            campaignCoupon.setDateCode(coupon.getDateCode());
                            campaignCoupon.setOther(coupon.getOther());
                            campaignCoupon.setLimit(coupon.getLimit());
                            campaignCoupon.setType(coupon.getType());
                            campaignCoupon.setTimePeriod(coupon.getTimePeriod());
                            campaignCoupon.setStores(storeList);
                        }
                    }
                }


            }
        }else {
            if(campaignNewNextThreeActivity.campaignCouponList!=null && campaignNewNextThreeActivity.campaignCouponList.size()>0){
                for (int i=0;i< campaignNewNextThreeActivity.campaignCouponList.size();i++) {
                    CampaignCoupon campaignCoupon = campaignNewNextThreeActivity.campaignCouponList.get(i);
                    String couponId = String.valueOf(campaignCoupon.getCouponId());
                    String couponId2 = String.valueOf(coupon.getId());
                    if (couponId != null && couponId2 != null) {
                        String isSort2 = curPosition +"";
                        String isSort = i+"";
                        if (couponId.equals(couponId2) &&  isSort.equals(isSort2)) {
                            campaignCoupon.setBackground(background);
                            campaignCoupon.setBackgroundThumbnail(backgroundThumbnail);
                            if(couponQnt!= null && !"".equals(couponQnt) && !"0".equals(couponQnt)){
                                campaignCoupon.setCouponQnt(Integer.parseInt(couponQnt));  //新增张数 2019-05-07
                            }else {
                                campaignCoupon.setCouponQnt(1);
                            }
                            if(storeIdCouponType!=null && !"".equals(storeIdCouponType)){
                                if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                                    campaignCoupon.setStoreStr("");
                                    campaignCoupon.setStoreSelectType(storeIdCouponType);
                                }else if("H".equals(storeIdCouponType)){
                                    campaignCoupon.setStoreStr(storeId);
                                    campaignCoupon.setStoreSelectType("");
                                }
                            }else {
                                campaignCoupon.setStoreStr(storeId);
                            }
                            campaignCoupon.setObject(coupon.getObject());

                            if("A".equals(expireEndType)){
                                campaignCoupon.setExpireEndDay("");
                                campaignCoupon.setExpireStartDay("");
                                campaignCoupon.setExpireStartDate(startDate!=null && !"".equals(startDate)?startDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()):"");
                                campaignCoupon.setExpireEndDate(endDate!=null&& !"".equals(endDate)?endDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()):"");
                            }else {
                                campaignCoupon.setExpireEndDay(endDay != null && !"".equals(endDay)?endDay:"");
                                campaignCoupon.setExpireStartDay(startDay != null && !"".equals(startDay)?startDay:"");
                                campaignCoupon.setExpireStartDate("");
                                campaignCoupon.setExpireEndDate("");
                            }
                            campaignCoupon.setExpireEndDay(endDay != null && !"".equals(endDay)?endDay:"");
                            campaignCoupon.setExpireStartDay(startDay != null && !"".equals(startDay)?startDay:"");


                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setId(coupon.getId());
                            campaignCoupon.setExpireStartType(expireEndType);
                            campaignCoupon.setExpireEndType(expireEndType);
                            campaignCoupon.setDiscount(coupon.getDiscount());
                            campaignCoupon.setMoney(coupon.getMoney());
                            campaignCoupon.setNoDate(coupon.getNoDate());
                            campaignCoupon.setNoItem(coupon.getNoItem());
                            campaignCoupon.setDateCode(coupon.getDateCode());
                            campaignCoupon.setOther(coupon.getOther());
                            campaignCoupon.setLimit(coupon.getLimit());
                            campaignCoupon.setType(coupon.getType());
                            campaignCoupon.setTimePeriod(coupon.getTimePeriod());
                            campaignCoupon.setStores(storeList);
                        }
                    }
                }


            }
        }



    }

    private void setEdtDayDate(){
        edtDayDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getValidDayStr = edtDayDate.getText().toString().trim();

                if(getValidDayStr != null && !"".equals(getValidDayStr)){
                    getValidDay = Integer.parseInt(getValidDayStr);

                }else {
                    getValidDay = 0;
                }

                if( getValidDay >99){
                    ToastUtils.showShort("最高有效为99天！");
                    getValidDay = 99;
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

            }
        });

        edtDayDate2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getValidDayStr2 = edtDayDate2.getText().toString().trim();

                if(getValidDayStr2 != null && !"".equals(getValidDayStr2)){
                    if("0".equals(getValidDayStr2)){
                        getValidDayStr2 = "1";
                    }
                    couponDateDay = Integer.parseInt(getValidDayStr2);
                }else {
                    couponDateDay = 1;
                }

                if(couponDateDay>=1){
                    validDaysStr2 = couponDateDay+"";
                    ivLeftSubtract.setBackgroundResource(R.drawable.ic_blue_subtracted);
                    tvCouponDays.setText(validDaysStr2);
                }else {
                    validDaysStr2 = "1";
                    ivLeftSubtract.setBackgroundResource(R.drawable.ic_gray_unsubtract);
                    tvCouponDays.setText(validDaysStr2);
                }

            }
        });

    }

    private void setUpdateCoupon(){

        if("YES".equals(isNextTwoAndThree)) { //第二步优惠券店铺信息更新
            if(campaignNewNextTwoActivity.couponList != null && campaignNewNextTwoActivity.couponList.size()>0){
                for(int i=0;i<campaignNewNextTwoActivity.couponList.size();i++){
                    Coupon coupon1  = campaignNewNextTwoActivity.couponList.get(i);
                    String couponId = String.valueOf(coupon1.getId());
                    String id = String.valueOf(coupon.getId());
                    String isSort2 = curPosition +"";
                    String isSort = i+"";
                    if(couponId.equals(id) && isSort.equals(isSort2)){
                        if(couponQnt!= null && !"".equals(couponQnt) && !"0".equals(couponQnt)){
                            coupon1.setCouponQnt(Integer.parseInt(couponQnt));  //新增张数 2019-05-07
                        }else {
                            coupon1.setCouponQnt(1);
                        }

                        if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                            coupon1.setStoreSelectType(storeIdCouponType);
                        }else {
                            coupon1.setStoreSelectType("");
                            coupon1.setStoreIds(storeId);
                            coupon1.setStoreNames(storeName);
                        }
                        coupon1.setExpireStartType(expireEndType);
                        coupon1.setExpireEndType(expireEndType);
                        if("A".equals(expireEndType)){
                            coupon1.setExpireStartDate(startDate!=null && !"".equals(startDate)?startDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()):"");
                            coupon1.setExpireEndDate(endDate!=null&& !"".equals(endDate)?endDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()):"");
                            coupon1.setExpireStartDay("");
                            coupon1.setExpireEndDay("");
                        }else if("R1".equals(expireEndType)){
                            coupon1.setExpireStartDay(startDay != null && !"".equals(startDay)?startDay:"");
                            coupon1.setExpireEndDay(endDay != null && !"".equals(endDay)?endDay:"");
                            coupon1.setExpireStartDate("");
                            coupon1.setExpireEndDate("");

                        }
                    }
                }
            }
        }else {   //第三步优惠券店铺选择信息更新
            if(campaignNewNextThreeActivity.couponList != null && campaignNewNextThreeActivity.couponList.size()>0){
                for(int i=0;i<campaignNewNextThreeActivity.couponList.size();i++){
                    Coupon coupon1  = campaignNewNextThreeActivity.couponList.get(i);
                    String couponId = String.valueOf(coupon1.getId());
                    String id = String.valueOf(coupon.getId());
                    String isSort2 = curPosition +"";
                    String isSort = i+"";
                    if(couponId.equals(id) && isSort.equals(isSort2)){
                        if(couponQnt!= null && !"".equals(couponQnt) && !"0".equals(couponQnt)){
                            coupon1.setCouponQnt(Integer.parseInt(couponQnt));  //新增张数 2019-05-07
                        }else {
                            coupon1.setCouponQnt(1);
                        }
                        coupon1.setStoreIds(storeId);
                        coupon1.setStoreNames(storeName);
                        if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                            coupon1.setStoreSelectType(storeIdCouponType);
                        }else if("H".equals(storeIdCouponType)){
                            coupon1.setStoreSelectType("");
                        }
                        coupon1.setExpireStartType(expireEndType);
                        coupon1.setExpireEndType(expireEndType);
                        if("A".equals(expireEndType)){
                            coupon1.setExpireStartDate(startDate!=null && !"".equals(startDate)?startDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()):"");
                            coupon1.setExpireEndDate(endDate!=null&& !"".equals(endDate)?endDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()):"");
                            coupon1.setExpireStartDay("");
                            coupon1.setExpireEndDay("");
                        }else if("R1".equals(expireEndType)){
                            coupon1.setExpireStartDay(startDay != null && !"".equals(startDay)?startDay:"");
                            coupon1.setExpireEndDay(endDay != null && !"".equals(endDay)?endDay:"");
                            coupon1.setExpireStartDate("");
                            coupon1.setExpireEndDate("");

                        }
                    }
                }
            }
        }



    }

    private void setCouponSelectStore(){
        if(storeId != null && storeId.contains(",")){
            storeId = storeId.replace(",","-");
        }
        setUpdateCoupon();
        if("YES".equals(isNextTwoAndThree)){ //第二步
            if(campaignNewNextTwoActivity.campaignCouponList!=null && campaignNewNextTwoActivity.campaignCouponList.size()>0){
                for (int i=0;i< campaignNewNextTwoActivity.campaignCouponList.size();i++) {
                    CampaignCoupon campaignCoupon1 = campaignNewNextTwoActivity.campaignCouponList.get(i);
                    String couponName = campaignCoupon1.getCouponName();
                    String name = campaignCoupon.getCouponName();
                    String isSort2 = curPosition +"";
                    String isSort = i+"";
                    if(couponName.equals(name)&& isSort.equals(isSort2)){
                        if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                            campaignCoupon1.setStoreStr("");
                            campaignCoupon1.setStoreSelectType(storeIdCouponType);
                        }else if("H".equals(storeIdCouponType)){
                            campaignCoupon1.setStoreStr(storeId);
                            campaignCoupon1.setStoreSelectType("");
                        }else {
                            campaignCoupon1.setStoreStr(storeId);
                            campaignCoupon1.setStoreSelectType("");
                        }

                        campaignCoupon1.setBackground(background);
                        campaignCoupon1.setBackgroundThumbnail(backgroundThumbnail);
                        if(couponQnt!= null && !"".equals(couponQnt) && !"0".equals(couponQnt)){
                            campaignCoupon1.setCouponQnt(Integer.parseInt(couponQnt));  //新增张数 2019-05-07
                        }else {
                            campaignCoupon1.setCouponQnt(1);
                        }
                    }
                }
            }else {
                campaignNewNextTwoActivity.campaignCouponList.add(campaignCoupon);
            }
        }else {     //第三步
            if(campaignNewNextThreeActivity.campaignCouponList!=null && campaignNewNextThreeActivity.campaignCouponList.size()>0){
                for (int i=0;i< campaignNewNextThreeActivity.campaignCouponList.size();i++) {
                    CampaignCoupon campaignCoupon1 = campaignNewNextThreeActivity.campaignCouponList.get(i);
                    String couponName = campaignCoupon1.getCouponName();
                    String name = campaignCoupon.getCouponName();
                    String isSort2 = curPosition +"";
                    String isSort = i+"";
                    if(couponName.equals(name)&& isSort.equals(isSort2)){
                        if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                            campaignCoupon1.setStoreStr("");
                            campaignCoupon1.setStoreSelectType(storeIdCouponType);
                        }else if("H".equals(storeIdCouponType)){
                            campaignCoupon1.setStoreStr(storeId);
                            campaignCoupon1.setStoreSelectType("");
                        }else {
                            campaignCoupon1.setStoreStr(storeId);
                            campaignCoupon1.setStoreSelectType("");
                        }
                        campaignCoupon1.setBackground(background);
                        campaignCoupon1.setBackgroundThumbnail(backgroundThumbnail);
                        if(couponQnt!= null && !"".equals(couponQnt) && !"0".equals(couponQnt)){
                            campaignCoupon1.setCouponQnt(Integer.parseInt(couponQnt));  //新增张数 2019-05-07
                        }else {
                            campaignCoupon1.setCouponQnt(1);
                        }
                    }
                }
            }else {
                campaignNewNextThreeActivity.campaignCouponList.add(campaignCoupon);
            }
        }

    }

    private void getDateData() {
        setUpdateCoupon();
        if ("YES".equals(isNextTwoAndThree)) {
            if (campaignNewNextTwoActivity.campaignCouponList != null && campaignNewNextTwoActivity.campaignCouponList.size() > 0) {
                for (int i=0;i< campaignNewNextTwoActivity.campaignCouponList.size();i++) {
                    CampaignCoupon campaignCoupon = campaignNewNextTwoActivity.campaignCouponList.get(i);
                    String couponId = String.valueOf(campaignCoupon.getCouponId());
                    String couponId2 = String.valueOf(coupon.getId());
                    if (couponId != null && couponId2 != null) {
                        String isSort2 = curPosition +"";
                        String isSort = i+"";
                        if (couponId.equals(couponId2) &&  isSort.equals(isSort2)) {
                            campaignCoupon.setBackground(background);
                            campaignCoupon.setBackgroundThumbnail(backgroundThumbnail);
                            if(couponQnt!= null && !"".equals(couponQnt) && !"0".equals(couponQnt)){
                                campaignCoupon.setCouponQnt(Integer.parseInt(couponQnt));  //新增张数 2019-05-07
                            }else {
                                campaignCoupon.setCouponQnt(1);
                            }  //优惠券张数
                            campaignCoupon.setExpireStartDay("");
                            campaignCoupon.setExpireEndDay("");
                            campaignCoupon.setExpireStartDate(startDate!=null && !"".equals(startDate)?startDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()):"");
                            campaignCoupon.setExpireEndDate(endDate!=null&& !"".equals(endDate)?endDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()):"");
//                                campaignCoupon.setStoreSelectType(storeIdCouponType);
                            if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                                campaignCoupon.setStoreStr("");
                                campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
                            }else {
                                campaignCoupon.setStoreStr(storeId);
                                campaignCoupon.setStoreSelectType("");
                            }

                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setCouponId(coupon.getId());
                            campaignCoupon.setExpireStartType(expireEndType);
                            campaignCoupon.setExpireEndType(expireEndType);
                            campaignCoupon.setDiscount(coupon.getDiscount());
                            campaignCoupon.setMoney(coupon.getMoney());
                            campaignCoupon.setNoDate(coupon.getNoDate());
                            campaignCoupon.setNoItem(coupon.getNoItem());
                            campaignCoupon.setDateCode(coupon.getDateCode());
                            campaignCoupon.setOther(coupon.getOther());
                            campaignCoupon.setLimit(coupon.getLimit());
                            campaignCoupon.setType(coupon.getType());
                            campaignCoupon.setTimePeriod(coupon.getTimePeriod());
                        }
                    }

                }
            } else {

                campaignCoupon.setBackground(background);
                campaignCoupon.setBackgroundThumbnail(backgroundThumbnail);
                couponQnt = edt_coupon_num.getText().toString().trim();
                if(couponQnt!= null && !"".equals(couponQnt) && !"0".equals(couponQnt)){
                    campaignCoupon.setCouponQnt(Integer.parseInt(couponQnt));  //新增张数 2019-05-07
                }else {
                    campaignCoupon.setCouponQnt(1);
                }  //优惠券张数
                campaignCoupon.setExpireStartDay("");
                campaignCoupon.setExpireEndDay("");
                campaignCoupon.setExpireStartDate(startDate!=null && !"".equals(startDate)?startDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()):"");
                campaignCoupon.setExpireEndDate(endDate!=null&& !"".equals(endDate)?endDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()):"");
//                    campaignCoupon.setStoreSelectType(storeIdCouponType);
                if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                    campaignCoupon.setStoreStr("");
                    campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
                }else if("H".equals(storeIdCouponType)){
                    campaignCoupon.setStoreStr(storeId);
                    campaignCoupon.setStoreSelectType("");
                }
                campaignCoupon.setObject(coupon.getObject());
                campaignCoupon.setCouponName(coupon.getName());
                campaignCoupon.setCouponId(coupon.getId());
                campaignCoupon.setExpireStartType(expireEndType);
                campaignCoupon.setExpireEndType(expireEndType);
                campaignCoupon.setDiscount(coupon.getDiscount());
                campaignCoupon.setMoney(coupon.getMoney());
                campaignCoupon.setNoDate(coupon.getNoDate());
                campaignCoupon.setNoItem(coupon.getNoItem());
                campaignCoupon.setDateCode(coupon.getDateCode());
                campaignCoupon.setOther(coupon.getOther());
                campaignCoupon.setLimit(coupon.getLimit());
                campaignCoupon.setType(coupon.getType());
                campaignCoupon.setTimePeriod(coupon.getTimePeriod());
                campaignNewNextTwoActivity.campaignCouponList.add(campaignCoupon);
            }

        } else {
            if (campaignNewNextThreeActivity.campaignCouponList != null && campaignNewNextThreeActivity.campaignCouponList.size() > 0) {
                for (int i=0;i< campaignNewNextThreeActivity.campaignCouponList.size();i++) {
                    CampaignCoupon campaignCoupon = campaignNewNextThreeActivity.campaignCouponList.get(i);
                    String couponId = String.valueOf(campaignCoupon.getCouponId());
                    String couponId2 = String.valueOf(coupon.getId());
                    if (couponId != null && couponId2 != null) {
                        String isSort2 = curPosition +"";
                        String isSort = i+"";
                        if (couponId.equals(couponId2) &&  isSort.equals(isSort2)) {
                            campaignCoupon.setBackground(background);
                            campaignCoupon.setBackgroundThumbnail(backgroundThumbnail);
                            couponQnt = edt_coupon_num.getText().toString().trim();
                            if(couponQnt!= null && !"".equals(couponQnt) && !"0".equals(couponQnt)){
                                campaignCoupon.setCouponQnt(Integer.parseInt(couponQnt));  //新增张数 2019-05-07
                            }else {
                                campaignCoupon.setCouponQnt(1);
                            }  //优惠券张数
                            campaignCoupon.setExpireStartDay("");
                            campaignCoupon.setExpireEndDay("");
                            campaignCoupon.setExpireStartDate(startDate!=null && !"".equals(startDate)?startDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()):"");
                            campaignCoupon.setExpireEndDate(endDate!=null&& !"".equals(endDate)?endDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()):"");
//                                campaignCoupon.setStoreSelectType(storeIdCouponType);
                            if (storeIdCouponType != null && !"".equals(storeIdCouponType)) {
                                if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                                    campaignCoupon.setStoreStr("");
                                    campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
                                }else if("H".equals(storeIdCouponType)){
                                    campaignCoupon.setStoreStr(storeId);
                                    campaignCoupon.setStoreSelectType("");
                                }
                            } else {
                                campaignCoupon.setStoreStr(storeId);
                            }
                            campaignCoupon.setObject(coupon.getObject());
                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setCouponId(coupon.getId());
                            campaignCoupon.setExpireStartType(expireEndType);
                            campaignCoupon.setExpireEndType(expireEndType);
                            campaignCoupon.setDiscount(coupon.getDiscount());
                            campaignCoupon.setMoney(coupon.getMoney());
                            campaignCoupon.setNoDate(coupon.getNoDate());
                            campaignCoupon.setNoItem(coupon.getNoItem());
                            campaignCoupon.setDateCode(coupon.getDateCode());
                            campaignCoupon.setOther(coupon.getOther());
                            campaignCoupon.setLimit(coupon.getLimit());
                            campaignCoupon.setType(coupon.getType());
                            campaignCoupon.setTimePeriod(coupon.getTimePeriod());
                        }
                    }

                }
            } else {
                campaignCoupon.setBackground(background);
                campaignCoupon.setBackgroundThumbnail(backgroundThumbnail);
                couponQnt = edt_coupon_num.getText().toString().trim();
                if(couponQnt!= null && !"".equals(couponQnt) && !"0".equals(couponQnt)){
                    campaignCoupon.setCouponQnt(Integer.parseInt(couponQnt));  //新增张数 2019-05-07
                }else {
                    campaignCoupon.setCouponQnt(1);
                }  //优惠券张数
                campaignCoupon.setExpireStartDay("");
                campaignCoupon.setExpireEndDay("");
                campaignCoupon.setExpireStartDate(startDate!=null && !"".equals(startDate)?startDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()):"");
                campaignCoupon.setExpireEndDate(endDate!=null&& !"".equals(endDate)?endDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()):"");
//                    campaignCoupon.setStoreSelectType(storeIdCouponType);
                if (storeIdCouponType != null && !"".equals(storeIdCouponType)) {
                    if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                        campaignCoupon.setStoreStr("");
                        campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
                    }else if("H".equals(storeIdCouponType)){
                        campaignCoupon.setStoreStr(storeId);
                        campaignCoupon.setStoreSelectType("");
                    }
                } else {
                    campaignCoupon.setStoreStr(storeId);
                }
                campaignCoupon.setObject(coupon.getObject());
                campaignCoupon.setCouponName(coupon.getName());
                campaignCoupon.setCouponId(coupon.getId());
                campaignCoupon.setExpireStartType(expireEndType);
                campaignCoupon.setExpireEndType(expireEndType);
                campaignCoupon.setDiscount(coupon.getDiscount());
                campaignCoupon.setMoney(coupon.getMoney());
                campaignCoupon.setNoDate(coupon.getNoDate());
                campaignCoupon.setNoItem(coupon.getNoItem());
                campaignCoupon.setDateCode(coupon.getDateCode());
                campaignCoupon.setOther(coupon.getOther());
                campaignCoupon.setLimit(coupon.getLimit());
                campaignCoupon.setType(coupon.getType());
                campaignCoupon.setTimePeriod(coupon.getTimePeriod());
                campaignNewNextTwoActivity.campaignCouponList.add(campaignCoupon);
            }
        }
    }
    private void getDaysData(){
        setUpdateCoupon();
        if("YES".equals(isNextTwoAndThree)){
            if(campaignNewNextTwoActivity.campaignCouponList!=null && campaignNewNextTwoActivity.campaignCouponList.size()>0){
                for (int i=0;i< campaignNewNextTwoActivity.campaignCouponList.size();i++) {
                    CampaignCoupon campaignCoupon = campaignNewNextTwoActivity.campaignCouponList.get(i);
                    String couponId = String.valueOf(campaignCoupon.getCouponId());
                    String couponId2 = String.valueOf(coupon.getId());
                    if (couponId != null && couponId2 != null) {
                        String isSort2 = curPosition +"";
                        String isSort = i+"";
                        if (couponId.equals(couponId2) &&  isSort.equals(isSort2)) {
                            campaignCoupon.setBackground(background);
                            campaignCoupon.setBackgroundThumbnail(backgroundThumbnail);
                            if(couponQnt!= null && !"".equals(couponQnt) && !"0".equals(couponQnt)){
                                campaignCoupon.setCouponQnt(Integer.parseInt(couponQnt));  //新增张数 2019-05-07
                            }else {
                                campaignCoupon.setCouponQnt(1);
                            }  //优惠券张数
                            campaignCoupon.setExpireEndDay(endDay);
                            campaignCoupon.setExpireStartDay(startDay);
//                                campaignCoupon.setStoreSelectType(storeIdCouponType);
                            if(storeIdCouponType!=null && !"".equals(storeIdCouponType)){
                                if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                                    campaignCoupon.setStoreStr("");
                                    campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
                                }else if("H".equals(storeIdCouponType)){
                                    campaignCoupon.setStoreStr(storeId);
                                    campaignCoupon.setStoreSelectType("");
                                }
                            }else {
                                campaignCoupon.setStoreStr(storeId);
                            }
                            campaignCoupon.setObject(coupon.getObject());
                            campaignCoupon.setExpireStartDate("");
                            campaignCoupon.setExpireEndDate("");
                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setCouponId(coupon.getId());
                            campaignCoupon.setExpireStartType(expireEndType);
                            campaignCoupon.setExpireEndType(expireEndType);
                            campaignCoupon.setDiscount(coupon.getDiscount());
                            campaignCoupon.setMoney(coupon.getMoney());
                            campaignCoupon.setNoDate(coupon.getNoDate());
                            campaignCoupon.setNoItem(coupon.getNoItem());
                            campaignCoupon.setDateCode(coupon.getDateCode());
                            campaignCoupon.setOther(coupon.getOther());
                            campaignCoupon.setLimit(coupon.getLimit());
                            campaignCoupon.setType(coupon.getType());
                            campaignCoupon.setTimePeriod(coupon.getTimePeriod());
                        }
                    }
                }


            }else {
                campaignCoupon.setBackground(background);
                campaignCoupon.setBackgroundThumbnail(backgroundThumbnail);
                if(couponQnt!= null && !"".equals(couponQnt) && !"0".equals(couponQnt)){
                    campaignCoupon.setCouponQnt(Integer.parseInt(couponQnt));  //新增张数 2019-05-07
                }else {
                    campaignCoupon.setCouponQnt(1);
                }  //优惠券张数
                campaignCoupon.setExpireStartDate("");
                campaignCoupon.setExpireEndDate("");
                campaignCoupon.setExpireEndDay(endDay);
                campaignCoupon.setExpireStartDay(startDay);
                campaignCoupon.setStoreSelectType(storeIdCouponType);
                if(storeIdCouponType!=null && !"".equals(storeIdCouponType)){
                    if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                        campaignCoupon.setStoreStr("");
                        campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
                    }else if("H".equals(storeIdCouponType)){
                        campaignCoupon.setStoreStr(storeId);
                        campaignCoupon.setStoreSelectType("");
                    }
                }else {
                    campaignCoupon.setStoreStr(storeId);
                }
                campaignCoupon.setObject(coupon.getObject());
                campaignCoupon.setCouponName(coupon.getName());
                campaignCoupon.setCouponId(coupon.getId());
                campaignCoupon.setExpireStartType(expireEndType);
                campaignCoupon.setExpireEndType(expireEndType);
                campaignCoupon.setDiscount(coupon.getDiscount());
                campaignCoupon.setMoney(coupon.getMoney());
                campaignCoupon.setNoDate(coupon.getNoDate());
                campaignCoupon.setNoItem(coupon.getNoItem());
                campaignCoupon.setDateCode(coupon.getDateCode());
                campaignCoupon.setOther(coupon.getOther());
                campaignCoupon.setLimit(coupon.getLimit());
                campaignCoupon.setType(coupon.getType());
                campaignCoupon.setTimePeriod(coupon.getTimePeriod());
                campaignNewNextTwoActivity.campaignCouponList.add(campaignCoupon);
            }

        }else {
            if(campaignNewNextThreeActivity.campaignCouponList!=null && campaignNewNextThreeActivity.campaignCouponList.size()>0){
                for (int i=0;i< campaignNewNextThreeActivity.campaignCouponList.size();i++) {
                    CampaignCoupon campaignCoupon = campaignNewNextThreeActivity.campaignCouponList.get(i);
                    String couponId = String.valueOf(campaignCoupon.getCouponId());
                    String couponId2 = String.valueOf(coupon.getId());
                    if (couponId != null && couponId2 != null) {
                        String isSort2 = curPosition +"";
                        String isSort = i+"";
                        if (couponId.equals(couponId2) &&  isSort.equals(isSort2)) {
                            campaignCoupon.setBackground(background);
                            campaignCoupon.setBackgroundThumbnail(backgroundThumbnail);
                            if(couponQnt!= null && !"".equals(couponQnt) && !"0".equals(couponQnt)){
                                campaignCoupon.setCouponQnt(Integer.parseInt(couponQnt));  //新增张数 2019-05-07
                            }else {
                                campaignCoupon.setCouponQnt(1);
                            }  //优惠券张数
                            campaignCoupon.setExpireEndDay(endDay);
                            campaignCoupon.setExpireStartDay(startDay);
                            if(storeIdCouponType!=null && !"".equals(storeIdCouponType)){
                                if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                                    campaignCoupon.setStoreStr("");
                                    campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
                                }else if("H".equals(storeIdCouponType)){
                                    campaignCoupon.setStoreStr(storeId);
                                    campaignCoupon.setStoreSelectType("");
                                }
                            }else {
                                campaignCoupon.setStoreStr("");
                            }
                            campaignCoupon.setObject(coupon.getObject());
                            campaignCoupon.setExpireStartDate("");
                            campaignCoupon.setExpireEndDate("");
                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setCouponId(coupon.getId());
                            campaignCoupon.setExpireStartType(expireEndType);
                            campaignCoupon.setExpireEndType(expireEndType);
                            campaignCoupon.setDiscount(coupon.getDiscount());
                            campaignCoupon.setMoney(coupon.getMoney());
                            campaignCoupon.setNoDate(coupon.getNoDate());
                            campaignCoupon.setNoItem(coupon.getNoItem());
                            campaignCoupon.setDateCode(coupon.getDateCode());
                            campaignCoupon.setOther(coupon.getOther());
                            campaignCoupon.setLimit(coupon.getLimit());
                            campaignCoupon.setType(coupon.getType());
                            campaignCoupon.setTimePeriod(coupon.getTimePeriod());
                        }
                    }
                }


            }else {
                campaignCoupon.setBackground(background);
                campaignCoupon.setBackgroundThumbnail(backgroundThumbnail);
                if(couponQnt!= null && !"".equals(couponQnt) && !"0".equals(couponQnt)){
                    campaignCoupon.setCouponQnt(Integer.parseInt(couponQnt));  //新增张数 2019-05-07
                }else {
                    campaignCoupon.setCouponQnt(1);
                }  //优惠券张数
                campaignCoupon.setExpireEndDay(endDay);
                campaignCoupon.setExpireStartDay(startDay);
                if(storeIdCouponType!=null && !"".equals(storeIdCouponType)){
                    if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                        campaignCoupon.setStoreStr("");
                        campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
                    }else if("H".equals(storeIdCouponType)){
                        campaignCoupon.setStoreStr(storeId);
                        campaignCoupon.setStoreSelectType("");
                    }
                }else {
                    campaignCoupon.setStoreStr(storeId);
                }
                campaignCoupon.setObject(coupon.getObject());
                campaignCoupon.setExpireStartDate("");
                campaignCoupon.setExpireEndDate("");
                campaignCoupon.setCouponName(coupon.getName());
                campaignCoupon.setCouponId(coupon.getId());
                campaignCoupon.setExpireStartType(expireEndType);
                campaignCoupon.setExpireEndType(expireEndType);
                campaignCoupon.setDiscount(coupon.getDiscount());
                campaignCoupon.setMoney(coupon.getMoney());
                campaignCoupon.setNoDate(coupon.getNoDate());
                campaignCoupon.setNoItem(coupon.getNoItem());
                campaignCoupon.setDateCode(coupon.getDateCode());
                campaignCoupon.setOther(coupon.getOther());
                campaignCoupon.setLimit(coupon.getLimit());
                campaignCoupon.setType(coupon.getType());
                campaignCoupon.setTimePeriod(coupon.getTimePeriod());

                campaignNewNextThreeActivity.campaignCouponList.add(campaignCoupon);
            }
        }
    }

    @Override
    protected void initData() {


    }


    private void setDateVisible(){
        llDate.setVisibility(View.VISIBLE);
        llDays.setVisibility(View.GONE);
        tvDateLabel.setTextColor(getResources().getColor(R.color.btn_color_blue));
        leftView.setBackground(getResources().getDrawable(R.color.btn_color_blue));
        tvDurLaybel.setTextColor(getResources().getColor(R.color.campaign_new_font_gray));
        rightView.setBackground(getResources().getDrawable(R.color.campaign_new_font_gray));
        leftView.setVisibility(View.VISIBLE);
        rightView.setVisibility(View.INVISIBLE);
    }

    private void setDaysVisible(){
        llDate.setVisibility(View.GONE);
        llDays.setVisibility(View.VISIBLE);
        tvDateLabel.setTextColor(getResources().getColor(R.color.campaign_new_font_gray));
        leftView.setBackground(getResources().getDrawable(R.color.campaign_new_font_gray));

        tvDurLaybel.setTextColor(getResources().getColor(R.color.btn_color_blue));
        rightView.setBackground(getResources().getDrawable(R.color.btn_color_blue));
        leftView.setVisibility(View.INVISIBLE);
        rightView.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.tv_select_shop,
            R.id.ll_left_dates,R.id.ll_right_durations,R.id.ll_start,R.id.ll_end,
           R.id.iv_left_subtract,R.id.iv_right_add,
            R.id.iv_left_subtract2,R.id.iv_right_add2,
            R.id.iv_num_left_subtract,R.id.iv_num_right_add,})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_select_shop:  //开始时间

                presenter.queryBrandAndStoreList(getActivity(),userId,isCampaignAndCouponStore,false);
                break;

            case R.id.iv_num_left_subtract: //第一个加减

                getCouponNumStr = edt_coupon_num.getText().toString().trim();

                if(getCouponNumStr != null && !"".equals(getCouponNumStr)){
                    getCouponNum = Integer.parseInt(getCouponNumStr);
                }else {
                    getCouponNum = 1;

                }
                getCouponNum--;
                if(getCouponNum<=0){
                    getCouponNum = 1;
                }

                if( getCouponNum <= 1){
                    iv_num_left_subtract.setImageResource(R.drawable.ic_gray_unsubtract);
                }else {
                    iv_num_left_subtract.setImageResource(R.drawable.ic_blue_subtracted);
                }

                edt_coupon_num.setText(getCouponNum+"");
                break;
            case R.id.iv_num_right_add:

                getCouponNumStr = edt_coupon_num.getText().toString().trim();

                if(getCouponNumStr != null && !"".equals(getCouponNumStr)){
                    getCouponNum = Integer.parseInt(getCouponNumStr);
                }else {
                    getCouponNum = 1;

                }
                getCouponNum++;

                if(getCouponNum >=1){
                    iv_num_right_add.setImageResource(R.drawable.ic_blue_added);
                }
                if(getCouponNum <= 1){
                    iv_num_left_subtract.setImageResource(R.drawable.ic_gray_unsubtract);
                }else {
                    iv_num_left_subtract.setImageResource(R.drawable.ic_blue_subtracted);
                }


                edt_coupon_num.setText(getCouponNum+"");
                break;
            case R.id.ll_left_dates:  //  //按固定日期
                expireEndType = "A";
                startDate = tvStartTime.getText().toString().trim();
                endDate = tvEndTime.getText().toString().trim();
                couponQnt = edt_coupon_num.getText().toString().trim();
                setDateVisible();
               /* linearParams=(LinearLayout.LayoutParams) setViewPager.getLayoutParams();
                linearParams.height = 1600;
                setViewPager.setLayoutParams(linearParams);*/

                getDateData();
                break;
            case R.id.ll_right_durations: //按固定时长
                expireEndType = "R1";
                startDay = edtDayDate.getText().toString().trim();
                endDay = edtDayDate2.getText().toString().trim();
                couponQnt = edt_coupon_num.getText().toString().trim();
                setDaysVisible();
               /* linearParams=(LinearLayout.LayoutParams) setViewPager.getLayoutParams();
                linearParams.height = 2200;
                setViewPager.setLayoutParams(linearParams);*/
               /* if("YES".equals(isNextTwoAndThree)){
                    CampaignNewNextTwoActivity activity = (CampaignNewNextTwoActivity)getActivity();
                    linearParams=(LinearLayout.LayoutParams) activity.setViewPager.getLayoutParams();
                    linearParams.height = 2200;
                    activity.setViewPager.setLayoutParams(linearParams);
                }else {
                    CampaignNewNextThreeActivity activity = (CampaignNewNextThreeActivity)getActivity();
                    linearParams=(LinearLayout.LayoutParams) activity.setViewPager.getLayoutParams();
                    linearParams.height = 2200;
                    activity.setViewPager.setLayoutParams(linearParams);
                }*/
/*
                if("YES".equals(isNextTwoAndThree)){
                    linearParams=(LinearLayout.LayoutParams) setViewPager.getLayoutParams();
                    if("A".equals(expireEndType)){
                        linearParams.height = 2200;
                        campaignNewNextTwoActivity.setViewPager.setLayoutParams(linearParams);
                    }else if("R1".equals(expireEndType)){  //固定时长


                        linearParams.height = 2200;
                        campaignNewNextTwoActivity.setViewPager.setLayoutParams(linearParams);

                    }
                }else {
                    linearParams=(LinearLayout.LayoutParams) setViewPager.getLayoutParams();
                    if("A".equals(expireEndType)){
                        linearParams.height = 2200;
                        campaignNewNextThreeActivity.setViewPager.setLayoutParams(linearParams);
                    }else if("R1".equals(expireEndType)){  //固定时长


                        linearParams.height = 2200;
                        campaignNewNextThreeActivity.setViewPager.setLayoutParams(linearParams);

                    }
                }*/

                getDaysData();


                break;
            case R.id.ll_start:  //开始时间
                isStartDate =true;
                pvCustomTime.show();

                break;
            case R.id.ll_end: //结束时间
                isStartDate =false;
                pvCustomTime.show();
                break;

            case R.id.iv_left_subtract: //第一个加减
                getValidDayStr = edtDayDate.getText().toString().trim();

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

                edtDayDate.setText(getValidDay+"");
                break;
            case R.id.iv_right_add:
                getValidDayStr = edtDayDate.getText().toString().trim();

                if(getValidDayStr != null && !"".equals(getValidDayStr)){
                    getValidDay = Integer.parseInt(getValidDayStr);

                }else {
                    getValidDay = 0;

                }
                getValidDay++;
                if( getValidDay >= 99){
                    ToastUtils.showShort("最高有效为99天！");
                    getValidDay = 99;
                    ivRightAdd.setBackgroundResource(R.drawable.ic_gray_add);
                }else {
                    ivRightAdd.setBackgroundResource(R.drawable.ic_blue_added);
                }
                if(getValidDay>=1){
                    ivLeftSubtract.setBackgroundResource(R.drawable.ic_blue_subtracted);
                }

                tvValidDays.setText(getValidDay+"");

                edtDayDate.setText(getValidDay+"");
                break;
            case R.id.iv_left_subtract2: //第二个加减
                getValidDayStr2 = edtDayDate2.getText().toString().trim();

                if(getValidDayStr2 != null && !"".equals(getValidDayStr2)){

                    couponDateDay = Integer.parseInt(getValidDayStr2);
                }else {
                    couponDateDay = 1;
                }
                couponDateDay--;
                if(couponDateDay<1){
                    couponDateDay = 1;
                }

                if( couponDateDay <= 1){
                    tvCouponDays.setText("1");
                    ivLeftSubtract2.setBackgroundResource(R.drawable.ic_gray_unsubtract);
                }else {
                    tvCouponDays.setText(couponDateDay+"");
                    ivLeftSubtract2.setBackgroundResource(R.drawable.ic_blue_subtracted);
                }
                edtDayDate2.setText(couponDateDay+"");

                break;
            case R.id.iv_right_add2:

                getValidDayStr2 = edtDayDate2.getText().toString().trim();

                if(getValidDayStr2 != null && !"".equals(getValidDayStr2)){
                    couponDateDay = Integer.parseInt(getValidDayStr2);
                }else {
                    couponDateDay = 0;
                }
                couponDateDay++;

                if(couponDateDay>=2){
                    ivLeftSubtract2.setBackgroundResource(R.drawable.ic_blue_subtracted);
                }
                tvCouponDays.setText(couponDateDay+"");
                edtDayDate2.setText(couponDateDay+"");
                break;

        }
    }





    private void setDateAndDurColor(){
        tvDateLabel.setTextColor(getResources().getColor(R.color.btn_color_blue));
        leftView.setBackground(getResources().getDrawable(R.color.btn_color_blue));
        tvDurLaybel.setTextColor(getResources().getColor(R.color.campaign_new_font_gray));
        rightView.setBackground(getResources().getDrawable(R.color.campaign_new_font_gray));
        rightView.setVisibility(View.INVISIBLE);
    }

    //选择多个店铺
    protected void  showPopView(Context context){


        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_campaign_select_more_store,null);
        if("YES".equals(isNextTwoAndThree)){
            CampaignNewNextTwoActivity activity = (CampaignNewNextTwoActivity)getActivity();
            popWindow= new PopWindow.Builder(getActivity())
                    .setStyle(PopWindow.PopWindowStyle.PopDown)
                    .setView(contentView)
                    .show(activity.tvTitle);
        }else {
            CampaignNewNextThreeActivity activity = (CampaignNewNextThreeActivity) getActivity();
            //创建并显示popWindow
            popWindow= new PopWindow.Builder(getActivity())
                    .setStyle(PopWindow.PopWindowStyle.PopDown)
                    .setView(contentView)
                    .show(activity.tvTitle);
        }


        RelativeLayout rlAllStore = contentView.findViewById(R.id.rl_all);
        CheckBox cbAllStore = contentView.findViewById(R.id.cb__all);
        TextView tvAllStore =contentView.findViewById(R.id.tv_all);

        RelativeLayout rlSelfStore = contentView.findViewById(R.id.rl_self_bus);
        CheckBox cbSelfStore = contentView.findViewById(R.id.cb_self_bus);
        TextView tvSelfStore =contentView.findViewById(R.id.tv_self_bus);

        RelativeLayout rlJoinStore = contentView.findViewById(R.id.rl_all_join);
        CheckBox cbJoinStore = contentView.findViewById(R.id.cb_all_join);
        TextView tvJoinStore =contentView.findViewById(R.id.tv_all_join);

        ListView lv_left = contentView.findViewById(R.id.lv_menu);
        ListView lv_right = contentView.findViewById(R.id.lv_home);
        Button reset = contentView.findViewById(R.id.reset);
        Button confirm = contentView.findViewById(R.id.confirm);






        rlAllStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                StringBuilder storeIdSb = new StringBuilder();
                StringBuilder storeNameSb = new StringBuilder();
                String storeIdArr = "";
                if(storeList!=null&& storeList.size()>0){
                    for (Store store:storeList){
                        store.setChecked(false);
                        String storeId = store.getStoreId();
                        String storeName = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";

                        storeIdSb.append(storeId).append("-");
                        storeNameSb.append(storeName);

                    }

                    myAdapter.notifyDataSetChanged();



                    storeIdCouponType = "A";
                    if(storeIdSb!=null ||!"".equals(storeIdSb)){
                        if(storeIdSb.toString().contains("-")){
                            storeIdArr = storeIdSb.substring(0,storeIdSb.length() - 1);
                        }
                    }else {

                        storeIdArr = "";
                    }
                    storeId = storeIdArr;
                    storeName = "#全部店铺";
                    tvStoreNames.setText(storeNameSb.toString());
                    if("YES".equals(isNextTwoAndThree)){
                        SPUtils.getInstance().put("storeIdCouponTypeTwo","A");
                        SPUtils.getInstance().put("storeIdArrayCouponTwo",storeIdArr);
                        SPUtils.getInstance().put("storeNameArrayCouponTwo","#全部店铺");
                    }else {
                        SPUtils.getInstance().put("storeIdCouponTypeThree","A");
                        SPUtils.getInstance().put("storeIdArrayCouponThree",storeIdArr);
                        SPUtils.getInstance().put("storeNameArrayCouponThree","#全部店铺");
                    }

                }
                cbAllStore.setChecked(true);
                cbAllStore.setButtonDrawable(R.drawable.ic_correct);
                tvAllStore.setTextColor(getResources().getColor(R.color.font_color_blue));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));
            }
        });

        rlSelfStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder storeIdSb = new StringBuilder();
                StringBuilder storeNameSb = new StringBuilder();

                String storeIdArr = "";
                if(storeList!=null&& storeList.size()>0){
                    for (Store store:storeList){
                        store.setChecked(false);
                        Integer statusQuo = store.getStatusQuo();
                        String storeId = store.getStoreId();
                        String storeName = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                        if(statusQuo!=null){
                            if(statusQuo == 1){
                                storeIdSb.append(storeId).append("-");
                                storeNameSb.append(storeName);
                            }

                        }

                    }

                    myAdapter.notifyDataSetChanged();
                    if(storeIdSb!=null ||!"".equals(storeIdSb)){
                        if(storeIdSb.toString().contains("-")){
                            storeIdArr = storeIdSb.substring(0,storeIdSb.length() - 1);
                        }

                    }else {
                        storeIdArr = "";
                    }

                    storeId = storeIdArr;
                    storeName = "#全部自营";
                    if("YES".equals(isNextTwoAndThree)){
                        SPUtils.getInstance().put("storeIdCouponTypeTwo","D");
                        SPUtils.getInstance().put("storeIdArrayCouponTwo",storeIdArr);
                        SPUtils.getInstance().put("storeNameArrayCouponTwo","#全部自营");
                    }else {
                        SPUtils.getInstance().put("storeIdCouponTypeThree","D");
                        SPUtils.getInstance().put("storeIdArrayCouponThree",storeIdArr);
                        SPUtils.getInstance().put("storeNameArrayCouponThree","#全部自营");
                    }

                }
                storeIdCouponType  = "D";
                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(true);
                cbSelfStore.setButtonDrawable(R.drawable.ic_correct);
                tvSelfStore.setTextColor(getResources().getColor(R.color.font_color_blue));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));
            }
        });
        rlJoinStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                StringBuilder storeIdSb = new StringBuilder();
                StringBuilder storeNameSb = new StringBuilder();

                String storeIdArr = "";
                if(storeList!=null&& storeList.size()>0){
                    for (Store store:storeList){

                        store.setChecked(false);
                        Integer statusQuo = store.getStatusQuo();
                        String storeId = store.getStoreId();
                        String storeName = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                        if(statusQuo!=null){
                            if(statusQuo == 2){
                                storeIdSb.append(storeId).append("-");
                                storeNameSb.append(storeName);
                            }

                        }

                    }

                    if(storeIdSb.toString()!=null ||!"".equals(storeIdSb.toString())){

                        if(storeIdSb.toString().contains("-")){
                            storeIdArr = storeIdSb.substring(0,storeIdSb.length() - 1);

                        }
                    }else {
                        storeIdArr = "";
                    }
                    storeIdCouponType  = "J";
                    storeName = "#全部加盟";
                    storeId = storeIdArr;
                    myAdapter.notifyDataSetChanged();
                    if("YES".equals(isNextTwoAndThree)){
                        SPUtils.getInstance().put("storeIdCouponTypeTwo","J");
                        SPUtils.getInstance().put("storeIdArrayCouponTwo",storeIdArr);
                        SPUtils.getInstance().put("storeNameArrayCouponTwo","#全部加盟");
                    }else {
                        SPUtils.getInstance().put("storeIdCouponTypeThree","J");
                        SPUtils.getInstance().put("storeIdArrayCouponThree",storeIdArr);
                        SPUtils.getInstance().put("storeNameArrayCouponThree","#全部加盟");
                    }


                }

                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(true);
                cbJoinStore.setButtonDrawable(R.drawable.ic_correct);
                tvJoinStore.setTextColor(getResources().getColor(R.color.font_color_blue));
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.getInstance().put("storeIdCouponType","");
                SPUtils.getInstance().put("storeIdArrayCoupon","");
                SPUtils.getInstance().put("storeNameArrayCoupon","");

                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                //自定义默认不选中
                if(storeList!=null&& storeList.size()>0){

                    for (Store store:storeList){
                        store.setChecked(false);
                    }
                    myAdapter.notifyDataSetChanged();
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder storeNameSb = new StringBuilder();
                StringBuilder storeNameSb2 = new StringBuilder();
                StringBuilder storeIdSb = new StringBuilder();
                String storeIdArr = "";
                String storeNameArr = "";

                if(storeList!=null&& storeList.size()>0){

                    if(storeIdCouponType != null && !"".equals(storeIdCouponType)){
                        if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                            //storeName 为全部店铺  自营  加盟
                            tvStoreNames.setText(storeName);
                            tvStoreNames.refreshText();
                            if("YES".equals(isNextTwoAndThree)){   //从第二步进来
                                SPUtils.getInstance().put("storeIdCouponTypeTwo",storeIdCouponType);
                                SPUtils.getInstance().put("storeIdArrayCouponTwo",storeId);
                                SPUtils.getInstance().put("storeNameArrayCouponTwo",storeName);
                                if(branList !=null && branList.size()>0){

                                    brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                                    if(brandLogo!=null){
                                        setBrandLogo();
                                    }
                                    SPUtils.getInstance().put("brandLogoCouponTwo",brandLogo);
                                }
                            }else {  //从第三步进来
                                SPUtils.getInstance().put("storeIdCouponTypeThree",storeIdCouponType);
                                SPUtils.getInstance().put("storeIdArrayCouponThree",storeId);
                                SPUtils.getInstance().put("storeNameArrayCouponThree",storeName);
                                if(branList !=null && branList.size()>0){

                                    brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                                    if(brandLogo!=null){
                                        setBrandLogo();
                                    }
                                    SPUtils.getInstance().put("brandLogoCouponThree",brandLogo);
                                }
                            }
                        }else if("H".equals(storeIdCouponType)){  //H为自定义店铺
                            for (Store store: storeList){
                                if(store!=null){
                                    if(store.isChecked()){
                                        String name = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                        storeNameSb.append(name);
                                        storeIdSb.append(store.getStoreId()).append("-");
                                    }

                                }
                            }

                            String storeIdA= storeIdSb.toString();
                            if(storeIdA!=null  && !"".equals(storeIdA)){
                                storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                            }


                            storeId = storeIdArr;
                            storeName = storeNameSb.toString();
                            if(storeIdA!=null && !"".equals(storeIdA)){
                                storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                                storeId = storeIdArr;
                                storeName = storeNameSb.toString();
                                storeIdCouponType = "";
                            }else {
                                storeId = "";
                                storeName = "#全部店铺";
                                storeIdCouponType = "A";
                            }
                            if(campaignCoupon != null){
                                campaignCoupon.setStoreStr(storeIdArr);
                                campaignCoupon.setStoreSelectType(storeIdCouponType);
                            }
                            tvStoreNames.setText(storeName);
                            tvStoreNames.refreshText();
                            if("YES".equals(isNextTwoAndThree)){   //从第二步进来
                                SPUtils.getInstance().put("storeIdCouponTypeTwo","H");
                                SPUtils.getInstance().put("storeIdArrayCouponTwo",storeId);
                                SPUtils.getInstance().put("storeNameArrayCouponTwo",storeName);

                                if(branList !=null && branList.size()>0){

                                    brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                                    if(brandLogo!=null){
                                        setBrandLogo();
                                    }
                                    SPUtils.getInstance().put("brandLogoCouponTwo",brandLogo);
                                }
                            }else {  //从第三步进来
                                SPUtils.getInstance().put("storeIdCouponTypeThree","H");
                                SPUtils.getInstance().put("storeIdArrayCouponThree",storeIdArr);
                                SPUtils.getInstance().put("storeNameArrayCouponThree",storeNameSb.toString());
                                if(branList !=null && branList.size()>0){

                                    brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                                    if(brandLogo!=null){
                                        setBrandLogo();
                                    }
                                    SPUtils.getInstance().put("brandLogoCouponThree",brandLogo);
                                }
                            }
                        }


                        popWindow.dismiss();
                    }else {
                        for (Store store: storeList){
                            if(store!=null){
                                if(store.isChecked()){
                                    String name = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                    storeNameSb.append(name);
                                    storeIdSb.append(store.getStoreId()).append("-");
                                }

                            }
                        }

                        String storeIdA= storeIdSb.toString();
                        if(storeIdA!=null && !"".equals(storeIdA)){
                            storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                            storeId = storeIdArr;
                            storeName = storeNameSb.toString();
                            storeIdCouponType = "";
                        }else {
                            storeId = "";
                            storeName = "#全部店铺";
                            storeIdCouponType = "A";
                        }
                        if(campaignCoupon != null){
                            campaignCoupon.setStoreStr(storeId);
                            campaignCoupon.setStoreSelectType(storeIdCouponType);
                        }
                        tvStoreNames.setText(storeNameSb.toString());
                        tvStoreNames.refreshText();
                        if("YES".equals(isNextTwoAndThree)){   //从第二步进来
                            SPUtils.getInstance().put("storeIdCouponTypeTwo","H");
                            SPUtils.getInstance().put("storeIdArrayCouponTwo",storeId);
                            SPUtils.getInstance().put("storeNameArrayCouponTwo",storeName);
                            if(branList !=null && branList.size()>0){

                                brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                                if(brandLogo!=null){
                                    setBrandLogo();
                                }
                                SPUtils.getInstance().put("brandLogoCouponTwo",brandLogo);
                            }
                        }else {  //从第三步进来
                            SPUtils.getInstance().put("storeIdCouponTypeThree","H");
                            SPUtils.getInstance().put("storeIdArrayCouponThree",storeIdArr);
                            SPUtils.getInstance().put("storeNameArrayCouponThree",storeNameSb.toString());
                            if(branList !=null && branList.size()>0){

                                brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                                if(brandLogo!=null){
                                    setBrandLogo();
                                }
                                SPUtils.getInstance().put("brandLogoCouponThree",brandLogo);
                            }
                        }

                        popWindow.dismiss();
                    }

                }
            }
        });


        MoreBrandListAdapter brandListAdapter = new MoreBrandListAdapter(getActivity(),branList);
        lv_left.setAdapter(brandListAdapter);

        if(brandPostion == -1){
            brandPostion = 0;
        }
        brandListAdapter.setSelectItem(brandPostion);

        myAdapter = new CampaignSelectStoreAdapter(context);
        int selectItem = brandListAdapter.getSelectItem();
        if(branList!=null&&branList.size()>0){
            storeList = branList.get(selectItem).getStoreList();
            brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(selectItem).getBrandLogo();
            SPUtils.getInstance().put("brandLogoCoupon",brandLogo);
            myAdapter.setDatas(storeList);
        }

        lv_right.setAdapter(myAdapter);
        //判断默认选择店铺
        String storeNameStr = tvStoreNames.getTextView().getText().toString().trim();

//        storeIdCouponType = SPUtils.getInstance().getString("storeIdCouponType");

        if(storeIdCouponType!=null && !"".equals(storeIdCouponType)){
            if("A".equals(storeIdCouponType)){

                cbAllStore.setChecked(true);
                cbAllStore.setButtonDrawable(R.drawable.ic_correct);
                tvAllStore.setTextColor(getResources().getColor(R.color.font_color_blue));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));
                if(storeList!=null&& storeList.size()>0){  //自定义默认不选中
                    for (Store store:storeList){
                        if(store!=null){
                            store.setChecked(false);
                        }

                    }
                    myAdapter.notifyDataSetChanged();

                }
                storeId = "";

            }else if("D".equals(storeIdCouponType)){

                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(true);
                cbSelfStore.setButtonDrawable(R.drawable.ic_correct);
                tvSelfStore.setTextColor(getResources().getColor(R.color.font_color_blue));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));
                if(storeList!=null&& storeList.size()>0){  //自定义默认不选中
                    for (Store store:storeList){
                        if(store!=null){
                            store.setChecked(false);
                        }

                    }
                    myAdapter.notifyDataSetChanged();

                }
            }else if("J".equals(storeIdCouponType)){
                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(true);
                cbJoinStore.setButtonDrawable(R.drawable.ic_correct);
                tvJoinStore.setTextColor(getResources().getColor(R.color.font_color_blue));
                if(storeList!=null&& storeList.size()>0){  //自定义默认不选中
                    for (Store store:storeList){
                        if(store!=null){
                            store.setChecked(false);
                        }

                    }
                    myAdapter.notifyDataSetChanged();

                }
            }else if("H".equals(storeIdCouponType)){
                //分类默认不选中，自定义选中
                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));
                if(storeList!=null&&storeList.size()>0){

                    for (int i = 0;i<storeList.size();i++){
                        Store store = storeList.get(i);

                        if(storeNameStr!=null){
                            if(storeNameStr.contains(store.getStoreName())){
                                store.setChecked(true);
                            }else {
                                store.setChecked(false);
                            }
                        }

                    }
                }
            }

        }else {
            //分类默认不选中，自定义选中
            cbAllStore.setChecked(false);
            cbAllStore.setButtonDrawable(R.color.white);
            tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

            cbSelfStore.setChecked(false);
            cbSelfStore.setButtonDrawable(R.color.white);
            tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

            cbJoinStore.setChecked(false);
            cbJoinStore.setButtonDrawable(R.color.white);
            tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));
            if(storeList!=null&&storeList.size()>0){

                for (int i = 0;i<storeList.size();i++){
                    Store store = storeList.get(i);

                    if(storeNameStr!=null){
                        if(storeNameStr.contains(store.getStoreName())){
                            store.setChecked(true);
                        }else {
                            store.setChecked(false);
                        }
                    }

                }
            }
        }



        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(branList!=null&&branList.size()>0){
                    if(branList.get(position)!=null){
                        brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(position).getBrandLogo();
                        SPUtils.getInstance().put("brandLogoCoupon",brandLogo);
                        setBrandLogo();
                        SPUtils.getInstance().put("brandPositionCoupon",brandPostion);
                        storeList = branList.get(position).getStoreList();
                        brandPostion = position;
                        brandListAdapter.setSelectItem(position);
                        brandListAdapter.notifyDataSetInvalidated();
                        myAdapter = new CampaignSelectStoreAdapter(context);
                        myAdapter.setDatas(storeList);
                        lv_right.setAdapter(myAdapter);
//                        myAdapter.setDatas(storeList);
//                        myAdapter.notifyDataSetChanged();
                    }
                }

            }
        });


        myAdapter.notifyDataSetChanged();
        lv_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(currentNum == -1){ //选中
                    if(storeList.get(position).isChecked()){
                        storeList.get(position).setChecked(false);
                    }else {
                        storeList.get(position).setChecked(true);
                    }

                    currentNum = position;
                }else if(currentNum == position){ //同一个item选中变未选中
                    storeList.get(position).setChecked(false);
                    currentNum = -1;
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的

                    if(storeList.get(position).isChecked()){
                        storeList.get(position).setChecked(false);
                    }else {
                        storeList.get(position).setChecked(true);
                    }
                    currentNum = position;
                }


                myAdapter.notifyDataSetChanged();
                storeIdCouponType = "H";
                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

            }
        });
    }


    private void setBrandLogo(){
        RoundedCorners roundedCorners= new RoundedCorners(8);

        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_image_loading);

        Glide.with(this).load(brandLogo).apply(requestOptions).into(ivBrand);
    }



    private void initDatePicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 12, 28);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
//                Toast.makeText(StaffEditActivity.this, TimeUtils.date2String(date), Toast.LENGTH_SHORT).show();
                if(isStartDate){
                    tvStartTime.setText(TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd")));
                }else {
                    tvEndTime.setText(TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd")));
                }

            }
        })

                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .isCyclic(true)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final ImageView tvSubmit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
//                                pvCustomTime.dismiss();
                                String startDate  = tvStartTime.getText().toString().trim();
                                String endDate = tvEndTime.getText().toString().trim();
                                if(isStartDate){
                                    if(!startDate.isEmpty() && !endDate.isEmpty()){
                                        Date start = TimeUtils.string2Date(startDate);
                                        Date end = TimeUtils.string2Date(endDate);

                                        if(start.after(end)){  //start 大于end时 返回true; 反之 小于等于返回false
                                            tvStartTime.setText("");
                                            ToastUtils.showToast("开始时间不可大于结束时间！");
                                        }else {
                                            pvCustomTime.dismiss();
                                        }


                                    }else {
                                        pvCustomTime.dismiss();
                                    }
                                }else {


                                    if(!startDate.isEmpty() && !endDate.isEmpty()){
                                        Date start = TimeUtils.string2Date(startDate);
                                        Date end = TimeUtils.string2Date(endDate);

                                        if(end.before(start)){  //start 大于end时 返回true; 反之 小于等于返回false
                                            ToastUtils.showToast("结束时间不可小于开始时间！");
                                            tvEndTime.setText("");
                                        }else {
                                            pvCustomTime.dismiss();
                                        }


                                    }else {
                                        pvCustomTime.dismiss();
                                    }

                                }

                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });

                    }



                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }


}
