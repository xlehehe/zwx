package com.zwx.scan.app.feature.contractmanage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.utils.TimeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Contract;
import com.zwx.scan.app.data.bean.ReceiveFund;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/01/19
 * desc   :
 * version: 1.0
 **/
public class ContractListAdapter  extends BaseAdapter{


    private Context mContext;
    private List<Contract> mDatas = new ArrayList();

    public ContractListAdapter(Context context,List<Contract> datas) {
        mContext = context;
        this.mDatas = datas;
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Contract getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_contract_list, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        Contract contract = mDatas.get(position);

       /* String createTime = contract.getCreateTime();
        String time = "";
        if(createTime != null && createTime.length()>0){
            time = TimeUtils.date2String(TimeUtils.string2Date(createTime));
        }else {
        }*/
       String contractNo =contract.getContractNo();
        String startDate = contract.getStartDate();
        String endDate = contract.getEndDate();
        
        String startTime = "";
        String endTime = "";
        if(startDate != null && startDate.length()>0){
             startTime = TimeUtils.date2String(TimeUtils.string2Date(startDate));
        }else {
        }

        if(endDate != null && endDate.length()>0){
            endTime = TimeUtils.date2String(TimeUtils.string2Date(endDate));
        }
        
        mViewHolder.tv_time.setText(startTime +" - "+ endTime);
        mViewHolder.tv_contract_no.setText(contractNo != null?contractNo:"");
        String status = contract.getStatus();

        /**
         * WHEN 'A' THEN '审核中'
         WHEN 'P' THEN '生效中'
         WHEN 'N' THEN '审核不通过'
         WHEN 'R' THEN '续签中'
         WHEN 'O' THEN '已过期'
         * */
        if("A".equals(status)){

        }else if("P".equals(status)){
            mViewHolder.iv_status.setBackgroundResource(R.drawable.ic_contract_p);
        }else if("N".equals(status)){

        }else if("R".equals(status)){

        }else if("O".equals(status)){
            mViewHolder.iv_status.setBackgroundResource(R.drawable.ic_contract_o);
        }
     

        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.tv_contract_no)
        TextView tv_contract_no;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.iv_status)
        ImageView iv_status;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
