package com.zwx.scan.app.feature.member;

import android.content.Context;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.zwx.library.pickerview.wheelview.view.WheelView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.DateUtils;
import com.zwx.library.utils.EncryptUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.base.BaseObserver;
import com.zwx.scan.app.data.bean.CardQrcBean;
import com.zwx.scan.app.data.bean.CompMemType;
import com.zwx.scan.app.data.bean.CompMemTypeDetailBean;
import com.zwx.scan.app.data.bean.CompMemberType;
import com.zwx.scan.app.data.bean.ComsumeLog;
import com.zwx.scan.app.data.bean.MaterialCard;
import com.zwx.scan.app.data.bean.MemberConsumeBean;
import com.zwx.scan.app.data.bean.MemberInfoBean;
import com.zwx.scan.app.data.bean.MemberInfoDetailBean;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.Order;
import com.zwx.scan.app.data.bean.TemplateBean;
import com.zwx.scan.app.data.bean.TemplateChild;
import com.zwx.scan.app.data.http.service.CampaignServiceManager;
import com.zwx.scan.app.data.http.service.MemberManageServiceManager;
import com.zwx.scan.app.data.http.service.RetrofitServiceManager;
import com.zwx.scan.app.feature.countanalysis.campaign.CampaignAnalysisActivity;
import com.zwx.scan.app.feature.couponmanage.CouponManageActivity;
import com.zwx.scan.app.feature.ptmanage.PtManageActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * author : lizhilong
 * time   : 2018/10/18
 * desc   :
 * version: 1.0
 **/
public class MemberManagePresenter implements MemberManageContract.Presenter {



    private  MemberManageContract.View memberView;
    private MemberManageServiceManager memberManage;
    private CampaignServiceManager campaignServiceManager;
    //添加订阅，应用于取消订阅，防止造成内存泄漏
    private CompositeDisposable disposable;

    public MemberManagePresenter(MemberManageContract.View view) {
        memberManage = new MemberManageServiceManager();
        campaignServiceManager = new CampaignServiceManager();
        this.memberView = view;
        disposable = new CompositeDisposable();
    }

    @Override
    public void comsumeListByMap(Context context,String userId,int pageSize, int pageNumber,String order,String desc,boolean isRefresh) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        RetrofitServiceManager.setHeadTokenMap(null);
        params.put("userId", userId);
        params.put("pageNumber", pageNumber+"");
        params.put("pageSize", pageSize +"");
        params.put("order", order);
        params.put("desc", desc);
        params.put("token",token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        MemberConsumeListFragment view = (MemberConsumeListFragment)memberView;
        memberManage.comsumeListByMap(userId, pageNumber, pageSize,order,desc)
                .subscribe(new BaseObserver<MemberConsumeBean>(context,false){
                    @Override
                    public void onSuccess(MemberConsumeBean consumeBean,String msg) {
                        List<MemberConsumeBean.MemberIT.MemberConsume> dataList = new ArrayList<>();

                        if(consumeBean!=null){
                           MemberConsumeBean.MemberIT memberIT = consumeBean.getMemberIT();
                           if(memberIT!=null){
                                dataList = memberIT.getData();
                               if (dataList != null && dataList.size() > 0) {
                                   view.memberConsumeList.addAll(dataList);
                                   view.listAdapter.notifyDataSetChanged();
                                   view.ptr.setPullToRefreshOverScrollEnabled(true);
                                   if (dataList.size() < 10) {
                                       view.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                   } else {
                                       view.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                   }
                                   view.ll_no_data.setVisibility(View.GONE);
                                   view.listView.setVisibility(View.VISIBLE);

                               } else {
                                   view.memberConsumeList.addAll(dataList);
                                   view.listAdapter.notifyDataSetChanged();

                                   if(view.memberConsumeList != null  &&  view.memberConsumeList.size()>0){
                                       if(view.memberConsumeList.size() < 10){
                                           view.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                       }else {
                                           view.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                       }

                                       view.ll_no_data.setVisibility(View.GONE);
                                       view.listView.setVisibility(View.VISIBLE);
                                   }else {

                                       view.ll_no_data.setVisibility(View.VISIBLE);
                                       view.listView.setVisibility(View.GONE);
                                       view.tv_no_data.setText("您现在没有会员消费记录哦！");
                                   }


                               }

                           }else {
                               view.memberConsumeList.addAll(dataList);
                               view.listAdapter.notifyDataSetChanged();

                               if(view.memberConsumeList != null  &&  view.memberConsumeList.size()>0){
                                   if(view.memberConsumeList.size() < 10){
                                       view.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                   }else {
                                       view.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                   }

                                   view.ll_no_data.setVisibility(View.GONE);
                                   view.listView.setVisibility(View.VISIBLE);
                               }else {

                                   view.ll_no_data.setVisibility(View.VISIBLE);
                                   view.listView.setVisibility(View.GONE);
                                   view.tv_no_data.setText("您现在没有会员消费记录哦！");
                               }

                           }
                        }else {
                            view.memberConsumeList.addAll(dataList);
                            view.listAdapter.notifyDataSetChanged();

                            if(view.memberConsumeList != null  &&  view.memberConsumeList.size()>0){
                                if(view.memberConsumeList.size() < 10){
                                    view.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                }else {
                                    view.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }

                                view.ll_no_data.setVisibility(View.GONE);
                                view.listView.setVisibility(View.VISIBLE);
                            }else {

                                view.ll_no_data.setVisibility(View.VISIBLE);
                                view.listView.setVisibility(View.GONE);
                                view.tv_no_data.setText("您现在没有会员消费记录哦！");
                            }


                        }
                        view.ptr.onRefreshComplete();

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        view.listAdapter.notifyDataSetChanged();
                        view.ptr.onRefreshComplete();

                        view.listView.setVisibility(View.GONE);
                        view.ll_no_data.setVisibility(View.VISIBLE);
                        view.tv_no_data.setText("您的网络可能睡着了！");
                    }
                });
    }


