package com.zwx.scan.app.feature.ptmanage;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.zwx.library.pickerview.picker.builder.TimePickerBuilder;
import com.zwx.library.pickerview.picker.listener.CustomListener;
import com.zwx.library.pickerview.picker.listener.OnTimeSelectListener;
import com.zwx.library.pickerview.picker.view.TimePickerView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.DateUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.GroupBuyCampagin;
import com.zwx.scan.app.data.bean.MemberInfoBean;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.countanalysis.campaign.PinTuanAnalysisActivity;
import com.zwx.scan.app.feature.couponmanage.DaggerGiveCouponComponent;
import com.zwx.scan.app.feature.couponmanage.GiveCouponModule;
import com.zwx.scan.app.feature.member.MemberInfoDetailActivity;
import com.zwx.scan.app.feature.member.MemberInfoListActivity;
import com.zwx.scan.app.feature.member.MemberInfoListAdapter;
import com.zwx.scan.app.widget.MyListView;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2018/12/18
 * desc   : 拼团订单
 * version: 1.0
 **/
public class PtOrderActivity extends BaseActivity<PtContract.Presenter> implements PtContract.View,View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ScrollView>  {


    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.btn_day)
    protected Button btn_day;

    @BindView(R.id.btn_week)
    protected Button btn_week;

    @BindView(R.id.btn_month)
    protected Button btn_month;

    @BindView(R.id.ll_start)
    protected LinearLayout ll_start;

    @BindView(R.id.ll_end)
    protected LinearLayout ll_end;

    @BindView(R.id.tv_start_time)
    protected TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    protected TextView tv_end_time;
    @BindView(R.id.list_view)
    public MyListView listView;
//    @BindView(R.id.rv_list)
//    public RecyclerView rvList;
    @BindView(R.id.ptr)
    public PullToRefreshScrollView ptr;
    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;


    protected TimePickerView pvCustomTime;

    private  boolean isStartDate;

    protected String startDate;
    protected String endDate;

    private int pageNumber = 1;
    private int pageSize = 10;
    private String campaignName="";
    private String salesTimeSta;
    private String salesTimesEnd;
    private String storeId;
    public PtOrderListViewAdapter listAdapter;
