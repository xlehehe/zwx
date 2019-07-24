package com.zwx.scan.app.feature.couponmanage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zwx.scan.app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/03/21
 * desc   :
 * version: 1.0
 **/
public class GiveCouponDayMonthAdapter extends BaseAdapter {

    private Context mContext;
    private List<DayDateBean> mDatas = new ArrayList();

    public GiveCouponDayMonthAdapter(Context context,List<DayDateBean> mDatas) {
        mContext = context;
        this.mDatas = mDatas;
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public DayDateBean getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final GiveCouponDayAdapter.ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_give_coupon_month_day_grid, parent, false);
            mViewHolder = new GiveCouponDayAdapter.ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (GiveCouponDayAdapter.ViewHolder) convertView.getTag();
        }

        if (mDatas.get(position).getChecked() != null &&  mDatas.get(position).getChecked()) {
            mViewHolder.tv_day_date.setBackgroundResource(R.drawable.ic_month_bg_selected);
            mViewHolder.tv_day_date.setTextColor(mContext.getResources().getColor(R.color.font_color_blue));
        } else {
            mViewHolder.tv_day_date.setBackgroundResource(R.drawable.bg_button_normal);
            mViewHolder.tv_day_date.setTextColor(mContext.getResources().getColor(R.color.color_gray_deep));
        }

        mViewHolder.tv_day_date.setText(mDatas.get(position).getValue());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_day_date)
        TextView tv_day_date;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
