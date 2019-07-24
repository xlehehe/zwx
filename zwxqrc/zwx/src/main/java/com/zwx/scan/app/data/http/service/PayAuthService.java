package com.zwx.scan.app.data.http.service;

import com.zwx.scan.app.data.bean.CollectionFlowDetailResultBean;
import com.zwx.scan.app.data.bean.CollectionFlowResultBean;
import com.zwx.scan.app.data.bean.CompIdentityResultInfoBean;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.PayAuthInfoResultBean;
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.data.bean.TradeDrawingRecordResultBean;
import com.zwx.scan.app.data.bean.TradeDrawingSelectResultBean;
import com.zwx.scan.app.data.http.HttpUrls;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * author : lizhilong
 * time   : 2019/05/15
 * desc   :
 * version: 1.0
 **/
public interface PayAuthService {


    @GET(HttpUrls.SELECT_PAY_AUTH_STATUS)
    Observable<HttpResponse<Map<String,Object>>> selectPaymentAuthStatus(@Query("userId") String userId);

    @GET(HttpUrls.SELECT_PAY_AUTH_INFO)
    Observable<HttpResponse<Map<String,String>>> selectPaymentAuthInfo(@Query("userId") String userId);

    @GET(HttpUrls.SELECT_PAY_AUTH_PERSONAL_OR_COMPANY_INFO)
    Observable<HttpResponse<PayAuthInfoResultBean>> selectPersonalOrCompanyAuthInfo(@Query("userId") String userId);
    @GET(HttpUrls.SELECT_PAY_AUTH_PERSONAL_OR_COMPANY_INFO)
    Observable<HttpResponse<CompIdentityResultInfoBean>> selectCompanyAuthInfo(@Query("userId") String userId);

    //个人认证第一步身份校验
    @POST(HttpUrls.SELECT_PAY_AUTH_PERSONAL_INFO)
    Observable<HttpResponse<Map<String,String>>> setPersonalAuthInfo(@Query("userId") String userId,@Query("name") String name,@Query("identityNo") String identityNo);

    //绑定手机发送验证码
    @POST(HttpUrls.SELECT_PAY_AUTH_SEND_VERIFICATION_CODE)
    Observable<HttpResponse<Map<String,String>>> sendVerificationCodeByBindPhone(@Query("userId") String userId,@Query("phone") String phone);
    //解绑手机发送验证码
    @POST(HttpUrls.SELECT_PAY_AUTH_SEND_VERIFICATION_CODE_UN)
    Observable<HttpResponse<Map<String,String>>> sendVerificationCodeByUnBindPhone(@Query("userId") String userId,@Query("phone") String phone);

    @POST(HttpUrls.SELECT_PAY_AUTH_BIND_PHONE)
    Observable<HttpResponse<Map<String,String>>> bindPhone(@Query("userId") String userId,@Query("managerName") String managerName,
                                                           @Query("phone") String phone,@Query("verificationCode") String verificationCode);
    @POST(HttpUrls.SELECT_PAY_AUTH_UN_BIND_PHONE)
    Observable<HttpResponse<Map<String,String>>> unBindPhone(@Query("userId") String userId,
                                                           @Query("phone") String phone,@Query("verificationCode") String verificationCode);

    @POST(HttpUrls.SELECT_PAY_AUTH_UN_BIND_BANK_CARD)
    Observable<HttpResponse<Map<String,String>>> unbindBankCard(@Query("userId") String userId,
                                                             @Query("cardNo") String cardNo);


    @POST(HttpUrls.SELECT_PAY_AUTH_UN_BIND_PHONE)
    Observable<HttpResponse<Map<String,String>>> unbindPhone(@Query("userId") String userId,
                                                           @Query("phone") String phone,@Query("verificationCode") String verificationCode);
    //创建通联会员
    @POST(HttpUrls.SELECT_PAY_AUTH_CREATE_MEMBER)
    Observable<HttpResponse<Map<String,String>>> createMember(@Query("userId") String userId,@Query("memberType") String memberType);
    ///api/tradeWithdrawalController/createMember.do


    @POST(HttpUrls.SELECT_PAY_AUTH_BIND_BANK_CARD)
    Observable<HttpResponse<Map<String,String>>> bindBankCard(@Query("userId") String userId,@Query("cardNo") String cardNo,@Query("name") String name,
                                                              @Query("identityNo") String identityNo,@Query("bankName") String bankName,@Query("phone") String phone);


    //签订协议HTML地址查询

