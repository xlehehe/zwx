package com.zwx.scan.app.feature.personal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Feedback;

import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/03/22
 * desc   :
 * version: 1.0
 **/
public class FeedBackAdapter extends RecyclerView.Adapter {

    private List<Feedback> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public FeedBackAdapter(Context context, List<Feedback> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (data.size() == 0) {
            return 0;
        } else {
            return data.size();
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (data.size() == 0) {
            return;
        }
       ChildViewHolder holder = (ChildViewHolder) viewHolder;
        Feedback  feedback= data.get(position);



       String type = feedback.getName();
        String status = feedback.getStatus();
        String dealResult = feedback.getDealResult();
        String createTime = feedback.getCreateTime();
        String desc = feedback.getContent();
        String createrTel = feedback.getCreaterTel();
        String typeStr ="";
    /*    if("1".equals(type)){
            typeStr = "功能建议";
        }if("1".equals(type)){
            typeStr = "性能问题";
        }if("1".equals(type)){
            typeStr = "其他问题";
        }*/

        holder.tv_type.setText(type);
        String statusStr = "";
        if("N".equals(status)){
            statusStr = "未处理";
            holder.tv_dealResult.setTextColor(context.getResources().getColor(R.color.font_color_red));
        }else if("R".equals(status)|| "U".equals(status) ){
            statusStr = "已处理";
            holder.tv_dealResult.setTextColor(context.getResources().getColor(R.color.font_color_blue));
        }

        if("U".equals(status) ){
            holder.status_msg.setVisibility(View.VISIBLE);

        }else {
            holder.status_msg.setVisibility(View.GONE);

        }


        holder.tv_desc.setText(desc != null && !"".equals(desc)?desc:"");
        holder.tv_dealResult.setText(statusStr);

        if(createTime != null && !"".equals(createTime)){
            holder.tv_date.setText(createTime);
        }else {
            holder.tv_date.setText("");
        }
       /*  String phone = "";
        if(tel != null && !"".equals(tel)){
            holder.tv_tel.setText(tel);
            if(memberName != null && !"".equals(memberName)){
                holder.tv_name.setText(memberName);
            }else {
                holder.tv_name.setText("");
                holder.line.setVisibility(View.GONE);
            }
        }else {
            holder.tv_name.setText("");
            holder.line.setVisibility(View.GONE);
            if(memberName != null && !"".equals(memberName)){
                holder.tv_name.setText(memberName);
            }else {
                holder.tv_name.setText("");
            }
        }
        if(time != null && !"".equals(time)){
            holder.tv_time.setText(time);
        }else {
            holder.tv_time.setText("");
        }




        holder.tv_memtype_name.setText(memTypeName!=null?memTypeName:"");
        holder.tv_from.setText(memberInfo.getJoinStoreName()!=null?memberInfo.getJoinStoreName():"");

        String campaignName = "";
        if(whatType!=null && !"".equals(whatType)){
            if("zf".equals(whatType)){
                campaignName = "转发活动";

            }else if("BuyCard".equals(whatType)){
                campaignName = "购买会员卡";
            }else if("ge".equals(whatType)){
                campaignName = "砸金蛋活动";
            }else if("lh".equals(whatType)){
                campaignName = "老虎机活动";
            }
            holder.tv_campaign_from.setText(campaignName);
            holder.tv_campaign_from.setVisibility(View.VISIBLE);
        }else {
            holder.tv_campaign_from.setVisibility(View.GONE);
        }*/


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_feed_back_list, null);
        return new ChildViewHolder(view);
    }
    private class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_type;
        public TextView status_msg;
        public TextView tv_dealResult;
        public TextView tv_desc;
        public TextView tv_date;
        public ChildViewHolder(View view) {
            super(view);
            tv_type = (TextView) view.findViewById(R.id.tv_type);
            status_msg = (TextView) view.findViewById(R.id.status_msg);
            tv_dealResult = (TextView) view.findViewById(R.id.tv_dealResult);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
        }
    }


}
