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
import com.zwx.scan.app.data.bean.CollectionFlowResultBean;
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
 * desc   :  代收款流水
 * version: 1.0
 **/
public class CollectionFlowListActivity extends BaseActivity<FinancialAffairsContract.Presenter> implements FinancialAffairsContract.View,View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ScrollView> {
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


    protected List<CollectionFlowResultBean.CollectionFlowBean> collectionFlowBeanList = new ArrayList<>();
    protected CollectionFlowListAdapter adapter = null;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection_flow_list;
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

        adapter = new CollectionFlowListAdapter(CollectionFlowListActivity.this, collectionFlowBeanList);
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


        presenter.selectUnSettlementCollectionStatementList(this, userId,pageNumber, pageSize);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CollectionFlowListActivity.this, TradeDrawingCollectionFlowDetailActivity.class);
                if (collectionFlowBeanList != null && collectionFlowBeanList.size() > 0) {
                    CollectionFlowResultBean.CollectionFlowBean flowBean = collectionFlowBeanList.get(position);
                    intent.putExtra("orderId",flowBean.getOrderId()+"");
                    intent.putExtra("flowBean",flowBean);
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right, R.anim.slide_out_left);
                }

            }
        });
    }



    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(CollectionFlowListActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;


        }
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        collectionFlowBeanList.clear();
        pageNumber = 1;
        presenter.selectUnSettlementCollectionStatementList(this, userId,pageNumber, pageSize);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.selectUnSettlementCollectionStatementList(this, userId,pageNumber, pageSize);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(CollectionFlowListActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
