package com.zwx.scan.app.feature.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateDialogFragment;
import com.zwx.library.utils.AppUtils;
import com.zwx.library.utils.EncryptUtils;
import com.zwx.library.utils.LogUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TextColorSizeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.base.BaseObserver;
import com.zwx.scan.app.data.bean.CommonImageBean;
import com.zwx.scan.app.data.bean.Index;
import com.zwx.scan.app.data.bean.MessageSet;
import com.zwx.scan.app.data.bean.MobileVersion;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.TResource;
import com.zwx.scan.app.data.http.service.HomeServiceManager;
import com.zwx.scan.app.data.http.service.PayAuthServiceManage;
import com.zwx.scan.app.data.http.service.PersonalManager;
import com.zwx.scan.app.data.http.service.RetrofitServiceManager;
import com.zwx.scan.app.data.http.service.UserServiceManager;
import com.zwx.scan.app.feature.personal.PersonalThreeFragment;
import com.zwx.scan.app.feature.personal.PersonalTwoFragment;
import com.zwx.scan.app.utils.SPObjUtil;
import com.zwx.scan.app.utils.UpdateAppHttpUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * author : lizhilong
 * time   : 2018/11/22
 * desc   :
 * version: 1.0
 **/
public class HomePresenter implements HomeContract.Presenter {

    private  HomeContract.View view;
    private HomeServiceManager homeServiceManager;
    private PersonalManager personalManager;
    //添加订阅，应用于取消订阅，防止造成内存泄漏
    private CompositeDisposable disposable;
    private UserServiceManager userServiceManager;

    private PayAuthServiceManage payAuthServiceManage;


    public HomePresenter(HomeContract.View view) {
        homeServiceManager = new HomeServiceManager();
        personalManager = new PersonalManager();
        payAuthServiceManage = new PayAuthServiceManage();
        this.view = view;
        disposable = new CompositeDisposable();
        userServiceManager = new UserServiceManager();
    }

    @Override
    public void queryStore(Context context, String userId,boolean isRefresh) {

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


        homeServiceManager.queryStore(userId)
                .subscribe(new BaseObserver<List<Store>>(context,false){
                    @Override
                    public void onSuccess(List<Store> stores,String msg) {
                        HomeFragment fragment = (HomeFragment)view;
                        if(stores != null && stores.size()>0){
                            fragment.stores = stores;

                            if("1".equals(fragment.isPopAndDialog)){
                                ((HomeFragment) view).showSelectStore(context);
                            }else {
                                ((HomeFragment) view).showPopView(context);
                            }



                        }else {
                            ((HomeFragment) view).ptr.onRefreshComplete();
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);
                        ((HomeFragment) view).ptr.onRefreshComplete();
                    }
                });
    }

    @Override
    public void queryResource(Context context, String userId) {
        RetrofitServiceManager.setHeadTokenMap(null);
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


        homeServiceManager.queryResource(userId)
                .subscribe(new BaseObserver<List<TResource>>(context,true){
                    @Override
                    public void onSuccess(List<TResource> resources,String msg) {
                        HomeFragment fragment = (HomeFragment)view;
                        fragment.resourcesList.clear();
                        if(resources != null && resources.size()>0){

                            fragment.resources = resources;
                            LogUtils.e("resources:"+resources);

                            for(TResource tResource : resources){
                                CommonImageBean imageBean = new CommonImageBean();

                                if(tResource != null ){
                                    if(tResource.getCode().equals("app:verification")){
//                                        ((HomeFragment) view).rbVeri.setText("核销");
//                                        ((HomeFragment) view).rbVeri.setVisibility(View.VISIBLE);
                                        imageBean.setDrawable(R.drawable.ic_home_scancode);
                                        imageBean.setType(tResource.getCode());
                                        imageBean.setName("核销");
                                    }

                                    if(tResource.getCode().equals("app:verificationrecord")){
//                                        ((HomeFragment) view).rbRecord.setText("核销记录");
//                                        ((HomeFragment) view).rbRecord.setVisibility(View.VISIBLE);


                                        imageBean.setDrawable(R.drawable.ic_home_recording);
                                        imageBean.setType(tResource.getCode());
                                        imageBean.setName("核销记录");
                                    }


                                    if(tResource.getCode().contains("app:countanalysis")){
//                                        ((HomeFragment) view).rbCount.setText("统计分析");
//                                        ((HomeFragment) view).rbCount.setVisibility(View.VISIBLE);

                                        imageBean.setDrawable(R.drawable.ic_home_statistics);
                                        imageBean.setType(tResource.getCode());
                                        imageBean.setName("统计分析");
                                    }


                                    if(tResource.getCode().contains("app:more")){
//                                        ((HomeFragment) view).rbMore.setText("更多");
//                                        ((HomeFragment) view).rbMore.setVisibility(View.VISIBLE);
                                        imageBean.setDrawable(R.drawable.ic_home_more);
                                        imageBean.setType(tResource.getCode());
                                        imageBean.setName("更多");
                                    }
                                    fragment.resourcesList.add(imageBean);

                                    if(fragment.moduleGridViewAdapter != null){
                                        fragment.moduleGridViewAdapter.notifyDataSetChanged();
                                    }else {
                                        fragment.moduleGridViewAdapter = new HomeModuleGridViewAdapter(context,fragment.resourcesList);
                                        fragment.gridView.setAdapter(fragment.moduleGridViewAdapter);
                                    }


                                }

                            }
                        }
                        ((HomeFragment) view).ptr.onRefreshComplete();
//                        queryMessageType(context,userId);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);
                        ((HomeFragment) view).ptr.onRefreshComplete();
                    }
                });
    }


