package com.zwx.scan.app.feature.campaign;


import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.CouponMaterial;
import com.zwx.scan.app.data.http.HttpUrls;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberInfoPerfectCouponNextFragment extends BaseFragment implements View.OnClickListener{

    @BindView(R.id.tv_name)
    public TextView tv_name;
    @BindView(R.id.tv_time_period)
    public  TextView tv_time_period;
    @BindView(R.id.tv_no_date)
    public  TextView tv_no_date;
    @BindView(R.id.tv_coupon_price)
    public TextView tv_coupon_price;

    @BindView(R.id.iv_delete)
    public ImageView ivDelete;
    @BindView(R.id.iv_avatar)
    public ImageView ivAvatar;

    @BindView(R.id.line)
    public View line;
    @BindView(R.id.tv_coupon_name)
    public TextView tv_coupon_name;

    //
    @BindView(R.id.ll_delete)
    public LinearLayout llDelete;
    protected Coupon coupon = new Coupon();

    protected CampaignCoupon campaignCoupon = new CampaignCoupon();

    private  int curPosition = 0;

    private String isNextTwoAndThree;
    public MemberInfoPerfectCouponNextFragment() {
        // Required empty public constructor
    }

   /* public static Fragment getInstance(Coupon coupon,int curPosition) {
        CampaignCouponsFragment fragment = new CampaignCouponsFragment();
        fragment.coupon = coupon;
        fragment.curPosition = curPosition;
        return fragment;
    }*/

    public static Fragment getInstance(Coupon coupon,int curPosition) {
        MemberInfoPerfectCouponNextFragment fragment = new MemberInfoPerfectCouponNextFragment();
        fragment.coupon = coupon;
        fragment.curPosition = curPosition;
        return fragment;
    }


    @Override
    protected int getlayoutId() {
        return R.layout.fragment_member_info_perfect_next;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {

        if(coupon == null){
            return;
        }

        campaignCoupon.setCouponId(coupon.getId());
        campaignCoupon.setCouponName(coupon.getName());
        String price = "";
        String pri = "";
        SpannableString spannableString;

        String dateCode = coupon.getDateCode();
        if(dateCode!=null && !"".equals(dateCode)) {
            if (!"00000".equals(dateCode)) {
                String noDate = coupon.getNoDate();
                if(noDate !=null && !"".equals(noDate)){

                    tv_no_date.setText(noDate);
                }else {
                    line.setVisibility(View.GONE);
                    tv_no_date.setText("");
                }
            }else {
                line.setVisibility(View.GONE);
                tv_no_date.setText("");
            }
        }

        Integer timePeriod = coupon.getTimePeriod();
        if(timePeriod !=null ){

            if(timePeriod == 1){
                tv_time_period.setText("全天");
            }else if(timePeriod == 2){
                tv_time_period.setText("午市");
            }else if(timePeriod == 3){
                tv_time_period.setText("晚市");
            }
        }else {
            line.setVisibility(View.GONE);
            tv_time_period.setText("");
        }
        tv_coupon_name.setText(coupon.getName());

        if(coupon.getLimit()!=null && coupon.getLimit()>0){
//            String limit =  new DecimalFormat("0.00").format(new BigDecimal(coupon.getLimit()).setScale(2, BigDecimal.ROUND_UNNECESSARY).doubleValue()).toString();
            String limit = "";
            if(String.valueOf(coupon.getLimit()).contains(".0")||String.valueOf(coupon.getLimit()).contains(".00")){
                limit = String.valueOf(coupon.getLimit()).substring(0,String.valueOf(coupon.getLimit()).indexOf("."));
            }else {
                limit = String.valueOf(coupon.getDiscount());
            }
            tv_coupon_price.setText("满"+limit+"元可用");
        }else {
            tv_coupon_price.setText("任意消费可用");
        }

        String object =coupon.getObject();
        if("CPC".equals(coupon.getType())){
            if(coupon.getMoney()!=null && coupon.getMoney() != 0.0){
                BigDecimal money = new BigDecimal(coupon.getMoney());
//                price =  new DecimalFormat("0").format(money.setScale(2, BigDecimal.ROUND_UNNECESSARY).doubleValue()).toString();
                if(String.valueOf(coupon.getMoney()).contains(".0")||String.valueOf(coupon.getMoney()).contains(".00")){
                    price = String.valueOf(coupon.getMoney()).substring(0,String.valueOf(coupon.getMoney()).indexOf("."));
                }else {
                    price = String.valueOf(coupon.getDiscount());
                }
            }else {
                price = "0";

            }
            pri = price+"元";
            spannableString= new SpannableString(pri);
            spannableString.setSpan(new AbsoluteSizeSpan(30,true), 0, price.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            tv_name.setText(spannableString);
            ivAvatar.setBackgroundResource(R.drawable.ic_coupon_price);



        }else if("CPD".equals(coupon.getType())){
            String discount = "";
            String dis = "";
            if(coupon.getDiscount()!=null && coupon.getDiscount() != 0){
                BigDecimal discounts = new BigDecimal(coupon.getDiscount());
//                discount =  new DecimalFormat("0").format(discounts.setScale(2, BigDecimal.ROUND_UNNECESSARY).doubleValue()).toString();
                if(String.valueOf(coupon.getDiscount()).contains(".0")){
                    discount = String.valueOf(coupon.getDiscount()).substring(0,String.valueOf(coupon.getDiscount()).indexOf("."));
                }else {
                    discount = String.valueOf(coupon.getDiscount());
                }
            }else {

                discount = "0";

            }
            dis = discount +"折";
            spannableString= new SpannableString(dis);
            spannableString.setSpan(new AbsoluteSizeSpan(30,true), 0, discount.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            tv_name.setText(spannableString);

        }else if("CPO".equals(coupon.getType())){
            tv_name.setText(object!=null?object:"");

        }else if("CPU".equals(coupon.getType())){

            tv_name.setText(object!=null?object:"");

        }else if("CPJ".equals(coupon.getType())){
            tv_name.setText("插队券");

        }else if("CPT".equals(coupon.getType())){  //其他
            String other = coupon.getOther();
            tv_name.setText(other!=null?other:"");

        }

        CouponMaterial material = coupon.getMaterial();
        String background = "";
        if(material != null){
            background = material.getBackground();
        }

        String bgPath = HttpUrls.IMAGE_ULR + background;

        Glide.with(getActivity()).load(bgPath).into(new SimpleTarget<Drawable>(220,220) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ivAvatar.setBackground(resource);    //设置背景
                }
            }
        });
        if("S".equals(MemberInfoPerfectNewActivity.compaignStatus)||"NEW".equals(MemberInfoPerfectNewActivity.compaignStatus)){
            llDelete.setVisibility(View.VISIBLE);
        }else {
            llDelete.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.ll_delete})
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case  R.id.ll_delete:
                MemberInfoPerfectNextTwoActivity activity = (MemberInfoPerfectNextTwoActivity)getActivity();

                if(activity.couponList != null && activity.couponList.size()>0){
                    for(int i = 0 ;i<activity.couponList.size();i++){
                        Coupon coupon1 = activity.couponList.get(i);
                        if(coupon1 != null){
                            Long couponId =coupon1.getId();
                            if(couponId != null){
                                if(couponId == coupon.getId()){
                                    activity.couponList.remove(i);
                                    i--;
                                }
                            }

                        }
                    }




                }
                activity.chanageLayout(activity.couponList);
                CampaignCouponListFragment.selectCoupons.clear();

                if(activity.campaignCouponList != null && activity.campaignCouponList.size()>0){
                    for(int i = 0 ;i<activity.campaignCouponList.size();i++){
                        CampaignCoupon campaignCoupon = activity.campaignCouponList.get(i);
                        if(campaignCoupon != null){
                            Long couponId =campaignCoupon.getCouponId();
                            if(couponId != null){
                                if(couponId == coupon.getId()){
                                    activity.campaignCouponList.remove(i);
                                    i--;
                                }
                            }

                        }
                    }
                }

                break;
        }
    }


    public  interface  ChanageToLayoutLisenter{

        void chanageLayout(ArrayList<Coupon> couponList);
    }
}
