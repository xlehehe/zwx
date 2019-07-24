package com.zwx.scan.app.feature.financialaffairs;

import android.content.Context;

import com.zwx.scan.app.base.BasePresenter;
import com.zwx.scan.app.base.BaseView;

import java.io.File;
import java.util.List;
import java.util.Map;

import retrofit2.http.Query;

/**
 * author : lizhilong
 * time   : 2019/05/09
 * desc   :  财务中心 接口
 * version: 1.0
 **/
public interface FinancialAffairsContract {

    interface  View extends BaseView<FinancialAffairsContract.Presenter> {

    }

    interface  Presenter extends BasePresenter {

   /*     //订单列表
        void doQueryOrder(Context context, String userId, String memebrTel, String salesTimeSta, String salesTimesEnd, Integer pageNumber, Integer pageSize, Boolean isfresh);

        //订单详情
        void doQueryOrderDetails(Context context, String detailedId);*/

        //交易提款
        void selectTradeWithdrawal(Context context,String userId);

        //交易提款详情
        void selectTradeWithdrawalDetails(String userId,String withdrawId);

        //支付认证状态
        void selectPaymentAuthStatus(Context context,String userId);

        //立即提款查询
        void selectImmediateWithdrawal(Context context,String userId);

        //提现记录
        void selectTradeWithdrawalList(Context context,String userId, String withdrawTimeSta,
                                  String  withdrawTimesEnd ,
                                  Integer pageNumber, Integer pageSize);


        //提现详情
        void selectTradeWithdrawalDetails(Context context,String userId,String withdrawId);

        //立即提现
        void immediateWithdrawal(Context context,String  userId,
                            String bizOrderNo,String validateType,String consumerIp);
        //发送验证码
        void sendVerificationCodeByImmediateWithdrawal(Context context,String  userId,
                                                       String amount,String fee,String bankCardNo,String bankCardPro);


        //代收款流水
        void selectUnSettlementCollectionStatementList(Context context,String userId,
                                                       Integer pageNumber, Integer pageSize);
        void selectCollectionStatementDetailsByType(Context context,String userId,
                                               String orderId);


        //收款账号设置
        //查询设置状态
        void loadConfigByCompId(Context context,String userId);


        //检查手机号和用户姓名
        void checkWxPayInfo(Context context,String brandId,String tel,String memberName);

        void loadWxPayRecord(Context context,String id);

        void queryOrderDetailForwxPay(Context context,String orderId);

        void queryWxPayInfo(Context context,String userId);

        void queryWxPayList(Context context,String userId,String flag,Integer pageNumber,Integer pageSize);
        void  queryWxPayRecord(Context context, String userId, String startTime,String endTime, Integer pageNumber, Integer pageSize);

        void saveCompWxpayConfig(Context context,String jsonstr);
        //会员卡员工报表销售
        void queryStoreList(Context context, String userId);


       void selectSalesByEmployee(Context context, String userId, String salesTimeSta, String salesTimesEnd,
                              String storeId, String storeStr, String storeSelectType, String orderType, Integer pageNumber, Integer pageSize);
       void selectSalesDetailsByEmployee(Context context,String userId, String salesTimeSta, String salesTimesEnd,
                                         String storeId, String storeStr, String storeSelectType, String orderType,
                                         String productId, String staffId,
                                         Integer pageNumber, Integer pageSize);
       void selectSalesListByEmployee(Context context,String userId, String salesTimeSta, String salesTimesEnd,
                                      String storeId, String storeStr, String storeSelectType, String orderType,
                                      String productIds,String staffTel,String sort,
                                      Integer pageNumber, Integer pageSize);

        /**
         *
         public Observable<HttpResponse<Map<String,String>>> queryOrderDetailForwxPay(String orderId){
         return  observe(financialAffairsService.queryOrderDetailForwxPay(orderId));
         }


         public Observable<HttpResponse<Map<String,String>>> queryWxPayInfo(String userId,String flag){
         return  observe(financialAffairsService.queryWxPayInfo(userId,flag));
         }

         public Observable<HttpResponse<Map<String,String>>> queryWxPayList(String userId,String flag){
         return  observe(financialAffairsService.queryWxPayList(userId,flag));
         }

         public Observable<HttpResponse<Map<String,String>>> queryWxPayRecord(String userId,Integer pageNumber,Integer pageSize){
         return  observe(financialAffairsService.queryWxPayRecord(userId,pageNumber,pageSize));
         }
         public Observable<HttpResponse<Map<String,String>>> saveCompWxpayConfig(String jsonstr){
         return  observe(financialAffairsService.saveCompWxpayConfig(jsonstr));
         }

         *
         *
         * */




    }
}
