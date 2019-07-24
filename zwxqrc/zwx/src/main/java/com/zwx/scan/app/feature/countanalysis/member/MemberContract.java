package com.zwx.scan.app.feature.countanalysis.member;

import android.content.Context;

import com.zwx.scan.app.base.BasePresenter;
import com.zwx.scan.app.base.BaseView;
import com.zwx.scan.app.feature.home.HomeContract;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
public interface MemberContract {

    interface  View extends BaseView<MemberContract.Presenter> {

    }

    interface  Presenter extends BasePresenter {


        void queryMemberCount(Context context, String storeId,String date);

        void queryMemTypeCountListDetail(Context context, String storeId,String compMemTypeId,String date);

        void queryMemTypeCountList(Context context, String storeId, String date);

        //多个店铺统计分析报表
        void queryMoreStoreMemberCountAnalysis(Context context, String storeId,String date,boolean isSelectStore);

        //多个店铺 各个会员卡情况详情报表
        void queryMoreStoreMemTypeCountListDetail(Context context, String storeId,String compMemTypeId,String date,boolean isSelectStore);

        void queryMoreStoreMemTypeCountList(Context context, String storeId, String date,boolean isSelelctStore,boolean isSelectAnalysis);

        void queryBrandAndStoreList(Context context, String userId);

    }
}
