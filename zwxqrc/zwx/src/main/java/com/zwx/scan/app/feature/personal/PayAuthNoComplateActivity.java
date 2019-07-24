package com.zwx.scan.app.feature.personal;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.feature.AppContext;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * author : lizhilong
 * time   : 2019/05/14
 * desc   : 支付认证未完成 跳转页面选项
 * version: 1.0
 **/
public class PayAuthNoComplateActivity extends BaseActivity<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener{

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.tv_label)
    protected TextView tv_label;

    @BindView(R.id.ll_personal)
    protected LinearLayout ll_personal;

    @BindView(R.id.ll_enterprise)
    protected LinearLayout ll_enterprise;

    protected static String memberType = "2";

    protected  String status;
    protected  String process;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_auth_no_complate;
    }

    @Override
    protected void initView() {

        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);
        tvTitle.setText("支付认证");

        String label   = "若您绑定“"+"<font size = '16'color = \'#0486FE\' weight = 'bolder'>个人银行卡账户</font>“，请进行"
                +"<font size = '16'color = \'#0486FE\' weight = 'bolder'>个人认证</font>\n"
                +"若绑定“<font size = '16'color = \'#0486FE\' weight = 'bolder'>企业银行卡账号</font>”，请进行"
                +"<font size = '16'color = \'#0486FE\' weight = 'bolder'>企业认证</font>\n";
        tv_label.setText(Html.fromHtml(label));
    }

    @Override
    protected void initData() {
        status = getIntent().getStringExtra("status");
        process = getIntent().getStringExtra("process");
    }


    @OnClick({R.id.iv_back,R.id.ll_personal,R.id.ll_enterprise})
    @Override
    public void onClick(View v) {
        String userId = SPUtils.getInstance().getString("userId");
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(PayAuthNoComplateActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case R.id.ll_personal:  //个人认证
                memberType = "3";
                presenter.createMember(this,userId,memberType);

                break;

            case R.id.ll_enterprise:  //企业认证
                memberType = "2";
                presenter.createMember(this,userId,memberType);

                break;



        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(PayAuthNoComplateActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void setDialog(String message){

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView title = (TextView)rootView.findViewById(R.id.title);
//        title.setText("绑定失败");
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
