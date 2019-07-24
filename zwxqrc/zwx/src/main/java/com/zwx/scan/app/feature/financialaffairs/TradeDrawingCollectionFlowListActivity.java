package com.zwx.scan.app.feature.financialaffairs;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.widget.MyListView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/05/09
 * desc   :  交易提款列表
 * version: 1.0
 **/
public class TradeDrawingCollectionFlowListActivity extends BaseActivity<FinancialAffairsContract.Presenter> implements FinancialAffairsContract.View,View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ScrollView>  {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.tv_right)
    protected TextView tvRight;
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.list_view)
    public MyListView listView;

    @BindView(R.id.ptr)
    protected PullToRefreshScrollView ptr;


    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;


    protected String userId;
    protected int pageNumber = 1;
    protected int pageSize = 10;

    protected List<TOrderObject> orderList= new ArrayList<>();
    protected TradeDrawingListAdapter adapter = null;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_trade_drawing_collection_flow_list;
    }

    @Override
    protected void initView() {

        DaggerFinancialAffairsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .financialAffairsModule(new FinancialAffairsModule(this))
                .build()
                .inject(this);

        tvTitle.setText("待结算收款流水");
    }

    @Override
    protected void initData() {


        userId = SPUtils.getInstance().getString("userId");

        TOrderObject order = new TOrderObject();
        order.setOrderCode(201806120253L);
        order.setPayWay("w");
        order.setProductName("菠萝西红柿炖鱼头、美味调料激情辣酱…");
        order.setMemberTel("12345678909");
        order.setSalesTime("2019.01.30 23:30:30");
        order.setPayAmount(new BigDecimal(50));
        order.setUseRedEnvelope(new BigDecimal(50));
        order.setBuyMsg("-");
        order.setBuyCount(5L);
        orderList.add(order);
        order = new TOrderObject();
        order.setOrderCode(201806120253L);
        order.setPayWay("w");
        order.setProductName("菠萝西红柿炖鱼头、美味调料激情辣酱…");
        order.setMemberTel("12345678909");
        order.setSalesTime("2019.01.30 23:30:30");
        order.setPayAmount(new BigDecimal(50));
        order.setUseRedEnvelope(new BigDecimal(50));
        order.setBuyMsg("-");
        order.setBuyCount(5L);
        orderList.add(order);
        order = new TOrderObject();
        order.setOrderCode(201806120253L);
        order.setPayWay("w");
        order.setProductName("菠萝西红柿炖鱼头、美味调料激情辣酱…");
        order.setMemberTel("12345678909");
        order.setSalesTime("2019.01.30 23:30:30");
        order.setPayAmount(new BigDecimal(50));
        order.setUseRedEnvelope(new BigDecimal(50));
        order.setBuyMsg("-");
        order.setBuyCount(5L);
        orderList.add(order);
        order = new TOrderObject();
        order.setOrderCode(201806120253L);
        order.setPayWay("w");
        order.setProductName("菠萝西红柿炖鱼头、美味调料激情辣酱…");
        order.setMemberTel("12345678909");
        order.setSalesTime("2019.01.30 23:30:30");
        order.setPayAmount(new BigDecimal(50));
        order.setUseRedEnvelope(new BigDecimal(50));
        order.setBuyMsg("-");
        order.setBuyCount(5L);
        orderList.add(order);
        order = new TOrderObject();
        order.setOrderCode(201806120253L);
        order.setPayWay("w");
        order.setProductName("菠萝西红柿炖鱼头、美味调料激情辣酱…");
        order.setMemberTel("12345678909");
        order.setSalesTime("2019.01.30 23:30:30");
        order.setPayAmount(new BigDecimal(50));
        order.setUseRedEnvelope(new BigDecimal(50));
        order.setBuyMsg("-");
        order.setBuyCount(5L);
        orderList.add(order);
        order = new TOrderObject();
        order.setOrderCode(201806120253L);
        order.setPayWay("w");
        order.setProductName("菠萝西红柿炖鱼头、美味调料激情辣酱…");
        order.setMemberTel("12345678909");
        order.setSalesTime("2019.01.30 23:30:30");
        order.setPayAmount(new BigDecimal(50));
        order.setUseRedEnvelope(new BigDecimal(50));
        order.setBuyMsg("-");
        order.setBuyCount(5L);
        orderList.add(order);
        order = new TOrderObject();
        order.setOrderCode(201806120253L);
        order.setPayWay("w");
        order.setProductName("菠萝西红柿炖鱼头、美味调料激情辣酱…");
        order.setMemberTel("12345678909");
        order.setSalesTime("2019.01.30 23:30:30");
        order.setPayAmount(new BigDecimal(50));
        order.setUseRedEnvelope(new BigDecimal(50));
        order.setBuyMsg("-");
        order.setBuyCount(5L);
        orderList.add(order);
        adapter = new TradeDrawingListAdapter(TradeDrawingCollectionFlowListActivity.this, orderList);
        listView.setAdapter(adapter);
        ptr.setOnRefreshListener(this);
        ptr.setMode(PullToRefreshBase.Mode.BOTH);
        ll_no_data.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        ILoadingLayout endLabelsr = ptr.getLoadingLayoutProxy(false, true);

        endLabelsr.setPullLabel("上拉可以加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("加载中");// 刷新时
        endLabelsr.setReleaseLabel("松开加载更多");// 下来达到一定距离时，显示的提示


        ILoadingLayout startLabelse = ptr.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉可以刷新");// 刚下拉时，显示的提示
        startLabelse.setLastUpdatedLabel("正在刷新");// 刷新时
        startLabelse.setReleaseLabel("松开后刷新");


//        presenter.(ShopOrderActivity.this, userId,memberTel, salesTimeSta,salesTimesEnd,pageNumber, pageSize,true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TradeDrawingCollectionFlowListActivity.this, TradeDrawingCollectionFlowDetailActivity.class);
                if (orderList != null && orderList.size() > 0) {
                    TOrderObject order = orderList.get(position);
                    intent.putExtra("orderId",order.getOrderId()+"");
                    intent.putExtra("order",order);
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right, R.anim.slide_out_left);
                }

            }
        });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber = 1;
        adapter.notifyDataSetChanged();
        if (orderList.size() < 10) {
            ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        } else {
            ptr.setMode(PullToRefreshBase.Mode.BOTH);
        }
        ptr.onRefreshComplete();
//        presenter.doQueryOrder(ShopOrderActivity.this, userId,memberTel, salesTimeSta,salesTimesEnd,pageNumber, pageSize,true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        adapter.notifyDataSetChanged();
        if (orderList.size() < 10) {
            ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        } else {
            ptr.setMode(PullToRefreshBase.Mode.BOTH);
        }
        ptr.onRefreshComplete();
//        presenter.doQueryOrder(ShopOrderActivity.this, userId,memberTel, salesTimeSta,salesTimesEnd,pageNumber, pageSize,false);

    }

    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(TradeDrawingCollectionFlowListActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(TradeDrawingCollectionFlowListActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
