package com.zwx.scan.app.feature.couponmanage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.zwx.library.swipe.SwipeItemClickListener;
import com.zwx.library.swipe.SwipeMenu;
import com.zwx.library.swipe.SwipeMenuBridge;
import com.zwx.library.swipe.SwipeMenuCreator;
import com.zwx.library.swipe.SwipeMenuItem;
import com.zwx.library.swipe.SwipeMenuItemClickListener;
import com.zwx.library.swipe.SwipeMenuRecyclerView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.campaign.CampaignListActivity;
import com.zwx.scan.app.feature.campaign.CampaignNewActivity;
import com.zwx.scan.app.feature.campaign.CampaignNewNextThreeActivity;
import com.zwx.scan.app.feature.campaign.CampaignNewNextTwoActivity;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanNewActivity;
import com.zwx.scan.app.feature.campaign.SelectCampaignTypeAdapter;
import com.zwx.scan.app.feature.countanalysis.CountAnalysis;
import com.zwx.scan.app.feature.modulemore.ModuleMoreListActivity;
import com.zwx.scan.app.widget.RecycleViewDivider;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 优惠券管理
 * */
public class CouponManageActivity extends  BaseActivity<GiveCouponContract.Presenter> implements GiveCouponContract.View,View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ScrollView> {


    @BindView(R.id.iv_back)
    protected ImageView ivBack;
    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.rv_list)
    public SwipeMenuRecyclerView rvList;
    @BindView(R.id.ptr)
    public PullToRefreshScrollView ptr;
    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;
    @BindView(R.id.iv_no_data)
    protected ImageView iv_no_data;

    protected CouponManageListAdapter listAdapter = null;

