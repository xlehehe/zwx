package com.zwx.scan.app.feature.verificationrecord;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.MemCoupon;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.verification.DaggerVerificationComponent;
import com.zwx.scan.app.feature.verification.VerificationContract;
import com.zwx.scan.app.feature.verification.VerificationModule;
import com.zwx.scan.app.widget.PhilExpandableTextView;
import com.zwx.scan.app.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class VerificationRecordDetailActivity extends BaseActivity<VerificationContract.Presenter> implements VerificationContract.View,View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ScrollView>{

    @BindView(R.id.iv_back)
    protected ImageView ivBack;


    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.tv_select_shop)
    protected TextView tvSelect;

    @BindView(R.id.iv_brand)
    protected ImageView ivBrand;

    @BindView(R.id.tv_store_name)
    protected PhilExpandableTextView tvStoreName;

    @BindView(R.id.iv_arrow)
    protected ImageView iv_arrow;

    @BindView(R.id.rv_list)
    public RecyclerView rvList;

    @BindView(R.id.ptr)
    public PullToRefreshScrollView ptr;

    @BindView(R.id.ll_no_data)
    public LinearLayout ll_no_data;

    @BindView(R.id.tv_no_data)
    public TextView tv_no_data;


    public List<MemCoupon> memCouponList = new ArrayList<>();
    public RecyclerDetailAdapter adapter = null;

//    public RecyclerAdapterWithHF mAdapter;

/*    @BindView(R.id.ll_no_data)
    public LinearLayout ll_no_data;

    @BindView(R.id.iv_no_data)
    public ImageView iv_no_data;

    @BindView(R.id.tv_no_data)
    public TextView tv_no_data;*/

    private int pageNumber = 1;
    private int pageSize = 10;
    private  String storeId = "";
    public  Boolean isRereshAndMore = true;
    public  String date = "day";
    private String storeName ;
    private String brandLogo;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_verification_record_detail;
    }

    @Override
    protected void initView() {


        DaggerVerificationComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .verificationModule(new VerificationModule(this))
                .build()
                .inject(this);

//        initPtr();
    }

    @Override
    protected void initData() {


         storeId = SPUtils.getInstance().getString("storeId");
        storeName = SPUtils.getInstance().getString("storeName");
        brandLogo = SPUtils.getInstance().getString("brandLogo");
         date = getIntent().getStringExtra("date");
         String dateStr = "今日";
         if("day".equals(date)){
             dateStr = "今日";
         }else if("week".equals(date)){
             dateStr = "本周";
         }else if("month".equals(date)){
             dateStr = "本月";
         }
        tvTitle.setText(dateStr+"核销详情记录");
        tvStoreName.setText("#"+storeName);
        setBrandLogo();




        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new RecycleViewDivider( LinearLayoutManager.VERTICAL, 20, getResources().getColor(R.color.divide_gray_color)));

        rvList.setHasFixedSize(true);
        rvList.setNestedScrollingEnabled(false);
        adapter = new RecyclerDetailAdapter(this,memCouponList);
        rvList.setAdapter(adapter);
//        mAdapter = new RecyclerAdapterWithHF(recordDetailAdapter);
//        recyclerView.setAdapter(mAdapter);

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
        rvList.setVisibility(View.GONE);
        ll_no_data.setVisibility(View.VISIBLE);
        presenter.queryVerificationDetail(this,storeId, pageSize, pageNumber,date,isRereshAndMore);


      /*  segmentControl.setSelectedIndex(0);
        segmentControl.setOnSegmentChangedListener(new SegmentControl.OnSegmentChangedListener() {
            @Override
            public void onSegmentChanged(int newSelectedIndex) {
                if(newSelectedIndex == 0) {
                    date = "day";
                }else if(newSelectedIndex == 1) {
                    date = "week";
                }if(newSelectedIndex == 2){
                    date = "month";
                }

                presenter.queryVerificationDetail(VerificationRecordDetailActivity.this,storeId, pageSize, pageNumber,date,isRereshAndMore);

            }
        });*/
    }
    private void setBrandLogo(){
        RoundedCorners roundedCorners= new RoundedCorners(8);

        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_logo_default)
                .error(R.drawable.ic_logo_default)
                .fallback(R.drawable.ic_logo_default);

        Glide.with(this).load(brandLogo).apply(requestOptions).into(ivBrand);
    }


    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.finishActivity(VerificationRecordDetailActivity.class,
                        R.anim.slide_in_left,R.anim.slide_out_right);
                break;
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(VerificationRecordDetailActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        isRereshAndMore = true;
        pageNumber = 1;
        presenter.queryVerificationDetail(VerificationRecordDetailActivity.this,storeId,pageSize,pageNumber,date,isRereshAndMore);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        isRereshAndMore = false;
        pageNumber++;
        presenter.queryVerificationDetail(VerificationRecordDetailActivity.this,storeId,pageSize,pageNumber,date,isRereshAndMore);
    }
