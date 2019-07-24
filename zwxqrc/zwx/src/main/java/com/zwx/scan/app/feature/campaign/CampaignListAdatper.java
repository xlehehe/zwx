package com.zwx.scan.app.feature.campaign;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zwx.library.swipe.SwipeAdapterWrapper;
import com.zwx.library.utils.TextColorSizeUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.MemCoupon;
import com.zwx.scan.app.data.bean.PosterMaterial;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.widget.NiceImageView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/19
 * desc   :
 * version: 1.0
 **/
public class CampaignListAdatper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Campaign> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public CampaignListAdatper(Context context, List<Campaign> data) {
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
        }else if("jz".equals(type)){
            campaignTypeName = "集赞活动";
        }
//        holder.tvType.setText(campaignTypeName);

        String startDate = campaign.getBeginDate();
        String endDate = campaign.getEndDate();


        Date start = null;
        Date end  = null;
        if((startDate!=null && !"".equals(startDate)) && (endDate!=null && !"".equals(endDate))){
            start = TimeUtils.string2Date(startDate);
            end = TimeUtils.string2Date(endDate);

            holder.tvTime.setText(TimeUtils.date2String(start,new SimpleDateFormat("yyyy-MM-dd")).replace("-",".") +" - " +TimeUtils.date2String(end,new SimpleDateFormat("yyyy-MM-dd")).replace("-",".") );
        }else {

            if((startDate!=null && !"".equals(startDate))){
                start = TimeUtils.string2Date(startDate);

                if(endDate!=null && !"".equals(endDate)){

                }else {
                    holder.tvTime.setText(TimeUtils.date2String(start,new SimpleDateFormat("yyyy-MM-dd")).replace("-",".") +" - ");
                }

            }else {

                if(endDate!=null && !"".equals(endDate)){
                    end = TimeUtils.string2Date(endDate);
                    holder.tvTime.setText(" - " +TimeUtils.date2String(end,new SimpleDateFormat("yyyy-MM-dd")).replace("-",".") );
                }else {
                    holder.tvTime.setText("");
                }

            }
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
        RoundedCorners roundedCorners= new RoundedCorners(10);
        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_image_loading);
        String brandLogo = "";
        String logo = campaign.getWechatIcon();
        if(poster != null){
            brandLogo = HttpUrls.IMAGE_ULR + poster.getWechatIcon();

        }else {
            brandLogo = HttpUrls.IMAGE_ULR ;
        }
        if("hy".equals(type)){
            holder.ivHead.setImageResource(R.drawable.ic_member_info_complete);
        }else {
            Glide.with(context).load(brandLogo).apply(requestOptions).into(holder.ivHead);
        }





    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_campaign_list, viewHolder,false);
        return new ChildViewHolder(view);
    }
   private class ChildViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivHead;
        public TextView tvType;
        public TextView tvCampaignType;
        public TextView tvName;
        public TextView tvTime;
        public ImageView tvStatus;

        public ChildViewHolder(View view) {
            super(view);
//            tvType = (TextView) view.findViewById(R.id.tv_type);
            tvName = (TextView) view.findViewById(R.id.tv_campaign_name);
            tvTime = (TextView) view.findViewById(R.id.tv_campaign_time);
            tvStatus = (ImageView) view.findViewById(R.id.iv_status);
            ivHead = (ImageView) view.findViewById(R.id.iv_head);

            tvCampaignType = (TextView) view.findViewById(R.id.tv_campaign_type);

        }

    }
}


