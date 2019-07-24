package com.zwx.scan.app.feature.campaign;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zwx.library.pickerview.picker.builder.TimePickerBuilder;
import com.zwx.library.pickerview.picker.listener.CustomListener;
import com.zwx.library.pickerview.picker.listener.OnTimeSelectListener;
import com.zwx.library.pickerview.picker.view.TimePickerView;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.countanalysis.MoreBrandListAdapter;
import com.zwx.scan.app.widget.MoreTextView;
import com.zwx.scan.app.widget.SmoothCheckBox;
import com.zwx.scan.app.widget.carouselview.CustomCarouseView;
import com.zwx.scan.app.widget.carouselview.ViewListener;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CampaignNewNextActivity extends BaseActivity<CampaignsContract.Presenter> implements CampaignsContract.View,View.OnClickListener{




    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.btn_add_coupon)
    protected Button btnAddCoupon;

    @BindView(R.id.carsour_view_coupon_list)
    protected  CustomCarouseView carouselView;

    @BindView(R.id.dots)
    protected LinearLayout mDotsLayout;

    @BindView(R.id.ll_add_top)
    protected LinearLayout llAddTop;

    @BindView(R.id.ll_bottom_coupon_edit)
    protected LinearLayout llBottom;

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


    @BindView(R.id.fab)
    protected ImageButton fab;

    @BindView(R.id.btn_pre)
    protected Button btnPre;

    @BindView(R.id.btn_next)
    protected Button btnNext;

    PopWindow popWindow;

    private List<Store> storeList = new ArrayList<>();
    public List<MoreStoreBean.BrandStoreBean> branList = new ArrayList<>();
    private  CampaignSelectStoreAdapter myAdapter;

    private String userId;
    private String storeName ;
    private String storeId;
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

    protected  int requestCode = 2222;

    protected  ArrayList<Coupon> couponList = new ArrayList<>();

    private List<ImageView> mDotsIV = new ArrayList<>();
    private CampaignCoupon campaignCoupon = new CampaignCoupon();

    private int getValidDay  = 0 ;
    private int couponDateDay  = 1 ;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_campaign_new_next;
    }



    @Override
    protected void initView() {

        tvTitle.setText("活动优惠券设置");

        userId = SPUtils.getInstance().getString("userId");

//        campaign = (Campaign) getIntent().getSerializableExtra("campaign");
        DaggerCampaignsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignsModule(new CampaignsModule(this))
                .build()
                .inject(this);

        initDatePicker();
        //默认按固定日期标签
        llDate.setVisibility(View.VISIBLE);
        llDays.setVisibility(View.GONE);
        llBottom.setVisibility(View.GONE);
        llBottomCoupon.setVisibility(View.GONE);
        setDateAndDurColor();
        setCarouselView();
        edtDayDate.setText("0");
        edtDayDate2.setText("1");
    }


    
    private void setCarouselView(){

        carouselView.setViewListener(viewListener);
        carouselView.setCurrentItem(0);
        carouselView.containerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        carouselView.containerViewPager.setPageWidth(0.55f);
        carouselView.containerViewPager.settPaddingBetweenItem(16);
    }

    ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(int position) {

            View customView = getLayoutInflater().inflate(R.layout.item_coupon_list, null);
            TextView   tv_name = (TextView) customView.findViewById(R.id.tv_name);
//            tv_time = (TextView) view.findViewById(R.id.tv_time);
            TextView tv_time_period = (TextView) customView.findViewById(R.id.tv_time_period);
            TextView  tv_no_date = (TextView) customView.findViewById(R.id.tv_no_date);
            LinearLayout checkBox = (LinearLayout) customView.findViewById(R.id.ll_checkbox);
            checkBox.setVisibility(View.GONE);

            TextView tv_coupon_price = (TextView)customView.findViewById(R.id.tv_coupon_price);
            ImageView iv_head = (ImageView) customView.findViewById(R.id.iv_avatar);
            LinearLayout ll_delete = (LinearLayout) customView.findViewById(R.id.ll_delete);
            ll_delete.setVisibility(View.VISIBLE);

            ll_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position >= 0){
                        if(couponList.size()>=1){
                            couponList.remove(position);
                            setDotLayout(couponList);
                            carouselView.setViewListener(viewListener);
                            llAddTop.setVisibility(View.GONE);
                            llBottom.setVisibility(View.VISIBLE);
                            llBottomCoupon.setVisibility(View.VISIBLE);
                        }else {
                            llAddTop.setVisibility(View.VISIBLE);
                            llBottom.setVisibility(View.GONE);
                            llBottomCoupon.setVisibility(View.GONE);
                        }

                    }

                }
            });
            carouselView.setIndicatorGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);

            if(couponList!=null && couponList.size()>0){

                Coupon coupon = couponList.get(position);
                String couponName = coupon.getName();

                Float limit = coupon.getLimit();
                String price = "";
                if(coupon.getMoney()!=null && coupon.getMoney()!=0.0){
                    BigDecimal money = new BigDecimal(coupon.getMoney());
                    String prices =  new DecimalFormat("0.00").format(money.setScale(2, BigDecimal.ROUND_DOWN).doubleValue()).toString();
                    price = "<font size = '40'>"+prices+"</font>";
                }else {
                    price = "<font size = '40'>"+"0.00"+"</font>";
                }


                String noItem = coupon.getNoItem();
                Integer timePeriod =coupon.getTimePeriod();
                String noDate = coupon.getNoDate();
                //0：没有日期限制；
                //1：周六，周日不可用；
                //2：自定义日期不可用
                if(noDate !=null ){

                    tv_no_date.setText(noDate);
                }else {
                    tv_no_date.setText("");
                }
                if(timePeriod !=null ){

                    if(timePeriod == 1){
                        tv_time_period.setText("全天");
                    }else if(timePeriod == 2){
                        tv_time_period.setText("午时");
                    }else if(timePeriod == 3){
                        tv_time_period.setText("晚时可用");
                    }
                }else {
                    tv_time_period.setText("");
                }


                if("CPC".equals(coupon.getType())){
                    tv_name.setText(Html.fromHtml(price)+"元");
                    iv_head.setBackgroundResource(R.drawable.ic_coupon_price);
                }else if("CPD".equals(coupon.getType())){
                    Float discount = coupon.getDiscount();

                    iv_head.setBackgroundResource(R.drawable.ic_coupon_discount);
                }else if("CPO".equals(coupon.getType())){
                    iv_head.setBackgroundResource(R.drawable.ic_coupon_prese);
                }else if("CPU".equals(coupon.getType())){
                    iv_head.setBackgroundResource(R.drawable.ic_coupon_dishes);
                }else if("CPJ".equals(coupon.getType())){
                    iv_head.setBackgroundResource(R.drawable.ic_coupon_paidui);
                }else if("CPT".equals(coupon.getType())){  //其他
                    iv_head.setBackgroundResource(R.drawable.ic_coupon_other);
                }
                tv_coupon_price.setText(noItem!=null&&!"".equals(noItem)?noItem:"");

                if(couponName !=null && !"".equals(couponName) ){
                    tvCouponName.setText(couponName);
                }else {
                    tvCouponName.setText("");
                }

            }


            return customView;
        }
    };
    
    private void setDateAndDurColor(){
        tvDateLabel.setTextColor(getResources().getColor(R.color.btn_color_blue));
        leftView.setBackground(getResources().getDrawable(R.color.btn_color_blue));
        tvDurLaybel.setTextColor(getResources().getColor(R.color.campaign_new_font_gray));
        rightView.setBackground(getResources().getDrawable(R.color.campaign_new_font_gray));
        rightView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData() {

        storeId =  SPUtils.getInstance().getString("storeId");
        storeName = SPUtils.getInstance().getString("storeName");
        brandLogo = SPUtils.getInstance().getString("brandLogo");
        storeIdArray= SPUtils.getInstance().getString("storeIdArrayCoupon");
        storeNameArray= SPUtils.getInstance().getString("storeNameArrayCoupon");

        brandLogoCoupon = SPUtils.getInstance().getString("brandLogoCoupon");

        brandPostion = SPUtils.getInstance().getInt("brandPostionCoupon");

        storeIdCouponType = SPUtils.getInstance().getString("storeIdCouponType");
    }

    @OnClick({R.id.iv_back,R.id.tv_select_shop,R.id.btn_add_coupon,
            R.id.ll_left_dates,R.id.ll_right_durations,R.id.ll_start,R.id.ll_end,
            R.id.btn_pre,R.id.btn_next,R.id.iv_left_subtract,R.id.iv_right_add,
            R.id.iv_left_subtract2,R.id.iv_right_add2})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.startActivity(CampaignListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
                break;
            case R.id.btn_add_coupon:
                Intent intent = new Intent(CampaignNewNextActivity.this,CampaignCouponListActivity.class);
                ActivityUtils.startActivityForResult(CampaignNewNextActivity.this,intent,requestCode,R.anim.slide_in_right,R.anim.slide_out_left);
                break;
            case R.id.tv_select_shop:  //开始时间

                if(storeList != null && storeList.size()>0){
                    showPopView(this);
                }else {
                    presenter.queryBrandAndStoreList(this,userId,isCampaignAndCouponStore,false);
                }

                break;
            case R.id.ll_left_dates:  //  //按固定日期
                llDate.setVisibility(View.VISIBLE);
                llDays.setVisibility(View.GONE);
                tvDateLabel.setTextColor(getResources().getColor(R.color.btn_color_blue));
                leftView.setBackground(getResources().getDrawable(R.color.btn_color_blue));
                tvDurLaybel.setTextColor(getResources().getColor(R.color.campaign_new_font_gray));
                rightView.setBackground(getResources().getDrawable(R.color.campaign_new_font_gray));
                leftView.setVisibility(View.VISIBLE);
                rightView.setVisibility(View.INVISIBLE);
                break;
            case R.id.ll_right_durations: //按固定时长
                llDate.setVisibility(View.GONE);
                llDays.setVisibility(View.VISIBLE);
                tvDateLabel.setTextColor(getResources().getColor(R.color.campaign_new_font_gray));
                leftView.setBackground(getResources().getDrawable(R.color.campaign_new_font_gray));

                tvDurLaybel.setTextColor(getResources().getColor(R.color.btn_color_blue));
                rightView.setBackground(getResources().getDrawable(R.color.btn_color_blue));
                leftView.setVisibility(View.INVISIBLE);
                rightView.setVisibility(View.VISIBLE);

                break;
            case R.id.ll_start:  //开始时间
                isStartDate =true;
                pvCustomTime.show();

                break;
            case R.id.ll_end: //结束时间
                isStartDate =false;
                pvCustomTime.show();
                break;
            case R.id.btn_pre: //上一步


                break;
            case R.id.btn_next: //下一步


                break;
            case R.id.iv_left_subtract: //第一个加减
                getValidDay--;
                if(getValidDay<0){
                    getValidDay = 0;
                }

                if( getValidDay == 0){
                    tvValidDays.setText("当");
                    ivLeftSubtract.setBackgroundResource(R.drawable.ic_gray_unsubtract);
                }else {
                    tvValidDays.setText(getValidDay+"");
                    ivLeftSubtract.setBackgroundResource(R.drawable.ic_blue_added);
                }

                edtDayDate.setText(getValidDay+"");
                break;
            case R.id.iv_right_add:
                getValidDay++;
                if( getValidDay >= 99){
                    ToastUtils.showShort("最高有效为99天！");
                    getValidDay = 99;
                    ivRightAdd.setBackgroundResource(R.drawable.ic_gray_add);
                }else {
                    ivRightAdd.setBackgroundResource(R.drawable.ic_blue_added);
                }
                if(getValidDay>=1){
                    ivLeftSubtract.setBackgroundResource(R.drawable.ic_blue_added);
                }

                tvValidDays.setText(getValidDay+"");

                edtDayDate.setText(getValidDay+"");
                break;
            case R.id.iv_left_subtract2: //第二个加减
                couponDateDay--;
                if(couponDateDay<1){
                    couponDateDay = 1;
                }

                if( couponDateDay == 1){
                    tvCouponDays.setText("1");
                    ivLeftSubtract2.setBackgroundResource(R.drawable.ic_gray_unsubtract);
                }else {
                    tvCouponDays.setText(couponDateDay+"");
                    ivLeftSubtract2.setBackgroundResource(R.drawable.ic_blue_added);
                }
                edtDayDate2.setText(couponDateDay+"");
                break;
            case R.id.iv_right_add2:
                couponDateDay++;

                if(couponDateDay>=2){
                    ivLeftSubtract2.setBackgroundResource(R.drawable.ic_blue_added);
                }
                tvCouponDays.setText(couponDateDay+"");
                edtDayDate2.setText(couponDateDay+"");
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(CampaignNewNextActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }


    //选择多个店铺
    protected void  showPopView(Context context){

        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_campaign_select_more_store,null);
        //创建并显示popWindow
        popWindow= new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopDown)
                .setView(contentView)
                .show(tvTitle);

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

                    String storeIdA = storeIdSb.toString();
                    if(storeIdA==null || "".equals(storeIdA)){
                        return;
                    }
                    storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                    storeId = storeIdArr;
                    storeName = "全部店铺";

                    storeIdCouponType = "A";
                    if(storeIdSb!=null ||!"".equals(storeIdSb)){
                        if(storeIdSb.toString().contains("-")){
                            storeIdArr = storeIdSb.substring(0,storeIdSb.length() - 1);
                            storeId = storeIdArr;
                            tvStoreNames.setText(storeNameSb.toString());

                            SPUtils.getInstance().put("storeIdCouponType","A");
                            SPUtils.getInstance().put("storeIdArrayCoupon",storeIdArr);
                            SPUtils.getInstance().put("storeNameArrayCoupon","全部店铺");
                        }
                        tvStoreNames.setText(storeNameSb.toString());
                    }else {
                        tvStoreNames.setText("");
//                        ToastUtils.showShort("请选择店铺");
                    }

                }
                cbAllStore.setChecked(true);
                cbAllStore.setButtonDrawable(R.drawable.ic_correct);
                tvAllStore.setTextColor(getResources().getColor(R.color.font_color_blue));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_deep));
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
                    storeIdCouponType  = "D";
                    String storeIdA = storeIdSb.toString();
                    if(storeIdA==null || "".equals(storeIdA)){
                        return;
                    }
                    storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                    storeId = storeIdArr;
                    storeName = "全部自营";
                    myAdapter.notifyDataSetChanged();
                    if(storeIdSb!=null ||!"".equals(storeIdSb)){
                        if(storeIdSb.toString().contains("-")){
                            storeIdArr = storeIdSb.substring(0,storeIdSb.length() - 1);
                            storeId = storeIdArr;
                            tvStoreNames.setText(storeNameSb.toString());

                            SPUtils.getInstance().put("storeIdCouponType","D");
                            SPUtils.getInstance().put("storeIdArrayCoupon",storeIdArr);
                            SPUtils.getInstance().put("storeNameArrayCoupon","全部自营");
                        }

                    }else {
                        tvStoreNames.setText("");
//                        ToastUtils.showShort("请选择店铺");
                    }


                }
                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                cbSelfStore.setChecked(true);
                cbSelfStore.setButtonDrawable(R.drawable.ic_correct);
                tvSelfStore.setTextColor(getResources().getColor(R.color.font_color_blue));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_deep));
            }
        });
        rlJoinStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                cbJoinStore.setChecked(true);
                cbJoinStore.setButtonDrawable(R.drawable.ic_correct);
                tvJoinStore.setTextColor(getResources().getColor(R.color.font_color_blue));

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
                    String storeIdA = storeIdSb.toString();
                    if(storeIdA==null || "".equals(storeIdA)){
                        return;
                    }
                    storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);

                    storeIdCouponType  = "J";
                    storeName = "全部加盟";
                    storeId = storeIdArr;
                    myAdapter.notifyDataSetChanged();
                    if(storeIdSb.toString()!=null ||!"".equals(storeIdSb.toString())){

                        if(storeIdSb.toString().contains("-")){

                            storeIdArr = storeIdSb.substring(0,storeIdSb.length() - 1);
                            storeId = storeIdArr;
                            SPUtils.getInstance().put("storeIdCouponType","J");
                            SPUtils.getInstance().put("storeIdArrayCoupon",storeIdArr);
                            SPUtils.getInstance().put("storeNameArrayCoupon","全部加盟");
                        }
                        tvStoreNames.setText(storeNameSb.toString());
                    }else {
                        tvStoreNames.setText("");
                    }
                    
                }
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
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

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
                        /*if("A".equals(storeIdCouponType)||"D".equals(storeIdCouponType)||"J".equals(storeIdCouponType)){   //全部店铺  自营  加盟

                        }*/
                        //storeName 为全部店铺  自营  加盟
                        tvStoreNames.setText(storeName);
                        tvStoreNames.refreshText();
                        SPUtils.getInstance().put("storeIdArrayCoupon",storeId);
                        SPUtils.getInstance().put("storeNameArrayCoupon",storeName);
                        if(branList !=null && branList.size()>0){

                            brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                            if(brandLogo!=null){
                                setBrandLogo();
                            }
                            SPUtils.getInstance().put("brandLogoCoupon",brandLogo);
                        }
                        popWindow.dismiss();
                    }else {
                        for (Store store: storeList){
                            if(store!=null){
                                if(store.isChecked()){
                                    String name = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                    storeNameSb.append(name);
                                    storeIdSb.append(store.getStoreId()).append(",");
                                    storeNameSb2.append(store.getStoreName()).append(",");
                                }

                            }
                        }
                        tvStoreNames.setText(storeNameSb.toString());
                        tvStoreNames.refreshText();
                        String storeIdA= storeIdSb.toString();
                        if(storeIdSb==null ||"".equals(storeIdA)){
                            ToastUtils.showShort("请选择店铺");
                            return;
                        }
                        storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                        String storeNameA = storeNameSb2.toString();
                        if(storeNameA==null ||"".equals(storeNameA)){
                            ToastUtils.showShort("请选择店铺");
                            return;
                        }
                        storeId = storeIdArr;
                        storeName = storeNameSb.toString();
                        SPUtils.getInstance().put("storeIdCouponType","");
                        SPUtils.getInstance().put("storeIdArrayCoupon",storeIdArr);
                        SPUtils.getInstance().put("storeNameArrayCoupon",storeNameSb.toString());
                        if(branList !=null && branList.size()>0){

                            brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                            if(brandLogo!=null){
                                setBrandLogo();
                            }
                            SPUtils.getInstance().put("brandLogoCoupon",brandLogo);
                        }
                        popWindow.dismiss();
                    }

                }
            }
        });


        MoreBrandListAdapter brandListAdapter = new MoreBrandListAdapter(this,branList);
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

        storeIdCouponType = SPUtils.getInstance().getString("storeIdCouponType");

        if(storeIdCouponType!=null && !"".equals(storeIdCouponType)){
            if("A".equals(storeIdCouponType)){

                cbAllStore.setChecked(true);
                cbAllStore.setButtonDrawable(R.drawable.ic_correct);
                tvAllStore.setTextColor(getResources().getColor(R.color.font_color_blue));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

            }else if("D".equals(storeIdCouponType)){

                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                cbSelfStore.setChecked(true);
                cbSelfStore.setButtonDrawable(R.drawable.ic_correct);
                tvSelfStore.setTextColor(getResources().getColor(R.color.font_color_blue));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_deep));
            }else if("J".equals(storeIdCouponType)){
                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                cbJoinStore.setChecked(true);
                cbJoinStore.setButtonDrawable(R.drawable.ic_correct);
                tvJoinStore.setTextColor(getResources().getColor(R.color.font_color_blue));
            }
            if(storeList!=null&& storeList.size()>0){  //自定义默认不选中
                for (Store store:storeList){
                    if(store!=null){
                        store.setChecked(false);
                    }

                }
                myAdapter.notifyDataSetChanged();

            }
            storeId = storeIdCouponType;
        }else {
            //分类默认不选中，自定义选中
            cbAllStore.setChecked(false);
            cbAllStore.setButtonDrawable(R.color.white);
            tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

            cbSelfStore.setChecked(false);
            cbSelfStore.setButtonDrawable(R.color.white);
            tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

            cbJoinStore.setChecked(false);
            cbJoinStore.setButtonDrawable(R.color.white);
            tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_deep));
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
                storeIdCouponType = "";
                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

            }
        });
    }


    private void setBrandLogo(){
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_banner_default)
                .error(R.drawable.ic_banner_default)
                .fallback(R.drawable.ic_banner_default);

        Glide.with(this).load(brandLogo).apply(requestOptions).into(ivBrand);
    }



    private void initDatePicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 12, 28);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
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
                                pvCustomTime.dismiss();

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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == 2222){
            if(requestCode == 2222){
                 couponList = (ArrayList<Coupon>)data.getSerializableExtra("selectCouponList"); //newStaffMemberId
                if(couponList!=null && couponList.size()>0 ){
                    llAddTop.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
                    llBottomCoupon.setVisibility(View.VISIBLE);
                    setDotLayout(couponList);
                }else {
                    llAddTop.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.GONE);
                    llBottomCoupon.setVisibility(View.GONE);
                }
            }
        }else {
        }
    }

    private void setDotLayout(ArrayList<Coupon> dataList){
        mDotsIV.clear();
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

        carouselView.setPageCount(dataList.size());
        carouselView.setSlideInterval(4000);
    }



}