    @Override
    public void index(Context context, String storeId,boolean isRefresh) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("storeId",storeId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);


        homeServiceManager.index(storeId)
                .subscribe(new BaseObserver<Index>(context,false){
                    @Override
                    public void onSuccess(Index index,String msg) {
                        HomeFragment fragment = (HomeFragment)view;

                       if(index != null){
                           fragment.index = index;
                           String  curMemberTotal = index.getCurMemberTotal();

                           String  memberTotal = index.getMemberTotal();

                           String  curCampaignTotal = index.getCurCampaignTotal();

                           String  campaignTotal = index.getCampaignTotal();
                           String  curStaffTotal = index.getCurStaffTotal();

                           String  monthStaffTotal = index.getMonthStaffTotal();

                           int subTextColor = Color.parseColor("#4C94FC");
                           String[] subTextArray = {"人"};
                           String curMemberTotal2 = "";
                           String  curCampaignTotal2 = "";
                           String curStaffTotal2 = "";
                           if(curMemberTotal!=null&&!"0".equals(curMemberTotal)){
                               curMemberTotal2 =curMemberTotal+"人";
                               ((HomeFragment) view).tv_member_cur.setText(TextColorSizeUtils.getTextSpan(context,subTextColor,25,curMemberTotal2,subTextArray));
                           }else {
                               curMemberTotal2 = "—";
                               ((HomeFragment) view).tv_member_cur.setText(curMemberTotal2);
                           }
                           if(curCampaignTotal!=null&&!"0".equals(curCampaignTotal)){
                               curCampaignTotal2 =curCampaignTotal+"人";
                               ((HomeFragment) view).tv_campaign_cur.setText(TextColorSizeUtils.getTextSpan(context,subTextColor,25,curCampaignTotal2,subTextArray));
                           }else{
                               curCampaignTotal2 = "—";
                               ((HomeFragment) view).tv_campaign_cur.setText(curCampaignTotal2);
                           }

                           if(curStaffTotal!=null&&!"0".equals(curStaffTotal)){
                               curStaffTotal2 =curStaffTotal+"人";
                               ((HomeFragment) view).tv_staff_cur.setText(TextColorSizeUtils.getTextSpan(context,subTextColor,25,curStaffTotal2,subTextArray));
                           }else{
                               curStaffTotal2= "—";
                               ((HomeFragment) view).tv_staff_cur.setText(curStaffTotal2);
                           }



                           String memberTotal2 ="";
                           String campaignTotal2 ="";
                           String monthStaffTotal2 ="";
                           if(memberTotal!=null&&!"0".equals(memberTotal)){
                               memberTotal2 =memberTotal+"人";
//                               memberTotal2 = TextColorSizeUtils.getTextSpan(context,subTextColor,16,memberTotal2,subTextArray).toString();
                           }else {
                               memberTotal2 = "—";
                           }
                           if(campaignTotal!=null&&!"0".equals(campaignTotal)){
                               campaignTotal2 =campaignTotal+"人";
//                               campaignTotal2 = TextColorSizeUtils.getTextSpan(context,subTextColor,16,campaignTotal2,subTextArray).toString();
                           }else{
                               campaignTotal2 = "—";
                           }

                           if(monthStaffTotal!=null&&!"0".equals(monthStaffTotal)){
                               monthStaffTotal2 =monthStaffTotal+"人";
//                               monthStaffTotal2 = TextColorSizeUtils.getTextSpan(context,subTextColor,16,monthStaffTotal2,subTextArray).toString();
                           }else{
                               monthStaffTotal2= "—";
                           }
                           ((HomeFragment) view).tv_member_total.setText(memberTotal2);
                           ((HomeFragment) view).tv_campaign_total.setText(campaignTotal2);
                           ((HomeFragment) view).tv_staff_total.setText(monthStaffTotal2);
                           ((HomeFragment) view).memberReport = index.getMemberTotalReport();
                           ((HomeFragment) view).campaignReport = index.getCampaignTotalReport();
                           ((HomeFragment) view).staffReport = index.getStaffTotalReport();

                           if(index.getDate() != null && !"".equals(index.getDate())){
                               ((HomeFragment) view).tvDate .setText("("+index.getDate()+")");
                           }else {
                               ((HomeFragment) view).tvDate .setText("");
                           }

//                           ((HomeFragment) view).pageAdapter.updateData(index);

                           ((HomeFragment) view).setPageAdapter();
                           String userId = SPUtils.getInstance().getString("userId");
                           queryResource(context,userId);

                       }
                        ((HomeFragment) view).ptr.onRefreshComplete();
                        if(((HomeFragment) view).shopPop != null){
                            ((HomeFragment) view).shopPop.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ((HomeFragment) view).ptr.onRefreshComplete();
                        if(((HomeFragment) view).shopPop != null){
                            ((HomeFragment) view).shopPop.dismiss();
                        }
                    }
                });


    }


