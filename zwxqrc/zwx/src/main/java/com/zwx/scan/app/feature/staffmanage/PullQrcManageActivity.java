package com.zwx.scan.app.feature.staffmanage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zwx.library.pickerview.picker.builder.OptionsPickerBuilder;
import com.zwx.library.pickerview.picker.listener.CustomListener;
import com.zwx.library.pickerview.picker.listener.OnOptionsSelectListener;
import com.zwx.library.pickerview.picker.view.OptionsPickerView;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.CardQrcBean;
import com.zwx.scan.app.data.bean.CateBean;
import com.zwx.scan.app.data.bean.DirectoryData;
import com.zwx.scan.app.data.bean.PushPreMessageBean;
import com.zwx.scan.app.data.bean.Pushnotify;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanNewActivity;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanNextTwoActivity;
import com.zwx.scan.app.feature.cateringinfomanage.StoreInfoManageActivity;
import com.zwx.scan.app.feature.cateringinfomanage.StoreInfoParameterSelectorActivity;
import com.zwx.scan.app.utils.ButtonUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2018/12/09
 * desc   : 拉新扫码二维码
 * version: 1.0
 **/
public class PullQrcManageActivity extends BaseActivity<StaffManageContract.Presenter> implements StaffManageContract.View,View.OnClickListener {
    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;
    @BindView(R.id.rl_msg_one)
    protected RelativeLayout rl_msg_one;

    @BindView(R.id.tv_msg_one)
    protected TextView tv_msg_one;
    @BindView(R.id.tv_msg_one_content)
    protected TextView tv_msg_one_content;


    @BindView(R.id.rl_msg_two)
    protected RelativeLayout rl_msg_two;

    @BindView(R.id.tv_msg_two)
    protected TextView tv_msg_two;

    @BindView(R.id.tv_msg_two_content)
    protected TextView tv_msg_two_content;


    @BindView(R.id.rl_msg_three)
    protected RelativeLayout rl_msg_three;

    @BindView(R.id.tv_msg_three)
    protected TextView tv_msg_three;

    @BindView(R.id.tv_msg_three_content)
    protected TextView tv_msg_three_content;

    @BindView(R.id.btn_save)
    protected Button btn_save;
    @BindView(R.id.btn_pre)
    protected Button btn_pre;



    protected OptionsPickerView pvTypeOne,pvTypeTwo,pvTypeThree;

    protected ArrayList<DirectoryData> directoryDataList = new ArrayList<>();
    protected ArrayList contentList = new ArrayList();
    protected List<CardQrcBean> cardQrcBeans = new ArrayList<>();
    protected  String msgOneType = "";
    protected static String msgOneContentId = "";
    protected  String msgOneContent = "无";
    protected Integer sortOne;
    protected  String msgTwoType = "";
    protected  String msgTwoContent = "无";
    protected Integer sortTwo;
    protected static String msgTwoContentId = "";
    protected  String msgThreeType = "";
    protected  String msgThreeContent = "无";
    protected static   String msgThreeContentId = "";
    protected Integer sortThree;

    protected  String pushContentIds = "";
    protected  String pushTypes = "";
//    protected  String pushType1 = "";
//    protected  String pushType2 = "";
//    protected  String pushType3 = "";

    protected String pushTypeDialog ;
    protected static  int RESULT_CODE = 7777;
    protected   int REQUEST_CODE_ONE = 7001;
    protected   int REQUEST_CODE_TWO = 7002;
    protected   int REQUEST_CODE_THREE = 7003;
    private  String userId;

    //活动消息
    protected String  banner;
    protected String  shareTitle;
    protected String  shareDesc;


    //拼团或会员卡消息
    protected String  title;
    protected String  first;
    protected String  keyword1_title;

    protected String  keyword1;
    protected String  keyword2_title;
    protected String  keyword2;

    protected String  campcoupId;
    protected String  keyword3_title;
    protected String  keyword3;
    protected String  keyword4_title;
    protected String  keyword4;
    protected String  remark;

    //会员卡信息
    protected String  title2;
    protected String  first2;
    protected String  keyword1_title2;

