package com.zwx.scan.app.data.http.service;

import com.zwx.scan.app.data.bean.Contract;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.ReceiveFund;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.http.HttpUrls;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author : lizhilong
 * time   : 2019/01/28
 * desc   :
 * version: 1.0
 **/
public interface FinanceService {



    @FormUrlEncoded
    @POST(HttpUrls.RECEIVEFUND_LIST_URL)
    Observable<HttpResponse<List<ReceiveFund>>> doQueryAllByStatus(@Field("storeId")String storeId,@Field("pageNumber")String pageNumber,@Field("pageSize")String pageSize,@Field("status")String status);

    @FormUrlEncoded
    @POST(HttpUrls.RECEIVEFUND_DETAIL_URL)
    Observable<HttpResponse<ReceiveFund>> doQueryByFundId(@Field("fundId")String fundId);



    @FormUrlEncoded
    @POST(HttpUrls.QUERY_STORE_URL)
    Observable<HttpResponse<List<Store>>> queryStores(@Field("userId") String userId);


    @FormUrlEncoded
    @POST(HttpUrls.DO_QUERY_MONEY_TN_URL)
    Observable<HttpResponse<Map<String,Object>>> doQueryMoneyByIds(@Field("userId") String userId,@Field("storeId") String storeId,@Field("ids") String ids);
}
