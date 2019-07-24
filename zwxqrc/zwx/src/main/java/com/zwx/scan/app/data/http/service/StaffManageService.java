package com.zwx.scan.app.data.http.service;

import com.zwx.scan.app.data.bean.DirectoryData;
import com.zwx.scan.app.data.bean.Feedback;
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
import com.zwx.scan.app.data.http.HttpUrls;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * author : lizhilong
 * time   : 2018/12/07
 * desc   :
 * version: 1.0
 **/
public interface StaffManageService {

    //获取会员信息
    @POST(HttpUrls.LOAD_MEMBER_URL)
    Observable<HttpResponse<Member>> loadMemberById( @Query("memberId") String memberId);

    //获取店铺可用的二维码
    @POST(HttpUrls.LIST_QRC_URL)
    Observable<HttpResponse<List<StaffQRCode>>> listAvailableByStoreId(@Query("storeId") String storeId);




    //校验手机号
    @POST(HttpUrls.CHECK_MOBILE_URL)
    Observable<HttpResponse<ResultBean>> checkMobile(@Query("storeId") String storeId,@Query("mobile") String mobile);




    //新增员工
    @POST(HttpUrls.STAFF_INSERT_URL)
    Observable<HttpResponse<ResultBean>> doInsertStaff(@Query("name") String name,@Query("telphone") String telphone,
                                                       @Query("memberId") String memberId,@Query("position") String position,
                                                       @Query("sex") String sex,@Query("birthday") String birthday,@Query("joinday") String joinday,
                                                       @Query("pullNewCode")String pullNewCode,@Query("storeId")String storeId);


    //编辑员工
    @POST(HttpUrls.DO_STAFF_UPDATE_URL)
    Observable<HttpResponse<ResultBean>> doUpdate(@Query("id") String id,@Query("staffId") String staffId,
                                                  @Query("status") String status,@Query("name") String name,@Query("telphone") String telphone,
                                                  @Query("position") String position,@Query("sex") String sex,
                                                  @Query("birthday") String birthday,@Query("joinday") String joinday,
                                                  @Query("quitday") String quitday,@Query("pullNewCode")String pullNewCode);

    //离职员工
    @POST(HttpUrls.DO_LEAVE_URL)
    Observable<HttpResponse<ResultBean>> doLeave(@Query("ids") String ids,@Query("position") String position,@Query("quitTime") String quitTime);




    //员工列表页查询
    @POST(HttpUrls.STAFF_MEMBER_LIST_URL)
    Observable<HttpResponse<StaffBean>> list(@Query("pageNumber") Integer pageNumber, @Query("pageSize") Integer pageSize, @Query("storeId") String storeId);


    //根据ID获取员工
    @POST(HttpUrls.STAFF_LOAD_URL)
    Observable<HttpResponse<StaffWork>> load(@Query("id") String id);

    //根据ID获取员工
    @POST(HttpUrls.CAT_LIST_URL)
    Observable<HttpResponse<List<DirectoryData>>> listByCatId(@Query("catId") String catId);


    //推送扫码相关

    @GET(HttpUrls.PUSH_TYPE_LIST)
    Observable<HttpResponse<ArrayList<DirectoryData>>> getPushType();

    @GET(HttpUrls.PUSH_CONTENT_TYPE_LIST)
    Observable<HttpResponse<List<Map<String,String>>>> getContentByType(@Query("userId") String userId,
                                                                        @Query("pushType") String pushType, @Query("pageSize") Integer pageSize, @Query("pageNumber") Integer pageNumber);
//    Observable<HttpResponse<List<Feedback>>> getContentByType(@Query("userId") String userId,
//                                                        @Query("pageSize") Integer pageSize, @Query("pageNumber") Integer pageNumber);

    @POST(HttpUrls.PUSH_UPDADE)
    Observable<HttpResponse<String>> doPushUpdate(@Query("userId") String userId, @Query("pushTypes") String pushTypes, @Query("pushContentIds") String pushContentIds);


    @GET(HttpUrls.PUSH_LOAD)
    Observable<HttpResponse<List<Pushnotify>>> doPushLoad(@Query("userId") String userId);


    @GET(HttpUrls.PUSH_PRE)
    Observable<HttpResponse<PushPreMessageBean>> doPushContentByMessage(@Query("userId") String userId, @Query("pushType") String pushType, @Query("pushContentId") String pushContentId);


    @GET(HttpUrls.PUSH_PRE_LIST)
    Observable<HttpResponse<List<PushPreMessageBean>>> doPushContentListByMessage(@Query("jsonStr") String jsonStr);

}