    @Override
    public void memberListByMap(Context context, String memberTel, String userId, int pageNumber, int pageSize , boolean isRefresh) {


        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("memberTel",memberTel);
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
        MemberInfoListActivity activity = (MemberInfoListActivity) context;
        memberManage.memberListByMap(memberTel,userId,pageNumber,pageSize)
                .subscribe(new BaseObserver<MemberInfoBean>(context,false){
                    @Override
                    public void onSuccess(MemberInfoBean memberInfoBean,String msg) {

                        if(memberInfoBean != null){
                            MemberInfoBean.MemberList memberList = memberInfoBean.getMemberList();
                            if (memberList!=null){
                                List<MemberInfoBean.MemberInfo> dataList = memberList.getData();
                                if (dataList != null && dataList.size() > 0) {
                                    activity.memberInfoList.addAll(dataList);
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
                                    activity.memberInfoList.addAll(dataList);
                                    activity.listAdapter.notifyDataSetChanged();

                                    if(activity.memberInfoList != null  &&  activity.memberInfoList.size()>0){
                                        if(activity.memberInfoList.size() < 10){
                                            activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                        }else {
                                            activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                        }

                                        activity.ll_no_data.setVisibility(View.GONE);
                                        activity.listView.setVisibility(View.VISIBLE);
                                    }else {

                                        activity.ll_no_data.setVisibility(View.VISIBLE);
                                        activity.listView.setVisibility(View.GONE);
                                        activity.tv_no_data.setText("暂无会员卡信息数据！");
                                    }


                                }

                            }else {
                                activity.listView.setVisibility(View.GONE);
                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                if(activity.memberInfoList != null  &&  activity.memberInfoList.size()>0){
                                    if(activity.memberInfoList.size() < 10){
                                        activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                    }else {
                                        activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                    }

                                    activity.ll_no_data.setVisibility(View.GONE);
                                    activity.listView.setVisibility(View.VISIBLE);
                                }else {

                                    activity.ll_no_data.setVisibility(View.VISIBLE);
                                    activity.listView.setVisibility(View.GONE);
                                    activity.tv_no_data.setText("暂无会员卡信息数据！");
                                }
                            }
                        }else {
                            if(activity.memberInfoList != null  &&  activity.memberInfoList.size()>0){
                                if(activity.memberInfoList.size() < 10){
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                }else {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }

                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.listView.setVisibility(View.VISIBLE);
                            }else {

                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.listView.setVisibility(View.GONE);
                                activity.tv_no_data.setText("暂无会员卡信息数据！");
                            }
                        }
                        activity.listAdapter.notifyDataSetChanged();
                        activity.ptr.onRefreshComplete();
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                        if(activity.memberInfoList != null  &&  activity.memberInfoList.size()>0){
                            if(activity.memberInfoList.size() < 10){
                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }else {
                                activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                            }

                            activity.ll_no_data.setVisibility(View.GONE);
                            activity.listView.setVisibility(View.VISIBLE);
                        }else {

                            activity.ll_no_data.setVisibility(View.VISIBLE);
                            activity.listView.setVisibility(View.GONE);
                            activity.iv_no_data.setImageResource(R.drawable.ic_wifi_not);
                            activity.tv_no_data.setText("您的网络可能睡着了！");
                        }
                        activity.listAdapter.notifyDataSetChanged();
                        activity.ptr.onRefreshComplete();

                    }
                });


    }

