package com.zwx.scan.app.feature.accountmanage;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.zwx.scan.app.data.bean.User;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.cateringinfomanage.StoreInfoManageActivity;
import com.zwx.scan.app.feature.personal.DaggerPersonalComponent;
import com.zwx.scan.app.feature.personal.PersonalModule;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AccountActivity extends BaseActivity<AccountContract.Presenter> implements AccountContract.View,View.OnClickListener{

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.tv_right)
    protected TextView tvRight;
    @BindView(R.id.ll_right)
    protected LinearLayout llRight;

    @BindView(R.id.rv_list)
    public RecyclerView rvList;


    @BindView(R.id.ptr)
    public PtrClassicFrameLayout ptr;
/*
    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;

    @BindView(R.id.iv_no_data)
    protected ImageView iv_no_data;

    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;*/

    private String userId;
    private String compId;
    private int pageNumber = 1;
    private int pageSize = 10;

    protected List<User> userList = new ArrayList<>(); 
    protected AccountListAdapter listAdapter = null;
    protected RecyclerAdapterWithHF mAdapter = null;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_manage_module;
    }

    @Override
    protected void initView() {

        DaggerAccountComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .accountModule(new AccountModule(this))
                .build()
                .inject(this);

        tvTitle.setText(getResources().getString(R.string.title_account_manage));
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.new_account));

        initPtr();
    }

    @Override
    protected void initData() {
        userId = SPUtils.getInstance().getString("userId");

        presenter.accountList(this,userId,pageNumber,pageSize,true);
        setCaterAdapter();
    }

    private void setCaterAdapter(){
        listAdapter = new AccountListAdapter(AccountActivity.this, userList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new SpacesItemDecoration(20));
        mAdapter = new RecyclerAdapterWithHF(listAdapter);
        rvList.setAdapter(mAdapter);

        ptr.setVisibility(View.VISIBLE);
        mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                User user = userList.get(position);
                Intent  intent = new Intent(AccountActivity.this,AccountNewActivity.class);
                intent.putExtra("isNew","EDIT");
                intent.putExtra("user",user);
                intent.putExtra("title",getResources().getString(R.string.edit_account));
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }


    @OnClick({R.id.iv_back,R.id.ll_right})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {

            case R.id.iv_back:
                ActivityUtils.finishActivity(AccountActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);

                break;

            case R.id.ll_right:
                intent = new Intent(AccountActivity.this,AccountNewActivity.class);

                intent.putExtra("isNew","NEW");
                intent.putExtra("title",getResources().getString(R.string.new_account));
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                break;


        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(AccountActivity.class,
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
                presenter.accountList(AccountActivity.this, userId, pageNumber, pageSize,true);
            }
        });


        ptr.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNumber++;
                presenter.accountList(AccountActivity.this, userId, pageNumber, pageSize,false);

            }
        });
    }

}
