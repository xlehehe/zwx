package com.zwx.scan.app.data.http.service;

import android.content.Context;
import android.content.Intent;

import com.zwx.scan.app.data.base.BaseServiceManager;
import com.zwx.scan.app.data.bean.CampaginGrant;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CampaignEditBean;
import com.zwx.scan.app.data.bean.CampaignGroupBuy;
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
import com.zwx.scan.app.feature.couponmanage.CouponBean;
import com.zwx.scan.app.feature.couponmanage.CouponDetailBean;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;

/**
 * author : lizhilong
 * time   : 2018/12/19
 * desc   :
 * version: 1.0
 **/
public class CampaignServiceManager extends BaseServiceManager{

    private Context context;

    private CampaignService campaignService;

    private CateringInfoService cateringInfoService;
    private HomeService homeService;
    public CampaignServiceManager(){
        campaignService = RetrofitServiceManager.getInstance().create(CampaignService.class);
        cateringInfoService = RetrofitServiceManager.getInstance().create(CateringInfoService.class);
    }


    /**
     *  活动列表
     * @param userId
     * @param compId
     * @param pageNumber
     * @param pageSize
     * @return
     */

    public Observable<HttpResponse<List<Campaign>>> doQueryCampaignList(String userId,String compId,Integer pageNumber,Integer pageSize,String campaignType){
        return  observe(campaignService.doQueryCampaignList(userId,compId,pageNumber,pageSize,campaignType));
    }

    /**
     * 提前结束和作废活动
     * */
    public Observable<HttpResponse<ResultBean>> doCampaignUpdate(String campaignId, String operateFlag){
        return  observe(campaignService.doCampaignUpdate(campaignId,operateFlag));
    }

    public Observable<HttpResponse<ResultBean>> doCampaignDelete(String campaignId){
        return  observe(campaignService.doCampaignDelete(campaignId));
    }

    public Observable<HttpResponse<MoreStoreBean>> queryBrandAndStoreList(String userId){
        return  observe(campaignService.queryBrandAndStoreList(userId));
    }

    public Observable<HttpResponse<List<CouponType>>> queryCouponTypeList(String userId){
        return  observe(campaignService.queryCouponTypeList(userId));
    }

    public Observable<HttpResponse<List<Coupon>>> queryCoupon(String userId, String campaignId, String couponType, Integer pageNumber, Integer pageSize){
        return  observe(campaignService.queryCoupon(userId,campaignId,couponType,pageNumber,pageSize));
    }


    public Observable<HttpResponse<List<PosterMaterial>>> queryPoster(String compId){
        return  observe(campaignService.queryPoster(compId));
    }

/*
    public Observable<HttpResponse<CampaignSaveResult>> saveCampaignInfo(Campaign campaign,String storeSelectType,String storeStr, List<CampaignCoupon> forwardCoupon,List<CampaignCoupon> receiveCoupon,String posterId,
    String userId,
    String compId,
    String btnFlag){
        return  observe(campaignService.saveCampaignInfo(campaign,storeSelectType,storeStr,forwardCoupon,receiveCoupon,posterId,userId,compId,btnFlag));
    }*/

    public Observable<HttpResponse<CampaignSaveResult>> saveCampaignInfo(String jsonStr){
        return  observe(campaignService.saveCampaignInfo(jsonStr));
    }


    public Observable<HttpResponse<CampaignEditBean>> doEdit(String campaignId){
        return  observe(campaignService.doEdit(campaignId));
    }

    public Observable<HttpResponse<GiveCouponResultBean>> doGiveCouponEdit(String campaignId){
        return  observe(campaignService.doGiveCouponEdit(campaignId));
    }

    public Observable<HttpResponse<ResultBean>> copyCampaign(String campaignId){
        return  observe(campaignService.copyCampaign(campaignId));
    }


    public Observable<HttpResponse<List<CompMemberType>>> queryCommemberTypeByUserId(String userId){
        return  observe(campaignService.queryCommemberTypeByUserId(userId));
    }



    public Observable<HttpResponse<CampaginGrant>> doZJEdit(String campaignId){
        return  observe(campaignService.doZJEdit(campaignId));
    }

    public Observable<HttpResponse<List<MaterialGame>>> queryTigerPoster(String userId,String compId,String id,String templateType){
        return  observe(campaignService.queryTigerPoster(userId,compId,id,templateType));
    }

    public Observable<HttpResponse<List<MaterialGame>>> queryJzTigerPoster(String userId,String compId,String id,String templateType){
        return  observe(campaignService.queryJzTigerPoster(userId,compId,id,templateType));
    }

