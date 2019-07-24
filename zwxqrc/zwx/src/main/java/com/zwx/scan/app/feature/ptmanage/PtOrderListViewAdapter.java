package com.zwx.scan.app.feature.ptmanage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.GroupBuyCampagin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/05/13
 * desc   :  拼团订单Listview adapter 列表封装数据
 * version: 1.0
 **/
public class PtOrderListViewAdapter extends BaseAdapter {

    private List<GroupBuyCampagin> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public PtOrderListViewAdapter(Context context, List<GroupBuyCampagin> data) {
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
        final ChildViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_pt_order_list, parent, false);
            holder = new ChildViewHolder();


            holder.tv_campaign_name = (TextView) view.findViewById(R.id.tv_campaign_name);
            holder.tv_person_count = (TextView) view.findViewById(R.id.tv_person_count);
            holder.tv_pt_danjia = (TextView) view.findViewById(R.id.tv_pt_danjia);
            holder.tv_pt_total = (TextView) view.findViewById(R.id.tv_pt_total);
            view.setTag(holder);

        } else {
            holder = (ChildViewHolder) view.getTag();
        }

        GroupBuyCampagin groupBuyCampagin = data.get(position);
        BigDecimal unitPrice = groupBuyCampagin.getUnitPrice();
        String price = "0.00";
        if(unitPrice != null && unitPrice.doubleValue()>0){

            price = "￥"+ RegexUtils.getDoubleString(groupBuyCampagin.getUnitPrice().doubleValue());
        }else{
            price = "—";
        }

        holder.tv_pt_danjia.setText(price);
        holder.tv_campaign_name.setText(groupBuyCampagin.getCampaignName()!=null ?groupBuyCampagin.getCampaignName():"—");

        int count = groupBuyCampagin.getProSum().intValue();

        if(count>0){
            holder.tv_person_count.setText(count+"");
        }else {
            holder.tv_person_count.setText("—");
        }

        BigDecimal unitPriceTwo = groupBuyCampagin.getUnitPriceTwo();

        String total = "";
        if(unitPriceTwo != null && unitPriceTwo.doubleValue()>0){

            total = "￥"+ RegexUtils.getDoubleString(groupBuyCampagin.getUnitPriceTwo().doubleValue());
        }else {
            total = "—";
        }

        holder.tv_pt_total.setText(total);
        return view;
    }


    class ChildViewHolder  {
        public TextView tv_campaign_name;

        public TextView tv_person_count;
        public TextView tv_pt_danjia;
        public TextView tv_pt_total;



      /*  public ChildViewHolder(View view) {
            super(view);
            tv_campaign_name = (TextView) view.findViewById(R.id.tv_campaign_name);
            tv_person_count = (TextView) view.findViewById(R.id.tv_person_count);
            tv_pt_danjia = (TextView) view.findViewById(R.id.tv_pt_danjia);
            tv_pt_total = (TextView) view.findViewById(R.id.tv_pt_total);

        }*/
    }
    
    
}
