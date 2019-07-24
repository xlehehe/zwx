package com.zwx.scan.app.feature.financialaffairs;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.luck.picture.lib.entity.LocalMedia;
import com.zwx.library.pickerview.wheelview.view.WheelView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.DateUtils;
import com.zwx.library.utils.EncryptUtils;
import com.zwx.library.utils.LogUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.base.BaseObserver;
import com.zwx.scan.app.data.bean.CampaignSaveResult;
import com.zwx.scan.app.data.bean.CardBean;
import com.zwx.scan.app.data.bean.CollectionFlowDetailResultBean;
import com.zwx.scan.app.data.bean.CollectionFlowResultBean;
import com.zwx.scan.app.data.bean.CollectionToAccountOrderDetailBean;
import com.zwx.scan.app.data.bean.CompWxpayTransfer;
import com.zwx.scan.app.data.bean.CompWxpayTransferBean;
import com.zwx.scan.app.data.bean.MemberRedEnvelope;
import com.zwx.scan.app.data.bean.MemberRedEnvelopeBean;
import com.zwx.scan.app.data.bean.MemberRedEnvelopeDetailBean;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.OrderObjectBean;
import com.zwx.scan.app.data.bean.ProductCategory;
import com.zwx.scan.app.data.bean.ProductMedia;
import com.zwx.scan.app.data.bean.ProductResultBean;
import com.zwx.scan.app.data.bean.ProductSetNew;
import com.zwx.scan.app.data.bean.ProductSetNewBean;
import com.zwx.scan.app.data.bean.ProductStore;
import com.zwx.scan.app.data.bean.ProductVerifictionRecordBean;
import com.zwx.scan.app.data.bean.RedEnvelopeDetail;
import com.zwx.scan.app.data.bean.ShopResultBean;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.TOrder;
import com.zwx.scan.app.data.bean.TOrderBean;
import com.zwx.scan.app.data.bean.TOrderConsumeLog;
import com.zwx.scan.app.data.bean.TOrderConsumeLogBean;
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.data.bean.TOrderPay;
import com.zwx.scan.app.data.bean.TradeDrawingRecordResultBean;
import com.zwx.scan.app.data.bean.TradeDrawingSelectResultBean;
import com.zwx.scan.app.data.bean.TransferRecordBean;
import com.zwx.scan.app.data.bean.User;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.data.http.service.CampaignServiceManager;
import com.zwx.scan.app.data.http.service.FinancialAffairsService;
import com.zwx.scan.app.data.http.service.FinancialAffairsServiceManage;
import com.zwx.scan.app.data.http.service.PayAuthServiceManage;
import com.zwx.scan.app.data.http.service.RetrofitServiceManager;
import com.zwx.scan.app.data.http.service.ShopServiceManage;
import com.zwx.scan.app.feature.campaign.CampaignSelectStoreAdapter;
import com.zwx.scan.app.feature.member.MemberCardStreamActivity;
import com.zwx.scan.app.feature.personal.PersonalThreeFragment;
import com.zwx.scan.app.feature.personal.TradeDrawingPersonalAuthTwoActivity;
import com.zwx.scan.app.feature.ptmanage.PtManageActivity;
import com.zwx.scan.app.feature.ptmanage.PtNewActivity;
import com.zwx.scan.app.feature.ptmanage.PtNextSettingFragment;
import com.zwx.scan.app.feature.ptmanage.PtNextThreeActivity;
import com.zwx.scan.app.feature.shop.ProductVerificationRecordActivity;
import com.zwx.scan.app.feature.shop.ProductVerificationRecordDetailActivity;
import com.zwx.scan.app.feature.shop.RedEnvelopeActivity;
import com.zwx.scan.app.feature.shop.RedEnvelopeDetailActivity;
import com.zwx.scan.app.feature.shop.SearchRedEnvelopeListActivity;
import com.zwx.scan.app.feature.shop.ShopClassFragment;
import com.zwx.scan.app.feature.shop.ShopManageActivity;
import com.zwx.scan.app.feature.shop.ShopManageNewActivity;
import com.zwx.scan.app.feature.shop.ShopOrderActivity;
import com.zwx.scan.app.feature.shop.ShopOrderDetailActivity;
import com.zwx.scan.app.feature.shop.ShopOrderDetailAdapter;
import com.zwx.scan.app.feature.shop.ShopTelFragment;
import com.zwx.scan.app.feature.shop.ShopTelListAdapter;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * author : lizhilong
 * time   : 2019/05/09
 * desc   :  财务中心 接口实现
 * version: 1.0
 **/
public class FinancialAffairsPresenter implements FinancialAffairsContract.Presenter {

    private FinancialAffairsContract.View view;
    //添加订阅，应用于取消订阅，防止造成内存泄漏
    private CompositeDisposable disposable;
    private PayAuthServiceManage payAuthServiceManage;
    // 2019/05/24 新增
    private FinancialAffairsServiceManage financialAffairsServiceManage;
    private CampaignServiceManager campaignServiceManager;
    public FinancialAffairsPresenter(FinancialAffairsContract.View view) {
        this.view = view;
        disposable = new CompositeDisposable();
        payAuthServiceManage  = new PayAuthServiceManage();

        financialAffairsServiceManage = new FinancialAffairsServiceManage();

        campaignServiceManager = new CampaignServiceManager();
    }

