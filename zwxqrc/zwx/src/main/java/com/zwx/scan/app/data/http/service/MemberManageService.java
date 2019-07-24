package com.zwx.scan.app.data.http.service;

import android.content.Context;
import android.content.Intent;

import com.zwx.scan.app.data.bean.CampaignTotal;
import com.zwx.scan.app.data.bean.CompMemType;
import com.zwx.scan.app.data.bean.CompMemTypeDetailBean;
import com.zwx.scan.app.data.bean.CompMemberType;
import com.zwx.scan.app.data.bean.ComsumeLog;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.MemCoupon;
import com.zwx.scan.app.data.bean.MemTypeBean;
import com.zwx.scan.app.data.bean.MemberConsumeBean;
import com.zwx.scan.app.data.bean.MemberInfoBean;
import com.zwx.scan.app.data.bean.MemberInfoDetailBean;
import com.zwx.scan.app.data.bean.Order;
import com.zwx.scan.app.data.bean.TemplateBean;
import com.zwx.scan.app.data.bean.TokenVali;
import com.zwx.scan.app.data.http.HttpUrls;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * author : lizhilong
 * time   : 2018/12/12
 * desc   :
 * version: 1.0
 **/
public interface MemberManageService {
    //
    @FormUrlEncoded
    @POST(HttpUrls.MEMBER_CONSUME_LIST_URL)
    Observable<HttpResponse<MemberConsumeBean>> comsumeListByMap(@Field("userId")String userId, @Field("pageNumber")Integer pageNumber,
                                                                 @Field("pageSize")Integer pageSize,@Field("order")String order, @Field("desc")String desc);


    @FormUrlEncoded
    @POST(HttpUrls.MEMBER_CONSUME_LIST_URL)
    Observable<HttpResponse<MemberConsumeBean>> queryComsumeList(@Field("userId")String userId,@Field("pageNumber")Integer pageNumber,
                                                                 @Field("pageSize")Integer pageSize, @Field("order")String order, @Field("desc")String desc);


    @POST(HttpUrls.MEMBER_CONSUME_LIST_URL)
    Observable<HttpResponse<MemberConsumeBean>> checkIsBrandMember();
    @FormUrlEncoded
    @POST(HttpUrls.MEMBER_TYPE_LIST_URL)
    Observable<HttpResponse<List<MemTypeBean>>> memberCardListByMap(@Field("memberId")String memberId);


    //根据会员卡ID查询消费记录列表
    @FormUrlEncoded
    @POST(HttpUrls.MEMBER_CONSUME_DETAIL_LIST_URL)
    Observable<HttpResponse<List<CampaignTotal>>> doQuery(@Field("pageNumber")Integer pageNumber,
                                                          @Field("pageSize")Integer pageSize, @Field("compMemTypeId")String compMemTypeId, @Field("userId")String userId);



    @FormUrlEncoded
    @POST(HttpUrls.MEMBER_INFO_LIST_URL)
    Observable<HttpResponse<MemberInfoBean>> memberListByMap(@Field("memberTel")String memberTel, @Field("userId") String userId,
                                                             @Field("pageNumber")Integer pageNumber,
                                                             @Field("pageSize")Integer pageSize);


    @FormUrlEncoded
    @POST(HttpUrls.DO_MEMBER_INFO_URL)
    Observable<HttpResponse<MemberInfoDetailBean>> doGetMemberType(@Field("memberId")String memberId, @Field("userId") String userId);



    @FormUrlEncoded
    @POST(HttpUrls.DO_MEMBER_TYPE_URL)
    Observable<HttpResponse<List<CompMemType>>> doQueryMemberType(@Field("memberId")String memberId);


    @FormUrlEncoded
    @POST(HttpUrls.MEMBER_CONSUME_LOG_LIST__URL)
    Observable<HttpResponse<List<ComsumeLog>>> doQueryComsumeLog(
                                                             @Field("pageNumber")Integer pageNumber,
                                                             @Field("pageSize")Integer pageSize,
                                                             @Field("compMemTypeId")String compMemTypeId, @Field("userId") String userId, @Field("memberId") String memberId);



    //会员卡流水
    @GET(HttpUrls.MEMBER_CARD_ORDER_TOTAL)
    Observable<HttpResponse<List<Order>>>  selectToCounByOrder(@Query("userId") String userId, @Query("salesTimeSta") String salesTimeSta,
                                                              @Query("salesTimesEnd") String salesTimesEnd, @Query("storeId") String storeId,
                                                              @Query("pageSize") Integer pageSize, @Query("pageNumber") Integer pageNumber);

    //会员卡流水详情
    @GET(HttpUrls.MEMBER_CARD_ORDER_TOTAL_DETAIL)
    Observable<HttpResponse<List<Order>>>  selectToITByOrder(@Query("userId")String userId,@Query("compMemtypeId")String compMemtypeId,
                                                           @Query("salesTimeSta")String salesTimeSta,
                                                             @Query("salesTimesEnd")String salesTimesEnd,@Query("storeId")String storeId,
                                                             @Query("pageSize")Integer pageSize,@Query("pageNumber")Integer pageNumber);


    //会员卡列表

    @GET(HttpUrls.DO_MEMBER_TYPE_LIST)
    Observable<HttpResponse<List<CompMemberType>>> doMemTypeList(
            @Query("userId") String userId,
            @Query("pageNumber")Integer pageNumber,
            @Query("pageSize")Integer pageSize);

    //会员卡列表停用 删除
    @FormUrlEncoded
    @POST(HttpUrls.DO_MEMBER_STATUS)
    Observable<HttpResponse<String>> memberTypeOperation(
            @Field("userId") String userId, @Field("type") String type,
            @Field("memtypeIdArray") String memtypeIdArray, @Field("compMemberGroup") String compMemberGroup);

    // 查询模板与所属公司素材
    @GET(HttpUrls.DO_TEMPLACARD)
    Observable<HttpResponse<TemplateBean>> doTemplaCard(
            @Query("companyId") String companyId);



    //新增会员卡
    @FormUrlEncoded
    @POST(HttpUrls.INSERT_MEMBER_TYPE)
    Observable<HttpResponse<Map<String,String>>> insertMemberType(
            @Field("userId") String userId, @Field("compMemberTypeStr") String compMemberTypeStr,@Field("brandArray") String brandArray);


    @FormUrlEncoded
    @POST(HttpUrls.UPDATE_MEMBER_TYPE)
    Observable<HttpResponse<String>> updateMemberType(
            @Field("userId") String userId, @Field("compMemberTypeStr") String compMemberTypeStr,@Field("brandArray") String brandArray,@Field("flag") String memtypeStatus);



    @POST(HttpUrls.SELECT_MEMBER_TYPE)
    Observable<HttpResponse<CompMemTypeDetailBean>> doQueryByGroup(@Query("userId") String userId,
                                                                   @Query("array") String array);


    //根据用户id判断该品牌下的所有店铺是否都设置了收款账号
    @GET(HttpUrls.MEMBER_QIYNOG)
    Observable<HttpResponse<String>> isReceivingAccount(@Query("userId") String userId);
}
