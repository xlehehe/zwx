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
 * time   : 2019/05/22
 * desc   :
 * version: 1.0
 **/
public class CollectionFlowListAdapter extends BaseAdapter {

    private List<CollectionFlowResultBean.CollectionFlowBean> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public CollectionFlowListAdapter(Context context, List<CollectionFlowResultBean.CollectionFlowBean> data) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_trade_drawing_list, parent, false);
            holder = new ViewHolder(view);
            holder.tv_order_code = (TextView) view.findViewById(R.id.tv_order_code);
            holder.tv_pay_way = (TextView) view.findViewById(R.id.tv_pay_way);
            holder.tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
            holder.tv_buy_phone = (TextView) view.findViewById(R.id.tv_buy_phone);
            holder.tv_sale_time = (TextView) view.findViewById(R.id.tv_sale_time);
            holder.tv_pay_amt = (TextView) view.findViewById(R.id.tv_pay_amt);
            holder.tv_red_packget_amt = (TextView) view.findViewById(R.id.tv_red_packget_amt);
            holder.tv_sale_emploee = (TextView) view.findViewById(R.id.tv_sale_emploee);
            holder.tv_buy_num = (TextView) view.findViewById(R.id.tv_buy_num);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        CollectionFlowResultBean.CollectionFlowBean flowBean = data.get(position);

        //订单数量
/*        Long orderCount = flowBean.getBuyCount();
        holder.tv_buy_num.setText(orderCount != null && orderCount.intValue()>0?String.valueOf(orderCount.intValue()):"—");
        BigDecimal total =  flowBean.getUnitPrice();
        String money = "";
        if(total != null && total.doubleValue()>0){
            money = RegexUtils.getDoubleString(total.doubleValue()*orderCount.intValue());
            if(!"0".equals(money) &&!"0.0".equals(money)){

            }else {
                money = "-";
            }
        }else {
            money = "-";
        }*/
        String money = "—";
        BigDecimal total =  flowBean.getAmount();
        if(total != null && total.doubleValue()>0){
            money = RegexUtils.getDoubleString(total.doubleValue());
        }else {
            money = "—";
        }
        //商品总额
        holder.tv_pay_amt.setText(money);

        //商品名称
        String productName = flowBean.getOrderTypeName();
        holder.tv_product_name.setText(productName != null && !"".equals(productName)?productName:"—");

     /*   //商品金额
        BigDecimal payAmount = order.getPayAmount();
        String amount = "-";
        if(payAmount != null && payAmount.doubleValue()>0){
            amount = String.valueOf(payAmount.doubleValue());

        }

        holder.tv_pay_amt.setText(amount);*/



        //订单编号
        holder.tv_order_code.setText(flowBean.getOrderCode() != null && !"".equals(flowBean.getOrderCode())?String.valueOf(flowBean.getOrderCode()):"—");

        //红包
        BigDecimal usrRedEnvelope = flowBean.getUseRedEnvelopeAmount();
        String redEnvelope = "—";
        if( usrRedEnvelope != null && usrRedEnvelope.doubleValue()>0){
            redEnvelope = RegexUtils.getDoubleString(usrRedEnvelope.doubleValue());
        }
        holder.tv_red_packget_amt.setText(redEnvelope);

        //会员信息

        String memberInfo = flowBean.getMemberTel();

        if(memberInfo != null && !"".equals(memberInfo)){
        }else {
            memberInfo  = "-";
        }

        holder.tv_buy_phone.setText(memberInfo);

        String time = flowBean.getSalesTime();

        if(time != null && !"".equals(time)){
            time = time.replace("-",".");
        }else {
            time = "—";
        }


        holder.tv_sale_time.setText(time);




        //商品编码
        holder.tv_order_code.setText(flowBean.getOrderCode() != null ?String.valueOf(flowBean.getOrderCode()):"—");



        holder.tv_buy_phone.setText(flowBean.getBuyCount()!= null && flowBean.getBuyCount().intValue()>0?"x"+flowBean.getBuyCount().intValue():"—");

        String  payWay=flowBean.getPayChannel(); //支付方式
        if("w".equals(payWay)){
            holder.tv_pay_way.setText("微信");
        }else if("z".equals(payWay)){
            holder.tv_pay_way.setText("支付宝");
        }else if("y".equals(payWay)){
            holder.tv_pay_way.setText("银联");
        }else if("tl".equals(payWay)){
            holder.tv_pay_way.setText("微信");
        }else if("r".equals(payWay)){
            holder.tv_pay_way.setText("微信");
        }

        holder.tv_buy_num.setText(flowBean.getBuyCount()!= null && flowBean.getBuyCount().intValue()>0?flowBean.getBuyCount().intValue()+"":"—");

        holder.tv_sale_emploee.setText(flowBean.getStaffName()!= null && !"".equals(flowBean.getStaffName())?flowBean.getStaffName():"—");
        return view;
    }



    class ViewHolder {
        @BindView(R.id.tv_order_code)
        public TextView tv_order_code;
        @BindView(R.id.tv_pay_way)
        public TextView tv_pay_way;
        @BindView(R.id.tv_product_name)
        public TextView tv_product_name;
        @BindView(R.id.tv_buy_phone)
        public TextView tv_buy_phone;
        @BindView(R.id.tv_sale_time)
        public TextView tv_sale_time;
        @BindView(R.id.tv_pay_amt)
        public TextView tv_pay_amt;
        @BindView(R.id.tv_red_packget_amt)
        public TextView tv_red_packget_amt;
        @BindView(R.id.tv_sale_emploee)
        public TextView tv_sale_emploee;
        @BindView(R.id.tv_buy_num)
        public TextView tv_buy_num;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
