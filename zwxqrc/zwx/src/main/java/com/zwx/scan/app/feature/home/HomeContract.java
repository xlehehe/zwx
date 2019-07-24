package com.zwx.scan.app.feature.home;

import android.content.Context;

import com.zwx.scan.app.base.BasePresenter;
import com.zwx.scan.app.base.BaseView;
import com.zwx.scan.app.data.bean.User;
import com.zwx.scan.app.feature.personal.PersonalContract;

/**
 * author : lizhilong
 * time   : 2018/11/22
 * desc   :
 * version: 1.0
 **/
public class HomeContract {

    interface  View extends BaseView<HomeContract.Presenter> {

    }

    interface  Presenter extends BasePresenter {


        void queryStore(Context context, String userId,boolean isRefresh);

        void queryResource(Context context, String userId);

        void index(Context context, String storeId,boolean isRefresh);
        void resoruces(Context context, String userId);

        void indexConcat(Context context,String userId,String storeId);


        void queryMessageType(Context context, String userId);


        void doFeedFlag(Context context, String userId);

        //支付认证状态
        void selectPaymentAuthStatus(Context context, String userId);
        void doDownloadVersion(Context context, String id);

    }
}
