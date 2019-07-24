package com.zwx.scan.app.feature.personal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.User;
import com.zwx.scan.app.feature.AppContext;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/05/13
 * desc   : 交易提款认证
 * version: 1.0
 **/
public class TradeDrawingAuthActivity extends BaseActivity <PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener{

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_trade_drawing_auth;
    }

    @Override
    protected void initView() {

        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);
        tvTitle.setText("交易提款");
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(TradeDrawingAuthActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(TradeDrawingAuthActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);

    }
}
