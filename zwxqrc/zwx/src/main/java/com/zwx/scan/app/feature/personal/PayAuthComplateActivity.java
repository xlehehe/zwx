package com.zwx.scan.app.feature.personal;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.financialaffairs.DaggerFinancialAffairsComponent;
import com.zwx.scan.app.feature.financialaffairs.FinancialAffairsContract;
import com.zwx.scan.app.feature.financialaffairs.FinancialAffairsModule;
import com.zwx.scan.app.feature.main.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/05/14
 * desc   :  支付认证完成跳转该完成页面
 * version: 1.0
 **/
public class PayAuthComplateActivity extends BaseActivity<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;


    @BindView(R.id.tv_auth_type)
    protected TextView tv_auth_type;
    @BindView(R.id.tv_phone_status)
    protected TextView tv_phone_status;
    @BindView(R.id.tv_bank_status)
    protected TextView tv_bank_status;

    protected String userId;
    protected String managerTel;
    protected String cardNo;
    protected String type;

    protected Intent intent = null;
    protected String status;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_auth_complate;
    }

    @Override
    protected void initView() {
        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);
        tvTitle.setText("支付认证");


    }

    @Override
    protected void initData() {

        userId = SPUtils.getInstance().getString("userId");
        status = getIntent().getStringExtra("status");
        //查询是否已绑定以及认证类型(个人认证、企业认证)
        presenter.selectPaymentAuthInfo(this,userId);
    }


    @OnClick({R.id.iv_back,R.id.ll_bank,R.id.ll_phone,R.id.ll_auth_type})
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_back:
                intent = new Intent(PayAuthComplateActivity.this,MainActivity.class);
                intent.putExtra("isIntentFragment","PersonalFragment");
                ActivityUtils.startActivity(intent,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.ll_auth_type:

                if("0".equals(type)){ //个人认证页面
                    intent = new Intent(this,PayAuthInfoActivity.class);
                    intent.putExtra("status",status);
                    intent.putExtra("type", type);
                    ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                }else  if("1".equals(type)){ //企业认证页面


                    intent = new Intent(this,TradeDrawingEnterpriseInfoActivity.class);
                    intent.putExtra("status",status);
                    intent.putExtra("type", type);
                    ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                }
                break;
            case R.id.ll_phone:

                if(managerTel != null && !"".equals(managerTel)){
                    if("NP".equals(managerTel)){  //未绑定 //已认证的展示手机号未绑定  跳转绑定手机号页面 不展示顶部123步骤

                        intent = new Intent(this,TradeDrawingPersonalAuthTwoActivity.class);
                        intent.putExtra("status",status);
                        intent.putExtra("type", type);
                        ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);

                    }else { //否则展示手机号已绑定
                        intent = new Intent(this,PayAuthPhoneActivity.class);
                        intent.putExtra("status",status);
                        intent.putExtra("type", type);
                        ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                    }

                }

                break;
            case R.id.ll_bank:

                if(cardNo != null && !"".equals(cardNo)){
                    if("B".equals(cardNo)){  //已绑定

                        /*if("0".equals(type)){  // 0 个人认证 //1 企业认证

                        }*/
                        intent = new Intent(this,PayAuthUnboundActivity.class);
                        intent.putExtra("status",status);
                        intent.putExtra("type", type);
                        ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);


                    }else if("NB".equals(cardNo)){ //未绑定
                        if("0".equals(type)) {
                            intent = new Intent(this, TradeDrawingPersonalAuthThreeActivity.class);
                            intent.putExtra("status", status);
                            intent.putExtra("type", type);
                            ActivityUtils.startActivity(intent, R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    }

                }
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(PayAuthComplateActivity.this,MainActivity.class);
        intent.putExtra("isIntentFragment","PersonalFragment");
        ActivityUtils.startActivity(intent,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }
}


