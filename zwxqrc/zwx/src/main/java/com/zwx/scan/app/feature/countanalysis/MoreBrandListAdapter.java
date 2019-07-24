package com.zwx.scan.app.feature.countanalysis;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.MoreStoreBean;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/10
 * desc   :
 * version: 1.0
 **/
public class MoreBrandListAdapter extends BaseAdapter {

    private Context context;
    private int selectItem = 0;
    private List<MoreStoreBean.BrandStoreBean> list;

    public MoreBrandListAdapter(Context context, List<MoreStoreBean.BrandStoreBean> list) {
        this.list = list;
        this.context = context;
    }

    public int getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View arg1, ViewGroup arg2) {
        ViewHolder holder = null;
        if (arg1 == null) {
            holder = new ViewHolder();
            arg1 = View.inflate(context, R.layout.item_more_store_brand_list, null);
            holder.tv_name = (TextView) arg1.findViewById(R.id.tv_brand_name);
            arg1.setTag(holder);
        } else {
            holder = (ViewHolder) arg1.getTag();
        }
        if (position == selectItem) {
            holder.tv_name.setBackgroundColor(Color.WHITE);
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.font_color_blue));
        } else {
//            holder.tv_name.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.black));
        }


        if(list!=null&&list.size()>0){
            MoreStoreBean.BrandStoreBean brandStoreBean = list.get(position);
            if (brandStoreBean!=null){
                holder.tv_name.setText(brandStoreBean.getBrandName());
            }else {
                holder.tv_name.setText("");
            }

        }

        return arg1;
    }

    static class ViewHolder {
        private TextView tv_name;
    }
}