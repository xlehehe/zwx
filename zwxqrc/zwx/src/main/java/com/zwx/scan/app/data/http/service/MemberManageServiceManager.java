package com.zwx.scan.app.data.http.service;

import android.content.Context;

import com.zwx.scan.app.data.base.BaseServiceManager;
import com.zwx.scan.app.data.bean.CompMemType;
import com.zwx.scan.app.data.bean.CompMemTypeDetailBean;
import com.zwx.scan.app.data.bean.CompMemberType;
import com.zwx.scan.app.data.bean.ComsumeLog;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.MemCoupon;
import com.zwx.scan.app.data.bean.MemTypeBean;
import com.zwx.scan.app.data.bean.MemberConsumeBean;
import com.zwx.scan.app.data.bean.MemberInfoBean;
import com.zwx.scan.app.data.bean.MemberInfoDetailBean;
import com.zwx.scan.app.data.bean.Order;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.TemplateBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;

/**
 * author : lizhilong
 * time   : 2018/12/12
 * desc   :
 * version: 1.0
 **/
public class MemberManageServiceManager extends BaseServiceManager{

    private Context context;

    private MemberManageService memberManageService;


    public MemberManageServiceManager(){
        memberManageService = RetrofitServiceManager.getInstance().create(MemberManageService.class);
    }


    /**
     *  会员消费记录
     * @param userId
     * @return
     */

    public Observable<HttpResponse<MemberConsumeBean>> comsumeListByMap(String userId, int pageNumber,int pageSize,String order, String desc){
        return  observe(memberManageService.comsumeListByMap(userId,pageNumber,pageSize,order,desc));
    }

    public Observable<HttpResponse<MemberConsumeBean>> queryComsumeList(String userId, int pageNumber,int pageSize,String order, String desc){
        return  observe(memberManageService.queryComsumeList(userId,pageNumber,pageSize,order,desc));
    }
    public Observable<HttpResponse<List<MemTypeBean>>> memberCardListByMap(String memberId){
        return  observe(memberManageService.memberCardListByMap(memberId));
    }



    public Observable<HttpResponse<MemberInfoBean>> memberListByMap(String memberTel,String userId,Integer pageNumber,Integer pageSize){
        return  observe(memberManageService.memberListByMap(memberTel,userId,pageNumber,pageSize));
    }

    public Observable<HttpResponse<MemberInfoDetailBean>> doGetMemberType(String memberId, String userId){
        return  observe(memberManageService.doGetMemberType(memberId,userId));


    }

    public Observable<HttpResponse<List<ComsumeLog>>> doQueryComsumeLog(int pageNumber, int pageSize, String compMemTypeId, String userId,String memberId){
        return  observe(memberManageService.doQueryComsumeLog(pageNumber,pageSize,compMemTypeId,userId,memberId));
    }

    public Observable<HttpResponse<List<CompMemType>>> doQueryMemberType(String memberId){
        return  observe(memberManageService.doQueryMemberType(memberId));


    }


    //会员卡流水
    public Observable<HttpResponse<List<Order>>> selectToCounByOrder(String userId,String salesTimeSta,String salesTimesEnd,String storeId,Integer pageSize,Integer pageNumber){
        return  observe(memberManageService.selectToCounByOrder(userId,salesTimeSta,salesTimesEnd,storeId,pageSize,pageNumber));
    }

    public Observable<HttpResponse<List<Order>>> selectToITByOrder(String userId,String compMemtypeId,String salesTimeSta,String salesTimesEnd,String storeId,Integer pageSize,Integer pageNumber){
        return  observe(memberManageService.selectToITByOrder(userId,compMemtypeId,salesTimeSta,salesTimesEnd,storeId,pageSize,pageNumber));
    }


    public Observable<HttpResponse<List<CompMemberType>>> doMemTypeList(String userId, Integer pageNumber, Integer pageSize){
        return  observe(memberManageService.doMemTypeList(userId,pageNumber,pageSize));
    }

    public Observable<HttpResponse<String>> memberTypeOperation(String userId, String type,String memtypeIdArray,String compMemberGroup){
        return  observe(memberManageService.memberTypeOperation(userId,type,memtypeIdArray,compMemberGroup));
    }

    public Observable<HttpResponse<TemplateBean>> doTemplaCard(String companyId){
        return  observe(memberManageService.doTemplaCard(companyId));
    }


    public Observable<HttpResponse<Map<String,String>>> insertMemberType(String userId, String compMemberTypeStr, String brandArray){
        return  observe(memberManageService.insertMemberType(userId,compMemberTypeStr,brandArray));
    }
    public Observable<HttpResponse<String>> updateMemberType(String userId, String compMemberTypeStr,String brandArray,String memtypeStatus){
        return  observe(memberManageService.updateMemberType(userId,compMemberTypeStr,brandArray,memtypeStatus));
    }


    public Observable<HttpResponse<CompMemTypeDetailBean>> doQueryByGroup(String userId, String compMemTypeId){
        return  observe(memberManageService.doQueryByGroup(userId,compMemTypeId));
    }


    public Observable<HttpResponse<String>> isReceivingAccount(String userId){
        return  observe(memberManageService.isReceivingAccount(userId));
    }
}
