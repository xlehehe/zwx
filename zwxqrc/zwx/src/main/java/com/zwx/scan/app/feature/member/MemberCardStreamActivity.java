package com.zwx.scan.app.feature.member;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.zwx.scan.app.data.bean.CampaignChartBean;
import com.zwx.scan.app.data.bean.GroupBuyCampagin;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.Order;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.countanalysis.MoreBrandListAdapter;
import com.zwx.scan.app.feature.countanalysis.campaign.CampaignAnalysisActivity;
import com.zwx.scan.app.feature.home.SelectStoreAdapter;
import com.zwx.scan.app.feature.ptmanage.PtOrderActivity;
import com.zwx.scan.app.feature.ptmanage.PtOrderDetailActivity;
import com.zwx.scan.app.feature.ptmanage.PtOrderListAdapter;
import com.zwx.scan.app.widget.CustomPopWindow;
import com.zwx.scan.app.widget.MyListView;
import com.zwx.scan.app.widget.PhilExpandableTextView;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/04/04
 * desc   : 会员卡流水
 * version: 1.0
 **/
public class MemberCardStreamActivity extends BaseActivity<MemberManageContract.Presenter> implements MemberManageContract.View,View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ScrollView> {

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
    @BindView(R.id.ptr)
    public PullToRefreshScrollView ptr;
    @BindView(R.id.tv_select_shop)
    protected TextView tv_select_shop;

    @BindView(R.id.iv_brand)
    protected ImageView ivBrand;

    @BindView(R.id.ll_select_store)
    protected LinearLayout ll_select_store;

    @BindView(R.id.tv_store_name)
    protected PhilExpandableTextView tvStoreName;

    @BindView(R.id.tv_select_store_title1)
    protected TextView tv_select_store_title1;
    @BindView(R.id.ll_title)
    protected RelativeLayout ll_title;

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
    private String salesTimeSta;
    private String salesTimesEnd;
    private String storeId;
    private String storeName;
    private String userId;
    private int brandPostion = 0;
    public MemberCardStreamListViewAdapter listAdapter;
//    public RecyclerAdapterWithHF mAdapter;
    protected List<Order> orderList = new ArrayList<Order>();

    private boolean isSelectDate = false;



