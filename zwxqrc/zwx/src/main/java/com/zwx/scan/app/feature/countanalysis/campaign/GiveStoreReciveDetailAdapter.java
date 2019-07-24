package com.zwx.scan.app.feature.countanalysis.campaign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCouponDetailbean;
import com.zwx.scan.app.data.bean.CampaignTotal;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.feature.home.SelectStoreAdapter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/01/17
 * desc   :
 * version: 1.0
 **/
public class GiveStoreReciveDetailAdapter extends BaseAdapter {

    private Context mContext;
    private List<CampaignCouponDetailbean.StoreCouponBean> mDatas = new ArrayList();

    public GiveStoreReciveDetailAdapter(Context context, List<CampaignCouponDetailbean.StoreCouponBean>  datas) {
        mContext = context;
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public CampaignCouponDetailbean.StoreCouponBean getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //int index = position;
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_give_analysis_store_receive_detail_content_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        TextView tv_coupon_name = (TextView)holder..findViewById(R.id.tv_coupon_name);
//        TextView tv_receive = (TextView)detailContent.findViewById(R.id.tv_receive);
//
//        TextView tv_shouyi = (TextView)detailContent.findViewById(R.id.tv_shouyi);
//        mViewHolder.mCbCheckbox.setTag(mDatas.get(position));
//        mViewHolder.mCbCheckbox.setOnCheckedChangeListener(this);
//        mViewHolder.mCbCheckbox.setChecked(mDatas.get(position).getCheckStatus());
//        mDatas.get(position).setCheckStatus(mDatas.get(position).getCheckStatus()?true:false);

        CampaignCouponDetailbean.StoreCouponBean storeCouponBean= mDatas.get(position);

        String storeName = storeCouponBean.getStore().getStoreName();

        List<CampaignTotal> couponList = storeCouponBean.getCoupon();

        holder.ll_coupon.removeAllViews();
        if(couponList != null && couponList.size()>0){

            for (int i = 0;i<couponList.size();i++){
                CampaignTotal campaignTotal = couponList.get(i);
                View detailContent  = LayoutInflater.from(mContext).inflate(R.layout.item_give_analysis_store_receive_detail_content_coupon_layout,null);

                TextView tv_shouyi = (TextView)detailContent.findViewById(R.id.tv_shouyi);
                TextView tv_coupon_name = (TextView)detailContent.findViewById(R.id.tv_coupon_name);
                TextView tv_receive = (TextView)detailContent.findViewById(R.id.tv_receive);
                String couponName = campaignTotal.getCouponName();
                int  receive = campaignTotal.getReceiveCouponCount();
                BigDecimal amt = campaignTotal.getConsumeAmt();
                if(amt != null && amt.doubleValue()>0){
//                    String consume = new DecimalFormat("0.00").format(amt.setScale(2,BigDecimal.ROUND_DOWN).doubleValue()).toString();
                    String consume = RegexUtils.getDoubleString(amt.doubleValue());
                    tv_shouyi.setText(consume);
                }else {
                   tv_shouyi.setText("-");
                }
                tv_coupon_name.setText(couponName != null ? couponName : "");

                if(receive >0){
                   tv_receive.setText(receive +"");
                }else {
                    tv_receive.setText("-");
                }


                holder.ll_coupon.addView(detailContent);
            }
        }


        holder.tv_store.setText(storeName != null ? storeName : "");


        return convertView;
    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        Person person = (Person) buttonView.getTag();
//        person.setCheckStatus(isChecked);
//    }

     static class ViewHolder {
        @BindView(R.id.tv_store)
        TextView tv_store;
        @BindView(R.id.ll_coupon)
        LinearLayout ll_coupon;
       /* @BindView(R.id.tv_coupon_name)
        TextView tv_coupon_name;
        @BindView(R.id.tv_receive)
        TextView tv_receive;
        @BindView(R.id.tv_shouyi)
        TextView tv_shouyi;
*/
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