/*private  void initPtr(){
        ptrClassicFrameLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh(true);
            }
        }, 150);


        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNumber = 1;
                isRereshAndMore = true;
               presenter.queryVerificationDetail(VerificationRecordDetailActivity.this,storeId,pageSize,pageNumber,date,isRereshAndMore);
//                ptrClassicFrameLayout.refreshComplete();
//                ptrClassicFrameLayout.setLoadMoreEnable(true);
            }
        });



        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNumber++;
                isRereshAndMore = false;
                presenter.queryVerificationDetail(VerificationRecordDetailActivity.this,storeId,pageSize,pageNumber,date,isRereshAndMore);

            }
        });
    }*/

   /* public  void setRefresh(){
        if(isRereshAndMore = true){
            ptrClassicFrameLayout.refreshComplete();
            ptrClassicFrameLayout.setLoadMoreEnable(true);
        }else {
            ptrClassicFrameLayout.loadMoreComplete(true);
        }
    }*/

    /*public class RecyclerDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<MemCoupon> data = new ArrayList<>();
        private LayoutInflater inflater;

        public RecyclerDetailAdapter(Context context, List<MemCoupon> data) {
            super();
            inflater = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public int getItemCount() {
            if(data.size() == 0){
                return 0;
            }else {
                return data.size();
            }

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            if(data.size() == 0){
                return;
            }
            ChildViewHolder holder = (ChildViewHolder) viewHolder;
            MemCoupon memCoupon = data.get(position);

            BigDecimal amt = memCoupon.getConsumeAmt();
            String consume = "0.00";
            if(amt == null &&amt.doubleValue() ==0.0){

            }else {
                consume = new DecimalFormat("##.##").format(amt.setScale(2,BigDecimal.ROUND_DOWN).doubleValue()).toString();
            }

            String total =  memCoupon.getTotal() != 0 ? memCoupon.getTotal()+"":"-";


            holder.tv_member_tel.setText(memCoupon.getMemberTel()!=null?memCoupon.getMemberTel():"");
             holder.tv_use_count.setText("使用人数："+total);
            holder.tv_coupon_name.setText(memCoupon.getCouponName());
            holder.tv_consume_time.setText(memCoupon.getConsumeTime());
            holder.tv_money .setText("￥"+memCoupon.getConsumeAmt()!=null?memCoupon.getConsumeAmt().setScale(2,BigDecimal.ROUND_HALF_UP).toString()+"":"0.00");

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
            View view = inflater.inflate(R.layout.item_verification_record_detail_list, null);
            return new ChildViewHolder(view);
        }

    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_member_tel;

        public TextView tv_use_count;
        public TextView tv_coupon_name;
        public TextView tv_consume_time;
        public TextView tv_money;
        *//*  holder.setText(R.id.tv_member_tel, data.getMemberTel()!=null?data.getMemberTel():"")
                .setText(R.id.tv_use_count, data.getTotal() != 0 ? data.getTotal()+"":"-")
                .setText(R.id.tv_coupon_name,data.getCouponName())
                .setText(R.id.tv_consume_time,data.getConsumeTime())
                .setText(R.id.tv_money,"￥"+data.getConsumeAmt()!=null?data.getConsumeAmt().setScale(2,BigDecimal.ROUND_HALF_UP).toString()+"":"0.00")
                .setCommonClickListener(commonClickListener);*//*

        public ChildViewHolder(View view) {
            super(view);
            tv_member_tel = (TextView) view.findViewById(R.id.tv_member_tel);
            tv_use_count = (TextView) view.findViewById(R.id.tv_use_count);
            tv_coupon_name = (TextView) view.findViewById(R.id.tv_coupon_name);
            tv_consume_time = (TextView) view.findViewById(R.id.tv_consume_time);
            tv_money = (TextView) view.findViewById(R.id.tv_money);
        }

    }*/


}
