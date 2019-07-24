package com.zwx.scan.app.feature.staffmanage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.StaffWork;
import com.zwx.scan.app.feature.financialaffairs.MemCardEmploeeSaleReportListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/06/17
 * desc   :
 * version: 1.0
 **/
public class StaffListViewdapter extends BaseAdapter {


    private List<StaffWork> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private StaffListAdapter.OnItemClickListener mClickListener;

    public StaffListViewdapter(Context context, List<StaffWork> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_staff_list, parent, false);
            holder = new ViewHolder(view);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        StaffWork staffWork = data.get(position);
        if(staffWork.getStaff()!=null){
            holder.tv_name.setText(staffWork.getStaff().getName());
            holder.tv_phone.setText(staffWork.getStaff().getTel());
            if(staffWork.getStatus() != null &&staffWork.getStatus().intValue() == 0){
                holder.iv_is_leave.setBackgroundResource(R.drawable.ic_leave);
            }else if(staffWork.getStatus() != null &&staffWork.getStatus().intValue() == 1){
                holder.iv_is_leave.setBackgroundResource(R.drawable.ic_work);
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



        return view;
    }



    class ViewHolder {
        @BindView(R.id.tv_name)
        public TextView tv_name;
        @BindView(R.id.tv_phone)
        public TextView tv_phone;
        @BindView(R.id.tv_position)
        public TextView tv_position;
        @BindView(R.id.tv_qrc)
        public TextView tv_qrc;
        @BindView(R.id.iv_is_leave)
        public ImageView iv_is_leave;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


      /*  private StaffListAdapter.OnItemClickListener mListener;// 声明自定义的接口

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
*/
    }


}
