package com.zwx.scan.app.feature.financialaffairs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.CollectionFlowDetailResultBean;
import com.zwx.scan.app.data.bean.OrderCollect;
import com.zwx.scan.app.data.bean.TOrder;
import com.zwx.scan.app.data.bean.TOrderDetails;
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.data.bean.TOrderPay;
import com.zwx.scan.app.data.bean.TransferRecordBean;
import com.zwx.scan.app.feature.AppContext;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * author : lizhilong
 * time   : 2019/05/22
 * desc   : 收款到账详情
 * version: 1.0
 **/
public class CollectionToAccountOrderDetailActivity extends BaseActivity<FinancialAffairsContract.Presenter> implements FinancialAffairsContract.View,View.OnClickListener {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;


    @BindView(R.id.tv_right)
    protected TextView tv_right;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;


    @BindView(R.id.rl_to_time)
    protected RelativeLayout rl_to_time;

    @BindView(R.id.tv_to_time)
    protected TextView tv_to_time;
    //订单编号
    @BindView(R.id.tv_order_code)
    protected TextView tv_order_code;

    //购买类型
    @BindView(R.id.tv_buy_type)
    protected TextView tv_buy_type;
    @BindView(R.id.tv_buy_content)
    protected TextView tv_buy_content;


    @BindView(R.id.tv_sale_time)
    protected TextView tv_sale_time;


    @BindView(R.id.tv_buy_phone)
    protected TextView tv_buy_phone;

    @BindView(R.id.tv_sale_emploee)
    protected TextView tv_sale_emploee;

    @BindView(R.id.tv_tuijian_buy_phone)
    protected TextView tv_tuijian_buy_phone;

    @BindView(R.id.tv_pay_amt)
    protected TextView tv_pay_amt;

    @BindView(R.id.tv_red_packget_dixian)
    protected TextView tv_red_packget_dixian;

    @BindView(R.id.tv_buy_msg)
    protected TextView tv_buy_msg;

    //商品详情
    @BindView(R.id.ll_product_detail)
    protected LinearLayout ll_product_detail;

    //微信 支付宝银联
    @BindView(R.id.tv_pay_way)
    protected TextView tv_pay_way;

    @BindView(R.id.tv_pay_way_time)
    protected TextView tv_pay_way_time;

    //付款金额
    @BindView(R.id.tv_fu_amt)
    protected TextView tv_fu_amt;

    //商户订单号
    @BindView(R.id.tv_sh_order_code)
    protected TextView tv_sh_order_code;

    //微信支付订单号
    @BindView(R.id.tv_wx_order_code)
    protected TextView tv_wx_order_code;


    //支付方式的付款金额
    @BindView(R.id.tv_pay_red_way)
    protected TextView tv_pay_red_way;
    //红包支付
    @BindView(R.id.tv_red_packget_time)
    protected TextView tv_red_packget_time;

    //底线金额
    @BindView(R.id.tv_red_pack_dixian)
    protected TextView tv_red_pack_dixian;

    @BindView(R.id.ll_product_details)
    protected LinearLayout ll_product_details;

    @BindView(R.id.ll_w_pay)
    protected LinearLayout ll_w_pay;
    @BindView(R.id.ll_r_pay)
    protected LinearLayout ll_r_pay;

    @BindView(R.id.ll_goumai_content)
    protected LinearLayout ll_goumai_content;


    protected String userId;
    protected String orderId;

    protected String flag; //T 预计明天到账 // F未到账

    protected List<TOrderDetails> orderDetail = new ArrayList<>();

    protected OrderCollect ordercollect = new OrderCollect();

    protected List<TOrderPay> orderPay = new ArrayList<>();

    protected TOrder orderinfo = new TOrder();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection_to_account_order_detail;
    }

    @Override
    protected void initView() {
        DaggerFinancialAffairsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .financialAffairsModule(new FinancialAffairsModule(this))
                .build()
                .inject(this);

        tvTitle.setText("详情说明");
        ll_product_details.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {

        userId = SPUtils.getInstance().getString("userId");
        orderId = getIntent().getStringExtra("orderId");
        flag = getIntent().getStringExtra("flag");

        if("T".equals(flag)){
            rl_to_time.setVisibility(View.GONE);
        }else {
            rl_to_time.setVisibility(View.VISIBLE);
        }
        presenter.queryOrderDetailForwxPay(this,orderId);
    }

    protected  void setPoductDetail(){
        ll_product_detail.removeAllViews();
        if(orderDetail != null &&  orderDetail.size()>0){
            for(int i = 0;i<orderDetail.size();i++){
                View view = null;
                view = LayoutInflater.from(this).inflate(R.layout.layout_trad_drawing_detail_product_detail, null);
                TextView tv_buy_content = (TextView) view.findViewById(R.id.tv_buy_content);
                TextView tv_code = (TextView) view.findViewById(R.id.tv_code);
                TextView tv_buy_count = (TextView) view.findViewById(R.id.tv_buy_count);
                TextView tv_unit_price = (TextView) view.findViewById(R.id.tv_unit_price);
                TextView tv_product_spec = (TextView) view.findViewById(R.id.tv_product_spec);

                TOrderDetails  detailsBean= orderDetail.get(i);
                if(detailsBean != null){
                    String buyContent = detailsBean.getProductName();
                    String code = detailsBean.getProductCode();
                    String buyCount = "—";
                    if( detailsBean.getBuyCount() != null &&  detailsBean.getBuyCount().intValue()>0){
                        buyCount = String.valueOf(detailsBean.getBuyCount().intValue());
                    }

                    String unitPrice = "—";

                    if( detailsBean.getUnitPrice() != null &&  detailsBean.getUnitPrice().doubleValue()>0){
                        unitPrice = RegexUtils.getDoubleString(detailsBean.getUnitPrice().doubleValue());
                    }

                    String productSpec = "—";
                    if(detailsBean.getProductSpec() != null && !"".equals(detailsBean.getProductSpec())){
                        productSpec = detailsBean.getProductSpec();
                    }

                    tv_buy_content.setText(buyContent!= null && !"".equals(buyContent)?buyContent:"—");
                    tv_code.setText(code!= null && !"".equals(code)?code:"—");
                    tv_unit_price.setText(unitPrice);
                    tv_buy_count.setText(buyCount);
                    tv_product_spec.setText(productSpec);
                }


                ll_product_detail.addView(view);
            }
        }
    }


    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(CollectionToAccountOrderDetailActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(CollectionToAccountOrderDetailActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
