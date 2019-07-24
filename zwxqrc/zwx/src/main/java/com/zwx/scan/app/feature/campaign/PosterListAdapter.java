package com.zwx.scan.app.feature.campaign;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.PosterMaterial;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.widget.SmoothCheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/26
 * desc   :
 * version: 1.0
 **/
public class PosterListAdapter extends  RecyclerView.Adapter {

    private List<PosterMaterial> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private  int selectNum = 0;

    public PosterListAdapter(Context context, List<PosterMaterial> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (data.size() == 0) {
            return 0;
        } else {
            return data.size();
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (data.size() == 0) {
            return;
        }
        ChildViewHolder holder = (ChildViewHolder) viewHolder;
        PosterMaterial  posterMaterial= data.get(position);

//        holder.checkBox.setChecked(posterMaterial.isChecked());
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_image_loading);
        String ivbg = HttpUrls.BRAND_LOGO_ULR+posterMaterial.getBackground();
        Glide.with(context).load(ivbg).apply(requestOptions).into(holder.iv_head);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_poster_grid, null);
        return new ChildViewHolder(view);
    }
    private class ChildViewHolder extends RecyclerView.ViewHolder {
        //        public TextView tv_time;
        public ImageView checkBox;
        public ImageView iv_head;
        public ChildViewHolder(View view) {
            super(view);
            checkBox = (ImageView) view.findViewById(R.id.scb);
            iv_head = (ImageView) view.findViewById(R.id.iv_bg);
        }
    }
    

/*    private CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener;
    private Context context;
    public PosterListAdapter(Context context, List<PosterMaterial> dataList) {
        super(context, dataList, R.layout.item_poster_grid);

    }


    public PosterListAdapter(Context context, List<PosterMaterial> dataList, CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener) {
        super(context, dataList, R.layout.item_poster_grid);
        this.commonClickListener = commonClickListener;
        this.context = context;
    }
    @Override
    public void bindData(CommonRecyclerViewHolder holder, PosterMaterial data) {
        holder
                .setCommonClickListener(commonClickListener);


        ImageView ivBg = holder.getView(R.id.iv_bg);
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_banner_default)
                .error(R.drawable.ic_banner_default)
                .fallback(R.drawable.ic_banner_default);
        String ivbg = HttpUrls.BRAND_LOGO_ULR+data.getBackground();
        Glide.with(context).load(ivbg).apply(requestOptions).into(ivBg);



    }*/

}
