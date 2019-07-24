package com.zwx.scan.app.feature.user;

import android.content.Context;

import com.zwx.scan.app.base.BasePresenter;
import com.zwx.scan.app.base.BaseView;
import com.zwx.scan.app.data.bean.User;

/**
 * author : lizhilong
 * time   : 2018/08/31
 * desc   : 登录接口类
 * version: 1.0
 **/

public interface LoginContract {

    interface  View extends BaseView<Presenter> {
//        void showLoading();
//        void hideLoading();
    }

    interface  Presenter extends BasePresenter {
        void login(Context context, String username, String password);


//        void logout(User user);
        void checkUsername(Context context,String username);

        void doDownloadVersion(Context context,String id);

    }
}
