package com.zwx.scan.app.feature.financialaffairs;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.TOrder;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.member.MemberCardStreamDetailActivity;
import com.zwx.scan.app.widget.MyListView;
import com.zwx.scan.app.widget.PhilExpandableTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * author : lizhilong
 * time   : 2019/05/30
 * desc   :  会员卡员工销售报表详情
 * version: 1.0
 **/
public class MemCardEmploeeSaleReportDetailActivity extends BaseActivity<FinancialAffairsContract.Presenter> implements FinancialAffairsContract.View , View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ScrollView>  {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;



    @BindView(R.id.tv_mamber_card_name_label)
    protected TextView tv_mamber_card_name_label;


    @BindView(R.id.tv_mamber_card_name)
    protected TextView tv_mamber_card_name;


    @BindView(R.id.ll_title)
    protected RelativeLayout ll_title;

    @BindView(R.id.iv_brand)
    protected ImageView ivBrand;
    @BindView(R.id.tv_store_name)
    protected PhilExpandableTextView tvStoreNames;

    @BindView(R.id.tv_time)
    protected TextView tv_time;

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

    @BindView(R.id.tv_product_code)
    protected TextView tv_product_code;

    private int pageNumber = 1;
    private int pageSize = 10;
    private String campaignName="";
    private String salesTimeSta;
    private String salesTimesEnd;
    public MemCardEmploeeSaleReportListDetailAdapter listAdapter;
    protected List<TOrder> orderList = new ArrayList<TOrder>();

    private String storeSelectType = "A";
    private String storeStr = "";
    private String storeName = "";
    //订单类型 购卡M 拼团G  商城商品P
    protected String orderType = "M";
    private String userId;

    protected  String startDate;
    protected String endDate;
    private String brandLogo;
    private String productId;
    private String staffId;
    private String orderTypeName;
    private TOrder order = new TOrder();

    private String title;
    private String productCode;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_mem_card_emploee_sale_report_detail;
    }

    @Override
    protected void initView() {

        DaggerFinancialAffairsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .financialAffairsModule(new FinancialAffairsModule(this))
                .build()
                .inject(this);
        tvTitle.setText("会员卡员工销售详情");
    }

    @Override
    protected void initData() {
        storeStr = getIntent().getStringExtra("storeId");
        storeName = getIntent().getStringExtra("storeName");
        userId = SPUtils.getInstance().getString("userId");
        startDate = getIntent().getStringExtra("salesTimeSta");
        endDate = getIntent().getStringExtra("salesTimesEnd");
        orderType = getIntent().getStringExtra("orderType");
        storeStr = getIntent().getStringExtra("storeStr");
        order = (TOrder)getIntent().getSerializableExtra("order");
        storeSelectType = getIntent().getStringExtra("storeSelectType");

        if("M".equals(orderType)){
            title = "会员卡员工销售详情";
            tv_product_code.setVisibility(View.GONE);
        }else if("G".equals(orderType)){
            title = "拼团员工销售详情";
            tv_product_code.setVisibility(View.GONE);
            tv_mamber_card_name_label.setText("拼团名称：");
        }else if("P".equals(orderType)){
            title = "商品卡员工销售详情";
            tv_mamber_card_name_label.setText("商品名称：");
            tv_product_code.setVisibility(View.VISIBLE);

        }

        tvTitle.setText(title);

    /*    if("A".equals(storeSelectType)){
            tvStoreNames.setText("#全部店铺");
        }else if("J".equals(storeSelectType)){
            tvStoreNames.setText("#全部加盟");
        }else if("D".equals(storeSelectType)){
            tvStoreNames.setText("#全部自营");
        }else{
            tvStoreNames.setText(storeName);
        }*/

        tvStoreNames.setText(storeName);
        if(order != null){
            orderTypeName = order.getOrderTypeName();

            if(order.getProductId() != null){
                productId = String.valueOf(order.getProductId());
            }

            productCode = order.getProductCode();

            if(productCode != null && !"".equals(productCode)){
                tv_product_code.setText(productCode);
            }


        }

        tv_mamber_card_name.setText(orderTypeName != null &&!"".equals(orderTypeName)?orderTypeName:"");

        tv_time.setText(startDate+"-"+endDate);


        brandLogo =SPUtils.getInstance().getString("brandLogo");
        setBrandLogo();
        tvStoreNames.setText(storeName);
        if(startDate != null && !"".equals(startDate)){
            salesTimeSta = startDate.replace(".","-");
        }
        if(endDate != null && !"".equals(endDate)){
            salesTimesEnd = endDate.replace(".","-");
        }



        listAdapter = new MemCardEmploeeSaleReportListDetailAdapter(MemCardEmploeeSaleReportDetailActivity.this, orderList);
//        rvList.setLayoutManager(new LinearLayoutManager(this));
//        rvList.addItemDecoration(new SpacesItemDecoration(20));
//        rvList.setAdapter(listAdapter);
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

//        presenter.selectSalesDetailsByEmployee(MemCardEmploeeSaleReportDetailActivity.this, userId, salesTimeSta,salesTimesEnd,"",storeStr,storeSelectType,orderType, productId,staffId,pageNumber, pageSize);
        presenter.selectSalesListByEmployee(MemCardEmploeeSaleReportDetailActivity.this, userId, salesTimeSta,salesTimesEnd,"",storeStr,storeSelectType,orderType, productId,staffId,"",pageNumber, pageSize);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MemCardEmploeeSaleReportDetailActivity.this, MemCardEmploeeSaleReportDetailListActivity.class);
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
                }

            }
        });

    }

    private void setBrandLogo(){
        RoundedCorners roundedCorners= new RoundedCorners(8);

        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_logo_default)
                .error(R.drawable.ic_logo_default)
                .fallback(R.drawable.ic_logo_default);

        Glide.with(this).load(brandLogo).apply(requestOptions).into(ivBrand);
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        orderList.clear();
        pageNumber = 1;
        presenter.selectSalesListByEmployee(MemCardEmploeeSaleReportDetailActivity.this, userId, salesTimeSta,salesTimesEnd,"",storeStr,storeSelectType,orderType, productId,staffId,"",pageNumber, pageSize);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.selectSalesListByEmployee(MemCardEmploeeSaleReportDetailActivity.this, userId, salesTimeSta,salesTimesEnd,"",storeStr,storeSelectType,orderType, productId,staffId,"",pageNumber, pageSize);
    }


    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.finishActivity(MemCardEmploeeSaleReportDetailActivity.class,
                        R.anim.slide_in_left,R.anim.slide_out_right);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(MemCardEmploeeSaleReportDetailActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
    }



}