    public Observable<HttpResponse<List<MaterialGame>>> queryTemplateTigerPoster(String userId,String templateType){
        return  observe(campaignService.queryTemplateTigerPoster(userId,templateType));
    }
    public Observable<HttpResponse<List<MaterialGame>>> queryTigerPrizePoster(String userId,String compId,String id){
        return  observe(campaignService.queryTigerPosterPrize(userId,compId,id));
    }

    public Observable<HttpResponse<CampaignSaveResult>> saveCampaignInfoForgame(String jsonStr){
        return  observe(campaignService.saveCampaignInfoForgame(jsonStr));
    }

    /**
     * 单个文件
     * */
    public Observable<HttpResponse<Map<String,Object>>> upload(File file){
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part MultipartFile =
                MultipartBody.Part.createFormData("androidbrandLogo", file.getName(), requestFile);

        return  observe(cateringInfoService.upload(MultipartFile));
    }

    /**
     * 多个文件
     * */
    public Observable<HttpResponse<Map<String,Object>>> uploadFiles(List<File> fileList){

        MultipartBody.Builder builder = new MultipartBody.Builder();
        //上传多张图片
        if(fileList != null && fileList.size()>0){
            for (File file : fileList){
                builder.addFormDataPart("AndroidImageFile-"+file.getName(), file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
            }

        }
        MultipartBody multipartBody = builder.setType(MultipartBody.FORM).build();
       /* MultipartBody multipartBody  = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("imageFile", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                .addFormDataPart("imageFile2", file2.getName(), RequestBody.create(MediaType.parse("image/*"), file2))
                .addFormDataPart("imageFile3", file3.getName(), RequestBody.create(MediaType.parse("image/*"), file3))
                .build();*/
        return  observe(cateringInfoService.uploadFiles(multipartBody));
    }

    //老虎机 砸金蛋
    public Observable<HttpResponse<LhPtEditBean>> doEditForGame(String jsonStr){
        return  observe(campaignService.doEditForGame(jsonStr));
    }

    //拼团
    public Observable<HttpResponse<PinTuanBean>> doEditForPinTuanGame(String jsonStr){
        return  observe(campaignService.doEditForPinTuanGame(jsonStr));
    }

    //集赞
    public Observable<HttpResponse<JiZanResultBean>> doEditForJzGame(String jsonStr){
        return  observe(campaignService.doEditForJzGame(jsonStr));
    }
    public Observable<HttpResponse<CampaignSaveResult>> savePinTuanCampaignInfoForgame(String jsonStr){
        return  observe(campaignService.savePinTuanCampaignInfoForgame(jsonStr));
    }


    public Observable<HttpResponse<List<GroupBuyCampagin>>> doQueryGroupBuy(String campaignName, String salesTimeSta, String salesTimesEnd, Integer pageSize, Integer pageNumber, String storeId){
        return  observe(campaignService.doQueryGroupBuy(campaignName,salesTimeSta,salesTimesEnd,pageSize,pageNumber,storeId));
    }


    public Observable<HttpResponse<List<GroupBuyCampagin>>> groupBuyCampaginRunning(String campaignId, Integer pageSize, Integer pageNumber, String storeId,String salesTimeSta, String salesTimesEnd){
        return  observe(campaignService.groupBuyCampaginRunning(campaignId, pageSize, pageNumber,storeId, salesTimeSta, salesTimesEnd));
    }


    //优惠券管理

    public Observable<HttpResponse<List<CouponBean>>> doQueryCouponByCompId(String storeId, String compId, Integer pageNumber, Integer pageSize){
        return  observe(campaignService.doQueryByCompId(storeId,compId,pageNumber,pageSize));
    }

    public Observable<HttpResponse<String>> doStatus(String storeId, String compId, String id,String status){
        return  observe(campaignService.doStatus(storeId,compId,id,status));
    }
    public Observable<HttpResponse<String>> checkStatus(String storeId, String compId, String id,String status){
        return  observe(campaignService.checkStatus(storeId,compId,id,status));
    }



    public Observable<HttpResponse<String>> doCouponInsert(String userId,String storeId, String compId, String couponJson,String startDate,String endDate){
        return  observe(campaignService.doCouponInsert(userId,storeId,compId, couponJson,  startDate, endDate));
    }
    public Observable<HttpResponse<String>> doCouponUpdate(String userId,String storeId, String compId, String couponJson,String startDate,String endDate){
        return  observe(campaignService.doCouponUpdate(userId,storeId,compId, couponJson,  startDate, endDate));
    }


    public Observable<HttpResponse<CouponDetailBean>> doCouponLoad(String storeId, String compId, String id){
        return  observe(campaignService.doCouponLoad(storeId,compId, id));
    }
}