    public List<Store> storeList  = new ArrayList<>();
    public String brandLogo;
    private CustomPopWindow popWindow;
    private boolean isClicked = true;
    private SelectStoreAdapter myAdapter;
    public List<MoreStoreBean.BrandStoreBean> branList = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_card_stream;
    }

    @Override
    protected void initView() {

        DaggerMemberManageComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .memberManageModule(new MemberManageModule(this))
                .build()
                .inject(this);
        tvTitle.setText("会员卡收款流水");
        tv_select_store_title1.setText("选择店铺");
        tv_select_shop.setText("选择查看店铺");
        initDatePicker();
        //默认本周
        btn_day.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
        btn_day.setTextColor(getResources().getColor(R.color.color_gray_deep));

        btn_week.setBackground(getResources().getDrawable(R.drawable.ic_time_selected));
        btn_week.setTextColor(getResources().getColor(R.color.btn_color_blue));

        btn_month.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
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
        storeName = SPUtils.getInstance().getString("storeName");
        userId = SPUtils.getInstance().getString("userId");
        brandLogo = SPUtils.getInstance().getString("brandLogo");
        setBrandLogo();
        tvStoreName.setText("# "+storeName);
        listAdapter = new MemberCardStreamListViewAdapter(MemberCardStreamActivity.this, orderList);
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


        presenter.selectToCounByOrder(MemberCardStreamActivity.this, userId, salesTimeSta,salesTimesEnd,storeId, pageSize, pageNumber,true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MemberCardStreamActivity.this, MemberCardStreamDetailActivity.class);
                if (orderList != null && orderList.size() > 0) {
                    Order order = orderList.get(position);
                    intent.putExtra("compMemtypeId",order.getCompMemtypeId()+"");
                    intent.putExtra("memTypeName",order.getMemtypeName());
                    intent.putExtra("order",order);
                    intent.putExtra("storeName",storeName);
                    intent.putExtra("storeId",storeId);

                    startDate = tv_start_time.getText().toString( ).trim();
                    endDate = tv_end_time.getText().toString().trim();
                    intent.putExtra("salesTimeSta",startDate);
                    intent.putExtra("salesTimesEnd",endDate);
                    intent.putExtra("brandLogo",brandLogo);


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

                    if(salesTimeSta != null && !"".equals(salesTimeSta)){
                        if(salesTimesEnd != null && !"".equals(salesTimesEnd)){
                            pageNumber = 1;
                            presenter.selectToCounByOrder(MemberCardStreamActivity.this, userId, salesTimeSta,salesTimesEnd,storeId, pageSize, pageNumber,true);
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

                    if(salesTimeSta != null && !"".equals(salesTimeSta)){
                        if(salesTimesEnd != null && !"".equals(salesTimesEnd)){
                            pageNumber = 1;
                            presenter.selectToCounByOrder(MemberCardStreamActivity.this, userId, salesTimeSta,salesTimesEnd,storeId, pageSize, pageNumber,true);
                        }
                    }
                }
            }
        });
    }
    private void setGrayBg(){
        btn_day.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
        btn_day.setTextColor(getResources().getColor(R.color.color_gray_deep));

        btn_week.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
        btn_week.setTextColor(getResources().getColor(R.color.color_gray_deep));

        btn_month.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
        btn_month.setTextColor(getResources().getColor(R.color.color_gray_deep));
    }
    @OnClick({R.id.iv_back,R.id.btn_day,R.id.btn_week,R.id.btn_month,R.id.ll_start,R.id.ll_end,R.id.tv_select_shop})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.finishActivity(MemberCardStreamActivity.class,
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
                btn_day.setBackground(getResources().getDrawable(R.drawable.ic_time_selected));
                btn_day.setTextColor(getResources().getColor(R.color.btn_color_blue));
                btn_week.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
                btn_week.setTextColor(getResources().getColor(R.color.color_gray_deep));

                btn_month.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
                btn_month.setTextColor(getResources().getColor(R.color.color_gray_deep));
                tv_start_time.setText(TimeUtils.getReqDate(new Date()));
                tv_end_time.setText(TimeUtils.getReqDate(new Date()));
                startDate = tv_start_time.getText().toString().trim();
                endDate = tv_end_time.getText().toString().trim();
                salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-"):"";

                isSelectDate = false;
                salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-"):"";
                pageNumber = 1;
                orderList.clear();
                presenter.selectToCounByOrder(MemberCardStreamActivity.this, userId, salesTimeSta,salesTimesEnd,storeId, pageSize, pageNumber,true);
                break;

            case R.id.btn_week:
                btn_day.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
                btn_day.setTextColor(getResources().getColor(R.color.color_gray_deep));

                btn_week.setBackground(getResources().getDrawable(R.drawable.ic_time_selected));
                btn_week.setTextColor(getResources().getColor(R.color.btn_color_blue));

                btn_month.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
                btn_month.setTextColor(getResources().getColor(R.color.color_gray_deep));

                startDate = tv_start_time.getText().toString().trim();
                endDate = tv_end_time.getText().toString().trim();
                tv_start_time.setText(TimeUtils.getReqDate(TimeUtils.getStartWeekDate()));
                tv_end_time.setText(TimeUtils.getReqDate(TimeUtils.getEndWeekDate()));

                startDate = tv_start_time.getText().toString().trim();
                endDate = tv_end_time.getText().toString().trim();
                salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-"):"";

                salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-"):"";


              /*  salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-")+" "+ DateUtils.formatTime(System.currentTimeMillis()):"";

                salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-")+" "+ DateUtils.formatTime(System.currentTimeMillis()):"";*/
                isSelectDate = false;
                pageNumber = 1;
                orderList.clear();
                presenter.selectToCounByOrder(MemberCardStreamActivity.this, userId, salesTimeSta,salesTimesEnd,storeId, pageSize, pageNumber,true);
                break;

            case R.id.btn_month:
                btn_day.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
                btn_day.setTextColor(getResources().getColor(R.color.color_gray_deep));

                btn_week.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
                btn_week.setTextColor(getResources().getColor(R.color.color_gray_deep));

                btn_month.setBackground(getResources().getDrawable(R.drawable.ic_time_selected));
                btn_month.setTextColor(getResources().getColor(R.color.btn_color_blue));
//                presenter.campaignAnalysisForPt(PtOrderActivity.this, userId,campaignStatus,pageSize,pageNumber,true);
                tv_start_time.setText(TimeUtils.getReqDate(TimeUtils.getFirstDayOfMonth(new Date())));
                tv_end_time.setText(TimeUtils.getReqDate(TimeUtils.getLastDayOfMonth(new Date())));
                startDate = tv_start_time.getText().toString().trim();
                endDate = tv_end_time.getText().toString().trim();
                salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-"):"";

                salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-"):"";
                isSelectDate = false;
                pageNumber = 1;
                orderList.clear();
                presenter.selectToCounByOrder(MemberCardStreamActivity.this, userId, salesTimeSta,salesTimesEnd,storeId, pageSize, pageNumber,true);
                break;
            case R.id.tv_select_shop:

                if(storeList != null && storeList.size()>0){
                    showPopView(this);
                }else {
                    presenter.queryStoreList(this,userId);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(MemberCardStreamActivity.class,
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
        orderList.clear();
        pageNumber = 1;
        presenter.selectToCounByOrder(MemberCardStreamActivity.this, userId, salesTimeSta,salesTimesEnd,storeId, pageSize, pageNumber,true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.selectToCounByOrder(MemberCardStreamActivity.this, userId, salesTimeSta,salesTimesEnd,storeId, pageSize, pageNumber,false);
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
                    presenter.selectToCounByOrder(MemberCardStreamActivity.this, userId, salesTimeSta,salesTimesEnd,storeId, pageSize, pageNumber,true);
                }
            });


            ptr.setOnLoadMoreListener(new OnLoadMoreListener() {

                @Override
                public void loadMore() {
                    pageNumber++;
                    presenter.selectToCounByOrder(MemberCardStreamActivity.this, userId, salesTimeSta,salesTimesEnd,storeId, pageSize, pageNumber,false);

                }
            });
        }*/
    private void setBrandLogo(){
        RoundedCorners roundedCorners= new RoundedCorners(8);

        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_image_loading);

        Glide.with(this).load(brandLogo).apply(requestOptions).into(ivBrand);
    }

    protected void  showPopView(Context context){

        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_select_more_shop,null);
        //创建并显示popWindow
        popWindow= new CustomPopWindow.PopupWindowBuilder(context)
                .setView(contentView)
