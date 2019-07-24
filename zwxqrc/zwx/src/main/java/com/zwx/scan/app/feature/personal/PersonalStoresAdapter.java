package com.zwx.scan.app.feature.personal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.feature.campaign.CampaignSelectStoreAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/01/07
 * desc   :
 * version: 1.0
 **/
public class PersonalStoresAdapter extends BaseAdapter {

    private Context mContext;
    private List<Store> mDatas = new ArrayList();

    public PersonalStoresAdapter(Context context,List<Store> stores) {
        mContext = context;
        mDatas = stores;
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Store getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_my_store_list, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.tvBrand.setText(mDatas.get(position).getBrandName());
        mViewHolder.tvStore.setText(mDatas.get(position).getStoreName());
        return convertView;
    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        Person person = (Person) buttonView.getTag();
//        person.setCheckStatus(isChecked);
//    }

    static class ViewHolder {
        @BindView(R.id.tv_brand_name)
        TextView tvBrand;
        @BindView(R.id.tv_store_name)
        TextView tvStore;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
