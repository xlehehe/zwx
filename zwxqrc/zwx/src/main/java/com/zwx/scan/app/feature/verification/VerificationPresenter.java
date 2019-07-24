package com.zwx.scan.app.feature.verification;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.zwx.library.utils.EncryptUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TextColorSizeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.base.BaseObserver;
import com.zwx.scan.app.data.bean.MemCoupon;
import com.zwx.scan.app.data.bean.ResultBean;
import com.zwx.scan.app.data.bean.VerificationRecord;
import com.zwx.scan.app.data.http.service.RetrofitServiceManager;
import com.zwx.scan.app.data.http.service.VerificationServiceManager;
import com.zwx.scan.app.feature.verificationrecord.RecyclerDetailAdapter;
import com.zwx.scan.app.feature.verificationrecord.VerificationRecordActivity;
import com.zwx.scan.app.feature.verificationrecord.VerificationRecordDetailActivity;

import java.util.ArrayList;
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
public class VerificationPresenter implements VerificationContract.Presenter {

    private  VerificationContract.View view;
    private VerificationServiceManager scanServiceManager;

    //添加订阅，应用于取消订阅，防止造成内存泄漏
    private CompositeDisposable disposable;

    public VerificationPresenter(VerificationContract.View view) {
        this.view = view;

        scanServiceManager = new VerificationServiceManager();
        this.disposable = new CompositeDisposable();

    }
    @Override
    public void appVerificationMemberType(Context context, String storeId,
                                          String memberId, String memberType,
                                          String consumeAmt, String personQnt) {

        /*if(NetworkUtils.isNetworkConnected(context)){
            ToastUtils.showShort(R.string.network_error);
            return;
        }*/
        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        authMap.put("storeId",storeId);
        authMap.put("memberId",memberId);
        authMap.put("memberType",memberType);
        authMap.put("consumeAmt",consumeAmt);
        authMap.put("personQnt",personQnt);
        authMap.put("token",token );
        authMap.put("timestamp",timestamp);

        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        VerificationActivity activity = (VerificationActivity)context;
        scanServiceManager.appVerificationMemberType(storeId,memberId,memberType,consumeAmt,personQnt)
                .subscribe(new BaseObserver<ResultBean>(context,true){
                    @Override
                    public void onSuccess(ResultBean resultInfo,String msg) {
//                        activity.vibrate();
//                        activity.mZXingView.startSpot();
                        ToastUtils.showToastCenter("扫码成功");
                        activity.openRawMusic("1");
                        activity. mZXingView.startSpotDelay(5*1000);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showToastCenter(message);
                        activity. mZXingView.startSpotDelay(3000);
                    }
                });



    }

    @Override
    public void appVerificationMemCoupon(Context context, String userId, String storeId,String compMemberTypeId,String couponIdArray, String memberId,
                                          String consumeAmt, String personQnt) {
        VerificationActivity activity = (VerificationActivity)context;

        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        authMap.put("userId",userId);
        authMap.put("storeId",storeId);
        authMap.put("compMemberTypeId",compMemberTypeId);
        authMap.put("array",couponIdArray);
        authMap.put("memberId",memberId);
        authMap.put("consumeAmt",consumeAmt);
        authMap.put("personQnt",personQnt);

        authMap.put("token",token );
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        scanServiceManager.appVerificationMemCoupon(userId,storeId,compMemberTypeId,couponIdArray,memberId,consumeAmt,personQnt)
                .subscribe(new BaseObserver<String>(context,true){
                    @Override
                    public void onSuccess(String resultInfo,String msg) {

                        ToastUtils.showToastCenter("扫码成功");
                        activity.openRawMusic("1");
                        activity. mZXingView.startSpotDelay(3000);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                        ToastUtils.showToastCenter(message);
                        activity. mZXingView.startSpotDelay(3000);

                    }
                });
    }

