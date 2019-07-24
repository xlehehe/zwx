package com.zwx.scan.app.data.http.service;

import android.content.Context;

import com.zwx.scan.app.data.bean.CampaginGrant;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCount;
import com.zwx.scan.app.data.bean.CampaignCountSecond;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CampaignCouponDetailbean;
import com.zwx.scan.app.data.bean.CampaignDetaiList;
import com.zwx.scan.app.data.bean.CampaignDetail;
import com.zwx.scan.app.data.bean.CampaignDetailBean;
import com.zwx.scan.app.data.bean.CampaignEditBean;
import com.zwx.scan.app.data.bean.CampaignSaveResult;
import com.zwx.scan.app.data.bean.CampaignTotal;
import com.zwx.scan.app.data.bean.CompMemberType;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.CouponType;
import com.zwx.scan.app.data.bean.GiveCouponResultBean;
import com.zwx.scan.app.data.bean.GroupBuyCampagin;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.JiZanResultBean;
import com.zwx.scan.app.data.bean.LhPtEditBean;
import com.zwx.scan.app.data.bean.MaterialGame;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.PinTuanBean;
import com.zwx.scan.app.data.bean.PosterMaterial;
import com.zwx.scan.app.data.bean.ResultBean;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.couponmanage.CouponBean;
import com.zwx.scan.app.feature.couponmanage.CouponDetailBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
public interface CampaignService {


    @FormUrlEncoded
    @POST(HttpUrls.CAMPAIGN_ANALYSIS_URL)
    Observable<HttpResponse<CampaignCount>> queryCampaignCountanalysis(@Field("storeId")String storeId, @Field("date")String date);
    @FormUrlEncoded
    @POST(HttpUrls.CAMPAIGN_LIST_URL)
    Observable<HttpResponse<List<CampaignTotal>>> querySpecificCampaignCountList(@Field("storeId")String storeId, @Field("status")String status);



    @FormUrlEncoded
    @POST(HttpUrls.CAMPAIGN_DETAIL_REPORT_URL)
    Observable<HttpResponse<CampaignDetail>> queryCountByCampaignIdAndDate(@Field("campaignId")String campaignId, @Field("date")String date);

    @FormUrlEncoded
    @POST(HttpUrls.CAMPAIGN_DETAIL_LIST_URL)
    Observable<HttpResponse<CampaignDetaiList>> queryCampaignTotalDetailList(@Field("campaignId")String campaignId);

    @FormUrlEncoded
    @POST(HttpUrls.MORE_STORE_URL)
    Observable<HttpResponse<MoreStoreBean>> queryBrandAndStoreList(@Field("userId") String userId);
    @FormUrlEncoded
    @POST(HttpUrls.CAMPAIGN_MORE_ANALYSIS_URL)
    Observable<HttpResponse<CampaignCountSecond>> queryMoreStoreCampaignCountanalysis(@Field("storeId")String storeId, @Field("date")String date);

    @FormUrlEncoded
    @POST(HttpUrls.MORE_STORE_CAMPAIGN_MORE_ANALYSIS_URL)
    Observable<HttpResponse<CampaignDetaiList>> queryMoreStoreWholeAndCouponCount(@Field("storeId")String storeId, @Field("campaignId")String campaignId);

    @FormUrlEncoded
    @POST(HttpUrls.MORE_STORE_SPECIFIC_URL)
    Observable<HttpResponse<List<CampaignTotal>>> queryMoreSpecificCampaignCountList(@Field("storeId")String storeId, @Field("status")String status);

    @FormUrlEncoded
    @POST(HttpUrls.MORE_STORE_CAMPAIGN_WHOLE_SPECIFIC_URL)
    Observable<HttpResponse<CampaignDetailBean>> queryMoreStoreWholeCount(@Field("storeId")String storeId, @Field("campaignId")String campaignId, @Field("date")String date);

    //活动列表
    @FormUrlEncoded
    @POST(HttpUrls.QUERY_CAMPAIGN_LIST_URL)
    Observable<HttpResponse<List<Campaign>>> doQueryCampaignList(@Field("userId")String userId, @Field("compId")String compId,
                                                                 @Field("pageNumber")Integer pageNumber,@Field("pageSize")Integer pageSize,@Field("campaignType")String campaignType);


    //删除活动
    @FormUrlEncoded
    @POST(HttpUrls.CAMPAIGN_DELETE_URL)
    Observable<HttpResponse<ResultBean>> doCampaignDelete(@Field("campaignId")String campaignId);

    //提前结束和作废 operateFlag 操作标志 1代表作废，2代表提前结束
    @FormUrlEncoded
    @POST(HttpUrls.CAMPAIGN_UPDATE_URL)
    Observable<HttpResponse<ResultBean>> doCampaignUpdate(@Field("campaignId")String campaignId, @Field("operateFlag")String operateFlag);


