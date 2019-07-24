package com.zwx.scan.app.feature.ptmanage;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zwx.library.utils.TextColorSizeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.CampaignGroupBuy;
import com.zwx.scan.app.data.bean.GroupBuyCampagin;
import com.zwx.scan.app.data.bean.MemberConsumeBean;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dagger.multibindings.ElementsIntoSet;

/**
 * author : lizhilong
 * time   : 2019/02/15
 * desc   :
 * version: 1.0
 **/
public class PtOrderListDetailAdapter extends RecyclerView.Adapter {
    private List<GroupBuyCampagin> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public PtOrderListDetailAdapter(Context context, List<GroupBuyCampagin> data) {
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
        GroupBuyCampagin groupBuyCampagin = data.get(position);

        BigDecimal unitPrice = groupBuyCampagin.getUnitPrice();
        String price = "";
        if(unitPrice != null && unitPrice.doubleValue()>0){

            if(String.valueOf(unitPrice.doubleValue()).contains("0.0") && String.valueOf(unitPrice.doubleValue()).contains("0.00")){
                price = "￥"+String.valueOf(unitPrice.doubleValue()).substring(0,String.valueOf(unitPrice.doubleValue()).indexOf("."));
            }else {
                price = "￥"+String.valueOf(unitPrice.doubleValue());
            }
        }
        holder.tv_consume_amt.setText(price != null ?price:"");
        String salesTime = groupBuyCampagin.getSalesTime();
        holder.tv_pay_time.setText(salesTime != null ?"支付时间："+salesTime.replace("-","."):"");
        String phone = groupBuyCampagin.getMemberTel();
        holder.tv_phone.setText(phone != null ?phone:"");
        String payChannel = groupBuyCampagin.getPayChannel();

        if("w".equals(payChannel)){
            holder.tv_pay_name.setText("微信");
        }else if("z".equals(payChannel)){
            holder.tv_pay_name.setText("支付宝");
        }else if("y".equals(payChannel)){
            holder.tv_pay_name.setText("银联");
        }
/*
        String phone =  campaignGroupBuy.get() != null ? memberConsume.getCompEatTotalQnt()+"":"-";

        String personQnt = "消费次数："+total;
        int subTextColor = Color.parseColor("#333333");
        String[] subTextArray = {"消费次数："};

        String consumeTime = memberConsume.getConsumeTime();
        String memTypeName = memberConsume.getMemTypeName();
        holder.tv_memtype_name.setText(memTypeName);

//        String name = couponName.replace("\\,","\n");

        holder.tv_pay_time.setText(memberConsume.getMemberTel()!=null?memberConsume.getMemberTel():"");
        holder.tv_consume_count.setText(TextColorSizeUtils.getTextSpan(context,subTextColor,32,personQnt,subTextArray));

        holder.tv_consume_time.setText(consumeTime);
        holder.tv_money .setText("￥"+consume);*/

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_pt_order_detail_list, null);
        return new ChildViewHolder(view);
    }
    class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_pay_time;

        public TextView tv_consume_amt;
        public TextView tv_phone;
        public TextView tv_pay_name;



        public ChildViewHolder(View view) {
            super(view);
            tv_pay_time = (TextView) view.findViewById(R.id.tv_pay_time);
            tv_consume_amt = (TextView) view.findViewById(R.id.tv_consume_amt);
            tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            tv_pay_name = (TextView) view.findViewById(R.id.tv_pay_name);

        }
    }
}