    @Override
    public void doGetMemberType(Context context, String memberId, String userId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("memberId",memberId);
        params.put("userId",userId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        MemberInfoDetailActivity activity = (MemberInfoDetailActivity) context;
        memberManage.doGetMemberType(memberId,userId)
                .subscribe(new BaseObserver<MemberInfoDetailBean>(context,true){
                    @Override
                    public void onSuccess(MemberInfoDetailBean memberInfoBean,String msg) {

                        if(memberInfoBean != null){
                            List<MemberInfoDetailBean.MemberInfoDetail> memberList = memberInfoBean.getMemberIT();

                            if(memberList!=null&&memberList.size()>0){
                                activity.detailList = memberList;
                                activity.setAdapter();
                            }

                        }else {
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });


    }

    @Override
    public void doQueryComsumeLog(Context context, int pageNumber, int pageSize, String compMemTypeId, String userId,String memberId,boolean isRefresh) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());


        params.put("pageNumber",String.valueOf(pageNumber));
        params.put("pageSize",String.valueOf(pageSize));
        params.put("compMemTypeId",compMemTypeId);
        params.put("userId",userId);
        params.put("memberId",memberId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        MemberConsumeListDetailActivity activity = (MemberConsumeListDetailActivity) context;
        memberManage.doQueryComsumeLog(pageNumber,pageSize,compMemTypeId,userId,memberId)
                .subscribe(new BaseObserver<List<ComsumeLog>>(context,true){
                    @Override
                    public void onSuccess(List<ComsumeLog> dataList,String msg) {

                        //"您现在还没有消费记录哦！"
                        if(dataList!=null && dataList.size()>0 && !dataList.contains(null)) {
                            activity.memberConsumeList.addAll(dataList);
                            activity.listAdapter.notifyDataSetChanged();
                            activity.ptr.setPullToRefreshOverScrollEnabled(true);
                            if (dataList.size() < 10) {
                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            } else {
                                activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                            activity.ll_no_data.setVisibility(View.GONE);
                            activity.listView.setVisibility(View.VISIBLE);


                        }else {
                            activity.memberConsumeList.addAll(dataList);
                            activity.listAdapter.notifyDataSetChanged();

                            if(activity.memberConsumeList != null  &&  activity.memberConsumeList.size()>0){
                                if(activity.memberConsumeList.size() < 10){
                                    activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                }else {
                                    activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                                }
                                activity.listAdapter.notifyDataSetChanged();
                                activity.ll_no_data.setVisibility(View.GONE);
                                activity.listView.setVisibility(View.VISIBLE);
                            }else {

                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.listView.setVisibility(View.GONE);
                                activity.tv_no_data.setText("您现在还没有消费记录哦！");
                            }


                        }

                        activity.ptr.onRefreshComplete();

                        if (activity.isShowMemberDetail!=null &&"YES".equals(activity.isShowMemberDetail)){

                        }else {
                            doQueryMemberType(context,memberId);
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                        if(activity.memberConsumeList != null  &&  activity.memberConsumeList.size()>0){
                            if(activity.memberConsumeList.size() < 10){
                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }else {
                                activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                            }

                            activity.ll_no_data.setVisibility(View.GONE);
                            activity.listView.setVisibility(View.VISIBLE);
                        }else {

                            activity.ll_no_data.setVisibility(View.VISIBLE);
                            activity.listView.setVisibility(View.GONE);
                            activity.iv_no_data.setImageResource(R.drawable.ic_wifi_not);
                            activity.tv_no_data.setText("您的网络可能睡着了！");
                        }
                        activity.listAdapter.notifyDataSetChanged();
                        activity.ptr.onRefreshComplete();
                    }
                });



    }

    @Override
    public void doQueryMemberType(Context context, String memberId) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("memberId",memberId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        MemberConsumeListDetailActivity activity = (MemberConsumeListDetailActivity) context;
        memberManage.doQueryMemberType(memberId)
                .subscribe(new BaseObserver<List<CompMemType>>(context,true){
                    @Override
                    public void onSuccess(List<CompMemType> compMemTypeList,String msg) {

                        if(compMemTypeList != null){

                            if(compMemTypeList!=null&&compMemTypeList.size()>0){

                                List<CardQrcBean> cardQrcBeans = new ArrayList<>();
                                CardQrcBean cardQrcBean = new CardQrcBean();
                                cardQrcBean.setId("all");
                                cardQrcBean.setCardNo("全部会员卡");
                                cardQrcBeans.add(cardQrcBean);
                                for(CompMemType data : compMemTypeList){
                                    CardQrcBean cardBean = new CardQrcBean();
                                    if(data != null){
                                        cardBean.setId(data.getCompMemTypeId());
                                        cardBean.setCardNo(data.getMemTypeName());
                                        cardQrcBeans.add(cardBean);
                                    }
                                }
                                activity.cardQrcBeans = cardQrcBeans; ;

                                activity.pvQrc.setPicker(activity.cardQrcBeans);
                            }

                        }else {
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });
    }

    @Override
    public void selectToCounByOrder(Context context, String userId, String salesTimeSta, String salesTimesEnd, String storeId, Integer pageSize, Integer pageNumber,boolean isRefresh) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("salesTimeSta",salesTimeSta);
        params.put("salesTimesEnd",salesTimesEnd);
        params.put("storeId",storeId);

        params.put("pageSize",String.valueOf(pageSize));
        params.put("pageNumber",String.valueOf(pageNumber));
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        MemberCardStreamActivity activity = (MemberCardStreamActivity) context;
        memberManage.selectToCounByOrder(userId,salesTimeSta,salesTimesEnd,storeId,pageSize,pageNumber)
                .subscribe(new BaseObserver<List<Order>>(context,true){
                    @Override
                    public void onSuccess(List<Order>  dataList, String msg) {

                        /*if(orderList!=null && orderList.size()>0){

                            if(isRefresh){
                                activity.orderList.clear();
                                activity.orderList.addAll(orderList);
                                activity.listAdapter = new MemberCardStreamAdapter(activity,activity.orderList);
                                activity.mAdapter.notifyDataSetChanged();
                                activity.ptr.refreshComplete();
                                if(orderList.size()<10 ){
                                    activity.ptr.setLoadMoreEnable(false);
                                }else {
                                    activity.ptr.setLoadMoreEnable(true);
                                }
                            }else {
                                activity.orderList.addAll(orderList);
                                if(orderList.size()<10){
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
                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.listView.setVisibility(View.GONE);
                                activity.tv_no_data.setText("暂无会员卡收款流水数据！");
                            }


                        }
                        activity.ptr.onRefreshComplete();

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
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
                            activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            activity.ll_no_data.setVisibility(View.VISIBLE);
                            activity.listView.setVisibility(View.GONE);
                            activity.tv_no_data.setText("暂无会员卡收款流水数据！");
                        }
                        activity.ptr.onRefreshComplete();
                    }
                });

    }

    @Override
    public void selectToITByOrder(Context context,String userId, String compMemtypeId, String salesTimeSta, String salesTimesEnd, String storeId, Integer pageSize, Integer pageNumber,boolean isRefresh) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("compMemtypeId",compMemtypeId);
        params.put("salesTimeSta",salesTimeSta);
        params.put("salesTimesEnd",salesTimesEnd);
        params.put("storeId",storeId);
        params.put("pageSize",String.valueOf(pageSize));
        params.put("pageNumber",String.valueOf(pageNumber));
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        MemberCardStreamDetailActivity activity = (MemberCardStreamDetailActivity) context;
        memberManage.selectToITByOrder(userId,compMemtypeId,salesTimeSta,salesTimesEnd,storeId,pageSize,pageNumber)
                .subscribe(new BaseObserver<List<Order>>(context,true){
                    @Override
                    public void onSuccess(List<Order>  dataList, String msg) {

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

                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                activity.ll_no_data.setVisibility(View.VISIBLE);
                                activity.listView.setVisibility(View.GONE);
                                activity.tv_no_data.setText("暂无会员卡收款流水数据！");
                            }


                        }
                        activity.ptr.onRefreshComplete();


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
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

                            activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            activity.ll_no_data.setVisibility(View.VISIBLE);
                            activity.listView.setVisibility(View.GONE);
                            activity.tv_no_data.setText("暂无会员卡收款流水数据！");
                        }
                        activity.ptr.onRefreshComplete();
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

        MemberCardStreamActivity activity = (MemberCardStreamActivity) context;
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
    public void memTypeList(Context context, String userId, int pageNumber, int pageSize, boolean isRefresh) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        RetrofitServiceManager.setHeadTokenMap(null);
        params.put("userId", userId);
        params.put("pageNumber", pageNumber+"");
        params.put("pageSize", pageSize +"");
        params.put("token",token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        MemberCardManageActivity activity = (MemberCardManageActivity)context;
        memberManage.doMemTypeList(userId, pageNumber, pageSize)
                .subscribe(new BaseObserver<List<CompMemberType>>(context,false){
                    @Override
                    public void onSuccess(List<CompMemberType> dataList,String msg) {
                        if (dataList!=null && dataList.size()>0) {
                            activity.rvList.setVisibility(View.VISIBLE);
                            activity.ll_no_data.setVisibility(View.GONE);


                            for(int i=0;i<dataList.size();i++){
                                if(dataList.get(i) != null){
                                    if(dataList.get(i).getMemtypeIdArray() != null){
                                        String memtypeIdArray = String.valueOf(dataList.get(i).getMemtypeIdArray());
                                        if(activity.compMemberTypeList != null &&  activity.compMemberTypeList.size()>0){
                                            for(int j=0;j<activity.compMemberTypeList.size();j++){
                                                String memtypeIdArray2 = String.valueOf(activity.compMemberTypeList.get(j).getMemtypeIdArray());
                                                if(memtypeIdArray2 != null &&!"".equals(memtypeIdArray2)){
                                                    if(memtypeIdArray.equals(memtypeIdArray2)){
                                                        activity.compMemberTypeList.remove(j);
                                                        break;
                                                    }
                                                }

                                            }
                                        }
                                    }
                                }

                            }
                            activity.compMemberTypeList.addAll(dataList);
                            activity.listAdapter.notifyDataSetChanged();
                            if (dataList.size() < 10) {
                                activity.ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            } else {
                                activity.ptr.setMode(PullToRefreshBase.Mode.BOTH);
                            }

                        }else {

                            if(activity.compMemberTypeList != null && activity.compMemberTypeList.size()>0){
                                if(activity.compMemberTypeList.size() < 10){
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
                                activity.tv_no_data.setText("您现在没有会员卡记录哦！");
                            }

                        }
                        activity.listAdapter.notifyDataSetChanged();
                        activity.ptr.onRefreshComplete();

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        if(activity.compMemberTypeList != null && activity.compMemberTypeList.size()>0){
                            if(activity.compMemberTypeList.size() < 10){
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
                            activity.tv_no_data.setText("您现在没有会员卡记录哦！");
                        }
                        activity.ptr.onRefreshComplete();

                    }
                });
    }

    @Override
    public void memberTypeOperation(Context context, String userId, String type,String memtypeIdArray,String compMemberGroup) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("type",type);
        params.put("memtypeIdArray",memtypeIdArray);
        params.put("compMemberGroup",compMemberGroup);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        MemberCardManageActivity activity = (MemberCardManageActivity) context;
        memberManage.memberTypeOperation(userId,type, memtypeIdArray,compMemberGroup)
                .subscribe(new BaseObserver<String>(context,false){
                    @Override
                    public void onSuccess(String result, String msg) {
                        if("3".equals(type)){  //是否删除成功
                            ToastUtils.showCustomShortBottom("已删除");
                            activity.compMemberTypeList.remove(activity.deletePostion);
                            activity.listAdapter.notifyDataSetChanged();
                            if(activity.dialog!=null){
                                activity.dialog.dismiss();
                            }

                        }else if("2".equals(type)){ //停用
                            ToastUtils.showShort("已停用");
                        }else if("1".equals(type)){ //启用
                            ToastUtils.showShort("已启用");
                        }
                        //刷新数据
                        memTypeList(context,userId,activity.pageNumber,activity.pageSize,true);

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);


                        if(code ==3){
                            //"message":"该系列会员卡已经是启用状态"
                            activity.setResultMessageDialog();
                        }else{
                            ToastUtils.showShort(message);
                        }

                    }
                });
    }

    @Override
    public void doTemplaCard(Context context, String companyId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("companyId",companyId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        MemberCardNewTwoActivity activity = (MemberCardNewTwoActivity) context;
        memberManage.doTemplaCard(companyId)
                .subscribe(new BaseObserver<TemplateBean>(context,false){
                    @Override
                    public void onSuccess(TemplateBean result, String msg) {
                        if(result != null ){
                            activity.companyId = result.getCompanyId();
                            activity.templateList = result.getTemplates();
                            activity.materialCardList = result.getMaterialCards();


                            if(activity.templateList != null && activity.templateList.size()>0){
                                activity.templateChildList = activity.templateList.get(0).getTemplateChilds();

                            }
                            activity.empletAdapter = new CardTempletListAdapter(activity,activity.templateChildList);
                            activity.empletAdapter.notifyDataSetChanged();
                            activity.grid_view1.setAdapter(activity.empletAdapter);
                            activity.materialCardAdapter = new SelectMaterialCardAdapter(activity,activity.materialCardList);
                            activity.materialCardAdapter.notifyDataSetChanged();
                            activity.grid_view2.setAdapter(activity.materialCardAdapter);
                            String materrialBack = activity.background;
                            if("1".equals(activity.isTemplet)) { //模板
                                if(activity.templateChildList != null && activity.templateChildList.size()>0){
                                    for (TemplateChild templateChild:activity.templateChildList){
                                        if(templateChild.getId() != null){
                                            String materImgUrl = String.valueOf(templateChild.getImgUrl());
                                            if(materImgUrl.equals(activity.background)){
                                                templateChild.setChecked(true);
                                                if("1".equals(activity.isTemplet)){ //模板
                                                    if(activity.background != null && !"".equals(activity.background)){

                                                    }else {
                                                        activity.background = templateChild.getImgUrl();
                                                        activity.setCardBg();
                                                    }
                                                    if(materrialBack != null && !"".equals(materrialBack)){
                                                        if(activity.background.equals(materrialBack)){
                                                            templateChild.setChecked(true);
                                                        }
                                                    }
                                                }
                                                break;
                                            }
                                        }

                                    }
                                }
                                activity.grid_view1.setVisibility(View.VISIBLE);
                                activity.grid_view2.setVisibility(View.GONE);
                                activity.empletAdapter.notifyDataSetChanged();
                            }else if("0".equals(activity.isTemplet)) { //素材
                                activity.grid_view1.setVisibility(View.GONE);
                                activity.grid_view2.setVisibility(View.VISIBLE);
                                if(activity.materialCardList != null && activity.materialCardList.size()>0){
                                    for (MaterialCard materialCard:activity.materialCardList){
                                        if(materialCard.getId() != null){
                                            String materId = String.valueOf(materialCard.getId());
                                            if(materId.equals(activity.materialId)){
                                                materialCard.setChecked(true);
                                                activity.background = materialCard.getBackground();
                                                if(activity.background != null && !"".equals(activity.background)){
                                                }else {
                                                    activity.background = materialCard.getBackground();
                                                    activity.setCardBg();
                                                }

                                                if(materrialBack != null && !"".equals(materrialBack)){
                                                    if(activity.background.equals(materrialBack)){
                                                        materialCard.setChecked(true);
                                                    }
                                                }
                                                if(materialCard.getId() !=null){
                                                    activity.materialId = String.valueOf(materialCard.getId());

                                                }


                                                break;
                                            }
                                        }

                                    }
                                }
                                activity.materialCardAdapter.notifyDataSetChanged();
                            }
                            if("EDIT".equals(MemberCardNewActivity.memberCardStatusNew)){

                                activity.tv_card_template_left.setTextColor(activity.getResources().getColor(R.color.color_gray_deep));
                                activity.tv_card_material_right.setTextColor( activity.getResources().getColor(R.color.btn_color_blue));
                                activity.grid_view1.setVisibility(View.GONE);
                                activity.grid_view2.setVisibility(View.VISIBLE);

                                if( activity.materialCardList != null &&  activity.materialCardList.size()>0){
                                    for(MaterialCard materialCard: activity.materialCardList){
                                        String backGround = materialCard.getBackground();
                                        String  materialId2 = String.valueOf(materialCard.getId());
                                        if( activity.materialId.equals(materialId2)){
                                            activity.background = backGround;
                                            materialCard.setChecked(true);
                                        }
                                    }
                                    activity.materialCardAdapter.notifyDataSetChanged();
                                }
                                activity.setCardBg();
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
    public void updateMemberType(Context context, String userId, String compMemberTypeStr, String brandArray,String memtypeStatus) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("compMemberTypeStr",compMemberTypeStr);
        params.put("brandArray",brandArray);
        params.put("flag",memtypeStatus);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        MemberCardNewTwoActivity activity = (MemberCardNewTwoActivity) context;
        memberManage.updateMemberType(userId,compMemberTypeStr,brandArray,memtypeStatus)
                .subscribe(new BaseObserver<String>(context,true){
                    @Override
                    public void onSuccess(String result, String msg) {
                        if("1".equals(memtypeStatus)){
                            ToastUtils.showCustomShortBottom("已启用");
                        }else {
                            ToastUtils.showCustomShortBottom("已保存");

                        }

                        ActivityUtils.startActivity(MemberCardManageActivity.class, R.anim.slide_in_right,R.anim.slide_out_left);
                        activity.finish();
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);
                    }
                });
    }

    @Override
    public void insertMemberType(Context context, String userId, String compMemberTypeStr,String brandArray,String memtypeStatus) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("compMemberTypeStr",compMemberTypeStr);
        params.put("brandArray",brandArray);
        params.put("memtypeStatus",memtypeStatus);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        MemberCardNewTwoActivity activity = (MemberCardNewTwoActivity) context;
        memberManage.insertMemberType(userId,compMemberTypeStr,brandArray)
                .subscribe(new BaseObserver<Map<String,String>>(context,true){
                    @Override
                    public void onSuccess(Map<String,String> result, String msg) {
                        if("0".equals(memtypeStatus)){
                            ToastUtils.showCustomShortBottom("已保存");
                        }else {
                            ToastUtils.showCustomShortBottom("已启用");
                        }

                        ActivityUtils.startActivity(MemberCardManageActivity.class, R.anim.slide_in_right,R.anim.slide_out_left);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort(message);
                    }
                });

    }

    /**
     * 会员卡管理详情查询
     * */
    @Override
    public void doQueryByGroup(Context context, String userId, String compMemTypeId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        params.put("userId",userId);
        params.put("array",compMemTypeId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);

        MemberCardNewActivity activity = (MemberCardNewActivity) context;
        memberManage.doQueryByGroup(userId,compMemTypeId)
                .subscribe(new BaseObserver<CompMemTypeDetailBean>(context,false){
                    @Override
                    public void onSuccess(CompMemTypeDetailBean result,String msg) {

                        if(result != null ){
                            activity.compMemberType = result.getData();

                            activity.brand = result.getBrand();
                            if(activity.compMemberType != null ){


                                activity.compMemberGroup = activity.compMemberType.getCompMemberGroup();
                                //会员卡名称
                                activity.edt_card_name.setText(activity.compMemberGroup!= null && !"".equals(activity.compMemberGroup)?activity.compMemberGroup:"");
                                //品牌
                                if(activity.brand != null ){
                                    activity.brandName = activity.brand.getName();

                                }
                                //pos Id
                                activity.posId = activity.compMemberType.getPosId();
                                activity.edt_pos_id.setText(activity.posId != null && !"".equals(activity.posId)?activity.posId:"");


                                //消费加入条件
                                activity.joinCondition = activity.compMemberType.getJoinCondition();
                                //购买金额
                                BigDecimal price = activity.compMemberType.getPurchasingPrice();



                                //消费加入条件
                                if("1".equals(activity.joinCondition)){ //在线购买
                                    activity.ll_buy_money.setVisibility(View.VISIBLE);
                                    if(price != null && price.doubleValue()>0){
                                        activity.purchasingPrice = RegexUtils.getDoubleString(price.doubleValue());
                                    }else {
                                        activity.purchasingPrice = "";
                                    }
                                    //购买金额
                                    activity.edt_buy_money.setText(activity.purchasingPrice);
                                    activity.tv_consume_join.setText("在线购买（非储值）");
                                }else if("0".equals(activity.joinCondition)){ //免费领取
                                    activity.purchasingPrice = "";
                                    activity.ll_buy_money.setVisibility(View.GONE);
                                    activity.tv_consume_join.setText("免费在线领取");
                                }

                                //会员权益

                                activity.isShareright = activity.compMemberType.getIsShareright();

                                if("1".equals(activity.isShareright)){ //可共享
                                    activity.tv_other_share.setText("可共享");
                                }else if("0".equals(activity.isShareright)){//不可共享
                                    activity.tv_other_share.setText("不可共享");
                                }

                                //会员权益说明
                                activity.memtypeNotes = activity.compMemberType.getMemtypeNotes();

                                if(activity.memtypeNotes != null && !"".equals(activity.memtypeNotes)){
                                    activity.edt_rights_content.setText(activity.memtypeNotes);
                                    activity.rightsNum = String.valueOf(activity.memtypeNotes.length());
                                    activity.tv_rights_content_num.setText(activity.rightsNum+"/500");
                                }else {
                                    activity.tv_rights_content_num.setText(activity.rightsNum+"/500");
                                }

                                //N表示长期有效 A或R表示否
                                activity.effectiveValue = activity.compMemberType.getCustomTime();
                                if("N".equals(activity.effectiveValue)){
                                    activity.tv_long_effective.setText("是");
                                    activity.ll_long_effective.setVisibility(View.GONE);

                                }else {
                                    activity.tv_long_effective.setText("否");
                                    activity.wayValue = activity.compMemberType.getCustomTime();
                                    activity.ll_long_effective.setVisibility(View.VISIBLE);
                                    if("A".equals(activity.wayValue)){   //按起止时间天
                                        String endTime = activity.compMemberType.getAbsoluteEndtime();
                                        String startTime = activity.compMemberType.getAbsoluteStartime();
                                        if(startTime != null && !"".equals(startTime)){
                                            Date date = DateUtils.parse(startTime,"yyyy-MM-dd");
                                            activity.absoluteStartime = DateUtils.formatDate(date,"yyyy-MM-dd").replace("-",".");
                                            activity.tvStartTime.setText(DateUtils.formatDate(date,"yyyy-MM-dd").replace("-","."));
                                        }else {
                                            activity.tvStartTime.setText("");
                                        }

                                        if(endTime != null && !"".equals(endTime)){
                                            Date date = DateUtils.parse(endTime,"yyyy-MM-dd");
                                            activity.absoluteEndtime = DateUtils.formatDate(date,"yyyy-MM-dd").replace("-",".");
                                            activity.tvEndTime.setText(activity.absoluteEndtime);
                                        }else {
                                            activity.tvEndTime.setText("");
                                        }
                                        //设置方式
                                        activity.tv_set_way.setText("固定起止时间");

                                        activity.relativeTime = null;


                                        activity.rl_set_way_kai_ka.setVisibility(View.GONE);
                                        activity.ll_date.setVisibility(View.VISIBLE);
                                    }else if("R".equals(activity.wayValue)){ //按开卡日时间
                                        activity.tv_set_way.setText("根据开卡日设置");
                                        activity.rl_set_way_kai_ka.setVisibility(View.VISIBLE);
                                        activity.ll_date.setVisibility(View.GONE);
                                        activity.absoluteStartime = "";
                                        activity.absoluteEndtime  = "";
                                        //开卡之日起，多长时间有效
                                        Integer reTime = activity.compMemberType.getRelativeTime();
                                        if(reTime != null && reTime.intValue() >0){
                                            activity.relativeTime = String.valueOf(reTime);
                                        }else {
                                            activity.relativeTime = "";
                                        }
                                        activity.edt_set_way_kai_ka.setText(activity.relativeTime);

                                    }

                                    //过期前多久通知会员
                                    Integer proTime = activity.compMemberType.getPromptTime();
                                    if(proTime != null && proTime.intValue()>0){
                                        activity.promptTime = String.valueOf(proTime);
                                    }else {
                                        activity.promptTime = "";
                                    }
                                    activity.edt_set_way_guo_qi.setText(activity.promptTime);

                                    //过期提示语
                                    activity.reminder = activity.compMemberType.getReminder();
                                    if(activity.reminder != null && !"".equals(activity.reminder)){
                                        activity.guoQiNum = String.valueOf(activity.reminder.length());
                                        activity.tv_guo_qi_tip_num.setText(activity.guoQiNum+"/100");
                                        activity.edt_guo_qi_tip.setText(activity.reminder);
                                    }else {
                                        activity.edt_guo_qi_tip.setText(activity.reminder);
                                        if(activity.reminder != null && !"".equals(activity.reminder)){
                                            activity.guoQiNum = String.valueOf(activity.reminder.length());
                                            activity.tv_guo_qi_tip_num.setText(activity.guoQiNum+"/100");
                                        }


                                    }
                                }

                                //备注
                                activity.notes = activity.compMemberType.getNotes();
                                if(activity.notes != null && !"".equals(activity.notes)){
                                    activity.edt_remark.setText(activity.notes);
                                    activity.remarkNum = String.valueOf(activity.notes.length());
                                    activity.tv_remark_num.setText(activity.remarkNum+"/500");
                                }else {
                                    activity.tv_remark_num.setText("0/500");
                                }
                                //升级规则类型
                                activity.upgradeType = activity.compMemberType.getUpgradeType();

                                //是否选择模板还是素材
                                MemberCardNewTwoActivity.isTemplet = activity.compMemberType.getIsTemplet();
                                //素材
                                Long matId = activity.compMemberType.getMaterialId();
                                if(matId != null){
                                    MemberCardNewTwoActivity.materialId = String.valueOf(matId);

                                }else {
                                    MemberCardNewTwoActivity.materialId= "";
                                }
                                //从模板选择额
                                if("1".equals(MemberCardNewTwoActivity.isTemplet)){   //新建如果是从模板选择素材，在编辑时候会在素材里面展示，进入第二步选择模板素材该页面，会默认在素材里面展示选中
                                    MemberCardNewTwoActivity.background = activity.compMemberType.getBackground();

                                }else {
                                    MemberCardNewTwoActivity.background = activity.compMemberType.getMaterialBack();
                                    MemberCardNewTwoActivity.materialBack = activity.compMemberType.getMaterialBack();
                                }
//                                MemberCardNewTwoActivity.name = activity.compMemberType.getName();

                                MemberCardNewTwoActivity.displayRule = activity.compMemberType.getDisplayRule();
                                MemberCardNewTwoActivity.colour = activity.compMemberType.getColour();


                              /*  if("1".equals(MemberCardNewTwoActivity.isTemplet)){ //从模板选中\
                                    MemberCardNewTwoActivity.background = activity.compMemberType.getMaterialBack();


                                }else {//从素材选中

                                    MemberCardNewTwoActivity.materialBack = activity.compMemberType.getMaterialBack();
                                }*/

                                activity.isDisplayValue = activity.compMemberType.getVisible();
                                if("1".equals(activity.isDisplayValue)){//不可见
                                    activity.tv_home_is_display.setText("否");
                                }else  if("0".equals(activity.isDisplayValue)){ //可见
                                    activity.tv_home_is_display.setText("是");
                                }
                                activity.isDefault = activity.compMemberType.getIsDefault();
                                //默认会员卡
                                if("0".equals(activity.isDefault)){
                                    //免费在线领取
                                    activity.joinCondition = "0";
                                    activity.effectiveValue = "N";
                                    activity.tv_long_effective.setCompoundDrawables(null,null,null,null);
                                    activity.tv_consume_join.setCompoundDrawables(null,null,null,null);
                                    activity.ll_long_effective.setVisibility(View.GONE);
                                    activity.tvRight.setVisibility(View.GONE);
                                    activity.tv_home_is_display.setCompoundDrawables(null,null,null,null);
                                    activity.tv_home_is_display.setEnabled(false);
                                    activity.ll_long_effective.setVisibility(View.GONE);
                                }else if("1".equals(activity.isDefault)){ //不是默认
                                    activity.tvRight.setVisibility(View.VISIBLE);
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
    public void isReceivingAccount(Context context, String userId, String memtypeStatus) {
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
        MemberCardNewTwoActivity activity = (MemberCardNewTwoActivity) context;
        memberManage.isReceivingAccount(userId)
                .subscribe(new BaseObserver<String>(context,false){
                    @Override
                    public void onSuccess(String result, String msg) {


                        if("EDIT".equals(MemberCardNewActivity.memberCardStatusNew)){
                            updateMemberType(activity,userId,activity.compMemberTypeJSON,activity.brandId,memtypeStatus);
                        }else {
                            insertMemberType(activity,userId,activity.compMemberTypeJSON,activity.brandId,memtypeStatus);
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);


                        if(code ==3){
                            activity.setQiYongDialog(message);
                        }else{
                            ToastUtils.showShort(message);
                        }

                    }
                });
    }
}
