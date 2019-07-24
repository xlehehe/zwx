package com.zwx.scan.app.feature.financialaffairs;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zwx.library.pickerview.picker.builder.TimePickerBuilder;
import com.zwx.library.pickerview.picker.listener.CustomListener;
import com.zwx.library.pickerview.picker.listener.OnTimeSelectListener;
import com.zwx.library.pickerview.picker.view.TimePickerView;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.DateUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.TOrder;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.campaign.CampaignSelectStoreAdapter;
import com.zwx.scan.app.feature.countanalysis.MoreBrandListAdapter;
import com.zwx.scan.app.widget.MyListView;
import com.zwx.scan.app.widget.PhilExpandableTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/05/30
 * desc   :  会员卡员工销售报表
 * version: 1.0
 **/
public class MemCardEmploeeSaleReportActivity extends BaseActivity<FinancialAffairsContract.Presenter> implements FinancialAffairsContract.View,
        View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ScrollView>  {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.ll_select_store)
    protected LinearLayout ll_select_store;
    @BindView(R.id.tv_select_shop)
    protected TextView tvSelect;
    @BindView(R.id.ll_title)
    protected RelativeLayout ll_title;

    @BindView(R.id.iv_brand)
    protected ImageView ivBrand;
    @BindView(R.id.iv_arrow)
    protected ImageView iv_arrow;
    @BindView(R.id.tv_store_name)
    protected PhilExpandableTextView tvStoreNames;



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
    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;
    @BindView(R.id.iv_no_data)
    protected ImageView iv_no_data;

    protected TimePickerView pvCustomTime;

    private  boolean isStartDate;

    protected String startDate;
    protected String endDate;

    private int pageNumber = 1;
    private int pageSize = 10;
    private String campaignName="";
    private String salesTimeSta;
    private String salesTimesEnd;
    public MemCardEmploeeSaleReportListAdapter listAdapter;
    protected List<TOrder> orderList = new ArrayList<TOrder>();

    private boolean isSelectDate = false;
    private String userId ;
    private String storeName ="";
    public List<Store> storeList  = new ArrayList<>();
    public String brandLogo;
    protected PopWindow popWindow;
    private boolean isClicked = true;
    protected  CampaignSelectStoreAdapter myAdapter;
    private int brandPostion = 0;

    private String storeSelectType = "A";
    private String storeStr = "";

    //订单类型 购卡M 拼团G  商城商品P
    protected String orderType = "M";

    public List<MoreStoreBean.BrandStoreBean> branList = new ArrayList<>();
    private String title;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_mem_card_emploee_sale_report;
    }

    @Override
    protected void initView() {

        DaggerFinancialAffairsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .financialAffairsModule(new FinancialAffairsModule(this))
                .build()
                .inject(this);

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
        orderType = getIntent().getStringExtra("orderType");

        if("M".equals(orderType)){
            title = "会员卡员工销售报表";
        }else if("G".equals(orderType)){
            title = "拼团员工销售报表";
        }else if("P".equals(orderType)){
            title = "商品卡员工销售报表";
        }

        tvTitle.setText(title);
        brandLogo =SPUtils.getInstance().getString("brandLogo");
        if(brandLogo!=null){
            setBrandLogo();
        }
    }

    private void setBrandLogo(){
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_banner_default)
                .error(R.drawable.ic_banner_default)
                .fallback(R.drawable.ic_banner_default);

        Glide.with(this).load(brandLogo).apply(requestOptions).into(ivBrand);
    }

    @Override
    protected void initData() {
        if("A".equals(storeSelectType)){
            tvStoreNames.setText("#全部店铺");
        }

        iv_arrow.setVisibility(View.GONE);
        userId = SPUtils.getInstance().getString("userId");
        startDate = tv_start_time.getText().toString( ).trim();
        endDate = tv_end_time.getText().toString().trim();
        salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-"):"";
        salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-"):"";
//        storeId = SPUtils.getInstance().getString("storeId");

        listAdapter = new MemCardEmploeeSaleReportListAdapter(MemCardEmploeeSaleReportActivity.this, orderList);
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

        presenter.selectSalesByEmployee(MemCardEmploeeSaleReportActivity.this, userId, salesTimeSta,salesTimesEnd,"",storeStr,storeSelectType,orderType, pageNumber, pageSize);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MemCardEmploeeSaleReportActivity.this, MemCardEmploeeSaleReportDetailActivity.class);
                if (orderList != null && orderList.size() > 0) {
                    TOrder order = orderList.get(position);
                    intent.putExtra("orderId",order.getOrderId()+"");
                    startDate = tv_start_time.getText().toString( ).trim();
                    endDate = tv_end_time.getText().toString().trim();
                    intent.putExtra("storeSelectType",storeSelectType);
                    intent.putExtra("orderType",orderType);
                    intent.putExtra("salesTimeSta",startDate);
                    intent.putExtra("salesTimesEnd",endDate);
                    intent.putExtra("storeStr",storeStr);
                    intent.putExtra("order",order);

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
//                            presenter.doQueryGroupBuy(PtOrderActivity.this, campaignName, salesTimeSta,salesTimesEnd, pageSize, pageNumber,storeId,true);
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
//                            presenter.doQueryGroupBuy(PtOrderActivity.this, campaignName, salesTimeSta,salesTimesEnd, pageSize, pageNumber,storeId,true);
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


    @OnClick({R.id.iv_back,R.id.btn_day,R.id.btn_week,R.id.btn_month,R.id.ll_start,R.id.ll_end,R.id.tv_select_shop})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.finishActivity(MemCardEmploeeSaleReportActivity.class,
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
//                orderList.clear();
//                presenter.selectToCounByOrder(MemberCardStreamActivity.this, userId, salesTimeSta,salesTimesEnd,storeId, pageSize, pageNumber,true);
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


                isSelectDate = false;
                pageNumber = 1;
                orderList.clear();
                presenter.selectSalesByEmployee(MemCardEmploeeSaleReportActivity.this, userId, salesTimeSta,salesTimesEnd,"",storeStr,storeSelectType,orderType, pageNumber, pageSize);
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
                presenter.selectSalesByEmployee(MemCardEmploeeSaleReportActivity.this, userId, salesTimeSta,salesTimesEnd,"",storeStr,storeSelectType,orderType, pageNumber, pageSize);
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

        ActivityUtils.finishActivity(MemCardEmploeeSaleReportActivity.class,
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
        presenter.selectSalesByEmployee(MemCardEmploeeSaleReportActivity.this, userId, salesTimeSta,salesTimesEnd,"",storeStr,storeSelectType,orderType, pageNumber, pageSize);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.selectSalesByEmployee(MemCardEmploeeSaleReportActivity.this, userId, salesTimeSta,salesTimesEnd,"",storeStr,storeSelectType,orderType, pageNumber, pageSize);
    }

    //选择多个店铺
    protected void  showPopView(Context context){


        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_campaign_select_more_store,null);
        popWindow= new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopDown)
                .setView(contentView)
                .show(tvTitle);


        RelativeLayout rlAllStore = contentView.findViewById(R.id.rl_all);
        CheckBox cbAllStore = contentView.findViewById(R.id.cb__all);
        TextView tvAllStore =contentView.findViewById(R.id.tv_all);

        RelativeLayout rlSelfStore = contentView.findViewById(R.id.rl_self_bus);
        CheckBox cbSelfStore = contentView.findViewById(R.id.cb_self_bus);
        TextView tvSelfStore =contentView.findViewById(R.id.tv_self_bus);

        RelativeLayout rlJoinStore = contentView.findViewById(R.id.rl_all_join);
        CheckBox cbJoinStore = contentView.findViewById(R.id.cb_all_join);
        TextView tvJoinStore =contentView.findViewById(R.id.tv_all_join);

        ListView lv_left = contentView.findViewById(R.id.lv_menu);
        ListView lv_right = contentView.findViewById(R.id.lv_home);
        Button reset = contentView.findViewById(R.id.reset);
        Button confirm = contentView.findViewById(R.id.confirm);

        rlAllStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                StringBuilder storeIdSb = new StringBuilder();
                StringBuilder storeNameSb = new StringBuilder();
                String storeIdArr = "";
                if(storeList!=null&& storeList.size()>0){
                    for (Store store:storeList){
                        store.setChecked(false);
                        String storeId = store.getStoreId();
                        String storeName = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";

                        storeIdSb.append(storeId).append("-");
                        storeNameSb.append(storeName);

                    }

                    myAdapter.notifyDataSetChanged();
                    if(storeIdSb.toString()!=null && !"".equals(storeIdSb.toString())){
                        storeIdArr = storeIdSb.substring(0,storeIdSb.length() - 1);
                    }
                    storeStr = "";
                    storeName = "#全部店铺";
                    tvStoreNames.setText(storeNameSb.toString());
                    storeSelectType = "A";


                }
                cbAllStore.setChecked(true);
                cbAllStore.setButtonDrawable(R.drawable.ic_correct);
                tvAllStore.setTextColor(getResources().getColor(R.color.font_color_blue));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));
            }
        });

        rlSelfStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder storeIdSb = new StringBuilder();
                StringBuilder storeNameSb = new StringBuilder();

                String storeIdArr = "";
                if(storeList!=null&& storeList.size()>0){
                    for (Store store:storeList){
                        store.setChecked(false);
                        String storeId = store.getStoreId();
                        String storeName = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                        storeIdSb.append(storeId).append("-");
                        storeNameSb.append(storeName);

                    }

                    myAdapter.notifyDataSetChanged();
                    if(storeIdSb!=null ||!"".equals(storeIdSb)){
                        storeIdArr = storeIdSb.substring(0,storeIdSb.length() - 1);

                    }

                    storeStr = "";
                    storeName = "#全部自营";
                    storeSelectType = "D";


                }
                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(true);
                cbSelfStore.setButtonDrawable(R.drawable.ic_correct);
                tvSelfStore.setTextColor(getResources().getColor(R.color.font_color_blue));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));
            }
        });
        rlJoinStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                StringBuilder storeIdSb = new StringBuilder();
                StringBuilder storeNameSb = new StringBuilder();

                String storeIdArr = "";
                if(storeList!=null&& storeList.size()>0){
                    for (Store store:storeList){

                        store.setChecked(false);
                        String storeId = store.getStoreId();
                        String storeName = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                        storeIdSb.append(storeId).append("-");
                        storeNameSb.append(storeName);

                    }

                    if(storeIdSb!=null ||!"".equals(storeIdSb)){
                        storeIdArr = storeIdSb.substring(0,storeIdSb.length() - 1);

                    }
                    storeSelectType  = "J";
                    storeName = "#全部加盟";
                    storeStr = "";
                    myAdapter.notifyDataSetChanged();



                }

                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(true);
                cbJoinStore.setButtonDrawable(R.drawable.ic_correct);
                tvJoinStore.setTextColor(getResources().getColor(R.color.font_color_blue));
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                //自定义默认不选中
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
                StringBuilder storeNameSb2 = new StringBuilder();
                StringBuilder storeIdSb = new StringBuilder();
                String storeIdArr = "";
                String storeNameArr = "";
                if(storeList!=null&& storeList.size()>0){
                    if(storeSelectType != null && !"".equals(storeSelectType)){
                        if("A".equals(storeSelectType)||"D".equals(storeSelectType)||"J".equals(storeSelectType)){   //全部店铺  自营  加盟
                            //storeName 为全部店铺  自营  加盟

                            if(branList !=null && branList.size()>0){
                                brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                                SPUtils.getInstance().put("brandLogo",brandLogo);
                                if(brandLogo!=null){
                                    setBrandLogo();
                                }
                            }

                            tvStoreNames.setText(storeName);


                        }else if("H".equals(storeSelectType)){  //H为自定义店铺
                            for (Store store: storeList){
                                if(store!=null){
                                    if(store.isChecked()){
                                        String name = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                        storeNameSb.append(name);
                                        storeIdSb.append(store.getStoreId()).append("-");
                                    }

                                }
                            }

                            String storeIdA= storeIdSb.toString();
                            if(storeIdA!=null && !"".equals(storeIdA)){
                                storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);

                                storeName = storeNameSb.toString();
                            }else {
                                storeName = "#全部店铺";
                                storeSelectType = "A";
                            }
                            storeStr = storeIdArr;

                            tvStoreNames.setText(storeNameSb.toString());
                            if(branList !=null && branList.size()>0){
                                brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                                SPUtils.getInstance().put("brandLogo",brandLogo);
                                if(brandLogo!=null){
                                    setBrandLogo();
                                }
                            }

                            SPUtils.getInstance().put("brandLogoPt",brandLogo);
                        }
                        popWindow.dismiss();
                    }else {
                        storeSelectType = "H";
                        for (Store store: storeList){
                            if(store!=null){
                                if(store.isChecked()){
                                    String name = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                    storeNameSb.append(name);
                                    storeIdSb.append(store.getStoreId()).append("-");
                                }

                            }
                        }

                        String storeIdA= storeIdSb.toString();
                        if(storeIdA!=null && !"".equals(storeIdA)){
                            storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                            storeName = storeNameSb.toString();
                            storeSelectType = "";
                        }else {
                            storeIdArr = "";
                            storeName = "#全部店铺";
                            storeSelectType = "A";
                        }
                        storeStr = storeIdArr;

                        tvStoreNames.setText(storeName);
                        storeSelectType = "H";
                        if(branList !=null && branList.size()>0){
                            brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                            if(brandLogo!=null){
                                setBrandLogo();
                            }
                        }


                        popWindow.dismiss();
                    }

                }
            }
        });


        MoreBrandListAdapter brandListAdapter = new MoreBrandListAdapter(this,branList);
        lv_left.setAdapter(brandListAdapter);

        if(brandPostion == -1){
            brandPostion = 0;
        }
        brandListAdapter.setSelectItem(brandPostion);

        myAdapter = new CampaignSelectStoreAdapter(context);
        int selectItem = brandListAdapter.getSelectItem();
        if(branList!=null&&branList.size()>0){
            storeList = branList.get(selectItem).getStoreList();
            brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(selectItem).getBrandLogo();
            SPUtils.getInstance().put("brandLogoGive",brandLogo);
            myAdapter.setDatas(storeList);
        }

        lv_right.setAdapter(myAdapter);
        //判断默认选择店铺
        String storeNameStr = tvStoreNames.getText().toString().trim();

