package com.zwx.scan.app.feature.campaign;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
 * time   : 2019/06/06
 * desc   :
 * version: 1.0
 **/
public class CouponListViewAdapter extends BaseAdapter {
    private List<Coupon> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private  int selectNum = 0;

    public CouponListViewAdapter(Context context, List<Coupon> data) {
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
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_coupon_list, parent, false); //加载布局
            holder = new ViewHolder();

            holder.tv_coupon_name = (TextView) view.findViewById(R.id.tv_coupon_name);
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
//            tv_time = (TextView) view.findViewById(R.id.tv_time);
            holder.tv_time_period = (TextView) view.findViewById(R.id.tv_time_period);
            holder.tv_no_date = (TextView) view.findViewById(R.id.tv_no_date);
            holder.checkBox = (CheckBox) view.findViewById(R.id.scb);
            holder.tv_coupon_price = (TextView)view.findViewById(R.id.tv_coupon_price);
            holder.iv_avatar = (ImageView) view.findViewById(R.id.iv_avatar);

            holder.line = (View)view.findViewById(R.id.line);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        Coupon  coupon= data.get(position);

        String price = "";

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
        String object = coupon.getObject();
        if(coupon.isChecked()){
            holder.checkBox.setButtonDrawable(R.drawable.ic_red_selected);
            holder.checkBox.setChecked(true);
        }else {
            holder.checkBox.setButtonDrawable(R.drawable.ic_gray_unselect);
            holder.checkBox.setChecked(false);
        }

        if(coupon.getLimit()>0){


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


        return view;
    }


    private class ViewHolder  {
        public TextView tv_name;
        //        public TextView tv_time;
        public CheckBox checkBox;
        public  TextView tv_time_period;
        public  TextView tv_no_date;
        public TextView tv_coupon_price;
        public ImageView iv_avatar;
        public TextView tv_coupon_name;
        public View line;

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
