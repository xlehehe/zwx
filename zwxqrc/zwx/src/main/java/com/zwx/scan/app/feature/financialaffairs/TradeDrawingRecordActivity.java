package com.zwx.scan.app.feature.financialaffairs;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zwx.library.pickerview.picker.builder.TimePickerBuilder;
import com.zwx.library.pickerview.picker.listener.CustomListener;
import com.zwx.library.pickerview.picker.listener.OnTimeSelectListener;
import com.zwx.library.pickerview.picker.view.TimePickerView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.data.bean.TradeDrawingRecordResultBean;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.shop.ShopOrderActivity;
import com.zwx.scan.app.feature.shop.ShopOrderDetailActivity;
import com.zwx.scan.app.feature.shop.ShopOrderListViewAdapter;
import com.zwx.scan.app.widget.CustomEditText;
import com.zwx.scan.app.widget.MyListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/05/09
 * desc   :  交易提款记录
 * version: 1.0
 **/
public class TradeDrawingRecordActivity extends BaseActivity <FinancialAffairsContract.Presenter> implements FinancialAffairsContract.View,View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ScrollView> {
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
    protected PullToRefreshScrollView ptr;

    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;


    protected String userId;
    protected int pageNumber = 1;
    protected int pageSize = 10;

    protected TimePickerView pvCustomTime;

    private  boolean isStartDate;

    protected String startDate;
    protected String endDate;
    protected String salesTimeSta;
    protected String salesTimesEnd;
    private boolean isSelectDate = false;


    public DrawingRecordListAdapter listAdapter;


    protected List<TradeDrawingRecordResultBean.DrawingRecordBean> recordBeanList = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_trade_drawing_record;
    }

    @Override
    protected void initView() {
        DaggerFinancialAffairsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .financialAffairsModule(new FinancialAffairsModule(this))
                .build()
                .inject(this);
        tvTitle.setText("提现记录");

        //默认本周
        btn_day.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
        btn_day.setTextColor(getResources().getColor(R.color.color_gray_deep));

        btn_week.setBackground(getResources().getDrawable(R.drawable.ic_time_selected));
        btn_week.setTextColor(getResources().getColor(R.color.btn_color_blue));

        btn_month.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
        btn_month.setTextColor(getResources().getColor(R.color.color_gray_deep));

        tv_start_time.setText(TimeUtils.getReqDate(TimeUtils.getStartWeekDate()).replace("-","."));
        tv_end_time.setText(TimeUtils.getReqDate(TimeUtils.getEndWeekDate()).replace("-","."));


        setListener();
        initDatePicker();
    }

    @Override
    protected void initData() {


        userId = SPUtils.getInstance().getString("userId");
        startDate = tv_start_time.getText().toString( ).trim();
        endDate = tv_end_time.getText().toString().trim();
        salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-"):"";
        salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-"):"";

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

        listAdapter = new DrawingRecordListAdapter(this,recordBeanList);

        listView.setAdapter(listAdapter);

        presenter.selectTradeWithdrawalList(this,userId,salesTimeSta,salesTimesEnd,pageNumber,pageSize);



    }

    private void setGrayBg(){
        btn_day.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
        btn_day.setTextColor(getResources().getColor(R.color.color_gray_deep));

        btn_week.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
        btn_week.setTextColor(getResources().getColor(R.color.color_gray_deep));

        btn_month.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
        btn_month.setTextColor(getResources().getColor(R.color.color_gray_deep));
    }

    private  void setListener(){

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TradeDrawingRecordActivity.this, FinancialDrawingDetailActivity.class);
                if (recordBeanList != null && recordBeanList.size() > 0) {
                    TradeDrawingRecordResultBean.DrawingRecordBean recordBean = recordBeanList.get(position);
                    intent.putExtra("withdrawId",recordBean.getId()+"");
                    intent.putExtra("isList","YES");
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
                if(isSelectDate) {
                    setGrayBg();
                    startDate = tv_start_time.getText().toString().trim();
                    endDate = tv_end_time.getText().toString().trim();
                    salesTimeSta = startDate != null && startDate.length() > 0 ? startDate.replace(".", "-") : "";

                    salesTimesEnd = endDate != null && endDate.length() > 0 ? endDate.replace(".", "-") : "";
//                    orderList.clear();
                    if (salesTimeSta != null && !"".equals(salesTimeSta)) {
                        if (salesTimesEnd != null && !"".equals(salesTimesEnd)) {
                            pageNumber = 1;
                            recordBeanList.clear();
                            presenter.selectTradeWithdrawalList(TradeDrawingRecordActivity.this, userId, salesTimeSta,salesTimesEnd,pageNumber, pageSize);

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
                    salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-"):"";

                    salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-"):"";

                    if(salesTimeSta != null && !"".equals(salesTimeSta)){
                        if(salesTimesEnd != null && !"".equals(salesTimesEnd)){
                            recordBeanList.clear();
                            pageNumber = 1;
                            presenter.selectTradeWithdrawalList(TradeDrawingRecordActivity.this, userId, salesTimeSta,salesTimesEnd,pageNumber, pageSize);
                        }
                    }
                }

            }
        });

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {

        recordBeanList.clear();
        pageNumber = 1;
        presenter.selectTradeWithdrawalList(TradeDrawingRecordActivity.this, userId, salesTimeSta,salesTimesEnd,pageNumber, pageSize);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.selectTradeWithdrawalList(TradeDrawingRecordActivity.this, userId, salesTimeSta,salesTimesEnd,pageNumber, pageSize);
    }




    @OnClick({R.id.iv_back,R.id.btn_day,R.id.btn_week,R.id.btn_month,R.id.ll_start,R.id.ll_end})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.startActivity(TradeDrawingActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);

                finish();
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
                salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-"):"";
                isSelectDate = false;
                pageNumber = 1;
                recordBeanList.clear();
                presenter.selectTradeWithdrawalList(TradeDrawingRecordActivity.this, userId, salesTimeSta,salesTimesEnd,pageNumber, pageSize);
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
                recordBeanList.clear();
                presenter.selectTradeWithdrawalList(TradeDrawingRecordActivity.this, userId, salesTimeSta,salesTimesEnd,pageNumber, pageSize);
                break;

            case R.id.btn_month:
                btn_day.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
                btn_day.setTextColor(getResources().getColor(R.color.color_gray_deep));

                btn_week.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
                btn_week.setTextColor(getResources().getColor(R.color.color_gray_deep));

                btn_month.setBackground(getResources().getDrawable(R.drawable.ic_time_selected));
                btn_month.setTextColor(getResources().getColor(R.color.btn_color_blue));
                tv_start_time.setText(TimeUtils.getReqDate(TimeUtils.getFirstDayOfMonth(new Date())));
                tv_end_time.setText(TimeUtils.getReqDate(TimeUtils.getLastDayOfMonth(new Date())));
                startDate = tv_start_time.getText().toString().trim();
                endDate = tv_end_time.getText().toString().trim();
                salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-"):"";

                salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-"):"";
                isSelectDate = false;
                pageNumber = 1;
                recordBeanList.clear();
                presenter.selectTradeWithdrawalList(TradeDrawingRecordActivity.this, userId, salesTimeSta,salesTimesEnd,pageNumber, pageSize);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.startActivity(TradeDrawingActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
        finish();

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


}
