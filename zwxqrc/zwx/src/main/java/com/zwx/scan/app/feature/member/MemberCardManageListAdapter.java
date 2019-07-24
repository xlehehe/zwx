package com.zwx.scan.app.feature.member;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.CompMemberType;

import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/04/02
 * desc   :
 * version: 1.0
 **/
public class MemberCardManageListAdapter extends RecyclerView.Adapter {

    private List<CompMemberType> data = new ArrayList<>();

    private LayoutInflater inflater;
    private Context context;
    public MemberCardManageListAdapter(Context context, List<CompMemberType> data) {
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

        CompMemberType compMemberType =  data.get(position);
        String status = compMemberType.getMemtypeStatus();
        String isDefault = compMemberType.getIsDefault();

        if("1".equals(isDefault)){
            if(status != null && !"".equals(status) && "1".equals(status)){
                return   1 ;
            }else  if(status != null && !"".equals(status) && "0".equals(status)){
                return   0 ;
            }
        }else {
            return 2;
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

        CompMemberType coupon = data.get(position);

        String name = coupon.getCompMemberGroup();
        String status =coupon.getMemtypeStatus();

        String isDefault = coupon.getIsDefault();
        holder.tv_card_name.setText(name!=null?name:"");

        if("1".equals(isDefault)||isDefault == "1"){
            holder.tv_card_type_name.setText("非默认会员卡");
        }else  if("0".equals(isDefault)||isDefault == "0"){
            holder.tv_card_type_name.setText("默认会员卡");
        }



        if(status != null && !"".equals(status) && "1".equals(status)){
            holder.ivStatus.setBackgroundResource(R.drawable.ic_coupon_status_use);
        }else  if(status != null && !"".equals(status) && "0".equals(status)){
            holder.ivStatus.setBackgroundResource(R.drawable.ic_status_green_unuse);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_member_card_manage_list, viewHolder,false);
        return new ChildViewHolder(view);
    }
    class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_card_name;
        public TextView tv_card_type_name;
        public ImageView ivStatus;
//        public  TextView tv_time_period;
//        public  TextView tv_no_date;
//        public TextView tv_coupon_price;
//        public ImageView iv_head;
//
//        public View line;


        public ChildViewHolder(View view) {
            super(view);
            ivStatus = (ImageView) view.findViewById(R.id.iv_status);
            tv_card_name = (TextView) view.findViewById(R.id.tv_card_name);
            tv_card_type_name = (TextView) view.findViewById(R.id.tv_card_type_name);



        }

    }


}
