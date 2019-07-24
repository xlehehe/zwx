package com.zwx.scan.app.data.http.service;

import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.Index;
import com.zwx.scan.app.data.bean.TResource;
import com.zwx.scan.app.data.http.HttpUrls;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author : lizhilong
 * time   : 2018/11/26
 * desc   :
 * version: 1.0
 **/
public interface HomeService {



    /***
     * 资源信息
     * @param  userId
     * @return Type Observable<HttpResponse>
     * */
    @FormUrlEncoded
    @POST(HttpUrls.QUERY_RESOURCE_URL)
    Observable<HttpResponse<List<TResource>>> queryReource(@Field("userId") String userId);

    /***
     * 首页报表信息
     * @param  storeId
     * @return Type Observable<HttpResponse>
     * */
    @FormUrlEncoded
    @POST(HttpUrls.INDEX_URL)
    Observable<HttpResponse<Index>> index(@Field("storeId") String storeId);




    @FormUrlEncoded
    @POST(HttpUrls.QUERY_RESOURCE_URL)
    Observable<HttpResponse<Index>> resources(@Field("userId") String userId);




}
