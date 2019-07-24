package com.zwx.scan.app.feature.countanalysis.member;

import android.content.Context;
import android.graphics.Color;

import com.zwx.library.utils.EncryptUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TextColorSizeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.base.BaseObserver;
import com.zwx.scan.app.data.bean.CampaignChartBean;
import com.zwx.scan.app.data.bean.CompMember;
import com.zwx.scan.app.data.bean.Index;
import com.zwx.scan.app.data.bean.MemCoupon;
import com.zwx.scan.app.data.bean.MemberCount;
import com.zwx.scan.app.data.bean.MemberCountBean;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.http.service.CountAnalysisManager;
import com.zwx.scan.app.data.http.service.HomeServiceManager;
import com.zwx.scan.app.data.http.service.RetrofitServiceManager;
import com.zwx.scan.app.feature.countanalysis.campaign.CampaignAnalysisActivity;
import com.zwx.scan.app.feature.home.HomeContract;
import com.zwx.scan.app.feature.home.HomeFragment;

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
public class MemberPresenter  implements MemberContract.Presenter {


    private  MemberContract.View view;
    private CountAnalysisManager countAnalysisManager;

    //添加订阅，应用于取消订阅，防止造成内存泄漏
    private CompositeDisposable disposable;




    public MemberPresenter(MemberContract.View view) {
        countAnalysisManager = new CountAnalysisManager();
        this.view = view;
        disposable = new CompositeDisposable();
    }


