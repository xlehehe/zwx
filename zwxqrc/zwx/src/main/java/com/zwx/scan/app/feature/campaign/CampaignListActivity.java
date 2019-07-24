package com.zwx.scan.app.feature.campaign;

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
import com.zwx.scan.app.data.bean.CampaignCollect;
import com.zwx.scan.app.data.bean.CampaignGame;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.countanalysis.CountAnalysis;
import com.zwx.scan.app.feature.modulemore.ModuleMoreListActivity;
import com.zwx.scan.app.widget.RecycleViewDivider;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CampaignListActivity extends BaseActivity<CampaignsContract.Presenter> implements CampaignsContract.View ,View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ScrollView> {

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
//    @BindView(R.id.ptr)
//    public PtrClassicFrameLayout ptr;


    @BindView(R.id.ll_no_data)
    protected RelativeLayout ll_no_data;
    @BindView(R.id.iv_no_data)
    protected ImageView iv_no_data;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;

    protected List<Campaign> campaignList = new ArrayList<>();
    protected CampaignListAdatper listAdapter = null;

//        protected RecyclerAdapterWithHF mAdapter;
//    protected SwipeAdapterWrapper mAdapter;

    private String userId;
    private String compId;
    protected int pageNumber = 1;
    private int pageSize = 10;
    private String campaignType = "zf-ge-lh-jz-hy";

    protected  AlertDialog.Builder builder  = null;
    protected AlertDialog dialog = null;
    private String campaignStatus;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_campaign_list;
    }

    @Override
    protected void initView() {
        tvTitle.setText("活动管理");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("新建");
        DaggerCampaignsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignsModule(new CampaignsModule(this))
                .build()
                .inject(this);
        userId = SPUtils.getInstance().getString("userId");
        compId = SPUtils.getInstance().getString("compId");
//        initPtr();
//        ptr.setAutoLoadMoreEnable(true);
    }

    @Override
    protected void initData() {
        rvList.setSwipeItemClickListener(mItemClickListener);
        rvList.setSwipeMenuCreator(mSwipeMenuCreator);
        rvList.setSwipeMenuItemClickListener(mMenuItemClickListener);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new RecycleViewDivider( LinearLayoutManager.VERTICAL, 20, getResources().getColor(R.color.divide_gray_color)));

        rvList.setHasFixedSize(true);
        rvList.setNestedScrollingEnabled(false);

        listAdapter = new CampaignListAdatper(CampaignListActivity.this, campaignList);
        rvList.setAdapter(listAdapter);

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
        presenter.doQueryCampaignList(CampaignListActivity.this, userId, compId, pageNumber, pageSize,campaignType,true);
//        mAdapter = new RecyclerAdapterWithHF(listAdapter);
//        mAdapter = new SwipeAdapterWrapper(this,listAdapter);
//        rvList.setAdapter(mAdapter);

