package com.zwx.scan.app.feature.shop;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
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
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.modulemore.ModuleMoreListActivity;
import com.zwx.scan.app.feature.verification.ProductVerificationActivity;
import com.zwx.scan.app.feature.verificationrecord.VerificationRecordActivity;
import com.zwx.scan.app.feature.verificationrecord.VerificationRecordDetailActivity;
import com.zwx.scan.app.widget.MyListView;
import com.zwx.scan.app.widget.PhilExpandableTextView;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ProductVerificationRecordActivity extends BaseActivity<ShopContract.Presenter> implements ShopContract.View ,View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ScrollView>  {

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_brand)
    protected ImageView iv_brand;
    @BindView(R.id.tv_store_name)
    protected PhilExpandableTextView tv_store_name;



    @BindView(R.id.tv_product_verification_total)
    protected TextView tv_product_verification_total;
    @BindView(R.id.tv_sale_total)
    protected TextView tv_sale_total;
    @BindView(R.id.tv_red_packge_amt_total)
    protected TextView tv_red_packge_amt_total;

    @BindView(R.id.tv_pay_amt_total)
    protected TextView tv_pay_amt_total;

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

    @BindView(R.id.tv_time)
    protected TextView tv_time;

    @BindView(R.id.list_view)
    public MyListView listView;

    @BindView(R.id.ptr)
    protected PullToRefreshScrollView ptr;

    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;
    @BindView(R.id.ll_detail)
    protected LinearLayout ll_detail;

  /*  @BindView(R.id.rv_list)
    public RecyclerView rvList;

    @BindView(R.id.ptr)
    protected PtrClassicFrameLayout ptr;*/


    private String storeId;
    private String storeName;
    private String brandLogo;
    private String userId;
    private String memberTel = "";
    private int pageNumber = 1;
    private int pageSize = 10;

    protected TimePickerView pvCustomTime;

    private  boolean isStartDate;

    protected String startDate;
    protected String endDate;
    private String salesTimeSta;
    private String salesTimesEnd;
    private boolean isSelectDate = false;

    public ProductVerificationRecordListAdapter listAdapter;
//    public RecyclerAdapterWithHF mAdapter;
    protected List<TOrderObject> orderList = new ArrayList<TOrderObject>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_verification_record;
    }

    @Override
    protected void initView() {

        DaggerShopComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .shopModule(new ShopModule(this))
                .build()
                .inject(this);
        tvTitle.setText("商品核销记录");

        //默认本周
        btn_day.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
        btn_day.setTextColor(getResources().getColor(R.color.color_gray_deep));

        btn_week.setBackground(getResources().getDrawable(R.drawable.ic_time_selected));
        btn_week.setTextColor(getResources().getColor(R.color.btn_color_blue));

        btn_month.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
        btn_month.setTextColor(getResources().getColor(R.color.color_gray_deep));

        tv_start_time.setText(TimeUtils.getReqDate(TimeUtils.getStartWeekDate()).replace("-","."));
        tv_end_time.setText(TimeUtils.getReqDate(TimeUtils.getEndWeekDate()).replace("-","."));

        tv_time.setText(TimeUtils.getReqDate(TimeUtils.getStartWeekDate()).replace("-",".") +" - "+TimeUtils.getReqDate(TimeUtils.getEndWeekDate()).replace("-","."));

        initDatePicker();

        storeId = SPUtils.getInstance().getString("storeId");
        storeName = SPUtils.getInstance().getString("storeName");
        brandLogo = SPUtils.getInstance().getString("brandLogo");
        tv_store_name.setText("#"+storeName);
        setBrandLogo();

    }

    private void setBrandLogo(){
        RoundedCorners roundedCorners= new RoundedCorners(8);

        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_logo_default)
                .error(R.drawable.ic_logo_default)
                .fallback(R.drawable.ic_logo_default);

        Glide.with(this).load(brandLogo).apply(requestOptions).into(iv_brand);
    }
    @Override
    protected void initData() {

        startDate = tv_start_time.getText().toString( ).trim();
        endDate = tv_end_time.getText().toString().trim();
        salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-"):"";
        salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-"):"";
        userId = SPUtils.getInstance().getString("userId");
        listAdapter = new ProductVerificationRecordListAdapter(ProductVerificationRecordActivity.this, orderList);
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
        presenter.countVerification(ProductVerificationRecordActivity.this,storeId,salesTimeSta,salesTimesEnd,pageNumber, pageSize,true);
      /*  listAdapter = new ProductVerificationRecordAdapter(ProductVerificationRecordActivity.this, orderList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new SpacesItemDecoration(20));
        mAdapter = new RecyclerAdapterWithHF(listAdapter);
        rvList.setAdapter(mAdapter);
        presenter.countVerification(ProductVerificationRecordActivity.this,storeId , salesTimeSta,salesTimesEnd,pageNumber, pageSize,true);

*/
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
                    salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-"):"";
                    salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-"):"";
//                    salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-")+" "+ DateUtils.formatTime(System.currentTimeMillis()):"";
//
//                    salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-")+" "+ DateUtils.formatTime(System.currentTimeMillis()):"";

                    if(salesTimeSta != null && !"".equals(salesTimeSta)){
                        if(salesTimesEnd != null && !"".equals(salesTimesEnd)){
                            pageNumber = 1;
                            presenter.countVerification(ProductVerificationRecordActivity.this,storeId , salesTimeSta,salesTimesEnd,pageNumber, pageSize,true);


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
                            pageNumber = 1;
                            presenter.countVerification(ProductVerificationRecordActivity.this,storeId , salesTimeSta,salesTimesEnd,pageNumber, pageSize,true);
                        }
                    }
                }
            }
        });
    }

    @OnClick({R.id.iv_back,R.id.btn_day,R.id.btn_week,R.id.btn_month,R.id.ll_start,R.id.ll_end,R.id.ll_detail})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.finishActivity(ProductVerificationRecordActivity.class,
                        R.anim.slide_in_left,R.anim.slide_out_right);
