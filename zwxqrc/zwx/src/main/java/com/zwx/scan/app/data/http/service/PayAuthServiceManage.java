package com.zwx.scan.app.data.http.service;

import android.content.Context;
import android.content.Intent;

import com.zwx.scan.app.data.base.BaseServiceManager;
import com.zwx.scan.app.data.bean.CollectionFlowDetailResultBean;
import com.zwx.scan.app.data.bean.CollectionFlowResultBean;
import com.zwx.scan.app.data.bean.CompIdentityResultInfoBean;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.PayAuthInfoResultBean;
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.data.bean.TradeDrawingRecordResultBean;
import com.zwx.scan.app.data.bean.TradeDrawingSelectResultBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Query;

/**
 * author : lizhilong
 * time   : 2019/05/15
 * desc   : 支付认证
 * version: 1.0
 **/
public class PayAuthServiceManage extends BaseServiceManager {


    private Context context;

    private PayAuthService payAuthService;


    public PayAuthServiceManage(){
        payAuthService = RetrofitServiceManager.getInstance().create(PayAuthService.class);
    }



    //订单详情
    public Observable<HttpResponse<Map<String,Object>>> selectPaymentAuthStatus(String userId){
        return  observe(payAuthService.selectPaymentAuthStatus(userId));
    }


    public Observable<HttpResponse<Map<String,String>>> selectPaymentAuthInfo(String userId){
        return  observe(payAuthService.selectPaymentAuthInfo(userId));
    }


    //个人或企业认证信息查询
    public Observable<HttpResponse<PayAuthInfoResultBean>> selectPersonalOrCompanyAuthInfo(String userId){
        return  observe(payAuthService.selectPersonalOrCompanyAuthInfo(userId));
    }
    public Observable<HttpResponse<CompIdentityResultInfoBean>> selectCompanyAuthInfo(String userId){
        return  observe(payAuthService.selectCompanyAuthInfo(userId));
    }
    //个人认证第一步
    public Observable<HttpResponse<Map<String,String>>> setPersonalAuthInfo(String userId,String name,String identityNo){
        return  observe(payAuthService.setPersonalAuthInfo(userId,name,identityNo));
    }

    //绑定手机发送验证码
    public Observable<HttpResponse<Map<String,String>>> sendVerificationCodeByBindPhone(String userId,String phone){
        return  observe(payAuthService.sendVerificationCodeByBindPhone(userId,phone));
    }
    //解绑手机发送验证码
    public Observable<HttpResponse<Map<String,String>>> sendVerificationCodeByUnBindPhone(String userId,String phone){
        return  observe(payAuthService.sendVerificationCodeByUnBindPhone(userId,phone));
    }
    public Observable<HttpResponse<Map<String,String>>> bindPhone(String userId,String managerName,String phone,String verificationCode){
        return  observe(payAuthService.bindPhone(userId,managerName,phone,verificationCode));
    }

    public Observable<HttpResponse<Map<String,String>>> unBindPhone(String userId,String phone,String verificationCode){
        return  observe(payAuthService.unBindPhone(userId,phone,verificationCode));
    }

    //解绑银行卡
    public Observable<HttpResponse<Map<String,String>>> unbindBankCard(String userId,String cardNo){
        return  observe(payAuthService.unbindBankCard(userId,cardNo));
    }


    public Observable<HttpResponse<Map<String,String>>> createMember(String userId,String memberType){
        return  observe(payAuthService.createMember(userId,memberType));
    }

    //个人认证绑定银行卡
    public Observable<HttpResponse<Map<String,String>>> bindBankCard(String userId, String cardNo, String name,
                                                                          String identityNo, String bankName, String phone){
        return  observe(payAuthService.bindBankCard(userId,cardNo,name,identityNo,bankName,phone));
    }


    //签订协议
    public Observable<HttpResponse<Map<String,String>>> signContractNotice(String userId){
        return  observe(payAuthService.signContractNotice(userId));
    }
    //设置企业认证信息
    public Observable<HttpResponse<Map<String,String>>> setCompanyAuthInfo(String userId,String companyName,String businessLicenseImg, String uniCredit, String legalName,
                                                                     String legalPhone,String legalIds,String legalIdcard,
                                                                     String legalIdcardBack,String accountNo,String parentBankName,
                                                                     String province, String city){
        return  observe(payAuthService.setCompanyAuthInfo(userId, companyName, businessLicenseImg,uniCredit,legalName,legalPhone,legalIds,legalIdcard,legalIdcardBack,accountNo,parentBankName,province,city));
    }

    //交易提款查询
    public Observable<HttpResponse<Map<String,String>>> selectTradeWithdrawal(String userId){
        return  observe(payAuthService.selectTradeWithdrawal(userId));
    }

    /**
     * 立即体现查询
     * */
    public Observable<HttpResponse<TradeDrawingSelectResultBean>> selectImmediateWithdrawal(String userId){
        return  observe(payAuthService.selectImmediateWithdrawal(userId));
    }

    /**
     *提现记录
     * */
    public Observable<HttpResponse<TradeDrawingRecordResultBean>> selectTradeWithdrawalList(String userId, String withdrawTimeSta,
                                                                                            String  withdrawTimesEnd ,
                                                                                            Integer pageNumber, Integer pageSize){
        return  observe(payAuthService.selectTradeWithdrawalList(userId,withdrawTimeSta,withdrawTimesEnd,pageNumber,pageSize));
    }



    /**
     *提现详情
     * */
    public Observable<HttpResponse<Map<String,String>>> selectTradeWithdrawalDetails(String userId,String withdrawId){
        return  observe(payAuthService.selectTradeWithdrawalDetails(userId,withdrawId));
    }

    /**
     * 立即提现
     * */
    public Observable<HttpResponse<Map<String,String>>> immediateWithdrawal(String  userId,
                                                                                      String bizOrderNo,String validateType,String consumerIp){
        return  observe(payAuthService.immediateWithdrawal(userId,bizOrderNo,validateType,consumerIp));
    }


    //提现验证码发送
    public Observable<HttpResponse<Map<String,String>>> sendVerificationCodeByImmediateWithdrawal(String  userId,
                                                                            String amount,String fee,String bankCardNo,String bankCardPro){
        return  observe(payAuthService.sendVerificationCodeByImmediateWithdrawal(userId,amount,fee,bankCardNo,bankCardPro));
    }


    /**
     *待结算收款流水
     * */
    public Observable<HttpResponse<CollectionFlowResultBean>> selectUnSettlementCollectionStatementList(String userId,
                                                                                                        Integer pageNumber, Integer pageSize){
        return  observe(payAuthService.selectUnSettlementCollectionStatementList(userId,pageNumber,pageSize));
    }

    /**
     *待结算收款流水详情
     * */
    public Observable<HttpResponse<CollectionFlowDetailResultBean>> selectCollectionStatementDetailsByType(String userId,
                                                                                                           String orderId){
        return  observe(payAuthService.selectCollectionStatementDetailsByType(userId,orderId));
    }


}
