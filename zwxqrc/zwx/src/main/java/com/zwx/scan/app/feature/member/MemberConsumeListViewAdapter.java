package com.zwx.scan.app.feature.member;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.TextColorSizeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.MemberConsumeBean;
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.feature.financialaffairs.CollectionIntoAmtOrderAdapter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/06/13
 * desc   :
 * version: 1.0
 **/
public class MemberConsumeListViewAdapter extends BaseAdapter {
    private List<MemberConsumeBean.MemberIT.MemberConsume> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public MemberConsumeListViewAdapter(Context context, List<MemberConsumeBean.MemberIT.MemberConsume> data) {
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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_member_consume_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        MemberConsumeBean.MemberIT.MemberConsume memberConsume = data.get(position);

        BigDecimal amt = memberConsume.getCompEatTotalAmt();
        String consume = "0.00";
        if(amt == null &&amt.doubleValue() ==0.0){
            consume = "-";
        }else {
            consume = "￥"+new DecimalFormat("0.00").format(amt.setScale(2,BigDecimal.ROUND_DOWN).doubleValue()).toString();
        }

        String total =  memberConsume.getCompEatTotalQnt() != null ? memberConsume.getCompEatTotalQnt()+"":"-";

        String personQnt = "消费次数："+total;
        int subTextColor = Color.parseColor("#333333");
        String[] subTextArray = {"消费次数："};

        String consumeTime = memberConsume.getConsumeTime();
        String memTypeName = memberConsume.getMemTypeName();
        String name = memberConsume.getMemberName();
        holder.tv_memtype_name.setText(memTypeName);
        holder.ll_memtype_name.setVisibility(View.INVISIBLE);
//        String name = couponName.replace("\\,","\n");

        holder.tv_tel.setText(memberConsume.getMemberTel()!=null?memberConsume.getMemberTel():"");
        holder.tv_consume_count.setText(TextColorSizeUtils.getTextSpan(context,subTextColor,32,personQnt,subTextArray));


        holder.tv_consume_time.setText(consumeTime);
        holder.tv_money .setText(consume);
        holder.tv_name.setText( name != null ?name:"");
        holder.ll_memtype.setVisibility(View.GONE);

        return convertView;

    }



    class ViewHolder {
        @BindView(R.id.tv_tel)
        public TextView tv_tel;
        @BindView(R.id.tv_name)
        public TextView tv_name;
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
        @BindView(R.id.ll_memtype)
        public LinearLayout ll_memtype;
        @BindView(R.id.ll_memtype_name)
        public LinearLayout ll_memtype_name;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

      /*  public ChildViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_tel = (TextView) view.findViewById(R.id.tv_tel);
            tv_consume_count = (TextView) view.findViewById(R.id.tv_consume_count);
            tv_memtype_name = (TextView) view.findViewById(R.id.tv_memtype_name);
            tv_consume_time = (TextView) view.findViewById(R.id.tv_consume_time);
            tv_money = (TextView) view.findViewById(R.id.tv_money);
            ll_memtype = (LinearLayout) view.findViewById(R.id.ll_memtype);
            ll_memtype_name = (LinearLayout) view.findViewById(R.id.ll_memtype_name);
        }*/
    }

}
