package com.zwx.scan.app.feature.contractmanage;

import android.content.Context;

import com.zwx.scan.app.base.BasePresenter;
import com.zwx.scan.app.base.BaseView;
import com.zwx.scan.app.data.bean.Contract;

/**
 * author : lizhilong
 * time   : 2018/08/31
 * desc   : 登录接口类
 * version: 1.0
 **/

public interface ContractContract {

    interface  View extends BaseView<Presenter> {
//        void showLoading();
//        void hideLoading();
    }

    interface  Presenter extends BasePresenter {


        void doQueryContract(Context context,String storeId,String contractId);

        void doQueryContractList(Context context,String storeId,String isDetailAndList);
    }
}
