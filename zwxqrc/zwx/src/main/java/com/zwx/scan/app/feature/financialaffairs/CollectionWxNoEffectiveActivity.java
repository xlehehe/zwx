package com.zwx.scan.app.feature.financialaffairs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.SpanUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.CompWxpayConfig;
import com.zwx.scan.app.feature.AppContext;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/05/22
 * desc   : 收款账号设置 微信零钱 生效中
 * version: 1.0
 **/
public class CollectionWxNoEffectiveActivity extends BaseActivity<FinancialAffairsContract.Presenter> implements FinancialAffairsContract.View,View.OnClickListener {

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.edt_phone)
    protected EditText edt_phone;


    @BindView(R.id.edt_name)
    protected EditText edt_name;
    @BindView(R.id.btn_effect)
    protected Button btn_effect;
    protected String userId;
    protected String brandId;
    protected   String phone;
    protected String name;

    //0 钱包 生效中   1 银行卡 生效中   2 未设置
    protected String configState;
    protected String jsonstr;
    protected String type; //1 表示钱包  2表示银行卡 区分 切换钱包 或银行卡 按钮为立即生效

    protected CompWxpayConfig wxPayBean = new CompWxpayConfig();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection_wx_no_effective;
    }

    @Override
    protected void initView() {
        DaggerFinancialAffairsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .financialAffairsModule(new FinancialAffairsModule(this))
                .build()
                .inject(this);

        tvTitle.setText("收款到微信零钱");
    }

    @Override
    protected void initData() {
        userId = SPUtils.getInstance().getString("userId");
        brandId = SPUtils.getInstance().getString("brandId");
        configState = getIntent().getStringExtra("configState");
        phone  = getIntent().getStringExtra("phone");
        name  = getIntent().getStringExtra("name");


        if("0".equals(configState)){
            btn_effect.setText("立即生效");
            edt_name.setEnabled(false);
            edt_phone.setEnabled(false);
            edt_phone.setText(phone != null && !"".equals(phone)?phone:"");
            edt_name.setText(name != null && !"".equals(name)?name:"");

            btn_effect.setVisibility(View.GONE);
        }else if("2".equals(configState)){
            edt_name.setEnabled(true);
            edt_phone.setEnabled(true);
            btn_effect.setText("保存并生效");
        }
        /*edt_phone.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus){
                    phone = edt_phone.getText().toString().trim();
                    if(phone != null && !"".equals(phone)){
                        if(!RegexUtils.isMobileSimple(phone)){
                            ToastUtils.showShort("请输入正确格式手机号");
                            return;
                        }

                    }else {
                        ToastUtils.showShort("请输入成为会员时注册的手机号");
                    }
                }

            }
        });

        edt_name.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus){
                    name = edt_name.getText().toString().trim();

                    if(name != null && !"".equals(name)){


                    }else {
                        ToastUtils.showShort("请输入会员的姓名");
                    }
                }
            }
        });*/
    }


    @OnClick({R.id.iv_back,R.id.btn_effect})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(CollectionWxNoEffectiveActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.btn_effect:
                phone = edt_phone.getText().toString().trim();
                name = edt_name.getText().toString().trim();
                if(!RegexUtils.isMobileSimple(phone)){
                    ToastUtils.showShort("请输入正确格式手机号");
                    return;
                }

                if(name == null || "".equals(name)){
                    ToastUtils.showShort("请输入会员的姓名");
                    return;
                }

                if(name.length()>15){
                    ToastUtils.showShort("会员的姓名长度不能大于15");
                    return;
                }


                name = edt_name.getText().toString().trim();

                wxPayBean.setPhone(phone);
//                wxPayBean.setAccountName(name);
                wxPayBean.setPayType("0");
                wxPayBean.setCompId(Long.parseLong(SPUtils.getInstance().getString("compId")));
                wxPayBean.setMemberName(name);
//                wxPayBean.setBrandId(SPUtils.getInstance().getString("brandId"));
//                wxPayBean.setConfigState("0");

                Map<String,Object> map = new HashMap<>();
                map.put("compWxpayConfig",wxPayBean);
                map.put("brandId",SPUtils.getInstance().getString("brandId"));

                jsonstr=new Gson().toJson(map);

                presenter.checkWxPayInfo(this,brandId,phone,name);
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(CollectionWxNoEffectiveActivity.class,
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
