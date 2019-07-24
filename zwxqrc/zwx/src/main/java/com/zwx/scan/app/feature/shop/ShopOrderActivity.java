package com.zwx.scan.app.feature.shop;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.zwx.library.pickerview.picker.builder.TimePickerBuilder;
import com.zwx.library.pickerview.picker.listener.CustomListener;
import com.zwx.library.pickerview.picker.listener.OnTimeSelectListener;
import com.zwx.library.pickerview.picker.view.TimePickerView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.DateUtils;
import com.zwx.library.utils.KeyboardUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.CardQrcBean;
import com.zwx.scan.app.data.bean.Order;
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.member.MemberCardStreamActivity;
import com.zwx.scan.app.feature.member.MemberCardStreamAdapter;
import com.zwx.scan.app.feature.member.MemberCardStreamDetailActivity;
import com.zwx.scan.app.feature.member.MemberInfoListActivity;
import com.zwx.scan.app.feature.staffmanage.StaffEditActivity;
import com.zwx.scan.app.feature.verification.ProductVerificationActivity;
import com.zwx.scan.app.feature.verification.VerificationActivity;
import com.zwx.scan.app.utils.MaxTextLengthFilter;
import com.zwx.scan.app.widget.CustomEditText;
import com.zwx.scan.app.widget.MyListView;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/04/18
 * desc   : 商城订单管理
 * version: 1.0
 **/
public class ShopOrderActivity extends BaseActivity<ShopContract.Presenter> implements ShopContract.View ,View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ScrollView>   {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.edt_search)
    protected CustomEditText edtSearch;

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

