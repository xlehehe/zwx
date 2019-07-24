package com.zwx.scan.app.feature.campaign;

import android.content.Context;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.luck.picture.lib.entity.LocalMedia;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.DateUtils;
import com.zwx.library.utils.EncryptUtils;
import com.zwx.library.utils.LogUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.base.BaseObserver;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCollect;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CampaignEditBean;
import com.zwx.scan.app.data.bean.CampaignGame;
import com.zwx.scan.app.data.bean.CampaignGamesetreward;
import com.zwx.scan.app.data.bean.CampaignGroupBuy;
import com.zwx.scan.app.data.bean.CampaignNonrewardPic;
import com.zwx.scan.app.data.bean.CampaignSaveResult;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.CouponType;
import com.zwx.scan.app.data.bean.JiZanResultBean;
import com.zwx.scan.app.data.bean.LhPtEditBean;
import com.zwx.scan.app.data.bean.MaterialGame;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.PinTuanBean;
import com.zwx.scan.app.data.bean.PosterMaterial;
import com.zwx.scan.app.data.bean.ResultBean;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.data.http.service.CampaignServiceManager;
import com.zwx.scan.app.data.http.service.RetrofitServiceManager;
import com.zwx.scan.app.feature.ptmanage.PtManageActivity;
import com.zwx.scan.app.feature.ptmanage.PtNewActivity;
import com.zwx.scan.app.feature.ptmanage.PtNextThreeActivity;
import com.zwx.scan.app.feature.ptmanage.PtNextTwoActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
public class CampaignsPresenter implements CampaignsContract.Presenter {


    private  CampaignsContract.View view;
    private CampaignServiceManager campaignServiceManager;

    //添加订阅，应用于取消订阅，防止造成内存泄漏
    private CompositeDisposable disposable;




