package com.zwx.scan.app.feature.cateringinfomanage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Message;
import com.zwx.scan.app.data.bean.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/01/09
 * desc   :
 * version: 1.0
 **/
public class CateringinfoManageAdapter extends RecyclerView.Adapter {

    private List<Store> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public CateringinfoManageAdapter(Context context, List<Store> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if(data.size() == 0){
            return 0;
        }else {
            return data.size();
        }

    }




    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(data.size() == 0){
            return;
        }
        ChildViewHolder holder = (ChildViewHolder) viewHolder;


        Store store = data.get(position);

        String name = store.getManagerName();
        String phone = store.getManagerTel();
        String type = store.getType();
        String storeName = store.getName();
        holder.tvName.setText(name!=null?name:"");

        holder.tvPhone.setText(phone!=null?phone:"");

        if("1".equals(type)){
            holder.ivStatus.setBackgroundResource(R.drawable.ic_zhiying);
        }else if("2".equals(type)) {
            holder.ivStatus.setBackgroundResource(R.drawable.ic_jiameng);
        }
        holder.tvStoreName.setText(storeName!=null?storeName:"");



    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_cateringinfo_list, viewHolder,false);
        return new ChildViewHolder(view);
    }

}

class ChildViewHolder extends RecyclerView.ViewHolder {
    public TextView tvName;
    public ImageView ivStatus;
    public TextView tvStoreName;
    public TextView tvPhone;
    public ChildViewHolder(View view) {
        super(view);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        ivStatus = (ImageView) view.findViewById(R.id.iv_status);
        tvStoreName = (TextView) view.findViewById(R.id.tv_store_name);

        tvPhone = (TextView) view.findViewById(R.id.tv_phone);

    }

}
