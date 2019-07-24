package com.zwx.scan.app.feature.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Store;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/03
 * desc   :
 * version: 1.0
 **/
public class StoreList2Adapter extends BaseAdapter {

    private Context context;
    private List<Store> datas;
    private int layoutId;

    public StoreList2Adapter(Context context, List<Store> datas) {
        this.context = context;
        this.datas = datas;
    }


    @Override
    public int getCount() {
        return  datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_store_content, parent, false); //加载布局
            holder = new ViewHolder();

            holder.tvStoreName = (TextView) convertView.findViewById(R.id.tv_store_name);
            holder.tvBrandName = (TextView) convertView.findViewById(R.id.tv_brand_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(datas != null && datas.size()>0){
            Store store = datas.get(position);

            holder.tvBrandName.setText(store.getBrandName());
            holder.tvStoreName.setText(store.getStoreName());
        }

        return convertView;
    }



    private class ViewHolder{

        TextView tvBrandName;
        TextView tvStoreName;
    }
}
