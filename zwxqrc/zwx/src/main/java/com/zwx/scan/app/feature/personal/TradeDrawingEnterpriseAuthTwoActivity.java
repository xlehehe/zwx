package com.zwx.scan.app.feature.personal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;

import butterknife.OnClick;

public class TradeDrawingEnterpriseAuthTwoActivity extends BaseActivity implements View.OnClickListener{


    Intent intent = null;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_trade_drawing_enterprise_auth_two;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }



    @OnClick({R.id.iv_back,R.id.btn_next})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(TradeDrawingEnterpriseAuthTwoActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.btn_next:

                ActivityUtils.startActivity(TradeDrawingPersonalAuthOneActivity.class,R.anim.slide_in_right,R.anim.slide_out_left);
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(TradeDrawingPersonalAuthOneActivity.class);
        ActivityUtils.finishActivity(TradeDrawingEnterpriseAuthTwoActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);

    }
}
