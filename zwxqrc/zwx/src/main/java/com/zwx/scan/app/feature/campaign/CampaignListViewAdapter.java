package com.zwx.scan.app.feature.campaign;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zwx.library.utils.TimeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.PosterMaterial;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.widget.NiceImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/20
 * desc   :
 * version: 1.0
 **/
public class CampaignListViewAdapter extends BaseAdapter {


    private List<Campaign> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public CampaignListViewAdapter(Context context, List<Campaign> data) {
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
    public int getViewTypeCount() {
        // menu type count
        return 1;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context,R.layout.item_campaign_list, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();

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
        String time = campaign.getCreateTime();
        String type = campaign.getCampaignType();
        String status =campaign.getCompaignStatus();

        holder.tvName.setText(name!=null?name:"");
        String campaignTypeName = "";
        if("zf".equals(type)){
            campaignTypeName = "转发活动";
        }else if("lh".equals(type)){
            campaignTypeName = "老虎机活动";
        }else if("ge".equals(type)){
            campaignTypeName = "砸金蛋活动";
        }else if("hy".equals(type)){
            campaignTypeName = "会员信息完善活动";
        }
//        holder.tvType.setText(campaignTypeName);

        String startDate = campaign.getBeginDate();
        String endDate = campaign.getEndDate();


        Date start = null;
        Date end  = null;
        if((startDate!=null && !"".equals(startDate)) && (endDate!=null && !"".equals(endDate))){
            start = TimeUtils.string2Date(startDate);
            end = TimeUtils.string2Date(endDate);

            holder.tvTime.setText(TimeUtils.date2String(start,new SimpleDateFormat("yyyy-MM-dd")) +" - " +TimeUtils.date2String(end,new SimpleDateFormat("yyyy-MM-dd")));
        }else {
            holder.tvTime.setText("");
        }


        holder.tvCampaignType.setText(campaignTypeName);

        String campaignStatus = "";
        if("S".equals(status)){
            campaignStatus = "未发布";
            holder.tvStatus.setBackgroundResource(R.drawable.ic_campaign_s);
        }else if("N".equals(status)){
            campaignStatus = "未开始";
            holder.tvStatus.setBackgroundResource(R.drawable.ic_status_n);
        }else if("P".equals(status)){
            campaignStatus = "进行中";
            holder.tvStatus.setBackgroundResource(R.drawable.ic_status_p);
        }else if("E".equals(status)){
            campaignStatus = "已结束";
            holder.tvStatus.setBackgroundResource(R.drawable.ic_status_e);
        }else if("C".equals(status)){
            campaignStatus = "已作废";
            holder.tvStatus.setBackgroundResource(R.drawable.ic_status_c);
        }else if("I".equals(status)){
            campaignStatus = "提前结束";
            holder.tvStatus.setBackgroundResource(R.drawable.ic_status_i);
        }

        PosterMaterial  poster = campaign.getPoster();
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_logo_default)
                .error(R.drawable.ic_logo_default)
                .fallback(R.drawable.ic_logo_default);
        if(poster !=null){
//            holder.tvStatus .setText(campaignStatus);
            String brandLogo = HttpUrls.BRAND_LOGO_ULR+poster.getWechatIcon();
            Glide.with(context).load(brandLogo).apply(requestOptions).into(holder.ivHead);
        }

        return convertView;
    }

    class ViewHolder {
        public NiceImageView ivHead;
        public TextView tvType;

        public TextView tvName;
        public TextView tvTime;
        public ImageView tvStatus;
        public TextView tvCampaignType;
        public ViewHolder(View view) {
//            tvType = (TextView) view.findViewById(R.id.tv_type);
            tvName = (TextView) view.findViewById(R.id.tv_campaign_name);
            tvTime = (TextView) view.findViewById(R.id.tv_campaign_time);
            tvStatus = (ImageView) view.findViewById(R.id.iv_status);
            ivHead = (NiceImageView) view.findViewById(R.id.iv_head);

            tvCampaignType = (TextView) view.findViewById(R.id.tv_campaign_type);
            view.setTag(this);
        }
    }
}
