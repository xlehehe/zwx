package com.zwx.scan.app.feature.financialaffairs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.CollectionFlowResultBean;
import com.zwx.scan.app.data.bean.TOrderObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/05/23
 * desc   :  收款到账列表适配器
 * version: 1.0
 **/
public class CollectionIntoAmtOrderAdapter extends BaseAdapter {
    private List<TOrderObject> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private String flag;
    public CollectionIntoAmtOrderAdapter(Context context, List<TOrderObject> data,String flag) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
        this.flag = flag;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_collection_expect_into_amt_order_list, parent, false);
            holder = new ViewHolder(view);
            holder.tv_sale_time_label = (TextView) view.findViewById(R.id.tv_sale_time_label);
            holder.tv_sale_time = (TextView) view.findViewById(R.id.tv_sale_time);
            holder.tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
            holder.tv_order_num = (TextView) view.findViewById(R.id.tv_order_num);
            holder.tv_pay_amt = (TextView) view.findViewById(R.id.tv_pay_amt);
            holder.tv_red_packget_amt = (TextView) view.findViewById(R.id.tv_red_packget_amt);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        TOrderObject orderObject = data.get(position);

        if("F".equals(flag)){
            holder.tv_sale_time_label.setText("预计到账时间：");
        }

        String money = "—";
        BigDecimal total =  orderObject.getPrice();
        if(total != null && total.doubleValue()>0){
            money = RegexUtils.getDoubleString(total.doubleValue())+"元";
        }else if(total != null && total.doubleValue()==0){
            money = RegexUtils.getDoubleString(total.doubleValue())+"元";
        }

        holder.tv_pay_amt.setText(money);

        //商品名称
        String productName = orderObject.getProductName();
        holder.tv_product_name.setText(productName != null && !"".equals(productName)?productName:"—");


        //红包
        BigDecimal usrRedEnvelope = orderObject.getUseRedEnvelope();
        String redEnvelope = "—";
        if( usrRedEnvelope != null && usrRedEnvelope.doubleValue()>0){

            redEnvelope = RegexUtils.getDoubleString(usrRedEnvelope.doubleValue())+"元";
        }else if( usrRedEnvelope != null && usrRedEnvelope.doubleValue()==0){
            redEnvelope = RegexUtils.getDoubleString(usrRedEnvelope.doubleValue())+"元";
        }
        holder.tv_red_packget_amt.setText(redEnvelope);






        if("F".equals(flag)){  //未到账时间
            String estimateTime = orderObject.getEstimateTime();

            if(estimateTime != null && !"".equals(estimateTime)){
                estimateTime = estimateTime.replace("-",".");
            }else {
                estimateTime = "—";
            }
            holder.tv_sale_time.setText(estimateTime);
        }else {
            String time = orderObject.getSalesTime();

            if(time != null && !"".equals(time)){
                time = time.replace("-",".");
            }else {
                time = "—";
            }
            holder.tv_sale_time.setText(time);
        }


        String orderCode = "—";

        if(orderObject.getOrderCode() != null  && !"".equals(orderObject.getOrderCode())){
            orderCode = String.valueOf(orderObject.getOrderCode());
            if(orderObject.getOrderCode() != 0){

            }else {
                orderCode = "—";
            }

        }
        //商品编码
        holder.tv_order_num.setText(orderCode);



        return view;
    }



    class ViewHolder {

        @BindView(R.id.tv_sale_time_label)
        public TextView tv_sale_time_label;
        @BindView(R.id.tv_order_num)
        public TextView tv_order_num;
        @BindView(R.id.tv_sale_time)
        public TextView tv_sale_time;
        @BindView(R.id.tv_pay_amt)
        public TextView tv_pay_amt;
        @BindView(R.id.tv_red_packget_amt)
        public TextView tv_red_packget_amt;
        @BindView(R.id.tv_product_name)
        public TextView tv_product_name;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
