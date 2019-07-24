package com.zwx.scan.app.feature.couponmanage;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
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
import com.zwx.library.utils.DateUtils;
import com.zwx.library.utils.SPUtils;
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
import com.zwx.scan.app.feature.campaign.CampaignSelectStoreAdapter;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanNextTwoActivity;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanNextTwoFragment;
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
public class GiveCouponConsumeSettingFragment extends BaseFragment<GiveCouponContract.Presenter> implements GiveCouponContract.View,View.OnClickListener  {


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
    @BindView(R.id.ll_right_dates)
    protected LinearLayout llRightDates;

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
    @BindView(R.id.right_view)
    protected View rightView;


    //按固定时长
    @BindView(R.id.ll_left_durations)
    protected LinearLayout llLeftDurations;

    @BindView(R.id.tv_duration_label)
    protected TextView tvDayLabel;
    @BindView(R.id.left_view)
    protected View leftView;

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

    private Coupon coupon = new Coupon();

    PopWindow popWindow;

    protected List<Store> storeList = new ArrayList<>();
    public List<MoreStoreBean.BrandStoreBean> branList = new ArrayList<>();
    private CampaignSelectStoreAdapter myAdapter;

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
    private  boolean isCampaignAndCouponStore = false;  //优惠券店铺
    private String storeIdCouponType;

    private boolean isStartDate;

    protected TimePickerView pvCustomTime;

    private int getValidDay  = 0 ;
    private int couponDateDay  = 1 ;
    public String getValidDayStr2 ;
    private String validDaysStr2 = "";
    private int curPosition = 0;
    protected CampaignCoupon campaignCoupon = new CampaignCoupon();

    private  String isNextTwoAndThree;


    protected  String expireEndType = "R1";  //A 按固定日期  R1 按固定时长
    protected  String expireStartType = "R1";

    private GiveCouponNextConsumeTwoFragment fragment =null;
    private GiveCouponNewNextConsumeActivity activity =null;
    protected  String startDate;
    protected  String endDate;

    protected  String startDay ;
    protected  String endDay;

    protected  String brandLogoGive;
    LinearLayout.LayoutParams linearParams;

    protected  ArrayList<CampaignCoupon> campaignCoupons = new ArrayList<>();

    public String couponStoreSelectType;
    NoScrollViewPager setViewPager;

    private String prizeName;
    protected List<Store> selectStoreList = new ArrayList<>();
    public String getValidDayStr ;
    private String validDaysStr = "";


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

    private String background;

    private String backgroundThumbnail;
    private CouponMaterial material = null;
    
    public GiveCouponConsumeSettingFragment() {
        // Required empty public constructor
    }

    public static GiveCouponConsumeSettingFragment getInstance(Coupon coupon, int curPosition, String name, NoScrollViewPager setViewPager) {
        GiveCouponConsumeSettingFragment fragment = new GiveCouponConsumeSettingFragment();
        fragment.coupon = coupon;
        fragment.curPosition = curPosition;
        fragment.setViewPager = setViewPager;
        fragment.prizeName = name;
        return fragment;
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_give_coupon_consume_setting;
    }

