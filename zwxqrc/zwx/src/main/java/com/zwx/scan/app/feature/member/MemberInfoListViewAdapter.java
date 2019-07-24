package com.zwx.scan.app.feature.member;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.MemberInfoBean;
import com.zwx.scan.app.feature.staffmanage.StaffListViewdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/06/17
 * desc   : 会员卡信息管理列表
 * version: 1.0
 **/
public class MemberInfoListViewAdapter extends BaseAdapter {

    private List<MemberInfoBean.MemberInfo> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public MemberInfoListViewAdapter(Context context, List<MemberInfoBean.MemberInfo> data) {
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
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_member_info_list, parent, false);
            holder = new ViewHolder(view);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

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
        return view;
    }

    class ViewHolder {
        @BindView(R.id.tv_tel)
        public TextView tv_tel;
        @BindView(R.id.tv_name)
        public TextView tv_name;
        @BindView(R.id.tv_time)
        public TextView tv_time;
        @BindView(R.id.tv_memtype_name)
        public TextView tv_memtype_name;
        @BindView(R.id.tv_from)
        public TextView tv_from;
        @BindView(R.id.tv_campaign_from)
        public TextView tv_campaign_from;
        @BindView(R.id.line)
        public View line;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
