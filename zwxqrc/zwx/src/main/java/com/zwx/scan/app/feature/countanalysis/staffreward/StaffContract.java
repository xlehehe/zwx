package com.zwx.scan.app.feature.countanalysis.staffreward;

import android.content.Context;

import com.zwx.scan.app.base.BasePresenter;
import com.zwx.scan.app.base.BaseView;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
public interface StaffContract {

    interface  View extends BaseView<StaffContract.Presenter> {

    }

    interface  Presenter extends BasePresenter {


        void queryStaffRewardTotalList(Context context, String storeId, String date);


        void queryStaffRewardRankTotalList(Context context, String storeId, String date);


        void queryMoreStoreStaffRewardTotalList(Context context, String userId,String storeId, String date,boolean isSelectStore);


        void queryMoreStoreStaffRewardRankTotalList(Context context, String storeId, String date,String rewardType);

        void queryBrandAndStoreList(Context context, String userId);
    }
}
