package com.zwx.scan.app.feature.verificationrecord;

import android.content.Context;

import com.zwx.scan.app.R;
import com.zwx.scan.app.base.adapter.CommonRecycleAdapter;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.MemCoupon;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/11/23
 * desc   : 优惠券核销详情记录
 * version: 1.0
 **/
public class VerificationRecordDetailAdapter extends CommonRecycleAdapter<MemCoupon> {

    private CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener;

    public VerificationRecordDetailAdapter(Context context, List<MemCoupon> dataList) {
        super(context, dataList, R.layout.item_verification_record_detail_list);
    }

    public VerificationRecordDetailAdapter(Context context, List<MemCoupon> dataList, CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener) {
        super(context, dataList, R.layout.item_verification_record_detail_list);
        this.commonClickListener = commonClickListener;
    }


    @Override
    public void bindData(CommonRecyclerViewHolder holder, MemCoupon data) {
        BigDecimal amt = data.getConsumeAmt();
        String consume = "0.00";
        if(amt == null &&amt.doubleValue() ==0.0){

        }else {
            ;
            consume = new DecimalFormat("##.##").format(amt.setScale(2,BigDecimal.ROUND_DOWN).doubleValue()).toString();
        }



        holder.setText(R.id.tv_member_tel, data.getMemberTel()!=null?data.getMemberTel():"")
                .setText(R.id.tv_use_count, data.getTotal() != 0 ? data.getTotal()+"":"-")
                .setText(R.id.tv_coupon_name,data.getCouponName())
                .setText(R.id.tv_consume_time,data.getConsumeTime())
                .setText(R.id.tv_money,"￥"+data.getConsumeAmt()!=null?data.getConsumeAmt().setScale(2, BigDecimal.ROUND_HALF_UP).toString()+"":"0.00")
                .setCommonClickListener(commonClickListener);

    }



}
