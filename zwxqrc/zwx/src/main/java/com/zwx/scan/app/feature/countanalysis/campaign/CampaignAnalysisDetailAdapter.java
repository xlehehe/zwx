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

import java.util.List;

public class CampaignAnalysisDetailAdapter extends RecyclerView.Adapter {

    private List<CampaignTotal> list;
    private Context context;
    protected LayoutInflater layoutInflater;

    public CampaignAnalysisDetailAdapter(Context context, List<CampaignTotal> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new OneViewHolder(LayoutInflater.from(context).inflate(R.layout.item_verification_record_list_title, parent,false));
        } else {
            return new TwoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_verification_record_list_content, parent,false));
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

            tvTitleName = (TextView) itemView.findViewById(R.id.tv_coupon_name);
            tvTtitleCount = (TextView) itemView.findViewById(R.id.tv_count);
            tvTitleName.setText("指标");

            tvTtitleCount.setText("统计情况");
        }
    }

    private class TwoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private  TextView tvCount;
        public TwoViewHolder(View itemView) {
            super(itemView);

        }

        public void setData(int position) {


            tvName = (TextView) itemView.findViewById(R.id.tv_coupon_name);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            CampaignTotal campaignTotal = list.get(position);
            tvName.setText(campaignTotal.getCampaignName());

            if(position == 0){
                tvCount.setText(campaignTotal.getJoinCount()+"");
            }else if(position == 1){
                tvCount.setText(campaignTotal.getFowardCount() == 0?"-":"343");
            }else if(position == 2){
                tvCount.setText(campaignTotal.getSendCouponCount()+"");
            }else if(position == 3){
                tvCount.setText(campaignTotal.getGetCouponCount() == 0?"-":"");
            }else if(position == 4){
                tvCount.setText(campaignTotal.getReceiveCouponCount() == 0?"-":"");
            }else if(position == 5){
                tvCount.setText(campaignTotal.getRegisterMemCount()+"");
            }else if(position == 6){
                tvCount.setText(campaignTotal.getViewCount()+"");
            }
        }



    }
}
