package com.zwx.scan.app.base.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * author : lizhilong
 * time   : 2018/09/12
 * desc   :
 * version: 1.0
 **/
//public class CommonRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{
public class CommonRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    // SparseArray 比 HashMap 更省内存，在某些条件下性能更好，只能存储 key 为 int 类型的数据，
    // 用来存放 View 以减少 findViewById 的次数
    private SparseArray<View> viewSparseArray;

    private OnItemCommonClickListener commonClickListener;
    public CommonRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);
//        itemView.setOnLongClickListener(this);
        viewSparseArray = new SparseArray<>();

    }

    /**
     * 根据 ID 来获取 View
     *
     * @param viewId viewID
     * @param <T>    泛型
     * @return 将结果强转为 View 或 View 的子类型
     */
    public <T extends View> T getView(int viewId) {
        // 先从缓存中找，找打的话则直接返回
        // 如果找不到则 findViewById ，再把结果存入缓存中
        View view = viewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewSparseArray.put(viewId, view);
        }
        return (T) view;
    }

    public CommonRecyclerViewHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public CommonRecyclerViewHolder setViewVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    public CommonRecyclerViewHolder setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }


    public CommonRecyclerViewHolder setCheckBox(int viewId, boolean selected) {
        Checkable checkable = getView(viewId);
        checkable.setChecked(selected);
        return this;
    }


    public interface OnItemCommonClickListener {

        void onItemClickListener(int position);

//        void onItemLongClickListener(int position);

    }

    public void setCommonClickListener(OnItemCommonClickListener commonClickListener) {
        this.commonClickListener = commonClickListener;
    }

    @Override
    public void onClick(View v) {
        if (commonClickListener != null) {
            commonClickListener.onItemClickListener(getAdapterPosition());
        }

    }

   /* @Override
    public boolean onLongClick(View v) {

        if (commonClickListener != null) {
            commonClickListener.onItemLongClickListener(getAdapterPosition());
        }
        return false;
    }*/
}
