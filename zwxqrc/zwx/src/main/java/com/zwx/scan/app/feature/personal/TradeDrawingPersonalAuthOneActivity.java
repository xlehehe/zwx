package com.zwx.scan.app.feature.personal;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
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
 * time   : 2019/05/13
 * desc   : 交易提款 个人认证第一步
 * version: 1.0
 **/
public class TradeDrawingPersonalAuthOneActivity extends BaseActivity<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener {

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
    protected TextView edt_name;

    @BindView(R.id.edt_id_card)
    protected TextView edt_id_card;
    //身份证明
    @BindView(R.id.tv_id_name)
    protected TextView tv_id_name;

    private String name; //认证姓名

    //身份证件
    protected String certificateName;
    //身份证件
    protected static String idCardNo ;

    protected String dialogTitle;
    private String userId;
    //status N 下 process  ：N 跳选择认证界面   CI  跳企业认证，CP 跳企业绑定手机，CS  跳企业签订协议，PI  跳个人认证第一步信息，PP 跳个人绑定手机 ，PB跳个人绑定银行卡，PS  跳企业签订协议
    protected String process;
    protected String status;




    @Override
    protected int getLayoutId() {
        return R.layout.activity_trade_drawing_personal_auth_one;
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

        if("N".equals(status)){ //未认证
            ll_step.setVisibility(View.VISIBLE);
                if("PI".equals(process)){

                    setSetTep();
//                    presenter.selectPersonalOrCompanyAuthInfo(this,userId,status,process);
                }

        }else if("A".equals(status)){ //已认证
            if("PI".equals(process)){
                ll_step.setVisibility(View.GONE);
                presenter.selectPersonalOrCompanyAuthInfo(this,userId,status,process);
            }
        }

    }

    private void setSetTep() {

        ivOne.setBackgroundResource(R.drawable.ic_first_clicked);
        ivEllipsisOne.setBackgroundResource(R.drawable.ic_ellipsis_gray);
        ivTwo.setBackgroundResource(R.drawable.ic_two_untclick);
        ivEllipsisTwo.setBackgroundResource(R.drawable.ic_ellipsis_gray);
        ivThree.setBackgroundResource(R.drawable.ic_three_untclick);
    }


    @OnClick({R.id.iv_back,R.id.btn_next})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(TradeDrawingPersonalAuthOneActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;
                case R.id.btn_next:
                    name = edt_name.getText().toString().trim();
                    idCardNo = edt_id_card.getText().toString().trim();

                    if(name == null || "".equals(name)){

                        setDialog("请输入认证姓名");
                        dialogTitle = "提示";
                        return;
                    }

                    if(idCardNo == null || "".equals(idCardNo)){
                        setDialog("请输入身份证号");
                        dialogTitle = "提示";
                        return;
                    }



                    presenter.setPersonalAuthInfo(this,userId,name,idCardNo);
//                    ActivityUtils.startActivity(TradeDrawingPersonalAuthTwoActivity.class,R.anim.slide_in_right,R.anim.slide_out_left);
                    break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(TradeDrawingPersonalAuthOneActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);

    }

    public void setDialog(String message){

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView tvTitle = (TextView)rootView.findViewById(R.id.title);
        tvTitle.setText(dialogTitle);
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
