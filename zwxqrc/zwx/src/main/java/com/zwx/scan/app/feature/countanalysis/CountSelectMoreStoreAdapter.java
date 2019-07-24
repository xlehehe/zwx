package com.zwx.scan.app.feature.countanalysis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.feature.home.SelectStoreAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2018/12/10
 * desc   :
 * version: 1.0
 **/
public class CountSelectMoreStoreAdapter extends BaseAdapter {

    private Context mContext;
    private List<Store> datas = new ArrayList();
    public static HashMap<Integer, Boolean> isSelected;
    public CountSelectMoreStoreAdapter(Context context,List<Store> datas) {
        mContext = context;
        this.datas = datas;
        init();
    }

    // 初始化 设置所有item都为未选择
    public void init() {
        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < datas.size(); i++) {
            Store store = datas.get(i);
            isSelected.put(i, store.isChecked());
        }
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Store getItem(int position) {
        return datas.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shop_cb, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
//        mViewHolder.mCbCheckbox.setTag(mDatas.get(position));
//        mViewHolder.mCbCheckbox.setOnCheckedChangeListener(this);
//        mViewHolder.mCbCheckbox.setChecked(mDatas.get(position).getCheckStatus());
//        mDatas.get(position).setCheckStatus(mDatas.get(position).getCheckStatus()?true:false);


       /* if(position ==0){
            mViewHolder.mTvTitle.setText("全部店铺");
            mViewHolder.mCbCheckbox.setVisibility(View.GONE);
        }else {
            mViewHolder.mCbCheckbox.setVisibility(View.VISIBLE);

        }*/
       boolean seke = isSelected.get(position);
        mViewHolder.mCbCheckbox.setChecked(seke);
        if (seke) {
            datas.get(position).setChecked(true);
            mViewHolder.mCbCheckbox.setChecked(true);
            mViewHolder.mCbCheckbox.setButtonDrawable(R.drawable.ic_correct);
            mViewHolder.mTvTitle.setTextColor(mContext.getResources().getColor(R.color.font_color_blue));
        } else {
            mViewHolder.mCbCheckbox.setChecked(false);
            mViewHolder.mCbCheckbox.setButtonDrawable(R.color.white);
            mViewHolder.mTvTitle.setTextColor(mContext.getResources().getColor(R.color.shop_font_color_gray));
        }

        mViewHolder.mTvTitle.setText(datas.get(position).getStoreName());

        return convertView;
    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        Person person = (Person) buttonView.getTag();
//        person.setCheckStatus(isChecked);
//    }

    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.cb_checkbox)
        CheckBox mCbCheckbox;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
