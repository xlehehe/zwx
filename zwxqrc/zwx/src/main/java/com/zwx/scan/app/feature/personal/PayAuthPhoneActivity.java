package com.zwx.scan.app.feature.personal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.base.adapter.SelectConstantListViewAdapter;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.member.CommonConstantBean;
import com.zwx.scan.app.feature.shop.ShopManageNewActivity;
import com.zwx.scan.app.widget.VerificationCountDownTimer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/05/14
 * desc   :  支付认证 -> 变更手机号
 * version: 1.0
 **/
public class PayAuthPhoneActivity extends BaseActivity<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener {
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;


    @BindView(R.id.tv_name)
    protected TextView tv_name;

    @BindView(R.id.edt_phone)
    protected EditText edt_phone;

    @BindView(R.id.btn_update)
    protected Button btn_update;

    private PopWindow popWindow = null;

    protected String phone = "";

    protected String name = "";
    protected String userId;
    //status N 下 process  ：N 跳选择认证界面   CI  跳企业认证，CP 跳企业绑定手机，CS  跳企业签订协议，PI  跳个人认证第一步信息，PP 跳个人绑定手机 ，PB跳个人绑定银行卡，PS  跳企业签订协议
    protected String process;
    protected String status;
    TextView tv_code_tip = null;



    protected VerificationCountDownTimer verificationCountDownTimer = null;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_auth_phone;
    }

    @Override
    protected void initView() {

        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);
        tvTitle.setText("手机号");


        initCountDownTimer();
    }

    @Override
    protected void initData() {

        userId = SPUtils.getInstance().getString("userId");
        process = getIntent().getStringExtra("process");
        status = getIntent().getStringExtra("status");
        presenter.selectPersonalOrCompanyAuthInfo(this,userId,"UpdatePhone","");
    }



    @OnClick({R.id.iv_back,R.id.btn_update})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(PayAuthPhoneActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case R.id.btn_update:
                showPop();
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(PayAuthPhoneActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
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
//                tv_code_tip.setEnabled(false);
                tv_code_tip.setText((millisUntilFinished/1000)+"s后重发");
            }

            @Override
            public void onFinish() {

                tv_code_tip.setEnabled(true);
                tv_code_tip.setText(getString(R.string.get_verification_code));

                if(countDownTimer !=180000){
                    setCountDownTimer(180000);
                    presenter.sendVerificationCodeByUnBindPhone(PayAuthPhoneActivity.this,userId,phone,status,"");
                }
            }


        };
    }

    private void showPop(){
        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_virification_code_dialog,null);

        EditText edit_code1 = (EditText)contentView.findViewById(R.id.edt_code1);
        EditText edit_code2 = (EditText)contentView.findViewById(R.id.edt_code2);
        EditText edit_code3 = (EditText)contentView.findViewById(R.id.edt_code3);
        EditText edit_code4 = (EditText)contentView.findViewById(R.id.edt_code4);
        EditText edit_code5= (EditText)contentView.findViewById(R.id.edt_code5);
        EditText edit_code6 = (EditText)contentView.findViewById(R.id.edt_code6);
        Button btn_submit = (Button)contentView.findViewById(R.id.btn_submit);
         tv_code_tip = (TextView)contentView.findViewById(R.id.tv_code_tip);

        presenter.sendVerificationCodeByUnBindPhone(PayAuthPhoneActivity.this,userId,phone,status,"");
        verificationCountDownTimer.timerStart(true);
        //创建并显示popWindow
        popWindow = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .setView(contentView)
                .show();


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code1 = edit_code1.getText().toString().trim();
                String code2 = edit_code2.getText().toString().trim();
                String code3 = edit_code3.getText().toString().trim();
                String code4 = edit_code4.getText().toString().trim();

                String code5 = edit_code5.getText().toString().trim();
                String code6 = edit_code6.getText().toString().trim();

                String code = code1+code2+code3+code4+code5+code6;
               /* if(code == null ||"".equals(code)){
                    ToastUtils.showShort("请输入验证码");
                    return;
                }*/

                if(code.length()<6){
                    ToastUtils.showShort("请输入六位长度的验证码");
                    return;
                }

                presenter.unBindPhone(PayAuthPhoneActivity.this,userId,phone,code,status,"");


            }
        });






    }



}
