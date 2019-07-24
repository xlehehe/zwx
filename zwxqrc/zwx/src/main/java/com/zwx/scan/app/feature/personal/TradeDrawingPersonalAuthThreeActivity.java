package com.zwx.scan.app.feature.personal;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.main.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class TradeDrawingPersonalAuthThreeActivity extends BaseActivity<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener  {

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;


    @BindView(R.id.ll_step)
    protected LinearLayout ll_step;
    @BindView(R.id.iv_one)
    protected ImageView ivOne;
    @BindView(R.id.iv_ellipsis_one)
    protected ImageView ivEllipsisOne;
    @BindView(R.id.iv_two)
    protected ImageView ivTwo;
    @BindView(R.id.iv_ellipsis_two)
    protected ImageView ivEllipsisTwo;
    @BindView(R.id.iv_three)
    protected ImageView ivThree;


    @BindView(R.id.edt_name)
    protected EditText edt_name;
    @BindView(R.id.edt_bank_no)
    protected EditText edt_bank_no;

    @BindView(R.id.edt_bank_name)
    protected EditText edt_bank_name;

    @BindView(R.id.edt_phone)
    protected EditText edt_phone;
    @BindView(R.id.btn_next)
    protected Button btn_next;


    protected Intent intent = null;

    protected String name;
    protected String userId;
    protected String identityNo; //身份证号
    //status N 下 process  ：N 跳选择认证界面   CI  跳企业认证，CP 跳企业绑定手机，CS  跳企业签订协议，PI  跳个人认证第一步信息，PP 跳个人绑定手机 ，PB跳个人绑定银行卡，PS  跳企业签订协议
    protected String process;
    protected String status;
    protected String bankNo;
    protected String bankName;
    protected String phone;

    protected String dialogTitle;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_trade_drawing_personal_auth_three;
    }

    @Override
    protected void initView() {

        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);
        tvTitle.setText("认证");
        setSetTep();
    }

    @Override
    protected void initData() {
        userId = SPUtils.getInstance().getString("userId");
        process = getIntent().getStringExtra("process");
        status = getIntent().getStringExtra("status");

        name = getIntent().getStringExtra("managerName");

        edt_name.setText(name);
        edt_name.setEnabled(false);
        if("N".equals(status)){ //未认证
            ll_step.setVisibility(View.VISIBLE);
            if("PB".equals(process)){  //
                presenter.selectPersonalOrCompanyAuthInfo(this,userId,status,process);
            }

        }else if("A".equals(status)||"NA".equals(status)){ //已认证  不展示 123 步骤
            ll_step.setVisibility(View.GONE);

            tvTitle.setText("绑定银行卡");
            btn_next.setText("完成");
            presenter.selectPersonalOrCompanyAuthInfo(this,userId,"BindBank",process);
        }

    }

    private void setSetTep(){

        ivOne.setBackgroundResource(R.drawable.ic_first_clicked);
        ivEllipsisOne.setBackgroundResource(R.drawable.ic_ellipsis_blue);
        ivTwo.setBackgroundResource(R.drawable.ic_two_clicked);
        ivEllipsisTwo.setBackgroundResource(R.drawable.ic_ellipsis_blue);
        ivThree.setBackgroundResource(R.drawable.ic_three_clicked);
    }

    @OnClick({R.id.iv_back,R.id.btn_next})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:

                if("A".equals(status)||"NA".equals(status)||"BindBank".equals(status)){  //返回已认证/未完成页面
                    intent = new Intent(TradeDrawingPersonalAuthThreeActivity.this,PayAuthComplateActivity.class);
                }else {
                    intent = new Intent(TradeDrawingPersonalAuthThreeActivity.this,MainActivity.class);
                    intent.putExtra("isIntentFragment","PersonalFragment");
                }
                ActivityUtils.startActivity(intent,
                        R.anim.slide_in_left, R.anim.slide_out_right);
//                ActivityUtils.finishActivity(TradeDrawingPersonalAuthThreeActivity.class,
//                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.btn_next:

                name =edt_name.getText().toString().trim();
                bankNo=edt_bank_no.getText().toString().trim();
                phone =edt_phone.getText().toString().trim();
                bankName=edt_bank_name.getText().toString().trim();
                if(name == null || "".equals(name)){
                    dialogTitle = "提示";
                    setDialog("请输入开户名");

                    return;
                }

                if(name == null || "".equals(name)){
                    dialogTitle = "提示";
                    setDialog("请输入开户名");

                    return;
                }

                if(bankNo == null || "".equals(bankNo)){
                    dialogTitle = "提示";
                    setDialog("请输入银行卡号");

                    return;
                }

                if(!RegexUtils.isMobileSimple(phone)){
                    dialogTitle = "提示";
                    setDialog("请输入正确手机号");
                    return;
                }


                if(bankName == null || "".equals(bankName)){
                    setDialog("请输入开户行");
                    dialogTitle = "提示";
                    return;
                }
//                Intent intent = new Intent(this,PayAuthComplateActivity.class);
//                intent.putExtra("process",process);
//                intent.putExtra("status",status);
//                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);

                presenter.bindBankCard(this,userId,bankNo,name,identityNo,bankName,phone,status,process);
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if("A".equals(status)||"NA".equals(status)||"BindBank".equals(status)){  //返回已认证/未完成页面
            intent = new Intent(TradeDrawingPersonalAuthThreeActivity.this,PayAuthComplateActivity.class);
        }else {
            intent = new Intent(TradeDrawingPersonalAuthThreeActivity.this,MainActivity.class);
            intent.putExtra("isIntentFragment","PersonalFragment");
        }
        ActivityUtils.startActivity(intent,
                R.anim.slide_in_left, R.anim.slide_out_right);

    }

    public void setDialog(String message){

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView title = (TextView)rootView.findViewById(R.id.title);
        title.setText(dialogTitle);
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
