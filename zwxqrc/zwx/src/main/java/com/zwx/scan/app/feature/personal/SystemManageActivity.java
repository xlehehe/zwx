package com.zwx.scan.app.feature.personal;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.popwindow.PopItemAction;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.AppUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.User;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.user.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SystemManageActivity extends BaseActivity<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener{

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

//    @BindView(R.id.tv_clear)
//    protected TextView tvClear;

    @BindView(R.id.btn_exit)
    protected Button btnExit;
    @BindView(R.id.tv_version)
    protected TextView tv_version;


    private String tip ;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_manage;
    }

    @Override
    protected void initView() {
        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);
        tvTitle.setText(getResources().getText(R.string.title_system_manage));
    }

    @Override
    protected void initData() {

        String version = AppUtils.getAppVersionName();
        tv_version.setText("V"+version);
    }


    @OnClick({R.id.iv_back,R.id.btn_exit})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(SystemManageActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;
         /*   case R.id.tv_clear:

                tip = getResources().getString(R.string.you_clear_cache);
                setDialog();
                break;*/
            case R.id.btn_exit:

                User user =new User();
                String userId = SPUtils.getInstance().getString("userId");
                String username = SPUtils.getInstance().getString("username");


                user.setId(Long.parseLong(userId));
                user.setUsername(username);

                presenter.logout(this,user);

                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(SystemManageActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);

    }


    public void setDialog(){

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView textView = (TextView)rootView.findViewById(R.id.message);
        textView.setGravity(Gravity.TOP|Gravity.CENTER);
        textView.setText(tip);
        rootView.findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SPUtils.getInstance().clear(true);
                dialog.dismiss();

            }
        });

        rootView.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
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

    }
}
