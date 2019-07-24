package com.zwx.scan.app.feature.countanalysis.campaign;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.zwx.library.utils.EncryptUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TextColorSizeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.base.BaseObserver;
import com.zwx.scan.app.data.bean.CampaignChartBean;
import com.zwx.scan.app.data.bean.CampaignCount;
import com.zwx.scan.app.data.bean.CampaignCountSecond;
import com.zwx.scan.app.data.bean.CampaignCouponDetailbean;
import com.zwx.scan.app.data.bean.CampaignDetaiList;
import com.zwx.scan.app.data.bean.CampaignDetail;
import com.zwx.scan.app.data.bean.CampaignDetailBean;
import com.zwx.scan.app.data.bean.CampaignTotal;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.StaffCount;
import com.zwx.scan.app.data.bean.StaffReward;
import com.zwx.scan.app.data.bean.User;
import com.zwx.scan.app.data.http.service.CountAnalysisManager;
import com.zwx.scan.app.data.http.service.RetrofitServiceManager;
import com.zwx.scan.app.feature.accountmanage.AccountActivity;
import com.zwx.scan.app.feature.accountmanage.AccountListAdapter;
import com.zwx.scan.app.feature.countanalysis.staffreward.StaffAnalysisAdapter;
import com.zwx.scan.app.feature.countanalysis.staffreward.StaffRewardAnalysisActivity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
public class CampaignPresenter implements CampaignContract.Presenter {


    private  CampaignContract.View view;
    private CountAnalysisManager countAnalysisManager;

    //添加订阅，应用于取消订阅，防止造成内存泄漏
    private CompositeDisposable disposable;




    public CampaignPresenter(CampaignContract.View view) {
        countAnalysisManager = new CountAnalysisManager();
        this.view = view;
        disposable = new CompositeDisposable();
    }