    public CampaignsPresenter(CampaignsContract.View view) {
        campaignServiceManager = new CampaignServiceManager();
        this.view = view;
        disposable = new CompositeDisposable();
    }
    @Override
    public void doQueryCampaignList(Context context, String userId, String compId, Integer pageNumber, Integer pageSize,String campaignType,boolean isRefresh) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("compId",compId);
        params.put("pageNumber",String.valueOf(pageNumber));
        params.put("pageSize",String.valueOf(pageSize));
        params.put("campaignType",campaignType);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        CampaignListActivity activity = (CampaignListActivity) context;
        campaignServiceManager.doQueryCampaignList(userId,compId,pageNumber,pageSize,campaignType)
                .subscribe(new BaseObserver<List<Campaign>>(context,true){
                    @Override
                    public void onSuccess(List<Campaign> dataList, String msg) {

                        if (dataList!=null && dataList.size()>0) {
                            activity.ll_no_data.setVisibility(View.GONE);
                            activity.rvList.setVisibility(View.VISIBLE);
                           /* if (isRefresh) {
                                activity.campaignList.clear();

                            } else {


                            }*/
                            for(int i=0;i<dataList.size();i++){
                                if(dataList.get(i) != null){
                                    if(dataList.get(i).getCampaignId() != null){
                                        String goodId = String.valueOf(dataList.get(i).getCampaignId());
                                        if(activity.campaignList != null &&  activity.campaignList.size()>0){
                                            for(int j=0;j<activity.campaignList.size();j++){
                                                if(goodId.equals(String.valueOf(activity.campaignList.get(j).getCampaignId()))){
                                                    activity.campaignList.remove(j);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                            activity.campaignList.addAll(dataList);
                            activity.listAdapter.notifyDataSetChanged();
                            /*activity.listAdapter = new CampaignListAdatper(context, activity.campaignList);
                            activity.rvList.setAdapter(activity.listAdapter);*/
                            if (dataList.size() < 10) {
                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            } else {
                                activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                            activity.ptr.onRefreshComplete();

                        }else {
                            activity.listAdapter.notifyDataSetChanged();
                            if(activity.campaignList != null && activity.campaignList.size()>0){
                                if(activity.campaignList.size() < 10){
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                }else {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }
                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.rvList.setVisibility(View.VISIBLE);
                            }else {
                                activity.listAdapter.notifyDataSetChanged();
                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.rvList.setVisibility(View.GONE);
                                activity.tv_no_data.setText("您现在没有活动记录哦！");
                            }

                            activity.ptr.onRefreshComplete();
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.listAdapter.notifyDataSetChanged();
                        if(activity.campaignList != null && activity.campaignList.size()>0){
                            if(activity.campaignList.size() < 10){
                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }else {
                                activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                            activity.ll_no_data.setVisibility(View.GONE);
                            activity.rvList.setVisibility(View.VISIBLE);
                        }else {
                            activity.listAdapter.notifyDataSetChanged();
                            activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            activity.ll_no_data.setVisibility(View.VISIBLE);
                            activity.rvList.setVisibility(View.GONE);
                            activity.iv_no_data.setImageResource(R.drawable.ic_wifi_not);
                            activity.tv_no_data.setText("您的网络可能睡着了！");
                        }

                        activity.ptr.onRefreshComplete();

                    }
                });

    }


    @Override
    public void queryCampaignList(Context context, String userId, String compId, Integer pageNumber, Integer pageSize, String campaignType, boolean isRefresh) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("compId",compId);
        params.put("pageNumber",String.valueOf(pageNumber));
        params.put("pageSize",String.valueOf(pageSize));
        params.put("campaignType",campaignType);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        CampaignListTwoActivity activity = (CampaignListTwoActivity) context;
        campaignServiceManager.doQueryCampaignList(userId,compId,pageNumber,pageSize,campaignType)
                .subscribe(new BaseObserver<List<Campaign>>(context,true){
                    @Override
                    public void onSuccess(List<Campaign> campaignList, String msg) {

                        if (campaignList!=null && campaignList.size()>0) {
//                            activity.ptr.setVisibility(View.VISIBLE);
//                            activity.llNoData.setVisibility(View.GONE);
                            if (isRefresh) {
                                activity.campaignList.clear();
                                activity.campaignList.addAll(campaignList);
                                activity.mAdapter.notifyDataSetChanged();

                                if (campaignList.size() < 10) {

                                    activity.ptr.refreshComplete();
                                    activity.ptr.setLoadMoreEnable(false);
                                } else {
                                    activity.ptr.refreshComplete();
                                    activity.ptr.setLoadMoreEnable(true);
                                }
                            } else {
                                activity.campaignList.addAll(campaignList);
//                                activity.mAdapter = new CampaignListViewAdapter(activity, activity.campaignList);
                                activity.mAdapter.notifyDataSetChanged();
                                if (campaignList.size() < 10) {
                                    activity.ptr.refreshComplete();
                                    activity.ptr.setLoadMoreEnable(false);
                                } else {
                                    activity.ptr.refreshComplete();
                                    activity.ptr.loadMoreComplete(true);
                                }
                            }
                        }else {
                            activity.ptr.refreshComplete();
                            activity.ptr.setLoadMoreEnable(false);
//                            activity.ptr.setVisibility(View.GONE);
//                            activity.llNoData.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.ptr.refreshComplete();
                        activity.ptr.setLoadMoreEnable(false);
//                        activity.ptr.setVisibility(View.GONE);
//                        activity.llNoData.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void doCampaignDelete(Context context,String campaignId,int position) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("campaignId",campaignId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        CampaignListActivity activity = (CampaignListActivity) context;
        campaignServiceManager.doCampaignDelete(campaignId)
                .subscribe(new BaseObserver<ResultBean>(context,false){
                    @Override
                    public void onSuccess(ResultBean resultBean, String msg) {

                        ToastUtils.showShort("已删除");
                        activity.campaignList.remove(position);
                        activity.listAdapter.notifyDataSetChanged();
                        if(activity.dialog!=null){
                            activity.dialog.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort("删除失败");
                        if(activity.dialog!=null){
                            activity.dialog.dismiss();
                        }
                    }
                });
    }

    @Override
    public void doCampaignUpdate(Context context,String campaignId, String operateFlag) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("campaignId",campaignId);
        params.put("operateFlag",operateFlag);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        CampaignListActivity activity = (CampaignListActivity) context;
        campaignServiceManager.doCampaignUpdate(campaignId,operateFlag)
                .subscribe(new BaseObserver<ResultBean>(context,false){
                    @Override
                    public void onSuccess(ResultBean resultBean, String msg) {
                        String userId = SPUtils.getInstance().getString("userId");
                        String compId = SPUtils.getInstance().getString("compId");
                        if("1".equals(operateFlag)){
                            ToastUtils.showShort("已作废！");


                        }else if("2".equals(operateFlag)){
                            ToastUtils.showShort("已提前结束！");
                        }
                        if(activity.dialog!=null){
                            activity.dialog.dismiss();
                        }
                        doQueryCampaignList(context, userId,  compId,  activity.pageNumber,  10, "zf", false);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort("操作失败！");
                        if(activity.dialog!=null){
                            activity.dialog.dismiss();
                        }
                    }
                });
    }

    /**
     * @param  isCampaignAndCouponStore 活动或优惠券店铺标志
     * */
    @Override
    public void queryBrandAndStoreList(Context context,String userId,boolean isCampaignAndCouponStore,boolean isDefaultAllStore) {


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

        campaignServiceManager.queryBrandAndStoreList(userId)
                .subscribe(new BaseObserver<MoreStoreBean>(context,false){
                    CampaignNewActivity campaignNewActivity = null;
                    CampaignCouponNextSettingFragment campaignNewNextActivity = null;

                    @Override
                    public void onSuccess(MoreStoreBean moreStoreBean,String msg) {
                        if (isCampaignAndCouponStore) {
                            campaignNewActivity = (CampaignNewActivity) view;
                            if(moreStoreBean != null ){
                                campaignNewActivity.branList = moreStoreBean.getBrandList();
                                if(isDefaultAllStore){
                                    if(campaignNewActivity.branList !=null && campaignNewActivity.branList.size()>0){
                                        if(campaignNewActivity.branList.get(0)!=null){
                                            campaignNewActivity.storeList = campaignNewActivity.branList.get(0).getStoreList();
                                            StringBuilder storeIdSb = new StringBuilder();
                                            StringBuilder storeNameSb = new StringBuilder();
                                            String storeIdArr = "";
                                            for (Store store:campaignNewActivity.storeList){
                                                store.setChecked(false);
                                                String storeId = store.getStoreId();
                                                String storeName = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                                storeIdSb.append(storeId).append("-");
                                                storeNameSb.append(storeName);
                                            }
                                            if(storeIdSb!=null ||!"".equals(storeIdSb)){
                                                if(storeIdSb.toString().contains("-")){
                                                    storeIdArr = storeIdSb.substring(0,storeIdSb.length() - 1);
//                                                    campaignNewActivity.storeId = storeIdArr;
                                                 /*   SPUtils.getInstance().put("storeIdCampaignType","A");
                                                    SPUtils.getInstance().put("storeIdArrayCampaign",storeIdArr);
                                                    SPUtils.getInstance().put("storeNameArrayCampaign",storeNameSb.toString());*/
                                                }
                                            }else {
                                                campaignNewActivity.tvStoreNames.setText("# 全部店铺");
                                            }
                                        }

                                    }else {
                                        campaignNewActivity.tvStoreNames.setText("# 全部店铺");
                                    }

                                }else {
                                    campaignNewActivity.showPopView(campaignNewActivity);
                                }

                            }
                        }else {
                            campaignNewNextActivity = (CampaignCouponNextSettingFragment) view;
                            if(moreStoreBean != null ){
                                campaignNewNextActivity.branList = moreStoreBean.getBrandList();
//                                campaignNewNextActivity.showPopView(campaignNewNextActivity.getActivity());
                                if(isDefaultAllStore){
                                    if(campaignNewNextActivity.branList !=null && campaignNewNextActivity.branList.size()>0){
                                        if(campaignNewNextActivity.branList.get(0)!=null){
                                            campaignNewNextActivity.storeList = campaignNewNextActivity.branList.get(0).getStoreList();
                                            StringBuilder storeIdSb = new StringBuilder();
                                            StringBuilder storeNameSb = new StringBuilder();
                                            String storeIdArr = "";
                                            for (Store store:campaignNewNextActivity.storeList){
                                                store.setChecked(false);
                                                String storeId = store.getStoreId();
                                                String storeName = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                                storeIdSb.append(storeId).append("-");
                                                storeNameSb.append(storeName);
                                            }
                                            /*if(storeIdSb!=null ||!"".equals(storeIdSb)){
                                                if(storeIdSb.toString().contains("-")){
                                                    storeIdArr = storeIdSb.substring(0,storeIdSb.length() - 1);
                                                    campaignNewNextActivity.storeId = storeIdArr;
                                                    SPUtils.getInstance().put("storeIdCouponType","A");
                                                    SPUtils.getInstance().put("storeIdArrayCoupon",storeIdArr);
                                                    SPUtils.getInstance().put("storeNameArrayCoupon","全部店铺");
                                                }
                                                campaignNewNextActivity.tvStoreNames.setText("全部店铺");
                                            }else {
                                                campaignNewNextActivity.tvStoreNames.setText("");
                                            }*/
                                        }

                                    }else {
                                        campaignNewNextActivity.tvStoreNames.setText("#全部店铺");
                                    }

                                }else {
                                    campaignNewNextActivity.showPopView(campaignNewNextActivity.getActivity());
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
    public void queryCouponTypeList(Context context, String userId) {
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
        CampaignCouponListActivity activity = (CampaignCouponListActivity)context;
        campaignServiceManager.queryCouponTypeList(userId)
                .subscribe(new BaseObserver<List<CouponType>>(context,true){
                    CampaignNewActivity campaignNewActivity = null;
                    CampaignNewNextActivity campaignNewNextActivity = null;

                    @Override
                    public void onSuccess(List<CouponType> couponTypeList,String msg) {

                        if(couponTypeList != null && couponTypeList.size()>0){
                            activity.typeList = couponTypeList;
                            for (CouponType couponType : couponTypeList){
                                if(couponType != null){
                                    String type = couponType.getType();
                                    activity.setPagerAdapter();
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
    public void queryCoupon(Context context, String userId, String campaignId, String couponType, Integer pageNumber, Integer pageSize, boolean isRefresh) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("campaignId",campaignId);
        params.put("couponType",couponType);
        params.put("pageNumber",String.valueOf(pageNumber));
        params.put("pageSize",String.valueOf(pageSize));
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        CampaignCouponListFragment fragment = (CampaignCouponListFragment)view;
        campaignServiceManager.queryCoupon(userId,campaignId,couponType,pageNumber,pageSize)
                .subscribe(new BaseObserver<List<Coupon>>(context,false){

                    @Override
                    public void onSuccess(List<Coupon> couponList,String msg) {
                        if (couponList != null && couponList.size() > 0) {
                            fragment.listView.setVisibility(View.VISIBLE);
                            fragment.ll_no_data.setVisibility(View.GONE);

                            for(int i=0;i<couponList.size();i++){
                                if(couponList.get(i) != null){
                                    if(couponList.get(i).getId() != null){
                                        String detailedId = String.valueOf(couponList.get(i).getId());
                                        if(fragment.couponList != null &&  fragment.couponList.size()>0){
                                            for(int j=0;j<fragment.couponList.size();j++){
                                                if(detailedId.equals(String.valueOf(fragment.couponList.get(j).getId()))){
                                                    fragment.couponList.remove(j);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                            fragment.couponList.addAll(couponList);
                            fragment.listAdapter.notifyDataSetChanged();

                            if (couponList.size() < 10) {
                                fragment.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            } else {
                                fragment.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                            fragment.ptr.onRefreshComplete();

                        } else {

                            fragment.couponList.addAll(couponList);
                            fragment.listAdapter.notifyDataSetChanged();
                            fragment.ptr.setPullToRefreshOverScrollEnabled(true);

                            if(fragment.couponList != null  &&  fragment.couponList.size()>0){
                                if(fragment.couponList.size() < 10){
                                    fragment.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                }else {
                                    fragment.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }
                                fragment.ptr.onRefreshComplete();
                                fragment.ll_no_data.setVisibility(View.GONE);
                                fragment.listView.setVisibility(View.VISIBLE);
                            }else {

                                fragment.ll_no_data.setVisibility(View.VISIBLE);
                                fragment.listView.setVisibility(View.GONE);
                                fragment.tv_no_data.setText("暂无优惠券数据");
                            }
                        }


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        fragment.listAdapter.notifyDataSetChanged();
                        fragment.ptr.onRefreshComplete();
                        fragment.tv_no_data.setText("暂无优惠券数据");
                        fragment.ll_no_data.setVisibility(View.VISIBLE);
                        fragment.listView.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void queryPoster(Context context, String compId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("compId",compId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        PosterListActivity activity = (PosterListActivity) context;
        campaignServiceManager.queryPoster(compId)
                .subscribe(new BaseObserver<List<PosterMaterial>>(context,false){
                    @Override
                    public void onSuccess(List<PosterMaterial> posterMaterialList, String msg) {


                        if(posterMaterialList!=null && posterMaterialList.size()>0){
                            activity.posterMaterialList.clear();
                            if("YES".equals(activity.isEditCampaign)||"NEW".equals(activity.isEditCampaign)){
                                for (PosterMaterial posterMaterial:posterMaterialList){
                                    Long posterId =posterMaterial.getId();
                                    if(activity.posterId!=null){
                                        if(activity.posterId.equals(String.valueOf(posterId))){
                                            posterMaterial.setChecked(true);
                                        }
                                    }
                                }
                            }else {
                                for (PosterMaterial posterMaterial:posterMaterialList){
                                    Long posterId =posterMaterial.getId();
                                    if(activity.posterId!=null){
                                        if(activity.posterId.equals(String.valueOf(posterId))){
                                            posterMaterial.setChecked(true);
                                        }
                                    }
                                }
                            }
                            activity.posterMaterialList.addAll(posterMaterialList);
                            activity.mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });
    }


    @Override
    public void saveCampaignInfo(Context context, Campaign campaign, String storeSelectType, String storeStr,
                                 List<CampaignCoupon> forwardCoupon, List<CampaignCoupon> receiveCoupon,
                                 String posterId, String userId, String compId, String btnFlag) {


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
        PosterListActivity activity = (PosterListActivity) context;
        JSONObject jsonObject = new JSONObject();
        String jsonStr = "";
        try{
            jsonObject.put("userId",userId);
            jsonObject.put("storeStr",storeStr);
            jsonObject.put("posterId",posterId);
            jsonObject.put("btnFlag",btnFlag);
            String campaignJSON=new Gson().toJson(campaign);
            jsonObject.put("campaign",campaignJSON);

            jsonObject.put("compId",compId);
            String forwardCouponJSON = new Gson().toJson(forwardCoupon);
            String receiveCouponJSON = new Gson().toJson(receiveCoupon);
            jsonObject.put("forwardCoupon",forwardCouponJSON);
            jsonObject.put("receiveCoupon",receiveCouponJSON);
            jsonObject.put("storeSelectType",storeSelectType);
            jsonStr = jsonObject.toString();
        }catch (Exception e){

        }
        campaignServiceManager.saveCampaignInfo(jsonStr)
                .subscribe(new BaseObserver<CampaignSaveResult>(context,true){
                    @Override
                    public void onSuccess(CampaignSaveResult result, String msg) {

                        if(result!=null){
                            if("save".equals(btnFlag)){
                                ToastUtils.showCustomShortBottom("保存成功");
                            }else {
                                ToastUtils.showCustomShortBottom("发布成功");
                            }

                            ActivityUtils.startActivity(CampaignListActivity.class, R.anim.slide_in_right,R.anim.slide_out_left);

                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        if("save".equals(btnFlag)){
                            ToastUtils.showCustomShortBottom("保存失败");
                        }else {
                            ToastUtils.showCustomShortBottom("发布失败");
                        }
                    }
                });

    }

    @Override
    public void doEdit(Context context, String campaignId) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("campaignId",campaignId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        CampaignNewActivity campaignNewActivity =(CampaignNewActivity) context;
        campaignServiceManager.doEdit(campaignId)
                .subscribe(new BaseObserver<CampaignEditBean>(context,true){
                    @Override
                    public void onSuccess(CampaignEditBean campaignEditBean, String msg) {

                        if(campaignEditBean!=null){
                            campaignNewActivity.campaign  =campaignEditBean.getCampaign();
                            campaignNewActivity.campaignType = campaignEditBean.getCampaign().getCampaignType();

                            campaignNewActivity.storeList = campaignEditBean.getStore();
                            if(campaignNewActivity.storeList!=null && campaignNewActivity.storeList.size()>0){
                                StringBuilder storeIdSb = new StringBuilder();
                                StringBuilder storeNameSb = new StringBuilder();
                                campaignNewActivity.brandLogo = SPUtils.getInstance().getString("brandLogo");
                                RoundedCorners roundedCorners= new RoundedCorners(8);
                                RequestOptions requestOptions = new RequestOptions()
                                        .bitmapTransform(roundedCorners)
                                        .placeholder(R.drawable.ic_image_loading)
                                        .error(R.drawable.ic_load_fail)
                                        .fallback(R.drawable.ic_image_loading);

                                Glide.with(context).load(campaignNewActivity.brandLogo).apply(requestOptions).into(campaignNewActivity.ivBrand);

                                campaignNewActivity.storeIdCampaignType =campaignNewActivity.storeList.get(0).getStoreSelectType();
                                if("A".equals(campaignNewActivity.storeIdCampaignType)){

                                    campaignNewActivity.storeName = "#全部店铺";
                                    campaignNewActivity.tvStoreNames.setText(campaignNewActivity.storeName);
                                    for (Store store:campaignNewActivity.storeList){
                                        store.setChecked(false);

                                    }

                                }  else if("D".equals(campaignNewActivity.storeIdCampaignType)){
                                    campaignNewActivity.storeName = "#全部自营";
                                    campaignNewActivity.tvStoreNames.setText(campaignNewActivity.storeName);
                                    for (Store store:campaignNewActivity.storeList){
                                        store.setChecked(false);

                                    }
                                }else if("J".equals(campaignNewActivity.storeIdCampaignType)){
                                    campaignNewActivity.storeName = "#全部加盟";
                                    campaignNewActivity.tvStoreNames.setText(campaignNewActivity.storeName);
                                    for (Store store:campaignNewActivity.storeList){
                                        store.setChecked(false);

                                    }
                                }else {
                                    campaignNewActivity.storeIdCampaignType = "H";
                                    for (Store store:campaignNewActivity.storeList){
                                        store.setChecked(false);
                                        if(store.getId()>0){
                                            String storeId = String.valueOf(store.getId());
                                            String storeName = store.getName()!=null?"#"+store.getName()+ "    ":"";
                                            storeIdSb.append(storeId).append("-");
                                            storeNameSb.append(storeName);
                                        }


                                    }
                                    String storeIdArr ;
                                    if(!storeIdSb.toString().isEmpty()){
                                        storeIdArr = storeIdSb.substring(0,storeIdSb.length() - 1);
                                        campaignNewActivity.storeId = storeIdArr;
                                    }
                                    campaignNewActivity.storeName =storeNameSb.toString()!=null?storeNameSb.toString():"";
                                    campaignNewActivity.tvStoreNames.setText(campaignNewActivity.storeName);

                                }

                                SPUtils.getInstance().put("storeIdCampaignType",campaignNewActivity.storeIdCampaignType);
                                SPUtils.getInstance().put("storeIdArrayCampaign", campaignNewActivity.storeId);
                                SPUtils.getInstance().put("storeNameArrayCampaign",campaignNewActivity.storeName);
                            }


                            campaignNewActivity.campaignName = campaignNewActivity.campaign.getCampaignName();
                            campaignNewActivity.startDate = campaignNewActivity.campaign.getBeginDate();
                            campaignNewActivity.endDate = campaignNewActivity.campaign.getEndDate();
                            if(campaignNewActivity.startDate!=null && !"".equals(campaignNewActivity.startDate)){
                                Date date = DateUtils.parse(campaignNewActivity.startDate,"yyyy-MM-dd HH:mm");
                                ;
                                campaignNewActivity.edtStart.setText(DateUtils.formatDate(date,"yyyy-MM-dd HH:mm").replace("-","."));
                            }else {
                                campaignNewActivity.edtStart.setText("");
                            }

                            if(campaignNewActivity.endDate!=null && !"".equals(campaignNewActivity.endDate)){
                                Date date = DateUtils.parse(campaignNewActivity.endDate,"yyyy-MM-dd HH:mm");
                                ;
                                campaignNewActivity.edtEnd.setText(DateUtils.formatDate(date,"yyyy-MM-dd HH:mm").replace("-","."));
                            }else {
                                campaignNewActivity.edtEnd.setText("");
                            }

                            campaignNewActivity.edtName.setText(campaignNewActivity.campaignName!=null?campaignNewActivity.campaignName:"");
                            CampaignNewNextTwoActivity.forwardCouponList = campaignEditBean.getListfoward();
                            CampaignNewNextThreeActivity.receiveCouponList = campaignEditBean.getListreceive();

                        }



                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });
    }

    @Override
    public void copyCampaign(Context context, String campaignId) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("compId",campaignId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        PosterListActivity activity = (PosterListActivity) context;
        campaignServiceManager.copyCampaign(campaignId)
                .subscribe(new BaseObserver<ResultBean>(context,false){
                    @Override
                    public void onSuccess(ResultBean resultBean, String msg) {
                        ToastUtils.showShort(msg);



                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                        ToastUtils.showShort(message);
                    }
                });
    }

    @Override
    public void queryStoreList(Context context, String userId, boolean isDefaultAllStore,boolean isLaoHuPinTuanNewOrTwo) {

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
        campaignServiceManager.queryBrandAndStoreList(userId)
                .subscribe(new BaseObserver<MoreStoreBean>(context,false){


                    @Override
                    public void onSuccess(MoreStoreBean moreStoreBean,String msg) {

                        if(isLaoHuPinTuanNewOrTwo){
                            LaohuPinTuanNewActivity laohuPinTuanNewActivity = (LaohuPinTuanNewActivity)context;
                            laohuPinTuanNewActivity.branList = moreStoreBean.getBrandList();
                            if(isDefaultAllStore){

                                if(laohuPinTuanNewActivity.branList !=null && laohuPinTuanNewActivity.branList.size()>0){
                                    if(laohuPinTuanNewActivity.branList.get(0)!=null){
                                        laohuPinTuanNewActivity.storeList = laohuPinTuanNewActivity.branList.get(0).getStoreList();
                                        StringBuilder storeIdSb = new StringBuilder();
                                        StringBuilder storeNameSb = new StringBuilder();
                                        String storeIdArr = "";
                                        for (Store store:laohuPinTuanNewActivity.storeList){
                                            store.setChecked(false);
                                            String storeId = store.getStoreId();
                                            String storeName = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                            storeIdSb.append(storeId).append("-");
                                            storeNameSb.append(storeName);
                                        }

                                    }

                                }else {

                                    if("NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                                        laohuPinTuanNewActivity.tvStoreNames.setText("#全部店铺");
                                    }

                                }

                            }else {
                                laohuPinTuanNewActivity.showPopView(context);
                            }
                        }else {
                            LaohuPinTuanNextSettingFragment nextSettingFragment = (LaohuPinTuanNextSettingFragment)view;
                            nextSettingFragment.branList = moreStoreBean.getBrandList();
                            if(isDefaultAllStore){

                                if(nextSettingFragment.branList !=null && nextSettingFragment.branList.size()>0){
                                    if(nextSettingFragment.branList.get(0)!=null){
                                        nextSettingFragment.storeList = nextSettingFragment.branList.get(0).getStoreList();
                                        StringBuilder storeIdSb = new StringBuilder();
                                        StringBuilder storeNameSb = new StringBuilder();
                                        String storeIdArr = "";
                                        for (Store store:nextSettingFragment.storeList){
                                            store.setChecked(false);
                                            String storeId = store.getStoreId();
                                            String storeName = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                            storeIdSb.append(storeId).append("-");
                                            storeNameSb.append(storeName);
                                        }

                                    }

                                }else {
                                    if("NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                                        nextSettingFragment.tvStoreNames.setText("#全部店铺");
                                    }
                                }

                            }else {
                                nextSettingFragment.showPopView(context);
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
    public void queryLhGeTigerPoster(Context context, String userId, String compId, String id, String isUnPrize,String templateType) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("compId",compId);
        params.put("id",id);

        params.put("templateType",templateType);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);



        campaignServiceManager.queryTigerPoster(userId,compId,id,templateType)
                .subscribe(new BaseObserver<List<MaterialGame>>(context,false){
                    @Override
                    public void onSuccess(List<MaterialGame>  materialGameList, String msg) {

                        if(materialGameList!=null){

                            if("Prize1".equals(isUnPrize)||"Prize2".equals(isUnPrize)||"Prize3".equals(isUnPrize)||"Prize4".equals(isUnPrize)
                                    ||"Prize5".equals(isUnPrize)||"Prize6".equals(isUnPrize)||"Prize7".equals(isUnPrize)||"Prize8".equals(isUnPrize)){ //条件1~7
                                LaohuPinTuanNextTwoFragment fragment = (LaohuPinTuanNextTwoFragment) view;
                                if(materialGameList != null && materialGameList.size()>0){
                                    materialGameList = materialGameList;
                                    if(materialGameList != null && materialGameList.size()>0){


                                        Object win = materialGameList.get(0).getWin();
                                        Object non = materialGameList.get(0).getNon();
                                        List<String> wins = new ArrayList<>();
                                        List<String> nons = new ArrayList<>();
                                        if(win != null){
                                            wins = (List<String>)win;
                                        }

                                        if("Prize8".equals(isUnPrize)){
                                            String nonId = "";
                                            if(non != null){
                                                nons = (List<String>)non;
                                                 nonId = nons.get(0);

                                            }
                                            LaohuPinTuanNextTwoActivity.prizePath8 = HttpUrls.IMAGE_ULR + nonId;


                                            if("NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                                                LaohuPinTuanNextTwoActivity.selectList = new ArrayList<>();
                                                if(LaohuPinTuanNextTwoActivity.selectList != null && LaohuPinTuanNextTwoActivity.selectList.size()>0){
                                                    for (LocalMedia localMedia:LaohuPinTuanNextTwoActivity.selectList){
                                                        String prizePath = localMedia.getCompressPath();
                                                        if(prizePath != null && !"".equals(prizePath)){
                                                            String nonId2  =  prizePath.substring(prizePath.indexOf("get.do?id=")+10,prizePath.length());
                                                            if(nonId.equals(nonId2)){

                                                            }
                                                        }
                                                    }
                                                }else {
                                                    LocalMedia localMedia =new LocalMedia();
                                                    localMedia.setCompressed(true);
                                                    localMedia.setCompressPath(LaohuPinTuanNextTwoActivity.prizePath8);
                                                    localMedia.setPath( LaohuPinTuanNextTwoActivity.prizePath8);
                                                    LaohuPinTuanNextTwoActivity.selectList.add(localMedia);
                                                }
                                            }


                                            fragment.setImage();
                                        }
                                        List<PrizeDefaultBean> winList = new ArrayList<>();


                                        if(wins != null && wins.size()>0){
                                           String id =wins.get(0);
                                           if("Prize7".equals(isUnPrize)){ //安慰奖
                                                LaohuPinTuanNextTwoActivity.prizePath7 = HttpUrls.IMAGE_ULR + id;
                                                EventBus.getDefault().post(new LhGeEventBus(id,"安慰奖"));
                                                fragment.prizePath = LaohuPinTuanNextTwoActivity.prizePath7;
                                           }else if("Prize1".equals(isUnPrize)){ //条件1~7
                                                LaohuPinTuanNextTwoActivity.prizePath1 = HttpUrls.IMAGE_ULR + id;
                                                EventBus.getDefault().post(new LhGeEventBus(id,"条件一"));
                                               fragment.prizePath = LaohuPinTuanNextTwoActivity.prizePath1;
                                            }else if("Prize2".equals(isUnPrize)){
                                                LaohuPinTuanNextTwoActivity.prizePath2 = HttpUrls.IMAGE_ULR + id;
                                                EventBus.getDefault().post(new LhGeEventBus(id,"条件二"));
                                               fragment.prizePath = LaohuPinTuanNextTwoActivity.prizePath2;
                                            }else if("Prize3".equals(isUnPrize)){
                                                LaohuPinTuanNextTwoActivity.prizePath3 = HttpUrls.IMAGE_ULR + id;
                                                EventBus.getDefault().post(new LhGeEventBus(id,"条件三"));
                                               fragment.prizePath = LaohuPinTuanNextTwoActivity.prizePath3;
                                            }else if("Prize4".equals(isUnPrize)){
                                                LaohuPinTuanNextTwoActivity.prizePath4 = HttpUrls.IMAGE_ULR + id;
                                                EventBus.getDefault().post(new LhGeEventBus(id,"条件四"));
                                               fragment.prizePath = LaohuPinTuanNextTwoActivity.prizePath3;
                                            }else if("Prize5".equals(isUnPrize)){
                                                LaohuPinTuanNextTwoActivity.prizePath5 = HttpUrls.IMAGE_ULR + id;
                                                EventBus.getDefault().post(new LhGeEventBus(id,"条件五"));
                                               fragment.prizePath = LaohuPinTuanNextTwoActivity.prizePath5;
                                            }else if("Prize6".equals(isUnPrize)){
                                                LaohuPinTuanNextTwoActivity.prizePath6 = HttpUrls.IMAGE_ULR + id;
                                                EventBus.getDefault().post(new LhGeEventBus(id,"条件六"));
                                               fragment.prizePath = LaohuPinTuanNextTwoActivity.prizePath6;
                                           }

                                        }
                                    }

                                }
                            }else if("PrizeTwo".equals(isUnPrize)){
                                LaohuPinTuanNextTwoActivity activity = (LaohuPinTuanNextTwoActivity) context;
                                activity.materialGameList = materialGameList;
                                if(materialGameList != null && materialGameList.size()>0){
                                    if(materialGameList != null && materialGameList.size()>0){
                                        Object win = materialGameList.get(0).getWin();
                                        Object non = materialGameList.get(0).getNon();
                                        List<String> wins = new ArrayList<>();
                                        List<String> nons = new ArrayList<>();
                                        if(win != null){
                                            wins = (List<String>)win;
                                        }

                                        if(non != null){
                                            nons = (List<String>)non;
                                            String nonId =nons.get(0);
                                            LaohuPinTuanNextTwoActivity.prizePath8 = HttpUrls.IMAGE_ULR + nonId;


                                            if("NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                                                LaohuPinTuanNextTwoActivity.selectList = new ArrayList<>();
                                                if(LaohuPinTuanNextTwoActivity.selectList != null && LaohuPinTuanNextTwoActivity.selectList.size()>0){
                                                    for (LocalMedia localMedia:LaohuPinTuanNextTwoActivity.selectList){
                                                        String prizePath = localMedia.getCompressPath();
                                                        if(prizePath != null && !"".equals(prizePath)){
                                                            if("get.do?id=".contains(prizePath)){
                                                                String nonId2  =  prizePath.substring(prizePath.indexOf("get.do?id=")+10,prizePath.length());
                                                                if(nonId2 != null && !"".equals(nonId2)){
                                                                    if(nonId.equals(nonId2)){

                                                                    }else {
                                                                        localMedia.setCompressPath(LaohuPinTuanNextTwoActivity.prizePath8);
                                                                        localMedia.setPath(LaohuPinTuanNextTwoActivity.prizePath8);
                                                                    }
                                                                }
                                                            }



                                                        }
                                                    }
                                                }else {
                                                    LocalMedia localMedia =new LocalMedia();
                                                    localMedia.setCompressed(true);
                                                    localMedia.setCompressPath(LaohuPinTuanNextTwoActivity.prizePath8);
                                                    localMedia.setPath( LaohuPinTuanNextTwoActivity.prizePath8);
                                                    LaohuPinTuanNextTwoActivity.selectList.add(localMedia);
                                                }
                                            }


                                        }

                                        List<PrizeDefaultBean> winList = new ArrayList<>();
                                        if(wins != null && wins.size()>0){
                                            String id =wins.get(0);

                                            if("NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                                                LaohuPinTuanNextTwoActivity.prizePath1 = HttpUrls.IMAGE_ULR + id;
                                                LaohuPinTuanNextTwoActivity.prizePath2 = HttpUrls.IMAGE_ULR + id;
                                                LaohuPinTuanNextTwoActivity.prizePath3 = HttpUrls.IMAGE_ULR + id;
                                                LaohuPinTuanNextTwoActivity.prizePath4 = HttpUrls.IMAGE_ULR + id;
                                                LaohuPinTuanNextTwoActivity.prizePath5 = HttpUrls.IMAGE_ULR + id;
                                                LaohuPinTuanNextTwoActivity.prizePath6 = HttpUrls.IMAGE_ULR + id;
                                                LaohuPinTuanNextTwoActivity.prizePath7 = HttpUrls.IMAGE_ULR + id;
                                                activity.prizePath = HttpUrls.IMAGE_ULR + id;
                                            }else {
                                                if( LaohuPinTuanNextTwoActivity.prizePath1 != null && !"".equals( LaohuPinTuanNextTwoActivity.prizePath1)){

                                                }else {
                                                    LaohuPinTuanNextTwoActivity.prizePath1 = HttpUrls.IMAGE_ULR + id;
                                                }

                                                if( LaohuPinTuanNextTwoActivity.prizePath2!= null && !"".equals( LaohuPinTuanNextTwoActivity.prizePath2)){

                                                }else {
                                                    LaohuPinTuanNextTwoActivity.prizePath2 = HttpUrls.IMAGE_ULR + id;
                                                }

                                                if( LaohuPinTuanNextTwoActivity.prizePath3!= null && !"".equals( LaohuPinTuanNextTwoActivity.prizePath3)){

                                                }else {
                                                    LaohuPinTuanNextTwoActivity.prizePath3 = HttpUrls.IMAGE_ULR + id;
                                                }

                                                if( LaohuPinTuanNextTwoActivity.prizePath4!= null && !"".equals( LaohuPinTuanNextTwoActivity.prizePath4)){

                                                }else {
                                                    LaohuPinTuanNextTwoActivity.prizePath4 = HttpUrls.IMAGE_ULR + id;
                                                }

                                                if( LaohuPinTuanNextTwoActivity.prizePath5!= null && !"".equals( LaohuPinTuanNextTwoActivity.prizePath5)){

                                                }else {
                                                    LaohuPinTuanNextTwoActivity.prizePath5 = HttpUrls.IMAGE_ULR + id;
                                                }
                                                if( LaohuPinTuanNextTwoActivity.prizePath6!= null && !"".equals( LaohuPinTuanNextTwoActivity.prizePath6)){

                                                }else {
                                                    LaohuPinTuanNextTwoActivity.prizePath6 = HttpUrls.IMAGE_ULR + id;
                                                }
                                                if( LaohuPinTuanNextTwoActivity.prizePath7!= null && !"".equals( LaohuPinTuanNextTwoActivity.prizePath7)){

                                                }else {
                                                    LaohuPinTuanNextTwoActivity.prizePath7 = HttpUrls.IMAGE_ULR + id;
                                                }
                                                if( LaohuPinTuanNextTwoActivity.prizePath2!= null && !"".equals( LaohuPinTuanNextTwoActivity.prizePath2)){

                                                }else {
                                                    LaohuPinTuanNextTwoActivity.prizePath2 = HttpUrls.IMAGE_ULR + id;
                                                }
                                            }


                                            activity.prizePath = HttpUrls.IMAGE_ULR + id;

                                        }
                                    }

                                }else {
                                    LaohuPinTuanNextTwoActivity.selectList = new ArrayList<>();
                                    LocalMedia localMedia =new LocalMedia();
                                    localMedia.setCompressed(true);
                                    localMedia.setCompressPath(LaohuPinTuanNextTwoActivity.prizePath8);
                                    localMedia.setPath( LaohuPinTuanNextTwoActivity.prizePath8);
                                    LaohuPinTuanNextTwoActivity.selectList.add(localMedia);
                                }
                                //初始化加载刷新奖项图片
                                activity.pagerAdapter.updateData(activity.prizeBeanList);

                            }else {
                                LaohuPinTuanNextThreeActivity activity = (LaohuPinTuanNextThreeActivity) context;
                                activity.materialGameList = materialGameList;
                                activity.materialGame = activity.materialGameList.get(0);
                                activity.posterId = String.valueOf(activity.materialGame.getId());
                                if(activity.materialGame != null){
                                    activity.forwardTitle =activity.materialGame.getShareTitle();
                                    activity.forwardContent = activity.materialGame.getShareDesc();

                                    activity.edt_forward_title.setText( activity.forwardTitle != null ? activity.forwardTitle :"");
                                    activity.edt_forward_content.setText( activity.forwardContent != null ? activity.forwardContent :"");
                                    activity.backGroundId = activity.materialGame.getBackground();
                                    activity.wxIconId = activity.materialGame.getWxLinkIcon();
                                    activity.bannerId = activity.materialGame.getBanner();
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
    public void queryTemplateTigerPoster(Context context, String userId, String compId, String id,String templateType) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("compId","");
        params.put("id","");
        params.put("templateType",templateType);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        LaoHuTempletActivity activity = (LaoHuTempletActivity) context;

        campaignServiceManager.queryTigerPoster(userId,compId,id,templateType)
                .subscribe(new BaseObserver<List<MaterialGame>>(context,false){
                    @Override
                    public void onSuccess(List<MaterialGame>  materialGameList, String msg) {

                        /***
                         *
                         * {"code":"1","message":"获取游戏素材成功","result":[{"background":"5c6e888d273c7b12dddce37b","backgroundThumb":"5c6e888d273c7b12dddce37b","banner":"5bf8f020027005387c9a6516","bannerThumb":"5bf8f020027005387c9a6516",
                         * "campaignListPic":"","campaignNonrewardPic":"5c6e886c273c7b12dddce379","campaignWinPic":"5c6e8827273c7b12dddce36f-5c6e8835273c7b12dddce371-5c6e883e273c7b12dddce373-5c6e8848273c7b12dddce375-5c6e8862273c7b12dddce377-5c85cf17273c7b49e7d3a2c9-5c85cf42273c7b49e7d3a2cb",
                         * "compId":1,"id":0,"miniLinkIcon":"5c6e88d4273c7b12dddce385","name":"老虎机","non":["5c6e886c273c7b12dddce379"],"shareDesc":"亲，快来点我玩游戏获大奖喽~","shareTitle":"[点我玩，获优惠]确认了眼神，你就是幸运的人","templateType":"lh",
                         * "win":["5c6e8827273c7b12dddce36f","5c6e8835273c7b12dddce371",
                         * "5c6e883e273c7b12dddce373","5c6e8848273c7b12dddce375","5c6e8862273c7b12dddce377","5c85cf17273c7b49e7d3a2c9","5c85cf42273c7b49e7d3a2cb"],"wxLinkIcon":"5c6e889e273c7b12dddce380"}]}
                         * */
                        if(materialGameList!=null && materialGameList.size()>0){
                            activity.materialGameList.clear();
                            activity.materialGameList.addAll(materialGameList) ;

                           /* if( activity.posterId != null && !"".equals(activity.posterId)){
                                for (MaterialGame materialGame : activity.materialGameList){
                                    String pId = "";
                                    if(materialGame.getId() != null){
                                        pId = String.valueOf(materialGame.getId());
                                        if(activity.posterId .equals(pId)){
                                            materialGame.setChecked(true);
                                        }
                                    }
                                }
                            }*/
                           MaterialGame materialGame = materialGameList.get(0);

                           if(materialGame != null){
                               activity.shareTitle =activity.materialGame.getShareTitle();
                               activity.shareDesc= activity.materialGame.getShareDesc();
                           }

                            activity.mAdapter.notifyDataSetChanged();

                        }


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });
    }

    @Override
    public void queryTigerPoster(Context context, String userId, String compId, String id,String isUnPrize,String templateType) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("compId",compId);
        params.put("id",id);
        params.put("templateType",templateType);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);



        campaignServiceManager.queryTigerPoster(userId,compId,id,templateType)
                .subscribe(new BaseObserver<List<MaterialGame>>(context,false){
                    @Override
                    public void onSuccess(List<MaterialGame>  materialGameList, String msg) {

                        if(materialGameList!=null){

                            if("Prize1".equals(isUnPrize)||"Prize2".equals(isUnPrize)||"Prize3".equals(isUnPrize)||"Prize4".equals(isUnPrize)
                                    ||"Prize5".equals(isUnPrize)||"Prize6".equals(isUnPrize)||"Prize7".equals(isUnPrize)||"Prize8".equals(isUnPrize)){ //条件1~7
                                PrizeTempletActivity activity = (PrizeTempletActivity) context;
                                if(materialGameList != null && materialGameList.size()>0){
                                    activity.materialGameList = materialGameList;
                                    if(activity.materialGameList != null && activity.materialGameList.size()>0){


                                        Object win = activity.materialGameList.get(0).getWin();
                                        Object non = activity.materialGameList.get(0).getNon();
                                        List<String> wins = new ArrayList<>();
                                        List<String> nons = new ArrayList<>();
                                        if(win != null){
                                            wins = (List<String>)win;
                                        }
                                        if(non != null){
                                            nons  = (List<String>)non;
                                        }
                                        List<PrizeDefaultBean> nonList = new ArrayList<>();
                                        List<PrizeDefaultBean> winList = new ArrayList<>();
                                        if(nons != null && nons.size()>0){
                                            for (String id : nons){
                                                PrizeDefaultBean bean = new PrizeDefaultBean();
                                                bean.setId(id);
                                                bean.setChecked(false);
                                                nonList.add(bean);
                                            }
                                            activity.nonList = nonList;
                                        }

                                        if(wins != null && !"".equals(wins)){
                                            for (String id : wins){
                                                PrizeDefaultBean bean = new PrizeDefaultBean();
                                                bean.setId(id);
                                                bean.setChecked(false);
                                                winList.add(bean);
                                            }
                                            activity.winList = winList;
                                        }


                                    }

                                    activity.setPostAdapter();
                                }
                            }else if("lhptThree".equals(isUnPrize)){
                                LaohuPinTuanNextThreeActivity activity = (LaohuPinTuanNextThreeActivity) context;
                                activity.materialGameList = materialGameList;
                                if(activity.materialGameList != null && activity.materialGameList.size()>0){
                                    activity.materialGame = activity.materialGameList.get(0);
                                    activity.posterId = String.valueOf(activity.materialGame.getId());
                                    if(activity.materialGame != null){
                                        if("S".equals(LaohuPinTuanNewActivity.compaignStatus)){  //编辑页面
                                            activity.wxIconId = activity.materialGame.getWxLinkIcon();
                                            activity.bannerId = activity.materialGame.getBanner();
                                            activity.miniLinkIcon = activity.materialGame.getMiniLinkIcon();
                                            activity.backGroundId = activity.materialGame.getBackground();

                                        }else if("NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){   //新建活动
                                            activity.forwardTitle =activity.materialGame.getShareTitle();
                                            activity.forwardContent = activity.materialGame.getShareDesc();
                                          /*  if("YES".equals(activity.isEditCampaign)){
                                                if("NO".equals(activity.isCopyCreate)){
                                                    activity.forwardTitle = "";
                                                    activity.forwardContent = "";
                                                }else {

                                                }
                                            }else {
                                            }*/

                                        }

                                        activity.edt_forward_content.setText( activity.forwardContent != null && !"".equals(activity.forwardContent)? activity.forwardContent :LaoHuTempletActivity.shareDesc);
                                        activity.edt_forward_title.setText( activity.forwardTitle != null && !"".equals(activity.forwardTitle) ? activity.forwardTitle :LaoHuTempletActivity.shareTitle);

                                        if(activity.edt_forward_content.getText().toString().trim() != null && !"".equals(activity.edt_forward_content.getText().toString().trim())){
                                            int strLen = activity.edt_forward_content.getText().toString().trim().length();
                                            activity.tv_content_num.setText(strLen+"/45");
                                        }

                                        if(activity.edt_forward_title.getText().toString().trim() != null && !"".equals(activity.edt_forward_title.getText().toString().trim())){
                                            int strLen = activity.edt_forward_title.getText().toString().trim().length();
                                            activity.tv_title_num.setText(strLen+"/30");
                                        }
                                        activity.wxIconId = activity.materialGame.getWxLinkIcon();
                                        activity.bannerId = activity.materialGame.getBanner();
                                        activity.miniLinkIcon = activity.materialGame.getMiniLinkIcon();
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
    public void saveCampaignInfoForgame(Context context, Campaign campaign, String storeSelectType, String storeStr, List<CampaignNonrewardPic> campaignNonrewardPics,
                                        List<CampaignGamesetreward> rewardlist, CampaignGame campaignGame, String posterId, String userId, String compId, String btnFlag) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("compId",compId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        LaohuPinTuanNextThreeActivity activity = (LaohuPinTuanNextThreeActivity) context;
        JSONObject jsonObject = new JSONObject();
        String jsonStr = "";
        Map<String,Object> jsonMap = new HashMap<String,Object>();
        try{
            jsonMap.put("userId",userId);
            jsonMap.put("storeStr",storeStr);
            jsonMap.put("posterId",posterId);
            jsonMap.put("btnFlag",btnFlag);
            String campaignJSON=new Gson().toJson(campaign);
            jsonMap.put("campaign",campaignJSON);
            String campaignGameJSON=new Gson().toJson(campaignGame);
            jsonMap.put("campaignGame",campaignGameJSON);
            jsonMap.put("compId",compId);

            List<String> campaignNonrewardPicList = new ArrayList<>();

            if(campaignNonrewardPics != null && campaignNonrewardPics.size()>0){
                for (CampaignNonrewardPic campaignNonrewardPic1 : campaignNonrewardPics){
                    if(campaignNonrewardPic1 != null){
                        String pictureId = campaignNonrewardPic1.getPictureId();
                        campaignNonrewardPicList.add(pictureId);
                    }
                }
            }
            jsonMap.put("campaignNonrewardPic",campaignNonrewardPicList);
            String rewardlistJSON=new Gson().toJson(rewardlist);
            jsonMap.put("rewardlist",rewardlist);
            jsonMap.put("storeSelectType",storeSelectType);
//            jsonObject = new JSONObject(jsonMap);
            jsonStr = JSON.toJSONString(jsonMap);
        }catch (Exception e){

        }

        campaignServiceManager.saveCampaignInfoForgame(jsonStr)
                .subscribe(new BaseObserver<CampaignSaveResult>(context,false){
                    @Override
                    public void onSuccess(CampaignSaveResult result, String msg) {

                        if(result!=null){
                            if("save".equals(btnFlag)){
                                ToastUtils.showCustomShortBottom("保存成功");
                            }else {
                                ToastUtils.showCustomShortBottom("发布成功");
                            }

                            ActivityUtils.startActivity(CampaignListActivity.class, R.anim.slide_in_right,R.anim.slide_out_left);


                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });



    }

    @Override
    public void doEditForGame(Context context, String campaignId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("campaignId",campaignId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        LaohuPinTuanNewActivity campaignNewActivity =(LaohuPinTuanNewActivity) context;
        campaignServiceManager.doEditForGame(campaignId)
                .subscribe(new BaseObserver<LhPtEditBean>(context,true){
                    @Override
                    public void onSuccess(LhPtEditBean lhPtEditBean, String msg) {

                        if(lhPtEditBean!=null){
                            campaignNewActivity.campaign  =lhPtEditBean.getCampaign();
                            if(campaignNewActivity.campaign!=null){
                                SPUtils.getInstance().putData("campaign",campaignNewActivity.campaign);

                            }
                            campaignNewActivity.campaignType = lhPtEditBean.getCampaign().getCampaignType();
                            //获取素材id 和海报
                            campaignNewActivity.posterId = lhPtEditBean.getCampaign().getPosterTemplete();
                            campaignNewActivity.backGroundId = lhPtEditBean.getCampaign().getPoster().getBackground();
                            campaignNewActivity.miniLinkIcon = lhPtEditBean.getCampaign().getPoster().getMiniLinkIcon();
                            campaignNewActivity.storeList = lhPtEditBean.getStore();
                            if(campaignNewActivity.storeList!=null && campaignNewActivity.storeList.size()>0){
                                StringBuilder storeIdSb = new StringBuilder();
                                StringBuilder storeNameSb = new StringBuilder();
                                campaignNewActivity.brandLogo = SPUtils.getInstance().getString("brandLogo");
                                RoundedCorners roundedCorners= new RoundedCorners(8);
                                RequestOptions requestOptions = new RequestOptions()
                                        .bitmapTransform(roundedCorners)
                                        .placeholder(R.drawable.ic_logo_default)
                                        .error(R.drawable.ic_logo_default)
                                        .fallback(R.drawable.ic_logo_default);

                                Glide.with(context).load(campaignNewActivity.brandLogo).apply(requestOptions).into(campaignNewActivity.ivBrand);

                                campaignNewActivity.storeIdCampaignType =campaignNewActivity.storeList.get(0).getStoreSelectType();
                                if("A".equals(campaignNewActivity.storeIdCampaignType)){
                                    campaignNewActivity.storeId = "";
                                    campaignNewActivity.storeStr = "";
                                    campaignNewActivity.storeName = "#全部店铺";
                                    campaignNewActivity.tvStoreNames.setText(campaignNewActivity.storeName);
                                    for (Store store:campaignNewActivity.storeList){
                                        store.setChecked(false);

                                    }

                                }  else if("D".equals(campaignNewActivity.storeIdCampaignType)){
                                    campaignNewActivity.storeId = "";
                                    campaignNewActivity.storeStr = "";
                                    campaignNewActivity.storeName = "#全部自营";
                                    campaignNewActivity.tvStoreNames.setText(campaignNewActivity.storeName);
                                    for (Store store:campaignNewActivity.storeList){
                                        store.setChecked(false);

                                    }
                                }else if("J".equals(campaignNewActivity.storeIdCampaignType)){
                                    campaignNewActivity.storeId = "";
                                    campaignNewActivity.storeStr = "";

                                    campaignNewActivity.storeName = "#全部加盟";
                                    campaignNewActivity.tvStoreNames.setText(campaignNewActivity.storeName);
                                    for (Store store:campaignNewActivity.storeList){
                                        store.setChecked(false);
                                    }
                                }else {
                                    campaignNewActivity.storeIdCampaignType = "";
                                    for (Store store:campaignNewActivity.storeList){
                                        store.setChecked(false);
                                        if(store.getId()>0){
                                            String storeId = String.valueOf(store.getId());
                                            String storeName = store.getName()!=null?"#"+store.getName()+ "    ":"";
                                            storeIdSb.append(storeId).append("-");
                                            storeNameSb.append(storeName);
                                        }


                                    }
                                    String storeIdArr ;
                                    if(!storeIdSb.toString().isEmpty()){
                                        storeIdArr = storeIdSb.substring(0,storeIdSb.length() - 1);
                                        campaignNewActivity.storeId = storeIdArr;
                                        campaignNewActivity.storeStr = campaignNewActivity.storeId;
                                    }
                                    campaignNewActivity.storeName =storeNameSb.toString()!=null?storeNameSb.toString():"";
                                    campaignNewActivity.tvStoreNames.setText(campaignNewActivity.storeName);

                                }

                            }


                            campaignNewActivity.campaignName = campaignNewActivity.campaign.getCampaignName();
                            campaignNewActivity.startDate = campaignNewActivity.campaign.getBeginDate();
                            campaignNewActivity.endDate = campaignNewActivity.campaign.getEndDate();
                            if(campaignNewActivity.startDate!=null && !"".equals(campaignNewActivity.startDate)){
                                Date date = DateUtils.parse(campaignNewActivity.startDate,"yyyy-MM-dd HH:mm");
                                ;
                                campaignNewActivity.tvStartTime.setText(DateUtils.formatDate(date,"yyyy-MM-dd HH:mm").replace("-","."));
                            }else {
                                campaignNewActivity.tvStartTime.setText("");
                            }

                            if(campaignNewActivity.endDate!=null && !"".equals(campaignNewActivity.endDate)){
                                Date date = DateUtils.parse(campaignNewActivity.endDate,"yyyy-MM-dd HH:mm");
                                ;
                                campaignNewActivity.tvEndTime.setText(DateUtils.formatDate(date,"yyyy-MM-dd HH:mm").replace("-","."));
                            }else {
                                campaignNewActivity.tvEndTime.setText("");
                            }

                            campaignNewActivity.edtName.setText(campaignNewActivity.campaignName!=null?campaignNewActivity.campaignName:"");


                            LaohuPinTuanNextThreeActivity.campaignGame = lhPtEditBean.getCampaignGame();

                            if(LaohuPinTuanNextThreeActivity.campaignGame  == null){
                                LaohuPinTuanNextThreeActivity.campaignGame = new CampaignGame();
                            }
                            //未中奖
                            if(lhPtEditBean.getNonRewardList() != null && lhPtEditBean.getNonRewardList().size()>0){
                                LaohuPinTuanNextTwoActivity.campaignNonrewardPicList = lhPtEditBean.getNonRewardList();

                            }
                            LaohuPinTuanNextTwoActivity.campaignGamesetrewardList = new ArrayList<>();
                            //获取中奖和未中奖
                            List<LhPtEditBean.RewardBean>  rewardBeanList= lhPtEditBean.getGamesecondinfo();
                            if(rewardBeanList != null){
                                for (LhPtEditBean.RewardBean rewardBean : rewardBeanList){
                                    CampaignGamesetreward campaignGamesetreward = rewardBean.getRewardinfo();

                                    ArrayList<CampaignCoupon> couponinfo = rewardBean.getCouponinfo();
                                    if(campaignGamesetreward != null){
                                        String rewardType = campaignGamesetreward.getRewardType();
                                        Integer rewardOrder = campaignGamesetreward.getPrizeOrder();
                                        campaignGamesetreward.setList(couponinfo);

                                        if("1".equals(rewardType)){
                                            if(rewardOrder != null){
                                                if(rewardOrder == 1){
                                                    campaignGamesetreward.setPrizeName("奖项一");
                                                    LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
                                                }else if(rewardOrder == 2){
                                                    campaignGamesetreward.setPrizeName("奖项二");
                                                    LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
                                                }else if(rewardOrder == 3){
                                                    campaignGamesetreward.setPrizeName("奖项三");
                                                    LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
                                                }else if(rewardOrder == 4){
                                                    campaignGamesetreward.setPrizeName("奖项四");
                                                    LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
                                                }else if(rewardOrder == 5){
                                                    campaignGamesetreward.setPrizeName("奖项五");
                                                    LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
                                                }else if(rewardOrder == 6){
                                                    campaignGamesetreward.setPrizeName("奖项六");

                                                    LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
                                                }
                                            }
                                        }else if("0".equals(rewardType)){ //安慰奖
                                            campaignGamesetreward.setPrizeName("安慰奖");
                                            LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
                                            LaohuPinTuanNextTwoActivity.campaignNonrewardPicList = new ArrayList<CampaignNonrewardPic>();
                                        }else {
                                            if(rewardOrder != null){
                                                if(rewardOrder == 1){
                                                    campaignGamesetreward.setPrizeName("奖项一");
                                                    LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
                                                }else if(rewardOrder == 2){
                                                    campaignGamesetreward.setPrizeName("奖项二");
                                                    LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
                                                }else if(rewardOrder == 3){
                                                    campaignGamesetreward.setPrizeName("奖项三");
                                                    LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
                                                }else if(rewardOrder == 4){
                                                    campaignGamesetreward.setPrizeName("奖项四");
                                                    LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
                                                }else if(rewardOrder == 5){
                                                    campaignGamesetreward.setPrizeName("奖项五");
                                                    LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
                                                }else if(rewardOrder == 6){
                                                    campaignGamesetreward.setPrizeName("奖项六");
                                                    LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            LogUtils.e("------老虎机砸金蛋------"+LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.toString());

                        }



                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });



    }

    @Override
    public void uploadFiles(Context context, List<File> fileList,String name) {
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
        LaohuPinTuanNextTwoActivity activity = (LaohuPinTuanNextTwoActivity)context;

        campaignServiceManager.uploadFiles(fileList)
                .subscribe(new BaseObserver<Map<String,Object>>(context,false){
                    @Override
                    public void onSuccess(Map<String,Object> map, String msg) {
                        String imageId = "";
                        if(map != null && map.size()>0){
                            imageId =(String)map.get("id");

                            if("未中奖".equals(name)){
                                if(imageId.contains(",")){
                                    String[] ids = imageId.split(",");

                                    for (int i = 0;i<ids.length;i++){
                                        if(LaohuPinTuanNextTwoActivity.selectList!= null && LaohuPinTuanNextTwoActivity.selectList.size()>0){
                                            for (LocalMedia localMedia : LaohuPinTuanNextTwoActivity.selectList){
                                                if(localMedia != null){
                                                    String path = localMedia.getPath();
                                                    if(!path.contains("get.do")){
                                                        localMedia.setCompressPath(HttpUrls.IMAGE_ULR + ids[i]);
                                                        localMedia.setCompressed(true);
                                                        localMedia.setPath(HttpUrls.IMAGE_ULR + ids[i]);
                                                        break;

                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if("YES".equals(activity.isUpAndNext)){
                                        activity.setIntentUpActivity();
                                    }else if("NO".equals(activity.isUpAndNext)) {
                                        activity.setIntentActivity();
                                    }else if("COPY".equals(activity.isUpAndNext)) {
                                        activity.copyAndCreate();
                                    }

                                }else {
                                    for (LocalMedia localMedia : LaohuPinTuanNextTwoActivity.selectList){
                                        if(localMedia != null){
                                            String path = localMedia.getPath();
                                            if(!path.contains("get.do")){
                                                localMedia.setCompressPath(HttpUrls.IMAGE_ULR + imageId);
                                                localMedia.setCompressed(true);
                                                localMedia.setPath(HttpUrls.IMAGE_ULR + imageId);

                                            }

                                        }
                                    }
                                    if("YES".equals(activity.isUpAndNext)){
                                        activity.setIntentUpActivity();
                                    }else if("NO".equals(activity.isUpAndNext)) {
                                        activity.setIntentActivity();
                                    }else if("COPY".equals(activity.isUpAndNext)) {
                                        activity.copyAndCreate();
                                    }
                                }
                            }else {
                                if("奖项一".equals(name)){
                                    EventBus.getDefault().post(new LhGeEventBus(imageId,name));
                                    LaohuPinTuanNextTwoActivity.prizePath1 = HttpUrls.IMAGE_ULR + imageId;
                                }else if("奖项二".equals(name)){
                                    EventBus.getDefault().post(new LhGeEventBus(imageId,name));
                                    LaohuPinTuanNextTwoActivity.prizePath2 = HttpUrls.IMAGE_ULR + imageId;
                                }else if("奖项三".equals(name)){
                                    EventBus.getDefault().post(new LhGeEventBus(imageId,name));
                                    LaohuPinTuanNextTwoActivity.prizePath3 = HttpUrls.IMAGE_ULR + imageId;
                                }else if("奖项四".equals(name)){
                                    EventBus.getDefault().post(new LhGeEventBus(imageId,name));
                                    LaohuPinTuanNextTwoActivity.prizePath4 = HttpUrls.IMAGE_ULR + imageId;
                                }else if("奖项五".equals(name)){
                                    EventBus.getDefault().post(new LhGeEventBus(imageId,name));
                                    LaohuPinTuanNextTwoActivity.prizePath5 = HttpUrls.IMAGE_ULR + imageId;
                                }else if("奖项六".equals(name)){
                                    EventBus.getDefault().post(new LhGeEventBus(imageId,name));
                                    LaohuPinTuanNextTwoActivity.prizePath6 = HttpUrls.IMAGE_ULR + imageId;
                                }else if("安慰奖".equals(name)){
                                    EventBus.getDefault().post(new LhGeEventBus(imageId,name));
                                    LaohuPinTuanNextTwoActivity.prizePath7= HttpUrls.IMAGE_ULR + imageId;
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
    public void queryMemberInfoPerfectStoreList(Context context, String userId, boolean isDefaultAllStore, boolean isMemberInfoPerfectTwo) {
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
        campaignServiceManager.queryBrandAndStoreList(userId)
                .subscribe(new BaseObserver<MoreStoreBean>(context,false){


                    @Override
                    public void onSuccess(MoreStoreBean moreStoreBean,String msg) {

                        if(isMemberInfoPerfectTwo){
                            MemberInfoPerfectNewActivity memberInfoPerfectActivity = (MemberInfoPerfectNewActivity)context;
                            memberInfoPerfectActivity.branList = moreStoreBean.getBrandList();
                            if(isDefaultAllStore){

                                if(memberInfoPerfectActivity.branList !=null && memberInfoPerfectActivity.branList.size()>0){
                                    if(memberInfoPerfectActivity.branList.get(0)!=null){
                                        memberInfoPerfectActivity.storeList = memberInfoPerfectActivity.branList.get(0).getStoreList();
                                        StringBuilder storeIdSb = new StringBuilder();
                                        StringBuilder storeNameSb = new StringBuilder();
                                        String storeIdArr = "";
                                        for (Store store:memberInfoPerfectActivity.storeList){
                                            store.setChecked(false);
                                            String storeId = store.getStoreId();
                                            String storeName = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                            storeIdSb.append(storeId).append("-");
                                            storeNameSb.append(storeName);
                                        }

                                    }

                                }else {

                                    if("NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                                        memberInfoPerfectActivity.tvStoreNames.setText("#全部店铺");
                                    }

                                }

                            }else {
                                memberInfoPerfectActivity.showPopView(context);
                            }
                        }else {
                            MemberInfoPerfectCouponNextSettingFragment nextSettingFragment = (MemberInfoPerfectCouponNextSettingFragment)view;
                            nextSettingFragment.branList = moreStoreBean.getBrandList();
                            if(isDefaultAllStore){

                                if(nextSettingFragment.branList !=null && nextSettingFragment.branList.size()>0){
                                    if(nextSettingFragment.branList.get(0)!=null){
                                        nextSettingFragment.storeList = nextSettingFragment.branList.get(0).getStoreList();
                                        StringBuilder storeIdSb = new StringBuilder();
                                        StringBuilder storeNameSb = new StringBuilder();
                                        String storeIdArr = "";
                                        for (Store store:nextSettingFragment.storeList){
                                            store.setChecked(false);
                                            String storeId = store.getStoreId();
                                            String storeName = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                            storeIdSb.append(storeId).append("-");
                                            storeNameSb.append(storeName);
                                        }

                                    }

                                }else {
                                    if("NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                                        nextSettingFragment.tvStoreNames.setText("#全部店铺");
                                    }
                                }

                            }else {
                                nextSettingFragment.showPopView(context);
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
    public void queryJzStoreList(Context context, String userId, boolean isDefaultAllStore, boolean isOneOrTwo) {
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
        campaignServiceManager.queryBrandAndStoreList(userId)
                .subscribe(new BaseObserver<MoreStoreBean>(context,false){


                    @Override
                    public void onSuccess(MoreStoreBean moreStoreBean,String msg) {

                        if(isOneOrTwo){
                            CampaignLikeNewActivity activity = (CampaignLikeNewActivity)context;
                            activity.branList = moreStoreBean.getBrandList();
                            if(isDefaultAllStore){

                                if(activity.branList !=null && activity.branList.size()>0){
                                    if(activity.branList.get(0)!=null){
                                        activity.storeList = activity.branList.get(0).getStoreList();
                                        StringBuilder storeIdSb = new StringBuilder();
                                        StringBuilder storeNameSb = new StringBuilder();
                                        String storeIdArr = "";
                                        for (Store store:activity.storeList){
                                            store.setChecked(false);
                                            String storeId = store.getStoreId();
                                            String storeName = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                            storeIdSb.append(storeId).append("-");
                                            storeNameSb.append(storeName);
                                        }

                                    }

                                }else {

                                    if("NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                                        activity.tvStoreNames.setText("#全部店铺");
                                    }

                                }

                            }else {
                                activity.showPopView(context);
                            }
                        }else {
                            CampaignLikeTwoCouponSettingFragment fragment = (CampaignLikeTwoCouponSettingFragment)view;
                            fragment.branList = moreStoreBean.getBrandList();
                            if(isDefaultAllStore){

                                if(fragment.branList !=null && fragment.branList.size()>0){
                                    if(fragment.branList.get(0)!=null){
                                        fragment.storeList = fragment.branList.get(0).getStoreList();
                                        StringBuilder storeIdSb = new StringBuilder();
                                        StringBuilder storeNameSb = new StringBuilder();
                                        String storeIdArr = "";
                                        for (Store store:fragment.storeList){
                                            store.setChecked(false);
                                            String storeId = store.getStoreId();
                                            String storeName = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                            storeIdSb.append(storeId).append("-");
                                            storeNameSb.append(storeName);
                                        }

                                    }

                                }else {
                                    if("NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                                        fragment.tvStoreNames.setText("#全部店铺");
                                    }
                                }

                            }else {
                                fragment.showPopView(context);
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
    public void queryJzTigerPoster(Context context, String userId, String compId, String id ,String templateType) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("compId","");
        params.put("id","");
        params.put("templateType",templateType);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        CampaignLikeThreeActivity activity = (CampaignLikeThreeActivity) context;

        campaignServiceManager.queryJzTigerPoster(userId,compId,id,templateType)
                .subscribe(new BaseObserver<List<MaterialGame>>(context,false){
                    @Override
                    public void onSuccess(List<MaterialGame>  result, String msg) {
                        if(result!=null && result.size()>0){
                            activity.materialGameList= result ;
                            activity.materialGame = activity.materialGameList.get(0);
                            activity.posterId = String.valueOf(activity.materialGame.getId());
                            if(activity.materialGame != null){

                                if("S".equals(CampaignLikeNewActivity.compaignStatus)){  //编辑页面


                                }else if("NEW".equals(CampaignLikeNewActivity.compaignStatus)){   //新建活动
                                    activity.forwardTitle =activity.materialGame.getShareTitle();
                                    activity.forwardContent = activity.materialGame.getShareDesc();
                                   /* if("YES".equals(activity.isEditCampaign)){
                                        if("NO".equals(activity.isCopyCreate)){
                                            activity.forwardTitle = "";
                                            activity.forwardContent = "";
                                        }else {

                                        }
                                    }*/
                                    activity.edt_forward_content.setText( activity.forwardContent != null ? activity.forwardContent :"");
                                    activity.edt_forward_title.setText( activity.forwardTitle != null ? activity.forwardTitle :"");


                                    if(activity.edt_forward_content.getText().toString().trim() != null && !"".equals(activity.edt_forward_content.getText().toString().trim())){
                                        int strLen = activity.edt_forward_content.getText().toString().trim().length();
                                        activity.tv_content_num.setText(strLen+"/45");
                                    }

                                    if(activity.edt_forward_title.getText().toString().trim() != null && !"".equals(activity.edt_forward_title.getText().toString().trim())){
                                        int strLen = activity.edt_forward_title.getText().toString().trim().length();
                                        activity.tv_title_num.setText(strLen+"/30");
                                    }

                                }

                                activity.wxIconId = activity.materialGame.getWxLinkIcon();
                                activity.bannerId = activity.materialGame.getBanner();
                                activity.miniLinkIcon = activity.materialGame.getMiniLinkIcon();
                                activity.backGroundId = activity.materialGame.getBackground();
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
    public void doEditForJzGame(Context context, String campaignId) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("campaignId",campaignId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        CampaignLikeNewActivity campaignNewActivity =(CampaignLikeNewActivity) context;
        campaignServiceManager.doEditForJzGame(campaignId)
                .subscribe(new BaseObserver<JiZanResultBean>(context,false){
                    @Override
                    public void onSuccess(JiZanResultBean resultBean, String msg) {

                        if(resultBean!=null){
                            campaignNewActivity.campaign  =resultBean.getCampaign();
                            if(campaignNewActivity.campaign!=null){
                                SPUtils.getInstance().putData("campaign",campaignNewActivity.campaign);
                            }
                            campaignNewActivity.campaignType = resultBean.getCampaign().getCampaignType();

                            campaignNewActivity.storeList = resultBean.getStore();
                            if(campaignNewActivity.storeList!=null && campaignNewActivity.storeList.size()>0){
                                StringBuilder storeIdSb = new StringBuilder();
                                StringBuilder storeNameSb = new StringBuilder();
                                campaignNewActivity.brandLogo = SPUtils.getInstance().getString("brandLogo");
                                RequestOptions requestOptions = new RequestOptions()
                                        .placeholder(R.drawable.ic_logo_default)
                                        .error(R.drawable.ic_logo_default)
                                        .fallback(R.drawable.ic_logo_default);

                                Glide.with(context).load(campaignNewActivity.brandLogo).apply(requestOptions).into(campaignNewActivity.ivBrand);

                                campaignNewActivity.storeIdCampaignType =campaignNewActivity.storeList.get(0).getStoreSelectType();
                                if("A".equals(campaignNewActivity.storeIdCampaignType)){

                                    campaignNewActivity.storeName = "#全部店铺";
                                    campaignNewActivity.tvStoreNames.setText(campaignNewActivity.storeName);
                                    for (Store store:campaignNewActivity.storeList){
                                        store.setChecked(false);
                                        String storeId = String.valueOf(store.getId());
                                        storeIdSb.append(storeId).append("-");

                                    }

                                    String storeIdArr ;
                                    if(!storeIdSb.toString().isEmpty()){
                                        storeIdArr = storeIdSb.substring(0,storeIdSb.length() - 1);
                                        campaignNewActivity.storeId = storeIdArr;
                                    }
                                }  else if("D".equals(campaignNewActivity.storeIdCampaignType)){
                                    campaignNewActivity.storeName = "#全部自营";
                                    campaignNewActivity.tvStoreNames.setText(campaignNewActivity.storeName);
                                    for (Store store:campaignNewActivity.storeList){
                                        store.setChecked(false);

                                    }
                                }else if("J".equals(campaignNewActivity.storeIdCampaignType)){
                                    campaignNewActivity.storeName = "#全部加盟";
                                    campaignNewActivity.tvStoreNames.setText(campaignNewActivity.storeName);
                                    for (Store store:campaignNewActivity.storeList){
                                        store.setChecked(false);

                                    }
                                }else {
                                    campaignNewActivity.storeIdCampaignType = "H";
                                    for (Store store:campaignNewActivity.storeList){
                                        store.setChecked(false);
                                        if(store.getId()>0){
                                            String storeId = String.valueOf(store.getId());
                                            String storeName = store.getName()!=null?"#"+store.getName()+ "    ":"";
                                            storeIdSb.append(storeId).append("-");
                                            storeNameSb.append(storeName);
                                        }


                                    }
                                    String storeIdArr ;
                                    if(!storeIdSb.toString().isEmpty()){
                                        storeIdArr = storeIdSb.substring(0,storeIdSb.length() - 1);
                                        campaignNewActivity.storeId = storeIdArr;
                                    }
                                    campaignNewActivity.storeName =storeNameSb.toString()!=null?storeNameSb.toString():"";
                                    campaignNewActivity.tvStoreNames.setText(campaignNewActivity.storeName);

                                }

//                                SPUtils.getInstance().put("storeIdTypePtOne",campaignNewActivity.storeIdCampaignType);
//                                SPUtils.getInstance().put("storeIdArrayPtOne", campaignNewActivity.storeId);
//                                SPUtils.getInstance().put("storeNameArrayPtOne",campaignNewActivity.storeName);
                            }


                            campaignNewActivity.campaignName = campaignNewActivity.campaign.getCampaignName();
                            campaignNewActivity.startDate = campaignNewActivity.campaign.getBeginDate();
                            campaignNewActivity.endDate = campaignNewActivity.campaign.getEndDate();
                            if(campaignNewActivity.startDate!=null && !"".equals(campaignNewActivity.startDate)){
                                Date date = DateUtils.parse(campaignNewActivity.startDate,"yyyy-MM-dd HH:mm");
                                ;
                                campaignNewActivity.tvStartTime.setText(DateUtils.formatDate(date,"yyyy-MM-dd HH:mm").replace("-","."));
                            }else {
                                campaignNewActivity.tvStartTime.setText("");
                            }

                            if(campaignNewActivity.endDate!=null && !"".equals(campaignNewActivity.endDate)){
                                Date date = DateUtils.parse(campaignNewActivity.endDate,"yyyy-MM-dd HH:mm");
                                ;
                                campaignNewActivity.tvEndTime.setText(DateUtils.formatDate(date,"yyyy-MM-dd HH:mm").replace("-","."));
                            }else {
                                campaignNewActivity.tvEndTime.setText("");
                            }

                            campaignNewActivity.edtName.setText(campaignNewActivity.campaignName!=null?campaignNewActivity.campaignName:"");

                            CampaignLikeThreeActivity.campaignCollect = resultBean.getCampaignCollect();
                            if(CampaignLikeThreeActivity.campaignCollect== null){
                                CampaignLikeThreeActivity.campaignCollect = new CampaignCollect();
                            }


                            CampaignLikeTwoActivity.campaignGamesetrewardList = new ArrayList<>();
                            //获取条件一至条件三 点赞人奖励
                            List<JiZanResultBean.RewardBean>  rewardBeanList= resultBean.getGamesecondinfo();
                            if(rewardBeanList != null){
                                for (JiZanResultBean.RewardBean rewardBean : rewardBeanList){
                                    CampaignGamesetreward campaignGamesetreward = rewardBean.getRewardinfo();

                                    ArrayList<CampaignCoupon> couponinfo = rewardBean.getCouponinfo();

                                    if(campaignGamesetreward != null){
                                        String rewardType = campaignGamesetreward.getRewardType();
                                        Integer rewardOrder = campaignGamesetreward.getPrizeOrder();
                                        campaignGamesetreward.setList(couponinfo);

                                        if("1".equals(rewardType)){
                                            if(rewardOrder != null){
                                                if(rewardOrder == 1){
                                                    campaignGamesetreward.setPrizeName("条件一");
                                                    CampaignLikeTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
                                                }else if(rewardOrder == 2){
                                                    campaignGamesetreward.setPrizeName("条件二");
                                                    CampaignLikeTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
                                                }else if(rewardOrder == 3){
                                                    campaignGamesetreward.setPrizeName("条件三");
                                                    CampaignLikeTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
                                                }
                                            }
                                        }else if("0".equals(rewardType)){ //
                                            campaignGamesetreward.setPrizeName("点赞人奖励");
                                            CampaignLikeTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
                                        }
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
    public void saveJzCampaignInfoForgame(Context context, String jsonStr,String btnFlag) {


        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("jsonstr",jsonStr);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        CampaignLikeThreeActivity activity = (CampaignLikeThreeActivity) context;

        campaignServiceManager.saveCampaignInfoForgame(jsonStr)
                .subscribe(new BaseObserver<CampaignSaveResult>(context,false){
                    @Override
                    public void onSuccess(CampaignSaveResult result, String msg) {

                        if(result!=null){
                            if("save".equals(btnFlag)){
                                ToastUtils.showCustomShortBottom("保存成功");
                            }else {
                                ToastUtils.showCustomShortBottom("发布成功");
                            }

                            ActivityUtils.startActivity(CampaignListActivity.class, R.anim.slide_in_right,R.anim.slide_out_left);
                            activity.finish();

                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });
    }

    @Override
    public void jzUploadFiles(Context context, List<File> fileList) {
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
        CampaignLikeThreeActivity activity = (CampaignLikeThreeActivity)context;

        campaignServiceManager.uploadFiles(fileList)
                .subscribe(new BaseObserver<Map<String,Object>>(context,false){
                    @Override
                    public void onSuccess(Map<String,Object> map, String msg) {
                        String imageId = "";
                        if(map != null && map.size()>0){
                            imageId =(String)map.get("id");
                            activity.prizeImg = imageId;
                            activity.prizeUrl = HttpUrls.IMAGE_ULR + activity.prizeImg;
                            activity.setImage();

                            if(activity.campaignCollect != null){
                                activity.campaignCollect.setDefaultImg(imageId);
                            }

                            for (LocalMedia localMedia : activity.selectList){
                                if(localMedia != null){
                                    String path = localMedia.getPath();
                                    if(!path.contains("get.do")){
                                        localMedia.setCompressPath(HttpUrls.IMAGE_ULR + imageId);
                                        localMedia.setCompressed(true);
                                        localMedia.setPath(HttpUrls.IMAGE_ULR + imageId);

                                    }

                                }
                            }

                            activity.setCampaignCollect();
                            activity.setJsonStr();
                            saveJzCampaignInfoForgame(context,activity.jsonStr,activity.btnFlag);



                        }



                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });
    }



    @Override
    public void saveHyCampaignInfo(Context context, Campaign campaign, String storeSelectType, String storeStr,
                                 List<CampaignCoupon> forwardCoupon, List<CampaignCoupon> receiveCoupon,
                                 String posterId, String userId, String compId, String btnFlag) {


        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("compId",compId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        MemberInfoPerfectNextTwoActivity activity = (MemberInfoPerfectNextTwoActivity) context;
        JSONObject jsonObject = new JSONObject();
        String jsonStr = "";
        try{
            jsonObject.put("userId",userId);
            jsonObject.put("storeStr",storeStr);
            jsonObject.put("posterId",posterId);
            jsonObject.put("btnFlag",btnFlag);
            String campaignJSON=new Gson().toJson(campaign);
            jsonObject.put("campaign",campaignJSON);

            jsonObject.put("compId",compId);
            String forwardCouponJSON = new Gson().toJson(forwardCoupon);
            String receiveCouponJSON = new Gson().toJson(receiveCoupon);
            jsonObject.put("forwardCoupon",forwardCouponJSON);
            jsonObject.put("receiveCoupon",receiveCouponJSON);
            jsonObject.put("storeSelectType",storeSelectType);
            jsonStr = jsonObject.toString();
        }catch (Exception e){

        }

        campaignServiceManager.saveCampaignInfo(jsonStr)
                .subscribe(new BaseObserver<CampaignSaveResult>(context,true){
                    @Override
                    public void onSuccess(CampaignSaveResult result, String msg) {

                        if(result!=null){
                            if("save".equals(btnFlag)){
                                ToastUtils.showCustomShortBottom("保存成功");
                            }else {
                                ToastUtils.showCustomShortBottom("发布成功");
                            }


                            ActivityUtils.startActivity(CampaignListActivity.class, R.anim.slide_in_right,R.anim.slide_out_left);

                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });

    }


    @Override
    public void doHyEdit(Context context, String campaignId) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("campaignId",campaignId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        MemberInfoPerfectNewActivity activity =(MemberInfoPerfectNewActivity) context;
        campaignServiceManager.doEdit(campaignId)
                .subscribe(new BaseObserver<CampaignEditBean>(context,true){
                    @Override
                    public void onSuccess(CampaignEditBean campaignEditBean, String msg) {

                        if(campaignEditBean!=null){
                            activity.campaign  =campaignEditBean.getCampaign();

                            activity.campaign.setCampaignId(Long.parseLong(activity.campaignId));

                            activity.campaignType = campaignEditBean.getCampaign().getCampaignType();

//                            campaignNewActivity.storeName = campaignNewActivity.campaign.getStore().getName();
                            activity.storeList = campaignEditBean.getStore();
                            if(activity.storeList!=null && activity.storeList.size()>0){
                                StringBuilder storeIdSb = new StringBuilder();
                                StringBuilder storeNameSb = new StringBuilder();
                                activity.brandLogo = SPUtils.getInstance().getString("brandLogo");
                                RoundedCorners roundedCorners= new RoundedCorners(8);
                                RequestOptions requestOptions = new RequestOptions()
                                        .bitmapTransform(roundedCorners)
                                        .placeholder(R.drawable.ic_image_loading)
                                        .error(R.drawable.ic_load_fail)
                                        .fallback(R.drawable.ic_image_loading);

                                Glide.with(context).load(activity.brandLogo).apply(requestOptions).into(activity.ivBrand);

                                activity.storeIdCampaignType =activity.storeList.get(0).getStoreSelectType();
                                if("A".equals(activity.storeIdCampaignType)){

                                    activity.storeStr = "";
                                    activity.storeId = "";
                                    activity.storeName = "#全部店铺";
                                    activity.tvStoreNames.setText(activity.storeName);
                                    for (Store store:activity.storeList){
                                        store.setChecked(false);

                                    }

                                }  else if("D".equals(activity.storeIdCampaignType)){
                                    activity.storeStr = "";
                                    activity.storeId = "";
                                    activity.storeName = "#全部自营";
                                    activity.tvStoreNames.setText(activity.storeName);
                                    for (Store store:activity.storeList){
                                        store.setChecked(false);

                                    }
                                }else if("J".equals(activity.storeIdCampaignType)){
                                    activity.storeStr = "";
                                    activity.storeId = "";
                                    activity.storeName = "#全部加盟";
                                    activity.tvStoreNames.setText(activity.storeName);
                                    for (Store store:activity.storeList){
                                        store.setChecked(false);

                                    }
                                }else {
                                    activity.storeIdCampaignType = "";
                                    for (Store store:activity.storeList){
                                        store.setChecked(false);
                                        if(store.getId()>0){
                                            String storeId = String.valueOf(store.getId());
                                            String storeName = store.getName()!=null?"#"+store.getName()+ "    ":"";
                                            storeIdSb.append(storeId).append("-");
                                            storeNameSb.append(storeName);
                                        }


                                    }
                                    String storeIdArr ;
                                    if(!storeIdSb.toString().isEmpty()){
                                        storeIdArr = storeIdSb.substring(0,storeIdSb.length() - 1);
                                        activity.storeId = storeIdArr;
                                        activity.storeStr = activity.storeId;
                                    }
                                    activity.storeName =storeNameSb.toString()!=null?storeNameSb.toString():"";
                                    activity.tvStoreNames.setText(activity.storeName);

                                }

                            }


                            activity.campaignName = activity.campaign.getCampaignName();
                            activity.startDate = activity.campaign.getBeginDate();
                            activity.endDate = activity.campaign.getEndDate();
                            if(activity.startDate!=null && !"".equals(activity.startDate)){
                                Date date = DateUtils.parse(activity.startDate,"yyyy-MM-dd HH:mm");
                                ;
                                activity.edtStart.setText(DateUtils.formatDate(date,"yyyy-MM-dd HH:mm").replace("-","."));
                            }else {
                                activity.edtStart.setText("");
                            }

                            if(activity.endDate!=null && !"".equals(activity.endDate)){
                                Date date = DateUtils.parse(activity.endDate,"yyyy-MM-dd HH:mm");
                                ;
                                activity.edtEnd.setText(DateUtils.formatDate(date,"yyyy-MM-dd HH:mm").replace("-","."));
                            }else {
                                activity.edtEnd.setText("");
                            }

                            activity.edtName.setText(activity.campaignName!=null?activity.campaignName:"");
                            MemberInfoPerfectNextTwoActivity.forwardCouponList = campaignEditBean.getListfoward();

                        }



                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });
    }

}
