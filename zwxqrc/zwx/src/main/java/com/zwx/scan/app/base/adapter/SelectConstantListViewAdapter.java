package com.zwx.scan.app.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.feature.member.CommonConstantBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/04/02
 * desc   :
 * version: 1.0
 **/
public class SelectConstantListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<CommonConstantBean> mDatas = new ArrayList();

    public SelectConstantListViewAdapter(Context context) {
        mContext = context;
    }

    public void setDatas(List datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public CommonConstantBean getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_select_common_constant, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        if (mDatas.get(position).getChecked()) {
            mViewHolder.tv_constant.setTextColor(mContext.getResources().getColor(R.color.font_color_blue));
        } else {
            mViewHolder.tv_constant.setTextColor(mContext.getResources().getColor(R.color.shop_font_color_gray));
        }

        mViewHolder.tv_constant.setText(mDatas.get(position).getKey());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_constant)
        TextView tv_constant;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
