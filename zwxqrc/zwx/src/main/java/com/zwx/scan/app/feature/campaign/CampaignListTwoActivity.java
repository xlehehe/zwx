package com.zwx.scan.app.feature.campaign;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.zwx.library.swipe.SwipeItemClickListener;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.countanalysis.CountAnalysis;
import com.zwx.scan.app.feature.modulemore.ModuleMoreListActivity;
import com.zwx.scan.app.widget.swipelistview.SwipeMenu;
import com.zwx.scan.app.widget.swipelistview.SwipeMenuCreator;
import com.zwx.scan.app.widget.swipelistview.SwipeMenuItem;
import com.zwx.scan.app.widget.swipelistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/01/07
 * desc   :
 * version: 1.0
 **/
public class CampaignListTwoActivity extends BaseActivity<CampaignsContract.Presenter> implements CampaignsContract.View ,View.OnClickListener{
    @BindView(R.id.iv_back)
    protected ImageView ivBack;
    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.list_view)
    public SwipeMenuListView rvList;
    @BindView(R.id.ptr)
    public PtrClassicFrameLayout ptr;

//    @BindView(R.id.ll_no_data)
//    protected LinearLayout llNoData;

    protected List<Campaign> campaignList = new ArrayList<>();
    protected CampaignListViewAdapter mAdapter = null;

