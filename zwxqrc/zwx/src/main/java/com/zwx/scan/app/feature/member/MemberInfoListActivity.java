package com.zwx.scan.app.feature.member;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
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
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.MemberInfoBean;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.countanalysis.CountAnalysisHomeActivity;
import com.zwx.scan.app.feature.main.MainActivity;
import com.zwx.scan.app.feature.verificationrecord.VerificationRecordActivity;
import com.zwx.scan.app.feature.verificationrecord.VerificationRecordDetailActivity;
import com.zwx.scan.app.widget.CustomEditText;
import com.zwx.scan.app.widget.MyListView;
import com.zwx.scan.app.widget.RecycleViewDivider;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * author : lizhilong
 * time   : 2019/04/04
 * desc   : 会员卡信息管理
 * version: 1.0
 **/
public class MemberInfoListActivity extends BaseActivity<MemberManageContract.Presenter> implements MemberManageContract.View, View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ScrollView>  {
    @BindView(R.id.iv_back)
    protected ImageView ivBack;


    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.edt_search)
    protected CustomEditText edtSearch;


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
 /*   @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;

    @BindView(R.id.iv_no_data)
    protected ImageView iv_no_data;

    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;*/

    private String userId;
    private String memberTel;
    private int pageNumber = 1;
    private int pageSize = 10;
    public MemberInfoListViewAdapter listAdapter;
    public RecyclerAdapterWithHF mAdapter;
    public List<MemberInfoBean.MemberInfo> memberInfoList = new ArrayList<>();

    public boolean isRefresh = true;

    Handler handler = new Handler();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_info_list;
    }

    @Override
    protected void initView() {
        tvTitle.setText("会员卡信息管理");
        DaggerMemberManageComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .memberManageModule(new MemberManageModule(this))
                .build()
                .inject(this);


        userId = SPUtils.getInstance().getString("userId");
        memberTel = "";

        listAdapter = new MemberInfoListViewAdapter(MemberInfoListActivity.this, memberInfoList);
        listView.setAdapter(listAdapter);
//        rvList.setLayoutManager(new LinearLayoutManager(this));
//        rvList.addItemDecoration(new RecycleViewDivider( LinearLayoutManager.VERTICAL, 20, getResources().getColor(R.color.divide_gray_color)));
//
//        rvList.setNestedScrollingEnabled(false);
//        mAdapter = new RecyclerAdapterWithHF(listAdapter);
//        rvList.setAdapter(mAdapter);

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
        tv_no_data.setText("暂无会员卡信息数据！");
        listView.setVisibility(View.GONE);
        presenter.memberListByMap(MemberInfoListActivity.this, memberTel, userId, pageNumber, pageSize, true);
    }

    @Override
    protected void initData() {


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MemberInfoListActivity.this, MemberInfoDetailActivity.class);
                if (memberInfoList != null && memberInfoList.size() > 0) {
                    MemberInfoBean.MemberInfo memberInfo = memberInfoList.get(position);
                    SPUtils.getInstance().put("compMemTypeId",memberInfo.getCompMemTypeId());
                    intent.putExtra("memberInfo", memberInfo);
                    intent.putExtra("isShowMemberDetail","YES");
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String memTel = edtSearch.getText().toString().trim();
                pageNumber = 1;
                memberInfoList.clear();
                isRefresh = true;
                presenter.memberListByMap(MemberInfoListActivity.this, memTel, userId, pageNumber, pageSize, isRefresh);

            }
        });


    }

    @OnClick({R.id.iv_back, R.id.edt_search})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(MemberInfoListActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
//                ActivityUtils.startActivity(CountAnalysisHomeActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
//                ActivityUtils.finishActivity(MemberInfoListActivity.class);
                break;
            case R.id.edt_search:

                memberInfoList.clear();
//                listAdapter = new MemberInfoListViewAdapter(this, memberInfoList);
                listAdapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        ActivityUtils.finishActivity(MemberInfoListActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
//        ActivityUtils.startActivity(CountAnalysisHomeActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
//        ActivityUtils.finishActivity(MemberInfoListActivity.class);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        memberInfoList.clear();
        pageNumber = 1;
        presenter.memberListByMap(MemberInfoListActivity.this, memberTel, userId, pageNumber, pageSize, true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.memberListByMap(MemberInfoListActivity.this, memberTel, userId, pageNumber, pageSize, false);
    }

    /*private void initPtr() {
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
                presenter.memberListByMap(MemberInfoListActivity.this, memberTel, userId, pageNumber, pageSize, true);
            }
        });


        ptr.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNumber++;
                presenter.memberListByMap(MemberInfoListActivity.this, memberTel, userId, pageNumber, pageSize, false);

            }
        });
    }*/
}
