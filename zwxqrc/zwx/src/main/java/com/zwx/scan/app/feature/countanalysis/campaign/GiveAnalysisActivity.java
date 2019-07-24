package com.zwx.scan.app.feature.countanalysis.campaign;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.zwx.library.utils.TimeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.CampaignTotal;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.accountmanage.AccountActivity;
import com.zwx.scan.app.feature.accountmanage.AccountListAdapter;
import com.zwx.scan.app.feature.cateringinfomanage.StoreInfoManageActivity;
import com.zwx.scan.app.widget.MyListView;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GiveAnalysisActivity extends BaseActivity<CampaignContract.Presenter>  implements CampaignContract.View,View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ScrollView> {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.btn_effect)
    protected Button btnEff;

    @BindView(R.id.btn_uneffect)
    protected Button btnUneff;

    @BindView(R.id.list_view)
   public MyListView listView;
    @BindView(R.id.ptr)
    public PullToRefreshScrollView ptr;

    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;

    @BindView(R.id.iv_no_data)
    protected ImageView iv_no_data;

    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;


    private String userId;
    protected static String campaignStatus = "P";
    private int pageNumber = 1;
    private int pageSize = 10;
    protected GiveAnalysisListViewAdapter listAdapter = null;
//    protected RecyclerAdapterWithHF mAdapter = null;
    List<CampaignTotal> campaignTotalList = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_give_analysis;
    }

    @Override
    protected void initView() {

        DaggerCampaignComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignModule(new CampaignModule(this))
                .build()
                .inject(this);
        tvTitle.setText(getText(R.string.changguifaquan_manage_count));
    }

    @Override
    protected void initData() {
        userId = SPUtils.getInstance().getString("userId");

        btnEff.setBackground(getResources().getDrawable(R.drawable.ic_btn_blue_selected));
        btnEff.setTextColor(getResources().getColor(R.color.white));
        btnUneff.setBackground(getResources().getDrawable(R.drawable.ic_btn_gray_unselect));
        btnUneff.setTextColor(getResources().getColor(R.color.color_gray_deep));
        campaignStatus = "P";


        setRvAdapter();

    }


    private void setRvAdapter(){
        listAdapter = new GiveAnalysisListViewAdapter(this, campaignTotalList);
        listView.setAdapter(listAdapter);
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
        presenter.campaignAnalysisForzj(GiveAnalysisActivity.this, userId,campaignStatus,pageSize,pageNumber,true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CampaignTotal campaignTotal = campaignTotalList.get(position);
                String time = campaignTotal.getTimeRange();
                String date = "";
                if(time != null && !"".equals(time)){
                    if("N".equals(GiveAnalysisActivity.campaignStatus)){

                    }else {
                        String startDate  = time.substring(0,10);
                        String curDateStr = TimeUtils.getReqDate(TimeUtils.getNowDate());

                        Date end = TimeUtils.string2Date(curDateStr);

                        if(startDate != null && !"".equals(startDate)){
                            Date start = TimeUtils.string2Date(startDate.replace(".","-"));
                            Long day = (end.getTime() - start.getTime())/(24*60*60*1000);
                            if(day != null && day.intValue()>0){
                                campaignTotal.setDays(day.intValue());
                            }else {
                                campaignTotal.setDays(0);
                            }
                        }
                        campaignTotal.setTimeRange(startDate+" - 今日");

                    }


                }
                Intent intent = new Intent(GiveAnalysisActivity.this,GiveAnalysisDetailActivity.class);
                intent.putExtra("title",campaignTotal.getCampaignName());
                intent.putExtra("campaignId",String.valueOf(campaignTotal.getCampaignId()));
                intent.putExtra("campaignTotal",campaignTotal);
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        campaignTotalList.clear();
        pageNumber = 1;
        presenter.campaignAnalysisForzj(GiveAnalysisActivity.this, userId,campaignStatus,pageSize,pageNumber,true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.campaignAnalysisForzj(GiveAnalysisActivity.this, userId,campaignStatus,pageSize,pageNumber,false);
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
                presenter.campaignAnalysisForzj(GiveAnalysisActivity.this, userId,campaignStatus,pageSize,pageNumber,true);
            }
        });


        ptr.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNumber++;
                presenter.campaignAnalysisForzj(GiveAnalysisActivity.this, userId,campaignStatus,pageSize,pageNumber,false);

            }
        });
    }*/



    @OnClick({R.id.iv_back,R.id.btn_effect,R.id.btn_uneffect})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.finishActivity(GiveAnalysisActivity.class,
                        R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            case R.id.btn_effect:
                btnEff.setBackground(getResources().getDrawable(R.drawable.ic_btn_blue_selected));
                btnEff.setTextColor(getResources().getColor(R.color.white));
                btnUneff.setBackground(getResources().getDrawable(R.drawable.ic_btn_gray_unselect));
                btnUneff.setTextColor(getResources().getColor(R.color.color_gray_deep));
                campaignStatus = "P";
                campaignTotalList.clear();
                presenter.campaignAnalysisForzj(GiveAnalysisActivity.this, userId,campaignStatus,pageSize,pageNumber,true);

                break;

            case R.id.btn_uneffect:
                campaignStatus = "N";
                campaignTotalList.clear();
                btnUneff.setBackground(getResources().getDrawable(R.drawable.ic_btn_blue_selected));
                btnUneff.setTextColor(getResources().getColor(R.color.white));
                btnEff.setBackground(getResources().getDrawable(R.drawable.ic_btn_gray_unselect));
                btnEff.setTextColor(getResources().getColor(R.color.color_gray_deep));
//                presenter.queryMoreSpecificCampaignCountList(this,storeId,"ECI",date,true,false);
                presenter.campaignAnalysisForzj(GiveAnalysisActivity.this, userId,campaignStatus,pageSize,pageNumber,true);
                break;


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(GiveAnalysisActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
    }

}