//    protected SwipeAdapterWrapper mAdapter;

    private String userId;
    private String compId;
    private int pageNumber = 1;
    private int pageSize = 10;
    private String campaignType = "zf";

    private  AlertDialog.Builder builder  = null;
    private AlertDialog dialog = null;
    private String campaignStatus;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_campaign_list_two;
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
        initPtr();
    }

    @Override
    protected void initData() {
//        presenter.doQueryCampaignList(CampaignListTwoActivity.this, userId, compId, pageNumber, pageSize,campaignType,true);

        mAdapter = new CampaignListViewAdapter(CampaignListTwoActivity.this, campaignList);
        rvList.setAdapter(mAdapter);
//        ptr.setVisibility(View.VISIBLE);
//        llNoData.setVisibility(View.GONE);
        setSwipeListener();

    }

    private void setSwipeListener(){
        rvList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Campaign campaign = campaignList.get(index);
                if(campaignList!= null &&campaignList.size()>0){
                    String status =campaign.getCompaignStatus();
                    Long campaignId = campaign.getCampaignId();
                    String operateFlag = "";
                    if(campaign!=null ){
                        if("S".equals(status)){
//                            campaignStatus = "未发布";
                            campaignStatus = status;
                            if(campaignId !=null){
                                setDialog("", String.valueOf(campaignId),index);
                            }
                        }else if("N".equals(status)){
//                            campaignStatus = "未开始";
                            campaignStatus = status;
                            operateFlag = "1";
                            setDialog(operateFlag,String.valueOf(campaignId),index);

                           /* if(campaignId !=null){
                                presenter.doCampaignUpdate(CampaignListTwoActivity.this,String.valueOf(campaignId),operateFlag);
                            }*/
                        }else if("P".equals(status)){
//                            campaignStatus = "进行中";
                            campaignStatus = status;

                            operateFlag = "2";
                            setDialog(operateFlag,String.valueOf(campaignId),index);
                           /* if(campaignId !=null){
                                presenter.doCampaignUpdate(CampaignListTwoActivity.this,String.valueOf(campaignId),operateFlag);
                            }*/
                        }else if("E".equals(status)){
//                            campaignStatus = "已结束";
//                        setDialog();
                        }else if("C".equals(status)){
//                            campaignStatus = "已作废";
                            operateFlag = "1";
                        }else if("I".equals(status)){
//                            campaignStatus = "提前结束";
                        }

                    }
                }
                return false;
            }
        });

        rvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(campaignList!=null && campaignList.size()>0){
                    Campaign campaign = campaignList.get(position);
                    removeCampaignData();
                    Intent intent = new Intent(CampaignListTwoActivity.this,CampaignNewActivity.class);
                    intent.putExtra("compaignStatus",campaign.getCompaignStatus());
                    intent.putExtra("campaignType","zf");
                    intent.putExtra("title","转发活动");
                    intent.putExtra("isEditCampaign","YES");
                    intent.putExtra("isCopyCreate","YES");
                    intent.putExtra("campaignId",String.valueOf(campaign.getCampaignId()));
                    ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                }

            }
        });
    }

    @OnClick({R.id.iv_back,R.id.tv_right})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                removeCampaignData();
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
        removeCampaignData();
        ActivityUtils.startActivity(ModuleMoreListActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    private void initPtr() {
        ptr.disableWhenHorizontalMove(true);
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
                presenter.queryCampaignList(CampaignListTwoActivity.this, userId, compId, pageNumber, pageSize,campaignType,true);
            }
        });


        ptr.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNumber++;
                presenter.queryCampaignList(CampaignListTwoActivity.this, userId, compId, pageNumber, pageSize,campaignType,false);

            }
        });
    }



    /**
     * RecyclerView的Item点击监听。
     */
    protected SwipeItemClickListener mItemClickListener = new SwipeItemClickListener() {
        @Override
        public void onItemClick(View itemView, int position) {

            if(campaignList!=null && campaignList.size()>0){
                Campaign campaign = campaignList.get(position);
                removeCampaignData();
                Intent intent = new Intent(CampaignListTwoActivity.this,CampaignNewActivity.class);
                intent.putExtra("compaignStatus",campaign.getCompaignStatus());
                intent.putExtra("campaignType","zf");
                intent.putExtra("title","转发活动");
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
    /*protected SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。

            Campaign campaign = campaignList.get(adapterPosition);
            if(campaignList!= null &&campaignList.size()>0){
                String status =campaign.getCompaignStatus();
                Long campaignId = campaign.getCampaignId();
                String operateFlag = "";
                if(campaign!=null ){
                    if("S".equals(status)){
                        campaignStatus = "未发布";
                        if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
//                            ToastUtils.showShort(adapterPosition+"右侧菜单");
                            if(campaignId !=null){
                                setDialogDelete(status,String.valueOf(campaignId),adapterPosition);
                            }
                        }
                    }else if("N".equals(status)){
                        campaignStatus = "未开始";
                        setDialogDelete(status,String.valueOf(campaignId),adapterPosition);
                        operateFlag = "1";
                        if(campaignId !=null){
                            presenter.doCampaignUpdate(CampaignListTwoActivity.this,String.valueOf(campaignId),operateFlag);
                        }
                    }else if("P".equals(status)){
                        campaignStatus = "进行中";
                        operateFlag = "2";
                        if(campaignId !=null){
                            presenter.doCampaignUpdate(CampaignListTwoActivity.this,String.valueOf(campaignId),operateFlag);
                        }
                    }else if("E".equals(status)){
                        campaignStatus = "已结束";
//                        setDialog();
                    }else if("C".equals(status)){
                        campaignStatus = "已作废";
                        operateFlag = "1";
                    }else if("I".equals(status)){
                        campaignStatus = "提前结束";
                    }

                    menuBridge.setText(campaignStatus);

                }
            }

        }
    };
*/
    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    protected SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void create(SwipeMenu menu) {
            int viewType = menu.getViewType();
            int width = getResources().getDimensionPixelSize(R.dimen.margin_100);
            String statusType = "";
            if(viewType == 0){
                statusType = "S";
                createMemuDelete(menu);
            }else if(viewType == 1){
                statusType = "N";
                createMemuZuofei(menu);
            }else if(viewType == 2){
                statusType = "P";
                createMemuEnd(menu);
            }
        }
    };





    private void createMemuDelete(SwipeMenu menu){
        // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
        // 2. 指定具体的高，比如80;
        // 3. WRAP_CONTENT，自身高度，不推荐;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        int width = getResources().getDimensionPixelSize(R.dimen.margin_100);
        SwipeMenuItem closeItem= new SwipeMenuItem(CampaignListTwoActivity.this);
        closeItem.setBackground(R.drawable.shape_selector_red);
        closeItem.setWidth(width);
//        closeItem.setHeight(height);
        closeItem.setTitleSize(16);
        closeItem.setTitleColor(getResources().getColor(R.color.white));
        closeItem.setTitle("删除");
        menu.addMenuItem(closeItem);
    }
    private void createMemuZuofei(SwipeMenu menu){
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        int width = getResources().getDimensionPixelSize(R.dimen.margin_100);
        SwipeMenuItem closeItem= new SwipeMenuItem(CampaignListTwoActivity.this);
        closeItem.setBackground(R.drawable.shape_selector_red);
        closeItem.setWidth(width);
//        closeItem.setHeight(height);
        closeItem.setTitleSize(16);
        closeItem.setTitleColor(getResources().getColor(R.color.white));
        closeItem.setTitle("作废");
        menu.addMenuItem(closeItem);
    }

    private void createMemuEnd(SwipeMenu menu){
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        int width = getResources().getDimensionPixelSize(R.dimen.margin_100);
        SwipeMenuItem closeItem= new SwipeMenuItem(CampaignListTwoActivity.this);
        closeItem.setBackground(R.drawable.shape_selector_red);
        closeItem.setBackground(R.drawable.shape_selector_red);
        closeItem.setWidth(width);
//        closeItem.setHeight(height);
        closeItem.setTitleSize(16);
        closeItem.setTitleColor(getResources().getColor(R.color.white));
        closeItem.setTitle("提前结束");
        menu.addMenuItem(closeItem);
    }



    private void setDialog(String operateFlag,final String campaignId,final int position){
        String tip = "";

        if("S".equals(campaignStatus)){
            tip = "您确定要解除该活动吗？删除后将"+"<font size = '16'color = \'#0486FE\' weight = 'bolder'>无法恢复！</font>";
        }else if("N".equals(campaignStatus)){
            tip = "您确定要作废该活动吗？";
        }else if("P".equals(campaignStatus)){
            tip = "您确定要提前结束该活动吗？结束后将"+"<font size = '16'color = \'#0486FE\' weight = 'bolder'> 无法参与该活动！</font>";
        }


        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
//        final Dialog dialog = DialogUIUtils.showCustomAlert(this, rootView, Gravity.CENTER, true, false).show();
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


                if("S".equals(campaignStatus)){
                    presenter.doCampaignDelete(CampaignListTwoActivity.this,campaignId,position);
                }else if("N".equals(campaignStatus)){
                    presenter.doCampaignUpdate(CampaignListTwoActivity.this,String.valueOf(campaignId),operateFlag);
                }else if("P".equals(campaignStatus)){
                    presenter.doCampaignUpdate(CampaignListTwoActivity.this,String.valueOf(campaignId),operateFlag);
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
        ivCancel.setOnClickListener(new View.OnClickListener() {
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
        countAnalysis.setMemTypeNameEn("Forward");
        countAnalysis.setTitle("转发活动");
        list.add(countAnalysis);

        countAnalysis = new CountAnalysis();

        countAnalysis.setDrawableName(R.drawable.ic_campaign_staytuned);
        countAnalysis.setMemTypeName("直接发放活动");
        countAnalysis.setMemTypeNameEn("Direct issuance");
        countAnalysis.setTitle("直接发放活动");
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
        rvGrid.setAdapter(new SelectCampaignTypeAdapter(CampaignListTwoActivity.this, list, new CommonRecyclerViewHolder.OnItemCommonClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = new Intent();
                CountAnalysis countAnalysis1 = list.get(position);

                dialog.dismiss();
                if("转发活动".equals(countAnalysis1.getMemTypeName())){
                    intent.setClass(CampaignListTwoActivity.this,CampaignNewActivity.class);
                    intent.putExtra("compaignStatus","NEW");
                    intent.putExtra("campaignType","zf");
                    intent.putExtra("title","转发活动");
                    intent.putExtra("isEditCampaign","NO");
                    intent.putExtra("isCopyCreate","NO");
                    removeCampaignData();
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("会员管理".equals(countAnalysis1.getMemTypeName())){

                }else if("拉新报表".equals(countAnalysis1.getMemTypeName())){
                }
            }
        }));

    }


    private void removeCampaignData(){
        SPUtils.getInstance().remove("receiveCouponList");
        SPUtils.getInstance().remove("forwardCouponList");
        SPUtils.getInstance().remove("campaign");

        SPUtils.getInstance().remove("brandLogoCouponTwo");
        SPUtils.getInstance().remove("storeIdArrayCouponTwo");
        SPUtils.getInstance().remove("storeNameArrayCouponTwo");

        SPUtils.getInstance().remove("brandLogoCouponTwo");

        SPUtils.getInstance().remove("brandPostionCouponTwo");

        SPUtils.getInstance().remove("storeIdCouponTypeTwo");
        SPUtils.getInstance().remove("brandLogoCouponThree");
        SPUtils.getInstance().remove("storeIdArrayCouponThree");
        SPUtils.getInstance().remove("storeNameArrayCouponThree");
        SPUtils.getInstance().remove("brandPostionCouponThree");
        SPUtils.getInstance().remove("storeIdCouponTypeThree");

        SPUtils.getInstance().remove("storeIdArrayCampaign");
        SPUtils.getInstance().remove("storeNameArrayCampaign");
        SPUtils.getInstance().remove("brandLogoCampaign");
        SPUtils.getInstance().remove("brandPostionCampaign");
        SPUtils.getInstance().remove("storeIdCampaignType");
        CampaignNewNextTwoActivity.forwardCouponList = new ArrayList<>();
        CampaignNewNextThreeActivity.receiveCouponList = new ArrayList<>();
        CampaignNewActivity.campaign = new Campaign();
    }

}
