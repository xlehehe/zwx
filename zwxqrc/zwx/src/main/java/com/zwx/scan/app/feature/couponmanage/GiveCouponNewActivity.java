package com.zwx.scan.app.feature.couponmanage;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwx.library.pickerview.picker.builder.TimePickerBuilder;
import com.zwx.library.pickerview.picker.listener.CustomListener;
import com.zwx.library.pickerview.picker.listener.OnTimeSelectListener;
import com.zwx.library.pickerview.picker.view.TimePickerView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.KeyboardUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ScreenUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.library.utils.Utils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.CampaginGrant;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CampaignGame;
import com.zwx.scan.app.data.bean.CampaignGamesetreward;
import com.zwx.scan.app.data.bean.CompMemberType;
import com.zwx.scan.app.data.bean.GiveCouponResultBean;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.utils.EmojiInputFilter;
import com.zwx.scan.app.widget.MyGridView;
import com.zwx.scan.app.widget.flowlayout.ExpandFlowLayout;
import com.zwx.scan.app.widget.flowlayout.FlowLayout;
import com.zwx.scan.app.widget.flowlayout.TagAdapter;
import com.zwx.scan.app.widget.flowlayout.TagFlowLayout;
import com.zwx.scan.app.widget.tagflowlayout.view.MyNestedScrollView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

public class GiveCouponNewActivity extends BaseActivity<GiveCouponContract.Presenter> implements GiveCouponContract.View,View.OnClickListener{
    @BindView(R.id.iv_back)
    protected ImageView ivBack;
    @BindView(R.id.tv_right)
    protected TextView tvRight;
    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    //1 - 2步骤
    @BindView(R.id.iv_one)
    protected ImageView ivOne;
    @BindView(R.id.iv_ellipsis_one)
    protected ImageView ivEllipsisOne;
    @BindView(R.id.iv_two)
    protected ImageView ivTwo;



    @BindView(R.id.edt_name)
    protected EditText edtName;

    @BindView(R.id.flowlayout)
    protected TagFlowLayout flowLayout;
    @BindView(R.id.hsv_tag_content)
    protected MyNestedScrollView hsv_tag_content;


    @BindView(R.id.ll_days)
    protected LinearLayout llDays;
    //生日礼加减
    @BindView(R.id.iv_left_subtract)
    protected ImageView ivLeftSubtract;

    @BindView(R.id.edt_day_one)
    protected EditText edtDayDate;

    @BindView(R.id.tv_valid_days)
    protected TextView tvValidDays;
    @BindView(R.id.iv_right_add)
    protected ImageView ivRightAdd;


    @BindView(R.id.tv_flow_label)
    protected TextView tvFlowLabel;

    @BindView(R.id.tv_days_label)
    protected TextView tvDayLabel;

    @BindView(R.id.tv_birthday_label)
    protected TextView tv_birthday_label;

    @BindView(R.id.tv_date_label)
    protected TextView tvDateLabel;



    //日期
    @BindView(R.id.tv_start_time)
    protected TextView tvStartTime;
    @BindView(R.id.ll_start)
    protected LinearLayout llStart;

    @BindView(R.id.tv_end_time)
    protected TextView tvEndTime;
    @BindView(R.id.ll_end)
    protected LinearLayout llEnd;

    @BindView(R.id.btn_next)
    protected Button btnNext;

    @BindView(R.id.iv_arrow)
    protected ImageView iv_arrow;


    @BindView(R.id.ll_arrow)
    protected LinearLayout ll_arrow;

    @BindView(R.id.ll_member_date)
    protected LinearLayout ll_member_date;

    @BindView(R.id.rl_week_month)
    protected RelativeLayout rl_week_month;
    @BindView(R.id.tv_week_month_label)
    protected TextView tv_week_month_label;
//    @BindView(R.id.rl_month)
//    protected RelativeLayout rl_month;

    @BindView(R.id.tv_week_date)
    protected TextView tv_week_date;

    @BindView(R.id.tv_month_date)
    protected TextView tv_month_date;
    @BindView(R.id.ll_week)
    protected LinearLayout ll_week;
    @BindView(R.id.tv_tab_week)
    protected TextView tv_tab_week;
    @BindView(R.id.line_week)
    protected View line_week;
    @BindView(R.id.ll_month)
    protected LinearLayout ll_month;

    @BindView(R.id.tv_tab_month)
    protected TextView tv_tab_month;
    @BindView(R.id.line_month)
    protected View line_month;

    private int getValidDay  = 0 ;

    public static CampaginGrant campaginGrant = new CampaginGrant();


    protected  String couponName ;
    private String userId;

    private String compId;//会员卡

    protected TimePickerView pvCustomTime;
    private  boolean isStartDate ;


    protected String campaignName;
    protected String startDate;
    protected String endDate;


    protected List<CompMemberType> compMemTypeList = new ArrayList<>();
    public static CampaignGame campaignGame = new CampaignGame();

    protected String title;

    public static String compMemTypeId = "";

    protected String compMemTypeName;

    protected   boolean isCampaignAndCouponStore = true;


    public static  Campaign campaign = new Campaign();

    public  static String storeStr;

    protected   String isEditCampaign = "NO";

    private String isCopyCreate = "YES" ;
    protected String campaignId;
    protected static  String compaignStatus ;   //活动状态
    protected String campaignType = "zj" ;
    public static String grantType;

