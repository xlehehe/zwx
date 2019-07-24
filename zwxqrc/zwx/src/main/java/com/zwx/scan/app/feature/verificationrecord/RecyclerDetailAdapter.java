package com.zwx.scan.app.feature.verificationrecord;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zwx.library.utils.TextColorSizeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.MemCoupon;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/01
 * desc   :
 * version: 1.0
 **/
public class RecyclerDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MemCoupon> data = new ArrayList<>();
    private LayoutInflater inflater;
    private  Context context;
    public RecyclerDetailAdapter(Context context, List<MemCoupon> data) {
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
        MemCoupon memCoupon = data.get(position);

        BigDecimal amt = memCoupon.getConsumeAmt();
        String consume = "0.00";
        if(amt == null &&amt.doubleValue() ==0.0){

        }else {
            consume = new DecimalFormat("0.00").format(amt.setScale(2,BigDecimal.ROUND_DOWN).doubleValue()).toString();
        }

        String total =  memCoupon.getPersonQnt() != 0 ? memCoupon.getPersonQnt()+"":"-";

        String personQnt = "就餐人数："+total;
        int subTextColor = Color.parseColor("#333333");
        String[] subTextArray = {"就餐人数："};



        StringBuilder sb = new StringBuilder();
        String couponName= memCoupon.getCouponName();
        if(couponName.contains(",")){

            String name = couponName.replace(",","\n");

            holder.tv_coupon_name.setText(name);
        }else {
            holder.tv_coupon_name.setText(couponName);
        }

//        String name = couponName.replace("\\,","\n");

        holder.tv_member_tel.setText(memCoupon.getMemberTel()!=null?memCoupon.getMemberTel():"");
        holder.tv_use_count.setText(TextColorSizeUtils.getTextSpan(context,subTextColor,32,personQnt,subTextArray));
        String date = "";
        if(memCoupon.getConsumeTime() != null && !"".equals(memCoupon.getConsumeTime())){
             date = memCoupon.getConsumeTime().replace("-",".");

        }else {

        }
        holder.tv_consume_time.setText(date);
//        double amount = memCoupon.getConsumeAmt()!=null?memCoupon.getConsumeAmt().setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue():0;
        holder.tv_money .setText("￥"+consume);
        holder.tv_name.setText(memCoupon.getMemberName()!=null && !"".equals(memCoupon.getMemberName())?memCoupon.getMemberName():"");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_verification_record_detail_list, null);
        return new ChildViewHolder(view);
    }

}

 class ChildViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_member_tel;

    public TextView tv_use_count;
    public TextView tv_coupon_name;
    public TextView tv_consume_time;
    public TextView tv_money;
    public  TextView tv_name;
        /*  holder.setText(R.id.tv_member_tel, data.getMemberTel()!=null?data.getMemberTel():"")
                .setText(R.id.tv_use_count, data.getTotal() != 0 ? data.getTotal()+"":"-")
                .setText(R.id.tv_coupon_name,data.getCouponName())
                .setText(R.id.tv_consume_time,data.getConsumeTime())
                .setText(R.id.tv_money,"￥"+data.getConsumeAmt()!=null?data.getConsumeAmt().setScale(2,BigDecimal.ROUND_HALF_UP).toString()+"":"0.00")
                .setCommonClickListener(commonClickListener);*/

    public ChildViewHolder(View view) {
        super(view);
        tv_member_tel = (TextView) view.findViewById(R.id.tv_member_tel);
        tv_use_count = (TextView) view.findViewById(R.id.tv_use_count);
        tv_coupon_name = (TextView) view.findViewById(R.id.tv_coupon_name);
        tv_consume_time = (TextView) view.findViewById(R.id.tv_consume_time);
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        tv_name= (TextView) view.findViewById(R.id.tv_name);
    }

}
