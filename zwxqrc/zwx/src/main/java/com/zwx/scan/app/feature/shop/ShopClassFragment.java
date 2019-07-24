package com.zwx.scan.app.feature.shop;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.data.bean.ProductCategory;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.utils.EmojiInputFilter;
import com.zwx.scan.app.utils.MaxTextLengthFilter;
import com.zwx.scan.app.widget.RecycleViewDivider;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/04/18
 * desc   : 商品分类
 * version: 1.0
 **/
public class ShopClassFragment extends BaseFragment<ShopContract.Presenter> implements ShopContract.View ,View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ScrollView>  {

    @BindView(R.id.add_class)
    public TextView add_class;

    @BindView(R.id.rv_list)
    public SwipeMenuRecyclerView rvList;
    @BindView(R.id.ptr)
    public PullToRefreshScrollView ptr;
    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;


    private String userId;
    private String compId;
    private int pageNumber = 1;
    private int pageSize = 10;

    private String catId;
    protected ShopClassListAdapter listAdapter = null;

//    protected RecyclerAdapterWithHF mAdapter;
    protected  AlertDialog.Builder builder  = null;
    protected AlertDialog dialog = null;
    protected String catName;
    protected String salesType;

    protected boolean isAddCateFlag = true;

    protected List<ProductCategory> categoryList = new ArrayList<>();

    protected  int adapterPosition = 0;



    public ShopClassFragment() {
        // Required empty public constructor
    }
    public static ShopClassFragment getInstance(String param) {
        ShopClassFragment instance = new ShopClassFragment();
        return instance;
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_shop_class;
    }

    @Override
    protected void initInjector() {
        DaggerShopComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .shopModule(new ShopModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initView() {

        userId = SPUtils.getInstance().getString("userId");
        compId = SPUtils.getInstance().getString("compId");
//        initPtr();



        rvList.setSwipeItemClickListener(mItemClickListener);
        rvList.setSwipeMenuCreator(mSwipeMenuCreator);
        rvList.setSwipeMenuItemClickListener(mMenuItemClickListener);
        listAdapter = new ShopClassListAdapter(getActivity(), categoryList);
        rvList.setAdapter(listAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.addItemDecoration(new RecycleViewDivider( LinearLayoutManager.VERTICAL, 20, getResources().getColor(R.color.divide_gray_color)));
        rvList.setHasFixedSize(true);
        rvList.setNestedScrollingEnabled(false);
//        rvList.setItemViewCacheSize(10);


        ptr.setOnRefreshListener(this);
        ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ILoadingLayout endLabelsr = ptr.getLoadingLayoutProxy(false, true);

        endLabelsr.setPullLabel("上拉可以加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("加载中");// 刷新时
        endLabelsr.setReleaseLabel("松开加载更多");// 下来达到一定距离时，显示的提示


        ILoadingLayout startLabelse = ptr.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉可以刷新");// 刚下拉时，显示的提示
        startLabelse.setLastUpdatedLabel("正在刷新");// 刷新时
        startLabelse.setReleaseLabel("松开后刷新");

        ll_no_data.setVisibility(View.VISIBLE);
        rvList.setVisibility(View.GONE);
        tv_no_data.setText("暂无数据");
        presenter.queryProductCategory(getActivity(),userId,true);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.add_class})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_class:
                isAddCateFlag = true;

                showAddCate();
                break;
        }
    }

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
            }

        }
    };
    private void createMemuDelete(SwipeMenu swipeRightMenu){
        // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
        // 2. 指定具体的高，比如80;
        // 3. WRAP_CONTENT，自身高度，不推荐;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        int width = getResources().getDimensionPixelSize(R.dimen.margin_100);
        SwipeMenuItem closeItem= new SwipeMenuItem(getActivity())
                .setBackground(R.drawable.shape_selector_red)
                .setWidth(width)
                .setHeight(height)
                .setTextSize(16)
                .setTextColor(getResources().getColor(R.color.white));
        closeItem.setText("删除");
        swipeRightMenu.addMenuItem(closeItem);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        categoryList.clear();
        pageNumber = 1;
        presenter.queryProductCategory(getActivity(),userId,true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.queryProductCategory(getActivity(),userId,false);
    }
    /*
    private void initPtr() {
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
//                presenter.doQueryCampaignList(CampaignListActivity.this, userId, compId, pageNumber, pageSize,campaignType,true);
                ptr.refreshComplete();
                presenter.queryProductCategory(getActivity(),userId,true);
            }
        });


        ptr.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNumber++;
                ptr.refreshComplete();
                presenter.queryProductCategory(getActivity(),userId,false);

            }
        });
    }*/



    /**
     * RecyclerView的Item点击监听。
     */
    protected SwipeItemClickListener mItemClickListener = new SwipeItemClickListener() {
        @Override
        public void onItemClick(View itemView, int position) {

            if(categoryList!=null && categoryList.size()>0){
                ProductCategory category = categoryList.get(position);
                if(category != null){

                    catId = String.valueOf(category.getCatId());
                    catName  = category.getCatName();
                    isAddCateFlag = false;
                    showAddCate();
                }


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
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
             adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if(categoryList!= null && categoryList.size()>0){
                ProductCategory category = categoryList.get(adapterPosition);
                if(category.getCatId() != null){
                    catId = String.valueOf(category.getCatId());
//                    presenter.doCampaignDelete(CampaignListActivity.this,campaignId,position);
                    presenter.deleteProductCategory(getActivity(),catId);
                }

            }

            menuBridge.setText("删除");


        }
    };


    protected void showAddCate() {

        View customView = View.inflate(getActivity(), R.layout.layout_shop_add_category, null);

        AlertDialog.Builder builder  = new AlertDialog.Builder(getActivity());
        AlertDialog dialog = builder.create();
        dialog.setView(customView, 0, 0, 0, 0);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        EditText edt_name = (EditText)customView.findViewById(R.id.edt_name);
        edt_name.setSelection(edt_name.getText().length());
        TextView title = (TextView)customView.findViewById(R.id.title);
        edt_name.setFilters(new InputFilter[]{new EmojiInputFilter()});
        edt_name.setFilters(new InputFilter[]{new MaxTextLengthFilter(10)});
        if(isAddCateFlag){
            catName = "";
            edt_name.setText("");
            title.setText("新建分类");
        }else {
            edt_name.setText(catName);
            title.setText("编辑分类");
        }
        Button btn_use = (Button)customView.findViewById(R.id.confirmBtn);
        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String name = edt_name.getText().toString().trim();

                 if(name != null && !"".equals(name)){
                     if(isAddCateFlag){
                         presenter.addProductCategory(getActivity(),name,salesType,userId);
                     }else {
                         presenter.updateProductCategory(getActivity(),catId,name);
                     }

                 }else {
                     ToastUtils.showShort("不可为空");
                     return;
                 }

                dialog.dismiss();
            }
        });
        Button cancelBtn = (Button)customView.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    /*private void setDialogDelete(final String operateFlag,final String cateId,final int position){
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

        rootView.findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                presenter.doCampaignDelete(CampaignListActivity.this,campaignId,position);

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
    }*/


}
