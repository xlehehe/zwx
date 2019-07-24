package com.zwx.scan.app.feature.shop;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.base.BaseObserver;
import com.zwx.scan.app.data.bean.CardBean;
import com.zwx.scan.app.data.bean.MemberRedEnvelope;
import com.zwx.scan.app.data.bean.MemberRedEnvelopeBean;
import com.zwx.scan.app.data.bean.MemberRedEnvelopeDetailBean;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.OrderObjectBean;
import com.zwx.scan.app.data.bean.ProductCategory;
import com.zwx.scan.app.data.bean.ProductExtendDesc;
import com.zwx.scan.app.data.bean.ProductMedia;
import com.zwx.scan.app.data.bean.ProductResultBean;
import com.zwx.scan.app.data.bean.ProductSetNew;
import com.zwx.scan.app.data.bean.ProductSetNewBean;
import com.zwx.scan.app.data.bean.ProductStore;
import com.zwx.scan.app.data.bean.ProductVerifictionRecordBean;
import com.zwx.scan.app.data.bean.RedEnvelopeDetail;
import com.zwx.scan.app.data.bean.ShopResultBean;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.TOrderConsumeLog;
import com.zwx.scan.app.data.bean.TOrderConsumeLogBean;
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.data.http.service.CampaignServiceManager;
import com.zwx.scan.app.data.http.service.RetrofitServiceManager;
import com.zwx.scan.app.data.http.service.ShopServiceManage;
import com.zwx.scan.app.feature.campaign.CampaignSelectStoreAdapter;
import com.zwx.scan.app.feature.ptmanage.PtNewActivity;
import com.zwx.scan.app.feature.ptmanage.PtNextSettingFragment;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * author : lizhilong
 * time   : 2019/04/18
 * desc   :  商城接口服务
 * version: 1.0
 **/
public class ShopPresenter implements ShopContract.Presenter {

    private ShopContract.View view;
    private ShopServiceManage shopServiceManage;
    private CampaignServiceManager campaignServiceManager;
    //添加订阅，应用于取消订阅，防止造成内存泄漏
    private CompositeDisposable disposable;


    public ShopPresenter(ShopContract.View view) {
        shopServiceManage = new ShopServiceManage();
        this.view = view;
        disposable = new CompositeDisposable();
        campaignServiceManager = new CampaignServiceManager();
    }


