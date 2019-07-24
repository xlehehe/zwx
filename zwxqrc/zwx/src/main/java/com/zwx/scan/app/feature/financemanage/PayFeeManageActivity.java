package com.zwx.scan.app.feature.financemanage;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.ReceiveFund;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.widget.MyListView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PayFeeManageActivity extends BaseActivity<FinanceContract.Presenter> implements FinanceContract.View,View.OnClickListener{

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.recycler_view_frame)
    protected PtrClassicFrameLayout ptr;

   /* @BindView(R.id.rv_list)
    protected RecyclerView rvList;*/

    @BindView(R.id.list_view)
    protected MyListView rvList;
    @BindView(R.id.tv_store_name)
    protected TextView tv_store_name;

    @BindView(R.id.tv_select_store)
    protected TextView tv_select_store;

    @BindView(R.id.btn_unpay)
    protected TextView btn_unpay;
    @BindView(R.id.cb_total)
    protected CheckBox cb_total;

    @BindView(R.id.btn_payed)
    protected TextView btn_payed;
    @BindView(R.id.rl_pay)
    protected RelativeLayout rl_pay;

    @BindView(R.id.tv_selected_count)
    protected TextView tv_selected_count;

    @BindView(R.id.btn_submit)
    protected Button btn_submit;
    private String userId;

    private int pageNumber = 1;
    private int pageSize = 10;

//    public PayFeeListAdapter listAdapter;
    public PayFeeListViewAdapter mAdapter;
//    public RecyclerAdapterWithHF mAdapter;

    public List<ReceiveFund> receiveFundList = new ArrayList<>();

    public List<ReceiveFund> selectReceiveFundList = new ArrayList<>();
    private  String couponType = "CPC";

    private  boolean isRefresh = true;
    private int selectNum = 0;

    private String isSelectAll = "YES";

