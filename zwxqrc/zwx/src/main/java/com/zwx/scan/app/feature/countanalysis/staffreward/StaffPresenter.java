package com.zwx.scan.app.feature.countanalysis.staffreward;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import com.zwx.library.utils.EncryptUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TextColorSizeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.base.BaseObserver;
import com.zwx.scan.app.data.bean.CampaignChartBean;
import com.zwx.scan.app.data.bean.CompMember;
import com.zwx.scan.app.data.bean.MemberCount;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.StaffCount;
import com.zwx.scan.app.data.bean.StaffCountBean;
import com.zwx.scan.app.data.bean.StaffCountSecond;
import com.zwx.scan.app.data.bean.StaffReward;
import com.zwx.scan.app.data.http.service.CountAnalysisManager;
import com.zwx.scan.app.data.http.service.RetrofitServiceManager;
import com.zwx.scan.app.feature.countanalysis.member.MemberAnalysisActivity;
import com.zwx.scan.app.feature.countanalysis.member.MemberAnalysisDetailActivity;

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
public class StaffPresenter implements StaffContract.Presenter {


    private  StaffContract.View view;
    private CountAnalysisManager countAnalysisManager;

    //添加订阅，应用于取消订阅，防止造成内存泄漏
    private CompositeDisposable disposable;




    public StaffPresenter(StaffContract.View view) {
        countAnalysisManager = new CountAnalysisManager();
        this.view = view;
        disposable = new CompositeDisposable();
    }


    @Override
    public void queryStaffRewardTotalList(Context context, String storeId, String date) {
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

        StaffRewardAnalysisActivity activity = (StaffRewardAnalysisActivity) context;
        countAnalysisManager.queryStaffRewardTotalList(storeId,date)
                .subscribe(new BaseObserver<StaffCount>(context,true){
                    @Override
                    public void onSuccess(StaffCount staffCount,String msg) {

                        if(staffCount != null){
                            String  count = staffCount.getTotal() != null && !"0".equals(staffCount.getTotal())? staffCount.getTotal():"-";
                           activity.tv_date.setText("拉新参与总数："+count);
                           activity.staffCounts = staffCount.getStaffReport();
//                           activity.pageAdapter.updateData(activity.staffCounts);
                            queryStaffRewardRankTotalList(context,storeId, date);
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });

    }


    @Override
    public void queryStaffRewardRankTotalList(Context context, String storeId,String date) {

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

        StaffRewardAnalysisActivity activity = (StaffRewardAnalysisActivity) context;
        countAnalysisManager.queryStaffRewardRankTotalList(storeId,date)
                .subscribe(new BaseObserver<List<StaffReward>>(context,true){
                    @Override
                    public void onSuccess(List<StaffReward> staffRewards,String msg) {

                        if(staffRewards != null && staffRewards.size()>= 0){
                            activity.staffRewards =staffRewards;

                            activity.analysisAdapter= new StaffAnalysisAdapter(activity,activity.staffRewards);
                            activity.recyclerView.setAdapter(activity.analysisAdapter);
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });

    }

    @Override
    public void queryMoreStoreStaffRewardTotalList(Context context,String userId, String storeId, String date,boolean isSelectStore) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("staffId",userId);
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

        StaffRewardAnalysisActivity activity = (StaffRewardAnalysisActivity) context;
        countAnalysisManager.queryMoreStoreStaffRewardTotalList(userId,storeId,date)
                .subscribe(new BaseObserver<StaffCountBean>(context,false){
                    @Override
                    public void onSuccess(StaffCountBean staffCount,String msg) {

                        if(staffCount != null){
                            activity.staffCount = staffCount;
                            String  count = staffCount.getTotal() != null && !"0".equals(staffCount.getTotal())? staffCount.getTotal():"—";
                            activity.joinCount = staffCount.getPtotal();
                            activity.successCount = staffCount.getPstotal();
                            if("A".equals(activity.selectPFlag)){
                                if(activity.joinCount != null && !"".equals(activity.joinCount) && !"0".equals(activity.joinCount)){
                                    activity.tv_date.setText("拉新参与总数："+activity.joinCount);
                                }else {
                                    activity.tv_date.setText("拉新参与总数：—");
                                }
                                activity.tv_desc.setText("拉新参与数");

                            }else {
                                if(activity.successCount != null && !"".equals(activity.successCount) && !"0".equals(activity.successCount)){
                                    activity.tv_date.setText("拉新成功总数："+activity.successCount);

                                }else {
                                    activity.tv_date.setText("拉新成功总数：—");
                                }

                                activity.tv_desc.setText("拉新成功数");
                            }

                            activity.staffJoinList = staffCount.getpStaffReport();
                            activity.staffSuccessList = staffCount.getPsStaffReport();

                            if (activity.staffJoinList != null && activity.staffJoinList.size()>0){
                                for (CampaignChartBean chartBean2: activity.staffJoinList){
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
                                activity.staffJoinList = new ArrayList<>();
                            }

                            if (activity.staffSuccessList != null && activity.staffSuccessList.size()>0){
                                for (CampaignChartBean chartBean2: activity.staffSuccessList){
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
                                activity.staffSuccessList = new ArrayList<>();
                            }
                            if(isSelectStore){
                                activity.setCheckboxAdapter();
                            }
                            activity.staffCount = staffCount;
                            activity.staffPagerAdapter.updateData();

                        }
                        queryMoreStoreStaffRewardRankTotalList(context,storeId, date,activity.rewardType);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);
                        queryMoreStoreStaffRewardRankTotalList(context,storeId, date,activity.rewardType);
                    }
                });
    }

    @Override
    public void queryMoreStoreStaffRewardRankTotalList(Context context, String storeId, String date,String rewardType) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("storeId",storeId);
        params.put("date",date);
        params.put("rewardType",rewardType);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        StaffRewardAnalysisActivity activity = (StaffRewardAnalysisActivity) context;
        countAnalysisManager.queryMoreStoreStaffRewardRankTotalList(storeId,date,rewardType)
                .subscribe(new BaseObserver<List<StaffReward>>(context,true){
                    @Override
                    public void onSuccess(List<StaffReward> staffRewards,String msg) {

                        if(staffRewards != null && staffRewards.size()> 0){
                            activity.staffRewards =staffRewards;
                            activity.recyclerView.setVisibility(View.VISIBLE);
                            activity.ll_no_data.setVisibility(View.GONE);
                        }else {
                            activity.staffRewards.clear();
                            activity.recyclerView.setVisibility(View.GONE);
                            activity.ll_no_data.setVisibility(View.VISIBLE);
                            activity.tv_no_data.setText("暂无员工拉新成功哦！");

                        }
                        activity.analysisAdapter= new StaffAnalysisAdapter(activity,activity.staffRewards);
                        activity.recyclerView.setAdapter(activity.analysisAdapter);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                        activity.recyclerView.setVisibility(View.GONE);
                        activity.ll_no_data.setVisibility(View.VISIBLE);
                        activity.iv_no_data.setImageResource(R.drawable.ic_wifi_not);
                        activity.tv_no_data.setVisibility(View.GONE);
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

        StaffRewardAnalysisActivity activity = (StaffRewardAnalysisActivity) context;
        countAnalysisManager.queryStaffBrandAndStoreList(userId)
                .subscribe(new BaseObserver<MoreStoreBean>(context,true){
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
}
