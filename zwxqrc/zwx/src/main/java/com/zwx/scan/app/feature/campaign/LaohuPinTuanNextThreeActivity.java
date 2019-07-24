package com.zwx.scan.app.feature.campaign;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zwx.library.banner.MZBanner;
import com.zwx.library.banner.holder.MZHolderCreator;
import com.zwx.library.expandablelayout.ExpandableUtils;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CampaignGame;
import com.zwx.scan.app.data.bean.CampaignGamesetreward;
import com.zwx.scan.app.data.bean.CampaignNonrewardPic;
import com.zwx.scan.app.data.bean.MaterialGame;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.data.http.dialog.HttpUiTips;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.utils.ButtonUtils;
import com.zwx.scan.app.utils.MaxTextLengthFilter;
import com.zwx.scan.app.utils.SPObjUtil;
import com.zwx.scan.app.widget.ContainsEmojiEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class LaohuPinTuanNextThreeActivity extends BaseActivity<CampaignsContract.Presenter> implements CampaignsContract.View,View.OnClickListener  {

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
    @BindView(R.id.ll_save)
    protected LinearLayout ll_save;
    @BindView(R.id.btn_save)
    protected Button btnSave;

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

    @BindView(R.id.edt_fen_nun)
    protected EditText edt_fen_nun;

    //参与抽奖的次数限制是多少
    @BindView(R.id.per_num_rg)
    protected RadioGroup per_num_rg;
    @BindView(R.id.per_left_num_rb)
    protected RadioButton per_left_num_rb;

    @BindView(R.id.per_right_num_rb)
    protected RadioButton per_right_num_rb;


    @BindView(R.id.ll_left_day_num)
    protected LinearLayout ll_left_day_num;
    @BindView(R.id.edt_left_ci_nun)
    protected EditText edt_left_ci_nun;


    @BindView(R.id.ll_right_day_num)
    protected LinearLayout ll_right_day_num;
    @BindView(R.id.edt_right_ci_nun)
    protected EditText edt_right_ci_nun;



    @BindView(R.id.tv_title_num)
    protected TextView tv_title_num;

    @BindView(R.id.edt_forward_title)
    protected EditText edt_forward_title;

    @BindView(R.id.edt_forward_content)
    protected EditText edt_forward_content;
    @BindView(R.id.tv_content_num)
    protected TextView tv_content_num;

    @BindView(R.id.prize_rg)
    protected RadioGroup prize_rg;
    @BindView(R.id.prize_yes_rb)
    protected RadioButton prize_yes_rb;

    @BindView(R.id.prize_no_rb)
    protected RadioButton prize_no_rb;

    @BindView(R.id.ll_bottom_num)
    protected LinearLayout ll_bottom_num;
    @BindView(R.id.edt_prize_ci_nun)
    protected EditText edt_prize_ci_nun;
    @BindView(R.id.tv_ge_xianzhi_num)
    protected TextView tv_ge_xianzhi_num;

    @BindView(R.id.tv_top_label)
    protected TextView tv_top_label;

    private String winCount; //中奖次数

    private String gameCount; //游戏次数

    private String limitType = "D"; //没人每天限制类型 每人每天D，每人P

    private String  shareGameCount; //奖励次数
    /**
     * 是否设置安慰奖，1-是，0-否
     */
    private String consolationPrizeFlag;

    private String  isShareGameCount = "YES";

   /* protected String forwardTitle = "亲，快来点我玩游戏获大奖喽~";
    protected String forwardContent = "[点我玩，获优惠]确认了眼神，你就是幸运的人";*/
   protected String forwardTitle = "";
    protected String forwardContent = "";

    private String isPrizeNum = "YES";
    private String isPlayDayNum = "YES";

    private String btnFlag = "save";

    private  String compId;

    protected Campaign campaign = new Campaign();

    protected String posterTemplete;

    protected  List<CampaignCoupon> forwardCouponList = new ArrayList<>();

    protected List<MaterialGame> materialGameList = new ArrayList<>();
    private String storeSelectType ;
    private String storeStr;
    private String userId;
    private String compaignStatus;


    protected   String isEditCampaign = "NO";

    protected String isCopyCreate ;


    private String title;

    private String campaignTypeId;

    protected static   CampaignGame campaignGame = new CampaignGame();

    //素材
    protected MaterialGame materialGame = new MaterialGame();
    //素材id
    protected   String posterId;
    //小程序图标
    protected   String miniLinkIcon;
    protected String bannerId;
    protected String backGroundId;
    protected String wxIconId;
    protected String backGround;

    protected int rewardTitleLenth = 0;
    protected int rewardContentLenth = 0;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_laohu_pin_tuan_next_three;
    }

    @Override
    protected void initView() {

        if("lh".equals(LaohuPinTuanNewActivity.campaignType)){
            campaignTypeId = "0";
            tvTitle.setText("老虎机活动设置");
            tv_ge_xianzhi_num.setVisibility(View.GONE);
            tv_top_label.setText("本次老虎机的中奖概率是多少");
        }else {
            tvTitle.setText("砸金蛋活动设置");
            campaignTypeId = "1";
            tv_top_label.setText("本次砸金蛋的中奖概率是多少");
            tv_ge_xianzhi_num.setVisibility(View.VISIBLE);
        }

        DaggerCampaignsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignsModule(new CampaignsModule(this))
                .build()
                .inject(this);

        setSetTep();
        compId = SPUtils.getInstance().getString("compId");
        userId = SPUtils.getInstance().getString("userId");

        edt_forward_title.setFilters(new InputFilter[]{inputFilter});
        edt_forward_content.setFilters(new InputFilter[]{inputFilter});
        isEditCampaign  = getIntent().getStringExtra("isEditCampaign");
        isCopyCreate = getIntent().getStringExtra("isCopyCreate");
        if("YES".equals(isEditCampaign)){  //编辑
            if("YES".equals(isCopyCreate)){  //编辑页面复制并创建
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setText("复制并创建");
            }else {
                tvRight.setVisibility(View.GONE);
            }
        }

        compaignStatus = LaohuPinTuanNewActivity.compaignStatus;

        campaign = LaohuPinTuanNewActivity.campaign; //编辑页面进入


        if(campaign != null ){
            forwardTitle = campaign.getShareTitle();
            forwardContent = campaign.getShareDesc();

            edt_forward_title.setText(forwardTitle!=null ?forwardTitle:LaoHuTempletActivity.shareTitle);
            edt_forward_content.setText(forwardContent!=null ?forwardContent:LaoHuTempletActivity.shareDesc);

            posterId = campaign.getPosterTemplete();

        }

        if(campaignGame ==null){

            campaignGame = new CampaignGame();

        }
        if(campaignGame != null){

            if(campaignGame.getGameCount()!= null && campaignGame.getGameCount().intValue()>0){
                gameCount = String.valueOf(campaignGame.getGameCount());
            }else {
                gameCount = "";
            }

            if(campaignGame.getWinCount()!= null && campaignGame.getWinCount().intValue()>0){
                winCount = String.valueOf(campaignGame.getWinCount());
            }else {
                winCount = "";
            }
            //中奖概率
            if(campaignGame.getShareGameCount()!= null && campaignGame.getShareGameCount().intValue()>0){  //表示有奖励
                shareGameCount = String.valueOf(campaignGame.getShareGameCount());
                isShareGameCount = "YES";
            }else if(campaignGame.getShareGameCount()!= null){
                isShareGameCount = "YES";  //默认为奖励次数
                shareGameCount = "0";
            }

            if(campaignGame.getEffectTimeFlag()!= null && !"".equals(campaignGame.getEffectTimeFlag())){
            }else {
                campaignGame.setEffectTimeFlag(null);
            }

            if(campaignGame.getMaterialGameId()!= null && !"".equals(campaignGame.getMaterialGameId())){
            }else {
                campaignGame.setMaterialGameId(null);
            }
            limitType = campaignGame.getLimitType();

        }


        if("A".equals(storeSelectType)){
            storeStr = "";
        }else if("D".equals(storeSelectType)){
            storeStr = "";
        }else if("J".equals(storeSelectType)){
            storeStr = "";
        }else{
            storeSelectType = "";

        }
        edt_fen_nun.setText(winCount);

        if("D".equals(limitType)){
            ll_left_day_num.setVisibility(View.VISIBLE);
            ll_right_day_num.setVisibility(View.GONE);
            per_left_num_rb.setChecked(true);
            per_right_num_rb.setChecked(false);
            edt_left_ci_nun.setText(gameCount);

        }else if("P".equals(limitType)){
            ll_left_day_num.setVisibility(View.GONE);
            ll_right_day_num.setVisibility(View.VISIBLE);
            per_left_num_rb.setChecked(false);
            per_right_num_rb.setChecked(true);
            edt_right_ci_nun.setText(gameCount);
        }else {
            limitType = "D";
            per_left_num_rb.setChecked(true);
            per_right_num_rb.setChecked(false);
            edt_left_ci_nun.setText(gameCount);
            ll_left_day_num.setVisibility(View.VISIBLE);
            ll_right_day_num.setVisibility(View.GONE);
        }

        if("YES".equals(isShareGameCount)){
            prize_yes_rb.setChecked(true);
            prize_no_rb.setChecked(false);
            edt_prize_ci_nun.setText(shareGameCount);
            ll_bottom_num.setVisibility(View.VISIBLE);

        }else {
            prize_yes_rb.setChecked(false);
            prize_no_rb.setChecked(true);
            ll_bottom_num.setVisibility(View.GONE);
        }

        posterId = LaohuPinTuanNewActivity.posterId;
        backGroundId = LaohuPinTuanNewActivity.backGroundId;
        miniLinkIcon = LaohuPinTuanNewActivity.miniLinkIcon;

        if("S".equals(LaohuPinTuanNewActivity.compaignStatus)){  //编辑页面
            forwardTitle = campaign.getShareTitle();
            forwardContent = campaign.getShareDesc();
            edt_forward_title.setText(forwardTitle!=null ?forwardTitle:"");
            edt_forward_content.setText(forwardContent!=null ?forwardContent:"");

        }else if("NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){   //新建活动
            forwardTitle = campaign.getShareTitle();
            forwardContent = campaign.getShareDesc();
            if("YES".equals(LaohuPinTuanNewActivity.intentTemplet)){  //新建选择模板跳进来
                posterId = LaohuPinTuanNewActivity.posterId;
                backGroundId = LaohuPinTuanNewActivity.backGroundId;
                miniLinkIcon = LaohuPinTuanNewActivity.miniLinkIcon;
                campaignTypeId = posterId;
            }
        }
        presenter.queryTigerPoster(this,userId,"",campaignTypeId,"lhptThree",LaohuPinTuanNewActivity.campaignType);

        edt_forward_title.setText(forwardTitle!=null && !"".equals(forwardTitle)?forwardTitle:LaoHuTempletActivity.shareTitle);
        edt_forward_content.setText(forwardContent!=null && !"".equals(forwardContent)?forwardContent:LaoHuTempletActivity.shareDesc);
        if(edt_forward_content.getText().toString().trim() != null ){
            int strLen = edt_forward_content.getText().toString().trim().length();
            tv_content_num.setText(strLen+"/45");
        }

        if(edt_forward_title.getText().toString().trim() != null ){
            int strLen =edt_forward_title.getText().toString().trim().length();
            tv_title_num.setText(strLen+"/30");
        }
        if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
            ll_save.setVisibility(View.VISIBLE);
            btnSavePublic.setVisibility(View.VISIBLE);
            btnPre.setVisibility(View.VISIBLE);
            edt_forward_content.setEnabled(true);
            edt_forward_title.setEnabled(true);
            edt_fen_nun.setEnabled(true);
            edt_prize_ci_nun.setEnabled(true);
            edt_right_ci_nun.setEnabled(true);
            edt_left_ci_nun.setEnabled(true);
            per_num_rg.setEnabled(true);

            prize_rg.setEnabled(true);
            per_num_rg.setFocusable(true);
            prize_rg.setFocusable(true);
            per_left_num_rb.setEnabled(true);
            per_right_num_rb.setEnabled(true);
            prize_no_rb.setEnabled(true);
            prize_yes_rb.setEnabled(true);
        }else {
            ll_save.setVisibility(View.GONE);
            btnSavePublic.setText("返回");
            btnSavePublic.setVisibility(View.VISIBLE);
            btnPre.setVisibility(View.VISIBLE);
            edt_forward_content.setEnabled(false);
            edt_forward_title.setEnabled(false);
            edt_fen_nun.setEnabled(false);
            edt_prize_ci_nun.setEnabled(false);
            edt_right_ci_nun.setEnabled(false);
            edt_left_ci_nun.setEnabled(false);
            per_num_rg.setEnabled(false);
            prize_rg.setEnabled(false);
            per_num_rg.setFocusable(false);
            prize_rg.setFocusable(false);
            per_left_num_rb.setEnabled(false);
            per_right_num_rb.setEnabled(false);
            prize_no_rb.setEnabled(false);
            prize_yes_rb.setEnabled(false);
        }

        setListener();

        edt_fen_nun.setSelection(edt_fen_nun.getText().toString().trim().length());
        edt_left_ci_nun.setSelection(edt_left_ci_nun.getText().toString().trim().length());
        edt_right_ci_nun.setSelection(edt_right_ci_nun.getText().toString().trim().length());
        edt_prize_ci_nun.setSelection(edt_prize_ci_nun.getText().toString().trim().length());
        edt_forward_content.setSelection(edt_forward_content.getText().toString().trim().length());
        edt_forward_title.setSelection(edt_forward_title.getText().toString().trim().length());


    }

    @Override
    protected void initData() {



    }

    private void setListener(){

        edt_fen_nun.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edt_fen_nun.getText().toString().trim().length()>0){
//                    edt_fen_nun.setSelection(edt_fen_nun.getText().toString().trim().length());
                   String fen =  edt_fen_nun.getText().toString().trim();

                   if(fen != null && !"".equals(fen)){


                       if(Integer.parseInt(fen)>100){
                           ToastUtils.showShort("请填写小于等于100数字");
                           edt_fen_nun.setText("");
                       }

                       if(Integer.parseInt(fen)<1){
                           ToastUtils.showShort("最少是大于等于1");
                           edt_fen_nun.setText("");
                       }
                   }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_left_ci_nun.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edt_left_ci_nun.getText().toString().trim().length()>0){
                    edt_left_ci_nun.setSelection(edt_left_ci_nun.getText().toString().trim().length());

                    String cinum =  edt_left_ci_nun.getText().toString().trim();

                    if(cinum != null && !"".equals(cinum)){
                        if(Integer.parseInt(cinum)<1){
                            ToastUtils.showShort("最少是大于等于1");
                            edt_left_ci_nun.setText("");
                        }
                        if("ge".equals(LaohuPinTuanNewActivity.campaignType)){
                            if(Integer.parseInt(cinum)>9){
                                ToastUtils.showShort("请输入小于等于9的数字");
                                edt_left_ci_nun.setText("");
                            }
                        }

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_right_ci_nun.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edt_right_ci_nun.getText().toString().trim().length()>0){
                    edt_right_ci_nun.setSelection(edt_right_ci_nun.getText().toString().trim().length());
                    String cinum =  edt_right_ci_nun.getText().toString().trim();
                    if(cinum != null && !"".equals(cinum)){
                        if(Integer.parseInt(cinum)<1){
                            ToastUtils.showShort("最少是大于等于1");
                            edt_right_ci_nun.setText("");
                        }

                        if("ge".equals(LaohuPinTuanNewActivity.campaignType)){
                            if(Integer.parseInt(cinum)>9){
                                ToastUtils.showShort("请输入小于等于9的数字");
                                edt_right_ci_nun.setText("");
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_prize_ci_nun.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edt_prize_ci_nun.getText().toString().trim().length()>0){
                    edt_prize_ci_nun.setSelection(edt_prize_ci_nun.getText().toString().trim().length());
                    String cinum =  edt_prize_ci_nun.getText().toString().trim();
                    if(cinum != null && !"".equals(cinum)){
                        if(Integer.parseInt(cinum)<1){
                            ToastUtils.showShort("最少是大于等于1");
                            edt_prize_ci_nun.setText("");
                        }

                        /*if("D".equals(limitType)){
                            String leftCi = edt_left_ci_nun.getText().toString().trim();

                            if(leftCi != null && !"".equals(leftCi)){
                                if(Integer.parseInt(cinum)>Integer.parseInt(leftCi)){
                                    ToastUtils.showShort("请输入小于等于"+Integer.parseInt(leftCi)+"的数字");
                                    edt_prize_ci_nun.setText("");
                                }
                            }

                        }else {  //p 每人
                            String rightCi = edt_right_ci_nun.getText().toString().trim();
                            if("ge".equals(LaohuPinTuanNewActivity.campaignType)){


                                if(rightCi != null && !"".equals(rightCi)){
                                    if(Integer.parseInt(cinum)>Integer.parseInt(rightCi)){
                                        ToastUtils.showShort("请输入小于等于"+Integer.parseInt(rightCi)+"的数字");
                                        edt_prize_ci_nun.setText("");
                                    }
                                }
                            }else {
                                if(rightCi != null && !"".equals(rightCi)){
                                    if(Integer.parseInt(cinum)>Integer.parseInt(rightCi)){
                                        ToastUtils.showShort("请输入小于等于"+Integer.parseInt(rightCi)+"的数字");
                                        edt_prize_ci_nun.setText("");
                                    }
                                }
                            }
                        }*/
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

        //每人每天玩限制次数
        per_num_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.per_left_num_rb:
                        limitType = "D";
                        ll_left_day_num.setVisibility(View.VISIBLE);
                        ll_right_day_num.setVisibility(View.GONE);
                        break;

                    case R.id.per_right_num_rb:

                        limitType = "P";
                        ll_left_day_num.setVisibility(View.GONE);
                        ll_right_day_num.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        });

        //分享奖励次数
        prize_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.prize_yes_rb:
                        isShareGameCount = "YES";
                        ll_bottom_num.setVisibility(View.VISIBLE);

                        break;

                    case R.id.prize_no_rb:
                        isShareGameCount = "NO";
                        ll_bottom_num.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.startActivity(CampaignListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }

    public  boolean isNumeric(String str){
        //String regex="^[1-9]\\d*$"; 不以0 为开头
        if(str.matches("^0\\d*$")){  //以0开头
            return true;
        }else{
            return false;
        }
    }


    private void setSetTep(){

        ivOne.setBackgroundResource(R.drawable.ic_first_clicked);
        ivEllipsisOne.setBackgroundResource(R.drawable.ic_ellipsis_blue);
        ivTwo.setBackgroundResource(R.drawable.ic_two_clicked);
        ivEllipsisTwo.setBackgroundResource(R.drawable.ic_ellipsis_blue);
        ivThree.setBackgroundResource(R.drawable.ic_three_clicked);
    }


    @OnClick({R.id.iv_back,R.id.tv_right,
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
                ToastUtils.showCustomShortBottom(getResources().getString(R.string.create_success));
                isCopyCreate = "NO";
                isEditCampaign = "YES";
                LaohuPinTuanNewActivity.compaignStatus = "NEW";
                LaohuPinTuanNewActivity.posterId = posterId;
                //缓存

                setValue();
                LaohuPinTuanNewActivity.campaign = campaign;
                if(LaohuPinTuanNewActivity.campaign != null){
                    LaohuPinTuanNewActivity.campaign.setCampaignId(null);
                    LaohuPinTuanNewActivity.campaign.setCampaignName("");
                }

                Intent intent1 = new Intent(LaohuPinTuanNextThreeActivity.this,LaohuPinTuanNewActivity.class);
                intent1.putExtra("title",title);
                intent1.putExtra("isCopyCreate",isCopyCreate);
                intent1.putExtra("isEditCampaign",isEditCampaign);
                intent1.putExtra("compaignStatus",LaohuPinTuanNewActivity.compaignStatus);
                intent1.putExtra("campaignType",LaohuPinTuanNewActivity.campaignType);
                ActivityUtils.startActivity(intent1,
                        R.anim.slide_in_left, R.anim.slide_out_right);

                break;
            case R.id.btn_save:
                btnFlag = "save";

                setValue();
                if(check()){
                    return;
                }

                if (!ButtonUtils.isFastDoubleClick(R.id.btn_save)) {
                    presenter.saveCampaignInfoForgame(this,campaign,LaohuPinTuanNewActivity.storeIdCampaignType,LaohuPinTuanNewActivity.storeStr,LaohuPinTuanNextTwoActivity.campaignNonrewardPicList,LaohuPinTuanNextTwoActivity.campaignGamesetrewardList,campaignGame,posterId,userId,compId,btnFlag);
                }
                break;
            case R.id.btn_save_and_public:
                btnFlag = "saveAndpublic";
                setValue();
                if(check()){
                    return;
                }


                if("S".equals(compaignStatus)||"NEW".equals(compaignStatus)){
//
                    if (!ButtonUtils.isFastDoubleClick(R.id.btn_save_and_public)) {
                        presenter.saveCampaignInfoForgame(this,campaign,LaohuPinTuanNewActivity.storeIdCampaignType,LaohuPinTuanNewActivity.storeStr,LaohuPinTuanNextTwoActivity.campaignNonrewardPicList,LaohuPinTuanNextTwoActivity.campaignGamesetrewardList,campaignGame,posterId,userId,compId,btnFlag);
                    }

                }else {
                    ActivityUtils.startActivity(CampaignListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                    finish();
                }
                break;
            case R.id.btn_pre:  //预览
                setValue();
                setPopDialogLhGe();
                break;

            case R.id.btn_up:
                setValue();
                if(campaign != null){
                    LaohuPinTuanNewActivity.campaign = campaign;
                }

                ActivityUtils.finishActivity(LaohuPinTuanNextThreeActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                break;


        }
    }

    private void setValue(){
        winCount = edt_fen_nun.getText().toString().trim();
        if("D".equals(limitType)){  //没人每天限制
            gameCount = edt_left_ci_nun.getText().toString().trim();
        }else if("P".equals(limitType)){ //一个人玩限制
            gameCount = edt_right_ci_nun.getText().toString().trim();
        }

        if("YES".equals(isShareGameCount)){  //奖励次数
            shareGameCount = edt_prize_ci_nun.getText().toString().trim();

        }else if("NO".equals(isShareGameCount)){ //
            shareGameCount = "0";
        }

        if(winCount != null && !"".equals(winCount)){

        }else {
            winCount = "0";
        }

        if(gameCount != null && !"".equals(gameCount)){

        }else {
            gameCount = "0";
        }
        if(shareGameCount != null && !"".equals(shareGameCount)){

        }else {
            shareGameCount = "0";
        }
        campaignGame.setLimitType(limitType);
        campaignGame.setWinCount(Integer.parseInt(winCount));
        campaignGame.setGameCount(Integer.parseInt(gameCount));
        campaignGame.setShareGameCount(Integer.parseInt(shareGameCount));

        forwardContent = edt_forward_content.getText().toString().trim();
        forwardTitle = edt_forward_title.getText().toString().trim();
        campaign.setShareDesc(forwardContent);
        campaign.setShareTitle(forwardTitle);
    }




    private boolean check(){

        if("save".equals(btnFlag)){
            if(winCount != null && !"".equals(winCount)){
                if(winCount != null && !"".equals(winCount)){
                    if(Integer.parseInt(winCount)>100){
                        ToastUtils.showShort("请填写小于等于100数字");
                        return true;
                    }
                }else {
                    ToastUtils.showShort("请填写小于等于100数字");
                    return true;
                }
            }else {
                setDialog("您在第三步中，信息设置不完善，请补充信息。");
                return true;
            }

            if(gameCount != null && !"".equals(gameCount)){

                if(Integer.parseInt(winCount)<1){
                    ToastUtils.showShort("最少是大于等于1");
                    return true;
                }
            }else {
                setDialog("您在第三步中，信息设置不完善，请补充信息。");
                return true;
            }
            if(shareGameCount != null && !"".equals(shareGameCount)){

                if(Integer.parseInt(winCount)<1){
                    ToastUtils.showShort("最少是大于等于1");
                    return true;
                }
            }else {
                setDialog("您在第三步中，设置信息不完善，请补充信息。");
                return true;
            }
            if(LaohuPinTuanNextTwoActivity.campaignGamesetrewardList!=null && LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.size()>0){
                for(CampaignGamesetreward campaignGamesetreward : LaohuPinTuanNextTwoActivity.campaignGamesetrewardList){

                    if(campaignGamesetreward != null){
                        String name = campaignGamesetreward.getPrizeName();

                        List<CampaignCoupon> campaignCouponList = campaignGamesetreward.getList();
                        if(campaignCouponList != null && campaignCouponList.size()>0){


                        }else{
                            setDialog("请完善第二步优惠券信息。");
                            return true;
                        }

                    }



                }
            }else {
                setDialog("您在第二步中，设置信息不完善，请补充信息。");
                return true;
            }


        }else {
            if(winCount != null && !"".equals(winCount)){
                if(winCount != null && !"".equals(winCount)){
                    if(Integer.parseInt(winCount)>100){
                        ToastUtils.showShort("请填写小于等于100数字");
                        return true;
                    }
                }else {
                    ToastUtils.showShort("请填写小于等于100数字");
                    return true;
                }
            }else {
                setDialog("您在第三步中，设置信息不完善，请补充信息。");
                return true;
            }

            if(gameCount != null && !"".equals(gameCount)){

                if(Integer.parseInt(winCount)<1){
                    ToastUtils.showShort("最少是大于等于1");
                    return true;
                }
            }else {
                setDialog("您在第三步中，设置信息不完善，请补充信息。");
                return true;
            }
            if(shareGameCount != null && !"".equals(shareGameCount)){

                if(Integer.parseInt(winCount)<1){
                    ToastUtils.showShort("最少是大于等于1");
                    return true;
                }
            }else {
                setDialog("您在第三步中，设置信息不完善，请补充信息。");
                return true;
            }
            if(campaign !=null){
                String campaignName = campaign.getCampaignName();
                if(campaignName == null ||"".equals(campaignName)){
//                ToastUtils.showShort("请输入活动名称");
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
            if("0".equals(campaignGame.getConsolationPrizeFlag())){
                if(LaohuPinTuanNextTwoActivity.campaignNonrewardPicList!=null && LaohuPinTuanNextTwoActivity.campaignNonrewardPicList.size()>0){
                    for(CampaignNonrewardPic campaignNonrewardPic : LaohuPinTuanNextTwoActivity.campaignNonrewardPicList){

                        if(campaignNonrewardPic != null){
                            String id = campaignNonrewardPic.getPictureId();

                            if(id.isEmpty()){
                                setDialog("您在第二步中，未中奖设置信息不完善，请补充信息。");
                                return true;
                            }
                        }else {
                            setDialog("您在第二步中，未中奖设置信息不完善，请补充信息。");
                            return true;
                        }
                    }

                }
            }else {
                if(LaohuPinTuanNextTwoActivity.campaignGamesetrewardList!=null && LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.size()>0){
                    for(CampaignGamesetreward campaignGamesetreward : LaohuPinTuanNextTwoActivity.campaignGamesetrewardList){

                        if(campaignGamesetreward != null){
                            String name = campaignGamesetreward.getPrizeName();

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
                }else {
                    setDialog("您在第二步中，设置信息不完善，请补充信息。");
                    return true;
                }
            }

            if(forwardContent == null ||"".equals(forwardContent)){
                setDialog("在第三步，设置信息不完善，请补充信息。");
                return true;
            }
            if(forwardTitle == null || "".equals(forwardTitle)){
                setDialog("在第三步，设置信息不完善，请补充信息。");
                return true;
            }

            if(posterId == null || "".equals(posterId)){
                setDialog("在第三步，设置信息不完善，请补充信息。");
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



    protected void setPopDialog(){

        View customView = View.inflate(this, R.layout.layout_lhpt_preview_dialog, null);

        PopWindow popAlertView = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopAlert)
                .setView(customView)
                .show();
        FrameLayout fl_content = (FrameLayout) customView.findViewById(R.id.fl_content);
        Button btn_use = (Button)customView.findViewById(R.id.btn_use);
        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popAlertView.dismiss();
            }
        });

        ImageView iv_banner = (ImageView)customView.findViewById(R.id.iv_banner);

//        ImageView iv_show_poster = (ImageView)customView.findViewById(R.id.iv_show_poster);
        LinearLayout ll_dis_top = (LinearLayout)customView.findViewById(R.id.ll_dis_top);
        ll_dis_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAlertView.dismiss();
            }
        });
        LinearLayout ll_wechart = (LinearLayout) customView.findViewById(R.id.ll_wechart);
        TextView tv_title = (TextView) customView.findViewById(R.id.tv_title);
        TextView tv_title2 = (TextView) customView.findViewById(R.id.tv_title2);
        TextView tv_content2 = (TextView) customView.findViewById(R.id.tv_content2);

        forwardContent = edt_forward_content.getText().toString().trim();
        forwardTitle = edt_forward_title.getText().toString().trim();

        tv_title.setText(forwardTitle != null ?forwardTitle:"");
        tv_title2.setText(forwardTitle != null ?forwardTitle:"");

        tv_content2.setText(forwardContent != null ?forwardContent:"");


        LinearLayout ll_sylunbo = (LinearLayout) customView.findViewById(R.id.ll_sylunbo);

        RelativeLayout ll_sylb = (RelativeLayout) customView.findViewById(R.id.ll_sylb);
        ImageView iv_lhpt_sylb_campaign = (ImageView)customView.findViewById(R.id.iv_lhpt_sylb_campaign);
        String wxPath = HttpUrls.IMAGE_ULR +wxIconId;

        RoundedCorners roundedCorners= new RoundedCorners(8);

        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_logo_default)
                .error(R.drawable.ic_logo_default)
                .fallback(R.drawable.ic_logo_default);

        Glide.with(this).load(wxPath).apply(requestOptions).into(iv_lhpt_sylb_campaign);
       /* TextView tv_campaign_title = (TextView) customView.findViewById(R.id.tv_campaign_title);
        TextView tv_campaign_content = (TextView) customView.findViewById(R.id.tv_campaign_content);
        tv_campaign_title.setText(forwardTitle);
        tv_campaign_content.setText(forwardContent);*/


        LinearLayout ll_yxzy = (LinearLayout) customView.findViewById(R.id.ll_yxzy);
        ImageView iv_lh_pt_yxzy = (ImageView)customView.findViewById(R.id.iv_lh_pt_yxzy);

        LinearLayout ll_yxzy2 = (LinearLayout) customView.findViewById(R.id.ll_yxzy2);
        ImageView iv_lh_pt_yxzy2 = (ImageView)customView.findViewById(R.id.iv_lh_pt_yxzy2);
        String bgPath = HttpUrls.IMAGE_ULR +backGroundId;
    /*    Glide.with(this).load(bgPath).into(new SimpleTarget<Drawable>(50,50) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_lh_pt_yxzy.setBackground(resource);
                }
            }
        });*/

        RequestOptions requestOptions2 = new RequestOptions()
                .placeholder(R.drawable.ic_banner_default)
                .error(R.drawable.ic_banner_default)
                .fallback(R.drawable.ic_banner_default);


        Glide.with(this).load(bgPath).apply(requestOptions2).into(iv_lh_pt_yxzy);


        Glide.with(this).load(bgPath).apply(requestOptions2).into(iv_lh_pt_yxzy2);

        Button lh_pt_fxxg = (Button)customView.findViewById(R.id.lh_pt_fxxg);
        Button lh_pt_sylunbo = (Button)customView.findViewById(R.id.lh_pt_sylunbo);
        Button lh_pt_sylb = (Button)customView.findViewById(R.id.lh_pt_sylb);
        Button lh_pt_yxzy = (Button)customView.findViewById(R.id.lh_pt_yxzy);

        //首页列表
        TextView tv_time = (TextView) customView.findViewById(R.id.tv_time);
        TextView tv_campaign_name = (TextView) customView.findViewById(R.id.tv_campaign_name);
        String startDate = "";
        String endDate = "";
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
        String bannerPath = HttpUrls.IMAGE_ULR +bannerId;
        Glide.with(this).load(bannerPath).into(new SimpleTarget<Drawable>(50,50) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_banner.setBackground(resource);
                }
            }
        });


        //默认海报
        lh_pt_fxxg.setPressed(true);
        lh_pt_sylunbo.setPressed(false);
        lh_pt_sylb.setPressed(false);
        lh_pt_yxzy.setPressed(false);
        lh_pt_fxxg.setTextColor(getResources().getColor(R.color.btn_color_blue));
        lh_pt_sylunbo.setTextColor(getResources().getColor(R.color.color_gray_deep));
        lh_pt_sylb.setTextColor(getResources().getColor(R.color.color_gray_deep));
        lh_pt_yxzy.setTextColor(getResources().getColor(R.color.color_gray_deep));

        lh_pt_fxxg.setBackgroundResource(R.drawable.ic_lh_pt_fxxg_selected);
        lh_pt_sylunbo.setBackgroundResource(R.drawable.ic_lh_pt_sylunbo_unselect);
        lh_pt_sylb.setBackgroundResource(R.drawable.ic_lh_pt_sylb_unselect);
        lh_pt_yxzy.setBackgroundResource(R.drawable.ic_lh_pt_yxzy_unselect);

        ll_wechart.setVisibility(View.VISIBLE);
        ll_sylunbo.setVisibility(View.GONE);
        ll_sylb.setVisibility(View.GONE);
        ll_yxzy.setVisibility(View.GONE);
        ll_yxzy2.setVisibility(View.GONE);
        fl_content.setBackgroundResource(R.drawable.ic_iphone_forward);
        lh_pt_fxxg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lh_pt_fxxg.setPressed(true);
                lh_pt_sylunbo.setPressed(false);
                lh_pt_sylb.setPressed(false);
                lh_pt_yxzy.setPressed(false);

                lh_pt_fxxg.setTextColor(getResources().getColor(R.color.btn_color_blue));
                lh_pt_sylunbo.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_sylb.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_yxzy.setTextColor(getResources().getColor(R.color.color_gray_deep));

                lh_pt_fxxg.setBackgroundResource(R.drawable.ic_lh_pt_fxxg_selected);
                lh_pt_sylunbo.setBackgroundResource(R.drawable.ic_lh_pt_sylunbo_unselect);
                lh_pt_sylb.setBackgroundResource(R.drawable.ic_lh_pt_sylb_unselect);
                lh_pt_yxzy.setBackgroundResource(R.drawable.ic_lh_pt_yxzy_unselect);

                ll_wechart.setVisibility(View.VISIBLE);
                ll_sylunbo.setVisibility(View.GONE);
                ll_sylb.setVisibility(View.GONE);
                ll_yxzy.setVisibility(View.GONE);
                ll_yxzy2.setVisibility(View.GONE);
                fl_content.setBackgroundResource(R.drawable.ic_iphone_forward);
            }
        });

        lh_pt_sylunbo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lh_pt_fxxg.setPressed(false);
                lh_pt_sylunbo.setPressed(true);
                lh_pt_sylb.setPressed(false);
                lh_pt_yxzy.setPressed(false);
                lh_pt_fxxg.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_sylunbo.setTextColor(getResources().getColor(R.color.btn_color_blue));
                lh_pt_sylb.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_yxzy.setTextColor(getResources().getColor(R.color.color_gray_deep));

                lh_pt_fxxg.setBackgroundResource(R.drawable.ic_lh_pt_fxxg_unselect);
                lh_pt_sylunbo.setBackgroundResource(R.drawable.ic_lh_pt_sylunbo_selected);
                lh_pt_sylb.setBackgroundResource(R.drawable.ic_lh_pt_sylb_unselect);
                lh_pt_yxzy.setBackgroundResource(R.drawable.ic_lh_pt_yxzy_unselect);

                ll_wechart.setVisibility(View.GONE);
                ll_sylunbo.setVisibility(View.VISIBLE);
                ll_sylb.setVisibility(View.GONE);
                ll_yxzy.setVisibility(View.GONE);
                ll_yxzy2.setVisibility(View.GONE);
                fl_content.setBackgroundResource(R.drawable.ic_pt_preview_bg);

            }
        });

        lh_pt_sylb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lh_pt_fxxg.setPressed(false);
                lh_pt_sylunbo.setPressed(false);
                lh_pt_sylb.setPressed(true);
                lh_pt_yxzy.setPressed(false);

                lh_pt_fxxg.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_sylunbo.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_sylb.setTextColor(getResources().getColor(R.color.btn_color_blue));
                lh_pt_yxzy.setTextColor(getResources().getColor(R.color.color_gray_deep));

                lh_pt_fxxg.setBackgroundResource(R.drawable.ic_lh_pt_fxxg_unselect);
                lh_pt_sylunbo.setBackgroundResource(R.drawable.ic_lh_pt_sylunbo_unselect);
                lh_pt_sylb.setBackgroundResource(R.drawable.ic_lh_pt_sylb_selected);
                lh_pt_yxzy.setBackgroundResource(R.drawable.ic_lh_pt_yxzy_unselect);

                ll_wechart.setVisibility(View.GONE);
                ll_sylunbo.setVisibility(View.GONE);
                ll_sylb.setVisibility(View.VISIBLE);
                ll_yxzy.setVisibility(View.GONE);
                ll_yxzy2.setVisibility(View.GONE);

                fl_content.setBackgroundResource(R.drawable.ic_pt_preview_bg);
            }
        });

        lh_pt_yxzy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lh_pt_fxxg.setPressed(false);
                lh_pt_sylunbo.setPressed(false);
                lh_pt_sylb.setPressed(false);
                lh_pt_yxzy.setPressed(true);

                lh_pt_fxxg.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_sylunbo.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_sylb.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_yxzy.setTextColor(getResources().getColor(R.color.btn_color_blue));

                lh_pt_fxxg.setBackgroundResource(R.drawable.ic_lh_pt_fxxg_unselect);
                lh_pt_sylunbo.setBackgroundResource(R.drawable.ic_lh_pt_sylunbo_unselect);
                lh_pt_sylb.setBackgroundResource(R.drawable.ic_lh_pt_sylb_unselect);
                lh_pt_yxzy.setBackgroundResource(R.drawable.ic_lh_pt_yxzy_selected);

                ll_wechart.setVisibility(View.GONE);
                ll_sylunbo.setVisibility(View.GONE);
                ll_sylb.setVisibility(View.GONE);


                if("lh".equals(LaohuPinTuanNewActivity.campaignType)){
                    ll_yxzy.setVisibility(View.VISIBLE);
                    ll_yxzy2.setVisibility(View.GONE);
                    fl_content.setBackgroundResource(R.drawable.ic_lh_preview_bg);
                }else if("ge".equals(LaohuPinTuanNewActivity.campaignType)){
                    ll_yxzy.setVisibility(View.GONE);
                    ll_yxzy2.setVisibility(View.VISIBLE);
                    fl_content.setBackgroundResource(R.drawable.ic_ge_three_bg);
                }
            }
        });

    }
    protected void setPopDialogLhGe(){

        View customView = View.inflate(this, R.layout.layout_lh_ge_template_preview, null);

        PopWindow popAlertView = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopAlert)
                .setView(customView)
                .show();
        FrameLayout fl_content = (FrameLayout) customView.findViewById(R.id.fl_content);
        Button btn_use = (Button)customView.findViewById(R.id.btn_use);
        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popAlertView.dismiss();
            }
        });

        ImageView iv_banner = (ImageView)customView.findViewById(R.id.iv_banner);

