package com.zwx.scan.app.feature.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.TOrderConsumeLog;
import com.zwx.scan.app.data.bean.TOrderObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/04/28
 * desc   :
 * version: 1.0
 **/
public class ProductVerificationRecordListDetailAdapter extends BaseAdapter {

    private List<TOrderConsumeLog> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public ProductVerificationRecordListDetailAdapter(Context context, List<TOrderConsumeLog> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }


    @Override
    public int getCount() {
        if(data.size() == 0){
            return 0;
        }else {
            return data.size();
        }
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
        final ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_product_verifiction_record_detail_list, parent, false);
            holder = new ViewHolder(view);

            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            holder.tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            holder.tv_time = (TextView) view.findViewById(R.id.tv_time);
            holder.tv_coupon_name = (TextView) view.findViewById(R.id.tv_coupon_name);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        TOrderConsumeLog orderObject = data.get(position);

        String name = orderObject.getMemberName();
        holder.tv_name.setText(name != null ? name : "");

        holder.tv_phone.setText(orderObject.getMemberTel() != null ? orderObject.getMemberTel() : "-");
        String time = "-";
        if (orderObject.getConsumeTime() != null && !"".equals(orderObject.getConsumeTime())) {
            time = orderObject.getConsumeTime().replace("-",".");
        }
        holder.tv_time.setText(time);
        String couponName = "-";

        if (orderObject.getProductName() != null && !"".equals(orderObject.getProductName())) {
            couponName = orderObject.getProductName();
        }
        holder.tv_coupon_name.setText(couponName);
       /* //红包额度
        BigDecimal usrRedEnvelope = orderObject.getUsrRedEnvelope();
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
        holder.tv_pay_amt.setText(pri);*/


        return view;
    }



    class ViewHolder {

        /*public TextView tv_name;
        public TextView tv_veri_zhang;
        public TextView tv_sale_amt;
        public TextView tv_red_packet_amt;
        public TextView tv_pay_amt;*/

        /*public ChildViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_veri_zhang = (TextView) view.findViewById(R.id.tv_veri_zhang);
            tv_sale_amt = (TextView) view.findViewById(R.id.tv_sale_amt);
            tv_red_packet_amt = (TextView) view.findViewById(R.id.tv_red_packet_amt);
            tv_pay_amt = (TextView) view.findViewById(R.id.tv_pay_amt);
        }*/

        @BindView(R.id.tv_phone)
        TextView tv_phone;
        @BindView(R.id.tv_name)
        TextView tv_name;

        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_coupon_name)
        TextView tv_coupon_name;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
