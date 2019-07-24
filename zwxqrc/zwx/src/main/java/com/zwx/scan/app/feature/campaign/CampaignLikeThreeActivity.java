package com.zwx.scan.app.feature.campaign;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zwx.library.pickerview.picker.builder.OptionsPickerBuilder;
import com.zwx.library.pickerview.picker.listener.CustomListener;
import com.zwx.library.pickerview.picker.listener.OnOptionsSelectListener;
import com.zwx.library.pickerview.picker.view.OptionsPickerView;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.KeyboardUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCollect;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CampaignGame;
import com.zwx.scan.app.data.bean.CampaignGamesetreward;
import com.zwx.scan.app.data.bean.CampaignGroupBuy;
import com.zwx.scan.app.data.bean.CardBean;
import com.zwx.scan.app.data.bean.MaterialGame;
import com.zwx.scan.app.data.bean.PosterMaterial;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.ptmanage.PtManageActivity;
import com.zwx.scan.app.feature.ptmanage.PtNextThreeActivity;
import com.zwx.scan.app.utils.ButtonUtils;
import com.zwx.scan.app.utils.CashierInputFilter;
import com.zwx.scan.app.utils.MaxTextLengthFilter;
import com.zwx.scan.app.widget.ContainsEmojiEditText;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/04/06
 * desc   :  集赞活动第三步
 * version: 1.0
 **/
public class CampaignLikeThreeActivity extends BaseActivity<CampaignsContract.Presenter> implements CampaignsContract.View,View.OnClickListener  {

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;


    @BindView(R.id.btn_up)
    protected Button btn_up;

    @BindView(R.id.btn_pre)
    protected Button btnPre;

    @BindView(R.id.btn_save_and_public)
    protected Button btnSavePublic;

    @BindView(R.id.btn_save)
    protected Button btnSave;
    @BindView(R.id.ll_save)
    protected LinearLayout ll_save;
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

    //主动发起参与集赞的次数限制
    @BindView(R.id.per_meiren_ci_rg)
    protected RadioGroup per_meiren_ci_rg;
    @BindView(R.id.per_meiren_left_num_rb)
    protected RadioButton per_meiren_left_num_rb;
    @BindView(R.id.per_meiren_right_num_rb)
    protected RadioButton per_meiren_right_num_rb;

    @BindView(R.id.ll_meiren_ci_num)
    protected LinearLayout ll_meiren_ci_num;


    @BindView(R.id.per_meiren_ci_label)
    protected TextView per_meiren_ci_label;

    @BindView(R.id.edt_zhudong_pintuan_ci_num)
    protected EditText edt_zhudong_pintuan_ci_num;


    //按照小时 自然天数
    @BindView(R.id.per_xiao_ziran_rg)
    protected RadioGroup per_xiao_ziran_rg;

    @BindView(R.id.xiaoshi_left_num_rb)
    protected RadioButton xiaoshi_left_num_rb;
    @BindView(R.id.zirantianshu_right_num_rb)
    protected RadioButton zirantianshu_right_num_rb;


    @BindView(R.id.ll_shi_num)
    protected LinearLayout ll_shi_num;     //按照小时

    @BindView(R.id.tv_xiaoshi_ziran_nun2)
    protected TextView tv_xiaoshi_ziran_nun2;

    @BindView(R.id.edt_xiaoshi_ziran_nun)
    protected EditText edt_xiaoshi_ziran_nun;

    @BindView(R.id.tv_xiaoshi_ziran_num_unit)
    protected TextView tv_xiaoshi_ziran_num_unit;



    //使用默认图片 和上传图片
    @BindView(R.id.upload_image_rg)
    protected RadioGroup upload_image_rg;
    @BindView(R.id.upload_default_rb)
    protected RadioButton upload_default_rb;
    @BindView(R.id.upload_image_rb)
    protected RadioButton upload_image_rb;

    //上传图片
    @BindView(R.id.iv_pt_image)
    protected ImageView iv_pt_image;

    @BindView(R.id.edt_forward_title)
    protected EditText edt_forward_title;

    @BindView(R.id.edt_forward_content)
    protected EditText edt_forward_content;

    @BindView(R.id.tv_title_num)
    protected TextView tv_title_num;

    @BindView(R.id.tv_content_num)
    protected TextView tv_content_num;


    //按自然天数
    protected OptionsPickerView pvBusMode;

    protected List<CardBean> cardBeans = new ArrayList<>();

    protected String btnFlag = "save";

    private  String compId;

    protected Campaign campaign = new Campaign();

    protected String posterTemplete;


    protected List<MaterialGame> materialGameList = new ArrayList<>();


    protected List<PrizeDefaultBean> prizeDefaultBeanList = new ArrayList<>();
    //奖项图片
    protected List<PrizeDefaultBean> winList = new ArrayList<>();
    private String storeSelectType ;
    private String storeStr;
    private String userId;
    private String compaignStatus;


    protected   String isEditCampaign = "NO";

    protected String isCopyCreate ;
    private String title;


    private String validTimeType = "H";  //有效时间类型 按照自然天D，按照小时H
    private String vilidTime;
    /**
     * 参与次数限制类型：每人P，每人每天D
     */
    private String setupCountType = "D";

    /**
     * 主动参与限制次数
     */
    private String setupCount;


    /**
     * 活动素材
     */
    private Long materialCampainId;

    /**
     * 奖项图片
     */
    protected String prizeImg;

    protected String prizeUrl;
    private String uploadType = "D";

    /**
     * 商品详细说明
     */
    private String description;

    private boolean isSelectXiaoshi = true;

    private boolean isSelectMeiRenMeiTian = true;


    private boolean isSelectMemberMeiRenMeiTian = true;

    private boolean isSelectDefaultImage = true;

//    protected String forwardTitle = "[点我玩，获优惠]幸运时刻已开启，快来帮我点一下";
//    protected String forwardContent = "亲，快来助我一臂之力，你也能获大奖哦~";
    protected String forwardTitle = "";
    protected String forwardContent = "";
    protected  List<LocalMedia> selectList = new ArrayList<>();
    protected List<File> fileList  = new ArrayList<>();
    protected static CampaignGroupBuy campaignGroupBuy = new CampaignGroupBuy();
    //素材
    protected MaterialGame materialGame = new MaterialGame();
    //素材id
    protected   String posterId;
    protected String bannerId;
    protected String backGroundId;
    protected String wxIconId;
    //小程序图标
    protected   String miniLinkIcon;
    private String campaignTypeId = "2";

