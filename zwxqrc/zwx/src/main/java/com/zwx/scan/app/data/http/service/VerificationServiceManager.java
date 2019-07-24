package com.zwx.scan.app.data.http.service;

import android.content.Context;

import com.zwx.scan.app.data.base.BaseServiceManager;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.MemCoupon;
import com.zwx.scan.app.data.bean.ResultBean;
import com.zwx.scan.app.data.bean.VerificationRecord;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author lizhilong
 * @desc
 * @time 2018/10/16
 * @version 1.0
 */
public class VerificationServiceManager extends BaseServiceManager {

    private Context context;

    private VerificationService scanService;


    public VerificationServiceManager(){
        scanService = RetrofitServiceManager.getInstance().create(VerificationService.class);
    }

    /**
     *
     * storeId=3&memberId=1&memberType=1&consumeAmt=111&personQnt=2
     * */

    public  Observable<HttpResponse<ResultBean>> appVerificationMemberType(String storeId, String memberId, String memberType, String consumeAmt , String personQnt){
        return  observe(scanService.appVerificationMemberType( storeId,  memberId, memberType, consumeAmt , personQnt));
    }


    //优惠券核销
    public  Observable<HttpResponse<String>> appVerificationMemCoupon(String userId, String storeId,String compMemberTypeId, String couponIdArray, String memberId, String consumeAmt , String personQnt){
        return  observe(scanService.appVerificationMemCoupon( userId,storeId,compMemberTypeId, couponIdArray, memberId, consumeAmt , personQnt));
    }

    //商品核销
    public  Observable<HttpResponse<String>> verificationProduct(String storeId,String memberId,String compMemId,String orderCouponId,String compMemberTypeId ){
        return  observe(scanService.verificationProduct(storeId,memberId,compMemId,orderCouponId,compMemberTypeId));
    }

    public  Observable<HttpResponse<String>> verificationPlatProduct(String storeId,String memberId,String compMemId,String orderCouponId,String compMemberTypeId,String type ){
        return  observe(scanService.verificationPlatProduct(storeId,memberId,compMemId,orderCouponId,compMemberTypeId,type));
    }

    public  Observable<HttpResponse<VerificationRecord>> queryVerificationRecord(String storeId, String compId, String date){
        return  observe(scanService.queryVerificationRecord( storeId,  compId,  date));
    }

    public  Observable<HttpResponse<List<MemCoupon>>> queryVerificationList(String storeId,String compId,String date){
        return  observe(scanService.queryVerificationList( storeId,compId,date));
    }
    public  Observable<HttpResponse<List<MemCoupon>>> queryVerificationDetail(String storeId,int pageSize, int pageNumber,String date){
        return  observe(scanService.queryVerificationDetail(storeId,pageSize,pageNumber,date));
    }

    public  Observable<HttpResponse<ResultBean>> checkIsBrandMember(String storeId,String memberId){
        return  observe(scanService.checkIsBrandMember(storeId,memberId));
    }

}