//        ImageView iv_show_poster = (ImageView)customView.findViewById(R.id.iv_show_poster);
        LinearLayout ll_dis_top = (LinearLayout)customView.findViewById(R.id.ll_dis_top);
        ll_dis_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAlertView.dismiss();
            }
        });
        LinearLayout ll_wechart = (LinearLayout) customView.findViewById(R.id.ll_wechart);
        //小程序
        ImageView iv_img = (ImageView) customView.findViewById(R.id.iv_img);
        //微信小图标
        ImageView iv_imgs = (ImageView) customView.findViewById(R.id.iv_imgs);

        TextView tv_title = (TextView) customView.findViewById(R.id.tv_title);
        TextView tv_title2 = (TextView) customView.findViewById(R.id.tv_title2);
        TextView tv_content2 = (TextView) customView.findViewById(R.id.tv_content2);
        String wxPath = HttpUrls.IMAGE_ULR + miniLinkIcon;
        Glide.with(this).load(wxPath).into(new SimpleTarget<Drawable>(100,100) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_img.setBackground(resource);
                }
            }
        });

        Glide.with(this).load(HttpUrls.IMAGE_ULR + wxIconId).into(new SimpleTarget<Drawable>(20,20) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_imgs.setBackground(resource);
                }
            }
        });


        forwardContent = edt_forward_content.getText().toString().trim();
        forwardTitle = edt_forward_title.getText().toString().trim();

        tv_title.setText(forwardTitle != null ?forwardTitle:"");
        tv_title2.setText(forwardTitle != null ?forwardTitle:"");

        tv_content2.setText(forwardContent != null ?forwardContent:"");


        RelativeLayout ll_sylb = (RelativeLayout) customView.findViewById(R.id.ll_sylb);
        ImageView iv_lhpt_sylb_campaign = (ImageView)customView.findViewById(R.id.iv_lhpt_sylb_campaign);
