package com.zwx.scan.app.feature.member;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.MemberInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/12
 * desc   :
 * version: 1.0
 **/
public class MemberInfoListAdapter extends RecyclerView.Adapter {
    private List<MemberInfoBean.MemberInfo> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public MemberInfoListAdapter(Context context, List<MemberInfoBean.MemberInfo> data) {
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
        MemberInfoBean.MemberInfo  memberInfo= data.get(position);



        String time = memberInfo.getJoinSysTime();
        String memTypeName = memberInfo.getMemTypeName();
        String tel = memberInfo.getMemberTel();
        String memberName = memberInfo.getMemberName();

        String whatType = memberInfo.getWhatType();
//      String name = couponName.replace("\\,","\n");

     /*  holder.tv_tel.setText(memberConsume.getMemberTel() != null ? memberConsume.getMemberTel() :"");
        holder.tv_consume_count.setText(TextColorSizeUtils.getTextSpan(context, subTextColor, 32, personQnt,subTextArray));*/
     String phone = "";
     if(tel != null && !"".equals(tel) ){
         holder.tv_tel.setText(tel);
         if(memberName != null && !"".equals(memberName) && !"null".equals(memberName)){
             holder.tv_name.setText(memberName);
             holder.line.setVisibility(View.VISIBLE);
         }else {
             holder.tv_name.setText("");
             holder.line.setVisibility(View.GONE);
         }
     }else {
         holder.tv_name.setText("");
         holder.line.setVisibility(View.GONE);
         if(memberName != null && !"".equals(memberName) && !"null".equals(memberName)){
             holder.tv_name.setText(memberName);
         }else {
             holder.tv_name.setText("");
         }
     }
        if(time != null && !"".equals(time)){


            holder.tv_time.setText(time.replace("-","."));
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
        }


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_member_info_list, null);
        return new ChildViewHolder(view);
    }
    private class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_tel;
        public TextView tv_name;
        public TextView tv_time;
        public TextView tv_memtype_name;
        public TextView tv_from;
        public TextView tv_campaign_from;
        public View line;
        public ChildViewHolder(View view) {
            super(view);
            line = (View) view.findViewById(R.id.line);
            tv_tel = (TextView) view.findViewById(R.id.tv_tel);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_memtype_name = (TextView) view.findViewById(R.id.tv_memtype_name);
            tv_from = (TextView) view.findViewById(R.id.tv_from);
            tv_campaign_from = (TextView) view.findViewById(R.id.tv_campaign_from);
        }
    }

}


