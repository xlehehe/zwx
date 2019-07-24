package com.zwx.scan.app.feature.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.MemberRedEnvelope;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/04/30
 * desc   :
 * version: 1.0
 **/
public class RedEnvelopeListViewAdapter extends BaseAdapter {
    private List<MemberRedEnvelope> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public RedEnvelopeListViewAdapter(Context context, List<MemberRedEnvelope> data) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_red_envelope_list, parent, false);
            holder = new ViewHolder(view);


            holder.tv_red_id = (TextView) view.findViewById(R.id.tv_red_id);
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            holder.tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            holder.tv_red_amt = (TextView) view.findViewById(R.id.tv_red_amt);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        MemberRedEnvelope envelope = data.get(position);
        BigDecimal amt = envelope.getBalance();
        if(amt != null &&amt.doubleValue()>0){
            holder.tv_red_amt.setText(RegexUtils.getDoubleString(amt.doubleValue())+"元");
        }else {
            holder.tv_red_amt.setText("0元");
        }
        String name = envelope.getMemberName();
        if(name != null && !"".equals(name)){
            holder.tv_name.setText(name);
        }else {
            holder.tv_name.setText("-");
        }
        String phone = envelope.getMemberTel();
        if(phone != null && !"".equals(phone)){
            holder.tv_phone.setText(phone);
        }else {
            holder.tv_phone.setText("-");
        }
        int cout = position+1;
        holder.tv_red_id.setText(cout+"");


        return view;
    }


    class ViewHolder {
        @BindView(R.id.tv_red_id)
        public TextView tv_red_id;
        @BindView(R.id.tv_name)
        public TextView tv_name;
        @BindView(R.id.tv_phone)
        public TextView tv_phone;
        @BindView(R.id.tv_red_amt)
        public TextView tv_red_amt;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
