package com.zwx.scan.app.feature.financialaffairs;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.data.bean.CollectionFlowResultBean;
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionNoToArriveAmtFragment extends BaseFragment<FinancialAffairsContract.Presenter> implements FinancialAffairsContract.View,PullToRefreshBase.OnRefreshListener2<ScrollView>  {
    @BindView(R.id.list_view)
    public MyListView listView;

    @BindView(R.id.ptr)
    protected PullToRefreshScrollView ptr;


    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;

    @BindView(R.id.tv_order_num)
    protected TextView tv_order_num;

    protected String userId;
    protected int pageNumber = 1;
    protected int pageSize = 10;

    protected  String flag; //查询标志 T-明日到账，F-未到账
    protected  String total;
    protected List<TOrderObject> orderList= new ArrayList<>();
    protected CollectionIntoAmtOrderAdapter adapter = null;

    public CollectionNoToArriveAmtFragment() {
        // Required empty public constructor
    }

    public static CollectionNoToArriveAmtFragment getInstance(String param) {
        CollectionNoToArriveAmtFragment instance = new CollectionNoToArriveAmtFragment();
        instance.flag = param;
        return instance;
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_collection_no_to_arrive_amt;
    }

    @Override
    protected void initInjector() {
        DaggerFinancialAffairsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .financialAffairsModule(new FinancialAffairsModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initView() {


        userId = SPUtils.getInstance().getString("userId");

        adapter = new CollectionIntoAmtOrderAdapter(getActivity(), orderList,flag);
        listView.setAdapter(adapter);
        ptr.setOnRefreshListener(this);
        ptr.setMode(PullToRefreshBase.Mode.BOTH);
        ll_no_data.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        ILoadingLayout endLabelsr = ptr.getLoadingLayoutProxy(false, true);

        endLabelsr.setPullLabel("上拉可以加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("加载中");// 刷新时
        endLabelsr.setReleaseLabel("松开加载更多");// 下来达到一定距离时，显示的提示


        ILoadingLayout startLabelse = ptr.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉可以刷新");// 刚下拉时，显示的提示
        startLabelse.setLastUpdatedLabel("正在刷新");// 刷新时
        startLabelse.setReleaseLabel("松开后刷新");

        presenter.queryWxPayList(getActivity(),userId,flag,pageNumber,pageSize);
//        presenter.selectUnSettlementCollectionStatementList(getActivity(), userId,pageNumber, pageSize);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CollectionToAccountOrderDetailActivity.class);
                if (orderList != null && orderList.size() > 0) {
                    TOrderObject order = orderList.get(position);
                    intent.putExtra("orderId",order.getOrderId()+"");
                    intent.putExtra("order",order);
                    intent.putExtra("flag",flag);
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right, R.anim.slide_out_left);
                }

            }
        });
    }

    @Override
    protected void initData() {



    }



    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        orderList.clear();
        pageNumber = 1;
        presenter.queryWxPayList(getActivity(),userId,flag,pageNumber,pageSize);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.queryWxPayList(getActivity(),userId,flag,pageNumber,pageSize);

    }
}
