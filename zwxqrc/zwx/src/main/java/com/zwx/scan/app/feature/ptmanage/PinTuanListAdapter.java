package com.zwx.scan.app.feature.ptmanage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwx.library.utils.TimeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Campaign;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/02/12
 * desc   :
 * version: 1.0
 **/
public class PinTuanListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Campaign> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public PinTuanListAdapter(Context context, List<Campaign> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if(data.size() == 0){
            return 0;
        }else {
            return data.size();
        }

    }


    @Override
    public int getItemViewType(int position) {

        Campaign campaign = data.get(position);
        String status =campaign.getCompaignStatus();
        String campaignStatus = "";
        if("S".equals(status)){
            campaignStatus = "未发布";
            return   0 ;
        }else if("N".equals(status)){
            campaignStatus = "未开始";
            return   1 ;
        }else if("P".equals(status)){
            campaignStatus = "进行中";
            return   2;
        }else if("E".equals(status)){
            campaignStatus = "已结束";
            return   3;
        }else if("C".equals(status)){
            campaignStatus = "已作废";
            return   4;
        }else if("I".equals(status)){
            campaignStatus = "提前结束";
            return   5;
        }

        return position;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(data.size() == 0){
            return;
        }
        ChildViewHolder holder = (ChildViewHolder) viewHolder;

        int viewType =getItemViewType(position);

        if(viewType == 0){
            //未发布
        }else if(viewType == 1){
            //未开始
        }else if(viewType == 2){

        }else if(viewType == 3){

        }
        Campaign campaign = data.get(position);

        String name = campaign.getCampaignName();
        String expireStartDate = campaign.getBeginDate();
        String expireEndDate = campaign.getEndDate();
        String type = campaign.getSendType();
        String status =campaign.getCompaignStatus();

        holder.tvName.setText(name!=null?name:"");
        String campaignTypeName = "";
       /* if("K".equals(type)){
            campaignTypeName = "开卡礼";
        }else if("B".equals(type)){
            campaignTypeName = "生日礼";
        }else if("M".equals(type)){
            campaignTypeName = "会员日";
        }else if("S".equals(type)){
            campaignTypeName = "唤醒消费礼";
        }*/
//        holder.tvCampaignType.setText(campaignTypeName);
        holder.tvCampaignType.setVisibility(View.GONE);

        holder.rl_type.setVisibility(View.GONE);
        String startDate = campaign.getBeginDate();
        String endDate = campaign.getEndDate();


        Date start = null;
        Date end  = null;
        if((startDate!=null && !"".equals(startDate)) && (endDate!=null && !"".equals(endDate))){
            start = TimeUtils.string2Date(startDate);
            end = TimeUtils.string2Date(endDate);

            String startTime = TimeUtils.date2String(start,TimeUtils.DATE_FORMAT_DATE);
            String endTime = TimeUtils.date2String(end,TimeUtils.DATE_FORMAT_DATE);
            holder.tvTime.setText(startTime.replace("-",".")+" - " +endTime.replace("-","."));
        }else {
            holder.tvTime.setText("");
        }




        String campaignStatus = "";
        if("S".equals(status)){
//            campaignStatus = "未发布";
            holder.tvStatus.setBackgroundResource(R.drawable.ic_campaign_s);
        }else if("N".equals(status)){
//            campaignStatus = "未开始";
            holder.tvStatus.setBackgroundResource(R.drawable.ic_status_n);
        }else if("P".equals(status)){
//            campaignStatus = "进行中";
            holder.tvStatus.setBackgroundResource(R.drawable.ic_status_p);
        }else if("E".equals(status)){
//            campaignStatus = "已结束";
            holder.tvStatus.setBackgroundResource(R.drawable.ic_status_e);
        }else if("C".equals(status)){
//            campaignStatus = "已作废";
            holder.tvStatus.setBackgroundResource(R.drawable.ic_status_c);
        }else if("I".equals(status)){
//            campaignStatus = "提前结束";
            holder.tvStatus.setBackgroundResource(R.drawable.ic_status_i);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_give_coupon_manage_list, viewHolder,false);
        return new ChildViewHolder(view);
    }

}

class ChildViewHolder extends RecyclerView.ViewHolder {

    public TextView tvCampaignType;
    public TextView tvName;
    public TextView tvTime;
    public ImageView tvStatus;
    public RelativeLayout rl_type;
    public ChildViewHolder(View view) {
        super(view);
        tvName = (TextView) view.findViewById(R.id.tv_campaign_name);
        tvTime = (TextView) view.findViewById(R.id.tv_campaign_time);
        tvStatus = (ImageView) view.findViewById(R.id.iv_status);

        tvCampaignType = (TextView) view.findViewById(R.id.tv_campaign_type);
        rl_type = (RelativeLayout) view.findViewById(R.id.rl_type);
    }

}