//    public RecyclerAdapterWithHF mAdapter;
    protected List<GroupBuyCampagin> groupBuyCampaginList = new ArrayList<GroupBuyCampagin>();

    private boolean isSelectDate = false;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pt_order;
    }

    @Override
    protected void initView() {

        DaggerPtComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .ptModule(new PtModule(this))
                .build()
                .inject(this);

        tvTitle.setText("拼团订单");
        initDatePicker();
        //默认本周
        btn_day.setBackground(getResources().getDrawable(R.drawable.shape_pt_order_btn_normal));
        btn_day.setTextColor(getResources().getColor(R.color.color_gray_deep));

        btn_week.setBackground(getResources().getDrawable(R.drawable.shape_pt_order_btn_blue));
        btn_week.setTextColor(getResources().getColor(R.color.btn_color_blue));

        btn_month.setBackground(getResources().getDrawable(R.drawable.shape_pt_order_btn_normal));
        btn_month.setTextColor(getResources().getColor(R.color.color_gray_deep));

        tv_start_time.setText(TimeUtils.getReqDate(TimeUtils.getStartWeekDate()).replace("-","."));
        tv_end_time.setText(TimeUtils.getReqDate(TimeUtils.getEndWeekDate()).replace("-","."));
//        initPtr();


    }

    @Override
    protected void initData() {
        startDate = tv_start_time.getText().toString( ).trim();
        endDate = tv_end_time.getText().toString().trim();
        salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-"):"";
        salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-"):"";
        storeId = SPUtils.getInstance().getString("storeId");

        listAdapter = new PtOrderListViewAdapter(PtOrderActivity.this, groupBuyCampaginList);
//        rvList.setLayoutManager(new LinearLayoutManager(this));
//        rvList.addItemDecoration(new SpacesItemDecoration(20));
//        rvList.setAdapter(listAdapter);
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
        startLabelse.setReleaseLabel("松开后刷新");

        ll_no_data.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);

        presenter.doQueryGroupBuy(PtOrderActivity.this, campaignName, salesTimeSta,salesTimesEnd, pageSize, pageNumber,storeId,true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PtOrderActivity.this, PtOrderDetailActivity.class);
                if (groupBuyCampaginList != null && groupBuyCampaginList.size() > 0) {
                    GroupBuyCampagin groupBuyCampagin = groupBuyCampaginList.get(position);
                    intent.putExtra("campaignId",groupBuyCampagin.getCampaignId()+"");
                    intent.putExtra("campaignName",groupBuyCampagin.getCampaignName());
                    startDate = tv_start_time.getText().toString( ).trim();
                    endDate = tv_end_time.getText().toString().trim();
                    intent.putExtra("salesTimeSta",startDate);
                    intent.putExtra("salesTimesEnd",endDate);
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right, R.anim.slide_out_left);
                }

            }
        });

        tv_start_time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(isSelectDate){
                    setGrayBg();
                    startDate = tv_start_time.getText().toString().trim();
                    endDate = tv_end_time.getText().toString().trim();
                    salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-")+" "+ DateUtils.formatTime(System.currentTimeMillis()):"";

                    salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-")+" "+ DateUtils.formatTime(System.currentTimeMillis()):"";
                    groupBuyCampaginList.clear();
                    if(salesTimeSta != null && !"".equals(salesTimeSta)){
                        if(salesTimesEnd != null && !"".equals(salesTimesEnd)){
                            pageNumber = 1;
                            presenter.doQueryGroupBuy(PtOrderActivity.this, campaignName, salesTimeSta,salesTimesEnd, pageSize, pageNumber,storeId,true);
                        }
                    }
                }

            }
        });
        tv_end_time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(isSelectDate){
                    setGrayBg();
                    startDate = tv_start_time.getText().toString().trim();
                    endDate = tv_end_time.getText().toString().trim();
                    salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-")+" "+ DateUtils.formatTime(System.currentTimeMillis()):"";

                    salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-")+" "+ DateUtils.formatTime(System.currentTimeMillis()):"";
                    groupBuyCampaginList.clear();
                    if(salesTimeSta != null && !"".equals(salesTimeSta)){
                        if(salesTimesEnd != null && !"".equals(salesTimesEnd)){
                            pageNumber = 1;
                            presenter.doQueryGroupBuy(PtOrderActivity.this, campaignName, salesTimeSta,salesTimesEnd, pageSize, pageNumber,storeId,true);
                        }
                    }
                }
            }
        });
    }

    private void setGrayBg(){
        btn_day.setBackground(getResources().getDrawable(R.drawable.shape_pt_order_btn_normal));
        btn_day.setTextColor(getResources().getColor(R.color.color_gray_deep));

        btn_week.setBackground(getResources().getDrawable(R.drawable.shape_pt_order_btn_normal));
        btn_week.setTextColor(getResources().getColor(R.color.color_gray_deep));

        btn_month.setBackground(getResources().getDrawable(R.drawable.shape_pt_order_btn_normal));
        btn_month.setTextColor(getResources().getColor(R.color.color_gray_deep));
    }
    @OnClick({R.id.iv_back,R.id.btn_day,R.id.btn_week,R.id.btn_month,R.id.ll_start,R.id.ll_end})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.finishActivity(PtOrderActivity.class,
                        R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            case R.id.ll_start:  //开始时间

                isStartDate =true;
                pvCustomTime.show();
                break;
            case R.id.ll_end: //结束时间
                isStartDate =false;
                pvCustomTime.show();
                break;

            case R.id.btn_day:
                btn_day.setBackground(getResources().getDrawable(R.drawable.shape_pt_order_btn_blue));
                btn_day.setTextColor(getResources().getColor(R.color.btn_color_blue));
                btn_week.setBackground(getResources().getDrawable(R.drawable.shape_pt_order_btn_normal));
                btn_week.setTextColor(getResources().getColor(R.color.color_gray_deep));

                btn_month.setBackground(getResources().getDrawable(R.drawable.shape_pt_order_btn_normal));
                btn_month.setTextColor(getResources().getColor(R.color.color_gray_deep));
//                presenter.campaignAnalysisForPt(PtOrderActivity.this, userId,campaignStatus,pageSize,pageNumber,true);
                tv_start_time.setText(TimeUtils.getReqDate(new Date()));
                tv_end_time.setText(TimeUtils.getReqDate(new Date()));
                startDate = tv_start_time.getText().toString().trim();
                endDate = tv_end_time.getText().toString().trim();
                salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-"):"";

                isSelectDate = false;
                salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-"):"";
                pageNumber = 1;
                groupBuyCampaginList.clear();
                presenter.doQueryGroupBuy(PtOrderActivity.this, campaignName, salesTimeSta,salesTimesEnd, pageSize, pageNumber,storeId,true);
                break;

            case R.id.btn_week:
                btn_day.setBackground(getResources().getDrawable(R.drawable.shape_pt_order_btn_normal));
                btn_day.setTextColor(getResources().getColor(R.color.color_gray_deep));

                btn_week.setBackground(getResources().getDrawable(R.drawable.shape_pt_order_btn_blue));
                btn_week.setTextColor(getResources().getColor(R.color.btn_color_blue));

                btn_month.setBackground(getResources().getDrawable(R.drawable.shape_pt_order_btn_normal));
                btn_month.setTextColor(getResources().getColor(R.color.color_gray_deep));
//                presenter.campaignAnalysisForPt(PtOrderActivity.this, userId,campaignStatus,pageSize,pageNumber,true);
                startDate = tv_start_time.getText().toString().trim();
                endDate = tv_end_time.getText().toString().trim();
                tv_start_time.setText(TimeUtils.getReqDate(TimeUtils.getStartWeekDate()).replace("-","."));
                tv_end_time.setText(TimeUtils.getReqDate(TimeUtils.getEndWeekDate()).replace("-","."));

                startDate = tv_start_time.getText().toString().trim();
                endDate = tv_end_time.getText().toString().trim();
                salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-"):"";

                salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-"):"";

              /*  salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-")+" "+ DateUtils.formatTime(System.currentTimeMillis()):"";

                salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-")+" "+ DateUtils.formatTime(System.currentTimeMillis()):"";*/
                isSelectDate = false;
                pageNumber = 1;
                groupBuyCampaginList.clear();
                presenter.doQueryGroupBuy(PtOrderActivity.this, campaignName, salesTimeSta,salesTimesEnd, pageSize, pageNumber,storeId,true);
                break;

            case R.id.btn_month:
                btn_day.setBackground(getResources().getDrawable(R.drawable.shape_pt_order_btn_normal));
                btn_day.setTextColor(getResources().getColor(R.color.color_gray_deep));

                btn_week.setBackground(getResources().getDrawable(R.drawable.shape_pt_order_btn_normal));
                btn_week.setTextColor(getResources().getColor(R.color.color_gray_deep));

                btn_month.setBackground(getResources().getDrawable(R.drawable.shape_pt_order_btn_blue));
                btn_month.setTextColor(getResources().getColor(R.color.btn_color_blue));
//                presenter.campaignAnalysisForPt(PtOrderActivity.this, userId,campaignStatus,pageSize,pageNumber,true);
                tv_start_time.setText(TimeUtils.getReqDate(TimeUtils.getFirstDayOfMonth(new Date())).replace("-","."));
                tv_end_time.setText(TimeUtils.getReqDate(TimeUtils.getLastDayOfMonth(new Date())).replace("-","."));
                startDate = tv_start_time.getText().toString().trim();
                endDate = tv_end_time.getText().toString().trim();
                salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-"):"";

                salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-"):"";
                isSelectDate = false;
                pageNumber = 1;
                groupBuyCampaginList.clear();
                presenter.doQueryGroupBuy(PtOrderActivity.this, campaignName, salesTimeSta,salesTimesEnd, pageSize, pageNumber,storeId,true);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(PtOrderActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
    }
    private void initDatePicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 12, 28);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                //区分是否选择上部日期还是下部本周本月 本日
                isSelectDate = true;
                if(isStartDate){
                    tv_start_time.setText(TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd")).replace("-","."));

                }else {
                    tv_end_time.setText(TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd")).replace("-","."));
                }

            }
        })

                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .isCyclic(true)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final ImageView tvSubmit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                String startDate  = tv_start_time.getText().toString().trim();
                                String endDate = tv_end_time.getText().toString().trim();
                                if(isStartDate){
                                    if(!startDate.isEmpty() && !endDate.isEmpty()){
                                        Date start = TimeUtils.string2Date(startDate.replace(".","-"));
                                        Date end = TimeUtils.string2Date(endDate.replace(".","-"));

                                        if(start.after(end)){  //start 大于end时 返回true; 反之 小于等于返回false
                                            tv_start_time.setText("");
                                            ToastUtils.showToast("开始时间不可大于结束时间！");
                                        }else {
                                            pvCustomTime.dismiss();
                                        }


                                    }else {
                                        pvCustomTime.dismiss();
                                    }
                                }else {


                                    if(!startDate.isEmpty() && !endDate.isEmpty()){
                                        Date start = TimeUtils.string2Date(startDate.replace(".","-"));
                                        Date end = TimeUtils.string2Date(endDate.replace(".","-"));

                                        if(end.before(start)){  //start 大于end时 返回true; 反之 小于等于返回false
                                            ToastUtils.showToast("结束时间不可小于开始时间！");
                                            tv_end_time.setText("");
                                        }else {
                                            pvCustomTime.dismiss();
                                        }


                                    }else {
                                        pvCustomTime.dismiss();
                                    }

                                }


                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });

                    }



                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber = 1;
        presenter.doQueryGroupBuy(PtOrderActivity.this, campaignName, salesTimeSta,salesTimesEnd, pageSize, pageNumber,storeId,true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.doQueryGroupBuy(PtOrderActivity.this, campaignName, salesTimeSta,salesTimesEnd, pageSize, pageNumber,storeId,false);
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
                presenter.doQueryGroupBuy(PtOrderActivity.this, campaignName, salesTimeSta,salesTimesEnd, pageSize, pageNumber,storeId,true);
            }
        });


        ptr.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNumber++;
                presenter.doQueryGroupBuy(PtOrderActivity.this, campaignName, salesTimeSta,salesTimesEnd, pageSize, pageNumber,storeId,false);

            }
        });
    }*/



}
