package com.zwx.scan.app.feature.countanalysis;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.base.adapter.CommonRecycleAdapter;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.CompMember;
import com.zwx.scan.app.data.bean.StaffReward;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/06
 * desc   :
 * version: 1.0
 **/
public class CountAnalysisAdapter extends CommonRecycleAdapter<CountAnalysis> {

    private CountAnalysisHomeActivity activity;
    private CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener;

    public CountAnalysisAdapter(CountAnalysisHomeActivity activity, List<CountAnalysis> dataList) {
        super(activity, dataList, R.layout.layout_count_analysis_home_campaign);
        this.activity = activity;
    }


    public CountAnalysisAdapter(CountAnalysisHomeActivity activity, List<CountAnalysis> dataList, CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener) {
        super(activity, dataList, R.layout.layout_count_analysis_home_campaign);
        this.commonClickListener = commonClickListener;
    }
    @Override
    public void bindData(CommonRecyclerViewHolder holder, CountAnalysis data) {
        holder.setText(R.id.tv_title, "")
                .setText(R.id.tv_en, "")
                .setText(R.id.name, data.getTitle() != null ? data.getTitle()  : "")
                .setCommonClickListener(commonClickListener);


        RelativeLayout rv = holder.getView(R.id.rl_campaign);

        rv.setBackgroundResource(data.getDrawableName());
     /*   if("活动报表".equals(data.getMemTypeName())){
            rv.setBackground(activity.getResources().getDrawable(R.drawable.ic_analysis_campaign));
        }else if("会员管理".equals(data.getMemTypeName())){
            rv.setBackground(activity.getResources().getDrawable(R.drawable.ic_analysis_member));
        }else if("拉新报表".equals(data.getMemTypeName())){
            rv.setBackground(activity.getResources().getDrawable(R.drawable.ic_analysis_staff));
        }*/


    }
}