/*    @BindView(R.id.rv_list)
    public RecyclerView rvList;
    @BindView(R.id.ptr)
    public PtrClassicFrameLayout ptr;*/

    @BindView(R.id.list_view)
    public MyListView listView;

    @BindView(R.id.ptr)
    protected PullToRefreshScrollView ptr;

    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;


    //订单编号
   protected EditText edt_order_id = null;

    protected String userId;
    protected String memberTel = "";
    protected int pageNumber = 1;
    protected int pageSize = 10;

    protected TimePickerView pvCustomTime;

    private  boolean isStartDate;

    protected String startDate;
    protected String endDate;
    protected String salesTimeSta;
    protected String salesTimesEnd;
    private boolean isSelectDate = false;


    public ShopOrderListViewAdapter listAdapter;
    public static List<TOrderObject> orderList = new ArrayList<TOrderObject>();

    protected static String orderId;

    protected String postCode;
    protected static String detailId;

    @Override
    protected void onResume() {
        super.onResume();

        if(orderList != null &&  orderList.size()>0){
            ll_no_data.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            listAdapter.notifyDataSetChanged();
            presenter.queryOrderDetailForApp(ShopOrderActivity.this,orderId);
        }else {
            ll_no_data.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            tv_no_data.setText("您现在没有订单记录哦！");
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_order;
    }

    @Override
    protected void initView() {
        DaggerShopComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .shopModule(new ShopModule(this))
                .build()
                .inject(this);

        EventBus.getDefault().register(this);

        tvTitle.setText("订单管理");
        //默认本周
        btn_day.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
        btn_day.setTextColor(getResources().getColor(R.color.color_gray_deep));

        btn_week.setBackground(getResources().getDrawable(R.drawable.ic_time_selected));
        btn_week.setTextColor(getResources().getColor(R.color.btn_color_blue));

        btn_month.setBackground(getResources().getDrawable(R.drawable.ic_time_unselect));
        btn_month.setTextColor(getResources().getColor(R.color.color_gray_deep));

        tv_start_time.setText(TimeUtils.getReqDate(TimeUtils.getStartWeekDate()).replace("-","."));
        tv_end_time.setText(TimeUtils.getReqDate(TimeUtils.getEndWeekDate()).replace("-","."));
        initDatePicker();
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber = 1;
        presenter.doQueryOrder(ShopOrderActivity.this, userId,memberTel, salesTimeSta,salesTimesEnd,pageNumber, pageSize,true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.doQueryOrder(ShopOrderActivity.this, userId,memberTel, salesTimeSta,salesTimesEnd,pageNumber, pageSize,false);

    }

    @Override
    protected void initData() {
        startDate = tv_start_time.getText().toString( ).trim();
        endDate = tv_end_time.getText().toString().trim();
        salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-"):"";
        salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-"):"";
        userId = SPUtils.getInstance().getString("userId");
        listAdapter = new ShopOrderListViewAdapter(ShopOrderActivity.this, orderList);
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
        if(orderList != null && orderList.size()>0){
            listAdapter.notifyDataSetChanged();
        }else {
            presenter.doQueryOrder(ShopOrderActivity.this, userId,memberTel, salesTimeSta,salesTimesEnd,pageNumber, pageSize,true);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShopOrderActivity.this, ShopOrderDetailActivity.class);
                if (orderList != null && orderList.size() > 0) {
                    TOrderObject order = orderList.get(position);
                    intent.putExtra("detailedId",order.getDetailedId()+"");
                    intent.putExtra("order",order);


                    if(order.getOrderId()!=null){
                        orderId = String.valueOf(order.getOrderId());
                    }
                    startDate = tv_start_time.getText().toString( ).trim();
                    endDate = tv_end_time.getText().toString().trim();
                    intent.putExtra("salesTimeSta",startDate);
                    intent.putExtra("salesTimesEnd",endDate);
//                    intent.putExtra("isDetail","YES");

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
                    orderList.clear();
                    if (salesTimeSta != null && !"".equals(salesTimeSta)) {
                        if (salesTimesEnd != null && !"".equals(salesTimesEnd)) {
                            pageNumber = 1;
                            presenter.doQueryOrder(ShopOrderActivity.this, userId, memberTel, salesTimeSta, salesTimesEnd, pageNumber, pageSize, true);


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
                            orderList.clear();
                            pageNumber = 1;
                            presenter.doQueryOrder(ShopOrderActivity.this, userId,memberTel, salesTimeSta,salesTimesEnd,pageNumber, pageSize,true);
                        }
                    }
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

                setGrayBg();
                startDate = tv_start_time.getText().toString().trim();
                endDate = tv_end_time.getText().toString().trim();
                salesTimeSta =  startDate!=null&&startDate.length()>0?startDate.replace(".","-"):"";

                salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-"):"";

                if(salesTimeSta != null && !"".equals(salesTimeSta)){
                    if(salesTimesEnd != null && !"".equals(salesTimesEnd)){
                        orderList.clear();
                        pageNumber = 1;
                        presenter.doQueryOrder(ShopOrderActivity.this, userId,memberTel, salesTimeSta,salesTimesEnd,pageNumber, pageSize,true);
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
    @OnClick({R.id.iv_back,R.id.btn_day,R.id.btn_week,R.id.btn_month,R.id.ll_start,R.id.ll_end})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.finishActivity(ShopOrderActivity.class,
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
                salesTimesEnd =  endDate!=null&&endDate.length()>0?endDate.replace(".","-"):"";
                isSelectDate = false;
                pageNumber = 1;
                orderList.clear();
                presenter.doQueryOrder(ShopOrderActivity.this, userId,memberTel, salesTimeSta,salesTimesEnd,pageNumber, pageSize,true);
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
                presenter.doQueryOrder(ShopOrderActivity.this, userId,memberTel, salesTimeSta,salesTimesEnd,pageNumber, pageSize,true);
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
                orderList.clear();
                presenter.doQueryOrder(ShopOrderActivity.this, userId,memberTel, salesTimeSta,salesTimesEnd,pageNumber, pageSize,true);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(ShopOrderActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    //EventBus 接收事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(ProductEventBus event) {
        if (event != null) {
            String operateFlag = event.getOperateFlag();

            if("listFlush".equals(operateFlag)){
                orderId = event.getProductId();
                if(orderList != null &&  orderList.size()>0){
                    ll_no_data.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    listAdapter.notifyDataSetChanged();
                    presenter.queryOrderDetailForApp(ShopOrderActivity.this,orderId);
                }else {
                    ll_no_data.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    tv_no_data.setText("您现在没有订单记录哦！");
                }
            }else {
                detailId = event.getProductId();
                showPost();
            }


        }
    }

    //确认邮寄弹窗
    protected void showPost() {
        View customView = View.inflate(this, R.layout.layout_goods_manage_post, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog  dialog = builder.create();
        dialog.setView(customView, 0, 0, 0, 0);
        //公司名称
        EditText edt_company_name = (EditText) customView.findViewById(R.id.edt_company_name);
        //统计文字个数
        TextView tv_company_name_num = (TextView) customView.findViewById(R.id.tv_company_name_num);
        //订单编号
        edt_order_id = (EditText) customView.findViewById(R.id.edt_order_id);

        LinearLayout ll_cancel = (LinearLayout) customView.findViewById(R.id.ll_cancel);

        edt_order_id.setText("");
        edt_company_name.setSelection(edt_company_name.getText().length());
        edt_company_name.setFilters(new InputFilter[]{new MaxTextLengthFilter(30)});

        edt_company_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                int remarkLenth = s.length();
                if(edt_company_name.getText().toString().trim().length()>0){
                    edt_company_name.setSelection(edt_company_name.getText().toString().trim().length());
                    tv_company_name_num.setText(remarkLenth+"/30");
                }

            }
        });
        ImageView iv_qrc = (ImageView) customView.findViewById(R.id.iv_qrc);
        iv_qrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopOrderActivity.this,ProductVerificationActivity.class);
                intent.putExtra("intentOrderListAndDetailFlag","YES1");
                intent.putExtra("title","快递订单扫码");
                ActivityUtils.startActivityForResult(ShopOrderActivity.this,intent,2222,R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        Button btn_use = (Button)customView.findViewById(R.id.confirmBtn);
        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                KeyboardUtils.showSoftInput(edt_company_name);
//                KeyboardUtils.showSoftInput(edt_order_id);
                String companyName  = edt_company_name.getText().toString().trim();
                String postCode = edt_order_id.getText().toString().trim();


                if(companyName == null || "".equals(companyName)){
                    ToastUtils.showShort("请输入快递公司名称");
                    return;
                }

                if(postCode == null || "".equals(postCode)){
                    ToastUtils.showShort("请输入快递单号");
                    return;
                }
                presenter.queryOrderConfirmPost(ShopOrderActivity.this,detailId,companyName,postCode);
                dialog.dismiss();

            }
        });
        Button cancelBtn = (Button)customView.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        boolean  isPull = false ;

        if(resultCode == 2222){
            if(requestCode == 2222){
                if(data != null){
                    postCode = data.getStringExtra("postCode"); //newStaffMemberId
                    if(edt_order_id != null){
                        edt_order_id.setText(postCode);
                    }
                }
            }
        }else {
            postCode = "";
        }
    }


}
