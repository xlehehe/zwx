package com.zwx.scan.app.feature.financialaffairs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.feature.AppContext;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * author : lizhilong
 * time   : 2019/05/22
 * desc   : 收款账号设置 银行卡 生效中
 * version: 1.0
 **/
public class CollectionBankNoEffectiveActivity extends BaseActivity <FinancialAffairsContract.Presenter> implements FinancialAffairsContract.View,View.OnClickListener  {

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.tv_bank_name) //开户行
    protected TextView tv_bank_name;

    @BindView(R.id.edt_bank_no)   //开户卡号
    protected EditText edt_bank_no;

    @BindView(R.id.edt_name)   //开户名
    protected EditText edt_name;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection_bank_no_effective;
    }

    @Override
    protected void initView() {

        DaggerFinancialAffairsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .financialAffairsModule(new FinancialAffairsModule(this))
                .build()
                .inject(this);

        tvTitle.setText("收款到银行卡");
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.iv_back,R.id.tv_bank_name})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(CollectionBankNoEffectiveActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case R.id.tv_bank_name:
                ActivityUtils.startActivity(CollectionSelectBankNameListActivity.class,
                        R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(CollectionBankNoEffectiveActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }


    public void setDialog(String message){

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView tvTitle = (TextView)rootView.findViewById(R.id.title);
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
