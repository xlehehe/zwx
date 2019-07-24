package com.zwx.scan.app.feature.member;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.GroupBuyCampagin;
import com.zwx.scan.app.data.bean.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/04/04
 * desc   :
 * version: 1.0
 **/
public class MemberCardStreamAdapter extends RecyclerView.Adapter {

    private List<Order> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public MemberCardStreamAdapter(Context context, List<Order> data) {
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

   /*     BigDecimal unitPrice = groupBuyCampagin.getUnitPrice();
        String price = "0.00";
        if(unitPrice != null && unitPrice.doubleValue()>0){

           *//* if(String.valueOf(unitPrice.doubleValue()).contains("0.0") &&  String.valueOf(unitPrice.doubleValue()).contains("0.00")){
                price = "￥"+String.valueOf(unitPrice.doubleValue()).substring(0,String.valueOf(unitPrice.doubleValue()).indexOf("."));
            }else {
                price = "￥"+String.valueOf(unitPrice.doubleValue());
            }*//*

            price = "￥"+ RegexUtils.getDoubleString(groupBuyCampagin.getUnitPrice().doubleValue());
        }else{
            price = "-";
        }*/

        holder.tv_mamber_card_name.setText(order.getMemtypeName()!=null ?order.getMemtypeName():"");

        int count = order.getCoun().intValue();

        if(order.getCoun()!= null && order.getCoun().intValue()>0){
            holder.tv_num.setText(count+"");
        }else {
            holder.tv_num.setText("-");
        }

        BigDecimal unitPriceTwo = order.getUnitPrice();

        String total = "";
        if(unitPriceTwo != null && unitPriceTwo.doubleValue()>0){

            total =  RegexUtils.getDoubleString(unitPriceTwo.doubleValue())+"元";
        }else {
            total = "-";
        }
        holder.tv_good_daihao.setText(order.getProductCode() != null ? order.getProductCode():"");
        holder.tv_total_money.setText(total);


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_member_card_stream_list, null);
        return new ChildViewHolder(view);
    }
    class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_mamber_card_name;

        public TextView tv_good_daihao;
        public TextView tv_num;
        public TextView tv_total_money;



        public ChildViewHolder(View view) {
            super(view);
            tv_mamber_card_name = (TextView) view.findViewById(R.id.tv_mamber_card_name);
            tv_good_daihao = (TextView) view.findViewById(R.id.tv_good_daihao);
            tv_num = (TextView) view.findViewById(R.id.tv_num);
            tv_total_money = (TextView) view.findViewById(R.id.tv_total_money);

        }
    }
    
}
