package com.zwx.scan.app.feature.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.data.http.HttpUrls;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/04/18
 * desc   :
 * version: 1.0
 **/
public class ShopOrderDetailAdapter extends BaseAdapter{

    private List<TOrderObject> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public ShopOrderDetailAdapter(Context context, List<TOrderObject> data) {
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

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shop_order_detail, parent, false); //加载布局
            holder = new ViewHolder();

            holder.tv_duihuan_time = (TextView) convertView.findViewById(R.id.tv_duihuan_time);
            holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            holder.iv_duihuan = (ImageView) convertView.findViewById(R.id.iv_duihuan);
            holder.tv_good_name = (TextView) convertView.findViewById(R.id.tv_good_name);

            holder.tv_sale_price = (TextView) convertView.findViewById(R.id.tv_sale_price);

            holder.tv_good_spec =(TextView) convertView.findViewById(R.id.tv_good_spec);
            holder.tv_duihuan = (TextView) convertView.findViewById(R.id.tv_duihuan);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        TOrderObject order = data.get(position);

        String amt = order.getFactSalesPrice();

        String productSpec = order.getProductSpec();
        if(productSpec != null && !"".equals(productSpec)){

        }else {
            productSpec = "—";
        }
        holder.tv_good_spec.setText(productSpec);
        BigDecimal unitPrice =  order.getUnitPrice();
        String money = "";
        if(unitPrice != null && unitPrice.doubleValue()>0){
            money = RegexUtils.getDoubleString(unitPrice.doubleValue());
        }else {
            money = "—";
        }
        holder.tv_sale_price.setText(money);

        //商品时间
        String time = order.getConsumeTime();

        holder.tv_duihuan_time.setText(time!= null && !"".equals(time)?time.replace("-","."):"—");
        String status = order.getCouponStatus();
         if("U".equals(status)){
            holder.iv_duihuan.setImageResource(R.drawable.ic_duihuan_selected);
        }else if("N".equals(status)){
            holder.iv_duihuan.setImageResource(R.drawable.ic_duihuan_unselect);
        }else {

         }


         if(time != null && !"".equals(time)){
             holder.iv_duihuan.setImageResource(R.drawable.ic_duihuan_selected);
         }else {
             holder.iv_duihuan.setImageResource(R.drawable.ic_duihuan_unselect);
         }



        //商品名称
        holder.tv_good_name.setText(order.getProductName() != null && !"".equals(order.getProductName())?order.getProductName():"—");


        String imgPath = HttpUrls.IMAGE_ULR+ order.getThumbnailUrl();
        RoundedCorners roundedCorners= new RoundedCorners(8);

        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_load_fail)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_load_fail);

        Glide.with(context).load(imgPath).apply(requestOptions).into(holder.iv_img);

        String storeName = order.getStoreName();

        holder.tv_duihuan.setText(storeName != null && !"".equals(storeName)?storeName:"—");
        return convertView;
    }



    private class ViewHolder{
        public TextView tv_duihuan_time;
        public ImageView iv_duihuan;
        public ImageView iv_img;
        public TextView tv_good_name;
        public TextView tv_sale_price;
        public TextView tv_duihuan;

        public TextView  tv_good_spec;

    }
}
