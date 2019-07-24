package com.zwx.scan.app.feature.financialaffairs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.personal.TradeDrawingPersonalAuthOneActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/05/22
 * desc   : 收款账号设置
 * version: 1.0
 **/
public class CollectionMoneyAccountSettingActivity extends BaseActivity<FinancialAffairsContract.Presenter> implements FinancialAffairsContract.View,View.OnClickListener {
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.tv_label)
    protected TextView tv_label;

    @BindView(R.id.ll_wx)
    protected LinearLayout ll_wx;

    @BindView(R.id.ll_bank)
    protected LinearLayout ll_bank;


    @BindView(R.id.tv_setting_wx)
    protected TextView tv_setting_wx;

    protected String configState;
    protected String userId;

    protected String phone;
    protected String name;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection_money_account_setting;
    }

    @Override
    protected void initView() {

        DaggerFinancialAffairsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .financialAffairsModule(new FinancialAffairsModule(this))
                .build()
                .inject(this);

        tvTitle.setText("收款帐号设置");
    }

    @Override
    protected void initData() {

        userId = SPUtils.getInstance().getString("userId");

        presenter.loadConfigByCompId(this,userId);


    }


    @OnClick({R.id.iv_back,R.id.ll_wx,R.id.ll_bank})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(CollectionMoneyAccountSettingActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case R.id.ll_wx: //未生效

               /* if("2".equals(configState)){

                }*/

                intent = new Intent(this,CollectionWxNoEffectiveActivity.class);
                intent.putExtra("type","1");
                intent.putExtra("configState",configState);
                intent.putExtra("phone",phone);
                intent.putExtra("name",name);
                ActivityUtils.startActivity(intent,
                        R.anim.slide_in_right, R.anim.slide_out_left);

                break;

            case R.id.ll_bank:  //银行卡
                /*intent = new Intent(this,CollectionWxNoEffectiveActivity.class);
                intent.putExtra("type","2");
                intent.putExtra("configState",configState);
                intent.putExtra("phone",phone);
                intent.putExtra("name",name);
                ActivityUtils.startActivity(intent,
                        R.anim.slide_in_right, R.anim.slide_out_left);*/
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(CollectionMoneyAccountSettingActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
