package com.zwx.scan.app.feature.financialaffairs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.CollectionFlowResultBean;
import com.zwx.scan.app.data.bean.CompWxpayTransfer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/05/23
 * desc   : //转账记录适配器
 * version: 1.0
 **/
public class CollectionTransferRecordAdapter extends BaseAdapter {

    private List<CompWxpayTransfer> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public CollectionTransferRecordAdapter(Context context, List<CompWxpayTransfer> data) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_collection_transfer_record_list, parent, false);
            holder = new ViewHolder(view);
         /*   holder.tv_order_code = (TextView) view.findViewById(R.id.tv_order_code);
            holder.tv_pay_way = (TextView) view.findViewById(R.id.tv_pay_way);
            holder.tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
            holder.tv_buy_phone = (TextView) view.findViewById(R.id.tv_buy_phone);
            holder.tv_sale_time = (TextView) view.findViewById(R.id.tv_sale_time);
            holder.tv_pay_amt = (TextView) view.findViewById(R.id.tv_pay_amt);
            holder.tv_red_packget_amt = (TextView) view.findViewById(R.id.tv_red_packget_amt);
            holder.tv_sale_emploee = (TextView) view.findViewById(R.id.tv_sale_emploee);
            holder.tv_buy_num = (TextView) view.findViewById(R.id.tv_buy_num);*/
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        CompWxpayTransfer transfer = data.get(position);


        String fee = "—";
        BigDecimal fe =  transfer.getFee();
        if(fe != null && fe.doubleValue()>0){
            fee = RegexUtils.getDoubleString(fe.doubleValue());
        }else {
            fee = "—";
        }
        holder.tv_fee.setText(fee);

        String money = "—";
        BigDecimal mon =  transfer.getAmount();
        if(mon != null && mon.doubleValue()>0){
            money = RegexUtils.getDoubleString(mon.doubleValue());

        }else {
            money = "—";
        }
        //商品总额
        holder.tv_daozhang_total_amt.setText(money);

        double sjAmt = mon.doubleValue() - fe.doubleValue();
        String shijiAmt = "—";
        if(sjAmt>0){
            shijiAmt = RegexUtils.getDoubleString(mon.doubleValue() - fe.doubleValue());
            holder.tv_shiji_to_amt.setText(shijiAmt+"元");
        }else{
            holder.tv_shiji_to_amt.setText(shijiAmt);
        }

         //总金额减去手续费

        //商品名称
        String bankName = transfer.getBankName();
        holder.tv_bank_name.setText(bankName != null && !"".equals(bankName)?bankName:"—");


        String bankNo = transfer.getBankCardNo();
        holder.tv_bank_no.setText(bankNo != null && !"".equals(bankNo)?bankNo:"—");


        String time = transfer.getTime();

        if(time != null && !"".equals(time)){
            time = time.replace("-",".");
        }else {
            time = "—";
        }
        holder.tv_draing_time.setText(time);

        String status = transfer.getState();

        if("1".equals(status)){
            holder.iv_drawing_status.setImageResource(R.drawable.ic_daozhagn_success);
        }else if("0".equals(status)){
            holder.iv_drawing_status.setImageResource(R.drawable.ic_daozhagn_fail);
        }


        String type = transfer.getType();
        if("1".equals(type)){
            holder.tv_to_amt_type.setText("银行卡");
            holder.iv_to_amt_type.setImageResource(R.drawable.ic_collecmoney_bank1);
            holder.tv_daozhang_total_amt_no_han.setVisibility(View.VISIBLE);
            holder.ll_daozhang_amt.setVisibility(View.VISIBLE);
            holder.ll_fee.setVisibility(View.VISIBLE);

        }else if("0".equals(type)){
            holder.tv_to_amt_type.setText("钱包");
            holder.iv_to_amt_type.setImageResource(R.drawable.ic_collecmoney_wx1);
            holder.tv_daozhang_total_amt_no_han.setVisibility(View.GONE);

            holder.tv_shiji_to_amt_label.setText("到账金额：");
            holder.tv_label1.setText("到账人会员名：");
            holder.tv_label2.setText("到账人手机号：");
            String memberName = transfer.getMemberName();
            holder.tv_bank_name.setText(memberName != null && !"".equals(memberName)?memberName:"—");

            String phone = transfer.getPhone();
            holder.tv_bank_no.setText(phone != null && !"".equals(phone)?phone:"—");
            holder.ll_daozhang_amt.setVisibility(View.GONE);
            holder.ll_fee.setVisibility(View.GONE);
        }

      /*  holder.tv_sale_time.setText(time);




        //商品编码
        holder.tv_order_code.setText(flowBean.getOrderCode() != null ?String.valueOf(flowBean.getOrderCode()):"—");



        holder.tv_buy_phone.setText(flowBean.getBuyCount()!= null && flowBean.getBuyCount().intValue()>0?"x"+flowBean.getBuyCount().intValue():"—");


        holder.tv_buy_num.setText(flowBean.getBuyCount()!= null && flowBean.getBuyCount().intValue()>0?flowBean.getBuyCount().intValue()+"":"—");

        holder.tv_sale_emploee.setText(flowBean.getStaffName()!= null && !"".equals(flowBean.getStaffName())?flowBean.getStaffName():"—");*/
        return view;
    }



    class ViewHolder {
        @BindView(R.id.tv_draing_time)
        public TextView tv_draing_time;
        @BindView(R.id.iv_to_amt_type)
        public ImageView iv_to_amt_type;
        @BindView(R.id.tv_to_amt_type)
        public TextView tv_to_amt_type;
        @BindView(R.id.tv_daozhang_total_amt)
        public TextView tv_daozhang_total_amt;
        @BindView(R.id.tv_daozhang_total_amt_no_han)
        public TextView tv_daozhang_total_amt_no_han;
        @BindView(R.id.tv_fee)
        public TextView tv_fee;
        @BindView(R.id.tv_shiji_to_amt)
        public TextView tv_shiji_to_amt;
        @BindView(R.id.iv_drawing_status)
        public ImageView iv_drawing_status;
        @BindView(R.id.tv_label1)
        public TextView tv_label1;
        @BindView(R.id.tv_bank_name)
        public TextView tv_bank_name;

        @BindView(R.id.ll_label2)
        public LinearLayout ll_label2;

        @BindView(R.id.tv_label2)
        public TextView tv_label2;
        @BindView(R.id.tv_bank_no)
        public TextView tv_bank_no;

        @BindView(R.id.tv_shiji_to_amt_label)
        public TextView tv_shiji_to_amt_label;

        @BindView(R.id.ll_fee)
        public LinearLayout ll_fee;
        @BindView(R.id.ll_daozhang_amt)
        public LinearLayout ll_daozhang_amt;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
