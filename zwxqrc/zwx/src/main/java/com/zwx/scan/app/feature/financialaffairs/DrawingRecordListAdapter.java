package com.zwx.scan.app.feature.financialaffairs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.TradeDrawingRecordResultBean.DrawingRecordBean;
import com.zwx.scan.app.data.bean.TradeDrawingRecordResultBean;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.shop.ProductEventBus;
import com.zwx.scan.app.feature.shop.ShopOrderListViewAdapter;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/05/21
 * desc   :
 * version: 1.0
 **/
public class DrawingRecordListAdapter extends BaseAdapter {

    private List<TradeDrawingRecordResultBean.DrawingRecordBean> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public DrawingRecordListAdapter(Context context, List<TradeDrawingRecordResultBean.DrawingRecordBean> data) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_trade_drawing_record_list, parent, false);
            holder = new ViewHolder(view);


            holder.tv_daozhang_amt = (TextView) view.findViewById(R.id.tv_daozhang_amt);
            holder.iv_drawing = (ImageView) view.findViewById(R.id.iv_drawing);
            holder.tv_draing_time = (TextView) view.findViewById(R.id.tv_draing_time);
            holder.tv_draing_amt = (TextView) view.findViewById(R.id.tv_draing_amt);
            holder.tv_procedures_fee = (TextView) view.findViewById(R.id.tv_procedures_fee);

            holder.tv_operate_ren = (TextView) view.findViewById(R.id.tv_operate_ren);
            holder.tv_operate_tel = (TextView) view.findViewById(R.id.tv_operate_tel);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        TradeDrawingRecordResultBean.DrawingRecordBean bean = data.get(position);

       /* //订单数量
        Long orderCount = order.getBuyCount();
        BigDecimal total =  order.getUnitPrice();
        String money = "";
        if(total != null && total.doubleValue()>0){
            money = RegexUtils.getDoubleString(total.doubleValue()*orderCount.intValue());
            if(!"0".equals(money) &&!"0.0".equals(money)){

            }else {
                money = "-";
            }
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
        BigDecimal usrRedEnvelope = order.getUseRedEnvelope();
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
        holder.iv_post_way.setImageResource(R.drawable.ic_ziti);

        holder.tv_date.setText(time);



        Glide.with(context).load(imgPath).apply(requestOptions).into(holder.iv_img);

        String productName = order.getProductName();

        holder.tv_good_name.setText(productName != null && !"".equals(productName)?productName:"-");

        //商品编码
        holder.tv_good_id.setText(order.getProductCode() != null && !"".equals(order.getProductCode())?order.getProductCode():"");



        holder.tv_good_num.setText(order.getBuyCount()!= null && order.getBuyCount().intValue()>0?"x"+order.getBuyCount().intValue():"-");

        String deliveryType = order.getDelivType();

        if("邮寄".equals(deliveryType)){
            holder.iv_post_way.setImageResource(R.drawable.ic_shop_order_yj);
        }else if("自提".equals(deliveryType)){
            holder.iv_post_way.setImageResource(R.drawable.ic_ziti);
        }
        String status =order.getStatus();
        if("未兑换".equals(status)){
            holder.iv_duihuan.setImageResource(R.drawable.ic_duihuan_unselect);
            holder.ll_post.setVisibility(View.GONE);
        }else if("已兑换".equals(status)){
            holder.iv_duihuan.setImageResource(R.drawable.ic_duihuan_selected);
            holder.ll_post.setVisibility(View.GONE);
        }else if("待邮寄".equals(status)){
            holder.iv_duihuan.setImageResource(R.drawable.ic_good_post_unselect);
            holder.ll_post.setVisibility(View.VISIBLE);
        }else if("已邮寄".equals(status)){
            holder.iv_duihuan.setImageResource(R.drawable.ic_good_post_selected);
            holder.ll_post.setVisibility(View.GONE);
        }*/

       BigDecimal am = bean.getAmount();

        BigDecimal fe = bean.getFee();

        String toAmt = "—";
        String fee = "—";
        String amount = "—";
        if(am != null && am.doubleValue()>=0){
            amount = RegexUtils.getDoubleString(am.doubleValue());
            if(fe != null &&  fe.doubleValue()>=0){
                fee = RegexUtils.getDoubleString(fe.doubleValue());
                if(am.doubleValue()>=fe.doubleValue()){
                    double total = am.doubleValue() - fe.doubleValue();
                    toAmt = String.valueOf(total);
                }
            }

            if(fe.doubleValue() == 0){
                fee = "0.00";
            }
        }else {
            if(fe != null &&  fe.doubleValue()>=0){
                fee = RegexUtils.getDoubleString(fe.doubleValue());
                if(am.doubleValue()>=fe.doubleValue()){
                    double total = am.doubleValue() - fe.doubleValue();
                    toAmt = String.valueOf(total);
                }

            }

            if(fe.doubleValue() == 0){
                fee = "0.00";
            }
        }
        holder.tv_draing_amt.setText(amount+"元");
        holder.tv_daozhang_amt.setText(toAmt+"元");
        holder.tv_procedures_fee.setText(fee+"元");


        String time = bean.getCreateTime();

        if(time != null && !"".equals(time)){
            time = time.replace("-",".");
        }else {
            time = "—";
        }
        holder.tv_draing_time.setText(time);

        String status = bean.getStatus();

        if("1".equals(status)){
            holder.iv_drawing.setImageResource(R.drawable.ic_drawing_money_success);
        }else if("0".equals(status)){
            holder.iv_drawing.setImageResource(R.drawable.ic_drawing_money_fail);
        }

        String createTel = bean.getCreateTel();
        String createName = bean.getCreateName();

        if(createTel != null && !"".equals(createTel)){
            holder.tv_operate_tel.setText(createTel);
        }else {
            holder.tv_operate_tel.setText("—");
        }

        if(createName != null && !"".equals(createName)){
            holder.tv_operate_ren.setText(createName);
        }else {
            holder.tv_operate_ren.setText("—");
        }
        return view;
    }



    class ViewHolder {
        @BindView(R.id.tv_daozhang_amt)
        public TextView tv_daozhang_amt;
        @BindView(R.id.tv_draing_time)
        public TextView tv_draing_time;
        @BindView(R.id.tv_draing_amt)
        public TextView tv_draing_amt;
        @BindView(R.id.tv_procedures_fee)
        public TextView tv_procedures_fee;
        @BindView(R.id.tv_operate_ren)
        public TextView tv_operate_ren;
        @BindView(R.id.iv_drawing)
        public ImageView iv_drawing;
        @BindView(R.id.tv_operate_tel)
        public TextView tv_operate_tel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
