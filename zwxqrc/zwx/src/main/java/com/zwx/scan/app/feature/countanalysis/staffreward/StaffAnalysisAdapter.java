package com.zwx.scan.app.feature.countanalysis.staffreward;

import android.content.Context;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.base.adapter.CommonRecycleAdapter;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.StaffReward;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/11/24
 * desc   :
 * version: 1.0
 **/
public class StaffAnalysisAdapter extends CommonRecycleAdapter<StaffReward> {

    private  Context context;
    private CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener;
    public StaffAnalysisAdapter(Context context, List<StaffReward> dataList) {
        super(context, dataList, R.layout.item_staff_analysis_list);
        this.context = context;
    }



    @Override
    public void bindData(CommonRecyclerViewHolder holder, StaffReward data) {


        holder.setText(R.id.tv_name, data.getStaffName()!=null?data.getStaffName():"")
                .setText(R.id.tv_store_name,data.getStoreName()!=null?data.getStoreName():"")
                .setText(R.id.tv_total, data.getTotal() != 0 && data.getTotal() != null ? data.getTotal().intValue()+"":"-")
                .setCommonClickListener(commonClickListener);


        TextView tvRow = holder.getView(R.id.tv_row);
        if(data.getRownum() != null){
            int  rownum = data.getRownum().intValue();

            if(rownum == 1){
                tvRow.setBackground(context.getResources().getDrawable(R.drawable.ic_first));
            }else if(rownum == 2){
                tvRow.setBackground(context.getResources().getDrawable(R.drawable.ic_second));
            }else if(rownum == 3){
                tvRow.setBackground(context.getResources().getDrawable(R.drawable.ic_third));
            }else {
                tvRow.setText(data.getRownum().intValue()+"");
            }
        }


    }
}