//        ptr.setVisibility(View.GONE);
//        llNoData.setVisibility(View.GONE);
    }

    @OnClick({R.id.iv_back,R.id.tv_right})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
               /* ActivityUtils.finishActivity(CampaignListActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);*/
                removeCampaignData();

                ActivityUtils.startActivity(ModuleMoreListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                ActivityUtils.finishActivity(CampaignListActivity.class);
                break;

            case R.id.tv_right:
                setNewDialog();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
      /*  ActivityUtils.finishActivity(CampaignListActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);*/

      ActivityUtils.startActivity(ModuleMoreListActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
        ActivityUtils.finishActivity(CampaignListActivity.class);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber = 1;
        presenter.doQueryCampaignList(CampaignListActivity.this, userId, compId, pageNumber, pageSize,campaignType,true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.doQueryCampaignList(CampaignListActivity.this, userId, compId, pageNumber, pageSize,campaignType,false);
    }




    /**
     * RecyclerView的Item点击监听。
     */
    protected SwipeItemClickListener mItemClickListener = new SwipeItemClickListener() {
        @Override
        public void onItemClick(View itemView, int position) {
//            Toast.makeText(getContext(), "第" + position + "个", Toast.LENGTH_SHORT).show();


            if(campaignList!=null && campaignList.size()>0){
                Campaign campaign = campaignList.get(position);

                Intent intent = new Intent();

                intent.putExtra("campaignType",campaign.getCampaignType());

                if("zf".equals(campaign.getCampaignType())){
                    removeCampaignData();
                    intent.setClass(CampaignListActivity.this,CampaignNewActivity.class);
                    intent.putExtra("title","转发活动");
                }else if("hy".equals(campaign.getCampaignType())){
                    removeLaohuCampaignData();
                    intent.setClass(CampaignListActivity.this,MemberInfoPerfectNewActivity.class);
                    intent.putExtra("title","会员信息完善");
                }else if("lh".equals(campaign.getCampaignType())){
                    removeLaohuCampaignData();
                    intent.setClass(CampaignListActivity.this,LaohuPinTuanNewActivity.class);
                    intent.putExtra("title","老虎机活动");
                }else if("ge".equals(campaign.getCampaignType())){
                    removeLaohuCampaignData();
                    intent.setClass(CampaignListActivity.this,LaohuPinTuanNewActivity.class);
                    intent.putExtra("title","砸金蛋活动");
                }else if("jz".equals(campaign.getCampaignType())){
                    removeJzCampaignData();
                    intent.setClass(CampaignListActivity.this,CampaignLikeNewActivity.class);
                    intent.putExtra("title","集赞活动");
                }
                intent.putExtra("isEditCampaign","YES");
                intent.putExtra("isCopyCreate","YES");
                intent.putExtra("compaignStatus",campaign.getCompaignStatus());

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
//                            ToastUtils.showShort(adapterPosition+"右侧菜单");
                            if(campaignId !=null){
                                setDialogDelete("",String.valueOf(campaignId),adapterPosition);
                            }
                        }
                        menuBridge.setText("删除");
                    }else if("N".equals(status)){
//                        campaignStatus = "未开始";
                        campaignStatus = status;

                        operateFlag = "1";
                        setDialogDelete(operateFlag,String.valueOf(campaignId),adapterPosition);

                    }else if("P".equals(status)){
//                        campaignStatus = "进行中";
                        campaignStatus = status;
                        operateFlag = "2";
                        setDialogDelete(operateFlag,String.valueOf(campaignId),adapterPosition);

                    }else if("E".equals(status)){
                        campaignStatus = "已结束";
//                        setDialog();
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
        SwipeMenuItem closeItem= new SwipeMenuItem(CampaignListActivity.this)
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
        SwipeMenuItem closeItem= new SwipeMenuItem(CampaignListActivity.this)
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
        SwipeMenuItem closeItem= new SwipeMenuItem(CampaignListActivity.this)
                .setBackground(R.drawable.shape_selector_red)
                .setWidth(width)
                .setHeight(height)
                .setTextSize(16)
                .setTextColor(getResources().getColor(R.color.white));
        closeItem.setText("提前结束");
        swipeRightMenu.addMenuItem(closeItem);
    }



    private void setDialogDelete(final String operateFlag,final String campaignId,final int position){
        String tip = "";

        if("S".equals(campaignStatus)){
            tip = "您确定要解除该活动吗？删除后将"+"<font size = '16'color = \'#0486FE\' weight = 'bolder'>无法恢复！</font>";
        }else if("N".equals(campaignStatus)){
            tip = "您确定要作废该活动吗？";
        }else if("P".equals(campaignStatus)){
            tip = "您确定要提前结束该活动吗？结束后将"+"<font size = '16'color = \'#0486FE\' weight = 'bolder'> 无法参与该活动！</font>";
        }
        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView message = (TextView)rootView.findViewById(R.id.message);
        message.setText(Html.fromHtml(tip));
        TextView tvTitle = (TextView)rootView.findViewById(R.id.title);
        tvTitle.setText("提示");
        rootView.findViewById(R.id.confirmBtn).setBackground(getResources().getDrawable(R.drawable.dialog_confirm_cancel_util_right_btn_bg));
        rootView.findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               /* if("S".equals(status)){
                    dialog.dismiss();
                    presenter.doCampaignDelete(CampaignListActivity.this,campaignId,position);
                }*/

                if("S".equals(campaignStatus)){
                    presenter.doCampaignDelete(CampaignListActivity.this,campaignId,position);
                }else if("N".equals(campaignStatus)){
                    presenter.doCampaignUpdate(CampaignListActivity.this,String.valueOf(campaignId),operateFlag);
                }else if("P".equals(campaignStatus)){
                    presenter.doCampaignUpdate(CampaignListActivity.this,String.valueOf(campaignId),operateFlag);
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

        LinearLayout ll_cancel = (LinearLayout) rootView.findViewById(R.id.ll_cancel);
        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



        RecyclerView rvGrid = (RecyclerView)rootView.findViewById(R.id.rv_grid);
         List<CountAnalysis> list = new ArrayList<>();
        CountAnalysis countAnalysis = new CountAnalysis();

        countAnalysis.setDrawableName(R.drawable.ic_campaign_forward);
        countAnalysis.setMemTypeName("转发活动");
        countAnalysis.setMemTypeNameEn("zf");
        countAnalysis.setTitle("转发活动");
        list.add(countAnalysis);

        countAnalysis = new CountAnalysis();
        countAnalysis.setDrawableName(R.drawable.ic_campaign_hy_type);
        countAnalysis.setMemTypeName("hy");
        countAnalysis.setMemTypeNameEn("hy");
        countAnalysis.setTitle("会员信息完善");
        list.add(countAnalysis);

        countAnalysis = new CountAnalysis();
        countAnalysis.setDrawableName(R.drawable.ic_lh_campaign);
        countAnalysis.setMemTypeName("老虎机活动");
        countAnalysis.setMemTypeNameEn("lh");
        countAnalysis.setTitle("老虎机活动");
        list.add(countAnalysis);

        countAnalysis = new CountAnalysis();
        countAnalysis.setDrawableName(R.drawable.ic_ge_campaign);
        countAnalysis.setMemTypeName("砸金蛋");
        countAnalysis.setMemTypeNameEn("ge");
        countAnalysis.setTitle("砸金蛋活动");
        list.add(countAnalysis);
        countAnalysis = new CountAnalysis();
        countAnalysis.setDrawableName(R.drawable.ic_campaign_jz_type);
        countAnalysis.setMemTypeName("jz");
        countAnalysis.setMemTypeNameEn("jz");
        countAnalysis.setTitle("集赞活动");
        list.add(countAnalysis);
        countAnalysis = new CountAnalysis();
        countAnalysis.setDrawableName(R.drawable.ic_campaign_staytuned);
        countAnalysis.setMemTypeName("敬请期待");
        countAnalysis.setMemTypeNameEn("jqqd");
        countAnalysis.setTitle("敬请期待");
        list.add(countAnalysis);


        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        rvGrid.setLayoutManager(layoutManager);
        rvGrid.setAdapter(new SelectCampaignTypeAdapter(CampaignListActivity.this, list, new CommonRecyclerViewHolder.OnItemCommonClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = new Intent();
                CountAnalysis countAnalysis1 = list.get(position);

                dialog.dismiss();
                if("zf".equals(countAnalysis1.getMemTypeNameEn())){
                    intent.setClass(CampaignListActivity.this,CampaignNewActivity.class);
                    intent.putExtra("compaignStatus","NEW");
                    intent.putExtra("campaignType",countAnalysis1.getMemTypeNameEn());
                    intent.putExtra("title",countAnalysis1.getTitle());
                    intent.putExtra("isEditCampaign","NO");
                    intent.putExtra("isCopyCreate","NO");
                    removeCampaignData();
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("hy".equals(countAnalysis1.getMemTypeNameEn())){
                    removeHyCampaignData();
                    intent.setClass(CampaignListActivity.this,MemberInfoPerfectNewActivity.class);
                    intent.putExtra("compaignStatus","NEW");
                    intent.putExtra("campaignType",countAnalysis1.getMemTypeNameEn());
                    intent.putExtra("title",countAnalysis1.getTitle());
                    intent.putExtra("isEditCampaign","NO");
                    intent.putExtra("isCopyCreate","NO");
                    removeLaohuCampaignData();
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("ge".equals(countAnalysis1.getMemTypeNameEn())){
                    intent.setClass(CampaignListActivity.this,LaoHuTempletActivity.class);
                    intent.putExtra("compaignStatus","NEW");
                    intent.putExtra("campaignType",countAnalysis1.getMemTypeNameEn());
                    intent.putExtra("title",countAnalysis1.getTitle());
                    intent.putExtra("isEditCampaign","NO");
                    intent.putExtra("isCopyCreate","NO");
                    removeLaohuCampaignData();
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("lh".equals(countAnalysis1.getMemTypeNameEn())){
                    intent.setClass(CampaignListActivity.this,LaoHuTempletActivity.class);
                    intent.putExtra("compaignStatus","NEW");
                    intent.putExtra("campaignType",countAnalysis1.getMemTypeNameEn());
                    intent.putExtra("title",countAnalysis1.getTitle());
                    intent.putExtra("isEditCampaign","NO");
                    intent.putExtra("isCopyCreate","NO");
                    removeLaohuCampaignData();
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right,R.anim.slide_out_left);

                }else if("jz".equals(countAnalysis1.getMemTypeNameEn())){
                    intent.setClass(CampaignListActivity.this,CampaignLikeNewActivity.class);
                    intent.putExtra("compaignStatus","NEW");
                    intent.putExtra("campaignType",countAnalysis1.getMemTypeNameEn());
                    intent.putExtra("title",countAnalysis1.getTitle());
                    intent.putExtra("isEditCampaign","NO");
                    intent.putExtra("isCopyCreate","NO");
                    removeJzCampaignData();
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right,R.anim.slide_out_left);
                }
            }
        }));

    }


    private void removeCampaignData(){
        CampaignNewActivity.storeIdCampaignType = "A";
        CampaignNewActivity.storeStr = "";
        CampaignNewActivity.storeId = "";
        SPUtils.getInstance().remove("storeIdArrayCampaign");
        SPUtils.getInstance().remove("storeNameArrayCampaign");
        SPUtils.getInstance().remove("brandLogoCampaign");
        SPUtils.getInstance().remove("brandPostionCampaign");
        SPUtils.getInstance().remove("storeIdCampaignType");
        CampaignNewNextTwoActivity.forwardCouponList = new ArrayList<>();
        CampaignNewNextThreeActivity.receiveCouponList = new ArrayList<>();
        CampaignNewNextTwoActivity.campaignCouponList = new ArrayList<>();
        CampaignNewNextThreeActivity.campaignCouponList = new ArrayList<>();

        CampaignNewActivity.campaign = null;
    }
    private void removeHyCampaignData(){


        MemberInfoPerfectNewActivity.campaign = new Campaign();
        MemberInfoPerfectNewActivity.storeIdCampaignType = "A";
        MemberInfoPerfectNewActivity.storeStr = "";
        MemberInfoPerfectNewActivity.storeName = "";
        MemberInfoPerfectNewActivity.storeId = "";
        MemberInfoPerfectNextTwoActivity.campaignCouponList = new ArrayList<>();
        MemberInfoPerfectNextTwoActivity.forwardCouponList = new ArrayList<>();
        MemberInfoPerfectNextTwoActivity.couponList = new ArrayList<>();

    }
    private void removeLaohuCampaignData(){

        SPUtils.getInstance().remove("brandLogoLHPTOne");
        SPUtils.getInstance().remove("storeIdArrayLHPTOne");
        SPUtils.getInstance().remove("storeNameArrayLHPTOne");
        SPUtils.getInstance().remove("brandPostionLHPTOne");

        SPUtils.getInstance().remove("brandLogoLHP");
        SPUtils.getInstance().remove("brandPostionLHPT");

        LaohuPinTuanNextTwoActivity.campaignGamesetrewardList = new ArrayList<>();
        LaohuPinTuanNextTwoActivity.campaignNonrewardPicList = new ArrayList<>();

        LaohuPinTuanNewActivity.campaign = new Campaign();
        LaohuPinTuanNextThreeActivity.campaignGame = new CampaignGame();

        LaohuPinTuanNextTwoActivity.campaignCouponList1 = new ArrayList<>();
        LaohuPinTuanNextTwoActivity.campaignCouponList2 = new ArrayList<>();
        LaohuPinTuanNextTwoActivity.campaignCouponList3 = new ArrayList<>();
        LaohuPinTuanNextTwoActivity.campaignCouponList4 = new ArrayList<>();
        LaohuPinTuanNextTwoActivity.campaignCouponList5 = new ArrayList<>();
        LaohuPinTuanNextTwoActivity.campaignCouponList6= new ArrayList<>();
        LaohuPinTuanNextTwoActivity.campaignCouponList7 = new ArrayList<>();
        LaohuPinTuanNextTwoActivity.prizePath1 = null;
        LaohuPinTuanNextTwoActivity.prizePath2 = null;
        LaohuPinTuanNextTwoActivity.prizePath3 = null;
        LaohuPinTuanNextTwoActivity.prizePath4 = null;
        LaohuPinTuanNextTwoActivity.prizePath5 = null;
        LaohuPinTuanNextTwoActivity.prizePath6 = null;
        LaohuPinTuanNextTwoActivity.prizePath7 = null;
        LaohuPinTuanNextTwoActivity.prizePath8 = null;

        LaohuPinTuanNextTwoActivity.selectList = new ArrayList<>();
        LaohuPinTuanNewActivity.storeIdCampaignType = "A";
        LaohuPinTuanNewActivity.storeStr = "";

    }


    private void removeJzCampaignData(){
        SPUtils.getInstance().remove("storeIdCampaignType");
        SPUtils.getInstance().remove("storeIdArrayCampaign");
        SPUtils.getInstance().remove("storeNameArrayCampaign");
        CampaignLikeTwoActivity.campaignGamesetrewardList = new ArrayList<>();

        CampaignLikeNewActivity.campaign = null;
        CampaignLikeThreeActivity.campaignCollect = new CampaignCollect();
        CampaignLikeTwoActivity.campaignCouponList1 = new ArrayList<>();
        CampaignLikeTwoActivity.campaignCouponList2 = new ArrayList<>();
        CampaignLikeTwoActivity.campaignCouponList3 = new ArrayList<>();
        CampaignLikeTwoActivity.campaignCouponList4 = new ArrayList<>();
        CampaignLikeTwoActivity.couponList1 = new ArrayList<>();
        CampaignLikeTwoActivity.couponList2 = new ArrayList<>();
        CampaignLikeTwoActivity.couponList3 = new ArrayList<>();
        CampaignLikeTwoActivity.couponList4 = new ArrayList<>();
    }

}