//                .enableBackgroundDark(true)   //背景是否变暗
//                 .setBgDarkAlpha(0.7f) //调整亮度
//                .enableOutsideTouchableDissmiss(false)
                .setOutsideTouchable(false)
                .size(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create()
                .showAsDropDown(ll_title,0,2);


        ListView lv_left = contentView.findViewById(R.id.lv_menu);
        ListView lv_right = contentView.findViewById(R.id.lv_home);
        Button reset = contentView.findViewById(R.id.reset);
        Button confirm = contentView.findViewById(R.id.confirm);

        TextView tv_all =contentView.findViewById(R.id.tv_all);


        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isClicked){
                    if(storeList!=null&& storeList.size()>0){
                        for (Store store:storeList){
                            if(store!=null){
                                store.setChecked(false);
                            }
                        }

                        for (Store store:storeList){
                            if(store!=null){

                                store.setChecked(true);
                            }

                        }
                    }
                    isClicked = false;
                }else {
                    if(storeList!=null&& storeList.size()>0){
                        for (Store store:storeList){
                            if(store!=null){
                                store.setChecked(false);
                            }
                        }
                    }
                    isClicked = true;
                }

                myAdapter.notifyDataSetChanged();


            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(storeList!=null&& storeList.size()>0){
                    for (Store store:storeList){
                        store.setChecked(false);
                    }
                    myAdapter.notifyDataSetChanged();

                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder storeNameSb = new StringBuilder();
                StringBuilder storeIdSb = new StringBuilder();
                String storeIdArr = "";
                if(storeList!=null&& storeList.size()>0){

                    for (Store store: storeList){
                        if(store!=null){
                            if(store.isChecked()){
                                String name = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                storeNameSb.append(name);
                                storeIdSb.append(store.getStoreId()).append("-");
                            }

                        }
                    }
                    tvStoreName.setText(storeNameSb.toString());
                }

                String storeIdA= storeIdSb.toString();
                if(storeIdA!=null&& !"".equals(storeIdA)){
                    storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                    String storeNameA = storeNameSb.toString();
                    storeId = storeIdArr;
                    storeName = storeNameSb.toString();
                    if(branList !=null && branList.size()>0){
                        brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                        if(brandLogo!=null){
                            setBrandLogo();
                        }
                    }

                    orderList.clear();
                    presenter.selectToCounByOrder(MemberCardStreamActivity.this, userId, salesTimeSta,salesTimesEnd,storeId, pageSize, pageNumber,true);
                    popWindow.dissmiss();
                }else {
                    ToastUtils.showShort("请选择店铺");
                    return;
                }




            }
        });




        MoreBrandListAdapter brandListAdapter = new MoreBrandListAdapter(this,branList);
        myAdapter = new SelectStoreAdapter(context);
        if(brandPostion == -1){
            brandPostion = 0;
        }
        brandListAdapter.setSelectItem(brandPostion);

        lv_left.setAdapter(brandListAdapter);
        int selectItem = brandListAdapter.getSelectItem();
        if(branList!=null&&branList.size()>0){
            storeList = branList.get(selectItem).getStoreList();
            brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(selectItem).getBrandLogo();
            SPUtils.getInstance().put("brandLogo1",brandLogo);
            setBrandLogo();
            myAdapter.setDatas(storeList);
        }


        lv_right.setAdapter(myAdapter);

        //判断默认选择店铺
        String storeNameStr = tvStoreName.getText().toString().trim();
        if(storeList!=null&&storeList.size()>0){

            for (int i = 0;i<storeList.size();i++){
                Store store = storeList.get(i);

                if(storeNameStr!=null){

                    if(storeNameStr.contains(store.getStoreName())){
                        store.setChecked(true);
                    }else {
                        store.setChecked(false);
                    }
                }

            }
        }



        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(branList!=null&&branList.size()>0){
                    if(branList.get(position)!=null){
                        brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(position).getBrandLogo();
                        RequestOptions requestOptions = new RequestOptions()
                                .placeholder(R.drawable.ic_banner_default)
                                .error(R.drawable.ic_banner_default)
                                .fallback(R.drawable.ic_banner_default);
                        Glide.with(context).load(brandLogo).apply(requestOptions).into(ivBrand);

                        storeList = branList.get(position).getStoreList();
                        brandPostion = position;
                        brandListAdapter.setSelectItem(position);
                        brandListAdapter.notifyDataSetInvalidated();
                        myAdapter = new SelectStoreAdapter(context);
                        myAdapter.setDatas(storeList);
                        lv_right.setAdapter(myAdapter);
                    }
                }

            }
        });
/*
        String storeName = tvStoreName.getTextView().getText().toString().trim();
        //判断默认选择的店铺为true;否则false
        for(Store store : storeList){
            if(storeName.contains(store.getStoreName())){
                store.setChecked(true);
            }else {
                store.setChecked(false);
            }

        }*/
        myAdapter.notifyDataSetChanged();
        lv_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //            int currentNum = -1;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int currentNum = -1;
                if(currentNum == -1){ //选中
                    if(storeList.get(position).isChecked()){
                        storeList.get(position).setChecked(false);
                    }else {
                        storeList.get(position).setChecked(true);
                    }

                    currentNum = position;
                }else if(currentNum == position){ //同一个item选中变未选中
                   /* for(Store store : storeList){
                        store.setChecked(false);
                    }*/
                    storeList.get(position).setChecked(false);
                    currentNum = -1;
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的
                  /*  for(Store store : storeList){
                        store.setChecked(false);
                    }*/
//                    storeList.get(position).setChecked(true);

                    if(storeList.get(position).isChecked()){
                        storeList.get(position).setChecked(false);
                    }else {
                        storeList.get(position).setChecked(true);
                    }
                    currentNum = position;
                }

                myAdapter.notifyDataSetChanged();

            }
        });
    }
}
