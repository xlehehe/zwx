package com.zwx.scan.app.feature.campaign;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.MaterialGame;
import com.zwx.scan.app.data.bean.PosterMaterial;
import com.zwx.scan.app.data.http.HttpUrls;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/01/22
 * desc   :
 * version: 1.0
 **/
public class MaterialGameListAdapter extends BaseAdapter {


    private Context mContext;
    private List<PrizeDefaultBean> mDatas = new ArrayList();

    public MaterialGameListAdapter(Context context,List<PrizeDefaultBean> mDatas) {
        mContext = context;
        this.mDatas = mDatas;
    }

    /*public void setDatas(List datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }*/

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public PrizeDefaultBean getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_prize_material_grid, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        if (mDatas.get(position).getChecked()) {
            mViewHolder.mCbCheckbox.setBackgroundResource(R.drawable.ic_complete);

        } else {
            mViewHolder.mCbCheckbox.setBackgroundResource(R.drawable.ic_normal);

        }
        if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
            mViewHolder.mCbCheckbox.setEnabled(true);
        }else {
            mViewHolder.mCbCheckbox.setEnabled(false);
        }
        String ivbg = HttpUrls.IMAGE_ULR+mDatas.get(position).getId();
        Glide.with(mContext).load(ivbg).into(new SimpleTarget<Drawable>(111,111) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mViewHolder.iv_bg.setBackground(resource);    //设置背景
                }
            }
        });
        return convertView;
    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        Person person = (Person) buttonView.getTag();
//        person.setCheckStatus(isChecked);
//    }

    static class ViewHolder {
        @BindView(R.id.iv_bg)
        ImageView iv_bg;
        @BindView(R.id.scb)
        ImageView mCbCheckbox;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
