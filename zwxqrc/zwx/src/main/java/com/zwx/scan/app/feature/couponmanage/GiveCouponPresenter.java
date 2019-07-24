package com.zwx.scan.app.feature.couponmanage;

import android.content.Context;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.DateUtils;
import com.zwx.library.utils.EncryptUtils;
import com.zwx.library.utils.LogUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.base.BaseObserver;
import com.zwx.scan.app.data.bean.CampaginGrant;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CampaignEditBean;
import com.zwx.scan.app.data.bean.CampaignGame;
import com.zwx.scan.app.data.bean.CampaignGamesetreward;
import com.zwx.scan.app.data.bean.CampaignNonrewardPic;
import com.zwx.scan.app.data.bean.CampaignSaveResult;
import com.zwx.scan.app.data.bean.CompMemberType;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.GiveCouponResultBean;
import com.zwx.scan.app.data.bean.LhPtEditBean;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.ResultBean;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.UnavailableDate;
import com.zwx.scan.app.data.http.service.CampaignServiceManager;
import com.zwx.scan.app.data.http.service.RetrofitServiceManager;
import com.zwx.scan.app.feature.campaign.CampaignListActivity;
import com.zwx.scan.app.feature.campaign.CampaignListAdatper;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanNextThreeActivity;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanNextTwoActivity;
import com.zwx.scan.app.feature.ptmanage.PtManageActivity;
import com.zwx.scan.app.utils.SPObjUtil;

import org.json.JSONObject;

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
public class GiveCouponPresenter implements GiveCouponContract.Presenter {


    private  GiveCouponContract.View view;
    private CampaignServiceManager campaignServiceManager;

    //添加订阅，应用于取消订阅，防止造成内存泄漏
    private CompositeDisposable disposable;




