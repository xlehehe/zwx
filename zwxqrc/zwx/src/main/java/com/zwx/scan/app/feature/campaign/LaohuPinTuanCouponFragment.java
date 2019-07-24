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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.CouponMaterial;
import com.zwx.scan.app.data.http.HttpUrls;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LaohuPinTuanCouponFragment extends BaseFragment implements View.OnClickListener{
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


    private String prizeName;
    public LaohuPinTuanCouponFragment() {
    }


    public static Fragment getInstance(Coupon coupon,String prizeName) {
        LaohuPinTuanCouponFragment fragment = new LaohuPinTuanCouponFragment();
        fragment.coupon = coupon;
        fragment.prizeName = prizeName;
        return fragment;
    }
    @Override
    protected int getlayoutId() {
        return R.layout.fragment_laohu_pin_tuan_coupon;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        if(coupon != null){
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
                        tv_no_date.setText("");
                        line.setVisibility(View.GONE);
                    }
                }else {
                    tv_no_date.setText("");
                    line.setVisibility(View.GONE);
                }
            }

            Integer timePeriod = coupon.getTimePeriod();
            if(timePeriod !=null ){

                if(timePeriod == 1){
                    tv_time_period.setText("全天");
                }else if(timePeriod == 2){
                    tv_time_period.setText("午市");
                }else if(timePeriod == 3){
                    tv_time_period.setText("晚时可用");
                }
            }else {
                tv_time_period.setText("");
                line.setVisibility(View.GONE);
            }
            tv_coupon_name.setText(coupon.getName());

            if(coupon.getLimit()!=null && coupon.getLimit().floatValue()>0){
//                String limit =  new DecimalFormat("0.00").format(new BigDecimal(Double.parseDouble(String.valueOf(coupon.getLimit()))).setScale(2, BigDecimal.ROUND_UNNECESSARY).doubleValue()).toString();
                String limit = "0";
               /* if(String.valueOf(coupon.getLimit()).contains(".0")||String.valueOf(coupon.getLimit()).contains(".00")){
                    limit = String.valueOf(coupon.getLimit()).substring(0,String.valueOf(coupon.getLimit()).indexOf("."));
                }else {
                    if(String.valueOf(coupon.getDiscount()).contains(".0")||String.valueOf(coupon.getDiscount()).contains(".00")){
                        limit = String.valueOf(coupon.getDiscount()).substring(0,String.valueOf(coupon.getDiscount()).indexOf("."));
                    }else {
                        limit = String.valueOf(coupon.getDiscount());
                    }
                }*/
                limit = RegexUtils.getDoubleString(Double.parseDouble(String.valueOf(coupon.getLimit())));
                tv_coupon_price.setText("满"+limit+"元可用");
            }else {
                tv_coupon_price.setText("任意消费可用");
            }

            String object =coupon.getObject();
            if("CPC".equals(coupon.getType())){
                if(coupon.getMoney()!=null && coupon.getMoney() != 0.0){
                    BigDecimal money = new BigDecimal(coupon.getMoney().doubleValue());
                    price =  new DecimalFormat("0").format(money.setScale(2, BigDecimal.ROUND_UNNECESSARY).doubleValue()).toString();

                }else {
                    price = "0";

                }
                pri = price+"元";
                spannableString= new SpannableString(pri);
                spannableString.setSpan(new AbsoluteSizeSpan(30,true), 0, price.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                tv_name.setText(spannableString);
//                ivAvatar.setBackgroundResource(R.drawable.ic_coupon_price);



            }else if("CPD".equals(coupon.getType())){
                String discount = "";
                String dis = "";
                if(coupon.getDiscount()!=null && coupon.getDiscount() != 0){
//                    BigDecimal discounts = new BigDecimal(coupon.getDiscount());
//                    discount =  new DecimalFormat("0").format(discounts.setScale(2, BigDecimal.ROUND_UNNECESSARY).doubleValue()).toString();
                   /* if(String.valueOf(coupon.getDiscount()).contains(".0")){
                        discount = String.valueOf(coupon.getDiscount()).substring(0,String.valueOf(coupon.getDiscount()).indexOf("."));
                    }else {
                        discount = String.valueOf(coupon.getDiscount());
                    }*/
                    discount = RegexUtils.getDoubleString(Double.parseDouble(String.valueOf(coupon.getDiscount())));

                }else {

                    discount = "0";

                }
                dis = discount +"折";
                spannableString= new SpannableString(dis);
                spannableString.setSpan(new AbsoluteSizeSpan(30,true), 0, discount.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

                tv_name.setText(spannableString);
//                ivAvatar.setBackgroundResource(R.drawable.ic_coupon_discount);

            }else if("CPO".equals(coupon.getType())){
//                ivAvatar.setBackgroundResource(R.drawable.ic_coupon_prese);
                tv_name.setText(object!=null?object:"");

            }else if("CPU".equals(coupon.getType())){
//                ivAvatar.setBackgroundResource(R.drawable.ic_coupon_dishes);

                tv_name.setText(object!=null?object:"");

            }else if("CPJ".equals(coupon.getType())){
//                ivAvatar.setBackgroundResource(R.drawable.ic_coupon_paidui);
                tv_name.setText("插队券");

            }else if("CPT".equals(coupon.getType())){  //其他
                String other = coupon.getOther();
                tv_name.setText(other!=null?other:"");
//                ivAvatar.setBackgroundResource(R.drawable.ic_coupon_other);

            }
            if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                llDelete.setVisibility(View.VISIBLE);
            }else {
                llDelete.setVisibility(View.GONE);
            }

            CouponMaterial material = coupon.getMaterial();
            String background = "";
            if(material != null){
                background = material.getBackground();
            }

            String bgPath = HttpUrls.IMAGE_ULR + background;

           /* Glide.with(getActivity()).load(bgPath).into(new SimpleTarget<Drawable>(220,220) {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        ivAvatar.setBackground(resource);    //设置背景
                    }
                }
            });*/
            RequestOptions requestOptions2 = new RequestOptions()
                    .placeholder(R.drawable.ic_banner_default)
                    .error(R.drawable.ic_banner_default)
                    .fallback(R.drawable.ic_banner_default);

            Glide.with(this).load(bgPath).apply(requestOptions2).into(ivAvatar);
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


                LaohuPinTuanNextTwoActivity activity = (LaohuPinTuanNextTwoActivity)getActivity();


               /* if("奖项一".equals(prizeName)){

                }else if("奖项二".equals(prizeName)){

                }else if("奖项三".equals(prizeName)){

                }else if("奖项四".equals(prizeName)){

                }else if("奖项五".equals(prizeName)){

                }else if("奖项六".equals(prizeName)){

                }else if("安慰奖".equals(prizeName)){

                }*/

                if("奖项一".equals(prizeName)){
                    if(activity.campaignCouponList1!= null && activity.campaignCouponList1.size()>0){
                        for(int i = 0 ;i<activity.campaignCouponList1.size();i++){
                            CampaignCoupon campaignCoupon = activity.campaignCouponList1.get(i);
                            if(campaignCoupon != null){
                                Long couponId =campaignCoupon.getCouponId();
                                if(couponId != null){
                                    if(couponId == coupon.getId()){
                                        activity.campaignCouponList1.remove(i);
                                        i--;
                                    }
                                }

                            }
                        }
                    }
                    if(activity.couponList1 != null && activity.couponList1.size()>0){
                        for(int i = 0 ;i<activity.couponList1.size();i++){
                            Coupon coupon1 = activity.couponList1.get(i);
                            if(coupon1 != null){
                                Long couponId =coupon1.getId();
                                if(couponId != null){
                                    if(couponId == coupon.getId()){
                                        activity.couponList1.remove(i);
                                        i--;
                                    }
                                }

                            }
                        }
                        CampaignCouponListFragment.selectCoupons.clear();
                        //同时删除优惠券列表页面
                        ((LaohuPinTuanNextTwoFragment) (this.getParentFragment())).chanageLayout(activity.couponList1,prizeName);

                    }
                }else if("奖项二".equals(prizeName)){
                    if(activity.campaignCouponList2!= null && activity.campaignCouponList2.size()>0){
                        for(int i = 0 ;i<activity.campaignCouponList2.size();i++){
                            CampaignCoupon campaignCoupon = activity.campaignCouponList2.get(i);
                            if(campaignCoupon != null){
                                Long couponId =campaignCoupon.getCouponId();
                                if(couponId != null){
                                    if(couponId == coupon.getId()){
                                        activity.campaignCouponList2.remove(i);
                                        i--;
                                    }
                                }

                            }
                        }
                    }
                    if(activity.couponList2 != null && activity.couponList2.size()>0){
                        for(int i = 0 ;i<activity.couponList2.size();i++){
                            Coupon coupon1 = activity.couponList2.get(i);
                            if(coupon1 != null){
                                Long couponId =coupon1.getId();
                                if(couponId != null){
                                    if(couponId == coupon.getId()){
                                        activity.couponList2.remove(i);
                                        i--;
                                    }
                                }

                            }
                        }
                        CampaignCouponListFragment.selectCoupons.clear();
                        //同时删除优惠券列表页面
                        ((LaohuPinTuanNextTwoFragment) (this.getParentFragment())).chanageLayout(activity.couponList2,prizeName);

                    }
                }else if("奖项三".equals(prizeName)){
                    if(activity.campaignCouponList3!= null && activity.campaignCouponList3.size()>0){
                        for(int i = 0 ;i<activity.campaignCouponList3.size();i++){
                            CampaignCoupon campaignCoupon = activity.campaignCouponList3.get(i);
                            if(campaignCoupon != null){
                                Long couponId =campaignCoupon.getCouponId();
                                if(couponId != null){
                                    if(couponId == coupon.getId()){
                                        activity.campaignCouponList3.remove(i);
                                        i--;
                                    }
                                }

                            }
                        }
                    }
                    if(activity.couponList3 != null && activity.couponList3.size()>0){
                        for(int i = 0 ;i<activity.couponList3.size();i++){
                            Coupon coupon1 = activity.couponList3.get(i);
                            if(coupon1 != null){
                                Long couponId =coupon1.getId();
                                if(couponId != null){
                                    if(couponId == coupon.getId()){
                                        activity.couponList3.remove(i);
                                        i--;
                                    }
                                }

                            }
                        }
                        CampaignCouponListFragment.selectCoupons.clear();
                        //同时删除优惠券列表页面
                        ((LaohuPinTuanNextTwoFragment) (this.getParentFragment())).chanageLayout(activity.couponList3,prizeName);

                    }
                }else if("奖项四".equals(prizeName)){
                    if(activity.campaignCouponList4!= null && activity.campaignCouponList4.size()>0){
                        for(int i = 0 ;i<activity.campaignCouponList4.size();i++){
                            CampaignCoupon campaignCoupon = activity.campaignCouponList4.get(i);
                            if(campaignCoupon != null){
                                Long couponId =campaignCoupon.getCouponId();
                                if(couponId != null){
                                    if(couponId == coupon.getId()){
                                        activity.campaignCouponList4.remove(i);
                                        i--;
                                    }
                                }

                            }
                        }
                    }
                    if(activity.couponList4 != null && activity.couponList4.size()>0){
                        for(int i = 0 ;i<activity.couponList4.size();i++){
                            Coupon coupon1 = activity.couponList4.get(i);
                            if(coupon1 != null){
                                Long couponId =coupon1.getId();
                                if(couponId != null){
                                    if(couponId == coupon.getId()){
                                        activity.couponList4.remove(i);
                                        i--;
                                    }
                                }

                            }
                        }
                        CampaignCouponListFragment.selectCoupons.clear();
                        //同时删除优惠券列表页面
                        ((LaohuPinTuanNextTwoFragment) (this.getParentFragment())).chanageLayout(activity.couponList4,prizeName);

                    }
                }else if("奖项五".equals(prizeName)){
                    if(activity.campaignCouponList5!= null && activity.campaignCouponList5.size()>0){
                        for(int i = 0 ;i<activity.campaignCouponList5.size();i++){
                            CampaignCoupon campaignCoupon = activity.campaignCouponList5.get(i);
                            if(campaignCoupon != null){
                                Long couponId =campaignCoupon.getCouponId();
                                if(couponId != null){
                                    if(couponId == coupon.getId()){
                                        activity.campaignCouponList5.remove(i);
                                        i--;
                                    }
                                }

                            }
                        }
                    }
                    if(activity.couponList5!= null && activity.couponList5.size()>0){
                        for(int i = 0 ;i<activity.couponList5.size();i++){
                            Coupon coupon1 = activity.couponList5.get(i);
                            if(coupon1 != null){
                                Long couponId =coupon1.getId();
                                if(couponId != null){
                                    if(couponId == coupon.getId()){
                                        activity.couponList5.remove(i);
                                        i--;
                                    }
                                }

                            }
                        }
                        CampaignCouponListFragment.selectCoupons.clear();
                        //同时删除优惠券列表页面
                        ((LaohuPinTuanNextTwoFragment) (this.getParentFragment())).chanageLayout(activity.couponList5,prizeName);

                    }


                }else if("奖项六".equals(prizeName)){
                    if(activity.campaignCouponList6!= null && activity.campaignCouponList6.size()>0){
                        for(int i = 0 ;i<activity.campaignCouponList6.size();i++){
                            CampaignCoupon campaignCoupon = activity.campaignCouponList6.get(i);
                            if(campaignCoupon != null){
                                Long couponId =campaignCoupon.getCouponId();
                                if(couponId != null){
                                    if(couponId == coupon.getId()){
                                        activity.campaignCouponList6.remove(i);
                                        i--;
                                    }
                                }

                            }
                        }
                    }
                    if(activity.couponList6!= null && activity.couponList6.size()>0){
                        for(int i = 0 ;i<activity.couponList6.size();i++){
                            Coupon coupon1 = activity.couponList6.get(i);
                            if(coupon1 != null){
                                Long couponId =coupon1.getId();
                                if(couponId != null){
                                    if(couponId == coupon.getId()){
                                        activity.couponList6.remove(i);
                                        i--;
                                    }
                                }

                            }
                        }
                        CampaignCouponListFragment.selectCoupons.clear();
                        //同时删除优惠券列表页面
                        ((LaohuPinTuanNextTwoFragment) (this.getParentFragment())).chanageLayout(activity.couponList6,prizeName);

                    }
                }else if("安慰奖".equals(prizeName)){
                    if(activity.campaignCouponList7!= null && activity.campaignCouponList7.size()>0){
                        for(int i = 0 ;i<activity.campaignCouponList7.size();i++){
                            CampaignCoupon campaignCoupon = activity.campaignCouponList7.get(i);
                            if(campaignCoupon != null){
                                Long couponId =campaignCoupon.getCouponId();
                                if(couponId != null){
                                    if(couponId == coupon.getId()){
                                        activity.campaignCouponList7.remove(i);
                                        i--;
                                    }
                                }

                            }
                        }
                    }
                    if(activity.couponList7!= null && activity.couponList7.size()>0){
                        for(int i = 0 ;i<activity.couponList7.size();i++){
                            Coupon coupon1 = activity.couponList7.get(i);
                            if(coupon1 != null){
                                Long couponId =coupon1.getId();
                                if(couponId != null){
                                    if(couponId == coupon.getId()){
                                        activity.couponList7.remove(i);
                                        i--;
                                    }
                                }

                            }
                        }
                        CampaignCouponListFragment.selectCoupons.clear();
                        //同时删除优惠券列表页面
                        ((LaohuPinTuanNextTwoFragment) (this.getParentFragment())).chanageLayout(activity.couponList7,prizeName);

                    }
                }

                break;
        }
    }




    interface  ChanageLhPtTwoLayoutLisenter{

        void chanageLayout(ArrayList<Coupon> couponList,String prizeName);
    }
}
