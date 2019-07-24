package com.zwx.scan.app.feature.countanalysis;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Store;

import java.util.HashMap;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/10
 * desc   :
 * version: 1.0
 **/
public class CheckListAdapter extends BaseAdapter {

    private Context context;
    private List<Store> beans;


    // 用来控制CheckBox的选中状况
    private static HashMap<Integer, Boolean> isSelected;


    class ViewHolder {
        TextView tvName;
        CheckBox cb;
        LinearLayout LL;
    }

    public CheckListAdapter(Context context, List<Store> beans,HashMap<Integer,Boolean> isSelected) {
        // TODO Auto-generated constructor stub
        this.beans = beans;
        this.context = context;
        this.isSelected = isSelected;
        // 初始化数据
        initDate();

    }

    // 初始化isSelected的数据
    private void initDate() {
        for (int i = 0; i < beans.size(); i++) {
            getIsSelected().put(i, false);
        }
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // 页面
        Log.v("MyListViewBase", "getView " + position + " " + convertView);
        ViewHolder holder = null;
        Store bean = beans.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.item_shop_cb, null);
            holder = new ViewHolder();
            holder.cb = (CheckBox) convertView.findViewById(R.id.cb_checkbox);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_store_name);
            holder.LL =  (LinearLayout) convertView.findViewById(R.id.RL);
            convertView.setTag(holder);
        } else {
            // 取出holder
            holder = (ViewHolder) convertView.getTag();
        }
        System.out.println(isSelected.toString());
        holder.tvName.setText(bean.getStoreName());
        // 监听checkBox并根据原来的状态来设置新的状态
        holder.LL.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                System.out.println("点击："+position);
                if (isSelected.get(position)) {
                    isSelected.put(position, false);
                    setIsSelected(isSelected);
                } else {
                    isSelected.put(position, true);
                    setIsSelected(isSelected);
                }
                notifyDataSetChanged();
            }
        });

        // 根据isSelected来设置checkbox的选中状况
        holder.cb.setChecked(getIsSelected().get(position));
        return convertView;
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        CheckListAdapter.isSelected = isSelected;
    }
}