/*        Glide.with(this).load(campaignPath).into(new SimpleTarget<Drawable>(50,50) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_lhpt_sylb_campaign.setBackground(resource);
                }
            }
        });*/

        RoundedCorners roundedCorners= new RoundedCorners(8);
        RequestOptions requestOptions2 = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_banner_default)
                .error(R.drawable.ic_banner_default)
                .fallback(R.drawable.ic_banner_default);

        Glide.with(this).load(wxPath).apply(requestOptions2).into(iv_lhpt_sylb_campaign);


        LinearLayout ll_yxzy = (LinearLayout) customView.findViewById(R.id.ll_yxzy);
        ImageView iv_lh_pt_yxzy = (ImageView)customView.findViewById(R.id.iv_lh_pt_yxzy);

        LinearLayout ll_yxzy2 = (LinearLayout) customView.findViewById(R.id.ll_yxzy2);
        ImageView iv_lh_pt_yxzy2 = (ImageView)customView.findViewById(R.id.iv_lh_pt_yxzy2);
        String bgPath = HttpUrls.IMAGE_ULR +backGroundId;
  /*      Glide.with(this).load(bgPath).into(new SimpleTarget<Drawable>(50,50) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_lh_pt_yxzy.setBackground(resource);
                }
            }
        });*/

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_banner_default)
                .error(R.drawable.ic_banner_default)
                .fallback(R.drawable.ic_banner_default);


        Glide.with(this).load(bgPath).apply(requestOptions).into(iv_lh_pt_yxzy);
        Glide.with(this).load(bgPath).apply(requestOptions).into(iv_lh_pt_yxzy2);

        Button lh_pt_fxxg = (Button)customView.findViewById(R.id.lh_pt_fxxg);
        Button lh_pt_sylb = (Button)customView.findViewById(R.id.lh_pt_sylb);
        Button lh_pt_yxzy = (Button)customView.findViewById(R.id.lh_pt_yxzy);

        //首页列表
        TextView tv_time = (TextView) customView.findViewById(R.id.tv_time);
        TextView tv_campaign_name = (TextView) customView.findViewById(R.id.tv_campaign_name);
        String startDate = "";
        String endDate = "";
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
        String bannerPath = HttpUrls.IMAGE_ULR +bannerId;
        Glide.with(this).load(bannerPath).into(new SimpleTarget<Drawable>(50,50) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_banner.setBackground(resource);
                }
            }
        });


        //默认海报
        lh_pt_fxxg.setPressed(false);
        lh_pt_sylb.setPressed(false);
        lh_pt_yxzy.setPressed(true);
        lh_pt_fxxg.setTextColor(getResources().getColor(R.color.color_gray_deep));
        lh_pt_sylb.setTextColor(getResources().getColor(R.color.color_gray_deep));
        lh_pt_yxzy.setTextColor(getResources().getColor(R.color.btn_color_blue));


        ll_wechart.setVisibility(View.GONE);
        ll_sylb.setVisibility(View.GONE);
        if("lh".equals(LaohuPinTuanNewActivity.campaignType)){
            ll_yxzy.setVisibility(View.VISIBLE);
            ll_yxzy2.setVisibility(View.GONE);
            fl_content.setBackgroundResource(R.drawable.ic_lh_preview_bg);
        }else if("ge".equals(LaohuPinTuanNewActivity.campaignType)){
            ll_yxzy.setVisibility(View.GONE);
            ll_yxzy2.setVisibility(View.VISIBLE);
            fl_content.setBackgroundResource(R.drawable.ic_ge_three_bg);
        }
        lh_pt_fxxg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lh_pt_fxxg.setPressed(true);
                lh_pt_sylb.setPressed(false);
                lh_pt_yxzy.setPressed(false);

                lh_pt_fxxg.setTextColor(getResources().getColor(R.color.btn_color_blue));
                lh_pt_sylb.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_yxzy.setTextColor(getResources().getColor(R.color.color_gray_deep));


                ll_wechart.setVisibility(View.VISIBLE);
