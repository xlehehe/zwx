package com.zwx.scan.app.feature.personal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.popwindow.PopItemAction;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.feature.AppContext;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/05/14
 * desc   : 支付认证 个人认证信息
 * version: 1.0
 **/
public class PayAuthInfoActivity extends BaseActivity<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener {
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.tv_name)
    protected TextView tv_name;
    @BindView(R.id.tv_auth_cert)
    protected TextView tv_auth_cert;
    @BindView(R.id.tv_id_card)
    protected TextView tv_id_card;


    protected String name;


    protected String cert;
    //身份证号
    protected String identityNo;
    protected String phone;
    protected String status;
    protected String userId;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_auth_info;
    }

    @Override
    protected void initView() {
        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);
        tvTitle.setText("认证信息");


    }

    @Override
    protected void initData() {

        userId = SPUtils.getInstance().getString("userId");
        status = getIntent().getStringExtra("status");

        presenter.selectPersonalOrCompanyAuthInfo(this,userId,status,"");
    }


    @OnClick({R.id.iv_back,R.id.btn_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(PayAuthInfoActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case R.id.btn_unbound:
                ActivityUtils.finishActivity(PayAuthInfoActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(PayAuthInfoActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
