package com.zwx.scan.app.feature.modulemore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.CommonImageBean;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/05/24
 * desc   : 功能模块列表
 * version: 1.0
 **/
public class ModuleGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater layinf;
    //GridView加载不同布局
    public List<CommonImageBean> list = null;

    public ModuleGridViewAdapter(Context context, List<CommonImageBean> lists){
        this.mContext = context;
        this.list = lists;
        layinf = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

 /*   @Override
    public int getItemViewType(int position) {
        //根据position返回指定的布局类型，比如0、1，根据这个返回值加载不同布局
        return lists.get(position).getPropertyType();
    }

    @Override
    public int getViewTypeCount() {
        //这里是adapter里有几种布局
        return 2;
    }*/

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if(convertView == null){
            convertView = layinf.inflate(R.layout.item_module_more_grid, parent, false);

            //使用减少findView的次数
            holder = new ViewHolder();

            holder.iv_module = (ImageView) convertView.findViewById(R.id.iv_module);
            holder.tv_module_name = (TextView) convertView.findViewById(R.id.tv_module_name);

            //设置标记
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        CommonImageBean imageBean = list.get(position);

        holder.tv_module_name.setText(imageBean.getName());

        holder.iv_module.setImageResource(imageBean.getDrawable());



        return convertView;
    }


    static class ViewHolder {
        ImageView iv_module;
        TextView tv_module_name;
    }
}