    //优惠券类型列表
    @FormUrlEncoded
    @POST(HttpUrls.COUPON_TYPE_LSIT_URL)
    Observable<HttpResponse<List<CouponType>>> queryCouponTypeList(@Field("userId")String userId);

    //优惠券列表
    @FormUrlEncoded
    @POST(HttpUrls.COUPON_LIST_URL)
    Observable<HttpResponse<List<Coupon>>> queryCoupon(@Field("userId")String userId, @Field("campaignId")String campaignId,
                                                       @Field("couponType")String couponType, @Field("pageNumber")Integer pageNumber, @Field("pageSize")Integer pageSize);


    //海报列表
    @FormUrlEncoded
    @POST(HttpUrls.POSTER_LIST_URL)
    Observable<HttpResponse<List<PosterMaterial>>> queryPoster(@Field("compId")String compId);


    /*//海报列表
    @FormUrlEncoded
    @POST(HttpUrls.SAVE_CAMPAIGN_URL)
    Observable<HttpResponse<CampaignSaveResult>> saveCampaignInfo(@Body Campaign campaign,@Field("storeSelectType")String storeSelectType,
                                                                  @Field("storeStr")String storeStr,
                                                                  @Body List<CampaignCoupon> forwardCoupon,
                                                                  @Body List<CampaignCoupon> receiveCoupon,
                                                                  @Field("posterId")String posterId,
                                                                  @Field("userId")String userId,
                                                                  @Field("compId")String compId,
                                                                  @Field("btnFlag")String btnFlag);*/

    @FormUrlEncoded
    @POST(HttpUrls.SAVE_CAMPAIGN_URL)
    Observable<HttpResponse<CampaignSaveResult>> saveCampaignInfo(
                                                                  @Field("jsonstr")String jsonstr);


    @FormUrlEncoded
    @POST(HttpUrls.DO_CAMPAIGN_EDIT_URL)
    Observable<HttpResponse<CampaignEditBean>> doEdit(
            @Field("campaignId")String campaignId);

    @FormUrlEncoded
    @POST(HttpUrls.DO_CAMPAIGN_EDIT_URL)
    Observable<HttpResponse<GiveCouponResultBean>> doGiveCouponEdit(
            @Field("campaignId")String campaignId);


    @FormUrlEncoded
    @POST(HttpUrls.DO_CAMPAIGN_COPY_URL)
    Observable<HttpResponse<ResultBean>> copyCampaign(
            @Field("campaignId")String campaignId);


    @FormUrlEncoded
    @POST(HttpUrls.COMMEMBER_TYPE_URL)
    Observable<HttpResponse<List<CompMemberType>>> queryCommemberTypeByUserId(
            @Field("userId")String userId);



    @FormUrlEncoded
    @POST(HttpUrls.DO_CAMPAIGN_EDIT_URL)
    Observable<HttpResponse<CampaginGrant>> doZJEdit(
            @Field("campaignId")String campaignId);


    @FormUrlEncoded
    @POST(HttpUrls.CAMPAIGN_ANALYSIS_COUPON_URL)
    Observable<HttpResponse<List<CampaignTotal>>> campaignAnalysisForzj(@Field("userId")String userId, @Field("compaignStatus")String compaignStatus,
                                                                        @Field("pageSize")Integer pageSize, @Field("pageNumber")Integer pageNumber);


    @FormUrlEncoded
    @POST(HttpUrls.CAMPAIGN_ANALYSIS_COUPON_DETAIL_URL)
    Observable<HttpResponse<CampaignCouponDetailbean>> queryzjCountDetail(@Field("campaignId")String campaignId);

    @FormUrlEncoded
    @POST(HttpUrls.CAMPAIGN_ANALYSIS_PT_COUPON_DETAIL_URL)
    Observable<HttpResponse<CampaignCouponDetailbean>> queryPtCountDetail(@Field("campaignId")String campaignId);
    @FormUrlEncoded
    @POST(HttpUrls.CAMPAIGN_ANALYSIS_PT_LIST_URL)
    Observable<HttpResponse<List<CampaignTotal>>> campaignAnalysisForPt(@Field("userId")String userId, @Field("compaignStatus")String compaignStatus,
                                                                             @Field("pageSize")Integer pageSize, @Field("pageNumber")Integer pageNumber);

    @FormUrlEncoded
    @POST(HttpUrls.CAMPAIGN_ME_POSTER_URL)
    Observable<HttpResponse<List<MaterialGame>>> queryTigerPoster(@Field("userId")String userId, @Field("compId")String compId, @Field("id")String id,@Field("templateType")String templateType);
    @FormUrlEncoded
    @POST(HttpUrls.CAMPAIGN_ME_POSTER_URL)
    Observable<HttpResponse<List<MaterialGame>>> queryJzTigerPoster(@Field("userId")String userId, @Field("compId")String compId, @Field("id")String id,@Field("templateType")String templateType);