    public GiveCouponPresenter(GiveCouponContract.View view) {
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
        GiveCouponManageActivity activity = (GiveCouponManageActivity) context;
        campaignServiceManager.doQueryCampaignList(userId,compId,pageNumber,pageSize,campaignType)
                .subscribe(new BaseObserver<List<Campaign>>(context,false){
                    @Override
                    public void onSuccess(List<Campaign> dataList, String msg) {

                        if (dataList!=null && dataList.size()>0) {
                            activity.rvList.setVisibility(View.VISIBLE);
                            activity.ll_no_data.setVisibility(View.GONE);

                            for(int i=0;i<dataList.size();i++){
                                if(dataList.get(i) != null){
                                    if(dataList.get(i).getCampaignId() != null){
                                        String campaignId = String.valueOf(dataList.get(i).getCampaignId());
                                        if(activity.campaignList != null &&  activity.campaignList.size()>0){
                                            for(int j=0;j<activity.campaignList.size();j++){
                                                if(campaignId.equals(String.valueOf(activity.campaignList.get(j).getCampaignId()))){
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

                        }else {

                            if(activity.campaignList != null && activity.campaignList.size()>0){
                                activity.listAdapter.notifyDataSetChanged();
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
                                activity.tv_no_data.setText("您现在没有常规发券记录哦！");
                            }

                        }

                        activity.ptr.onRefreshComplete();

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                        activity.listAdapter.notifyDataSetChanged();
                        activity.ptr.onRefreshComplete();
                        activity.ll_no_data.setVisibility(View.VISIBLE);
                        activity.rvList.setVisibility(View.GONE);
                        activity.tv_no_data.setText("您现在没有常规发券记录哦！");


                    }
                });
    }

    @Override
    public void queryBrandAndStoreList(Context context, String userId, boolean isDefaultAllStore) {

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
                    GiveCouponNextFragment fragment = null;

                    @Override
                    public void onSuccess(MoreStoreBean moreStoreBean,String msg) {
                        fragment = (GiveCouponNextFragment) view;
                        if(moreStoreBean != null ){
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
                                    fragment.tvStoreNames.setText("#全部店铺");
                                }

                            }else {
                                fragment.showPopView(fragment.getActivity());
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
    public void queryStoreList(Context context, String userId, boolean isDefaultAllStore) {
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
                    GiveCouponConsumeSettingFragment fragment = null;

                    @Override
                    public void onSuccess(MoreStoreBean moreStoreBean,String msg) {
                        fragment = (GiveCouponConsumeSettingFragment) view;
                        if(moreStoreBean != null ){
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
                                    fragment.tvStoreNames.setText("#全部店铺");
                                }

                            }else {
                                fragment.showPopView(fragment.getActivity());
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
        GiveCouponManageActivity activity = (GiveCouponManageActivity) context;
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
        GiveCouponManageActivity activity = (GiveCouponManageActivity) context;
        campaignServiceManager.doCampaignUpdate(campaignId,operateFlag)
                .subscribe(new BaseObserver<ResultBean>(context,false){
                    @Override
                    public void onSuccess(ResultBean resultBean, String msg) {
                        String userId = SPUtils.getInstance().getString("userId");
                        String compId = SPUtils.getInstance().getString("compId");
                        if("1".equals(operateFlag)){
                            ToastUtils.showShort("已作废！");


                        }else if("2".equals(operateFlag)){
                            ToastUtils.showShort("已结束！");
                        }
                        if(activity.dialog!=null){
                            activity.dialog.dismiss();
                        }
                        queryCampaignList(context, userId,  compId,  activity.pageNumber,  10, "zj", true);
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






    @Override
    public void saveCampaignInfo(Context context, Campaign campaign, String compMemTypeId,CampaginGrant campaginGrant,
                                 List<CampaignCoupon> forwardCoupon,
                                 String userId, String compId, String btnFlag) {


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

       Map<String,Object> jsonMap = new HashMap<>();
        String jsonStr = "";
        try{
            jsonMap.put("userId",userId);
            jsonMap.put("storeStr","");
            jsonMap.put("storeSelectType","");
            jsonMap.put("compMemTypeId",compMemTypeId);
            jsonMap.put("posterId","");
            jsonMap.put("btnFlag",btnFlag);
            String campaignJSON=new Gson().toJson(campaign);
            jsonMap.put("campaign",campaignJSON);
            String campaginGrantJSON=new Gson().toJson(campaginGrant);
            jsonMap.put("campaginGrant",campaginGrantJSON);
            jsonMap.put("compId",compId);

            jsonMap.put("forwardCoupon",forwardCoupon);
            jsonStr = JSON.toJSONString(jsonMap);
        }catch (Exception e){
            e.printStackTrace();
        }

        campaignServiceManager.saveCampaignInfo(jsonStr)
                .subscribe(new BaseObserver<CampaignSaveResult>(context,true){
                    @Override
                    public void onSuccess(CampaignSaveResult result, String msg) {

                        if(result!=null){
                            if("save".equals(btnFlag)){
                                ToastUtils.showCustomShortBottom("保存成功！");
                            }else {
                                ToastUtils.showCustomShortBottom("发布成功！");
                            }

                            ActivityUtils.startActivity(GiveCouponManageActivity.class, R.anim.slide_in_right,R.anim.slide_out_left);


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
        GiveCouponNewActivity campaignNewActivity =(GiveCouponNewActivity) context;
        campaignServiceManager.doGiveCouponEdit(campaignId)
                .subscribe(new BaseObserver<GiveCouponResultBean>(context,true){
                    @Override
                    public void onSuccess(GiveCouponResultBean campaignEditBean, String msg) {

                        if(campaignEditBean!=null){
                            campaignNewActivity.campaign  =campaignEditBean.getCampaign();

                            campaignNewActivity.campaignType = campaignEditBean.getCampaign().getCampaignType();
                            campaignNewActivity.campaginGrant = campaignEditBean.getCampaignGrant();
                            if(campaignNewActivity.campaginGrant != null){
                                campaignNewActivity.compMemTypeId = String.valueOf(campaignNewActivity.campaginGrant.getCompMemTypeId());
                                campaignNewActivity.grantType= campaignNewActivity.campaginGrant.getGrantType();
                                campaignNewActivity.memberDay = campaignNewActivity.campaginGrant.getMemberDay();
                                campaignNewActivity.setDefaultFlowLayout();
                                if("B".equals(campaignNewActivity.grantType)){  //生日礼
                                    campaignNewActivity.title = "生日礼";
                                    campaignNewActivity.sendDay = String.valueOf(campaignNewActivity.campaginGrant.getSendDay());
                                    campaignNewActivity.tvValidDays.setText(campaignNewActivity.sendDay);
                                    campaignNewActivity.edtDayDate.setText(campaignNewActivity.sendDay);
                                }else if("K".equals(campaignNewActivity.grantType)){ //开卡礼
                                    campaignNewActivity.title = "开卡礼";
                                }else if("M".equals(campaignNewActivity.grantType)){
                                    campaignNewActivity.memberDayType = campaignNewActivity.campaginGrant.getMemberDayType();
                                    if(campaignNewActivity.campaginGrant.getSendDay() != null && campaignNewActivity.campaginGrant.getSendDay().intValue()>0){
                                        campaignNewActivity.sendDay = String.valueOf(campaignNewActivity.campaginGrant.getSendDay());
                                        campaignNewActivity.tvValidDays.setText(campaignNewActivity.sendDay);
                                    }else {
                                        campaignNewActivity.sendDay = "0";
                                        campaignNewActivity.tvValidDays.setText("当");
                                    }

                                    campaignNewActivity.edtDayDate.setText(campaignNewActivity.sendDay);
                                    StringBuilder dayNameSb = new StringBuilder();
                                    if("W".equals(campaignNewActivity.memberDayType)){

                                        if(!"".equals(campaignNewActivity.memberDay)){
                                            String[] memberDays = campaignNewActivity.memberDay.split(",");
                                            for (int i = 0;i<memberDays.length;i++){
                                                DayDateBean dayDateBean = new DayDateBean();
                                                dayDateBean.setChecked(true);
                                                dayDateBean.setId(memberDays[i]);
                                                String value = "";
                                                if("1".equals(memberDays[i])){
                                                    value = "周一";
                                                    dayNameSb.append("周一").append("、");
                                                }else if("2".equals(memberDays[i])){
                                                    value = "周二";
                                                    dayNameSb.append("周二").append("、");
                                                }else if("3".equals(memberDays[i])){
                                                    value = "周三";
                                                    dayNameSb.append("周三").append("、");
                                                }else if("4".equals(memberDays[i])){
                                                    value = "周四";
                                                    dayNameSb.append("周四").append("、");
                                                }else if("5".equals(memberDays[i])){
                                                    value = "周五";
                                                    dayNameSb.append("周五").append("、");
                                                }else if("6".equals(memberDays[i])){
                                                    value = "周六";
                                                    dayNameSb.append("周六").append("、");
                                                }else if("7".equals(memberDays[i])){
                                                    value = "周日";
                                                    dayNameSb.append("周日").append("、");
                                                }
                                                dayDateBean.setValue(value);
                                                campaignNewActivity.selectDayDateBeanList.add(dayDateBean);
                                            }

                                            String dayNameA= dayNameSb.toString();
                                            if(dayNameA!=null && !"".equals(dayNameA)){
                                                campaignNewActivity.memberDayStr= dayNameA.substring(0,dayNameA.length() - 1);
                                            }

                                        }
                                        campaignNewActivity.tv_week_date.setText(campaignNewActivity.memberDayStr);
                                        campaignNewActivity.setWeekStyle();
                                    }else {
                                        if(!"".equals(campaignNewActivity.memberDay)){
                                            String[] memberDays = campaignNewActivity.memberDay.split(",");
                                            for (int i = 0;i<memberDays.length;i++){
                                                DayDateBean dayDateBean = new DayDateBean();
                                                dayDateBean.setChecked(true);
                                                dayDateBean.setId(memberDays[i]);
                                                String value = "";

                                                if("1".equals(memberDays[i])){
                                                    value ="01";
                                                    dayNameSb.append("01号").append("、");
                                                }else if("2".equals(memberDays[i])){
                                                    value ="02";
                                                    dayNameSb.append("02号").append("、");
                                                }else if("3".equals(memberDays[i])){
                                                    value ="03";
                                                    dayNameSb.append("03号").append("、");
                                                }else if("4".equals(memberDays[i])){
                                                    value ="04";
                                                    dayNameSb.append("04号").append("、");
                                                }else if("5".equals(memberDays[i])){
                                                    value ="05";
                                                    dayNameSb.append("05号").append("、");
                                                }else if("6".equals(memberDays[i])){
                                                    value ="06";
                                                    dayNameSb.append("06号").append("、");
                                                }else if("7".equals(memberDays[i])){
                                                    value ="07";
                                                    dayNameSb.append("07号").append("、");
                                                }else if("8".equals(memberDays[i])){
                                                    value ="08";
                                                    dayNameSb.append("08号").append("、");
                                                }else if("9".equals(memberDays[i])){
                                                    value ="09";
                                                    dayNameSb.append("09号").append("、");
                                                }else {
                                                    value =memberDays[i];
                                                    dayNameSb.append(memberDays[i]).append("号、");
                                                }
                                                dayDateBean.setValue(value);
                                                campaignNewActivity.selectDayDateBeanList.add(dayDateBean);
                                            }

                                            String dayNameA= dayNameSb.toString();
                                            if(dayNameA!=null && !"".equals(dayNameA)){
                                                campaignNewActivity.memberDayStr= dayNameA.substring(0,dayNameA.length() - 1);
                                            }

                                        }
                                        campaignNewActivity.tv_month_date.setText(campaignNewActivity.memberDayStr);
                                        campaignNewActivity.setMonthStyle();
                                    }

                                }else if("S".equals(campaignNewActivity.grantType)||"V".equals(campaignNewActivity.grantType)) {
                                    List<GiveCouponResultBean.RewardBean> rewardBeanList = campaignEditBean.getGamesecondinfo();
                                    GiveCouponNewNextConsumeActivity.campaignGamesetrewardList = new ArrayList<>();

                                    if(rewardBeanList != null){
                                        for (GiveCouponResultBean.RewardBean rewardBean : rewardBeanList){
                                            CampaignGamesetreward campaignGamesetreward = rewardBean.getRewardinfo();

                                            ArrayList<CampaignCoupon> couponinfo = rewardBean.getCouponinfo();
                                            if(campaignGamesetreward != null){
                                                String rewardType = campaignGamesetreward.getRewardType();
                                                Integer rewardOrder = campaignGamesetreward.getPrizeOrder();
                                                campaignGamesetreward.setList(couponinfo);

                                                if(rewardOrder == 1){
                                                    campaignGamesetreward.setPrizeName("条件一");
                                                }else if(rewardOrder == 2){
                                                    campaignGamesetreward.setPrizeName("条件二");
                                                }else if(rewardOrder == 3){
                                                    campaignGamesetreward.setPrizeName("条件三");
                                                }else if(rewardOrder == 4){
                                                    campaignGamesetreward.setPrizeName("条件四");
                                                }
                                                GiveCouponNewNextConsumeActivity.campaignGamesetrewardList.add(campaignGamesetreward);
                                            }
                                        }
                                    }
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
                        ToastUtils.showShort(message);
                    }
                });
    }

    @Override
    public void queryCommemberTypeByUserId(Context context, String userId) {

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
        GiveCouponNewActivity activity = (GiveCouponNewActivity) context;
        campaignServiceManager.queryCommemberTypeByUserId(userId)
                .subscribe(new BaseObserver<List<CompMemberType>>(context,false){
                    @Override
                    public void onSuccess(List<CompMemberType> compMemberTypeList, String msg) {

                        if(compMemberTypeList != null ){
                            activity.compMemTypeList = compMemberTypeList;
                            activity.initFlowLayout();

                            activity.setDefaultFlowLayout();
                        }

                        if(!"NEW".equals(activity.compaignStatus)){

                            doEdit(context,activity.campaignId);
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);
                        doEdit(context,activity.campaignId);
                    }
                });
    }


    @Override
    public void doQueryCouponByCompId(Context context, String storeId, String compId, Integer pageNumber, Integer pageSize,Boolean isRefresh) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("storeId",storeId);
        params.put("compId",compId);
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
        CouponManageActivity activity = (CouponManageActivity) context;
        campaignServiceManager.doQueryCouponByCompId(storeId,compId,pageNumber,pageSize)
                .subscribe(new BaseObserver<List<CouponBean>>(context,false){
                    @Override
                    public void onSuccess(List<CouponBean> dataList, String msg) {
                        if (dataList!=null && dataList.size()>0) {
                            activity.ll_no_data.setVisibility(View.GONE);
                            activity.rvList.setVisibility(View.VISIBLE);

                           /* for(int i=0;i<dataList.size();i++){
                                if(dataList.get(i) != null){
                                    if(dataList.get(i).getId() != null){
                                        String goodId = String.valueOf(dataList.get(i).getId());
                                        if(activity.couponList != null &&  activity.couponList.size()>0){
                                            for(int j=0;j<activity.couponList.size();j++){
                                                if(goodId.equals(String.valueOf(activity.couponList.get(j).getId()))){
                                                    activity.couponList.remove(j);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }

                            }*/
                            activity.couponList.addAll(dataList);
                            activity.listAdapter.notifyDataSetChanged();
                            if (dataList.size() < 10) {
                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            } else {
                                activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                            }

                        }else {
                            activity.listAdapter.notifyDataSetChanged();
                            if(activity.couponList != null && activity.couponList.size()>0){
                                if(activity.couponList.size() < 10){
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
                                activity.tv_no_data.setText("您现在没有优惠券记录哦！");
                            }


                        }
                        activity.ptr.onRefreshComplete();


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
//                        ToastUtils.showShort(message);
                        activity.listAdapter.notifyDataSetChanged();

                        if(activity.couponList != null && activity.couponList.size()>0){
                            if(activity.couponList.size() < 10){
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
    public void doStatus(Context context, String storeId, String compId, String id, String status) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("storeId",storeId);
        params.put("compId",compId);
        params.put("id", id);
        params.put("status",status);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        CouponManageActivity activity = (CouponManageActivity) context;
        campaignServiceManager.doStatus(storeId,compId,id,status)
                .subscribe(new BaseObserver<String>(context,false){
                    @Override
                    public void onSuccess(String result, String msg) {

                        if("1".equals(status)){
                            ToastUtils.showCustomShortBottom("已启用");
                            doQueryCouponByCompId(context,storeId,compId,1,10,true);
                        }else if("2".equals(status)){
                            doQueryCouponByCompId(context,storeId,compId,1,10,true);
                            ToastUtils.showCustomShortBottom("已停用");
                        }else if("3".equals(status)){

                            ToastUtils.showCustomShortBottom("已删除");
                            activity.couponList.remove(activity.deletePostion);
                            activity.listAdapter.notifyDataSetChanged();
                            if(activity.dialog!=null){
                                activity.dialog.dismiss();
                            }
                        }


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.setResultMessageDialog();
                    }
                });
    }

    @Override
    public void checkStatus(Context context, String storeId, String compId, String id, String status) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("storeId",storeId);
        params.put("compId",compId);
        params.put("id", id);
        params.put("status",status);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        CouponManageActivity activity = (CouponManageActivity) context;
        campaignServiceManager.checkStatus(storeId,compId,id,status)
                .subscribe(new BaseObserver<String>(context,false){
                    @Override
                    public void onSuccess(String result, String msg) {
                        if("2".equals(status)){  //是否删除成功
                            activity.setcouponStatusDialog();
                        }else if("3".equals(status)){ //是否可以停用用
                            activity.setcouponStatusDialog();
                        }else if("1".equals(status)){ //是否可以编辑
                            activity.setIntentActivity(id);
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
//                        ToastUtils.showShort(message);
                            activity.setNoEditIntentActivity(id);
                    }
                });

    }


    @Override
    public void doCouponInsert(Context context, String userId, String storeId, String compId, String couponJson, String startDate, String endDate,String status) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("storeId",storeId);
        params.put("compId",compId);
        params.put("couponJson", couponJson);
        params.put("startDate",startDate);

        params.put("endDate",endDate);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        CouponNewActivity activity = (CouponNewActivity) context;
        campaignServiceManager.doCouponInsert(userId,storeId,compId, couponJson,startDate, endDate)
                .subscribe(new BaseObserver<String>(context,false){
                    @Override
                    public void onSuccess(String result, String msg) {

                        if("0".equals(status)){
                            ToastUtils.showCustomShortBottom("已保存");

                            ActivityUtils.startActivity(CouponManageActivity.class, R.anim.slide_in_right,R.anim.slide_out_left);
                            activity.finish();
                        }else {
                            ToastUtils.showCustomShortBottom("已启用");
                            ActivityUtils.startActivity(CouponManageActivity.class, R.anim.slide_in_right,R.anim.slide_out_left);
                            activity.finish();
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
    public void doCouponUpdate(Context context, String userId, String storeId, String compId, String couponJson, String startDate, String endDate,String status) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("storeId",storeId);
        params.put("compId",compId);
        params.put("couponJson", couponJson);
        params.put("startDate",startDate);

        params.put("endDate",endDate);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        CouponNewActivity activity = (CouponNewActivity) context;
        campaignServiceManager.doCouponUpdate(userId,storeId,compId, couponJson,  startDate, endDate)
                .subscribe(new BaseObserver<String>(context,false){
                    @Override
                    public void onSuccess(String result, String msg) {

                        if("0".equals(status)){
                            ToastUtils.showCustomShortBottom("已保存");

                            ActivityUtils.startActivity(CouponManageActivity.class, R.anim.slide_in_right,R.anim.slide_out_left);
                            activity.finish();
                        }else {
                            ToastUtils.showCustomShortBottom("已启用");
                            ActivityUtils.startActivity(CouponManageActivity.class, R.anim.slide_in_right,R.anim.slide_out_left);
                            activity.finish();
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
    public void doCouponLoad(Context context, String storeId, String compId, String id) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("storeId",storeId);
        params.put("compId",compId);
        params.put("id", id);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        CouponNewActivity activity = (CouponNewActivity) context;
        campaignServiceManager.doCouponLoad(storeId,compId,id)
                .subscribe(new BaseObserver<CouponDetailBean>(context,false){
                    @Override
                    public void onSuccess(CouponDetailBean coupon, String msg) {
                        if(coupon != null){
                            if(coupon.getCoupon() != null){

                                activity.coupon = coupon.getCoupon();

                                if(activity.coupon != null){
                                    activity.couponName = activity.coupon.getName();
                                    if(activity.couponName != null && !"".equals(activity.couponName)){
                                        activity.edt_coupon_name.setText(activity.couponName);
                                    }
                                    activity.couponType = activity.coupon.getType();
                                    if(activity.coupon.getLimit()!=null && !"".equals(activity.coupon.getLimit())){
//                                        activity.menKanPrize = RegexUtils.getDoubleString(Double.parseDouble(String.valueOf(activity.coupon.getLimit())));
                                        activity.menKanPrize = String.valueOf(activity.coupon.getLimit());
                                    }
                                    //门槛
                                    activity.edt_user_men_kan.setText(activity.menKanPrize);

                                    if(activity.coupon.getObject() != null && !"".equals(activity.coupon.getObject())){
                                        activity.zengPinName = activity.coupon.getObject();
                                    }else {
                                        activity.zengPinName = "";
                                    }

                                    if(activity.coupon.getLimit()!=null && !"".equals(activity.coupon.getLimit())){
//                                        activity.menKanPrize = RegexUtils.getDoubleString(Double.parseDouble(String.valueOf(activity.coupon.getLimit())));
                                        activity.menKanPrize = String.valueOf(activity.coupon.getLimit());
                                    }else {
                                        activity.menKanPrize = "0";
                                    }
                                    //门槛
                                    activity.edt_user_men_kan.setText(activity.menKanPrize);

                                  /*  if(activity.coupon.getMoney()!=null && !"".equals(activity.coupon.getMoney())){

                                        activity.goodPrize = RegexUtils.getDoubleString(Double.parseDouble(String.valueOf(activity.coupon.getMoney())));
                                    }*/
                                    if(activity.coupon.getMoney()!=null &&  !"".equals(activity.coupon.getMoney())){
//                                        activity.goodPrize = RegexUtils.getDoubleString(Double.parseDouble(String.valueOf(activity.coupon.getMoney())));
                                        activity.goodPrize = String.valueOf(activity.coupon.getMoney());
                                    }else {
                                        activity. goodPrize = "";
                                    }

                                    if ("CPC".equals(activity.couponType)) {   //代金券

                                        activity.edt_edu.setText(activity.goodPrize);
                                    } else if ("CPD".equals(activity.couponType)) {   //折扣

                                        if(activity.coupon.getDiscount()!=null && !"".equals(activity.coupon.getDiscount())){
//                                            activity.goodPrize = RegexUtils.getDoubleString(Double.parseDouble(String.valueOf(activity.coupon.getDiscount())));
                                            activity.goodPrize = String.valueOf(activity.coupon.getDiscount());
                                        }else {
                                            activity.goodPrize = "";
                                        }
                                        activity.edt_edu.setText(activity.goodPrize);
                                    } else if ("CPO".equals(activity.couponType)) {  //赠品券

                                        activity.edt_edu.setText(activity.goodPrize);

                                        activity.edt_zengpin.setText(activity.zengPinName);
                                    } else if ("CPU".equals(activity.couponType)) {  //菜品券

                                        activity.edt_edu.setText(activity.goodPrize);
                                        activity.edt_zengpin.setText(activity.zengPinName);
                                    } else if ("CPI".equals(activity.couponType)) {  //其他优惠券

                                        if(activity.coupon.getOther() != null && !"".equals(activity.coupon.getOther())){
                                            activity.zengPinName = activity.coupon.getOther();
                                        }
                                        activity.edt_zengpin.setText(activity.zengPinName);
                                    }


                                    activity.dateCode = activity.coupon.getDateCode();
                                    activity.noDate = activity.coupon.getNoDate();
                                    if(activity.dateCode!=null && !"".equals(activity.dateCode)) {  //不可用时间 是
                                        /*   * 11000 周六、日不可用
                                         * 10100 周六、日及法定节假日不可用
                                         * 10010 法定节假日不可用
                                         * 00000 没有不可用日期
                                         * 11001 周六、日不可用…
                                         * 10101 周六、日及法定节假日不可用…
                                         * 10011 法定节假日不可用…*/
                                        if (!"00000".equals(activity.dateCode)) { //是  末尾 0表示时间段无值 1表示有值
                                            activity.isBuKeYongTime = "1";
                                            if("11000".equals(activity.dateCode)){
                                                activity.buKeYongTime = "1";
                                            }else if("10100".equals(activity.dateCode)){
                                                activity.buKeYongTime = "2";
                                            }else if("10010".equals(activity.dateCode)){
                                                activity.buKeYongTime = "3";
                                            }else if("11001".equals(activity.dateCode)){
                                                activity.buKeYongTime = "1";

                                            }else if("10101".equals(activity.dateCode)){
                                                activity.buKeYongTime = "2";
                                            }else if("10011".equals(activity.dateCode)){
                                                activity.buKeYongTime = "3";
                                            }

                                            activity. unavailableDateList = activity.coupon.getUnavailableDate();

                                            if(activity.unavailableDateList != null && activity.unavailableDateList.size()>0){
                                                activity.ll_coupon_add_date.removeAllViews();
                                                for (UnavailableDate unavailableDate : activity.unavailableDateList) {
                                                    if (unavailableDate != null) {
//                                                        activity.unavailableDate = unavailableDate;
                                                        String startDate = unavailableDate.getStartDate();
                                                        String endDate = unavailableDate.getEndDate();
                                                        String startDate2 = "";
                                                        String endDate2 = "";
                                                        if(startDate != null && !"".equals(startDate) ){
                                                            startDate2 = startDate.replace("-",".").substring(0,10);
                                                        }
                                                        if(endDate != null && !"".equals(endDate)){
                                                            endDate2 = endDate.replace("-",".").substring(0,10);
                                                        }
                                                        activity.addDateItem(startDate2,endDate2);
                                                    }
                                                }

                                            }else {
                                                activity.unavailableDateList = new ArrayList<>();
                                            }
                                            if(activity.noDate !=null && !"".equals(activity.noDate)){

                                                activity.tv_bu_ke_yong_time.setText(activity.noDate);
                                            }else {
                                                activity.tv_bu_ke_yong_time.setText("");
                                            }

                                        }else {
                                            activity.isBuKeYongTime = "0";  //不可用时间 否
                                            activity.buKeYongTime = "";
                                        }
                                    }else {
                                        activity.isBuKeYongTime = "0";  //不可用时间 否
                                        activity.buKeYongTime = "";
                                    }


                                    activity.share = activity.coupon.getShare();
                                    if(activity.share != null && activity.share.booleanValue()){
                                        activity.isCommonCoupon = "1";
                                    }else {
                                        activity.isCommonCoupon = "0";
                                    }

                                    activity.assign = activity.coupon.getAssign();

                                    if(activity.assign != null && activity.assign.booleanValue()){
                                        activity.isZhuanZeng = "1";
                                    }else {
                                        activity.isZhuanZeng = "0";
                                    }

                                    String timePeriod = activity.coupon.getTimePeriod();
                                    String sd = "";
                                    if(timePeriod !=null  && !"".equals(timePeriod)){
                                        activity.shiduan = String.valueOf(timePeriod);

                                        if( "1".equals(timePeriod)){
                                            activity.timePeriodStr = "全天";
                                        }else if( "2".equals(timePeriod)){
                                            activity.timePeriodStr = "午市" ;
                                        }else if( "3".equals(timePeriod)){
                                            activity.timePeriodStr = "晚市";
                                        }
                                        activity.tv_user_shiduan.setText(activity.timePeriodStr);
                                    }else {
                                        activity.tv_user_shiduan.setText("");
                                    }



                                }

                                if("1".equals(activity.shiduan)){
                                    activity.tv_user_shiduan.setText("全天");
                                }else if("2".equals(activity.shiduan)){
                                    activity.tv_user_shiduan.setText("午市");
                                }else if("3".equals(activity.shiduan)){
                                    activity. tv_user_shiduan.setText("晚市");
                                }


                                if("1".equals(activity.isBuKeYongTime)){
                                    activity.ll_select_date.setVisibility(View.VISIBLE);
                                    activity.rl_bu_ke_yong.setVisibility(View.VISIBLE);
                                    activity.tv_is_bu_ke_yong_time.setText("是");
                                    if("1".equals(activity.buKeYongTime)){
                                        activity.noDate = "周六、日不可用";
                                    }else if("2".equals(activity.buKeYongTime)){
                                        activity.noDate = "周六、日及法定节假日不可用";
                                    }else if("3".equals(activity.buKeYongTime)){
                                        activity.noDate = "法定节假日不可用";
                                    }
                                }else if("0".equals(activity.isBuKeYongTime)){
                                    activity.ll_select_date.setVisibility(View.GONE);
                                    activity.rl_bu_ke_yong.setVisibility(View.GONE);
                                    activity.tv_is_bu_ke_yong_time.setText("否");
                                    activity.buKeYongTime = "";

                                }


                                if("1".equals(activity.isZhuanZeng)){
                                    activity.tv_coupon_zhuan_zeng.setText("是");
                                }else {
                                    activity.tv_coupon_zhuan_zeng.setText("否");
                                }

                                if("1".equals(activity.isCommonCoupon)){
                                    activity.tv_qi_ta_coupon_gongyong.setText("是");
                                }else {
                                    activity.tv_qi_ta_coupon_gongyong.setText("否");
                                }
                                activity.buKeYongDesc =  activity.coupon.getNoItem();

                                if(activity.buKeYongDesc != null && !"".equals(activity.buKeYongDesc)){
                                    activity.edt_bu_ke_yong_desc.setText(activity.buKeYongDesc);
                                }else {
                                    activity.edt_bu_ke_yong_desc.setText("");
                                }
                                activity.remark =  activity.coupon.getNotes();
                                if(activity.remark != null && !"".equals(activity.remark)){
                                    activity.edt_remark.setText(activity.remark);
                                }else {
                                    activity.edt_remark.setText("");
                                }
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


            jsonMap.put("campaignNonrewardPic",campaignNonrewardPicList);
            jsonMap.put("rewardlist",rewardlist);
            jsonMap.put("storeSelectType",storeSelectType);
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
    public void saveCampaignInfoS(Context context, Campaign campaign, String compMemTypeId, CampaginGrant campaginGrant, List<CampaignCoupon> forwardCoupon, List<CampaignGamesetreward> rewardlist, String userId, String compId, String btnFlag) {

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
        Map<String,Object> jsonMap = new HashMap<String,Object>();
        String jsonStr = "";
        try{
            jsonMap.put("userId",userId);
            jsonMap.put("storeStr","");
            jsonMap.put("storeSelectType","");
            jsonMap.put("compMemTypeId",compMemTypeId);
            jsonMap.put("posterId","");
            jsonMap.put("btnFlag",btnFlag);
            String campaignJSON=new Gson().toJson(campaign);
            jsonMap.put("campaign",campaignJSON);

            String campaginGrantJSON=new Gson().toJson(campaginGrant);
            jsonMap.put("campaginGrant",campaginGrantJSON);
            jsonMap.put("compId",compId);

            jsonMap.put("rewardlist",rewardlist);
            jsonMap.put("forwardCoupon",forwardCoupon);
            jsonStr = JSON.toJSONString(jsonMap);
        }catch (Exception e){
            e.printStackTrace();
        }

        LogUtils.e("jsonStr---"+jsonStr);
        campaignServiceManager.saveCampaignInfo(jsonStr)
                .subscribe(new BaseObserver<CampaignSaveResult>(context,false){
                    @Override
                    public void onSuccess(CampaignSaveResult result, String msg) {

                        if(result!=null){
                            if("save".equals(btnFlag)){
                                ToastUtils.showCustomShortBottom("保存成功！");
                            }else {
                                ToastUtils.showCustomShortBottom("发布成功！");
                            }

                            ActivityUtils.startActivity(GiveCouponManageActivity.class, R.anim.slide_in_right,R.anim.slide_out_left);


                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });

    }
}