//        storeIdCouponType = SPUtils.getInstance().getString("storeIdCouponType");

        if(storeSelectType!=null && !"".equals(storeSelectType)){
            if("A".equals(storeSelectType)){

                cbAllStore.setChecked(true);
                cbAllStore.setButtonDrawable(R.drawable.ic_correct);
                tvAllStore.setTextColor(getResources().getColor(R.color.font_color_blue));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));
                if(storeList!=null&& storeList.size()>0){  //自定义默认不选中
                    for (Store store:storeList){
                        if(store!=null){
                            store.setChecked(false);
                        }

                    }
                    myAdapter.notifyDataSetChanged();

                }
                storeStr = "";

            }else if("D".equals(storeSelectType)){

                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(true);
                cbSelfStore.setButtonDrawable(R.drawable.ic_correct);
                tvSelfStore.setTextColor(getResources().getColor(R.color.font_color_blue));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));
                if(storeList!=null&& storeList.size()>0){  //自定义默认不选中
                    for (Store store:storeList){
                        if(store!=null){
                            store.setChecked(false);
                        }

                    }
                    myAdapter.notifyDataSetChanged();

                }
                storeStr = "";
            }else if("J".equals(storeSelectType)){
                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(true);
                cbJoinStore.setButtonDrawable(R.drawable.ic_correct);
                tvJoinStore.setTextColor(getResources().getColor(R.color.font_color_blue));
                if(storeList!=null&& storeList.size()>0){  //自定义默认不选中
                    for (Store store:storeList){
                        if(store!=null){
                            store.setChecked(false);
                        }

                    }
                    myAdapter.notifyDataSetChanged();

                }
                storeStr = "";
            }else if("H".equals(storeSelectType)){
                //分类默认不选中，自定义选中
                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));
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
            }

        }else {
            //分类默认不选中，自定义选中
            cbAllStore.setChecked(false);
            cbAllStore.setButtonDrawable(R.color.white);
            tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

            cbSelfStore.setChecked(false);
            cbSelfStore.setButtonDrawable(R.color.white);
            tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

            cbJoinStore.setChecked(false);
            cbJoinStore.setButtonDrawable(R.color.white);
            tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));
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
        }



        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(branList!=null&&branList.size()>0){
                    if(branList.get(position)!=null){
                        brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(position).getBrandLogo();


                        SPUtils.getInstance().put("brandLogoPt",brandLogo);
                        SPUtils.getInstance().put("brandPositionPt",brandPostion);
                        storeList = branList.get(position).getStoreList();
                        brandPostion = position;
                        brandListAdapter.setSelectItem(position);
                        brandListAdapter.notifyDataSetInvalidated();
                        myAdapter = new CampaignSelectStoreAdapter(context);
                        myAdapter.setDatas(storeList);
                        lv_right.setAdapter(myAdapter);
                    }
                }

            }
        });


        myAdapter.notifyDataSetChanged();
        lv_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(currentNum == -1){ //选中
                    if(storeList.get(position).isChecked()){
                        storeList.get(position).setChecked(false);
                    }else {
                        storeList.get(position).setChecked(true);
                    }

                    currentNum = position;
                }else if(currentNum == position){ //同一个item选中变未选中
                    storeList.get(position).setChecked(false);
                    currentNum = -1;
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的

                    if(storeList.get(position).isChecked()){
                        storeList.get(position).setChecked(false);
                    }else {
                        storeList.get(position).setChecked(true);
                    }
                    currentNum = position;
                }


                myAdapter.notifyDataSetChanged();
                storeSelectType = "H";
                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

            }
        });
    }
}
