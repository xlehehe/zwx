package com.zwx.scan.app.feature.financialaffairs;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.TOrder;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.widget.MyListView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * author : lizhilong
 * time   : 2019/05/30
 * desc   :  会员卡员工销售报表详情列表的具体详情
 * version: 1.0
 **/
public class MemCardEmploeeSaleReportDetailListActivity extends BaseActivity<FinancialAffairsContract.Presenter> implements FinancialAffairsContract.View , View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ScrollView>  {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_employee_name)
    protected TextView tv_employee_name;
    @BindView(R.id.tv_phone)
    protected TextView tv_phone;
    @BindView(R.id.tv_time)
    protected TextView tv_time;
    @BindView(R.id.tv_store_name)
    protected TextView tv_store_name;


    @BindView(R.id.tv_member_card_label)
    protected TextView tv_member_card_label;

    @BindView(R.id.tv_member_card_name)
    protected TextView tv_member_card_name;

    //销售总额
    @BindView(R.id.tv_sale_amt)
    protected TextView tv_sale_amt;

    //商品名称 和编码
   /* @BindView(R.id.rl_product)
    protected RelativeLayout rl_product;
    @BindView(R.id.tv_product_name)
    protected TextView tv_product_name;
*/
    @BindView(R.id.tv_product_code)
    protected TextView tv_product_code;

    //支付金额 红包金额
    @BindView(R.id.rl_pay_red_amt)
    protected RelativeLayout rl_pay_red_amt;
    @BindView(R.id.tv_pay_amt)
    protected TextView tv_pay_amt;

    @BindView(R.id.tv_red_amt)
    protected TextView tv_red_amt;


    @BindView(R.id.list_view)
    public MyListView listView;

    @BindView(R.id.ptr)
    public PullToRefreshScrollView ptr;
    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;
    @BindView(R.id.iv_no_data)
    protected ImageView iv_no_data;

    private int pageNumber = 1;
    private int pageSize = 10;
    private String campaignName="";
    private String salesTimeSta;
    private String salesTimesEnd;
    public MemCardEmploeeSaleReportListDetailDetailListAdapter listAdapter;
    protected List<TOrder> orderList = new ArrayList<TOrder>();
    private String storeSelectType;
    private String storeName = "";
    //订单类型 购卡M 拼团G  商城商品P
    protected String orderType = "M";
    private String userId;

    protected  String startDate;
    protected String endDate;

    private String orderTypeName;
    private String productCode;
    private TOrder order = new TOrder();
    private String storeStr;
    private String productId;
    private String staffId;
    private String staffName;
    private String staffTel;
    private String saleAmt;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_mem_card_emploee_sale_report_detail_list;
    }

    @Override
    protected void initView() {
        DaggerFinancialAffairsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .financialAffairsModule(new FinancialAffairsModule(this))
                .build()
                .inject(this);
        tvTitle.setText("销售详情");
    }

    @Override
    protected void initData() {

        storeName = getIntent().getStringExtra("storeName");
        userId = SPUtils.getInstance().getString("userId");
        startDate = getIntent().getStringExtra("salesTimeSta");
        endDate = getIntent().getStringExtra("salesTimesEnd");
        orderType = getIntent().getStringExtra("orderType");
        storeStr = getIntent().getStringExtra("storeStr");
        order = (TOrder)getIntent().getSerializableExtra("order");
        storeSelectType=getIntent().getStringExtra("storeSelectType");

        if("M".equals(orderType)){
            tv_product_code.setVisibility(View.GONE);
            rl_pay_red_amt.setVisibility(View.GONE);
        }else if("G".equals(orderType)){
            tv_product_code.setVisibility(View.GONE);
            rl_pay_red_amt.setVisibility(View.GONE);
            tv_member_card_label.setText("拼团名称：");
        }else if("P".equals(orderType)){
            rl_pay_red_amt.setVisibility(View.VISIBLE);
            tv_member_card_label.setText("商品名称：");
            tv_product_code.setVisibility(View.VISIBLE);

        }

        if(order != null){
            orderTypeName = order.getOrderTypeName();

            if(order.getProductId() != null){
                productId = String.valueOf(order.getProductId());
            }

            if(order.getStaffId() != null){
                staffId = String.valueOf(order.getStaffId());
            }
            staffName = order.getStaffName();
            if(staffName!= null && !"".equals(staffName)){
               tv_employee_name.setText(staffName);
            }

            staffTel = order.getStaffTel();
            if(staffTel!= null && !"".equals(staffTel)){
                tv_phone.setText(staffTel);
            }
            String storeName = order.getStoreName();
            if(storeName!= null && !"".equals(storeName)){
                tv_store_name.setText(storeName);
            }

            BigDecimal amount = order.getAmount();
            String am = "—";
            if( amount != null && amount.doubleValue()>0){
                am = RegexUtils.getDoubleString(amount.doubleValue())+"元";
            }
            tv_sale_amt.setText(am);

            //商品名称和编码

            productCode = order.getProductCode();
            if(productCode != null && !"".equals(productCode)){
                tv_product_code.setText(productCode);
            }

            BigDecimal payAmt = order.getUseCashAmount();
            String payAm = "—";
            if( payAmt != null && payAmt.doubleValue()>0){
                payAm = RegexUtils.getDoubleString(payAmt.doubleValue())+"元";
            }
            tv_pay_amt.setText(payAm);


            BigDecimal redAmt = order.getUseRedEnvelopeAmount();
            String reAm = "—";
            if( redAmt != null && redAmt.doubleValue()>0){
                reAm = RegexUtils.getDoubleString(redAmt.doubleValue())+"元";
            }
            tv_red_amt.setText(reAm);

        }

        tv_member_card_name.setText(orderTypeName != null &&!"".equals(orderTypeName)?orderTypeName:"—");

        tv_time.setText(startDate+"-"+endDate);


        if(startDate != null && !"".equals(startDate)){
            salesTimeSta = startDate.replace(".","-");
        }
        if(endDate != null && !"".equals(endDate)){
            salesTimesEnd = endDate.replace(".","-");
        }

        listAdapter = new MemCardEmploeeSaleReportListDetailDetailListAdapter(MemCardEmploeeSaleReportDetailListActivity.this, orderList);

        listView.setAdapter(listAdapter);
        ptr.setOnRefreshListener(this);
        ptr.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout endLabelsr = ptr.getLoadingLayoutProxy(false, true);

        endLabelsr.setPullLabel("上拉可以加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("加载中");// 刷新时
        endLabelsr.setReleaseLabel("松开加载更多");// 下来达到一定距离时，显示的提示


        ILoadingLayout startLabelse = ptr.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉可以刷新");// 刚下拉时，显示的提示
        startLabelse.setLastUpdatedLabel("正在刷新");// 刷新时
        startLabelse.setReleaseLabel("松开后刷新");

        ll_no_data.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);

        presenter.selectSalesDetailsByEmployee(MemCardEmploeeSaleReportDetailListActivity.this, userId, salesTimeSta,salesTimesEnd,"",storeStr,storeSelectType,orderType, productId,staffId,pageNumber, pageSize);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* Intent intent = new Intent(MemCardEmploeeSaleReportDetailActivity.this, MemCardEmploeeSaleReportDetailActivity.class);
                if (orderList != null && orderList.size() > 0) {
                    TOrder order = orderList.get(position);
                    intent.putExtra("orderId",order.getOrderId()+"");
                    intent.putExtra("storeSelectType",storeSelectType);
                    intent.putExtra("orderType",orderType);
                    intent.putExtra("order",order);
                    intent.putExtra("salesTimeSta",startDate);
                    intent.putExtra("salesTimesEnd",endDate);
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right, R.anim.slide_out_left);
                }*/

            }
        });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber = 1;
        orderList.clear();
        presenter.selectSalesDetailsByEmployee(MemCardEmploeeSaleReportDetailListActivity.this, userId, salesTimeSta,salesTimesEnd,"",storeStr,storeSelectType,orderType, productId,staffId,pageNumber, pageSize);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.selectSalesDetailsByEmployee(MemCardEmploeeSaleReportDetailListActivity.this, userId, salesTimeSta,salesTimesEnd,"",storeStr,storeSelectType,orderType, productId,staffId,pageNumber, pageSize);
    }

    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.finishActivity(MemCardEmploeeSaleReportDetailListActivity.class,
                        R.anim.slide_in_left,R.anim.slide_out_right);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(MemCardEmploeeSaleReportDetailListActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
    }

}