    @Override
    protected void initInjector() {
        DaggerGiveCouponComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .giveCouponModule(new GiveCouponModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initView() {

        userId = SPUtils.getInstance().getString("userId");

        compId  = SPUtils.getInstance().getString("compId");
        //活动状态 暂存 可以编辑，否则其他状态不可编辑
        if("S".equals(GiveCouponNewActivity.compaignStatus)||"NEW".equals(GiveCouponNewActivity.compaignStatus)){
            tvSelectStore.setVisibility(View.VISIBLE);
            llStartTime.setEnabled(true);
            llEndTime.setEnabled(true);

            ivLeftSubtract.setEnabled(true);
            ivLeftSubtract2.setEnabled(true);
            iv_num_left_subtract.setEnabled(true);
            iv_num_right_add.setEnabled(true);
            edt_coupon_num.setEnabled(true);

            edtDayDate.setEnabled(true);
            edtDayDate2.setEnabled(true);

            ivRightAdd.setEnabled(true);
            ivRightAdd.setEnabled(true);
            ivRightAdd2.setEnabled(true);
            ivRightAdd2.setEnabled(true);

            llLeftDurations.setEnabled(true);
            llRightDates.setEnabled(true);
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
            llLeftDurations.setEnabled(false);
            llRightDates.setEnabled(false);

        }

        activity = (GiveCouponNewNextConsumeActivity) getActivity();
        fragment = new GiveCouponNextConsumeTwoFragment();



        if((storeIdArray !=null && storeIdArray.length()>0)){
            storeId = storeIdArray;
        }else {
            presenter.queryStoreList(getActivity(),userId,true);
        }

        if(storeNameArray !=null &&storeNameArray.length()>0){
            storeName = storeNameArray;

        }

        initDatePicker();
        setDaysVisible();


        edtDayDate.setSelection(edtDayDate.getText().toString().length());
        edtDayDate2.setSelection(edtDayDate2.getText().toString().length());

        //从编辑页面进入
        if(coupon != null){

            //张数
            if(coupon.getCouponQnt() != null && coupon.getCouponQnt().intValue()>1){
                couponQnt  = String.valueOf(coupon.getCouponQnt().intValue());
                iv_num_left_subtract.setBackgroundResource(R.drawable.ic_blue_subtracted);
            }else {
                couponQnt = "1";
                iv_num_left_subtract.setBackgroundResource(R.drawable.ic_gray_unsubtract);
            }
            edt_coupon_num.setText(couponQnt);
            material = coupon.getMaterial();
            if(material != null){
                background = material.getBackground();
                backgroundThumbnail = material.getBackgroundThumbnail();
                campaignCoupon.setBackground(background);
                campaignCoupon.setBackgroundThumbnail(backgroundThumbnail);
            }
            
            String couponName = coupon.getName();
            startDate = coupon.getExpireStartDate();
            endDate = coupon.getExpireEndDate();
            startDay = coupon.getExpireStartDay();
            endDay = coupon.getExpireEndDay();
            tvCouponName.setText(couponName!=null?couponName:"");

            tvStoreNames.setText(storeName);
            expireEndType = coupon.getExpireEndType();

            expireStartType = coupon.getExpireStartType();

            if(expireEndType!=null && "A".equals(expireEndType)){
                if(startDate!=null && !"".equals(startDate)){
                    Date date = DateUtils.parse(startDate,"yyyy-MM-dd");
                    if(date != null){
                        String startTime = DateUtils.formatDate(date,"yyyy-MM-dd").replace("-",".");
                        tvStartTime.setText(startTime);
                    }else {
                        tvStartTime.setText("");
                    }

                }else {
                    tvStartTime.setText("");
                }

                if(endDate!=null && !"".equals(endDate)){
                    Date date = DateUtils.parse(endDate,"yyyy-MM-dd");
                    if(date != null){
                        String endTime = DateUtils.formatDate(date,"yyyy-MM-dd").replace("-",".");
                        tvEndTime.setText(endTime);
                    }else {
                        tvEndTime.setText("");
                    }

                }else {
                    tvEndTime.setText("");
                }

                setDateVisible();

              /*  linearParams=(LinearLayout.LayoutParams) setViewPager.getLayoutParams();
                //设置viewpager高度
                linearParams.height = 1600;*/
            }else  if(expireEndType!=null && "R1".equals(expireEndType)){
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
                expireEndType = "R1";
                setDaysVisible();

            }

            storeIdCouponType = coupon.getStoreSelectType();

            if(storeIdCouponType == null || "".equals(storeIdCouponType)){
                storeIdCouponType = "H";
            }
            storeId = coupon.getStoreIds();
            storeName = coupon.getStoreNames();
        }

        //默认选日期
/*
        linearParams=(LinearLayout.LayoutParams) setViewPager.getLayoutParams();
        //设置viewpager高度
        linearParams.height = 2200;
        setViewPager.setLayoutParams(linearParams);*/
       /* if("A".equals(expireEndType)){
            linearParams.height = 1400;
            setViewPager.setLayoutParams(linearParams);
        }else {
            linearParams.height = 2000;
            setViewPager.setLayoutParams(linearParams);
        }*/
        if(storeIdCouponType !=null && !"".equals(storeIdCouponType)){

            if("A".equals(storeIdCouponType)){

                tvStoreNames.setText("#全部店铺");
            }else if("D".equals(storeIdCouponType)){
                tvStoreNames.setText("#全部自营");
            }else if("J".equals(storeIdCouponType)){
                tvStoreNames.setText("#全部加盟");
            }else if("H".equals(storeIdCouponType)){
                if(storeName != null && !"".equals(storeName)){
                    tvStoreNames.setText(storeName);
                }else {
                    storeIdCouponType = "A";
                    tvStoreNames.setText("#全部店铺");
                }
            }else {
                storeIdCouponType = "A";
                tvStoreNames.setText("#全部店铺");
            }
        }else { //否则自定义 选择

            if("S".equals(GiveCouponNewActivity.compaignStatus)){
                tvStoreNames.setText(storeName);
            }else {
                storeIdCouponType = "A";
                tvStoreNames.setText("#全部店铺");
            }

        }
      /*  if(brandLogo !=null &&!"".equals(brandLogo)){

        }else {
            brandLogo = SPUtils.getInstance().getString("brandLogo");
        }*/
        brandLogo = SPUtils.getInstance().getString("brandLogo");
        setBrandLogo();

        startDate = tvStartTime.getText().toString().trim();
        endDate = tvEndTime.getText().toString().trim();

        startDay = edtDayDate.getText().toString().trim();
        endDay = edtDayDate2.getText().toString().trim();

        couponQnt = edt_coupon_num.getText().toString().trim();
        if(couponQnt != null && !"".equals(couponQnt)){
            campaignCoupon.setCouponQnt(Integer.parseInt(couponQnt));
        }else {
            campaignCoupon.setCouponQnt(1);
        }
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

        if(storeId != null && storeId.contains(",")){
            storeId = storeId.replace(",","-");
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
            campaignCoupon.setStoreSelectType("");
        }
        campaignCoupon.setExpireStartType(expireEndType);
        campaignCoupon.setExpireEndType(expireEndType);

        if("A".equals(expireEndType)){
            campaignCoupon.setExpireStartDate(startDate!=null && !"".equals(startDate)?startDate.replace("-","")+" "+DateUtils.formatTime(System.currentTimeMillis()):"");
            campaignCoupon.setExpireEndDate(endDate!=null&& !"".equals(endDate)?endDate.replace("-",".")+" "+DateUtils.formatTime(System.currentTimeMillis()):"");
            campaignCoupon.setExpireStartDay("");
            campaignCoupon.setExpireEndDay("");
        }else if("R1".equals(expireEndType)){
            campaignCoupon.setExpireStartDay(startDay);
            campaignCoupon.setExpireEndDay(endDay);
            campaignCoupon.setExpireStartDate("");
            campaignCoupon.setExpireEndDate("");

        }

        if("条件一".equals(prizeName)){
            if(activity.campaignCouponList1!=null && activity.campaignCouponList1.size()>0){
                for(int i =0;i<  activity.campaignCouponList1.size();i++){
                    CampaignCoupon campaignCoupon1 = activity.campaignCouponList1.get(i);
                    String couponId1 =String.valueOf(campaignCoupon1.getCouponId());
                    String couponId2 =String.valueOf(campaignCoupon.getCouponId());
                    String isPosition = curPosition+"";
                    String isPosition2 = i+"";
                    if(couponId1!=null && couponId2 !=null){
                        if(couponId1.equals(couponId2)&& isPosition.equals(isPosition2)){
                            campaignCoupon1 = campaignCoupon;
                        }
                    }
                }
            }
        }else if("条件二".equals(prizeName)){
            if(activity.campaignCouponList2!=null && activity.campaignCouponList2.size()>0){
                for(int i =0;i<  activity.campaignCouponList2.size();i++){
                    CampaignCoupon campaignCoupon1 = activity.campaignCouponList2.get(i);
                    String couponId1 =String.valueOf(campaignCoupon1.getCouponId());
                    String couponId2 =String.valueOf(campaignCoupon.getCouponId());
                    String isPosition = curPosition+"";
                    String isPosition2 = i+"";
                    if(couponId1!=null && couponId2 !=null){
                        if(couponId1.equals(couponId2)&& isPosition.equals(isPosition2)){
                            campaignCoupon1 = campaignCoupon;
                        }
                    }
                }
            }
        }else if("条件三".equals(prizeName)){

            if(activity.campaignCouponList3!=null && activity.campaignCouponList3.size()>0){
                for(int i =0;i<  activity.campaignCouponList3.size();i++){
                    CampaignCoupon campaignCoupon1 = activity.campaignCouponList3.get(i);
                    String couponId1 =String.valueOf(campaignCoupon1.getCouponId());
                    String couponId2 =String.valueOf(campaignCoupon.getCouponId());
                    String isPosition = curPosition+"";
                    String isPosition2 = i+"";
                    if(couponId1!=null && couponId2 !=null){
                        if(couponId1.equals(couponId2)&& isPosition.equals(isPosition2)){
                            campaignCoupon1 = campaignCoupon;
                        }
                    }
                }
            }
        }else if("条件四".equals(prizeName)){
            if(activity.campaignCouponList4!=null && activity.campaignCouponList4.size()>0){
                for(int i =0;i<  activity.campaignCouponList4.size();i++){
                    CampaignCoupon campaignCoupon1 = activity.campaignCouponList4.get(i);
                    String couponId1 =String.valueOf(campaignCoupon1.getCouponId());
                    String couponId2 =String.valueOf(campaignCoupon.getCouponId());
                    String isPosition = curPosition+"";
                    String isPosition2 = i+"";
                    if(couponId1!=null && couponId2 !=null){
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
                startDay = edtDayDate.getText().toString().trim();
                endDay = edtDayDate2.getText().toString().trim();
                couponQnt = edt_coupon_num.getText().toString().trim();
                edtDayDate.setSelection(edtDayDate.getText().toString().trim().length());
                if("R1".equals(expireEndType)){
                    if("条件一".equals(prizeName)){
                        getDaysData1();
                    }else if("条件二".equals(prizeName)){
                        getDaysData2();
                    }else if("条件三".equals(prizeName)){
                        getDaysData3();
                    }else if("条件四".equals(prizeName)){
                        getDaysData4();
                    }
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
                startDay = edtDayDate.getText().toString().trim();
                endDay = edtDayDate2.getText().toString().trim();
                couponQnt = edt_coupon_num.getText().toString().trim();
                edtDayDate2.setSelection(edtDayDate2.getText().toString().trim().length());
                if("R1".equals(expireEndType)){


                    if("条件一".equals(prizeName)){
                        getDaysData1();
                    }else if("条件二".equals(prizeName)){
                        getDaysData2();
                    }else if("条件三".equals(prizeName)){
                        getDaysData3();
                    }else if("条件四".equals(prizeName)){
                        getDaysData4();
                    }
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
                startDate = tvStartTime.getText().toString().trim();
                endDate = tvEndTime.getText().toString().trim();
                couponQnt = edt_coupon_num.getText().toString().trim();
                if("A".equals(expireEndType)){


                    if("条件一".equals(prizeName)){
                        getDateData1();
                    }else if("条件二".equals(prizeName)){
                        getDateData2();
                    }else if("条件三".equals(prizeName)){
                        getDateData3();
                    }else if("条件四".equals(prizeName)){
                        getDateData4();
                    }
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
                    campaignCoupon.setStoreSelectType(storeIdCouponType);

                    if("条件一".equals(prizeName)){
                        getDateData1();
                    }else if("条件二".equals(prizeName)){
                        getDateData2();
                    }else if("条件三".equals(prizeName)){
                        getDateData3();
                    }else if("条件四".equals(prizeName)){
                        getDateData4();
                    }
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

        if("条件一".equals(prizeName)){
            if(activity.campaignCouponList1!=null && activity.campaignCouponList1.size()>0){
                for (int i=0;i< activity.campaignCouponList1.size();i++) {
                    CampaignCoupon campaignCoupon = activity.campaignCouponList1.get(i);
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
                            campaignCoupon.setStores(selectStoreList);
                        }
                    }
                }


            }
        }else if("条件二".equals(prizeName)){
            if(activity.campaignCouponList2!=null && activity.campaignCouponList2.size()>0){
                for (int i=0;i< activity.campaignCouponList2.size();i++) {
                    CampaignCoupon campaignCoupon = activity.campaignCouponList2.get(i);
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
                            campaignCoupon.setStores(selectStoreList);
                        }
                    }
                }


            }
        }else if("条件三".equals(prizeName)){
            if(activity.campaignCouponList3!=null && activity.campaignCouponList3.size()>0){
                for (int i=0;i< activity.campaignCouponList3.size();i++) {
                    CampaignCoupon campaignCoupon = activity.campaignCouponList3.get(i);
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
                            campaignCoupon.setStores(selectStoreList);
                        }
                    }
                }


            }
        }else if("条件四".equals(prizeName)){
            if(activity.campaignCouponList4!=null && activity.campaignCouponList4.size()>0){
                for (int i=0;i< activity.campaignCouponList4.size();i++) {
                    CampaignCoupon campaignCoupon = activity.campaignCouponList4.get(i);
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
                            campaignCoupon.setStores(selectStoreList);
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

    @Override
    protected void initData() {


    }

    private void setUpdateCoupon(){

        if("条件一".equals(prizeName)){
            if(activity.couponList1 != null && activity.couponList1.size()>0){
                for(int i=0;i<activity.couponList1.size();i++){
                    Coupon coupon1  = activity.couponList1.get(i);
                    String couponId = String.valueOf(coupon1.getId());
                    String id = String.valueOf(coupon.getId());
                    String isSort2 = curPosition +"";
                    String isSort = i+"";
                    if(couponId.equals(id) && isSort.equals(isSort2)){
                        coupon1.setMaterial(material);
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
        }else if("条件二".equals(prizeName)){
            if(activity.couponList2 != null && activity.couponList2.size()>0){
                for(int i=0;i<activity.couponList2.size();i++){
                    Coupon coupon1  = activity.couponList2.get(i);
                    String couponId = String.valueOf(coupon1.getId());
                    String id = String.valueOf(coupon.getId());
                    String isSort2 = curPosition +"";
                    String isSort = i+"";
                    if(couponId.equals(id) && isSort.equals(isSort2)){
                        coupon1.setMaterial(material);
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
        }else if("条件三".equals(prizeName)){
            if(activity.couponList3 != null && activity.couponList3.size()>0){
                for(int i=0;i<activity.couponList3.size();i++){
                    Coupon coupon1  = activity.couponList3.get(i);
                    String couponId = String.valueOf(coupon1.getId());
                    String id = String.valueOf(coupon.getId());
                    String isSort2 = curPosition +"";
                    String isSort = i+"";
                    if(couponId.equals(id) && isSort.equals(isSort2)){
                        coupon1.setMaterial(material);
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
        }else if("条件四".equals(prizeName)){
            if(activity.couponList4 != null && activity.couponList4.size()>0){
                for(int i=0;i<activity.couponList4.size();i++){
                    Coupon coupon1  = activity.couponList4.get(i);
                    String couponId = String.valueOf(coupon1.getId());
                    String id = String.valueOf(coupon.getId());
                    String isSort2 = curPosition +"";
                    String isSort = i+"";
                    if(couponId.equals(id) && isSort.equals(isSort2)){
                        coupon1.setMaterial(material);
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
        if("条件一".equals(prizeName)){
            if(activity.campaignCouponList1!=null && activity.campaignCouponList1.size()>0){
                for (int i=0;i< activity.campaignCouponList1.size();i++) {
                    CampaignCoupon campaignCoupon1 = activity.campaignCouponList1.get(i);
                    String couponName = campaignCoupon1.getCouponName();
                    String name = campaignCoupon.getCouponName();
                    String isSort2 = curPosition +"";
                    String isSort = i+"";
                    if(couponName.equals(name)&& isSort.equals(isSort2)){
                        campaignCoupon1.setStores(selectStoreList);
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
                campaignCoupon.setStores(selectStoreList);
                activity.campaignCouponList1.add(campaignCoupon);
            }

        }else if("条件二".equals(prizeName)){

            if(activity.campaignCouponList2!=null && activity.campaignCouponList2.size()>0){
                for (int i=0;i< activity.campaignCouponList2.size();i++) {
                    CampaignCoupon campaignCoupon1 = activity.campaignCouponList2.get(i);
                    String couponName = campaignCoupon1.getCouponName();
                    String name = campaignCoupon.getCouponName();
                    String isSort2 = curPosition +"";
                    String isSort = i+"";
                    if(couponName.equals(name)&& isSort.equals(isSort2)){
                        campaignCoupon1.setStores(selectStoreList);
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
                activity.campaignCouponList2.add(campaignCoupon);
            }
        }else if("条件三".equals(prizeName)){

            if(activity.campaignCouponList3!=null && activity.campaignCouponList3.size()>0){
                for (int i=0;i< activity.campaignCouponList3.size();i++) {
                    CampaignCoupon campaignCoupon1 = activity.campaignCouponList3.get(i);
                    String couponName = campaignCoupon1.getCouponName();
                    String name = campaignCoupon.getCouponName();
                    String isSort2 = curPosition +"";
                    String isSort = i+"";
                    if(couponName.equals(name)&& isSort.equals(isSort2)){
                        campaignCoupon1.setStores(selectStoreList);
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
                activity.campaignCouponList3.add(campaignCoupon);
            }
        }else if("条件四".equals(prizeName)){

            if(activity.campaignCouponList4!=null && activity.campaignCouponList4.size()>0){
                for (int i=0;i< activity.campaignCouponList4.size();i++) {
                    CampaignCoupon campaignCoupon1 = activity.campaignCouponList4.get(i);
                    String couponName = campaignCoupon1.getCouponName();
                    String name = campaignCoupon.getCouponName();
                    String isSort2 = curPosition +"";
                    String isSort = i+"";
                    if(couponName.equals(name)&& isSort.equals(isSort2)){
                        campaignCoupon1.setStores(selectStoreList);
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
                activity.campaignCouponList4.add(campaignCoupon);
            }
        }
    }


    private void getDateData4() {
        if (activity.campaignCouponList4 != null && activity.campaignCouponList4.size() > 0) {
            for (int i=0;i< activity.campaignCouponList4.size();i++) {
                CampaignCoupon campaignCoupon = activity.campaignCouponList4.get(i);
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
                        campaignCoupon.setStores(selectStoreList);
                        campaignCoupon.setExpireStartDay("");
                        campaignCoupon.setExpireEndDay("");
                        campaignCoupon.setExpireStartDate(startDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()));
                        campaignCoupon.setExpireEndDate(endDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()));
                        if(storeIdCouponType!=null && !"".equals(storeIdCouponType)){
                            if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                                campaignCoupon.setStoreStr("");
                                campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
                            }else if("H".equals(storeIdCouponType)){
                                campaignCoupon.setStoreStr(storeId);
                                campaignCoupon.setStoreSelectType("");
                            }else {
                                campaignCoupon.setStoreStr(storeId);
                                campaignCoupon.setStoreSelectType("");
                            }
                        }else {
                            campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
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
        }
        setUpdateCoupon();
    }
    private void getDaysData4(){
        if(activity.campaignCouponList4!=null && activity.campaignCouponList4.size()>0){
            for (int i=0;i< activity.campaignCouponList4.size();i++) {
                CampaignCoupon campaignCoupon = activity.campaignCouponList4.get(i);
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
                        campaignCoupon.setStores(selectStoreList);
                        campaignCoupon.setExpireEndDay(endDay);
                        campaignCoupon.setExpireStartDay(startDay);
                        if(storeIdCouponType!=null && !"".equals(storeIdCouponType)){
                            if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                                campaignCoupon.setStoreStr("");
                                campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
                            }else if("H".equals(storeIdCouponType)){
                                campaignCoupon.setStoreStr(storeId);
                                campaignCoupon.setStoreSelectType("");
                            }else {
                                campaignCoupon.setStoreStr(storeId);
                                campaignCoupon.setStoreSelectType("");
                            }
                        }else {
                            campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
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


        }
        setUpdateCoupon();
    }
    private void getDateData3() {
        if (activity.campaignCouponList3 != null && activity.campaignCouponList3.size() > 0) {
            for (int i=0;i< activity.campaignCouponList3.size();i++) {
                CampaignCoupon campaignCoupon = activity.campaignCouponList3.get(i);
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
                        campaignCoupon.setStores(selectStoreList);
                        campaignCoupon.setExpireStartDay("");
                        campaignCoupon.setExpireEndDay("");
                        campaignCoupon.setExpireStartDate(startDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()));
                        campaignCoupon.setExpireEndDate(endDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()));
                        if(storeIdCouponType!=null && !"".equals(storeIdCouponType)){
                            if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                                campaignCoupon.setStoreStr("");
                                campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
                            }else if("H".equals(storeIdCouponType)){
                                campaignCoupon.setStoreStr(storeId);
                                campaignCoupon.setStoreSelectType("");
                            }else {
                                campaignCoupon.setStoreStr(storeId);
                                campaignCoupon.setStoreSelectType("");
                            }
                        }else {
                            campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
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
        }
        setUpdateCoupon();
    }
    private void getDaysData3(){
        if(activity.campaignCouponList3!=null && activity.campaignCouponList3.size()>0){
            for (int i=0;i< activity.campaignCouponList3.size();i++) {
                CampaignCoupon campaignCoupon = activity.campaignCouponList3.get(i);
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
                        campaignCoupon.setStores(selectStoreList);
                        campaignCoupon.setExpireEndDay(endDay);
                        campaignCoupon.setExpireStartDay(startDay);
                        if(storeIdCouponType!=null && !"".equals(storeIdCouponType)){
                            if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                                campaignCoupon.setStoreStr("");
                                campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
                            }else if("H".equals(storeIdCouponType)){
                                campaignCoupon.setStoreStr(storeId);
                                campaignCoupon.setStoreSelectType("");
                            }else {
                                campaignCoupon.setStoreStr(storeId);
                                campaignCoupon.setStoreSelectType("");
                            }
                        }else {
                            campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
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
            campaignCoupon.setStores(selectStoreList);
            campaignCoupon.setExpireStartDate("");
            campaignCoupon.setExpireEndDate("");
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
                campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
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
            activity.campaignCouponList3.add(campaignCoupon);
        }
        setUpdateCoupon();
    }
    private void getDateData2() {
        if ((startDate != null && !"".equals(startDate)) && (endDate != null && !"".equals(endDate))) {
            if (activity.campaignCouponList2 != null && activity.campaignCouponList2.size() > 0) {
                for (int i=0;i< activity.campaignCouponList2.size();i++) {
                    CampaignCoupon campaignCoupon = activity.campaignCouponList2.get(i);
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
                            campaignCoupon.setStores(selectStoreList);
                            campaignCoupon.setExpireStartDay("");
                            campaignCoupon.setExpireEndDay("");
                            campaignCoupon.setExpireStartDate(startDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()));
                            campaignCoupon.setExpireEndDate(endDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()));
                            if(storeIdCouponType!=null && !"".equals(storeIdCouponType)){
                                if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                                    campaignCoupon.setStoreStr("");
                                    campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
                                }else if("H".equals(storeIdCouponType)){
                                    campaignCoupon.setStoreStr(storeId);
                                    campaignCoupon.setStoreSelectType("");
                                }else {
                                    campaignCoupon.setStoreStr(storeId);
                                    campaignCoupon.setStoreSelectType("");
                                }
                            }else {
                                campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
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
                campaignCoupon.setStores(selectStoreList);
                campaignCoupon.setExpireStartDay("");
                campaignCoupon.setExpireEndDay("");
                campaignCoupon.setExpireStartDate(startDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()));
                campaignCoupon.setExpireEndDate(endDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()));
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
                activity.campaignCouponList2.add(campaignCoupon);
            }

        }
        setUpdateCoupon();
    }
    private void getDaysData2(){
        if(activity.campaignCouponList2!=null && activity.campaignCouponList2.size()>0){
            for (int i=0;i< activity.campaignCouponList2.size();i++) {
                CampaignCoupon campaignCoupon = activity.campaignCouponList2.get(i);
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
                        campaignCoupon.setStores(selectStoreList);
                        campaignCoupon.setExpireEndDay(endDay);
                        campaignCoupon.setExpireStartDay(startDay);
                        if(storeIdCouponType!=null && !"".equals(storeIdCouponType)){
                            if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                                campaignCoupon.setStoreStr("");
                                campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
                            }else if("H".equals(storeIdCouponType)){
                                campaignCoupon.setStoreStr(storeId);
                                campaignCoupon.setStoreSelectType("");
                            }else {
                                campaignCoupon.setStoreStr(storeId);
                                campaignCoupon.setStoreSelectType("");
                            }
                        }else {
                            campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
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
            campaignCoupon.setStores(selectStoreList);
            campaignCoupon.setExpireStartDate("");
            campaignCoupon.setExpireEndDate("");
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
                campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
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
            activity.campaignCouponList2.add(campaignCoupon);
        }
        setUpdateCoupon();
    }
    private void getDateData1() {
        if (activity.campaignCouponList1 != null && activity.campaignCouponList1.size() > 0) {
            for (int i=0;i< activity.campaignCouponList1.size();i++) {
                CampaignCoupon campaignCoupon = activity.campaignCouponList1.get(i);
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
                        campaignCoupon.setStores(selectStoreList);
                        campaignCoupon.setExpireStartDay("");
                        campaignCoupon.setExpireEndDay("");
                        campaignCoupon.setExpireStartDate(startDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()));
                        campaignCoupon.setExpireEndDate(endDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()));
                        if(storeIdCouponType!=null && !"".equals(storeIdCouponType)){
                            if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                                campaignCoupon.setStoreStr("");
                                campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
                            }else if("H".equals(storeIdCouponType)){
                                campaignCoupon.setStoreStr(storeId);
                                campaignCoupon.setStoreSelectType("");
                            }else {
                                campaignCoupon.setStoreStr(storeId);
                                campaignCoupon.setStoreSelectType("");
                            }
                        }else {
                            campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
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
            campaignCoupon.setStores(selectStoreList);
            campaignCoupon.setExpireStartDay("");
            campaignCoupon.setExpireEndDay("");
            campaignCoupon.setExpireStartDate(startDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()));
            campaignCoupon.setExpireEndDate(endDate.replace(".","-")+" "+DateUtils.formatTime(System.currentTimeMillis()));
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
            activity.campaignCouponList1.add(campaignCoupon);
        }
        setUpdateCoupon();
    }
    private void getDaysData1(){
        if(activity.campaignCouponList1!=null && activity.campaignCouponList1.size()>0){
            for (int i=0;i< activity.campaignCouponList1.size();i++) {
                CampaignCoupon campaignCoupon = activity.campaignCouponList1.get(i);
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
                        campaignCoupon.setStores(selectStoreList);
                        campaignCoupon.setExpireEndDay(endDay);
                        campaignCoupon.setExpireStartDay(startDay);
                        if(storeIdCouponType!=null && !"".equals(storeIdCouponType)){
                            if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                                campaignCoupon.setStoreStr("");
                                campaignCoupon.setStoreSelectType(storeIdCouponType);
                            }else if("H".equals(storeIdCouponType)){
                                campaignCoupon.setStoreStr(storeId);
                                campaignCoupon.setStoreSelectType("");
                            }else {
                                campaignCoupon.setStoreStr(storeId);
                                campaignCoupon.setStoreSelectType("");
                            }
                        }else {
                            campaignCoupon.setStoreSelectType(coupon.getStoreSelectType());
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


        }
        setUpdateCoupon();
    }



    private void setDateVisible(){
        llDate.setVisibility(View.VISIBLE);
        llDays.setVisibility(View.GONE);
        tvDayLabel.setTextColor(getResources().getColor(R.color.campaign_new_font_gray));
        leftView.setBackground(getResources().getDrawable(R.color.campaign_new_font_gray));
        tvDateLabel.setTextColor(getResources().getColor(R.color.btn_color_blue));
        rightView.setBackground(getResources().getDrawable(R.color.btn_color_blue));

        leftView.setVisibility(View.INVISIBLE);
        rightView.setVisibility(View.VISIBLE);
    }

    private void setDaysVisible(){
        llDate.setVisibility(View.GONE);
        llDays.setVisibility(View.VISIBLE);

        tvDayLabel.setTextColor(getResources().getColor(R.color.btn_color_blue));
        leftView.setBackground(getResources().getDrawable(R.color.btn_color_blue));
        tvDateLabel.setTextColor(getResources().getColor(R.color.campaign_new_font_gray));
        rightView.setBackground(getResources().getDrawable(R.color.campaign_new_font_gray));

        leftView.setVisibility(View.VISIBLE);
        rightView.setVisibility(View.INVISIBLE);
    }

    @OnClick({R.id.tv_select_shop,
            R.id.ll_right_dates,R.id.ll_left_durations,R.id.ll_start,R.id.ll_end,
            R.id.iv_left_subtract,R.id.iv_right_add,
            R.id.iv_left_subtract2,R.id.iv_right_add2,
            R.id.iv_num_right_add,R.id.iv_num_left_subtract})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

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
                    iv_num_left_subtract.setBackgroundResource(R.drawable.ic_gray_unsubtract);
                }else {
                    iv_num_left_subtract.setBackgroundResource(R.drawable.ic_blue_subtracted);
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
                    iv_num_right_add.setBackgroundResource(R.drawable.ic_blue_added);
                }
                if(getCouponNum>=2){
                    iv_num_left_subtract.setBackgroundResource(R.drawable.ic_blue_subtracted);
                }


                edt_coupon_num.setText(getCouponNum+"");
                break;
            
            case R.id.tv_select_shop:  //开始时间

                presenter.queryStoreList(getActivity(),userId,false);
                break;
            case R.id.ll_right_dates:  //  //按固定日期
                expireEndType = "A";
                startDate = tvStartTime.getText().toString().trim();
                endDate = tvEndTime.getText().toString().trim();
                couponQnt = edt_coupon_num.getText().toString().trim();
                setDateVisible();

//                linearParams=(LinearLayout.LayoutParams) setViewPager.getLayoutParams();
//                linearParams.height = 1600;
//                setViewPager.setLayoutParams(linearParams);
                if("条件一".equals(prizeName)){
                    getDateData1();
                }else if("条件二".equals(prizeName)){
                    getDateData2();
                }else if("条件三".equals(prizeName)){
                    getDateData3();
                }else if("条件四".equals(prizeName)){
                    getDateData4();
                }
                break;
            case R.id.ll_left_durations: //按固定时长
                expireEndType = "R1";
                startDay = edtDayDate.getText().toString().trim();
                endDay = edtDayDate2.getText().toString().trim();
                couponQnt = edt_coupon_num.getText().toString().trim();
                setDaysVisible();

//                linearParams=(LinearLayout.LayoutParams) setViewPager.getLayoutParams();
//                linearParams.height = 2200;
//                setViewPager.setLayoutParams(linearParams);

                if("条件一".equals(prizeName)){
                    getDaysData1();
                }else if("条件二".equals(prizeName)){
                    getDaysData2();
                }else if("条件三".equals(prizeName)){
                    getDaysData3();
                }else if("条件四".equals(prizeName)){
                    getDaysData4();
                }


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
                    couponDateDay = 1;
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


    //选择多个店铺
    protected void  showPopView(Context context){


        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_campaign_select_more_store,null);
        popWindow= new PopWindow.Builder(getActivity())
                .setStyle(PopWindow.PopWindowStyle.PopDown)
                .setView(contentView)
                .show(activity.tvTitle);


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
                    storeIdCouponType = "A";


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
                    storeIdCouponType = "D";


                }
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
                    selectStoreList.clear();  //已选择店铺
                    if(storeIdCouponType != null && !"".equals(storeIdCouponType)){
                        if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟
                            //storeName 为全部店铺  自营  加盟

                            if(branList !=null && branList.size()>0){
                                brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                                if(brandLogo!=null){
                                    setBrandLogo();
                                }
                            }
                            tvStoreNames.setText(storeName);
                            tvStoreNames.refreshText();


                        }else if("H".equals(storeIdCouponType)){  //H为自定义店铺
                            for (Store store: storeList){
                                if(store!=null){
                                    if(store.isChecked()){
                                        String name = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                        storeNameSb.append(name);
                                        storeIdSb.append(store.getStoreId()).append("-");
                                        selectStoreList.add(store);  //已选择店铺
                                    }

                                }
                            }

                            String storeIdA= storeIdSb.toString();
                            if(storeIdSb!=null && !"".equals(storeIdA)){
                                storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                            }


                            storeId = storeIdArr;
                            storeName = storeNameSb.toString();
                            storeIdCouponType = "H";
                            if(branList !=null && branList.size()>0){
                                brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                                if(brandLogo!=null){
                                    setBrandLogo();
                                }
                            }
                            tvStoreNames.setText(storeName);
                            tvStoreNames.refreshText();

                        }


                        popWindow.dismiss();
                    }else {
                        for (Store store: storeList){
                            if(store!=null){
                                if(store.isChecked()){
                                    String name = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                    storeNameSb.append(name);
                                    storeIdSb.append(store.getStoreId()).append("-");
                                    selectStoreList.add(store);
                                }

                            }
                        }

                        String storeIdA= storeIdSb.toString();
                        if(storeIdSb!=null && !"".equals(storeIdA)){
                            storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                        }

                      /*  String storeNameA = storeNameSb2.toString();
                        if(storeNameA==null ||"".equals(storeNameA)){
                            ToastUtils.showShort("请选择店铺");
                            return;
                        }*/
                        storeId = storeIdArr;
                        storeName = storeNameSb.toString();
                        storeIdCouponType = "H";
                        tvStoreNames.setText(storeName);
                        tvStoreNames.refreshText();
                        if(branList !=null && branList.size()>0){
                            brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                            if(brandLogo!=null){
                                setBrandLogo();
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
            SPUtils.getInstance().put("brandLogoGive",brandLogo);
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

                        if("条件一".equals(prizeName)){
                        }else if("条件二".equals(prizeName)){
                        }else if("条件三".equals(prizeName)){
                        }else if("条件四".equals(prizeName)){
                        }else if("条件五".equals(prizeName)){
                        }else if("条件六".equals(prizeName)){
                        }else if("安慰奖".equals(prizeName)){

                        }
                        SPUtils.getInstance().put("brandLogoLHPT",brandLogo);
                        SPUtils.getInstance().put("brandPositionLHPT",brandPostion);
                        storeList = branList.get(position).getStoreList();
                        brandPostion = position;
                        brandListAdapter.setSelectItem(position);
                        brandListAdapter.notifyDataSetInvalidated();
                        myAdapter = new CampaignSelectStoreAdapter(context);
                        myAdapter.setDatas(storeList);
                        lv_right.setAdapter(myAdapter);
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
                    String startTime = TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd"));
                    tvStartTime.setText(startTime.replace("-","."));
                }else {
                    String endTime = TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd"));
                    tvEndTime.setText(endTime.replace("-","."));
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
                                        Date start = TimeUtils.string2Date(startDate.replace(".","-"));
                                        Date end = TimeUtils.string2Date(endDate.replace(".","-"));

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
                                        Date start = TimeUtils.string2Date(startDate.replace(".","-"));
                                        Date end = TimeUtils.string2Date(endDate.replace(".","-"));

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
