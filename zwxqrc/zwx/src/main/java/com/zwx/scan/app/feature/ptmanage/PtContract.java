package com.zwx.scan.app.feature.ptmanage;

import android.content.Context;

import com.zwx.scan.app.base.BasePresenter;
import com.zwx.scan.app.base.BaseView;
import com.zwx.scan.app.data.bean.CampaginGrant;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCoupon;

import java.io.File;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
public interface PtContract {

    interface  View extends BaseView<PtContract.Presenter> {

    }

    interface  Presenter extends BasePresenter {


        //活动列表
        void queryCampaignList(Context context, String userId, String compId, Integer pageNumber, Integer pageSize, String campaignType, boolean isRefresh);


        void doCampaignDelete(Context context, String campaignId, int position);

        void doCampaignUpdate(Context context, String campaignId, String operateFlag);


        //查询店铺
        void queryBrandAndStoreList(Context context, String userId, boolean isDefaultAllStore,boolean isCampaignAndCouponStore);


      /*  void saveCampaignInfo(Context context, Campaign campaign, String compMemTypeId, CampaginGrant campaginGrant,
                              List<CampaignCoupon> forwardCoupon,
                              String userId, String compId, String btnFlag);*/

        void savePinTuanCampaignInfoForgame(Context context, String jsonStr,String btnFlag);


        void doEdit(Context context, String campaignId);


        void uploadFiles(Context context, List<File> fileList);

        void queryTigerPoster(Context context, String userId, String compId, String id,String templateType);

        void doEditForGame(Context context, String campaignId);


        void doQueryGroupBuy(Context context,String campaignName,String salesTimeSta,String salesTimesEnd,Integer pageSize,Integer pageNumber,String storeId,boolean isRefresh);

        void groupBuyCampaginRunning(Context context,String campaignId,Integer pageSize,Integer pageNumber,String storeId,String salesTimeSta,String salesTimesEnd,boolean isRefresh);


    }



}
