package com.zwx.scan.app.feature.countanalysis.campaign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.CampaignTotal;
import com.zwx.scan.app.feature.couponmanage.CouponManageListViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/06/27
 * desc   :
 * version: 1.0
 **/
public class CampaignAnalysisListViewAdapter extends BaseAdapter {
    private List<CampaignTotal> list;
    private Context context;
    protected LayoutInflater layoutInflater;

    public CampaignAnalysisListViewAdapter(Context context, List<CampaignTotal> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_campaign_analysis_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        CampaignTotal data = list.get(position);

        String campaignType = data.getCampaignType();
        String campaignStatus = "";
        if(campaignType!=null&&"zf".equals(campaignType)){
            campaignStatus = "转发活动";
        }else if(campaignType!=null&&"lh".equals(campaignType)){
            campaignStatus = "老虎机";
        }else if(campaignType!=null&&"ge".equals(campaignType)){
            campaignStatus = "砸金蛋";
        }else if(campaignType!=null&&"jz".equals(campaignType)){
            campaignStatus = "集赞";
        }

        String joinCount = "" ;
        String getCouponCount = "";
        String getReceiveCouponCount = "";
        if(data.getJoinCount()!=null && data.getJoinCount() !=0){
            joinCount =String.valueOf(data.getJoinCount());
        }else {
            joinCount ="—";
        }

        if(data.getGetCouponCount()!=null && data.getGetCouponCount() !=0){
            getCouponCount =String.valueOf(data.getGetCouponCount());
        }else {
            getCouponCount ="—";
        }


        if(data.getReceiveCouponCount()!=null && data.getReceiveCouponCount() !=0){
            getReceiveCouponCount =String.valueOf(data.getReceiveCouponCount());
        }else {
            getReceiveCouponCount ="—";
        }

        String name = data.getCampaignName();
        if(name!=null && !"".equals(name)){
        }else {
            name ="—";
        }

        holder.tv_way.setText(campaignStatus);
        holder.tv_name.setText(name);
        holder.tv_join.setText(joinCount);
        holder.tv_get.setText(getCouponCount);
        holder.tv_receive.setText(getReceiveCouponCount);
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_way)
        TextView tv_way;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_join)
        TextView tv_join;



        @BindView(R.id.tv_get)
        TextView tv_get;
        @BindView(R.id.tv_receive)
        TextView tv_receive;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