//                ll_sylunbo.setVisibility(View.GONE);
                ll_sylb.setVisibility(View.GONE);
                ll_yxzy.setVisibility(View.GONE);
                ll_yxzy2.setVisibility(View.GONE);
                fl_content.setBackgroundResource(R.drawable.ic_iphone_forward);
            }
        });



        lh_pt_sylb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lh_pt_fxxg.setPressed(false);
                lh_pt_sylb.setPressed(true);
                lh_pt_yxzy.setPressed(false);

                lh_pt_fxxg.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_sylb.setTextColor(getResources().getColor(R.color.btn_color_blue));
                lh_pt_yxzy.setTextColor(getResources().getColor(R.color.color_gray_deep));


                ll_wechart.setVisibility(View.GONE);
                ll_sylb.setVisibility(View.VISIBLE);
                ll_yxzy.setVisibility(View.GONE);
                ll_yxzy2.setVisibility(View.GONE);

                fl_content.setBackgroundResource(R.drawable.ic_lh_ge_preview_xcxsy_bg);
            }
        });

        lh_pt_yxzy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lh_pt_fxxg.setPressed(false);
                lh_pt_sylb.setPressed(false);
                lh_pt_yxzy.setPressed(true);

                lh_pt_fxxg.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_sylb.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_yxzy.setTextColor(getResources().getColor(R.color.btn_color_blue));


                ll_wechart.setVisibility(View.GONE);
                ll_sylb.setVisibility(View.GONE);


                if("lh".equals(LaohuPinTuanNewActivity.campaignType)){
                    ll_yxzy.setVisibility(View.VISIBLE);
                    ll_yxzy2.setVisibility(View.GONE);
                    fl_content.setBackgroundResource(R.drawable.ic_lh_preview_bg);
                }else if("ge".equals(LaohuPinTuanNewActivity.campaignType)){
                    ll_yxzy.setVisibility(View.GONE);
                    ll_yxzy2.setVisibility(View.VISIBLE);
                    fl_content.setBackgroundResource(R.drawable.ic_ge_three_bg);
                }
            }
        });

    }


    InputFilter inputFilter=new InputFilter() {

        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_,.?!:;…~_\\-\"\"/@*+'()<>{}/[/]()<>{}\\[\\]=%&$|\\/♀♂#¥£¢€\"^` ，。？！：；……～“”、“（）”、（——）‘’＠‘·’＆＊＃《》￥《〈〉》〈＄〉［］￡［］｛｝｛｝￠【】【】％〖〗〖〗／〔〕〔〕＼『』『』＾「」「」｜﹁﹂｀．]");

        @Override
        public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
            Matcher matcher=  pattern.matcher(charSequence);
            if(!matcher.find()){
                return null;
            }else{
//                Toast.makeText(MyApplication.context, "非法字符！", Toast.LENGTH_SHORT).show();
                return "";
            }

        }
    };


}
