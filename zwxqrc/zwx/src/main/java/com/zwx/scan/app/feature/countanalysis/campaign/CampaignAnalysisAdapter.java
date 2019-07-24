package com.zwx.scan.app.feature.countanalysis.campaign;

import android.content.Context;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.base.adapter.CommonRecycleAdapter;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.CampaignTotal;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/11/23
 * desc   :
 * version: 1.0
 **/
public class CampaignAnalysisAdapter  extends CommonRecycleAdapter<CampaignTotal>{

    private CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener;
/*
    public CampaignAnalysisAdapter(Context context, List<CampaignTotal> dataList) {
        super(context, dataList, R.layout.item_campaign_analysis_list);
    }*/

    public CampaignAnalysisAdapter(Context context, List<CampaignTotal> dataList, CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener) {
        super(context, dataList, R.layout.item_campaign_analysis_list);
        this.commonClickListener = commonClickListener;
    }


    @Override
    public void bindData(CommonRecyclerViewHolder holder, CampaignTotal data) {

        String campaignType = data.getCampaignType();
        String campaignStatus = "";
        if(campaignType!=null&&"zf".equals(campaignType)){
            campaignStatus = "转发活动";
        }else if(campaignType!=null&&"lh".equals(campaignType)){
            campaignStatus = "老虎机";
        }else if(campaignType!=null&&"ge".equals(campaignType)){
            campaignStatus = "砸金蛋";
        }else if(campaignType!=null&&"jz".equals(campaignType)){
            campaignStatus = "集赞";
        }

        String joinCount = "" ;
        String getCouponCount = "";
        String getReceiveCouponCount = "";
        if(data.getJoinCount()!=null && data.getJoinCount() !=0){
            joinCount =String.valueOf(data.getJoinCount());
        }else {
            joinCount ="—";
        }

        if(data.getGetCouponCount()!=null && data.getGetCouponCount() !=0){
            getCouponCount =String.valueOf(data.getGetCouponCount());
        }else {
            getCouponCount ="—";
        }

        if(data.getReceiveCouponCount()!=null && data.getReceiveCouponCount() !=0){
            getReceiveCouponCount =String.valueOf(data.getReceiveCouponCount());
        }else {
            getReceiveCouponCount ="—";
        }

        holder.setText(R.id.tv_way, campaignStatus)
                .setText(R.id.tv_name, data.getCampaignName()!=null?data.getCampaignName():"")
                .setText(R.id.tv_join,joinCount)
                .setText(R.id.tv_get, getCouponCount)
                .setText(R.id.tv_receive, getReceiveCouponCount)
                .setCommonClickListener(commonClickListener);


    }
}
