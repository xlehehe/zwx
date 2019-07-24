package com.zwx.scan.app.data.http.service;

import android.content.Context;

import com.zwx.scan.app.data.bean.Brand;
import com.zwx.scan.app.data.bean.CampaignCount;
import com.zwx.scan.app.data.bean.CampaignGroupBuy;
import com.zwx.scan.app.data.bean.GroupBuyCampagin;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.ResultBean;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.StoreResult;
import com.zwx.scan.app.data.http.HttpUrls;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * author : lizhilong
 * time   : 2019/01/11
 * desc   :
 * version: 1.0
 **/
public interface CateringInfoService {

 /*   @FormUrlEncoded
    @POST(HttpUrls.DO_CATERING_LIST_URL)
    Observable<HttpResponse<List<Store>>> doQuery(@Field("brandId")String brandId, @Field("pageNumber")Integer pageNumber,@Field("pageSize")Integer pageSize);*/

   /* @FormUrlEncoded
    @GET(HttpUrls.DO_CATERING_BRAND_URL)
    Observable<HttpResponse<Brand>> doQueryByCurrentUser(@Field("userId")String userId);*/

    @GET(HttpUrls.DO_CATERING_LIST_URL)
    Observable<HttpResponse<StoreResult>> doQuery(@Query("brandId") String brandId, @Query("pageNumber")Integer pageNumber, @Query("pageSize")Integer pageSize);

    @GET(HttpUrls.DO_CATERING_BRAND_URL)
    Observable<HttpResponse<List<Brand>>> doQueryByCurrentUser(@Query("userId")String userId);


    @Multipart
    @POST(HttpUrls.UPLOAD_AVATAR_URL)
    Observable<HttpResponse<Map<String,Object>>> upload(@Part MultipartBody.Part MultipartFile);

//    @Multipart
    @POST(HttpUrls.UPLOAD_AVATAR_URL)
    Observable<HttpResponse<Map<String,Object>>> uploadFiles(@Body RequestBody body);

    @FormUrlEncoded
    @POST(HttpUrls.DELETE_AVATAR_URL)
    Observable<HttpResponse<String>> delete(@Field("id") String id);
    /**
     * 保存
     * */
    @FormUrlEncoded
    @POST(HttpUrls.SAVE_BRAND_URL)
    Observable<HttpResponse<String>> saveBrand(@Field("jsonStr") String jsonStr);


    @GET(HttpUrls.CATE_SELECT_STORE_URL)
    Observable<HttpResponse<Store>> doLoad(@Query("id") String id);


    @GET(HttpUrls.CATE_QR_CODE_URL)
    Observable<HttpResponse<String>> createQrCode(@Query("storeId") String storeId);

    @FormUrlEncoded
    @POST(HttpUrls.CATE_SAVE_STORE_URL)
    Observable<HttpResponse<String>> saveStore(@Field("obj") String obj);




}
