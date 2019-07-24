package com.zwx.scan.app.feature.personal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.Feedback;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.member.MemberInfoListActivity;
import com.zwx.scan.app.feature.member.MemberInfoListAdapter;
import com.zwx.scan.app.widget.RecycleViewDivider;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FeedBackListActivity extends BaseActivity<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener  {
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.tv_right)
    protected TextView tv_right;


    @BindView(R.id.rv_list)
    public RecyclerView rvList;
    @BindView(R.id.ptr)
    public PtrClassicFrameLayout ptr;

    protected List<Feedback> feedbackList = new ArrayList<>();

    private String userId;
    private int pageNumber = 1;
    private int pageSize = 10;
    public FeedBackAdapter listAdapter;
    public RecyclerAdapterWithHF mAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back_list;
    }

    @Override
    protected void initView() {

        tvTitle.setText(getResources().getText(R.string.feed_back_msg));
        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);
        userId = SPUtils.getInstance().getString("userId");
        initPtr();
    }

    @Override
    protected void initData() {



        listAdapter = new FeedBackAdapter(FeedBackListActivity.this, feedbackList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new RecycleViewDivider( LinearLayoutManager.VERTICAL, 20, getResources().getColor(R.color.divide_gray_color)));
//        rvList.setNestedScrollingEnabled(false);
        mAdapter = new RecyclerAdapterWithHF(listAdapter);
        mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {

                Feedback feedback1 = feedbackList.get(position);
                Intent intent = new Intent(FeedBackListActivity.this,FeedBackDetailActivity.class);
                intent.putExtra("feedbackId",feedback1.getFeedbackId()+"");
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        rvList.setAdapter(mAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.doFeedList(FeedBackListActivity.this,userId, pageNumber, pageSize, true);
    }

    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(FeedBackListActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);

                break;



        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(FeedBackListActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }


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
                presenter.doFeedList(FeedBackListActivity.this,userId, pageNumber, pageSize, true);
            }
        });


        ptr.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNumber++;
                presenter.doFeedList(FeedBackListActivity.this,userId, pageNumber, pageSize, false);

            }
        });
    }
}
