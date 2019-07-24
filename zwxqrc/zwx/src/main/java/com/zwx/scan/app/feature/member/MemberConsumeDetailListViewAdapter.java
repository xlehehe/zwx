package com.zwx.scan.app.feature.member;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Order;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
public class MemberConsumeDetailListViewAdapter extends BaseAdapter {

    private List<Order> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public MemberConsumeDetailListViewAdapter(Context context, List<Order> data) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_member_card_stream_detail_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

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

        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.tv_pay_time)
        public TextView tv_pay_time;
        @BindView(R.id.tv_buy_monery)
        public TextView tv_buy_monery;
        @BindView(R.id.tv_buy_phone)
        public TextView tv_buy_phone;
        @BindView(R.id.tv_daihao)
        public TextView tv_daihao;
        @BindView(R.id.tv_sale_store)
        public TextView tv_sale_store;
        @BindView(R.id.tv_channel)
        public TextView tv_channel;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


    }
}