    //游戏主页
    protected  String backGround;
    //游戏主页的 奖项图片
    protected  List<String> wins = new ArrayList<>();

    protected String jsonStr = "";


    int rewardTitleLenth = 0;
    int rewardContentLenth = 0;


    protected  int  prizeCount1;
    protected  int  prizeCount2;
    protected  int  prizeCount3;

    protected static  String campaignType;

    protected static CampaignCollect campaignCollect = new CampaignCollect();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_campaign_like_three;
    }

    @Override
    protected void initView() {
        DaggerCampaignsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignsModule(new CampaignsModule(this))
                .build()
                .inject(this);
        setLisenter();
        initOptionPicker();
        setSetTep();
    }

    @Override
    protected void initData() {


        compId = SPUtils.getInstance().getString("compId");
        userId = SPUtils.getInstance().getString("userId");

        isEditCampaign  = getIntent().getStringExtra("isEditCampaign");
        isCopyCreate = getIntent().getStringExtra("isCopyCreate");
        if("YES".equals(isEditCampaign)){
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText("复制并创建");
        }else {
            tvRight.setVisibility(View.GONE);
        }

        storeStr = CampaignLikeNewActivity.storeStr;
        storeSelectType = CampaignLikeNewActivity.campaignStoreSelectType;
        compaignStatus = CampaignLikeNewActivity.compaignStatus;

        tvTitle.setText("集赞活动设置");

        campaign = CampaignLikeNewActivity.campaign; //编辑页面进入
        if(campaign != null){  //缓存提取
            forwardTitle = campaign.getShareTitle();
            forwardContent = campaign.getShareDesc();
            posterId = campaign.getPosterTemplete();
            PosterMaterial posterMaterial = campaign.getPoster();
            if(posterId != null && !"".equals(posterId)){
            }else {
                posterId = "";
            }

        }


        if(campaignCollect != null) {

            validTimeType = campaignCollect.getEffectTimeFlag();

            if(validTimeType == null || "".equals(validTimeType)){
                validTimeType = "H";
            }

            if("D".equals(validTimeType)){  //按天
                if (campaignCollect.getEffectTime() != null && campaignCollect.getEffectTime().intValue() >=0) {
                    vilidTime = String.valueOf(campaignCollect.getEffectTime());
                } else {
                    vilidTime = "";
                }
            }else if("H".equals(validTimeType)){ //按小时
                if (campaignCollect.getEffectTime() != null && campaignCollect.getEffectTime().intValue() >0) {
                    vilidTime = String.valueOf(campaignCollect.getEffectTime());
                } else {
                    vilidTime = "";
                }
            }


            //限制次数
            setupCountType = campaignCollect.getLimitType();

            if(setupCountType == null || "".equals(setupCountType)){
                setupCountType = "D";
            }

            if (campaignCollect.getLimitCount() != null && campaignCollect.getLimitCount().intValue() > 0) {
                setupCount = String.valueOf(campaignCollect.getLimitCount());
            } else {
                setupCount = "";
            }

            prizeImg = campaignCollect.getDefaultImg();




            edt_zhudong_pintuan_ci_num.setText(setupCount); //主动开团限制次数

        } else {
            campaignCollect = new CampaignCollect();
        }

        if (prizeImg != null && !"".equals(prizeImg)) {
            uploadType = "S";
            prizeUrl = HttpUrls.IMAGE_ULR + prizeImg;
            setImage();
            upload_image_rb.setChecked(true);
            upload_default_rb.setTextColor(getResources().getColor(R.color.coupon_list_color));
            upload_image_rb.setTextColor(getResources().getColor(R.color.btn_color_blue));

            iv_pt_image.setVisibility(View.VISIBLE);
        } else {   //默认不传
            uploadType = "D";
            upload_default_rb.setChecked(true);
            upload_default_rb.setTextColor(getResources().getColor(R.color.btn_color_blue));
            upload_image_rb.setTextColor(getResources().getColor(R.color.coupon_list_color));
            iv_pt_image.setVisibility(View.GONE);
        }
        if ("D".equals(validTimeType)) {  //自然天数
            xiaoshi_left_num_rb.setChecked(false);
            zirantianshu_right_num_rb.setChecked(true);
            xiaoshi_left_num_rb.setTextColor(getResources().getColor(R.color.coupon_list_color));
            zirantianshu_right_num_rb.setTextColor(getResources().getColor(R.color.btn_color_blue));
            tv_xiaoshi_ziran_nun2.setInputType(InputType.TYPE_CLASS_TEXT);

            if("0".equals(vilidTime) ){
                tv_xiaoshi_ziran_nun2.setText("当");
            }else {
                tv_xiaoshi_ziran_nun2.setText(vilidTime);
            }
            tv_xiaoshi_ziran_nun2.setVisibility(View.VISIBLE);
            edt_xiaoshi_ziran_nun.setVisibility(View.GONE);
            tv_xiaoshi_ziran_num_unit.setText("天");
        } else if ("H".equals(validTimeType)) {  //按小时
            zirantianshu_right_num_rb.setChecked(false);
            xiaoshi_left_num_rb.setChecked(true);
            xiaoshi_left_num_rb.setTextColor(getResources().getColor(R.color.btn_color_blue));
            zirantianshu_right_num_rb.setTextColor(getResources().getColor(R.color.coupon_list_color));
            edt_xiaoshi_ziran_nun.setFocusable(true);
            edt_xiaoshi_ziran_nun.setFocusableInTouchMode(true);
            edt_xiaoshi_ziran_nun.setText(vilidTime);
            edt_xiaoshi_ziran_nun.setVisibility(View.VISIBLE);
            tv_xiaoshi_ziran_nun2.setVisibility(View.GONE);
            tv_xiaoshi_ziran_num_unit.setText("小时");

        }


        if("D".equals(setupCountType)){
            isSelectMeiRenMeiTian = true;
            per_meiren_left_num_rb.setChecked(true);
            per_meiren_right_num_rb.setChecked(false);

            per_meiren_left_num_rb.setTextColor(getResources().getColor(R.color.btn_color_blue));
            per_meiren_right_num_rb.setTextColor(getResources().getColor(R.color.coupon_list_color));
            per_meiren_ci_label.setText("每人每天最多参与");
        }else if("P".equals(setupCountType)){
            isSelectMeiRenMeiTian = false;
            per_meiren_left_num_rb.setChecked(false);
            per_meiren_right_num_rb.setChecked(true);
            per_meiren_left_num_rb.setTextColor(getResources().getColor(R.color.coupon_list_color));
            per_meiren_right_num_rb.setTextColor(getResources().getColor(R.color.btn_color_blue));
            per_meiren_ci_label.setText("一个人最多参与");
        }

        if ("S".equals(compaignStatus) || "NEW".equals(compaignStatus)) {
            ll_save.setVisibility(View.VISIBLE);
            btnSavePublic.setVisibility(View.VISIBLE);
            btnPre.setVisibility(View.VISIBLE);
            edt_xiaoshi_ziran_nun.setEnabled(true);
            tv_xiaoshi_ziran_nun2.setEnabled(true);

            per_meiren_ci_rg.setEnabled(true);
            per_xiao_ziran_rg.setEnabled(true);

            edt_zhudong_pintuan_ci_num.setEnabled(true);
            upload_image_rg.setEnabled(true);

            for (int i = 0; i < per_meiren_ci_rg.getChildCount(); i++) {
                per_meiren_ci_rg.getChildAt(i).setEnabled(true);
            }
            for (int i = 0; i < per_xiao_ziran_rg.getChildCount(); i++) {
                per_xiao_ziran_rg.getChildAt(i).setEnabled(true);
            }

            for (int i = 0; i < upload_image_rg.getChildCount(); i++) {
                upload_image_rg.getChildAt(i).setEnabled(true);
            }
            edt_forward_content.setEnabled(true);
            edt_forward_title.setEnabled(true);
        } else {

            btnSavePublic.setText("返回");
            btnSavePublic.setVisibility(View.VISIBLE);
            ll_save.setVisibility(View.GONE);
            btnPre.setVisibility(View.VISIBLE);

            per_meiren_ci_rg.setEnabled(false);

            edt_xiaoshi_ziran_nun.setEnabled(false);
            tv_xiaoshi_ziran_nun2.setEnabled(false);

            per_xiao_ziran_rg.setEnabled(false);
            //主动发起参与集赞的次数限制
            edt_zhudong_pintuan_ci_num.setEnabled(false);

            upload_image_rg.setEnabled(false);

            for (int i = 0; i < per_meiren_ci_rg.getChildCount(); i++) {
                per_meiren_ci_rg.getChildAt(i).setEnabled(false);
            }
            for (int i = 0; i < per_xiao_ziran_rg.getChildCount(); i++) {
                per_xiao_ziran_rg.getChildAt(i).setEnabled(false);
            }

            for (int i = 0; i < upload_image_rg.getChildCount(); i++) {
                upload_image_rg.getChildAt(i).setEnabled(false);
            }

            edt_forward_content.setEnabled(false);
            edt_forward_title.setEnabled(false);
        }

        edt_forward_title.setText(forwardTitle != null && !"".equals(forwardTitle)?forwardTitle:"");
        edt_forward_content.setText(forwardContent != null && !"".equals(forwardContent)?forwardContent:"");

        if(edt_forward_content.getText().toString().trim() != null ){
            int strLen = edt_forward_content.getText().toString().trim().length();
            tv_content_num.setText(strLen+"/45");
        }

        if(edt_forward_title.getText().toString().trim() != null ){
            int strLen =edt_forward_title.getText().toString().trim().length();
            tv_title_num.setText(strLen+"/30");
        }
     /*   if ("NEW".equals(CampaignLikeNewActivity.compaignStatus)) {   //新建活动
            edt_forward_title.setText(forwardTitle);
            edt_forward_content.setText(forwardContent);

        } else {
//            edt_forward_title.setText(forwardTitle != null && !"".equals(forwardTitle)?forwardTitle:"[一起拼，开大奖]我想要，你需要，拼完还能拿大奖");
//            edt_forward_content.setText(forwardContent != null && !"".equals(forwardContent)?forwardContent:"GOGOGO~  亲，帮我一起拼，解锁属于我们的幸运和大奖吧。");
            edt_forward_title.setText(forwardTitle != null && !"".equals(forwardTitle)?forwardTitle:"");
            edt_forward_content.setText(forwardContent != null && !"".equals(forwardContent)?forwardContent:"");
        }*/


        if ("A".equals(storeSelectType)) {
            storeStr = "";
        } else if ("D".equals(storeSelectType)) {
            storeStr = "";
        } else if ("J".equals(storeSelectType)) {
            storeStr = "";
        } else {
            storeSelectType = "";

        }

        presenter.queryJzTigerPoster(this,userId,"",campaignTypeId,CampaignLikeNewActivity.campaignType);
    }

    private void setSetTep(){
        ivOne.setBackgroundResource(R.drawable.ic_first_clicked);
        ivEllipsisOne.setBackgroundResource(R.drawable.ic_ellipsis_blue);
        ivTwo.setBackgroundResource(R.drawable.ic_two_clicked);
        ivEllipsisTwo.setBackgroundResource(R.drawable.ic_ellipsis_blue);
        ivThree.setBackgroundResource(R.drawable.ic_three_clicked);
    }



    private  void setLisenter(){



        //小时、天数选择
      /*  xiaoshi_left_num_rb.setChecked(true);
        xiaoshi_left_num_rb.setTextColor(getResources().getColor(R.color.btn_color_blue));
        zirantianshu_right_num_rb.setTextColor(getResources().getColor(R.color.coupon_list_color));
        validTimeType = "H";*/

        ll_shi_num.setVisibility(View.VISIBLE);
        //按持续小时
        per_xiao_ziran_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.xiaoshi_left_num_rb:
                        isSelectXiaoshi = true;
                        xiaoshi_left_num_rb.setChecked(true);
                        xiaoshi_left_num_rb.setTextColor(getResources().getColor(R.color.btn_color_blue));
                        zirantianshu_right_num_rb.setTextColor(getResources().getColor(R.color.coupon_list_color));
                        edt_xiaoshi_ziran_nun.setInputType(InputType.TYPE_CLASS_NUMBER);
                        validTimeType = "H";
                        tv_xiaoshi_ziran_num_unit.setText("小时");
                        edt_xiaoshi_ziran_nun.setText("");
                        edt_xiaoshi_ziran_nun.setVisibility(View.VISIBLE);
                        tv_xiaoshi_ziran_nun2.setVisibility(View.GONE);
                        KeyboardUtils.showSoftInput(edt_xiaoshi_ziran_nun);
                        break;

                    case R.id.zirantianshu_right_num_rb:
                        isSelectXiaoshi = false;
                        zirantianshu_right_num_rb.setChecked(true);
                        xiaoshi_left_num_rb.setTextColor(getResources().getColor(R.color.coupon_list_color));
                        zirantianshu_right_num_rb.setTextColor(getResources().getColor(R.color.btn_color_blue));
                        edt_xiaoshi_ziran_nun.setInputType(InputType.TYPE_CLASS_TEXT);
                        validTimeType = "D";
                        edt_xiaoshi_ziran_nun.setText("");
                        tv_xiaoshi_ziran_num_unit.setText("天");
                        edt_xiaoshi_ziran_nun.setVisibility(View.GONE);
                        tv_xiaoshi_ziran_nun2.setVisibility(View.VISIBLE);
                        KeyboardUtils.hideSoftInput(edt_xiaoshi_ziran_nun);
                        break;
                    default:
                        break;
                }
            }
        });

        edt_xiaoshi_ziran_nun.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edt_xiaoshi_ziran_nun.getText().toString().trim().length()>0){
                    edt_xiaoshi_ziran_nun.setSelection(edt_xiaoshi_ziran_nun.getText().toString().trim().length());
                    String cinum =  edt_xiaoshi_ziran_nun.getText().toString().trim();
                    if(cinum != null && !"".equals(cinum)){
                        if("H".equals(validTimeType)){
                            if(Integer.parseInt(cinum)<1){
                                ToastUtils.showShort("至少为1");
                                edt_xiaoshi_ziran_nun.setText("");
                            }
                        }

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        //转发内容
        edt_forward_content.setFilters(new InputFilter[]{new MaxTextLengthFilter(45)});
        edt_forward_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                rewardContentLenth = s.length();
                if(edt_forward_content.getText().toString().trim().length()>0){
                    edt_forward_content.setSelection(edt_forward_content.getText().toString().trim().length());
                    tv_content_num.setText(rewardContentLenth+"/45");
                }
            }
        });
        //转发标题
        edt_forward_title.setFilters(new InputFilter[]{new MaxTextLengthFilter(30)});
        edt_forward_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                rewardTitleLenth = s.length();
                if(edt_forward_title.getText().toString().trim().length()>0){
                    edt_forward_title.setSelection(edt_forward_title.getText().toString().trim().length());
                    tv_title_num.setText(rewardTitleLenth+"/30");
                }
            }
        });



        per_meiren_ci_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.per_meiren_left_num_rb:
                        isSelectMeiRenMeiTian = true;
                        per_meiren_left_num_rb.setChecked(true);
                        per_meiren_left_num_rb.setTextColor(getResources().getColor(R.color.btn_color_blue));
                        per_meiren_right_num_rb.setTextColor(getResources().getColor(R.color.coupon_list_color));
                        per_meiren_ci_label.setText("每人每天最多参与");
                        setupCountType = "D";
                        break;

                    case R.id.per_meiren_right_num_rb:
                        isSelectMeiRenMeiTian = false;
                        per_meiren_right_num_rb.setChecked(true);
                        per_meiren_left_num_rb.setTextColor(getResources().getColor(R.color.coupon_list_color));
                        per_meiren_right_num_rb.setTextColor(getResources().getColor(R.color.btn_color_blue));

                        per_meiren_ci_label.setText("一个人最多参与");
                        setupCountType = "P";
                        break;
                    default:
                        break;
                }
            }
        });
        //上传
        upload_image_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.upload_default_rb:  //默认
                        isSelectDefaultImage = true;
                        upload_default_rb.setChecked(true);
                        upload_default_rb.setTextColor(getResources().getColor(R.color.btn_color_blue));
                        upload_image_rb.setTextColor(getResources().getColor(R.color.coupon_list_color));
                        iv_pt_image.setVisibility(View.GONE);
                        selectList = new ArrayList<>();
                        uploadType = "D";
                        break;

                    case R.id.upload_image_rb:  //上传
                        isSelectDefaultImage = false;
                        upload_image_rb.setChecked(true);
                        upload_default_rb.setTextColor(getResources().getColor(R.color.coupon_list_color));
                        upload_image_rb.setTextColor(getResources().getColor(R.color.btn_color_blue));
                        iv_pt_image.setVisibility(View.VISIBLE);
                        uploadType = "S";
                        break;
                    default:
                        break;
                }
            }
        });

    }


    @OnClick({R.id.iv_back,R.id.tv_right,R.id.edt_xiaoshi_ziran_nun,R.id.tv_xiaoshi_ziran_nun2,R.id.iv_pt_image,
            R.id.btn_save_and_public,R.id.btn_save,R.id.btn_pre,R.id.btn_up})
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.startActivity(CampaignListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
                break;
            case R.id.tv_right:
                ToastUtils.showCustomShortBottom("创建成功");
                //复制并创建
                isCopyCreate = "NO";
                isEditCampaign = "YES";
                CampaignLikeNewActivity.compaignStatus = "NEW";
                if(campaign != null){
                    campaign.setCampaignId(null);
                    campaign.setCampaignName("");
                    campaign.setShareDesc(edt_forward_content.getText().toString().trim());
                    campaign.setShareTitle(edt_forward_title.getText().toString().trim());
                    CampaignLikeNewActivity.campaign = campaign;
                }
                Intent intent1 = new Intent(CampaignLikeThreeActivity.this,CampaignLikeNewActivity.class);
                intent1.putExtra("isCopyCreate",isCopyCreate);
                intent1.putExtra("isEditCampaign",isEditCampaign);
                intent1.putExtra("campaignType",CampaignLikeNewActivity.campaignType);
                intent1.putExtra("compaignStatus",CampaignLikeNewActivity.compaignStatus);
                ActivityUtils.startActivity(intent1,
                        R.anim.slide_in_left, R.anim.slide_out_right);


                break;
            case R.id.tv_xiaoshi_ziran_nun2:  //按自然天数
                KeyboardUtils.hideSoftInput(CampaignLikeThreeActivity.this,edt_xiaoshi_ziran_nun);
                if("D".equals(validTimeType)){
                    pvBusMode.show();
                }
                break;
            case R.id.iv_pt_image:


                int config = PictureConfig.CHOOSE_REQUEST;
                int themeId = R.style.picture_default_style;
                int aspect_ratio_x = 1;
                int aspect_ratio_y = 1;
                PictureSelector.create(this)
                        .openGallery( PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                        .maxSelectNum(1)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .selectionMode(PictureConfig.MULTIPLE )
                        /* .selectionMode(cb_choose_mode.isChecked() ?
                                 PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选*/
                        .previewImage(true)// 是否可预览图片
                        .previewVideo(false)// 是否可预览视频
                        .enablePreviewAudio(false) // 是否可播放音频
                        .isCamera(false)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                        .enableCrop(false)// 是否裁剪
                        .compress(true)// 是否压缩
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                        //.compressSavePath(getPath())//压缩图片保存地址
                        //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .hideBottomControls(false)
                        .isGif(false)// 是否显示gif图片
                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                        .circleDimmedLayer(false)// 是否圆形裁剪
                        .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .openClickSound(false)// 是否开启点击声音
                        .selectionMedia(selectList)// 是否传入已选图片
                        //.isDragFrame(false)// 是否可拖动裁剪框(固定)
                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .forResult(config);//结果回调onActivityResult code
                break;
            case R.id.btn_save:
                btnFlag = "save";

                setCampaignCollect();
                if(check()){
                    return;
                }


                if (!ButtonUtils.isFastDoubleClick(R.id.btn_save)) {
                    if(fileList != null && fileList.size()>0){
                        presenter.jzUploadFiles(this,fileList);
                    }else {
                        setJsonStr();
                        presenter.saveJzCampaignInfoForgame(this,jsonStr,btnFlag);
                    }

                }
                break;
            case R.id.btn_save_and_public:

                btnFlag = "saveAndpublic";
                setCampaignCollect();

                if(check()){
                    return;
                }



                if("S".equals(compaignStatus)||"NEW".equals(compaignStatus)){
//
                    if (!ButtonUtils.isFastDoubleClick(R.id.btn_save_and_public)) {

                        if(fileList != null && fileList.size()>0){
                            presenter.jzUploadFiles(this,fileList);
                        }else {

                            setJsonStr();
                            presenter.saveJzCampaignInfoForgame(this,jsonStr,btnFlag);
                        }

                    }

                }else {
                    ActivityUtils.startActivity(PtManageActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                    finish();
                }
                break;
            case R.id.btn_pre:  //预览

                setPopDialog();
                break;

            case R.id.btn_up:  //上一步

                if(campaign != null){
                    campaign.setShareDesc(edt_forward_content.getText().toString().trim());
                    campaign.setShareTitle(edt_forward_title.getText().toString().trim());
                }

                if(campaign != null){
//                    SPUtils.getInstance().putData("campaign",campaign);
                    CampaignLikeNewActivity.campaign = campaign;
                }
                setCampaignCollect();
                ActivityUtils.finishActivity(CampaignLikeThreeActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);

                break;


        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.startActivity(CampaignListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
        finish();

    }

    protected void setImage(){
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_load_fail)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_load_fail);

        Glide.with(this).load(prizeUrl).apply(requestOptions).into(iv_pt_image);
        /*Glide.with(this).load(prizeUrl).into(new SimpleTarget<Drawable>(111,111) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_pt_image.setBackground(resource);    //设置背景
                }
            }
        });*/
    }



    protected void setCampaignCollect(){

        setupCount = edt_zhudong_pintuan_ci_num.getText().toString().trim();   //主动参与限制次数

        forwardContent = edt_forward_content.getText().toString().trim();
        forwardTitle = edt_forward_title.getText().toString().trim();


        if(campaignCollect == null){
            campaignCollect = new CampaignCollect();
        }

        if(validTimeType!= null && !"".equals(validTimeType)){

            if("D".equals(validTimeType) ){  //天数
                vilidTime = tv_xiaoshi_ziran_nun2.getText().toString().trim();
            }else if("H".equals(validTimeType) ){//小时
                vilidTime = edt_xiaoshi_ziran_nun.getText().toString().trim();


            }
            campaignCollect.setEffectTimeFlag(validTimeType);
        }

        if(vilidTime != null && !"".equals(vilidTime)){
            if("当".equals(vilidTime)){
                campaignCollect.setEffectTime(0);
            }else {
                campaignCollect.setEffectTime(Integer.parseInt(vilidTime));
            }
        }
        if(setupCountType!= null && !"".equals(setupCountType)){
            campaignCollect.setLimitType(setupCountType);
        }

        //主动参与
        if(setupCount != null && !"".equals(setupCount)){
            campaignCollect.setLimitCount(Integer.parseInt(setupCount));
        }

        if("D".equals(uploadType)){
            campaignCollect.setDefaultImg("");
        }else {
            campaignCollect.setDefaultImg(prizeImg);
        }

        campaign.setShareDesc(forwardContent);
        campaign.setShareTitle(forwardTitle);
    }

    protected void setJsonStr(){
        Map<String,Object> jsonMap = new HashMap<String,Object>();
        try{
            jsonMap.put("userId",userId);
            jsonMap.put("storeStr",storeStr);
            jsonMap.put("posterId",posterId);
            jsonMap.put("btnFlag",btnFlag);
            String campaignJSON=new Gson().toJson(campaign);
            jsonMap.put("campaign",campaignJSON);
            String campaignCollectJSON=new Gson().toJson(campaignCollect);
            jsonMap.put("campaignCollect",campaignCollectJSON);
            CampaignGame campaignGame = new CampaignGame();
            String campaignGameJSON=new Gson().toJson(campaignGame);
            jsonMap.put("campaignGame",campaignGameJSON);
            jsonMap.put("compId",compId);

            List<String> campaignNonrewardPicList = new ArrayList<>();

            List<CampaignGamesetreward> rewardlist  = CampaignLikeTwoActivity.campaignGamesetrewardList;
            jsonMap.put("campaignNonrewardPic",campaignNonrewardPicList);
            jsonMap.put("rewardlist",rewardlist);

            jsonMap.put("storeSelectType",storeSelectType);
            jsonStr = JSON.toJSONString(jsonMap);
        }catch (Exception e){

        }
    }

    protected void initOptionPicker() {//条件选择器初始化，自定义布局

        for (int i = 0 ;i<91;i++){
            CardBean cardBean = new CardBean();
            if(i == 0){
                cardBean.setId("0");
                cardBean.setCardNo("当天");
            }else if( i == 1){
                cardBean.setId(""+ i);
                cardBean.setCardNo("1天(次日)");
            }else {
                cardBean.setId(""+ i);
                cardBean.setCardNo(i+"天");
            }

            cardBeans.add(cardBean);
        }
        pvBusMode = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String tx = cardBeans.get(options1).getPickerViewText();
                String value = cardBeans.get(options1).getId();

                if("0".equals(value)){
                    vilidTime = value;
                }else{
                    vilidTime = value;
                }


                if("D".equals(validTimeType)){
                    if("0".equals(value)){
                        tv_xiaoshi_ziran_nun2.setText("当");
                    }else {
                        tv_xiaoshi_ziran_nun2.setText(value);
                    }
                }

            }
        })

                .setLayoutRes(R.layout.layout_pickerview_custom_position, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final  ImageView ivSubmit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        ivSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvBusMode.returnData();
                                pvBusMode.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvBusMode.dismiss();
                            }
                        });


                    }
                })
                .build();


        pvBusMode.setPicker(cardBeans);

    }

    private boolean check(){

        if("save".equals(btnFlag)){
            if(CampaignLikeTwoActivity.campaignGamesetrewardList!=null && CampaignLikeTwoActivity.campaignGamesetrewardList.size()>0){
                for(CampaignGamesetreward campaignGamesetreward : CampaignLikeTwoActivity.campaignGamesetrewardList){

                    if(campaignGamesetreward != null){
                        String name = campaignGamesetreward.getPrizeName();

                        Integer prizeCount = campaignGamesetreward.getPrizeCount();
                        if(prizeCount != null && prizeCount.intValue()>0){
                            if("条件一".equals(name)){
                               prizeCount1 = prizeCount;

                            }else  if("条件二".equals(name)){
                                prizeCount2= prizeCount;
                            }else  if("条件三".equals(name)){
                                prizeCount3= prizeCount;
                            }
                        }else{
                            if("条件一".equals(name)){
                                setDialog("您在第二步中，"+name+"设置信息不完善，请补充信息。");
                                return true;
                            }else  if("条件二".equals(name)){
                                setDialog("您在第二步中，"+name+"设置信息不完善，请补充信息。");
                                return true;
                            }else  if("条件三".equals(name)){
                                setDialog("您在第二步中，"+name+"设置信息不完善，请补充信息。");
                                return true;
                            }

                        }
                        List<CampaignCoupon> campaignCouponList = campaignGamesetreward.getList();
                        if(campaignCouponList != null && campaignCouponList.size()>0){

                        }else{
                            setDialog("请完善第二步,"+name+"优惠券信息。");
                            return true;

                        }

                    }

                }

                if(prizeCount1 >0 && prizeCount2>0){
                    if(prizeCount1<prizeCount2  ){

                    }else {
                        setDialog("您在第二步中，"+getResources().getString(R.string.campaign_like_coupon_tip));
                        return true;
                    }
                }

                if(prizeCount2>0 && prizeCount3>0){
                    if(prizeCount2<prizeCount3  ){

                    }else {
                        setDialog("您在第二步中，"+getResources().getString(R.string.campaign_like_coupon_tip));
                        return true;
                    }
                }

                if(prizeCount1>0 && prizeCount3>0){
                    if(prizeCount1<prizeCount3  ){

                    }else {
                        setDialog("您在第二步中，"+getResources().getString(R.string.campaign_like_coupon_tip));
                        return true;
                    }
                }
            }else {
                setDialog("您在第二步中，设置信息不完善，请补充信息。");
                return true;
            }


        }else {

            if(campaign !=null){
                String campaignName = campaign.getCampaignName();


                if(campaignName == null ||"".equals(campaignName)){
                    setDialog("您在第一步中，设置信息不完善，请补充信息。");
                    return true;
                }

                String startDate = campaign.getBeginDate();
                String endDate = campaign.getEndDate();
                if(startDate == null ||"".equals(startDate)){
                    setDialog("您在第一步中，设置信息不完善，请补充信息。");
                    return true;
                }

                if(endDate == null ||"".equals(endDate)){
                    setDialog("您在第一步中，设置信息不完善，请补充信息。");
                    return true;
                }
            }else {
                setDialog("您在第一步中，设置信息不完善，请补充信息。");
                return true;
            }

            if(CampaignLikeTwoActivity.campaignGamesetrewardList!=null && CampaignLikeTwoActivity.campaignGamesetrewardList.size()>0){
                for(CampaignGamesetreward campaignGamesetreward : CampaignLikeTwoActivity.campaignGamesetrewardList){

                    if(campaignGamesetreward != null){
                        String name = campaignGamesetreward.getPrizeName();

                        Integer prizeCount = campaignGamesetreward.getPrizeCount();
                        if(prizeCount != null && prizeCount.intValue()>0){
                            if("条件一".equals(name)){
                                prizeCount1 = prizeCount;

                            }else  if("条件二".equals(name)){
                                prizeCount2= prizeCount;
                            }else  if("条件三".equals(name)){
                                prizeCount3= prizeCount;
                            }
                        }else{
                            if("条件一".equals(name)){
                                setDialog("您在第二步中，"+name+"设置信息不完善，请补充信息。");
                                return true;
                            }else  if("条件二".equals(name)){
                                setDialog("您在第二步中，"+name+"设置信息不完善，请补充信息。");
                                return true;
                            }else  if("条件三".equals(name)){
                                setDialog("您在第二步中，"+name+"设置信息不完善，请补充信息。");
                                return true;
                            }

                        }
                        List<CampaignCoupon> campaignCouponList = campaignGamesetreward.getList();
                        if(campaignCouponList != null && campaignCouponList.size()>0){
                            for (CampaignCoupon campaignCoupon : campaignCouponList){
                                String startDate =campaignCoupon.getExpireStartDate();
                                String endDate = campaignCoupon.getExpireEndDate();
                                String startDay =campaignCoupon.getExpireStartDay();
                                String endDay = campaignCoupon.getExpireEndDay();
                                String couponName =campaignCoupon.getCouponName();
                                String expireType = campaignCoupon.getExpireEndType();
                                if("A".equals(expireType)){
                                    if(startDate == null ||"".equals(startDate)){
                                        setDialog("您在第二步中，"+name+"设置信息不完善，请补充信息。");

                                        return true;
                                    }

                                    if(endDate == null ||"".equals(endDate)){
                                        setDialog("您在第二步中，"+name+"设置信息不完善，请补充信息。");
                                        return true;
                                    }
                                }else if("R1".equals(expireType)){
                                    if(startDay == null ||"".equals(startDay)){
                                        setDialog("您在第二步中，"+name+"设置信息不完善，请补充信息。");
                                        return true;
                                    }

                                    if(endDay == null ||"".equals(endDay)){
                                        setDialog("您在第二步中，"+name+"设置信息不完善，请补充信息。");
                                        return true;
                                    }


                                }
                                if(couponName == null ||"".equals(couponName)){
                                    setDialog("您在第二步中，"+name+"设置信息不完善，请补充信息。");
                                    return true;
                                }
                            }
                        }else{
                            setDialog("您在第二步中，设置信息不完善，请补充信息。");
                            return true;
                        }

                    }
                }
                //判断条件1至3集赞数量
                if(prizeCount1 >0 && prizeCount2>0){
                    if(prizeCount1<prizeCount2  ){

                    }else {
                        setDialog("您在第二步中，"+getResources().getString(R.string.campaign_like_coupon_tip));
                        return true;
                    }
                }

                if(prizeCount2>0 && prizeCount3>0){
                    if(prizeCount2<prizeCount3  ){

                    }else {
                        setDialog("您在第二步中，"+getResources().getString(R.string.campaign_like_coupon_tip));
                        return true;
                    }
                }

                if(prizeCount1>0 && prizeCount3>0){
                    if(prizeCount1<prizeCount3  ){

                    }else {
                        setDialog("您在第二步中，"+getResources().getString(R.string.campaign_like_coupon_tip));
                        return true;
                    }
                }
            }else {
                setDialog("您在第二步中，设置信息不完善，请补充信息。");
                return true;
            }


            if(vilidTime != null && !"".equals(vilidTime)){

            }else {
                setDialog("您在第三步中，设置信息不完善，请补充信息。");
                return true;
            }



            if(setupCount != null && !"".equals(setupCount)){
            }else {
                setDialog("您在第三步中，设置信息不完善，请补充信息。");
                return true;
            }



            if(forwardContent == null ||"".equals(forwardContent)){
                setDialog("您在第三步中，信息设置不完善，请补充信息。");
                return true;
            }
            if(forwardTitle == null || "".equals(forwardTitle)){
                setDialog("您在第三步中，信息设置不完善，请补充信息。");
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        fileList.clear();
        if (resultCode == RESULT_OK) {
            LocalMedia localMedia = new LocalMedia();
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);

                    if (selectList != null && selectList.size() > 0) {
                        localMedia = selectList.get(0);
                        prizeImg = localMedia.getCompressPath();
                        prizeUrl = prizeImg;
                        setImage();
                        File file = new File(prizeImg);
                        fileList.add(file);
                    }
                    if (fileList != null && fileList.size() > 0) {
//                        presenter.uploadFiles(CampaignLikeThreeActivity.this, fileList);
                    }
                    break;
            }
        }
    }


    private void setPopDialog(){

        View customView = View.inflate(this, R.layout.layout_jz_preview_dialog, null);

        PopWindow popAlertView = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopAlert)
                .setView(customView)
                .show();

        FrameLayout fl_content = (FrameLayout) customView.findViewById(R.id.fl_content);


        ImageView iv_jz_yxzy = (ImageView)customView.findViewById(R.id.iv_jz_yxzy);
        Button btn_use = (Button)customView.findViewById(R.id.btn_use);
        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popAlertView.dismiss();
            }
        });

        ImageView iv_banner = (ImageView)customView.findViewById(R.id.iv_banner);
        String bannerPath = HttpUrls.IMAGE_ULR +bannerId;

  /*      Glide.with(this).load(bannerPath).into(new SimpleTarget<Drawable>(50,50) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_banner.setBackground(resource);
                }
            }
        });*/

        RequestOptions requestOptions2 = new RequestOptions()
                .placeholder(R.drawable.ic_load_fail)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_load_fail);

        Glide.with(this).load(bannerPath).apply(requestOptions2).into(iv_banner);
        LinearLayout ll_dis_top = (LinearLayout)customView.findViewById(R.id.ll_dis_top);
        ll_dis_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAlertView.dismiss();
            }
        });
        LinearLayout ll_wechart = (LinearLayout) customView.findViewById(R.id.ll_wechart);
        ImageView iv_img = (ImageView)customView.findViewById(R.id.iv_img);
        ImageView iv_wx_imgs = (ImageView)customView.findViewById(R.id.iv_imgs);
        TextView tv_title = (TextView) customView.findViewById(R.id.tv_title);
        TextView tv_title2 = (TextView) customView.findViewById(R.id.tv_title2);
        TextView tv_content2 = (TextView) customView.findViewById(R.id.tv_content2);

