package com.zwx.scan.app.feature.campaign;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zwx.scan.app.R;
import com.zwx.scan.app.base.adapter.CommonRecycleAdapter;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.feature.countanalysis.CountAnalysis;
import com.zwx.scan.app.feature.countanalysis.CountAnalysisHomeActivity;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/21
 * desc   :
 * version: 1.0
 **/
public class SelectCampaignTypeAdapter  extends CommonRecycleAdapter<CountAnalysis> {

    private Context context;
    private CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener;

    public SelectCampaignTypeAdapter(CountAnalysisHomeActivity activity, List<CountAnalysis> dataList) {
        super(activity, dataList, R.layout.item_layout_campaign_new_select_type);
    }


    public SelectCampaignTypeAdapter(Context context, List<CountAnalysis> dataList, CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener) {
        super(context, dataList, R.layout.item_layout_campaign_new_select_type);
        this.commonClickListener = commonClickListener;
    }
    @Override
    public void bindData(CommonRecyclerViewHolder holder, CountAnalysis data) {
       /* holder.setText(R.id.tv_title, "")
                .setText(R.id.tv_en, "")
                .setCommonClickListener(commonClickListener);*/


       /* RelativeLayout rv = holder.getView(R.id.rl_campaign);

        rv.setBackgroundResource(data.getDrawableName());*/
        holder
                .setCommonClickListener(commonClickListener);
        ImageView iv = holder.getView(R.id.iv_arrow);

        iv.setBackgroundResource(data.getDrawableName());
    }
}