//    protected RecyclerAdapterWithHF mAdapter;

    private String userId;
    private String compId;
    private int pageNumber = 1;
    private int pageSize = 10;
    private String campaignType = "zj";

    protected  AlertDialog.Builder builder  = null;
    protected AlertDialog dialog = null;
    private String couponStatus;
    protected List<CouponBean> couponList = new ArrayList<>();

    private  String storeId;

    private String couponId;
    //"类型：1:是否可以编辑、2：是否可以删除、3：是否可以停用"
    private String operateFlag = "";
    //根根据优惠券ID判断优惠券操作状态
    private String isStatus = "";
    protected   String couponType = "";
    protected int deletePostion = 0 ;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon_manage;
    }

    @Override
    protected void initView() {

        tvTitle.setText(getResources().getString(R.string.coupon_manage));

        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.new_label));
        DaggerGiveCouponComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .giveCouponModule(new GiveCouponModule(this))
                .build()
                .inject(this);
        userId = SPUtils.getInstance().getString("userId");
        storeId = SPUtils.getInstance().getString("storeId");
        compId = SPUtils.getInstance().getString("compId");
    }

    @Override
    protected void initData() {


        rvList.setSwipeItemClickListener(mItemClickListener);
        rvList.setSwipeMenuCreator(mSwipeMenuCreator);
        rvList.setSwipeMenuItemClickListener(mMenuItemClickListener);
        listAdapter = new CouponManageListAdapter(CouponManageActivity.this, couponList);
        rvList.setAdapter(listAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new RecycleViewDivider( LinearLayoutManager.VERTICAL, 20, getResources().getColor(R.color.divide_gray_color)));

        rvList.setHasFixedSize(true);
        rvList.setNestedScrollingEnabled(false);


        ptr.setOnRefreshListener(this);
        ptr.setMode(PullToRefreshBase.Mode.BOTH);

        ILoadingLayout endLabelsr = ptr.getLoadingLayoutProxy(false, true);

        endLabelsr.setPullLabel("上拉可以加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("加载中");// 刷新时
        endLabelsr.setReleaseLabel("松开加载更多");// 下来达到一定距离时，显示的提示


        ILoadingLayout startLabelse = ptr.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉可以刷新");// 刚下拉时，显示的提示
        startLabelse.setLastUpdatedLabel("正在刷新");// 刷新时
        startLabelse.setReleaseLabel("松开后刷新");// 下来达到一定距离时，显示的提示
        rvList.setVisibility(View.GONE);
        ll_no_data.setVisibility(View.VISIBLE);
        tv_no_data.setText("暂无优惠券记录数据！");
        presenter.doQueryCouponByCompId(CouponManageActivity.this, storeId, compId, pageNumber, pageSize,true);

    }


    @OnClick({R.id.iv_back,R.id.tv_right})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.startActivity(ModuleMoreListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
                break;

            case R.id.tv_right:
                setNewDialog();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.startActivity(ModuleMoreListActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber = 1;
        couponList.clear();
        presenter.doQueryCouponByCompId(CouponManageActivity.this, storeId, compId, pageNumber, pageSize,true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.doQueryCouponByCompId(CouponManageActivity.this, storeId, compId, pageNumber, pageSize,false);
    }
/* private void initPtr() {
        ptr.postDelayed(new Runnable() {

            @Override
            public void run() {
                ptr.autoRefresh(true);
            }
        }, 150);


        ptr.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                pageNumber = 1;
                presenter.doQueryCouponByCompId(CouponManageActivity.this, storeId, compId, pageNumber, pageSize,true);
            }
        });


        ptr.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNumber++;
                presenter.doQueryCouponByCompId(CouponManageActivity.this, storeId, compId, pageNumber, pageSize,false);

            }
        });
    }*/


    /**
     * RecyclerView的Item点击监听。
     */
    protected SwipeItemClickListener mItemClickListener = new SwipeItemClickListener() {
        @Override
        public void onItemClick(View itemView, int position) {
//            Toast.makeText(getContext(), "第" + position + "个", Toast.LENGTH_SHORT).show();

            if(couponList!=null && couponList.size()>0) {
                CouponBean coupon = couponList.get(position);
                String couponid = String.valueOf(coupon.getId());
                couponType = coupon.getType();
                presenter.checkStatus(CouponManageActivity.this,storeId,compId,couponid,"1");
            }



        }
    };

    protected void setIntentActivity(String couponId){
        Intent intent = new Intent(CouponManageActivity.this,CouponNewActivity.class);

        intent.putExtra("couponStatus","EDIT");
        String title = "";
        //CPC代金券，CPD折扣券，CPO赠品券，CPU菜品券、CPJ插队券、CPT其他
        if ("CPC".equals(couponType)) {   //代金券
            title = "代金券";
        } else if ("CPD".equals(couponType)) {
            title = "折扣券";
        } else if ("CPO".equals(couponType)) {
            title = "赠品券";
        } else if ("CPU".equals(couponType)) {
            title = "菜品券";
        } else if ("CPI".equals(couponType)) {
            title = "插队券";
        }

        intent.putExtra("title",title);
        intent.putExtra("isEditCoupon","YES");
        intent.putExtra("isCopyCreate","YES");
        intent.putExtra("couponId",couponId);
        intent.putExtra("couponType",couponType);
        ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
    }

    protected void setNoEditIntentActivity(String couponId){
        Intent intent = new Intent(CouponManageActivity.this,CouponNewActivity.class);

        intent.putExtra("couponStatus","NOEDIT");
        String title = "";
        //CPC代金券，CPD折扣券，CPO赠品券，CPU菜品券、CPJ插队券、CPT其他
        if ("CPC".equals(couponType)) {   //代金券
            title = "代金券";
        } else if ("CPD".equals(couponType)) {
            title = "折扣券";
        } else if ("CPO".equals(couponType)) {
            title = "赠品券";
        } else if ("CPU".equals(couponType)) {
            title = "菜品券";
        } else if ("CPI".equals(couponType)) {
            title = "插队券";
        }

        intent.putExtra("title",title);
        intent.putExtra("isEditCoupon","YES");
        intent.putExtra("isCopyCreate","YES");
        intent.putExtra("couponId",couponId);
        intent.putExtra("couponType",couponType);
        ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
    }

    /**
     * RecyclerView的Item中的Menu点击监听。
     */
    protected SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            CouponBean coupon = couponList.get(adapterPosition);
            if(couponList!= null &&couponList.size()>0){
                String status = "";

                if(coupon != null){
                    if(coupon.getStatus() != null ){
                        status = String.valueOf(coupon.getStatus());
                    }
                    if(coupon.getId() != null){
                        couponId = String.valueOf(coupon.getId());
                    }

                }

                if(coupon!=null ){
                   if("0".equals(status)){   //未使用

                       if(menuPosition == 0){
                           couponStatus = "启用";
                           operateFlag = "1";
                           isStatus = "1";  //是否删除   "1" 表示可编辑
                           presenter.doStatus(CouponManageActivity.this,storeId,compId,couponId,operateFlag);
                       }else if(menuPosition == 1){
                           deletePostion = adapterPosition;
                           couponStatus = "删除";
                           operateFlag = "3";
                           isStatus = "2";  //是否删除   "1" 表示可编辑
                           presenter.checkStatus(CouponManageActivity.this,storeId,compId,couponId,isStatus);
                       }
                       menuBridge.setText(couponStatus);

                      
                    }else if("1".equals(status)){
                        couponStatus = "停用";
                        operateFlag = "2";
                       isStatus = "3";  //是否停用
                       presenter.checkStatus(CouponManageActivity.this,storeId,compId,couponId,isStatus);
                       menuBridge.setText(couponStatus);
                    }



                }
            }

        }
    };

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    protected SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {

            String statusType = "";
            if(viewType == 0){  //未使用
                statusType = "0";
                createMemuWeiShiYong(swipeRightMenu);
            }else if(viewType == 1){   //启用
                statusType = "1";
                createMemuTingyong(swipeRightMenu);
            }
        }
    };



    private void createMemuWeiShiYong(SwipeMenu swipeRightMenu){
        // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
        // 2. 指定具体的高，比如80;
        // 3. WRAP_CONTENT，自身高度，不推荐;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        int width = getResources().getDimensionPixelSize(R.dimen.margin_100);
        SwipeMenuItem leftItem= new SwipeMenuItem(CouponManageActivity.this)
                .setBackground(R.drawable.shape_selector_blue)
                .setWidth(width)
                .setHeight(height)
                .setTextSize(16)
                .setTextColor(getResources().getColor(R.color.white));
        leftItem.setText("启用");
        swipeRightMenu.addMenuItem(leftItem);
        SwipeMenuItem rightItem= new SwipeMenuItem(CouponManageActivity.this)
                .setBackground(R.drawable.shape_selector_red)
                .setWidth(width)
                .setHeight(height)
                .setTextSize(16)
                .setTextColor(getResources().getColor(R.color.white));
        rightItem.setText("删除");

        swipeRightMenu.addMenuItem(rightItem);
    }
    private void createMemuTingyong(SwipeMenu swipeRightMenu){
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        int width = getResources().getDimensionPixelSize(R.dimen.margin_100);
        SwipeMenuItem closeItem= new SwipeMenuItem(CouponManageActivity.this)
                .setBackground(R.drawable.shape_selector_red)
                .setWidth(width)
                .setHeight(height)
                .setTextSize(16)
                .setTextColor(getResources().getColor(R.color.white));
        closeItem.setText("停用");
        swipeRightMenu.addMenuItem(closeItem);
    }




    protected void setcouponStatusDialog(){
        String tip = "";
        //编辑为1
        if("2".equals(isStatus)){
            tip = "是否确认删除优惠券，删除后将"+"<font size = '16'color = \'#0486FE\' weight = 'bolder'>无法恢复！</font>";
        }else if("3".equals(isStatus)){//停用
            tip = "您确定要停用该优惠券吗？停用后将"+"<font size = '16'color = \'#0486FE\' weight = 'bolder'>无法发放该优惠券！</font>";
        }
        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView message = (TextView)rootView.findViewById(R.id.message);
        message.setText(Html.fromHtml(tip));
        TextView tvTitle = (TextView)rootView.findViewById(R.id.title);
        tvTitle.setText("提示");

        rootView.findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.doStatus(CouponManageActivity.this,storeId,compId,couponId,operateFlag);
                dialog.dismiss();

            }
        });

        rootView.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        rootView.findViewById(R.id.cancelBtn).setVisibility(View.VISIBLE);
    }

    protected void setResultMessageDialog(){
        String tip = "";
        if("1".equals(operateFlag)){  //启用
            tip = "该优惠券"+"<font size = '16'color = \'#0486FE\' weight = 'bolder'>无法恢复！</font>";
        }else if("2".equals(operateFlag)){ //停用
            tip = "该优惠券"+"<font size = '16'color = \'#0486FE\' weight = 'bolder'>无法被停用</font>" + "，有会员持有该优惠券或者有活动正在使用该优惠券。";
        }else if("3".equals(operateFlag)){  //删除
            tip = "该优惠券"+"<font size = '16'color = \'#0486FE\' weight = 'bolder'>无法被删除</font>"+"，有会员持有该优惠券或者有活动正在使用该优惠券。";
        }

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView message = (TextView)rootView.findViewById(R.id.message);
        message.setText(Html.fromHtml(tip));
        TextView tvTitle = (TextView)rootView.findViewById(R.id.title);
        tvTitle.setText("提示");

        rootView.findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        rootView.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        rootView.findViewById(R.id.cancelBtn).setVisibility(View.VISIBLE);
    }



    private void setNewDialog(){


        View rootView = View.inflate(this, R.layout.layout_select_campaign_grid, null);
        builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        LinearLayout ivCancel = (LinearLayout) rootView.findViewById(R.id.ll_cancel);
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        TextView tv_tip = (TextView)rootView.findViewById(R.id.tv_tip);
        tv_tip.setVisibility(View.GONE);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);

        tvTitle.setText("选择优惠券类型");
        RecyclerView rvGrid = (RecyclerView)rootView.findViewById(R.id.rv_grid);
        List<CountAnalysis> list = new ArrayList<>();
        CountAnalysis countAnalysis = new CountAnalysis();

        countAnalysis.setDrawableName(R.drawable.ic_coupon_djq);
        countAnalysis.setMemTypeName("代金券");
        countAnalysis.setMemTypeNameEn("CPC");
