package com.zwx.scan.app.data.http.service;

import android.content.Context;

import com.zwx.scan.app.data.base.BaseServiceManager;
import com.zwx.scan.app.data.bean.DirectoryData;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.Member;
import com.zwx.scan.app.data.bean.PushContentBean;
import com.zwx.scan.app.data.bean.PushPreMessageBean;
import com.zwx.scan.app.data.bean.PushTypeBean;
import com.zwx.scan.app.data.bean.Pushnotify;
import com.zwx.scan.app.data.bean.ResultBean;
import com.zwx.scan.app.data.bean.StaffBean;
import com.zwx.scan.app.data.bean.StaffQRCode;
import com.zwx.scan.app.data.bean.StaffWork;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Query;

/**
 * author : lizhilong
 * time   : 2018/12/07
 * desc   :
 * version: 1.0
 **/
public class StaffManageServiceManager  extends BaseServiceManager {

    private Context context;

    private StaffManageService staffManageService;


    public StaffManageServiceManager(){
        staffManageService = RetrofitServiceManager.getInstance().create(StaffManageService.class);
    }



    public Observable<HttpResponse<List<StaffQRCode>>> listAvailableByStoreId(String storeId){
        return  observe(staffManageService.listAvailableByStoreId(storeId));
    }

    public Observable<HttpResponse<Member>> loadMemberById(String memberId){
        return  observe(staffManageService.loadMemberById(memberId));
    }

    public Observable<HttpResponse<ResultBean>> checkMobile(String storeId,String mobile){
        return  observe(staffManageService.checkMobile(storeId,mobile));
    }

    public Observable<HttpResponse<ResultBean>> doInsertStaff(String name,String telphone,
                                                                              String memberId,String position,
                                                                              String sex,String birthday, String joinday,
                                                                              String pullNewCode,String storeId){
        return  observe(staffManageService.doInsertStaff(name,telphone,memberId,position,sex,birthday,joinday,pullNewCode,storeId));
    }

    public Observable<HttpResponse<ResultBean>> doUpdate(String id,String staffId,String status,String name,String telphone,
                                                                String position,
                                                                String sex,String birthday,String joinday,
                                                                String quitday,String pullNewCode){
        return  observe(staffManageService.doUpdate(id,staffId,status,name,telphone,position,sex,birthday,joinday,quitday,pullNewCode));
    }

    public Observable<HttpResponse<ResultBean>> doLeave(String ids, String position,String quitTime){
        return  observe(staffManageService.doLeave(ids,position,quitTime));
    }

    public Observable<HttpResponse<StaffBean>> list(int pageNumber, int pageSize, String storeId){
        return  observe(staffManageService.list(pageNumber,pageSize,storeId));
    }

    public Observable<HttpResponse<StaffWork>> load(String id){
        return  observe(staffManageService.load(id));
    }
    public Observable<HttpResponse<List<DirectoryData>>> listByCatId(String catId){
        return  observe(staffManageService.listByCatId(catId));
    }


    /**
     * 推送扫码相关
     * */
    //获取推送消息类型
    public Observable<HttpResponse<ArrayList<DirectoryData>>> getPushType(){
        return  observe(staffManageService.getPushType());
    }

    //获取推送消息内容
    public Observable<HttpResponse<List<Map<String,String>>>> getContentByType(String userId, String pushType, int pageNumber, int pageSize){
        return  observe(staffManageService.getContentByType(userId,pushType,pageSize,pageNumber));
    }


    public Observable<HttpResponse<String>> doPushUpdate(String userId, String pushTypes, String pushContentIds){
        return  observe(staffManageService.doPushUpdate(userId,pushTypes,pushContentIds));
    }

    public Observable<HttpResponse<List<Pushnotify>>> doPushLoad(String userId){
        return  observe(staffManageService.doPushLoad(userId));
    }


    public Observable<HttpResponse<PushPreMessageBean>> doPushContentByMessage(String userId, String pushType, String pushContentId){
        return  observe(staffManageService.doPushContentByMessage(userId,pushType,pushContentId));
    }

    public Observable<HttpResponse<List<PushPreMessageBean>>> doPushContentListByMessage(String jsonStr){
        return  observe(staffManageService.doPushContentListByMessage(jsonStr));
    }
}
