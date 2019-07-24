package com.zwx.scan.app.feature.verificationrecord;

import android.content.Context;

import com.zwx.scan.app.R;
import com.zwx.scan.app.base.adapter.CommonRecycleAdapter;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.MemCoupon;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/11/23
 * desc   :
 * version: 1.0
 **/
public class VerificationRecordAdapter extends CommonRecycleAdapter<MemCoupon> {

    private CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener;

    public VerificationRecordAdapter(Context context, List<MemCoupon> dataList) {
        super(context, dataList, R.layout.item_verification_record_list_content);
    }

    public VerificationRecordAdapter(Context context, List<MemCoupon> dataList, CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener) {
        super(context, dataList, R.layout.item_verification_record_list_content);
        this.commonClickListener = commonClickListener;
    }


    @Override
    public void bindData(CommonRecyclerViewHolder holder, MemCoupon data) {
        holder.setText(R.id.tv_coupon_name, data.getCouponName())
                .setText(R.id.tv_count, data.getTotal()+"")
                .setCommonClickListener(commonClickListener);

    }
}
