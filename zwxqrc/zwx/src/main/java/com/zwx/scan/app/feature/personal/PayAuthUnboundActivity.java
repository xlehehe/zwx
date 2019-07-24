package com.zwx.scan.app.feature.personal;

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
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.feature.AppContext;

import butterknife.BindView;
import butterknife.OnClick;

public class PayAuthUnboundActivity extends BaseActivity<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener {

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.tv_name)
    protected TextView tv_name;
    @BindView(R.id.tv_bank_card)
    protected TextView tv_bank_card;
    @BindView(R.id.tv_bank_name)
    protected TextView tv_bank_name;
    @BindView(R.id.tv_bank_phone)
    protected TextView tv_bank_phone;

    @BindView(R.id.btn_unbound)
    protected Button btn_unbound;

    protected String name;
    protected String cardNo;
    protected String bankName;
    protected String phone;
    protected String status;
    protected String userId;

    protected String dialogTitle;

    protected String type ;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_auth_unbound;
    }

    @Override
    protected void initView() {


        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);

        tvTitle.setText("银行卡");

    }

    @Override
    protected void initData() {


        userId = SPUtils.getInstance().getString("userId");
        type = getIntent().getStringExtra("type");
        status = getIntent().getStringExtra("status");

        if("1".equals(type)){  //企业认证不用展示解绑银行卡按钮
            btn_unbound.setVisibility(View.GONE);
        }else {
            btn_unbound.setVisibility(View.VISIBLE);
        }
//        status = "UnBindBank";
        presenter.selectPersonalOrCompanyAuthInfo(this,userId,"UnBindBank","");
    }




    @OnClick({R.id.iv_back,R.id.btn_unbound})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(PayAuthUnboundActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case R.id.btn_unbound:

                if(cardNo == null || "".equals(cardNo)){
                    dialogTitle = "提示";
                    setDialog("银行卡账号不能为空");
                    return;
                }

                presenter.unbindBankCard(this,userId,cardNo);
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(PayAuthUnboundActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }


    public void setDialog(String message){

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView tvTitle = (TextView)rootView.findViewById(R.id.title);
        tvTitle.setText(dialogTitle);
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
