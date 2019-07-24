package com.zwx.scan.app.feature.member;

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

public interface MemberManageContract {

    interface  View extends BaseView<Presenter> {
//        void showLoading();
//        void hideLoading();
    }

    interface  Presenter extends BasePresenter {


        void comsumeListByMap(Context context, String userId,int pageNumber,int pageSize,String order,String desc,boolean isRefresh);



        void memberListByMap(Context context, String memberTel, String userId,int pageNumber,int pageSize,boolean isRefresh);


        void doGetMemberType(Context context, String memberId,String userId);

        void doQueryComsumeLog(Context context, int pageNumber,int pageSize,String compMemTypeId,String userId,String memberId,boolean isRefresh);

        void doQueryMemberType(Context context, String memberId);

        void selectToCounByOrder(Context context,String userId,String salesTimeSta,String salesTimesEnd,String storeId,Integer pageSize,Integer pageNumber,boolean isRefresh);

        void selectToITByOrder(Context context,String userId,String compMemTypeId,String salesTimeSta,String salesTimesEnd,String storeId,Integer pageSize,Integer pageNumber,boolean isRefresh);

        void queryStoreList(Context context, String userId);


        void memTypeList(Context context, String userId,int pageNumber,int pageSize,boolean isRefresh);

        void memberTypeOperation(Context context, String userId,String type,String memtypeIdArray,String compMemberGroup);


        void doTemplaCard(Context context, String companyId);



        void updateMemberType(Context context,String userId, String compMemberTypeStr
                             ,String brandArray,String memtypeStatus);

        void insertMemberType(Context context,String userId, String compMemberTypeStr,String brandArray,String memtypeStatus);

        void doQueryByGroup(Context context,String userId, String compMemTypeId);

        void isReceivingAccount(Context context,String userId,String memtypeStatus);
    }
}