//        String linkPath = HttpUrls.IMAGE_ULR +miniLinkIcon;

        String wxPath = HttpUrls.IMAGE_ULR + wxIconId;


       /* Glide.with(this).load(wxPath).into(new SimpleTarget<Drawable>(100,100) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_jz_yxzy.setBackground(resource);
                }
            }
        });*/

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_load_fail)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_load_fail);

        if("S".equals(uploadType)){
            Glide.with(this).load(prizeUrl).apply(requestOptions).into(iv_jz_yxzy);
        }else {
            Glide.with(this).load(wxPath).apply(requestOptions).into(iv_jz_yxzy);
        }


        Glide.with(this).load(wxPath).into(new SimpleTarget<Drawable>(100,100) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_img.setBackground(resource);
                }
            }
        });
        Glide.with(this).load(wxPath).into(new SimpleTarget<Drawable>(20,20) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_wx_imgs.setBackground(resource);
                }
            }
        });

        forwardContent = edt_forward_content.getText().toString().trim();
        forwardTitle = edt_forward_title.getText().toString().trim();

        tv_title.setText(forwardTitle != null ?forwardTitle:"");
        tv_title2.setText(forwardTitle != null ?forwardTitle:"");

        tv_content2.setText(forwardContent != null ?forwardContent:"");



        RelativeLayout ll_sylb = (RelativeLayout) customView.findViewById(R.id.ll_sylb);
        ImageView iv_pt_sylb_wx = (ImageView)customView.findViewById(R.id.iv_pt_sylb_wx);
        String campaignPath = HttpUrls.IMAGE_ULR +wxIconId;
        RoundedCorners roundedCorners= new RoundedCorners(8);

        requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_load_fail)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_load_fail);

        Glide.with(this).load(campaignPath).apply(requestOptions).into(iv_pt_sylb_wx);



        TextView tv_campaign_name = (TextView) customView.findViewById(R.id.tv_campaign_name);

        TextView tv_time = (TextView) customView.findViewById(R.id.tv_time);
        String startDate = "";
        String endDate = "";
        String campaignName = "";

        if(campaign != null){
            tv_campaign_name.setText(campaign.getCampaignName() != null ?campaign.getCampaignName():"");
            String startTime = campaign.getBeginDate();
            String endTime = campaign.getEndDate();

            if(startTime != null && !"".equals(startTime)){
                startDate = TimeUtils.date2String(TimeUtils.string2Date(startTime),TimeUtils.DATE_FORMAT_DATE).replace("-",".");
                if(endTime != null && !"".equals(endTime)){
                    endDate = TimeUtils.date2String(TimeUtils.string2Date(endTime),TimeUtils.DATE_FORMAT_DATE).replace("-",".");
                    tv_time.setText(startDate + "-" +endDate);
                }else {
                    tv_time.setText(startDate );
                }
            }else {
                tv_time.setText("");
            }

        }else {
            tv_time.setText("");
        }
        tv_campaign_name.setText(campaignName);


        LinearLayout ll_yxzy = (LinearLayout) customView.findViewById(R.id.ll_yxzy);


        TextView tv_time2 = (TextView) customView.findViewById(R.id.tv_time2);




        if(campaign != null){
            tv_campaign_name.setText(campaign.getCampaignName() != null ?campaign.getCampaignName():"");
            String startTime = campaign.getBeginDate();
            String endTime = campaign.getEndDate();

            if(startTime != null && !"".equals(startTime)){
                startDate = TimeUtils.date2String(TimeUtils.string2Date(startTime),TimeUtils.DATE_FORMAT_DATE).replace("-",".");
                if(endTime != null && !"".equals(endTime)){
                    endDate = TimeUtils.date2String(TimeUtils.string2Date(endTime),TimeUtils.DATE_FORMAT_DATE).replace("-",".");
                    tv_time.setText(startDate + "-" +endDate);
                    tv_time2.setText(startDate + "-" +endDate);
                }else {
                    tv_time.setText(startDate );
                    tv_time2.setText(startDate );
                }
            }else {
                tv_time.setText("");
                tv_time2.setText("");
            }

        }else {
            tv_time.setText("");
            tv_time2.setText("");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("邀请好友助力，赢取奖励");
        if(CampaignLikeTwoActivity.campaignGamesetrewardList != null && CampaignLikeTwoActivity.campaignGamesetrewardList.size()>0){
            for (CampaignGamesetreward campaignGamesetreward:CampaignLikeTwoActivity.campaignGamesetrewardList){
                Integer count = campaignGamesetreward.getPrizeCount();
                String prizeName = campaignGamesetreward.getPrizeName();
                if("条件一".equals(prizeName)){
                    if(count != null && count.intValue()>0){
                        sb.append(count.intValue()+"人助力可得5元优惠券");
                    }
                }else if("条件二".equals(prizeName)){
                    if(count != null && count.intValue()>0){
                        sb.append(count.intValue()+"人助力可得10元优惠券");
                    }
                }else if("条件三".equals(prizeName)){
                    if(count != null && count.intValue()>0){
                        sb.append(count.intValue()+"人助力可得50元优惠券");
                    }
                }

            }
        }

        TextView tv_campaign_fangshi = (TextView) customView.findViewById(R.id.tv_campaign_fangshi);
        tv_campaign_fangshi.setText(sb.toString());

        Button lh_pt_fxxg = (Button)customView.findViewById(R.id.lh_pt_fxxg);
        Button lh_pt_sylb = (Button)customView.findViewById(R.id.lh_pt_sylb);
        Button lh_pt_yxzy = (Button)customView.findViewById(R.id.lh_pt_yxzy);


      /*  String backGroundPath = HttpUrls.IMAGE_ULR + backGround;
       //游戏主页
        Glide.with(this).load(backGroundPath).into(new SimpleTarget<Drawable>(50,50) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    lh_pt_yxzy.setBackground(resource);
                }
            }
        });*/

        //默认海报
        lh_pt_fxxg.setPressed(false);
        lh_pt_sylb.setPressed(false);
        lh_pt_yxzy.setPressed(true);

        ll_wechart.setVisibility(View.GONE);
        ll_sylb.setVisibility(View.GONE);
        ll_yxzy.setVisibility(View.VISIBLE);

        lh_pt_fxxg.setTextColor(getResources().getColor(R.color.color_gray_deep));
        lh_pt_sylb.setTextColor(getResources().getColor(R.color.color_gray_deep));
        lh_pt_yxzy.setTextColor(getResources().getColor(R.color.btn_color_blue));

        lh_pt_fxxg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lh_pt_fxxg.setPressed(true);
                lh_pt_sylb.setPressed(false);
                ll_yxzy.setPressed(false);

                lh_pt_fxxg.setTextColor(getResources().getColor(R.color.btn_color_blue));
                lh_pt_sylb.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_yxzy.setTextColor(getResources().getColor(R.color.color_gray_deep));


                ll_wechart.setVisibility(View.VISIBLE);
                ll_sylb.setVisibility(View.GONE);
                ll_yxzy.setVisibility(View.GONE);

                fl_content.setBackgroundResource(R.drawable.ic_iphone_forward);
            }
        });


        lh_pt_sylb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lh_pt_fxxg.setPressed(false);
                lh_pt_sylb.setPressed(true);
                ll_yxzy.setPressed(false);

                lh_pt_fxxg.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_sylb.setTextColor(getResources().getColor(R.color.btn_color_blue));
                lh_pt_yxzy.setTextColor(getResources().getColor(R.color.color_gray_deep));


                ll_wechart.setVisibility(View.GONE);
                ll_sylb.setVisibility(View.VISIBLE);
                ll_yxzy.setVisibility(View.GONE);
                fl_content.setBackgroundResource(R.drawable.ic_lh_ge_preview_xcxsy_bg);
            }
        });

        lh_pt_yxzy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lh_pt_fxxg.setPressed(false);
                lh_pt_sylb.setPressed(false);
                ll_yxzy.setPressed(true);

                lh_pt_fxxg.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_sylb.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_yxzy.setTextColor(getResources().getColor(R.color.btn_color_blue));


                ll_wechart.setVisibility(View.GONE);
                ll_sylb.setVisibility(View.GONE);
                ll_yxzy.setVisibility(View.VISIBLE);
                fl_content.setBackgroundResource(R.drawable.ic_campaign_like_hdzy_round);
            }
        });




    }

}
