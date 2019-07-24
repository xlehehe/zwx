package com.zwx.scan.app.feature.personal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.google.gson.Gson;
import com.luck.picture.lib.entity.LocalMedia;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.EncryptUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.base.BaseObserver;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignNonrewardPic;
import com.zwx.scan.app.data.bean.CompIdentityResultInfoBean;
import com.zwx.scan.app.data.bean.Feedback;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.MessageBean;
import com.zwx.scan.app.data.bean.MessageSet;
import com.zwx.scan.app.data.bean.PayAuthInfoResultBean;
import com.zwx.scan.app.data.bean.PersonalBean;
import com.zwx.scan.app.data.bean.ProductMedia;
import com.zwx.scan.app.data.bean.Role;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.Toke;
import com.zwx.scan.app.data.bean.User;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.data.http.service.CampaignServiceManager;
import com.zwx.scan.app.data.http.service.PayAuthServiceManage;
import com.zwx.scan.app.data.http.service.PersonalManager;
import com.zwx.scan.app.data.http.service.RetrofitServiceManager;
import com.zwx.scan.app.data.http.service.UserServiceManager;
import com.zwx.scan.app.feature.campaign.CampaignListTwoActivity;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanNextTwoActivity;
import com.zwx.scan.app.feature.campaign.LhGeEventBus;
import com.zwx.scan.app.feature.cateringinfomanage.FullyGridLayoutManager;
import com.zwx.scan.app.feature.main.MainActivity;
import com.zwx.scan.app.feature.user.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.schedulers.SchedulerWhen;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author : lizhilong
 * time   : 2018/10/18
 * desc   :
 * version: 1.0
 **/
public class PersonalPresenter implements PersonalContract.Presenter {



    private PersonalContract.View personalView;
    private UserServiceManager userServiceManager;

    private PersonalManager personalManager;
    private CampaignServiceManager campaignServiceManager;

    private PayAuthServiceManage payAuthServiceManage;

    //添加订阅，应用于取消订阅，防止造成内存泄漏
    private CompositeDisposable disposable;

    public PersonalPresenter(PersonalContract.View personalView) {
        userServiceManager = new UserServiceManager();
        personalManager = new PersonalManager();
        campaignServiceManager = new CampaignServiceManager();
        payAuthServiceManage = new PayAuthServiceManage();
        this.personalView = personalView;
        disposable = new CompositeDisposable();
    }

    @Override
    public void logout(Context context,User user) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

