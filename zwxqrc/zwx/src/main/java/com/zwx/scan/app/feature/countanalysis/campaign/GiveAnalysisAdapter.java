package com.zwx.scan.app.feature.countanalysis.campaign;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zwx.library.utils.TimeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.adapter.CommonRecycleAdapter;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.CampaignTotal;
import com.zwx.scan.app.data.bean.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/01/11
 * desc   :
 * version: 1.0
 **/
public class GiveAnalysisAdapter extends RecyclerView.Adapter {

    private List<CampaignTotal> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public GiveAnalysisAdapter(Context context, List<CampaignTotal> data) {
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
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(data.size() == 0){
            return;
        }
        ChildViewHolder holder = (ChildViewHolder) viewHolder;

        CampaignTotal campaignTotal = data.get(position);

        String campaignStatus = "";


        String sendCouponCount = "" ;
        String getCouponCount = "";
        String receiveCouponCount = "";
       if(campaignTotal.getSendCouponCount()!=null && campaignTotal.getSendCouponCount() !=0){
           sendCouponCount =String.valueOf(campaignTotal.getSendCouponCount());
        }else {
           sendCouponCount ="-";
        }

        if(campaignTotal.getGetCouponCount()!=null && campaignTotal.getGetCouponCount() !=0){
            getCouponCount =String.valueOf(campaignTotal.getGetCouponCount());
        }else {
            getCouponCount ="-";
        }

     /*   if(campaignTotal.getReceiveCouponCount()!=null && campaignTotal.getReceiveCouponCount() !=0){
            receiveCouponCount =String.valueOf(campaignTotal.getReceiveCouponCount());
        }else {
            receiveCouponCount ="-";
        }
*/

        if(campaignTotal.getReceiveCouponCount()!=null && campaignTotal.getReceiveCouponCount() !=0){
            receiveCouponCount =String.valueOf(campaignTotal.getReceiveCouponCount());
        }else {
            receiveCouponCount ="-";
        }


       String campaignName = campaignTotal.getCampaignName();
        int days = campaignTotal.getDays();
       String sendName = campaignTotal.getSendName();
        holder.tv_name.setText(campaignName != null?campaignName : "");

        holder.tv_way.setText(sendName != null? sendName : "");

        holder.tv_join.setText(sendCouponCount);

        holder.tv_receive.setText(receiveCouponCount);

        holder.tv_get.setText(getCouponCount);

        if(days >0){
            holder.tv_days.setText(days + "");
        }else {
            holder.tv_days.setText("1");
        }

        String time = campaignTotal.getTimeRange();
        String date = "";
        if(time != null && !"".equals(time)){


            if("N".equals(GiveAnalysisActivity.campaignStatus)){
                date = time ;
            }else {
                String startDate  = time.substring(0,10);
                String endDate = time.substring(11,time.length()).replace(".","-");

                String curDateStr = TimeUtils.getReqDate(TimeUtils.getNowDate());

                Date end = TimeUtils.string2Date(curDateStr);

                if(startDate != null && !"".equals(startDate)){
                    Date start = TimeUtils.string2Date(startDate.replace(".","-"));
                    long day = (end.getTime() - start.getTime())/(24*60*60*1000);

                    if(day>0){
                        holder.tv_days.setText(day+"");
                    }else {
                        holder.tv_days.setText("1");
                    }

                }

                date = startDate+" - 今日" ;
            }


        }
        if(date != null && !"".equals(date)){
            holder.tv_time.setText(date);
        }else{
            holder.tv_time.setText("-");

        }

        if("N".equals(GiveAnalysisActivity.campaignStatus)){
            holder.tv_join.setTextColor(context.getResources().getColor(R.color.color_gray_deep));
            holder.tv_receive.setTextColor(context.getResources().getColor(R.color.color_gray_deep));
            holder.tv_get.setTextColor(context.getResources().getColor(R.color.color_gray_deep));
        }


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_give_analysis_list, viewHolder,false);
        return new ChildViewHolder(view);
    }
    private class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_way;
        public TextView tv_name;
        public TextView tv_join;
        public TextView tv_receive;
        public  TextView tv_get;

        public  TextView tv_time;
        public  TextView tv_days;
        public ChildViewHolder(View view) {
            super(view);
            tv_way = (TextView) view.findViewById(R.id.tv_way);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_join = (TextView) view.findViewById(R.id.tv_join);
            tv_receive = (TextView) view.findViewById(R.id.tv_receive);
            tv_get = (TextView) view.findViewById(R.id.tv_get);

            tv_time = (TextView) view.findViewById(R.id.tv_time);

            tv_days = (TextView) view.findViewById(R.id.tv_days);
        }

    }
}




    /*@Override
    public void bindData(CommonRecyclerViewHolder holder, CampaignTotal data) {

        String campaignType = data.getCampaignType();
        String campaignStatus = "";
        if(campaignType!=null&&"zf".equals(campaignType)){
            campaignStatus = "转发活动";
        }else if(campaignType!=null&&"lh".equals(campaignType)){
            campaignStatus = "老虎机";
        }else if(campaignType!=null&&"ge".equals(campaignType)){
            campaignStatus = "砸金蛋";
        }else {
            campaignStatus = "生日礼";
        }

        String joinCount = "" ;
        String getCouponCount = "";
        String getReceiveCouponCount = "";
        if(data.getJoinCount()!=null && data.getJoinCount() !=0){
            joinCount =String.valueOf(data.getJoinCount());
        }else {
            joinCount ="-";
        }

        if(data.getGetCouponCount()!=null && data.getGetCouponCount() !=0){
            getCouponCount =String.valueOf(data.getGetCouponCount());
        }else {
            getCouponCount ="-";
        }

        if(data.getReceiveCouponCount()!=null && data.getReceiveCouponCount() !=0){
            getReceiveCouponCount =String.valueOf(data.getReceiveCouponCount());
        }else {
            getReceiveCouponCount ="-";
        }

        holder.setText(R.id.tv_way, campaignStatus)
                .setText(R.id.tv_name, data.getCampaignName()!=null?data.getCampaignName():"")
                .setText(R.id.tv_join,joinCount)
                .setText(R.id.tv_get, getCouponCount)
                .setText(R.id.tv_receive, getReceiveCouponCount)
                .setCommonClickListener(commonClickListener);


    }
}*/

