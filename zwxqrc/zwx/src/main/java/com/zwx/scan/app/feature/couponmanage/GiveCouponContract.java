package com.zwx.scan.app.feature.couponmanage;

import android.content.Context;

import com.zwx.scan.app.base.BasePresenter;
import com.zwx.scan.app.base.BaseView;
import com.zwx.scan.app.data.bean.CampaginGrant;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CampaignGame;
import com.zwx.scan.app.data.bean.CampaignGamesetreward;
import com.zwx.scan.app.data.bean.CampaignNonrewardPic;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
public interface GiveCouponContract {

    interface  View extends BaseView<GiveCouponContract.Presenter> {

    }

    interface  Presenter extends BasePresenter {


        //活动列表
        void queryCampaignList(Context context, String userId, String compId, Integer pageNumber, Integer pageSize, String campaignType, boolean isRefresh);


        void doCampaignDelete(Context context, String campaignId, int position);

        void doCampaignUpdate(Context context, String campaignId, String operateFlag);


        //查询店铺
        void queryBrandAndStoreList(Context context, String userId, boolean isDefaultAllStore);


        void saveCampaignInfo(Context context, Campaign campaign, String compMemTypeId,CampaginGrant campaginGrant,
                              List<CampaignCoupon> forwardCoupon,
                              String userId, String compId, String btnFlag);


        /**
         * 唤醒消费礼
         * */
        void saveCampaignInfoS(Context context, Campaign campaign, String compMemTypeId,CampaginGrant campaginGrant,
                              List<CampaignCoupon> forwardCoupon,List<CampaignGamesetreward> rewardlist,
                              String userId, String compId, String btnFlag);
        void doEdit(Context context, String campaignId);

        void queryCommemberTypeByUserId(Context context, String userId);


        //优惠券管理
        void doQueryCouponByCompId(Context context, String storeId, String compId, Integer pageNumber, Integer pageSize,Boolean isRefresh);

        void doStatus(Context context, String storeId, String compId, String id,String status);
        void checkStatus(Context context, String storeId, String compId, String id,String status);

       void  doCouponInsert(Context context, String userId,String storeId, String compId, String couponJson,String startDate,String endDate,String status);
       void  doCouponUpdate(Context context, String userId,String storeId, String compId, String couponJson,String startDate,String endDate,String status);

        void doCouponLoad(Context context, String storeId, String compId, String id);


        //唤醒消费
        void queryStoreList(Context context, String userId, boolean isDefaultAllStore);


        void saveCampaignInfoForgame(Context context, Campaign campaign, String storeSelectType, String storeStr, List<CampaignNonrewardPic> campaignNonrewardPicList,
                                     List<CampaignGamesetreward> rewardlist, CampaignGame campaignGame, String posterId,
                                     String userId,
                                     String compId,
                                     String btnFlag);
    }



}
