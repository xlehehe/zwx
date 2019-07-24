package com.zwx.scan.app.feature.financialaffairs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.NetworkUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.feature.AppContext;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/05/09
 * desc   :  立即提现详情
 * version: 1.0
 **/
public class FinancialDrawingDetailActivity extends BaseActivity<FinancialAffairsContract.Presenter> implements FinancialAffairsContract.View,View.OnClickListener {


    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.iv_draw_status)
    protected ImageView iv_draw_status;



    @BindView(R.id.tv_daozhang_amt)
    protected TextView tv_daozhang_amt;
    @BindView(R.id.tv_draw_amt)
    protected TextView tv_draw_amt;

    @BindView(R.id.tv_fee)
    protected TextView tv_fee;

    @BindView(R.id.tv_draw_time)
    protected TextView tv_draw_time;

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


    //是否从提现记录列表进入该页面，若是控制返回按钮和物理返回键的返回页面逻辑
    private String isList= "YES";

    protected String userId ;
    protected String withdrawId;//提现id



    protected String amount; //提现金额
    protected String fee = "0";  //手续费
    protected String toAmt = "0";  //到账金额

    protected String createTime;
    protected String bankName ; //开户名
    protected String bankCardno; //开户账号

    protected String createName;  //操作人
    protected String createTel;  //操作电话

    protected String managerTel;  //负责人电话
    protected String managerName;//负责人

    @Override
    protected int getLayoutId() {
        return R.layout.activity_financial_drawing_detail;
    }

    @Override
    protected void initView() {
        DaggerFinancialAffairsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .financialAffairsModule(new FinancialAffairsModule(this))
                .build()
                .inject(this);
        tvTitle.setText("提现详情");

    }

    @Override
    protected void initData() {
        isList = getIntent().getStringExtra("isList");

        userId = SPUtils.getInstance().getString("userId");
        withdrawId = getIntent().getStringExtra("withdrawId");
        presenter.selectTradeWithdrawalDetails(this,userId,withdrawId);
    }

    @OnClick({R.id.iv_back,R.id.btn_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                if("YES".equals(isList)){
                    ActivityUtils.finishActivity(FinancialDrawingDetailActivity.class,
                            R.anim.slide_in_left, R.anim.slide_out_right);
                }else {
                    ActivityUtils.startActivity(TradeDrawingActivity.class,
                            R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                }
                break;
            case R.id.btn_back:
                if("YES".equals(isList)){
                    ActivityUtils.finishActivity(FinancialDrawingDetailActivity.class,
                            R.anim.slide_in_left, R.anim.slide_out_right);
                }else {
                    ActivityUtils.startActivity(TradeDrawingActivity.class,
                            R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                }
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if("YES".equals(isList)){
            ActivityUtils.finishActivity(FinancialDrawingDetailActivity.class,
                    R.anim.slide_in_left, R.anim.slide_out_right);
        }else {
            ActivityUtils.startActivity(TradeDrawingActivity.class,
                    R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        }
    }
}
