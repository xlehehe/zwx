package com.zwx.scan.app.feature.member;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.GroupBuyCampagin;
import com.zwx.scan.app.data.bean.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/07/03
 * desc   :
 * version: 1.0
 **/
public class MemberCardStreamListViewAdapter extends BaseAdapter {
    private List<Order> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public MemberCardStreamListViewAdapter(Context context, List<Order> data) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_member_card_stream_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

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
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_mamber_card_name)
        public TextView tv_mamber_card_name;
        @BindView(R.id.tv_good_daihao)
        public TextView tv_good_daihao;

        @BindView(R.id.tv_num)
        public TextView tv_num;
        @BindView(R.id.tv_total_money)
        public TextView tv_total_money;


        public TextView tv_sale_store;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


    }
}
