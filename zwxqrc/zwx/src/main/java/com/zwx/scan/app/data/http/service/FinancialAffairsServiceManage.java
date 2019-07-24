package com.zwx.scan.app.data.http.service;

import android.content.Context;

import com.zwx.scan.app.data.base.BaseServiceManager;
import com.zwx.scan.app.data.bean.CollectionToAccountOrderDetailBean;
import com.zwx.scan.app.data.bean.CompWxpayTransfer;
import com.zwx.scan.app.data.bean.CompWxpayTransferBean;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.MemberConsumeBean;
import com.zwx.scan.app.data.bean.OrderObjectBean;
import com.zwx.scan.app.data.bean.TOrder;
import com.zwx.scan.app.data.bean.TOrderBean;
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.data.bean.TransferRecordBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Query;

/**
 * author : lizhilong
 * time   : 2019/05/24
 * desc   : 财务中心
 * version: 1.0
 **/
public class FinancialAffairsServiceManage extends BaseServiceManager {

    private Context context;

    private FinancialAffairsService financialAffairsService;


    public FinancialAffairsServiceManage(){
        financialAffairsService = RetrofitServiceManager.getInstance().create(FinancialAffairsService.class);
    }


    /**
     *  收款账号设置状态查询
     * @param userId
     * @return
     */

    public Observable<HttpResponse<Map<String,String>>> loadConfigByCompId(String userId){
        return  observe(financialAffairsService.loadConfigByCompId(userId));
    }
    //检查手机号和用户姓名
    public Observable<HttpResponse<String>> checkWxPayInfo(String brandId,String tel,String memberName){
        return  observe(financialAffairsService.checkWxPayInfo(brandId,tel,memberName));
    }


    public Observable<HttpResponse<CompWxpayTransfer>> loadWxPayRecord(String id){
        return  observe(financialAffairsService.loadWxPayRecord(id));
    }


    public Observable<HttpResponse<TransferRecordBean>> queryOrderDetailForwxPay(String orderId){
        return  observe(financialAffairsService.queryOrderDetailForwxPay(orderId));
    }

    //收款到账详情
    public Observable<HttpResponse<CollectionToAccountOrderDetailBean>> queryOrderListDetailForwxPay(String orderId){
        return  observe(financialAffairsService.queryOrderListDetailForwxPay(orderId));
    }


    public Observable<HttpResponse<TOrderObject>> queryWxPayInfo(String userId){
        return  observe(financialAffairsService.queryWxPayInfo(userId));
    }

    public Observable<HttpResponse<OrderObjectBean>> queryWxPayList(String userId, String flag,Integer pageNumber, Integer pageSize){
        return  observe(financialAffairsService.queryWxPayList(userId,flag,pageNumber,pageSize));
    }

    public Observable<HttpResponse<CompWxpayTransferBean>> queryWxPayRecord(String userId, String startTime, String endTime, Integer pageNumber, Integer pageSize){
        return  observe(financialAffairsService.queryWxPayRecord(userId,startTime,endTime,pageNumber,pageSize));
    }
    public Observable<HttpResponse<String>> saveCompWxpayConfig(String jsonstr){
        return  observe(financialAffairsService.saveCompWxpayConfig(jsonstr));
    }



    //会员卡员工销售报表汇总
    public Observable<HttpResponse<List<TOrder>>> selectSalesByEmployee(String userId, String salesTimeSta, String salesTimesEnd,
                                                                        String storeId, String storeStr, String storeSelectType, String orderType, Integer pageNumber, Integer pageSize){
        return  observe(financialAffairsService.selectSalesByEmployee(userId,salesTimeSta,salesTimesEnd,storeId,storeStr,storeSelectType,orderType,pageNumber,pageSize));
    }
    //具体详情
    public Observable<HttpResponse<List<TOrder>>> selectSalesDetailsByEmployee(String userId, String salesTimeSta, String salesTimesEnd,
                                                                String storeId, String storeStr, String storeSelectType, String orderType,
                                                                         String productId, String staffId,
                                                                         Integer pageNumber, Integer pageSize){
        return  observe(financialAffairsService.selectSalesDetailsByEmployee(userId,salesTimeSta,salesTimesEnd,storeId,storeStr,storeSelectType,orderType,productId,staffId,pageNumber,pageSize));
    }
    //详情

    public Observable<HttpResponse<List<TOrder>>> selectSalesListByEmployee(String userId, String salesTimeSta, String salesTimesEnd,
                                                                         String storeId, String storeStr, String storeSelectType, String orderType,
                                                                         String productIds,String staffTel,String sort,
                                                                         Integer pageNumber, Integer pageSize){
        return  observe(financialAffairsService.selectSalesListByEmployee(userId,salesTimeSta,salesTimesEnd,storeId,storeStr,storeSelectType,orderType,productIds,staffTel,sort,pageNumber,pageSize));
    }



}
