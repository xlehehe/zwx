package com.zwx.scan.app.feature.staffmanage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.EncryptUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TextColorSizeUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.base.BaseObserver;
import com.zwx.scan.app.data.bean.CampaignNonrewardPic;
import com.zwx.scan.app.data.bean.CardBean;
import com.zwx.scan.app.data.bean.CardQrcBean;
import com.zwx.scan.app.data.bean.DirectoryData;
import com.zwx.scan.app.data.bean.MemCoupon;
import com.zwx.scan.app.data.bean.Member;
import com.zwx.scan.app.data.bean.PushPreMessageBean;
import com.zwx.scan.app.data.bean.PushTypeBean;
import com.zwx.scan.app.data.bean.Pushnotify;
import com.zwx.scan.app.data.bean.ResultBean;
import com.zwx.scan.app.data.bean.StaffBean;
import com.zwx.scan.app.data.bean.StaffQRCode;
import com.zwx.scan.app.data.bean.StaffWork;
import com.zwx.scan.app.data.bean.VerificationRecord;
import com.zwx.scan.app.data.http.service.RetrofitServiceManager;
import com.zwx.scan.app.data.http.service.StaffManageServiceManager;
import com.zwx.scan.app.data.http.service.VerificationServiceManager;
import com.zwx.scan.app.feature.verification.VerificationActivity;
import com.zwx.scan.app.feature.verificationrecord.RecordCouponTotalAdapter;
import com.zwx.scan.app.feature.verificationrecord.RecyclerDetailAdapter;
import com.zwx.scan.app.feature.verificationrecord.VerificationRecordActivity;
import com.zwx.scan.app.feature.verificationrecord.VerificationRecordDetailActivity;
import com.zwx.scan.app.widget.dialog.DialogUIUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author lizhilong
 * @version 1.0
 * @desc
 * @time 2018/10/16
 */
public class StaffManagePresenter implements StaffManageContract.Presenter {

    private  StaffManageContract.View view;
    private StaffManageServiceManager staffManageServiceManager;

    //添加订阅，应用于取消订阅，防止造成内存泄漏
    private CompositeDisposable disposable;

    public StaffManagePresenter(StaffManageContract.View view) {
        this.view = view;

        staffManageServiceManager = new StaffManageServiceManager();
        this.disposable = new CompositeDisposable();

    }


    @Override
    public void listAvailableByStoreId(Context context,String storeId) {

        StaffEditActivity activity = (StaffEditActivity)context;
        RetrofitServiceManager.setHeadTokenMap(null);
        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        authMap.put("storeId",storeId);
        authMap.put("token",token );
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        staffManageServiceManager.listAvailableByStoreId(storeId)
                .subscribe(new BaseObserver<List<StaffQRCode>>(context,false){
                    @Override
                    public void onSuccess(List<StaffQRCode> staffQRCodes,String msg) {
                        activity.staffQRCodeList=staffQRCodes;
                        List<CardQrcBean> cardQrcBeans = new ArrayList<>();
                        CardQrcBean cardQrcBean = new CardQrcBean();
                        cardQrcBean.setCardNo("无");
                        cardQrcBean.setId("0");
                        cardQrcBeans.add(cardQrcBean);
                        for(StaffQRCode data : activity.staffQRCodeList){
                            CardQrcBean cardBean = new CardQrcBean();
                            if(data != null){
                                cardBean.setId(String.valueOf(data.getId()));
                                cardBean.setCardNo(data.getNos().intValue() + "号二维码");
                                cardQrcBeans.add(cardBean);
                            }
                        }
                        activity.cardQrcBeans = cardQrcBeans;

                        activity.pvQrc.setPicker(activity.cardQrcBeans);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                    }
                });
    }

