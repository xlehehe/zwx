package com.zwx.scan.app.feature.member;

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
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.TemplateChild;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.countanalysis.CountAnalysis;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/04/03
 * desc   :
 * version: 1.0
 **/
public class CardTempletListAdapter extends BaseAdapter {

    private Context mContext;
    private List<TemplateChild> mDatas = new ArrayList();

    public CardTempletListAdapter(Context context,List<TemplateChild> mDatas) {
        mContext = context;
        this.mDatas = mDatas;
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public TemplateChild getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_member_card_select_image_list, parent, false);
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
        if("NEW".equals(MemberCardNewActivity.memberCardStatusNew)){
            mViewHolder.mCbCheckbox.setEnabled(true);
        }else {
            mViewHolder.mCbCheckbox.setEnabled(false);
        }
//        String ivbg = HttpUrls.IMAGE_ULR+mDatas.get(position).getBackground();
    /*    Glide.with(mContext).load(ivbg).into(new SimpleTarget<Drawable>(100,220) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mViewHolder.iv_bg.setBackground(resource);    //设置背景
                }
            }
        });*/

//        RequestOptions requestOptions = new RequestOptions()
//                .placeholder(R.drawable.ic_load_fail)
//                .error(R.drawable.ic_load_fail)
//                .fallback(R.drawable.ic_load_fail);
//        Glide.with(mContext).load(ivbg).apply(requestOptions).into(mViewHolder.iv_bg);
//        String name = mDatas.get(position).getName();
        String name = mDatas.get(position).getName();
        mViewHolder.tv_title.setText(name != null && !"".equals(name)?name:"");

//        mViewHolder.iv_bg.setBackgroundResource(mDatas.get(position).getDrawableName());
        String bgPath = HttpUrls.IMAGE_ULR + mDatas.get(position).getImgUrl();
        Glide.with(mContext).load(bgPath).into(new SimpleTarget<Drawable>(220,140) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mViewHolder.iv_bg.setBackground(resource);    //设置背景
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_bg)
        ImageView iv_bg;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.scb)
        ImageView mCbCheckbox;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
