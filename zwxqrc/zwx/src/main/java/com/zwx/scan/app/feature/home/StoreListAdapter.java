package com.zwx.scan.app.feature.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Store;

import java.util.List;

public class StoreListAdapter extends RecyclerView.Adapter {

    private List<Store> list;
    private Context context;
    protected LayoutInflater layoutInflater;
    private OnItemCommonClickListener commonClickListener;
    public StoreListAdapter(Context context, List<Store> list,OnItemCommonClickListener commonClickListener) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        this.commonClickListener =commonClickListener;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new OneViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_store_title, parent,false));
        } else {
            return new TwoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_store_content, parent,false));
        }
//       View  view = layoutInflater.inflate(R.layout.item_verification_record_list_content, parent,false);
//        return new TwoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      /*  TwoViewHolder twoViewHolder = (TwoViewHolder) holder;
//        twoViewHolder.setData(position);
        MemCoupon memCoupon = list.get(position);
        twoViewHolder.getTvName().setText(memCoupon.getCouponName());
        twoViewHolder.getTvCount().setText(memCoupon.getTotal()+"");*/

        if (getItemViewType(position) == 1) {
            OneViewHolder oneViewHolder = (OneViewHolder) holder;
            oneViewHolder.setData(position);
        } else {
            TwoViewHolder twoViewHolder = (TwoViewHolder) holder;
            twoViewHolder.setData(position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        if(position == 0){
            return 1;
        }else {
            return 2;
        }
    }


    class OneViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitleName;
        private  TextView tvTtitleCount;
        public OneViewHolder(View itemview) {
            super(itemview);
        }

        public void setData(int position) {

//            tvTitleName = (TextView) itemView.findViewById(R.id.tv_brand);
//            tvTtitleCount = (TextView) itemView.findViewById(R.id.tv_count);
//            tvTitleName.setText("品牌名称");
//
//            tvTtitleCount.setText("店铺名称");
        }
    }

    public class TwoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvName;
        private  TextView tvStoreName;
        private LinearLayout llSelect;
//        private OnItemCommonClickListener commonClickListener;
        public TwoViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

        }

        public void setData(int position) {


            tvName = (TextView) itemView.findViewById(R.id.tv_brand_name);
            tvStoreName = (TextView) itemView.findViewById(R.id.tv_store_name);
            llSelect = (LinearLayout) itemView.findViewById(R.id.ll_select);
            Store store = list.get(position);

            tvName.setText(store.getBrandName());
            tvStoreName.setText(store.getStoreName());

        }

        @Override
        public void onClick(View v) {
            if (commonClickListener != null) {
                commonClickListener.onItemClickListener(getAdapterPosition());
            }

        }


    }
    public void setCommonClickListener(OnItemCommonClickListener commonClickListener) {
        this.commonClickListener = commonClickListener;
    }
    public  interface OnItemCommonClickListener {

        void onItemClickListener(int position);

    }


}
