package com.zwx.scan.app.feature.ptmanage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.zwx.scan.app.data.bean.CampaignGroupBuy;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.campaign.CampaignListActivity;
import com.zwx.scan.app.feature.campaign.SelectCampaignTypeAdapter;
import com.zwx.scan.app.feature.countanalysis.CountAnalysis;
import com.zwx.scan.app.feature.couponmanage.GiveCouponNewActivity;
import com.zwx.scan.app.feature.modulemore.ModuleMoreListActivity;
import com.zwx.scan.app.widget.RecycleViewDivider;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PtManageActivity extends BaseActivity<PtContract.Presenter> implements PtContract.View,View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ScrollView>{


    @BindView(R.id.iv_back)
    protected ImageView ivBack;
    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.rv_list)
    public SwipeMenuRecyclerView rvList;
//    @BindView(R.id.ptr)
//    public PtrClassicFrameLayout ptr;
    @BindView(R.id.ptr)
    public PullToRefreshScrollView ptr;

    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;
//    @BindView(R.id.ll_no_data)
//    protected LinearLayout llNoData;

    //    protected List<CampaignCoupon> campaignList = new ArrayList<>();
    protected PinTuanListAdapter listAdapter = null;

//    protected RecyclerAdapterWithHF mAdapter;

    private String userId;
    private String compId;
    protected int pageNumber = 1;
    private int pageSize = 10;
    private String campaignType = "pt";

    protected  AlertDialog.Builder builder  = null;
    protected AlertDialog dialog = null;
    private String campaignStatus;
    protected List<Campaign> campaignList = new ArrayList<>();
    protected List<CampaignCoupon>  campaignCouponList = new ArrayList<>();
    private List<SwipeMenuItem>  swipeMenuItemList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pt_manage;
    }

    @Override
    protected void initView() {
        tvTitle.setText(getResources().getString(R.string.pt_manage));

        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.new_label));
        DaggerPtComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .ptModule(new PtModule(this))
                .build()
                .inject(this);
        userId = SPUtils.getInstance().getString("userId");
        compId = SPUtils.getInstance().getString("compId");

    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.queryCampaignList(PtManageActivity.this, userId, compId, pageNumber, pageSize,campaignType,true);

    }

    @Override
    protected void initData() {

        rvList.setSwipeItemClickListener(mItemClickListener);
        rvList.setSwipeMenuCreator(mSwipeMenuCreator);
        rvList.setSwipeMenuItemClickListener(mMenuItemClickListener);
        listAdapter = new PinTuanListAdapter(PtManageActivity.this, campaignList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new RecycleViewDivider( LinearLayoutManager.VERTICAL, 20, getResources().getColor(R.color.divide_gray_color)));
        rvList.setHasFixedSize(true);
        rvList.setNestedScrollingEnabled(false);
        rvList.setAdapter(listAdapter);

        ptr.setOnRefreshListener(this);
        ptr.setMode(PullToRefreshBase.Mode.BOTH);
//        ptr.setRefreshing(PullToRefreshBase.Mode.PULL_FROM_START);//自动出现下拉布局
//        ptr.setRefreshing(PullToRefreshBase.Mode.PULL_FROM_END);//自动出现上拉布局
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



    }



    @OnClick({R.id.iv_back,R.id.tv_right})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                removePtCampaignData();
                ActivityUtils.finishActivity(this);

                ActivityUtils.startActivity(ModuleMoreListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);

                break;
            case R.id.tv_right:

                Intent intent = new Intent();
                intent.setClass(PtManageActivity.this,PtNewActivity.class);
                intent.putExtra("compaignStatus","NEW");
                intent.putExtra("campaignType","pt");
                intent.putExtra("title","拼团管理");
                intent.putExtra("isEditCampaign","NO");
                intent.putExtra("isCopyCreate","NO");
                removePtCampaignData();
                ActivityUtils.startActivity(intent,
                        R.anim.slide_in_right,R.anim.slide_out_left);
                break;

        }
    }


    private void removePtCampaignData(){



        PtNextTwoActivity.campaignGamesetrewardList = new ArrayList<>();

        PtNextTwoActivity.couponList1 = new ArrayList<>();
        PtNextTwoActivity.couponList2= new ArrayList<>();
        PtNextTwoActivity.campaignCouponList1 = new ArrayList<>();
        PtNextTwoActivity.campaignCouponList2 = new ArrayList<>();
        PtNewActivity.campaign = new Campaign();
        PtNextThreeActivity.campaignGroupBuy = new CampaignGroupBuy();
    }

    private void remove(List<Coupon> selectCouponList){
        for(int i=0;i<selectCouponList.size();i++){
            for(int j=selectCouponList.size()-1;j>i;j--){
                if(selectCouponList.get(i).getId()==selectCouponList.get(j).getId()){
                    selectCouponList.remove(j);
                }
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        removePtCampaignData();
        finish();
        ActivityUtils.startActivity(ModuleMoreListActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        campaignList.clear();
        pageNumber = 1;
        presenter.queryCampaignList(PtManageActivity.this, userId, compId, pageNumber, pageSize,campaignType,true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.queryCampaignList(PtManageActivity.this, userId, compId, pageNumber, pageSize,campaignType,true);
    }

    /**
     * RecyclerView的Item点击监听。
     */
    protected SwipeItemClickListener mItemClickListener = new SwipeItemClickListener() {
        @Override
        public void onItemClick(View itemView, int position) {
//            Toast.makeText(getContext(), "第" + position + "个", Toast.LENGTH_SHORT).show();

            removePtCampaignData();
            if(campaignList!=null && campaignList.size()>0){
                Campaign campaign = campaignList.get(position);
                removePtCampaignData();
//                presenter.doEdit(PtManageActivity.this,String.valueOf(campaign.getCampaignId()));
                Intent intent = new Intent(PtManageActivity.this,PtNewActivity.class);
                intent.putExtra("compaignStatus",campaign.getCompaignStatus());
                intent.putExtra("campaignType","pt");
                intent.putExtra("title","拼团设置");

                intent.putExtra("isEditCampaign","YES");
                intent.putExtra("isCopyCreate","YES");
                intent.putExtra("campaignId",String.valueOf(campaign.getCampaignId()));
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
            }

        }
    };

    /**
     * RecyclerView的Item中的Menu点击监听。
     */
    protected SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();
//            menuBridge.setText("");
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            Campaign campaign = campaignList.get(adapterPosition);
            if(campaignList!= null &&campaignList.size()>0){
                String status =campaign.getCompaignStatus();
                Long campaignId = campaign.getCampaignId();
                String operateFlag = "";
                if(campaign!=null ){
                    if("S".equals(status)){
                        campaignStatus = status;
                        if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                            if(campaignId !=null){
                                setCampaignStatusDialog("",String.valueOf(campaignId),adapterPosition);
                            }
                        }
                        menuBridge.setText(campaignStatus);
                    }else if("N".equals(status)){
//                        campaignStatus = "未开始";
                        campaignStatus = status;

                        operateFlag = "1";
                        setCampaignStatusDialog(operateFlag,String.valueOf(campaignId),adapterPosition);

                    }else if("P".equals(status)){
//                        campaignStatus = "进行中";
                        campaignStatus = status;
                        operateFlag = "2";
                        setCampaignStatusDialog(operateFlag,String.valueOf(campaignId),adapterPosition);

                    }else if("E".equals(status)){
                        campaignStatus = "已结束";
                    }else if("C".equals(status)){
                        campaignStatus = "已作废";
                        operateFlag = "1";
                    }else if("I".equals(status)){
                        campaignStatus = "提前结束";
                        menuBridge.setText(campaignStatus);
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
            int width = getResources().getDimensionPixelSize(R.dimen.margin_100);
            String statusType = "";
            if(viewType == 0){
                statusType = "S";
                createMemuDelete(swipeRightMenu);
            }else if(viewType == 1){
                statusType = "N";
                createMemuZuofei(swipeRightMenu);
            }else if(viewType == 2){
                statusType = "P";
                createMemuEnd(swipeRightMenu);
            }

        }
    };



    private void createMemuDelete(SwipeMenu swipeRightMenu){
        // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
        // 2. 指定具体的高，比如80;
        // 3. WRAP_CONTENT，自身高度，不推荐;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        int width = getResources().getDimensionPixelSize(R.dimen.margin_100);
        SwipeMenuItem closeItem= new SwipeMenuItem(PtManageActivity.this)
                .setBackground(R.drawable.shape_selector_red)
                .setWidth(width)
                .setHeight(height)
                .setTextSize(16)
                .setTextColor(getResources().getColor(R.color.white));
        closeItem.setText("删除");
        swipeRightMenu.addMenuItem(closeItem);
    }
    private void createMemuZuofei(SwipeMenu swipeRightMenu){
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        int width = getResources().getDimensionPixelSize(R.dimen.margin_100);
        SwipeMenuItem closeItem= new SwipeMenuItem(PtManageActivity.this)
                .setBackground(R.drawable.shape_selector_red)
                .setWidth(width)
                .setHeight(height)
                .setTextSize(16)
                .setTextColor(getResources().getColor(R.color.white));
        closeItem.setText("作废");
        swipeRightMenu.addMenuItem(closeItem);
    }

    private void createMemuEnd(SwipeMenu swipeRightMenu){
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        int width = getResources().getDimensionPixelSize(R.dimen.margin_100);
        SwipeMenuItem closeItem= new SwipeMenuItem(PtManageActivity.this)
                .setBackground(R.drawable.shape_selector_red)
                .setWidth(width)
                .setHeight(height)
                .setTextSize(16)
                .setTextColor(getResources().getColor(R.color.white));
        closeItem.setText("提前结束");
        swipeRightMenu.addMenuItem(closeItem);
    }


    private void setCampaignStatusDialog(final String operateFlag,final String campaignId,final int position){
        String tip = "";

        if("S".equals(campaignStatus)){
            tip = "是否确认要删除该拼团";
        }else if("N".equals(campaignStatus)){
            tip = "是否确认要废止该拼团";
        }else if("P".equals(campaignStatus)){
            tip = "是否确认要提前结束该拼团";
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


               /* if("S".equals(status)){
                    dialog.dismiss();
                    presenter.doCampaignDelete(CampaignListActivity.this,campaignId,position);
                }*/

                if("S".equals(campaignStatus)){
                    presenter.doCampaignDelete(PtManageActivity.this,campaignId,position);
                }else if("N".equals(campaignStatus)){
                    presenter.doCampaignUpdate(PtManageActivity.this,String.valueOf(campaignId),operateFlag);
                }else if("P".equals(campaignStatus)){
                    presenter.doCampaignUpdate(PtManageActivity.this,String.valueOf(campaignId),operateFlag);
                }
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

        TextView tvTitle = (TextView)rootView.findViewById(R.id.tvTitle);
        tvTitle.setText("选择发放类型");
        tvTitle.setTextColor(getResources().getColor(R.color.coupon_list_color));
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



        RecyclerView rvGrid = (RecyclerView)rootView.findViewById(R.id.rv_grid);
        List<CountAnalysis> list = new ArrayList<>();
        CountAnalysis countAnalysis = new CountAnalysis();

        countAnalysis.setDrawableName(R.drawable.ic_coupon_kaikali);
        countAnalysis.setMemTypeName("开卡礼");
        countAnalysis.setMemTypeNameEn("K");
//        countAnalysis.setTitle("转发活动");
        list.add(countAnalysis);

        countAnalysis = new CountAnalysis();

        countAnalysis.setDrawableName(R.drawable.ic_coupon_shengrili3);
        countAnalysis.setMemTypeName("生日礼");
        countAnalysis.setMemTypeNameEn("B");
//        countAnalysis.setTitle("直接发放活动");
        list.add(countAnalysis);
        countAnalysis = new CountAnalysis();
        countAnalysis.setDrawableName(R.drawable.ic_coupon_huiyuanri);
        countAnalysis.setMemTypeName("会员日");
        countAnalysis.setMemTypeNameEn("M");
//        countAnalysis.setTitle("直接发放活动");
        list.add(countAnalysis);
        countAnalysis = new CountAnalysis();
        countAnalysis.setDrawableName(R.drawable.ic_coupon_zhouqili);
        countAnalysis.setMemTypeName("会员周期礼");
        countAnalysis.setMemTypeNameEn("Z");
//        countAnalysis.setTitle("直接发放活动");
        list.add(countAnalysis);
        countAnalysis = new CountAnalysis();
        countAnalysis.setDrawableName(R.drawable.ic_coupon_huanxingxiaofeili);
        countAnalysis.setMemTypeName("唤醒消费礼");
        countAnalysis.setMemTypeNameEn("S");
//        countAnalysis.setTitle("直接发放活动");
        list.add(countAnalysis);

      /*  countAnalysis = new CountAnalysis();

        countAnalysis.setDrawableName(R.drawable.ic_analysis_staff);
        countAnalysis.setMemTypeName("积点活动");
        countAnalysis.setMemTypeNameEn("Points");
        countAnalysis.setTitle("积点活动");
        list.add(countAnalysis);
        countAnalysis.setDrawableName(R.drawable.ic_analysis_staff);
        countAnalysis.setMemTypeName("老虎机活动");
        countAnalysis.setMemTypeNameEn("Slot machine ");
        countAnalysis.setTitle("老虎机活动");
        list.add(countAnalysis);*/


        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        rvGrid.setLayoutManager(layoutManager);
        rvGrid.setAdapter(new SelectCampaignTypeAdapter(PtManageActivity.this, list, new CommonRecyclerViewHolder.OnItemCommonClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = new Intent();
                CountAnalysis countAnalysis1 = list.get(position);

                dialog.dismiss();
                if("开卡礼".equals(countAnalysis1.getMemTypeName())){
                    intent.setClass(PtManageActivity.this,GiveCouponNewActivity.class);
                    intent.putExtra("compaignStatus","NEW");
                    intent.putExtra("campaignType","zj");
                    intent.putExtra("grantType",countAnalysis1.getMemTypeNameEn());
                    intent.putExtra("title",countAnalysis1.getMemTypeName());
                    intent.putExtra("isEditCampaign","NO");
                    intent.putExtra("isCopyCreate","NO");
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("生日礼".equals(countAnalysis1.getMemTypeName())){
                    intent.setClass(PtManageActivity.this,GiveCouponNewActivity.class);
                    intent.putExtra("compaignStatus","NEW");
                    intent.putExtra("grantType",countAnalysis1.getMemTypeNameEn());
                    intent.putExtra("campaignType","zj");
                    intent.putExtra("title",countAnalysis1.getMemTypeName());
                    intent.putExtra("isEditCampaign","NO");
                    intent.putExtra("isCopyCreate","NO");
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right,R.anim.slide_out_left);

                }else if("拉新报表".equals(countAnalysis1.getMemTypeName())){
                }
            }
        }));

    }





}