    @Override
    public void loadMemberById(Context context,String memberId) {
        StaffEditActivity activity = (StaffEditActivity)context;
        RetrofitServiceManager.setHeadTokenMap(null);
        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        authMap.put("memberId",memberId);
        authMap.put("token",token );
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        staffManageServiceManager.loadMemberById(memberId)
                .subscribe(new BaseObserver<Member>(context,false){
                    @Override
                    public void onSuccess(Member member,String msg) {
                        if(member != null){
                            activity.edtName.setText(member.getMemberName());
                            activity.edtPhone.setText(member.getMemberTel());

                            if(member.getSexType() != null && member.getSexType() == 1){
                                activity.tvSex.setText("男");
                                activity.sex = "1";
                            }

                            if(member.getSexType() != null && member.getSexType() == 0){
                                activity.tvSex.setText("女");
                                activity.sex = "0";
                            }
                            if(member.getBrirthday()!=null && !"".equals(member.getBrirthday())){
                                Date date = com.zwx.library.utils.DateUtils.parse(member.getBrirthday(),"yyyy-MM-dd");
                                String  birthday = com.zwx.library.utils.DateUtils.formatDate(date,"yyyy-MM-dd");

                                activity.tvBirthday.setText(birthday);
                            }else {
                                activity.tvEntry.setText("");
                            }

                            if(member.getJoinSysTime()!=null && !"".equals(member.getJoinSysTime())){
                                Date date2 = com.zwx.library.utils.DateUtils.parse(member.getJoinSysTime(),"yyyy-MM-dd");
                                String  joinTime = com.zwx.library.utils.DateUtils.formatDate(date2,"yyyy-MM-dd");


                                if(activity.isMember!=null&& "YES".contains(activity.isMember)){
                                    activity.tvEntry.setText(com.zwx.library.utils.DateUtils.formatDate(System.currentTimeMillis()));
                                }else {
                                    activity.tvEntry.setText(joinTime);
                                }
                            }else {
                                activity.tvEntry.setText(com.zwx.library.utils.DateUtils.formatDate(System.currentTimeMillis()));
                            }

                            String catId = "37861829885000";
                            doPosition(activity,catId);
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
    public void checkMobile(Context context,String storeId, String mobile) {
        StaffEditActivity activity = (StaffEditActivity)context;
        RetrofitServiceManager.setHeadTokenMap(null);
        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        authMap.put("storeId",storeId);
        authMap.put("mobile",mobile);
        authMap.put("token",token );
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        staffManageServiceManager.checkMobile(storeId,mobile)
                .subscribe(new BaseObserver<ResultBean>(context,false){
                    @Override
                    public void onSuccess(ResultBean resultBean,String msg) {
                       String staffMemberId = (String)resultBean.getResult();
                        activity.newPhoneMemberId = staffMemberId;


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);
                    }
                });
    }

    @Override
    public void doInsertStaff(Context context,String name, String telphone, String memberId,
                              String position, String sex, String birthday, String joinday,String pullNewCode, String storeId) {

        StaffEditActivity activity = (StaffEditActivity)context;
        RetrofitServiceManager.setHeadTokenMap(null);
        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        authMap.put("name",name);
        authMap.put("telphone",telphone);
        authMap.put("memberId",memberId);
        authMap.put("position",position);
        authMap.put("sex",sex);
        authMap.put("birthday",birthday);
        authMap.put("joinday",joinday);
        authMap.put("pullNewCode",pullNewCode);
        authMap.put("storeId",storeId);
        authMap.put("token",token );
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        staffManageServiceManager.doInsertStaff(name,telphone,memberId,position,sex,birthday,joinday,pullNewCode,storeId)
                .subscribe(new BaseObserver<ResultBean>(context,false){
                    @Override
                    public void onSuccess(ResultBean resultBean,String msg) {
                        ToastUtils.showShort("员工保存成功");
                        ActivityUtils.startActivity(StaffListActivity.class,R.anim.slide_in_right,R.anim.slide_out_left);

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort("员工保存失败");

                    }
                });
    }

    @Override
    public void doUpdate(Context context,String id, String staffId, String status, String name, String telphone, String position, String sex, String birthday, String joinday, String quitday, String pullNewCode) {
        StaffEditActivity activity = (StaffEditActivity)context;
        RetrofitServiceManager.setHeadTokenMap(null);
        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        authMap.put("id",id);
        authMap.put("staffId",staffId);
        authMap.put("status",status);
        authMap.put("name",name);
        authMap.put("telphone",telphone);
        authMap.put("position",position);
        authMap.put("sex",sex);
        authMap.put("birthday",birthday);
        authMap.put("joinday",joinday);
        authMap.put("quitday",quitday);
        authMap.put("pullNewCode",pullNewCode);
        authMap.put("token",token );
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        staffManageServiceManager.doUpdate(id,staffId,status,name,telphone,position,sex,birthday,joinday,quitday,pullNewCode)
                .subscribe(new BaseObserver<ResultBean>(context,false){
                    @Override
                    public void onSuccess(ResultBean resultBean,String msg) {

                        ToastUtils.showShort(msg);
                        ActivityUtils.startActivity(StaffListActivity.class,R.anim.slide_in_right,R.anim.slide_out_left);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                    }
                });
    }

    @Override
    public void doLeave(Context context,String ids, String position, String quitTime) {
        StaffEditActivity activity = (StaffEditActivity)context;
        RetrofitServiceManager.setHeadTokenMap(null);
        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        authMap.put("ids",ids);
        authMap.put("position",position);
        authMap.put("quitTime",quitTime);
        authMap.put("token",token );
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        staffManageServiceManager.doLeave(ids,position,quitTime)
                .subscribe(new BaseObserver<ResultBean>(context,false){
                    @Override
                    public void onSuccess(ResultBean resultBean,String msg) {


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                    }
                });
    }

    @Override
    public void list(Context context,int pageNumber, int pageSize, String storeId,boolean isRefresh) {
        StaffListActivity activity = (StaffListActivity)context;
        RetrofitServiceManager.setHeadTokenMap(null);
        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        authMap.put("pageNumber",String.valueOf(pageNumber));
        authMap.put("pageSize",String.valueOf(pageSize));
        authMap.put("storeId",storeId);
        authMap.put("token",token );
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        staffManageServiceManager.list(pageNumber,pageSize,storeId)
                .subscribe(new BaseObserver<StaffBean>(context,false){
                    @Override
                    public void onSuccess(StaffBean staffBean,String msg) {

                        if(staffBean != null){
                            List<StaffWork> dataList = staffBean.getData();

                            if(dataList != null && dataList.size()>0){
                                if (dataList != null && dataList.size() > 0) {
                                    activity.staffWorks.addAll(dataList);
                                    activity.listAdapter.notifyDataSetChanged();
                                    activity.ptr.setPullToRefreshOverScrollEnabled(true);
                                    if (dataList.size() < 10) {
                                        activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                    } else {
                                        activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                    }
                                    activity.ll_no_data.setVisibility(View.GONE);
                                    activity.listView.setVisibility(View.VISIBLE);

                                } else {
                                    activity.staffWorks.addAll(dataList);
                                    activity.listAdapter.notifyDataSetChanged();

                                    if(activity.staffWorks != null  &&  activity.staffWorks.size()>0){
                                        if(activity.staffWorks.size() < 10){
                                            activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                        }else {
                                            activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                        }

                                        activity.ll_no_data.setVisibility(View.GONE);
                                        activity.listView.setVisibility(View.VISIBLE);
                                    }else {

                                        activity.ll_no_data.setVisibility(View.VISIBLE);
                                        activity.listView.setVisibility(View.GONE);
                                        activity.tv_no_data.setText("您现在没有员工列表记录哦！");
                                    }


                                }

                            }else {

                                if(activity.staffWorks != null  &&  activity.staffWorks.size()>0){
                                    if(activity.staffWorks.size() < 10){
                                        activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                    }else {
                                        activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                    }

                                    activity.ll_no_data.setVisibility(View.GONE);
                                    activity.listView.setVisibility(View.VISIBLE);
                                }else {

                                    activity.ll_no_data.setVisibility(View.VISIBLE);
                                    activity.listView.setVisibility(View.GONE);
                                    activity.tv_no_data.setText("您现在没有员工列表记录哦！");
                                }
                            }
                        }else {
                            if(activity.staffWorks != null  &&  activity.staffWorks.size()>0){
                                if(activity.staffWorks.size() < 10){
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                }else {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }

                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.listView.setVisibility(View.VISIBLE);
                            }else {

                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.listView.setVisibility(View.GONE);
                                activity.tv_no_data.setText("您现在没有员工列表记录哦！");
                            }
                        }
                        activity.ptr.onRefreshComplete();
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.listAdapter.notifyDataSetChanged();
                        activity.listView.setVisibility(View.GONE);
                        activity.ll_no_data.setVisibility(View.VISIBLE);
                    /*    activity.iv_no_data.setImageResource(R.drawable.ic_wifi_not);
                        activity.tv_no_data.setText("您的网络可能睡着了");*/

                        if(activity.staffWorks != null  &&  activity.staffWorks.size()>0){
                            if(activity.staffWorks.size() < 10){
                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }else {
                                activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                            }

                            activity.ll_no_data.setVisibility(View.GONE);
                            activity.listView.setVisibility(View.VISIBLE);
                        }else {

                            activity.ll_no_data.setVisibility(View.VISIBLE);
                            activity.listView.setVisibility(View.GONE);
                            activity.iv_no_data.setImageResource(R.drawable.ic_wifi_not);
                            activity.tv_no_data.setText("您的网络可能睡着了！");
                        }

                    }
                });
    }

    @Override
    public void load(Context context,String id) {
        StaffEditActivity activity = (StaffEditActivity)context;
        RetrofitServiceManager.setHeadTokenMap(null);
        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        authMap.put("id",id);
        authMap.put("token",token );
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        staffManageServiceManager.load(id)
                .subscribe(new BaseObserver<StaffWork>(context,false){
                    @Override
                    public void onSuccess(StaffWork staffWork,String msg) {


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                    }
                });
    }


    @Override
    public void doPosition(Context context, String catId) {

        StaffEditActivity activity = (StaffEditActivity)context;
        RetrofitServiceManager.setHeadTokenMap(null);
        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        authMap.put("catId",catId);
        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        staffManageServiceManager.listByCatId(catId)
                .subscribe(new BaseObserver<List<DirectoryData>>(context,false){
                    @Override
                    public void onSuccess(List<DirectoryData> data,String msg) {
                        activity.directoryDataList = data;
                        List<CardBean> cardBeans = new ArrayList<>();
                        for(DirectoryData data1 : activity.directoryDataList){
                            CardBean cardBean = new CardBean();
                            if(data1 != null){
                                cardBean.setId(String.valueOf(data1.getId()));
                                cardBean.setCardNo(data1.getKey());
                                cardBeans.add(cardBean);
                            }
                        }
                        activity.cardBeans = cardBeans;

//                        activity.initPositionOptionPicker();
                        activity.pvPosition.setPicker(cardBeans);//添加数据
//                        activity.initPositionOptionPicker();
                        String storeId = SPUtils.getInstance().getString("storeId");
                        listAvailableByStoreId(activity,storeId);

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                    }
                });

    }

    @Override
    public void checkMobile(Context context, String name, String telphone, String memberId, String position,
                            String sex, String birthday, String joinday, String pullNewCode, String storeId) {

        StaffEditActivity activity = (StaffEditActivity)context;
        RetrofitServiceManager.setHeadTokenMap(null);
        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        authMap.put("storeId",storeId);
        authMap.put("mobile",telphone);
        authMap.put("token",token );
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        staffManageServiceManager.checkMobile(storeId,telphone)
                .subscribe(new BaseObserver<ResultBean>(context,false){
                    @Override
                    public void onSuccess(ResultBean resultBean,String msg) {

                      ToastUtils.showShort("该手机号已加入店铺！");


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
//                        ToastUtils.showShort(message);
                        doInsertStaff(context,name,telphone,memberId,position,sex,birthday,joinday,pullNewCode,storeId);
                    }
                });

    }

    @Override
    public void getPushType(Context context) {
        PullQrcManageActivity activity = (PullQrcManageActivity)context;
        RetrofitServiceManager.setHeadTokenMap(null);
        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

      /*  authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);*/
        staffManageServiceManager.getPushType()
                .subscribe(new BaseObserver<ArrayList<DirectoryData>>(context,false){
                    @Override
                    public void onSuccess(ArrayList<DirectoryData> resultBean,String msg) {
                        if(resultBean != null){
                            activity.directoryDataList = resultBean;
                            if(activity.directoryDataList != null && activity.directoryDataList.size()>0){
                                CardQrcBean cardQrcBean = new CardQrcBean();
                                cardQrcBean.setId("0");
                                cardQrcBean.setCardNo("请选择");
                                activity.cardQrcBeans.add(cardQrcBean);
                                for (DirectoryData directoryData : activity.directoryDataList){
                                     cardQrcBean = new CardQrcBean();
                                    cardQrcBean.setCardNo(directoryData.getKey());
                                    cardQrcBean.setId(directoryData.getValue());
                                    activity.cardQrcBeans.add(cardQrcBean);
                                    activity.pvTypeOne.setPicker(activity.cardQrcBeans);
                                    activity.pvTypeTwo.setPicker(activity.cardQrcBeans);
                                    activity.pvTypeThree.setPicker(activity.cardQrcBeans);

                                }

                            }

                            String userId = SPUtils.getInstance().getString("userId");
                            doPushLoad(context,userId,true);
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
    public void getContentByType(Context context, String userId, String pushType,int pageNumber, int pageSize,boolean isRefresh) {

        PullQrcSelectContentActivity activity = (PullQrcSelectContentActivity)context;
        RetrofitServiceManager.setHeadTokenMap(null);
        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        authMap.put("pageNumber",String.valueOf(pageNumber));
        authMap.put("pageSize",String.valueOf(pageSize));
        authMap.put("userId",userId);
        authMap.put("pushType",pushType);
        authMap.put("token",token );
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        staffManageServiceManager.getContentByType(userId,pushType,pageNumber,pageSize)
                .subscribe(new BaseObserver<List<Map<String,String>>>(context,false){
                    @Override
                    public void onSuccess(List<Map<String,String>> resultList,String msg) {
                        Map<String,String> noMap = new HashMap<>();

                        noMap.put("id","");
                        noMap.put("name","无");
                        noMap.put("isChecked","false");
                        if(resultList != null && resultList.size()>0){

                            for (Map<String,String> map : resultList){
                                map.put("isChecked","false");
                            }

                            if(isRefresh){
                                activity.list.clear();
                                activity.list.add(noMap);
                                activity.list.addAll(resultList);

                                if(PullQrcManageActivity.msgOneContentId != null && !"".equals(PullQrcManageActivity.msgOneContentId)){
                                    if(activity.list != null && activity.list.size()>0){
                                        for (int i = 0;i <activity.list.size();i++){
                                            Map<String,String> map = activity.list.get(i);

                                            String idStr = map.get("id");
                                            if(PullQrcManageActivity.msgOneContentId .equals(idStr)){
                                                activity.list.remove(i);
                                                i--;
                                                break;
                                            }
                                        }
                                    }
                                }

                                if(PullQrcManageActivity.msgTwoContentId != null && !"".equals(PullQrcManageActivity.msgTwoContentId)){
                                    if(activity.list != null && activity.list.size()>0){
                                        for (int i = 0;i <activity.list.size();i++){
                                            Map<String,String> map = activity.list.get(i);

                                            String idStr = map.get("id");
                                            if(PullQrcManageActivity.msgTwoContentId .equals(idStr)){
                                                activity.list.remove(i);
                                                i--;
                                                break;
                                            }
                                        }
                                    }
                                }

                                if(PullQrcManageActivity.msgThreeContentId != null && !"".equals(PullQrcManageActivity.msgThreeContentId)){
                                    if(activity.list != null && activity.list.size()>0){
                                        for (int i = 0;i <activity.list.size();i++){
                                            Map<String,String> map = activity.list.get(i);

                                            String idStr = map.get("id");
                                            if(PullQrcManageActivity.msgThreeContentId .equals(idStr)){
                                                activity.list.remove(i);
                                                i--;
                                                break;
                                            }
                                        }
                                    }
                                }
                                activity.mAdapter.notifyDataSetChanged();
                                activity.ptr.refreshComplete();
                                if(resultList.size()<10 ){
                                    activity.ptr.setLoadMoreEnable(false);
                                }else {

                                    activity.ptr.setLoadMoreEnable(true);
                                }
                            }else {
                                activity.list.add(noMap);
                                activity.list.addAll(resultList);
                                activity.mAdapter.notifyDataSetChanged();
                                activity.ptr.refreshComplete();
                                activity.ptr.loadMoreComplete(true);
                            }

                        }else {

                            activity.mAdapter.notifyDataSetChanged();
                            activity.ptr.refreshComplete();
                            activity.ptr.setLoadMoreEnable(false);
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);
                        activity.mAdapter.notifyDataSetChanged();
                        activity.ptr.refreshComplete();
                        activity.ptr.setLoadMoreEnable(false);

                    }
                });
    }

    @Override
    public void doPushUpdate(Context context, String userId, String pushTypes, String pushContentIds) {
        PullQrcManageActivity activity = (PullQrcManageActivity)context;
        RetrofitServiceManager.setHeadTokenMap(null);
        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        authMap.put("userId",userId);
        authMap.put("pushTypes",pushTypes);
        authMap.put("pushContentIds",pushContentIds);
        authMap.put("token",token );
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        staffManageServiceManager.doPushUpdate(userId,pushTypes,pushContentIds)
                .subscribe(new BaseObserver<String>(context,false){
                    @Override
                    public void onSuccess(String result,String msg) {

                        if("YES".equals(activity.isTip)){
                            ToastUtils.showCustomShortBottom("保存成功");
                        }

                        doPushLoad(context,userId,true);

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);

                    }
                });
    }

    @Override
    public void doPushLoad(Context context, String userId,boolean isLoad) {
        PullQrcManageActivity activity = (PullQrcManageActivity)context;
        RetrofitServiceManager.setHeadTokenMap(null);
        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        authMap.put("userId",userId);
        authMap.put("token",token );
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        staffManageServiceManager.doPushLoad(userId)
                .subscribe(new BaseObserver<List<Pushnotify>>(context,false){
                    @Override
                    public void onSuccess(List<Pushnotify> pushnotifyList,String msg) {


                        if(pushnotifyList != null && pushnotifyList.size()>0){
                            activity.pushnotifyList = pushnotifyList;
                            if(isLoad){
                                for (Pushnotify pushnotify:pushnotifyList){
                                    String pushType = pushnotify.getPushType();
                                    String pushContentId = pushnotify.getPushContentId();
//                                doPushContentByMessage(context,userId,pushType,pushContentId);
                                    Integer sort = pushnotify.getSort();

                                    if(sort != null && sort.intValue() ==1){
                                        activity.msgOneContentId = pushnotify.getPushContentId();
                                        activity.msgOneContent = pushnotify.getPushContentName();
                                        activity.msgOneType = pushnotify.getPushType();
                                        if(activity.msgOneContent != null && !"".equals(activity.msgOneContent)){

                                        }else {
                                            activity.msgOneContent = "无";
                                        }
                                        activity.sortOne = sort;
                                        activity.tv_msg_one.setText(pushnotify.getPushTypeName()!= null && !"".equals(pushnotify.getPushTypeName())?pushnotify.getPushTypeName():"请选择");
                                        activity.tv_msg_one_content.setText(activity.msgOneContent);

                                    }

                                    if(sort != null && sort.intValue() ==2){
                                        activity.sortTwo = sort;
                                        activity.msgTwoContentId = pushnotify.getPushContentId();
                                        activity.msgTwoContent = pushnotify.getPushContentName();
                                        if(activity.msgTwoContent != null && !"".equals(activity.msgTwoContent)){

                                        }else {
                                            activity.msgTwoContent = "无";
                                        }
                                        activity.msgTwoType = pushnotify.getPushType();

                                        activity.tv_msg_two.setText(pushnotify.getPushTypeName()!= null && !"".equals(pushnotify.getPushTypeName())?pushnotify.getPushTypeName():"请选择");
                                        activity.tv_msg_two_content.setText(activity.msgTwoContent);
                                    }

                                    if(sort != null && sort.intValue() ==3){
                                        activity.sortThree = sort;
                                        activity.msgThreeContentId = pushnotify.getPushContentId();
                                        activity.msgThreeContent = pushnotify.getPushContentName();
                                        activity.msgThreeType = pushnotify.getPushType();
                                        if(activity.msgThreeContent != null && !"".equals(activity.msgThreeContent)){

                                        }else {
                                            activity.msgThreeContent = "无";
                                        }

                                        activity.tv_msg_three.setText(pushnotify.getPushTypeName()!= null && !"".equals(pushnotify.getPushTypeName())?pushnotify.getPushTypeName():"请选择");
                                        activity.tv_msg_three_content.setText(activity.msgThreeContent);
                                    }

                                }
                            }else {
                                doPushContentListByMessage(context,pushnotifyList);
                            }



                        }else {
                            activity.tv_msg_one.setText("请选择");
                            activity.tv_msg_two.setText("请选择");
                            activity.tv_msg_three.setText("请选择");
                            activity.tv_msg_one_content.setText(activity.msgThreeContent);
                            activity.tv_msg_two_content.setText(activity.msgThreeContent);
                            activity.tv_msg_three_content.setText(activity.msgThreeContent);
                        }
//                        getPushType(context);

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);

                    }
                });
    }

    @Override
    public void doPushContentByMessage(Context context, String userId, String pushType, String pushContentId) {

        PullQrcManageActivity activity = (PullQrcManageActivity)context;
        RetrofitServiceManager.setHeadTokenMap(null);
        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        authMap.put("userId",userId);
        authMap.put("pushType",pushType);
        authMap.put("pushContentId",pushContentId);
        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        staffManageServiceManager.doPushContentByMessage(userId,pushType,pushContentId)
                .subscribe(new BaseObserver<PushPreMessageBean>(context,false){
                    @Override
                    public void onSuccess(PushPreMessageBean result,String msg) {

                        if(result != null){

                            if("1".equals(pushType)){

                                activity.banner = result.getBanner();
                                activity.shareTitle = result.getShareTitle();
                                activity.shareDesc = result.getShareDesc();
                            }else if("2".equals(pushType)){
                                activity.title = result.getTitle();
                                activity.first = result.getFirst();
                                activity.keyword1_title = result.getKeyword1_title();
                                activity.keyword1 = result.getKeyword1();
                                activity.keyword2_title = result.getKeyword2_title();
                                activity.keyword2 = result.getKeyword2();

                                activity.campcoupId = result.getCampcoupId();
                                activity.keyword3_title = result.getKeyword3_title();
                                activity.keyword3 = result.getKeyword3();

                                activity.keyword4_title = result.getKeyword4_title();
                                activity.keyword4 = result.getKeyword4();
                                activity.remark = result.getRemark();
                            }else if("3".equals(pushType)){
                                activity.title2 = result.getTitle();
                                activity.first2 = result.getFirst();
                                activity.keyword1_title2 = result.getKeyword1_title();
                                activity.keyword12 = result.getKeyword1();
                                activity.keyword2_title2 = result.getKeyword2_title();
                                activity.keyword22 = result.getKeyword2();

                                activity.keyword3_title2 = result.getKeyword3_title();
                                activity.keyword32 = result.getKeyword3();

                                activity.remark2 = result.getRemark();
                            }
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
    public void doPushContentListByMessage(Context context, List<Pushnotify> pushnotifyList) {
        PullQrcManageActivity activity = (PullQrcManageActivity)context;

        String jsonStr = "";
        Map<String,Object> jsonMap = new HashMap<String,Object>();
        try{


            String userId = SPUtils.getInstance().getString("userId");
            jsonMap.put("userId",userId);
            jsonMap.put("pushnotify",pushnotifyList);
            jsonStr = JSON.toJSONString(jsonMap);
        }catch (Exception e){

        }

        RetrofitServiceManager.setHeadTokenMap(null);
        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        authMap.put("jsonStr",jsonStr);
        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        staffManageServiceManager.doPushContentListByMessage(jsonStr)
                .subscribe(new BaseObserver<List<PushPreMessageBean>>(context,false){
                    @Override
                    public void onSuccess(List<PushPreMessageBean> result,String msg) {
                        if(result != null && result.size()>0){
                            activity.pushPreMessageList = result;


                            activity.setPopDialog();

                        }



                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);

                    }
                });
    }
}