      /*  params.put("id",SPUtils.getInstance().getString("userId"));
        params.put("userId",SPUtils.getInstance().getString("userId"));
        params.put("username", SPUtils.getInstance().getString("username"));*/

        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        SystemManageActivity activity = (SystemManageActivity)context;
        userServiceManager.logout(user)
                .subscribe(new BaseObserver<String>(context,true){
                    @Override
                    public void onSuccess(String result,String msg) {
//                        SPUtils.getInstance().remove("username",false);
                        SPUtils.getInstance().remove("password",true);
                        SPUtils.getInstance().put("isSave",false);
                        SPUtils.getInstance().remove("token",true);

                        SPUtils.getInstance().put("isLogined","YES");
                        ActivityUtils.startActivity(new Intent(activity, LoginActivity.class),R.anim.slide_in_right, R.anim.slide_out_left);
                        activity.finish();

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);



                    }
                });

    }

    @Override
    public void queryStoreList(Context context, String userId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
//        AccountManageActivity activity =  (AccountManageActivity)context;
        personalManager.queryStore(userId)
                .subscribe(new BaseObserver<List<Store>>(context,false){
                    @Override
                    public void onSuccess(List<Store> storeList,String msg) {
                        if(storeList != null && storeList.size()>0){
//                            activity.storeList = storeList;
//                            activity.setStoresAdapter();

                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);



                    }
                });
    }

    @Override
    public void queryUser(Context context, String userId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        PersonalAccountManageActivity activity =  (PersonalAccountManageActivity)context;
        personalManager.queryUser(userId)
                .subscribe(new BaseObserver<PersonalBean>(context,false){
                    @Override
                    public void onSuccess(PersonalBean personalBean,String msg) {
                        if(personalBean != null ){

                            if("YES".equals(activity.isShowPsd)){

                            }else {
                                String username = personalBean.getUserName();
                                String nickName = personalBean.getNickName();
                                activity.edtName.setText(nickName != null && !"".equals(nickName)?nickName:"");
                                activity.tvAccount.setText(username != null && !"".equals(username)?username:"");
                            }


                            activity.storeList = personalBean.getStoreList();
                            activity.setStoresAdapter();


                            activity.roleList = personalBean.getRoleList();
                            activity.setRolesAdapter();

                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);



                    }
                });
    }

    @Override
    public void updateUser(Context context, String userId, String updateStr, String operateFlag) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("updateStr",updateStr);
        params.put("operateFlag", operateFlag);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        PasswordManageActivity activity = null;
        if("2".equals(operateFlag)){
            activity =  (PasswordManageActivity)context;
        }
        boolean shoProcess = true;
        if("1".equals(operateFlag)){
            shoProcess = false;
        }
        personalManager.updateUser(userId,updateStr,operateFlag)
                .subscribe(new BaseObserver<String>(context,shoProcess){
                    @Override
                    public void onSuccess(String result,String msg) {

                        if("2".equals(operateFlag)){
                            ToastUtils.showCustomShortBottom("修改密码成功！");
                            SPUtils.getInstance().remove("password",true);
                            SPUtils.getInstance().put("isSave",false);

                            ActivityUtils.startActivity(LoginActivity.class,R.anim.slide_in_right,R.anim.slide_out_left);
                            ActivityUtils.finishActivity((MainActivity)context);
                        }else {
//                            ToastUtils.showCustomShortBottom("修改用户名成功！");
                        }



                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);



                    }
                });
    }

    @Override
    public void checkPsd(Context context, String userId, String password) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("password",password);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        PasswordManageActivity activity =  (PasswordManageActivity)context;
        personalManager.checkPsd(userId,password)
                .subscribe(new BaseObserver<String>(context,false){
                    @Override
                    public void onSuccess(String result,String msg) {
                       activity.tv_psd_tip.setText(msg);

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                        activity.tv_psd_tip.setText(message);

                    }
                });
    }

    @Override
    public void queryMessageType(Context context, String userId,String isFragmentFlag) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        personalManager.queryMessageType(userId)
                .subscribe(new BaseObserver<List<MessageSet>>(context,false){
                    @Override
                    public void onSuccess(List<MessageSet> messageSetList,String msg) {



                        if("personalFragment".equals(isFragmentFlag)){
                            int count = 0;
                            PersonalThreeFragment fragment = (PersonalThreeFragment)personalView;
                            if(!messageSetList.isEmpty()){
                                for (int i = 0;i<fragment.messageSetList.size();i++){
                                    MessageSet messageSet = fragment.messageSetList.get(i);

                                     int count1 = messageSet.getCount();

                                     count +=count1;
                                }

                                if(count >= 1){
                                    fragment.msgView.setText(count);
                                }else {
                                    fragment.msgView.setVisibility(View.INVISIBLE);
                                }
                            }
                            queryMyUser(context,userId);
                        }else if("messageListActivity".equals(isFragmentFlag)){
                            MessageListActivity activity  =  (MessageListActivity)context;
                            if(!messageSetList.isEmpty()){
                                activity.messageSetList = messageSetList;
                                if("NO".equals(activity.isUpdate)){
                                    activity.setPagerAdapter();
                                }else {
                                    activity.pagerAdapter.updateData(messageSetList);
                                }

                                for (int i = 0;i<activity.messageSetList.size();i++){
                                    MessageSet messageSet = activity.messageSetList.get(i);

                                    int count = messageSet.getCount();

                                    if(count >= 1){
                                        activity.tabLayout.showDot(i);
                                        activity.tabLayout.showMsg(i,count);
                                        activity.tabLayout.setMsgMargin(i, 0, 10);
                                    }else {

                                    }

                                }

                            }
                        }


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        if("personalFragment".equals(isFragmentFlag)){
                            queryMyUser(context,userId);
                        }

                    }
                });
    }


    @Override
    public void queryMessageList(Context context, String userId, Integer pageNumber, Integer pageSize, String messageType,boolean isRefresh) {


        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("pageNumber",String.valueOf(pageNumber));
        params.put("pageSize",String.valueOf(pageSize));
        params.put("messageType",messageType);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        MessageListFragment fragment = (MessageListFragment) personalView;
        personalManager.queryMessageList(userId,pageNumber,pageSize,messageType)
                .subscribe(new BaseObserver<MessageBean>(context,false){
                    @Override
                    public void onSuccess(MessageBean messageBean, String msg) {

                        if(messageBean != null){
                            List<MessageSet> messageSetList = messageBean.getRows();
                             if (messageSetList!=null && messageSetList.size()>0) {
                                if (isRefresh) {
                                    fragment.messageSetList.clear();
                                    fragment.messageSetList.addAll(messageSetList);
                                    fragment.mAdapter.notifyDataSetChanged();

                                    if (messageSetList.size() < 10) {

                                        fragment.ptr.refreshComplete();
                                        fragment.ptr.setLoadMoreEnable(false);
                                    } else {
                                        fragment.ptr.refreshComplete();
                                        fragment.ptr.setLoadMoreEnable(true);
                                    }
                                } else {
                                    fragment.messageSetList.addAll(messageSetList);
//                                activity.mAdapter = new CampaignListViewAdapter(activity, activity.campaignList);
                                    fragment.mAdapter.notifyDataSetChanged();
                                    if (messageSetList.size() < 10) {
                                        fragment.ptr.refreshComplete();
                                        fragment.ptr.setLoadMoreEnable(false);
                                    } else {
                                        fragment.ptr.refreshComplete();
                                        fragment.ptr.loadMoreComplete(true);
                                    }
                                }
                            }else {
                                fragment.ptr.refreshComplete();
                                fragment.ptr.setLoadMoreEnable(false);
                            }
                        }else {
                            fragment.ptr.refreshComplete();
                            fragment.ptr.setLoadMoreEnable(false);
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        fragment.ptr.refreshComplete();
                        fragment.ptr.setLoadMoreEnable(false);
                    }
                });
    }

    @Override
    public void doStatus( Context context, String userId, String status, String type) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("messageType",type);
        params.put("status",status);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        MessageListActivity activity =  (MessageListActivity)context;
        personalManager.doStatus(userId,type,status)
                .subscribe(new BaseObserver<String>(context,false){
                    @Override
                    public void onSuccess(String result,String msg) {

                        if("R".equals(status)){
                            ToastUtils.showCustomShortBottom("消息已读");
                        }else {
                            ToastUtils.showCustomShortBottom("消息已清空");
                        }

                        activity.type = type;
//                        queryMessageType(context,userId,false);


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                        ToastUtils.showCustomShortBottom(message);

                    }
                });
    }

    @Override
    public void doMessageStatus(Context context,String id, String userId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("id",id);
        params.put("userId",userId);

        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        MessageListFragment fragment =  (MessageListFragment) personalView;
        personalManager.doMessageStatus(id,userId)
                .subscribe(new BaseObserver<MessageSet>(context,false){
                    @Override
                    public void onSuccess(MessageSet set,String msg) {


                        if(set != null ){

                            String status = set.getStatus();

                            if("R".equals(status)){
                                Intent intent = new Intent(fragment.getContext(),MessageListDetailActivity.class);
                                intent.putExtra("messageId",id);
                                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                            }else if("W".equals(status)){
                                fragment.setDialog();
                            }
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);


                    }
                });

    }


    @Override
    public void uploadFiles(Context context, List<File> fileList) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        FeedBackActivity activity = (FeedBackActivity)context;

        campaignServiceManager.uploadFiles(fileList)
                .subscribe(new BaseObserver<Map<String,Object>>(context,false){
                    @Override
                    public void onSuccess(Map<String,Object> map, String msg) {
                        String imageId = "";
                        if(map != null && map.size()>0) {
                            imageId = (String) map.get("id");

                            if(imageId != null && !"".equals(imageId)){
                               /* activity.images.clear();
                                if (imageId.contains(",")) {
                                    String[] ids = imageId.split(",");

                                    for (int i = 0; i < ids.length; i++) {
                                        activity.images.add(ids[i]);

                                    }
                                } else {
                                    activity.images.add(imageId);
                                }
                                String imagejson=new Gson().toJson(activity.images);*/
                                doFeedSave(context,activity.type,activity.desc,imageId,activity.phone,activity.userId);
                            }


                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.btnSubmit.setEnabled(true);
                    }
                });

    }

    /**
     * 意见反馈
     * */
    @Override
    public void doFeedList(Context context,String userId,Integer pageNumber,Integer pageSize,boolean isRefresh) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("pageSize",String.valueOf(pageSize));
        params.put("pageNumber",String.valueOf(pageNumber));
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        FeedBackListActivity fragment = (FeedBackListActivity) context;
        personalManager.doFeedList(userId,pageNumber,pageSize)
                .subscribe(new BaseObserver<List<Feedback>>(context,true){
                    @Override
                    public void onSuccess(List<Feedback> feedbackList, String msg) {

                        if (feedbackList!=null && feedbackList.size()>0) {
                            if (isRefresh) {
                                fragment.feedbackList.clear();
                                fragment.feedbackList.addAll(feedbackList);
                                fragment.mAdapter.notifyDataSetChanged();

                                if (feedbackList.size() < 10) {

                                    fragment.ptr.refreshComplete();
                                    fragment.ptr.setLoadMoreEnable(false);
                                } else {
                                    fragment.ptr.refreshComplete();
                                    fragment.ptr.setLoadMoreEnable(true);
                                }
                            } else {
                                fragment.feedbackList.addAll(feedbackList);
//                                activity.mAdapter = new CampaignListViewAdapter(activity, activity.campaignList);
                                fragment.mAdapter.notifyDataSetChanged();
                                if (feedbackList.size() < 10) {
                                    fragment.ptr.refreshComplete();
                                    fragment.ptr.setLoadMoreEnable(false);
                                } else {
                                    fragment.ptr.refreshComplete();
                                    fragment.ptr.loadMoreComplete(true);
                                }
                            }
                        }else {
                            fragment.mAdapter.notifyDataSetChanged();
                            fragment.ptr.refreshComplete();
                            fragment.ptr.setLoadMoreEnable(false);
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        fragment.mAdapter.notifyDataSetChanged();
                        fragment.ptr.refreshComplete();
                        fragment.ptr.setLoadMoreEnable(false);
                        ToastUtils.showShort(message);
                    }
                });

    }

    @Override
    public void doFeedQuery(Context context, String feedbackId) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("feedbackId",feedbackId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        FeedBackDetailActivity activity =  (FeedBackDetailActivity)context;
        personalManager.doFeedQuery(feedbackId)
                .subscribe(new BaseObserver<Feedback>(context,true){
                    @Override
                    public void onSuccess(Feedback feedback,String msg) {
                        if(feedback != null ){
                            //处理类型
                            activity.name = feedback.getName();
                            activity.imageIds = feedback.getImages();
                            activity.content = feedback.getContent();
                            activity.phone = feedback.getCreaterTel();
                            activity.createName = feedback.getCreaterName();
                            activity.createTime = feedback.getCreateTime();
                            activity.dealResult = feedback.getDealResult();
                            activity.dealTime = feedback.getDealTime();


                            activity.tv_type.setText(activity.name != null && !"".equals(activity.name)?activity.name:"");

                            activity.tv_content.setText(activity.content != null && !"".equals(activity.content)?activity.content:"");
                            activity.tv_time.setText(activity.createTime != null && !"".equals(activity.createTime)?activity.createTime:"");

                            activity.tv_creater.setText(activity.createName != null && !"".equals(activity.createName)?activity.createName:"");
                            activity.tv_tel.setText(activity.phone != null && !"".equals(activity.phone)?activity.phone:"");

                            if(activity.dealResult != null && !"".equals(activity.dealResult)){
                                activity.tv_result.setText(activity.dealResult != null && !"".equals(activity.dealResult)?activity.dealResult:"");
                                activity.tv_result_time.setText(activity.dealTime != null && !"".equals(activity.dealTime)?activity.dealTime:"");
                            }else {
                                activity.ll_result.setVisibility(View.GONE);
                            }

                            activity.selectList.clear();
                            if(activity.imageIds.contains(",")){
                                String[] photos = activity.imageIds.split(",");

                                for (int i = 0 ;i<photos.length;i++){
                                    LocalMedia localMedia = new LocalMedia();
                                    localMedia.setCompressPath(HttpUrls.IMAGE_ULR+photos[i]);
                                    localMedia.setPath(HttpUrls.IMAGE_ULR+photos[i]);
                                    localMedia.setCompressed(true);
                                    activity.selectList.add(localMedia);

                                }

                            }else {

                                LocalMedia localMedia = new LocalMedia();
                                localMedia.setCompressPath(HttpUrls.IMAGE_ULR+activity.imageIds);
                                localMedia.setPath(HttpUrls.IMAGE_ULR+activity.imageIds);
                                localMedia.setCompressed(true);
                                activity.selectList.add(localMedia);
                            }
                            activity.adapter.setList(activity.selectList);
//                            activity.manager = new FullyGridLayoutManager(context, activity.selectList.size(), GridLayoutManager.VERTICAL, false);
                            activity.manager = new FullyGridLayoutManager(context, activity.selectList.size());
                            activity.rv_photo.setLayoutManager(activity.manager);
                            activity.adapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);



                    }
                });

    }

    @Override
    public void doFeedSave(Context context, String type, String content, String images, String createrTel, String userId) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("type",type);
        params.put("content",content);
        params.put("images",images);
        params.put("createrTel",createrTel);
        params.put("userId",userId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        FeedBackActivity activity =  (FeedBackActivity)context;
        personalManager.doFeedSave(type,content,images,createrTel,userId)
                .subscribe(new BaseObserver<String>(context,true){
                    @Override
                    public void onSuccess(String result,String msg) {
                        ToastUtils.showCustomShortBottom(msg);
                        ActivityUtils.finishActivity(FeedBackActivity.class,
                                R.anim.slide_in_left, R.anim.slide_out_right);
                        activity.btnSubmit.setEnabled(true);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);
                        activity.btnSubmit.setEnabled(true);

                    }
                });

    }

    @Override
    public void doFeedFlag(Context context, String userId,String isPersonalFragment) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        personalManager.doFeedFlag(userId)
                .subscribe(new BaseObserver<String>(context,false){
                    @Override
                    public void onSuccess(String result,String msg) {

                        if("FRAGMENT".equals(isPersonalFragment)){
                            PersonalThreeFragment activity =  (PersonalThreeFragment)personalView;
                            if("1".equals(result)){
                                activity.feedback_msg_tip.setVisibility(View.VISIBLE);
                            }else {
                                activity.feedback_msg_tip.setVisibility(View.GONE);
                            }
                            //我的 ->消息未读
                            queryMessageType(context,userId,"personalFragment");
                        }else {
                            FeedBackActivity activity =  (FeedBackActivity)context;
                            if("1".equals(result)){
                                activity.feedback_msg_tip.setVisibility(View.VISIBLE);
                            }else {
                                activity.feedback_msg_tip.setVisibility(View.GONE);
                            }
                        }


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                        if("FRAGMENT".equals(isPersonalFragment)){
                            PersonalThreeFragment activity =  (PersonalThreeFragment)personalView;
                            activity.feedback_msg_tip.setVisibility(View.GONE);
                        }else {
                            FeedBackActivity activity =  (FeedBackActivity)context;
                            activity.feedback_msg_tip.setVisibility(View.GONE);
                        }
                    }
                });
    }

    //支付认证状态查询
    @Override
    public void selectPaymentAuthStatus(Context context, String userId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);

        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        PersonalThreeFragment fragment =  (PersonalThreeFragment) personalView;
        payAuthServiceManage.selectPaymentAuthStatus(userId)
                .subscribe(new BaseObserver<Map<String,Object>>(context,false){
                    @Override
                    public void onSuccess(Map<String,Object> result,String msg) {

                       // "process":"N","status":"N"
                        String statusStr = "";


                        fragment.status = (String)result.get("status");

                        if("N".equals(fragment.status)){
                            statusStr = "未认证";
                            fragment.process= null;
                        }else if("A".equals(fragment.status)){
                            statusStr = "已认证";
                            fragment.process= null;
                        }else if(" NA".equals(fragment.status)){
                            statusStr = "未完成";
                            fragment.process = (String)result.get("process");

                        }
                        fragment.tv_auth_state.setText(statusStr);
//                        queryMessageType(context,userId,true);
                        doFeedFlag(context,userId,"FRAGMENT");
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);


                    }
                });
    }

    @Override
    public void setPersonalAuthInfo(Context context, String userId, String name, String identityNo) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("name",name);
        params.put("identityNo",identityNo);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        TradeDrawingPersonalAuthOneActivity activity =  (TradeDrawingPersonalAuthOneActivity) context;
        payAuthServiceManage.setPersonalAuthInfo(userId,name,identityNo)
                .subscribe(new BaseObserver<Map<String,String>>(context,true){
                    @Override
                    public void onSuccess(Map<String,String> result,String msg) {

                        String status = result.get("status");

                        if("OK".equals(status)){
                            Intent intent = new Intent(context,TradeDrawingPersonalAuthTwoActivity.class);
                            intent.putExtra("process",activity.process);
                            intent.putExtra("status",activity.status);
                            ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                        }else {
                            activity.dialogTitle = "认证失败";
                            activity.setDialog(context.getResources().getString(R.string.auth_error));
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                       ToastUtils.showShort(message);

                    }
                });
    }

    @Override
    public void sendVerificationCodeByBindPhone(Context context, String userId, String phone,String status,String process) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("phone",phone);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        payAuthServiceManage.sendVerificationCodeByBindPhone(userId,phone)
                .subscribe(new BaseObserver<Map<String,String>>(context,true){
                    @Override
                    public void onSuccess(Map<String,String> result,String msg) {
                        String resultStatus = result.get("status");
                        TradeDrawingPersonalAuthTwoActivity activity =  (TradeDrawingPersonalAuthTwoActivity) context;
                        if("OK".equals(resultStatus)){

                        }else {
//                            activity.dialogTitle = "绑定失败";
//                            activity.setDialog("手机号绑定失败，请检查后，重新操作。");
                            activity.dialogTitle = "提示";
                            activity.setDialog("获取验证码失败");
                        }



                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);

                    }
                });
    }

    @Override
    public void sendVerificationCodeByUnBindPhone(Context context, String userId, String phone, String status, String process) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("phone",phone);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        PayAuthPhoneActivity activity =  (PayAuthPhoneActivity) context;
        payAuthServiceManage.sendVerificationCodeByUnBindPhone(userId,phone)
                .subscribe(new BaseObserver<Map<String,String>>(context,true){
                    @Override
                    public void onSuccess(Map<String,String> result,String msg) {


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);

                    }
                });
    }

    @Override
    public void bindPhone(Context context,String userId, String managerName, String phone, String verificationCode, String status, String process) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("managerName",managerName);
        params.put("phone",phone);
        params.put("verificationCode",verificationCode);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        TradeDrawingPersonalAuthTwoActivity activity =  (TradeDrawingPersonalAuthTwoActivity) context;
        payAuthServiceManage.bindPhone(userId,managerName,phone,verificationCode)
                .subscribe(new BaseObserver<Map<String,String>>(context,true){
                    @Override
                    public void onSuccess(Map<String,String> result,String msg) {

                        String resultStatus = result.get("status");
                        Intent intent = null;

                        if("OK".equals(resultStatus)){
                            if("A".equals(status)||"NA".equals(status)||"UpdatePhone".equals(status)){
                                intent = new Intent(context,PayAuthComplateActivity.class);
                                intent.putExtra("process",activity.process);
                                intent.putExtra("status",activity.status);
                                intent.putExtra("managerName",managerName);
                            }else {
                                if("CP".equals(activity.process)){
                                    intent = new Intent(context,PayAuthAgreeActivity.class);
                                }else if("PP".equals(activity.process)){
                                    intent = new Intent(context,TradeDrawingPersonalAuthThreeActivity.class);
                                }
                                intent.putExtra("process",activity.process);
                                intent.putExtra("status",activity.status);
                                intent.putExtra("managerName",managerName);
                            }

                            ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);

                        }else {
                            activity.dialogTitle = "绑定失败";
                            activity.setDialog("手机号绑定失败，请检查后，重新操作。");

                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);

                    }
                });
    }

    @Override
    public void unBindPhone(Context context, String userId, String phone, String verificationCode, String status, String process) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("phone",phone);
        params.put("verificationCode",verificationCode);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        PayAuthPhoneActivity activity =  (PayAuthPhoneActivity) context;
        payAuthServiceManage.unBindPhone(userId,phone,verificationCode)
                .subscribe(new BaseObserver<Map<String,String>>(context,true){
                    @Override
                    public void onSuccess(Map<String,String> result,String msg) {

                        String resultStatus = result.get("status");

                        if("OK".equals(resultStatus)){
                            Intent intent = new Intent(context,TradeDrawingPersonalAuthTwoActivity.class);
//                            intent.putExtra("process",activity.process);
                            intent.putExtra("status",activity.status);
                            ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);

                        }else {
//                            activity.dialogTitle = "绑定失败";
//                            activity.setDialog("手机号绑定失败，请检查后，重新操作。");
                            ToastUtils.showShort("解绑失败");

                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);

                    }
                });
    }

    @Override
    public void unbindBankCard(Context context, String userId, String cardNo) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("cardNo",cardNo);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        PayAuthUnboundActivity activity =  (PayAuthUnboundActivity) context;
        payAuthServiceManage.unbindBankCard(userId,cardNo)
                .subscribe(new BaseObserver<Map<String,String>>(context,true){
                    @Override
                    public void onSuccess(Map<String,String> result,String msg) {

                        String resultStatus = result.get("status");

                        if("OK".equals(resultStatus)){
                            Intent intent = new Intent(context,TradeDrawingPersonalAuthThreeActivity.class);
                            intent.putExtra("status",activity.status);
                            ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);

                        }else {
//                            activity.dialogTitle = "绑定失败";
//                            activity.setDialog("手机号绑定失败，请检查后，重新操作。");
                            ToastUtils.showShort("解绑失败");

                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);

                    }
                });
    }

    //支付认证查询
    @Override
    public void selectPaymentAuthInfo(Context context, String userId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);

        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        PayAuthComplateActivity activity =  (PayAuthComplateActivity) context;
        payAuthServiceManage.selectPaymentAuthInfo(userId)
                .subscribe(new BaseObserver<Map<String,String>>(context,true){
                    @Override
                    public void onSuccess(Map<String,String> result,String msg) {

                        /**
                         * result:{"managerTel":"18633333333","type":"0","cardNo":"B"}
                         * type  类型：0个人认证，1企业企业认证
                         *
                         *  "cardNo", "B"银行卡绑定 ，  "cardNo", "NB"银行卡未绑定，"managerTel", "NP"  手机未绑定;
                         * */
                        if(result != null && result.size()>0){

                            activity.type = result.get("type");
                            activity.managerTel = result.get("managerTel");
                            activity.cardNo = result.get("cardNo");
                            if("0".equals(activity.type)){
                                activity.tv_auth_type.setText("个人认证");
                            }else {
                                activity.tv_auth_type.setText("企业认证");
                            }



                            if(activity.managerTel != null && !"".equals(activity.managerTel)){
                                if("NP".equals(activity.managerTel)){
                                    activity.tv_phone_status.setText("未绑定");
                                    activity.status = "NA";
                                    activity.tv_phone_status.setTextColor(context.getResources().getColor(R.color.font_color_red));
                                }else { //否则展示手机号
                                    activity.status = "A";
                                    activity.tv_phone_status.setTextColor(context.getResources().getColor(R.color.font_color_gray_dark));
                                    activity.tv_phone_status.setText(activity.managerTel);
                                }

                            }

                            if(activity.cardNo != null && !"".equals(activity.cardNo)){
                                if("B".equals(activity.cardNo)){
                                    activity.tv_bank_status.setText("已绑定");
                                    activity.status = "A";
                                    activity.tv_bank_status.setTextColor(context.getResources().getColor(R.color.font_color_blue));
                                }else if("NB".equals(activity.cardNo)){ //未绑定
                                    activity.tv_bank_status.setText("未绑定");
                                    activity.status = "NA";
                                    activity.tv_bank_status.setTextColor(context.getResources().getColor(R.color.font_color_red));
                                }

                            }



                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);


                    }
                });
    }


    @Override
    public void createMember(Context context, String userId, String memberType) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("memberType",memberType);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        PayAuthNoComplateActivity activity =  (PayAuthNoComplateActivity) context;
        payAuthServiceManage.createMember(userId,memberType)
                .subscribe(new BaseObserver<Map<String,String>>(context,true){
                    @Override
                    public void onSuccess(Map<String,String> result,String msg) {

                        String status = result.get("status");
                        Intent intent = null;
                        //error 表示失败
                        if("OK".equals(status)){
                            if("3".equals(PayAuthNoComplateActivity.memberType)){  //个人
                                intent = new Intent(context,TradeDrawingPersonalAuthOneActivity.class);
                                intent.putExtra("type","0");
                            }else if("2".equals(PayAuthNoComplateActivity.memberType)){ //企业
                                intent = new Intent(context,TradeDrawingEnterpriseAuthOneActivity.class);
                                intent.putExtra("type","1");
                            }
                            intent.putExtra("status",activity.status);
                            intent.putExtra("process",activity.process);
                            ActivityUtils.startActivity(intent,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }else {
                            activity.setDialog("暂无会员");
                        }



                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);

                    }
                });

    }

    @Override
    public void selectPersonalOrCompanyAuthInfo(Context context, String userId,String status,String process) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        payAuthServiceManage.selectPersonalOrCompanyAuthInfo(userId)
                .subscribe(new BaseObserver<PayAuthInfoResultBean>(context,true){
                    @Override
                    public void onSuccess(PayAuthInfoResultBean result,String msg) {

                        if(result != null){
                            PayAuthInfoResultBean.PayAuthInfoBean payAuthInfoBean = result.getCompIdentity();
                            if(payAuthInfoBean!= null){
                                if("N".equals(status)){//未认证
                                    if("PI".equals(process)){ //个人认证信息
                                        TradeDrawingPersonalAuthOneActivity activity =  (TradeDrawingPersonalAuthOneActivity)context;
                                    }else if("CI".equals(process)){
                                        TradeDrawingEnterpriseAuthOneActivity activity =  (TradeDrawingEnterpriseAuthOneActivity)context;
                                    }else if("PP".equals(process)||"CP".equals(process)){ //跳企业绑定手机号
                                        TradeDrawingPersonalAuthTwoActivity activity =  (TradeDrawingPersonalAuthTwoActivity)context;
                                    }else if("PB".equals(process)||"CS".equals(process)){ //跳企业签订协议
                                        TradeDrawingPersonalAuthThreeActivity activity =  (TradeDrawingPersonalAuthThreeActivity) context;
                                        activity.name = payAuthInfoBean.getIdentityName();
                                        activity.identityNo = payAuthInfoBean.getLegalId();
                                        activity.phone = payAuthInfoBean.getPhone();
                                        if(activity.name != null &&  !"".equals(activity.name)){
                                            activity.edt_name.setText(activity.name);
                                        }
                                    }
                                }else if("A".equals(status) ||"NA".equals(status)){//  process  ：N 跳选择认证界面   CI  跳企业认证，CP 跳企业绑定手机，CS  跳企业签订协议，PI  跳个人认证第一步信息，PP 跳个人绑定手机 ，PB跳个人绑定银行卡，PS  跳企业签订协议

                                    PayAuthPhoneActivity activity =  (PayAuthPhoneActivity) context;
                                    activity.phone = payAuthInfoBean.getManagerTel();
                                    activity.name = payAuthInfoBean.getManagerName();

                                    if(activity.phone != null && !"".equals(activity.phone)){
                                        activity.edt_phone.setText(activity.phone);
                                    }
                                    if(activity.name != null && !"".equals(activity.name)){

                                        activity.tv_name.setText(activity.name);
                                    }
                                }else if("0".equals(status)){  //个人认证信息
                                    PayAuthInfoActivity activity =  (PayAuthInfoActivity) context;
                                    activity.name = payAuthInfoBean.getIdentityName();
                                    activity.identityNo = payAuthInfoBean.getLegalId();
                                    activity.phone = payAuthInfoBean.getPhone();
                                    if(activity.name != null &&  !"".equals(activity.name)){
                                        activity.tv_name.setText(activity.name);
                                    }

                                    if(activity.name != null &&  !"".equals(activity.name)){
                                        activity.tv_auth_cert.setText(activity.name);
                                    }

                                    if(activity.identityNo != null &&  !"".equals(activity.identityNo)){
                                        activity.tv_id_card.setText(activity.identityNo);
                                    }
                                }else if("1".equals(status)){  //企业认证信息
//                                    TradeDrawingEnterpriseInfoActivity activity =  (TradeDrawingEnterpriseInfoActivity) context;




                                }else if("UpdatePhone".equals(status)){  //企业认证信息
                                    PayAuthPhoneActivity activity =  (PayAuthPhoneActivity) context;
                                    activity.phone = payAuthInfoBean.getManagerTel();
                                    activity.name = payAuthInfoBean.getManagerName();

                                    if(activity.phone != null && !"".equals(activity.phone)){
                                        activity.edt_phone.setText(activity.phone);
                                    }
                                    if(activity.name != null && !"".equals(activity.name)){

                                        activity.tv_name.setText(activity.name);
                                    }

                                }else if("BindPhone".equals(status)){  //变更手机号
                                    PayAuthPhoneActivity activity =  (PayAuthPhoneActivity) context;
                                    activity.phone = payAuthInfoBean.getManagerTel();
                                    activity.name = payAuthInfoBean.getManagerName();

                                    if(activity.phone != null && !"".equals(activity.phone)){
                                        activity.edt_phone.setText(activity.phone);
                                    }
                                    if(activity.name != null && !"".equals(activity.name)){

                                        activity.tv_name.setText(activity.name);
                                    }

                                }else if("UnBindBank".equals(status)){// 解绑银行卡
                                    PayAuthUnboundActivity activity =  (PayAuthUnboundActivity) context;
                                    activity.phone = payAuthInfoBean.getPhone();
                                    activity.name = payAuthInfoBean.getIdentityName(); //开户名
                                    activity.bankName = payAuthInfoBean.getBankName(); //开户行
                                    activity.cardNo = payAuthInfoBean.getCardNo();  //开户银行卡账号


                                    if(activity.phone != null && !"".equals(activity.phone)){
                                        activity.tv_bank_phone.setText(activity.phone);
                                    }
                                    if(activity.name != null && !"".equals(activity.name)){

                                        activity.tv_name.setText(activity.name);
                                    }


                                    if(activity.bankName != null && !"".equals(activity.bankName)){

                                        activity.tv_bank_name.setText(activity.bankName);
                                    }

                                    if(activity.cardNo != null && !"".equals(activity.cardNo)){

                                        activity.tv_bank_card.setText(activity.cardNo);
                                    }
                                }else if("BindBank".equals(status)){// 绑定银行卡
                                    TradeDrawingPersonalAuthThreeActivity activity =  (TradeDrawingPersonalAuthThreeActivity) context;
                                    activity.phone = payAuthInfoBean.getPhone();
                                    activity.name = payAuthInfoBean.getIdentityName(); //开户名
                                    activity.identityNo = payAuthInfoBean.getLegalId();
                                    activity.bankName = payAuthInfoBean.getBankName(); //开户行

                                    if(activity.name != null && !"".equals(activity.name)){

                                        activity.edt_name.setText(activity.name);
                                    }

                                }
                            }

                        }




                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);


                    }
                });
    }

    @Override
    public void selectCompanyAuthInfo(Context context, String userId, String status, String process) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        TradeDrawingEnterpriseInfoActivity activity = (TradeDrawingEnterpriseInfoActivity)context;
        payAuthServiceManage.selectCompanyAuthInfo(userId)
                .subscribe(new BaseObserver<CompIdentityResultInfoBean>(context,true){
                    @Override
                    public void onSuccess(CompIdentityResultInfoBean result,String msg) {

                        /**
                         *  private  String imgType = ""; //1表示营业执照，2 表示身份证正面 3 表示身份证反面
                         protected String userId ; //
                         protected String companyName ;//企业名称
                         protected String businessLicenseImg ;//企业营业执照
                         protected String uniCredit ;//统一社会信用代码
                         protected String legalName ;//法人姓名
                         protected String legalPhone ;//法人手机号

                         protected String legalIds ;//法人身份证
                         protected String legalIdcard ;//身份证正面
                         protected String legalIdcardBack ;//身份证反面
                         protected String accountNo ;//对公账户
                         protected String parentBankName ;//开户行名称
                         protected String province ;//开户行所在省

                         protected String city ;//city开户行所在市
                         protected String status ;
                         protected String process ;
                         protected String type;  //1 企业认证类型
                         *
                         * */

                        /**
                         * "compIdentityDetailed":{"auditStatus":"0","businessIicenseImg":"5cde8efa41c9af2318e20120","businessLicense":"216464dhdhcj","city":"济南市","compId":281697771028480,"compIdentityId":406304434896896,"companyName":"大炮筒",
                         * "legalIdcard":"5cde8efa41c9af2318e20120","legalIdcardBack":"5cde8efa41c9af2318e20120","legalPhone":"dapao","province":"山东省"},"compIdentity":{"bankCode":"01030000","bankName":"农业银行","cardNo":"6228488888888888888","cardType":"1",
                         * "compId":281697771028480,"contractNo":"","id":406304434896896,
                         * "identityName":"216464dhdhcj","identityType":"1","legalId":"18699999999","managerName":"","managerTel":"","phone":"dapao","status":"","type":"1"}}
                         * */
                        if(result != null){
                            CompIdentityResultInfoBean.CompIdentityBean compIdentityBean = result.getCompIdentity();
                            CompIdentityResultInfoBean.CompIdentityDetailedBean detailedBean = result.getCompIdentityDetailed();
                            if(compIdentityBean!= null){
                                activity.legalName = compIdentityBean.getIdentityName();
                                if(activity.legalName !=null &&  !"".equals(activity.legalName)){
                                    activity.edt_legal_person.setText(activity.legalName);
                                }else {
                                    activity.edt_legal_person.setText("");
                                }


                                activity.legalIds = compIdentityBean.getLegalId();
                                if(activity.legalIds !=null &&  !"".equals(activity.legalIds)){
                                    activity.edt_legal_id_num.setText(activity.legalIds);
                                }else {
                                    activity.edt_legal_id_num.setText("");
                                }
                                activity.accountNo = compIdentityBean.getCardNo();
                                if(activity.accountNo !=null &&  !"".equals(activity.accountNo)){
                                    activity.edt_duigong_account.setText(activity.accountNo);
                                }else {
                                    activity.edt_duigong_account.setText("");
                                }


                                activity.parentBankName = compIdentityBean.getBankName();
                                if(activity.parentBankName !=null &&  !"".equals(activity.parentBankName)){
                                    activity.edt_bank_name.setText(activity.parentBankName);
                                }else {
                                    activity.edt_bank_name.setText("");
                                }
                            }

                            if(detailedBean != null){

                                //企业民称
                                activity.companyName = detailedBean.getCompanyName();
                                if(activity.companyName !=null &&  !"".equals(activity.companyName)){
                                    activity.edt_enterprise_name.setText(activity.companyName);
                                }else {
                                    activity.edt_enterprise_name.setText("");
                                }

                                //身份证
                                activity.businessLicenseImg =HttpUrls.IMAGE_ULR+detailedBean.getBusinessIicenseImg();
                                if(activity.businessLicenseImg !=null &&  !"".equals(activity.businessLicenseImg)){
                                    activity.setImage(activity.businessLicenseImg,"1");
                                }

                                //统一社会代码
                                activity.uniCredit = detailedBean.getBusinessLicense();
                                if(activity.uniCredit !=null &&  !"".equals(activity.uniCredit)){
                                    activity.edt_credit_code.setText(activity.uniCredit);
                                }else {
                                    activity.edt_credit_code.setText("");
                                }

                                activity.legalPhone = detailedBean.getLegalPhone();
                                if(activity.legalPhone !=null &&  !"".equals(activity.legalPhone)){
                                    activity.edt_legal_phone.setText(activity.legalPhone);
                                }else {
                                    activity.edt_legal_phone.setText("");
                                }



                                activity.legalIdcard =HttpUrls.IMAGE_ULR+detailedBean.getLegalIdcard();
                                if(activity.legalIdcard !=null &&  !"".equals(activity.legalIdcard)){
                                    activity.setImage(activity.legalIdcard,"2");
                                }
                                activity.legalIdcardBack =HttpUrls.IMAGE_ULR+detailedBean.getLegalIdcardBack();
                                if(activity.legalIdcard !=null &&  !"".equals(activity.legalIdcard)){
                                    activity.setImage(activity.legalIdcardBack,"3");
                                }

                                activity.province = detailedBean.getProvince();
                                activity.city = detailedBean.getCity();
                                if(activity.province !=null &&  !"".equals(activity.province)){

                                    if(activity.city !=null &&  !"".equals(activity.city)){
                                        activity.edt_bank_address.setText(activity.province+activity.city);
                                    }else {
                                        activity.edt_bank_address.setText(activity.province);
                                    }

                                }else {
                                    activity.edt_bank_address.setText("");
                                }
                            }

                        }




                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);


                    }
                });
    }

    @Override
    public void bindBankCard(Context context, String userId, String cardNo, String name, String identityNo, String bankName, String phone, String status, String process) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("cardNo",cardNo);
        params.put("name",name);
        params.put("identityNo",identityNo);
        params.put("bankName",bankName);
        params.put("phone",phone);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        TradeDrawingPersonalAuthThreeActivity activity = (TradeDrawingPersonalAuthThreeActivity)context;
        payAuthServiceManage.bindBankCard(userId,cardNo,name,identityNo,bankName,phone)
                .subscribe(new BaseObserver<Map<String,String>>(context,true){
                    @Override
                    public void onSuccess(Map<String,String> result,String msg) {
                        String resultStatus = result.get("status");
                        Intent intent = null;
                        if("OK".equals(resultStatus)){

                            if("A".equals(status)||"NA".equals(status)){
                                intent = new Intent(context,PayAuthComplateActivity.class);
                                intent.putExtra("process",activity.process);
                                intent.putExtra("status",activity.status);
                            }else {
                                intent = new Intent(context,PayAuthAgreeActivity.class);
                                intent.putExtra("process",activity.process);
                                intent.putExtra("status",activity.status);
                            }

                            ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);

                        }else {
                            activity.dialogTitle = "绑定失败";
                            activity.setDialog("银行卡绑定失败，请检查后，重新操作。");
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);

                    }
                });
    }

    @Override
    public void signContractNotice(Context context, String userId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        PayAuthAgreeActivity activity = (PayAuthAgreeActivity)context;
        payAuthServiceManage.signContractNotice(userId)
                .subscribe(new BaseObserver<Map<String,String>>(context,true){
                    @Override
                    public void onSuccess(Map<String,String> result,String msg) {

                    /*    String resultStatus = result.get("status");

                        if("OK".equals(resultStatus)){
                            Intent intent = new Intent(context,PayAuthComplateActivity.class);
                            intent.putExtra("process",activity.process);
                            intent.putExtra("status",activity.status);
                            ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);

                        }else {
                            ToastUtils.showShort("签订协议失败");

                        }*/

                        activity.webParamUrl  = result.get("webParamUrl").trim();
                        activity.setUrl();

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);

                    }
                });
    }

    //企业认证上传图片
    @Override
    public void uploadFiles(Context context, List<File> fileList, String selectImgType) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        TradeDrawingEnterpriseAuthOneActivity activity = (TradeDrawingEnterpriseAuthOneActivity)context;

        campaignServiceManager.uploadFiles(fileList)
                .subscribe(new BaseObserver<Map<String,Object>>(context,false){
                    @Override
                    public void onSuccess(Map<String,Object> map, String msg) {
                        String imageId = "";
                        if(map != null && map.size()>0) {
                            imageId = (String) map.get("id");

                            if(imageId != null && !"".equals(imageId)){

                                if("1".equals(selectImgType)){
                                    activity.businessLicenseImg = imageId;
                                    activity.zzPath = HttpUrls.IMAGE_ULR +imageId;
                                    activity.setImage(selectImgType,activity.zzPath);
                                }else if("2".equals(selectImgType)){
                                    activity.idCardZPath = HttpUrls.IMAGE_ULR +imageId;
                                    activity.setImage(selectImgType,activity.zzPath);
                                    activity.legalIdcard = imageId;
                                }else if("3".equals(selectImgType)){
                                    activity.idCardFPath = HttpUrls.IMAGE_ULR +imageId;
                                    activity.setImage(selectImgType,activity.zzPath);
                                    activity.legalIdcardBack = imageId;
                                }
                            }


                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                    }
                });


    }
    //设置企业认证信息
    @Override
    public void setCompanyAuthInfo(Context context, String userId,String companyName,String businessLicenseImg, String uniCredit, String legalName, String legalPhone, String legalIds,
                                   String legalIdcard, String legalIdcardBack, String accountNo, String parentBankName, String province, String city, String status) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("companyName",companyName);
        params.put("businessLicenseImg",businessLicenseImg);
        params.put("uniCredit",uniCredit);
        params.put("legalName",legalName);
        params.put("legalPhone",legalPhone);
        params.put("legalIds",legalIds);
        params.put("legalIdcard",legalIdcard);

        params.put("legalIdcardBack",legalIdcardBack);
        params.put("accountNo",accountNo);
        params.put("parentBankName",parentBankName);
        params.put("province",province);
        params.put("city",city);

        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        TradeDrawingEnterpriseAuthOneActivity activity = (TradeDrawingEnterpriseAuthOneActivity)context;
        payAuthServiceManage.setCompanyAuthInfo(userId,companyName,businessLicenseImg,uniCredit,legalName,legalPhone,legalIds,legalIdcard,legalIdcardBack,accountNo,parentBankName,province,city)
                .subscribe(new BaseObserver<Map<String,String>>(context,true){
                    @Override
                    public void onSuccess(Map<String,String> result,String msg) {
                        String resultStatus = result.get("status");
                        Intent intent = null;
                        if("OK".equals(resultStatus)){
                            intent = new Intent(context,TradeDrawingPersonalAuthTwoActivity.class);
                            intent.putExtra("process",activity.process);
                            intent.putExtra("status",activity.status);

                            ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);

                        }else {
                            activity.dialogTitle = "设置企业认证信息失败";
                            activity.setDialog("设置企业认证信息失败，请检查后，重新操作。");
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);

                    }
                });


    }


    @Override
    public void queryMyUser(Context context, String userId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        PersonalThreeFragment fragment =  (PersonalThreeFragment)personalView;
        personalManager.queryUser(userId)
                .subscribe(new BaseObserver<PersonalBean>(context,false){
                    @Override
                    public void onSuccess(PersonalBean personalBean,String msg) {
                        if(personalBean != null ){

                            String username = personalBean.getUserName();
                            String nickName = personalBean.getNickName();
                            fragment.tvName.setText(nickName != null && !"".equals(nickName)?nickName:"");
//                            fragment.tvAccount.setText(username != null && !"".equals(username)?username:"");

                            List<Store> storeList = personalBean.getStoreList();

                            List<Role> roleList = personalBean.getRoleList();

                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);



                    }
                });
    }
}
