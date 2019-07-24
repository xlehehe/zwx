package com.zwx.scan.app.feature.member;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.MaterialCard;
import com.zwx.scan.app.data.bean.MaterialGame;
import com.zwx.scan.app.data.http.HttpUrls;

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
public class SelectFontColorAdapter extends BaseAdapter {

    private Context mContext;
    private List<FontBean> mDatas = new ArrayList();

    public SelectFontColorAdapter(Context context,List<FontBean> mDatas) {
        mContext = context;
        this.mDatas = mDatas;
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public FontBean getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_member_card_content_font_grid, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        if (mDatas.get(position).getChecked() != null &&  mDatas.get(position).getChecked()) {
            mViewHolder.mCbCheckbox.setBackgroundResource(R.drawable.ic_complete);
        } else {
            mViewHolder.mCbCheckbox.setBackground(null);

        }


        mViewHolder.iv_bg.setImageResource(mDatas.get(position).getFont());

      /*  String ivbg = HttpUrls.IMAGE_ULR+mDatas.get(position).getBackground();


        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_load_fail)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_load_fail);
        Glide.with(mContext).load(ivbg).apply(requestOptions).into(mViewHolder.iv_bg);*/
//        String name = mDatas.get(position).getName();


        return convertView;
    }

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
