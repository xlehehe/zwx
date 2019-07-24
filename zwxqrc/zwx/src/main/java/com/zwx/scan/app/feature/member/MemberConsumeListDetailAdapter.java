package com.zwx.scan.app.feature.member;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.TextColorSizeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.ComsumeLog;
import com.zwx.scan.app.data.bean.MemberInfoBean;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/13
 * desc   :
 * version: 1.0
 **/
public class MemberConsumeListDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ComsumeLog> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public MemberConsumeListDetailAdapter(Context context, List<ComsumeLog> data) {
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
        ComsumeLog  memberInfo= data.get(position);
        String time = "-";
        String couponName = "";
        String storeName = "";
        BigDecimal amt = null;
        String total = "-";
        String compMemberGroup = "-";
        if(memberInfo != null ){
            time = memberInfo.getConsumeTime();
            couponName = memberInfo.getCouponName();
            storeName = memberInfo.getStoreName();
            amt = memberInfo.getConsumeAmt();
            compMemberGroup = memberInfo.getCompMemberGroup();
            total = memberInfo.getPersonQnt() != 0 ? memberInfo.getPersonQnt()+"":"-";

            if(compMemberGroup != null && !"".equals(compMemberGroup)){
            }else {
                compMemberGroup ="暂无";
            }
        }



//        String memberName = memberInfo.getMemberName();

        String consume = "-";
        if(amt != null && amt.doubleValue()>0){
//            consume = RegexUtils.getDoubleString(amt.doubleValue());
            consume = new DecimalFormat("0.00").format(amt.setScale(2,BigDecimal.ROUND_DOWN).doubleValue()).toString();
        }else {
//            consume = new DecimalFormat("0.00").format(amt.setScale(2,BigDecimal.ROUND_DOWN).doubleValue()).toString();
        }


        String personQnt = "就餐人数："+total;
        int subTextColor = Color.parseColor("#333333");
        String[] subTextArray = {"就餐人数："};
        holder.tv_time_label.setText("消费时间：");
        holder.tv_card_label.setText("优惠券：");
        holder.tv_time_label.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        holder.tv_card_label.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));



//        String name = couponName.replace("\\,","\n");

     /*   holder.tv_tel.setText(memberConsume.getMemberTel() != null ? memberConsume.getMemberTel() : "");
        holder.tv_consume_count.setText(TextColorSizeUtils.getTextSpan(context, subTextColor, 32, personQnt, subTextArray));*/
        String couponName1  = "";
        StringBuilder sb = new StringBuilder();
        if(couponName != null && !"".equals(couponName)){
           /* String[] couponNames = couponName.split(",");
            for (int i = 0;i<couponNames.length;i++){
                couponName1+=context.getResources().getString(R.string.huanhang)+couponNames[i];
            }*/
           if(couponName.contains(",")){
               couponName1 = couponName.replaceAll(",","\n");
           }else {
               couponName1 = couponName;
           }

        }else {

        }



        holder.tv_memtype_name.setText(couponName1);

//        String name = couponName.replace("\\,","\n");

        holder.tv_tel.setText(storeName);
        holder.tv_consume_count.setText(TextColorSizeUtils.getTextSpan(context,subTextColor,32,personQnt,subTextArray));
        holder.tv_consume_time.setText(time);
        holder.tv_money.setText("￥"+consume);


        holder.tv_memtype_name2.setText(compMemberGroup);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_member_consume_list, null);
        return new ChildViewHolder(view);
    }
    private class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_tel;

        public TextView tv_consume_count;
        public TextView tv_memtype_name;
        public TextView tv_consume_time;
        public TextView tv_money;
        public TextView tv_card_label;
        public TextView tv_time_label;

        public TextView tv_memtype_name2;
        public ChildViewHolder(View view) {
            super(view);
            tv_tel = (TextView) view.findViewById(R.id.tv_tel);
            tv_consume_count = (TextView) view.findViewById(R.id.tv_consume_count);
            tv_memtype_name = (TextView) view.findViewById(R.id.tv_memtype_name);
            tv_consume_time = (TextView) view.findViewById(R.id.tv_consume_time);
            tv_money = (TextView) view.findViewById(R.id.tv_money);
            tv_time_label = (TextView) view.findViewById(R.id.tv_time_label);
            tv_card_label = (TextView) view.findViewById(R.id.tv_card_label);
            tv_memtype_name2 = (TextView) view.findViewById(R.id.tv_memtype_name2);

        }
    }
    
}
