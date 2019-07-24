package com.zwx.scan.app.feature.couponmanage;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zwx.library.pickerview.picker.builder.OptionsPickerBuilder;
import com.zwx.library.pickerview.picker.builder.TimePickerBuilder;
import com.zwx.library.pickerview.picker.listener.CustomListener;
import com.zwx.library.pickerview.picker.listener.OnOptionsSelectListener;
import com.zwx.library.pickerview.picker.listener.OnTimeSelectListener;
import com.zwx.library.pickerview.picker.view.OptionsPickerView;
import com.zwx.library.pickerview.picker.view.TimePickerView;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.CardBean;
import com.zwx.scan.app.data.bean.UnavailableDate;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.user.LoginActivity;
import com.zwx.scan.app.utils.ButtonUtils;
import com.zwx.scan.app.utils.EmojiInputFilter;
import com.zwx.scan.app.utils.MaxTextLengthFilter;
import com.zwx.scan.app.widget.dialog.DialogUIUtils;
import com.zwx.scan.app.widget.dialog.bean.TieBean;
import com.zwx.scan.app.widget.dialog.listener.DialogUIItemListener;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 优惠券新建
 */
public class CouponNewActivity extends BaseActivity<GiveCouponContract.Presenter> implements GiveCouponContract.View, View.OnClickListener {
    //初始化Logger
    protected static Logger log = Logger.getLogger(LoginActivity.class);
    @BindView(R.id.iv_back)
    protected ImageView ivBack;
    @BindView(R.id.tv_right)
    protected TextView tvRight;
    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.edt_coupon_name)
    protected EditText edt_coupon_name;


    @BindView(R.id.rl_zengpin)
    protected RelativeLayout rl_zengpin;

    @BindView(R.id.tv_zengpin_label)
    protected TextView tv_zengpin_label;
    @BindView(R.id.edt_zengpin)
    protected EditText edt_zengpin;

    @BindView(R.id.ll_daijin_mem_kan)
    protected LinearLayout ll_daijin_mem_kan;

    @BindView(R.id.rl_daijin)
    protected RelativeLayout rl_daijin;
    @BindView(R.id.tv_daijin_label)
    protected TextView tv_daijin_label;
    @BindView(R.id.edt_edu)
    protected EditText edt_edu;
    @BindView(R.id.tv_yuan)
    protected TextView tv_yuan;

    @BindView(R.id.rl_user_men_kan)
    protected RelativeLayout rl_user_men_kan;
    @BindView(R.id.tv_user_men_kan_label)
    protected TextView tv_user_men_kan_label;
    @BindView(R.id.edt_user_men_kan)
    protected EditText edt_user_men_kan;


    @BindView(R.id.rl_user_shiduan)
    protected RelativeLayout rl_user_shiduan;
    @BindView(R.id.tv_user_shiduan)
    protected TextView tv_user_shiduan;

    @BindView(R.id.rl_is_bu_ke_yong_time)
    protected RelativeLayout rl_is_bu_ke_yong_time;
    @BindView(R.id.tv_is_bu_ke_yong_time)
    protected TextView tv_is_bu_ke_yong_time;

    @BindView(R.id.rl_bu_ke_yong)
    protected RelativeLayout rl_bu_ke_yong;

    @BindView(R.id.tv_bu_ke_yong_time)
    protected TextView tv_bu_ke_yong_time;

//    @BindView(R.id.date_list_view)
//    protected MyListView date_list_view;
    @BindView(R.id.ll_coupon_add_date)
    protected LinearLayout ll_coupon_add_date;

    @BindView(R.id.ll_select_date)
    protected LinearLayout ll_select_date;
    @BindView(R.id.ll_add_date)
    protected LinearLayout ll_add_date;


    @BindView(R.id.rl_qi_ta_coupon_gongyong)
    protected RelativeLayout rl_qi_ta_coupon_gongyong;

    @BindView(R.id.tv_qi_ta_coupon_gongyong)
    protected TextView tv_qi_ta_coupon_gongyong;

    @BindView(R.id.rl_coupon_zhuan_zeng)
    protected RelativeLayout rl_coupon_zhuan_zeng;
    @BindView(R.id.tv_coupon_zhuan_zeng)
    protected TextView tv_coupon_zhuan_zeng;

    @BindView(R.id.edt_bu_ke_yong_desc)
    protected EditText edt_bu_ke_yong_desc;

    @BindView(R.id.tv_bu_ke_yong_num)
    protected TextView tv_bu_ke_yong_num;

    @BindView(R.id.edt_remark)
    protected EditText edt_remark;

    @BindView(R.id.tv_remark_num)
    protected TextView tv_remark_num;

    @BindView(R.id.btn_save)
    protected Button btn_save;

    @BindView(R.id.btn_user)  //启用
    protected Button btn_user;
    @BindView(R.id.btn_pre)  //预览
    protected Button btn_pre;

    @BindView(R.id.ll_user)  //启用
    protected LinearLayout ll_user;
    protected OptionsPickerView pvShiDduan, pvIsBuKeYongTime, pvBuKeYongTime, pvIsCommonCouponAndZhuanZeng;
    private CouponSelectDateAdapter mAdapter = null;

    private String title = "";
    private String userId = "";
    private String storeId = "";
    private String compId = "";
    private String couponJson = "";

    //自定义不可用字段
    private String startDate = "";
    private String endDate = "";

    protected String isEditCoupon = "NO";

    private String isCopyCreate = "YES";
    protected String couponId;
    protected static String couponStatus = "NEW";   //活动状态
    protected static String couponType = "";  //优惠券类型：VCS單次儲值卡，VCM多次儲值卡，CPC代金券，CPD折扣券，CPO菜品券，CPU菜品券、CPJ插队券、CPT其他
    protected String couponName;  //优惠券名称
    protected String obejct;
    protected String zengPinName;         // 赠品名称
    protected String goodPrize;  //商品价格
    protected String menKanPrize;  //使用门槛
    protected String timePerio = "1";
    protected String shiduan = "1";    //使用时段
    protected String isBuKeYongTime = "1"; //是否不可用时间

    protected List<UnavailableDate> unavailableDateList = new ArrayList<>();
    /**
     * 不可用日期 的 编码
     * <p>
     * 第一位  表示没有不可用日期0 和 有不可用日期1
     * 第二位  为1表示周六日不可用 为0表示 未选择
     * 第三位  为1表示周六、日及法定节假日不可用， 为0表示 未选择： 第二位和第三位互斥
     * 第4位  法定节假日不可用 1 为是 0为否
     * 第5位  自定义不可用时间段
     * <p>
     * 11000 周六、日不可用
     * 10100 周六、日及法定节假日不可用
     * 10010 法定节假日不可用
     * 00000 没有不可用日期
     * 11001 周六、日不可用…
     * 10101 周六、日及法定节假日不可用…
     * 10011 法定节假日不可用…
     */
    protected String dateCode = "11000"; //不可用时间段编码
    protected String noDate ="周六、日不可用";
    protected String timePeriodStr = "全天";
    protected String buKeYongTime = "1";  //不可用时间

    protected String isCommonCoupon = "1";  //是否共用
    protected String isZhuanZeng = "1";  //是否转增
    protected String buKeYongDesc;  //不可用项目
    protected String remark;    //备注

    protected Boolean share = true;
    protected Boolean assign = true;
    protected List<CardBean> timePeriodBeans = null;   //时段



    protected List<CardBean> noDateBeans = null;
    protected boolean isCommonCouponAndZhuanZeng = true;   //区分共用和是否转增
