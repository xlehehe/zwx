package com.zwx.scan.app.feature.personal;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.main.MainActivity;
import com.zwx.scan.app.widget.VerificationCountDownTimer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/05/13
 * desc   : 交易提款 个人认证第二步
 * version: 1.0
 **/
public class TradeDrawingPersonalAuthTwoActivity extends BaseActivity<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener {

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;


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
    @BindView(R.id.ll_step)
    protected LinearLayout ll_step;

    @BindView(R.id.edt_name)
    protected TextView edt_name;

    @BindView(R.id.edt_phone)
    protected TextView edt_phone;
    @BindView(R.id.edt_code)
    protected TextView edt_code;

    @BindView(R.id.btn_code)
    protected Button btn_code;
    @BindView(R.id.btn_next)
    protected Button btn_next;

    @BindView(R.id.ll_three)
    protected LinearLayout ll_three;


    protected  String name;
    protected String code;
    protected static String phone;

    protected String dialogTitle;

    private String userId;
    protected VerificationCountDownTimer verificationCountDownTimer = null;

    protected Intent intent = null;

    //status N 下 process  ：N 跳选择认证界面   CI  跳企业认证，CP 跳企业绑定手机，CS  跳企业签订协议，PI  跳个人认证第一步信息，PP 跳个人绑定手机 ，PB跳个人绑定银行卡，PS  跳企业签订协议
    protected String process;
    protected String status;
    protected String type ; // 0 表示个人认证信息 1 表示企业认证信息
    @Override
    protected int getLayoutId() {
        return R.layout.activity_trade_drawing_personal_auth_two;
    }

    @Override
    protected void initView() {
        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);
        tvTitle.setText("认证");
        setSetTep();
        initCountDownTimer();
    }

    @Override
    protected void initData() {
        userId = SPUtils.getInstance().getString("userId");
        process = getIntent().getStringExtra("process");
        status = getIntent().getStringExtra("status");
        if("N".equals(status)){ //未认证
            ll_step.setVisibility(View.VISIBLE);

            if("PP".equals(process)){  //跳绑定手机号页面标志   //个人认证绑定手机号

                ll_three.setVisibility(View.VISIBLE);
                ivEllipsisTwo.setVisibility(View.VISIBLE);
                presenter.selectPersonalOrCompanyAuthInfo(this,userId,status,process);

            }else if("CP".equals(process)){   //企业认证直跳绑定手机号

                ll_three.setVisibility(View.GONE);

                ivEllipsisTwo.setVisibility(View.GONE);
                presenter.selectPersonalOrCompanyAuthInfo(this,userId,status,process);
            }

        }else if("A".equals(status)||"NA".equals(status)){ //已认证列表页面跳转进来
            ll_step.setVisibility(View.GONE);
            tvTitle.setText("绑定手机号");
            btn_next.setText("完成");
            presenter.selectPersonalOrCompanyAuthInfo(this,userId,"BindPhone",process);
        }

    }


    private void setSetTep(){

        ivOne.setBackgroundResource(R.drawable.ic_first_clicked);
        ivEllipsisOne.setBackgroundResource(R.drawable.ic_ellipsis_blue);
        ivTwo.setBackgroundResource(R.drawable.ic_two_clicked);
        ivEllipsisTwo.setBackgroundResource(R.drawable.ic_ellipsis_gray);
        ivThree.setBackgroundResource(R.drawable.ic_three_untclick);

    }

    @OnClick({R.id.iv_back,R.id.btn_next,R.id.btn_code})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:

                if("A".equals(status)||"NA".equals(status)||"BindPhone".equals(status)){  //返回已认证/未完成页面
                    intent = new Intent(TradeDrawingPersonalAuthTwoActivity.this,PayAuthComplateActivity.class);
                }else {
                    intent = new Intent(TradeDrawingPersonalAuthTwoActivity.this,MainActivity.class);
                    intent.putExtra("isIntentFragment","PersonalFragment");
                }

                ActivityUtils.startActivity(intent,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                ActivityUtils.finishActivity(TradeDrawingPersonalAuthTwoActivity.class);
//                ActivityUtils.finishActivity(TradeDrawingPersonalAuthTwoActivity.class,
//                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.btn_next:

                name = edt_name.getText().toString().trim();
                phone = edt_phone.getText().toString().trim();
                code = edt_code.getText().toString().trim();

                if(name == null || "".equals(name)){

                    setDialog("请输入负责人姓名");
                    dialogTitle = "提示";
                    return;
                }

                if(phone == null || "".equals(phone)){
                    setDialog("请输入手机号");
                    dialogTitle = "提示";
                    return;
                }

                if(!RegexUtils.isMobileSimple(phone)){
                    setDialog("请输入正确手机号");
                    dialogTitle = "提示";
                    return;
                }


                if(code == null || "".equals(code)){
                    setDialog("请输入验证码");
                    dialogTitle = "提示";
                    return;
                }

                presenter.bindPhone(this,userId,name, phone,code,status,process);

                break;
            case R.id.btn_code:
                phone = edt_phone.getText().toString().trim();
                if(phone == null || "".equals(phone)){
                    setDialog("请输入手机号");
                    dialogTitle = "提示";
                    return;
                }

                if(!RegexUtils.isMobileSimple(phone)){
                    setDialog("请输入正确手机号");
                    dialogTitle = "提示";
                    return;
                }

                btn_code.setEnabled(false);
                presenter.sendVerificationCodeByBindPhone(this,userId,phone,status,process);
//                ActivityUtils.startActivity(TradeDrawingPersonalAuthThreeActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);

                //默认为true  直接从180秒倒计时，防止其他地方调用时默认不是从180秒倒计时
                VerificationCountDownTimer.FLAG_FIRST_IN = true;
                verificationCountDownTimer.timerStart(true);



                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if("A".equals(status)||"NA".equals(status)||"BindPhone".equals(status)){  //返回已认证/未完成页面
            intent = new Intent(TradeDrawingPersonalAuthTwoActivity.this,PayAuthComplateActivity.class);
        }else {
            intent = new Intent(TradeDrawingPersonalAuthTwoActivity.this,MainActivity.class);
            intent.putExtra("isIntentFragment","PersonalFragment");
        }
        ActivityUtils.startActivity(intent,
                R.anim.slide_in_left, R.anim.slide_out_right);
        ActivityUtils.finishActivity(TradeDrawingPersonalAuthTwoActivity.class);

    }
    public void initCountDownTimer(){
        if(!VerificationCountDownTimer.FLAG_FIRST_IN && VerificationCountDownTimer.curMillis+180000>System.currentTimeMillis()){


            setCountDownTimer(VerificationCountDownTimer.curMillis+180000-System.currentTimeMillis());
        }else {
            setCountDownTimer(180000);
        }
    }


    public void setCountDownTimer(final long countDownTimer){
        verificationCountDownTimer =  new VerificationCountDownTimer(countDownTimer,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                btn_code.setEnabled(false);
                btn_code.setText((millisUntilFinished/1000)+"s后重发");
            }

            @Override
            public void onFinish() {

                btn_code.setEnabled(true);
                btn_code.setText(getString(R.string.get_verification_code));

                if(countDownTimer !=180000){
                    setCountDownTimer(180000);
//                    presenter.sendVerificationCodeByBindPhone(TradeDrawingPersonalAuthTwoActivity.this,userId,phone,status,process);
                }
            }


        };
    }



    public void setDialog(String message){

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView title = (TextView)rootView.findViewById(R.id.title);
        title.setText(dialogTitle);
        TextView textView = (TextView)rootView.findViewById(R.id.message);
        textView.setText(message);
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
}
