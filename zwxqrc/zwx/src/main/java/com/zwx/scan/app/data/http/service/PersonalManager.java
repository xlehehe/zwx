package com.zwx.scan.app.data.http.service;

import com.zwx.scan.app.data.base.BaseServiceManager;
import com.zwx.scan.app.data.bean.Feedback;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.MessageBean;
import com.zwx.scan.app.data.bean.MessageSet;
import com.zwx.scan.app.data.bean.PersonalBean;
import com.zwx.scan.app.data.bean.Store;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * author : lizhilong
 * time   : 2019/01/08
 * desc   :
 * version: 1.0
 **/
public class PersonalManager extends BaseServiceManager {

    private StoreService storeService;


    private UserService userService;
    public PersonalManager(){
        storeService = RetrofitServiceManager.getInstance().create(StoreService.class);
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
     *  用户信息
     * @param userId
     * @return
     */
    public Observable<HttpResponse<PersonalBean>> queryUser(String userId){
        return  observe(userService.queryUser(userId));
    }

    public Observable<HttpResponse<String>> updateUser(String userId,String updateStr,String operateFlag){
        return  observe(userService.updateUser(userId,updateStr,operateFlag));
    }

    public Observable<HttpResponse<String>> checkPsd(String userId,String password){
        return  observe(userService.checkPsd(userId,password));
    }


    public Observable<HttpResponse<List<MessageSet>>> queryMessageType(String userId){
        return  observe(userService.queryMessageType(userId));
    }

    public Observable<HttpResponse<MessageBean>> queryMessageList(String userId, Integer pageNumber, Integer pageSize, String messageType){
        return  observe(userService.queryMessageList(userId,pageNumber,pageSize,messageType));
    }


    public Observable<HttpResponse<String>> doStatus(String userId,String messageType, String  status ){
        return  observe(userService.doStatus(userId,messageType,status));
    }

    public Observable<HttpResponse<MessageSet>> doMessageStatus(String id,String userId){
        return  observe(userService.doMessageStatus(id,userId));
    }


    /**
     * 意见反馈
     * */

    public Observable<HttpResponse<List<Feedback>>> doFeedList(String userId,Integer pageNumber,Integer pageSize){
        return  observe(userService.doFeedList(userId,pageSize,pageNumber));
    }

    public Observable<HttpResponse<Feedback>> doFeedQuery(String feedbackId){
        return  observe(userService.doFeedQuery(feedbackId));
    }
    public Observable<HttpResponse<String>> doFeedSave(String type, String content, String images, String createrTel,String userId){
        return  observe(userService.doFeedSave(type,content,images,createrTel,userId));
    }


    public Observable<HttpResponse<String>> doFeedFlag(String feedbackId){
        return  observe(userService.doFeedFlag(feedbackId));
    }
}
