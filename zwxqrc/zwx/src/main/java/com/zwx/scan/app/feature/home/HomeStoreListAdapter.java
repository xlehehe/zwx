package com.zwx.scan.app.feature.home;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.zwx.scan.app.R;
import com.zwx.scan.app.base.adapter.CommonRecycleAdapter;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.MemCoupon;
import com.zwx.scan.app.data.bean.Store;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/03
 * desc   :
 * version: 1.0
 **/
public class HomeStoreListAdapter extends CommonRecycleAdapter<Store> {

    private CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener;

    public HomeStoreListAdapter(Context context, List<Store> dataList) {
        super(context, dataList, R.layout.item_home_store_content);
    }

    public HomeStoreListAdapter(Context context, List<Store> dataList, CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener) {
        super(context, dataList, R.layout.item_home_store_content);
        this.commonClickListener = commonClickListener;
    }


    @Override
    public void bindData(CommonRecyclerViewHolder holder, Store data) {

        holder.setText(R.id.tv_brand_name, data.getBrandName())
                .setText(R.id.tv_store_name,data.getStoreName()!=null?data.getStoreName():"")
                .setCommonClickListener(commonClickListener);

       LinearLayout llStore = holder.getView(R.id.ll_store);
       llStore.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });

    }
}