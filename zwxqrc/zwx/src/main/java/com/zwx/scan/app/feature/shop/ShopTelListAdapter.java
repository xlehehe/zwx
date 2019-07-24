package com.zwx.scan.app.feature.shop;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.feature.campaign.LhGeEventBus;
import com.zwx.scan.app.feature.campaign.LhPtEventBus;
import com.zwx.scan.app.feature.user.LoginActivity;
import com.zwx.scan.app.utils.MaxTextLengthFilter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/04/18
 * desc   : 商品联系电话列表
 * version: 1.0
 **/
public class ShopTelListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Store> mDatas = new ArrayList();
    private String phone;
    private String storeId;
    public ShopTelListAdapter(Context context,List<Store> storeList) {
        super();
        this.mContext = context;
        this.mDatas = storeList;
    }

/*    public void setDatas(List datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }*/

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //int index = position;
         ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shop_tel_list, parent, false);
            holder = new ViewHolder();
            holder.tv_name  = (TextView) convertView.findViewById(R.id.tv_name);
            holder.edt_tel  = (EditText) convertView.findViewById(R.id.edt_tel);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String name = mDatas.get(position).getStoreName();

        if(name != null && !"".equals(name)){
            holder.tv_name.setText(name);
        }else {
            holder.tv_name.setText("");
        }

        String tel = mDatas.get(position).getShopTel();
        if(tel != null && !"".equals(tel)){
            holder.edt_tel.setText(tel);
        }else {
            holder.edt_tel.setText("");
        }
//        holder.edt_tel.setFilters(new InputFilter[]{new MaxTextLengthFilter(12)});
        holder.edt_tel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String phone = holder.edt_tel.getText().toString().trim();
//                String storeId  = mDatas.get(position).getStoreId();
//                EventBus.getDefault().post(new LhGeEventBus(storeId,phone));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.edt_tel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                }else {
                    String phone = holder.edt_tel.getText().toString().trim();
                    String storeId  = mDatas.get(position).getStoreId();
                    EventBus.getDefault().post(new LhGeEventBus(storeId,phone));
                }
            }
        });



        return convertView;
    }

    private class ViewHolder {
        TextView tv_name;
        TextView edt_tel;

    }
    interface  UpdaTel{

        void updateTel(String storeId,String tel);
    }

      /*class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.edt_tel)
        EditText edt_tel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }*/
}
