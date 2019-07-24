package com.zwx.scan.app.feature.financialaffairs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.TOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/05/30
 * desc   :
 * version: 1.0
 **/
public class MemCardEmploeeSaleReportListDetailAdapter extends BaseAdapter {

    private List<TOrder> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public MemCardEmploeeSaleReportListDetailAdapter(Context context, List<TOrder> data) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_member_card_employee_sale_report_list_detail, parent, false);
            holder = new ViewHolder(view);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        TOrder order = data.get(position);

        MemCardEmploeeSaleReportDetailActivity activity = (MemCardEmploeeSaleReportDetailActivity)context;
        //订单类型 购卡M 拼团G  商城商品P
        if("M".equals(activity.orderType)){
            holder.rl_pay_red_amt.setVisibility(View.GONE);
        }else if("G".equals(activity.orderType)){

            holder.rl_pay_red_amt.setVisibility(View.GONE);
        }else if("P".equals(activity.orderType)){

            holder.rl_pay_red_amt.setVisibility(View.VISIBLE);


            BigDecimal payAmt = order.getUseCashAmount();
            String payAm = "—";
            if( payAmt != null && payAmt.doubleValue()>0){
                payAm = RegexUtils.getDoubleString(payAmt.doubleValue())+"元";
            }
            holder.tv_pay_amt.setText(payAm);


            BigDecimal redAmt = order.getUseRedEnvelopeAmount();
            String reAm = "—";
            if( redAmt != null && redAmt.doubleValue()>0){
                reAm = RegexUtils.getDoubleString(redAmt.doubleValue())+"元";
            }
            holder.tv_red_packget_amt.setText(reAm);

        }

        BigDecimal amount = order.getAmount();
        String am = "—";
        if( amount != null && amount.doubleValue()>0){
            am = RegexUtils.getDoubleString(amount.doubleValue())+"元";
        }
        holder.tv_sale_amt.setText(am);

        Long count =  order.getCoun();
        String total = "—";
        if(count!= null ){
            total  = String.valueOf(count.intValue());


        }
        holder.tv_sale_total.setText(total);

        String phone =order.getStaffTel();

        if(phone != null && !"".equals(phone)){
            holder.tv_phone.setText(phone);
        }else {
            holder.tv_phone.setText("—");
        }
        String name =order.getStaffName();

        if(name != null && !"".equals(name)){
            holder.tv_employee_name.setText(name);
        }else {
            holder.tv_employee_name.setText("—");
        }


        String storeName =order.getStoreName();

        if(storeName != null && !"".equals(storeName)){
            holder.tv_store_name.setText(storeName);
        }else {
            holder.tv_store_name.setText("—");
        }

        return view;
    }



    class ViewHolder {
        @BindView(R.id.tv_employee_name)
        public TextView tv_employee_name;
        @BindView(R.id.tv_sale_total)
        public TextView tv_sale_total;
        @BindView(R.id.tv_sale_amt)
        public TextView tv_sale_amt;

        @BindView(R.id.tv_phone)
        public TextView tv_phone;

        @BindView(R.id.tv_store_name)
        public TextView tv_store_name;


        @BindView(R.id.rl_pay_red_amt)
        public RelativeLayout rl_pay_red_amt;


        @BindView(R.id.tv_pay_amt)
        public TextView tv_pay_amt;

        @BindView(R.id.tv_red_packget_amt)
        public TextView tv_red_packget_amt;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
