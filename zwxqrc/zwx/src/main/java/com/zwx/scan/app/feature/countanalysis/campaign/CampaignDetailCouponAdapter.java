package com.zwx.scan.app.feature.countanalysis.campaign;

import android.content.Context;

import com.zwx.scan.app.R;
import com.zwx.scan.app.base.adapter.CommonRecycleAdapter;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.CampaignTotal;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * author : lizhilong
 * time   : 2018/11/30
 * desc   :
 * version: 1.0
 **/
public class CampaignDetailCouponAdapter extends CommonRecycleAdapter<Map<String,Object>>{

    private CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener;
/*
    public CampaignAnalysisAdapter(Context context, List<CampaignTotal> dataList) {
        super(context, dataList, R.layout.item_campaign_analysis_list);
    }*/

    public CampaignDetailCouponAdapter(Context context, List<Map<String,Object>> dataList, CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener) {
        super(context, dataList, R.layout.item_campaign_analysis_detail_coupon_list_content);
        this.commonClickListener = commonClickListener;
    }


    @Override
    public void bindData(CommonRecyclerViewHolder holder, Map<String,Object> map) {
        DecimalFormat format = new DecimalFormat("#####");
        String getCount = "—";
        String get = String.valueOf(map.get("getCouponCount"));
        if(get != null && !"0.0".equals(get)){
            getCount = format.format(map.get("getCouponCount"));

        }


        String sendCount = "—";
        String send = String.valueOf(map.get("sendCouponCount"));
        if(send != null && !"0.0".equals(send)){
            sendCount = format.format(map.get("sendCouponCount"));

        }

        String receiveCount = "—";
        String receive = String.valueOf(map.get("receiveCouponCount"));
        if( receive != null && !"0.0".equals(receive)){
            receiveCount = format.format(map.get("receiveCouponCount"));

        }
        String  couponName = (String)map.get("couponName") !=null ? (String)map.get("couponName") : "";

        holder.setText(R.id.tv_foward, getCount)
                .setText(R.id.tv_send, sendCount)
                .setText(R.id.tv_coupon_name,couponName )
                .setText(R.id.tv_receive, receiveCount)

                .setCommonClickListener(commonClickListener);

    }
}
