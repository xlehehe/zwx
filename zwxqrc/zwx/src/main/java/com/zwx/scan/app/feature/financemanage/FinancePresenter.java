package com.zwx.scan.app.feature.financemanage;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;

import com.zwx.library.utils.DateUtils;
import com.zwx.library.utils.EncryptUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.data.base.BaseObserver;
import com.zwx.scan.app.data.bean.CampaginGrant;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.ReceiveFund;
import com.zwx.scan.app.data.bean.Resource;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.http.service.CampaignServiceManager;
import com.zwx.scan.app.data.http.service.FinanceServiceManager;
import com.zwx.scan.app.data.http.service.RetrofitServiceManager;
import com.zwx.scan.app.feature.campaign.CampaignListTwoActivity;
import com.zwx.scan.app.feature.ptmanage.PtManageActivity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
public class FinancePresenter implements FinanceContract.Presenter {


    private  FinanceContract.View view;
    private FinanceServiceManager financeServiceManager;

    //添加订阅，应用于取消订阅，防止造成内存泄漏
    private CompositeDisposable disposable;




    public FinancePresenter(FinanceContract.View view) {
        financeServiceManager = new FinanceServiceManager();
        this.view = view;
        disposable = new CompositeDisposable();
    }




    @Override
    public void queryBrandAndStoreList(Context context, String userId,boolean isSelectStore) {
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
        PayFeeManageActivity activity = (PayFeeManageActivity)context;
        financeServiceManager.queryStoreList(userId)
                .subscribe(new BaseObserver<List<Store>>(context,false){

                    @Override
                    public void onSuccess(List<Store> storeList,String msg) {
                        if(storeList != null  && storeList.size()>0){
                            activity.storeList = storeList;

                            if(isSelectStore){

                            }else {
                                activity.showPopListView();
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
    public void doQueryAllByStatus(Context context, String storeId, String pageNumber, String pageSize, String status,boolean isRefresh) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("storeId",storeId);
        params.put("pageNumber",pageNumber);
        params.put("pageSize",pageSize);
        params.put("status",status);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        PayFeeManageActivity activity = (PayFeeManageActivity) context;
        financeServiceManager.doQueryAllByStatus(storeId,pageNumber,pageSize,status)
                .subscribe(new BaseObserver<List<ReceiveFund>>(context,false){
                    @Override
                    public void onSuccess(List<ReceiveFund> receiveFundList, String msg) {

                        if (receiveFundList!=null && receiveFundList.size()>0) {
                            if (isRefresh) {
                                activity.receiveFundList.clear();
                                activity.receiveFundList.addAll(receiveFundList);

                                if (receiveFundList.size() < 10) {
//                                    activity.mAdapter = new PayFeeListViewAdapter(context,receiveFundList);
//                                    activity.rvList.setAdapter(activity.mAdapter);
//                                    activity.mAdapter.notifyDataSetChanged();
                                    activity.mAdapter.changetShowCb(status);
                                    activity.ptr.refreshComplete();
                                    activity.ptr.setLoadMoreEnable(false);
                                } else {
                                   /* activity.mAdapter = new PayFeeListViewAdapter(context,receiveFundList);
                                    activity.rvList.setAdapter(activity.mAdapter);*/
                                    activity.mAdapter.changetShowCb(status);
                                    activity.ptr.refreshComplete();
                                    activity.ptr.setLoadMoreEnable(true);
                                }
                            } else {
                                activity.receiveFundList.addAll(receiveFundList);
//                                activity.mAdapter = new PayFeeListViewAdapter(context,receiveFundList);
//                                activity.rvList.setAdapter(activity.mAdapter);
                                activity.mAdapter.notifyDataSetChanged();
                                activity.mAdapter.changetShowCb(status);
                                if (receiveFundList.size() < 10) {
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
                        }

                        String userId = SPUtils.getInstance().getString("userId");

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.ptr.refreshComplete();
                        activity.ptr.setLoadMoreEnable(false);
                    }
                });
    }

    @Override
    public void doQueryByFundId(Context context, String fundId) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("fundId",fundId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        PayFeeListDetailActivity activity = (PayFeeListDetailActivity) context;
        financeServiceManager.doQueryByFundId(fundId)
                .subscribe(new BaseObserver<ReceiveFund>(context,false){
                    @Override
                    public void onSuccess(ReceiveFund receiveFund, String msg) {

                        if(receiveFund != null){
                            activity.tv_pay_status.setText(receiveFund.getIsOverdue()!= null ?receiveFund.getIsOverdue():"");
                            String date = receiveFund.getPlanReceiveDate();
                            if(date != null && !"".equals(date)){
                                Date dateTime = DateUtils.parse(date,"yyyy-MM-dd");

                                activity.tv_pay_time.setText( DateUtils.formatDate(dateTime,"yyyy-MM-dd").replace("-","."));
                            }else {
                                activity.tv_pay_time.setText("");
                            }
                            activity.tv_pay_status.setText(receiveFund.getIsOverdue()!= null ?receiveFund.getIsOverdue():"");
                            activity.tv_pay_no.setText(receiveFund.getFundNo()!= null ?receiveFund.getFundNo():"");

                            activity.tv_contract_no.setText(receiveFund.getContractNo()!= null ?receiveFund.getContractNo():"");

                            activity.tv_contract_no.setText(receiveFund.getContractNo()!= null ?receiveFund.getContractNo():"");

                            BigDecimal money = receiveFund.getMoney();
                            String moneyPr = "0.00";
                            if (money != null && money.doubleValue() > 0) {

                                moneyPr = new DecimalFormat("0.00").format(money.setScale(2, BigDecimal.ROUND_DOWN).doubleValue()).toString();
                            } else {
                                moneyPr = "0.00";

                            }
                            moneyPr = "￥" + moneyPr;
//                            SpannableString spannableString  = new SpannableString(moneyPr);
//                            spannableString.setSpan(new AbsoluteSizeSpan(30, true), 0, price.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                            activity.tv_money.setText(moneyPr);
                            activity.contractId = String.valueOf(receiveFund.getContractId());
                            if("U".equals(activity.status)){
                                activity.tv_pay_status.setText("未交款");
                                activity.tv_pay_time_label.setText("最后交款时间：");
                            }else if("R".equals(activity.status)){
                                activity.tv_pay_status.setText("已付款");
                                activity.tv_pay_time_label.setText("交款时间：");
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
    public void doQueryMoneyByIds(Context context, String userId, String storeId,String ids) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("storeId",storeId);
        params.put("ids",ids);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        PayFeeManageActivity activity = (PayFeeManageActivity)context;
        financeServiceManager.doQueryMoneyByIds(userId,storeId,ids)
                .subscribe(new BaseObserver<Map<String,Object>>(context,false){

                    @Override
                    public void onSuccess(Map<String,Object> map,String msg) {

                        if(map != null && map.size()>0){
                            ToastUtils.showShort(map.toString());
                            String tn =(String)map.get("tn");
                            if(tn != null && !"".equals(tn)){

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
