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
import android.widget.EditText;
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
import com.zwx.library.utils.DateUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.MemberRedEnvelope;
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.widget.CustomEditText;
import com.zwx.scan.app.widget.MyListView;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/04/25
 * desc   : 红包查询
 * version: 1.0
 **/
public class RedEnvelopeActivity extends BaseActivity<ShopContract.Presenter> implements ShopContract.View ,View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ScrollView>  {


    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.edt_search)
    protected TextView edtSearch;


    @BindView(R.id.tv_red_envelope_num)
    protected TextView tv_red_envelope_num;

    @BindView(R.id.tv_re_envelope_amt)
    protected TextView tv_re_envelope_amt;

    @BindView(R.id.list_view)
    public MyListView listView;
    @BindView(R.id.ptr)
    public PullToRefreshScrollView ptr;

    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;

    private String userId;
    private String memberTel = "";
    private int pageNumber = 1;
    private int pageSize = 15;
    private String compId;
    public RedEnvelopeListViewAdapter listAdapter;
//    public RecyclerAdapterWithHF mAdapter;
    protected List<MemberRedEnvelope> envelopeList = new ArrayList<MemberRedEnvelope>();
    private String memberName = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_red_envelope;
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.queryAllMemberRedEnvlope(RedEnvelopeActivity.this,memberName,memberTel,compId,pageSize,pageNumber,true,false);
    }

    @Override
    protected void initView() {

        DaggerShopComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .shopModule(new ShopModule(this))
                .build()
                .inject(this);
        tvTitle.setText("会员红包查询");
    }

    @Override
    protected void initData() {

        compId = SPUtils.getInstance().getString("compId");
        userId = SPUtils.getInstance().getString("userId");
        listAdapter = new RedEnvelopeListViewAdapter(RedEnvelopeActivity.this, envelopeList);
//        rvList.setLayoutManager(new LinearLayoutManager(this));
//        rvList.addItemDecoration(new SpacesItemDecoration(20));
//        mAdapter = new RecyclerAdapterWithHF(listAdapter);
        listView.setAdapter(listAdapter);
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
//        presenter.queryAllMemberRedEnvlope(RedEnvelopeActivity.this,memberName,memberTel,compId,pageSize,pageNumber,true,false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RedEnvelopeActivity.this, RedEnvelopeDetailActivity.class);
                if (envelopeList != null && envelopeList.size() > 0) {
                    MemberRedEnvelope envelope = envelopeList.get(position);
                    intent.putExtra("memberId",envelope.getMemberId()+"");
                    intent.putExtra("memberTel",envelope.getMemberTel());
                    intent.putExtra("memberName",envelope.getMemberName());
                    BigDecimal balance = envelope.getBalance();
                    if(balance != null && balance.doubleValue()>0){
                        intent.putExtra("balance", RegexUtils.getDoubleString(balance.doubleValue()));
                    }else {
                        intent.putExtra("balance", "-");
                    }

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
                memberTel = edtSearch.getText().toString().trim();
                pageNumber = 1;

            }
        });
    }
    @OnClick({R.id.iv_back,R.id.edt_search})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(RedEnvelopeActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case R.id.edt_search:
                ActivityUtils.startActivity(SearchRedEnvelopeListActivity.class,
                        R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(RedEnvelopeActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber = 1;
        presenter.queryAllMemberRedEnvlope(RedEnvelopeActivity.this,memberName,memberTel,compId,pageSize,pageNumber,true,false);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.queryAllMemberRedEnvlope(RedEnvelopeActivity.this,memberName,memberTel,compId,pageSize,pageNumber,false,false);
    }
    /* private void initPtr() {
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
//                presenter.doQueryOrder(RedEnvelopeActivity.this, userId,memberTel, salesTimeSta,salesTimesEnd,pageNumber, pageSize,true);
                presenter.queryAllMemberRedEnvlope(RedEnvelopeActivity.this,memberName,memberTel,compId,pageSize,pageNumber,true,false);
//                ptr.refreshComplete();
            }
        });


        ptr.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNumber++;
//                ptr.refreshComplete();
//                presenter.doQueryOrder(RedEnvelopeActivity.this, userId,memberTel, salesTimeSta,salesTimesEnd,pageNumber, pageSize,false);
                presenter.queryAllMemberRedEnvlope(RedEnvelopeActivity.this,memberName,memberTel,compId,pageSize,pageNumber,false,false);
            }
        });
    }
*/

}
