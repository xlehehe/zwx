package com.zwx.scan.app.feature.financialaffairs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.feature.AppContext;

import butterknife.BindView;
import butterknife.OnClick;

public class CollectionTransferRecordDetailActivity extends BaseActivity<FinancialAffairsContract.Presenter> implements FinancialAffairsContract.View,
        View.OnClickListener {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_daozhang_time)
    protected TextView tv_daozhang_time;


    @BindView(R.id.tv_type_name_label)
    protected TextView tv_type_name_label;

    @BindView(R.id.tv_daozhang_jg_status)
    protected TextView tv_daozhang_jg_status;

    @BindView(R.id.tv_daozhang_total_amt)
    protected TextView tv_daozhang_total_amt;

    @BindView(R.id.tv_daozhang_total_amt_no_han)
    protected TextView tv_daozhang_total_amt_no_han;

    @BindView(R.id.ll_fee)
    protected LinearLayout ll_fee;

    @BindView(R.id.ll_daozhang_amt)
    protected LinearLayout ll_daozhang_amt;

    @BindView(R.id.tv_fee)
    protected TextView tv_fee;

    @BindView(R.id.tv_shiji_to_amt)
    protected TextView tv_shiji_to_amt;

    @BindView(R.id.tv_order_num)
    protected TextView tv_order_num;

    @BindView(R.id.tv_bank_name)
    protected TextView tv_bank_name;

    @BindView(R.id.tv_name)
    protected TextView tv_name;

    @BindView(R.id.tv_bank_no)
    protected TextView tv_bank_no;



    @BindView(R.id.tv_label1)
    protected TextView tv_label1;

    @BindView(R.id.tv_order_num_label)
    protected TextView tv_order_num_label;

    @BindView(R.id.rl_bank)
    protected RelativeLayout rl_bank;
    @BindView(R.id.tv_name_label)
    protected TextView tv_name_label;

    @BindView(R.id.tv_bank_no_label)
    protected TextView tv_bank_no_label;

    protected String transferId;
    protected String type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection_transfer_record_detail;
    }

    @Override
    protected void initView() {
        DaggerFinancialAffairsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .financialAffairsModule(new FinancialAffairsModule(this))
                .build()
                .inject(this);
        tvTitle.setText("转账记录");
    }

    @Override
    protected void initData() {

        transferId = getIntent().getStringExtra("transferId");
        type = getIntent().getStringExtra("type");
        if("1".equals(type)){
 /*           holder.tv_to_amt_type.setText("银行卡");
            holder.iv_to_amt_type.setImageResource(R.drawable.ic_collecmoney_bank1);
            holder.tv_daozhang_total_amt_no_han.setVisibility(View.VISIBLE);
            holder.ll_daozhang_amt.setVisibility(View.VISIBLE);
            holder.ll_fee.setVisibility(View.VISIBLE);*/

        }else if("0".equals(type)){
       /*     holder.tv_to_amt_type.setText("钱包");
            holder.iv_to_amt_type.setImageResource(R.drawable.ic_collecmoney_wx1);
            holder.tv_daozhang_total_amt_no_han.setVisibility(View.GONE);

            holder.tv_shiji_to_amt_label.setText("到账金额：");
            holder.tv_label1.setText("到账人会员名：");
            holder.tv_label2.setText("到账人手机号：");
            String memberName = transfer.getMemberName();
            holder.tv_bank_name.setText(memberName != null && !"".equals(memberName)?memberName:"—");

            String phone = transfer.getPhone();
            holder.tv_bank_no.setText(phone != null && !"".equals(phone)?phone:"—");
            holder.ll_daozhang_amt.setVisibility(View.GONE);
            holder.ll_fee.setVisibility(View.GONE);*/
        }
        presenter.loadWxPayRecord(this,transferId);
    }


    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(CollectionTransferRecordDetailActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(CollectionTransferRecordDetailActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
