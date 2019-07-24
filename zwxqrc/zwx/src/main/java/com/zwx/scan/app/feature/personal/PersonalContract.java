package com.zwx.scan.app.feature.personal;

import android.content.Context;

import com.zwx.scan.app.base.BasePresenter;
import com.zwx.scan.app.base.BaseView;
import com.zwx.scan.app.data.bean.User;

import java.io.File;
import java.util.List;

import retrofit2.http.Query;

/**
 * author : lizhilong
 * time   : 2018/08/31
 * desc   : 登录接口类
 * version: 1.0
 **/

public interface PersonalContract {

    interface  View extends BaseView<Presenter> {
//        void showLoading();
//        void hideLoading();
    }

    interface  Presenter extends BasePresenter {


        void logout(Context context,User user);

        void queryStoreList(Context context,String userId);
        void checkPsd(Context context,String userId,String password);
        void queryUser(Context context,String userId);
        void updateUser(Context context,String userId,String updateStr,String operateFlag);


        void queryMessageType(Context context,String userId,String isFragmentFlag);

        void queryMessageList(Context context,String userId,Integer pageNumber,Integer pageSize,String messageType,boolean isRefresh);

        void doStatus(Context context,String userId,String status,String messageType);

        void doMessageStatus(Context context,String id,String userId);

        void uploadFiles(Context context, List<File> fileList);

        void doFeedList(Context context,String userId,Integer pageNumber,Integer pageSize,boolean isResh);
        void doFeedQuery(Context context,String feedbackId);
        void doFeedSave(Context context,String type, String content, String images, String createrTel,String userId);

        //是否有未读消息判断
        void doFeedFlag(Context context,String userId,String isPersonalFragment);



        //支付认证状态
        void selectPaymentAuthStatus(Context context,String userId);
        //支付认证查询
        void selectPaymentAuthInfo(Context context,String userId);

        void setPersonalAuthInfo(Context context,String userId,String name,String identityNo);

        void sendVerificationCodeByBindPhone(Context context,String userId,String phone,String status,String process);

        void sendVerificationCodeByUnBindPhone(Context context,String userId,String phone,String status,String process);
        //绑定手机号
        void bindPhone(Context context,String userId,String managerName,String phone,String verificationCode,String status,String process);

        //解绑手机
        void unBindPhone(Context context,String userId,String phone,String verificationCode,String status,String process);

        //解绑银行卡
        void unbindBankCard(Context context,String userId,String cardNo);

        void createMember(Context context,String userId,String memberType);

       void  selectPersonalOrCompanyAuthInfo(Context context,String userId,String status,String process);

       void  bindBankCard(Context context,String userId, String cardNo, String name,
                     String identityNo, String bankName, String phone,String status,String process);



       void signContractNotice(Context context,String userId);


        void uploadFiles(Context context,List<File> fileList,String selectImgType);

        //设置企业认证信息
        void setCompanyAuthInfo(Context context,String userId, String companyName,String businessLicenseImg,String uniCredit, String legalName,
                          String legalPhone,String legalIds,String legalIdcard,
                          String legalIdcardBack,String accountNo,String parentBankName,
                          String province, String city,String status);

        //企业认证信息
        void  selectCompanyAuthInfo(Context context,String userId,String status,String process);


        //个人中心查询用户信息
        void queryMyUser(Context context,String userId);
    }
}
