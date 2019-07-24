package com.zwx.scan.app.feature.personal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.campaign.PrizeGridImageAdapter;
import com.zwx.scan.app.feature.cateringinfomanage.FullyGridLayoutManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TradeDrawingEnterpriseInfoActivity extends BaseActivity<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener {

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;


    @BindView(R.id.edt_enterprise_name)
    protected EditText edt_enterprise_name;

    //营业执照
    @BindView(R.id.iv_business_license)
    protected ImageView iv_business_license;


    @BindView(R.id.edt_credit_code)
    protected EditText edt_credit_code;

    @BindView(R.id.edt_legal_person)
    protected EditText edt_legal_person;

    @BindView(R.id.edt_legal_phone)
    protected EditText edt_legal_phone;


    @BindView(R.id.edt_legal_id_num)
    protected EditText edt_legal_id_num;


    //身份证反
    @BindView(R.id.iv_id_num_fan)
    protected ImageView iv_id_num_fan;

    //身份证正
    @BindView(R.id.iv_id_num_zheng)
    protected ImageView iv_id_num_zheng;
    //对公账户
    @BindView(R.id.edt_duigong_account)
    protected EditText edt_duigong_account;

    //开户行名称
    @BindView(R.id.edt_bank_name)
    protected EditText edt_bank_name;
    @BindView(R.id.edt_bank_address)
    protected EditText edt_bank_address;


    @BindView(R.id.btn_back)
    protected Button btn_back;
    private  String imgType = ""; //1表示营业执照，2 表示身份证正面 3 表示身份证反面
    protected String userId ; //
    protected String companyName ;//企业名称
    protected String businessLicenseImg ;//企业营业执照
    protected String uniCredit ;//统一社会信用代码
    protected String legalName ;//法人姓名
    protected String legalPhone ;//法人手机号

    protected String legalIds ;//法人身份证
    protected String legalIdcard ;//身份证正面
    protected String legalIdcardBack ;//身份证反面
    protected String accountNo ;//对公账户
    protected String parentBankName ;//开户行名称
    protected String province ;//开户行所在省

    protected String city ;//city开户行所在市
    protected String status ;
    protected String process ;
    protected String type;  //1 企业认证类型
    protected String dialogTitle;




    @Override
    protected int getLayoutId() {
        return R.layout.activity_trade_drawing_enterprise_info;
    }

    @Override
    protected void initView() {
        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);
        tvTitle.setText("企业认证");
    }

    @Override
    protected void initData() {
        userId = SPUtils.getInstance().getString("userId");
        status = getIntent().getStringExtra("status");
        presenter.selectCompanyAuthInfo(this,userId,"1","");
    }


    protected void setImage(String selectImgType,String path){
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_load_fail)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_load_fail);

        if("1".equals(selectImgType)){
            Glide.with(this).load(path).apply(requestOptions).into(iv_business_license);
        }else if("2".equals(selectImgType)){
            Glide.with(this).load(path).apply(requestOptions).into(iv_id_num_zheng);
        }else if("3".equals(selectImgType)){
            Glide.with(this).load(path).apply(requestOptions).into(iv_id_num_fan);
        }

    }

    @OnClick({R.id.iv_back,R.id.btn_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(TradeDrawingEnterpriseInfoActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.btn_back:
                ActivityUtils.finishActivity(TradeDrawingEnterpriseInfoActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(TradeDrawingEnterpriseInfoActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);

    }
}
