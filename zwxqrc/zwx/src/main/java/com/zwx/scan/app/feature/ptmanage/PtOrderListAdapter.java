package com.zwx.scan.app.feature.ptmanage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.CampaignGroupBuy;
import com.zwx.scan.app.data.bean.GroupBuyCampagin;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/02/15
 * desc   :
 * version: 1.0
 **/
public class PtOrderListAdapter extends RecyclerView.Adapter {

    private List<GroupBuyCampagin> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public PtOrderListAdapter(Context context, List<GroupBuyCampagin> data) {
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
        String price = "0.00";
        if(unitPrice != null && unitPrice.doubleValue()>0){

           /* if(String.valueOf(unitPrice.doubleValue()).contains("0.0") &&  String.valueOf(unitPrice.doubleValue()).contains("0.00")){
                price = "￥"+String.valueOf(unitPrice.doubleValue()).substring(0,String.valueOf(unitPrice.doubleValue()).indexOf("."));
            }else {
                price = "￥"+String.valueOf(unitPrice.doubleValue());
            }*/

            price = "￥"+ RegexUtils.getDoubleString(groupBuyCampagin.getUnitPrice().doubleValue());
        }else{
            price = "-";
        }

        holder.tv_pt_danjia.setText(price);
        holder.tv_campaign_name.setText(groupBuyCampagin.getCampaignName()!=null ?groupBuyCampagin.getCampaignName():"");

        int count = groupBuyCampagin.getProSum().intValue();

        if(count>0){
            holder.tv_person_count.setText(count+"");
        }else {
            holder.tv_person_count.setText("-");
        }

        BigDecimal unitPriceTwo = groupBuyCampagin.getUnitPriceTwo();

        String total = "";
        if(unitPriceTwo != null && unitPriceTwo.doubleValue()>0){

          /*  if(String.valueOf(unitPriceTwo.doubleValue()).lastIndexOf("0").contains("0.0") &&  String.valueOf(unitPriceTwo.doubleValue()).contains("0.00")){
                total ="￥"+ String.valueOf(unitPriceTwo.doubleValue()).substring(0,String.valueOf(unitPriceTwo.doubleValue()).indexOf("."));
            }else {
                total ="￥"+ String.valueOf(unitPriceTwo.doubleValue());
            }*/

            price = "￥"+ RegexUtils.getDoubleString(groupBuyCampagin.getUnitPriceTwo().doubleValue());
        }else {
            total = "-";
        }

        holder.tv_pt_total.setText(total);


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_pt_order_list, null);
        return new ChildViewHolder(view);
    }
    class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_campaign_name;

        public TextView tv_person_count;
        public TextView tv_pt_danjia;
        public TextView tv_pt_total;



        public ChildViewHolder(View view) {
            super(view);
            tv_campaign_name = (TextView) view.findViewById(R.id.tv_campaign_name);
            tv_person_count = (TextView) view.findViewById(R.id.tv_person_count);
            tv_pt_danjia = (TextView) view.findViewById(R.id.tv_pt_danjia);
            tv_pt_total = (TextView) view.findViewById(R.id.tv_pt_total);

        }
    }
}
