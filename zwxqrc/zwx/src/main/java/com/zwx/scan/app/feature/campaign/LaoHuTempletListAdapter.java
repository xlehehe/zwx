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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.MaterialGame;
import com.zwx.scan.app.data.bean.MemberConsumeBean;
import com.zwx.scan.app.data.bean.PosterMaterial;
import com.zwx.scan.app.data.http.HttpUrls;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/03/07
 * desc   :
 * version: 1.0
 **/
public class LaoHuTempletListAdapter extends BaseAdapter {


    private Context mContext;
    private List<MaterialGame> mDatas = new ArrayList();

    public LaoHuTempletListAdapter(Context context,List<MaterialGame> mDatas) {
        mContext = context;
        this.mDatas = mDatas;
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public MaterialGame getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_laohu_select_grid, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        if (mDatas.get(position).getChecked() != null &&  mDatas.get(position).getChecked()) {
            mViewHolder.mCbCheckbox.setBackgroundResource(R.drawable.ic_complete);

        } else {
            mViewHolder.mCbCheckbox.setBackgroundResource(R.drawable.ic_normal);

        }
        if("S".equals(LaoHuTempletActivity.compaignStatus)||"NEW".equals(LaoHuTempletActivity.compaignStatus)){

            mViewHolder.mCbCheckbox.setEnabled(true);
        }else {
            mViewHolder.mCbCheckbox.setEnabled(false);
        }
        String ivbg = HttpUrls.IMAGE_ULR+mDatas.get(position).getBackground();
    /*    Glide.with(mContext).load(ivbg).into(new SimpleTarget<Drawable>(100,220) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mViewHolder.iv_bg.setBackground(resource);    //设置背景
                }
            }
        });*/

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_image_loading);
        Glide.with(mContext).load(ivbg).apply(requestOptions).into(mViewHolder.iv_bg);
        String name = mDatas.get(position).getName();

        mViewHolder.tv_campaign_type_name.setText(name != null && !"".equals(name)?name:"");

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_bg)
        ImageView iv_bg;
        @BindView(R.id.scb)
        ImageView mCbCheckbox;

        @BindView(R.id.tv_campaign_type_name)
        TextView tv_campaign_type_name;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
