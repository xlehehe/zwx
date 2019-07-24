package com.zwx.scan.app.feature.financemanage;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.DateUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.ReceiveFund;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/01/28
 * desc   :
 * version: 1.0
 **/
public class PayFeeListViewAdapter extends BaseAdapter {

    private List<ReceiveFund> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private  int selectNum = 0;
    private String isPayFee;
    public PayFeeListViewAdapter(Context context, List<ReceiveFund> data) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pay_fee_manage_list, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ReceiveFund receiveFund = data.get(position);

        String price = "";
        if (receiveFund.isChecked()) {
            holder.checkBox.setButtonDrawable(R.drawable.ic_blue_selected);
//            holder.checkBox.setCompoundDrawables(context.getResources().getDrawable(R.drawable.ic_blue_selected),null,null,null);
//            holder.checkBox.setCompoundDrawablePadding(10);
        } else {
            holder.checkBox.setButtonDrawable(R.drawable.ic_gray_unselect);
//            holder.checkBox.setCompoundDrawables(context.getResources().getDrawable(R.drawable.ic_gray_unselect),null,null,null);
//            holder.checkBox.setCompoundDrawablePadding(10);
        }
        SpannableString spannableString = null;

        BigDecimal money = receiveFund.getMoney();
        String moneyPr = "0.00";
        if (money != null && money.doubleValue() > 0) {

            moneyPr = new DecimalFormat("0.00").format(money.setScale(2, BigDecimal.ROUND_DOWN).doubleValue()).toString();
        } else {
            moneyPr = "0.00";

        }
        moneyPr = "￥" + moneyPr;
//        spannableString = new SpannableString(moneyPr);
//        spannableString.setSpan(new AbsoluteSizeSpan(30, true), 0, moneyPr.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        holder.tv_money.setText(moneyPr);
        String date = receiveFund.getPlanReceiveDate();
        if(date != null && !"".equals(date)){
            Date dateTime = DateUtils.parse(date,"yyyy-MM-dd");

            holder.tv_pay_time.setText( DateUtils.formatDate(dateTime,"yyyy-MM-dd").replace("-","."));
        }else {
            holder.tv_pay_time.setText("");
        }

            /*if ("1".equals(receiveFund.getIsOverdue())){
                holder.tv_status.setText("已逾期");
                holder.tv_status.setVisibility(View.VISIBLE);
            }else {
                holder.tv_status.setVisibility(View.GONE);
            }*/
        holder.tv_status.setText(receiveFund.getIsOverdue());
        holder.tv_pay_no.setText(String.valueOf(receiveFund.getFundId()));

        if("U".equals(isShow)){
            holder.ll_cb.setVisibility(View.VISIBLE);
        }else if("R".equals(isShow)){
            holder.ll_cb.setVisibility(View.GONE);
        }


        holder.ll_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((PayFeeManageActivity)context,PayFeeListDetailActivity.class);

                intent.putExtra("status",receiveFund.getStatus());
                intent.putExtra("fundId",String.valueOf(data.get(position).getFundId()));
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        holder.ll_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( (PayFeeManageActivity)context,PayFeeListDetailActivity.class);

                intent.putExtra("status",receiveFund.getStatus());
                intent.putExtra("fundId",String.valueOf(data.get(position).getFundId()));
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        return convertView;
    }

     class ViewHolder  {

        @BindView(R.id.tv_money)
         TextView tv_money;
        @BindView(R.id.tv_status)
         TextView tv_status;
        @BindView(R.id.tv_pay_no)
         TextView tv_pay_no;
        @BindView(R.id.tv_pay_time)
         TextView tv_pay_time;
        @BindView(R.id.ll_cb)
         LinearLayout ll_cb;
        @BindView(R.id.ll_bottom)
         LinearLayout ll_bottom;
        @BindView(R.id.ll_top)
         LinearLayout ll_top;
        @BindView(R.id.cb)
         CheckBox checkBox;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

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

    private String isShow;
    //改变显示删除的imageview，通过定义变量isShow去接收变量isManager
    public void changetShowCb(String isShow) {
        this.isShow = isShow;
        notifyDataSetChanged();
    }
}
