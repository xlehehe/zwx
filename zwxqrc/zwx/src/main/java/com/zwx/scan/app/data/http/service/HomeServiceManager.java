package com.zwx.scan.app.data.http.service;

import android.content.Context;

import com.zwx.scan.app.data.base.BaseServiceManager;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.Index;
import com.zwx.scan.app.data.bean.MessageSet;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.TResource;

import java.util.List;

import io.reactivex.Observable;

/**
 * author : lizhilong
 * time   : 2018/11/22
 * desc   : 首页接口服务管理
 * version: 1.0
 **/
public class HomeServiceManager extends BaseServiceManager {

    private Context context;

    private StoreService storeService;

    private UserService userService;
    private HomeService homeService;
    public HomeServiceManager(){
        storeService = RetrofitServiceManager.getInstance().create(StoreService.class);

        homeService = RetrofitServiceManager.getInstance().create(HomeService.class);

        userService = RetrofitServiceManager.getInstance().create(UserService.class);
    }



    /**
     *  店铺信息
     * @param userId
     * @return
     */

    public Observable<HttpResponse<List<Store>>> queryStore(String userId){
        return  observe(storeService.queryStores(userId));
    }


    /**
     *  权限按钮
     * @param userId
     * @return
     */

    public Observable<HttpResponse<List<TResource>>> queryResource(String userId){
        return  observe(homeService.queryReource(userId));
    }

    public Observable<HttpResponse<Index>> resources(String userId){
        return  observe(homeService.resources(userId));
    }

    /**
     *  报表
     * @param storeId
     * @return
     */

    public Observable<HttpResponse<Index>> index(String storeId){
        return  observe(homeService.index(storeId));
    }


    public Observable<HttpResponse<Index>> indexContact(String userId,String storeId){

        return  concat(resources(userId),index(storeId));
    }



    public Observable<HttpResponse<List<MessageSet>>> queryMessageType(String userId){
        return  observe(userService.queryMessageType(userId));
    }


    public Observable<HttpResponse<String>> doFeedFlag(String userId){
        return  observe(userService.doFeedFlag(userId));
    }
}