//                ActivityUtils.startActivity(ModuleMoreListActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
//                ActivityUtils.finishActivity(ProductVerificationRecordActivity.class);
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
                tv_time.setText(startDate +" - "+ endDate);
                isSelectDate = false;
                pageNumber = 1;
                orderList.clear();
                presenter.countVerification(ProductVerificationRecordActivity.this,storeId , salesTimeSta,salesTimesEnd,pageNumber, pageSize,true);
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
                tv_time.setText(startDate +" - "+ endDate);
                isSelectDate = false;
                pageNumber = 1;
                orderList.clear();
                presenter.countVerification(ProductVerificationRecordActivity.this,storeId,salesTimeSta,salesTimesEnd,pageNumber, pageSize,true);
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
                tv_time.setText(startDate +" - "+ endDate);
                isSelectDate = false;
                pageNumber = 1;
                orderList.clear();
                presenter.countVerification(ProductVerificationRecordActivity.this,storeId , salesTimeSta,salesTimesEnd,pageNumber, pageSize,true);
                break;
            case R.id.ll_detail:
                startDate = tv_start_time.getText().toString().trim();
                endDate = tv_end_time.getText().toString().trim();

                Intent intent = new Intent(ProductVerificationRecordActivity.this,ProductVerificationRecordDetailActivity.class);
                intent.putExtra("startDate",startDate);
                intent.putExtra("endDate",endDate);
                ActivityUtils.startActivity(intent,
                        R.anim.slide_in_right, R.anim.slide_out_left);
                break;

        }
    }


    @Override
    public void onBackPressed() {
//        ActivityUtils.startActivity(ModuleMoreListActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
        ActivityUtils.finishActivity(ProductVerificationRecordActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void setGrayBg(){
        btn_day.setBackground(getResources().getDrawable(R.drawable.shape_pt_order_btn_normal));
        btn_day.setTextColor(getResources().getColor(R.color.color_gray_deep));

        btn_week.setBackground(getResources().getDrawable(R.drawable.shape_pt_order_btn_normal));
        btn_week.setTextColor(getResources().getColor(R.color.color_gray_deep));

        btn_month.setBackground(getResources().getDrawable(R.drawable.shape_pt_order_btn_normal));
        btn_month.setTextColor(getResources().getColor(R.color.color_gray_deep));
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
                                            tv_time.setText( " - "+endDate);
                                            ToastUtils.showToast("开始时间不可大于结束时间！");
                                        }else {
                                            tv_time.setText(startDate + " - "+endDate);
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
                                            tv_time.setText(startDate + " - ");
                                            tv_end_time.setText("");
                                        }else {
                                            pvCustomTime.dismiss();
                                            tv_time.setText(startDate + " - "+endDate);
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
        presenter.countVerification(ProductVerificationRecordActivity.this,storeId,salesTimeSta,salesTimesEnd,pageNumber, pageSize,true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.countVerification(ProductVerificationRecordActivity.this,storeId,salesTimeSta,salesTimesEnd,pageNumber, pageSize,false);
    }
}
