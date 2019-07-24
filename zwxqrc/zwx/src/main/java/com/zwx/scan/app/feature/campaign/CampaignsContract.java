package com.zwx.scan.app.feature.campaign;

import android.content.Context;

import com.zwx.scan.app.base.BasePresenter;
import com.zwx.scan.app.base.BaseView;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCollect;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CampaignGame;
import com.zwx.scan.app.data.bean.CampaignGamesetreward;
import com.zwx.scan.app.data.bean.CampaignNonrewardPic;
import com.zwx.scan.app.data.bean.CampaignSaveResult;
import com.zwx.scan.app.data.bean.HttpResponse;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
public interface CampaignsContract {

    interface  View extends BaseView<CampaignsContract.Presenter> {

    }

    interface  Presenter extends BasePresenter {


        //活动列表
        void doQueryCampaignList(Context context, String userId, String compId, Integer pageNumber, Integer pageSize,String campaignType,boolean isRefresh);
        //活动列表
        void queryCampaignList(Context context, String userId, String compId, Integer pageNumber, Integer pageSize,String campaignType,boolean isRefresh);


        void doCampaignDelete(Context context,String campaignId,int position);

        void doCampaignUpdate(Context context,String campaignId,String operateFlag);


        //查询店铺
        void queryBrandAndStoreList(Context context, String userId,boolean isCampaignAndCouponStore,boolean isDefaultAllStore);

        void queryCouponTypeList(Context context, String userId);

        void queryCoupon(Context context, String userId, String campaignId,String couponType, Integer pageNumber, Integer pageSize,boolean isRefresh);

        void queryPoster(Context context, String compId);

        void saveCampaignInfo(Context context,Campaign campaign, String storeSelectType, String storeStr, List<CampaignCoupon> forwardCoupon, List<CampaignCoupon> receiveCoupon, String posterId,
                                                                             String userId,
                                                                             String compId,
                                                                             String btnFlag);


        void doEdit(Context context,String campaignId);

        void copyCampaign(Context context,String campaignId);


        void queryStoreList(Context context, String userId,boolean isDefaultAllStore,boolean isLaoHuPinTuanNewOrTwo);


        //素材
        void queryTigerPoster(Context context,String userId,String compId,String id,String isUnPrize,String templateType);

        //中奖默认图
        void queryLhGeTigerPoster(Context context,String userId,String compId,String id,String isUnPrize,String templateType);

        void doEditForGame(Context context, String campaignId);



        void saveCampaignInfoForgame(Context context, Campaign campaign, String storeSelectType, String storeStr, List<CampaignNonrewardPic> campaignNonrewardPicList,
                                      List<CampaignGamesetreward> rewardlist, CampaignGame campaignGame, String posterId,
                                     String userId,
                                     String compId,
                                     String btnFlag);


        void uploadFiles(Context context,List<File> fileList,String name);


        void queryTemplateTigerPoster(Context context, String userId, String compId, String id,String templateType);


        //会员完善信息 店铺

        void queryMemberInfoPerfectStoreList(Context context, String userId, boolean isDefaultAllStore,boolean isLaoHuPinTuanNewOrTwo);

        //集赞活动 店铺 isOneOrTwo  yes 表示集赞活动第一步店铺  no表示第二步优惠券店铺
        void queryJzStoreList(Context context, String userId, boolean isDefaultAllStore,boolean isOneOrTwo);

        void queryJzTigerPoster(Context context, String userId, String compId, String id,String templateType);



        //集赞
        //编辑详情查询
        void doEditForJzGame(Context context, String campaignId);

        //创建并保存
     /*   void saveJzCampaignInfoForgame(Context context, Campaign campaign, String storeSelectType, String storeStr,
                                       List<CampaignGamesetreward> rewardlist, CampaignCollect campaignCollect, String posterId,
                                       String userId,
                                       String compId,
                                       String btnFlag);*/
        void saveJzCampaignInfoForgame(Context context, String jsonStr,String btnFlag);

        void jzUploadFiles(Context context,List<File> fileList);


        void saveHyCampaignInfo(Context context,Campaign campaign, String storeSelectType, String storeStr, List<CampaignCoupon> forwardCoupon, List<CampaignCoupon> receiveCoupon, String posterId,
                              String userId,
                              String compId,
                              String btnFlag);

        void doHyEdit(Context context, String campaignId) ;

    }




}
