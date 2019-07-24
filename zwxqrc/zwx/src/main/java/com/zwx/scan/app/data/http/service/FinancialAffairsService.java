package com.zwx.scan.app.data.http.service;

import android.content.Intent;

import com.zwx.scan.app.data.bean.CollectionToAccountOrderDetailBean;
import com.zwx.scan.app.data.bean.CompWxpayTransfer;
import com.zwx.scan.app.data.bean.CompWxpayTransferBean;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.OrderObjectBean;
import com.zwx.scan.app.data.bean.TOrder;
import com.zwx.scan.app.data.bean.TOrderBean;
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.data.bean.TradeDrawingSelectResultBean;
import com.zwx.scan.app.data.bean.TransferRecordBean;
import com.zwx.scan.app.data.http.HttpUrls;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * author : lizhilong
 * time   : 2019/05/24
 * desc   :
 * version: 1.0
 **/
public interface FinancialAffairsService {



    /**
     * 收款账号设置状态查询
     * @param userId
     * */
    @POST(HttpUrls.SELECT_COLLECTION_ACCOUNT_SETTTING_STATUS)
    Observable<HttpResponse<Map<String,String>>> loadConfigByCompId(@Query("userId") String userId);

    //检查手机号或名称
    @POST(HttpUrls.SELECT_COLLECTION_ACCOUNT_SETTTING_CHECK)
    Observable<HttpResponse<String>> checkWxPayInfo(@Query("brandId") String brandId,@Query("tel") String tel,@Query("memberName") String memberName);

    @POST(HttpUrls.SELECT_COLLECTION_ACCOUNT_PAY_RECORD)
    Observable<HttpResponse<CompWxpayTransfer>> loadWxPayRecord(@Query("id") String id);

    //转账记录详情
    @POST(HttpUrls.SELECT_COLLECTION_ACCOUNT_PAY_RECORD_DETAIL)
    Observable<HttpResponse<TransferRecordBean>> queryOrderDetailForwxPay(@Query("orderId") String orderId);

    @POST(HttpUrls.SELECT_COLLECTION_TO_ACCOUNT_LIST_DETAIL)
    Observable<HttpResponse<CollectionToAccountOrderDetailBean>> queryOrderListDetailForwxPay(@Query("orderId") String orderId);
    //收款账号金额汇总
    @POST(HttpUrls.SELECT_COLLECTION_ACCOUNT_PAY_INFO)
    Observable<HttpResponse<TOrderObject>> queryWxPayInfo(@Query("userId") String userId);

    //收款账号列表
    @POST(HttpUrls.SELECT_COLLECTION_ACCOUNT_PAY_LIST)
    Observable<HttpResponse<OrderObjectBean>> queryWxPayList(@Query("userId") String userId, @Query("flag") String flag, @Query("pageNumber") Integer pageNumber, @Query("pageSize") Integer pageSize);

    //转账记录
    @POST(HttpUrls.SELECT_COLLECTION_ACCOUNT_WX_PAY_RECORD)
    Observable<HttpResponse<CompWxpayTransferBean>> queryWxPayRecord(@Query("userId") String userId, @Query("startTime") String startTime, @Query("endTime") String endTime
            , @Query("pageNumber") Integer pageNumber, @Query("pageSize") Integer pageSize);

    @POST(HttpUrls.SELECT_COLLECTION_ACCOUNT_WX_PAY_CONFIG)
    Observable<HttpResponse<String>> saveCompWxpayConfig(@Query("jsonstr") String jsonstr);



    //收款账号列表
    @GET(HttpUrls.SELECT_EMPLOYEE_SALES_REPORT_LIST)
    Observable<HttpResponse<List<TOrder>>> selectSalesByEmployee(@Query("userId") String userId, @Query("salesTimeSta") String salesTimeSta, @Query("salesTimesEnd") String salesTimesEnd,
                                                                 @Query("storeId") String storeId, @Query("storeStr") String storeStr, @Query("storeSelectType") String storeSelectType, @Query("orderType") String orderType,
                                                                 @Query("pageNumber") Integer pageNumber, @Query("pageSize") Integer pageSize);

    //详情
    @GET(HttpUrls.SELECT_EMPLOYEE_SALES_REPORT_LIST_DETAIL)
    Observable<HttpResponse<List<TOrder>>> selectSalesListByEmployee(@Query("userId") String userId,  @Query("salesTimeSta") String salesTimeSta, @Query("salesTimesEnd") String salesTimesEnd,
                                                               @Query("storeId") String storeId,@Query("storeStr") String storeStr,@Query("storeSelectType") String storeSelectType,@Query("orderType") String orderType,
                                                               @Query("productIds") String productIds,@Query("staffTel") String staffTel,@Query("sort") String sort,
                                                               @Query("pageNumber") Integer pageNumber, @Query("pageSize") Integer pageSize);

    //具体详情
    @GET(HttpUrls.SELECT_EMPLOYEE_SALES_REPORT_DETAIL)
    Observable<HttpResponse<List<TOrder>>> selectSalesDetailsByEmployee(@Query("userId") String userId,  @Query("salesTimeSta") String salesTimeSta, @Query("salesTimesEnd") String salesTimesEnd,
                                                                              @Query("storeId") String storeId,@Query("storeStr") String storeStr,@Query("storeSelectType") String storeSelectType,@Query("orderType") String orderType,
                                                                              @Query("productId") String productId,@Query("staffId") String staffId,
                                                                              @Query("pageNumber") Integer pageNumber, @Query("pageSize") Integer pageSize);

}
