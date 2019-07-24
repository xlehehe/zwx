package com.zwx.scan.app.feature.countanalysis.campaign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.CampaignCouponDetailbean;
import com.zwx.scan.app.data.bean.CampaignTotal;
import com.zwx.scan.app.feature.AppContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class PinTuanAnalysisDetailActivity extends BaseActivity<CampaignContract.Presenter>  implements CampaignContract.View,View.OnClickListener  {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.iv_back)
    protected ImageView ivBack;


    @BindView(R.id.rv_coupon_list)
    protected RecyclerView rvCouponList;


    @BindView(R.id.tv_time)
    protected TextView tvTime;

    @BindView(R.id.tv_days)
    protected TextView tvDays;

    @BindView(R.id.tv_join)
    protected TextView tv_join;

//    @BindView(R.id.tv_pt)
//    protected TextView tv_pt;
    @BindView(R.id.tv_forward)
    protected TextView tv_forward;
    @BindView(R.id.tv_view)
    protected TextView tv_view;

    @BindView(R.id.tv_send)
    protected TextView tvSend;


    @BindView(R.id.tv_receive)
    protected TextView tvReceive;

    @BindView(R.id.tv_consume_amt)
    protected TextView tvConsumeAmt;

    @BindView(R.id.tv_sale_amt)
    protected TextView tvSaleAmt;
    @BindView(R.id.tv_coupon_list_tip)
    protected TextView tv_coupon_list_tip;


    protected  PinTuanAnalysisDetailAdapter couponAdapter;

    protected CampaignTotal wholeInfo = new CampaignTotal();
    protected CampaignTotal campaignTotal = new CampaignTotal();
    protected List<CampaignTotal> couponList = new ArrayList<>();

    protected  List<CampaignTotal> storeCouponList = new ArrayList<>();

    protected  List<CampaignCouponDetailbean.StoreCouponBean> storeCouponCountList = new ArrayList<>();

    protected List<Map<String,Object>> couponCampaignTotalList = new ArrayList<>();
    private String title ;

    private String campaignid;


    private String time;
    private int days;
    protected String storeId;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pin_tuan_analysis_detail;
    }

    @Override
    protected void initView() {
        DaggerCampaignComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignModule(new CampaignModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {

        storeId = SPUtils.getInstance().getString("storeId");
        title = getIntent().getStringExtra("title");
        campaignid = getIntent().getStringExtra("campaignId");
        campaignTotal = (CampaignTotal)getIntent().getSerializableExtra("campaignTotal");

        if(campaignTotal != null){
            time = campaignTotal.getTimeRange();

            days = campaignTotal.getDays();

            if(days >0){
                tvDays.setText(days + "天");
            }else {
                tvDays.setText("1天");

            }

            String date = "";
            String endDate = "";
            String startDate = "";
            if(time != null && !"".equals(time)){
                startDate = time.substring(0,10);
                endDate = time.substring(11,time.length());

                if((startDate != null && !"".equals(startDate)) && (endDate != null && !"".equals(endDate))){
                    if(TimeUtils.IsToday(endDate)){
                        date = startDate +" - " + "今日";
                    }else {
                        date = startDate +" - " + endDate;
                    }
                }
            }

            tvTime.setText(date);

        }
        tvTitle.setText(title+"统计报表");

        presenter.queryPtCountDetail(this,campaignid);
    }

    protected  void setCustomAdapter(){

        //优惠券整体统计
        couponAdapter = new PinTuanAnalysisDetailAdapter(this, couponList, new CommonRecyclerViewHolder.OnItemCommonClickListener() {
            @Override
            public void onItemClickListener(int position) {

            }
        });
        rvCouponList.setLayoutManager(new LinearLayoutManager(this));
        rvCouponList.setNestedScrollingEnabled(false);
        rvCouponList.setAdapter(couponAdapter);

        if(couponList != null && couponList.size()>0){
            rvCouponList.setVisibility(View.VISIBLE);
            tv_coupon_list_tip.setVisibility(View.GONE);
        }else {
            rvCouponList.setVisibility(View.GONE);
            tv_coupon_list_tip.setVisibility(View.VISIBLE);
        }

    }


    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.finishActivity(PinTuanAnalysisDetailActivity.class,
                        R.anim.slide_in_left,R.anim.slide_out_right);
                break;


        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(PinTuanAnalysisDetailActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
    }


}
