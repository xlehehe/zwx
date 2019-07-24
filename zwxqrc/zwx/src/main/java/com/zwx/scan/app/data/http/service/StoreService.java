package com.zwx.scan.app.data.http.service;

import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.TResource;
import com.zwx.scan.app.data.bean.User;
import com.zwx.scan.app.data.http.HttpUrls;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author : lizhilong
 * time   : 2018/11/22
 * desc   :
 * version: 1.0
 **/
public interface StoreService {

    /***
     * 店铺信息
     * @param  userId
     * @return Type Observable<HttpResponse>
     * */
    @FormUrlEncoded
    @POST(HttpUrls.QUERY_STORE_URL)
    Observable<HttpResponse<List<Store>>> queryStores(@Field("userId") String userId);


}
