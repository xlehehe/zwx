package com.zwx.scan.app.feature.countanalysis.campaign;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.CampaignTotal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
public class CampaignCouponAdapter extends RecyclerView.Adapter {

    private List<Map<String,Object>> list = new ArrayList<>();
    private Context context;
    protected LayoutInflater layoutInflater;

    public CampaignCouponAdapter(Context context, List<Map<String,Object>> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new OneViewHolder(LayoutInflater.from(context).inflate(R.layout.item_campaign_coupon_list_title, parent,false));
        } else {
            return new TwoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_campaign_coupon_list_content, parent,false));
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

        private TextView tv_send;
        private  TextView tv_foward;

        private  TextView tv_receive;
        public OneViewHolder(View itemview) {
            super(itemview);
        }

        public void setData(int position) {

          /*  tv_send = (TextView) itemView.findViewById(R.id.tv_send);
            tv_foward = (TextView) itemView.findViewById(R.id.tv_foward);
            tv_receive = (TextView) itemView.findViewById(R.id.tv_receive);
            tv_send.setText();

            tvTtitleCount.setText("统计情况");*/
        }
    }

    private class TwoViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private TextView tv_send;
        private  TextView tv_foward;

        private  TextView tv_receive;
        public TwoViewHolder(View itemView) {
            super(itemView);

        }

        public void setData(int position) {

            if(list ==null && list.size() == 0){

                return;
            }
            tv_name = (TextView) itemView.findViewById(R.id.tv_coupon_name);
            tv_send = (TextView) itemView.findViewById(R.id.tv_send);
            tv_foward = (TextView) itemView.findViewById(R.id.tv_foward);
            tv_receive = (TextView) itemView.findViewById(R.id.tv_receive);
            Map<String,Object>  map= list.get(position);
            tv_name.setText((String)map.get("couponName"));
            tv_send.setText(String.valueOf(map.get("getCouponCount") )!=null?String.valueOf(map.get("getCouponCount")):"-");

            tv_foward.setText(String.valueOf(map.get("sendCouponCount")) !=null?String.valueOf(map.get("sendCouponCount")):"-");

            tv_receive.setText(String.valueOf(map.get("receiveCouponCount")) !=null?String.valueOf(map.get("receiveCouponCount")):"-");




        }



    }
}
