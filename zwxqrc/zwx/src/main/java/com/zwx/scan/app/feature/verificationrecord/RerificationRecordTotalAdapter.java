package com.zwx.scan.app.feature.verificationrecord;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.MemCoupon;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/11/23
 * desc   : 核销记录优惠券核销情况item
 * version: 1.0
 **/
public class RerificationRecordTotalAdapter extends RecyclerView.Adapter {

    private List<MemCoupon> list;
    private Context context;
    protected LayoutInflater layoutInflater;

    public RerificationRecordTotalAdapter(Context context, List<MemCoupon> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new OneViewHolder(LayoutInflater.from(context).inflate(R.layout.item_verification_record_list_title, parent,false));
        } else {
            return new TwoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_verification_record_list_content, parent,false));
        }
//       View  view = layoutInflater.inflate(R.layout.item_verification_record_list_content, parent,false);
//        return new TwoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == 1) {
            OneViewHolder oneViewHolder = (OneViewHolder) holder;
            oneViewHolder.setData(position);
        } else {
            TwoViewHolder twoViewHolder = (TwoViewHolder) holder;
            twoViewHolder.setData(position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        if(position == 0){
            return 1;
        }else {
            return 2;
        }
    }


    class OneViewHolder extends RecyclerView.ViewHolder {
        public OneViewHolder(View itemview) {
            super(itemview);
        }

        public void setData(int position) {
        }
    }

    private class TwoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private  TextView tvCount;
        public TwoViewHolder(View itemView) {
            super(itemView);

        }

        public void setData(int position) {


            tvName = (TextView) itemView.findViewById(R.id.tv_coupon_name);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            MemCoupon memCoupon = list.get(position);
            tvName.setText(memCoupon.getCouponName());
            tvCount.setText(memCoupon.getTotal()+"");
        }



    }

}
