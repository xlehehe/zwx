package com.zwx.scan.app.feature.shop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.MemberRedEnvelope;
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.data.http.HttpUrls;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/04/25
 * desc   :
 * version: 1.0
 **/
public class RedEnvelopeListAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {


    private List<MemberRedEnvelope> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public RedEnvelopeListAdapter(Context context, List<MemberRedEnvelope> data) {
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
        MemberRedEnvelope envelope = data.get(position);


     /*   //订单数量
        Integer orderCount = order.getSumnum();
        BigDecimal total =  order.getUnitPrice();
        String money = "";
        if(total != null && total.doubleValue()>0){
            money = String.valueOf(total.doubleValue()*orderCount.intValue());
        }else {
            money = "-";
        }

        //商品总额
        holder.tv_good_amt.setText(money);

        //商品名称
        holder.tv_good_name.setText(order.getProductName() != null && !"".equals(order.getProductName())?order.getProductName():"");

        //商品金额
        BigDecimal payAmount = order.getPayAmount();
        String amount = "-";
        if(payAmount != null && payAmount.doubleValue()>0){
            amount = String.valueOf(payAmount.doubleValue());

        }

        holder.tv_order_pay_amt.setText(amount);


        //实际付款
        String price = "-";
        BigDecimal pri = order.getPayAmount();
        if(pri != null && pri.doubleValue()>0){


            price = RegexUtils.getDoubleString(pri.doubleValue());
        }
        holder.tv_order_pay_amt.setText(price);

        //订单编号
        holder.tv_order_num.setText(order.getOrderCode() != null && !"".equals(order.getOrderCode())?String.valueOf(order.getOrderCode()):"-");

        //红包
        BigDecimal usrRedEnvelope = order.getUsrRedEnvelope();
        String redEnvelope = "-";
        if( usrRedEnvelope != null && usrRedEnvelope.doubleValue()>0){
            redEnvelope = RegexUtils.getDoubleString(usrRedEnvelope.doubleValue());
        }
        holder.tv_red_packet.setText(redEnvelope);

        //会员信息

        String memberInfo = order.getMemberTel();

        if(memberInfo != null && !"".equals(memberInfo)){

        }else {
            memberInfo  = "-";
        }

        holder.tv_member_info.setText(memberInfo);

        String time = order.getSalesTime();

        if(time != null && !"".equals(time)){
            time = time.replace("-",".");
        }else {
            time = "-";
        }

        String deType = order.getDelivType();

        if("1".equals(deType)){
            holder.iv_ziti.setImageResource(R.drawable.ic_ziti);
        }else if("2".equals(deType)){
            holder.iv_ziti.setImageResource(R.drawable.ic_shop_order_yj);
        }else {
            holder.iv_ziti.setVisibility(View.INVISIBLE);
        }
        holder.tv_date.setText(time);



        String productName = order.getProductName();

        holder.tv_good_name.setText(productName != null && !"".equals(productName)?productName:"-");

        //商品编码
        holder.tv_good_id.setText(order.getProductCode() != null && !"".equals(order.getProductCode())?order.getProductCode():"");
*/

        BigDecimal amt = envelope.getBalance();
        if(amt != null &&amt.doubleValue()>0){
            holder.tv_red_amt.setText(RegexUtils.getDoubleString(amt.doubleValue())+"元");
        }else {
            holder.tv_red_amt.setText("0元");
        }
        String name = envelope.getMemberName();
        if(name != null && !"".equals(name)){
            holder.tv_name.setText(name);
        }else {
            holder.tv_name.setText("-");
        }
        String phone = envelope.getMemberTel();
        if(phone != null && !"".equals(phone)){
            holder.tv_phone.setText(phone);
        }else {
            holder.tv_phone.setText("-");
        }
        int cout = position+1;
        holder.tv_red_id.setText(cout+"");



    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_red_envelope_list, null);
        return new ChildViewHolder(view);
    }
    private class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_red_id;

        public TextView tv_name;
        public TextView tv_phone;
        public TextView tv_red_amt;
       /* public TextView tv_date;

        public ImageView iv_img;

        public TextView tv_good_name;
        public TextView tv_good_num;

        public TextView tv_good_id;
        public ImageView iv_ziti;
        public ImageView iv_duihuan;*/
        public ChildViewHolder(View view) {
            super(view);
            tv_red_id = (TextView) view.findViewById(R.id.tv_red_id);
            tv_red_amt = (TextView) view.findViewById(R.id.tv_red_amt);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_phone = (TextView) view.findViewById(R.id.tv_phone);
          /*  tv_member_info = (TextView) view.findViewById(R.id.tv_member_info);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            iv_img = (ImageView) view.findViewById(R.id.iv_img);

            tv_good_name = (TextView) view.findViewById(R.id.tv_good_name);
            tv_good_num = (TextView) view.findViewById(R.id.tv_good_num);
            tv_good_id = (TextView) view.findViewById(R.id.tv_good_id);
            iv_ziti = (ImageView) view.findViewById(R.id.iv_ziti);
            iv_duihuan = (ImageView) view.findViewById(R.id.iv_duihuan);*/
        }


    }
}