//    private String isPayFee = "NO";

    public static List<Store> storeList = new ArrayList<>();
    protected  PayFeeSelectStoreAdapter selectStoreAdapter= null;

    private String storeId;
    private String status = "U";
    private String storeName;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_fee_manage;
    }

    @Override
    protected void initView() {


        tvTitle.setText("缴费管理");

        userId = SPUtils.getInstance().getString("userId");
        DaggerFinanceComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .financeModule(new FinanceModule(this))
                .build()
                .inject(this);
        initPtr();
    }

    @Override
    protected void initData() {
        storeId =SPUtils.getInstance().getString("storeId");

        storeName = SPUtils.getInstance().getString("storeName");
        tv_store_name.setText(storeName != null && !"".equals(storeName)?storeName:"");
        presenter.queryBrandAndStoreList(this,userId,true);
        btn_unpay.setBackground(getResources().getDrawable(R.drawable.shape_btn_blue_pressed));
        btn_unpay.setTextColor(getResources().getColor(R.color.white));
        btn_payed.setBackground(getResources().getDrawable(R.drawable.shape_text_normal));
        btn_payed.setTextColor(getResources().getColor(R.color.color_gray_deep));


//        listAdapter = new PayFeeListAdapter(this, receiveFundList,isPayFee);
//        rvList.setLayoutManager(new LinearLayoutManager(this));
//        rvList.addItemDecoration(new SpacesItemDecoration(20));
        mAdapter = new PayFeeListViewAdapter(this, receiveFundList);
//        mAdapter.changetShowCb(status);
//        mAdapter = new RecyclerAdapterWithHF(listAdapter);
        rvList.setAdapter(mAdapter);

        rvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if("U".equals(status)){
                    if(currentNum == -1){ //选中
                        if(receiveFundList.get(position).isChecked()){
                            receiveFundList.get(position).setChecked(false);
                            selectNum--;
                            if(selectNum  <= -1){
                                selectNum = 0;
                            }
                        }else {
                            receiveFundList.get(position).setChecked(true);
                            selectNum++;
                        }

                        currentNum = position;
                    }else if(currentNum == position){ //同一个item选中变未选中
                        receiveFundList.get(position).setChecked(false);
                        currentNum = -1;
                        selectNum--;
                        if(selectNum <= -1){
                            selectNum = 0;
                        }
                    }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的

                        if(receiveFundList.get(position).isChecked()){
                            receiveFundList.get(position).setChecked(false);
                            selectNum-- ;
                            if(selectNum  <= -1){
                                selectNum = 0;
                            }
                        }else {
                            receiveFundList.get(position).setChecked(true);

                            selectNum++;
                        }
                        currentNum = position;
                    }
                    mAdapter.notifyDataSetChanged();
                    double money = 0;
                    for (ReceiveFund receiveFund : receiveFundList){
                        if(receiveFund.isChecked()){
                            BigDecimal moneyStr = receiveFund.getMoney();
                            if(moneyStr!=null){
                                money += moneyStr.doubleValue();
                            }
                        }

                    }
                    if(money >0){
                        BigDecimal amt = new BigDecimal(money);
                        String consume = new DecimalFormat("0.00").format(amt.setScale(2,BigDecimal.ROUND_DOWN).doubleValue()).toString();
                        tv_selected_count.setText(consume);
                    }else {
                        tv_selected_count.setText("0");
                    }


                    btn_submit.setText("立即支付("+selectNum+")");
                }else {
                    Intent intent = new Intent(PayFeeManageActivity.this,PayFeeListDetailActivity.class);

                    intent.putExtra("status",status);
                    intent.putExtra("fundId",String.valueOf(receiveFundList.get(position).getFundId()));
                    ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                }
            }
        });


       /* rvList.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            int currentNum = -1;
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {


                if("NO".equals(isPayFee)){
                    if(currentNum == -1){ //选中
                        if(receiveFundList.get(position).getChecked()){
                            receiveFundList.get(position).setChecked(false);
                        }else {
                            receiveFundList.get(position).setChecked(true);
                        }

                        currentNum = position;
                    }else if(currentNum == position){ //同一个item选中变未选中
                        receiveFundList.get(position).setChecked(false);
                        currentNum = -1;
                    }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的

                        if(receiveFundList.get(position).getChecked()){
                            receiveFundList.get(position).setChecked(false);
                        }else {
                            receiveFundList.get(position).setChecked(true);
                        }
                        currentNum = position;
                    }

                    mAdapter.notifyDataSetChanged();
                    Intent intent = new Intent(PayFeeManageActivity.this,PayFeeListDetailActivity.class);

                    intent.putExtra("isPayFee",isPayFee);

                    ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                }else {
                    Intent intent = new Intent(PayFeeManageActivity.this,PayFeeListDetailActivity.class);

                    intent.putExtra("isPayFee",isPayFee);

                    ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                }




            }
        });*/

    }

    private void initReceiveFund(){
        ReceiveFund receiveFund = new ReceiveFund();
        BigDecimal  bigDecimal = new BigDecimal(122);
        receiveFund.setFundId(20183293724L);
        receiveFund.setChecked(false);
        receiveFund.setIsOverdue("1");
        receiveFund.setFactReceiveDate("2018.11.18");
        bigDecimal = new BigDecimal(122);
        receiveFund.setMoney(bigDecimal);
        receiveFundList.add(receiveFund);

        receiveFund = new ReceiveFund();
        receiveFund.setFundId(20183293724L);
        receiveFund.setChecked(false);
        receiveFund.setIsOverdue("1");
        receiveFund.setFactReceiveDate("2018.11.18");
        bigDecimal = new BigDecimal(122);
        receiveFund.setMoney(bigDecimal);
        receiveFundList.add(receiveFund);

        receiveFund = new ReceiveFund();
        receiveFund.setFundId(20183293724L);
        receiveFund.setChecked(false);
        receiveFund.setIsOverdue("0");
        receiveFund.setFactReceiveDate("2018.11.18");
        bigDecimal = new BigDecimal(122);
        receiveFund.setMoney(bigDecimal);
        receiveFundList.add(receiveFund);
    }
    protected   void  setRemoveUnchecked(ReceiveFund removeReceiveFund){

        if(selectReceiveFundList !=null && selectReceiveFundList.size()>0){
            for (int i = 0;i <selectReceiveFundList.size();i++){
                ReceiveFund receiveFundUn = selectReceiveFundList.get(i);
                if(removeReceiveFund!=null){
                    boolean unChecked = removeReceiveFund.isChecked();
                    if(!unChecked){
                        Long removeFundId = removeReceiveFund.getFundId();
                        Long removeFundId2 = receiveFundUn.getFundId();
                        if(removeFundId!=null){
                            if(removeFundId2!=null){
                                if(removeFundId.equals(removeFundId2)){
                                    selectReceiveFundList.remove(i);
                                    i--;
                                }
                            }
                        }
                    }


                }
            }
        }
    }

    @OnClick({R.id.iv_back,R.id.tv_select_store,R.id.btn_submit,R.id.btn_unpay,R.id.btn_payed,R.id.ll_cb_total})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(PayFeeManageActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.btn_submit:
               /* StringBuilder sb = new StringBuilder();
                for (int i = 0 ;i<receiveFundList.size();i++){
                    ReceiveFund receiveFund = receiveFundList.get(i);


                    if(receiveFund != null ){
                        if(receiveFund.isChecked()){
                            if(i == 0){
                                sb.append(receiveFund.getFundId());
                            }else {
                                sb.append(","+receiveFund.getFundId());
                            }
                        }
                    }
                }
                if(sb.toString() != null && !"".equals(sb.toString())){
                    String ids = sb.toString();
                    presenter.doQueryMoneyByIds(this,userId,storeId,ids);
                }*/
                ToastUtils.showShort("跳转支付接口回调页面");

                break;
            case R.id.btn_unpay:

                btn_unpay.setBackground(getResources().getDrawable(R.drawable.shape_btn_blue_pressed));
                btn_unpay.setTextColor(getResources().getColor(R.color.white));
                btn_payed.setBackground(getResources().getDrawable(R.drawable.shape_text_normal));
                btn_payed.setTextColor(getResources().getColor(R.color.color_gray_deep));
                rl_pay.setVisibility(View.VISIBLE);

                status = "U";
                mAdapter.changetShowCb(status);
                presenter.doQueryAllByStatus(PayFeeManageActivity.this,storeId,String.valueOf(pageNumber),String.valueOf(pageSize),status,true);
                break;
            case R.id.btn_payed:
                btn_payed.setBackground(getResources().getDrawable(R.drawable.shape_btn_blue_pressed));
                btn_payed.setTextColor(getResources().getColor(R.color.white));
                btn_unpay.setBackground(getResources().getDrawable(R.drawable.shape_text_normal));
                btn_unpay.setTextColor(getResources().getColor(R.color.color_gray_deep));
                rl_pay.setVisibility(View.GONE);
                status = "R";
                mAdapter.changetShowCb(status);
                presenter.doQueryAllByStatus(PayFeeManageActivity.this,storeId,String.valueOf(pageNumber),String.valueOf(pageSize),status,true);
                break;

            case R.id.tv_select_store:
                if(storeList != null && storeList.size()>0){
                    showPopListView();
                }else {
                    presenter.queryBrandAndStoreList(this,userId,false);
                }

                break;
            case R.id.ll_cb_total:
                int total = 0;
                double money = 0.00;
                if("YES".equals(isSelectAll)){
                    isSelectAll = "NO";
                    for (ReceiveFund receiveFund : receiveFundList){
                        BigDecimal moneyStr = receiveFund.getMoney();
                        if(moneyStr!=null){
                            money += moneyStr.doubleValue();
                        }
                        receiveFund.setChecked(true);
                    }

                    for (ReceiveFund receiveFund : receiveFundList){

                        if(receiveFund.isChecked()){
                            total++;
                        }

                    }
                    mAdapter.notifyDataSetChanged();
                    total = receiveFundList.size();
                    cb_total.setButtonDrawable(R.drawable.ic_blue_selected);
                    BigDecimal bigDecimal = new BigDecimal(money);
                    String moneyPr = new DecimalFormat("#.##").format(bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).doubleValue()).toString();
                    tv_selected_count.setText(moneyPr);
                    btn_submit.setText("立即支付("+total+")");
                }else if ("NO".equals(isSelectAll)){
                    isSelectAll = "YES";
                    for (ReceiveFund receiveFund : receiveFundList){
                        receiveFund.setChecked(false);

                    }
                    tv_selected_count.setText("0.00");
                    cb_total.setButtonDrawable(R.drawable.ic_gray_unselect);
                    btn_submit.setText("立即支付("+0+")");
                    mAdapter.notifyDataSetChanged();
                }

                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(PayFeeManageActivity.class,
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
                presenter.doQueryAllByStatus(PayFeeManageActivity.this,storeId,String.valueOf(pageNumber),String.valueOf(pageSize),status,true);
            }
        });


        ptr.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNumber++;
                presenter.doQueryAllByStatus(PayFeeManageActivity.this,storeId,String.valueOf(pageNumber),String.valueOf(pageSize),status,false);

            }
        });
    }


    protected    PopWindow popWindow = null;
    protected void showPopListView(){
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_list,null);
        //处理popWindow 显示内容
        handleListView(contentView);
        popWindow= new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopAlert)
                .setView(contentView)