//    protected TimePickerView pvStartTime, pvEndTime;


    protected boolean isStartDate = false;

    private int mBtnAddId = 1000;
    private int mEtId = 2000;

    private int mEtHeight;
    private LinkedList<LinearLayout> mDelList;
    private LinearLayout mLlContent;


    protected static CouponDetailBeanTwo coupon = new CouponDetailBeanTwo();
    protected StringBuilder startDateSb = new StringBuilder();
    protected StringBuilder endDateSb = new StringBuilder();

    protected  UnavailableDate unavailableDate = new UnavailableDate();
    int curView = 0;

    private  Boolean isAddDate = true;
    private List<View> viewList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon_new;
    }

    @Override
    protected void initView() {
        setInjector();

        title = getIntent().getStringExtra("title");
        userId = SPUtils.getInstance().getString("userId");
        storeId = SPUtils.getInstance().getString("storeId");
        compId = SPUtils.getInstance().getString("compId");
        couponId = getIntent().getStringExtra("couponId");
        //是否编辑还是新建
        isEditCoupon = getIntent().getStringExtra("isEditCoupon");
        isCopyCreate = getIntent().getStringExtra("isCopyCreate");

        couponStatus = getIntent().getStringExtra("couponStatus");
        couponType = getIntent().getStringExtra("couponType");
        edt_coupon_name.setSelection(edt_coupon_name.getText().toString().trim().length());
        edt_coupon_name.setFilters(new InputFilter[]{new EmojiInputFilter()});
        edt_coupon_name.setFilters(new InputFilter[]{new MaxTextLengthFilter(15)});

        edt_edu.setSelection(edt_edu.getText().toString().trim().length());
        edt_user_men_kan.setSelection(edt_user_men_kan.getText().toString().trim().length());
        edt_bu_ke_yong_desc.setSelection(edt_bu_ke_yong_desc.getText().toString().trim().length());
        edt_remark.setSelection(edt_remark.getText().toString().trim().length());
        edt_zengpin.setFilters(new InputFilter[]{new EmojiInputFilter()});
        edt_remark.setFilters(new InputFilter[]{new EmojiInputFilter()});
        edt_bu_ke_yong_desc.setFilters(new InputFilter[]{new EmojiInputFilter()});
        viewList.add(edt_coupon_name);
        viewList.add(edt_edu);
        viewList.add(edt_user_men_kan);
        viewList.add(edt_bu_ke_yong_desc);
        viewList.add(edt_remark);
        viewList.add(edt_zengpin);
        viewList.add(rl_user_shiduan);
        viewList.add(tv_user_shiduan);
        viewList.add(rl_bu_ke_yong);
        viewList.add(tv_bu_ke_yong_time);
        viewList.add(tv_is_bu_ke_yong_time);
        viewList.add(ll_coupon_add_date);
        viewList.add(ll_select_date);
        viewList.add(ll_add_date);



        viewList.add(tv_qi_ta_coupon_gongyong);
        viewList.add(rl_is_bu_ke_yong_time);
        viewList.add(rl_coupon_zhuan_zeng);
        viewList.add(tv_coupon_zhuan_zeng);
        viewList.add(tv_bu_ke_yong_num);


        if ("YES".equals(isEditCoupon)) {   //编辑

            if ("YES".equals(isCopyCreate)) {  //编辑页面复制并创建
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setText("复制并创建");
            } else {
                tvRight.setVisibility(View.GONE);
                tvRight.setText("");
            }
        } else {    //新建
            tvRight.setVisibility(View.GONE);
        }

        initTimePeriodPicker();
        initNoDatePicker();
        mDelList = new LinkedList<>();

        if("NEW".equals(couponStatus)){
            setViewsEnable(true);
            btn_save.setVisibility(View.VISIBLE);
            btn_user.setVisibility(View.VISIBLE);
            btn_pre.setVisibility(View.VISIBLE);
            ll_user.setVisibility(View.VISIBLE);
            ll_add_date.setVisibility(View.VISIBLE);
        }else if("EDIT".equals(couponStatus)) {
            btn_save.setVisibility(View.VISIBLE);
            btn_user.setVisibility(View.VISIBLE);
            btn_pre.setVisibility(View.VISIBLE);
            ll_user.setVisibility(View.VISIBLE);
            ll_add_date.setVisibility(View.VISIBLE);
            setViewsEnable(true);
            presenter.doCouponLoad(this,storeId,compId,couponId);
        }else {
            presenter.doCouponLoad(this,storeId,compId,couponId);
            setViewsEnable(false);
            btn_save.setVisibility(View.VISIBLE);
            btn_save.setText("返回");
            btn_user.setVisibility(View.GONE);
            btn_pre.setVisibility(View.VISIBLE);
            ll_user.setVisibility(View.GONE);
            ll_add_date.setVisibility(View.GONE);
        }

        if(coupon != null){
            couponName = coupon.getName();

            if(coupon.getLimit()!=null && !"".equals(coupon.getLimit())){
                menKanPrize = RegexUtils.getDoubleString(Double.parseDouble(String.valueOf(coupon.getLimit())));

            }else {
                menKanPrize = "0";
            }
            //门槛
            edt_user_men_kan.setText(menKanPrize);
            if(coupon.getObject()!=null && !"".equals(coupon.getObject())){
                zengPinName = coupon.getObject();
            }else {
                zengPinName = "";
            }
            if(coupon.getMoney()!=null && !"".equals(coupon.getMoney())){

                goodPrize = RegexUtils.getDoubleString(Double.parseDouble(String.valueOf(coupon.getMoney())));
            }else {
                goodPrize = "";
            }
            if ("CPC".equals(couponType)) {   //代金券
                edt_edu.setText(goodPrize);
            } else if ("CPD".equals(couponType)) {   //折扣
                edt_edu.setText(goodPrize);
            } else if ("CPO".equals(couponType)) {  //赠品券

                edt_edu.setText(goodPrize);
                edt_zengpin.setText(zengPinName);
            } else if ("CPU".equals(couponType)) {  //菜品券

                edt_zengpin.setText(zengPinName);
                edt_edu.setText(goodPrize);
            } else if ("CPI".equals(couponType)) {  //其他优惠券

                if(coupon.getOther() != null && !"".equals(coupon.getOther())){
                    zengPinName = coupon.getOther();
                }
                edt_zengpin.setText(zengPinName);
            }


            dateCode = coupon.getDateCode();
            noDate = coupon.getNoDate();
            if(dateCode!=null && !"".equals(dateCode)) {
             /*   * 11000 周六、日不可用
              * 10100 周六、日及法定节假日不可用
              * 10010 法定节假日不可用
              * 00000 没有不可用日期
              * 11001 周六、日不可用…
              * 10101 周六、日及法定节假日不可用…
              * 10011 法定节假日不可用…*/
                if (!"00000".equals(dateCode)) {

                    if("11000".equals(dateCode)){  //不可用时间  末尾0表示没有时间段值
                        buKeYongTime = "1";
                    }else if("10100".equals(dateCode)){ //周六、日及法定节假日不可用
                        buKeYongTime = "2";
                    }else if("10010".equals(dateCode)){ //法定节假日不可用
                        buKeYongTime = "3";
                    }else if("11001".equals(dateCode)){//周六、周日不可用…   //末尾1表示时间段有值 ，否则无值
                        buKeYongTime = "1";
                    }else if("10101".equals(dateCode)){//周六、日及法定节假日不可用…
                        buKeYongTime = "2";
                    }else if("10011".equals(dateCode)){ //法定节假日不可用
                        buKeYongTime = "3";
                    }
                    if(noDate !=null && !"".equals(noDate)){
                        tv_bu_ke_yong_time.setText(noDate);
                    }else {
                        tv_bu_ke_yong_time.setText("");
                    }
                    isBuKeYongTime = "1";
                    tv_is_bu_ke_yong_time.setText("是");
                }else {
                    isBuKeYongTime = "0";
                    buKeYongTime = "";
                    tv_is_bu_ke_yong_time.setText("否");

                }
            }else {
                isBuKeYongTime = "0";
                buKeYongTime = "";
            }

            String timePeriod = coupon.getTimePeriod();
            if(timePeriod !=null  && !"".equals(timePeriod)){
                shiduan = String.valueOf(timePeriod);
                if( "1".equals(timePeriod)){
                    timePeriodStr = "全天";
                }else if( "2".equals(timePeriod)){
                    timePeriodStr = "午市" ;
                }else if( "3".equals(timePeriod)){
                    timePeriodStr = "晚市";
                }
                tv_user_shiduan.setText(timePeriodStr);
            }else {
               tv_user_shiduan.setText("");
            }

            unavailableDateList = coupon.getUnavailableDate();
            if(unavailableDateList != null && unavailableDateList.size()>0){
                for (UnavailableDate unavailableDate : unavailableDateList) {
                    if (unavailableDate != null) {
                        String startDate = unavailableDate.getStartDate();
                        String endDate = unavailableDate.getEndDate();
                        String startDate2 = "";
                        String endDate2 = "";
                        if(startDate != null && !"".equals(startDate)){
                            startDate2 = startDate.replace("-",".");
                        }
                        if(endDate != null && !"".equals(endDate)){
                            endDate2 = endDate.replace("-",".");
                        }

                        addDateItem(startDate2,endDate2);

                    }
                }
            }

             share = coupon.getShare();
            if(share!=null && share.booleanValue()){
                isCommonCoupon = "1";
            }else {
                isCommonCoupon = "0";
            }

             assign = coupon.getAssign();

            if(assign!=null && assign.booleanValue()){
                isZhuanZeng = "1";
            }else {
                isZhuanZeng = "0";
            }
        }
        if("1".equals(shiduan)){
            timePeriodStr = "全天";
        }else if("2".equals(shiduan)){
            timePeriodStr = "午市";
        }else if("3".equals(shiduan)){
            timePeriodStr = "晚市";
        }
        tv_user_shiduan.setText(timePeriodStr);
        if("1".equals(isBuKeYongTime)){
            ll_select_date.setVisibility(View.VISIBLE);
            rl_bu_ke_yong.setVisibility(View.VISIBLE);
            tv_is_bu_ke_yong_time.setText("是");

            if("1".equals(buKeYongTime)){
                noDate = "周六、日不可用";
            }else if("2".equals(buKeYongTime)){
                noDate = "周六、日及法定节假日不可用";
            }else if("3".equals(buKeYongTime)){
                noDate = "法定节假日不可用";
            }else {
                noDate = "周六、日不可用";
            }
            tv_bu_ke_yong_time.setText(noDate);
        }else if("0".equals(isBuKeYongTime)){
            ll_select_date.setVisibility(View.GONE);
            rl_bu_ke_yong.setVisibility(View.GONE);
            tv_is_bu_ke_yong_time.setText("否");
            tv_bu_ke_yong_time.setText("");
            buKeYongTime = "";
        }

        if("1".equals(isZhuanZeng)){
            tv_coupon_zhuan_zeng.setText("是");
        }else {
            tv_coupon_zhuan_zeng.setText("否");
        }

        if("1".equals(isCommonCoupon)){
            tv_qi_ta_coupon_gongyong.setText("是");
        }else {
            tv_qi_ta_coupon_gongyong.setText("否");
        }
    }

    private void setViewsEnable(boolean able) {
        for (View view : viewList) {
            view.setEnabled(able);
//            view.setFocusable(able);

        }
    }

    @Override
    protected void initData() {

        tvTitle.setText(title);

        setStyle();
        setLisenter();
    }

    private void setInjector() {
        DaggerGiveCouponComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .giveCouponModule(new GiveCouponModule(this))
                .build()
                .inject(this);
    }

    protected void setStyle() {

        //CPC代金券，CPD折扣券，CPO赠品券，CPU菜品券、CPJ插队券、CPT其他
        if ("CPC".equals(couponType)) {   //代金券
            rl_zengpin.setVisibility(View.GONE);
            edt_edu.setHint("50");
        } else if ("CPD".equals(couponType)) {
            rl_zengpin.setVisibility(View.GONE);
            tv_daijin_label.setText("折扣");
            tv_yuan.setText("折");
            edt_edu.setHint("7.8");
            edt_user_men_kan.setHint("消费满多少元可用");
        } else if ("CPO".equals(couponType)) {
            rl_zengpin.setVisibility(View.VISIBLE);
            tv_zengpin_label.setText("赠品名称");
            tv_daijin_label.setText("赠品价值");
            tv_yuan.setText("元");
            edt_zengpin.setHint("请输入兑换赠品名称");
            edt_edu.setHint("请输入兑换赠品的价格");
        } else if ("CPU".equals(couponType)) {

            rl_zengpin.setVisibility(View.VISIBLE);
            tv_zengpin_label.setText("菜名");
            tv_daijin_label.setText("菜价");
            tv_yuan.setText("元");
            edt_zengpin.setHint("请输入兑换菜品名称");
            edt_edu.setHint("请输入兑换菜品的价格");
        } else if ("CPI".equals(couponType)) {
            rl_zengpin.setVisibility(View.VISIBLE);
            tv_zengpin_label.setText("优惠内容");
            edt_zengpin.setHint("例：包间免费");
            rl_daijin.setVisibility(View.GONE);
            tv_daijin_label.setText("");
            tv_yuan.setText("元");
        }
    }

   /* private void setDateAdapter(){
        mAdapter = new CouponSelectDateAdapter(this);
        date_list_view.setAdapter(mAdapter);
//        mAdapter.setData(list());
    }*/


   private  void setLisenter(){
       edt_bu_ke_yong_desc.setFilters(new InputFilter[]{new MaxTextLengthFilter(500)});
       edt_bu_ke_yong_desc.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
               int descriptionLength = s.length();
               if(edt_bu_ke_yong_desc.getText().toString().trim().length()>0){
                   edt_bu_ke_yong_desc.setSelection(edt_bu_ke_yong_desc.getText().toString().trim().length());
                   tv_bu_ke_yong_num.setText(descriptionLength+"/500");
               }
           }
       });


       edt_remark.setFilters(new InputFilter[]{new MaxTextLengthFilter(500)});
       edt_remark.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
               int descriptionLength = s.length();
               if(edt_remark.getText().toString().trim().length()>0){
                   edt_remark.setSelection(edt_remark.getText().toString().trim().length());
                   tv_remark_num.setText(descriptionLength+"/500");
               }
           }
       });
   }


    @OnClick({R.id.iv_back, R.id.tv_right, R.id.tv_user_shiduan, R.id.tv_is_bu_ke_yong_time,
            R.id.tv_bu_ke_yong_time, R.id.tv_qi_ta_coupon_gongyong, R.id.tv_coupon_zhuan_zeng, R.id.ll_add_date, R.id.btn_save, R.id.btn_user, R.id.btn_pre})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.startActivity(CouponManageActivity.class, R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
                break;

            case R.id.tv_right:
                ToastUtils.showCustomShortBottom("创建成功");
                couponName = edt_coupon_name.getText().toString().trim();
                zengPinName = edt_zengpin.getText().toString().trim();
                goodPrize = edt_edu.getText().toString().trim();
                menKanPrize = edt_user_men_kan.getText().toString().trim();
                buKeYongDesc = edt_bu_ke_yong_desc.getText().toString().trim();
                remark = edt_remark.getText().toString().trim();
                //复制并创建
                isCopyCreate = "NO";
                isEditCoupon = "NO";
                couponStatus = "NEW";
                setUnavailableDate();
                curView =0;
                if(unavailableDateList != null && unavailableDateList.size()>0){
                    ll_coupon_add_date.removeAllViews();
                    for (UnavailableDate unavailableDate : unavailableDateList) {
                        if (unavailableDate != null) {
                            unavailableDate = unavailableDate;
                            String startDate = unavailableDate.getStartDate();
                            String endDate = unavailableDate.getEndDate();
                            String startDate2 = "";
                            String endDate2 = "";
                            if(startDate != null && !"".equals(startDate) ){
                                startDate2 = startDate.replace("-",".").substring(0,10);
                            }
                            if(endDate != null && !"".equals(endDate)){
                                endDate2 = endDate.replace("-",".").substring(0,10);
                            }
                           addDateItem(startDate2,endDate2);
                        }
                    }

                }else {
                    unavailableDateList = new ArrayList<>();
                }
                setCouponValue();


                if(coupon != null){
                    coupon.setName("");
                    coupon.setId(null);
                    edt_coupon_name.setText("");
                    coupon.setType(couponType);
                }
                tvRight.setVisibility(View.GONE);
                edt_coupon_name.setEnabled(true);
                edt_edu.setEnabled(true);
                edt_user_men_kan.setEnabled(true);
                edt_bu_ke_yong_desc.setEnabled(true);
                edt_bu_ke_yong_desc.setEnabled(true);
                edt_remark.setEnabled(true);
                edt_zengpin.setEnabled(true);
                rl_user_shiduan.setEnabled(true);
                tv_user_shiduan.setEnabled(true);

                rl_bu_ke_yong.setEnabled(true);

                tv_bu_ke_yong_time.setEnabled(true);

                tv_is_bu_ke_yong_time.setEnabled(true);

                ll_coupon_add_date.setEnabled(true);

                ll_select_date.setEnabled(true);

                ll_add_date.setEnabled(true);

                tv_qi_ta_coupon_gongyong.setEnabled(true);

                rl_is_bu_ke_yong_time.setEnabled(true);

                rl_coupon_zhuan_zeng.setEnabled(true);

                tv_coupon_zhuan_zeng.setEnabled(true);

                tv_bu_ke_yong_num.setEnabled(true);


                btn_save.setVisibility(View.VISIBLE);
                btn_save.setText("保存");
                btn_user.setVisibility(View.VISIBLE);
                btn_pre.setVisibility(View.VISIBLE);
                ll_user.setVisibility(View.VISIBLE);
                ll_add_date.setVisibility(View.VISIBLE);

                break;
            case R.id.tv_user_shiduan:  // 时段
                pvShiDduan.show();
                break;
            case R.id.tv_is_bu_ke_yong_time:    //设置不可用日期

                initDateCodePicker();

                break;
            case R.id.tv_bu_ke_yong_time:

                pvBuKeYongTime.show();
                break;
            case R.id.tv_qi_ta_coupon_gongyong:    //共享
                isCommonCouponAndZhuanZeng = true;
                setCouponGongYongPicker();
                break;
            case R.id.tv_coupon_zhuan_zeng:      //转增
                isCommonCouponAndZhuanZeng = false;
                setZhuanZengPicker();
                break;
            case R.id.ll_add_date:
                isAddDate = true;
                unavailableDateList.add(new UnavailableDate());
                addDateItem("","");
                break;

            case R.id.btn_save:   //保存
                if (coupon == null) {
                    coupon = new CouponDetailBeanTwo();
                }
                couponName = edt_coupon_name.getText().toString().trim();
                zengPinName = edt_zengpin.getText().toString().trim();
                goodPrize = edt_edu.getText().toString().trim();
                menKanPrize = edt_user_men_kan.getText().toString().trim();
                buKeYongDesc = edt_bu_ke_yong_desc.getText().toString().trim();
                remark = edt_remark.getText().toString().trim();

                coupon.setStatus("0");
                setUnavailableDate();


                if(check()){
                    return;
                }
                setCouponValue();
                couponJson = new Gson().toJson(coupon);
                if(!ButtonUtils.isFastDoubleClick(R.id.btn_save)){
                    if("NEW".equals(couponStatus)){
                        presenter.doCouponInsert(this, userId, storeId, compId, couponJson, startDate, endDate,"0");
                    }else if("EDIT".equals(couponStatus)){
                        presenter.doCouponUpdate(this, userId, storeId, compId, couponJson, startDate, endDate,"0");
                    }else {
                        ActivityUtils.startActivity(CouponManageActivity.class, R.anim.slide_in_left, R.anim.slide_out_right);
                        finish();
                    }
                }


                break;

            case R.id.btn_user:  //启用
                if (coupon == null) {
                    coupon = new CouponDetailBeanTwo();
                }
                couponName = edt_coupon_name.getText().toString().trim();
                zengPinName = edt_zengpin.getText().toString().trim();
                goodPrize = edt_edu.getText().toString().trim();
                menKanPrize = edt_user_men_kan.getText().toString().trim();
                buKeYongDesc = edt_bu_ke_yong_desc.getText().toString().trim();
                remark = edt_remark.getText().toString().trim();
                setUnavailableDate();

                if(check()){
                    return;
                }
                setCouponValue();


                coupon.setStatus("1");

                couponJson = new Gson().toJson(coupon);
                if(!ButtonUtils.isFastDoubleClick(R.id.btn_save)){
                    if("NEW".equals(couponStatus)){
                        presenter.doCouponInsert(this, userId, storeId, compId, couponJson, startDate, endDate,"1");
                    }else if("EDIT".equals(couponStatus)){
                        presenter.doCouponUpdate(this, userId, storeId, compId, couponJson, startDate, endDate,"1");
                    }
                }


                break;
            case R.id.btn_pre: //预览
                setPopDialog();
                break;


        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.startActivity(CouponManageActivity.class, R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    private boolean check() {
        if (couponName == null || "".equals(couponName)) {
            setDialog("请输入优惠券名称");
            return true;
        }

        /*if (couponName.length() > 15) {
            setDialog("优惠券名称长度不超过15位");
            return true;
        }*/

        if ("CPC".equals(couponType)) {   //代金券
            if (goodPrize == null || "".equals(goodPrize)) {
                setDialog("请输入代金额度");
                return true;
            }


        } else if ("CPD".equals(couponType)) {   //折扣
            if (goodPrize == null || "".equals(goodPrize)) {
                setDialog("请输入折扣");
                return true;
            }


        } else if ("CPO".equals(couponType)) {  //赠品区
            if (zengPinName == null || "".equals(zengPinName)) {
                setDialog("请输入赠品名称");
                return true;
            }


            if (goodPrize == null || "".equals(goodPrize)) {
                setDialog("请输入赠品价值");
                return true;
            }

            double price = Double.parseDouble(goodPrize);
            if(price>10000){
                setDialog("赠品价值最多10000元");
                return true;
            }


        } else if ("CPU".equals(couponType)) {  //菜品券
            if (zengPinName == null || "".equals(zengPinName)) {
                setDialog("请输入菜名");
                return true;
            }

            if (goodPrize == null || "".equals(goodPrize)) {
                setDialog("请输入菜价");
                return true;
            }


        } else if ("CPI".equals(couponType)) {  //其他优惠券
            if (zengPinName == null || "".equals(zengPinName)) {
                setDialog("请输入优惠内容");
                return true;
            }

        }

        if (menKanPrize == null || "".equals(menKanPrize)) {
            setDialog("请输入使用门槛");
            return true;
        }

        if ("1".equals(isBuKeYongTime)) {   //不可用时间 选项 为1 表示 是 有不可用时间，0 表示没有不可用时间

            if (!"00000".equals(dateCode)) { //是  末尾 0表示时间段无值 1表示有值
                if ("11001".equals(dateCode)) {
                    if (unavailableDateList != null && unavailableDateList.size() > 0) {
                        for (UnavailableDate unavailableDate : unavailableDateList) {
                            String startDate = unavailableDate.getStartDate();
                            String endDate = unavailableDate.getEndDate();

                            if ((startDate == null || "".equals(startDate))) {
                                setDialog("选择开始时间");
                                return true;
                            }

                            if(endDate == null || "".equals(endDate)){
                                setDialog("选择结束时间");
                                return true;
                            }
                        }
                    }
                } else if ("10101".equals(dateCode)) {
                    if (unavailableDateList != null && unavailableDateList.size() > 0) {
                        for (UnavailableDate unavailableDate : unavailableDateList) {
                            String startDate = unavailableDate.getStartDate();
                            String endDate = unavailableDate.getEndDate();

                            if ((startDate == null || "".equals(startDate))) {
                                setDialog("选择开始时间");
                                return true;
                            }

                            if(endDate == null || "".equals(endDate)){
                                setDialog("选择结束时间");
                                return true;
                            }
                        }
                    }
                } else if ("10011".equals(dateCode)) {
                    if (unavailableDateList != null && unavailableDateList.size() > 0) {
                        for (UnavailableDate unavailableDate : unavailableDateList) {
                            String startDate = unavailableDate.getStartDate();
                            String endDate = unavailableDate.getEndDate();

                            if ((startDate == null || "".equals(startDate))) {
                                setDialog("选择开始时间");
                                return true;
                            }

                            if(endDate == null || "".equals(endDate)){
                                setDialog("选择结束时间");
                                return true;
                            }
                        }
                    }
                }
            }
        }


        return false;
    }

    private void setCouponValue() {

        if ("CPC".equals(couponType)) {   //代金券
            coupon.setMoney(goodPrize);
        } else if ("CPD".equals(couponType)) {   //折扣

            coupon.setDiscount(goodPrize);

        } else if ("CPO".equals(couponType)) {  //赠品区
            coupon.setMoney(goodPrize);
            coupon.setObject(zengPinName);

        } else if ("CPU".equals(couponType)) {  //菜品券
            coupon.setMoney(goodPrize);
            coupon.setObject(zengPinName);
        } else if ("CPI".equals(couponType)) {  //其他优惠券
            coupon.setMoney(goodPrize);
            coupon.setOther(zengPinName);
        }
        coupon.setType(couponType != null && !"".equals(couponType) ? couponType : "");

        coupon.setName(couponName != null && !"".equals(couponName) ? couponName : "");

        if (menKanPrize != null && !"".equals(menKanPrize)) {  //门槛
            coupon.setLimit(menKanPrize);
        }
        if ("1".equals(shiduan)) {   //时段
            coupon.setTimePeriod("1");
        } else if ("2".equals(shiduan)) {
            coupon.setTimePeriod("2");
        } else if ("3".equals(shiduan)) {
            coupon.setTimePeriod("3");
        }
        if ("0".equals(isBuKeYongTime)) {   //是否有不可用时间
            dateCode = "00000";
            coupon.setDateCode("00000");
        } else if ("1".equals(isBuKeYongTime)) {   //不可用时间 选项 为1 表示 是 有不可用时间，0 表示没有不可用时间
            String dateCode2 = "1100";
            if("1".equals(buKeYongTime)){
                dateCode2 = "1100";
            }else if("2".equals(buKeYongTime)){
                dateCode2 = "1010";
            }else if("3".equals(buKeYongTime)){
                dateCode2 = "1001";
            }
            startDateSb = new StringBuilder();
            endDateSb = new StringBuilder();
            if(unavailableDateList != null && unavailableDateList.size()>0){
                for (UnavailableDate unavailableDate : unavailableDateList){
                    String startDate = unavailableDate.getStartDate();
                    String endDate = unavailableDate.getEndDate();
                    if((startDate != null && !"".equals(startDate)) && (endDate != null && !"".equals(endDate))){
                        startDateSb.append(unavailableDate.getStartDate()).append(",");
                        endDateSb.append(unavailableDate.getEndDate()).append(",");
                    }/*else if((startDate != null && !"".equals(startDate)) && (endDate == null || "".equals(endDate))){
                        startDateSb.append(unavailableDate.getStartDate()).append(",");
                        endDateSb.append(unavailableDate.getEndDate()).append(",");
                    }else if((startDate == null || "".equals(startDate)) && (endDate != null && !"".equals(endDate))){
                        startDateSb.append(unavailableDate.getStartDate()).append(",");
                        endDateSb.append(unavailableDate.getEndDate()).append(",");
                    }*/

                }

                if( (startDateSb.toString() != null && !"".equals(startDateSb.toString())) && (endDateSb.toString() != null && !"".equals( endDateSb.toString()))){
                    startDate =  startDateSb.substring(0,startDateSb.toString().length() - 1);
                    endDate =  endDateSb.substring(0,endDateSb.toString().length() - 1);
                    dateCode = dateCode2 +"1";
                    coupon.setDateCode(dateCode);
                }
                dateCode = dateCode2 +"1";
                coupon.setUnavailableDate(unavailableDateList);
            }else {
                dateCode = dateCode2 +"0";
            }
            coupon.setDateCode(dateCode);



          /*  if( (startDateSb.toString() != null && !"".equals(startDateSb.toString())) && (endDateSb.toString() != null && !"".equals( endDateSb.toString()))){
                startDate =  startDateSb.substring(0,startDateSb.toString().length() - 1);
                endDate =  endDateSb.substring(0,endDateSb.toString().length() - 1);
                dateCode = dateCode2 +"1";
                coupon.setDateCode(dateCode);
            }else {
                dateCode = dateCode2 +"0";
                coupon.setDateCode(dateCode);
            }*/

        }


        if ("0".equals(isCommonCoupon)) {  //是否可共用
            coupon.setShare(false);
        } else if ("1".equals(isCommonCoupon)) {
            coupon.setShare(true);
        }


        if ("0".equals(isZhuanZeng)) {   //是否可转赠
            coupon.setAssign(false);
        } else if ("1".equals(isZhuanZeng)) {
            coupon.setAssign(true);
        }


        coupon.setNoItem(buKeYongDesc);

        coupon.setNotes(remark != null && !"".equals(remark) ? remark : "");
    }

    private void setCopyCreate() {
        ToastUtils.showCustomShortBottom(getResources().getString(R.string.create_success));
        couponName = edt_coupon_name.getText().toString().trim();
        zengPinName = edt_zengpin.getText().toString().trim();
        goodPrize = edt_edu.getText().toString().trim();
        menKanPrize = edt_user_men_kan.getText().toString().trim();


        buKeYongDesc = edt_bu_ke_yong_desc.getText().toString().trim();

        remark = edt_remark.getText().toString().trim();
        //复制并创建
        isCopyCreate = "NO";
        isEditCoupon = "NO";
        couponStatus = "NEW";

        //设置是否编辑
        setIsComponentEdit();

    }


    protected void setIsComponentEdit() {
        if ("NEW".equals(couponStatus)) {
            edt_coupon_name.setEnabled(true);
            edt_zengpin.setEnabled(true);
            edt_edu.setEnabled(true);
            edt_user_men_kan.setEnabled(true);
            edt_user_men_kan.setEnabled(true);
            tv_user_shiduan.setEnabled(true);

            tv_is_bu_ke_yong_time.setEnabled(true);
            tv_bu_ke_yong_time.setEnabled(true);
            tv_user_shiduan.setEnabled(true);

            tv_qi_ta_coupon_gongyong.setEnabled(true);
            tv_coupon_zhuan_zeng.setEnabled(true);
            edt_bu_ke_yong_desc.setEnabled(true);
            edt_remark.setEnabled(true);
        } else {
            edt_coupon_name.setEnabled(false);
            edt_zengpin.setEnabled(false);
            edt_edu.setEnabled(false);
            edt_user_men_kan.setEnabled(false);
            edt_user_men_kan.setEnabled(false);
            tv_user_shiduan.setEnabled(false);

            tv_is_bu_ke_yong_time.setEnabled(false);
            tv_bu_ke_yong_time.setEnabled(false);
            tv_user_shiduan.setEnabled(false);

            tv_qi_ta_coupon_gongyong.setEnabled(false);
            tv_coupon_zhuan_zeng.setEnabled(false);
            edt_bu_ke_yong_desc.setEnabled(false);
            edt_remark.setEnabled(false);
        }
    }


    protected void initTimePeriodPicker() {//条件选择器初始化，自定义布局
        timePeriodBeans = new ArrayList<>();
        CardBean cardBean = new CardBean();
        cardBean.setId("1");
        cardBean.setCardNo("全天");
        timePeriodBeans.add(cardBean);
        cardBean = new CardBean();
        cardBean.setId("2");
        cardBean.setCardNo("午市");
        timePeriodBeans.add(cardBean);
        cardBean = new CardBean();
        cardBean.setId("3");
        cardBean.setCardNo("晚市");
        timePeriodBeans.add(cardBean);
        pvShiDduan = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String value = timePeriodBeans.get(options1).getPickerViewText();
                String key = timePeriodBeans.get(options1).getId();

                if ("1".equals(key)) {
                    shiduan = key;
                } else if ("2".equals(key)) {
                    shiduan = key;
                } else if ("3".equals(key)) {
                    shiduan = key;
                }
                tv_user_shiduan.setText(value);
            }
        })
                .setLayoutRes(R.layout.layout_pickerview_custom_position, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final ImageView ivSubmit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        ivSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvShiDduan.returnData();
                                pvShiDduan.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvShiDduan.dismiss();
                            }
                        });


                    }
                })
                .build();


        pvShiDduan.setPicker(timePeriodBeans);

    }

    protected void initDateCodePicker() {//条件选择器初始化，自定义布局

        List<TieBean> tieBeanList = new ArrayList<>();
        tieBeanList.add(new TieBean("是", 1));
        tieBeanList.add(new TieBean("否", 0));



        DialogUIUtils.showMdBottomSheet(CouponNewActivity.this, true, "", tieBeanList, 1, new DialogUIItemListener() {
            @Override
            public void onItemClick(CharSequence text, int position) {
                String key = String.valueOf(tieBeanList.get(position).getId());
                String value = tieBeanList.get(position).getTitle();
                isBuKeYongTime = key;
                if ("0".equals(key)) {
                    rl_bu_ke_yong.setVisibility(View.GONE);
                    ll_select_date.setVisibility(View.GONE);
                    isAddDate = false;
                } else if ("1".equals(key)) {
                    rl_bu_ke_yong.setVisibility(View.VISIBLE);
                    ll_select_date.setVisibility(View.VISIBLE);
                    isAddDate = true;

                }
                tv_is_bu_ke_yong_time.setText(value);

            }
        }).show();

    }


    protected void initNoDatePicker() {//条件选择器初始化，自定义布局
        noDateBeans = new ArrayList<>();
        CardBean cardBean = new CardBean();
        cardBean.setId("1");
        cardBean.setCardNo("周六、日不可用");
        noDateBeans.add(cardBean);
        cardBean = new CardBean();
        cardBean.setId("2");
        cardBean.setCardNo("周六、日及法定节假日不可用");
        noDateBeans.add(cardBean);

        cardBean = new CardBean();
        cardBean.setId("3");
        cardBean.setCardNo("法定节假日不可用");
        noDateBeans.add(cardBean);

        pvBuKeYongTime = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                noDate = noDateBeans.get(options1).getPickerViewText();
                String key = noDateBeans.get(options1).getId();
                buKeYongTime = key;


            }
        })
                .setLayoutRes(R.layout.layout_pickerview_custom_position, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final ImageView ivSubmit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        ivSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvBuKeYongTime.returnData();
                                pvBuKeYongTime.dismiss();

                                if ("1".equals(buKeYongTime)) {
                                } else if ("2".equals(buKeYongTime)) {
                                } else if ("3".equals(buKeYongTime)) {
                                }
                                tv_bu_ke_yong_time.setText(noDate);
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvBuKeYongTime.dismiss();
                            }
                        });


                    }
                })
                .build();


        pvBuKeYongTime.setPicker(noDateBeans);

    }


    private void setCouponGongYongPicker() {
        List<TieBean> tieBeanList = new ArrayList<>();
        tieBeanList.add(new TieBean("是", 1));
        tieBeanList.add(new TieBean("否", 0));


        DialogUIUtils.showMdBottomSheet(CouponNewActivity.this, true, "", tieBeanList, 1, new DialogUIItemListener() {
            @Override
            public void onItemClick(CharSequence text, int position) {
                String key = String.valueOf(tieBeanList.get(position).getId());
                String value = tieBeanList.get(position).getTitle();
              /*  if ("0".equals(key)) {
                    isCommonCoupon = key;
                } else if ("1".equals(key)) {
                    isCommonCoupon = key;
                }*/
                isCommonCoupon = key;
                tv_qi_ta_coupon_gongyong.setText(value);
              /*  if (isCommonCouponAndZhuanZeng) {   //与其他优惠券共用

                } else {   //转增


                }*/

            }
        }).show();

    }


    private void setZhuanZengPicker() {
        List<TieBean> tieBeanList = new ArrayList<>();
        tieBeanList.add(new TieBean("是", 1));
        tieBeanList.add(new TieBean("否", 0));


        DialogUIUtils.showMdBottomSheet(CouponNewActivity.this, true, "", tieBeanList, 1, new DialogUIItemListener() {
            @Override
            public void onItemClick(CharSequence text, int position) {
                String key = String.valueOf(tieBeanList.get(position).getId());
                String value = tieBeanList.get(position).getTitle();
              /*  if (isCommonCouponAndZhuanZeng) {   //与其他优惠券共用
                    if ("0".equals(key)) {
                        isCommonCoupon = key;
                    } else if ("1".equals(key)) {
                        isCommonCoupon = key;
                    }
                    tv_qi_ta_coupon_gongyong.setText(value);
                } else {   //转增

                    if ("0".equals(key)) {
                        isZhuanZeng = key;
                    } else if ("1".equals(key)) {
                        isZhuanZeng = key;
                    }
                    tv_coupon_zhuan_zeng.setText(value);
                }*/
                isZhuanZeng = key;

                tv_coupon_zhuan_zeng.setText(value);

            }
        }).show();

    }



    //
    protected void addDateItem(String startDateStr,String endDateStr) {


        // 1. 根据传入的v, 判断是mListAddBtn中的哪一个
        View view = LayoutInflater.from(this).inflate(R.layout.item_coupon_new_date_list, null);
        TextView tvStartTime = (TextView) view.findViewById(R.id.tv_start_time);
        TextView tvEndTime = (TextView) view.findViewById(R.id.tv_end_time);
        RelativeLayout ll_start = (RelativeLayout) view.findViewById(R.id.ll_start);
        RelativeLayout ll_end = (RelativeLayout) view.findViewById(R.id.ll_end);
        LinearLayout ll_delete = (LinearLayout) view.findViewById(R.id.ll_delete);
        ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_delete);

        if (curView >= 0) {
            mDelList.add(curView, ll_delete);
            curView++;
        }
        tvStartTime.setText(startDateStr);
        tvEndTime.setText(endDateStr);
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 12, 28);
        //时间选择器 ，自定义布局
        TimePickerBuilder startBuilder  = new TimePickerBuilder(CouponNewActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvStartTime.setText(TimeUtils.date2String(date, new SimpleDateFormat("yyyy-MM-dd")).replace("-", "."));

            }
        });
        final TimePickerView pvStartTime = startBuilder.build();
        startBuilder .setDate(selectedDate);
        startBuilder.setRangDate(startDate, endDate);
        startBuilder.isCyclic(true);

        startBuilder.setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(final View v) {
                        final ImageView tvSubmit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvStartTime.returnData();
                                String startDate = tvStartTime.getText().toString().trim();
                                String endDate = tvEndTime.getText().toString().trim();

                                if (!startDate.isEmpty() && !endDate.isEmpty()) {
                                    Date start = TimeUtils.string2Date(startDate.replace(".", "-"));
                                    Date end = TimeUtils.string2Date(endDate.replace(".", "-"));

                                    if (start.after(end)) {  //start 大于end时 返回true; 反之 小于等于返回false
                                        tvStartTime.setText("");
                                        ToastUtils.showToast("开始时间不可大于结束时间！");
                                    }
                                }
                                pvStartTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvStartTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED);


        TimePickerBuilder endBuilder = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvEndTime.setText(TimeUtils.date2String(date, new SimpleDateFormat("yyyy-MM-dd")).replace("-", "."));
            }
        });

        if("NOEDIT".equals(couponStatus)){
            ll_start.setEnabled(false);
            ll_end.setEnabled(false);
            iv_delete.setEnabled(false);
            ll_delete.setVisibility(View.GONE);
        }else {

            ll_start.setEnabled(true);
            ll_end.setEnabled(true);
            iv_delete.setEnabled(true);
            ll_delete.setVisibility(View.VISIBLE);
        }

        endBuilder.setDate(selectedDate);
        endBuilder.setRangDate(startDate, endDate);
        endBuilder.isCyclic(true);
        final TimePickerView pvEndTime = endBuilder.build();
        endBuilder .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final ImageView tvSubmit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvEndTime.returnData();
                                String startDate = tvStartTime.getText().toString().trim();
                                String endDate = tvEndTime.getText().toString().trim();

                                if (!startDate.isEmpty() && !endDate.isEmpty()) {
                                    Date start = TimeUtils.string2Date(startDate.replace(".", "-"));
                                    Date end = TimeUtils.string2Date(endDate.replace(".", "-"));

                                    if (end.before(start)) {  //start 大于end时 返回true; 反之 小于等于返回false
                                        ToastUtils.showToast("结束时间不可小于开始时间！");
                                        tvEndTime.setText("");
                                    }
                                }

                                pvEndTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvEndTime.dismiss();
                            }
                        });

                    }

                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED);

        ll_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delItem(v);
