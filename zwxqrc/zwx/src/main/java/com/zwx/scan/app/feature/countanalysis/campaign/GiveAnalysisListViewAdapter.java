package com.zwx.scan.app.feature.countanalysis.campaign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zwx.library.utils.TimeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.CampaignTotal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/07/03
 * desc   :
 * version: 1.0
 **/
public class GiveAnalysisListViewAdapter extends BaseAdapter {
    private List<CampaignTotal> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public GiveAnalysisListViewAdapter(Context context, List<CampaignTotal> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_give_analysis_list, parent, false);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.tv_way = (TextView) convertView.findViewById(R.id.tv_way);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_join = (TextView) convertView.findViewById(R.id.tv_join);
            holder.tv_receive = (TextView) convertView.findViewById(R.id.tv_receive);
            holder.tv_get = (TextView) convertView.findViewById(R.id.tv_get);

            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);

            holder.tv_days = (TextView) convertView.findViewById(R.id.tv_days);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CampaignTotal campaignTotal = data.get(position);

        String campaignStatus = "";


        String sendCouponCount = "" ;
        String getCouponCount = "";
        String receiveCouponCount = "";
        if(campaignTotal.getSendCouponCount()!=null && campaignTotal.getSendCouponCount() !=0){
            sendCouponCount =String.valueOf(campaignTotal.getSendCouponCount());
        }else {
            sendCouponCount ="—";
        }

        if(campaignTotal.getGetCouponCount()!=null && campaignTotal.getGetCouponCount() !=0){
            getCouponCount =String.valueOf(campaignTotal.getGetCouponCount());
        }else {
            getCouponCount ="—";
        }

        if(campaignTotal.getReceiveCouponCount()!=null && campaignTotal.getReceiveCouponCount() !=0){
            receiveCouponCount =String.valueOf(campaignTotal.getReceiveCouponCount());
        }else {
            receiveCouponCount ="—";
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
                /*if((startDate != null && !"".equals(startDate)) && (endDate != null && !"".equals(endDate))){
                    if(TimeUtils.IsToday(endDate)){
                        date = startDate.replace("-",".")+" - 今日" ;
                    }else {
                        date = startDate.replace("-",".")+" - " +endDate.replace("-",".") ;
                    }
                }*/

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

        return convertView;
    }


    private class ViewHolder {
        public TextView tv_way;
        public TextView tv_name;
        public TextView tv_join;
        public TextView tv_receive;
        public  TextView tv_get;

        public  TextView tv_time;
        public  TextView tv_days;


    }
}
