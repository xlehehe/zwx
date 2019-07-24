
package com.zwx.scan.app.feature.staffmanage;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.StaffWork;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.modulemore.ModuleMoreListActivity;
import com.zwx.scan.app.feature.verification.VerificationActivity;
import com.zwx.scan.app.widget.MoreTextView;
import com.zwx.scan.app.widget.MyListView;
import com.zwx.scan.app.widget.PhilExpandableTextView;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class StaffListActivity extends BaseActivity<StaffManageContract.Presenter> implements StaffManageContract.View,View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ScrollView>   {

    @BindView(R.id.iv_back)
    protected ImageView ivBack;
    @BindView(R.id.tv_right)
    protected TextView tvRight;


  /*  @BindView(R.id.rv_list)
    public RecyclerView recyclerView;
    @BindView(R.id.test_recycler_view_frame)
    public PtrClassicFrameLayout ptrClassicFrameLayout;*/
    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.tv_select_shop)
    protected TextView tvSelect;

    @BindView(R.id.iv_brand)
    protected ImageView ivBrand;

    @BindView(R.id.tv_store_name)
    protected PhilExpandableTextView tvStoreName;

    @BindView(R.id.iv_arrow)
    protected ImageView iv_arrow;

    @BindView(R.id.list_view)
    public MyListView listView;

    @BindView(R.id.ptr)
    public PullToRefreshScrollView ptr;
    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;
    @BindView(R.id.iv_no_data)
    protected ImageView iv_no_data;


    public List<StaffWork> staffWorks = new ArrayList<>();
    private int pageNumber = 1;
    private int pageSize = 10;
    private String storeId;
    protected StaffListViewdapter listAdapter;

//    public RecyclerAdapterWithHF mAdapter;

    private String storeName ;
    private String brandLogo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_staff_list;
    }

    @Override
    protected void initView() {
        tvTitle.setText("员工管理");
        tvRight.setText("扫码新增员工");
        tvRight.setVisibility(View.VISIBLE);
        DaggerStaffManageComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .staffManageModule(new StaffManageModule(this))
                .build()
                .inject(this);

//        initPtr();
    }

    private void setBrandLogo(){
        RoundedCorners roundedCorners= new RoundedCorners(8);

        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_logo_default)
                .error(R.drawable.ic_logo_default)
                .fallback(R.drawable.ic_logo_default);

        Glide.with(this).load(brandLogo).apply(requestOptions).into(ivBrand);
    }

    @Override
    protected void initData() {
        storeId = SPUtils.getInstance().getString("storeId");

        storeName = SPUtils.getInstance().getString("storeName");
        brandLogo = SPUtils.getInstance().getString("brandLogo");
        tvStoreName.setText("#"+storeName);
        setBrandLogo();
        presenter.list(StaffListActivity.this, pageNumber,pageSize ,storeId,true);


        listAdapter = new StaffListViewdapter(this,staffWorks);
     /*   recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpacesItemDecoration(20));
        mAdapter = new RecyclerAdapterWithHF(staffListAdapter);*/
        listView.setAdapter(listAdapter);
       /* mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                Intent intent = new Intent(StaffListActivity.this,StaffEditActivity.class);
                if(staffWorks!=null&&staffWorks.size()>0){
                    StaffWork staffWork = staffWorks.get(position);
                    intent.putExtra("staffWork",staffWork);
                    intent.putExtra("isNewStaff",false);
                    intent.putExtra("isEditStaff","isEdtStaff");
                    ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                }
            }
        });*/

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

        ll_no_data.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        tv_no_data.setText("");
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(StaffListActivity.this,StaffEditActivity.class);
               if(staffWorks!=null&&staffWorks.size()>0){
                   StaffWork staffWork = staffWorks.get(position);
                   intent.putExtra("staffWork",staffWork);
                   intent.putExtra("isNewStaff",false);
                   intent.putExtra("isEditStaff","isEdtStaff");
                   ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
               }
           }
       });

    }


    @OnClick({R.id.iv_back,R.id.tv_right})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.startActivity(ModuleMoreListActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
                finish();

                break;
            case R.id.tv_right:

//                Intent intent = new Intent(StaffListActivity.this,StaffEditActivity.class);
//                intent.putExtra("isNewStaff",true);

                ActivityUtils.startActivity(VerificationActivity.class,R.anim.slide_in_right,R.anim.slide_out_left);
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
        staffWorks.clear();
        presenter.list(StaffListActivity.this, pageNumber,pageSize ,storeId,true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.list(StaffListActivity.this, pageNumber,pageSize ,storeId,false);
    }

/* private  void initPtr(){
        ptrClassicFrameLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh(true);
            }
        }, 150);


        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNumber = 1;
                staffWorks.clear();
                presenter.list(StaffListActivity.this, pageNumber,pageSize ,storeId,true);
//                ptrClassicFrameLayout.re
            }
        });



        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNumber++;

                presenter.list(StaffListActivity.this, pageNumber,pageSize ,storeId,false);

            }
        });
    }*/


}
