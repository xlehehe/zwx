package com.zwx.scan.app.feature.ptmanage;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.luck.picture.lib.entity.LocalMedia;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.DateUtils;
import com.zwx.library.utils.EncryptUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.base.BaseObserver;
import com.zwx.scan.app.data.bean.CampaginGrant;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CampaignGamesetreward;
import com.zwx.scan.app.data.bean.CampaignGroupBuy;
import com.zwx.scan.app.data.bean.CampaignSaveResult;
import com.zwx.scan.app.data.bean.GroupBuyCampagin;
import com.zwx.scan.app.data.bean.MaterialGame;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.PinTuanBean;
import com.zwx.scan.app.data.bean.ResultBean;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.data.http.service.CampaignServiceManager;
import com.zwx.scan.app.data.http.service.RetrofitServiceManager;
import com.zwx.scan.app.feature.campaign.CampaignCouponNextSettingFragment;
import com.zwx.scan.app.feature.campaign.CampaignListActivity;
import com.zwx.scan.app.feature.campaign.CampaignNewActivity;
import com.zwx.scan.app.feature.campaign.PrizeDefaultBean;
import com.zwx.scan.app.feature.member.MemberInfoListAdapter;
import com.zwx.scan.app.utils.SPObjUtil;

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
public class PtPresenter implements PtContract.Presenter {


    private  PtContract.View view;
    private CampaignServiceManager campaignServiceManager;

    //添加订阅，应用于取消订阅，防止造成内存泄漏
    private CompositeDisposable disposable;




    public PtPresenter(PtContract.View view) {
        campaignServiceManager = new CampaignServiceManager();
        this.view = view;
        disposable = new CompositeDisposable();
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
        PtManageActivity activity = (PtManageActivity) context;
        campaignServiceManager.doQueryCampaignList(userId,compId,pageNumber,pageSize,campaignType)
                .subscribe(new BaseObserver<List<Campaign>>(context,true){
                    @Override
                    public void onSuccess(List<Campaign> dataList, String msg) {



                        if (dataList!=null && dataList.size()>0) {
                            activity.ll_no_data.setVisibility(View.GONE);
                            activity.rvList.setVisibility(View.VISIBLE);

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
                                activity.tv_no_data.setText("您现在没有拼团活动记录哦！");
                            }

                            activity.ptr.onRefreshComplete();
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.listAdapter.notifyDataSetChanged();
                        activity.ptr.onRefreshComplete();
                        activity.ptr.setVisibility(View.GONE);
                        activity.ll_no_data.setVisibility(View.VISIBLE);
                        activity.tv_no_data.setText("您现在没有拼团活动记录哦");
                    }
                });
    }

