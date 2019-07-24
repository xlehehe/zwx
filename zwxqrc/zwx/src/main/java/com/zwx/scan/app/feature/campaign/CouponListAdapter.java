package com.zwx.scan.app.feature.campaign;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.CouponMaterial;
import com.zwx.scan.app.data.http.HttpUrls;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/24
 * desc   :
 * version: 1.0
 **/
public class CouponListAdapter extends RecyclerView.Adapter {

    private List<Coupon> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private  int selectNum = 0;

    public CouponListAdapter(Context context, List<Coupon> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (data.size() == 0) {
            return 0;
        } else {
            return data.size();
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (data.size() == 0) {
            return;
        }
        ChildViewHolder holder = (ChildViewHolder) viewHolder;
        Coupon  coupon= data.get(position);
//        String  money = String.valueOf(coupon.getMoney());

        String price = "";
//        DecimalFormat decimalFormat = new DecimalFormat("0.00");

       /* if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.tv_name.setText(Html.fromHtml(price,Html.FROM_HTML_MODE_LEGACY));
        }else {
            holder.tv_name.setText(Html.fromHtml(price));
        }*/
        String name = coupon.getName();
        holder.tv_coupon_name.setText(name!=null?name:"");
        String noItem = coupon.getNoItem();
        Integer timePeriod =coupon.getTimePeriod();
        String noDate = coupon.getNoDate();
        //0：没有日期限制；
        //1：周六，周日不可用；
        //2：自定义日期不可用

        String dateCode = coupon.getDateCode();
        if(dateCode!=null && !"".equals(dateCode)){
            if("00000".equals(dateCode)){   //不展示
              /*  if(noDate !=null && !"".equals(noDate)){
                    holder.tv_no_date.setText(noDate);
                }else {
                    holder.tv_no_date.setText("");
                }*/
                holder.tv_no_date.setText("");
                holder.line.setVisibility(View.GONE);
            }else { //不展示
                if(noDate !=null && !"".equals(noDate)){
                    holder.tv_no_date.setText(noDate);
                }else {
                    holder.tv_no_date.setText("");
                    holder.line.setVisibility(View.GONE);
                }
            }
        }

        if(timePeriod !=null ){

            if(timePeriod == 1){
                holder.tv_time_period.setText("全天");
            }else if(timePeriod == 2){
                holder.tv_time_period.setText("午市");
            }else if(timePeriod == 3){
                holder.tv_time_period.setText("晚市");
            }
        }else {
            holder.tv_time_period.setText("");
            holder.line.setVisibility(View.GONE);
        }




        holder.tv_coupon_price.setText(noItem!=null&&!"".equals(noItem)?noItem:"");
//        holder.checkBox.setChecked(coupon.isChecked());
        String object = coupon.getObject();
        if(coupon.isChecked()){
            holder.checkBox.setButtonDrawable(R.drawable.ic_red_selected);
        }else {
            holder.checkBox.setButtonDrawable(R.drawable.ic_gray_unselect);
        }

        if(coupon.getLimit()>0){
//            String limit =  new DecimalFormat("0").format(new BigDecimal(coupon.getLimit()).setScale(2, BigDecimal.ROUND_UNNECESSARY).doubleValue()).toString();
//            String limit = "";
         /*   if(String.valueOf(coupon.getLimit()).contains(".0")||String.valueOf(coupon.getLimit()).contains(".00")){
                limit = String.valueOf(coupon.getLimit()).substring(0,String.valueOf(coupon.getLimit()).indexOf("."));
            }else {
                if(String.valueOf(coupon.getDiscount()).contains(".0")||String.valueOf(coupon.getDiscount()).contains(".00")){
                    limit = String.valueOf(coupon.getDiscount()).substring(0,String.valueOf(coupon.getDiscount()).indexOf("."));
                }else {
                    limit = String.valueOf(coupon.getDiscount());
                }


            }*/

            String limit = RegexUtils.getDoubleString(Double.parseDouble(String.valueOf(coupon.getLimit())));
            holder.tv_coupon_price.setText("满"+limit+"元可用");
        }else {
            holder.tv_coupon_price.setText("任意消费可用");
        }
        SpannableString spannableString = null;
        String pri = "";
        CouponMaterial material = coupon.getMaterial();
        String background = "";
        if(material != null){
            background = material.getBackground();
        }

        String bgPath = HttpUrls.IMAGE_ULR + background;
        if("CPC".equals(coupon.getType())){
            if(coupon.getMoney()!=null && coupon.getMoney() != 0){
                BigDecimal money = new BigDecimal(coupon.getMoney());
                price =  new DecimalFormat("0").format(money.setScale(2, BigDecimal.ROUND_UNNECESSARY).doubleValue()).toString();
            }else {
                price = "0";

            }
            pri = price+"元";
            spannableString= new SpannableString(pri);
            spannableString.setSpan(new AbsoluteSizeSpan(30,true), 0, price.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            holder.tv_name.setText(spannableString );
//            holder.iv_avatar.setBackgroundResource(R.drawable.ic_coupon_price);



        }else if("CPD".equals(coupon.getType())){
            String discount = "";
            String dis = "";
            if(coupon.getDiscount()!=null && coupon.getDiscount() != 0){
//                BigDecimal discounts = new BigDecimal(coupon.getDiscount());
//                discount =  new DecimalFormat("0").format(discounts.setScale(2, BigDecimal.ROUND_UNNECESSARY).doubleValue()).toString();
               /* if(String.valueOf(coupon.getDiscount()).contains(".0")){
                    discount = String.valueOf(coupon.getDiscount()).substring(0,String.valueOf(coupon.getDiscount()).indexOf("."));
                }else {
                    discount = String.valueOf(coupon.getDiscount());
                }*/
               discount = RegexUtils.getDoubleString(Double.parseDouble(String.valueOf(coupon.getDiscount())));

            }else {

                discount = "0";

            }
            dis = discount +"折";
            spannableString= new SpannableString(dis);
            spannableString.setSpan(new AbsoluteSizeSpan(30,true), 0, discount.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            holder.tv_name.setText(spannableString);
//            holder.iv_head.setBackgroundResource(R.drawable.ic_coupon_discount);


        }else if("CPO".equals(coupon.getType())){
//            holder.iv_head.setBackgroundResource(R.drawable.ic_coupon_prese);
            holder.tv_name.setText(object!=null?object:"");

        }else if("CPU".equals(coupon.getType())){
//            holder.iv_head.setBackgroundResource(R.drawable.ic_coupon_dishes);

            holder.tv_name.setText(object!=null?object:"");

        }else if("CPJ".equals(coupon.getType())){
//            holder.iv_head.setBackgroundResource(R.drawable.ic_coupon_paidui);
            holder.tv_name.setText("插队券");

        }else if("CPT".equals(coupon.getType())){  //其他
            String other = coupon.getOther();
            holder.tv_name.setText(other!=null?other:"");
//            holder.iv_head.setBackgroundResource(R.drawable.ic_coupon_other);

        }
//        RoundedCorners roundedCorners= new RoundedCorners(8);
        RequestOptions requestOptions2 = new RequestOptions()
//                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_load_fail)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_load_fail);

        Glide.with(context).load(bgPath).apply(requestOptions2).into(holder.iv_avatar);
     /*   Glide.with(context).load(bgPath).into(new SimpleTarget<Drawable>(220,220) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.iv_avatar.setBackground(resource);    //设置背景
                }
            }
        });*/

    }


    protected   void  setRemoveUnchecked(Coupon removeCoupon){

        if(data !=null && data.size()>0){
            for (int i = 0;i <data.size();i++){
                Coupon couponUn = data.get(i);
                if(couponUn!=null){
                    boolean unChecked = couponUn.isChecked();
                    if(!unChecked){
                        String removeName = removeCoupon.getName();
                        String removeName2 = couponUn.getName();
                        if(removeName!=null){
                            if(removeName2!=null){
                                if(removeName.equals(removeName2)){
                                    data.remove(i);
                                    i--;
                                }
                            }
                        }
                    }


                }
            }
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_coupon_list, null);
        return new ChildViewHolder(view);
    }
    private class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
//        public TextView tv_time;
        public CheckBox checkBox;
        public  TextView tv_time_period;
        public  TextView tv_no_date;
        public TextView tv_coupon_price;
        public ImageView iv_avatar;
        public TextView tv_coupon_name;
        public View line;

        public ChildViewHolder(View view) {
            super(view);
            tv_coupon_name = (TextView) view.findViewById(R.id.tv_coupon_name);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
//            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_time_period = (TextView) view.findViewById(R.id.tv_time_period);
            tv_no_date = (TextView) view.findViewById(R.id.tv_no_date);
            checkBox = (CheckBox) view.findViewById(R.id.scb);
            tv_coupon_price = (TextView)view.findViewById(R.id.tv_coupon_price);
            iv_avatar = (ImageView) view.findViewById(R.id.iv_avatar);

            line = (View)view.findViewById(R.id.line);
        }
    }

    public void toggleSelection(boolean isChecked) {
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                if(i<=2){
                    data.get(i).setChecked(isChecked);
                }

            }
        }
        this.notifyDataSetChanged();
    }
    public int getCheck(){
        int select=0;
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                if(data.get(i).isChecked()){
                    select++;
                }

            }
        }
        return select;
    }

}
