package com.zwx.scan.app.feature.countanalysis.campaign;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zwx.library.utils.TimeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.CampaignTotal;

import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/02/15
 * desc   :
 * version: 1.0
 **/
public class PinTuanAnalysisAdapter extends RecyclerView.Adapter {

    private List<CampaignTotal> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public PinTuanAnalysisAdapter(Context context, List<CampaignTotal> data) {
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
        if(campaignTotal.getViewCount()!=null && campaignTotal.getViewCount().intValue()>0){
            sendCouponCount =String.valueOf(campaignTotal.getViewCount());
        }else {
            sendCouponCount ="—";
        }

        if(campaignTotal.getFowardCount()!=null && campaignTotal.getFowardCount().intValue()>0){
            getCouponCount =String.valueOf(campaignTotal.getFowardCount());
        }else {
            getCouponCount ="—";
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


        String memTypeName = campaignTotal.getMemtypeName();
        int days = campaignTotal.getDays();
        String sendName = campaignTotal.getSendName();
        holder.tv_name.setText(campaignTotal.getCampaignName() != null? campaignTotal.getCampaignName() : "");

        holder.tv_way.setText("拼团");

        holder.tv_join.setText(sendCouponCount);


        holder.tv_get.setText(getCouponCount);

        if(days >0){
            holder.tv_days.setText(days + "");
        }else {
            holder.tv_days.setText("1");
        }

        String time = campaignTotal.getTimeRange();

        String date = "";
        if(time != null && !"".equals(time)){

//            String startDate  = time.substring(0,10);
//            String endDate = time.substring(11,time.length());

          /*  if((startDate != null && !"".equals(startDate)) && (endDate != null && !"".equals(endDate))){
                if(TimeUtils.IsToday(endDate)){
                    date = startDate.replace("-",".")+" - 今日" ;
                }else {
                    date = startDate.replace("-",".")+" - " +endDate.replace("-",".") ;
                }
            }*/
           String[] str = time.split("-");
           if(str.length>0){
               String startDate = str[0];
               String endDate = str[1];

               if((startDate != null && !"".equals(startDate)) && (endDate != null && !"".equals(endDate))){
                   if(TimeUtils.IsToday(endDate.replace(".","-"))){
                       date = startDate+" - 今日" ;
                   }else {
                       date = startDate+" - " +endDate;
                   }
               }else {

               }
           }


        }

        holder.tv_time.setText(date);

        if("N".equals(PinTuanAnalysisActivity.campaignStatus)){
            holder.tv_join.setTextColor(context.getResources().getColor(R.color.color_gray_deep));
            holder.tv_get.setTextColor(context.getResources().getColor(R.color.color_gray_deep));
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_pt_analysis_list, viewHolder,false);
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
//            tv_receive = (TextView) view.findViewById(R.id.tv_receive);
            tv_get = (TextView) view.findViewById(R.id.tv_get);

            tv_time = (TextView) view.findViewById(R.id.tv_time);

            tv_days = (TextView) view.findViewById(R.id.tv_days);
        }

    }
}
