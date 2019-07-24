package com.zwx.scan.app.feature.campaign;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.zwx.library.expandablelayout.ExpandableLayoutListenerAdapter;
import com.zwx.scan.app.R;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/09/12
 * desc   :
 * version: 1.0
 **/
public class HomeCouponAdapter extends CouponRecyclerViewAdapter<ItemModel> {
    private ViewHolder.OnItemCommonClickListener commonClickListener;

    /*public HomeCouponAdapter(Context context, List<ItemModel> data, int layoutId) {
        super(context, data, R.layout.item_expre_coupon_list);
    }*/

    public HomeCouponAdapter(Context context, List<ItemModel> dataList, ViewHolder.OnItemCommonClickListener commonClickListener) {
        super(context, dataList, R.layout.item_expre_coupon_list);
        this.commonClickListener = commonClickListener;
    }

    @Override
    public void bindData(CouponRecyclerViewAdapter.ViewHolder holder, ItemModel item, int position) {


        holder.setIsRecyclable(false);
//        holder.textView.setText(item.rule);
        holder.tvCouponName.setText(item.getName());
        holder.tvCouponMoney.setText(item.getMoney());
        holder.tvExpreCouponName.setText(item.getCouponName());
//        holder.ivCoupon.setBackground(ContextCompat.getDrawable(context,item.getDrawableId()));
        holder.ivCoupon.setBackgroundResource(item.getDrawableId());
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, item.getColorId1()));
        if("yes".equals(item.getIsNew())){
            holder.btnUse.setBackgroundResource(R.drawable.style_home_btn_oranage_bg);
        }else {
//            holder.btnUse.setBackground(ContextCompat.getDrawable(context,R.drawable.style_home_btn_red_bg));
            holder.btnUse.setBackgroundResource(R.drawable.style_home_btn_red_bg);
        }
        holder.expandableLayout.setInRecyclerView(true);
        holder.expandableLayout.setBackgroundColor(ContextCompat.getColor(context, item.getColorId1()));
        holder.expandableLayout.setInterpolator(item.interpolator);
        holder.expandableLayout.setExpanded(expandState.get(position));
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(holder.buttonLayout, 0f, 180f).start();
                expandState.put(position, true);
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(holder.buttonLayout, 180f, 0f).start();
                expandState.put(position, false);
            }
        });

        holder.buttonLayout.setRotation(expandState.get(position) ? 180f : 0f);
        holder.buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(holder.expandableLayout);
            }
        });

        holder.setCommonClickListener(commonClickListener);


    }

    @Override
    public int getLayoutId() {
        return R.layout.item_expre_coupon_list;
    }
}
