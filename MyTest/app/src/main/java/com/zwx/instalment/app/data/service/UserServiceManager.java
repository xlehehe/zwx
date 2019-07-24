package com.zwx.instalment.app.data.service;

import com.zwx.instalment.app.data.base.BaseServiceManager;
import com.zwx.instalment.app.data.bean.BaseResponse;
import com.zwx.instalment.app.data.entity.User;
import com.zwx.instalment.app.data.request.service.RetrofitRequestManager;

import io.reactivex.Observable;

/**
 * author : lizhilong
 * time   : 2019/07/05
 * desc   : 用户接口服务管理
 * version: 1.0
 **/
public class UserServiceManager extends BaseServiceManager {

    private UserService userService;
    private static UserServiceManager userServiceManager;

    /**
     * 单例模式，得到requestbusiness的实例
     * @return
     */
    public static synchronized UserServiceManager getInstance() {
        if (userServiceManager == null) {
            userServiceManager = new UserServiceManager();
        }
        return userServiceManager;
    }

    /**
     * 得到接口API，调用相应的接口
     * @return
     */
    public UserService getAPI() {
        //构建Retrofit
        if (userService == null) {
            userService =
                    RetrofitRequestManager.getInstance().create(UserService.class);
        }
        return userService;
    }

    /**
     *  desc用户登录
     * @param username
     * @param password
     * @return
     */
    public Observable<BaseResponse<User>> login(String username, String password){
        return  observe(getAPI().login(username,password));
    }

}
