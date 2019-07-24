package com.zwx.scan.app.feature.couponmanage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Coupon;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/02/21
 * desc   :
 * version: 1.0
 **/
public class CouponManageListAdapter extends RecyclerView.Adapter {
    private List<CouponBean> data = new ArrayList<>();

    private LayoutInflater inflater;
    private Context context;
    public CouponManageListAdapter(Context context, List<CouponBean> data) {
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
    public int getItemViewType(int position) {

        CouponBean coupon =  data.get(position);
        Integer status = coupon.getStatus();
        String couponStatus = "";
        if(status != null && status.intValue() == 1){
            couponStatus = "启用";
            return   1 ;
        }else if(status != null && status.intValue() == 0){
            couponStatus = "停用";
            return   0 ;
        }

        return position;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(data.size() == 0){
            return;
        }
        ChildViewHolder holder = (ChildViewHolder) viewHolder;

        int viewType =getItemViewType(position);

        if(viewType == 0){  //未启用
        }else if(viewType == 1){ //启用
        }
        CouponBean coupon = data.get(position);

        String name = coupon.getName();
        Integer status =coupon.getStatus();

        holder.tv_coupon_name.setText(name!=null?name:"");

        String type =coupon.getType();


        if("CPC".equals(coupon.getType())){

            holder.tv_coupon_type.setText("代金券");

        }else if("CPD".equals(coupon.getType())){   //折扣券

            holder.tv_coupon_type.setText("折扣券");

        }else if("CPO".equals(coupon.getType())){
            holder.tv_coupon_type.setText("赠品券");
        }else if("CPU".equals(coupon.getType())){
            holder.tv_coupon_type.setText("菜品券");
        }else if("CPJ".equals(coupon.getType())){
            holder.tv_coupon_type.setText("插队券");

        }else if("CPT".equals(coupon.getType())){
            holder.tv_coupon_type.setText("其他券");

        }


        if(status != null && status.intValue() == 1){
            holder.ivStatus.setBackgroundResource(R.drawable.ic_coupon_status_use);
        }else if(status != null && status.intValue() == 0){
            holder.ivStatus.setBackgroundResource(R.drawable.ic_coupon_status_unuse);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_coupon_manange_list, viewHolder,false);
        return new ChildViewHolder(view);
    }
    class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_coupon_name;
        public TextView tv_coupon_type;
        public ImageView ivStatus;
//        public  TextView tv_time_period;
//        public  TextView tv_no_date;
//        public TextView tv_coupon_price;
//        public ImageView iv_head;
//
//        public View line;


        public ChildViewHolder(View view) {
            super(view);
            ivStatus = (ImageView) view.findViewById(R.id.iv_status);
            tv_coupon_name = (TextView) view.findViewById(R.id.tv_coupon_name);
            tv_coupon_type = (TextView) view.findViewById(R.id.tv_coupon_type);
//            tv_time = (TextView) view.findViewById(R.id.tv_time);
//            tv_time_period = (TextView) view.findViewById(R.id.tv_time_period);
//            tv_no_date = (TextView) view.findViewById(R.id.tv_no_date);
//            tv_coupon_price = (TextView)view.findViewById(R.id.tv_coupon_price);
//            iv_head = (ImageView) view.findViewById(R.id.iv_avatar);
//            line = (View)view.findViewById(R.id.line);



        }

    }


}


