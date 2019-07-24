package com.zwx.scan.app.feature.financemanage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.ReceiveFund;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/01/26
 * desc   :
 * version: 1.0
 **/
public class PayFeeListAdapter extends RecyclerView.Adapter {

    private List<ReceiveFund> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private  int selectNum = 0;
    private String isPayFee;
    private OnCustomClickListener onCustomClickListener;
    public PayFeeListAdapter(Context context, List<ReceiveFund> data,String isPayFee) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
        this.isPayFee = isPayFee;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (data.size() == 0) {
            return;
        }
        ChildViewHolder holder = (ChildViewHolder) viewHolder;
        ReceiveFund receiveFund = data.get(position);

        String price = "";
        if (receiveFund.isChecked()) {
            holder.checkBox.setButtonDrawable(R.drawable.ic_blue_selected);
        } else {
            holder.checkBox.setButtonDrawable(R.drawable.ic_gray_unselect);
        }
        SpannableString spannableString = null;

        BigDecimal money = receiveFund.getMoney();
        String moneyPr = "0.00";
        if (money != null && money.doubleValue() > 0) {

            moneyPr = new DecimalFormat("0.00").format(money.setScale(2, BigDecimal.ROUND_DOWN).doubleValue()).toString();
        } else {
            moneyPr = "0.00";


            moneyPr = "￥" + moneyPr;
            spannableString = new SpannableString(moneyPr);
            spannableString.setSpan(new AbsoluteSizeSpan(30, true), 0, price.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            holder.tv_money.setText(spannableString);
            String date = receiveFund.getFactReceiveDate();
            holder.tv_pay_time.setText(date!= null ?date: "");
            if ("1".equals(receiveFund.getIsOverdue())){
                holder.tv_status.setText("已逾期");
                holder.tv_status.setVisibility(View.VISIBLE);
            }else {
                holder.tv_status.setVisibility(View.GONE);
            }

            holder.tv_pay_no.setText(String.valueOf(receiveFund.getFundId()));

            if("NO".equals(isPayFee)){
                holder.ll_cb.setVisibility(View.VISIBLE);
            }else if("YES".equals(isPayFee)){
                holder.ll_cb.setVisibility(View.GONE);
            }

            holder.ll_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,PayFeeListDetailActivity.class);

                    intent.putExtra("isPayFee",isPayFee);

                    ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                }
            });

            holder.ll_bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(onCustomClickListener != null){
                        onCustomClickListener.onclick(v,position);
                    }

                }
            });


        }
    }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup viewHolder,int position){
            View view = inflater.inflate(R.layout.item_pay_fee_manage_list, null);
            return new ChildViewHolder(view);
        }
        private class ChildViewHolder extends RecyclerView.ViewHolder {
            public CheckBox checkBox;
            public TextView tv_money;
            public TextView tv_status;
            public TextView tv_pay_no;
            public TextView tv_pay_time;
            public LinearLayout ll_cb;
            public LinearLayout ll_bottom;
            public LinearLayout ll_top;

            public ChildViewHolder(View view) {
                super(view);
                tv_money = (TextView) view.findViewById(R.id.tv_money);
                tv_status = (TextView) view.findViewById(R.id.tv_status);
                tv_pay_no = (TextView) view.findViewById(R.id.tv_pay_no);
                tv_pay_time = (TextView) view.findViewById(R.id.tv_pay_time);
                checkBox = (CheckBox) view.findViewById(R.id.cb);

                ll_cb = (LinearLayout) view.findViewById(R.id.ll_cb);

                ll_bottom = (LinearLayout) view.findViewById(R.id.ll_bottom);

                ll_top = (LinearLayout) view.findViewById(R.id.ll_top);
            }
        }

        public void toggleSelection ( boolean isChecked){
            if (data != null && data.size() > 0) {
                for (int i = 0; i < data.size(); i++) {
                    if (i <= 2) {
                        data.get(i).setChecked(isChecked);
                    }

                }
            }
            this.notifyDataSetChanged();
        }
        public int getCheck () {
            int select = 0;
            if (data != null && data.size() > 0) {
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).isChecked()) {
                        select++;
                    }

                }
            }
            return select;
        }


    public OnCustomClickListener getOnCustomClickListener() {
        return onCustomClickListener;
    }

    public void setOnCustomClickListener(OnCustomClickListener onCustomClickListener) {
        this.onCustomClickListener = onCustomClickListener;
    }

    public interface OnCustomClickListener{
        public void onclick( View view,int position);
    }

}
