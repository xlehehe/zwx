package com.zwx.scan.app.feature.member;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwx.library.pickerview.picker.builder.TimePickerBuilder;
import com.zwx.library.pickerview.picker.listener.CustomListener;
import com.zwx.library.pickerview.picker.listener.OnTimeSelectListener;
import com.zwx.library.pickerview.picker.view.TimePickerView;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.DateUtils;
import com.zwx.library.utils.KeyboardUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.base.adapter.SelectConstantListViewAdapter;
import com.zwx.scan.app.data.bean.Brand;
import com.zwx.scan.app.data.bean.CompMemberType;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.main.MainActivity;
import com.zwx.scan.app.utils.EmojiInputFilter;
import com.zwx.scan.app.utils.MaxTextLengthFilter;
import com.zwx.scan.app.widget.ContainsEmojiEditText;
import com.zwx.scan.app.widget.CustomPopWindow;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/04/04
 * desc   : 会员卡新增第一步
 * version: 1.0
 **/
public class MemberCardNewActivity extends BaseActivity<MemberManageContract.Presenter> implements MemberManageContract.View,View.OnClickListener  {
    protected static Logger log = Logger.getLogger(MemberCardNewActivity.class);
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


    @BindView(R.id.edt_card_name)
    protected EditText edt_card_name;

    @BindView(R.id.tv_brand_name)
    protected TextView tv_brand_name;

    @BindView(R.id.edt_pos_id)
    protected EditText edt_pos_id;

    @BindView(R.id.ll_pos)
    protected LinearLayout ll_pos;

    @BindView(R.id.tv_consume_join)
    protected TextView tv_consume_join;

    @BindView(R.id.ll_buy_money)
    protected LinearLayout ll_buy_money;

    @BindView(R.id.tv_member_rights)
    protected TextView tv_member_rights;
    //购买金额
    @BindView(R.id.edt_buy_money)
    protected EditText edt_buy_money;

    @BindView(R.id.edt_rights_content)
    protected EditText edt_rights_content;
    @BindView(R.id.tv_rights_content_num)
    protected TextView tv_rights_content_num;

    //其他共享
    @BindView(R.id.tv_other_share)
    protected TextView tv_other_share;



    //不可共享
    @BindView(R.id.ll_long_effective)
    protected LinearLayout ll_long_effective;

    //是否长期有效
    @BindView(R.id.tv_long_effective)
    protected TextView tv_long_effective;


    //设置方式
    @BindView(R.id.tv_set_way)
    protected TextView tv_set_way;

    //开卡之日起，多长时间有效 时间
    @BindView(R.id.rl_set_way_kai_ka)
    protected RelativeLayout rl_set_way_kai_ka;
    @BindView(R.id.edt_set_way_kai_ka)
    protected TextView edt_set_way_kai_ka;

    //过期天数
    @BindView(R.id.edt_set_way_guo_qi)
    protected EditText edt_set_way_guo_qi;

    //过期描述
    @BindView(R.id.edt_guo_qi_tip)
    protected EditText edt_guo_qi_tip;

    //固定日期
    @BindView(R.id.ll_date)
    protected LinearLayout ll_date;

    @BindView(R.id.ll_start)
    protected LinearLayout llStartTime;

    @BindView(R.id.ll_end)
    protected LinearLayout llEndTime;
    //开卡日期时间段
    @BindView(R.id.tv_start_time)
    protected TextView tvStartTime;


    @BindView(R.id.tv_end_time)
    protected TextView tvEndTime;


    @BindView(R.id.edt_remark)
    protected EditText edt_remark;


    @BindView(R.id.tv_remark_num)
    protected TextView tv_remark_num;

    @BindView(R.id.tv_guo_qi_tip_num)
    protected TextView tv_guo_qi_tip_num;

    //是否长期有效
    @BindView(R.id.tv_home_is_display)
    protected TextView tv_home_is_display;



    private PopWindow mListPopWindow,isSharePop,effectivePop,setWayPop;
    //消费者加入条件
    List<CommonConstantBean> consumeJoinList = new ArrayList<>();
    //是否共享
    List<CommonConstantBean> isShareList = new ArrayList<>();
    List<CommonConstantBean> effectiveList = new ArrayList<>();
    List<CommonConstantBean> isDisplayList = new ArrayList<>();
    List<CommonConstantBean> dateList = new ArrayList<>();

    List<CommonConstantBean> templateList = new ArrayList<>();
    //按固定日期区分开始时间和结束时间
    private boolean isStartDate;
    protected TimePickerView pvCustomTime;


//    private String shareValue =  "1";
    //加入条件 默认免费在线领取
//    private String joinValue =  "1";

    protected static String joinCondition = "0";
    //会员权益
    private String memberRights =  "";

    //设置方式
    protected static String wayValue =  "R";

    //是否长期有效a
    protected static String effectiveValue =  "N";
    //首页是否可见
    protected static String isDisplayValue =  "0";   //0可见 1不可见
    protected   String isEditCampaign = "NO";

    private String isCopyCreate = "YES" ;
    public static CompMemberType compMemberType = new CompMemberType();

    //会员卡名称
    protected static String compMemberGroup ;

    //pos码
    protected String posId;

    //权益说明
    protected String memtypeNotes;

    /**
     * 绝对时间的起始时间
     */
    protected static String absoluteStartime;
    /**
     * 绝对时间的结束时间
     */
    protected static String absoluteEndtime;

