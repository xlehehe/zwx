package com.zwx.scan.app.feature.personal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.http.service.PersonalManager;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.user.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class PasswordManageActivity extends BaseActivity<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener{
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.btn_submit)
    protected Button btnSubmit;

    @BindView(R.id.edt_old_psd)
    protected EditText edt_old_psd;

    @BindView(R.id.cb_select)
    protected CheckBox cb_select;
    @BindView(R.id.edt_new_psd)
    protected EditText edt_new_psd;
    @BindView(R.id.tv_psd_tip)
    protected TextView tv_psd_tip;

    private String oldPsd;
    private String newPsd;
    private String userId;
    private String operateFlag;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_password_manage;
    }

    @Override
    protected void initView() {
        tvTitle.setText(getResources().getText(R.string.title_psd_manage));

        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {
        userId = SPUtils.getInstance().getString("userId");
        edt_old_psd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                oldPsd = edt_old_psd.getText().toString().trim();
                if(oldPsd.length()>0){
                    if(hasFocus){

                    }else {
                        presenter.checkPsd(PasswordManageActivity.this,userId,oldPsd);
                        edt_new_psd.setFocusable(true);
                        edt_new_psd.setFocusableInTouchMode(true);

                    }
                }

            }
        });


        cb_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){   //显示密码
                    edt_new_psd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edt_old_psd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    edt_new_psd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edt_old_psd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });
    }


    @OnClick({R.id.iv_back,R.id.btn_submit})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(PasswordManageActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);

                break;

            case R.id.btn_submit:
                newPsd = edt_new_psd.getText().toString().trim();

                if(newPsd == null || newPsd.length() == 0){
                    ToastUtils.showShort("密码不能为空！");
                    return;
                }
                operateFlag = "2";
                presenter.updateUser(this,userId,newPsd,"2");
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(PasswordManageActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
