package com.zwx.scan.app.feature.campaign;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zwx.library.utils.LogUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.MemberInfoBean;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.home.HomeFragment;
import com.zwx.scan.app.feature.member.MemberInfoListActivity;
import com.zwx.scan.app.feature.member.MemberInfoListAdapter;
import com.zwx.scan.app.widget.MyGridView;
import com.zwx.scan.app.widget.MyListView;
import com.zwx.scan.app.widget.SmoothCheckBox;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CampaignCouponListFragment extends BaseFragment<CampaignsContract.Presenter> implements CampaignsContract.View, PullToRefreshBase.OnRefreshListener2<ScrollView>  {

    @BindView(R.id.ptr)
    public PullToRefreshScrollView ptr;

    @BindView(R.id.list_view)
    public MyListView listView;
    @BindView(R.id.ll_no_data)
    public LinearLayout ll_no_data;

    @BindView(R.id.tv_no_data)
    public TextView tv_no_data;
    public static String type;


    private String userId;
    private String campaignId = "";
    private int pageNumber = 1;
    private int pageSize = 10;

    public CouponListViewAdapter listAdapter;
//    public RecyclerAdapterWithHF mAdapter;
    public  List<Coupon> couponList = new ArrayList<>();

    private  String couponType = "CPC";

    private  boolean isRefresh = true;

    public  static int selectNum = 0;

    public  ArrayList<Coupon> selectCouponList =new ArrayList<>();

    public  static ArrayList<Coupon> selectCoupons = new ArrayList<>();

    private  UpdateSelectCouponNum updateSelectCouponNum = null;
    private  CampaignCouponListActivity couponListActivity ;

    private boolean isCreated=false;
    public CampaignCouponListFragment() {
    }

    public static CampaignCouponListFragment getInstance(String type) {
        CampaignCouponListFragment fragment = new CampaignCouponListFragment();
        fragment.couponType = type;
        return fragment;
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_campaign_coupon_list;
    }

    @Override
    protected void initInjector() {
        DaggerCampaignsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignsModule(new CampaignsModule(this))
                .build()
                .inject(this);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated=true;
    }
    @Override
    protected void initView() {

//        EventBus.getDefault().register(this);
        userId = SPUtils.getInstance().getString("userId");

        listAdapter = new CouponListViewAdapter(getActivity(), couponList);

        listView.setAdapter(listAdapter);

        ptr.setOnRefreshListener(this);
        ptr.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout endLabelsr = ptr.getLoadingLayoutProxy(false, true);

        endLabelsr.setPullLabel("上拉可以加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("加载中");// 刷新时
        endLabelsr.setReleaseLabel("松开加载更多");// 下来达到一定距离时，显示的提示


        ILoadingLayout startLabelse = ptr.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉可以刷新");// 刚下拉时，显示的提示
        startLabelse.setLastUpdatedLabel("正在刷新");// 刷新时
        startLabelse.setReleaseLabel("松开后刷新");

//        initPtr();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //注销注册
//        EventBus.getDefault().unregister(this);
    }

    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isCreated){
            return;
        }else {
            couponList.clear();
            pageNumber = 1;
            //其他券停顿加载，防止同时请求 报401认证不通过
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    //休眠3秒
                    try{
                        Thread.sleep(1000);
                        presenter.queryCoupon(getActivity(), userId, campaignId,couponType,pageNumber, pageSize, isRefresh);
                    }catch (InterruptedException ex){

                    }

                }
            }.start();

        }
    }*/

    @Override
    public void onResume() {
        super.onResume();
        presenter.queryCoupon(getActivity(), userId, campaignId,couponType,pageNumber, pageSize, isRefresh);
      //CPC代金券，CPD折扣券，CPO赠品券，CPU菜品券、CPJ插队券、CPT其他
        /*if("CPC".equals(couponType)){
            presenter.queryCoupon(getActivity(), userId, campaignId,couponType,pageNumber, pageSize, isRefresh);
        }else  {

            if("CPD".equals(couponType)){
                //其他券停顿加载，防止同时请求 报401认证不通过
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //休眠3秒
                        try{
                            Thread.sleep(1000);
                            presenter.queryCoupon(getActivity(), userId, campaignId,couponType,pageNumber, pageSize, isRefresh);
                        }catch (InterruptedException ex){

                        }

                    }
                }.start();

            }else if("CPO".equals(couponType)){
                //其他券停顿加载，防止同时请求 报401认证不通过

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //休眠3秒
                        try{
                            Thread.sleep(3000);
                            presenter.queryCoupon(getActivity(), userId, campaignId,couponType,pageNumber, pageSize, isRefresh);
                        }catch (InterruptedException ex){

                        }

                    }
                }.start();
            }else if("CPU".equals(couponType)){
                //其他券停顿加载，防止同时请求 报401认证不通过
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //休眠3秒
                        try{
                            Thread.sleep(4000);
                            presenter.queryCoupon(getActivity(), userId, campaignId,couponType,pageNumber, pageSize, isRefresh);
                        }catch (InterruptedException ex){

                        }

                    }
                }.start();

            }else if("CPJ".equals(couponType)){
                //其他券停顿加载，防止同时请求 报401认证不通过
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //休眠3秒
                        try{
                            Thread.sleep(5000);
                            presenter.queryCoupon(getActivity(), userId, campaignId,couponType,pageNumber, pageSize, isRefresh);
                        }catch (InterruptedException ex){

                        }

                    }
                }.start();

            }else {
                    //其他券停顿加载，防止同时请求 报401认证不通过
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            //休眠3秒
                            try{
                                Thread.sleep(6000);
                                presenter.queryCoupon(getActivity(), userId, campaignId,couponType,pageNumber, pageSize, isRefresh);
                            }catch (InterruptedException ex){

                            }
                        }
                    }.start();
                }
            }*/

    }