    @Override
    public void resoruces(Context context, String userId) {

    }

    @Override
    public void indexConcat(Context context, String userId, String storeId) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

//        params.put("storeId",storeId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
//        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        homeServiceManager.indexContact(userId,storeId).subscribe(new BaseObserver<Index>(context,true){
            @Override
            public void onSuccess(Index index,String msg) {
                HomeFragment fragment = (HomeFragment)view;
                if(index != null){
                    String  curMemberTotal = index.getCurMemberTotal();

                    String  memberTotal = index.getMemberTotal();

                    String  curCampaignTotal = index.getCurCampaignTotal();

                    String  campaignTotal = index.getCampaignTotal();
                    String  curStaffTotal = index.getCurStaffTotal();

                    String  monthStaffTotal = index.getMonthStaffTotal();

                    List<Map<String,Object>> memberReport = index.getMemberTotalReport();
                    ((HomeFragment) view).tv_campaign_cur.setText(curCampaignTotal!="0"?curCampaignTotal+"":"-");
                    ((HomeFragment) view).tv_campaign_total.setText(campaignTotal!="0"?campaignTotal+"":"-");
                    ((HomeFragment) view).tv_member_cur.setText(curMemberTotal!="0"?curMemberTotal+"":"-");
                    ((HomeFragment) view).tv_member_total.setText(memberTotal!="0"?memberTotal+"":"-");
                    ((HomeFragment) view).tv_staff_cur.setText(curStaffTotal!="0"?curStaffTotal+"":"-");
                    ((HomeFragment) view).tv_staff_total.setText(monthStaffTotal!="0"?monthStaffTotal+"":"-");
                    ((HomeFragment) view).memberReport = index.getMemberTotalReport();
                    ((HomeFragment) view).campaignReport = index.getCampaignTotalReport();
                    ((HomeFragment) view).staffReport = index.getStaffTotalReport();

                    ((HomeFragment) view).tvDate .setText(index.getDate());
                    ((HomeFragment) view).setPageAdapter();
                    ((HomeFragment) view).resources = index.getResourceList();
//                           ((HomeFragment) view).index = index;
//                           ((HomeFragment) view).pageAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(int code, String message) {

                super.onFailure(code, message);
            }
        });

    }


    @Override
    public void queryMessageType(Context context, String userId) {
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

        homeServiceManager.queryMessageType(userId)
                .subscribe(new BaseObserver<List<MessageSet>>(context,true){
                    @Override
                    public void onSuccess(List<MessageSet> messageSetList,String msg) {

                        int count = 0;
                        if(!messageSetList.isEmpty()){
                            for (int i = 0;i<messageSetList.size();i++){
                                MessageSet messageSet = messageSetList.get(i);

                                int count1 = messageSet.getCount();

                                count +=count1;
                            }
                            PersonalTwoFragment.msgCount = count;
                            PersonalThreeFragment.msgCount = count;
                            doFeedFlag(context,userId);
                        }


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        doFeedFlag(context,userId);
                    }
                });
    }

    @Override
    public void doFeedFlag(Context context, final String userId) {
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
                .subscribe(new BaseObserver<String>(context,true){
                    @Override
                    public void onSuccess(String result,String msg) {

                        if(result != null && !"".equals(result)){
                            PersonalThreeFragment.feedStatus = result;
                        }
                        selectPaymentAuthStatus(context,userId);

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        selectPaymentAuthStatus(context,userId);
                    }
                });
    }

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
        HomeFragment fragment =  (HomeFragment) view;
        payAuthServiceManage.selectPaymentAuthStatus(userId)
                .subscribe(new BaseObserver<Map<String,Object>>(context,true){
                    @Override
                    public void onSuccess(Map<String,Object> result,String msg) {



                        PersonalThreeFragment.status = (String)result.get("status");
                        PersonalThreeFragment.process = (String)result.get("process");
                        SPUtils.getInstance().put("status",PersonalThreeFragment.status);
                        SPUtils.getInstance().put("process",PersonalThreeFragment.process);
                        if("N".equals(PersonalThreeFragment.status)){
                            PersonalThreeFragment.statusStr = "未认证";
                        }else if("A".equals(PersonalThreeFragment.status)){
                            PersonalThreeFragment.statusStr = "已认证";
                        }
//                        PersonalThreeFragment.tv_auth_state.setText(statusStr);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);
                    }
                });
    }

    @Override
    public void doDownloadVersion(Context context, String id) {
        userServiceManager.doDownloadVersion(id)
                .subscribe(new BaseObserver<MobileVersion>(context,false){

                    @Override
                    public void onSuccess(MobileVersion result,String msg) {
                        if(result != null){
                            int serverVersion =Integer.parseInt(result.getVersionCode());
                            int version = AppUtils.getAppVersionCode();
                            if(serverVersion>version){
                                String updateUrl = result.getUpdatePath();
                                UpdateAppManager appManager = new UpdateAppManager();
                                UpdateAppBean mUpdateApp = new UpdateAppBean();
                                mUpdateApp.setUpdate("Yes");
                                mUpdateApp.setApkFileUrl(updateUrl);
                                mUpdateApp.setOnlyWifi(false);
                                mUpdateApp.setNewVersion(result.getVersionName());
                                mUpdateApp.setUpdateLog(result.getUpdateLog());
                                mUpdateApp.setTargetSize("26");
                                appManager.mUpdateApp = mUpdateApp;

                                Bundle bundle = new Bundle();
                                //添加信息，
                                String path = "";
                                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()) {
                                    try {
                                        path = context.getExternalCacheDir().getAbsolutePath();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (TextUtils.isEmpty(path)) {
                                        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                                    }
                                } else {
                                    path = context.getCacheDir().getAbsolutePath();
                                }
                                if (mUpdateApp != null) {
                                    mUpdateApp.setTargetPath(path);
                                    mUpdateApp.setHttpManager(new UpdateAppHttpUtil());
                                    mUpdateApp.setHideDialog(false);
                                    mUpdateApp.showIgnoreVersion(false);
                                    mUpdateApp.dismissNotificationProgress(false);
                                    mUpdateApp.setOnlyWifi(false);
                                }
                                bundle.putSerializable("update_dialog_values", mUpdateApp);
                                int mThemeColor =0;
                                if (mThemeColor != 0) {
                                    bundle.putInt("theme_color", mThemeColor);
                                }
                                int mTopPic =0;
                                if (mTopPic != 0) {
                                    bundle.putInt("top_resId", mTopPic);
                                }

                                UpdateDialogFragment
                                        .newInstance(bundle)
                                        .setUpdateDialogFragmentListener(null)
                                        .show(((FragmentActivity) context).getSupportFragmentManager(), "dialog");
                            }
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);


                    }
                });
    }
}
