package com.zwx.scan.app.feature.countanalysis.campaign;

import android.content.Context;

import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.adapter.CommonRecycleAdapter;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.CampaignTotal;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * author : lizhilong
 * time   : 2019/01/11
 * desc   :
 * version: 1.0
 **/
public class GiveDetailCouponAdapter  extends CommonRecycleAdapter<CampaignTotal> {

    private CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener;
/*
    public CampaignAnalysisAdapter(Context context, List<CampaignTotal> dataList) {
        super(context, dataList, R.layout.item_campaign_analysis_list);
    }*/

    public GiveDetailCouponAdapter(Context context, List<CampaignTotal> dataList, CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener) {
        super(context, dataList, R.layout.item_campaign_coupon_list_content);
        this.commonClickListener = commonClickListener;
    }


    @Override
    public void bindData(CommonRecyclerViewHolder holder, CampaignTotal campaignTotal) {
        DecimalFormat format = new DecimalFormat("#####");
        String getCount = "-";
        int get = campaignTotal.getGetCouponCount();
        if(get >0){
            getCount = get + "";
        }else {

        }


        String sendCount = "-";
        int send = campaignTotal.getSendCouponCount();
        if(send >0){
            sendCount = send + "";
        }else {
        }

        String receiveCount = "-";
        int receive = campaignTotal.getReceiveCouponCount();
        if( receive >0){
            receiveCount = receive + "";

        }else {

        }
        String  couponName = campaignTotal.getCouponName()!=null ? campaignTotal.getCouponName() : "";

        String shouyiCount = "-";
        BigDecimal shouyi = campaignTotal.getConsumeAmt();
        if( shouyi != null && shouyi.doubleValue() >0){
            shouyiCount = RegexUtils.getDoubleString(shouyi.doubleValue());

        }else {
            shouyiCount = "-";
        }
        holder.setText(R.id.tv_foward, getCount)
                .setText(R.id.tv_send, sendCount)
                .setText(R.id.tv_coupon_name,couponName )
                .setText(R.id.tv_receive, receiveCount)
                .setText(R.id.tv_shouyi,shouyiCount)
                .setCommonClickListener(commonClickListener);

    }
}
