package com.zwx.scan.app.feature.verification;

import android.app.Activity;
import android.content.Context;

import com.zwx.scan.app.base.BasePresenter;
import com.zwx.scan.app.base.BaseView;
import com.zwx.scan.app.feature.verificationrecord.VerificationRecordActivity;
import com.zwx.scan.app.feature.verificationrecord.VerificationRecordDetailActivity;

public interface VerificationContract {

    interface  View extends BaseView<VerificationContract.Presenter> {
//        void showLoading();
//        void hideLoading();
    }

    interface  Presenter extends BasePresenter {
        //会员卡扫码
        void appVerificationMemberType(Context context, String storeId, String memberId, String memberType, String consumeAmt , String personQnt);

        //优惠券核销
        void appVerificationMemCoupon(Context context,String userId, String storeId,String compMemberTypeId,String couponIdArray, String memberId,String consumeAmt ,String personQnt);


        //商品核销
        void verificationProduct(Context context, String storeId, String memberId,String compMemId,String orderCouponId, String compMemberTypeId);

        void queryVerificationRecord(Context context, String storeId, String compId, String date);

        void queryVerificationList(Context context,String storeId,String compId,String date);


        void queryVerificationDetail(Context context, String storeId,int pageSize,int pageNumber,String date,boolean isRereshAndMore);


        void checkIsBrandMember(VerificationActivity activity,String storeId,String memberId);

        //商品核销
        void productVerification(Context context, String storeId, String memberId,String compMemId,String orderCouponId, String compMemberTypeId);


        void verificationPlatCoupon(Context context, String storeId, String memberId, String compMemId, String orderCouponId, String compMemberTypeId,String type);
    }
}