//                onDeleteClick(v);
            }
        });

        ll_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvStartTime.show();
            }
        });
        ll_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvEndTime.show();

            }
        });
        ll_coupon_add_date.addView(view);

    }


    /**
     * 处理删除击事件
     *
     * @param view 整个image对应的relativeLayout view
     */
    private void onDeleteClick(View view) {
        ImageView imageView = null;
        EditText editText = null;
        int index = ll_coupon_add_date.indexOfChild(view);

        View child;
        child = ll_coupon_add_date.getChildAt(index);
        if (!(child instanceof LinearLayout)) { //
            child = ll_coupon_add_date.getChildAt(index);
        }
        ll_coupon_add_date.removeView(view);

        if("EDIT".equals(couponStatus)){
            if(unavailableDateList != null && unavailableDateList.size()>0){
                for (int j = 0;j<unavailableDateList.size();j++) {

                    if(index == j){

                        unavailableDateList.remove(j);
                        j--;
                        break;
                    }

                }
            }
        }




    }
    /**
     * @param v 删除一个新的EditText条目
     */
    private void delItem(View v) {
        if (v == null) {
            return;
        }

        int curViewTwo = -1;
        for (int i = 0; i < mDelList.size(); i++) {
            if (mDelList.get(i) == v) {
                curViewTwo = i;
                break;
            }
        }

        if (curViewTwo >= 0) {
            if(unavailableDateList != null && unavailableDateList.size()>0){

                unavailableDateList.remove(curViewTwo);
            }
            mDelList.remove(curViewTwo);

            if(ll_coupon_add_date != null){
                if(ll_coupon_add_date.getChildAt(curViewTwo) !=null){
                    ll_coupon_add_date.removeViewAt(curViewTwo);
                    log.error("优惠券管理 删除不可用日期时间----"+curViewTwo);
                }
            }


            curView--;

            if(curView<= -1){
                curView = 0;
            }
        }
    }

    private void setUnavailableDate() {
        unavailableDateList = new ArrayList<>();
        for (int i = 0; i < ll_coupon_add_date.getChildCount(); i++) {

            View childAt = ll_coupon_add_date.getChildAt(i);
            TextView tv_start_time = childAt.findViewById(R.id.tv_start_time);

            TextView tv_end_time = childAt.findViewById(R.id.tv_end_time);
            String startDate = tv_start_time.getText().toString().trim();
            String endDate = tv_end_time.getText().toString().trim();
            UnavailableDate unavailableDate = new UnavailableDate();
            if(startDate != null && !"".equals(startDate)){
                unavailableDate.setStartDate(startDate.replace(".","-"));
            }
            if(endDate != null && !"".equals(endDate)){
                unavailableDate.setEndDate(endDate.replace(".","-"));
            }

            if((startDate != null && !"".equals(startDate)) && (endDate != null && !"".equals(endDate))){
                unavailableDateList.add(unavailableDate);
            }else if((startDate != null && !"".equals(startDate)) && (endDate == null||"".equals(endDate))){
                unavailableDateList.add(unavailableDate);
            }else if((startDate == null || "".equals(startDate)) && (endDate != null && !"".equals(endDate))){
                unavailableDateList.add(unavailableDate);
            }else if(startDate == null && endDate == null){

            }

        }


    }


    public void setDialog(String tip) {

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView textView = (TextView) rootView.findViewById(R.id.message);
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


    private void setPopDialog() {

        View customView = View.inflate(this, R.layout.layout_coupon_new_preview_dialog, null);

        PopWindow popAlertView = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopAlert)
                .setView(customView)
                .show();

        LinearLayout ll_content = (LinearLayout) customView.findViewById(R.id.ll_content);
        ImageView iv_coupon_head = (ImageView)customView.findViewById(R.id.iv_coupon_head);

        TextView tv_object =  (TextView)customView.findViewById(R.id.tv_object);
        TextView tv_time_period  = (TextView)customView.findViewById(R.id.tv_time_period);
        TextView tv_no_date  = (TextView)customView.findViewById(R.id.tv_no_date);
        TextView tv_coupon_price  = (TextView)customView.findViewById(R.id.tv_coupon_price);
        TextView tv_coupon_name  = (TextView)customView.findViewById(R.id.tv_coupon_name);
        View line   = (View) customView.findViewById(R.id.line);


        TextView tv_couponprice  = (TextView)customView.findViewById(R.id.tv_couponprice);
        TextView tv_timeperiod  = (TextView)customView.findViewById(R.id.tv_timeperiod);
        TextView tv_nodate  = (TextView)customView.findViewById(R.id.tv_nodate);
        TextView tv_noitem  = (TextView)customView.findViewById(R.id.tv_noitem);  //不可用
        TextView tv_assign =  (TextView)customView.findViewById(R.id.tv_assign);  //共享

        TextView tv_share  = (TextView)customView.findViewById(R.id.tv_share); //转增
        TextView tv_notes =  (TextView)customView.findViewById(R.id.tv_notes);  //备注说明

        LinearLayout ll_nodate = (LinearLayout) customView.findViewById(R.id.ll_nodate);
        LinearLayout ll_noitem = (LinearLayout) customView.findViewById(R.id.ll_noitem);
        LinearLayout ll_notes = (LinearLayout) customView.findViewById(R.id.ll_notes);

        Button btn_use = (Button) customView.findViewById(R.id.btn_use);
        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popAlertView.dismiss();
            }
        });

        LinearLayout ll_quit = (LinearLayout)customView.findViewById(R.id.ll_quit);
        ll_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAlertView.dismiss();
            }
        });
        LinearLayout ll_right = (LinearLayout)customView.findViewById(R.id.ll_right);
        ll_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAlertView.dismiss();
            }
        });

        couponName = edt_coupon_name.getText().toString().trim();
        zengPinName = edt_zengpin.getText().toString().trim();
        goodPrize = edt_edu.getText().toString().trim();
        menKanPrize = edt_user_men_kan.getText().toString().trim();
        buKeYongDesc = edt_bu_ke_yong_desc.getText().toString().trim();
        remark = edt_remark.getText().toString().trim();

        tv_coupon_name.setText(couponName != null && !"".equals(couponName)?couponName : "");

        setUnavailableDate();
        StringBuilder sb  = new StringBuilder();


        if(dateCode!=null && !"".equals(dateCode)){
            if("00000".equals(dateCode)){   //不展示
                tv_no_date.setText("");
                line.setVisibility(View.GONE);
                ll_nodate.setVisibility(View.GONE);
            }else {

                if("1".equals(isBuKeYongTime)){
                    if(noDate !=null && !"".equals(noDate)){
                        ll_nodate.setVisibility(View.VISIBLE);
                        tv_no_date.setText(noDate);
                        sb.append(noDate+"<br>");
                        if(unavailableDateList != null && unavailableDateList.size()>0){
                            for (UnavailableDate unavailableDate : unavailableDateList){
                                String startDate = unavailableDate.getStartDate();
                                String endDate = unavailableDate.getEndDate();
                                if((startDate != null && !"".equals(startDate)) && (endDate != null && !"".equals(endDate))){
                                    sb.append(unavailableDate.getStartDate()+ " - " +unavailableDate.getEndDate()).append("<br>");
                                }else if((startDate != null && !"".equals(startDate)) && endDate == null ){
                                    sb.append(unavailableDate.getStartDate()+" - "  +" ").append("<br>");
                                }else if((startDate == null) && (endDate != null && !"".equals(endDate))){
                                    sb.append(" - " +unavailableDate.getEndDate()).append("<br>");
                                }else if(startDate == null && endDate == null){

                                }
                            }
                        }


                        tv_nodate.setText(Html.fromHtml(""+sb.toString()));
                    }else {
                        tv_no_date.setText("");
                        line.setVisibility(View.GONE);
                        ll_nodate.setVisibility(View.GONE);
                    }
                }else {
                    tv_no_date.setText("");
                    line.setVisibility(View.GONE);
                    ll_nodate.setVisibility(View.GONE);
                }

            }
        }

        String timePeriod = "";
        if(shiduan !=null ){
            if("1".equals(shiduan)){
                timePeriod = "全天";
            }else if("2".equals(shiduan)){
                timePeriod = "午市";
            }else if("3".equals(shiduan)){
                timePeriod = "晚市";
            }
            tv_time_period.setText(timePeriod);

        }else {
            tv_time_period.setText("");
            line.setVisibility(View.GONE);
        }
        tv_timeperiod.setText("此优惠券"+timePeriod+"可用。");

        String shareStr = "";
        if("0".equals(isCommonCoupon)){
            shareStr = "不可以与其它优惠券共享。";
        }else if("1".equals(isCommonCoupon)){
            shareStr = "可以与其它优惠券共享。";
        }
        tv_share.setText(shareStr);

        String assignStr = "";
        if("0".equals(isZhuanZeng)){
            assignStr = "会员不可以转赠该优惠券。";
        }else if("1".equals(isZhuanZeng)){
            assignStr = "会员可以转赠该优惠券。";
        }
        tv_assign.setText(assignStr);

        if(buKeYongDesc != null && !"".equals(buKeYongDesc)){
            ll_noitem.setVisibility(View.VISIBLE);
        }else {
            ll_noitem.setVisibility(View.GONE);
        }
        tv_noitem.setText(buKeYongDesc);

        if(remark != null && !"".equals(remark)){
            ll_notes.setVisibility(View.VISIBLE);
        }else {
            ll_notes.setVisibility(View.GONE);
        }
        tv_notes.setText(remark);

        String object = zengPinName;
        String limit = "";

        String couponPriceDesc = "";
        if(menKanPrize != null && !"".equals(menKanPrize)){
            limit = RegexUtils.getDoubleString(Double.parseDouble(menKanPrize));
            couponPriceDesc = "满"+limit+"元可用";
        }else {
            couponPriceDesc = "任意消费可用";
        }
        tv_coupon_price.setText(couponPriceDesc);
        SpannableString spannableString = null;
        String price = "";
        if("CPC".equals(couponType)){  //代金券
            String pri = "";
            if(goodPrize != null && !"".equals(goodPrize)){
                price = RegexUtils.getDoubleString(Double.parseDouble(goodPrize));
                pri = price+"元";
            }

            spannableString= new SpannableString(pri);
            spannableString.setSpan(new AbsoluteSizeSpan(20,true), 0, price.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            tv_object.setText(spannableString);
            iv_coupon_head.setBackgroundResource(R.drawable.ic_coupon_price);


            tv_couponprice.setText("凭此券可享受减免"+price+"元优惠，"+couponPriceDesc);
        }else if("CPD".equals(couponType)){   //折扣券
            String discount = "";
            String dis = "";
            if(goodPrize!=null && !"".equals(goodPrize)){
                discount = RegexUtils.getDoubleString(Double.parseDouble(goodPrize)) + "折";
            }
            dis = discount ;
            spannableString= new SpannableString(dis);
            spannableString.setSpan(new AbsoluteSizeSpan(25,true), 0, discount.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            tv_object.setText(spannableString);
            iv_coupon_head.setBackgroundResource(R.drawable.ic_coupon_discount);

            tv_couponprice.setText("凭优惠券可享受"+discount+"折优惠，"+couponPriceDesc);
        }else if("CPO".equals(couponType)){  //赠品券
            String pri = "";
            if(goodPrize != null && !"".equals(goodPrize)){
                price = RegexUtils.getDoubleString(Double.parseDouble(goodPrize));
                pri = price+"元";
            }

            iv_coupon_head.setBackgroundResource(R.drawable.ic_coupon_prese);
            tv_object.setText(object!=null?object:"");

            tv_couponprice.setText("凭此券可获得价值"+price+"元"+zengPinName+"1份，"+couponPriceDesc);
        }else if("CPU".equals(couponType)){   //菜品
            iv_coupon_head.setBackgroundResource(R.drawable.ic_coupon_dishes);
            tv_object.setText(object!=null?object:"");
            String pri = "";
            if(goodPrize != null && !"".equals(goodPrize)){
                price = RegexUtils.getDoubleString(Double.parseDouble(goodPrize));
            }
            tv_couponprice.setText("凭此券可获得价值"+price+"元"+zengPinName+"1份，"+couponPriceDesc);
        }else if("CPJ".equals(couponType)){
            iv_coupon_head.setBackgroundResource(R.drawable.ic_coupon_paidui);
            tv_object.setText("插队券");
            tv_couponprice.setText("凭此券可享受插队券的优惠，"+couponPriceDesc);
        }else if("CPT".equals(couponType)){  //其他
            String other = zengPinName;
            tv_object.setText(other!=null?other:"");
            tv_object.setBackgroundResource(R.drawable.ic_coupon_other);
            tv_couponprice.setText("凭此券可享受"+other+"的优惠，"+couponPriceDesc);

        }



    }

}
