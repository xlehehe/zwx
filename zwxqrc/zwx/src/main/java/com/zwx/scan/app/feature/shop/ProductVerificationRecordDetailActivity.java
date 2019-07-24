package com.zwx.scan.app.feature.shop;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.TOrderConsumeLog;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.widget.MyListView;
import com.zwx.scan.app.widget.PhilExpandableTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ProductVerificationRecordDetailActivity extends BaseActivity<ShopContract.Presenter> implements ShopContract.View ,View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ScrollView>  {
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_brand)
    protected ImageView iv_brand;
    @BindView(R.id.tv_store_name)
    protected PhilExpandableTextView tv_store_name;

    @BindView(R.id.tv_time)
    protected TextView tv_time;

    @BindView(R.id.list_view)
    public MyListView listView;

    @BindView(R.id.ptr)
    protected PullToRefreshScrollView ptr;

    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;


    private String storeId;
    private String storeName;
    private String brandLogo;
    private String userId;
    private String memberTel = "";
    private int pageNumber = 1;
    private int pageSize = 10;


    protected String tel;
    protected String startDate;
    protected String endDate;
    private String salesTimeSta;
    private String salesTimesEnd;
    private boolean isSelectDate = false;

    public ProductVerificationRecordListDetailAdapter listAdapter;
    //    public RecyclerAdapterWithHF mAdapter;
    protected List<TOrderConsumeLog> orderList = new ArrayList<TOrderConsumeLog>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_verification_record_detail;
    }

    @Override
    protected void initView() {
        DaggerShopComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .shopModule(new ShopModule(this))
                .build()
                .inject(this);
        tvTitle.setText("商品核销详情记录");

        storeId = SPUtils.getInstance().getString("storeId");
        storeName = SPUtils.getInstance().getString("storeName");
        brandLogo = SPUtils.getInstance().getString("brandLogo");
        tv_store_name.setText("#"+storeName);
        setBrandLogo();
    }

    private void setBrandLogo(){
        RoundedCorners roundedCorners= new RoundedCorners(8);

        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_logo_default)
                .error(R.drawable.ic_logo_default)
                .fallback(R.drawable.ic_logo_default);

        Glide.with(this).load(brandLogo).apply(requestOptions).into(iv_brand);
    }

    @Override
    protected void initData() {
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        tv_time.setText( (startDate!= null &&!"".equals(startDate)?startDate:"") + " - "+ (endDate!= null &&!"".equals(endDate)?endDate:""));
        salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-"):"";
        salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-"):"";
        userId = SPUtils.getInstance().getString("userId");
        listAdapter = new ProductVerificationRecordListDetailAdapter(ProductVerificationRecordDetailActivity.this, orderList);
        listView.setAdapter(listAdapter);
        ILoadingLayout endLabelsr = ptr.getLoadingLayoutProxy(false, true);

        endLabelsr.setPullLabel("上拉可以加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("加载中");// 刷新时
        endLabelsr.setReleaseLabel("松开加载更多");// 下来达到一定距离时，显示的提示


        ILoadingLayout startLabelse = ptr.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉可以刷新");// 刚下拉时，显示的提示
        startLabelse.setLastUpdatedLabel("正在刷新");// 刷新时
        startLabelse.setReleaseLabel("松开后刷新");
        ptr.setOnRefreshListener(this);
        ptr.setMode(PullToRefreshBase.Mode.BOTH);
        presenter.doProductDetailQueryConsumeLog(ProductVerificationRecordDetailActivity.this,tel,salesTimeSta,salesTimesEnd,pageNumber, pageSize,storeId,true);
    }

    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.startActivity(ProductVerificationRecordActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
                ActivityUtils.finishActivity(ProductVerificationRecordDetailActivity.class);
                break;


        }
    }


    @Override
    public void onBackPressed() {
        ActivityUtils.startActivity(ProductVerificationRecordActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
        ActivityUtils.finishActivity(ProductVerificationRecordDetailActivity.class);
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber = 1;
        presenter.doProductDetailQueryConsumeLog(ProductVerificationRecordDetailActivity.this,tel,salesTimeSta,salesTimesEnd,pageNumber, pageSize,storeId,true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.doProductDetailQueryConsumeLog(ProductVerificationRecordDetailActivity.this,tel,salesTimeSta,salesTimesEnd,pageNumber, pageSize,storeId,false);
    }
}
