package com.zwx.scan.app.feature.member;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
import com.zwx.scan.app.data.bean.CompMemberType;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.modulemore.ModuleMoreListActivity;
import com.zwx.scan.app.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/04/04
 * desc   : 会员卡管理
 * version: 1.0
 **/
public class MemberCardManageActivity extends BaseActivity<MemberManageContract.Presenter> implements MemberManageContract.View,View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ScrollView> {
    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.rv_list)
    public SwipeMenuRecyclerView rvList;
    @BindView(R.id.ptr)
    public PullToRefreshScrollView ptr;
    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;

    protected MemberCardManageListAdapter listAdapter = null;

//    protected RecyclerAdapterWithHF mAdapter;

    private String userId;
    private String compId;
    protected int pageNumber = 1;
    protected int pageSize = 10;
    //"类型：1:是否可以编辑、2：是否可以删除、3：是否可以停用"
    private String operateFlag = "";
    //根根据优惠券ID判断优惠券操作状态
    private String isStatus = "";
    protected int deletePostion = 0 ;
    //会员卡id
    protected   String compMemtypeId = "";
    protected   String memberStatus = "";
    protected  String compMemberGroup = "";
    protected List<CompMemberType> compMemberTypeList = new ArrayList<>();

    protected  AlertDialog.Builder builder  = null;
    protected AlertDialog dialog = null;
    //是否从新建入口还是列表跳转入口编辑
    protected  String isEdit = "NO";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_card_manage;
    }

    @Override
    protected void initView() {
        DaggerMemberManageComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .memberManageModule(new MemberManageModule(this))
                .build()
                .inject(this);

        tvTitle.setText(getResources().getString(R.string.card_manage));

        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.new_label));
    }

    @Override
    protected void initData() {



        userId = SPUtils.getInstance().getString("userId");

        rvList.setSwipeItemClickListener(mItemClickListener);
        rvList.setSwipeMenuCreator(mSwipeMenuCreator);
        rvList.setSwipeMenuItemClickListener(mMenuItemClickListener);
        listAdapter = new MemberCardManageListAdapter(MemberCardManageActivity.this, compMemberTypeList);
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

        presenter.memTypeList(MemberCardManageActivity.this,userId,pageNumber,pageSize,true);
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
                isEdit = "NO";
                setIntentActivity();

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.startActivity(ModuleMoreListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        compMemberTypeList.clear();
        pageNumber = 1;
        presenter.memTypeList(MemberCardManageActivity.this,userId,pageNumber,pageSize,true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.memTypeList(MemberCardManageActivity.this,userId,pageNumber,pageSize,false);
    }


    /**
     * RecyclerView的Item点击监听。
     */
    protected SwipeItemClickListener mItemClickListener = new SwipeItemClickListener() {
        @Override
        public void onItemClick(View itemView, int position) {
            if(compMemberTypeList!=null && compMemberTypeList.size()>0) {
                CompMemberType compMemberType = compMemberTypeList.get(position);
                 compMemtypeId = String.valueOf(compMemberType.getMemtypeIdArray());
                isEdit  = "YES";
                setIntentActivity();
            }



        }
    };

    protected void setIntentActivity(){
        MemberCardNewActivity.compMemberType = new CompMemberType();
        Intent intent = new Intent(MemberCardManageActivity.this,MemberCardNewActivity.class);
        if("NO".equals(isEdit)){
            intent.putExtra("isEditCampaign","NO");
            intent.putExtra("isCopyCreate","NO");
            intent.putExtra("memberCardStatusNew","NEW");  //新建
        }else {
            intent.putExtra("isEditCampaign","YES");
            intent.putExtra("compMemtypeId",compMemtypeId);
            intent.putExtra("isCopyCreate","YES");
            intent.putExtra("memberCardStatusNew","EDIT");//编辑
        }

        MemberCardNewActivity.wayValue = "R";
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

            CompMemberType compMemberType = compMemberTypeList.get(adapterPosition);

            if(compMemberTypeList!= null &&compMemberTypeList.size()>0){
                String status = "";
                String isDefault = "";
                if(compMemberType!=null ){
                    isDefault = compMemberType.getIsDefault();
                    status = compMemberType.getMemtypeStatus()!= null ?String.valueOf(compMemberType.getMemtypeStatus()):"";
                    if(compMemberType.getMemtypeIdArray() != null){
                        compMemtypeId = String.valueOf(compMemberType.getMemtypeIdArray());
                    }
                    compMemberGroup = compMemberType.getCompMemberGroup();
                    if("0".equals(status)){   //未使用

                        if(menuPosition == 0){
                            memberStatus = "启用";
                            operateFlag = "1";

                            if("0".equals(isDefault)){ //默认卡
                            }else {
                                presenter.memberTypeOperation(MemberCardManageActivity.this,userId,operateFlag,compMemtypeId,compMemberGroup);
                            }

                        }else if(menuPosition == 1){
                            deletePostion = adapterPosition;
                            memberStatus = "删除";
                            operateFlag = "3";

                            if("0".equals(isDefault)){
                                operateFlag ="5";
                            }
                            setMemberStatusDialog();

                        }
                        menuBridge.setText(memberStatus);


                    }else if("1".equals(status)){
                        memberStatus = "停用";
                        operateFlag = "2";
                        menuBridge.setText(memberStatus);
                        if("0".equals(isDefault)){
                            operateFlag ="4";
                        }
                        setMemberStatusDialog();
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

            if(viewType == 0){  //未使用
                createMemuWeiShiYong(swipeRightMenu);
            }else if(viewType == 1){   //启用
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
        SwipeMenuItem leftItem= new SwipeMenuItem(MemberCardManageActivity.this)
                .setBackground(R.drawable.shape_selector_blue)
                .setWidth(width)
                .setHeight(height)
                .setTextSize(16)
                .setTextColor(getResources().getColor(R.color.white));
        leftItem.setText("启用");
        swipeRightMenu.addMenuItem(leftItem);
        SwipeMenuItem rightItem= new SwipeMenuItem(MemberCardManageActivity.this)
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
        SwipeMenuItem closeItem= new SwipeMenuItem(MemberCardManageActivity.this)
                .setBackground(R.drawable.shape_selector_red)
                .setWidth(width)
                .setHeight(height)
                .setTextSize(16)
                .setTextColor(getResources().getColor(R.color.white));
        closeItem.setText("停用");
        swipeRightMenu.addMenuItem(closeItem);
    }




    protected void setMemberStatusDialog(){

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView message = (TextView)rootView.findViewById(R.id.message);

        TextView tvTitle = (TextView)rootView.findViewById(R.id.title);
        Button confirmBtn =  rootView.findViewById(R.id.confirmBtn);
        Button cancelBtn =  rootView.findViewById(R.id.cancelBtn);
        String tip = "";
        String title = "";
        //编辑为1
        if("3".equals(operateFlag)){
            title = "删除";
            tip = "请问是否确认删除该会员卡";//"<font size = '16'color = \'#0486FE\' weight = 'bolder'>无法恢复！</font>";
            cancelBtn.setVisibility(View.VISIBLE);
            confirmBtn.setText("是");
            cancelBtn.setText("否");
        }else if("2".equals(operateFlag)){//停用
            title = "停用";
            tip = "请问是否确认停用该会员卡，停用后新会员将无法获得该会员卡";
            cancelBtn.setVisibility(View.VISIBLE);
            confirmBtn.setText("是");
            cancelBtn.setText("否");
        }else if("4".equals(operateFlag)){ //停用
            title = "停用";
            tip = "无法停用";
            cancelBtn.setVisibility(View.GONE);
        }else if("5".equals(operateFlag)){ //删除
            title = "删除";
            tip = "无法删除";
            cancelBtn.setVisibility(View.GONE);
        }
        message.setText(Html.fromHtml(tip));
        tvTitle.setText(title);
        confirmBtn.setBackground(getResources().getDrawable(R.drawable.dialog_confirm_submit_util_right_btn_bg_white));
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if("4".equals(operateFlag)||"5".equals(operateFlag)){

                }else {
                    presenter.memberTypeOperation(MemberCardManageActivity.this,userId,operateFlag,compMemtypeId,compMemberGroup);
                }

                dialog.dismiss();

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
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

    }

    protected void setResultMessageDialog(){
        String tip = "";
        if("1".equals(operateFlag)){  //启用
//            tip = "该优惠券"+"<font size = '16'color = \'#0486FE\' weight = 'bolder'>无法恢复！</font>";
            tip = "该系列会员卡已经是启用状态";
        }else if("2".equals(operateFlag)){ //停用
            tip = "有会员持有该会员卡，不能删除";
        }else if("3".equals(operateFlag)){  //删除
//            tip = "该优惠券"+"<font size = '16'color = \'#0486FE\' weight = 'bolder'>无法被删除</font>"+"，有会员持有该优惠券或者有活动正在使用该优惠券。";
            tip = "有会员持有该会员卡，不能删除";
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
        rootView.findViewById(R.id.cancelBtn).setVisibility(View.GONE);
    }

}
