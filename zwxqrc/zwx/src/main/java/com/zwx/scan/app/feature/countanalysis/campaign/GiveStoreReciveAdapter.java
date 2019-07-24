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
public class GiveStoreReciveAdapter extends CommonRecycleAdapter<CampaignTotal> {

    private CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener;
/*
    public CampaignAnalysisAdapter(Context context, List<CampaignTotal> dataList) {
        super(context, dataList, R.layout.item_campaign_analysis_list);
    }*/

    public GiveStoreReciveAdapter(Context context, List<CampaignTotal> dataList, CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener) {
        super(context, dataList, R.layout.item_give_analysis_store_receive_pool_content);
        this.commonClickListener = commonClickListener;
    }


    @Override
    public void bindData(CommonRecyclerViewHolder holder, CampaignTotal campaignTotal) {
        DecimalFormat format = new DecimalFormat("#####");


        String receiveCount = "-";
        int receive = campaignTotal.getReceiveCouponCount();
        if( receive >0 ){
            receiveCount = receive + "";

        }else {

        }

        String shouyiCount = "-";
        BigDecimal amt = campaignTotal.getConsumeAmt();
        if(amt.doubleValue()>0){
//            shouyiCount   = new DecimalFormat("0.00").format(amt.setScale(2,BigDecimal.ROUND_DOWN).doubleValue()).toString();
            shouyiCount = RegexUtils.getDoubleString(amt.doubleValue());

        }
        String  storeName = campaignTotal.getStoreName() !=null ? campaignTotal.getStoreName() : "";

        holder.setText(R.id.tv_store, storeName)
                .setText(R.id.tv_shouyi, shouyiCount)
                .setText(R.id.tv_receive, receiveCount)
                .setCommonClickListener(commonClickListener);

    }
}

