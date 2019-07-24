package com.zwx.scan.app.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/09/12
 * desc   :
 * version: 1.0
 **/
public abstract  class CommonRecycleAdapter<T> extends RecyclerView.Adapter<CommonRecyclerViewHolder> {


    protected LayoutInflater layoutInflater;

    protected List<T> dataList;

    protected int layoutId;

    public CommonRecycleAdapter(Context context, List<T> dataList, int layoutId) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.layoutId = layoutId;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(layoutId, parent, false);
        return new CommonRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommonRecyclerViewHolder holder, int position) {
        bindData(holder, dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public abstract void bindData(CommonRecyclerViewHolder holder, T data);
}
