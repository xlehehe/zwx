package com.zwx.scan.app.feature.member;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.Order;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.ptmanage.PtOrderActivity;
import com.zwx.scan.app.widget.MyListView;
import com.zwx.scan.app.widget.PhilExpandableTextView;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * author : lizhilong
 * time   : 2019/04/04
 * desc   : 会员卡流水详情
 * version: 1.0
 **/
public class MemberCardStreamDetailActivity extends BaseActivity<MemberManageContract.Presenter> implements MemberManageContract.View,View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ScrollView>  {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;
    @BindView(R.id.tv_time)
    protected TextView tv_time;

    @BindView(R.id.tv_card_name)
    protected TextView tv_card_name;
    @BindView(R.id.rl_select_store_title)
    protected RelativeLayout rl_select_store_title;
    @BindView(R.id.tv_select_shop)
    protected TextView tv_select_shop;

    @BindView(R.id.iv_brand)
    protected ImageView ivBrand;

    @BindView(R.id.ll_select_store)
    protected LinearLayout ll_select_store;

    @BindView(R.id.tv_store_name)
    protected PhilExpandableTextView tvStoreName;


    @BindView(R.id.list_view)
    public MyListView listView;
    @BindView(R.id.ptr)
    public PullToRefreshScrollView ptr;

    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;

    private int pageNumber = 1;
    private int pageSize = 10;
    private String salesTimeSta;
    private String salesTimesEnd;
    private String storeId;
    private String storeName;
    private String userId;
    protected List<Order> orderList = new ArrayList<Order>();
    public MemberConsumeDetailListViewAdapter listAdapter;
//    public RecyclerAdapterWithHF mAdapter;
    protected String compMemTypeId;
    protected String brandLogo;
    protected String memTypeName;
    protected  Order order = new Order();
    protected  String startDate;
    protected String endDate;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_card_stream_detail;
    }

    @Override
    protected void initView() {
        DaggerMemberManageComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .memberManageModule(new MemberManageModule(this))
                .build()
                .inject(this);
        tvTitle.setText("会员卡收款流水详情");
        rl_select_store_title.setVisibility(View.GONE);

//        initPtr();

    }

    @Override
    protected void initData() {
        order = (Order)getIntent().getSerializableExtra("order");
        compMemTypeId = String.valueOf(order.getCompMemtypeId());
        storeId = getIntent().getStringExtra("storeId");
        storeName = getIntent().getStringExtra("storeName");
        userId = SPUtils.getInstance().getString("userId");
        compMemTypeId = getIntent().getStringExtra("compMemtypeId");
        startDate = getIntent().getStringExtra("salesTimeSta");
        endDate = getIntent().getStringExtra("salesTimesEnd");
        memTypeName = getIntent().getStringExtra("memTypeName");
        tv_time.setText(startDate+"-"+endDate);

        tv_card_name.setText(memTypeName);
        tvStoreName.setText(storeName);
        salesTimeSta = startDate.replace(".","-");
        salesTimesEnd = endDate.replace(".","-");
//        presenter.selectToITByOrder(MemberCardStreamDetailActivity.this, userId,compMemTypeId,salesTimeSta.replace(".","-"),salesTimesEnd.replace(".","-"),storeId ,pageSize, pageNumber,true);

        listAdapter = new MemberConsumeDetailListViewAdapter(MemberCardStreamDetailActivity.this, orderList);
        listView.setAdapter(listAdapter);
        brandLogo = getIntent().getStringExtra("brandLogo");
        setBrandLogo();

        ptr.setOnRefreshListener(this);
        ptr.setMode(PullToRefreshBase.Mode.BOTH);
        ll_no_data.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        ILoadingLayout endLabelsr = ptr.getLoadingLayoutProxy(false, true);

        endLabelsr.setPullLabel("上拉可以加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("加载中");// 刷新时
        endLabelsr.setReleaseLabel("松开加载更多");// 下来达到一定距离时，显示的提示


        ILoadingLayout startLabelse = ptr.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉可以刷新");// 刚下拉时，显示的提示
        startLabelse.setLastUpdatedLabel("正在刷新");// 刷新时
        startLabelse.setReleaseLabel("松开后刷新");

        presenter.selectToITByOrder(MemberCardStreamDetailActivity.this, userId,compMemTypeId,salesTimeSta,salesTimesEnd,storeId ,pageSize, pageNumber,true);
    }

    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.finishActivity(MemberCardStreamDetailActivity.class,
                        R.anim.slide_in_left,R.anim.slide_out_right);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        ActivityUtils.finishActivity(MemberCardStreamDetailActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
    }


    private void setBrandLogo(){
        RoundedCorners roundedCorners= new RoundedCorners(8);

        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_image_loading);

        Glide.with(this).load(brandLogo).apply(requestOptions).into(ivBrand);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        orderList.clear();
        pageNumber = 1;

        presenter.selectToITByOrder(MemberCardStreamDetailActivity.this, userId,compMemTypeId,salesTimeSta,salesTimesEnd,storeId ,pageSize, pageNumber,true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.selectToITByOrder(MemberCardStreamDetailActivity.this, userId,compMemTypeId,salesTimeSta,salesTimesEnd,storeId ,pageSize, pageNumber,false);
    }

}
