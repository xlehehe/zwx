package com.zwx.scan.app.feature.financialaffairs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.TOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/05/31
 * desc   : 会员卡员工销售报表详情列表的详情
 * version: 1.0
 **/
public class MemCardEmploeeSaleReportListDetailDetailListAdapter extends BaseAdapter {

    private List<TOrder> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public MemCardEmploeeSaleReportListDetailDetailListAdapter(Context context, List<TOrder> data) {
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
        final MemCardEmploeeSaleReportListDetailDetailListAdapter.ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_member_card_employee_sale_report_list_detail_detail_list, parent, false);
            holder = new MemCardEmploeeSaleReportListDetailDetailListAdapter.ViewHolder(view);

            view.setTag(holder);

        } else {
            holder = (MemCardEmploeeSaleReportListDetailDetailListAdapter.ViewHolder) view.getTag();
        }

        TOrder order = data.get(position);



        BigDecimal amount = order.getAmount();
        String am = "—";
        if( amount != null && amount.doubleValue()>0){
            am = RegexUtils.getDoubleString(amount.doubleValue())+"元";
        }
        holder.tv_sale_amt.setText(am);

        Long count =  order.getCoun();
        String total = "—";
        if(count!= null ){
            total  = String.valueOf(count.intValue());


        }
        holder.tv_num.setText(total);

        String phone =order.getStaffTel();

        if(phone != null && !"".equals(phone)){
            holder.tv_phone.setText(phone);
        }else {
            holder.tv_phone.setText("—");
        }

        String time =order.getSalesTime();
        if(time != null && !"".equals(time)){
            holder.tv_time.setText(time);
        }else {
            holder.tv_time.setText("—");
        }


        MemCardEmploeeSaleReportDetailListActivity activity = (MemCardEmploeeSaleReportDetailListActivity)context;
        //订单类型 购卡M 拼团G  商城商品P
        if("M".equals(activity.orderType)){
            holder.ll_red_amt.setVisibility(View.GONE);
        }else if("G".equals(activity.orderType)){

            holder.ll_red_amt.setVisibility(View.GONE);
        }else if("P".equals(activity.orderType)){

            holder.ll_red_amt.setVisibility(View.VISIBLE);





            BigDecimal redAmt = order.getUseRedEnvelopeAmount();
            String reAm = "—";
            if( redAmt != null && redAmt.doubleValue()>0){
                reAm = RegexUtils.getDoubleString(redAmt.doubleValue())+"元";
            }
            holder.tv_red_amt.setText(reAm);

        }


        return view;
    }



    class ViewHolder {


        @BindView(R.id.ll_red_amt)
        public LinearLayout ll_red_amt;

        @BindView(R.id.tv_red_amt)
        public TextView tv_red_amt;

        @BindView(R.id.tv_sale_amt)
        public TextView tv_sale_amt;

        @BindView(R.id.tv_time)
        public TextView tv_time;
        @BindView(R.id.tv_phone)
        public TextView tv_phone;

        @BindView(R.id.tv_num)
        public TextView tv_num;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
