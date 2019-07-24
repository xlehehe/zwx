package com.zwx.scan.app.feature.shop;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.verification.ProductVerificationActivity;
import com.zwx.scan.app.utils.MaxTextLengthFilter;
import com.zwx.scan.app.widget.MyListView;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopOrderDetailActivity extends BaseActivity<ShopContract.Presenter> implements ShopContract.View ,View.OnClickListener  {
    @BindView(R.id.tv_title)
    protected TextView tvTitle;


    @BindView(R.id.tv_right)
    protected TextView tv_right;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_order_num)
    protected TextView tv_order_num;


    @BindView(R.id.tv_ziti)
    protected TextView tv_ziti;

    @BindView(R.id.tv_time)
    protected TextView tv_time;

    @BindView(R.id.tv_order_pay_amt)
    protected TextView tv_order_pay_amt;

    @BindView(R.id.tv_red_packet)
    protected TextView tv_red_packet;

    @BindView(R.id.tv_good_amt)
    protected TextView tv_good_amt;

    @BindView(R.id.tv_buy_liuyan)
    protected TextView tv_buy_liuyan;

    @BindView(R.id.tv_member_info)
    protected TextView tv_member_info;



    @BindView(R.id.tv_phone)
    protected TextView tv_phone;
    //微信 支付宝银联
    @BindView(R.id.tv_pay_way)
    protected TextView tv_pay_way;

    @BindView(R.id.ll_pay)
    protected LinearLayout ll_pay;
    @BindView(R.id.rl_red_packet)
    protected RelativeLayout rl_red_packet;


    @BindView(R.id.tv_pay_red_pack)
    protected TextView tv_pay_red_pack;
    //支付方式的付款金额
    @BindView(R.id.tv_pay_amt)
    protected TextView tv_pay_amt;
    //红包支付
    @BindView(R.id.tv_pay_red_way)
    protected TextView tv_pay_red_way;
    @BindView(R.id.tv_pay_num)
    protected TextView tv_pay_num;


    @BindView(R.id.ll_coupon_info)
    protected LinearLayout ll_coupon_info;
    @BindView(R.id.list_view)
    protected MyListView list_view;
    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;

    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;



    //收货人信息
    @BindView(R.id.ll_shou_goods_info)
    protected LinearLayout ll_shou_goods_info;
    @BindView(R.id.tv_shouhuoren)
    protected TextView tv_shouhuoren;

    @BindView(R.id.tv_shouhuoren_tel)
    protected TextView tv_shouhuoren_tel;
    @BindView(R.id.tv_shouhuoren_address)
    protected TextView tv_shouhuoren_address;

    @BindView(R.id.ll_post_info)
    protected LinearLayout ll_post_info;

    @BindView(R.id.tv_company_name)
    protected TextView tv_company_name;

    @BindView(R.id.tv_post_order_id)
    protected TextView tv_post_order_id;

    protected EditText edt_order_id = null;
    protected String detailedId;


    TOrderObject orderObject = new TOrderObject();
    List<TOrderObject> orderObjectList = new ArrayList<>();

    String orderCode = "—";
    /**
     * 1: 自提  2:邮寄
     */
    protected String delivType = "—";
    String time = "—";
    String payAmt = "—";
    String redpacket = "—";
    String goodAmt = "—";
    String buyLiuYan = "—";
    String memberName= "—";
    String phone= "—";

    String payNum= "—";

    String payWay = "";
    String usrRedEnvelope = "—";
    String status  = "";
    /**
     * 收货地址
     */
    protected String memberAddress;

    /**
     * 收货电话
     */
    protected String receiverTel;

    /**
     * 收件人
     */
    protected String receiver;


    /**
     * 快递 公司
     */
    protected String postCompany;

    /**
     * 快递 单号
     */
    protected String postCode;


    ShopOrderDetailAdapter adapter = null;

    protected static String isDetail ;

    protected String detailedListItemId;
    protected String stateListItem;
    protected String stateDetail;

    protected String orderId;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_order_detail;
    }

    @Override
    protected void initView() {
        DaggerShopComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .shopModule(new ShopModule(this))
                .build()
                .inject(this);
        tvTitle.setText("订单详情");
        tv_right.setText("确认邮寄");
        detailedId = getIntent().getStringExtra("detailedId");
        stateDetail = getIntent().getStringExtra("stateDetail");
        orderObject = (TOrderObject) getIntent().getSerializableExtra("order");


    }

    @Override
    protected void initData() {
        if(orderObject != null ){
            stateListItem = orderObject.getStatus();
            if(orderObject.getOrderId() != null ){
                orderId  = String.valueOf(orderObject.getOrderId());

            }
        }



        ll_no_data.setVisibility(View.VISIBLE);
        list_view.setVisibility(View.GONE);
        presenter.doQueryOrderDetails(this,detailedId);





    }
    @OnClick({R.id.iv_back,R.id.tv_right})
    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.iv_back:
             if(stateDetail!= null && !"".equals(stateDetail)){
                 if(stateDetail.equals(stateListItem)){  //邮寄状态不一致的话 更新局部更新

                 }else {
                     EventBus.getDefault().post(new ProductEventBus(orderId,"listFlush",""));
                 }
             }else {
                 EventBus.getDefault().post(new ProductEventBus(orderId,"listFlush",""));
             }

             ActivityUtils.finishActivity(ShopOrderDetailActivity.class,
                     R.anim.slide_in_left,R.anim.slide_out_right);
             break;
         case R.id.tv_right:

             showPost();
             break;
     }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(stateDetail!= null && !"".equals(stateDetail)){
            if(stateDetail.equals(stateListItem)){  //邮寄状态不一致的话 更新局部更新

            }else {
                EventBus.getDefault().post(new ProductEventBus(orderId,"listFlush",""));
            }
        }else {
            EventBus.getDefault().post(new ProductEventBus(orderId,"listFlush",""));
        }


        ActivityUtils.finishActivity(ShopOrderDetailActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
    }


    //确认邮寄弹窗
    protected void showPost() {
        View customView = View.inflate(this, R.layout.layout_goods_manage_post, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog  dialog = builder.create();
        dialog.setView(customView, 0, 0, 0, 0);
        //公司名称
        EditText edt_company_name = (EditText) customView.findViewById(R.id.edt_company_name);
        //统计文字个数
        TextView tv_company_name_num = (TextView) customView.findViewById(R.id.tv_company_name_num);
        //订单编号
         edt_order_id = (EditText) customView.findViewById(R.id.edt_order_id);

        LinearLayout ll_cancel = (LinearLayout) customView.findViewById(R.id.ll_cancel);

        edt_order_id.setText(postCode != null && !"".equals(postCode)?postCode:"");
        edt_company_name.setSelection(edt_company_name.getText().length());
        edt_company_name.setFilters(new InputFilter[]{new MaxTextLengthFilter(30)});

        edt_company_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                int remarkLenth = s.length();
                if(edt_company_name.getText().toString().trim().length()>0){
                    edt_company_name.setSelection(edt_company_name.getText().toString().trim().length());
                    tv_company_name_num.setText(remarkLenth+"/30");
                }

            }
        });
        ImageView iv_qrc = (ImageView) customView.findViewById(R.id.iv_qrc);
        iv_qrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopOrderDetailActivity.this,ProductVerificationActivity.class);
                intent.putExtra("intentOrderListAndDetailFlag","YES2");
                intent.putExtra("title","快递订单扫码");
                ActivityUtils.startActivityForResult(ShopOrderDetailActivity.this,intent,2222,R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        Button btn_use = (Button)customView.findViewById(R.id.confirmBtn);
        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                KeyboardUtils.showSoftInput(edt_company_name);
//                KeyboardUtils.showSoftInput(edt_order_id);
                String companyName  = edt_company_name.getText().toString().trim();
                String postCode = edt_order_id.getText().toString().trim();


                if(companyName == null || "".equals(companyName)){
                    ToastUtils.showShort("请输入快递公司名称");
                    return;
                }

                if(postCode == null || "".equals(postCode)){
                    ToastUtils.showShort("请输入快递单号");
                    return;
                }
                presenter.queryOrderConfirmPost(ShopOrderDetailActivity.this,detailedId,companyName,postCode);
                dialog.dismiss();

            }
        });
        Button cancelBtn = (Button)customView.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ll_cancel.setOnClickListener(new View.OnClickListener() {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == 2222){
            if(requestCode == 2222){

                if(data != null){
                    postCode = data.getStringExtra("postCode"); //newStaffMemberId
                    if(edt_order_id != null){
                        edt_order_id.setText(postCode);
                    }
                }
            }
        }else {
            postCode = "";
        }
    }
}