    @Override
    public void queryBrandAndStoreList(Context context, String userId, boolean isDefaultAllStore,boolean isCampaignAndCouponStore) {
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
                    PtNextSettingFragment fragment = null;
                    PtNewActivity campaignNewActivity = null;
                    @Override
                    public void onSuccess(MoreStoreBean moreStoreBean,String msg) {


                        if (isCampaignAndCouponStore) {
                            campaignNewActivity = (PtNewActivity) view;
                            if(moreStoreBean != null ){
                                campaignNewActivity.branList = moreStoreBean.getBrandList();
                                if(campaignNewActivity.branList !=null && campaignNewActivity.branList.size()>0){
                                    if(campaignNewActivity.branList.get(0)!=null){
                                        campaignNewActivity.storeList = campaignNewActivity.branList.get(0).getStoreList();
                                    }
                                }else {
                                    campaignNewActivity.tvStoreNames.setText("# 全部店铺");
                                }


                            }
                        }else {
                            fragment = (PtNextSettingFragment) view;
                            if(moreStoreBean != null ){
                                fragment.branList = moreStoreBean.getBrandList();
                                if(fragment.branList !=null && fragment.branList.size()>0){
                                    if(fragment.branList.get(0)!=null){
                                        fragment.storeList = fragment.branList.get(0).getStoreList();
                                    }
                                }else {
                                    fragment.tvStoreNames.setText("#全部店铺");
                                }
                               /* if(isDefaultAllStore){

                                }else {
                                    campaignNewActivity.showPopView(campaignNewActivity);
                                }*/

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
        PtManageActivity activity = (PtManageActivity) context;
        campaignServiceManager.doCampaignDelete(campaignId)
                .subscribe(new BaseObserver<ResultBean>(context,false){
                    @Override
                    public void onSuccess(ResultBean resultBean, String msg) {

                        ToastUtils.showCustomShortBottom("已删除");
                        activity.campaignList.remove(position);
                        activity.listAdapter.notifyDataSetChanged();
                        if(activity.dialog!=null){
                            activity.dialog.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showCustomShortBottom("删除失败");
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
        PtManageActivity activity = (PtManageActivity) context;
        campaignServiceManager.doCampaignUpdate(campaignId,operateFlag)
                .subscribe(new BaseObserver<ResultBean>(context,false){
                    @Override
                    public void onSuccess(ResultBean resultBean, String msg) {
                        String userId = SPUtils.getInstance().getString("userId");
                        String compId = SPUtils.getInstance().getString("compId");
                        if("1".equals(operateFlag)){
                            ToastUtils.showCustomShortBottom("已作废！");


                        }else if("2".equals(operateFlag)){
                            ToastUtils.showCustomShortBottom("已结束！");
                        }
                        if(activity.dialog!=null){
                            activity.dialog.dismiss();
                        }

                        queryCampaignList(context, userId,  compId,  activity.pageNumber,  10, "pt", true);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showCustomShortBottom("操作失败！");
                        if(activity.dialog!=null){
                            activity.dialog.dismiss();
                        }
                    }
                });
    }






    @Override
    public void savePinTuanCampaignInfoForgame(Context context,String jsonStr,String btnFlag) {


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

        PtNextThreeActivity activity = (PtNextThreeActivity) context;
/*        JSONObject jsonObject = new JSONObject();
        String jsonStr = "";
        try{
            jsonObject.put("storeStr","");
            jsonObject.put("storeSelectType","");
            jsonObject.put("userId",userId);
            jsonObject.put("compMemTypeId",compMemTypeId);
            jsonObject.put("btnFlag",btnFlag);
//            JSONObject campaignJSON = new JSONObject(campaign);
            String campaignJSON=new Gson().toJson(campaign);
            jsonObject.put("campaign",campaignJSON);

            jsonObject.put("campaginGrant",new Gson().toJson(campaginGrant));
            jsonObject.put("compId",compId);
            String forwardCouponJSON = new Gson().toJson(forwardCoupon);
            jsonObject.put("forwardCoupon",forwardCouponJSON);
            jsonStr = jsonObject.toString();
        }catch (Exception e){

        }*/

        campaignServiceManager.savePinTuanCampaignInfoForgame(jsonStr)
                .subscribe(new BaseObserver<CampaignSaveResult>(context,false){
                    @Override
                    public void onSuccess(CampaignSaveResult result, String msg) {

                        if(result!=null){
                            if("save".equals(btnFlag)){
                                ToastUtils.showCustomShortBottom("保存成功");
                            }else {
                                ToastUtils.showCustomShortBottom("发布成功");
                            }

                            ActivityUtils.startActivity(PtManageActivity.class, R.anim.slide_in_right,R.anim.slide_out_left);
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
    public void doEdit(Context context, String campaignId) {

        /*Map<String, String> params = new HashMap<String, String>();
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
        GiveCouponNewActivity campaignNewActivity =(GiveCouponNewActivity) context;
        campaignServiceManager.doEdit(campaignId)
                .subscribe(new BaseObserver<CampaignEditBean>(context,true){
                    @Override
                    public void onSuccess(CampaignEditBean campaignEditBean, String msg) {

                        if(campaignEditBean!=null){
                            campaignNewActivity.campaign  =campaignEditBean.getCampaign();
                            if(campaignNewActivity.campaign!=null){
                                SPObjUtil.save("campaign",campaignNewActivity.campaign);
                            }
                            campaignNewActivity.campaignType = campaignEditBean.getCampaign().getCampaignType();
                            campaignNewActivity.campaginGrant = campaignEditBean.getCampaignGrant();
                            if(campaignNewActivity.campaginGrant != null){
                                campaignNewActivity.compMemTypeId = String.valueOf(campaignNewActivity.campaginGrant.getCompMemTypeId());
                                campaignNewActivity.grantType= campaignNewActivity.campaginGrant.getGrantType();

                                if("B".equals(campaignNewActivity.grantType)){  //生日礼
                                    campaignNewActivity.title = "生日礼";
                                    campaignNewActivity.sendDay = String.valueOf(campaignNewActivity.campaginGrant.getSendDay());
                                    campaignNewActivity.edtDayDate.setText(campaignNewActivity.sendDay);
                                }else if("K".equals(campaignNewActivity.grantType)){ //开卡礼
                                    campaignNewActivity.title = "开卡礼";
                                }

                                campaignNewActivity.setTitle();

                            }

                            campaignNewActivity.campaignName = campaignNewActivity.campaign.getCampaignName();
                            campaignNewActivity.startDate = campaignNewActivity.campaign.getBeginDate();
                            campaignNewActivity.endDate = campaignNewActivity.campaign.getEndDate();
                            if(campaignNewActivity.startDate!=null && !"".equals(campaignNewActivity.startDate)){
                                Date date = DateUtils.parse(campaignNewActivity.startDate,"yyyy-MM-dd");
                                String startTime = DateUtils.formatDate(date,"yyyy-MM-dd").replace("-",".");
                                campaignNewActivity.tvStartTime.setText(startTime);
                            }else {
                                campaignNewActivity.tvEndTime.setText("");
                            }

                            if(campaignNewActivity.endDate!=null && !"".equals(campaignNewActivity.endDate)){
                                Date date = DateUtils.parse(campaignNewActivity.endDate,"yyyy-MM-dd");
                                String endtTime = DateUtils.formatDate(date,"yyyy-MM-dd").replace("-",".");
                                campaignNewActivity.tvEndTime.setText(endtTime);
                            }else {
                                campaignNewActivity.tvEndTime.setText("");
                            }

                            campaignNewActivity.edtName.setText(campaignNewActivity.campaignName!=null?campaignNewActivity.campaignName:"");
                            GiveCouponNewNextActivity.forwardCouponList = campaignEditBean.getListfoward();

                        }


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });*/
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
        PtNewActivity campaignNewActivity =(PtNewActivity) context;
        campaignServiceManager.doEditForPinTuanGame(campaignId)
                .subscribe(new BaseObserver<PinTuanBean>(context,false){
                    @Override
                    public void onSuccess(PinTuanBean PtEditBean, String msg) {

                        if(PtEditBean!=null){
                            campaignNewActivity.campaign  =PtEditBean.getCampaign();

                            campaignNewActivity.campaignType = PtEditBean.getCampaign().getCampaignType();

                            campaignNewActivity.storeList = PtEditBean.getStore();
                            if(campaignNewActivity.storeList!=null && campaignNewActivity.storeList.size()>0){
                                StringBuilder storeIdSb = new StringBuilder();
                                StringBuilder storeNameSb = new StringBuilder();
                                campaignNewActivity.brandLogo = SPUtils.getInstance().getString("brandLogo");
                                RequestOptions requestOptions = new RequestOptions()
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

                                SPUtils.getInstance().put("storeIdTypePtOne",campaignNewActivity.storeIdCampaignType);
                                SPUtils.getInstance().put("storeIdArrayPtOne", campaignNewActivity.storeId);
                                SPUtils.getInstance().put("storeNameArrayPtOne",campaignNewActivity.storeName);
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

                            PtNextThreeActivity.campaignGroupBuy = PtEditBean.getCampaignGroupBuy();
                           if(PtNextThreeActivity.campaignGroupBuy == null){
                               PtNextThreeActivity.campaignGroupBuy = new CampaignGroupBuy();
                           }


                            PtNextTwoActivity.campaignGamesetrewardList = new ArrayList<>();
                            //获取中奖和未中奖
                            List<PinTuanBean.RewardBean>  rewardBeanList= PtEditBean.getGamesecondinfo();
                            if(rewardBeanList != null){
                                for (PinTuanBean.RewardBean rewardBean : rewardBeanList){
                                    CampaignGamesetreward campaignGamesetreward = rewardBean.getRewardinfo();

                                    ArrayList<CampaignCoupon> couponinfo = rewardBean.getCouponinfo();
                                    if(campaignGamesetreward != null){
                                        String rewardType = campaignGamesetreward.getRewardType();
                                        Integer rewardOrder = campaignGamesetreward.getPrizeOrder();
                                        campaignGamesetreward.setList(couponinfo);

                                        if("1".equals(rewardType)){
                                            campaignGamesetreward.setPrizeName("商品设置");
                                            PtNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
                                        }else if("0".equals(rewardType)){ //
                                            campaignGamesetreward.setPrizeName("成团奖励");
                                            PtNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
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
        PtNextThreeActivity activity = (PtNextThreeActivity)context;

        campaignServiceManager.uploadFiles(fileList)
                .subscribe(new BaseObserver<Map<String,Object>>(context,false){
                    @Override
                    public void onSuccess(Map<String,Object> map, String msg) {
                        String imageId = "";
                        if(map != null && map.size()>0){
                            imageId =(String)map.get("id");
                            activity.prizeImg = imageId;
                            activity.setImage();
                            activity.campaignGroupBuy.setPrizeImg(imageId);
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


                             /*if(activity.selectList != null && activity.selectList.size()>0){
                                LocalMedia localMedia  = activity.selectList.get(0);
                                String prizeImg = localMedia.getCompressPath();

                                if(prizeImg != null && !"".equals(prizeImg)){
                                    if(prizeImg.contains("get.do?")){
                                        String picPictureId = prizeImg.substring(prizeImg.indexOf("get.do?id=")+10,prizeImg.length());

                                    }

                                }

                            }*/



                        }



                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });
    }


    @Override
    public void queryTigerPoster(Context context, String userId, String compId, String id,String templateType) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("compId",compId);
        params.put("id",id);
        params.put("compId",compId);
        params.put("templateType",templateType);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        PtNextThreeActivity activity = (PtNextThreeActivity)context;
        campaignServiceManager.queryTigerPoster(userId,compId,id,templateType)
                .subscribe(new BaseObserver<List<MaterialGame>>(context,false){
                    @Override
                    public void onSuccess(List<MaterialGame>  materialGameList, String msg) {

                        if(materialGameList!=null && materialGameList.size()>0){
                            activity.materialGameList = materialGameList;
                            if(activity.materialGameList != null && activity.materialGameList.size()>0){
                                Object win = activity.materialGameList.get(0).getWin();
                                List<String> wins = new ArrayList<>();
                                if(win != null){
                                    wins = (List<String>)win;
                                }
                                List<PrizeDefaultBean> winList = new ArrayList<>();


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



                            activity.materialGameList = materialGameList;
                            activity.materialGame = activity.materialGameList.get(0);
                            activity.posterId = String.valueOf(activity.materialGame.getId());
                            if(activity.materialGame != null){
                                String forwardTitle =activity.materialGame.getShareTitle();
                                String forwardContent = activity.materialGame.getShareDesc();
                                /*if(activity.forwardTitle != null && !"".equals(activity.forwardTitle)){
                                    activity.forwardTitle = "[一起拼，开大奖]我想要，你需要，拼完还能拿大奖";
                                }
                                if(activity.forwardContent != null && !"".equals(activity.forwardContent)){
                                    activity.forwardContent = "GOGOGO~  亲，帮我一起拼，解锁属于我们的幸运和大奖吧。";
                                }*/
                                if("S".equals(PtNewActivity.compaignStatus)){  //编辑页面
                                }else if("NEW".equals(PtNewActivity.compaignStatus)){   //新建活动
                                    if(activity.forwardTitle != null && !"".equals(activity.forwardTitle)){
                                    }else {

                                        activity.forwardTitle = forwardTitle;
                                        activity.edt_forward_title.setText( activity.forwardTitle != null && !"".equals(activity.forwardTitle)?activity.forwardTitle:"" );


                                    }

                                    if(activity.forwardContent != null && !"".equals(activity.forwardContent)){
                                    }else {

                                        activity.forwardContent = forwardContent;
                                        activity.edt_forward_content.setText( activity.forwardContent != null && !"".equals(activity.forwardContent)?activity.forwardContent:"" );

                                    }
                                }
                                if(activity.edt_forward_content.getText().toString().trim() != null && !"".equals(activity.edt_forward_content.getText().toString().trim())){
                                    int strLen = activity.edt_forward_content.getText().toString().trim().length();
                                    activity.tv_content_num.setText(strLen+"/45");
                                }

                                if(activity.edt_forward_title.getText().toString().trim() != null && !"".equals(activity.edt_forward_title.getText().toString().trim())){
                                    int strLen = activity.edt_forward_title.getText().toString().trim().length();
                                    activity.tv_title_num.setText(strLen+"/30");
                                }

                                activity.miniLinkIcon = activity.materialGame.getMiniLinkIcon();
                                activity.backGroundId = activity.materialGame.getBackground();
                                activity.wxIconId = activity.materialGame.getWxLinkIcon();
                                activity.bannerId = activity.materialGame.getBanner();
                                activity.backGround = activity.materialGame.getBackground();


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
    public void doQueryGroupBuy(Context context, String campaignName, String salesTimeSta, String salesTimesEnd,Integer pageSize,Integer pageNumber, String storeId,boolean isRefresh) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("campaignName",campaignName);
        params.put("salesTimeSta",salesTimeSta);
        params.put("salesTimesEnd",salesTimesEnd);
        params.put("storeId",storeId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        PtOrderActivity activity = (PtOrderActivity) context;
        campaignServiceManager.doQueryGroupBuy(campaignName,salesTimeSta,salesTimesEnd,pageSize,pageNumber,storeId)
                .subscribe(new BaseObserver<List<GroupBuyCampagin>>(context,true){
                    @Override
                    public void onSuccess(List<GroupBuyCampagin>  dataList, String msg) {

                       /* if(groupBuyCampaginList!=null && groupBuyCampaginList.size()>0){

                            if(isRefresh){
                                activity.groupBuyCampaginList.clear();
                                activity.groupBuyCampaginList.addAll(groupBuyCampaginList);
                                if(groupBuyCampaginList.size()<10 ){
                                    activity.listAdapter = new PtOrderListAdapter(activity,activity.groupBuyCampaginList);
                                    activity.mAdapter.notifyDataSetChanged();
                                    activity.ptr.refreshComplete();
                                    activity.ptr.setLoadMoreEnable(false);
                                }else {
                                    activity.listAdapter = new PtOrderListAdapter(activity,activity.groupBuyCampaginList);
                                    activity.mAdapter.notifyDataSetChanged();
                                    activity.ptr.refreshComplete();
                                    activity.ptr.setLoadMoreEnable(true);
                                }
                            }else {
                                activity.groupBuyCampaginList.addAll(groupBuyCampaginList);
                                activity.listAdapter = new PtOrderListAdapter(activity,activity.groupBuyCampaginList);
                                activity.mAdapter.notifyDataSetChanged();
                                if(groupBuyCampaginList.size()<10){
                                    activity.ptr.refreshComplete();
                                    activity.ptr.setLoadMoreEnable(false);
                                }else {
                                    activity.ptr.refreshComplete();
                                    activity.ptr.loadMoreComplete(true);
                                }
                            }

                        }else {
                            activity.ptr.refreshComplete();
                            activity.ptr.setLoadMoreEnable(false);
                        }*/


                        if (dataList != null && dataList.size() > 0) {

                            for(int i=0;i<dataList.size();i++){
                                if(dataList.get(i) != null){
                                    if(dataList.get(i).getCampaignId() != null){
                                        String detailedId = String.valueOf(dataList.get(i).getCampaignId());
                                        if(activity.groupBuyCampaginList != null &&  activity.groupBuyCampaginList.size()>0){
                                            for(int j=0;j<activity.groupBuyCampaginList.size();j++){
                                                if(detailedId.equals(String.valueOf(activity.groupBuyCampaginList.get(j).getCampaignId()))){
                                                    activity.groupBuyCampaginList.remove(j);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                            activity.groupBuyCampaginList.addAll(dataList);
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
                            activity.groupBuyCampaginList.addAll(dataList);
                            activity.listAdapter.notifyDataSetChanged();

                            if(activity.groupBuyCampaginList != null  &&  activity.groupBuyCampaginList.size()>0){
                                if(activity.groupBuyCampaginList.size() < 10){
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                }else {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }

                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.listView.setVisibility(View.VISIBLE);
                            }else {

                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.listView.setVisibility(View.GONE);
                                activity.tv_no_data.setText("您现在没有拼团订单记录哦！");
                            }


                        }
                        activity.ptr.onRefreshComplete();

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.groupBuyCampaginList.clear();
                        activity.listAdapter.notifyDataSetChanged();
                        activity.ptr.onRefreshComplete();
                        activity.ll_no_data.setVisibility(View.VISIBLE);
                        activity.listView.setVisibility(View.GONE);
                        activity.tv_no_data.setText("您现在没有拼团订单记录哦！");
                    }
                });
    }

    @Override
    public void groupBuyCampaginRunning(Context context, String campaignId,Integer pageSize,Integer pageNumber, String storeId,String salesTimeSta,String salesTimesEnd,boolean isRefresh) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("campaignId",campaignId);
        params.put("storeId",storeId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        PtOrderDetailActivity activity = (PtOrderDetailActivity) context;
        campaignServiceManager.groupBuyCampaginRunning(campaignId,pageSize,pageNumber,storeId,salesTimeSta,salesTimesEnd)
                .subscribe(new BaseObserver<List<GroupBuyCampagin>>(context,false){
                    @Override
                    public void onSuccess(List<GroupBuyCampagin>  groupBuyCampaginList, String msg) {

                        if(groupBuyCampaginList!=null && groupBuyCampaginList.size()>0){
                            if(groupBuyCampaginList.get(0) != null){
                                String  startDate = groupBuyCampaginList.get(0).getBeginDate();
                                String endDate = groupBuyCampaginList.get(0).getEndDate();

                                /*if(startDate != null && !"".equals(startDate)){
                                    if(endDate != null && !"".equals(endDate)){
                                        activity.tv_time.setText(TimeUtils.date2String(TimeUtils.string2Date(startDate)).replace("-",".") + "-" +TimeUtils.date2String(TimeUtils.string2Date(endDate)).replace("-","."));
                                    }else {
                                        activity.tv_time.setText(TimeUtils.date2String(TimeUtils.string2Date(startDate)).replace("-","."));
                                    }
                                }else {
                                    if(endDate != null && !"".equals(endDate)){
                                        activity.tv_time.setText(TimeUtils.date2String(TimeUtils.string2Date(endDate)).replace("-","."));
                                    }else {
                                        activity.tv_time.setText("");
                                    }
                                }*/
                            }else {
                                activity.tv_time.setText("");
                            }

                            if(isRefresh){
                                activity.groupBuyCampaginList.clear();
                                activity.groupBuyCampaginList.addAll(groupBuyCampaginList);
                                if(groupBuyCampaginList.size()<10 ){
//                                    activity.listAdapter = new PtOrderListDetailAdapter(activity,activity.groupBuyCampaginList);
                                    activity.mAdapter.notifyDataSetChanged();
                                    activity.ptr.refreshComplete();
                                    activity.ptr.setLoadMoreEnable(false);
                                }else {
//                                    activity.listAdapter = new PtOrderListDetailAdapter(activity,activity.groupBuyCampaginList);
                                    activity.mAdapter.notifyDataSetChanged();
                                    activity.ptr.refreshComplete();
                                    activity.ptr.setLoadMoreEnable(true);
                                }
                            }else {
                                activity.groupBuyCampaginList.addAll(groupBuyCampaginList);
//                                activity.listAdapter = new PtOrderListDetailAdapter(activity,activity.groupBuyCampaginList);
                                activity.mAdapter.notifyDataSetChanged();
                                if(groupBuyCampaginList.size()<10){
                                    activity.ptr.refreshComplete();
                                    activity.ptr.setLoadMoreEnable(false);
                                }else {
                                    activity.ptr.refreshComplete();
                                    activity.ptr.loadMoreComplete(true);
                                }
                            }



                        }else {
                            activity.ptr.refreshComplete();
                            activity.ptr.setLoadMoreEnable(false);
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.mAdapter.notifyDataSetChanged();
                        activity.ptr.refreshComplete();
                        activity.ptr.setLoadMoreEnable(false);
                    }
                });
    }
}
