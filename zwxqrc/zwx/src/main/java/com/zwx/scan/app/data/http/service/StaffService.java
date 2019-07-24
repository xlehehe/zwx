package com.zwx.scan.app.data.http.service;

import com.zwx.scan.app.data.bean.CompMember;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.MemberCount;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.StaffCount;
import com.zwx.scan.app.data.bean.StaffCountBean;
import com.zwx.scan.app.data.bean.StaffReward;
import com.zwx.scan.app.data.http.HttpUrls;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
public interface StaffService {


    @FormUrlEncoded
    @POST(HttpUrls.STAFF_LIST_URL)
    Observable<HttpResponse<List<StaffReward>>> queryStaffRewardRankTotalList(@Field("storeId")String storeId,@Field("date")String date);

    @FormUrlEncoded
    @POST(HttpUrls.STAFF_ANALYSIS_URL)
    Observable<HttpResponse<StaffCount>> queryStaffRewardTotalList(@Field("storeId")String storeId, @Field("date")String date);

    @FormUrlEncoded
    @POST(HttpUrls.MORE_STORE_URL)
    Observable<HttpResponse<MoreStoreBean>> queryBrandAndStoreList(@Field("userId") String userId);

    //单个或多个店铺员工任务拉新排名
    @FormUrlEncoded
    @POST(HttpUrls.MORE_STORE_STAFF_LIST_URL)
    Observable<HttpResponse<List<StaffReward>>> queryMoreStoreStaffRewardRankTotalList(@Field("storeId")String storeId,@Field("date")String date,@Field("rewardType")String rewardType);

    //单个或多个店铺报表统计分析
    @FormUrlEncoded
    @POST(HttpUrls.MORE_STORE_STAFF_ANALYSIS_URL)
    Observable<HttpResponse<StaffCountBean>> queryMoreStoreStaffRewardTotalList(@Field("staffId")String staffId,@Field("storeId")String storeId, @Field("date")String date);


}
