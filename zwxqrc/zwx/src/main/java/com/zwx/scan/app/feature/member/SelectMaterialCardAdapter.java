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
import com.zwx.scan.app.data.bean.MaterialCard;
import com.zwx.scan.app.data.bean.TemplateChild;
import com.zwx.scan.app.data.http.HttpUrls;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/04/12
 * desc   :
 * version: 1.0
 **/
public class SelectMaterialCardAdapter extends BaseAdapter {

    private Context mContext;
    private List<MaterialCard> mDatas = new ArrayList();

    public SelectMaterialCardAdapter(Context context,List<MaterialCard> mDatas) {
        mContext = context;
        this.mDatas = mDatas;
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public MaterialCard getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final CardTempletListAdapter.ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_member_card_select_image_list, parent, false);
            mViewHolder = new CardTempletListAdapter.ViewHolder(convertView);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (CardTempletListAdapter.ViewHolder) convertView.getTag();
        }

        String name = mDatas.get(position).getName();
        mViewHolder.tv_title.setText(name != null && !"".equals(name)?name:"");

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

//        mViewHolder.iv_bg.setBackgroundResource(mDatas.get(position).getDrawableName());
        String bgPath = HttpUrls.IMAGE_ULR + mDatas.get(position).getBackground();
        Glide.with(mContext).load(bgPath).into(new SimpleTarget<Drawable>(100,220) {
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


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
