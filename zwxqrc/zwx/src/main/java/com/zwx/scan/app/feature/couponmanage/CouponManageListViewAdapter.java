package com.zwx.scan.app.feature.couponmanage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwx.scan.app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/06/25
 * desc   :
 * version: 1.0
 **/
public class CouponManageListViewAdapter extends BaseAdapter {
    private List<CouponBean> data = new ArrayList<>();

    private LayoutInflater inflater;
    private Context context;
    public CouponManageListViewAdapter(Context context, List<CouponBean> data) {
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

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_coupon_new_date_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
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
            holder.iv_status.setBackgroundResource(R.drawable.ic_coupon_status_use);
        }else if(status != null && status.intValue() == 0){
            holder.iv_status.setBackgroundResource(R.drawable.ic_coupon_status_unuse);
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_coupon_name)
        TextView tv_coupon_name;
        @BindView(R.id.tv_coupon_type)
        TextView tv_coupon_type;
        @BindView(R.id.iv_status)
        ImageView iv_status;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