//        countAnalysis.setTitle("转发活动");
        list.add(countAnalysis);

       /* countAnalysis = new CountAnalysis();

        countAnalysis.setDrawableName(R.drawable.ic_analysis_give);
        countAnalysis.setMemTypeName("常规发券");
        countAnalysis.setMemTypeNameEn("zj");
        countAnalysis.setTitle("直接发放活动");
        list.add(countAnalysis);*/
        countAnalysis = new CountAnalysis();
        countAnalysis.setDrawableName(R.drawable.ic_coupon_zkq);
        countAnalysis.setMemTypeName("折扣券");
        countAnalysis.setMemTypeNameEn("CPD");
//        countAnalysis.setTitle("老虎机活动");
        list.add(countAnalysis);

        countAnalysis = new CountAnalysis();
        countAnalysis.setDrawableName(R.drawable.ic_coupon_cpq);
        countAnalysis.setMemTypeName("菜品券");
        countAnalysis.setMemTypeNameEn("CPU");
        list.add(countAnalysis);

        countAnalysis = new CountAnalysis();
        countAnalysis.setDrawableName(R.drawable.ic_coupon_zpq);
        countAnalysis.setMemTypeName("赠品券");
        countAnalysis.setMemTypeNameEn("CPO"); //礼品券
        list.add(countAnalysis);

      /*  countAnalysis = new CountAnalysis();
        countAnalysis.setDrawableName(R.drawable.ic_coupon_qt);
        countAnalysis.setMemTypeNameEn("CPI"); //其他
        list.add(countAnalysis);

        countAnalysis = new CountAnalysis();
        countAnalysis.setDrawableName(R.drawable.ic_coupon_qidai);
        list.add(countAnalysis);*/
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        rvGrid.setLayoutManager(layoutManager);
        rvGrid.setAdapter(new SelectCampaignTypeAdapter(CouponManageActivity.this, list, new CommonRecyclerViewHolder.OnItemCommonClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = new Intent();
                CountAnalysis countAnalysis1 = list.get(position);
                removeCouponData();
                dialog.dismiss();
                if("CPC".equals(countAnalysis1.getMemTypeNameEn())){
                    intent.setClass(CouponManageActivity.this,CouponNewActivity.class);
                    intent.putExtra("couponStatus","NEW");
                    intent.putExtra("couponType",countAnalysis1.getMemTypeNameEn());
                    intent.putExtra("title","代金券");
                    intent.putExtra("isEditCoupon","NO");
                    intent.putExtra("isCopyCreate","NO");
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("CPD".equals(countAnalysis1.getMemTypeNameEn())){
                    intent.setClass(CouponManageActivity.this,CouponNewActivity.class);
                    intent.putExtra("couponStatus","NEW");
                    intent.putExtra("couponType",countAnalysis1.getMemTypeNameEn());
                    intent.putExtra("title","折扣券");
                    intent.putExtra("isEditCoupon","NO");
                    intent.putExtra("isCopyCreate","NO");
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right,R.anim.slide_out_left);

                }else if("CPU".equals(countAnalysis1.getMemTypeNameEn())){
                    intent.setClass(CouponManageActivity.this,CouponNewActivity.class);
                    intent.putExtra("couponStatus","NEW");
                    intent.putExtra("couponType",countAnalysis1.getMemTypeNameEn());
                    intent.putExtra("title","菜品券");
                    intent.putExtra("isEditCoupon","NO");
                    intent.putExtra("isCopyCreate","NO");
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("CPO".equals(countAnalysis1.getMemTypeNameEn())){
                    intent.setClass(CouponManageActivity.this,CouponNewActivity.class);
                    intent.putExtra("couponStatus","NEW");
                    intent.putExtra("couponType",countAnalysis1.getMemTypeNameEn());
                    intent.putExtra("title","赠品券");
                    intent.putExtra("isEditCoupon","NO");
                    intent.putExtra("isCopyCreate","NO");
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("CPI".equals(countAnalysis1.getMemTypeNameEn())){
                    intent.setClass(CouponManageActivity.this,CouponNewActivity.class);
                    intent.putExtra("couponStatus","NEW");
                    intent.putExtra("couponType",countAnalysis1.getMemTypeNameEn());
                    intent.putExtra("title","其他");
                    intent.putExtra("isEditCoupon","NO");
                    intent.putExtra("isCopyCreate","NO");

                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right,R.anim.slide_out_left);
                }
            }
        }));

    }

    private void removeCouponData(){
        CouponNewActivity.coupon = null;
    }



}
