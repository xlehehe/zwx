package com.zwx.scan.app.feature.shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.KeyboardUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.MemberRedEnvelope;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.widget.CustomEditText;
import com.zwx.scan.app.widget.MyListView;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchRedEnvelopeListActivity extends BaseActivity<ShopContract.Presenter> implements ShopContract.View ,View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ScrollView>  {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.edt_search)
    protected CustomEditText edtSearch;

    @BindView(R.id.list_view)
    public MyListView listView;
    @BindView(R.id.ptr)
    public PullToRefreshScrollView ptr;

/*    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;*/


    private String userId;
    private String compId;
    private String memberTel = "";
    private String memberName = "";
    private int pageNumber = 1;
    private int pageSize = 10;

    public RedEnvelopeListViewAdapter listAdapter;
//    public RecyclerAdapterWithHF mAdapter;
    protected List<MemberRedEnvelope> envelopeList = new ArrayList<MemberRedEnvelope>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_red_envelope_list;
    }

    @Override
    protected void initView() {
        DaggerShopComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .shopModule(new ShopModule(this))
                .build()
                .inject(this);
        tvTitle.setText("会员红包搜索");
        compId = SPUtils.getInstance().getString("compId");
        userId = SPUtils.getInstance().getString("userId");
//        presenter.queryAllMemberRedEnvlope(SearchRedEnvelopeListActivity.this,memberName,memberTel,compId,pageSize,pageNumber,true,true);

//       listView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        KeyboardUtils.showSoftInput(edtSearch);
        listAdapter = new RedEnvelopeListViewAdapter(SearchRedEnvelopeListActivity.this, envelopeList);
//        rvList.setLayoutManager(new LinearLayoutManager(this));
//        rvList.addItemDecoration(new SpacesItemDecoration(20));
//        mAdapter = new RecyclerAdapterWithHF(listAdapter);
        listView.setAdapter(listAdapter);

        ptr.setOnRefreshListener(this);
        ptr.setMode(PullToRefreshBase.Mode.BOTH);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                KeyboardUtils.showSoftInput(edtSearch);
                memberTel = edtSearch.getText().toString().trim();
                if(memberTel != null && memberTel.length()>0){
                    if(RegexUtils.isZh(memberTel)){
                        memberName = edtSearch.getText().toString().trim();
                        memberTel = "";
                    }else {
                        memberName = "";
                    }



                    pageNumber = 1;
                    presenter.queryAllMemberRedEnvlope(SearchRedEnvelopeListActivity.this,memberName,memberTel,compId,pageSize,pageNumber,true,true);
                }else {
                    ActivityUtils.finishActivity(SearchRedEnvelopeListActivity.class,
                            R.anim.slide_in_left, R.anim.slide_out_right);
                }


            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchRedEnvelopeListActivity.this, RedEnvelopeDetailActivity.class);
                if (envelopeList != null && envelopeList.size() > 0) {
                    MemberRedEnvelope envelope = envelopeList.get(position);
                    intent.putExtra("memberId",envelope.getMemberId()+"");
                    intent.putExtra("memberTel",envelope.getMemberTel());
                    intent.putExtra("memberName",envelope.getMemberName());
                    BigDecimal balance = envelope.getBalance();
                    if(balance != null && balance.doubleValue()>0){
                        intent.putExtra("balance", RegexUtils.getDoubleString(balance.doubleValue()));
                    }else {
                        intent.putExtra("balance", "0");
                    }

                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });
    }


    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(SearchRedEnvelopeListActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(SearchRedEnvelopeListActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber = 1;
        presenter.queryAllMemberRedEnvlope(SearchRedEnvelopeListActivity.this,memberName,memberTel,compId,pageSize,pageNumber,true,true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.queryAllMemberRedEnvlope(SearchRedEnvelopeListActivity.this,memberName,memberTel,compId,pageSize,pageNumber,false,true);
    }

    /*private void initPtr() {
        ptr.postDelayed(new Runnable() {

            @Override
            public void run() {
                ptr.autoRefresh(false);
            }
        }, 150);


        ptr.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNumber = 1;
                presenter.queryAllMemberRedEnvlope(SearchRedEnvelopeListActivity.this,memberName,memberTel,compId,pageSize,pageNumber,true,true);
            }
        });


        ptr.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNumber++;
                presenter.queryAllMemberRedEnvlope(SearchRedEnvelopeListActivity.this,memberName,memberTel,compId,pageSize,pageNumber,false,true);
            }
        });
    }*/
}