    protected String  keyword12;
    protected String  keyword2_title2;
    protected String  keyword22;

    protected String  keyword3_title2;
    protected String  keyword32;
    protected String  remark2;


    protected List<PushPreMessageBean> pushPreMessageList = new ArrayList<>();
    protected List<Pushnotify> pushnotifyList = new ArrayList<>();

    //是否提示 保存成功
    protected String  isTip = "YES";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pull_qrc_manage;
    }

    @Override
    protected void initView() {

        userId = SPUtils.getInstance().getString("userId");
        DaggerStaffManageComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .staffManageModule(new StaffManageModule(this))
                .build()
                .inject(this);
        initOptionPickerOne();
        tvTitle.setText("拉新二维码推送消息");
    }

    @Override
    protected void initData() {

//        presenter.doPushLoad(this,userId);
        presenter.getPushType(this);

    }
    @OnClick({R.id.iv_back,R.id.rl_msg_one,R.id.rl_msg_two,R.id.rl_msg_three,R.id.rl_msg_one_content,R.id.rl_msg_two_content,R.id.rl_msg_three_content,R.id.btn_save,R.id.btn_pre})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.finishActivity(PullQrcManageActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.rl_msg_one:
                pvTypeOne.show();
                break;
            case R.id.rl_msg_two:
                pvTypeTwo.show();
                break;
            case R.id.rl_msg_three:
                pvTypeThree.show();
                break;

            case R.id.rl_msg_one_content:

                if("0".equals(msgOneType) ||"".equals(msgOneType)){
                    setDialog("请选择消息一类型");
                    return;
                }
                intent = new Intent(PullQrcManageActivity.this,PullQrcSelectContentActivity.class);
                if("1".equals(msgOneType)){
                    intent.putExtra("pushName","活动");
                }else  if("2".equals(msgOneType)){
                    intent.putExtra("pushName","拼团");
                }else  if("3".equals(msgOneType)){
                    intent.putExtra("pushName","领取会员卡");
                }else  if("4".equals(msgOneType)){
                    intent.putExtra("pushName","购买商品信息");
                }
                intent.putExtra("pushType",msgOneType);

                intent.putExtra("contentList",contentList);
                ActivityUtils.startActivityForResult(PullQrcManageActivity.this,intent,REQUEST_CODE_ONE,R.anim.slide_in_right,R.anim.slide_out_left);
                break;
            case R.id.rl_msg_two_content:
                if("0".equals(msgTwoType) ||"".equals(msgTwoType)){
                    setDialog("请选择消息二类型");
                    return;
                }
                intent = new Intent(PullQrcManageActivity.this,PullQrcSelectContentActivity.class);
                intent.putExtra("pushType",msgTwoType);
                if("1".equals(msgTwoType)){
                    intent.putExtra("pushName","活动");
                }else  if("2".equals(msgTwoType)){
                    intent.putExtra("pushName","拼团");
                }else  if("3".equals(msgTwoType)){
                    intent.putExtra("pushName","领取会员卡");
                }else  if("4".equals(msgTwoType)){
                    intent.putExtra("pushName","购买商品信息");
                }
                intent.putExtra("contentList",contentList);
                ActivityUtils.startActivityForResult(PullQrcManageActivity.this,intent,REQUEST_CODE_TWO,R.anim.slide_in_right,R.anim.slide_out_left);
                break;
            case R.id.rl_msg_three_content:
                if("0".equals(msgThreeType)||"".equals(msgThreeType) ){
                    setDialog("请选择消息三类型");
                    return;
                }
                intent = new Intent(PullQrcManageActivity.this,PullQrcSelectContentActivity.class);
                intent.putExtra("pushType",msgThreeType);
                if("1".equals(msgThreeType)){
                    intent.putExtra("pushName","活动");
                }else  if("2".equals(msgThreeType)){
                    intent.putExtra("pushName","拼团");
                }else  if("3".equals(msgThreeType)){
                    intent.putExtra("pushName","领取会员卡");
                }else  if("4".equals(msgThreeType)){
                    intent.putExtra("pushName","购买商品信息");
                }
                intent.putExtra("contentList",contentList);
                ActivityUtils.startActivityForResult(PullQrcManageActivity.this,intent,REQUEST_CODE_THREE,R.anim.slide_in_right,R.anim.slide_out_left);
                break;

            case R.id.btn_save:

                if(check()){
                    return;
                }
                StringBuilder typeSb = new StringBuilder();
                StringBuilder idSb = new StringBuilder();
                /*if(msgOneType != null && !"".equals(msgOneType) && !"0".equals(msgOneType)){
                    typeSb.append(msgOneType).append(",");
                }

                if(msgTwoType != null && !"".equals(msgTwoType)&& !"0".equals(msgTwoType)){
                    typeSb.append(msgTwoType).append(",");
                }

                if(msgThreeType != null && !"".equals(msgThreeType) && !"0".equals(msgThreeType)){
                    typeSb.append(msgThreeType).append(",");
                }*/



               /* if(msgOneContentId != null && !"".equals(msgOneContentId)){
                    idSb.append(msgOneContentId).append(",");
                }

                if(msgTwoContentId != null && !"".equals(msgTwoContentId)){
                    idSb.append(msgTwoContentId).append(",");
                }

                if(msgThreeContentId != null && !"".equals(msgThreeContentId)){
                    idSb.append(msgThreeContentId).append(",");
                }*/

                if(msgOneContentId != null && !"".equals(msgOneContentId)){
                    idSb.append(msgOneContentId).append(",");

                    if(msgOneType != null && !"".equals(msgOneType) && !"0".equals(msgOneType)){
                        typeSb.append(msgOneType).append(",");
                    }

                }

                if(msgTwoContentId != null && !"".equals(msgTwoContentId)){
                    idSb.append(msgTwoContentId).append(",");
                    if(msgTwoType != null && !"".equals(msgTwoType)&& !"0".equals(msgTwoType)){
                        typeSb.append(msgTwoType).append(",");
                    }
                }

                if(msgThreeContentId != null && !"".equals(msgThreeContentId)){
                    idSb.append(msgThreeContentId).append(",");
                    if(msgThreeType != null && !"".equals(msgThreeType) && !"0".equals(msgThreeType)){
                        typeSb.append(msgThreeType).append(",");
                    }
                }


                if(typeSb.toString() != null && !"".equals(typeSb.toString())){
                    pushTypes = typeSb.toString().substring(0, typeSb.toString().length() - 1);
                }

                if(idSb.toString() != null && !"".equals(idSb.toString())){
                    pushContentIds = idSb.toString().substring(0, idSb.toString().length() - 1);
                }
                if(pushContentIds == null || "".equals(pushContentIds)){
                    setDialog("至少选择一个推送内容");
                    return;
                }

                isTip = "YES";
                if (!ButtonUtils.isFastDoubleClick(R.id.btn_save_and_public)) {

                    presenter.doPushUpdate(this,userId,pushTypes,pushContentIds);

                }
                break;
            case R.id.btn_pre:

                presenter.doPushLoad(this,userId,false);

                break;
        }
    }
    private boolean check(){
        if("1".equals(msgOneType)){
            if(msgOneContentId == null || "".equals(msgOneContentId)){
                setDialog("消息一的推送内容未填写，请确认设置内容。");
                return true;
            }
        }
        if("2".equals(msgTwoType)){
            if(msgTwoContentId == null || "".equals(msgTwoContentId)){
                setDialog("消息二的推送内容未填写，请确认设置内容。");
                return true;
            }
        }
        if("3".equals(msgThreeType)){
            if(msgThreeContentId == null || "".equals(msgThreeContentId)){
                setDialog("消息三的推送内容未填写，请确认设置内容。");
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
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(PullQrcManageActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
    }


    protected void initOptionPickerOne() {//条件选择器初始化，自定义布局


        pvTypeOne = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                msgOneContent = cardQrcBeans.get(options1).getPickerViewText();
                msgOneType = cardQrcBeans.get(options1).getId();
                if("请选择".equals(msgOneContent)){
                    msgOneType = "";
                    msgTwoContentId = "";
                    tv_msg_one_content.setText("无");
                }


                tv_msg_one.setText(msgOneContent);
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
                                pvTypeOne.returnData();
                                pvTypeOne.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTypeOne.dismiss();
                            }
                        });


                    }
                })
                .build();