    @Override
    public void doQueryOrder(Context context, String userId, String memebrTel, String salesTimeSta, String salesTimesEnd, Integer pageNumber, Integer pageSize, Boolean isRefresh) {


        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("userId", userId);
        params.put("memebrTel", memebrTel);
        params.put("salesTimeSta", salesTimeSta);
        params.put("salesTimesEnd", salesTimesEnd);

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
        ShopOrderActivity activity = (ShopOrderActivity) context;
        shopServiceManage.doQueryOrder(userId, memebrTel, salesTimeSta, salesTimesEnd, pageNumber, pageSize)
                .subscribe(new BaseObserver<OrderObjectBean>(context, false) {
                    @Override
                    public void onSuccess(OrderObjectBean result, String msg) {
                        if (result != null) {
                            List<TOrderObject> dataList = result.getList();
                            /*if (dataList != null && dataList.size() > 0) {
                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.listView.setVisibility(View.VISIBLE);
                                if (isRefresh) {
                                    activity.orderList.clear();
                                    activity.orderList.addAll(dataList);
                                    activity.listAdapter.notifyDataSetChanged();
                                    if (dataList.size() < 10) {
                                        activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                    } else {
                                        activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                    }
                                } else {
                                    activity.orderList.addAll(dataList);
                                    activity.listAdapter.notifyDataSetChanged();
                                    if (dataList.size() < 10) {
                                        activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                    } else {
                                        activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                    }
                                }


                            } else {
                                activity.orderList.clear();
                                activity.orderList.addAll(dataList);
                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.listAdapter.notifyDataSetChanged();
                                activity.listView.setVisibility(View.GONE);
                                activity.tv_no_data.setText("您现在没有订单记录哦！");
                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }*/

                            if (dataList != null && dataList.size() > 0) {

                                for(int i=0;i<dataList.size();i++){
                                    if(dataList.get(i) != null){
                                        if(dataList.get(i).getDetailedId() != null){
                                            String detailedId = String.valueOf(dataList.get(i).getDetailedId());
                                            if(activity.orderList != null &&  activity.orderList.size()>0){
                                                for(int j=0;j<activity.orderList.size();j++){
                                                    if(detailedId.equals(String.valueOf(activity.orderList.get(j).getDetailedId()))){
                                                        activity.orderList.remove(j);
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }


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
                                    activity.tv_no_data.setText("您现在没有订单记录哦！");
                                }


                            }
                            activity.ptr.onRefreshComplete();
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
                                activity.tv_no_data.setText("您现在没有订单记录哦！");
                            }
                        }



                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                        activity.orderList.clear();
                        activity.listAdapter.notifyDataSetChanged();
                        activity.ptr.onRefreshComplete();
                        activity.ll_no_data.setVisibility(View.VISIBLE);
                        activity.listView.setVisibility(View.GONE);
                        activity.tv_no_data.setText("您现在没有订单记录哦！");

                    }
                });
    }
    //2019-05-08 确认订单
    @Override
    public void queryOrderConfirmPost(Context context, String detailedId, String postCompany, String postCode) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("detailedId", detailedId);
        params.put("postCompany", postCompany);
        params.put("postCode", postCode);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        shopServiceManage.queryOrderConfirmPost(detailedId, postCompany, postCode)
                .subscribe(new BaseObserver<String>(context, true) {
                    @Override
                    public void onSuccess(String result, String msg) {

                        if("YES".equals(ShopOrderDetailActivity.isDetail)){
                            ShopOrderDetailActivity activity = (ShopOrderDetailActivity) context;
                            doQueryOrderDetails(context,detailedId);
                        }else {
                            ShopOrderActivity activity = (ShopOrderActivity) context;
                            activity.listAdapter.notifyDataSetChanged();
                            doQueryOrder(context,activity.userId, activity.memberTel, activity.salesTimeSta, activity.salesTimesEnd, activity.pageNumber, activity.pageSize,false);
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);
                    }
                });

    }
    //修改确认邮寄订单状态时 单个订单查询，实现订单列表局部刷新
    @Override
    public void queryOrderDetailForApp(Context context, String orderId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("orderId", orderId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        ShopOrderActivity activity = (ShopOrderActivity) context;
        shopServiceManage.queryOrderDetailForApp(orderId)
                .subscribe(new BaseObserver<TOrderObject>(context, true) {
                    @Override
                    public void onSuccess(TOrderObject result, String msg) {
                        if(activity.orderList != null && activity.orderList.size()>0) {
                            for (int i = 0; i < activity.orderList.size(); i++) {
                                TOrderObject tOrderObject1 = activity.orderList.get(i);
                                if (tOrderObject1.getOrderId() != null) {
                                    String orderId1 = String.valueOf(tOrderObject1.getOrderId());

                                    if (result != null) {
                                        if (result.getOrderId() != null) {
                                            String orderId = String.valueOf(result.getOrderId());
                                            if (orderId1.equals(orderId)) {
                                                //先删除后为该索引添加新数据
                                                activity.orderList.remove(i);
                                                activity.orderList.add(i, result);
                                            }
                                        }
                                    }
                                }
                            }
                            activity.listAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });
    }

    @Override
    public void doQueryOrderDetails(Context context, String detailedId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("detailedId", detailedId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        ShopOrderDetailActivity activity = (ShopOrderDetailActivity) context;
        shopServiceManage.doQueryOrderDetails(detailedId)
                .subscribe(new BaseObserver<List<TOrderObject>>(context, true) {
                    @Override
                    public void onSuccess(List<TOrderObject> orderObjectList, String msg) {

                        if (orderObjectList != null && orderObjectList.size() > 0) {
                            TOrderObject result = orderObjectList.get(0);
                            activity.ll_no_data.setVisibility(View.GONE);
                            activity.list_view.setVisibility(View.VISIBLE);
                            if (result != null) {

                                activity.orderObject = result;
                                activity.stateDetail = activity.orderObject.getStatus();

                                if(activity.stateDetail != null && !"".equals(activity.stateDetail)){
                                    if(activity.stateDetail.equals(activity.stateListItem)){  //邮寄状态不一致的话 更新局部更新

                                    }
                                }
                                if (result.getOrderCode() != null) {
                                    activity.orderCode = String.valueOf(result.getOrderCode());

                                }
                                activity.tv_order_num.setText(activity.orderCode);
                                activity.delivType = result.getDelivType();

                                if (activity.delivType != null && !"".equals(activity.delivType)) {
                                    activity.tv_ziti.setText(activity.delivType);
                                } else {
                                    activity.tv_ziti.setText("—");
                                }


                                activity.time = result.getSalesTime();
                                if (activity.time != null && !"".equals(activity.time)) {

                                    activity.tv_time.setText(activity.time.replace("-", "."));
                                } else {
                                    activity.tv_time.setText("—");
                                }
                                activity.payAmt = "—";
                                BigDecimal pri = result.getPayAmount();
                                if (pri != null && pri.doubleValue() > 0) {
                                    activity.payAmt = RegexUtils.getDoubleString(pri.doubleValue());
                                    //支付方式 显示
                                    activity.ll_pay.setVisibility(View.VISIBLE);
                                } else {
                                    activity.ll_pay.setVisibility(View.GONE);
                                }
                                activity.tv_order_pay_amt.setText(activity.payAmt);


                                BigDecimal usrRedEnvelope = result.getUseRedEnvelope();
                                activity.redpacket = "-";
                                if (usrRedEnvelope != null && usrRedEnvelope.doubleValue() > 0) {
                                    activity.redpacket = RegexUtils.getDoubleString(usrRedEnvelope.doubleValue());
                                    activity.rl_red_packet.setVisibility(View.VISIBLE);
                                } else {
                                    activity.rl_red_packet.setVisibility(View.GONE);
                                }
                                activity.tv_red_packet.setText(activity.redpacket);
                                activity.tv_pay_red_pack.setText(activity.redpacket);
                                activity.goodAmt = "—";

                                //购买数量
                                Long buySum = result.getBuyCount();
                                int buyCount = 0;
                                if (buySum != null && buySum.intValue() > 0) {
                                    buyCount = buySum.intValue();
                                }
                                activity.goodAmt = "—";
                                //商品总金额 单价乘购买数量
                                BigDecimal totalPrice = result.getUnitPrice();
                                if (totalPrice != null && totalPrice.doubleValue() > 0) {
                                    activity.goodAmt = RegexUtils.getDoubleString(totalPrice.doubleValue() * buyCount);

                                }
                                activity.tv_good_amt.setText(activity.goodAmt);


                                activity.buyLiuYan = result.getBuyMsg();

                                activity.tv_buy_liuyan.setText(activity.buyLiuYan != null && !"".equals(activity.buyLiuYan) ? activity.buyLiuYan : "—");


                                activity.memberName = result.getMemberName();
                                activity.phone = result.getMemberTel();
                                activity.tv_member_info.setText(activity.memberName != null && !"".equals(activity.memberName) ? activity.memberName : "—");
                                activity.tv_phone.setText(activity.phone != null && !"".equals(activity.phone) ? activity.phone : "—");

                                //支付宝——z 微信——w 银联——y  红包 -- r
                                activity.payWay = result.getPayWay();

                                if ("z".equals(activity.payWay)) {
                                    activity.tv_pay_way.setText("支付宝");
                                } else if ("w".equals(activity.payWay)) {
                                    activity.tv_pay_way.setText("微信");
                                } else if ("y".equals(activity.payWay)) {
                                    activity.tv_pay_way.setText("银联");
                                } else if ("r".equals(activity.payWay)) {
                                }


                                activity.payNum = result.getPayId();
                                activity.tv_pay_amt.setText(activity.payAmt);

                            }

                            if (activity.payNum != null && !"".equals(activity.payNum)) {
                                activity.tv_pay_num.setText(activity.payNum);
                            } else {
                                activity.tv_pay_num.setText("-");
                            }
                            activity.status = result.getStatus();

                            if(activity.status != null &&  !"".equals(activity.status)){
                                if("待邮寄".equals(activity.status)){
                                    activity.ll_shou_goods_info.setVisibility(View.VISIBLE);
                                    activity.ll_post_info.setVisibility(View.GONE);
                                    activity.ll_coupon_info.setVisibility(View.GONE);
                                    activity.tv_right.setVisibility(View.VISIBLE);
                                }else if("已邮寄".equals(activity.status)){
                                    activity.ll_shou_goods_info.setVisibility(View.VISIBLE);
                                    activity.ll_post_info.setVisibility(View.VISIBLE);
                                    activity.ll_coupon_info.setVisibility(View.GONE);
                                    activity.tv_right.setVisibility(View.GONE);
                                }else {
                                    activity.ll_shou_goods_info.setVisibility(View.GONE);
                                    activity.ll_post_info.setVisibility(View.GONE);
                                    activity.ll_coupon_info.setVisibility(View.VISIBLE);
                                    activity.tv_right.setVisibility(View.GONE);
                                }
                            }

                            //收货地址 收货人  收回电话
                            activity.memberAddress = result.getMemberAddress();
                            activity.receiverTel = result.getReceiverTel();
                            activity.receiver = result.getReceiver();
                            //和快递公司名称地址
                            activity.postCompany = result.getPostCompany();
                            activity.postCode = result.getPostCode();

                            if(activity.memberAddress != null &&  !"".equals(activity.memberAddress)){
                                activity.tv_shouhuoren_address.setText(activity.memberAddress);
                            }else {
                                activity.tv_shouhuoren_address.setText("—");
                            }

                            if(activity.receiverTel != null &&  !"".equals(activity.receiverTel)){
                                activity.tv_shouhuoren.setText(activity.receiverTel);
                            }else {
                                activity.tv_shouhuoren.setText("—");
                            }

                            if(activity.receiverTel != null &&  !"".equals(activity.receiverTel)){
                                activity.tv_shouhuoren_tel.setText(activity.receiverTel);
                            }else {
                                activity.tv_shouhuoren_tel.setText("—");
                            }

                            if(activity.postCompany != null &&  !"".equals(activity.postCompany)){
                                activity.tv_company_name.setText(activity.postCompany);
                            }else {
                                activity.tv_company_name.setText("—");
                            }

                            if(activity.postCode != null &&  !"".equals(activity.postCode)){
                                activity.tv_post_order_id.setText(activity.postCode);
                            }else {
                                activity.tv_post_order_id.setText("—");
                            }

                            activity.orderObjectList = orderObjectList;
                            String productTotalPrice = "-";
                            Double totalPrice = 0d;
                            for (int i = 0; i < activity.orderObjectList.size(); i++) {
                                TOrderObject orderObject = activity.orderObjectList.get(i);

                                if (orderObject.getUnitPrice() != null && orderObject.getUnitPrice().doubleValue() > 0) {
                                    Double price = orderObject.getUnitPrice().doubleValue();
                                    totalPrice += price;
                                }

                            }
                            if (totalPrice != null && totalPrice > 0) {
                                productTotalPrice = RegexUtils.getDoubleString(totalPrice);
                            }
                            //商品总额
                            activity.tv_good_amt.setText(productTotalPrice);
                            if (activity.orderObjectList != null && activity.orderObjectList.size() > 0) {
                                activity.adapter = new ShopOrderDetailAdapter(context, activity.orderObjectList);
                                activity.list_view.setAdapter(activity.adapter);
                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.list_view.setVisibility(View.VISIBLE);
                            } else {
                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.list_view.setVisibility(View.GONE);
                                activity.tv_no_data.setText("您现在还没有订单哦");
                            }

                        }


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);
                        activity.orderObjectList.clear();
                        activity.adapter = new ShopOrderDetailAdapter(context, activity.orderObjectList);
                        activity.list_view.setAdapter(activity.adapter);
                        activity.ll_no_data.setVisibility(View.VISIBLE);
                        activity.list_view.setVisibility(View.GONE);
                        activity.tv_no_data.setText("您现在还没有订单哦");
                    }
                });
    }

    @Override
    public void addProductCategory(Context context, String catName, String salesType, String userId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("catName", catName);
        params.put("salesType", salesType);
        params.put("userId", userId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        ShopClassFragment activity = (ShopClassFragment) view;
        shopServiceManage.addProductCategory(catName, salesType, userId)
                .subscribe(new BaseObserver<Map<String, String>>(context, true) {
                    @Override
                    public void onSuccess(Map<String, String> result, String msg) {

                        activity.listAdapter.notifyDataSetChanged();
                        queryProductCategory(activity.getActivity(), SPUtils.getInstance().getString("userId"), true);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.listAdapter.notifyDataSetChanged();
                        activity.ptr.onRefreshComplete();
                    }
                });
    }

    @Override
    public void updateProductCategory(Context context, String catId, String catName) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("catId", catId);
        params.put("catName", catName);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        ShopClassFragment activity = (ShopClassFragment) view;
        shopServiceManage.updateProductCategory(catId, catName)
                .subscribe(new BaseObserver<String>(context, true) {
                    @Override
                    public void onSuccess(String result, String msg) {

                        activity.listAdapter.notifyDataSetChanged();
                        queryProductCategory(activity.getActivity(), SPUtils.getInstance().getString("userId"), false);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.listAdapter.notifyDataSetChanged();

                    }
                });
    }

    @Override
    public void queryProductCategory(Context context, String userId, Boolean isRefresh) {

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
        ShopClassFragment activity = (ShopClassFragment) view;
        shopServiceManage.queryProductCategory(userId)
                .subscribe(new BaseObserver<List<ProductCategory>>(context, true) {
                    @Override
                    public void onSuccess(List<ProductCategory> dataList, String msg) {
                       /* if (dataList != null && dataList.size() > 0) {
                            activity.categoryList.clear();
                            activity.categoryList.addAll(dataList);
                            activity.listAdapter.notifyDataSetChanged();
                            activity.ptr.onRefreshComplete();

                        } else {
                            activity.listAdapter.notifyDataSetChanged();
                            activity.ptr.onRefreshComplete();
                        }*/


                        if (dataList!=null && dataList.size()>0) {
                            activity.ll_no_data.setVisibility(View.GONE);
                            activity.rvList.setVisibility(View.VISIBLE);
                           /* if (isRefresh) {
                                activity.campaignList.clear();

                            } else {


                            }*/
                            for(int i=0;i<dataList.size();i++){
                                if(dataList.get(i) != null){
                                    if(dataList.get(i).getCatId() != null){
                                        String catId = String.valueOf(dataList.get(i).getCatId());
                                        if(activity.categoryList != null &&  activity.categoryList.size()>0){
                                            for(int j=0;j<activity.categoryList.size();j++){
                                                if(catId.equals(String.valueOf(activity.categoryList.get(j).getCatId()))){
                                                    activity.categoryList.remove(j);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                            activity.categoryList.addAll(dataList);
                            activity.listAdapter.notifyDataSetChanged();
                            activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            /*if (dataList.size() < 10) {
                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            } else {
                                activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                            }*/
                            activity.ptr.onRefreshComplete();

                        }else {
                            activity.listAdapter.notifyDataSetChanged();
                            if(activity.categoryList != null && activity.categoryList.size()>0){
                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                /*if(activity.categoryList.size() < 10){
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                }else {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }*/
                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.rvList.setVisibility(View.VISIBLE);
                            }else {
                                activity.listAdapter.notifyDataSetChanged();
                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.rvList.setVisibility(View.GONE);
                                activity.tv_no_data.setText("您现在没有商品分类记录哦！");
                            }

                            activity.ptr.onRefreshComplete();
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                        activity.listAdapter.notifyDataSetChanged();
                        activity.ll_no_data.setVisibility(View.VISIBLE);
                        activity.rvList.setVisibility(View.GONE);
                        activity.tv_no_data.setText("您现在没有商品分类记录哦！");
                    }
                });
    }

    @Override
    public void deleteProductCategory(Context context, String catId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("catId", catId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        ShopClassFragment activity = (ShopClassFragment) view;
        shopServiceManage.deleteProductCategory(catId)
                .subscribe(new BaseObserver<String>(context, true) {
                    @Override
                    public void onSuccess(String result, String msg) {
                        activity.categoryList.remove(activity.adapterPosition);
                        activity.listAdapter.notifyDataSetChanged();
                        queryProductCategory(activity.getActivity(), SPUtils.getInstance().getString("userId"), false);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.listAdapter.notifyDataSetChanged();
                        ToastUtils.showShort(message);
                    }
                });
    }

    @Override
    public void queryStoreByCompId(Context context, String userId) {


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
        ShopTelFragment activity = (ShopTelFragment) view;
        shopServiceManage.queryStoreByCompId(userId)
                .subscribe(new BaseObserver<List<Store>>(context, true) {
                    @Override
                    public void onSuccess(List<Store> storeList, String msg) {
                        if (storeList != null && storeList.size() > 0) {

                            ShopTelFragment.storeList = storeList;
                            activity.adapter = new ShopTelListAdapter(activity.getActivity(), ShopTelFragment.storeList);
                            activity.listView.setAdapter(activity.adapter);
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });
    }

    @Override
    public void updateShopTel(Context context, String storeId, String shopTel) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("storeId", storeId);
        params.put("shopTel", shopTel);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        ShopTelFragment activity = (ShopTelFragment) view;
        shopServiceManage.updateShopTel(storeId, shopTel)
                .subscribe(new BaseObserver<String>(context, false) {
                    @Override
                    public void onSuccess(String result, String msg) {
//                        ToastUtils.showCustomShortBottom("成功");
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });
    }

    @Override
    public void queryProductSet(Context context, String userId, String productName, String productCode, Integer pageSize, Integer pageNumber, boolean isRefresh) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("userId", userId);
        params.put("productName", productName);
        params.put("productCode", productCode);
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
        ShopManageActivity activity = (ShopManageActivity) context;
        shopServiceManage.queryProductSet(userId, productName, productCode, pageSize, pageNumber)
                .subscribe(new BaseObserver<ProductSetNewBean>(context, true) {
                    @Override
                    public void onSuccess(ProductSetNewBean productSetNewBean, String msg) {


                        if (productSetNewBean != null) {
                            List<ProductSetNew> dataList = productSetNewBean.getList();
                            boolean isLastPage = productSetNewBean.isLastPage();
                            if (dataList != null && dataList.size() > 0) {
                                //删除旧数据 这个在更新上架 下架 调整库 取消/设置热卖等状态改变删除旧数据，否则不删除

                                for(int i=0;i<dataList.size();i++){
                                    if(dataList.get(i) != null){
                                        if(dataList.get(i).getSellGoodsId() != null){
                                            String goodId = String.valueOf(dataList.get(i).getSellGoodsId());
                                            if(activity.productSetNewList != null &&  activity.productSetNewList.size()>0){
                                                for(int j=0;j<activity.productSetNewList.size();j++){
                                                    if(goodId.equals(String.valueOf(activity.productSetNewList.get(j).getSellGoodsId()))){
                                                        activity.productSetNewList.remove(j);
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }


                                activity.productSetNewList.addAll(dataList);
                                activity.listAdapter.notifyDataSetChanged();
                                activity.ptr.setPullToRefreshOverScrollEnabled(true);
                                if (dataList.size() < 10) {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                } else {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }
                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.list_view.setVisibility(View.VISIBLE);

                            } else {
                                activity.productSetNewList.addAll(dataList);
                                activity.listAdapter.notifyDataSetChanged();
                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

                                if(activity.productSetNewList != null  &&  activity.productSetNewList.size()>0){
                                    if(activity.productSetNewList.size() < 10){
                                        activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                    }else {
                                        activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                    }
                                    activity.ll_no_data.setVisibility(View.GONE);
                                    activity.list_view.setVisibility(View.VISIBLE);
                                }else {

                                    activity.ll_no_data.setVisibility(View.VISIBLE);
                                    activity.list_view.setVisibility(View.GONE);
                                    activity.tv_no_data.setText("您现在没有商品记录哦！");
                                }


                            }
                            activity.ptr.onRefreshComplete();

                        } else {
                            activity.productSetNewList.clear();
                            activity.listAdapter.notifyDataSetChanged();
                            activity.ptr.onRefreshComplete();
                            activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            activity.ll_no_data.setVisibility(View.VISIBLE);
                            activity.list_view.setVisibility(View.GONE);
                            activity.tv_no_data.setText("您现在没有商品记录哦！");
                        }



                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                        activity.listAdapter.notifyDataSetChanged();
                        activity.ptr.onRefreshComplete();
                        activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        activity.ll_no_data.setVisibility(View.VISIBLE);
                        activity.list_view.setVisibility(View.GONE);
                        activity.tv_no_data.setText("您现在没有会员红包记录哦！");


                    }
                });
    }

    //进入编辑页面修改内容 返回列表局部刷新调用
    @Override
    public void queryProductInfo(Context context,String productId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("productId", productId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        ShopManageActivity activity = (ShopManageActivity) context;
        shopServiceManage.queryProductInfo(productId)
                .subscribe(new BaseObserver<ProductSetNew>(context, false) {
                    @Override
                    public void onSuccess(ProductSetNew productSetNew, String msg) {

                        if(activity.productSetNewList != null && activity.productSetNewList.size()>0){
                            for (int i = 0;i< activity.productSetNewList.size();i++){
                                ProductSetNew productSetNew1 = activity.productSetNewList.get(i);
                                if(productSetNew1.getSellGoodsId() != null){
                                    String sellGoodId1 = String .valueOf(productSetNew1.getSellGoodsId());

                                    if(productSetNew != null){
                                        if(productSetNew.getSellGoodsId() != null){
                                            String sellGoodId = String .valueOf(productSetNew.getSellGoodsId());
                                            if(sellGoodId1.equals(sellGoodId)){
                                                //先删除后为该索引添加新数据
                                                activity.productSetNewList.remove(i);
                                                activity.productSetNewList.add(i,productSetNew);
                                            }
                                        }
                                    }
                                }
                            }
                            activity.listAdapter.notifyDataSetChanged();
                        }




                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                    }
                });
    }

    @Override
    public void doDelete(Context context, String productId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("productId", productId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        ShopManageActivity activity = (ShopManageActivity) context;
        shopServiceManage.doDelete(productId)
                .subscribe(new BaseObserver<String>(context, true) {
                    @Override
                    public void onSuccess(String result, String msg) {

                        String userId = SPUtils.getInstance().getString("userId");

                        queryProductSet(activity, userId, activity.memberName, activity.memberTel, activity.pageSize, activity.pageNumber, false);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.listAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void doUpdate(Context context, String productId, String operateFlag, String stockNum) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("productId", productId);
        params.put("operateFlag", operateFlag);
        params.put("stockNum", stockNum);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        ShopManageActivity activity = (ShopManageActivity) context;
        shopServiceManage.doUpdate(productId, operateFlag, stockNum)
                .subscribe(new BaseObserver<String>(context, false) {
                    @Override
                    public void onSuccess(String result, String msg) {
                        String userId = SPUtils.getInstance().getString("userId");
//                        queryProductSet(activity, userId, activity.memberName, activity.memberTel, activity.pageSize, activity.pageNumber, false);
                        queryProductInfo(context,productId);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);
                    }
                });
    }

    /**
     * 调整库存
     */
    @Override
    public void updateProductStock(Context context, String userId, String productId, String stockChange, String changeState) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("userId", userId);
        params.put("productId", productId);
        params.put("stockChange", stockChange);
        params.put("changeState", changeState);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        ShopManageActivity activity = (ShopManageActivity) context;
        shopServiceManage.updateProductStock(userId, productId, stockChange, changeState)
                .subscribe(new BaseObserver<Map<String, String>>(context, false) {
                    @Override
                    public void onSuccess(Map<String, String> result, String msg) {
                        String userId = SPUtils.getInstance().getString("userId");
                        queryProductSet(activity, userId, activity.memberName, activity.memberTel, activity.pageSize, activity.pageNumber, false);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);
                    }
                });
    }

    @Override
    public void queryStoreList(Context context, String userId) {

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
        ShopManageNewActivity activity = (ShopManageNewActivity) context;
        shopServiceManage.queryStoreList(userId)
                .subscribe(new BaseObserver<MoreStoreBean>(context, false) {
                    PtNextSettingFragment fragment = null;
                    PtNewActivity campaignNewActivity = null;

                    @Override
                    public void onSuccess(MoreStoreBean moreStoreBean, String msg) {


                        if (moreStoreBean != null) {
                            activity.branList = moreStoreBean.getBrandList();
                            if (activity.branList != null && activity.branList.size() > 0) {
                                if (activity.branList.get(0) != null) {
                                    activity.storeList = activity.branList.get(0).getStoreList();
                                }
                            } else {
                                activity.tv_store_name.setText("# 全部店铺");
                            }
                        }
                        queryCategory(context, userId);

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        if ("EDIT".equals(activity.productEditStatu)) { //编辑
                            doEdit(context, activity.productId);
                        }
                    }
                });

    }

    @Override
    public void queryCategory(Context context, String userId) {


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
        ShopManageNewActivity activity = (ShopManageNewActivity) view;
        shopServiceManage.queryProductCategory(userId)
                .subscribe(new BaseObserver<List<ProductCategory>>(context, true) {
                    @Override
                    public void onSuccess(List<ProductCategory> productCategoryList, String msg) {
                        if (productCategoryList != null && productCategoryList.size() > 0) {
                            for (ProductCategory productCategory : productCategoryList) {
                                String productId = String.valueOf(productCategory.getCatId());
                                String productName = productCategory.getCatName();

                                CardBean cardBean = new CardBean();
                                cardBean.setId(productId);
                                cardBean.setCardNo(productName);
                                activity.categoryList.add(cardBean);

                                if ("EDIT".equals(activity.productEditStatu)) { //编辑
                                } else {
                                    activity.initPickerCate();
                                }

                            }

                        }

                        if ("EDIT".equals(activity.productEditStatu)) { //编辑
                            doEdit(context, activity.productId);
                        }


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        if ("EDIT".equals(activity.productEditStatu)) { //编辑
                            doEdit(context, activity.productId);
                        }
                    }
                });
    }

    @Override
    public void addCategory(Context context, String catName, String salesType, String userId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("catName", catName);
        params.put("salesType", salesType);
        params.put("userId", userId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        ShopManageNewActivity activity = (ShopManageNewActivity) context;
        shopServiceManage.addProductCategory(catName, salesType, userId)
                .subscribe(new BaseObserver<Map<String, String>>(context, true) {
                    @Override
                    public void onSuccess(Map<String, String> result, String msg) {

                        if (result != null && result.size() > 0) {
                            activity.cateId = result.get("catId");
                            activity.cateName = result.get("catName");
                            activity.tv_product_cate.setText(activity.cateName);
                        }
                        queryCategory(context, userId);
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
        String timestamp = String.valueOf(new Date().getTime());

        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        ShopManageNewActivity activity = (ShopManageNewActivity) context;

        campaignServiceManager.uploadFiles(fileList)
                .subscribe(new BaseObserver<Map<String, Object>>(context, true) {
                    @Override
                    public void onSuccess(Map<String, Object> map, String msg) {
                        String imageId = "";
                        if (map != null && map.size() > 0) {
                            imageId = (String) map.get("id");

                            if (imageId != null && !"".equals(imageId)) {
                                if (imageId.contains(",")) {
                                    String[] ids = imageId.split(",");

                                    for (int i = 0; i < ids.length; i++) {
                                        ProductMedia productMedia = new ProductMedia();
                                        productMedia.setImgUrl(ids[i]);
                                        activity.productMediaList.add(productMedia);
                                        if (activity.selectList != null && activity.selectList.size() > 0) {
                                            for (LocalMedia localMedia : activity.selectList) {
                                                if (localMedia != null) {
                                                    String path = localMedia.getPath();
                                                    if (path != null && !"".equals(path) && !path.contains("get.do")) {
                                                        localMedia.setCompressPath(HttpUrls.IMAGE_ULR + ids[i]);
                                                        localMedia.setCompressed(true);
                                                        localMedia.setPath(HttpUrls.IMAGE_ULR + ids[i]);
                                                        break;

                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    ProductMedia productMedia = new ProductMedia();
                                    productMedia.setImgUrl(imageId);
                                    activity.productMediaList.add(productMedia);
                                }
                            }
                            activity.removeMediaList(activity.productMediaList);
                            activity.adapter.notifyDataSetChanged();

                           /* if (activity.productMediaList != null && activity.productMediaList.size() > 0) {
                                activity.setJson();
                                saveProductSetInfo(context, activity.params, activity.userId, activity.isSaveAndPush);
                            }*/

                           uploadGoodDetailImageFiles(context,activity.productExtendDescList);

                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.btn_push.setEnabled(true);
                    }
                });
    }



    @Override
    public void uploadGoodDetailImageFiles(Context context, List<ProductExtendDesc> productExtendDescList) {
        ShopManageNewActivity activity = (ShopManageNewActivity) context;

        if(productExtendDescList != null && productExtendDescList.size()>0){
            for (ProductExtendDesc productExtendDesc : productExtendDescList){
                String path = productExtendDesc.getProductContent();
                String type =productExtendDesc.getMediaType();
                if("1".equals(type)){
                    if(path.contains("storage")){
                        List<File> fileList = new ArrayList<>();
                        File file = new File(path);

                        fileList.add(file);
                        Map<String, String> params = new HashMap<String, String>();
                        String token = SPUtils.getInstance().getString("token");
                        String timestamp = String.valueOf(new Date().getTime());

                        params.put("token", token);
                        params.put("timestamp", timestamp);
                        String signature = EncryptUtils.createSign(params);
                        params = new HashMap<>();
                        params.put("token", token);
                        params.put("timestamp", timestamp);
                        params.put("signature", signature);
                        RetrofitServiceManager.setHeadTokenMap(params);


                        shopServiceManage.uploadFiles(fileList)
                                .subscribe(new BaseObserver<Map<String, Object>>(context, false) {
                                    @Override
                                    public void onSuccess(Map<String, Object> map, String msg) {
                                        String imageId = "";
                                        if (map != null && map.size() > 0) {
                                            imageId = (String) map.get("id");

                                            if (imageId != null && !"".equals(imageId)) {
                                                productExtendDesc.setProductContent(imageId);
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


            }

            //延迟加载
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    //休眠3秒
                    try{
                        Thread.sleep(3000);
                        activity.setJson();
                        saveProductSetInfo(context, activity.params, activity.userId, activity.isSaveAndPush);
                    }catch (InterruptedException ex){

                    }

                }
            }.start();

        }else {
            if (activity.productMediaList != null && activity.productMediaList.size() > 0) {
                activity.setJson();
                saveProductSetInfo(context, activity.params, activity.userId, activity.isSaveAndPush);
            }

        }




    }

    @Override
    public void saveProductSetInfo(Context context, Map<String, Object> data, String userId, String isSaveAndPush) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("jsonstr", new Gson().toJson(data));
        params.put("userId", userId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        ShopManageNewActivity activity = (ShopManageNewActivity) context;
        shopServiceManage.saveProductSetInfo(activity.jsonStr, userId)
                .subscribe(new BaseObserver<ShopResultBean>(context, true) {
                    @Override
                    public void onSuccess(ShopResultBean result, String msg) {

                        if ("save".equals(isSaveAndPush)) {
                            ToastUtils.showCustomShortBottom("保存成功");
                            activity.btn_save.setEnabled(true);
                        } else {
                            ToastUtils.showCustomShortBottom("上架成功");
                            activity.btn_push.setEnabled(true);
                        }

                        if("NEW".equals(activity.productEditStatu)){
                            ActivityUtils.startActivity(ShopManageActivity.class, R.anim.slide_in_right, R.anim.slide_out_left);
                            activity.finish();
                        }else if("EDIT".equals(activity.productEditStatu)){
                            EventBus.getDefault().post(new ProductEventBus(activity.productId,"listFlush",""));
                            activity.finish();
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);
                        if ("save".equals(isSaveAndPush)) {
                            activity.btn_save.setEnabled(true);
                        } else {
                            activity.btn_push.setEnabled(true);
                        }
                    }
                });

    }

    @Override
    public void doEdit(Context context, String productId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("productId", productId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        ShopManageNewActivity activity = (ShopManageNewActivity) context;
        shopServiceManage.doEdit(productId)
                .subscribe(new BaseObserver<ProductResultBean>(context, true) {
                    @Override
                    public void onSuccess(ProductResultBean result, String msg) {
                        if (result != null) {

                            activity.productSetNew = result.getProductSet();
                            activity.productExtend = result.getProductExtend();
                            activity.productMediaList = result.getProductMediaList();
                            activity.productStoreList = result.getProductStoreList();
                            activity.productExtendDescList = result.getRoductExtendDescList();

                            if(activity.productExtendDescList == null || activity.productExtendDescList.size() == 0){
                                activity.productExtendDescList = new ArrayList<ProductExtendDesc>();
                            }

                            if(activity.productExtendDescList != null && activity.productExtendDescList.size()>0){
                                for (ProductExtendDesc productExtendDesc : activity.productExtendDescList){
                                    String type = productExtendDesc.getMediaType();
                                    String content = productExtendDesc.getProductContent();
                                    if("1".equals(type)){
                                        activity.addGoodsImage(HttpUrls.IMAGE_ULR + content);
                                    }else if("2".equals(type)){
                                        activity.addGoodsEdit(content);
                                    }
                                }
                            }
                            if (activity.productSetNew != null) {
                                activity.productStatu = activity.productSetNew.getState();
                                if ("n".equals(activity.productStatu) || "o".equals(activity.productStatu)) {

                                    activity.rl_bottom.setVisibility(View.VISIBLE);
                                } else if ("s".equals(activity.productStatu)) {//销售中 只能预览
                                    activity.rl_bottom.setVisibility(View.GONE);
                                }
                                activity.productName = activity.productSetNew.getProductName();
                                //商品名称
                                if (activity.productName != null && !"".equals(activity.productName)) {
                                    activity.edt_product_name.setText(activity.productName);
                                }
                                //分类名称
                                activity.productCate = activity.productSetNew.getCatName();
                                if (activity.productCate != null && !"".equals(activity.productCate)) {
                                    activity.tv_product_cate.setText(activity.productCate);
                                }


                                //分类
                                if (activity.productSetNew.getCatId() != null) {
                                    activity.cateId = String.valueOf(activity.productSetNew.getCatId());
                                }

                                if (activity.categoryList != null && activity.categoryList.size() > 0) {
                                    for (CardBean cardBean : activity.categoryList) {
                                        String cateId2 = cardBean.getId();

                                        if (cateId2.equals(activity.cateId)) {
                                            activity.tv_product_cate.setText(cardBean.getCardNo());
                                            break;
                                        }
                                    }
                                }
                                activity.initPickerCate();

                                if (activity.productSetNew.getUnitPrice() != null && activity.productSetNew.getUnitPrice().doubleValue() > 0) {
                                    activity.salePrice = RegexUtils.getDoubleString(activity.productSetNew.getUnitPrice().doubleValue());
                                }
                                activity.edt_sale_price.setText(activity.salePrice);
                                //市场价
                                if (activity.productSetNew.getMarketPrice() != null && activity.productSetNew.getMarketPrice().doubleValue() > 0) {
                                    activity.marketPrice = RegexUtils.getDoubleString(activity.productSetNew.getMarketPrice().doubleValue());
                                } else {
                                    activity.marketPrice = "";
                                }
                                activity.edt_market_price.setText(activity.marketPrice);
                                //库存数量
                                if (activity.productSetNew.getGroundingCount() != null && activity.productSetNew.getGroundingCount().intValue() > 0) {
                                    activity.groundingCount = String.valueOf(activity.productSetNew.getGroundingCount());

                                } else {
                                    activity.groundingCount = "";
                                }
                                activity.edt_stock.setText(activity.groundingCount);


                                //返利红包
                                activity.rebateFlag = activity.productSetNew.getRebateFlag();
                                if ("1".equals(activity.rebateFlag)) {  //返利红包 是
                                    activity.rl_rebate.setVisibility(View.VISIBLE);
                                    activity.rebate_amt_tip.setVisibility(View.VISIBLE);
                                    activity.tv_rebate_amt_is.setText("是");
                                    if (activity.productSetNew.getRebateAmt() != null && activity.productSetNew.getRebateAmt().doubleValue() > 0) {
                                        activity.rebateAmt = RegexUtils.getDoubleString(activity.productSetNew.getRebateAmt().doubleValue());
                                        activity.edt_rebate_amt.setText(activity.rebateAmt);
                                    }else {
                                        if(activity.productSetNew.getRebateAmt()==null){
                                            activity.edt_rebate_amt.setText("");
                                        }else {
                                            activity.edt_rebate_amt.setText("0");
                                        }
                                    }

                                } else { //否 不展示
                                    activity.rebateAmt = null;
                                    activity.tv_rebate_amt_is.setText("否");
                                    activity.rl_rebate.setVisibility(View.GONE);
                                    activity.rebate_amt_tip.setVisibility(View.GONE);
                                }


                            }

                            if (activity.productExtend != null) {
                                //商品规格
                                if (activity.productExtend.getProductSpec() != null && !"".equals(activity.productExtend.getProductSpec())) {
                                    activity.productSpec = activity.productExtend.getProductSpec();

                                } else {
                                    activity.productSpec = "";
                                }

                                activity.edt_product_spec.setText(activity.productSpec);


                                activity.deliveryType = activity.productExtend.getDeliveryType();
                                if ("0".equals(activity.deliveryType)) {
                                    activity.tv_select_post_way.setText("到店自提");
                                    activity.tv_ziti_type.setText("到店自提");
                                    activity.ll_ziti.setVisibility(View.VISIBLE);
                                    activity.ll_youji.setVisibility(View.GONE);
                                    activity.isSelectZiti = true;
                                    activity.isSelectYouji = false;
                                    activity.iv_youji_clear.setVisibility(View.GONE);

                                }else if ("1".equals(activity.deliveryType)) {
                                    activity.tv_select_post_way.setText("快递邮寄");
                                    activity.tv_youji_type.setText("快递邮寄");
                                    activity.ll_ziti.setVisibility(View.GONE);
                                    activity.ll_youji.setVisibility(View.VISIBLE);
                                    activity.isSelectZiti = false;
                                    activity.isSelectYouji = true;
                                    activity.iv_ziti_clear.setVisibility(View.GONE);
                                }else if("2".equals(activity.deliveryType)){
                                    activity.tv_select_post_way.setText("到店自提、快递邮寄");
                                    activity.tv_ziti_type.setText("到店自提");
                                    activity.tv_youji_type.setText("快递邮寄");
                                    activity.ll_ziti.setVisibility(View.VISIBLE);
                                    activity.ll_youji.setVisibility(View.VISIBLE);

                                    activity.isSelectZiti = true;
                                    activity.isSelectYouji = true;
                                    if("s".equals(activity.productSetNew.getState())){
                                        activity.iv_youji_clear.setVisibility(View.GONE);
                                        activity.iv_ziti_clear.setVisibility(View.GONE);
                                    }else {
                                        activity.iv_youji_clear.setVisibility(View.VISIBLE);
                                        activity.iv_ziti_clear.setVisibility(View.VISIBLE);
                                    }

                                }
                                //到店自提类型
                                activity.deliveryDaytype = activity.productExtend.getDeliveryDaytype();
                                //有效时间
                                if ("R1".equals(activity.deliveryDaytype)) {// 固定时长
                                    activity.rl_valid_time.setVisibility(View.VISIBLE);
                                    activity.ll_valid_time.setVisibility(View.GONE);

                                    activity.delivEnddate = null;
                                    activity.delivStartdate = null;

                                    //至少1天
                                    if (activity.productExtend.getDelivDay() != null && !"".equals(activity.productExtend.getDelivDay()) && !"0".equals(activity.productExtend.getDelivDay())) {
                                        activity.delivDay = String.valueOf(activity.productExtend.getDelivDay().intValue());
                                        activity.edt_lingqu_day.setText(activity.delivDay);
                                    }

                                } else if ("A".equals(activity.deliveryDaytype)) {//固定时间
                                    activity.delivDay = null;
                                    activity.rl_valid_time.setVisibility(View.GONE);
                                    activity.ll_valid_time.setVisibility(View.VISIBLE);

                                    String enddate = activity.productExtend.getDelivEnddate();
                                    String startdate = activity.productExtend.getDelivStartdate();

                                    if (enddate != null && !"".equals(enddate)) {
                                        Date date = DateUtils.parse(enddate, "yyyy-MM-dd");
                                        activity.delivEnddate = DateUtils.formatDate(date, "yyyy-MM-dd").replace("-", ".");
                                    }
                                    activity.tv_valid_end_time.setText(activity.delivEnddate);

                                    if (startdate != null && !"".equals(startdate)) {
                                        Date date = DateUtils.parse(startdate, "yyyy-MM-dd");
                                        activity.delivStartdate = DateUtils.formatDate(date, "yyyy-MM-dd").replace("-", ".");
                                    }
                                    activity.tv_valid_start_time.setText(activity.delivStartdate);


                                }

                                //快递邮寄
                                if(activity.productExtend.getDeliveryFee()!= null && activity.productExtend.getDeliveryFee().doubleValue()>0){
                                    activity.deliveryFee  = RegexUtils.getDoubleString(activity.productExtend.getDeliveryFee().doubleValue());
                                    activity.youjiFeeIsDisplayFlag =  "0";  //消费支付类型
                                    activity.tv_youji_fee_type.setText("消费支付");
                                    activity.edt_youji_fee.setText(activity.deliveryFee);
                                    activity.rl_youji_fee.setVisibility(View.VISIBLE);
                                }else {
                                    activity.deliveryFee = null;
                                    activity.youjiFeeIsDisplayFlag =  "1";  //包邮类型
                                    activity.tv_youji_fee_type.setText("包邮");
                                    activity.rl_youji_fee.setVisibility(View.GONE);
                                }

                            /*    if(activity.productExtend.getDeliveryFee()!= null && !"".equals(activity.productExtend.getDeliveryFee())){
//                                    activity.deliveryFee  = RegexUtils.getDoubleString(activity.productExtend.getDeliveryFee().doubleValue());
                                    activity.deliveryFee = activity.productExtend.getDeliveryFee();
                                    activity.youjiFeeIsDisplayFlag =  "0";  //消费支付类型
                                    activity.tv_youji_fee_type.setText("消费支付");
                                    activity.edt_youji_fee.setText(activity.deliveryFee);
                                    activity.rl_youji_fee.setVisibility(View.VISIBLE);
                                }else {
                                    activity.deliveryFee = null;
                                    activity.youjiFeeIsDisplayFlag =  "1";  //包邮类型
                                    activity.tv_youji_fee_type.setText("包邮");
                                    activity.rl_youji_fee.setVisibility(View.GONE);
                                }*/
                                String saleStartdate = activity.productExtend.getSalesStartdate();
                                String saleEnddate = activity.productExtend.getSalesEnddate();

                                //开售时间
                                if (saleStartdate != null && !"".equals(saleStartdate)) {
                                    Date date = DateUtils.parse(saleStartdate, "yyyy-MM-dd HH:mm");
                                    activity.salesStartdate = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm").replace("-", ".");
                                    activity.kaiSaleTimeFlag = "0";
                                    activity.tv_kai_sale_custom_time.setText(activity.salesStartdate);
                                    activity.ll_kai_sale_custom_time.setVisibility(View.VISIBLE);

                                    activity.tv_kai_sale_time.setText("自定义");
                                } else {
                                    activity.kaiSaleTimeFlag = "1";
                                    activity.salesStartdate = null;
                                    activity.tv_kai_sale_time.setText("立即开售");
                                    activity.tv_kai_sale_custom_time.setText("");
                                    activity.ll_kai_sale_custom_time.setVisibility(View.GONE);
                                }
                                //停售时间
                                if (saleEnddate != null && !"".equals(saleEnddate)) {
                                    Date date = DateUtils.parse(saleEnddate, "yyyy-MM-dd HH:mm");
                                    activity.salesEnddate = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm").replace("-", ".");
                                    activity.stopSaleTimeFlag = "0";
                                    activity.tv_stop_sale_custom_time.setText(activity.salesEnddate);
                                    activity.ll_stop_sale_custom_time.setVisibility(View.VISIBLE);
                                    activity.tv_stop_sale_time.setText("自定义");
                                } else {
                                    activity.tv_stop_sale_time.setText("无停用时间");
                                    activity.stopSaleTimeFlag = "1";
                                    activity.salesEnddate = null;
                                    activity.tv_stop_sale_custom_time.setText("");
                                    activity.ll_stop_sale_custom_time.setVisibility(View.GONE);
                                }

                                //商品详情
                                /*activity.productDetail = activity.productExtend.getProductDetail();

                                if (activity.productDetail != null && !"".equals(activity.productDetail)) {

                                    activity.edt_product_remark.setText(activity.productDetail);
                                    int len = activity.productDetail.length();
                                    activity.tv_product_remark_num.setText(len + "/500");
                                } else {
                                    activity.tv_product_remark_num.setText("0/500");
                                }*/


                                //购买须知
                                activity.remark = activity.productExtend.getRemark();
                                if (activity.remark != null && !"".equals(activity.remark)) {
                                    activity.edt_buy_know.setText(activity.remark);
                                    int len = activity.remark.length();
                                    activity.tv_buy_know_num.setText(len + "/500");
                                } else {
                                    activity.tv_buy_know_num.setText("0/500");
                                }

                                Integer salesCou = activity.productExtend.getSalesCount();
                                if(salesCou != null){
                                    activity.salesCount = String.valueOf(salesCou.intValue());
                                }else {
                                   activity.salesCount = "1";
                                }
                                activity.edt_qishou_stock.setText(activity.salesCount);
                            }

                            if (activity.productMediaList != null && activity.productMediaList.size() > 0) {
                                activity.selectList.clear();
                                for (ProductMedia productMedia : activity.productMediaList) {
                                    String prizepath = HttpUrls.IMAGE_ULR + productMedia.getImgUrl();
                                    LocalMedia localMedia = new LocalMedia();
                                    localMedia.setCompressPath(prizepath);
                                    localMedia.setCompressed(true);
                                    localMedia.setPath(prizepath);
                                    activity.selectList.add(localMedia);
                                    activity.adapter.notifyDataSetChanged();
                                }


                            }

                            if (activity.productStoreList != null && activity.productStoreList.size() > 0) {

                                StringBuilder storeIdSb = new StringBuilder();
                                StringBuilder storeNameSb = new StringBuilder();
                                activity.brandLogo = SPUtils.getInstance().getString("brandLogo");
                                if (activity.brandLogo == null || "".equals(activity.brandLogo)) {
                                    activity.brandLogo = HttpUrls.IMAGE_ULR;
                                }
                                RoundedCorners roundedCorners = new RoundedCorners(8);
                                RequestOptions requestOptions = new RequestOptions()
                                        .bitmapTransform(roundedCorners)
                                        .placeholder(R.drawable.ic_image_loading)
                                        .error(R.drawable.ic_load_fail)
                                        .fallback(R.drawable.ic_image_loading);

                                Glide.with(activity).load(activity.brandLogo).apply(requestOptions).into(activity.iv_brand);

                                activity.storeType = activity.productStoreList.get(0).getStoreSelectType();
                                if ("A".equals(activity.storeType)) {

                                    activity.storeName = "#全部店铺";
                                    activity.tv_store_name.setText(activity.storeName);
                                    activity.storeId = "";
                                    for (Store store : activity.storeList) {
                                        store.setChecked(false);
                                    }
                                } else if ("D".equals(activity.storeType)) {
                                    activity.storeName = "#全部自营";
                                    activity.tv_store_name.setText(activity.storeName);
                                    activity.storeId = "";
                                    for (Store store : activity.storeList) {
                                        store.setChecked(false);

                                    }
                                } else if ("J".equals(activity.storeType)) {
                                    activity.storeName = "#全部加盟";
                                    activity.tv_store_name.setText(activity.storeName);
                                    for (Store store : activity.storeList) {
                                        store.setChecked(false);

                                    }

                                } else {
                                    activity.storeType = "H";

                                    for (ProductStore productStore : activity.productStoreList) {
                                        String storeId1 = productStore.getStoreId();

                                        storeIdSb.append(storeId1).append("-");
                                        for (Store store : activity.storeList) {
                                            String storeId2 = store.getStoreId();
                                            if (storeId1.equals(storeId2)) {
                                                store.setChecked(true);
                                                String storeName = store.getStoreName() != null ? "#" + store.getStoreName() + "    " : "";
                                                storeNameSb.append(storeName);
                                                break;
                                            }

                                        }
                                    }
                                    if (!storeIdSb.toString().isEmpty()) {
                                        activity.storeId = storeIdSb.substring(0, storeIdSb.length() - 1);
                                    }
                                    activity.storeName = storeNameSb.toString() != null ? storeNameSb.toString() : "";
                                    activity.tv_store_name.setText(activity.storeName);

                                }
                                activity.myAdapter = new CampaignSelectStoreAdapter(context);
                                activity.myAdapter.setDatas(activity.storeList);
                                activity.myAdapter.notifyDataSetChanged();

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
    public void queryAllMemberRedEnvlope(Context context, String memberName, String memberTel, String compId, Integer pageSize, Integer pageNumber, boolean isRefresh, boolean isSearchData) {
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("memberName", memberName);
        data.put("memberTel", memberTel);
        data.put("compId", compId);
        data.put("pageSize", String.valueOf(pageSize));
        data.put("pageNumber", String.valueOf(pageNumber));
        String jsonStr = new Gson().toJson(data);
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("jsonstr", jsonStr);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);


        shopServiceManage.queryAllMemberRedEnvlope(jsonStr)
                .subscribe(new BaseObserver<MemberRedEnvelopeBean>(context, false) {
                    @Override
                    public void onSuccess(MemberRedEnvelopeBean memberRedEnvelopeBean, String msg) {
                        RedEnvelopeActivity redEnvelopeActivity = null;
                        SearchRedEnvelopeListActivity searchRedEnvelopeListActivity = null;

                        if (memberRedEnvelopeBean != null) {
                            List<MemberRedEnvelope> dataList = memberRedEnvelopeBean.getData();
                            if (isSearchData) { //红包搜索列表
                                searchRedEnvelopeListActivity = (SearchRedEnvelopeListActivity) context;

                                if (dataList != null && dataList.size() > 0) {
                                    if (isRefresh) {
                                        searchRedEnvelopeListActivity.envelopeList.clear();
                                        searchRedEnvelopeListActivity.envelopeList.addAll(dataList);
                                        searchRedEnvelopeListActivity.listAdapter.notifyDataSetChanged();
                                        if (dataList.size() < 10) {
                                            searchRedEnvelopeListActivity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                        } else {
                                            searchRedEnvelopeListActivity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                        }
                                    } else {
                                        searchRedEnvelopeListActivity.envelopeList.addAll(dataList);
                                        if (dataList.size() < 10) {
                                            searchRedEnvelopeListActivity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                        } else {
                                            searchRedEnvelopeListActivity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                        }
                                    }


                                } else {
                                    searchRedEnvelopeListActivity.envelopeList.clear();
                                    searchRedEnvelopeListActivity.envelopeList.addAll(dataList);
                                    searchRedEnvelopeListActivity.listAdapter.notifyDataSetChanged();
                                    searchRedEnvelopeListActivity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                }
                                searchRedEnvelopeListActivity.ptr.onRefreshComplete();
                            } else {//查询列表
                                redEnvelopeActivity = (RedEnvelopeActivity) context;
                                redEnvelopeActivity.ptr.onRefreshComplete();
                                if (dataList != null && dataList.size() > 0) {
                                    redEnvelopeActivity.ll_no_data.setVisibility(View.GONE);
                                    redEnvelopeActivity.listView.setVisibility(View.VISIBLE);
                                    if (isRefresh) {
                                        redEnvelopeActivity.envelopeList.clear();
                                        redEnvelopeActivity.envelopeList.addAll(dataList);
                                        redEnvelopeActivity.listAdapter.notifyDataSetChanged();

                                        if (dataList.size() < 10) {
                                            redEnvelopeActivity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                        } else {
                                            redEnvelopeActivity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                        }
                                    } else {
                                        redEnvelopeActivity.envelopeList.addAll(dataList);
                                        if (dataList.size() < 10) {
                                            redEnvelopeActivity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                        } else {
                                            redEnvelopeActivity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                        }
                                    }


                                } else {
                                    redEnvelopeActivity.envelopeList.clear();
                                    redEnvelopeActivity.ll_no_data.setVisibility(View.VISIBLE);
                                    redEnvelopeActivity.listView.setVisibility(View.GONE);
                                    redEnvelopeActivity.tv_no_data.setText("您现在没有会员红包记录哦！");
                                    redEnvelopeActivity.listAdapter.notifyDataSetChanged();
                                }
                                queryAllMemberRedEnvlopeSum(context, compId);
                            }

                        }


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);
                        RedEnvelopeActivity redEnvelopeActivity = null;
                        SearchRedEnvelopeListActivity searchRedEnvelopeListActivity = null;
                        if (isSearchData) { //红包搜索列表

                           /* searchRedEnvelopeListActivity = (SearchRedEnvelopeListActivity) context;
                            searchRedEnvelopeListActivity.ll_no_data.setVisibility(View.VISIBLE);
                            searchRedEnvelopeListActivity.listView.setVisibility(View.GONE);
                            redEnvelopeActivity.tv_no_data.setText("您现在没有会员红包记录哦！");*/
                            searchRedEnvelopeListActivity.listAdapter.notifyDataSetChanged();
                        } else {
                            redEnvelopeActivity = (RedEnvelopeActivity) context;
                            redEnvelopeActivity.ll_no_data.setVisibility(View.VISIBLE);
                            redEnvelopeActivity.listView.setVisibility(View.GONE);
                            redEnvelopeActivity.tv_no_data.setText("您现在没有会员红包记录哦！");
                            redEnvelopeActivity.listAdapter.notifyDataSetChanged();

                        }

                    }
                });
    }

    @Override
    public void queryAllMemberRedEnvlopeSum(Context context, String compId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("compId", compId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        RedEnvelopeActivity activity = (RedEnvelopeActivity) context;
        shopServiceManage.queryAllMemberRedEnvlopeSum(compId)
                .subscribe(new BaseObserver<Map<String, Object>>(context, true) {
                    @Override
                    public void onSuccess(Map<String, Object> result, String msg) {
                        if (result != null && result.size() > 0) {
                            String num = String.valueOf(result.get("num"));

                            if (num != null && !"".equals(num) && !"0".equals(num)) {
                                Double total = Double.parseDouble(num);
                                activity.tv_red_envelope_num.setText(RegexUtils.getDoubleString(total));
                            } else {
                                activity.tv_red_envelope_num.setText("-");
                            }
                            String amt = String.valueOf(result.get("balance"));
                            if (amt != null && !"".equals(amt) && !"0.0".equals(amt)) {
                                Double amtD = Double.parseDouble(amt);
                                activity.tv_re_envelope_amt.setText(RegexUtils.getDoubleString(amtD));
                            } else {
                                activity.tv_re_envelope_amt.setText("-");
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
    public void queryAllMemberRedEnvlopeDeTail(Context context, String compId, String memberId, Integer pageSize, Integer pageNumber, boolean isRefresh) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("compId", compId);
        params.put("memberId", memberId);
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
        RedEnvelopeDetailActivity activity = (RedEnvelopeDetailActivity) context;


        shopServiceManage.queryAllMemberRedEnvlopeDeTail(compId, memberId, pageSize, pageNumber)
                .subscribe(new BaseObserver<MemberRedEnvelopeDetailBean>(context, true) {
                    @Override
                    public void onSuccess(MemberRedEnvelopeDetailBean memberRedEnvelopeBean, String msg) {

                        if (memberRedEnvelopeBean != null) {
                            List<RedEnvelopeDetail> dataList = memberRedEnvelopeBean.getData();

                            if (dataList != null && dataList.size() > 0) {

                                if (isRefresh) {
                                    activity.envelopeList.clear();
                                    activity.envelopeList.addAll(dataList);
                                    activity.mAdapter.notifyDataSetChanged();
                                    activity.ptr.refreshComplete();
                                    activity.ptr.setLoadMoreEnable(false);
                                    if (dataList.size() < 10) {
                                        activity.ptr.setLoadMoreEnable(false);
                                    } else {
                                        activity.ptr.setLoadMoreEnable(true);
                                    }
                                } else {
                                    activity.envelopeList.addAll(dataList);
                                    activity.ptr.refreshComplete();
                                    activity.ptr.setLoadMoreEnable(false);
                                    if (dataList.size() < 10) {
                                        activity.ptr.setLoadMoreEnable(false);
                                    } else {
                                        activity.ptr.loadMoreComplete(true);
                                    }
                                }


                            } else {
                                activity.ptr.refreshComplete();
                                activity.ptr.setLoadMoreEnable(false);
                            }
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

    @Override
    public void updateMemberBal(Context context, String compId, String memberId, String balanceChange, String changeState, String changeReason, String balanceBefore) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("compId", compId);
        params.put("memberId", memberId);
        params.put("balanceChange", balanceChange);
        params.put("changeState", changeState);
        params.put("changeReason", changeReason);
        params.put("balanceBefore", balanceBefore);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        RedEnvelopeDetailActivity activity = (RedEnvelopeDetailActivity) context;
        shopServiceManage.updateMemberBal(compId, memberId, balanceChange, changeState, changeReason, balanceBefore)
                .subscribe(new BaseObserver<Map<String, Object>>(context, true) {
                    @Override
                    public void onSuccess(Map<String, Object> result, String msg) {
                        ToastUtils.showShort("修改成功");
                        activity.tv_balance.setText(RegexUtils.getDoubleString(Double.parseDouble(activity.banlanceAfter)));
                        queryAllMemberRedEnvlopeDeTail(context, compId, memberId, 10, 1, true);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);
                    }
                });
    }

    @Override
    public void countVerification(Context context, String storeId, String consumeStr, String consumeEnd, Integer pageNumber, Integer pageSize, boolean isRefresh) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
        params.put("storeId", storeId);
        params.put("consumeStr", consumeStr);
        params.put("consumeEnd", consumeEnd);
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
        ProductVerificationRecordActivity activity = (ProductVerificationRecordActivity) context;
        shopServiceManage.countVerification(storeId, consumeStr, consumeEnd, pageNumber, pageSize)
                .subscribe(new BaseObserver<ProductVerifictionRecordBean>(context, true) {
                    @Override
                    public void onSuccess(ProductVerifictionRecordBean recordBean, String msg) {
                        activity.ptr.onRefreshComplete();
                        if (recordBean != null) {
                            BigDecimal v = recordBean.getV();  //销售总金额
                            BigDecimal h = recordBean.getH(); //抵扣金额
                            BigDecimal c = recordBean.getC(); //实付金额
                            Integer cou = recordBean.getCou(); //张数

                            if (v != null && v.doubleValue() > 0) {

                                activity.tv_sale_total.setText(RegexUtils.getDoubleString(v.doubleValue()));
                            } else {
                                activity.tv_sale_total.setText("-");
                            }
                            if (cou != null && cou.intValue() > 0) {

                                activity.tv_product_verification_total.setText(String.valueOf(cou.intValue()));
                            } else {
                                activity.tv_product_verification_total.setText("-");
                            }


                            if (c != null && c.doubleValue() > 0) {

                                activity.tv_pay_amt_total.setText(RegexUtils.getDoubleString(h.doubleValue()));
                            } else {
                                activity.tv_pay_amt_total.setText("-");
                            }
                            if (h != null && h.doubleValue() > 0) {

                                activity.tv_red_packge_amt_total.setText(RegexUtils.getDoubleString(c.doubleValue()));
                            } else {
                                activity.tv_red_packge_amt_total.setText("-");
                            }
                            ProductVerifictionRecordBean.OrderObjectBean orderObjectBean = recordBean.getList();
                            if (orderObjectBean != null) {


                                List<TOrderObject> dataList = orderObjectBean.getData();

                                if (dataList != null && dataList.size() > 0) {
                                    activity.ll_no_data.setVisibility(View.GONE);
                                    activity.listView.setVisibility(View.VISIBLE);
                                    if (isRefresh) {
                                        activity.orderList.clear();
                                        activity.orderList.addAll(dataList);
                                        activity.listAdapter.notifyDataSetChanged();
                                        if (dataList.size() < 10) {
                                            activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                        } else {
                                            activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                        }
                                    } else {
                                        activity.orderList.addAll(dataList);
                                        activity.listAdapter.notifyDataSetChanged();
                                        if (dataList.size() < 10) {
                                            activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                        } else {
                                            activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                        }
                                    }
                                } else {
                                    if (activity.orderList != null && activity.orderList.size() > 0) {
                                        activity.orderList.addAll(dataList);
                                        activity.listAdapter.notifyDataSetChanged();
                                        if (activity.orderList.size() < 10) {
                                            activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                        } else {
                                            activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                        }
                                    }else {

                                        activity.orderList.addAll(dataList);
                                        activity.listAdapter.notifyDataSetChanged();
                                    }

                                    activity.ll_no_data.setVisibility(View.VISIBLE);
                                    activity.listView.setVisibility(View.GONE);
                                    activity.tv_no_data.setText("您现在没有核销记录哦！");
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                }

                            }else {

                            }
                            activity.ptr.onRefreshComplete();
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                        activity.listAdapter.notifyDataSetChanged();
                        activity.ll_no_data.setVisibility(View.VISIBLE);
                        activity.listView.setVisibility(View.GONE);
                        activity.tv_no_data.setText("您现在没有核销记录哦！");
                        activity.ptr.onRefreshComplete();
                    }
                });
    }

    @Override
    public void doProductDetailQueryConsumeLog(Context context, String tel, String consumeTimeStr, String consumeTimeEnd, Integer pageNumber, Integer pageSize, String storeId, boolean isRefresh) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp = String.valueOf(new Date().getTime());
//        params.put("tel",tel);
        params.put("consumeTimeStr", consumeTimeStr);
        params.put("consumeTimeEnd", consumeTimeEnd);
        params.put("pageSize", String.valueOf(pageSize));
        params.put("pageNumber", String.valueOf(pageNumber));
        params.put("storeId", storeId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params = new HashMap<>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        ProductVerificationRecordDetailActivity activity = (ProductVerificationRecordDetailActivity) context;
        shopServiceManage.doProductDetailQueryConsumeLog(tel, consumeTimeStr, consumeTimeEnd, pageNumber, pageSize, storeId)
                .subscribe(new BaseObserver<TOrderConsumeLogBean>(context, true) {
                    @Override
                    public void onSuccess(TOrderConsumeLogBean recordBean, String msg) {
                        activity.ptr.onRefreshComplete();
                        if (recordBean != null) {

                            List<TOrderConsumeLog> dataList = recordBean.getData();
                            if (dataList != null && dataList.size() > 0) {
                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.listView.setVisibility(View.VISIBLE);
                                if (isRefresh) {
                                    activity.orderList.clear();
                                    activity.orderList.addAll(dataList);
                                    activity.listAdapter.notifyDataSetChanged();
                                    if (dataList.size() < 10) {
                                        activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                    } else {
                                        activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                    }
                                } else {
                                    activity.orderList.addAll(dataList);
                                    if (dataList.size() < 10) {
                                        activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                    } else {
                                        activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                    }
                                }
                            } else {
                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.listView.setVisibility(View.GONE);
                                activity.tv_no_data.setText("您现在没有核销记录哦！");
                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }

                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.ll_no_data.setVisibility(View.VISIBLE);
                        activity.listView.setVisibility(View.GONE);
                        activity.tv_no_data.setText("您现在没有核销记录哦！");
                        activity.ptr.onRefreshComplete();
                    }
                });
    }


}
