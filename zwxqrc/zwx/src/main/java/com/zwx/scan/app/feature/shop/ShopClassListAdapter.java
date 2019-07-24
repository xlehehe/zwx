package com.zwx.scan.app.feature.shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.ProductCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/04/18
 * desc   :
 * version: 1.0
 **/
public class ShopClassListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<ProductCategory> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public ShopClassListAdapter(Context context, List<ProductCategory> data) {
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
    public int getItemViewType(int position) {

        ProductCategory category = data.get(position);
        String num = category.getNum();

        if(num == null || "".equals(num)||"0".equals(num)){
            return   0 ;
        }

        return position;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(data.size() == 0){
            return;
        }
       ChildViewHolder holder = (ChildViewHolder) viewHolder;

        int viewType =getItemViewType(position);

        if(viewType == 0){
            //未发布
        }else if(viewType == 1){
            //未开始
        }else if(viewType == 2){

        }else if(viewType == 3){

        }
        ProductCategory category = data.get(position);

        String name = category.getCatName();
        String num = category.getNum();

        holder.tvName.setText(name!=null?name:"");

        if(num != null && !"".equals(num) && !"0".equals(num)){
            holder.tvNum.setText(num);
        }else {
            holder.tvNum.setText("0");
        }
       /* if("zf".equals(type)){
            campaignTypeName = "转发活动";
        }*/
//        holder.tvType.setText(campaignTypeName);




      /*  String logo = campaign.getWechatIcon();
        if(poster != null){
            brandLogo = HttpUrls.IMAGE_ULR + poster.getWechatIcon();

        }else {
            brandLogo = HttpUrls.IMAGE_ULR ;
        }
        Glide.with(context).load(brandLogo).apply(requestOptions).into(holder.ivHead);

*/


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_shop_class_list, viewHolder,false);
        return new ChildViewHolder(view);
    }
    private class ChildViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvNum;

        public ChildViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvNum = (TextView) view.findViewById(R.id.tv_num);


        }

    }


}
