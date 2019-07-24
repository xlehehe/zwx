package com.zwx.scan.app.feature.financialaffairs;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.CollectionFlowDetailResultBean;
import com.zwx.scan.app.data.bean.OrderCollect;
import com.zwx.scan.app.data.bean.TOrder;
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.data.bean.TOrderPay;
import com.zwx.scan.app.feature.AppContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * author : lizhilong
 * time   : 2019/05/09
 * desc   :  交易提款列表详情
 * version: 1.0
 **/
public class TradeDrawingCollectionFlowDetailActivity extends BaseActivity<FinancialAffairsContract.Presenter> implements FinancialAffairsContract.View,View.OnClickListener {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;


    @BindView(R.id.tv_right)
    protected TextView tv_right;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;


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


    protected String userId;
    protected String orderId;

    protected String productName;



    protected CollectionFlowDetailResultBean.CollectionFlowBean orderDetails = new CollectionFlowDetailResultBean.CollectionFlowBean();

    protected List<CollectionFlowDetailResultBean.PurchaseDetailsBean> purchaseDetails = new ArrayList<>();

    protected List<CollectionFlowDetailResultBean.OrderPayDetailsBean> orderPayDetails = new ArrayList<>();

    protected CollectionFlowDetailResultBean.PaymentDetailsBean paymentDetails = new CollectionFlowDetailResultBean.PaymentDetailsBean();

    protected TOrder orderInfo = new TOrder();

    protected OrderCollect orderCollect = new OrderCollect();
    protected List<TOrderPay> orderPay = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_trade_drawing_collection_flowdetail;
    }

    @Override
    protected void initView() {
        DaggerFinancialAffairsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .financialAffairsModule(new FinancialAffairsModule(this))
                .build()
                .inject(this);

        tvTitle.setText("详情说明");


    }

    @Override
    protected void initData() {
        userId = SPUtils.getInstance().getString("userId");
        orderId = getIntent().getStringExtra("orderId");
        /*TOrderObject order = new TOrderObject();
        order.setOrderCode(201806120253L);
        order.setPayWay("w");
        order.setProductName("请卖家快点发货，我要定\n" +
                "制名侦探柯南里面灰原哀\n" +
                "主题的会员卡，没有灰原\n" +
                "哀就要怪盗基德主题的会\n" +
                "员卡，谢谢！");
        order.setMemberTel("12345678909");
        order.setSalesTime("2019.01.30 23:30:30");
        order.setPayAmount(new BigDecimal(50));
        order.setUseRedEnvelope(new BigDecimal(50));
        order.setBuyMsg("购买内容");
        order.setBuyCount(5L);
        order.setProductCode("G4567");
        order.setUnitPrice(new BigDecimal(50));
        orderList.add(order);
        order = new TOrderObject();
        order.setOrderCode(201806120253L);
        order.setPayWay("w");
        order.setProductName("请卖家快点发货，我要定\n" +
                "制名侦探柯南里面灰原哀\n" +
                "主题的会员卡，没有灰原\n" +
                "哀就要怪盗基德主题的会\n" +
                "员卡，谢谢！");
        order.setMemberTel("12345678909");
        order.setSalesTime("2019.01.30 23:30:30");
        order.setPayAmount(new BigDecimal(50));
        order.setUseRedEnvelope(new BigDecimal(50));
        order.setBuyMsg("购买内容");
        order.setBuyCount(5L);
        order.setProductCode("G4567");
        order.setUnitPrice(new BigDecimal(50));
        orderList.add(order);


//        ll_product_detail.removeAllViews();
        if(orderList != null &&  orderList.size()>0){
            for(int i = 0;i<orderList.size();i++){
                View view = null;
                view = LayoutInflater.from(this).inflate(R.layout.layout_trad_drawing_detail_product_detail, null);
                TextView tv_buy_content = (TextView) view.findViewById(R.id.tv_buy_content);
                TextView tv_code = (TextView) view.findViewById(R.id.tv_code);
                TextView tv_buy_count = (TextView) view.findViewById(R.id.tv_buy_count);
                TextView tv_unit_price = (TextView) view.findViewById(R.id.tv_unit_price);
                TextView tv_product_spec = (TextView) view.findViewById(R.id.tv_product_spec);

                String buyContent = order.getBuyMsg();
                String code = order.getProductCode();
                String buyCount = "—";
                if( order.getBuyCount() != null &&  order.getBuyCount().intValue()>0){
                    buyCount = String.valueOf(order.getBuyCount().intValue());
                }

                String unitPrice = "—";

                if( order.getUnitPrice() != null &&  order.getUnitPrice().doubleValue()>0){
                    unitPrice = RegexUtils.getDoubleString(order.getUnitPrice().doubleValue());
                }

                String productSpec = "—";
                if(order.getBuyMsg() != null && "".equals(order.getBuyMsg())){
                    productSpec = order.getProductName();
                }

                tv_buy_content.setText(buyContent!= null && !"".equals(buyContent)?buyContent:"—");
                tv_code.setText(code!= null && !"".equals(code)?code:"—");
                tv_unit_price.setText(unitPrice);
                tv_buy_count.setText(buyCount);
                tv_product_spec.setText(productSpec);
                ll_product_detail.addView(view);
            }
        }*/


        presenter.selectCollectionStatementDetailsByType(this,userId,orderId);


    }

    protected  void setPoductDetail(){
        if(purchaseDetails != null &&  purchaseDetails.size()>0){
            for(int i = 0;i<purchaseDetails.size();i++){
               View view = null;
                view = LayoutInflater.from(this).inflate(R.layout.layout_trad_drawing_detail_product_detail, null);
                TextView tv_buy_content = (TextView) view.findViewById(R.id.tv_buy_content);
                TextView tv_code = (TextView) view.findViewById(R.id.tv_code);
                TextView tv_buy_count = (TextView) view.findViewById(R.id.tv_buy_count);
                TextView tv_unit_price = (TextView) view.findViewById(R.id.tv_unit_price);
                TextView tv_product_spec = (TextView) view.findViewById(R.id.tv_product_spec);

                CollectionFlowDetailResultBean.PurchaseDetailsBean detailsBean  = purchaseDetails.get(i);
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
                    if(detailsBean.getProductSpec() != null && "".equals(detailsBean.getProductSpec())){
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
                ActivityUtils.finishActivity(TradeDrawingCollectionFlowDetailActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(TradeDrawingCollectionFlowDetailActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
