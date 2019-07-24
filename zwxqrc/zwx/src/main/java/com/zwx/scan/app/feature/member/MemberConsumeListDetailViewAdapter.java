package com.zwx.scan.app.feature.member;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwx.library.utils.TextColorSizeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.ComsumeLog;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/06/17
 * desc   :
 * version: 1.0
 **/
public class MemberConsumeListDetailViewAdapter extends BaseAdapter {
    private List<ComsumeLog> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public MemberConsumeListDetailViewAdapter(Context context, List<ComsumeLog> data) {
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
        final ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_member_consume_list, parent, false);
            holder = new ViewHolder(view);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        ComsumeLog  memberInfo= data.get(position);
        String time = "—";
        String couponName = "";
        String storeName = "";
        BigDecimal amt = null;
        String total = "—";
        String compMemberGroup = "—";
        if(memberInfo != null ){
            time = memberInfo.getConsumeTime();
            couponName = memberInfo.getCouponName();
            storeName = memberInfo.getStoreName();
            amt = memberInfo.getConsumeAmt();
            compMemberGroup = memberInfo.getCompMemberGroup();
            total = memberInfo.getPersonQnt() != 0 ? memberInfo.getPersonQnt()+"":"—";

            if(compMemberGroup != null && !"".equals(compMemberGroup)){
            }else {
                compMemberGroup ="暂无";
            }
        }



//        String memberName = memberInfo.getMemberName();

        String consume = "—";
        if(amt != null && amt.doubleValue()>0){
//            consume = RegexUtils.getDoubleString(amt.doubleValue());
            consume = new DecimalFormat("0.00").format(amt.setScale(2,BigDecimal.ROUND_DOWN).doubleValue()).toString();
        }else {
        }


        String personQnt = "就餐人数："+total;
        int subTextColor = Color.parseColor("#333333");
        String[] subTextArray = {"就餐人数："};
        holder.tv_time_label.setText("消费时间：");
        holder.tv_card_label.setText("优惠券：");
        holder.tv_time_label.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        holder.tv_card_label.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        String couponName1  = "";
        StringBuilder sb = new StringBuilder();
        if(couponName != null && !"".equals(couponName)){

            if(couponName.contains(",")){
                couponName1 = couponName.replaceAll(",","\n");
            }else {
                couponName1 = couponName;
            }

        }else {

        }



        holder.tv_memtype_name.setText(couponName1);


        holder.tv_tel.setText(storeName);
        holder.tv_consume_count.setText(TextColorSizeUtils.getTextSpan(context,subTextColor,32,personQnt,subTextArray));
        holder.tv_consume_time.setText(time);
        holder.tv_money.setText("￥"+consume);


        holder.tv_memtype_name2.setText(compMemberGroup);
        return view;
    }


    class ViewHolder {
        @BindView(R.id.tv_tel)
        public TextView tv_tel;
        @BindView(R.id.tv_consume_count)
        public TextView tv_consume_count;
        @BindView(R.id.tv_memtype_name)
        public TextView tv_memtype_name;
        @BindView(R.id.tv_consume_time)
        public TextView tv_consume_time;
        @BindView(R.id.tv_money)
        public TextView tv_money;
        @BindView(R.id.tv_card_label)
        public TextView tv_card_label;
        @BindView(R.id.tv_time_label)
        public TextView tv_time_label;
        @BindView(R.id.tv_memtype_name2)
        public TextView tv_memtype_name2;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
