package com.zwx.scan.app.feature.cateringinfomanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.CateBean;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.feature.campaign.CampaignSelectStoreAdapter;
import com.zwx.scan.app.feature.cateringinfomanage.TypeBean;
import com.zwx.scan.app.feature.member.MemberCardNewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/01/09
 * desc   :
 * version: 1.0
 **/
public class SelectTypeAdapter extends BaseAdapter {


    private Context mContext;
    private ArrayList<CateBean> mDatas = new ArrayList();

    public SelectTypeAdapter(Context context,ArrayList<CateBean> datas) {
        this.mContext = context;
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
    public CateBean getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shop_cb, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }



        CateBean cateBean = mDatas.get(position);

        if(cateBean != null){
            if (cateBean.isChecked()) {
                mViewHolder.mCbCheckbox.setChecked(true);
                mViewHolder.mCbCheckbox.setButtonDrawable(R.drawable.ic_correct);
                mViewHolder.mTvTitle.setTextColor(mContext.getResources().getColor(R.color.font_color_blue));
            } else {
                mViewHolder.mCbCheckbox.setChecked(false);
                mViewHolder.mCbCheckbox.setButtonDrawable(R.color.white);
                mViewHolder.mTvTitle.setTextColor(mContext.getResources().getColor(R.color.shop_font_color_gray));
            }
            String name = cateBean.getKey();
            mViewHolder.mTvTitle.setText(name);
        }


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
