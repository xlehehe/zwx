package com.zwx.scan.app.feature.member;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.TextColorSizeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.MemberConsumeBean;
import com.zwx.scan.app.data.bean.Order;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/12
 * desc   :
 * version: 1.0
 **/
public class MemberConsumeDetailAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {
    private List<Order> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public MemberConsumeDetailAdapter(Context context, List<Order> data) {
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
        Order order = data.get(position);

        BigDecimal amt = order.getUnitPrice();
        String consume = "0.00";
        if(amt == null &&amt.doubleValue() ==0.0){

        }else {
            consume = new DecimalFormat("0.00").format(amt.setScale(2,BigDecimal.ROUND_DOWN).doubleValue()).toString();
        }

        BigDecimal total =  order.getUnitPrice();
        String money = "";
        if(total != null && total.doubleValue()>0){
            money = "￥"+ RegexUtils.getDoubleString(total.doubleValue());
        }else {
            money = "-";
        }
        holder.tv_buy_monery.setText(money);


        String consumeTime = order.getSalesTime();
        holder.tv_pay_time.setText(consumeTime != null ?consumeTime:"-");

//        String name = couponName.replace("\\,","\n");
        String channel = order.getPayChannel();

        if("z".equals(channel)){
            holder.tv_channel.setText("支付宝");
        }else if("w".equals(channel)){
            holder.tv_channel.setText("微信");
        }else if("y".equals(channel)){
            holder.tv_channel.setText("银联");
        }
        holder.tv_buy_phone.setText(order.getMemberTel()!=null?order.getMemberTel():"");
        String daihao = order.getProductCode();
        holder.tv_daihao.setText(daihao != null && !"".equals(daihao)?daihao:"");
        String storeName = order.getStoreName();
        holder.tv_sale_store .setText(storeName);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_member_card_stream_detail_list, null);
        return new ChildViewHolder(view);
    }
    private class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_pay_time;

        public TextView tv_buy_monery;
        public TextView tv_buy_phone;
        public TextView tv_daihao;
        public TextView tv_sale_store;
        public TextView tv_channel;
        public ChildViewHolder(View view) {
            super(view);
            tv_pay_time = (TextView) view.findViewById(R.id.tv_pay_time);
            tv_buy_monery = (TextView) view.findViewById(R.id.tv_buy_monery);
            tv_buy_phone = (TextView) view.findViewById(R.id.tv_buy_phone);
            tv_daihao = (TextView) view.findViewById(R.id.tv_daihao);
            tv_sale_store = (TextView) view.findViewById(R.id.tv_sale_store);
            tv_channel = (TextView) view.findViewById(R.id.tv_channel);
        }


    }

}