    @Override
    public void queryMemberCount(Context context, String storeId, String date) {


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

        MemberAnalysisActivity activity = (MemberAnalysisActivity) view;
        countAnalysisManager.queryMemberCount(storeId,date)
                .subscribe(new BaseObserver<MemberCount>(context,true){
                    @Override
                    public void onSuccess(MemberCount member,String msg) {

                        if(member != null){


                           String  reportTotal =  member.getMemberReportTotal();
                            if(reportTotal != null && !"0".equals(reportTotal)){
                                activity.tvCount.setText("新增会员总数："+reportTotal);
                            }else {
                                activity.tvCount.setText("新增会员总数：-");
                            }
                            String total = member.getStoreMemberTotal() != null && member.getStoreMemberTotal() != "0" ? member.getStoreMemberTotal()+"人":"-";
                            int subTextColor = Color.parseColor("#4C94FC");
                            String[] subTextArray = {"人"};

                            activity.tvNewCount.setText(TextColorSizeUtils.getTextSpan(activity,subTextColor,20,total,subTextArray));
                           activity.countList = member.getMemberReportList();

//                           activity.pageAdapter.updateData(activity.countList);

                           queryMemTypeCountList(context,storeId,date);
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });

    }

    @Override
    public void queryMemTypeCountListDetail(Context context, String storeId, String compMemTypeId, String date) {


        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("storeId",storeId);
        params.put("compMemTypeId",compMemTypeId);
        params.put("date",date);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        MemberAnalysisDetailActivity activity = (MemberAnalysisDetailActivity) view;
        countAnalysisManager.queryMemTypeCountListDetail(storeId,compMemTypeId,date)
                .subscribe(new BaseObserver<MemberCount>(context,true){
                    @Override
                    public void onSuccess(MemberCount member,String msg) {

                        if(member != null){
                            List<Map<String,Object>> memberReport = member.getMemberReportList();

                            String  reportTotal =  member.getMemberReportTotal();
                            if(reportTotal != null && !"0".equals(reportTotal)){
                                activity.tvDate.setText("新增会员总数："+reportTotal);
                            }else {
                                activity.tvDate.setText("新增会员总数：-");
                            }

                            String total = member.getStoreMemberTotal() != null ||member.getStoreMemberTotal() != "0" ? member.getStoreMemberTotal()+"人":"-";


                            int subTextColor = Color.parseColor("#4C94FC");
                            String[] subTextArray = {"人"};
                            activity.tvCount.setText(TextColorSizeUtils.getTextSpan(activity,subTextColor,20,total,subTextArray));
                            activity.countList = member.getMemberReportList();


//                            activity.pageAdapter.updateData(activity.countList);
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });
    }

    @Override
    public void queryMemTypeCountList(Context context, String storeId,String date) {

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

        MemberAnalysisActivity activity = (MemberAnalysisActivity) view;
        countAnalysisManager.queryMemTypeCountList(storeId,date)
                .subscribe(new BaseObserver<List<CompMember>>(context,true){
                    @Override
                    public void onSuccess(List<CompMember> members,String msg) {

                        if(members != null && members.size()>= 0){
                            activity.compMemberList =members;
                            activity.initRvAdapter();
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });

    }


    @Override
    public void queryMoreStoreMemberCountAnalysis(Context context, String storeId, String date,boolean isSelectMoreStore) {


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

        MemberAnalysisActivity activity = (MemberAnalysisActivity) context;
        countAnalysisManager.queryMoreStoreMemberCountAnalysis(storeId,date)
                .subscribe(new BaseObserver<MemberCountBean>(context,true){
                    @Override
                    public void onSuccess(MemberCountBean member,String msg) {

                        if(member != null){


                            String  reportTotal =  member.getMemberReportTotal();
                            if(reportTotal != null && !"0".equals(reportTotal)){
                                activity.tvCount.setText("新增会员总数："+reportTotal);
                            }else {
                                activity.tvCount.setText("新增会员总数：-");
                            }
                            String  total ;
                            int subTextColor = Color.parseColor("#4C94FC");
                            String[] subTextArray = {"人"};
                            if(member.getStoreMemberTotal() != null && member.getStoreMemberTotal() != "0"){
                                total =  member.getStoreMemberTotal()+"人";
                                activity.tvNewCount.setText(TextColorSizeUtils.getTextSpan(activity,subTextColor,40,total,subTextArray));
                            }else {
                                activity.tvNewCount.setText("-");
                            }

                            activity.memberCountList = member.getMemberReportList();

                            if (activity.memberCountList != null && activity.memberCountList.size()>0){
                                for (CampaignChartBean chartBean2: activity.memberCountList){
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
                                activity.memberCountList = new ArrayList<>();
                            }
                            activity.pageAdapter.updateData();

                            if(isSelectMoreStore){
                                activity.campaignChartBeanList = activity.memberCountList;
                                activity.setCheckboxAdapter();
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
    public void queryMoreStoreMemTypeCountListDetail(Context context, String storeId, String compMemTypeId, String date,boolean isSelectStore) {


        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("storeId",storeId);
        params.put("compMemTypeId",compMemTypeId);
        params.put("date",date);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        MemberAnalysisDetailActivity activity = (MemberAnalysisDetailActivity) view;
        countAnalysisManager.queryMoreStoreMemTypeCountListDetail(storeId,compMemTypeId,date)
                .subscribe(new BaseObserver<MemberCountBean>(context,false){
                    @Override
                    public void onSuccess(MemberCountBean member,String msg) {

                        if(member != null){
                            List<CampaignChartBean> memberReport = member.getMemberReportList();

                            String  reportTotal =  member.getMemberReportTotal();
                            if(reportTotal != null && !"0".equals(reportTotal)){
                                activity.tvDate.setText("新增会员总数："+reportTotal);
                            }else {
                                activity.tvDate.setText("新增会员总数：-");
                            }

                            String total ;


                            int subTextColor = Color.parseColor("#4C94FC");
                            String[] subTextArray = {"人"};

                            if(member.getStoreMemberTotal() != null ||member.getStoreMemberTotal() != "0"){
                                total = member.getStoreMemberTotal()+"人";
                                activity.tvCount.setText(TextColorSizeUtils.getTextSpan(activity,subTextColor,40,total,subTextArray));
                            }else {
                                activity.tvCount.setText("-");
                            }

                            activity.memberCountList= member.getMemberReportList();

                            if (activity.memberCountList != null && activity.memberCountList.size()>0){
                                for (CampaignChartBean chartBean2: activity.memberCountList){
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
                                activity.memberCountList = new ArrayList<>();
                            }
                            activity.pageAdapter.updateData();
                            if(isSelectStore){  //若取消或勾选复选框则
                                activity.campaignChartBeanList = activity.memberCountList;
                                activity.setCheckboxAdapter();

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
    public void queryMoreStoreMemTypeCountList(Context context, String storeId, String date,boolean isSelelctStore,boolean isSelelctAnalysis) {

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

        MemberAnalysisActivity activity = (MemberAnalysisActivity) view;
        countAnalysisManager.queryMorestoreMemTypeCountList(storeId,date)
                .subscribe(new BaseObserver<List<CompMember>>(context,false){
                    @Override
                    public void onSuccess(List<CompMember> members,String msg) {

                        if(members != null && members.size()>= 0){
                            activity.compMemberList =members;

                            activity.initRvAdapter();
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

        MemberAnalysisActivity activity = (MemberAnalysisActivity) view;
        countAnalysisManager.queryBrandAndStoreList(userId)
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
