package com.zwx.scan.app.feature.countanalysis.campaign;

import android.content.Context;

import com.zwx.scan.app.base.BasePresenter;
import com.zwx.scan.app.base.BaseView;
import com.zwx.scan.app.data.bean.CampaignDetaiList;

import retrofit2.http.Field;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
public interface CampaignContract {

    interface  View extends BaseView<CampaignContract.Presenter> {

    }

    interface  Presenter extends BasePresenter {


        void queryCampaignCountanalysis(Context context, String storeId, String date);


        void querySpecificCampaignCountList(Context context, String storeId,String status);

        void queryCountByCampaignIdAndDate(Context context, String campaignId,String date);


        void queryCampaignTotalDetailList(Context context, String campaignId);

        void queryBrandAndStoreList(Context context, String userId);


        //多个店铺

        void queryMoreStoreCampaignCountanalysis(Context context,String storeId, String date,boolean isSelectStore);

        void queryMoreSpecificCampaignCountList(Context context,String storeId, String status,String date,boolean isSelectStore,boolean isSelectAnalysis);

        void queryMoreStoreWholeAndCouponCount(Context context,String storeId, String campaignId,String date,boolean isSelectStore);

        void queryMoreStoreWholeCount(Context context,String storeId, String campaignId,String date,boolean isSelectStore);



        void campaignAnalysisForzj(Context context, String userId, String compaignStatus,Integer pageSize,Integer pageNumber,boolean isRefresh);


        void queryzjCountDetail(Context context, String campaignId);

        void queryPtCountDetail(Context context, String campaignId);

        void campaignAnalysisForPt(Context context, String userId, String compaignStatus,Integer pageSize,Integer pageNumber,boolean isRefresh);
    }
}
