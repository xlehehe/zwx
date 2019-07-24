package com.zwx.scan.app.feature.accountmanage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.feature.cateringinfomanage.TypeBean;
import com.zwx.scan.app.feature.cateringinfomanage.adapter.SelectTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/01/10
 * desc   :
 * version: 1.0
 **/
public class AccountStoreListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Store> mDatas = new ArrayList();

    public AccountStoreListAdapter(Context context,List<Store> datas) {
        mContext = context;
        this.mDatas = datas;
    }

   /* public void setDatas(List datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }*/

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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_account_new_store_list, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        Store store = mDatas.get(position);
        if (mDatas.get(position).isChecked()) {
            mViewHolder.mCbCheckbox.setChecked(true);
            mViewHolder.mCbCheckbox.setButtonDrawable(R.drawable.ic_blue_selected);
        } else {
            mViewHolder.mCbCheckbox.setChecked(false);
            mViewHolder.mCbCheckbox.setButtonDrawable(R.drawable.ic_gray_unselect);
        }

//        AccountNewActivity accountNewActivity = (AccountNewActivity) mContext;
      /*  if("0".equals(accountNewActivity.authFlag)){
            mViewHolder.mCbCheckbox.setEnabled(true);
        }else if("2".equals(accountNewActivity.isAllStore)){
            mViewHolder.mCbCheckbox.setEnabled(false);
        }*/

        String name =  mDatas.get(position).getName();
        mViewHolder.mCbCheckbox.setText(name!=null ? name :"");

        if("1".equals(store.getType())){
            mViewHolder.ivStatus.setBackgroundResource(R.drawable.ic_zhiying);
        }else if("2".equals(store.getType())){
            mViewHolder.ivStatus.setBackgroundResource(R.drawable.ic_jiameng);
        }


        return convertView;
    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        Person person = (Person) buttonView.getTag();
//        person.setCheckStatus(isChecked);
//    }

    static class ViewHolder {
        @BindView(R.id.iv_status)
        ImageView ivStatus;
        @BindView(R.id.cb_checkbox)
        CheckBox mCbCheckbox;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
