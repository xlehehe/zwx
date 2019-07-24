package com.zwx.scan.app.feature.staffmanage;

import android.content.Context;

import com.zwx.scan.app.base.BasePresenter;
import com.zwx.scan.app.base.BaseView;
import com.zwx.scan.app.data.bean.Pushnotify;
import com.zwx.scan.app.feature.verificationrecord.VerificationRecordActivity;
import com.zwx.scan.app.feature.verificationrecord.VerificationRecordDetailActivity;

import java.util.List;

public interface StaffManageContract {

    interface  View extends BaseView<Presenter> {
//        void showLoading();
//        void hideLoading();
    }

    interface  Presenter extends BasePresenter {


        void listAvailableByStoreId(Context context, String storeId);

        void loadMemberById(Context context, String id);
        void checkMobile(Context context, String storeId, String mobile);
        void doInsertStaff(Context context, String name, String telphone,
                           String memberId, String position,
                           String sex, String birthday, String joinday,
                            String pullNewCode, String storeId);

        void doUpdate(Context context, String id, String staffId, String status, String name, String telphone,
                      String position,
                      String sex, String birthday, String joinday,
                      String quitday, String pullNewCode);
        void doLeave(Context context, String ids, String position, String quitTime);
        void list(Context context, int pageNumber, int pageSize, String storeId,boolean isRefresh);

        void load(Context context, String id);


        void doPosition(Context context, String catId);


        void checkMobile(Context context, String name, String telphone,
                         String memberId, String position,
                         String sex, String birthday, String joinday,
                         String pullNewCode, String storeId);

        //推送类型
        void getPushType(Context context);

        //推送内容
        void getContentByType(Context context,String userId,String pushType,int pageNumber, int pageSize,boolean isRefresh);

        void doPushUpdate(Context context,String userId, String pushTypes, String pushContentIds);

        void doPushLoad(Context context, String userId,boolean isLoad);


        void doPushContentByMessage(Context context,String userId, String pushType, String pushContentId);
        void doPushContentListByMessage(Context context,List<Pushnotify> pushnotifyList);
    }
}
