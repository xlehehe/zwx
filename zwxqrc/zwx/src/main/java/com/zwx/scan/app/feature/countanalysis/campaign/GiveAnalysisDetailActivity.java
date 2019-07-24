package com.zwx.scan.app.feature.countanalysis.campaign;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.popwindow.PopItemAction;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCouponDetailbean;
import com.zwx.scan.app.data.bean.CampaignTotal;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.widget.MyListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class GiveAnalysisDetailActivity extends BaseActivity<CampaignContract.Presenter>  implements CampaignContract.View,View.OnClickListener {
    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.iv_back)
    protected ImageView ivBack;


    @BindView(R.id.rv_coupon_list)
    protected RecyclerView rvCouponList;

    @BindView(R.id.rv_receive_list)
    protected RecyclerView rvReceiveList;

    @BindView(R.id.list_view_store_coupon_detail)
    protected MyListView myListView;


    @BindView(R.id.tv_time)
    protected TextView tvTime;

    @BindView(R.id.tv_days)
    protected TextView tvDays;
    @BindView(R.id.tv_send)
    protected TextView tvSend;

    @BindView(R.id.tv_get)
    protected TextView tvGet;

    @BindView(R.id.tv_receive)
    protected TextView tvReceive;

    @BindView(R.id.tv_consume_amt)
    protected TextView tvConsumeAmt;

    //优惠券整体情况
    protected  GiveDetailCouponAdapter couponAdapter;


    protected  GiveStoreReciveAdapter storeReciveAdapter;

    //店铺回收详情
    protected  GiveStoreReciveDetailAdapter storeReciveDetailAdapter;

    //整体情况统计
    protected  CampaignTotal wholeInfo = new CampaignTotal();
    protected  List<CampaignTotal> couponList = new ArrayList<>();

    protected  List<CampaignTotal> storeCouponList = new ArrayList<>();

    protected  List<CampaignCouponDetailbean.StoreCouponBean> storeCouponCountList = new ArrayList<>();



    private String title ;

    private String campaignid;


    private String time;
    private int days;
    private CampaignTotal campaignTotal = new CampaignTotal();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_give_analysis_detail;
    }

    @Override
    protected void initView() {
        DaggerCampaignComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignModule(new CampaignModule(this))
                .build()
                .inject(this);
        title = getIntent().getStringExtra("title");
        campaignid = getIntent().getStringExtra("campaignId");
        campaignTotal = (CampaignTotal)getIntent().getSerializableExtra("campaignTotal");

        if(campaignTotal != null){
            time = campaignTotal.getTimeRange();

          days = campaignTotal.getDays();

              if(days >0){
                tvDays.setText(days + "天");
            }else {
                tvDays.setText("1");

            }

            if(time != null && !"".equals(time)){
                tvTime.setText(time);
            }else{
                tvTime.setText("-");
            }



        }
        tvTitle.setText(title+"报表");
    }

    @Override
    protected void initData() {
        presenter.queryzjCountDetail(this,campaignid);


    }

    protected  void setCustomAdapter(){

        //优惠券整体统计
        couponAdapter = new GiveDetailCouponAdapter(this, couponList, new CommonRecyclerViewHolder.OnItemCommonClickListener() {
            @Override
            public void onItemClickListener(int position) {

            }
        });
        rvCouponList.setLayoutManager(new LinearLayoutManager(this));
        rvCouponList.setNestedScrollingEnabled(false);
        rvCouponList.setAdapter(couponAdapter);

        //店铺回收情况
        storeReciveAdapter = new GiveStoreReciveAdapter(this, storeCouponList, new CommonRecyclerViewHolder.OnItemCommonClickListener() {
            @Override
            public void onItemClickListener(int position) {

            }
        });
        rvReceiveList.setLayoutManager(new LinearLayoutManager(this));
        rvReceiveList.setNestedScrollingEnabled(false);
        rvReceiveList.setAdapter(storeReciveAdapter);


        //回收详情
        storeReciveDetailAdapter = new GiveStoreReciveDetailAdapter(this,storeCouponCountList);

        myListView.setAdapter(storeReciveDetailAdapter);
    }

    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.finishActivity(GiveAnalysisDetailActivity.class,
                        R.anim.slide_in_left,R.anim.slide_out_right);
                break;


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(GiveAnalysisDetailActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
    }



}