    /**
     * 购买金额
     */
    protected String purchasingPrice;

    /**
     * 是否共享
     * */
    protected static String isShareright = "0";

    /**
     * 过期提示时间，过期前几天发出提示。
     */
    protected String promptTime;

    /**
     * 开卡日提示时间
     * */
    protected static String relativeTime;
    /**
     * 过期提示语
     */
    protected String reminder = "尊敬的客户：您好，您的会员卡即将过期喔。为了避免您就餐无法享受现有权益和获取更多优惠，请您点我继续领取吧。";
    /**
     * 备注
     */
    protected String notes = "本会员卡，最终解释权，归消费店铺所有。";

    /**
     * 会员卡升级类型 T-升级/F不升级
     */
    protected String upgradeType = "F";


    protected static Brand brand = new Brand();

    protected String brandName = "";

    protected static String compMemtypeId = "";

    /**
     * 是否为默认卡（1不是 0是）
     */
    protected static String isDefault;

    protected String userId;

    //是否编辑还是新建 NEW 新建 EDIT 编辑
    protected static String memberCardStatusNew = "NEW";

    //会员权益文字个数
    protected String rightsNum = "0";

    protected String guoQiNum = "0";
    protected String remarkNum = "0";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_card_new;
    }

    @Override
    protected void initView() {
        DaggerMemberManageComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .memberManageModule(new MemberManageModule(this))
                .build()
                .inject(this);
        tvTitle.setText("会员卡设置");
        brandName = SPUtils.getInstance().getString("brandName");
        tv_brand_name.setText(brandName != null && !"".equals(brandName)?brandName:"");
        setSetTep();

        //按固定起止时间
        initDatePicker();

        isShow();
        //是否编辑还是新建
        isEditCampaign =  getIntent().getStringExtra("isEditCampaign");
        isCopyCreate =  getIntent().getStringExtra("isCopyCreate");
        compMemtypeId = getIntent().getStringExtra("compMemtypeId");
        userId  = SPUtils.getInstance().getString("userId");
        memberCardStatusNew = getIntent().getStringExtra("memberCardStatusNew");
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

        if("NEW".equals(memberCardStatusNew)){  //新建

        }else if("EDIT".equals(memberCardStatusNew)){ //编辑
            presenter.doQueryByGroup(this,userId,compMemtypeId);
        }
        initMemberCardValue();

    }

    @Override
    protected void initData() {

        CommonConstantBean commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(true);
        commonConstantBean.setKey("免费在线领取");
        commonConstantBean.setValue("0");
        consumeJoinList.add(commonConstantBean);
        commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(false);
        commonConstantBean.setKey("在线购买（非储值）");
        commonConstantBean.setValue("1");
        consumeJoinList.add(commonConstantBean);

        commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(true);
        commonConstantBean.setKey("不可共享");
        commonConstantBean.setValue("0");
        isShareList.add(commonConstantBean);
        commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(false);
        commonConstantBean.setKey("可共享");
        commonConstantBean.setValue("1");
        isShareList.add(commonConstantBean);

        //是否长期有效
        commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(true);
        commonConstantBean.setKey("是");
        commonConstantBean.setValue("N");
        effectiveList.add(commonConstantBean);
        commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(false);
        commonConstantBean.setKey("否");
        commonConstantBean.setValue("A");
        effectiveList.add(commonConstantBean);

        //设置方式(时间)
        commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(true);
        commonConstantBean.setKey("根据开卡日设置");
        commonConstantBean.setValue("R");
        dateList.add(commonConstantBean);
        commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(false);
        commonConstantBean.setKey("固定起止时间");
        commonConstantBean.setValue("A");
        dateList.add(commonConstantBean);


        commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(false);
        commonConstantBean.setKey("该会员可以享受，9.8折优惠，每周每天可 用，限本人使用。");
        commonConstantBean.setValue("1.");
        templateList.add(commonConstantBean);
        commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(false);
        commonConstantBean.setKey("该会员可以享受，会员菜价，每周每天可 用，限本人可用。");
        commonConstantBean.setValue("2.");
        templateList.add(commonConstantBean);

        commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(false);
        commonConstantBean.setKey("持卡年限内，可享受生日礼、活动特惠等独 享会员服务。");
        commonConstantBean.setValue("3.");
        templateList.add(commonConstantBean);
        commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(false);
        commonConstantBean.setKey("持卡消费时请您主动出示收银台。");
        commonConstantBean.setValue("4.");
        templateList.add(commonConstantBean);

        commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(false);
        commonConstantBean.setKey("本卡不找零、不提现、不兑现、不退换过期 无效。");
        commonConstantBean.setValue("5.");
        templateList.add(commonConstantBean);


        //首页是否可见
        commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(true);
        commonConstantBean.setKey("是");
        commonConstantBean.setValue("0");
        isDisplayList.add(commonConstantBean);
        commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(false);
        commonConstantBean.setKey("否");
        commonConstantBean.setValue("1");
        isDisplayList.add(commonConstantBean);
       /* edt_guo_qi_tip.setSelection(edt_guo_qi_tip.getText().toString().trim().length());
        edt_remark.setSelection(edt_remark.getText().toString().trim().length());
        edt_rights_content.setSelection(edt_rights_content.getText().toString().trim().length());*/

        setListener();
    }
    
    
    private void initMemberCardValue(){
        if(compMemberType!= null ){
            isDefault = compMemberType.getIsDefault();

            compMemberGroup = compMemberType.getCompMemberGroup();

            //会员卡名称
            edt_card_name.setText(compMemberGroup!= null && !"".equals(compMemberGroup)?compMemberGroup:"");
            //品牌
            if(brand != null ){
                brandName = brand.getName();

            }
            //pos Id
            posId = compMemberType.getPosId();
            edt_pos_id.setText(posId != null && !"".equals(posId)?posId:"");


            //消费加入条件
            joinCondition = compMemberType.getJoinCondition();
            //购买金额
            BigDecimal price = compMemberType.getPurchasingPrice();



            //消费加入条件
            if("1".equals(joinCondition)){ //在线购买

                ll_buy_money.setVisibility(View.VISIBLE);
                if(price != null && price.doubleValue()>0){
                    purchasingPrice = RegexUtils.getDoubleString(price.doubleValue());
                }else {
                    purchasingPrice = "";
                }
                //购买金额
                edt_buy_money.setText(purchasingPrice);
                tv_consume_join.setText("在线购买（非储值）");
            }else if("0".equals(joinCondition)){ //免费领取
                purchasingPrice = "";
                ll_buy_money.setVisibility(View.GONE);
                tv_consume_join.setText("免费在线领取");
            }else{
                joinCondition = "0";
                purchasingPrice = "";
                ll_buy_money.setVisibility(View.GONE);
                tv_consume_join.setText("免费在线领取");
            }

            //会员权益
            isShareright = compMemberType.getIsShareright();



            if("1".equals(isShareright)){ //可共享
                tv_other_share.setText("可共享");
            }else if("0".equals(isShareright)){//不可共享
                tv_other_share.setText("不可共享");
            }else {
                isShareright = "0";
                tv_other_share.setText("不可共享");
            }

            //会员权益说明
            memtypeNotes = compMemberType.getMemtypeNotes();
            if(memtypeNotes != null && !"".equals(memtypeNotes)){
                edt_rights_content.setText(memtypeNotes);
                rightsNum = String.valueOf(memtypeNotes.length());
                tv_rights_content_num.setText(rightsNum+"/500");
            }else {
                tv_rights_content_num.setText(rightsNum+"/500");
            }



            //N表示长期有效 A或R表示否
            effectiveValue = compMemberType.getCustomTime();

            if(effectiveValue == null || "".equals(effectiveValue)){
                effectiveValue = "N";
            }
            if("N".equals(effectiveValue)){
                tv_long_effective.setText("是");
                ll_long_effective.setVisibility(View.GONE);
            }else {

                tv_long_effective.setText("否");
                wayValue = compMemberType.getCustomTime();
                if("A".equals(wayValue)){   //按起止时间天
                    String endTime = compMemberType.getAbsoluteEndtime();
                    String startTime = compMemberType.getAbsoluteStartime();
                    if(startTime != null && !"".equals(startTime)){
                        Date date = DateUtils.parse(startTime,"yyyy-MM-dd");
                        absoluteStartime = DateUtils.formatDate(date,"yyyy-MM-dd").replace("-",".");
                        tvStartTime.setText(DateUtils.formatDate(date,"yyyy-MM-dd").replace("-","."));
                    }else {
                        tvStartTime.setText("");
                    }

                    if(endTime != null && !"".equals(endTime)){
                        Date date = DateUtils.parse(endTime,"yyyy-MM-dd");
                        absoluteEndtime = DateUtils.formatDate(date,"yyyy-MM-dd").replace("-",".");
                        tvEndTime.setText(absoluteEndtime);
                    }else {
                        tvEndTime.setText("");
                    }
                    //设置方式
                    tv_set_way.setText("固定起止时间");

                    relativeTime = null;
                    tv_long_effective.setText("否");
                    ll_long_effective.setVisibility(View.VISIBLE);
                    rl_set_way_kai_ka.setVisibility(View.GONE);
                    ll_date.setVisibility(View.VISIBLE);
                }else if("R".equals(wayValue)){ //按开卡日时间
                    tv_set_way.setText("根据开卡日设置");
                    tv_long_effective.setText("否");
                    ll_long_effective.setVisibility(View.VISIBLE);
                    rl_set_way_kai_ka.setVisibility(View.VISIBLE);
                    ll_date.setVisibility(View.GONE);
                    absoluteStartime = "";
                    absoluteEndtime  = "";
                    //开卡之日起，多长时间有效
                    Integer reTime = compMemberType.getRelativeTime();
                    if(reTime != null && reTime.intValue() >0){
                        relativeTime = String.valueOf(reTime);
                    }else {
                        relativeTime = "12";
                    }
                    edt_set_way_kai_ka.setText(relativeTime);



                }else {
                    tv_set_way.setText("根据开卡日设置");
                    tv_long_effective.setText("否");
                    ll_long_effective.setVisibility(View.VISIBLE);
                    rl_set_way_kai_ka.setVisibility(View.VISIBLE);
                    ll_date.setVisibility(View.GONE);
                    absoluteStartime = "";
                    absoluteEndtime  = "";
                    //开卡之日起，多长时间有效
                    Integer reTime = compMemberType.getRelativeTime();
                    if(reTime != null && reTime.intValue() >0){
                        relativeTime = String.valueOf(reTime);
                    }else {
                        relativeTime = "12";
                    }
                    edt_set_way_kai_ka.setText(relativeTime);
                }
                //过期前多久通知会员
                Integer proTime = compMemberType.getPromptTime();
                if(proTime != null && proTime.intValue()>0){
                    promptTime = String.valueOf(proTime);
                }else {
                    promptTime = "15";
                }
                edt_set_way_guo_qi.setText(promptTime);

                //过期提示语
                reminder = compMemberType.getReminder();
                if(reminder != null && !"".equals(reminder)){
                    guoQiNum = String.valueOf(reminder.length());
                    tv_guo_qi_tip_num.setText(guoQiNum+"/100");
                    edt_guo_qi_tip.setText(reminder);
                }else {
                    reminder = "尊敬的客户：您好，您的会员卡即将过期喔。为了避免您就餐无法享受现有权益和获取更多优惠，请您点我继续领取吧。";
                    edt_guo_qi_tip.setText(reminder);
                    if(reminder != null && !"".equals(reminder)){
                        guoQiNum = String.valueOf(reminder.length());
                        tv_guo_qi_tip_num.setText(guoQiNum+"/100");
                    }
                }
            }
            //备注
            notes = compMemberType.getNotes();

            if(notes != null && !"".equals(notes)){
                remarkNum = String.valueOf(notes.length());
                tv_remark_num.setText(remarkNum+"/500");
                edt_remark.setText(notes);
            }else {
                notes = "本会员卡，最终解释权，归消费店铺所有。";
                edt_remark.setText(notes);
                if(notes != null && !"".equals(notes)){
                    remarkNum = String.valueOf(notes.length());
                    tv_remark_num.setText(remarkNum+"/500");
                }
            }

            //升级规则类型
            upgradeType = compMemberType.getUpgradeType();

            if(upgradeType == null || "".equals(upgradeType)){
                upgradeType = "F";
            }

            //是否选择模板还是素材
            MemberCardNewTwoActivity.isTemplet = compMemberType.getIsTemplet();

            //从模板选择额
            if("1".equals(MemberCardNewTwoActivity.isTemplet)){

            }else {
                //从素材选择
                Long matId = compMemberType.getMaterialId();
                if(matId != null){
                    MemberCardNewTwoActivity.materialId = String.valueOf(matId);

                }else {
                    MemberCardNewTwoActivity.materialId= "";
                }

            }
            MemberCardNewTwoActivity.background = compMemberType.getBackground();
            MemberCardNewTwoActivity.name = compMemberType.getName();

            MemberCardNewTwoActivity.materialBack = compMemberType.getMaterialBack();
            MemberCardNewTwoActivity.displayRule = compMemberType.getDisplayRule();
            MemberCardNewTwoActivity.colour = compMemberType.getColour();

            //默认会员卡
            if("0".equals(isDefault)){
                //免费在线领取
               joinCondition = "0";
                effectiveValue = "N";
                tv_long_effective.setCompoundDrawables(null,null,null,null);
                tv_consume_join.setCompoundDrawables(null,null,null,null);
                if("N".equals(effectiveValue)){
                    ll_long_effective.setVisibility(View.GONE);

                }else {
                    ll_long_effective.setVisibility(View.VISIBLE);
                }
                tv_home_is_display.setCompoundDrawables(null,null,null,null);
                tv_home_is_display.setEnabled(false);
            }else if("1".equals(isDefault)){  //非默认会员卡
                if("N".equals(effectiveValue)){
                    ll_long_effective.setVisibility(View.GONE);
                }else {
                    ll_long_effective.setVisibility(View.VISIBLE);
                }
            }

            isDisplayValue = compMemberType.getVisible();
            if("0".equals(isDisplayValue)){//可见
                tv_home_is_display.setText("是");
            }else  if("1".equals(isDisplayValue)){ //不可见
                tv_home_is_display.setText("否");
            }else {
                isDisplayValue = "0";  //默认可见
            }


        }else {
            //过期提示语
            reminder = edt_guo_qi_tip.getText().toString().trim();
            if(reminder != null && !"".equals(reminder)){
                guoQiNum = String.valueOf(reminder.length());
                tv_guo_qi_tip_num.setText(guoQiNum+"/100");
            }


            //备注
            notes = edt_remark.getText().toString().trim();
            if(notes != null && !"".equals(notes)){
                remarkNum = String.valueOf(notes.length());
                tv_remark_num.setText(remarkNum+"/500");
            }
        }
    }
    private void setSetTep(){
        ivOne.setBackgroundResource(R.drawable.ic_first_clicked);
        ivEllipsisOne.setBackgroundResource(R.drawable.ic_ellipsis_gray);
        ivTwo.setBackgroundResource(R.drawable.ic_two_untclick);
    }


    private void isShow(){

         if("N".equals(effectiveValue)){ //是长期有效
            ll_long_effective.setVisibility(View.GONE);
        }else {  //否
            ll_long_effective.setVisibility(View.VISIBLE);
             if("R".equals(wayValue)){ //相对时间 开卡日
                 rl_set_way_kai_ka.setVisibility(View.VISIBLE);
                 ll_date.setVisibility(View.GONE);
             }else { //绝对时间
                 rl_set_way_kai_ka.setVisibility(View.GONE);
                 ll_date.setVisibility(View.VISIBLE);
             }
        }

        if("0".equals(joinCondition)){
            ll_buy_money.setVisibility(View.GONE);
        }else {
            ll_buy_money.setVisibility(View.VISIBLE);
        }

    }

    // 获取光标当前位置
    public int getEditSelection() {
        return edt_card_name.getSelectionEnd();
    }
    // 获取文本框的内容
    public String getEditTextViewString() {
        return edt_card_name.getText().toString();
    }


    private void setListener(){
        edt_card_name.setFilters(new InputFilter[]{new EmojiInputFilter()});
        edt_card_name.setFilters(new InputFilter[]{new MaxTextLengthFilter(12)});

        edt_card_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

              /*  if (index < 0 || index >= getEditTextViewString().length()) {
                    edt_card_name.append(s);
                } else {
                    edt_card_name.getEditableText().insert(index, s);// 光标所在位置插入文字
                }*/
                edt_card_name.setCursorVisible(true);
                String  compMemberStr= edt_card_name.getText().toString().trim();
                if(compMemberStr != null && !"".equals(compMemberStr) && compMemberStr.contains(" ")){
//                    int index = getEditSelection();// 光标的位置
//                    edt_card_name.setSelection(index);
//                    edt_card_name.setCursorVisible(false);
//                    edt_card_name.setText(compMemberStr.replace(" ",""));
                    ToastUtils.showShort("会员卡名称不能含有空格");
                }


            }
        });


        edt_pos_id.setFilters(new InputFilter[]{new EmojiInputFilter()});
        edt_pos_id.setFilters(new InputFilter[]{new MaxTextLengthFilter(15)});
        edt_guo_qi_tip.setFilters(new InputFilter[]{new EmojiInputFilter()});
        edt_guo_qi_tip.setFilters(new InputFilter[]{new MaxTextLengthFilter(100)});
        edt_guo_qi_tip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if(edt_guo_qi_tip.getText().toString().trim().length()>0){
                    tv_guo_qi_tip_num.setText(length+"/100");
                }
            }
        });
        edt_remark.setFilters(new InputFilter[]{new EmojiInputFilter()});
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
                int remarkLenth = s.length();
                if(edt_remark.getText().toString().trim().length()>0){

                    tv_remark_num.setText(remarkLenth+"/500");
                }
            }
        });
        edt_rights_content.setFilters(new InputFilter[]{new EmojiInputFilter()});
        edt_rights_content.setFilters(new InputFilter[]{new MaxTextLengthFilter(500)});

        edt_rights_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int remarkLenth = s.length();
                if(edt_rights_content.getText().toString().trim().length()>0){
                    tv_rights_content_num.setText(remarkLenth+"/500");
                }
            }
        });

        edt_rights_content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                //触摸的是EditText而且当前EditText能够滚动则将事件交给EditText处理。否则将事件交由其父类处理
                if ((view.getId() == R.id.edt_rights_content && canVerticalScroll(edt_rights_content))) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;
            }
        });


        edt_remark.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                //触摸的是EditText而且当前EditText能够滚动则将事件交给EditText处理。否则将事件交由其父类处理
                if ((view.getId() == R.id.edt_remark && canVerticalScroll(edt_remark))) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;
            }
        });

    }

    /**
     * EditText竖直方向能否够滚动
     * @param editText  须要推断的EditText
     * @return  true：能够滚动   false：不能够滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() -editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if(scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }
    @OnClick({R.id.iv_back,R.id.tv_right,R.id.tv_consume_join,R.id.tv_other_share,R.id.tv_long_effective,
            R.id.ll_pos,R.id.ll_is_display,R.id.tv_home_is_display,R.id.tv_member_rights,R.id.ll_start,R.id.ll_end,R.id.tv_set_way,R.id.btn_next
            })
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.startActivity(MemberCardManageActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
                break;

            case R.id.tv_right:
                ToastUtils.showCustomShortBottom(getResources().getString(R.string.create_success));

                if(compMemberType == null){
                    compMemberType = new CompMemberType();
                }
                setValue();

                //复制并创建
                isCopyCreate = "NO";
                isEditCampaign = "YES";
                memberCardStatusNew = "NEW";
                edt_card_name.setText("");
                edt_remark.setEnabled(true);
                edt_card_name.setEnabled(true);
                edt_pos_id.setEnabled(true);
                edt_buy_money.setEnabled(true);
                edt_rights_content.setEnabled(true);

                edt_guo_qi_tip.setEnabled(true);
                edt_set_way_guo_qi.setEnabled(true);
                edt_set_way_kai_ka.setEnabled(true);
                tvRight.setVisibility(View.GONE);
                compMemberType.setMemtypeIdArray(null);
                compMemberType.setCompMemtypeId(null);


                break;
            case R.id.ll_pos:  //查看说明
                showPosDescPop();
                break;

            case R.id.tv_home_is_display:  //是否可见

                showHomeIsDisplayListView();
                break;
            case R.id.ll_is_display:  //首页可见 查看弹窗

                showHomeIsDisplayDescPop();
                break;

            case R.id.tv_consume_join:

                showPopListView();
                break;
            case R.id.tv_other_share:

                showIsShareListView();
                break;
            case R.id.tv_long_effective:
                showEffectiveListView();
                break;
            case R.id.tv_member_rights:
                showSelectTemplatePop();
                break;

            case R.id.ll_start:  //开始时间
                isStartDate =true;
                pvCustomTime.show();

                break;
            case R.id.ll_end: //结束时间
                isStartDate =false;
                pvCustomTime.show();
                break;
            case R.id.tv_set_way:
                showSetWayListView();
                break;

            case R.id.btn_next:
                setValue();
                Intent intent = new Intent(MemberCardNewActivity.this,MemberCardNewTwoActivity.class);
                intent.putExtra("isCopyCreate",isCopyCreate);
                intent.putExtra("isEditCampaign",isEditCampaign);

                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.startActivity(MemberCardManageActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }

    private void setValue(){
        String compMemberStr = edt_card_name.getText().toString().trim();

        posId = edt_pos_id.getText().toString().trim();
        if(compMemberStr != null && !"".equals(compMemberStr) && compMemberStr.contains(" ")){
            compMemberGroup = compMemberStr.replace(" ","");
        }else {
            compMemberGroup = compMemberStr;
        }
        compMemberType.setUpgradeType(null);
        compMemberType.setMemtypeName(compMemberGroup);
        compMemberType.setCompMemberGroup(compMemberGroup);
        compMemberType.setPosId(posId);

        if("1".equals(isShareright)){   //可共享tv
            compMemberType.setIsShareright(isShareright);
        }else {
            compMemberType.setIsShareright(isShareright); //不可共享
        }

        compMemberType.setJoinCondition(joinCondition);
        if("1".equals(joinCondition)){ //在线购买

            purchasingPrice = edt_buy_money.getText().toString().trim();
            if(purchasingPrice != null && !"".equals(purchasingPrice)){
                compMemberType.setPurchasingPrice(new BigDecimal(purchasingPrice));
            }
        }else if("0".equals(joinCondition)){ //免费领取
            compMemberType.setPurchasingPrice(new BigDecimal("0"));
        }

        memtypeNotes = edt_rights_content.getText().toString().trim();

        compMemberType.setMemtypeNotes(memtypeNotes != null && !"".equals(memtypeNotes)?memtypeNotes:"");
        if("N".equals(effectiveValue)){  //N表示长期有效 A或R表示否  绝对时间（选择时间段）——A 相对时间（激活之日起多少天）——R
            compMemberType.setCustomTime(effectiveValue);
            compMemberType.setRelativeTime(null);
            compMemberType.setPromptTime(null);
            compMemberType.setReminder(null);
            compMemberType.setAbsoluteEndtime(null);
            compMemberType.setAbsoluteStartime(null);
            wayValue = "";
        }else {   //
            compMemberType.setCustomTime(wayValue);
            if("A".equals(wayValue)){   //按起止时间天
                absoluteStartime = tvStartTime.getText().toString().trim();
                absoluteEndtime = tvEndTime.getText().toString().trim();
                compMemberType.setAbsoluteEndtime(absoluteEndtime!=null&&!"".equals(absoluteEndtime)?absoluteEndtime.replace(".","-")+" "+ "23:59:59":null);
                compMemberType.setAbsoluteStartime(absoluteStartime!=null&&!"".equals(absoluteStartime)?absoluteStartime.replace(".","-")+" "+"00:00:00":null);
                compMemberType.setRelativeTime(null);
                relativeTime = null;
            }else if("R".equals(wayValue)){ //按开卡日时间
                relativeTime = edt_set_way_kai_ka.getText().toString().trim();
                if(relativeTime != null && !"".equals(relativeTime)){
                    compMemberType.setRelativeTime(Integer.parseInt(relativeTime));
                }else {
                    compMemberType.setRelativeTime(null);
                }

                absoluteStartime = null;
                absoluteEndtime  = null;


                compMemberType.setAbsoluteEndtime(null);
                compMemberType.setAbsoluteStartime(null);

            }
            promptTime = edt_set_way_guo_qi.getText().toString().trim();

            if(promptTime != null && !"".equals(promptTime)){
                compMemberType.setPromptTime(Integer.parseInt(promptTime));
            }
            reminder = edt_guo_qi_tip.getText().toString().trim();
            compMemberType.setReminder(reminder);

        }

        notes = edt_remark.getText().toString().trim();
        compMemberType.setNotes(notes);

       //升级规则
        compMemberType.setUpgradeType(upgradeType);

        //首页是否可见

        if(isDisplayValue != null && !"".equals(isDisplayValue)){

        }else {
            isDisplayValue = "0"; //默认可见
        }
        compMemberType.setVisible(isDisplayValue);
    }


    private void showPopListView(){
        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_consume_join,null);
        //处理popWindow 显示内容
//        handleListView(contentView);
        ListView listView = (ListView) contentView.findViewById(R.id.list_view);
        SelectConstantListViewAdapter adapter = new SelectConstantListViewAdapter(MemberCardNewActivity.this);
        adapter.setDatas(consumeJoinList);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(CommonConstantBean constantBean:consumeJoinList){
                    constantBean.setChecked(false);
                }
                CommonConstantBean commonConstantBean = consumeJoinList.get(position);
                commonConstantBean.setChecked(true);

                adapter.notifyDataSetChanged();
                mListPopWindow.dismiss();
                joinCondition = commonConstantBean.getValue();
                tv_consume_join.setText(commonConstantBean.getKey());
                if("1".equals(joinCondition)){  //在线购买

                    ll_buy_money.setVisibility(View.VISIBLE);
                }else { //免费领取
                    ll_buy_money.setVisibility(View.GONE);
                }
            }
        });
        //创建并显示popWindow
        mListPopWindow = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .setView(contentView)
                .show(tv_consume_join);




    }


    private void handleListView(View contentView){
        ListView listView = (ListView) contentView.findViewById(R.id.list_view);
        SelectConstantListViewAdapter adapter = new SelectConstantListViewAdapter(MemberCardNewActivity.this);
        adapter.setDatas(consumeJoinList);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(CommonConstantBean constantBean:consumeJoinList){
                    constantBean.setChecked(false);
                }
                CommonConstantBean commonConstantBean = consumeJoinList.get(position);
                commonConstantBean.setChecked(true);

                adapter.notifyDataSetChanged();
                mListPopWindow.dismiss();
                joinCondition = commonConstantBean.getValue();
                tv_consume_join.setText(commonConstantBean.getKey());
                if("1".equals(joinCondition)){
                    ll_buy_money.setVisibility(View.GONE);
                }else {
                    ll_buy_money.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    protected void showPosDescPop() {

        View customView = View.inflate(this, R.layout.layout_member_card_new_pos_id, null);

        AlertDialog.Builder builder  = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.create();
        dialog.setView(customView, 0, 0, 0, 0);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Button btn_use = (Button)customView.findViewById(R.id.btn_use);
        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        LinearLayout ll_cancel = (LinearLayout)customView.findViewById(R.id.ll_cancel);
        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //快捷模板选择
    protected void showSelectTemplatePop() {

        View customView = View.inflate(this, R.layout.layout_member_card_new_select_template_list, null);

        AlertDialog.Builder builder  = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.create();
        dialog.setView(customView, 0, 0, 0, 0);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        LinearLayout ll_cancel = (LinearLayout)customView.findViewById(R.id.ll_cancel);
        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ListView listView = (ListView) customView.findViewById(R.id.list_view);
        CardSelectTemplateAdapter adapter = new CardSelectTemplateAdapter(MemberCardNewActivity.this);
        adapter.setDatas(templateList);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(CommonConstantBean constantBean:templateList){
                    constantBean.setChecked(false);
                }
                CommonConstantBean commonConstantBean = templateList.get(position);
                commonConstantBean.setChecked(true);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
                String content = edt_rights_content.getText().toString().trim();

                edt_rights_content.setText(content+commonConstantBean.getKey());
                edt_rights_content.setSelection(edt_rights_content.getText().length());

            }
        });
    }
    /**
     * 是否共享
     * */
    private void showIsShareListView(){
        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_consume_join,null);
        //处理popWindow 显示内容
        ListView listView = (ListView) contentView.findViewById(R.id.list_view);
        SelectConstantListViewAdapter adapter = new SelectConstantListViewAdapter(MemberCardNewActivity.this);
        adapter.setDatas(isShareList);

        listView.setAdapter(adapter);
        for(CommonConstantBean constantBean:isShareList){
            constantBean.setChecked(false);
        }
        for(CommonConstantBean constantBean:isShareList){
            if(isShareright.equals(constantBean.getValue())){
                constantBean.setChecked(true);
            }

        }

        adapter.notifyDataSetChanged();
        //创建并显示popWindow
        isSharePop = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .setView(contentView)
                .show(tv_consume_join);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(CommonConstantBean constantBean:isShareList){
                    constantBean.setChecked(false);
                }
                CommonConstantBean commonConstantBean = isShareList.get(position);
                commonConstantBean.setChecked(true);
                adapter.notifyDataSetChanged();
                isSharePop.dismiss();

                tv_other_share.setText(commonConstantBean.getKey());
                isShareright = commonConstantBean.getValue();

               /* if("1".equals(shareValue)){  //可共享
                    ll_long_effective.setVisibility(View.GONE);
                }else if("0".equals(shareValue)){ //不可共享
                    ll_long_effective.setVisibility(View.VISIBLE);
                }*/
            }
        });
    }

    /*
    * 是否长期有效
    * */
    private void showEffectiveListView(){
        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_consume_join,null);
        //处理popWindow 显示内容
        ListView listView = (ListView) contentView.findViewById(R.id.list_view);
        SelectConstantListViewAdapter adapter = new SelectConstantListViewAdapter(MemberCardNewActivity.this);
        adapter.setDatas(effectiveList);

        listView.setAdapter(adapter);
        for(CommonConstantBean constantBean:effectiveList){
            constantBean.setChecked(false);
        }
        for(CommonConstantBean constantBean:effectiveList){
            if(effectiveValue.equals(constantBean.getValue())){
                constantBean.setChecked(true);
            }

        }
        adapter.notifyDataSetChanged();
        //创建并显示popWindow
        effectivePop = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .setView(contentView)
                .show(tv_consume_join);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(CommonConstantBean constantBean:effectiveList){
                    constantBean.setChecked(false);
                }
                CommonConstantBean commonConstantBean = effectiveList.get(position);
                commonConstantBean.setChecked(true);
                adapter.notifyDataSetChanged();
                effectivePop.dismiss();
                tv_long_effective.setText(commonConstantBean.getKey());
                effectiveValue = commonConstantBean.getValue();
                if("N".equals(effectiveValue)){ //长期有效是
                    ll_long_effective.setVisibility(View.GONE);

                }else {  //长期有效 否
                    effectiveValue = "A";
                    ll_long_effective.setVisibility(View.VISIBLE);
                    wayValue  = "R";
                    tv_set_way.setText("根据开卡日设置");
                    rl_set_way_kai_ka.setVisibility(View.VISIBLE);
                    ll_date.setVisibility(View.GONE);

                    reminder = "尊敬的客户：您好，您的会员卡即将过期喔。为了避免您就餐无法享受现有权益和获取更多优惠，请您点我继续领取吧。";
                    edt_guo_qi_tip.setText(reminder);
                    if(reminder != null && !"".equals(reminder)){
                        guoQiNum = String.valueOf(reminder.length());
                        tv_guo_qi_tip_num.setText(guoQiNum+"/100");
                    }
                }

            }
        });
    }



    private void showHomeIsDisplayListView(){
        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_consume_join,null);
        //处理popWindow 显示内容
        ListView listView = (ListView) contentView.findViewById(R.id.list_view);
        SelectConstantListViewAdapter adapter = new SelectConstantListViewAdapter(MemberCardNewActivity.this);
        adapter.setDatas(isDisplayList);

        listView.setAdapter(adapter);
        for(CommonConstantBean constantBean:isDisplayList){
            constantBean.setChecked(false);
        }
        for(CommonConstantBean constantBean:isDisplayList){
            if(isDisplayValue.equals(constantBean.getValue())){
                constantBean.setChecked(true);
            }

        }

        adapter.notifyDataSetChanged();
        //创建并显示popWindow
        PopWindow isDisplayPop = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .setView(contentView)
                .show(tv_consume_join);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(CommonConstantBean constantBean:isDisplayList){
                    constantBean.setChecked(false);
                }
                CommonConstantBean commonConstantBean = isDisplayList.get(position);
                commonConstantBean.setChecked(true);
                adapter.notifyDataSetChanged();
                isDisplayPop.dismiss();
                tv_home_is_display.setText(commonConstantBean.getKey());
                isDisplayValue = commonConstantBean.getValue();


            }
        });
    }
    /**
     * 首页可见 查看
     * */
    protected void showHomeIsDisplayDescPop() {

        View customView = View.inflate(this, R.layout.layout_member_card_new_home_isdisplay, null);

        AlertDialog.Builder builder  = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.create();
        dialog.setView(customView, 0, 0, 0, 0);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView isDisplay = (TextView) customView.findViewById(R.id.tv_home_is_display_style);
        String content = "只能通过"+"<font size = '16'color = \'#0486FE\' weight = 'bolder'>扫码推送消息</font>,或者\n"+"<font size = '16'color = \'#0486FE\' weight = 'bolder'>员工推\n广</font>,，才可领取，购买。";
        isDisplay.setText(Html.fromHtml(content));
        Button confirmBtn = (Button)customView.findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        LinearLayout ll_cancel = (LinearLayout)customView.findViewById(R.id.ll_cancel);
        ll_cancel.setVisibility(View.GONE);
        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 设置方式
     * */
    private void showSetWayListView(){
        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_consume_join,null);
        //处理popWindow 显示内容
        ListView listView = (ListView) contentView.findViewById(R.id.list_view);


        SelectConstantListViewAdapter adapter = new SelectConstantListViewAdapter(MemberCardNewActivity.this);
        adapter.setDatas(dateList);

        listView.setAdapter(adapter);
        for(CommonConstantBean constantBean:dateList){
            constantBean.setChecked(false);
        }
        for(CommonConstantBean constantBean:dateList){
            if(wayValue.equals(constantBean.getValue())){
                constantBean.setChecked(true);
            }

        }
        adapter.notifyDataSetChanged();
        //创建并显示popWindow
        setWayPop = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .setView(contentView)
                .show(tv_consume_join);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(CommonConstantBean constantBean:dateList){
                    constantBean.setChecked(false);
                }
                CommonConstantBean commonConstantBean = dateList.get(position);
                commonConstantBean.setChecked(true);
                adapter.notifyDataSetChanged();
                setWayPop.dismiss();

                tv_set_way.setText(commonConstantBean.getKey());
                wayValue = commonConstantBean.getValue();
                if("A".equals(wayValue)){ //固定起止时间  相对时间
                    rl_set_way_kai_ka.setVisibility(View.GONE);
                    ll_date.setVisibility(View.VISIBLE);
                }else if("R".equals(wayValue)){  //开卡日设置  绝对时间
                    rl_set_way_kai_ka.setVisibility(View.VISIBLE);
                    ll_date.setVisibility(View.GONE);
                }

            }
        });
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
                                        if(end.before(TimeUtils.string2Date(TimeUtils.date2String(TimeUtils.getNowDate())))){
                                            ToastUtils.showToast("结束时间不能早于今日！");
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



    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(effectivePop != null){
            effectivePop.dismiss();
            effectivePop = null;
        }

        if(isSharePop != null){
            isSharePop.dismiss();
            isSharePop = null;
        }

        if(setWayPop != null){
            setWayPop.dismiss();
            setWayPop = null;
        }

        if(mListPopWindow != null){
            mListPopWindow.dismiss();
            mListPopWindow = null;
        }

        if(pvCustomTime != null){
            pvCustomTime.dismiss();
            pvCustomTime = null;
        }


       /* if(pvSaleTime != null){
            pvSaleTime.dismiss();
            pvSaleTime = null;
        }*/
    }
}
