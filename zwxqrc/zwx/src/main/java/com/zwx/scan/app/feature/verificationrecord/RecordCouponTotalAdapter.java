package com.zwx.scan.app.feature.verificationrecord;

import android.content.Context;

import com.zwx.scan.app.R;
import com.zwx.scan.app.base.adapter.CommonRecycleAdapter;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.MemCoupon;

import java.math.BigDecimal;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/01
 * desc   :
 * version: 1.0
 **/
public class RecordCouponTotalAdapter extends CommonRecycleAdapter<MemCoupon> {

    private CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener;

    public RecordCouponTotalAdapter(Context context, List<MemCoupon> dataList) {
        super(context, dataList, R.layout.item_verification_record_list_content);
    }

    public RecordCouponTotalAdapter(Context context, List<MemCoupon> dataList, CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener) {
        super(context, dataList, R.layout.item_verification_record_list_content);
        this.commonClickListener = commonClickListener;
    }


    @Override
    public void bindData(CommonRecyclerViewHolder holder, MemCoupon data) {
        Integer countNum = data.getTotal();
        String total = "-";
        if(countNum != null && countNum.intValue()>0){
            total = String.valueOf(countNum);
        }
        holder.setText(R.id.tv_count, total)
                .setText(R.id.tv_coupon_name,data.getCouponName()!=null?data.getCouponName():"")
                .setCommonClickListener(commonClickListener);

    }
}
