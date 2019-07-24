package com.zwx.scan.app.feature.ptmanage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.GroupBuyCampagin;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PtOrderDetailActivity extends BaseActivity<PtContract.Presenter> implements PtContract.View,View.OnClickListener  {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;


    @BindView(R.id.tv_campaign_name)
    protected TextView tv_campaign_name;

    @BindView(R.id.tv_time)
    protected TextView tv_time;
    @BindView(R.id.rv_list)
    public RecyclerView rvList;
    @BindView(R.id.ptr)
    public PtrClassicFrameLayout ptr;
    private int pageNumber = 1;
    private int pageSize = 10;
    private String storeId;
    private String campaignId;
    private String  campaignName;
    private String  salesTime;
    public PtOrderListDetailAdapter listAdapter;
    public RecyclerAdapterWithHF mAdapter;
    public List<GroupBuyCampagin> groupBuyCampaginList = new ArrayList<>();

    private String salesTimeSta, salesTimesEnd;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pt_order_detail;
    }

    @Override
    protected void initView() {
        DaggerPtComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .ptModule(new PtModule(this))
                .build()
                .inject(this);
        tvTitle.setText("拼团详情");

        initPtr();

    }

    @Override
    protected void initData() {

        campaignId  = getIntent().getStringExtra("campaignId");
        campaignName = getIntent().getStringExtra("campaignName");

        salesTime = getIntent().getStringExtra("salesTime");
        storeId = SPUtils.getInstance().getString("storeId");
        salesTimeSta = getIntent().getStringExtra("salesTimeSta");
        salesTimesEnd = getIntent().getStringExtra("salesTimesEnd");

        if(salesTimeSta != null && !"".equals(salesTimeSta)){
            if(salesTimesEnd != null && !"".equals(salesTimesEnd)){
                tv_time.setText(salesTimeSta +" - "+ salesTimesEnd );
            }
        }

        tv_campaign_name.setText(campaignName != null ? campaignName : "");
        storeId = SPUtils.getInstance().getString("storeId");
//        presenter.groupBuyCampaginRunning(PtOrderDetailActivity.this, campaignId, pageSize, pageNumber,storeId,true);
        listAdapter = new PtOrderListDetailAdapter(PtOrderDetailActivity.this, groupBuyCampaginList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new SpacesItemDecoration(20));
        mAdapter = new RecyclerAdapterWithHF(listAdapter);
        rvList.setAdapter(mAdapter);

    }


    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.finishActivity(PtOrderDetailActivity.class,
                        R.anim.slide_in_left,R.anim.slide_out_right);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(PtOrderDetailActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
    }


    private void initPtr() {
        ptr.postDelayed(new Runnable() {

            @Override
            public void run() {
                ptr.autoRefresh(true);
            }
        }, 150);


        ptr.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                pageNumber = 1;
                presenter.groupBuyCampaginRunning(PtOrderDetailActivity.this, campaignId, pageSize, pageNumber,storeId, salesTimeSta.replace(".","-"), salesTimesEnd.replace(".","-"),true);
            }
        });


        ptr.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNumber++;
                presenter.groupBuyCampaginRunning(PtOrderDetailActivity.this, campaignId, pageSize, pageNumber,storeId, salesTimeSta.replace(".","-"), salesTimesEnd.replace(".","-"),false);

            }
        });
    }
}