    @Override
    public void queryVerificationRecord(Context context, String storeId, String compId, String date) {
        RetrofitServiceManager.setHeadTokenMap(null);

        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        authMap.put("compId",compId);
        authMap.put("storeId",storeId);
        authMap.put("date",date);
        authMap.put("token",token );
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        VerificationRecordActivity activity = (VerificationRecordActivity)context;
        scanServiceManager.queryVerificationRecord(storeId,compId,date)
                .subscribe(new BaseObserver<VerificationRecord>(activity,true){
                    @Override
                    public void onSuccess(VerificationRecord record,String msg) {
                        if(record != null){

                            String  penTotal = record.getPenVerificationTotal();
                            String total = record.getVerificationTotal();

                            int subTextColor = Color.parseColor("#4C94FC");
                            String[] subTextArray = {"桌"};
                            String[] subTextArray2 = {"张"};
                            if(penTotal != null&&!"0".equals(penTotal)){
                                penTotal = penTotal + "桌";
                                activity.tvCouponPen.setText(TextColorSizeUtils.getTextSpan(activity,subTextColor,30,penTotal,subTextArray));
                            }else {
                                penTotal = "-";
                                activity.tvCouponPen.setText(penTotal);
                            }
                            if(total != null && !"0".equals(total) ){
                                total = total + "张";
                                activity.tvCouponTotal.setText(TextColorSizeUtils.getTextSpan(activity,subTextColor,30,total,subTextArray2));
                            }else {
                                total = "-";
                                activity.tvCouponTotal.setText(total);
                            }
                            activity.tvDate.setText(record.getDate());
//                            activity.tvCouponPen.setText(penTotal);
//                            activity.tvCouponTotal.setText(total);
                            activity.verificationTotalReport = (ArrayList<Map<String,Object>>) record.getVerificationTotalReport();
                            activity.penVerificationTotalReport = (ArrayList<Map<String,Object>>)record.getPenVerificationTotalReport();
//                            activity.initChart();
                            /*activity.fragments.add(ChartFragment.getInstance("优惠券总核销数",activity.verificationTotalReport));
                            activity.fragments.add(ChartFragment.getInstance("核销桌数",activity.verificationTotalReport));
                            activity.pageAdapter= new ChartPagerAdapter(activity.getSupportFragmentManager());
                            activity.viewPager.setAdapter(activity.pageAdapter);*/

                           activity.mPagerAdapter.updateData(record);
                           queryVerificationList(activity,storeId,compId,date);


                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                    }
                });

    }

    @Override
    public void queryVerificationList(Context context, String storeId,String compId,String date) {

        RetrofitServiceManager.setHeadTokenMap(null);
        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        authMap.put("storeId",storeId);
        authMap.put("compId",compId);
        authMap.put("date",date);
        authMap.put("token",token );
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        VerificationRecordActivity activity = (VerificationRecordActivity)context;
        scanServiceManager.queryVerificationList(storeId,compId,date)
                .subscribe(new BaseObserver<List<MemCoupon>>(activity,false){
                    @Override
                    public void onSuccess(List<MemCoupon> record,String msg) {
                        if(record != null && record.size()>0){
                            activity.memCouponList= record;
                            activity.ll_no_data.setVisibility(View.GONE);
                            activity.rvVerificationTotal.setVisibility(View.VISIBLE);
                        }else {
                            activity.memCouponList.clear();
                            activity.ll_no_data.setVisibility(View.VISIBLE);
                            activity.rvVerificationTotal.setVisibility(View.GONE);
                            activity.iv_no_data.setImageResource(R.drawable.ic_no_data_tip);
                            activity.tv_no_data.setText("暂无优惠券核销记录");
                        }
                        activity.setDataAdapter();

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.ll_no_data.setVisibility(View.VISIBLE);
                        activity.rvVerificationTotal.setVisibility(View.GONE);
                        activity.iv_no_data.setImageResource(R.drawable.ic_wifi_not);
                        activity.tv_no_data.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void queryVerificationDetail(Context context, String storeId, int pageSize, int pageNumber, String date,  boolean isRereshAndMore) {
        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        authMap.put("storeId",storeId);
        authMap.put("pageSize",String.valueOf(pageSize));
        authMap.put("pageNumber",String.valueOf(pageNumber));
        authMap.put("date",date);
        authMap.put("token",token );
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        VerificationRecordDetailActivity activity = (VerificationRecordDetailActivity)context;
        scanServiceManager.queryVerificationDetail(storeId,pageSize,pageNumber,date)
                .subscribe(new BaseObserver<List<MemCoupon>>(context,false){
                    @Override
                    public void onSuccess(List<MemCoupon> record,String msg) {
                        if(record != null && record.size()>0){


                            if(isRereshAndMore){
                                activity.memCouponList.clear();
                                activity.memCouponList.addAll(record);
                                activity.adapter.notifyDataSetChanged();

                                if (record.size() < 10) {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                } else {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }


                            }else {
                                activity.memCouponList.addAll(record);
                                activity.adapter.notifyDataSetChanged();
                                if (record.size() < 10) {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                } else {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }
                            }

                            activity.ll_no_data.setVisibility(View.GONE);
                            activity.rvList.setVisibility(View.VISIBLE);

                        }else {

                            if(activity.memCouponList != null && activity.memCouponList.size()>0){
                                if (activity.memCouponList.size() < 10) {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                } else {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }
                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.rvList.setVisibility(View.VISIBLE);
                            }else {
                                activity.adapter.notifyDataSetChanged();
                                activity.rvList.setVisibility(View.GONE);
                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.tv_no_data.setText("暂无优惠券核销记录！");
                            }

                        }
                        activity.ptr.onRefreshComplete();

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.ptr.onRefreshComplete();
                        activity.adapter.notifyDataSetChanged();
                        activity.rvList.setVisibility(View.GONE);
                        activity.ll_no_data.setVisibility(View.VISIBLE);
                        activity.tv_no_data.setText("暂无优惠券核销记录！");
                    }
                });
    }

    /**
     *检查是否当前品牌的会员
     * */
    @Override
    public void checkIsBrandMember(VerificationActivity activity, String storeId, String memberId) {
//        VerificationActivity activity = (VerificationActivity)context;
        RetrofitServiceManager.setHeadTokenMap(null);
        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        authMap.put("storeId",storeId);
        authMap.put("memberId",memberId);
        authMap.put("token",token );
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        scanServiceManager.checkIsBrandMember(storeId,memberId)
                .subscribe(new BaseObserver<ResultBean>(activity,false){
                    @Override
                    public void onSuccess(ResultBean resultBean,String msg) {

                        activity.setSuccessDialog(memberId);
                        activity. mZXingView.startSpotDelay(5000);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        String tip = "";
                        String storeName = SPUtils.getInstance().getString("storeName");
                        if(code == 2){
                            tip = "该消费者，是 "+'"'+"<font color=\'#0486FE\'>"+storeName+"</font>"+'"'+" 所在品牌会员，但已经是在职员工";
                        }else if(code == 3){
                            tip = "该消费者，还未成为 "+'"'+"<font color=\'#0486FE\'>"+storeName+"</font>"+'"'+" 所在品牌的会员，请加入后再进行操作";

                        }
                        activity.setFailDialog(tip);

                        activity. mZXingView.startSpotDelay(5000);
                    }
                });

    }

    @Override
    public void verificationProduct(Context context, String storeId, String memberId, String compMemId,String orderCouponId, String compMemberTypeId) {
        VerificationActivity activity = (VerificationActivity)context;

        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());


        authMap.put("storeId",storeId);
        authMap.put("memberId",memberId);
        authMap.put("compMemId",compMemId);
        authMap.put("orderCouponId",orderCouponId);
        authMap.put("compMemberTypeId",compMemberTypeId);
        authMap.put("token",token );
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
//        activity. mZXingView.stopSpot();
        scanServiceManager.verificationProduct(storeId,memberId,compMemId,orderCouponId,compMemberTypeId)
                .subscribe(new BaseObserver<String>(context,true){
                    @Override
                    public void onSuccess(String resultInfo,String msg) {

                        ToastUtils.showToastCenter("扫码成功");
                        activity.openRawMusic("1");
                        activity. mZXingView.startSpotDelay(3*1000);
                        return;
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                        ToastUtils.showToastCenter(message);
                        activity. mZXingView.startSpotDelay(3000);
                        return;
                    }
                });
    }

    @Override
    public void productVerification(Context context, String storeId, String memberId, String compMemId, String orderCouponId, String compMemberTypeId) {
        ProductVerificationActivity activity = (ProductVerificationActivity)context;

        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());


        authMap.put("storeId",storeId);
        authMap.put("memberId",memberId);
        authMap.put("compMemId",compMemId);
        authMap.put("orderCouponId",orderCouponId);
        authMap.put("compMemberTypeId",compMemberTypeId);
        authMap.put("token",token );
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
//        activity. mZXingView.stopSpot();
        scanServiceManager.verificationProduct(storeId,memberId,compMemId,orderCouponId,compMemberTypeId)
                .subscribe(new BaseObserver<String>(context,true){
                    @Override
                    public void onSuccess(String resultInfo,String msg) {

                        ToastUtils.showToastCenter("核销完成");
                        activity.openRawMusic("1");
                        activity. mZXingView.startSpotDelay(3*1000);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                        ToastUtils.showToastCenter(message);
                        activity. mZXingView.startSpotDelay(3000);
                    }
                });
    }

    @Override
    public void verificationPlatCoupon(Context context, String storeId, String memberId, String compMemId, String orderCouponId, String compMemberTypeId, String type) {
        VerificationActivity activity = (VerificationActivity)context;

        Map<String,String> authMap = new HashMap<String,String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());


        authMap.put("storeId",storeId);
        authMap.put("memberId",memberId);
        authMap.put("compMemId","");
        authMap.put("orderCouponId",orderCouponId);
        authMap.put("compMemberTypeId",compMemberTypeId);
        authMap.put("orderCouponType",type);
        authMap.put("token",token );
        authMap.put("timestamp",timestamp);
        String signature = EncryptUtils.createSign(authMap);
        authMap = new HashMap<String,String>();

        authMap.put("token",token);
        authMap.put("timestamp",timestamp);
        authMap.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(authMap);
        scanServiceManager.verificationPlatProduct(storeId,memberId,"",orderCouponId,compMemberTypeId,type)
                .subscribe(new BaseObserver<String>(context,true){
                    @Override
                    public void onSuccess(String resultInfo,String msg) {

                        ToastUtils.showToastCenter("核销完成");
                        activity.openRawMusic("1");
                        activity. mZXingView.startSpotDelay(3*1000);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                        ToastUtils.showToastCenter(message);
                        activity. mZXingView.startSpotDelay(3000);
                    }
                });
    }
}