    @POST(HttpUrls.SELECT_PAY_AUTH_SIGN_CONTRACT_NOTICE)
    Observable<HttpResponse<Map<String,String>>> signContractNotice(@Query("userId") String userId);


    /***
     *
     *    protected String uniCredit ;//统一社会信用代码
     protected String legalName ;//法人姓名
     protected String legalPhone ;//法人手机号

     protected String legalIds ;//法人身份证
     protected String legalIdcard ;//身份证正面
     protected String legalIdcardBack ;//身份证反面
     protected String accountNo ;//对公账户
     protected String parentBankName ;//开户行名称
     protected String province ;//开户行所在省

     protected String city ;//city开户行所在市
     */
    @POST(HttpUrls.SELECT_PAY_AUTH_COMPANY_AUTH_INFO)
    Observable<HttpResponse<Map<String,String>>> setCompanyAuthInfo(@Query("userId") String userId,@Query("companyName")String companyName,@Query("businessLicenseImg")String businessLicenseImg,@Query("uniCredit") String uniCredit,@Query("legalName") String legalName,
                                                              @Query("legalPhone") String legalPhone,@Query("legalIds") String legalIds,@Query("legalIdcard") String legalIdcard,
                                                                    @Query("legalIdcardBack") String legalIdcardBack,@Query("accountNo") String accountNo,@Query("parentBankName") String parentBankName,
                                                                    @Query("province") String province,@Query("city") String city);


    /**
     * 交易提款 以下接口
     * */

    //交易提现
    @GET(HttpUrls.SELECT_TRADE_DRAWAL)
    Observable<HttpResponse<Map<String,String>>> selectTradeWithdrawal(@Query("userId") String userId);

    /**
     *提现详情
     * */
    @GET(HttpUrls.SELECT_TRADE_DRAWAL_DETAIL)
    Observable<HttpResponse<Map<String,String>>> selectTradeWithdrawalDetails(@Query("userId") String userId,@Query("withdrawId") String withdrawId);

    /**
     *提现记录
     * */
    @GET(HttpUrls.SELECT_TRADE_DRAWAL_RECORD)
    Observable<HttpResponse<TradeDrawingRecordResultBean>> selectTradeWithdrawalList(@Query("userId") String userId, @Query("withdrawTimeSta") String withdrawTimeSta,
                                                                                     @Query("withdrawTimesEnd")String  withdrawTimesEnd ,
                                                                                     @Query("pageNumber") Integer pageNumber, @Query("pageSize") Integer pageSize);




    /**
     * 立即提现查询
     * @param userId
     * */
    @GET(HttpUrls.SELECT_IMMEDIATE_TRADE_DRAWAL)
    Observable<HttpResponse<TradeDrawingSelectResultBean>> selectImmediateWithdrawal(@Query("userId") String userId);
    /**
     * 立即体现
     * @param userId
     * @param bizOrderNo   商户订单号（提现订单）
     * @param  validateType  手机验证码
     * @param consumerIp 用户公网IP
     * */
    @POST(HttpUrls.IMMEDIATE_DRAWAL)
    Observable<HttpResponse<Map<String,String>>> immediateWithdrawal(@Query("userId") String userId,
                                                                                           @Query("bizOrderNo") String bizOrderNo,@Query("validateType") String validateType,@Query("consumerIp") String consumerIp);

    /**
     * 提现验证码发送
     * @param userId
     * @param amount   提现金额
     * @param  fee  手续费
     * @param bankCardNo 银行账户号
     * @param bankCardPro 账户类型: 0个人银行卡 ,1企业对公账户
     * */
    @POST(HttpUrls.SEND_TRADE_DRAWAL_CODE)
    Observable<HttpResponse<Map<String,String>>> sendVerificationCodeByImmediateWithdrawal(@Query("userId") String userId,
                                                                                           @Query("amount") String amount,@Query("fee") String fee,@Query("bankCardNo") String bankCardNo,@Query("bankCardPro") String bankCardPro);



    /**
     *待结算收款流水
     * */
    @GET(HttpUrls.SELECT_COLLECTION_STATEMENT_LIST)
    Observable<HttpResponse<CollectionFlowResultBean>> selectUnSettlementCollectionStatementList(@Query("userId") String userId,
                                                                                                 @Query("pageNumber") Integer pageNumber, @Query("pageSize") Integer pageSize);


    @GET(HttpUrls.SELECT_COLLECTION_STATEMENT_LIST_DETAIL)
    Observable<HttpResponse<CollectionFlowDetailResultBean>> selectCollectionStatementDetailsByType(@Query("userId") String userId,
                                                                                                    @Query("orderId") String orderId);



    //收款账号设置



}
