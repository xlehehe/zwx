package com.zwx.scan.app.feature.accountmanage;

import android.content.Context;

import com.zwx.scan.app.base.BasePresenter;
import com.zwx.scan.app.base.BaseView;

/**
 * author : lizhilong
 * time   : 2018/08/31
 * desc   : 登录接口类
 * version: 1.0
 **/

public interface AccountContract {

    interface  View extends BaseView<Presenter> {
//        void showLoading();
//        void hideLoading();
    }

    interface  Presenter extends BasePresenter {
        void accountList(Context context, String userId, Integer pageNumber,Integer pageSize,boolean isRefresh);


        //店铺列表
        void listTreeByCurrentUser(Context context, String page,String limit, String userId);

        //角色
        void listByType(Context context, Integer type);

        void checkUserNameRepeat(Context context,String username,String id);

        void insertCateingUser(Context context,String username,String nickname,String password,String roleIds,String storeIds,String authFlag);
        void editCateingUser(Context context,String id,String username,String nickname,String password,String roleIds,String storeIds,String authFlag);
        void resourceTreeListByRoleId(Context context,String id);
    }
}
