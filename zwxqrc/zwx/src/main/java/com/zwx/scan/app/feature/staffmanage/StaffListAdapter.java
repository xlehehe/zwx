package com.zwx.scan.app.feature.staffmanage;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.utils.TextColorSizeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.MemCoupon;
import com.zwx.scan.app.data.bean.StaffWork;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/09
 * desc   :
 * version: 1.0
 **/
public class StaffListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<StaffWork> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private OnItemClickListener mClickListener;

    public StaffListAdapter(Context context, List<StaffWork> data) {
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
        StaffWork staffWork = data.get(position);


        if(staffWork.getStaff()!=null){
            holder.tv_name.setText(staffWork.getStaff().getName());
            holder.tv_phone.setText(staffWork.getStaff().getTel());
            if(staffWork.getStatus() != null &&staffWork.getStatus().intValue() == 0){
                holder.ivLeave.setBackgroundResource(R.drawable.ic_leave);
            }else if(staffWork.getStatus() != null &&staffWork.getStatus().intValue() == 1){
                holder.ivLeave.setBackgroundResource(R.drawable.ic_work);
            }else {

            }
        }else {
            holder.tv_name.setText("");
            holder.tv_phone.setText("");
        }


        if(staffWork.getPosition()!=null){
            holder.tv_position.setText(staffWork.getPosition().getKey());
        }else {
            holder.tv_position.setText("");
        }

        if(staffWork.getQrCode() !=null){
            if(staffWork.getQrCode().getNos()!=null){
                holder.tv_qrc .setText(staffWork.getQrCode().getNos().intValue()+"号二维码");
            }else {
                holder.tv_qrc .setText("");
            }

        }else {
            holder.tv_qrc .setText("");
        }



    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_staff_list, null);
        return new ChildViewHolder(view,mClickListener);
    }
    interface OnItemClickListener {
        public void onItemClick(View view, int postion);
    }

    class ChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tv_name;

        public TextView tv_phone;
        public TextView tv_position;
        public TextView tv_qrc;
        public ImageView ivLeave;

        private StaffListAdapter.OnItemClickListener mListener;// 声明自定义的接口

        public ChildViewHolder(View view,StaffListAdapter.OnItemClickListener listener) {
            super(view);

            mListener = listener;
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            tv_position = (TextView) view.findViewById(R.id.tv_position);
            tv_qrc = (TextView) view.findViewById(R.id.tv_qrc);
            ivLeave = (ImageView) view.findViewById(R.id.iv_is_leave);
            view.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            // getpostion()为Viewholder自带的一个方法，用来获取RecyclerView当前的位置，将此作为参数，传出去
            mListener.onItemClick(v, getPosition());
        }

    }
}


