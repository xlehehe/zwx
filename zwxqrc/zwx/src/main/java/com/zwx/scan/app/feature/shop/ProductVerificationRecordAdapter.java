package com.zwx.scan.app.feature.shop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.ProductCategory;
import com.zwx.scan.app.data.bean.TOrderObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/04/26
 * desc   :
 * version: 1.0
 **/
public class ProductVerificationRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TOrderObject> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public ProductVerificationRecordAdapter(Context context, List<TOrderObject> data) {
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

        TOrderObject orderObject = data.get(position);

        String name = orderObject.getProductName();
//        String num = category.getNum();

        holder.tv_name.setText(name!=null?name:"-");
/*
        if(num != null && !"".equals(num) && !"0".equals(num)){
            holder.tvNum.setText(num);
        }else {
            holder.tvNum.setText("0");
        }*/
        //红包额度
        BigDecimal usrRedEnvelope = orderObject.getUseRedEnvelope();
        String redEnvelope = "-";
        if(usrRedEnvelope != null &&usrRedEnvelope.doubleValue()>0){
            redEnvelope = RegexUtils.getDoubleString(usrRedEnvelope.doubleValue());

        }
        BigDecimal unitPrice = orderObject.getUnitPrice();
        String uniPric =  "-";
        if(unitPrice != null &&unitPrice.doubleValue()>0){
            uniPric = RegexUtils.getDoubleString(unitPrice.doubleValue());

        }
        holder.tv_sale_amt.setText(uniPric);
        BigDecimal price = orderObject.getPrice();
        String pri = "-";
        if(price != null &&price.doubleValue()>0){
            pri = RegexUtils.getDoubleString(price.doubleValue());

        }
        holder.tv_red_packet_amt.setText(redEnvelope);


        //实付金额
        holder.tv_pay_amt.setText(pri);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_prodcut_verifiction_record_list, viewHolder,false);
        return new ChildViewHolder(view);
    }
    private class ChildViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_name;
        public TextView tv_veri_zhang;
        public TextView tv_sale_amt;
        public TextView tv_red_packet_amt;
        public TextView tv_pay_amt;

        public ChildViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_veri_zhang = (TextView) view.findViewById(R.id.tv_veri_zhang);
            tv_sale_amt = (TextView) view.findViewById(R.id.tv_sale_amt);
            tv_red_packet_amt = (TextView) view.findViewById(R.id.tv_red_packet_amt);
            tv_pay_amt = (TextView) view.findViewById(R.id.tv_pay_amt);
        }

    }
}