    @Override
    public void selectTradeWithdrawal(Context context, String userId) {


        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("userId", userId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        TradeDrawingActivity activity = (TradeDrawingActivity)context;
        payAuthServiceManage.selectTradeWithdrawal(userId)
                .subscribe(new BaseObserver<Map<String,String>>(context, true) {
                    @Override
                    public void onSuccess(Map<String,String> result, String msg) {


                        LogUtils.e(result.toString());

                        if(result != null &&  result.size()>0){
                            activity.unSettlement = result.get("unSettlement");
                            activity.amount = result.get("amount");

                            if(activity.unSettlement != null && !"".equals(activity.unSettlement)){
                                activity.tv_amt.setText(activity.unSettlement);
                            }else {
                                activity.tv_drawal_amt.setText("");
                            }

                            if(activity.amount != null && !"".equals(activity.amount)){
                                activity.tv_drawal_amt.setText(activity.amount);
                            }else {
                                activity.tv_drawal_amt.setText("");
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
        TradeDrawingActivity activity =  (TradeDrawingActivity) context;
        payAuthServiceManage.selectPaymentAuthStatus(userId)
                .subscribe(new BaseObserver<Map<String,Object>>(context,false){
                    @Override
                    public void onSuccess(Map<String,Object> result,String msg) {

                        // "process":"N","status":"N"
                        String statusStr = "";


                        activity.status = (String)result.get("status");

                        if("A".equals(activity.status)){ //"已认证"
                            ActivityUtils.startActivity(FinancialDrawingActivity.class,R.anim.slide_in_right,R.anim.slide_out_left);
                        }else if(" NA".equals(activity.status)||"N".equals(activity.status)){ //"未完成" 、"未认证"
                            activity.setDialog("交易提款前，请先完成支付认证。");
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
    public void sendVerificationCodeByImmediateWithdrawal(Context context,String  userId,
                                                          String amount,String fee,String bankCardNo,String bankCardPro){


        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("amount",amount);
        params.put("fee",fee);
        params.put("bankCardNo",bankCardNo);
        params.put("bankCardPro",bankCardPro);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        payAuthServiceManage.sendVerificationCodeByImmediateWithdrawal(userId,amount,fee,bankCardNo,bankCardPro)
                .subscribe(new BaseObserver<Map<String,String>>(context,true){
                    @Override
                    public void onSuccess(Map<String,String> result,String msg) {


                        /**
                         *
                         * {"code":"1","message":"操作成功！","result":{"compWithdraw":{"accountName":"紧迫啥","amount":0.01,"bankCardno":"6228483333333333333","bankName":"农业银行","compId":281616084303872,"compIdentityId":404593170776064,"createName":"",
                         * "createTel":"","createTime":null,"createUser":0,"fee":0.00,"id":411826821021696,"industryCode":"2311",
                         * "industryName":"食品饮料","managerName":"操","managerTel":"18633333344","orderNo":"1130749125088780288","phone":"18644444444","source":"1","status":"","type":"0","withdrawType":"D0"},"k":0,"status":"OK"}}
                         * */
                        String resultStatus = result.get("status");
                        String errorMessage = result.get("errorMessage");

                        FinancialDrawingActivity activity =  (FinancialDrawingActivity) context;
                        if("OK".equals(resultStatus)){
                            activity.bizOrderNo = result.get("id");
                        }else {
                            ToastUtils.showShort(errorMessage);
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);

                    }
                });
    }

    //立即提款查询
    @Override
    public void selectImmediateWithdrawal(Context context, String userId) {
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
        FinancialDrawingActivity activity =  (FinancialDrawingActivity) context;
        payAuthServiceManage.selectImmediateWithdrawal(userId)
                .subscribe(new BaseObserver<TradeDrawingSelectResultBean>(context,false){
                    @Override
                    public void onSuccess(TradeDrawingSelectResultBean result,String msg) {

                        // "process":"N","status":"N"

                        /**
                         *  {"code":"1","message":"查询成功！","result":{"amount":0.01,"compIdentity":{"bankCode":"01030000","bankName":"农业银行","cardNo":"6228483333333333333","cardType":"1","compId":281616084303872,"contractNo":"643210","id":404593170776064,"identityName":"紧迫啥",
                         *  "identityType":"1","legalId":"124576797976466494","managerName":"操","managerTel":"18633333344","phone":"18644444444","status":"0","type":"0"},"k":1,
                         *  "user":{"authFlag":"","id":13281618631442432,"nickname":"砸金蛋","password":"cecce8f767a5914c24197152f345e43c","realname":"","roleIds":"","roleNames":"","salt":"3e3e80af23d5d350c37edbc33eae4feb","signature":"","status":1,"storeIds":"","storeNames":"",
                         *  "timestamp":"","token":"","type":2,"userId":"13281618631442432","username":"test01"},"status":"OK"}}
                         * */
                        LogUtils.e(result.toString());

                        if(result != null){

                            activity.amount = result.getAmount();
                            activity.fee = result.getFee();
                            if(activity.fee == null || "".equals(activity.fee)||activity.fee == ""){
                                activity.fee = "0.00";
                            }
                            if(activity.amount != null && !"".equals(activity.amount)){
                                activity.edt_draw_amt.setText(activity.amount);

                                if(activity.fee != null && !"".equals(activity.fee)){
                                    if("0.00".equals(activity.fee)){
                                        activity.tv_fee.setText("0.00");
                                    }else {
                                        activity.tv_fee.setText(RegexUtils.getDoubleString(Double.parseDouble(activity.fee)));
                                    }

                                }else {
                                    activity.fee = "0.00";
                                    activity.tv_fee.setText(activity.fee);
                                }

                                Double am = Double.parseDouble(activity.amount);
                                Double fe = Double.parseDouble(activity.fee);
                                Double toAm = am.doubleValue() - fe.doubleValue();
                                activity.tv_daozhang_amt.setText(RegexUtils.getDoubleString(toAm));
                            }else {
                                activity.edt_draw_amt.setText("0");
                                if(activity.fee != null && !"".equals(activity.fee)){
                                    if("0".equals(activity.fee)){
                                        activity.tv_fee.setText("0.00");
                                    }else {
                                        activity.tv_fee.setText(RegexUtils.getDoubleString(Double.parseDouble(activity.fee)));
                                    }

                                }else {
                                    activity.fee = "0.00";
                                    activity.tv_fee.setText("0.00");
                                }
                            }

                          /*  if(activity.fee != null && !"".equals(activity.fee)){
                                if("0".equals(activity.fee)){
                                    activity.tv_fee.setText("0.00");
                                }else {
                                    activity.tv_fee.setText(RegexUtils.getDoubleString(Double.parseDouble(activity.fee)));
                                }

                            }else {
                                activity.tv_fee.setText("0.00");
                            }*/



                            TradeDrawingSelectResultBean.PayAuthInfoBean  payAuthInfoBean  = result.getCompIdentity();
                            User user = result.getUser();

                            if(user != null){
                                activity.username  = user.getUsername();
                                activity.nickname = user.getNickname();


                                if(activity.username != null && !"".equals(activity.username)){
                                    activity.tv_operate_tel.setText(activity.username);
                                }

                                if(activity.nickname != null && !"".equals(activity.nickname)){
                                    activity.tv_operate_person.setText(activity.nickname);
                                }
                            }
                            if(payAuthInfoBean != null){
                                activity.bankName = payAuthInfoBean.getBankName();
                                activity.bankNo = payAuthInfoBean.getCardNo();
                                activity.bankCardPro = payAuthInfoBean.getType();//  //账户类型: 0个人银行卡 ,1企业对公账户

                                activity.managerTel = payAuthInfoBean.getManagerTel();
                                activity.managerTName = payAuthInfoBean.getManagerName();


                                if(activity.bankName != null && !"".equals(activity.bankName)){
                                    activity.tv_account_name.setText(activity.bankName);
                                }

                                if(activity.bankNo != null && !"".equals(activity.bankNo)){
                                    activity.tv_account.setText(activity.bankNo);
                                }

                                if(activity.managerTel != null && !"".equals(activity.managerTel)){
                                    activity.tv_fuze_phone.setText(activity.managerTel);
                                }

                                if(activity.managerTName != null && !"".equals(activity.managerTName)){
                                    activity.tv_fuze_person.setText(activity.managerTName);
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
    public void selectTradeWithdrawalDetails(String userId, String withdrawId) {

    }

    //提现记录
    @Override
    public void selectTradeWithdrawalList(Context context, String userId, String withdrawTimeSta, String withdrawTimesEnd, Integer pageNumber, Integer pageSize) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("userId", userId);
        params.put("withdrawTimeSta", withdrawTimeSta);
        params.put("withdrawTimesEnd", withdrawTimesEnd);

        params.put("pageSize", String.valueOf(pageSize));
        params.put("pageNumber", String.valueOf(pageNumber));
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        TradeDrawingRecordActivity activity = (TradeDrawingRecordActivity) context;
        payAuthServiceManage.selectTradeWithdrawalList(userId, withdrawTimeSta, withdrawTimesEnd, pageNumber, pageSize)
                .subscribe(new BaseObserver<TradeDrawingRecordResultBean>(context, true) {
                    @Override
                    public void onSuccess(TradeDrawingRecordResultBean result, String msg) {

                        /***
                         *
                         *{
                         "code": "1",
                         "message": "查询成功！",
                         "result": {
                         "endRow": 2,
                         "firstPage": 1,
                         "hasNextPage": false,
                         "hasPreviousPage": false,
                         "isFirstPage": true,
                         "isLastPage": true,
                         "lastPage": 1,"list": [{
                         "accountName": "紧迫啥",
                         "amount": 0.01,
                         "bankCardno": "6228483333333333333",
                         "bankName": "农业银行",
                         "compId": 281616084303872,
                         "compIdentityId": 404593170776064,
                         "createName": "砸金蛋",
                         "createTel": "test01",
                         "createTime": "2019-05-21 16:58:54",
                         "createUser": 13281618631442432,
                         "fee": 0.00,
                         "id": 411869441507328,
                         "industryCode": "2311",
                         "industryName": "食品饮料",
                         "managerName": "操",
                         "managerTel": "18633333344",
                         "orderNo": "1130760036050542592",
                         "phone": "18644444444",
                         "source": "1",
                         "status": "1",
                         "type": "0",
                         "withdrawType": "D0"
                         }, {
                         "accountName": "紧迫啥",
                         "amount": 0.01,
                         "bankCardno": "6228483333333333333",
                         "bankName": "农业银行",
                         "compId": 281616084303872,
                         "compIdentityId": 404593170776064,
                         "createName": "砸金蛋",
                         "createTel": "test01",
                         "createTime": "2019-05-21 16:56:23",
                         "createUser": 13281618631442432,
                         "fee": 0.00,
                         "id": 411866947010560,
                         "industryCode": "2311",
                         "industryName": "食品饮料",
                         "managerName": "操",
                         "managerTel": "18633333344",
                         "orderNo": "1130759398424059904",
                         "phone": "18644444444",
                         "source": "1",
                         "status": "1",
                         "type": "0",
                         "withdrawType": "D0"
                         }],
                         "navigateFirstPage": 1,
                         "navigateLastPage": 1,
                         "navigatePages": 8,
                         "navigatepageNums": [1],
                         "nextPage": 0,
                         "pageNum": 1,
                         "pageSize": 10,
                         "pages": 1,
                         "prePage": 0,
                         "size": 2,
                         "startRow": 1,
                         "total": 2
                         }
                         }
                         *
                         * */
                        if (result != null) {
                            List<TradeDrawingRecordResultBean.DrawingRecordBean> dataList = result.getList();
                            if (dataList != null && dataList.size() > 0) {

                                for(int i=0;i<dataList.size();i++){
                                    if(dataList.get(i) != null){
                                        if(dataList.get(i).getId() != null){
                                            String detailedId = String.valueOf(dataList.get(i).getId());
                                            if(activity.recordBeanList != null &&  activity.recordBeanList.size()>0){
                                                for(int j=0;j<activity.recordBeanList.size();j++){
                                                    if(detailedId.equals(String.valueOf(activity.recordBeanList.get(j).getId()))){
                                                        activity.recordBeanList.remove(j);
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                                activity.recordBeanList.addAll(dataList);
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
                                activity.recordBeanList.addAll(dataList);
                                activity.listAdapter.notifyDataSetChanged();

                                if(activity.recordBeanList != null  &&  activity.recordBeanList.size()>0){
                                    if(activity.recordBeanList.size() < 10){
                                        activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                    }else {
                                        activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                    }

                                    activity.ll_no_data.setVisibility(View.GONE);
                                    activity.listView.setVisibility(View.VISIBLE);
                                }else {

                                    activity.ll_no_data.setVisibility(View.VISIBLE);
                                    activity.listView.setVisibility(View.GONE);
                                    activity.tv_no_data.setText("您现在没有提现记录哦！");
                                }


                            }
                            activity.ptr.onRefreshComplete();
                        }else {
                            if(activity.recordBeanList != null  &&  activity.recordBeanList.size()>0){
                                activity.listAdapter.notifyDataSetChanged();
                                if(activity.recordBeanList.size() < 10){
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                }else {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }
                                activity.ptr.onRefreshComplete();
                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.listView.setVisibility(View.VISIBLE);
                            }else {

                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.listView.setVisibility(View.GONE);
                                activity.tv_no_data.setText("您现在没有提现记录哦！");
                            }
                        }

                        activity.ptr.onRefreshComplete();

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

//                        activity.orderList.clear();
//                        activity.listAdapter.notifyDataSetChanged();
                        activity.ptr.onRefreshComplete();
                        activity.ll_no_data.setVisibility(View.VISIBLE);
                        activity.listView.setVisibility(View.GONE);
                        activity.tv_no_data.setText("您现在没有提现记录哦！");

                    }
                });

    }



    @Override
    public void selectTradeWithdrawalDetails(Context context, String userId, String withdrawId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("withdrawId",withdrawId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        FinancialDrawingDetailActivity activity =  (FinancialDrawingDetailActivity) context;
        payAuthServiceManage.selectTradeWithdrawalDetails(userId,withdrawId)
                .subscribe(new BaseObserver<Map<String,String>>(context,true){
                    @Override
                    public void onSuccess(Map<String,String> result,String msg) {


                        /**
                         *  {"code":"1","message":"查询成功！","result":{"accountName":"紧迫啥","amount":0.01,"bankCardno":"6228483333333333333","bankName":"农业银行","compId":281616084303872,"compIdentityId":404593170776064,"createName":"砸金蛋",
                         *  "createTel":"test01","createTime":"2019-05-21 16:58:54","createUser":13281618631442432,"fee":0.00,"id":411869441507328,"industryCode":"2311",
                         *  "industryName":"食品饮料","managerName":"操","managerTel":"18633333344","orderNo":"1130760036050542592","phone":"18644444444","source":"1","status":"1","type":"0","withdrawType":"D0"}}
                         *
                         * */


                        if(result != null && !"".equals(result)){
                            activity.amount = result.get("amount");

                            activity.fee = result.get("fee");

                            if(activity.amount != null && !"".equals(activity.amount)){
                                Double am  = Double.parseDouble(activity.amount);
                                if(activity.fee != null && !"".equals(activity.fee)){
                                    Double fe = Double.parseDouble(activity.fee);

                                    Double toAm = am.doubleValue() - fe.doubleValue();
                                    activity.toAmt = String.valueOf(toAm);
                                    activity.tv_daozhang_amt.setText(activity.toAmt);
                                }else {
                                    activity.fee = "0.00";
                                }


                            }else {
                                activity.amount = "";
                                if(activity.fee != null && !"".equals(activity.fee)){

                                }else {
                                    activity.fee = "0.00";
                                }
                            }
                            activity.tv_fee.setText(activity.fee);
                            activity.tv_draw_amt.setText(activity.amount);


                            activity.createTime = result.get("createTime");

                            if(activity.createTime != null && !"".equals(activity.createTime)){
                                activity.tv_draw_time.setText(activity.createTime.replace("-","."));
                            }

                            activity.bankName = result.get("bankName");
                            if(activity.bankName != null && !"".equals(activity.bankName)){
                                activity.tv_account_name.setText(activity.bankName);
                            }

                            activity.bankCardno = result.get("bankCardno");

                            if(activity.bankCardno != null && !"".equals(activity.bankCardno)){
                                activity.tv_account.setText(activity.bankCardno);
                            }

                            activity.createName = result.get("createName");

                            if(activity.createName != null && !"".equals(activity.createName)){
                                activity.tv_operate_person.setText(activity.createName);
                            }

                            activity.createTel = result.get("createTel");
                            if(activity.createTel != null && !"".equals(activity.createTel)){
                                activity.tv_operate_tel.setText(activity.createTel);
                            }
                            activity.managerTel = result.get("managerTel");

                            if(activity.managerTel != null && !"".equals(activity.managerTel)){
                                activity.tv_fuze_phone.setText(activity.managerTel);
                            }

                            activity.managerName = result.get("managerName");

                            if(activity.managerName != null && !"".equals(activity.managerName)){
                                activity.tv_fuze_person.setText(activity.managerName);
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
    public void immediateWithdrawal(Context context, String userId, String bizOrderNo, String validateType, String consumerIp) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("bizOrderNo",bizOrderNo);
        params.put("validateType",validateType);
        params.put("consumerIp",consumerIp);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        FinancialDrawingActivity activity =  (FinancialDrawingActivity) context;
        payAuthServiceManage.immediateWithdrawal(userId,bizOrderNo,validateType,consumerIp)
                .subscribe(new BaseObserver<Map<String,String>>(context,false){
                    @Override
                    public void onSuccess(Map<String,String> result,String msg) {

                        String status = (String)result.get("status");

                        if("OK".equals(status)){ //"已认证"
                            Intent intent = new Intent(context,FinancialDrawingDetailActivity.class);
                            intent.putExtra("withdrawId",bizOrderNo);
                            ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                        }else {
                            activity.setDialog(msg);
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);

                    }
                });
    }

    //待结算收款流水
    @Override
    public void selectUnSettlementCollectionStatementList(Context context, String userId, Integer pageNumber, Integer pageSize) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
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
        CollectionFlowListActivity activity =  (CollectionFlowListActivity) context;
        payAuthServiceManage.selectUnSettlementCollectionStatementList(userId,pageNumber,pageSize)
                .subscribe(new BaseObserver<CollectionFlowResultBean>(context,true){
                    @Override
                    public void onSuccess(CollectionFlowResultBean result,String msg) {


                        /**
                         * "list":[{"amount":10.00,"brandId":0,"buyCount":1,"buyMsg":"","compId":0,"coun":0,"delivType":"","fromId":"","fromType":"","memberId":0,"memberName":"","memberTel":"","orderCode":201905220101,
                         * "orderId":1,"orderState":"","orderType":"P","orderTypeName":"测试商品1","orderTypes":"","payChannel":"tl","productCode":"","productId":0,"productIds":"","recommendMemberName":"",
                         * "recommendMemberTel":"","salesTime":"2019-05-22 10:59:50","staffId":0,
                         * "staffName":"","staffTel":"","storeId":0,"storeName":"","unitPrice":0,"useCash":0,"useCashAmount":0,"useRedEnvelope":0,"useRedEnvelopeAmount":10.00}]
                         * */

                        if(result != null ){
                            List<CollectionFlowResultBean.CollectionFlowBean> dataList = result.getList();

                            if (dataList != null && dataList.size() > 0) {
                                activity.collectionFlowBeanList.addAll(dataList);
                                activity.adapter.notifyDataSetChanged();
                                activity.ptr.setPullToRefreshOverScrollEnabled(true);
                                if (dataList.size() < 10) {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                } else {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }
                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.listView.setVisibility(View.VISIBLE);

                            } else {
                                activity.collectionFlowBeanList.addAll(dataList);
                                activity.adapter.notifyDataSetChanged();

                                if(activity.collectionFlowBeanList != null  &&  activity.collectionFlowBeanList.size()>0){
                                    if(activity.collectionFlowBeanList.size() < 10){
                                        activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                    }else {
                                        activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                    }

                                    activity.ll_no_data.setVisibility(View.GONE);
                                    activity.listView.setVisibility(View.VISIBLE);
                                }else {

                                    activity.ll_no_data.setVisibility(View.VISIBLE);
                                    activity.listView.setVisibility(View.GONE);
                                    activity.tv_no_data.setText("您现在没有待结算收款流水记录哦！");
                                }


                            }
                            activity.ptr.onRefreshComplete();

                        }else {
                            if(activity.collectionFlowBeanList != null  &&  activity.collectionFlowBeanList.size()>0){
                                activity.adapter.notifyDataSetChanged();
                                if(activity.collectionFlowBeanList.size() < 10){
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                }else {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }
                                activity.ptr.onRefreshComplete();
                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.listView.setVisibility(View.VISIBLE);
                            }else {

                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.listView.setVisibility(View.GONE);
                                activity.tv_no_data.setText("您现在没有待结算收款流水记录哦！");
                            }
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);
                        activity.ll_no_data.setVisibility(View.VISIBLE);
                        activity.listView.setVisibility(View.GONE);
                        activity.tv_no_data.setText("您现在没有待结算收款流水记录哦！");

                    }
                });
    }

    @Override
    public void selectCollectionStatementDetailsByType(Context context, String userId, String orderId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("orderId",orderId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        TradeDrawingCollectionFlowDetailActivity activity =  (TradeDrawingCollectionFlowDetailActivity) context;
        payAuthServiceManage.selectCollectionStatementDetailsByType(userId,orderId)
                .subscribe(new BaseObserver<CollectionFlowDetailResultBean>(context,true){
                    @Override
                    public void onSuccess(CollectionFlowDetailResultBean result,String msg) {

                        /***
                         * {"code":"1","message":"查询成功！","result":{"orderDetails":{"amount":10.00,"brandId":0,"buyCount":0,"buyMsg":"","compId":0,"coun":0,"delivType":"",
                         * "fromId":"","fromType":"","memberId":0,"memberName":"","memberTel":"","orderCode":201905220101,"orderId":1,"orderState":"","orderType":"P","orderTypeName":"","orderTypes":"",
                         * "payChannel":"","productCode":"","productId":1,"productIds":"","recommendMemberName":"","recommendMemberTel":"","salesTime":"2019-05-22 10:59:50","staffId":0,"staffName":"",
                         * "staffTel":"","storeId":0,"storeName":"","unitPrice":0,"useCash":0,"useCashAmount":0,"useRedEnvelope":0,"useRedEnvelopeAmount":10.00},
                         *
                         *
                         * "purchaseDetails":[{"buyCount":1,"catId":0,"catName":"","compId":0,"cou":0,"deliveryFee":0,"detailedId":0,"factSalesPrice":"","getRedEnvelope":0,"marketPrice":0,"orderId":0,
                         * "payWay":"","productCode":"G00001","productId":0,"productName":"测试商品1","productSpec":"盘/份","salesType":"","status":"","thumbnailUrl":"","unitPrice":20.00,
                         * "unitScore":0,"useRedEnvelope":0}],"orderPayDetails":[{"compId":0,"id":0,"orderId":0,"payAmount":10.00,"payId":"","payTime":"20190521200620","payWay":"r","remark":"","result":""}],
                         * "paymentDetails":{"bankType":"","cashFee":10.00,"cashFeeType":"","feeType":"","orderId":0,"outTradeNo":"111111111","payChannel":"tl","paymentId":0,"subMchId":"",
                         * "timeEnd":"20190521200620","totalFee":0,"tradeType":"","transactionId":"4211111111111111111111111111","wxOrderId":"","ylOrderId":"","zfbOrderId":""}}}
                         * */

                        if(result != null){
                            activity.orderDetails = result.getOrderDetails();
                            activity.purchaseDetails = result.getPurchaseDetails();
                            activity.orderPayDetails = result.getOrderPayDetails();
                            activity.paymentDetails = result.getPaymentDetails();


                            if(activity.orderCollect != null){

                                String orderCode = activity.orderDetails.getOrderCode();

                                if(orderCode != null && !"".equals(orderCode)){
                                    activity.tv_order_code.setText(orderCode);
                                }else {
                                    activity.tv_order_code.setText("—");
                                }

                                String orderType= activity.orderDetails.getOrderType();

                                if(orderType != null && !"".equals(orderType)){
                                    if("P".equals(orderType)){
                                        activity.tv_buy_type.setText("商品");
                                    }

                                }else {
                                    activity.tv_buy_type.setText("—");
                                }


                              /*  String orderTypeName = activity.orderDetails.getOrderTypeName();

                                if(orderTypeName != null && !"".equals(orderTypeName)){
                                    activity.tv_buy_content.setText(orderTypeName);
                                }else {
                                    activity.tv_buy_content.setText("—");
                                }*/

                                String salesTime = activity.orderDetails.getSalesTime();

                                if(salesTime != null && !"".equals(salesTime)){
                                    activity.tv_sale_time.setText(salesTime.replace("-","."));
                                }else {
                                    activity.tv_sale_time.setText("—");
                                }

                                String buyName = activity.orderDetails.getMemberName();
                                String buyPhone = activity.orderDetails.getMemberTel();
                                if(buyPhone != null && !"".equals(buyPhone)){
                                    if(buyName != null &&  !"".equals(buyName)){
                                        activity.tv_buy_phone.setText(buyPhone+"("+buyName+")");
                                    }else {
                                        activity.tv_buy_phone.setText(buyPhone+"(—)");
                                    }

                                }else {
                                    if(buyName != null &&  !"".equals(buyName)){
                                        activity.tv_buy_phone.setText("—("+buyName+")");
                                    }else {
                                        activity.tv_buy_phone.setText("—");
                                    }

                                }

                                String staffName = activity.orderDetails.getStaffName();
                                if(staffName != null && !"".equals(staffName)){
                                    activity.tv_sale_emploee.setText(staffName);
                                }else {
                                    activity.tv_sale_emploee.setText("—");
                                }
                                String recommendMemberName = activity.orderDetails.getRecommendMemberName();
                                String recommendMemberTel = activity.orderDetails.getRecommendMemberTel();
                                if(recommendMemberTel != null && !"".equals(recommendMemberTel)){
//                                    activity.tv_tuijian_buy_phone.setText(recommendMemberTel);
                                    if(recommendMemberName != null &&  !"".equals(recommendMemberName)){
                                        activity.tv_tuijian_buy_phone.setText(recommendMemberTel+"("+recommendMemberName+")");
                                    }else {
                                        activity.tv_tuijian_buy_phone.setText(recommendMemberTel+"(—)");
                                    }
                                }else {
                                    if(recommendMemberName != null &&  !"".equals(recommendMemberName)){
                                        activity.tv_tuijian_buy_phone.setText("—("+recommendMemberName+")");
                                    }else {
                                        activity.tv_tuijian_buy_phone.setText("—");
                                    }
                                }

                                BigDecimal buyAmount = activity.orderDetails.getAmount();

                                String money = "—";
                                if(buyAmount != null && buyAmount.doubleValue()>0){
                                    money =  RegexUtils.getDoubleString(buyAmount.doubleValue());

                                }
                                activity.tv_pay_amt.setText(money);


                                BigDecimal redEnvelopeAmount = activity.orderDetails.getUseRedEnvelopeAmount();

                                String redEnvelope = "—";
                                if(redEnvelopeAmount != null && redEnvelopeAmount.doubleValue()>0){
                                    redEnvelope =  RegexUtils.getDoubleString(redEnvelopeAmount.doubleValue());

                                }
                                activity.tv_red_packget_dixian.setText(redEnvelope);

                                String buyMsg = activity.orderDetails.getBuyMsg();
                                if(buyMsg != null && !"".equals(buyMsg)){
                                    activity.tv_buy_msg.setText(buyMsg);
                                }else {
                                    activity.tv_buy_msg.setText("—");
                                }

                            }

                            if(activity.paymentDetails != null ){  //
                            }else {
                            }
                            //微信支付
                            if(activity.paymentDetails != null ){

                                if(activity.paymentDetails != null){
                                    String payWay = activity.paymentDetails.getPayChannel();

                                    if("tl".equals(payWay)){
                                        activity.tv_pay_way.setText("微信");
                                    }else if("w".equals(payWay)){
                                        activity.tv_pay_way.setText("微信");

                                    }else {
                                        activity.tv_pay_way.setText("—");
                                    }

                                    BigDecimal payAmt = activity.paymentDetails.getCashFee();
                                    String payAmount = "—";
                                    if(payAmt != null && payAmt.doubleValue()>0){
                                        payAmount =  RegexUtils.getDoubleString(payAmt.doubleValue());

                                    }
                                    activity.tv_fu_amt.setText(payAmount);
                                    //微信支付订单号
                                    String wxOrderId = activity.paymentDetails.getTransactionId();
                                    if(wxOrderId != null && !"".equals(wxOrderId)){
                                        activity.tv_wx_order_code.setText(wxOrderId);
                                    }else {
                                        activity.tv_wx_order_code.setText("—");
                                    }


                                    Long shOrderId = activity.paymentDetails.getOutTradeNo();
                                    if(shOrderId != null ){
                                        activity.tv_sh_order_code.setText(String.valueOf(shOrderId));
                                    }else {
                                        activity.tv_sh_order_code.setText("—");
                                    }

                                    String time = activity.paymentDetails.getTimeEnd();

                                    if(time != null && !"".equals(time)){

                                        String payTime = TimeUtils.millis2String(Long.parseLong(time),TimeUtils.DATE_FORMAT_DATETIME);
                                        if(payTime != null && !"".equals(payTime)){
                                            activity.tv_pay_way_time.setText(payTime.replace("-","."));
                                        }else {
                                            activity.tv_pay_way_time.setText("—");
                                        }

                                    }else {
                                        activity.tv_pay_way_time.setText("—");
                                    }


                                }
                            }else {
                            }

                            //红包
                            if(activity.orderPayDetails != null &&  activity.orderPayDetails.size()>0){
                                CollectionFlowDetailResultBean.OrderPayDetailsBean orderPayDetailsBean = activity.orderPayDetails.get(0);

                                if(orderPayDetailsBean != null){
                                    String payWay = orderPayDetailsBean.getPayWay();

                                    if("r".equals(payWay)){
                                        activity.tv_pay_red_way.setText("红包");
                                    }else {
                                        activity.tv_pay_red_way.setText("—");
                                    }

                                    BigDecimal payAmt = orderPayDetailsBean.getPayAmount();
                                    String payAmount = "—";
                                    if(payAmt != null && payAmt.doubleValue()>0){
                                        payAmount =  RegexUtils.getDoubleString(payAmt.doubleValue());

                                    }
                                    activity.tv_red_pack_dixian.setText(payAmount);



                                    String time = orderPayDetailsBean.getPayTime();

                                    String payTime = TimeUtils.millis2String(Long.parseLong(time),TimeUtils.DATE_FORMAT_DATETIME);
                                    if(payTime != null && !"".equals(payTime)){
                                        activity.tv_red_packget_time.setText(payTime.replace("-","."));
                                    }else {
                                        activity.tv_red_packget_time.setText("—");
                                    }


                                }
                            }else {
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
    public void loadConfigByCompId(Context context, String userId) {

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
        CollectionMoneyAccountSettingActivity activity =  (CollectionMoneyAccountSettingActivity) context;
        financialAffairsServiceManage.loadConfigByCompId(userId)
                .subscribe(new BaseObserver<Map<String,String>>(context,true){
                    @Override
                    public void onSuccess(Map<String,String> result,String msg) {

                        // "process":"N","status":"N"
                        String statusStr = "";  //0 钱包 生效中   1 银行卡 生效中   2 未设置
                        //"accountName":"","appid":"","bankCardNo":"","bankName":"","compId":0,"configState":"2","id":0,"memberName":"","openid":"","payType":"","phone":""}
                        //{"code":"1","message":"查询支付配置信息成功","result":{"accountName":"","appid":"wx6475079f65e3f3dc","bankCardNo":"","bankName":"","compId":1,"configState":"0","id":416017225531392,
                        // "memberName":"天台爱情","openid":"oZ9TG0cbLMa7XLVXM_5gaR-vR4WE","payType":"0","phone":"18053149097"}}
                        if(result != null &&  result.size()>0){

                            activity.configState = result.get("configState");
                            activity.phone = result.get("phone");
                            activity.name = result.get("memberName");
                            if("2".equals(activity.configState)){
                                activity.tv_setting_wx.setText("未设置");
                                activity.tv_setting_wx.setTextColor(context.getResources().getColor(R.color.font_color_blue));
                                activity.tv_setting_wx.setCompoundDrawablesWithIntrinsicBounds(null,null,context.getResources().getDrawable(R.drawable.ic_arrow_blue),null);
                            }else if("0".equals(activity.configState)){   //生效中
                                activity.tv_setting_wx.setText("生效中");
                                activity.tv_setting_wx.setTextColor(context.getResources().getColor(R.color.font_color_green));
                                activity.tv_setting_wx.setCompoundDrawablesWithIntrinsicBounds(null,null,context.getResources().getDrawable(R.drawable.ic_arrow_green),null);
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
    public void checkWxPayInfo(Context context, String brandId, String tel, String memberName) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",brandId);
        params.put("tel",tel);
        params.put("memberName",memberName);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        CollectionWxNoEffectiveActivity activity =  (CollectionWxNoEffectiveActivity) context;
        financialAffairsServiceManage.checkWxPayInfo(brandId,tel,memberName)
                .subscribe(new BaseObserver<String>(context,true){
                    @Override
                    public void onSuccess(String result,String msg) {

                        // "process":"N","status":"N"
                        String statusStr = "";  //0 钱包 生效中   1 银行卡 生效中   2 未设置
                        //"accountName":"","appid":"","bankCardNo":"","bankName":"","compId":0,"configState":"2","id":0,"memberName":"","openid":"","payType":"","phone":""}


                        // 1  不是平台会员
                        saveCompWxpayConfig(context,activity.jsonstr);


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        if(code == 0){
                            activity.setDialog(message);
                        }

                    }
                });

    }

    @Override
    public void saveCompWxpayConfig(Context context, String jsonstr) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("jsonstr",jsonstr);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        CollectionWxNoEffectiveActivity activity = (CollectionWxNoEffectiveActivity) context;


        financialAffairsServiceManage.saveCompWxpayConfig(jsonstr)
                .subscribe(new BaseObserver<String>(context,true){
                    @Override
                    public void onSuccess(String result, String msg) {

                        if("0".equals(activity.configState)){
                            ToastUtils.showShort("已生效");

                        }else if("2".equals(activity.configState)){
                            ToastUtils.showShort("已保存");
                        }
                        ActivityUtils.startActivity(CollectionMoneyAccountSettingActivity.class,R.anim.slide_in_right,R.anim.slide_out_left);
                        ((CollectionWxNoEffectiveActivity) context).finish();

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);
                    }
                });

    }
    //转账记录详情
    @Override
    public void loadWxPayRecord(Context context, String id) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("id",id);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        CollectionTransferRecordDetailActivity activity = (CollectionTransferRecordDetailActivity) context;


        financialAffairsServiceManage.loadWxPayRecord(id)
                .subscribe(new BaseObserver<CompWxpayTransfer>(context,true){
                    @Override
                    public void onSuccess(CompWxpayTransfer result, String msg) {
                        /**
                         *
                         *
                         * {"code":"1","message":"查询收款到账金额 汇总成功","result":{"accountName":"","amount":0,"appid":"wx6475079f65e3f3dc","bankCardNo":"","bankName":"","compId":1,"desc":"转账","fee":0,"id":1,"memberName":"天台爱情",
                         * "openid":"oZ9TG0cbLMa7XLVXM_5gaR-vR4WE","partnerTradeNo":"416072243904512","paymentNo":"123456","phone":"18053149097","state":"1","time":"2019-05-24 15:15:36","type":"0"}}
                         * */

                        if(result != null){
                            String time =result.getTime();
                            if(time != null && !"".equals(time)){
                                activity.tv_daozhang_time.setText(time.replace("-","."));
                            }else {
                                activity.tv_daozhang_time.setText("—");
                            }



                            String state =result.getState();
                            if("1".equals(state)){
                                activity.tv_daozhang_jg_status.setText("成功");
                            }else if("0".equals(state)){
                                activity.tv_daozhang_jg_status.setText("失败");
                            }else {
                                activity.tv_daozhang_jg_status.setText("—");
                            }

                            String fee = "—";
                            BigDecimal fe =  result.getFee();
                            if(fe != null && fe.doubleValue()>0){
                                fee = RegexUtils.getDoubleString(fe.doubleValue());
                            }else {
                                fee = "—";
                            }
                            activity.tv_fee.setText(fee);

                            String money = "—";
                            BigDecimal mon =  result.getAmount();
                            if(mon != null && mon.doubleValue()>0){
                                money = RegexUtils.getDoubleString(mon.doubleValue());

                            }else {
                                money = "—";
                            }
                            //商品总额
                            activity.tv_daozhang_total_amt.setText(money);

                            double sjAmt = mon.doubleValue() - fe.doubleValue();
                            String shijiAmt = "—";
                            if(sjAmt>0){
                                shijiAmt = RegexUtils.getDoubleString(mon.doubleValue() - fe.doubleValue());
                                activity.tv_shiji_to_amt.setText(shijiAmt+"元");
                            }else{
                                activity.tv_shiji_to_amt.setText(shijiAmt);
                            }

                            String orderId =result.getOpenid();
                            if(orderId != null && !"".equals(orderId)){
                                activity.tv_order_num.setText(orderId);
                            }else {
                                activity.tv_order_num.setText("—");
                            }

                            String bankName =result.getBankName();
                            if(bankName != null && !"".equals(bankName)){
                                activity.tv_bank_name.setText(bankName);
                            }else {
                                activity.tv_order_num.setText("—");
                            }

                            String bankNo =result.getBankCardNo();
                            if(bankNo != null && !"".equals(bankNo)){
                                activity.tv_bank_no.setText(bankNo);
                            }else {
                                activity.tv_bank_no.setText("—");
                            }

                            String name =result.getMemberName();
                            if(name != null && !"".equals(name)){
                                activity.tv_name.setText(name);
                            }else {
                                activity.tv_name.setText("—");
                            }


                            String type =result.getType();
                            if("1".equals(type)){
                                activity.tv_type_name_label.setText("银行卡");
                                activity.ll_fee.setVisibility(View.VISIBLE);
                                activity.ll_daozhang_amt.setVisibility(View.VISIBLE);

                                activity.rl_bank.setVisibility(View.VISIBLE);
                            }else if("0".equals(type)){
                                activity.ll_daozhang_amt.setVisibility(View.GONE);
                                activity.tv_type_name_label.setText("钱包");
                                activity.ll_fee.setVisibility(View.GONE);
                                activity.rl_bank.setVisibility(View.GONE);
                                activity.tv_label1.setText("到账总金额：");
                                activity.tv_order_num_label.setText("微信付款单号：");
                                activity.tv_name_label.setText("到账人会员名：");
                                activity.tv_bank_no_label.setText("到账人手机号：");
                                BigDecimal amount = result.getAmount();
                                String am = "—";
                                if(amount != null && amount.doubleValue()>0){
                                    am = RegexUtils.getDoubleString(amount.doubleValue());
                                }

                                activity.tv_shiji_to_amt.setText(am);

                                String paymentNo = result.getPaymentNo();
                                if(paymentNo != null && !"".equals(paymentNo)){

                                }else {
                                    paymentNo = "—";
                                }
                                activity.tv_order_num.setText(paymentNo);
                                String memberName = result.getMemberName();
                                activity.tv_bank_name.setText(memberName != null && !"".equals(memberName)?memberName:"—");

                                String phone = result.getPhone();
                                activity.tv_bank_no.setText(phone != null && !"".equals(phone)?phone:"—");
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
    //收款到账查看订单详情
    @Override
    public void queryOrderDetailForwxPay(Context context, String orderId) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("orderId",orderId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        CollectionToAccountOrderDetailActivity activity = (CollectionToAccountOrderDetailActivity) context;


        financialAffairsServiceManage.queryOrderListDetailForwxPay(orderId)
                .subscribe(new BaseObserver<CollectionToAccountOrderDetailBean>(context,false){
                    @Override
                    public void onSuccess(CollectionToAccountOrderDetailBean result, String msg) {

                        /**
                         *
                         * {"code":"1","message":"收款到账查看订单详情","result":{"ordercollect":{"balanceTime":null,"balanceType":"","compFee":0.00,"compId":1,"dealerCompId":"","dealerFee":0.00,"estimateTime":"2019-05-24 16:13:59","id":416072347418624,"orderId":416072243904512,
                         * "platformFee":0.00,"status":"0"},"orderDetail":[{"buyCount":1,"catId":397326128250880,"catName":"鲁菜","compId":1,"cou":0,"deliveryFee":0,"detailedId":416072244232192,"factSalesPrice":"","getRedEnvelope":5.00,"marketPrice":0,"orderId":416072243904512,"payWay":"0",
                         * "productCode":"G00008","productDetail":"","productId":397562090586112,"productName":"开发环境测试商品2","productSpec":"盘/份","salesType":"pd","status":"0","thumbnailUrl":"5cd66aa23e59d830d8aa94b2","unitPrice":0.01,"unitScore":0,"useRedEnvelope":0.00}],
                         * "orderPay":[{"compId":1,"id":416072244346880,"orderId":416072243904512,"payAmount":0.01,"payId":"4200000321201905248879749973","payTime":"20190524161359","payWay":"w","remark":"","result":"1"}],"
                         * orderinfo":{"amount":0,"brandId":0,"buyCount":0,"buyMsg":"","compId":0,"coun":0,"delivType":"","fromId":"","fromType":"","memberBuy":"","memberId":0,"memberName":"","memberTel":"","orderCode":201905240355,"orderId":416072243904512,"orderState":"","orderType":"P","
                         * orderTypeName":"","orderTypes":"","payChannel":"","productCode":"","productId":0,"productIds":"","productName":"开发环境测试商品2","recommendMemberName":"","recommendMemberTel":"","salesTime":"2019-05-24 16:13:54","staffId":0,"staffName":"",
                         * "staffTel":"","storeId":0,"storeName":"","unitPrice":0,"useCash":0,"useCashAmount":0,"useRedEnvelope":0.00,"useRedEnvelopeAmount":0}}}
                         * */

                        if(result != null){
                            activity.ordercollect = result.getOrdercollect();
                            activity.orderPay = result.getOrderPay();
                            activity.orderinfo = result.getOrderinfo();
                            activity.orderDetail = result.getOrderDetail();


                            if(activity.ordercollect != null){
                                String estimateTime = activity.ordercollect.getEstimateTime();
                                if(estimateTime != null && !"".equals(estimateTime)){
                                    activity.tv_to_time.setText(estimateTime.replace("-","."));
                                }else {
                                    activity.tv_to_time.setText("—");
                                }
                            }

                            if(activity.orderinfo != null){


                                if(activity.orderinfo.getOrderCode() != null && !"".equals(activity.orderinfo.getOrderCode())){

                                    if(activity.orderinfo.getOrderCode() != 0){
                                        activity.tv_order_code.setText(String.valueOf(activity.orderinfo.getOrderCode()));
                                    }else {
                                        activity.tv_order_code.setText("—");
                                    }

                                }else {
                                    activity.tv_order_code.setText("—");
                                }

                                String orderType= activity.orderinfo.getOrderType();

                                if(orderType != null && !"".equals(orderType)){
                                    if("P".equals(orderType)){
                                        activity.tv_buy_type.setText("商品");
                                        if(activity.orderDetail != null && activity.orderDetail.size()>0){
                                            activity.ll_product_details.setVisibility(View.VISIBLE);
                                            activity.setPoductDetail();

                                        }else{
                                            activity.ll_product_details.setVisibility(View.GONE);
                                        }
                                        activity.ll_goumai_content.setVisibility(View.GONE);
                                    }else if("M".equals(orderType)){
                                        activity.tv_buy_type.setText("会员卡");
                                        activity.ll_product_details.setVisibility(View.GONE);
                                        activity.ll_goumai_content.setVisibility(View.VISIBLE);
                                    }else if("G".equals(orderType)){
                                        activity.tv_buy_type.setText("拼团");
                                        activity.ll_product_details.setVisibility(View.GONE);
                                        activity.ll_goumai_content.setVisibility(View.VISIBLE);
                                    }

                                }else {
                                    activity.tv_buy_type.setText("—");
                                }

                                String productName = activity.orderinfo.getProductName();

                                if(productName != null && !"".equals(productName)){
                                    activity.tv_buy_content.setText(productName);
                                }else {
                                    activity.tv_buy_content.setText("—");
                                }


                                String salesTime = activity.orderinfo.getSalesTime();

                                if(salesTime != null && !"".equals(salesTime)){
                                    activity.tv_sale_time.setText(salesTime.replace("-","."));
                                }else {
                                    activity.tv_sale_time.setText("—");
                                }

                                String buyName = activity.orderinfo.getMemberName();
                                String buyPhone = activity.orderinfo.getMemberBuy();
                                if(buyPhone != null &&  !"".equals(buyPhone)){
                                    activity.tv_buy_phone.setText(buyPhone);
                                }else {
                                    activity.tv_buy_phone.setText("—");
                                }

                                String staffName = activity.orderinfo.getStaffName();
                                if(staffName != null && !"".equals(staffName)){
                                    activity.tv_sale_emploee.setText(staffName);
                                }else {
                                    activity.tv_sale_emploee.setText("—");
                                }
                                String recommendMemberName = activity.orderinfo.getRecommendMemberName();
                                String recommendMemberTel = activity.orderinfo.getRecommendMemberTel();
                                if(recommendMemberName != null &&  !"".equals(recommendMemberName)){
                                    activity.tv_tuijian_buy_phone.setText(recommendMemberName);
                                }else {
                                    activity.tv_tuijian_buy_phone.setText("—");
                                }
                                BigDecimal redEnvelopeAmount = activity.orderinfo.getUseRedEnvelope();

                                String redEnvelope = "—";
                                if(redEnvelopeAmount != null && redEnvelopeAmount.doubleValue()>0){
                                    redEnvelope =  RegexUtils.getDoubleString(redEnvelopeAmount.doubleValue());

                                }
                                activity.tv_red_packget_dixian.setText(redEnvelope);

                                String buyMsg = activity.orderinfo.getBuyMsg();
                                if(buyMsg != null && !"".equals(buyMsg)){
                                    activity.tv_buy_msg.setText(buyMsg);
                                }else {
                                    activity.tv_buy_msg.setText("—");
                                }

                            }

                            //微信支付
                            if(activity.orderPay != null && activity.orderPay.size()>0){


                                for(TOrderPay orderPay:activity.orderPay){
                                    String payWay = orderPay.getPayWay();

                                    if("w".equals(payWay)){
                                        activity.tv_pay_way.setText("微信");
                                        BigDecimal payAmt = orderPay.getPayAmount();
                                        String payAmount = "—";
                                        if(payAmt != null && payAmt.doubleValue()>0){
                                            payAmount =  RegexUtils.getDoubleString(payAmt.doubleValue());

                                        }
                                        activity.tv_fu_amt.setText(payAmount);
                                        activity.tv_pay_amt.setText(payAmount);


                                        //微信支付订单号
                                        if(orderPay.getPayId() != null && !"".equals(orderPay.getPayId())){
                                            activity.tv_wx_order_code.setText(String.valueOf(orderPay.getPayId()));
                                        }else {
                                            activity.tv_wx_order_code.setText("—");
                                        }

                                        if(orderPay.getOrderId() != null ){
                                            activity.tv_sh_order_code.setText(String.valueOf(orderPay.getOrderId()));
                                        }else {
                                            activity.tv_sh_order_code.setText("—");
                                        }

                                        String payTime = orderPay.getPayTime();

                                        if(payTime != null && !"".equals(payTime)){
                                            if(payTime != null && !"".equals(payTime)){
                                                activity.tv_pay_way_time.setText(payTime.replace("-","."));
                                            }else {
                                                activity.tv_pay_way_time.setText("—");
                                            }
//                                            String payTime = TimeUtils.millis2String(Long.parseLong(time),TimeUtils.DATE_FORMAT_DATETIME);
                                           /* Date date = TimeUtils.string2Date(payTime,TimeUtils.DATEFORMATDATE);
                                            if(date != null){
                                                String payTime2 = TimeUtils.date2String(date);

                                            }*/



                                        }else {
                                            activity.tv_pay_way_time.setText("—");
                                        }
                                        activity.ll_w_pay.setVisibility(View.VISIBLE);
                                    }

                                }
                                for(TOrderPay orderPay:activity.orderPay){
                                    String payWay = orderPay.getPayWay();
                                    if("r".equals(payWay)){
                                        activity.tv_pay_red_way.setText("红包");
                                        BigDecimal payAmt = orderPay.getPayAmount();
                                        String payAmount = "—";
                                        if(payAmt != null && payAmt.doubleValue()>0){
                                            payAmount =  RegexUtils.getDoubleString(payAmt.doubleValue());

                                        }
                                        activity.tv_red_pack_dixian.setText(payAmount);
                                        String payTime = orderPay.getPayTime();
                                        if(payTime != null && !"".equals(payTime)){
                                            activity.tv_red_packget_time.setText(payTime.replace("-","."));
                                        }else {
                                            activity.tv_red_packget_time.setText("—");
                                        }
//                                        String payTime = TimeUtils.millis2String(Long.parseLong(time),TimeUtils.DATE_FORMAT_DATETIME);
//                                        Date date = TimeUtils.string2Date(time,TimeUtils.formatTimestamp);

                                        /*Date date = TimeUtils.string2Date(time,TimeUtils.DATEFORMATDATE);
                                        if(date != null){
                                            String payTime =TimeUtils.date2String(date) ;
                                            if(payTime != null && !"".equals(payTime)){
                                                activity.tv_red_packget_time.setText(payTime.replace("-","."));
                                            }else {
                                                activity.tv_red_packget_time.setText("—");
                                            }
                                        }*/



                                        activity.ll_r_pay.setVisibility(View.VISIBLE);
                                    }
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
    //查询收款到账金额 汇总
    @Override
    public void queryWxPayInfo(Context context, String userId) {

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

        CollectionMoneyIntoAccountListActivity activity = (CollectionMoneyIntoAccountListActivity) context;


        financialAffairsServiceManage.queryWxPayInfo(userId)
                .subscribe(new BaseObserver<TOrderObject>(context,false){
                    @Override
                    public void onSuccess(TOrderObject result, String msg) {


                        if(result != null){
                            BigDecimal tomorrowAmt = result.getTomorrowAmt();
                            BigDecimal futureAmt = result.getFutureAmt();
                            String tAmt = "0";
                            if(tomorrowAmt != null && tomorrowAmt.doubleValue()>0){
                                tAmt = RegexUtils.getDoubleString(tomorrowAmt.doubleValue());
                            }
                            activity.tv_expected_into_amt.setText(tAmt);


                            String fAmt = "0";
                            if(futureAmt != null && futureAmt.doubleValue()>0){
                                fAmt = RegexUtils.getDoubleString(futureAmt.doubleValue());
                            }
                            activity.tv_no_into_amt.setText(fAmt);

                        }


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);
                    }
                });
    }

    //收款到账订单列表  flag"查询标志 T-明日到账，F-未到账"
    @Override
    public void queryWxPayList(Context context, String userId, String flag,Integer pageNumber,Integer pageSize) {


        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("userId", userId);
        params.put("flag", flag);
        params.put("pageSize", String.valueOf(pageSize));
        params.put("pageNumber", String.valueOf(pageNumber));
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        CollectionExpectToArriveAmtFragment expectTofragment = null;
        CollectionNoToArriveAmtFragment noTofragment = null;
        if("T".equals(flag)){ //查询标志 T-明日到账，F-未到账
            expectTofragment = (CollectionExpectToArriveAmtFragment)view;
        }else {
            noTofragment = (CollectionNoToArriveAmtFragment)view;
        }
        financialAffairsServiceManage.queryWxPayList(userId, flag,pageNumber,pageSize)
                .subscribe(new BaseObserver<OrderObjectBean>(context, true) {
                    @Override
                    public void onSuccess(OrderObjectBean result, String msg) {
                        if (result != null) {
                            List<TOrderObject> dataList = result.getList();
                            Integer to = result.getTotal();
                            if("T".equals(flag)){ //查询标志 T-明日到账，F-未到账
                                CollectionExpectToArriveAmtFragment expectTofragment = (CollectionExpectToArriveAmtFragment)view;


                                if(to !=null && to.intValue()>0){
                                    expectTofragment.total = String.valueOf(to.intValue());
                                    expectTofragment.tv_order_num.setText(expectTofragment.total);
                                }else {
                                    expectTofragment.tv_order_num.setText("0");
                                }

                                if (dataList != null && dataList.size() > 0) {

                                    for(int i=0;i<dataList.size();i++){
                                        if(dataList.get(i) != null){
                                            if(dataList.get(i).getOrderId() != null){
                                                String detailedId = String.valueOf(dataList.get(i).getOrderId());
                                                if(expectTofragment.orderList != null &&  expectTofragment.orderList.size()>0){
                                                    for(int j=0;j<expectTofragment.orderList.size();j++){
                                                        if(detailedId.equals(String.valueOf(expectTofragment.orderList.get(j).getOrderId()))){
                                                            expectTofragment.orderList.remove(j);
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }


                                    expectTofragment.orderList.addAll(dataList);
                                    expectTofragment.adapter.notifyDataSetChanged();
                                    expectTofragment.ptr.setPullToRefreshOverScrollEnabled(true);
                                    if (dataList.size() < 10) {
                                        expectTofragment.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                    } else {
                                        expectTofragment.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                    }
                                    expectTofragment.ll_no_data.setVisibility(View.GONE);
                                    expectTofragment.listView.setVisibility(View.VISIBLE);

                                } else {
                                    expectTofragment.orderList.addAll(dataList);
                                    expectTofragment.adapter.notifyDataSetChanged();

                                    if(expectTofragment.orderList != null  &&  expectTofragment.orderList.size()>0){
                                        if(expectTofragment.orderList.size() < 10){
                                            expectTofragment.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                        }else {
                                            expectTofragment.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                        }

                                        expectTofragment.ll_no_data.setVisibility(View.GONE);
                                        expectTofragment.listView.setVisibility(View.VISIBLE);
                                    }else {

                                        expectTofragment.ll_no_data.setVisibility(View.VISIBLE);
                                        expectTofragment.listView.setVisibility(View.GONE);
                                        expectTofragment.tv_no_data.setText("暂无数据");
                                    }


                                }
                                expectTofragment.ptr.onRefreshComplete();

                            }else {
                                CollectionNoToArriveAmtFragment noTofragment = (CollectionNoToArriveAmtFragment)view;
                                if(to !=null && to.intValue()>0){
                                    noTofragment.total = String.valueOf(to.intValue());
                                    noTofragment.tv_order_num.setText(noTofragment.total);
                                }else {
                                    noTofragment.tv_order_num.setText("0");
                                }
                                if (dataList != null && dataList.size() > 0) {

                                    for(int i=0;i<dataList.size();i++){
                                        if(dataList.get(i) != null){
                                            if(dataList.get(i).getOrderId() != null){
                                                String detailedId = String.valueOf(dataList.get(i).getOrderId());
                                                if(noTofragment.orderList != null &&  noTofragment.orderList.size()>0){
                                                    for(int j=0;j<noTofragment.orderList.size();j++){
                                                        if(detailedId.equals(String.valueOf(noTofragment.orderList.get(j).getOrderId()))){
                                                            noTofragment.orderList.remove(j);
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }


                                    noTofragment.orderList.addAll(dataList);
                                    noTofragment.adapter.notifyDataSetChanged();
                                    noTofragment.ptr.setPullToRefreshOverScrollEnabled(true);
                                    if (dataList.size() < 10) {
                                        noTofragment.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                    } else {
                                        noTofragment.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                    }
                                    noTofragment.ll_no_data.setVisibility(View.GONE);
                                    noTofragment.listView.setVisibility(View.VISIBLE);

                                } else {
                                    noTofragment.orderList.addAll(dataList);
                                    noTofragment.adapter.notifyDataSetChanged();

                                    if(noTofragment.orderList != null  &&  noTofragment.orderList.size()>0){
                                        if(noTofragment.orderList.size() < 10){
                                            noTofragment.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                        }else {
                                            noTofragment.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                        }

                                        noTofragment.ll_no_data.setVisibility(View.GONE);
                                        noTofragment.listView.setVisibility(View.VISIBLE);
                                    }else {

                                        noTofragment.ll_no_data.setVisibility(View.VISIBLE);
                                        noTofragment.listView.setVisibility(View.GONE);
                                        noTofragment.tv_no_data.setText("暂无数据");
                                    }


                                }
                                noTofragment.ptr.onRefreshComplete();

                            }



                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);

                        CollectionNoToArriveAmtFragment noTofragment = null;
                        if("T".equals(flag)){ //查询标志 T-明日到账，F-未到账
                            CollectionExpectToArriveAmtFragment fragment = (CollectionExpectToArriveAmtFragment)view;
                            fragment.orderList.clear();
                            fragment.adapter.notifyDataSetChanged();
                            fragment.ptr.onRefreshComplete();
                            fragment.ll_no_data.setVisibility(View.VISIBLE);
                            fragment.listView.setVisibility(View.GONE);
                            fragment.tv_no_data.setText("暂无数据");
                        }else {
                            CollectionNoToArriveAmtFragment fragment  = (CollectionNoToArriveAmtFragment)view;
                            fragment.orderList.clear();
                            fragment.adapter.notifyDataSetChanged();
                            fragment.ptr.onRefreshComplete();
                            fragment.ll_no_data.setVisibility(View.VISIBLE);
                            fragment.listView.setVisibility(View.GONE);
                            fragment.tv_no_data.setText("暂无数据");
                        }



                    }
                });

    }

    @Override
    public void queryWxPayRecord(Context context, String userId,String startTime,String endTime, Integer pageNumber, Integer pageSize) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
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
        CollectionTransferRecordActivity activity =  (CollectionTransferRecordActivity) context;
        financialAffairsServiceManage.queryWxPayRecord(userId,startTime,endTime,pageNumber,pageSize)
                .subscribe(new BaseObserver<CompWxpayTransferBean>(context,true){
                    @Override
                    public void onSuccess(CompWxpayTransferBean result,String msg) {

                        /**
                         *
                         * {"code":"1","message":"查询转账记录列表成功","result":{"endRow":1,"firstPage":1,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":true,"lastPage":1,
                         * "list":[{"accountName":"","amount":0,"appid":"wx6475079f65e3f3dc","bankCardNo":"","bankName":"","compId":1,"desc":"转账","fee":0,"id":1,"memberName":"天台爱情","openid":"oZ9TG0cbLMa7XLVXM_5gaR-vR4WE",
                         * "partnerTradeNo":"416072243904512","paymentNo":"123456","phone":"18053149097","state":"1","time":"2019-05-24 15:15:36","type":"0"}],
                         * "navigateFirstPage":1,"navigateLastPage":1,"navigatePages":8,"navigatepageNums":[1],"nextPage":0,"pageNum":1,"pageSize":10,"pages":1,"prePage":0,"size":1,"startRow":1,"total":1}}
                         * */

                        if(result != null ){
                            List<CompWxpayTransfer> dataList = result.getList();

                            if (dataList != null && dataList.size() > 0) {
                                activity.transferList.addAll(dataList);
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
                                activity.transferList.addAll(dataList);
                                activity.listAdapter.notifyDataSetChanged();

                                if(activity.transferList != null  &&  activity.transferList.size()>0){
                                    if(activity.transferList.size() < 10){
                                        activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                    }else {
                                        activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                    }

                                    activity.ll_no_data.setVisibility(View.GONE);
                                    activity.listView.setVisibility(View.VISIBLE);
                                }else {

                                    activity.ll_no_data.setVisibility(View.VISIBLE);
                                    activity.listView.setVisibility(View.GONE);
                                    activity.tv_no_data.setText("您现在没有转账记录哦！");
                                }


                            }
                            activity.ptr.onRefreshComplete();

                        }else {
                            if(activity.transferList != null  &&  activity.transferList.size()>0){
                                activity.listAdapter.notifyDataSetChanged();
                                if(activity.transferList.size() < 10){
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                }else {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }
                                activity.ptr.onRefreshComplete();
                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.listView.setVisibility(View.VISIBLE);
                            }else {

                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.listView.setVisibility(View.GONE);
                                activity.tv_no_data.setText("您现在没有转账记录哦！");
                            }
                            activity.ptr.onRefreshComplete();
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);

                        activity.listAdapter.notifyDataSetChanged();
                        activity.ptr.onRefreshComplete();
                        activity.ll_no_data.setVisibility(View.VISIBLE);
                        activity.listView.setVisibility(View.GONE);
                        activity.tv_no_data.setText("您现在没有转账记录哦！");

                    }
                });

    }


    @Override
    public void queryStoreList(Context context, String userId) {
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

        MemCardEmploeeSaleReportActivity activity = (MemCardEmploeeSaleReportActivity) context;
        campaignServiceManager.queryBrandAndStoreList(userId)
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
                        ToastUtils.showShort(message);
                    }
                });
    }

    @Override
    public void selectSalesByEmployee(Context context, String userId, String salesTimeSta, String salesTimesEnd, String storeId,
                                      String storeStr, String storeSelectType, String orderType, Integer pageNumber, Integer pageSize) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("salesTimeSta",salesTimeSta);
        params.put("salesTimesEnd",salesTimesEnd);
        params.put("storeId",storeId);
        params.put("storeStr",storeStr);
        params.put("storeSelectType",storeSelectType);
        params.put("orderType",orderType);
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
        MemCardEmploeeSaleReportActivity activity =  (MemCardEmploeeSaleReportActivity) context;
        financialAffairsServiceManage.selectSalesByEmployee(userId,salesTimeSta,salesTimesEnd,storeId,storeStr,storeSelectType,orderType,pageNumber,pageSize)
                .subscribe(new BaseObserver<List<TOrder>>(context,true){
                    @Override
                    public void onSuccess(List<TOrder> dataList,String msg) {



                      /*  if(dataList != null ){



                        }else {
                            if(activity.orderList != null  &&  activity.orderList.size()>0){
                                activity.listAdapter.notifyDataSetChanged();
                                if(activity.orderList.size() < 10){
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                }else {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }
                                activity.ptr.onRefreshComplete();
                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.listView.setVisibility(View.VISIBLE);
                            }else {

                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.listView.setVisibility(View.GONE);
                                activity.tv_no_data.setText("您现在没有销售记录哦！");
                            }
                            activity.ptr.onRefreshComplete();
                        }*/
                        if (dataList != null && dataList.size() > 0) {
                            activity.orderList.addAll(dataList);
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
                            activity.orderList.addAll(dataList);
                            activity.listAdapter.notifyDataSetChanged();

                            if(activity.orderList != null  &&  activity.orderList.size()>0){
                                if(activity.orderList.size() < 10){
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                }else {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }

                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.listView.setVisibility(View.VISIBLE);
                            }else {

                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.listView.setVisibility(View.GONE);
                                activity.tv_no_data.setText("您现在没有销售记录哦！");
                            }


                        }
                        activity.ptr.onRefreshComplete();
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);

                        activity.listAdapter.notifyDataSetChanged();
                        activity.ptr.onRefreshComplete();
                        activity.ll_no_data.setVisibility(View.VISIBLE);
                        activity.listView.setVisibility(View.GONE);
                        activity.iv_no_data.setImageResource(R.drawable.ic_wifi_not);
                        activity.tv_no_data.setText("您的网络可能睡着了！");

                    }
                });
    }

    @Override
    public void selectSalesDetailsByEmployee(Context context, String userId, String salesTimeSta, String salesTimesEnd, String storeId, String storeStr,
                                             String storeSelectType, String orderType, String productId, String staffId, Integer pageNumber, Integer pageSize) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("salesTimeSta",salesTimeSta);
        params.put("salesTimesEnd",salesTimesEnd);
        params.put("storeId",storeId);
        params.put("storeStr",storeStr);
        params.put("storeSelectType",storeSelectType);
        params.put("orderType",orderType);
        params.put("productId",productId);
        params.put("staffId",staffId);
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
        MemCardEmploeeSaleReportDetailListActivity activity =  (MemCardEmploeeSaleReportDetailListActivity) context;
        financialAffairsServiceManage.selectSalesDetailsByEmployee(userId,salesTimeSta,salesTimesEnd,storeId,storeStr,storeSelectType,orderType,productId, staffId,pageNumber,pageSize)
                .subscribe(new BaseObserver<List<TOrder>>(context,true){
                    @Override
                    public void onSuccess(List<TOrder> dataList,String msg) {

                        if (dataList != null && dataList.size() > 0) {
                            activity.orderList.addAll(dataList);
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
                            activity.orderList.addAll(dataList);
                            activity.listAdapter.notifyDataSetChanged();

                            if(activity.orderList != null  &&  activity.orderList.size()>0){
                                if(activity.orderList.size() < 10){
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                }else {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }

                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.listView.setVisibility(View.VISIBLE);
                            }else {

                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.listView.setVisibility(View.GONE);
                                activity.tv_no_data.setText("您现在没有销售记录哦！");
                            }


                        }
                        activity.ptr.onRefreshComplete();
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);

                        activity.listAdapter.notifyDataSetChanged();
                        activity.ptr.onRefreshComplete();
                        activity.ll_no_data.setVisibility(View.VISIBLE);
                        activity.listView.setVisibility(View.GONE);
                        activity.iv_no_data.setImageResource(R.drawable.ic_wifi_not);
                        activity.tv_no_data.setText("您的网络可能睡着了！");

                    }
                });

    }

    @Override
    public void selectSalesListByEmployee(Context context, String userId, String salesTimeSta, String salesTimesEnd, String storeId, String storeStr,
                                          String storeSelectType, String orderType, String productIds, String staffTel, String sort, Integer pageNumber, Integer pageSize) {


        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("salesTimeSta",salesTimeSta);
        params.put("salesTimesEnd",salesTimesEnd);
        params.put("storeId",storeId);
        params.put("storeStr",storeStr);
        params.put("storeSelectType",storeSelectType);
        params.put("orderType",orderType);
        params.put("productIds",productIds);
        params.put("staffTel",staffTel);
        params.put("sort",sort);
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
        MemCardEmploeeSaleReportDetailActivity activity =  (MemCardEmploeeSaleReportDetailActivity) context;
        financialAffairsServiceManage.selectSalesListByEmployee(userId,salesTimeSta,salesTimesEnd,storeId,storeStr,storeSelectType,orderType,productIds, staffTel,"",pageNumber,pageSize)
                .subscribe(new BaseObserver<List<TOrder>>(context,true){
                    @Override
                    public void onSuccess(List<TOrder> dataList,String msg) {

                        if (dataList != null && dataList.size() > 0) {
                            activity.orderList.addAll(dataList);
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
                            activity.orderList.addAll(dataList);
                            activity.listAdapter.notifyDataSetChanged();

                            if(activity.orderList != null  &&  activity.orderList.size()>0){
                                if(activity.orderList.size() < 10){
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                }else {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }

                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.listView.setVisibility(View.VISIBLE);
                            }else {

                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.listView.setVisibility(View.GONE);
                                activity.tv_no_data.setText("您现在没有销售记录哦！");
                            }


                        }
                        activity.ptr.onRefreshComplete();
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);

                        activity.listAdapter.notifyDataSetChanged();
                        activity.ptr.onRefreshComplete();
                        activity.ll_no_data.setVisibility(View.VISIBLE);
                        activity.listView.setVisibility(View.GONE);
                        activity.iv_no_data.setImageResource(R.drawable.ic_wifi_not);
                        activity.tv_no_data.setText("您的网络可能睡着了！");

                    }
                });
    }
}
