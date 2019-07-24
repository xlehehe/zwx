package com.zwx.scan.app.feature.financemanage;

import android.content.Context;

import com.zwx.scan.app.base.BasePresenter;
import com.zwx.scan.app.base.BaseView;
import com.zwx.scan.app.data.bean.CampaginGrant;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCoupon;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
public interface FinanceContract {

    interface  View extends BaseView<FinanceContract.Presenter> {

    }

    interface  Presenter extends BasePresenter {




        //查询店铺
        void queryBrandAndStoreList(Context context, String userId,boolean isSelectStore);


        void doQueryAllByStatus(Context context,String storeId,String pageNumber,String pageSize,String status,boolean isRefresh);


        void doQueryByFundId(Context context,String storeId);


        void doQueryMoneyByIds(Context context,String userId,String storeId,String ids);

    }



}