/*
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(LhPtEventBus event){  //tablayout 切换
        if(event != null){
            couponType = event.getName();
            couponList.clear();
//            //CPC代金券，CPD折扣券，CPO礼品券，CPU菜品券、CPJ插队券、CPT其他
            presenter.queryCoupon(getActivity(), userId, campaignId,couponType,pageNumber, pageSize, isRefresh);
        }
    }*/

    @Override
    protected void initData() {



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentNum == -1){ //选中
                    if(couponList.get(position).isChecked()){
                        couponList.get(position).setChecked(false);
                        selectNum--;
                        if(selectNum  <= -1){
                            selectNum = 0;
                        }
                        //删除
                        setRemoveUnchecked(couponList.get(position));
                    }else {
                        couponList.get(position).setChecked(true);
                        selectNum++;
                        selectCoupons .add(couponList.get(position));

                    }

                    currentNum = position;
                }else if(currentNum == position){ //同一个item选中变未选中
                    couponList.get(position).setChecked(false);
                    currentNum = -1;
                    selectNum--;
                    if(selectNum <= -1){
                        selectNum = 0;
                    }
                    setRemoveUnchecked(couponList.get(position));
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的

                    if(couponList.get(position).isChecked()){
                        couponList.get(position).setChecked(false);
                        selectNum-- ;
                        if(selectNum  <= -1){
                            selectNum = 0;
                        }
                        setRemoveUnchecked(couponList.get(position));
                    }else {
                        couponList.get(position).setChecked(true);
                        selectNum ++;

                        selectCoupons .add(couponList.get(position));
                    }
                    currentNum = position;
                }

                listAdapter.notifyDataSetChanged();

                couponListActivity = (CampaignCouponListActivity)getActivity();
                couponListActivity.updateSelectNum(selectNum);
            }
        });

    }

    protected   void  setRemoveUnchecked(Coupon removeCoupon){

        if(selectCoupons !=null && selectCoupons.size()>0){
            for (int i = 0;i <selectCoupons.size();i++){
                Coupon couponUn = selectCoupons.get(i);
                if(couponUn!=null){
                    boolean unChecked = couponUn.isChecked();
                    if(!unChecked){
                        String removeName = removeCoupon.getName();
                        String removeName2 = couponUn.getName();
                        if(removeName!=null){
                            if(removeName2!=null){
                                if(removeName.equals(removeName2)){
                                    selectCoupons.remove(i);
                                    i--;
                                }
                            }
                        }
                    }


                }
            }
        }
    }



    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        isRefresh = true;
        couponList.clear();
        pageNumber = 1;
        presenter.queryCoupon(getActivity(), userId, campaignId,couponType,pageNumber, pageSize, isRefresh);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        isRefresh = false;
        pageNumber++;
        presenter.queryCoupon(getActivity(), userId, campaignId,couponType,pageNumber, pageSize, false);
    }


    interface  UpdateSelectCouponNum{
        void  updateSelectNum(int selectNum);
    }

}