//        pvTypeOne.setPicker(cardQrcBeans);

        pvTypeTwo = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                msgTwoContent = cardQrcBeans.get(options1).getPickerViewText();
                msgTwoType = cardQrcBeans.get(options1).getId();
                if("请选择".equals(msgTwoContent)){
                    msgTwoType = "";
                    tv_msg_two_content.setText("无");
                    msgTwoContentId = "";
                }

                tv_msg_two.setText(msgTwoContent);
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
                                pvTypeTwo.returnData();
                                pvTypeTwo.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTypeTwo.dismiss();
                            }
                        });


                    }
                })
                .build();


//        pvTypeTwo.setPicker(cardQrcBeans);

        pvTypeThree = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                msgThreeContent = cardQrcBeans.get(options1).getPickerViewText();
                msgThreeType = cardQrcBeans.get(options1).getId();
                if("请选择".equals(msgThreeContent)){
                    msgThreeType = "";
                    tv_msg_three_content.setText("无");
                    msgThreeContentId = "";
                }
                tv_msg_three.setText(msgThreeContent);
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
                                pvTypeThree.returnData();
                                pvTypeThree.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTypeThree.dismiss();
                            }
                        });


                    }
                })
                .build();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 7777) {
            ArrayList<Map<String,String>> selectList = new ArrayList<>();
            if(data != null){
                selectList = (ArrayList<Map<String,String>>)data.getSerializableExtra("selectContentList");

            }
            if(requestCode == REQUEST_CODE_ONE){
                if(selectList != null && selectList.size()>0){
                    Map<String,String> map = selectList.get(0);

                    msgOneContent = (String)map.get("name");
                    msgOneContentId = String.valueOf(map.get("id"));
                    if(msgOneContent != null && !"".equals(msgOneContent)){
                        tv_msg_one_content.setText(msgOneContent);
                    }
                }
            }else if(requestCode == REQUEST_CODE_TWO){
                if(selectList != null && selectList.size()>0){
                    Map<String,String> map = selectList.get(0);

                    msgTwoContent = (String)map.get("name");
                    msgTwoContentId = String.valueOf(map.get("id"));
                    if(msgTwoContent != null && !"".equals(msgTwoContent)){
                        tv_msg_two_content.setText(msgTwoContent);
                    }
                }
            }else if(requestCode == REQUEST_CODE_THREE){
                if(selectList != null && selectList.size()>0){
                    Map<String,String> map = selectList.get(0);

                    msgThreeContent = (String)map.get("name");
                    msgThreeContentId = String.valueOf(map.get("id"));
                    if(msgThreeContent != null && !"".equals(msgThreeContent)){
                        tv_msg_three_content.setText(msgThreeContent);
                        SPUtils.getInstance().put("",msgThreeContent);
                    }
                }

            }

            StringBuilder typeSb = new StringBuilder();
            StringBuilder idSb = new StringBuilder();


            if(msgOneContentId != null && !"".equals(msgOneContentId)){
                idSb.append(msgOneContentId).append(",");

                if(msgOneType != null && !"".equals(msgOneType) && !"0".equals(msgOneType)){
                    typeSb.append(msgOneType).append(",");
                }

            }

            if(msgTwoContentId != null && !"".equals(msgTwoContentId)){
                idSb.append(msgTwoContentId).append(",");
                if(msgTwoType != null && !"".equals(msgTwoType)&& !"0".equals(msgTwoType)){
                    typeSb.append(msgTwoType).append(",");
                }
            }

            if(msgThreeContentId != null && !"".equals(msgThreeContentId)){
                idSb.append(msgThreeContentId).append(",");
                if(msgThreeType != null && !"".equals(msgThreeType) && !"0".equals(msgThreeType)){
                    typeSb.append(msgThreeType).append(",");
                }
            }

            /*if(msgOneType != null && !"".equals(msgOneType) && !"0".equals(msgOneType)){
                typeSb.append(msgOneType).append(",");
            }

            if(msgTwoType != null && !"".equals(msgTwoType)&& !"0".equals(msgTwoType)){
                typeSb.append(msgTwoType).append(",");
            }

            if(msgThreeType != null && !"".equals(msgThreeType) && !"0".equals(msgThreeType)){
                typeSb.append(msgThreeType).append(",");
            }


            if(msgOneContentId != null && !"".equals(msgOneContentId)){
                idSb.append(msgOneContentId).append(",");
            }

            if(msgTwoContentId != null && !"".equals(msgTwoContentId)){
                idSb.append(msgTwoContentId).append(",");
            }

            if(msgThreeContentId != null && !"".equals(msgThreeContentId)){
                idSb.append(msgThreeContentId).append(",");
            }

            if(idSb.toString() != null && !"".equals(idSb.toString())){
                pushContentIds = idSb.toString().substring(0, idSb.toString().length() - 1);
            }*/


            if(typeSb.toString() != null && !"".equals(typeSb.toString())){
                pushTypes = typeSb.toString().substring(0, typeSb.toString().length() - 1);
            }
            if(idSb.toString() != null && !"".equals(idSb.toString())){
                pushContentIds = idSb.toString().substring(0, idSb.toString().length() - 1);
            }
            isTip = "NO";
            presenter.doPushUpdate(this,userId,pushTypes,pushContentIds);
        }
    }



    protected void setPopDialog(){
        View customView = View.inflate(this, R.layout.layout_push_message_template, null);
        PopWindow popAlertView = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopAlert)
                .setView(customView)
                .show();
        Button btn_use = (Button)customView.findViewById(R.id.btn_use);
        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAlertView.dismiss();
            }
        });




        LinearLayout ll_dis_top = (LinearLayout)customView.findViewById(R.id.ll_dis_top);
        ll_dis_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAlertView.dismiss();
            }
        });

        LinearLayout ll_temlplate = (LinearLayout)customView.findViewById(R.id.ll_temlplate);

        ll_temlplate.removeAllViews();
        if(pushPreMessageList != null && pushPreMessageList.size()>0){
            for (PushPreMessageBean pushPreMessageBean : pushPreMessageList) {
                String pushType = pushPreMessageBean.getPushType();
                String date = new SimpleDateFormat("yyyy年MM月dd日 ahh:mm").format(new Date());
                String pushTime  =  new SimpleDateFormat("MM月dd日").format(new Date());

                title = pushPreMessageBean.getTitle();
                first = pushPreMessageBean.getFirst();
                keyword1_title = pushPreMessageBean.getKeyword1_title();
                keyword1 = pushPreMessageBean.getKeyword1();
                keyword2_title = pushPreMessageBean.getKeyword2_title();
                keyword2 = pushPreMessageBean.getKeyword2();

                campcoupId = pushPreMessageBean.getCampcoupId();
                keyword3_title = pushPreMessageBean.getKeyword3_title();
                keyword3 = pushPreMessageBean.getKeyword3();

                keyword4_title = pushPreMessageBean.getKeyword4_title();
                keyword4 = pushPreMessageBean.getKeyword4();
                remark = pushPreMessageBean.getRemark();
                View view = null;

                if("1".equals(pushType)){
                    view = LayoutInflater.from(this).inflate(R.layout.layout_push_msg_type2, null);
                    TextView tv_pt_time = (TextView) view.findViewById(R.id.tv_pt_time);
                    TextView tv_push_time = (TextView) view.findViewById(R.id.tv_push_time);
                    TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
                    TextView tv_first = (TextView) view.findViewById(R.id.tv_first);
                    TextView tv_campaign_name_label = (TextView) view.findViewById(R.id.tv_campaign_name_label);
                    TextView tv_campaign_name = (TextView) view.findViewById(R.id.tv_campaign_name);
                    TextView tv_campaign_time_label = (TextView) view.findViewById(R.id.tv_campaign_time_label);
                    TextView tv_campaign_time = (TextView) view.findViewById(R.id.tv_campaign_time);
                    TextView tv_campaign_address_label = (TextView) view.findViewById(R.id.tv_campaign_address_label);
                    TextView tv_campaign_address = (TextView) view.findViewById(R.id.tv_campaign_address);
                    TextView tv_member_name = (TextView) view.findViewById(R.id.tv_member_name);
                    TextView tv_push_desc = (TextView) view.findViewById(R.id.tv_push_desc);
                    tv_push_time.setText(pushTime);
                    tv_pt_time.setText(date);
                    tv_title.setText(title != null && !"".equals(title)?title:"");
                    tv_first.setText(first != null && !"".equals(first)?first:"");
                    tv_campaign_name_label.setText(keyword1_title != null  && !"".equals(keyword1_title)?keyword1_title:"");
                    tv_campaign_name.setText(keyword1 != null  && !"".equals(keyword1)?keyword1:"");
                    tv_campaign_time_label.setText(keyword2_title != null  && !"".equals(keyword2_title)?keyword2_title:"");
                    tv_campaign_time.setText(keyword2 != null  && !"".equals(keyword2)?keyword2:"");
                    tv_campaign_address_label.setText(keyword3_title != null  && !"".equals(keyword3_title)?keyword3_title:"");
                    tv_campaign_address.setText(keyword3 != null  && !"".equals(keyword3)?keyword3:"");
                    tv_push_desc.setText(remark != null  && !"".equals(remark)?remark:"");
                    ll_temlplate.addView(view);
                }else if("2".equals(pushType)){
                    view = LayoutInflater.from(this).inflate(R.layout.layout_push_msg_type2, null);
                    TextView tv_pt_time = (TextView) view.findViewById(R.id.tv_pt_time);
                    TextView tv_push_time = (TextView) view.findViewById(R.id.tv_push_time);
                    TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
                    TextView tv_first = (TextView) view.findViewById(R.id.tv_first);
                    TextView tv_campaign_name_label = (TextView) view.findViewById(R.id.tv_campaign_name_label);
                    TextView tv_campaign_name = (TextView) view.findViewById(R.id.tv_campaign_name);
                    TextView tv_campaign_time_label = (TextView) view.findViewById(R.id.tv_campaign_time_label);
                    TextView tv_campaign_time = (TextView) view.findViewById(R.id.tv_campaign_time);
                    TextView tv_campaign_address_label = (TextView) view.findViewById(R.id.tv_campaign_address_label);
                    TextView tv_campaign_address = (TextView) view.findViewById(R.id.tv_campaign_address);
                    TextView tv_member_name = (TextView) view.findViewById(R.id.tv_member_name);
                    TextView tv_push_desc = (TextView) view.findViewById(R.id.tv_push_desc);
                    tv_push_time.setText(pushTime);
                    tv_pt_time.setText(date);
                    tv_title.setText(title != null && !"".equals(title)?title:"");
                    tv_first.setText(first != null && !"".equals(first)?first:"");
                    tv_campaign_name_label.setText(keyword1_title != null  && !"".equals(keyword1_title)?keyword1_title:"");
                    tv_campaign_name.setText(keyword1 != null  && !"".equals(keyword1)?keyword1:"");
                    tv_campaign_time_label.setText(keyword2_title != null  && !"".equals(keyword2_title)?keyword2_title:"");
                    tv_campaign_time.setText(keyword2 != null  && !"".equals(keyword2)?keyword2:"");
                    tv_campaign_address_label.setText(keyword3_title != null  && !"".equals(keyword3_title)?keyword3_title:"");
                    tv_campaign_address.setText(keyword3 != null  && !"".equals(keyword3)?keyword3:"");
                    tv_push_desc.setText(remark != null  && !"".equals(remark)?remark:"");
                    ll_temlplate.addView(view);
                }else if("3".equals(pushType)){
                    view = LayoutInflater.from(this).inflate(R.layout.layout_push_msg_type3, null);
                    TextView tv_pt_time3 = (TextView) view.findViewById(R.id.tv_pt_time3);
                    TextView tv_title3 = (TextView) view.findViewById(R.id.tv_title3);

                    TextView tv_first3 = (TextView) view.findViewById(R.id.tv_first3);
                    TextView tv_push_time3 = (TextView) view.findViewById(R.id.tv_push_time3);
                    TextView tv_member_tequan_label = (TextView) view.findViewById(R.id.tv_member_tequan_label);
                    TextView tv_memebr_tequan = (TextView) view.findViewById(R.id.tv_memebr_tequan);

                    TextView tv_member_card_label = (TextView) view.findViewById(R.id.tv_member_card_label);
                    TextView tv_member_card = (TextView) view.findViewById(R.id.tv_member_card);
                    TextView tv_consume_time_label = (TextView) view.findViewById(R.id.tv_consume_time_label);
                    TextView tv_consume_time = (TextView) view.findViewById(R.id.tv_consume_time);
                    TextView tv_push_desc3 = (TextView) view.findViewById(R.id.tv_push_desc3);
                    tv_pt_time3.setText(date);
                    tv_push_time3.setText(pushTime);
                    tv_title3.setText(title != null && !"".equals(title)?title:"");
                    tv_first3.setText(first!= null && !"".equals(first)?first:"");
                    tv_member_tequan_label.setText(keyword1_title != null  && !"".equals(keyword1_title)?keyword1_title:"");
                    tv_memebr_tequan.setText(keyword1 != null  && !"".equals(keyword1)?keyword1:"");
                    tv_member_card_label.setText(keyword2_title != null  && !"".equals(keyword2_title)?keyword2_title:"");
                    tv_member_card.setText(keyword2 != null  && !"".equals(keyword2)?keyword2:"");
                    tv_consume_time_label.setText(keyword3_title != null  && !"".equals(keyword3_title)?keyword3_title:"");
                    tv_consume_time.setText("-");
                    tv_push_desc3.setText(remark != null  && !"".equals(remark)?remark:"");
                    ll_temlplate.addView(view);
                }else if("4".equals(pushType)){
                    view = LayoutInflater.from(this).inflate(R.layout.layout_push_msg_type3, null);
                    TextView tv_pt_time3 = (TextView) view.findViewById(R.id.tv_pt_time3);
                    TextView tv_title3 = (TextView) view.findViewById(R.id.tv_title3);
                    TextView tv_first3 = (TextView) view.findViewById(R.id.tv_first3);
                    TextView tv_push_time3 = (TextView) view.findViewById(R.id.tv_push_time3);
                    TextView tv_member_tequan_label = (TextView) view.findViewById(R.id.tv_member_tequan_label);
                    TextView tv_memebr_tequan = (TextView) view.findViewById(R.id.tv_memebr_tequan);

                    TextView tv_member_card_label = (TextView) view.findViewById(R.id.tv_member_card_label);
                    TextView tv_member_card = (TextView) view.findViewById(R.id.tv_member_card);
                    TextView tv_consume_time_label = (TextView) view.findViewById(R.id.tv_consume_time_label);
                    TextView tv_consume_time = (TextView) view.findViewById(R.id.tv_consume_time);
                    TextView tv_push_desc3 = (TextView) view.findViewById(R.id.tv_push_desc3);
                    tv_pt_time3.setText(date);
                    tv_push_time3.setText(pushTime);
                    tv_title3.setText(title != null && !"".equals(title)?title:"");
                    tv_first3.setText(first!= null && !"".equals(first)?first:"");
                    tv_member_tequan_label.setText(keyword1_title != null  && !"".equals(keyword1_title)?keyword1_title:"");
                    tv_memebr_tequan.setText(keyword1 != null  && !"".equals(keyword1)?keyword1:"");
                    tv_member_card_label.setText(keyword2_title != null  && !"".equals(keyword2_title)?keyword2_title:"");
                    tv_member_card.setText(keyword2 != null  && !"".equals(keyword2)?keyword2:"");
                    tv_consume_time_label.setText(keyword3_title != null  && !"".equals(keyword3_title)?keyword3_title:"");
                    tv_consume_time.setText(keyword3);
                    tv_push_desc3.setText(remark != null  && !"".equals(remark)?remark:"");
                    ll_temlplate.addView(view);
                }


            }
        }



    }


}
