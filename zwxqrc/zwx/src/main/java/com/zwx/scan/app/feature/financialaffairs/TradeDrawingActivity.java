package com.zwx.scan.app.feature.financialaffairs;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.personal.PayAuthAgreeActivity;
import com.zwx.scan.app.feature.personal.PayAuthComplateActivity;
import com.zwx.scan.app.feature.personal.PayAuthNoComplateActivity;
import com.zwx.scan.app.feature.personal.TradeDrawingEnterpriseAuthOneActivity;
import com.zwx.scan.app.feature.personal.TradeDrawingPersonalAuthOneActivity;
import com.zwx.scan.app.feature.personal.TradeDrawingPersonalAuthThreeActivity;
import com.zwx.scan.app.feature.personal.TradeDrawingPersonalAuthTwoActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/05/09
 * desc   :  交易提款主页
 * version: 1.0
 **/
public class TradeDrawingActivity extends BaseActivity<FinancialAffairsContract.Presenter> implements FinancialAffairsContract.View,View.OnClickListener {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.tv_right)
    protected TextView tvRight;
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_drawal_amt)
    protected TextView tv_drawal_amt;

    @BindView(R.id.tv_amt)
    protected TextView tv_amt;

    @BindView(R.id.iv_detailed)
    protected ImageView iv_detailed;


    @BindView(R.id.btn_drawing)
    protected Button btn_drawing;

    @BindView(R.id.btn_drawing_record)
    protected Button btn_drawing_record;



    private String userId;
    //待结算金额
    protected String unSettlement;

    //可提现金额
    protected String amount;

    //是否已认证  支付认证状态
    protected String status;

    protected String process;
    Intent intent = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trade_drawing;
    }

    @Override
    protected void initView() {

        DaggerFinancialAffairsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .financialAffairsModule(new FinancialAffairsModule(this))
                .build()
                .inject(this);
        tvTitle.setText("交易提款");
    }

    @Override
    protected void initData() {
        userId = SPUtils.getInstance().getString("userId");
        presenter.selectTradeWithdrawal(this,userId);

    }



    @OnClick({R.id.iv_back,R.id.iv_detailed,R.id.btn_drawing,R.id.btn_drawing_record})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(TradeDrawingActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.iv_detailed:

                ActivityUtils.startActivity(CollectionFlowListActivity.class,R.anim.slide_in_right,R.anim.slide_out_left);
                break;

            case R.id.btn_drawing:

//                ActivityUtils.startActivity(FinancialDrawingDetailActivity.class,R.anim.slide_in_right,R.anim.slide_out_left);
                presenter.selectPaymentAuthStatus(this,userId);
                break;

            case R.id.btn_drawing_record:

                ActivityUtils.startActivity(TradeDrawingRecordActivity.class,
                        R.anim.slide_in_right, R.anim.slide_out_left);
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(TradeDrawingActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }


    public void setDialog(String message){

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView tvTitle = (TextView)rootView.findViewById(R.id.title);
//        tvTitle.setText(dialogTitle);
        TextView textView = (TextView)rootView.findViewById(R.id.message);
        textView.setText(message);
        //立即前往
       Button confirmBtn =  rootView.findViewById(R.id.confirmBtn);
        confirmBtn.setText("立即前往");
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if("N".equals(status)){  //未认证
                    /**
                     * 在未完成状态下
                     *
                     * 在未认证状态status  :N 下  process  ：N 跳选择认证界面   CI  跳企业认证，CP 跳企业绑定手机，CS  跳企业签订协议，PI  跳个人认证第一步信息，PP 跳个人绑定手机 ，PB跳个人绑定银行卡，PS  跳企业签订协议
                     * */
                    if(process != null &&  !"".equals(process)){
                        if("CI".equals(process)||"NCI".equals(process)){  //跳企业认证
                            intent = new Intent(TradeDrawingActivity.this,TradeDrawingEnterpriseAuthOneActivity.class);
                        }else if("PI".equals(process)){  //跳个人认证
                            intent = new Intent(TradeDrawingActivity.this,TradeDrawingPersonalAuthOneActivity.class);
                        }else if("PP".equals(process)||"CP".equals(process)){//绑定手机号    展示  123 步骤
                            intent = new Intent(TradeDrawingActivity.this,TradeDrawingPersonalAuthTwoActivity.class);
                        }else if("PB".equals(process)){//PB跳个人绑定银行卡    展示  123 步骤  企业认证没有
                            intent = new Intent(TradeDrawingActivity.this,TradeDrawingPersonalAuthThreeActivity.class);
                        }else if("PS".equals(process)||"CS".equals(process)){//PS跳协议    展示  123 步骤
                            intent = new Intent(TradeDrawingActivity.this,PayAuthAgreeActivity.class);
                        }else if("N".equals(process)){ //选项
                            intent = new Intent(TradeDrawingActivity.this,PayAuthNoComplateActivity.class);
                        }
                        if(intent !=null){
                            intent.putExtra("process",process);
                        }
                    }
                }else if("A".equals(status)||"NA".equals(status)){ //已认证
                    intent = new Intent(TradeDrawingActivity.this,PayAuthComplateActivity.class);
                }
                if(intent !=null){
                    intent.putExtra("status",status);
                    ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                }

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
