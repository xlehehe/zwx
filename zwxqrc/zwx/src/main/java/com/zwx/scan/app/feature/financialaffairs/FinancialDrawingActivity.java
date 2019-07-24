package com.zwx.scan.app.feature.financialaffairs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.NetworkUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.widget.VerificationCountDownTimer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/05/09
 * desc   :  立即提现
 * version: 1.0
 **/
public class FinancialDrawingActivity extends BaseActivity<FinancialAffairsContract.Presenter> implements FinancialAffairsContract.View,View.OnClickListener {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;


    @BindView(R.id.edt_draw_amt)
    protected EditText edt_draw_amt;

    @BindView(R.id.btn_all_draw)
    protected Button btn_all_draw;
    @BindView(R.id.tv_fee)
    protected TextView tv_fee;

    @BindView(R.id.tv_daozhang_amt)
    protected TextView tv_daozhang_amt;

    @BindView(R.id.tv_account_name)
    protected TextView tv_account_name;

    @BindView(R.id.tv_account)
    protected TextView tv_account;

    @BindView(R.id.tv_operate_person)
    protected TextView tv_operate_person;

    @BindView(R.id.tv_operate_tel)
    protected TextView tv_operate_tel;

    @BindView(R.id.tv_fuze_person)
    protected TextView tv_fuze_person;
    @BindView(R.id.tv_fuze_phone)
    protected TextView tv_fuze_phone;


    @BindView(R.id.btn_code)
    protected Button btn_code;

    @BindView(R.id.edt_code)
    protected EditText edt_code;


    protected  String userId;
    protected String amount;
    protected String fee = "0.00";
    protected String toAmt = "0";  //到账金额

    protected String bankName;
    protected String bankNo;
    protected String nickname;  //操作人
    protected String username;  //操作电话

    protected String managerTel;  //负责人电话
    protected String managerTName;//负责人


    protected String consumerIp; //用户公网IP
    protected String bizOrderNo;
    protected String validateType;

    protected String bankCardPro;//账户类型: 0个人银行卡 ,1企业对公账户

