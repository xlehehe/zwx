package com.zwx.instalment.app.feature.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.instalment.app.R;
import com.zwx.instalment.app.data.bean.ClassBean;

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
public class HomeMenuListAdapter extends BaseAdapter {


    private Context mContext;
    private List<ClassBean> mDatas = new ArrayList();

    public HomeMenuListAdapter(Context context, List<ClassBean> mDatas) {
        mContext = context;
        this.mDatas = mDatas;
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
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



       /* RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_image_loading);
        Glide.with(mContext).load(ivbg).apply(requestOptions).into(mViewHolder.iv_bg);*/
        String name = mDatas.get(position).getValue();
        int drawable = mDatas.get(position).getDrawable();
        mViewHolder.tv_name.setText(name);

        mViewHolder.iv_bg.setImageResource(drawable);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_bg)
        ImageView iv_bg;

        @BindView(R.id.tv_name)
        TextView tv_name;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
