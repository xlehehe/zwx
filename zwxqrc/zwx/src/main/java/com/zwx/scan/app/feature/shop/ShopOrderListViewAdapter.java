package com.zwx.scan.app.feature.shop;

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
import com.zwx.scan.app.data.bean.TOrderConsumeLog;
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.data.http.HttpUrls;

import org.greenrobot.eventbus.EventBus;

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
public class ShopOrderListViewAdapter extends BaseAdapter {

    private List<TOrderObject> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public ShopOrderListViewAdapter(Context context, List<TOrderObject> data) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_shop_order_list, parent, false);
            holder = new ViewHolder(view);


            holder.tv_order_num = (TextView) view.findViewById(R.id.tv_order_num);
            holder.tv_good_amt = (TextView) view.findViewById(R.id.tv_good_amt);
            holder.tv_order_pay_amt = (TextView) view.findViewById(R.id.tv_order_pay_amt);
            holder.tv_red_packet = (TextView) view.findViewById(R.id.tv_red_packet);
            holder.tv_member_info = (TextView) view.findViewById(R.id.tv_member_info);
            holder.tv_date = (TextView) view.findViewById(R.id.tv_date);
            holder.iv_img = (ImageView) view.findViewById(R.id.iv_img);

            holder.tv_good_name = (TextView) view.findViewById(R.id.tv_good_name);
            holder.tv_good_num = (TextView) view.findViewById(R.id.tv_good_num);
            holder.tv_good_id = (TextView) view.findViewById(R.id.tv_good_id);
            holder.iv_post_way = (ImageView) view.findViewById(R.id.iv_post_way);
            holder.iv_duihuan = (ImageView) view.findViewById(R.id.iv_duihuan);
            //添加确认邮寄
            holder.ll_post = (LinearLayout) view.findViewById(R.id.ll_post);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        TOrderObject order = data.get(position);

        //订单数量
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

        String imgPath = HttpUrls.IMAGE_ULR+ order.getThumbnailUrl();
        RoundedCorners roundedCorners= new RoundedCorners(8);

        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_load_fail)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_load_fail);

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
        }


        holder.ll_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ProductEventBus(String.valueOf(order.getDetailedId()),order.getStatus(),""));
            }
        });


        return view;
    }



    class ViewHolder {
        @BindView(R.id.tv_order_num)
        public TextView tv_order_num;
        @BindView(R.id.tv_good_amt)
        public TextView tv_good_amt;
        @BindView(R.id.tv_order_pay_amt)
        public TextView tv_order_pay_amt;
        @BindView(R.id.tv_red_packet)
        public TextView tv_red_packet;
        @BindView(R.id.tv_member_info)
        public TextView tv_member_info;
        @BindView(R.id.tv_date)
        public TextView tv_date;
        @BindView(R.id.iv_img)
        public ImageView iv_img;
        @BindView(R.id.tv_good_name)
        public TextView tv_good_name;
        @BindView(R.id.tv_good_num)
        public TextView tv_good_num;
        @BindView(R.id.tv_good_id)
        public TextView tv_good_id;
        @BindView(R.id.iv_post_way)
        public ImageView iv_post_way;
        @BindView(R.id.iv_duihuan)
        public ImageView iv_duihuan;

        @BindView(R.id.ll_post)
        public LinearLayout ll_post;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
