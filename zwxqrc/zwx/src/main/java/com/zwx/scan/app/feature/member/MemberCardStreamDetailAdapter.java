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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/04/04
 * desc   :
 * version: 1.0
 **/
public class MemberCardStreamDetailAdapter extends RecyclerView.Adapter {
    private List<GroupBuyCampagin> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public MemberCardStreamDetailAdapter(Context context, List<GroupBuyCampagin> data) {
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


        holder.tv_buy_phone.setText(groupBuyCampagin.getCampaignName()!=null ?groupBuyCampagin.getCampaignName():"");

        int count = groupBuyCampagin.getProSum().intValue();

        if(count>0){
            holder.tv_daihao.setText(count+"");
        }else {
            holder.tv_daihao.setText("-");
        }

        BigDecimal unitPriceTwo = groupBuyCampagin.getUnitPriceTwo();

        String total = "";
        if(unitPriceTwo != null && unitPriceTwo.doubleValue()>0){

            total =  RegexUtils.getDoubleString(groupBuyCampagin.getUnitPriceTwo().doubleValue())+"元";
        }else {
            total = "-";
        }
        holder.tv_consume_count.setText(groupBuyCampagin.getPayChannel());
        holder.tv_consume_count.setText(total);


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_member_card_stream_detail_list, null);
        return new ChildViewHolder(view);
    }
    class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_pay_time;

        public TextView tv_consume_count;
        public TextView tv_buy_phone;
        public TextView tv_daihao;

        public TextView tv_sale_store;

        public ChildViewHolder(View view) {
            super(view);
            tv_pay_time = (TextView) view.findViewById(R.id.tv_pay_time);
            tv_consume_count = (TextView) view.findViewById(R.id.tv_consume_count);
            tv_buy_phone = (TextView) view.findViewById(R.id.tv_buy_phone);
            tv_daihao = (TextView) view.findViewById(R.id.tv_daihao);
            tv_sale_store = (TextView) view.findViewById(R.id.tv_sale_store);

        }
    }

}
