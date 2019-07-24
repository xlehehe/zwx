package com.zwx.scan.app.feature.financialaffairs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
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
public class MemCardEmploeeSaleReportListAdapter extends BaseAdapter {

    private List<TOrder> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public MemCardEmploeeSaleReportListAdapter(Context context, List<TOrder> data) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_member_card_employee_sale_report_list, parent, false);
            holder = new ViewHolder(view);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        TOrder order = data.get(position);



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
        String cardName = order.getOrderTypeName();

       holder.tv_member_card.setText(cardName!= null&& !"".equals(cardName)?cardName:"—");
        String productCode = order.getProductCode();

        MemCardEmploeeSaleReportActivity activity = (MemCardEmploeeSaleReportActivity)context;
        //订单类型 购卡M 拼团G  商城商品P
        if("M".equals(activity.orderType)){
            holder.ll_m_d.setVisibility(View.VISIBLE);
            holder.ll_p.setVisibility(View.GONE);
        }else if("G".equals(activity.orderType)){
            holder.tv_member_card_label.setText("拼团：");
            holder.ll_m_d.setVisibility(View.VISIBLE);
            holder.ll_p.setVisibility(View.GONE);
        }else if("P".equals(activity.orderType)){
            holder.tv_member_card_label.setText("商品：");
            holder.tv_sale_total_p.setText(total);
            holder.tv_sale_amt_p.setText(am);
            holder.tv_goods_name.setText(cardName!= null&& !"".equals(cardName)?cardName:"—");
            holder.tv_product_code.setText(productCode!= null&& !"".equals(productCode)?productCode:"—");

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
            holder.tv_red_amt.setText(reAm);

        }

        return view;
    }



    class ViewHolder {

        @BindView(R.id.ll_m_d)
        public LinearLayout ll_m_d;

        @BindView(R.id.tv_member_card)
        public TextView tv_member_card;
        @BindView(R.id.tv_member_card_label)
        public TextView tv_member_card_label;
        @BindView(R.id.tv_sale_total)
        public TextView tv_sale_total;
        @BindView(R.id.tv_sale_amt)
        public TextView tv_sale_amt;

        @BindView(R.id.ll_p)
        public LinearLayout ll_p;

        @BindView(R.id.tv_goods_name)
        public TextView tv_goods_name;
        @BindView(R.id.tv_product_code)
        public TextView tv_product_code;

        @BindView(R.id.tv_sale_total_p)
        public TextView tv_sale_total_p;
        @BindView(R.id.tv_pay_amt)
        public TextView tv_pay_amt;

        @BindView(R.id.tv_sale_amt_p)
        public TextView tv_sale_amt_p;
        @BindView(R.id.tv_red_amt)
        public TextView tv_red_amt;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
