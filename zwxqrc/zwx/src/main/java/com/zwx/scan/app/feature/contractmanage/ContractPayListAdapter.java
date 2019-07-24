package com.zwx.scan.app.feature.contractmanage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Contract;
import com.zwx.scan.app.data.bean.ReceiveFund;
import com.zwx.scan.app.data.bean.Role;
import com.zwx.scan.app.feature.accountmanage.AccountNewActivity;
import com.zwx.scan.app.feature.accountmanage.AccountRoleAuthActivity;
import com.zwx.scan.app.feature.accountmanage.AccountRoleListAdapter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/01/19
 * desc   :
 * version: 1.0
 **/
public class ContractPayListAdapter extends BaseAdapter {

    private Context mContext;
    private List<ReceiveFund> mDatas = new ArrayList();

    public ContractPayListAdapter(Context context,List<ReceiveFund> datas) {
        mContext = context;
        this.mDatas = datas;
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public ReceiveFund getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //int index = position;
        final ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_contract_pay_list, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        ReceiveFund receiveFund = mDatas.get(position);

        if(position<=6 ){
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.contract_pay_list_color_blue));
        }else {
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.contract_pay_list_color_gray));
        }

        String date = receiveFund.getPlanReceiveDate();

        if(date != null && date.length()>0){
            String time = TimeUtils.date2String(TimeUtils.string2Date(date));
            mViewHolder.tv_pay_date.setText(time.replace("-","."));
        }else {
            mViewHolder.tv_pay_date.setText("");
        }

        BigDecimal fee = receiveFund.getMoney();
        if(fee != null && fee.doubleValue()>0){
            String payFee = new DecimalFormat("0.00").format(fee.setScale(2,BigDecimal.ROUND_DOWN).doubleValue()).toString();
            mViewHolder.tv_pay_money.setText(payFee);
        }else {
            mViewHolder.tv_pay_money.setText("");
        }

        String status = receiveFund.getStatus();

        if("R".equals(status)){
            mViewHolder.tv_is_pay.setText("是");
        }else if("U".equals(status)){
            mViewHolder.tv_is_pay.setText("否");
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_pay_date)
        TextView tv_pay_date;
        @BindView(R.id.tv_pay_money)
        TextView tv_pay_money;
        @BindView(R.id.tv_is_pay)
        TextView tv_is_pay;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