    protected String phone;
    protected VerificationCountDownTimer verificationCountDownTimer = null;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_financial_drawing;
    }

    @Override
    protected void initView() {
        DaggerFinancialAffairsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .financialAffairsModule(new FinancialAffairsModule(this))
                .build()
                .inject(this);
        tvTitle.setText("立即提现");

        initCountDownTimer();


    }

    @Override
    protected void initData() {
        consumerIp = NetworkUtils.getIPAddress(true);
        userId = SPUtils.getInstance().getString("userId");
        presenter.selectImmediateWithdrawal(this,userId);



        edt_draw_amt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fee = tv_fee.getText().toString().trim();
                if(fee == ""||"".equals(fee)|| fee == null){
                    fee = "0.00";
                }
                tv_fee.setText(fee);
                amount = edt_draw_amt.getText().toString().trim();
                if(amount == null || "".equals(amount)){
                    amount ="0";
                    Double am  = Double.parseDouble(amount);
                    if(am<=0){
                        setDialog("可提现金额不能小于零");
                        return;
                    }
                    if(fee != null &&  !"".equals(fee)){
                        Double fe = Double.parseDouble(fee);
                        if(am<fe){
                            setDialog("可提现金额不能少于手续费");
                            return;
                        }
                        Double toAm = am.doubleValue() - fe.doubleValue();
                        toAmt = RegexUtils.getDoubleString(toAm);
                        tv_daozhang_amt.setText(toAmt);
                    }
                }else {
                    Double am  = Double.parseDouble(amount);

                    if(fee != null &&  !"".equals(fee)){
                        Double fe = Double.parseDouble(fee);
                        if(am<fe){
                            setDialog("可提现金额不能少于手续费");
                            return;
                        }
                        Double toAm = am.doubleValue() - fe.doubleValue();
                        toAmt = RegexUtils.getDoubleString(toAm);

                        tv_daozhang_amt.setText(toAmt);
                    }

                }
            }
        });
    }

    @OnClick({R.id.iv_back,R.id.btn_all_draw,R.id.btn_drawing,R.id.btn_code,R.id.btn_cancel})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(FinancialDrawingActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.btn_all_draw:

                fee = tv_fee.getText().toString().trim();
                amount = edt_draw_amt.getText().toString().trim();
                validateType =  edt_code.getText().toString().trim();
                if(amount == null || "".equals(amount)){
                    setDialog("请输入可提现金额");
                    return;
                }else {
                    Double am  = Double.parseDouble(amount);
                    if(am<=0){
                        setDialog("可提现金额不能小于零");
                        return;
                    }
                    if(fee != null &&  !"".equals(fee)){
                        Double fe = Double.parseDouble(fee);
                        if(am<fe){
                            setDialog("可提现金额不能少于手续费");
                            return;
                        }
                        Double toAm = am.doubleValue() - fe.doubleValue();
                        toAmt = String.valueOf(toAm);
                    }

                }

                if(validateType == null || "".equals(validateType)){
                    setDialog("请输入验证码");
                    return;
                }

                if(validateType.length()<6){
                    setDialog("验证码长度为6位");
                    return;
                }
                if(consumerIp == null || "".equals(consumerIp)){
                    setDialog("用户公网IP为空");
                    return;
                }

                if(bizOrderNo == null || "".equals(bizOrderNo)){
                    setDialog("商户订单号为空");
                    return;
                }

                presenter.immediateWithdrawal(this,userId,bizOrderNo,validateType,consumerIp);
                break;
            case R.id.btn_drawing:

                fee = tv_fee.getText().toString().trim();
                amount = edt_draw_amt.getText().toString().trim();
                validateType =  edt_code.getText().toString().trim();
                if(amount == null || "".equals(amount)){
                    setDialog("请输入可提现金额");
                    return;
                }else {
                    Double am  = Double.parseDouble(amount);
                    if(am<=0){
                        setDialog("可提现金额不能小于零");
                        return;
                    }
                    if(fee != null &&  !"".equals(fee)){
                        Double fe = Double.parseDouble(fee);
                        if(am<fe){
                            setDialog("可提现金额不能少于手续费");
                            return;
                        }
                        Double toAm = am.doubleValue() - fe.doubleValue();
                         toAmt = String.valueOf(toAm);
                    }

                }

                if(validateType == null || "".equals(validateType)){
                    setDialog("请输入验证码");
                    return;
                }

                if(validateType.length()<6){
                    setDialog("验证码长度为6位");
                    return;
                }
                if(consumerIp == null || "".equals(consumerIp)){
                    setDialog("用户公网IP为空");
                    return;
                }

                presenter.immediateWithdrawal(this,userId,bizOrderNo,validateType,consumerIp);


                break;

            case R.id.btn_cancel:


                ActivityUtils.finishActivity(FinancialDrawingActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case R.id.btn_code:
                phone = tv_fuze_phone.getText().toString().trim();
                amount = edt_draw_amt.getText().toString().trim();
                bankNo = tv_account.getText().toString().trim();
                fee = tv_fee.getText().toString().trim();

                if(amount == null || "".equals(amount)){
                    setDialog("请输入可提现金额");
                    return;
                }else {
                    Double am  = Double.parseDouble(amount);
                    if(am<=0){
                        setDialog("可提现金额不能小于零");
                        return;
                    }
                    if(fee != null &&  !"".equals(fee)){
                        Double fe = Double.parseDouble(fee);
                        if(am<fe){
                            setDialog("可提现金额不能少于手续费");
                            return;
                        }
                        Double toAm = am.doubleValue() - fe.doubleValue();
                        toAmt = String.valueOf(toAm);
                    }

                }

                if(phone == null || "".equals(phone)){
                    setDialog("负责人手机号为空");
                    return;
                }


                if(amount == null || "".equals(amount)){
                    setDialog("可提现金额为空");
                    return;
                }
                if(fee == null || "".equals(fee)){
                    setDialog("手续费为空");
                    return;
                }
                if(bankNo == null || "".equals(bankNo)){
                    setDialog("银行卡号为空");
                    return;
                }
                if(bankCardPro == null || "".equals(bankCardPro)){
                    setDialog("账户类型为空");
                    return;
                }
                presenter.sendVerificationCodeByImmediateWithdrawal(this,userId,amount,fee,bankNo,bankCardPro);
                VerificationCountDownTimer.FLAG_FIRST_IN = true;
                verificationCountDownTimer.timerStart(true);
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(FinancialDrawingActivity.class,
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
                btn_code.setEnabled(false);
                btn_code.setText((millisUntilFinished/1000)+"s后重发");
            }

            @Override
            public void onFinish() {

                btn_code.setEnabled(true);
                btn_code.setText(getString(R.string.get_verification_code));

                if(countDownTimer !=180000){
                    setCountDownTimer(180000);
                }
            }


        };
    }

    public void setDialog(String message){

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
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