    @Override
    public void queryCampaignCountanalysis(Context context, String storeId, String date) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("storeId",storeId);
        params.put("date",date);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        CampaignAnalysisActivity activity = (CampaignAnalysisActivity) view;
        countAnalysisManager.queryCampaignCountanalysis(storeId,date)
                .subscribe(new BaseObserver<CampaignCount>(context,true){
                    @Override
                    public void onSuccess(CampaignCount campaignCount,String msg) {

                        if(campaignCount != null){
                            activity.tvDate.setText(campaignCount.getDate());
                            activity.getCountList = campaignCount.getGetCountList();
                            activity.fowardCountList = campaignCount.getFowardCountList();
                            activity.receiveCountList = campaignCount.getReceiveCountList();


                            int subTextColor = Color.parseColor("#4C94FC");
                            String[] subTextArray = {"人"};
                            String join = "-";
                            String get = "-";
                            String receive = "-";
                            if(!campaignCount.getFowardCount().equals("0")&&campaignCount.getFowardCount()!=null){
                                join = campaignCount.getFowardCount() + "人";
                                activity.tvJoin .setText(TextColorSizeUtils.getTextSpan(context,subTextColor,25,join,subTextArray));
                            }else {
                                activity.tvJoin .setText("-");
                            }

                            if(!campaignCount.getGetCount().equals("0")&&campaignCount.getGetCount()!=null){
                                get = campaignCount.getGetCount() + "人";
                                activity.tvGet .setText(TextColorSizeUtils.getTextSpan(context,subTextColor,25,get,subTextArray));
                            }else {
                                activity.tvGet .setText("-");
                            }

                            if(!campaignCount.getReceiveCount().equals("0")&&campaignCount.getReceiveCount()!=null){
                                receive = campaignCount.getReceiveCount() + "人";
                                activity.tvReceive .setText(TextColorSizeUtils.getTextSpan(context,subTextColor,25,get,subTextArray));
                            }else {
                                activity.tvReceive .setText("-");
                            }



//                            activity.tvReceive .setText(receive);
//                            activity.tvJoin .setText(join);
                            activity.pageAdapter.updateData(campaignCount);

                            String  status = "P";
                            querySpecificCampaignCountList(activity,storeId,status);

                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });

    }


    @Override
    public void querySpecificCampaignCountList(Context context, String storeId,String status) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("storeId",storeId);
        params.put("status",status);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        CampaignAnalysisActivity activity = (CampaignAnalysisActivity) view;
        countAnalysisManager.querySpecificCampaignCountList(storeId,status)
                .subscribe(new BaseObserver<List<CampaignTotal>>(context,true){
                    @Override
                    public void onSuccess(List<CampaignTotal> campaignTotals,String msg) {

                        if(campaignTotals != null ){
                            activity.campaignTotals = campaignTotals;
                            activity.setRvAdapter();
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });

    }

    @Override
    public void queryCountByCampaignIdAndDate(Context context, String campaignId, String date) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("campaignId",campaignId);
        params.put("date",date);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        CampaignAnalysisDetailActivity activity = (CampaignAnalysisDetailActivity) view;
        countAnalysisManager.queryCountByCampaignIdAndDate(campaignId,date)
                .subscribe(new BaseObserver<CampaignDetail>(context,true){
                    @Override
                    public void onSuccess(CampaignDetail campaignDetail,String msg) {

                        if(campaignDetail != null ){
                            activity.getList =campaignDetail.getGetList();
                            activity.fowardList =campaignDetail.getFowardList();
                            activity.receiveList =campaignDetail.getReceiveList();
                            activity.joinList =campaignDetail.getJoinList();
                            activity.registerList =campaignDetail.getRegisterList();
                            activity.viewList =campaignDetail.getViewList();

                            activity.sendList =campaignDetail.getSendList();

                            activity.joinTotal =campaignDetail.getJoinTotal();
                            activity.fowardTotal =campaignDetail.getFowardTotal();
                            activity.getTotal =campaignDetail.getGetTotal();
                            activity.receiveTotal =campaignDetail.getReceiveTotal();
                            activity.registerTotal =campaignDetail.getRegisterTotal();


                            activity.viewTotal =campaignDetail.getViewTotal();

                            activity.tvChartName.setText("活动参与总人数量趋势");
                            String total =  activity.fowardTotal!=null && ! activity.fowardTotal.equals("0")? activity.fowardTotal + "人":"-";
                            activity.tvTotal.setText("参与总人数："+total);
//                            activity.setViewA();
//                            activity.mPagerAdapter.updateData(activity.mTitles,campaignDetail);
                            queryCampaignTotalDetailList(activity,campaignId);
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });
    }

    @Override
    public void queryCampaignTotalDetailList(Context context, String campaignId) {


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

        CampaignAnalysisDetailActivity activity = (CampaignAnalysisDetailActivity) view;
        countAnalysisManager.queryCampaignTotalDetailList(campaignId)
                .subscribe(new BaseObserver<CampaignDetaiList>(context,true){
                    @Override
                    public void onSuccess(CampaignDetaiList campaignCount,String msg) {

                        if(campaignCount != null ){
                            activity.couponCampaignTotalList =campaignCount.getCouponCampaignTotalList();
                            activity.campaignTotal = campaignCount.getCampaignTotal();
                           /* activity.analysisAdapter= new StaffAnalysisAdapter(activity,activity.staffRewards);
                            activity.recyclerView.setAdapter(activity.analysisAdapter);*/
                            String  campaignTotal = campaignCount.getCampaignTotal().getJoinCount()!=null&&campaignCount.getCampaignTotal().getJoinCount()!=0?campaignCount.getCampaignTotal().getJoinCount()+"":"-";
                            activity.tv_join.setText(campaignTotal);
                            activity.tv_foward.setText(campaignCount.getCampaignTotal().getFowardCount()!= null && campaignCount.getCampaignTotal().getFowardCount() != 0?campaignCount.getCampaignTotal().getFowardCount()+"":"-");
                            activity.tv_send.setText(campaignCount.getCampaignTotal().getSendCouponCount()!=null && campaignCount.getCampaignTotal().getSendCouponCount() != 0?campaignCount.getCampaignTotal().getSendCouponCount()+"":"-");
                            activity.tv_get.setText(campaignCount.getCampaignTotal().getGetCouponCount()!=null && campaignCount.getCampaignTotal().getGetCouponCount() != 0?campaignCount.getCampaignTotal().getGetCouponCount()+"":"-");
                            activity.tv_receive.setText(campaignCount.getCampaignTotal().getReceiveCouponCount()!=null && campaignCount.getCampaignTotal().getReceiveCouponCount() != 0?campaignCount.getCampaignTotal().getReceiveCouponCount()+"":"-");
                            activity.tv_register.setText(campaignCount.getCampaignTotal().getRegisterMemCount()!=null && campaignCount.getCampaignTotal().getRegisterMemCount() != 0?campaignCount.getCampaignTotal().getRegisterMemCount()+"":"-");
                            activity.tv_view.setText(campaignCount.getCampaignTotal().getViewCount()!=null && campaignCount.getCampaignTotal().getViewCount() != 0?campaignCount.getCampaignTotal().getViewCount()+"":"-");

                            activity.setCouponAdapter();

                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });
    }

    @Override
    public void queryBrandAndStoreList(Context context, String userId) {

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

        CampaignAnalysisActivity activity = (CampaignAnalysisActivity) view;
        countAnalysisManager.queryBrandAndStoreList(userId)
                .subscribe(new BaseObserver<MoreStoreBean>(context,false){
                    @Override
                    public void onSuccess(MoreStoreBean moreStoreBean,String msg) {

                        if(moreStoreBean != null ){
                            activity.branList = moreStoreBean.getBrandList();
                            activity.showPopView(activity);
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });

    }

    @Override
    public void queryMoreStoreCampaignCountanalysis(Context context, String storeId, String date,boolean isSelectStore) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("storeId",storeId);
        params.put("date",date);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        CampaignAnalysisActivity activity = (CampaignAnalysisActivity) view;
        countAnalysisManager.queryMoreStoreCampaignCountanalysis(storeId,date)
                .subscribe(new BaseObserver<CampaignCountSecond>(context,false){
                    @Override
                    public void onSuccess(CampaignCountSecond campaignCount, String msg) {

                        if(campaignCount != null){
                            if(campaignCount.getDate() != null && !"".equals(campaignCount.getDate())){

                                activity.tvDate.setText("("+campaignCount.getDate()+")");
                            }else {
                                activity.tvDate.setText("");
                            }

                            activity.getCountList2 = campaignCount.getGetCountList();
                            activity.fowardCountList2 = campaignCount.getFowardCountList();
                            activity.receiveCountList2 = campaignCount.getReceiveCountList();

                            if (activity.getCountList2 != null && activity.getCountList2.size()>0){
                                for (CampaignChartBean chartBean2: activity.getCountList2){
                                    String storeId2 = chartBean2.getStoreId();
                                    if (activity.checkStoreList != null && activity.checkStoreList.size()>0){
                                        for (CampaignChartBean chartBean: activity.checkStoreList){
                                            String storeId = chartBean.getStoreId();

                                            if(storeId2.equals(storeId)){
                                                chartBean2.setColor(chartBean.getColor());
                                                break;
                                            }
                                        }
                                    }
                                }
                            }else {
                                activity.getCountList2 = new ArrayList<>();
                            }

                            if (activity.fowardCountList2 != null && activity.fowardCountList2.size()>0){
                                for (CampaignChartBean chartBean2: activity.fowardCountList2){
                                    String storeId2 = chartBean2.getStoreId();
                                    if (activity.checkStoreList != null && activity.checkStoreList.size()>0){
                                        for (CampaignChartBean chartBean: activity.checkStoreList){
                                            String storeId = chartBean.getStoreId();

                                            if(storeId2.equals(storeId)){
                                                chartBean2.setColor(chartBean.getColor());
                                                break;
                                            }
                                        }
                                    }
                                }
                            }else {
                                activity.fowardCountList2 = new ArrayList<>();
                            }

                            if (activity.receiveCountList2 != null && activity.receiveCountList2.size()>0){
                                for (CampaignChartBean chartBean2: activity.receiveCountList2){
                                    String storeId2 = chartBean2.getStoreId();
                                    if (activity.checkStoreList != null && activity.checkStoreList.size()>0){
                                        for (CampaignChartBean chartBean: activity.checkStoreList){
                                            String storeId = chartBean.getStoreId();

                                            if(storeId2.equals(storeId)){
                                                chartBean2.setColor(chartBean.getColor());
                                                break;
                                            }
                                        }
                                    }
                                }
                            }else {
                                activity.receiveCountList2 = new ArrayList<>();
                            }

                            int subTextColor = Color.parseColor("#4C94FC");
                            String[] subTextArray = {"人"};
                            String join = "—";
                            String get = "—";
                            String receive = "—";
                            if(!campaignCount.getFowardCount().equals("0")&&campaignCount.getFowardCount()!=null){
                                join = campaignCount.getFowardCount() + "人";
                                activity.tvJoin .setText(TextColorSizeUtils.getTextSpan(context,subTextColor,40,join,subTextArray));
                            }else {
                                activity.tvJoin .setText("—");
                            }

                            String[] subTextArray2 = {"张"};
                            if(!campaignCount.getGetCount().equals("0")&&campaignCount.getGetCount()!=null){
                                get = campaignCount.getGetCount() + "张";
                                activity.tvGet .setText(TextColorSizeUtils.getTextSpan(context,subTextColor,40,get,subTextArray2));
                            }else {
                                activity.tvGet .setText("—");
                            }
                            String[] subTextArray3 = {"张"};
                            if(!campaignCount.getReceiveCount().equals("0")&&campaignCount.getReceiveCount()!=null){
                                receive = campaignCount.getReceiveCount() + "张";
                                activity.tvReceive .setText(TextColorSizeUtils.getTextSpan(context,subTextColor,40,receive,subTextArray3));
                            }else {
                                activity.tvReceive .setText("—");
                            }
                            if(isSelectStore){
                                activity.setCheckboxAdapter();
                            }
                            activity.campaignCountSecond = campaignCount;
                            activity.campaignPagerAdapter.updateData();

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
    public void queryMoreSpecificCampaignCountList(Context context, String storeId, String status,String date,boolean isSelectStore,boolean isSelectAnalysis) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("storeId",storeId);
        params.put("status",status);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        CampaignAnalysisActivity activity = (CampaignAnalysisActivity) context;
        countAnalysisManager.queryMoreSpecificCampaignCountList(storeId,status)
                .subscribe(new BaseObserver<List<CampaignTotal>>(context,true){
                    @Override
                    public void onSuccess(List<CampaignTotal> campaignTotals,String msg) {

                        if(campaignTotals != null && campaignTotals.size()>0){
                            activity.campaignTotals = campaignTotals;
                            activity.ll_no_data.setVisibility(View.GONE);
                            activity.lvCampaign.setVisibility(View.VISIBLE);
                        }else {
                            activity.ll_no_data.setVisibility(View.VISIBLE);
                            activity.lvCampaign.setVisibility(View.GONE);
                            activity.iv_no_data.setImageResource(R.drawable.ic_no_data_tip);

                        }
                        activity.campaignAnalysisAdapter = new CampaignAnalysisListViewAdapter(context,activity.campaignTotals);
                        activity.lvCampaign.setAdapter(activity.campaignAnalysisAdapter);



                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.campaignAnalysisAdapter.notifyDataSetChanged();
                        activity.ll_no_data.setVisibility(View.VISIBLE);
                        activity.lvCampaign.setVisibility(View.GONE);
                        activity.iv_no_data.setImageResource(R.drawable.ic_wifi_not);
//                        activity.tv_no_data.setText("信号不好");
                        activity.tv_no_data.setVisibility(View.GONE);

                    }
                });
    }


    @Override
    public void queryMoreStoreWholeAndCouponCount(Context context, String storeId, String campaignId,String date,boolean isSelectStore) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("storeId",storeId);
        params.put("campaignId",campaignId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        CampaignAnalysisDetailActivity activity = (CampaignAnalysisDetailActivity) view;
        countAnalysisManager.queryMoreStoreWholeAndCouponCount(storeId,campaignId)
                .subscribe(new BaseObserver<CampaignDetaiList>(context,true){
                    @Override
                    public void onSuccess(CampaignDetaiList campaignCount,String msg) {

                        if(campaignCount != null ){
                            activity.couponCampaignTotalList =campaignCount.getCouponCampaignTotalList();
                            activity.campaignTotal = campaignCount.getCampaignTotal();
                            if(activity.campaignTotal!=null){
                                String  campaignTotal = campaignCount.getCampaignTotal().getJoinCount()!=null&&campaignCount.getCampaignTotal().getJoinCount()!=0?campaignCount.getCampaignTotal().getJoinCount()+"":"—";
                                activity.tv_join.setText(campaignTotal);
                                activity.tv_foward.setText(campaignCount.getCampaignTotal().getFowardCount()!= null && campaignCount.getCampaignTotal().getFowardCount() != 0?campaignCount.getCampaignTotal().getFowardCount()+"":"—");
                                activity.tv_send.setText(campaignCount.getCampaignTotal().getSendCouponCount()!=null && campaignCount.getCampaignTotal().getSendCouponCount() != 0?campaignCount.getCampaignTotal().getSendCouponCount()+"":"—");
                                activity.tv_get.setText(campaignCount.getCampaignTotal().getGetCouponCount()!=null && campaignCount.getCampaignTotal().getGetCouponCount() != 0?campaignCount.getCampaignTotal().getGetCouponCount()+"":"—");
                                activity.tv_receive.setText(campaignCount.getCampaignTotal().getReceiveCouponCount()!=null && campaignCount.getCampaignTotal().getReceiveCouponCount() != 0?campaignCount.getCampaignTotal().getReceiveCouponCount()+"":"—");
                                activity.tv_register.setText(campaignCount.getCampaignTotal().getRegisterMemCount()!=null && campaignCount.getCampaignTotal().getRegisterMemCount() != 0?campaignCount.getCampaignTotal().getRegisterMemCount()+"":"—");
                                activity.tv_view.setText(campaignCount.getCampaignTotal().getViewCount()!=null && campaignCount.getCampaignTotal().getViewCount() != 0?campaignCount.getCampaignTotal().getViewCount()+"":"—");

                            }else {
                                activity.tv_join.setText("—");
                                activity.tv_foward.setText("—");
                                activity.tv_send.setText("—");
                                activity.tv_get.setText("—");
                                activity.tv_receive.setText("—");
                                activity.tv_register.setText("—");
                                activity.tv_view.setText("—");
                            }

                            if(activity.couponCampaignTotalList!= null && activity.couponCampaignTotalList.size()>0){
                                activity.tv_no_data.setVisibility(View.GONE);
                                activity.rvCouponList.setVisibility(View.VISIBLE);
                                activity.setCouponAdapter();
                            }else {
                                activity.tv_no_data.setVisibility(View.VISIBLE);
                                activity.rvCouponList.setVisibility(View.GONE);
                            }

                            if(isSelectStore){
                                queryMoreStoreWholeCount(context,storeId,campaignId,date,isSelectStore);
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
    public void queryMoreStoreWholeCount(Context context, String storeId, String campaignId, String date, boolean isSelectStore) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("storeId",storeId);
        params.put("campaignId",campaignId);
        params.put("date",date);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        CampaignAnalysisDetailActivity activity = (CampaignAnalysisDetailActivity) view;
        countAnalysisManager.queryMoreStoreWholeCount(storeId,campaignId,date)
                .subscribe(new BaseObserver<CampaignDetailBean>(context,true){
                    @Override
                    public void onSuccess(CampaignDetailBean campaignDetail,String msg) {

                        if(campaignDetail != null ){
                            activity.getList2 =campaignDetail.getGetList();
                            activity.fowardList2 =campaignDetail.getFowardList();
                            activity.receiveList2 =campaignDetail.getReceiveList();
                            activity.joinList2 =campaignDetail.getJoinList();
                            activity.registerList2 =campaignDetail.getRegisterList();
                            activity.viewList2 =campaignDetail.getViewList();

                            activity.sendList2 =campaignDetail.getSendList();

                            activity.joinTotal =campaignDetail.getJoinTotal();
                            activity.fowardTotal =campaignDetail.getFowardTotal();
                            activity.getTotal =campaignDetail.getGetTotal();
                            activity.receiveTotal =campaignDetail.getReceiveTotal();
                            activity.registerTotal =campaignDetail.getRegisterTotal();
                            activity.sendTotal = campaignDetail.getSendTotal();
                            activity.viewTotal = campaignDetail.getViewTotal();


                            if (activity.getList2 != null && activity.getList2.size()>0){
                                for (CampaignChartBean chartBean2: activity.getList2){
                                    String storeId2 = chartBean2.getStoreId();
                                    if (activity.checkStoreColorList != null && activity.checkStoreColorList.size()>0){
                                        for (CampaignChartBean chartBean: activity.checkStoreColorList){
                                            String storeId = chartBean.getStoreId();

                                            if(storeId2.equals(storeId)){
                                                chartBean2.setColor(chartBean.getColor());
                                                break;
                                            }
                                        }
                                    }
                                }
                            }else {
                                activity.getList2 = new ArrayList<>();
                            }


                            if (activity.fowardList2 != null && activity.fowardList2.size()>0){
                                for (CampaignChartBean chartBean2: activity.fowardList2){
                                    String storeId2 = chartBean2.getStoreId();
                                    if (activity.checkStoreColorList != null && activity.checkStoreColorList.size()>0){
                                        for (CampaignChartBean chartBean: activity.checkStoreColorList){
                                            String storeId = chartBean.getStoreId();

                                            if(storeId2.equals(storeId)){
                                                chartBean2.setColor(chartBean.getColor());
                                                break;
                                            }
                                        }
                                    }
                                }
                            }else {
                                activity.fowardList2 = new ArrayList<>();
                            }
                            if (activity.receiveList2 != null && activity.receiveList2.size()>0){
                                for (CampaignChartBean chartBean2: activity.receiveList2){
                                    String storeId2 = chartBean2.getStoreId();
                                    if (activity.checkStoreColorList != null && activity.checkStoreColorList.size()>0){
                                        for (CampaignChartBean chartBean: activity.checkStoreColorList){
                                            String storeId = chartBean.getStoreId();

                                            if(storeId2.equals(storeId)){
                                                chartBean2.setColor(chartBean.getColor());
                                                break;
                                            }
                                        }
                                    }
                                }
                            }else {
                                activity.receiveList2 = new ArrayList<>();
                            }

                            if (activity.joinList2 != null && activity.joinList2.size()>0){
                                for (CampaignChartBean chartBean2: activity.joinList2){
                                    String storeId2 = chartBean2.getStoreId();
                                    if (activity.checkStoreColorList != null && activity.checkStoreColorList.size()>0){
                                        for (CampaignChartBean chartBean: activity.checkStoreColorList){
                                            String storeId = chartBean.getStoreId();

                                            if(storeId2.equals(storeId)){
                                                chartBean2.setColor(chartBean.getColor());
                                                break;
                                            }
                                        }
                                    }
                                }
                            }else {
                                activity.joinList2 = new ArrayList<>();
                            }

                            if (activity.registerList2 != null && activity.registerList2.size()>0){
                                for (CampaignChartBean chartBean2: activity.registerList2){
                                    String storeId2 = chartBean2.getStoreId();
                                    if (activity.checkStoreColorList != null && activity.checkStoreColorList.size()>0){
                                        for (CampaignChartBean chartBean: activity.checkStoreColorList){
                                            String storeId = chartBean.getStoreId();

                                            if(storeId2.equals(storeId)){
                                                chartBean2.setColor(chartBean.getColor());
                                                break;
                                            }
                                        }
                                    }
                                }
                            }else {
                                activity.registerList2 = new ArrayList<>();
                            }

                            if (activity.viewList2 != null && activity.viewList2.size()>0){
                                for (CampaignChartBean chartBean2: activity.viewList2){
                                    String storeId2 = chartBean2.getStoreId();
                                    if (activity.checkStoreColorList != null && activity.checkStoreColorList.size()>0){
                                        for (CampaignChartBean chartBean: activity.checkStoreColorList){
                                            String storeId = chartBean.getStoreId();

                                            if(storeId2.equals(storeId)){
                                                chartBean2.setColor(chartBean.getColor());
                                                break;
                                            }
                                        }
                                    }
                                }
                            }else {
                                activity.viewList2 = new ArrayList<>();
                            }

                            if (activity.sendList2 != null && activity.sendList2.size()>0){
                                for (CampaignChartBean chartBean2: activity.sendList2){
                                    String storeId2 = chartBean2.getStoreId();
                                    if (activity.checkStoreColorList != null && activity.checkStoreColorList.size()>0){
                                        for (CampaignChartBean chartBean: activity.checkStoreColorList){
                                            String storeId = chartBean.getStoreId();

                                            if(storeId2.equals(storeId)){
                                                chartBean2.setColor(chartBean.getColor());
                                                break;
                                            }
                                        }
                                    }
                                }
                            }else {
                                activity.sendList2 = new ArrayList<>();
                            }


                            String total= "";
                            if(activity.tabSelect ==0){
                                activity.tvChartName.setText("活动参与总人数量趋势");
                                total= activity.joinTotal!=null && !"0".equals(activity.joinTotal)?activity.joinTotal +"人":"—";
                                activity.tvTotal.setText("参与总人数："+total);
                                activity.tvLegend.setText("参与人数");
                            }else if(activity.tabSelect ==1){
                                activity.tvChartName.setText("活动转发数量趋势");
                                total = activity.fowardTotal!=null && !activity.fowardTotal.equals("0")?activity.fowardTotal + "次":"—";
                                activity.tvTotal.setText("转发数量："+total);
                                activity.tvLegend.setText("转发数量");
                            }else if(activity.tabSelect ==2){
                                activity.tvChartName.setText("优惠券发放数量趋势");
                                total= activity.sendTotal!=null && !activity.sendTotal.equals("0")?activity.sendTotal + "张":"—";
                                activity.tvTotal.setText("发放总数："+total);
                                activity.tvLegend.setText("发放数量");
                            }else if(activity.tabSelect ==3){
                                activity.tvChartName.setText("优惠券领取数量趋势");
                                total = activity.getTotal!=null && !activity.getTotal.equals("0")?activity.getTotal+"张":"—";
                                activity.tvTotal.setText("领取总数："+total);
                                activity.tvLegend.setText("领取总数");
                            }else if(activity.tabSelect ==4){
                                activity.tvChartName.setText("优惠券回收数量趋势");
                                total = activity.receiveTotal!=null && !activity.receiveTotal.equals("0")?activity.receiveTotal+"张":"—";
                                activity.tvTotal.setText("回收总数："+total);
                                activity.tvLegend.setText("回收数量");
                            }else if(activity.tabSelect ==5){
                                activity.tvChartName.setText("活动新增会员趋势");
                                total= activity.registerTotal!=null && !activity.registerTotal.equals("0")?activity.registerTotal + "人":"—";
                                activity.tvTotal.setText("新增总数："+total);
                                activity.tvLegend.setText("新增会员数");
                            }else if(activity.tabSelect ==6){
                                activity.tvChartName.setText("活动浏览量趋势");
                                total = activity.viewTotal!=null && !activity.viewTotal.equals("0")?activity.viewTotal + "次":"—";
                                activity.tvTotal.setText("浏览总数："+total);
                                activity.tvLegend.setText("浏览数");
                            }

                            activity.viewTotal =campaignDetail.getViewTotal();

                            if(isSelectStore){
                                activity.setCheckboxAdapter(0);
                            }
                            activity.campaignDetail = campaignDetail;
                            activity.mPagerAdapter.updateData();

                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });
    }

    @Override
    public void campaignAnalysisForzj(Context context, String userId, String compaignStatus,Integer pageSize,Integer pageNumber,boolean isRefresh) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("compaignStatus",compaignStatus);
        params.put("pageNumber",String.valueOf(pageNumber));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        GiveAnalysisActivity activity =  (GiveAnalysisActivity)context;

        countAnalysisManager.campaignAnalysisForzj(userId,compaignStatus,pageSize,pageNumber)
                .subscribe(new BaseObserver<List<CampaignTotal>>(context,true){
                    @Override
                    public void onSuccess(List<CampaignTotal> dataList,String msg) {

                        if (dataList != null && dataList.size() > 0) {
                            activity.campaignTotalList.addAll(dataList);
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
                            activity.campaignTotalList.addAll(dataList);
                            activity.listAdapter.notifyDataSetChanged();

                            if(activity.campaignTotalList != null  &&  activity.campaignTotalList.size()>0){
                                if(activity.campaignTotalList.size() < 10){
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                }else {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }

                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.listView.setVisibility(View.VISIBLE);
                            }else {

                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.listView.setVisibility(View.GONE);
                                activity.tv_no_data.setText("暂无常规发券设置！");
                            }


                        }
                        activity.ptr.onRefreshComplete();
                      /*  if(campaignTotalList != null && campaignTotalList.size()>0){
                            if (isRefresh) {
                                activity.campaignTotalList.clear();
                                activity.campaignTotalList.addAll(campaignTotalList);
//                                activity.listAdapter = new GiveAnalysisAdapter(activity, activity.campaignTotalList);
                                activity.mAdapter.notifyDataSetChanged();
                                activity.ptr.refreshComplete();
                                if (campaignTotalList.size() < 10) {
                                    activity.ptr.setLoadMoreEnable(false);
                                } else {
                                    activity.ptr.setLoadMoreEnable(true);
                                }
                            } else {
                                activity.campaignTotalList.addAll(campaignTotalList);
                                activity.mAdapter.notifyDataSetChanged();
                                activity.ptr.refreshComplete();
                                if (campaignTotalList.size() < 10) {
                                    activity.ptr.loadMoreComplete(false);
                                } else {
                                    activity.ptr.loadMoreComplete(true);
                                }
                            }
                        }else {
                            ToastUtils.showShort("暂无常规发券设置");
                            if(activity.campaignTotalList != null && activity.campaignTotalList.size()>0){
                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.rvList.setVisibility(View.VISIBLE);
                            }else {

                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.rvList.setVisibility(View.GONE);
                                activity.iv_no_data.setImageResource(R.drawable.ic_no_data_tip);
                                activity.tv_no_data.setText("暂无常规发券设置");
                            }
                        }
*/

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

//                        ToastUtils.showShort(context.getResources().getString(R.string.network_error));
//                        activity.ll_no_data.setVisibility(View.VISIBLE);
//                        activity.listView.setVisibility(View.GONE);
//                        activity.iv_no_data.setImageResource(R.drawable.ic_wifi_not);
////                        activity.tv_no_data.setText("网络信号不好");
//                        activity.tv_no_data.setText(context.getResources().getString(R.string.network_error));

                        if(activity.campaignTotalList != null  &&  activity.campaignTotalList.size()>0){
                            if(activity.campaignTotalList.size() < 10){
                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }else {
                                activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                            }

                            activity.ll_no_data.setVisibility(View.GONE);
                            activity.listView.setVisibility(View.VISIBLE);
                        }else {

                            activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            activity.ll_no_data.setVisibility(View.VISIBLE);
                            activity.listView.setVisibility(View.GONE);
                            activity.iv_no_data.setImageResource(R.drawable.ic_wifi_not);
                            activity.tv_no_data.setText(context.getResources().getString(R.string.network_error));
                        }
                        activity.ptr.onRefreshComplete();
                    }
                });
    }


    @Override
    public void queryzjCountDetail(Context context, String campaignId) {
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

        GiveAnalysisDetailActivity activity = (GiveAnalysisDetailActivity) context;
        countAnalysisManager.queryzjCountDetail(campaignId)
                .subscribe(new BaseObserver<CampaignCouponDetailbean>(context,true){
                    @Override
                    public void onSuccess(CampaignCouponDetailbean detailbean,String msg) {

                        if(detailbean != null ){

                            activity.wholeInfo = detailbean.getWholeInfo();

                            if(activity.wholeInfo != null){
                                int get = activity.wholeInfo.getGetCouponCount();
                                int send = activity.wholeInfo.getSendCouponCount();
                                int receive = activity.wholeInfo.getReceiveCouponCount();
                                BigDecimal amt = activity.wholeInfo.getConsumeAmt();

                                if(get == 0){
                                    activity.tvGet.setText("-");
                                }else {
                                    activity.tvGet.setText(""+get);
                                }

                                if(send == 0){
                                    activity.tvSend.setText("-");
                                }else {
                                    activity.tvSend.setText(""+send);
                                }

                                if(receive == 0){
                                    activity.tvReceive.setText("-");
                                }else {
                                    activity.tvReceive.setText(""+receive);
                                }

                                if(amt != null && amt.doubleValue()>0){
                                    String consume = new DecimalFormat("0.00").format(amt.setScale(2,BigDecimal.ROUND_DOWN).doubleValue()).toString();
                                    activity.tvConsumeAmt.setText(consume);

                                }else {
                                    activity.tvConsumeAmt.setText("-");
                                }

                            }
                            activity.couponList = detailbean.getCouponList();
                            activity.storeCouponList = detailbean.getStoreList();
                            activity.storeCouponCountList = detailbean.getStoreCouponCountList();
                            activity.setCustomAdapter();

                        }


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showToast(message);
                    }
                });
    }

    @Override
    public void queryPtCountDetail(Context context, String campaignId) {
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

        PinTuanAnalysisDetailActivity activity = (PinTuanAnalysisDetailActivity) context;
        countAnalysisManager.queryPtCountDetail(campaignId)
                .subscribe(new BaseObserver<CampaignCouponDetailbean>(context,false){
                    @Override
                    public void onSuccess(CampaignCouponDetailbean detailbean,String msg) {

                        if(detailbean != null ){

                            activity.wholeInfo = detailbean.getWholeInfo();

                            if(activity.wholeInfo != null){
                                int join = activity.wholeInfo.getJoinCount();
                                int forward = activity.wholeInfo.getFowardCount();
                                int viewCount   = activity.wholeInfo.getViewCount();
                                int get = activity.wholeInfo.getGetCouponCount();
                                int send = activity.wholeInfo.getSendCouponCount();
                                int receive = activity.wholeInfo.getReceiveCouponCount();
                                BigDecimal amt = activity.wholeInfo.getConsumeAmt();
                                BigDecimal infoUnitPrice = activity.wholeInfo.getUnitPrice();

                                int total = activity.wholeInfo.getTotal();
                              /*  if(join == 0){
                                    activity.tv_pt.setText("—");
                                }else {
                                    activity.tv_pt.setText(""+total);
                                }*/

                                if(join == 0){
                                    activity.tv_join.setText("—");
                                }else {
                                    activity.tv_join.setText(""+join);
                                }
                                if(forward == 0){
                                    activity.tv_forward.setText("—");
                                }else {
                                    activity.tv_forward.setText(""+forward);
                                }

                                if(viewCount == 0){
                                    activity.tv_view.setText("—");
                                }else {
                                    activity.tv_view.setText(""+viewCount);
                                }


                                if(send == 0){
                                    activity.tvSend.setText("—");
                                }else {
                                    activity.tvSend.setText(""+send);
                                }

                                if(receive == 0){
                                    activity.tvReceive.setText("—");
                                }else {
                                    activity.tvReceive.setText(""+receive);
                                }
                                activity.tvSaleAmt.setText("—");

                                if(infoUnitPrice != null && infoUnitPrice.doubleValue()>0){
                                    String unitPrice = RegexUtils.getDoubleString(infoUnitPrice.doubleValue());
                                    activity.tvSaleAmt.setText(unitPrice);
                                }else {
                                    activity.tvSaleAmt.setText("—");
                                }
                                String consume = "";
                                if(amt != null && amt.doubleValue()>0){
//                                    String consume = new DecimalFormat("0.00").format(amt.setScale(2,BigDecimal.ROUND_DOWN).doubleValue()).toString();
                                 /*   if(String.valueOf(amt.doubleValue()) != null && !"".equals(String.valueOf(amt.doubleValue()))){
                                        if(String.valueOf(amt.doubleValue()).contains(".0") ||String.valueOf(amt.doubleValue()).contains(".00") ){
                                            consume = String.valueOf(amt.doubleValue()).substring(0,String.valueOf(amt.doubleValue()).indexOf("."));
                                        }else {
                                            consume = "—";
                                        }
                                    }else {
                                        consume = "—";
                                    }*/
                                  consume = String.valueOf(amt.doubleValue());
                                    activity.tvConsumeAmt.setText(consume);

                                }else {
                                    activity.tvConsumeAmt.setText("—");
                                }

                            }
                            activity.couponList = detailbean.getCouponList();
                            activity.setCustomAdapter();

                        }


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                    }
                });

        /*Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        PinTuanAnalysisDetailActivity activity = (PinTuanAnalysisDetailActivity) view;
        params.put("storeId",activity.storeId);
        params.put("campaignId",campaignId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);


        countAnalysisManager.queryMoreStoreWholeAndCouponCount(activity.storeId,campaignId)
                .subscribe(new BaseObserver<CampaignDetaiList>(context,false){
                    @Override
                    public void onSuccess(CampaignDetaiList campaignCount,String msg) {

                        if(campaignCount != null ){
                            activity.couponCampaignTotalList =campaignCount.getCouponCampaignTotalList();
                            activity.campaignTotal = campaignCount.getCampaignTotal();
                            if(activity.campaignTotal!=null){
                                String  campaignTotal = campaignCount.getCampaignTotal().getJoinCount()!=null&&campaignCount.getCampaignTotal().getJoinCount()!=0?campaignCount.getCampaignTotal().getJoinCount()+"":"—";
                                activity.tv_join.setText(campaignTotal);
                                activity.tv_pt.setText(""+campaignTotal);
                                activity.tv_forward.setText(campaignCount.getCampaignTotal().getFowardCount()!= null && campaignCount.getCampaignTotal().getFowardCount() != 0?campaignCount.getCampaignTotal().getFowardCount()+"":"—");
                                activity.tvSend.setText(campaignCount.getCampaignTotal().getSendCouponCount()!=null && campaignCount.getCampaignTotal().getSendCouponCount() != 0?campaignCount.getCampaignTotal().getSendCouponCount()+"":"—");
//                                activity.tvGet.setText(campaignCount.getCampaignTotal().getGetCouponCount()!=null && campaignCount.getCampaignTotal().getGetCouponCount() != 0?campaignCount.getCampaignTotal().getGetCouponCount()+"":"—");
                                activity.tvReceive.setText(campaignCount.getCampaignTotal().getReceiveCouponCount()!=null && campaignCount.getCampaignTotal().getReceiveCouponCount() != 0?campaignCount.getCampaignTotal().getReceiveCouponCount()+"":"—");
//                                activity.tvRigter.setText(campaignCount.getCampaignTotal().getRegisterMemCount()!=null && campaignCount.getCampaignTotal().getRegisterMemCount() != 0?campaignCount.getCampaignTotal().getRegisterMemCount()+"":"—");
                                activity.tv_view.setText(campaignCount.getCampaignTotal().getViewCount()!=null && campaignCount.getCampaignTotal().getViewCount() != 0?campaignCount.getCampaignTotal().getViewCount()+"":"—");

                                BigDecimal amt = activity.campaignTotal.getConsumeAmt();
                                BigDecimal infoUnitPrice = activity.campaignTotal.getUnitPrice();



                                if(infoUnitPrice != null && infoUnitPrice.doubleValue()>0){
                                    String unitPrice = RegexUtils.getDoubleString(infoUnitPrice.doubleValue());
                                    activity.tvSaleAmt.setText(unitPrice);
                                }else {
                                    activity.tvSaleAmt.setText("—");
                                }
                                String consume = "";
                                if(amt != null && amt.doubleValue()>0){
//                                    String consume = new DecimalFormat("0.00").format(amt.setScale(2,BigDecimal.ROUND_DOWN).doubleValue()).toString();
                                    if(String.valueOf(amt.doubleValue()) != null && !"".equals(String.valueOf(amt.doubleValue()))){
                                        if(String.valueOf(amt.doubleValue()).contains(".0") ||String.valueOf(amt.doubleValue()).contains(".00") ){
                                            consume = String.valueOf(amt.doubleValue()).substring(0,String.valueOf(amt.doubleValue()).indexOf("."));
                                        }else {
                                            consume = "—";
                                        }
                                    }else {
                                        consume = "—";
                                    }
                                    activity.tvConsumeAmt.setText(consume);

                                }else {
                                    activity.tvConsumeAmt.setText("—");
                                }

                            }

                            if(activity.couponCampaignTotalList!= null && activity.couponCampaignTotalList.size()>0){
//                                activity.rvCouponList.setVisibility(View.VISIBLE);
                                activity.setCustomAdapter();
                            }else {
//                                activity.tv_no_data.setVisibility(View.VISIBLE);
//                                activity.rvCouponList.setVisibility(View.GONE);
                            }


                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });*/
    }

    @Override
    public void campaignAnalysisForPt(Context context, String userId, String compaignStatus, Integer pageSize, Integer pageNumber, boolean isRefresh) {


        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("compaignStatus",compaignStatus);
        params.put("pageNumber",String.valueOf(pageNumber));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        PinTuanAnalysisActivity activity =  (PinTuanAnalysisActivity)context;

        countAnalysisManager.campaignAnalysisForPt(userId,compaignStatus,pageSize,pageNumber)
                .subscribe(new BaseObserver<List<CampaignTotal>>(context,false){
                    @Override
                    public void onSuccess(List<CampaignTotal> dataList,String msg) {

                        if (dataList != null && dataList.size() > 0) {
                            activity.campaignTotalList.addAll(dataList);
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
                            activity.campaignTotalList.addAll(dataList);
                            activity.listAdapter.notifyDataSetChanged();

                            if(activity.campaignTotalList != null  &&  activity.campaignTotalList.size()>0){
                                if(activity.campaignTotalList.size() < 10){
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                }else {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }

                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.listView.setVisibility(View.VISIBLE);
                            }else {

                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.listView.setVisibility(View.GONE);
                                activity.tv_no_data.setText("暂无拼团数据！");
                            }


                        }
                        activity.ptr.onRefreshComplete();
                       /* if(campaignTotalList != null && campaignTotalList.size()>0){
                            if (isRefresh) {
                                activity.campaignTotalList.clear();
                                activity.campaignTotalList.addAll(campaignTotalList);
                                activity.ptr.setVisibility(View.VISIBLE);
                                activity.listAdapter = new PinTuanAnalysisAdapter(activity, activity.campaignTotalList);
                                activity.mAdapter.notifyDataSetChanged();
                                activity.ptr.refreshComplete();
                                if (campaignTotalList.size() < 10) {

                                    activity.ptr.setLoadMoreEnable(false);
                                } else {
                                    activity.ptr.setLoadMoreEnable(true);
                                }
                            } else {
                                activity.campaignTotalList.addAll(campaignTotalList);
                                activity.listAdapter = new PinTuanAnalysisAdapter(activity, activity.campaignTotalList);
                                activity.mAdapter.notifyDataSetChanged();
                                activity.ptr.refreshComplete();
                                if (campaignTotalList.size() < 10) {

                                    activity.ptr.loadMoreComplete(false);
                                } else {
                                    activity.ptr.loadMoreComplete(true);
                                }
                            }

                        }else {
                            activity.campaignTotalList.clear();
                            activity.mAdapter.notifyDataSetChanged();
                            activity.ptr.refreshComplete();
                            activity.ptr.setLoadMoreEnable(false);
                            ToastUtils.showShort("暂无拼团");



                        }*/


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        if(activity.campaignTotalList != null  &&  activity.campaignTotalList.size()>0){
                            if(activity.campaignTotalList.size() < 10){
                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }else {
                                activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                            }

                            activity.ll_no_data.setVisibility(View.GONE);
                            activity.listView.setVisibility(View.VISIBLE);
                        }else {

                            activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            activity.ll_no_data.setVisibility(View.VISIBLE);
                            activity.listView.setVisibility(View.GONE);
                            activity.tv_no_data.setText(message);
                        }
                        activity.ptr.onRefreshComplete();

//                        activity.ll_no_data.setVisibility(View.VISIBLE);
//                        activity.rvList.setVisibility(View.GONE);
////                        activity.tv_no_data.setText("信号不好");
//                        activity.tv_no_data.setVisibility(View.GONE);

                    }
                });
    }
}