    //
    public static String sendDay = "0";
    public String getValidDayStr ;
    private String validDaysStr = "";


    TagAdapter tagAdapter = null;
    protected  String giveName = "";
    protected String giveNameTime = "";

    /**
     * 会员日方式： 星期：W 日期：D
     */
    protected String memberDayType  ;
    /**
     * 具体会员日： 星期多选：星期逗号分隔，如周一,周三（周一--周日） 日期多选：日期逗号分隔，如1,3,30（1--31）
     */
    protected String memberDay;
    protected String memberDayStr;


    private GiveCouponDayAdapter mAdapter = null;
    protected List<DayDateBean> dayDateBeanList = new ArrayList<>();
    protected List<DayDateBean> selectDayDateBeanList = new ArrayList<>();

    //是否折叠起来
    //true:折叠起来了
    //false:展开了
    public boolean isMore;

    //展开和折叠动画持续时间
    private int animationDuration =400;
    //内容区域最少显示高度(px)
    private int minVisibleHeight;
    //内容区域最大显示高度
    private int maxVisibleHeight;
    protected int height ;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_campaign_coupon_new;
    }

    @Override
    protected void initView() {

        DaggerGiveCouponComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .giveCouponModule(new GiveCouponModule(this))
                .build()
                .inject(this);
        title =getIntent().getStringExtra("title");


        userId = SPUtils.getInstance().getString("userId");
        compId = SPUtils.getInstance().getString("compId");
        //是否编辑还是新建
        isEditCampaign =  getIntent().getStringExtra("isEditCampaign");
        isCopyCreate =  getIntent().getStringExtra("isCopyCreate");

        compaignStatus = getIntent().getStringExtra("compaignStatus");
        //常规发券类型
        grantType = getIntent().getStringExtra("grantType");
        edtName.setSelection(edtName.getText().toString().trim().length());

        setTitle();
        initDatePicker();
        //步骤
        setSetTep();
        //限制表情图片输入
        edtName.setFilters(new InputFilter[]{new EmojiInputFilter()});
        edtDayDate.setSelection(edtDayDate.getText().length());

        minVisibleHeight = ScreenUtils.dip2px(GiveCouponNewActivity.this, 90);
        //内容区域最大显示高度
        maxVisibleHeight  = ScreenUtils.dip2px(GiveCouponNewActivity.this, 120);


    }

    protected  void setTitle(){


        if("B".equals(grantType)){ //生日礼
            tvDateLabel.setText(getText(R.string.camapign_coupon_new_shengrili_setting_time));
            tvFlowLabel.setText(getText(R.string.camapign_coupon_new_shengrili));
            llDays.setVisibility(View.VISIBLE);
            ll_member_date.setVisibility(View.GONE);
        }else if ("K".equals(grantType)){ //开卡礼
            tvDateLabel.setText(getText(R.string.camapign_coupon_new_kaikali_time));
            tvFlowLabel.setText(getText(R.string.camapign_coupon_new_kaikali));
            llDays.setVisibility(View.GONE);
            ll_member_date.setVisibility(View.GONE);
        }else if("M".equals(grantType)){  //会员日
            tvFlowLabel.setText(getText(R.string.camapign_coupon_new_huiyuanri));
            llDays.setVisibility(View.VISIBLE);
            ll_member_date.setVisibility(View.VISIBLE);
            if("NEW".equals(compaignStatus)){
                memberDayType = "W";
                setWeekStyle();
                tv_week_month_label.setText("每周选定成为会员日时间");
            }

            tvDayLabel.setText("你想什么时间将会员日礼品赠送给会员");
            tv_birthday_label.setText("会员日前几天发送");

            tvDateLabel.setText("请问你想让这个会员日设置持续多长时间");
        }else if("S".equals(grantType)){  //唤醒消费礼
            tvDateLabel.setText("请问你想让这个消费唤醒设置持续多长时间");
            tvFlowLabel.setText("请问你想唤醒什么会员卡的会员");
            llDays.setVisibility(View.GONE);
            ll_member_date.setVisibility(View.GONE);
        }else if("V".equals(grantType)){  //核销后证券
            tvDateLabel.setText("请问你想让这个核销后赠券持续多长时间");
            tvFlowLabel.setText("请问你想对什么会员卡的会员设置核销返券");
            llDays.setVisibility(View.GONE);
            ll_member_date.setVisibility(View.GONE);
        }
        String time = TimeUtils.date2String(TimeUtils.string2HHDate(TimeUtils.date2String(TimeUtils.getNowDate()))).substring(0,10);
        giveNameTime = time.replace("-","");
        giveName = title+giveNameTime;
        tvTitle.setText(title+getResources().getString(R.string.set_label));

        //监听生日礼或会员日礼增减数字变化
        setEdtDayDate();
    }

    private void setSetTep(){
        ivOne.setBackgroundResource(R.drawable.ic_first_clicked);
        ivEllipsisOne.setBackgroundResource(R.drawable.ic_ellipsis_gray);
        ivTwo.setBackgroundResource(R.drawable.ic_two_untclick);
    }

    @Override
    protected void initData() {
        // //编辑
        if("YES".equals(isEditCampaign)){

            if("YES".equals(isCopyCreate)){  //编辑页面复制并创建
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setText("复制并创建");
            }else {
                tvRight.setVisibility(View.GONE);
                tvRight.setText("");
            }

        }else { //新建
            tvRight.setVisibility(View.GONE);
        }

        campaignId= getIntent().getStringExtra("campaignId");
        //复制并创建缓存获取

        if(campaign != null){
            campaignName  = campaign.getCampaignName();
            startDate = campaign.getBeginDate();
            endDate = campaign.getEndDate();


            if(startDate!=null && !"".equals(startDate)){
                String startTime = TimeUtils.date2String(TimeUtils.string2Date(startDate),new SimpleDateFormat("yyyy-MM-dd"));
                tvStartTime.setText(startTime.replace("-","."));
            }else {
                tvStartTime.setText("");
            }
            if(endDate!=null && !"".equals(endDate)){
                String endtTime = TimeUtils.date2String(TimeUtils.string2Date(endDate),new SimpleDateFormat("yyyy-MM-dd"));
                tvEndTime.setText(endtTime.replace("-","."));
            }else {
                tvEndTime.setText("");
            }

        }

        if(campaginGrant != null){
            if(campaginGrant.getCompMemTypeId() != null){
                compMemTypeId = String.valueOf(campaginGrant.getCompMemTypeId());
            }else {
                compMemTypeId = "";
            }
//            grantType= campaginGrant.getGrantType();
            memberDayType = campaginGrant.getMemberDayType();
            if(memberDayType == null || "".equals(memberDayType)){
                memberDayType = "W";
            }

            initWeekAndMonth();
            memberDay = campaginGrant.getMemberDay();
            if(campaginGrant.getSendDay() != null && campaginGrant.getSendDay().intValue()>0 ){
                sendDay = String.valueOf(campaginGrant.getSendDay());
                tvValidDays.setText(sendDay);
            }else {
                tvValidDays.setText("当");
            }


            if("M".equals(grantType)){
                memberDayType = campaginGrant.getMemberDayType();
                if(campaginGrant.getSendDay() != null && campaginGrant.getSendDay().intValue()>0){
                    sendDay = String.valueOf(campaginGrant.getSendDay());
                    tvValidDays.setText(sendDay);
                }else {
                    sendDay = "0";
                    tvValidDays.setText("当");
                }

                edtDayDate.setText(sendDay);
                StringBuilder dayNameSb = new StringBuilder();
                if("W".equals(memberDayType)){

                    if(memberDay != null && !"".equals(memberDay)){
                        String[] memberDays = memberDay.split(",");
                        for (int i = 0;i<memberDays.length;i++){
                            DayDateBean dayDateBean = new DayDateBean();
                            dayDateBean.setChecked(true);
                            dayDateBean.setId(memberDays[i]);
                            String value = "";
                            if("1".equals(memberDays[i])){
                                value = "周一";
                                dayNameSb.append("周一").append("、");
                            }else if("2".equals(memberDays[i])){
                                value = "周二";
                                dayNameSb.append("周二").append("、");
                            }else if("3".equals(memberDays[i])){
                                value = "周三";
                                dayNameSb.append("周三").append("、");
                            }else if("4".equals(memberDays[i])){
                                value = "周四";
                                dayNameSb.append("周四").append("、");
                            }else if("5".equals(memberDays[i])){
                                value = "周五";
                                dayNameSb.append("周五").append("、");
                            }else if("6".equals(memberDays[i])){
                                value = "周六";
                                dayNameSb.append("周六").append("、");
                            }else if("7".equals(memberDays[i])){
                                value = "周日";
                                dayNameSb.append("周日").append("、");
                            }
                            dayDateBean.setValue(value);
                            selectDayDateBeanList.add(dayDateBean);
                        }

                        String dayNameA= dayNameSb.toString();
                        if(dayNameA!=null && !"".equals(dayNameA)){
                            memberDayStr= dayNameA.substring(0,dayNameA.length() - 1);
                        }
                        tv_week_date.setText(memberDayStr);
                        setWeekStyle();
                    }

                }else {
                    if(memberDay != null && !"".equals(memberDay)){
                        String[] memberDays = memberDay.split(",");
                        for (int i = 0;i<memberDays.length;i++){
                            DayDateBean dayDateBean = new DayDateBean();
                            dayDateBean.setChecked(true);
                            dayDateBean.setId(memberDays[i]);
                            String value = "";

                            if("1".equals(memberDays[i])){
                                value ="01";
                                dayNameSb.append("01号").append("、");
                            }else if("2".equals(memberDays[i])){
                                value ="02";
                                dayNameSb.append("02号").append("、");
                            }else if("3".equals(memberDays[i])){
                                value ="03";
                                dayNameSb.append("03号").append("、");
                            }else if("4".equals(memberDays[i])){
                                value ="04";
                                dayNameSb.append("04号").append("、");
                            }else if("5".equals(memberDays[i])){
                                value ="05";
                                dayNameSb.append("05号").append("、");
                            }else if("6".equals(memberDays[i])){
                                value ="06";
                                dayNameSb.append("06号").append("、");
                            }else if("7".equals(memberDays[i])){
                                value ="07";
                                dayNameSb.append("07号").append("、");
                            }else if("8".equals(memberDays[i])){
                                value ="08";
                                dayNameSb.append("08号").append("、");
                            }else if("9".equals(memberDays[i])){
                                value ="09";
                                dayNameSb.append("09号").append("、");
                            }else {
                                value =memberDays[i];
                                dayNameSb.append(memberDays[i]).append("号、");
                            }
                            dayDateBean.setValue(value);
                            selectDayDateBeanList.add(dayDateBean);
                        }

                        String dayNameA= dayNameSb.toString();
                        if(dayNameA!=null && !"".equals(dayNameA)){
                            memberDayStr= dayNameA.substring(0,dayNameA.length() - 1);
                        }
                        tv_month_date.setText(memberDayStr);
                        setMonthStyle();
                    }

                }

            }
        }
        if("NEW".equals(compaignStatus)){
            giveNameTime = giveNameTime.replace("-","");
            giveName = title+giveNameTime;


            if("YES".equals(isEditCampaign)){
                if("NO".equals(isCopyCreate)){
                    edtName.setText(campaignName!=null?campaignName:"");
                }
            }else {
                edtName.setText(giveName);
            }


        }else {
            edtName.setText(campaignName!=null?campaignName:"");
        }
        if("B".equals(grantType)){  //生日礼
            title = "生日礼";
            edtDayDate.setText(sendDay);
        }else if("K".equals(grantType)){ //开卡礼
            title = "开卡礼";
        }else if("M".equals(grantType)){ //会员日
            title = "会员日";
            edtDayDate.setText(sendDay);
        }else if("S".equals(grantType)){ //唤醒消费礼
            title = "唤醒消费礼";
        }else if("V".equals(grantType)){ //唤醒消费礼
            title = "核销后赠券";
        }

        if("S".equals(compaignStatus)||"NEW".equals(compaignStatus)){
            edtName.setEnabled(true);
            llStart.setEnabled(true);
            llEnd.setEnabled(true);
            llDays.setEnabled(true);
            ll_week.setEnabled(true);
            ll_month.setEnabled(true);
            rl_week_month.setEnabled(true);
            flowLayout.setEnabled(true);
        }else {
            edtName.setEnabled(false);
            llStart.setEnabled(false);
            llEnd.setEnabled(false);
            llDays.setEnabled(false);
            ll_week.setEnabled(false);
            ll_month.setEnabled(false);
            rl_week_month.setEnabled(false);
            flowLayout.setEnabled(false);
        }


        presenter.queryCommemberTypeByUserId(this,userId);



        flowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener(){
            @Override
            public void onSelected(Set<Integer> selectPosSet) {

                if(selectPosSet.size()>0){
                    int index = selectPosSet.iterator().next();

                    if(index > 0){
                        Collections.swap(compMemTypeList,0,index);
                        tagAdapter.setSelectedList(0);
                        tagAdapter.notifyDataChanged();

                    }
                    compMemTypeId = String.valueOf(compMemTypeList.get(0).getCompMemtypeId());

                }


                setTitle("choose:" + selectPosSet.toString());
            }
        });


    }

    protected void initWeekAndMonth(){
        dayDateBeanList = new ArrayList<>();
        if("W".equals(memberDayType)){

            DayDateBean dayDateBean = new DayDateBean();
            dayDateBeanList = new ArrayList<>();

            dayDateBean.setId("1");
            dayDateBean.setValue("周一");
            dayDateBean.setChecked(false);
            dayDateBeanList.add(dayDateBean);

            dayDateBean = new DayDateBean();
            dayDateBean.setId("2");
            dayDateBean.setValue("周二");
            dayDateBean.setChecked(false);
            dayDateBeanList.add(dayDateBean);
            dayDateBean = new DayDateBean();
            dayDateBean.setId("3");
            dayDateBean.setValue("周三");
            dayDateBean.setChecked(false);
            dayDateBeanList.add(dayDateBean);
            dayDateBean = new DayDateBean();
            dayDateBean.setId("4");
            dayDateBean.setValue("周四");
            dayDateBean.setChecked(false);
            dayDateBeanList.add(dayDateBean);
            dayDateBean = new DayDateBean();
            dayDateBean.setId("5");
            dayDateBean.setValue("周五");
            dayDateBean.setChecked(false);
            dayDateBeanList.add(dayDateBean);
            dayDateBean = new DayDateBean();
            dayDateBean.setId("6");
            dayDateBean.setValue("周六");
            dayDateBean.setChecked(false);
            dayDateBeanList.add(dayDateBean);
            dayDateBean = new DayDateBean();
            dayDateBean.setId("7");
            dayDateBean.setValue("周日");
            dayDateBean.setChecked(false);
            dayDateBeanList.add(dayDateBean);
        }else {
            DayDateBean dayDateBean = new DayDateBean();
            for (int i = 1;i<= 31;i++){
                dayDateBean = new DayDateBean();
                dayDateBean.setId(String.valueOf(i));
                if(i == 1){

                    dayDateBean.setValue("01");
                }else if(i == 2){
                    dayDateBean.setValue("02");
                }else if(i == 3){
                    dayDateBean.setValue("03");
                }else if(i == 4){
                    dayDateBean.setValue("04");
                }else if(i == 5){
                    dayDateBean.setValue("05");
                }else if(i == 6){
                    dayDateBean.setValue("06");
                }else if(i == 7){
                    dayDateBean.setValue("07");
                }else if(i == 8){
                    dayDateBean.setValue("08");
                }else if(i == 9){
                    dayDateBean.setValue("09");
                }else {
                    dayDateBean.setValue(String.valueOf(i));
                }
                dayDateBean.setChecked(false);
                dayDateBeanList.add(dayDateBean);
            }
        }
    }
    protected void initFlowLayout(){
        final LayoutInflater mInflater = LayoutInflater.from(this);

        tagAdapter =new TagAdapter<CompMemberType>(compMemTypeList) {
            @Override
            public View getView(FlowLayout parent, int position, CompMemberType compMemberType) {

                TextView tv = (TextView) mInflater.inflate(R.layout.layout_campaign_coupon_flow_txt,
                        flowLayout, false);


               String  compMemTypeId2 = String.valueOf(compMemberType.getCompMemtypeId());


                String compMemTypeName = compMemberType.getMemtypeName();

                tv.setText(compMemTypeName != null ? compMemTypeName:"");
                return tv;
            }

        };


        flowLayout.setAdapter(tagAdapter);

        final ViewTreeObserver vto =flowLayout.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                flowLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                 height = flowLayout.getMeasuredHeight();
                int width = flowLayout.getMeasuredWidth();
                return true;
            }
        });

    }

   protected void setDefaultFlowLayout(){
       if("NEW".equals(compaignStatus)){

           if("YES".equals(isEditCampaign)){
               if("NO".equals(isCopyCreate)){    //复制并创建
                   if(compMemTypeList != null && compMemTypeList.size()>0){
                       for (int i = 0 ;i<compMemTypeList.size();i++ ){
                           CompMemberType compMemberType = compMemTypeList.get(i);
                           if(compMemberType != null){
                               String typeId = String.valueOf(compMemberType.getCompMemtypeId());

                               if(compMemTypeId != null && !"".equals(compMemTypeId)){
                                   if(compMemTypeId.equals(typeId)){
                                       Collections.swap(compMemTypeList,0,i);
                                       tagAdapter.setSelectedList(0);
                                       tagAdapter.notifyDataChanged();
                                       break;
                                   }
                               }
                           }


                       }
                   }
               }
           }


       }else {

           if(!"S".equals(compaignStatus)){
               List<CompMemberType> selectComType = new ArrayList<>();
               if(compMemTypeList != null && compMemTypeList.size()>0){
                   for (int i = 0 ;i<compMemTypeList.size();i++ ){
                       CompMemberType compMemberType = compMemTypeList.get(i);
                       if(compMemberType != null){
                           String typeId = String.valueOf(compMemberType.getCompMemtypeId());

                           if(compMemTypeId != null && !"".equals(compMemTypeId)){
                               if(compMemTypeId.equals(typeId)){
                                   selectComType.add(compMemberType);
                                   Collections.swap(compMemTypeList,0,i);
                                   tagAdapter.setSelectedList(0);
                                   tagAdapter.notifyDataChanged();
                                   break;
                               }
                           }
                       }
                   }
               }
              /* tagAdapter =new TagAdapter<CompMemberType>(compMemTypeList) {
                   @Override
                   public View getView(FlowLayout parent, int position, CompMemberType compMemberType) {
                       TextView tv = (TextView)  LayoutInflater.from(GiveCouponNewActivity.this).inflate(R.layout.layout_campaign_coupon_flow_txt,
                               flowLayout, false);
                       compMemTypeId = String.valueOf(compMemberType.getCompMemtypeId());
                       compMemTypeName = compMemberType.getMemtypeName();
                       tv.setEnabled(false);
                       tv.setText(compMemTypeName != null ? compMemTypeName:"");
                       return tv;
                   }
               };
               flowLayout.setAdapter(tagAdapter);*/


           }else {
               if(compMemTypeList != null && compMemTypeList.size()>0){
                   for (int i = 0 ;i<compMemTypeList.size();i++ ){
                       CompMemberType compMemberType = compMemTypeList.get(i);
                       if(compMemberType != null){
                           String typeId = String.valueOf(compMemberType.getCompMemtypeId());

                           if(compMemTypeId != null && !"".equals(compMemTypeId)){
                               if(compMemTypeId.equals(typeId)){
                                   Collections.swap(compMemTypeList,0,i);
                                   tagAdapter.setSelectedList(0);
                                   tagAdapter.notifyDataChanged();
                                   break;
                               }
                           }
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

   }
   protected  void setMonthStyle(){
       tv_tab_week.setTextColor(getResources().getColor(R.color.color_gray_deep));
       line_week.setBackgroundColor(getResources().getColor(R.color.color_gray_deep));
       tv_tab_month.setTextColor(getResources().getColor(R.color.font_color_blue));
       line_month.setBackgroundColor(getResources().getColor(R.color.font_color_blue));
       line_month.setVisibility(View.VISIBLE);
       line_week.setVisibility(View.GONE);
       tv_week_month_label.setText("每月选定成为会员日时间");
       tv_month_date.setVisibility(View.VISIBLE);
       tv_week_date.setVisibility(View.GONE);
   }

    protected  void setWeekStyle(){
        tv_tab_week.setTextColor(getResources().getColor(R.color.font_color_blue));
        line_week.setBackgroundColor(getResources().getColor(R.color.font_color_blue));
        tv_tab_month.setTextColor(getResources().getColor(R.color.color_gray_deep));
        line_month.setBackgroundColor(getResources().getColor(R.color.color_gray_deep));
        line_month.setVisibility(View.GONE);
        line_week.setVisibility(View.VISIBLE);
        tv_week_month_label.setText("每周选定成为会员日时间");
        tv_month_date.setVisibility(View.GONE);
        tv_week_date.setVisibility(View.VISIBLE);
    }


    private void animate(int start, int end) {
//        if (start < 0 || end < 0) {
//            ViewGroup.LayoutParams layoutParams = hsv_tag_content.getLayoutParams();
//            layoutParams.height = -1;
//            hsv_tag_content.setLayoutParams(layoutParams);
//            return;
//        }
        if (start < 0) {
            start = flowLayout.getMeasuredHeight();
        }
        if(end<0){
            end = flowLayout.getMeasuredHeight();
        }
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(animationDuration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = hsv_tag_content.getLayoutParams();
                layoutParams.height = value;
                hsv_tag_content.setLayoutParams(layoutParams);
            }
        });
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    public void setMinVisibleHeight(int minVisibleHeight) {
        int minPx = ScreenUtils.dip2px(this, minVisibleHeight);
        if (!isMore) {
            animate(this.minVisibleHeight, minPx);
        }
        this.minVisibleHeight = minPx;
    }

    public int getMaxVisibleHeight() {
        return maxVisibleHeight;
    }

    public void setMaxVisibleHeight(int maxVisibleHeight) {
        int maxPx = ScreenUtils.dip2px(this, maxVisibleHeight);
        if (isMore) {
            animate(this.maxVisibleHeight, maxPx);
        }
        this.maxVisibleHeight = maxPx;
    }



    @OnClick({R.id.iv_back,R.id.tv_right,R.id.iv_left_subtract,R.id.iv_right_add,
            R.id.ll_start,R.id.ll_end,R.id.btn_next,R.id.ll_arrow,
            R.id.rl_week_month,R.id.ll_week,R.id.ll_month})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.startActivity(GiveCouponManageActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
                break;
            case R.id.ll_arrow:
               /* if (isMore) {
                    isMore = false;
                    flowLayout.setMaxLine(3);
//                    flowLayoutLvCategory.removeAllViews();
//                    Utils.addflow(context, list.get(position).getNext().size(), list.get(position).getNext(), flowLayoutLvCategory);
                    iv_arrow.setBackgroundResource(R.drawable.ic_arrow_down);
                } else {
                    isMore = true;
                    flowLayout.setMaxLine(flowLayout.getTotalLine());
//                    flowLayoutLvCategory.removeAllViews();
//                    Utils.addflow(context, 6, list.get(position).getNext(), flowLayoutLvCategory);
                    iv_arrow.setBackgroundResource(R.drawable.ic_arrow_up);
                }*/

                if (!isMore) {
                    ObjectAnimator.ofFloat(iv_arrow, "rotation", 0, 180).start();
//                    animate(minVisibleHeight, maxVisibleHeight);
                    setMinVisibleHeight(70);
//                    tv_more_hint.setText(foldHint);//点击收回


                } else {
                    ObjectAnimator.ofFloat(iv_arrow, "rotation", -180, 0).start();


                    setMaxVisibleHeight((height-300)/2);
//                    animate(maxVisibleHeight, minVisibleHeight);
//                    tv_more_hint.setText(expandHint);//点击展开
                }
                //设置false不可滑动
                hsv_tag_content.setCanScroll(false);
                isMore = !isMore;

                break;
            case R.id.ll_week:
                memberDayType = "W";
                selectDayDateBeanList = new ArrayList<>();
                initWeekAndMonth();
                setWeekStyle();

                break;
            case R.id.ll_month:
                selectDayDateBeanList = new ArrayList<>();
                memberDayType = "D";
                initWeekAndMonth();
                setMonthStyle();
                break;
            case R.id.rl_week_month:

                setWeekDialog();

                break;
            case R.id.tv_right:
                ToastUtils.showCustomShortBottom(getResources().getString(R.string.create_success));
                campaignName = edtName.getText().toString().trim();
                startDate = tvStartTime.getText().toString().trim();
                endDate = tvEndTime.getText().toString().trim();
                sendDay = edtDayDate.getText().toString().trim();
                //复制并创建
                isCopyCreate = "NO";
                isEditCampaign = "NO";
                compaignStatus = "NEW";
                edtName.setEnabled(true);
                llStart.setEnabled(true);
                llEnd.setEnabled(true);
                llDays.setEnabled(true);
                ll_week.setEnabled(true);
                ll_month.setEnabled(true);
                rl_week_month.setEnabled(true);
                if(campaginGrant == null){
                    campaginGrant = new CampaginGrant();

                }

                if("K".equals(grantType)){  //开卡礼
                    campaginGrant.setSendDay(null);
                    campaginGrant.setMemberDayType(null);
                    campaginGrant.setMemberDay(null);
                }else if("B".equals(grantType)){ //生日礼
                    if(sendDay != null && !"".equals(sendDay)){
                        campaginGrant.setSendDay(Integer.parseInt(sendDay));
                    }else {
                        campaginGrant.setSendDay(null);
                    }

                    campaginGrant.setMemberDayType(null);
                    campaginGrant.setMemberDay(null);
                }else if("M".equals(grantType)){ //会员日
                    campaginGrant.setMemberDayType(memberDayType);
                    campaginGrant.setMemberDay(memberDay);
                    if(sendDay != null && !"".equals(sendDay)){
                        campaginGrant.setSendDay(Integer.parseInt(sendDay));
                    }else {
                        campaginGrant.setSendDay(null);
                    }
                }

                campaginGrant.setGrantType(grantType);

                if(compMemTypeId != null && !"".equals(compMemTypeId)){
                    campaginGrant.setCompMemTypeId(compMemTypeId);
                }else {
                    campaginGrant.setCompMemTypeId(null);
                }

                if(campaign !=null){
                    edtName.setText("");
                    campaign.setCampaignName("");
                    campaign.setClient(2);
                    campaign.setCampaignType("zj");
                    campaign.setCompId(Long.parseLong(compId));
                    campaign.setCampaignName(campaignName);
                    campaign.setBeginDate(startDate!=null&&!"".equals(startDate)?startDate.replace(".","-")+" " +"00:00:00":"");
                    campaign.setEndDate(endDate!=null&&!"".equals(endDate)?endDate.replace(".","-")+" "+"23:59:59":"");
                    tvRight.setVisibility(View.GONE);

                }
                campaign.setSendType(grantType);
                campaign.setCampaignId(null);
//                campaign.setPosterTemplete("");
                //复制并粘贴缓存数据



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
                if(getValidDayStr != null && !"".equals(getValidDayStr)){
                    getValidDay = Integer.parseInt(getValidDayStr);
                }else {
                    getValidDay = 0;

                }
                getValidDay++;
                if( getValidDay >99){
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
            case R.id.ll_start:  //开始时间
                KeyboardUtils.hideSoftInput(edtName);
                isStartDate =true;
                pvCustomTime.show();
                break;
            case R.id.ll_end: //结束时间
                KeyboardUtils.hideSoftInput(edtName);
                isStartDate =false;
                pvCustomTime.show();
                break;
            case R.id.btn_next: //下一步
                Intent intent = null;
                if("S".equals(grantType)){   //唤醒消费礼

                    intent = new Intent(GiveCouponNewActivity.this,GiveCouponNewNextConsumeActivity.class);
                    intent.putExtra("grantType",grantType);
                }else if("V".equals(grantType)){   //唤醒消费礼
                    intent = new Intent(GiveCouponNewActivity.this,GiveCouponNewNextConsumeActivity.class);
                    intent.putExtra("grantType",grantType);
                }else {

                    intent = new Intent(GiveCouponNewActivity.this,GiveCouponNewNextActivity.class);
                }
                campaignName = edtName.getText().toString().trim();
                startDate = tvStartTime.getText().toString().trim();
                endDate = tvEndTime.getText().toString().trim();
                sendDay = edtDayDate.getText().toString().trim();

//                campaginGrant.setGrantType(grantType);
                if(campaginGrant == null){
                    campaginGrant = new CampaginGrant();

                }

                if(campaign == null){
                    campaign = new Campaign();

                }


                if("K".equals(grantType)){  //开卡礼
                    campaginGrant.setSendDay(null);
                    campaginGrant.setMemberDayType(null);
                    campaginGrant.setMemberDay(null);
                }else if("B".equals(grantType)){ //生日礼
                    if(sendDay != null && !"".equals(sendDay)){
                        campaginGrant.setSendDay(Integer.parseInt(sendDay));
                    }else {
                        campaginGrant.setSendDay(null);
                    }
                    campaginGrant.setMemberDayType(null);
                    campaginGrant.setMemberDay(null);
                }else if("M".equals(grantType)){ //会员日
                    campaginGrant.setMemberDayType(memberDayType);
                    campaginGrant.setMemberDay(memberDay);
                    if(sendDay != null && !"".equals(sendDay)){
                        campaginGrant.setSendDay(Integer.parseInt(sendDay));
                    }else {
                        campaginGrant.setSendDay(null);
                    }
                }else  if("S".equals(grantType)){  //开卡礼
                    campaginGrant.setSendDay(null);
                    campaginGrant.setMemberDayType(null);
                    campaginGrant.setMemberDay(null);
                }
                campaginGrant.setGrantType(grantType);
                if(compMemTypeId != null && !"".equals(compMemTypeId)){
                    campaginGrant.setCompMemTypeId(compMemTypeId);
                }else {
                    campaginGrant.setCompMemTypeId(null);
                }
                //新建
                campaign.setClient(2);
                campaign.setCampaignType(campaignType);
                campaign.setCompId(Long.parseLong(compId));
                campaign.setCampaignName(campaignName);
                campaign.setSendType(grantType);


                campaign.setBeginDate(startDate!=null&&!"".equals(startDate)?startDate.replace(".","-")+" " +"00:00:00":"");
                campaign.setEndDate(endDate!=null&&!"".equals(endDate)?endDate.replace(".","-")+" "+"23:59:59":"");

                intent.putExtra("isCopyCreate",isCopyCreate);
                intent.putExtra("isEditCampaign",isEditCampaign);
                intent.putExtra("grantType",grantType);
                //缓存本地
//                SPObjUtil.save("campaign",campaign);
//                intent.putExtra("campaign",campaign);
                intent.putExtra("title",title);

                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.startActivity(GiveCouponManageActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
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


    protected  AlertDialog.Builder builder  = null;
    protected AlertDialog dialog = null;
    private void setWeekDialog(){


        View rootView = View.inflate(this, R.layout.layout_give_coupon_day_date_grid, null);
        builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        LinearLayout ivCancel = (LinearLayout) rootView.findViewById(R.id.ll_cancel);
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        Button cancelBtn = (Button) rootView.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button confirmBtn = (Button) rootView.findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder daySb = new StringBuilder();
                StringBuilder dayNameWeekSb = new StringBuilder();
                StringBuilder dayNameMonthSb = new StringBuilder();
                for (DayDateBean dayDateBean : dayDateBeanList){
                    if(dayDateBean.getChecked()){
                        daySb.append(dayDateBean.getId()).append(",");
                        if("W".equals(memberDayType)){
                            dayNameWeekSb.append(dayDateBean.getValue()).append("、");
                        }else {
                            dayNameMonthSb.append(dayDateBean.getValue()+"号").append("、");
                        }

                    }
                }

                String dayA= daySb.toString();
                if(dayA!=null && !"".equals(dayA)){
                     memberDay= dayA.substring(0,dayA.length() - 1);
                }

                String dayNameWeekA= dayNameWeekSb.toString();
                if(dayNameWeekA!=null && !"".equals(dayNameWeekA)){
                    if("W".equals(memberDayType)){
                        memberDayStr= dayNameWeekA.substring(0,dayNameWeekA.length() - 1);
                    }

                    tv_week_date.setText(memberDayStr);
                    tv_month_date.setText("");
                    setWeekStyle();
                }
                String dayNameMonthA= dayNameMonthSb.toString();
                if(dayNameMonthA!=null && !"".equals(dayNameMonthA)){
                    if("D".equals(memberDayType)){
                        memberDayStr= dayNameMonthA.substring(0,dayNameMonthA.length() - 1);
                    }
                    tv_month_date.setText(memberDayStr);
                    tv_week_date.setText("");
                    setMonthStyle();
                }

                dialog.dismiss();
            }
        });

        TextView tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        MyGridView weekGrid= (MyGridView) rootView.findViewById(R.id.week_grid);

//        MyGridView monthGrid= (MyGridView) rootView.findViewById(R.id.month_grid);

        if("W".equals(memberDayType)){
            tv_title.setText("按周选择（可多选）");
            for(DayDateBean dayDateBean1 : dayDateBeanList){
                String id = dayDateBean1.getId();
                if(selectDayDateBeanList!= null && selectDayDateBeanList.size()>0){
                    for (DayDateBean dayDateBean2 :selectDayDateBeanList){

                        String id2 = dayDateBean2.getId();
                        if(id.equals(id2)){
                            dayDateBean1.setChecked(true);
                            break;
                        }

                    }
                }
            }
            mAdapter = new GiveCouponDayAdapter(GiveCouponNewActivity.this,dayDateBeanList);
            weekGrid.setAdapter(mAdapter);
        }else {
            tv_title.setText("按月选择（可多选）");
            weekGrid.setColumnWidth(30);
            weekGrid.setNumColumns(6);

            for(DayDateBean dayDateBean1 : dayDateBeanList){
                String id = dayDateBean1.getId();
                if(selectDayDateBeanList!= null && selectDayDateBeanList.size()>0){
                    for (DayDateBean dayDateBean2 :selectDayDateBeanList){

                        String id2 = dayDateBean2.getId();
                        if(id.equals(id2)){
                            dayDateBean1.setChecked(true);
                            break;
                        }

                    }
                }
            }
            mAdapter = new GiveCouponDayAdapter(GiveCouponNewActivity.this,dayDateBeanList);
            weekGrid.setAdapter(mAdapter);
        }

//        mAdapter.notifyDataSetChanged();

        weekGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentNum == -1){ //选中
                    if(dayDateBeanList.get(position).getChecked()){
                        dayDateBeanList.get(position).setChecked(false);
                        setRemoveUnchecked( dayDateBeanList.get(position));
                    }else {
                        dayDateBeanList.get(position).setChecked(true);
                        selectDayDateBeanList.add(dayDateBeanList.get(position));
                    }

                    currentNum = position;
                }else if(currentNum == position){ //同一个item选中变未选中
                    dayDateBeanList.get(position).setChecked(false);
                    setRemoveUnchecked( dayDateBeanList.get(position));
                    currentNum = -1;
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的

                    if(dayDateBeanList.get(position).getChecked()){
                        dayDateBeanList.get(position).setChecked(false);
                        setRemoveUnchecked(dayDateBeanList.get(position));
                    }else {
                        dayDateBeanList.get(position).setChecked(true);
                        selectDayDateBeanList.add(dayDateBeanList.get(position));
                    }
                    currentNum = position;
                }

                mAdapter.notifyDataSetChanged();
            }
        });



    }

    protected   void  setRemoveUnchecked(DayDateBean removeday){

        if(selectDayDateBeanList !=null && selectDayDateBeanList.size()>0){
            for (int i = 0;i <selectDayDateBeanList.size();i++){
                DayDateBean dayDateBean = selectDayDateBeanList.get(i);
                if(dayDateBean!=null){
                    boolean unChecked = dayDateBean.getChecked();
                    if(!unChecked){
                        String removeName = removeday.getValue();
                        String removeName2 = dayDateBean.getValue();
                        if(removeName!=null){
                            if(removeName2!=null){
                                if(removeName.equals(removeName2)){
                                    selectDayDateBeanList.remove(i);
                                    i--;
                                }
                            }
                        }
                    }


                }
            }
        }
    }

}
