package com.zwx.scan.app.feature.financemanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.ReceiveFund;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.contractmanage.ContractActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class PayFeeListDetailActivity extends BaseActivity<FinanceContract.Presenter> implements FinanceContract.View,View.OnClickListener{

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.btn_submit)
    protected Button btn_submit;

    @BindView(R.id.tv_pay_time_label)
    protected TextView tv_pay_time_label;
    @BindView(R.id.tv_pay_status)
    protected TextView tv_pay_status;

    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_pay_no)
    TextView tv_pay_no;
    @BindView(R.id.tv_pay_time)
    TextView tv_pay_time;
    @BindView(R.id.tv_contract_no)
    TextView tv_contract_no;
    private String fundId;


    @BindView(R.id.rl_contract)
    RelativeLayout rl_contract;


    protected String status = "U";  //显示支付按钮判断
    protected String contractId;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_fee_list_detail;
    }

    @Override
    protected void initView() {
        DaggerFinanceComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .financeModule(new FinanceModule(this))
                .build()
                .inject(this);
        tvTitle.setText("付款订单详情");

        fundId = SPUtils.getInstance().getString("userId");

        status = getIntent().getStringExtra("status");
        fundId = getIntent().getStringExtra("fundId");
        if("R".equals(status)){
            btn_submit.setVisibility(View.GONE);
            tv_pay_status.setText("已付款");
            tv_pay_time_label.setText("交款时间：");
        }else {
            btn_submit.setVisibility(View.VISIBLE);
            tv_pay_status.setText("未付款");
        }
    }

    @Override
    protected void initData() {

        presenter.doQueryByFundId(this,fundId);
    }


    @OnClick({R.id.iv_back,R.id.btn_submit,R.id.rl_contract})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(PayFeeListDetailActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.btn_submit:
                ActivityUtils.finishActivity(PayFeeListDetailActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.rl_contract:

                Intent intent = new Intent(PayFeeListDetailActivity.this,ContractActivity.class);

                intent.putExtra("contractId",contractId);
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(PayFeeListDetailActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
