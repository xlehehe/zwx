package com.zwx.scan.app.data.http.service;

import android.content.Context;

import com.zwx.scan.app.data.base.BaseServiceManager;
import com.zwx.scan.app.data.bean.CampaignCount;
import com.zwx.scan.app.data.bean.CampaignCountSecond;
import com.zwx.scan.app.data.bean.CampaignCouponDetailbean;
import com.zwx.scan.app.data.bean.CampaignDetaiList;
import com.zwx.scan.app.data.bean.CampaignDetail;
import com.zwx.scan.app.data.bean.CampaignDetailBean;
import com.zwx.scan.app.data.bean.CampaignTotal;
import com.zwx.scan.app.data.bean.CompMember;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.Index;
import com.zwx.scan.app.data.bean.MemCoupon;
import com.zwx.scan.app.data.bean.MemberCount;
import com.zwx.scan.app.data.bean.MemberCountBean;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.StaffCount;
import com.zwx.scan.app.data.bean.StaffCountBean;
import com.zwx.scan.app.data.bean.StaffReward;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.TResource;

import java.lang.reflect.Member;
import java.util.List;

import io.reactivex.Observable;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
public class CountAnalysisManager extends BaseServiceManager {






    private Context context;

    private MemberService memberService;
    private StaffService staffService;

    private CampaignService campaignService;
    public CountAnalysisManager(){
        memberService = RetrofitServiceManager.getInstance().create(MemberService.class);
        staffService = RetrofitServiceManager.getInstance().create(StaffService.class);
        campaignService = RetrofitServiceManager.getInstance().create(CampaignService.class);
    }



    /**
     *  会员统计分析首页趋势
     * @param
     * @return
     */

    public Observable<HttpResponse<MemberCount>> queryMemberCount(String storeId, String date){
        return  observe(memberService.queryMemberCount(storeId,date));
    }
    /**
     * 各个会员卡情况列表
     * @param
     * @return
     */

    public Observable<HttpResponse<List<CompMember>>> queryMemTypeCountList(String storeId,String date){
        return  observe(memberService.queryMemTypeCountList(storeId,date));
    }

    public Observable<HttpResponse<MemberCount>> queryMemTypeCountListDetail(String storeId,String compMemTypeId,String date){
        return  observe(memberService.queryMemTypeCountListDetail(storeId,compMemTypeId,date));
    }



    /**
     *  多个店铺会员统计分析首页趋势
     * @param
     * @return
     */

    public Observable<HttpResponse<MemberCountBean>> queryMoreStoreMemberCountAnalysis(String storeId, String date){
        return  observe(memberService.queryMoreStoreMemberCountAnalysis(storeId,date));
    }
    /**
     * 多个店铺各个会员卡情况列表
     * @param
     * @return
     */

    public Observable<HttpResponse<List<CompMember>>> queryMorestoreMemTypeCountList(String storeId,String date){
        return  observe(memberService.queryMorestoreMemTypeCountList(storeId,date));
    }
    /**
     * 多个店铺各个会员卡情况列表详情趋势
     * @param
     * @return
     */
    public Observable<HttpResponse<MemberCountBean>> queryMoreStoreMemTypeCountListDetail(String storeId,String compMemTypeId,String date){
        return  observe(memberService.queryMoreStoreMemTypeCountListDetail(storeId,compMemTypeId,date));
    }

    public Observable<HttpResponse<MoreStoreBean>> queryMemberBrandAndStoreList(String userId){
        return  observe(memberService.queryBrandAndStoreList(userId));
    }
    public Observable<HttpResponse<StaffCount>> queryStaffRewardTotalList(String storeId, String date){
        return  observe(staffService.queryStaffRewardTotalList(storeId,date));
    }

    public Observable<HttpResponse<List<StaffReward>>> queryStaffRewardRankTotalList(String storeId,String date){
        return  observe(staffService.queryStaffRewardRankTotalList(storeId,date));
    }


    public Observable<HttpResponse<StaffCountBean>> queryMoreStoreStaffRewardTotalList(String staffId,String storeId, String date){
        return  observe(staffService.queryMoreStoreStaffRewardTotalList( staffId,storeId,date));
    }

    public Observable<HttpResponse<List<StaffReward>>> queryMoreStoreStaffRewardRankTotalList(String storeId,String date,String rewardType){
        return  observe(staffService.queryMoreStoreStaffRewardRankTotalList(storeId,date,rewardType));
    }


    public Observable<HttpResponse<MoreStoreBean>> queryStaffBrandAndStoreList(String userId){
        return  observe(staffService.queryBrandAndStoreList(userId));
    }


    public Observable<HttpResponse<CampaignCount>> queryCampaignCountanalysis(String storeId,String date){
        return  observe(campaignService.queryCampaignCountanalysis(storeId,date));
    }


    public Observable<HttpResponse<List<CampaignTotal>>> querySpecificCampaignCountList(String storeId, String status){
        return  observe(campaignService.querySpecificCampaignCountList(storeId,status));
    }

    public Observable<HttpResponse<CampaignDetail>> queryCountByCampaignIdAndDate(String campaignId, String date){
        return  observe(campaignService.queryCountByCampaignIdAndDate(campaignId,date));
    }

    public Observable<HttpResponse<CampaignDetaiList>> queryCampaignTotalDetailList(String campaignId){
        return  observe(campaignService.queryCampaignTotalDetailList(campaignId));
    }
    public Observable<HttpResponse<MoreStoreBean>> queryBrandAndStoreList(String userId){
        return  observe(campaignService.queryBrandAndStoreList(userId));
    }

    /**
     * 多个店铺活动统计分析情况
     * */
    public Observable<HttpResponse<CampaignCountSecond>> queryMoreStoreCampaignCountanalysis(String userId, String date){
        return  observe(campaignService.queryMoreStoreCampaignCountanalysis(userId, date));
    }


    public Observable<HttpResponse<CampaignDetaiList>> queryMoreStoreWholeAndCouponCount(String storeId, String campaignId){
        return  observe(campaignService.queryMoreStoreWholeAndCouponCount(storeId, campaignId));
    }

    public Observable<HttpResponse<List<CampaignTotal>>> queryMoreSpecificCampaignCountList(String storeId, String status){
        return  observe(campaignService.queryMoreSpecificCampaignCountList(storeId, status));
    }

    public Observable<HttpResponse<CampaignDetailBean>> queryMoreStoreWholeCount(String storeId, String campaignId,String date){
        return  observe(campaignService.queryMoreStoreWholeCount(storeId,campaignId,date));
    }


    public Observable<HttpResponse<List<CampaignTotal>>> campaignAnalysisForzj(String userId, String compaignStatus,Integer pageSize,Integer pageNumber){
        return  observe(campaignService.campaignAnalysisForzj(userId,compaignStatus,pageSize,pageNumber));
    }


    public Observable<HttpResponse<CampaignCouponDetailbean>> queryzjCountDetail(String campaignId){
        return  observe(campaignService.queryzjCountDetail(campaignId));
    }
    //拼团统计分析列表详情
    public Observable<HttpResponse<CampaignCouponDetailbean>> queryPtCountDetail(String campaignId){
        return  observe(campaignService.queryPtCountDetail(campaignId));
    }

    //拼团统计分析列表
    public Observable<HttpResponse<List<CampaignTotal>>> campaignAnalysisForPt(String userId, String compaignStatus,Integer pageSize,Integer pageNumber){
        return  observe(campaignService.campaignAnalysisForPt(userId,compaignStatus,pageSize,pageNumber));
    }
}
