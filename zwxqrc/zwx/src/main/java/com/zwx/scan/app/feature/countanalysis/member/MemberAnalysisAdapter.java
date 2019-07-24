package com.zwx.scan.app.feature.countanalysis.member;

import android.content.Context;

import com.zwx.scan.app.R;
import com.zwx.scan.app.base.adapter.CommonRecycleAdapter;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.CampaignTotal;
import com.zwx.scan.app.data.bean.CompMember;
import com.zwx.scan.app.data.bean.MemCoupon;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/11/24
 * desc   :
 * version: 1.0
 **/
public class MemberAnalysisAdapter extends CommonRecycleAdapter<CompMember> {

    private CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener;
/*
    public CampaignAnalysisAdapter(Context context, List<CampaignTotal> dataList) {
        super(context, dataList, R.layout.item_campaign_analysis_list);
    }*/

    public MemberAnalysisAdapter(Context context, List<CompMember> dataList, CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener) {
        super(context, dataList, R.layout.item_member_analysis_list);
        this.commonClickListener = commonClickListener;
    }


    @Override
    public void bindData(CommonRecyclerViewHolder holder, CompMember data) {
        String total=data.getTotal();

        if(total != "0" && total != null  && !"".equals(total)){

        }else {
            total = "-";
        }

        holder.setText(R.id.tv_memtype_name, data.getMemTypeName() != null ? data.getMemTypeName():"")
                .setText(R.id.tv_coupon_total, total)
                .setCommonClickListener(commonClickListener);

    }
}
