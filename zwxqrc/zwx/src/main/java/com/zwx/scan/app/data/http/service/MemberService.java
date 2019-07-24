package com.zwx.scan.app.data.http.service;

import com.zwx.scan.app.data.bean.CompMember;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.MemCoupon;
import com.zwx.scan.app.data.bean.MemberCount;
import com.zwx.scan.app.data.bean.MemberCountBean;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.http.HttpUrls;

import java.lang.reflect.Member;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
public interface MemberService {


    @FormUrlEncoded
    @POST(HttpUrls.MEMBER_ANALYSIS_URL)
    Observable<HttpResponse<MemberCount>> queryMemberCount(@Field("storeId")String storeId, @Field("date")String date);


    @FormUrlEncoded
    @POST(HttpUrls.MEMBER_LIST_DETAIL_URL)
    Observable<HttpResponse<MemberCount>>  queryMemTypeCountListDetail(@Field("storeId")String storeId, @Field("compMemTypeId") String compMemTypeId, @Field("date")String date);


    @FormUrlEncoded
    @POST(HttpUrls.MEMBER_LIST_URL)
    Observable<HttpResponse<List<CompMember>>> queryMemTypeCountList(@Field("storeId")String storeId,@Field("date")String date);

    @FormUrlEncoded
    @POST(HttpUrls.MORE_STORE_URL)
    Observable<HttpResponse<MoreStoreBean>> queryBrandAndStoreList(@Field("userId") String userId);


    @FormUrlEncoded
    @POST(HttpUrls.MORE_STORE_MEMBER_ANALYSIS_URL)
    Observable<HttpResponse<MemberCountBean>> queryMoreStoreMemberCountAnalysis(@Field("storeId")String storeId, @Field("date")String date);


    @FormUrlEncoded
    @POST(HttpUrls.MORE_STORE_MEMBER_LIST_DETAIL_URL)
    Observable<HttpResponse<MemberCountBean>>  queryMoreStoreMemTypeCountListDetail(@Field("storeId")String storeId, @Field("compMemTypeId") String compMemTypeId, @Field("date")String date);


    @FormUrlEncoded
    @POST(HttpUrls.MORE_STORE_MEMBER_LIST_URL)
    Observable<HttpResponse<List<CompMember>>> queryMorestoreMemTypeCountList(@Field("storeId")String storeId,@Field("date")String date);
}
