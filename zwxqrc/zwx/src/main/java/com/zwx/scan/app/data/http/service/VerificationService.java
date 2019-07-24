package com.zwx.scan.app.data.http.service;


import android.content.Context;

import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.MemCoupon;
import com.zwx.scan.app.data.bean.ResultBean;
import com.zwx.scan.app.data.bean.VerificationRecord;
import com.zwx.scan.app.data.http.HttpUrls;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface VerificationService {

    /***
     * 微信 二维码扫描请求结果
     * @param
     * @param
     * @return Type Observable<HttpResponse>
     * */

    @POST(HttpUrls.WECHAT_URL)
    Observable<HttpResponse<ResultBean>> appVerificationMemberType(@Query("storeId") String storeId, @Query("memberId") String memberId,
                                                                   @Query("memberType") String memberType, @Query("consumeAmt") String consumeAmt,
                                                                   @Query("personQnt") String personQnt);




    /***
     *
     * 优惠券扫码请求结果
     * @param
     * @param
     * @return Type Observable<HttpResponse>
     * */
    @POST(HttpUrls.COUPON_URL)
    Observable<HttpResponse<String>> appVerificationMemCoupon(@Query("userId") String userId, @Query("storeId") String storeId,@Query("compMemberTypeId") String compMemberTypeId,
                                                                  @Query("array") String array,
                                                                  @Query("memberId") String memberId,
                                                                  @Query("consumeAmt") String consumeAmt, @Query("personQnt") String personQnt);

    /***
     *
     * 商品扫码请求结果
     * @param
     * @param
     * @return Type Observable<HttpResponse>
     * */
    @POST(HttpUrls.VERIFICATION_GOOD_URL)
    Observable<HttpResponse<String>> verificationProduct(@Query("storeId") String storeId,@Query("memberId") String memberId,
                                                             @Query("compMemId") String compMemId, @Query("orderCouponId") String orderCouponId,@Query("compMemberTypeId") String compMemberTypeId);

    /***
     *
     * 商品扫码请求结果
     * @param
     * @param
     * @return Type Observable<HttpResponse>
     * */
    @POST(HttpUrls.VERIFICATION_PLAT_COUPON_URL)
    Observable<HttpResponse<String>> verificationPlatProduct(@Query("storeId") String storeId,@Query("memberId") String memberId,
                                                         @Query("compMemId") String compMemId, @Query("orderCouponId") String orderCouponId,@Query("compMemberTypeId") String compMemberTypeId,@Query("orderCouponType") String orderCouponType);

    @POST(HttpUrls.VE_RECORD_URL)
    Observable<HttpResponse<VerificationRecord>> queryVerificationRecord(@Query("storeId") String storeId,
                                                                         @Query("compId") String compId,
                                                                         @Query("date") String date);

    @POST(HttpUrls.VE_LIST_URL)
    Observable<HttpResponse<List<MemCoupon>>> queryVerificationList(@Query("storeId") String storeId,@Query("compId") String compId,@Query("date") String date);


    @POST(HttpUrls.VE_LIST_DETAIL_URL)
    Observable<HttpResponse<List<MemCoupon>>> queryVerificationDetail(@Query("storeId") String storeId,@Query("pageSize") Integer pageSize,@Query("pageNumber") Integer pageNumber,@Query("date") String date);

    @POST(HttpUrls.CHECK_MEMBER_URL)
    Observable<HttpResponse<ResultBean>> checkIsBrandMember(@Query("storeId") String storeId, @Query("memberId") String memberId);




}