//                .setPopWindowMargins(ScreenUtils.dip2px(this,30), ScreenUtils.dip2px(this,90), ScreenUtils.dip2px(this,30),ScreenUtils.dip2px(this,0))
                .show();
    }

    private void handleListView(View contentView){

        ListView listView = (ListView) contentView.findViewById(R.id.list_view);
        selectStoreAdapter = new PayFeeSelectStoreAdapter(this,storeList);
        listView.setAdapter(selectStoreAdapter);

        for (Store store : storeList){
            String storeIds = store.getStoreId();

            if(storeIds.equals(storeId)){
                store.setChecked(true);
            }
        }
        selectStoreAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Store store1 = storeList.get(position);

                String name = store1.getStoreName();
                tv_store_name.setText(name);



                if(currentNum == -1){ //选中
                    for(Store store : storeList){
                        store.setChecked(false);
                    }
                    storeList.get(position).setChecked(true);
                    storeId =  storeList.get(position).getStoreId();
                    pageNumber = 1;
                    presenter.doQueryAllByStatus(PayFeeManageActivity.this,storeId,String.valueOf(pageNumber),String.valueOf(pageSize),status,true);
                    currentNum = position;
                }else if(currentNum == position){ //同一个item选中变未选中

                     for(Store store : storeList){
                        store.setChecked(false);
                    }
                    currentNum = -1;
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的

                   for(Store store : storeList){
                        store.setChecked(false);
                    }
                    storeList.get(position).setChecked(true);
                   storeId =  storeList.get(position).getStoreId();
                    pageNumber = 1;
                    presenter.doQueryAllByStatus(PayFeeManageActivity.this,storeId,String.valueOf(pageNumber),String.valueOf(pageSize),status,true);
                    currentNum = position;
                }

                selectStoreAdapter.notifyDataSetChanged();
                popWindow.dismiss();
            }
        });

    }


}