    @FormUrlEncoded
    @POST(HttpUrls.CAMPAIGN_ME_POSTER_URL)
    Observable<HttpResponse<List<MaterialGame>>> queryTemplateTigerPoster(@Field("userId")String userId,@Field("templateType")String templateType);
    @FormUrlEncoded
    @POST(HttpUrls.CAMPAIGN_ME_POSTER_URL)
    Observable<HttpResponse<List<MaterialGame>>> queryTigerPosterPrize(@Field("userId")String userId, @Field("compId")String compId, @Field("id")String id);

    /**
     * 老虎机&砸金蛋
     * */

    @FormUrlEncoded
    @POST(HttpUrls.DO_LH_PT_EDIT_URL)
    Observable<HttpResponse<LhPtEditBean>> doEditForGame(
            @Field("campaignId")String campaignId);


    @FormUrlEncoded
    @POST(HttpUrls.DO_LH_PT_SAVE_EDIT_URL)
    Observable<HttpResponse<CampaignSaveResult>> saveCampaignInfoForgame(
            @Field("jsonstr")String jsonstr);


    @FormUrlEncoded
    @POST(HttpUrls.DO_LH_PT_EDIT_URL)
    Observable<HttpResponse<PinTuanBean>> doEditForPinTuanGame(
            @Field("campaignId")String campaignId);

    //集赞详情接口
    @FormUrlEncoded
    @POST(HttpUrls.DO_LH_PT_EDIT_URL)
    Observable<HttpResponse<JiZanResultBean>> doEditForJzGame(
            @Field("campaignId")String campaignId);


    //海报列表
    @FormUrlEncoded
    @POST(HttpUrls.PIN_TUAN_NEW_URL)
    Observable<HttpResponse<CampaignSaveResult>> savePinTuanCampaignInfoForgame(
            @Field("jsonstr")String jsonstr);



    @GET(HttpUrls.PIN_TUAN_ORDER_LIST_URL)
    Observable<HttpResponse<List<GroupBuyCampagin>>> doQueryGroupBuy(@Query("campaignName") String campaignName, @Query("salesTimeSta")String salesTimeSta,
                                                                     @Query("salesTimesEnd")String salesTimesEnd, @Query("pageSize")Integer pageSize, @Query("pageNumber")Integer pageNumber, @Query("storeId")String storeId);

    @GET(HttpUrls.PIN_TUAN_ORDER_LIST_DETAIL_URL)
    Observable<HttpResponse<List<GroupBuyCampagin>>> groupBuyCampaginRunning(@Query("campaignId")String campaignId,@Query("pageSize")Integer pageSize, @Query("pageNumber")Integer pageNumber,
                                                                             @Query("storeId")String storeId, @Query("salesTimeSta")String salesTimeSta,
                                                                             @Query("salesTimesEnd")String salesTimesEnd);


    // 优惠券管理


    @GET(HttpUrls.COUPON_MANAGE_LIST_URL)
    Observable<HttpResponse<List<CouponBean>>> doQueryByCompId(
            @Query("storeId") String storeId, @Query("compId")String compId, @Query("pageNumber")Integer pageNumber, @Query("pageSize")Integer pageSize);



    @GET(HttpUrls.CHECK_COUPON_STATUS_LIST_URL)
    Observable<HttpResponse<String>> checkStatus(
            @Query("storeId") String storeId, @Query("compId")String compId, @Query("id")String id, @Query("status")String status);

    @FormUrlEncoded
    @POST(HttpUrls.UPDATE_COUPON_STATUS_LIST_URL)
    Observable<HttpResponse<String>> doStatus(
            @Field("storeId") String storeId, @Field("compId") String compId, @Field("id")String id, @Field("status")String status);



    @FormUrlEncoded
    @POST(HttpUrls.INSERT_COUPON_URL)
    Observable<HttpResponse<String>> doCouponInsert(@Field("userId") String userId,
            @Field("storeId") String storeId, @Field("compId") String compId, @Field("couponJson")String couponJson, @Field("startDate")String startDate, @Field("endDate")String endDate);

    @FormUrlEncoded
    @POST(HttpUrls.UPDATE_COUPON_URL)
    Observable<HttpResponse<String>> doCouponUpdate(@Field("userId") String userId,
                                                    @Field("storeId") String storeId, @Field("compId") String compId, @Field("couponJson")String couponJson, @Field("startDate")String startDate, @Field("endDate")String endDate);


    @FormUrlEncoded
    @POST(HttpUrls.LOAD_COUPON_URL)
    Observable<HttpResponse<CouponDetailBean>> doCouponLoad(
                                                    @Field("storeId") String storeId, @Field("compId") String compId, @Field("id")String id);

}
