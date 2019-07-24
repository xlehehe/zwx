package com.zwx.scan.app.feature.member;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.zwx.library.utils.TextColorSizeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.data.bean.MemCoupon;
import com.zwx.scan.app.data.bean.MemberConsumeBean;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.personal.PersonalModule;
import com.zwx.scan.app.feature.staffmanage.DaggerStaffManageComponent;
import com.zwx.scan.app.feature.verificationrecord.RecyclerDetailAdapter;
import com.zwx.scan.app.feature.verificationrecord.VerificationRecordDetailActivity;
import com.zwx.scan.app.widget.MyListView;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberConsumeListFragment extends BaseFragment<MemberManageContract.Presenter> implements MemberManageContract.View ,View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ScrollView> {

    @BindView(R.id.tv_tab_left)
    protected TextView tvLeft;

    @BindView(R.id.tv_tab_right)
    protected TextView tvRight;

    @BindView(R.id.list_view)
    public MyListView listView;

    @BindView(R.id.ptr)
    protected PullToRefreshScrollView ptr;

 /*   @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;

    @BindView(R.id.iv_no_data)
    protected ImageView iv_no_data;

    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;*/

    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;


    public MemberConsumeListViewAdapter listAdapter = null;

//    public RecyclerAdapterWithHF mAdapter;
   /* @BindView(R.id.test_list_view)
    protected ListView rvList;*/

//    public ListViewAdapter mAdapter;

    private List<String>  tabTitles = new ArrayList<>();
    private int pageNumber = 1;
    private int pageSize = 10;
    private String order = "1"; //1 消费金额 2 消费次数 3  消费时间
    private String desc = "desc"; //空 升序  有值 降序


    private  String userId;
    protected   List<MemberConsumeBean.MemberIT.MemberConsume> memberConsumeList = new ArrayList<>();

    private String type;
    public MemberConsumeListFragment() {
    }

    public static MemberConsumeListFragment getInstance(String type,List<String>  tabTitles) {
        MemberConsumeListFragment fragment = new MemberConsumeListFragment();
        fragment.order = type;
        fragment.tabTitles = tabTitles;
        return fragment;

    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_member_consume_list;
    }

    @Override
    protected void initInjector() {
        DaggerMemberManageComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .memberManageModule(new MemberManageModule(this))
                .build()
                .inject(this);

//        initPtr();
    }

    @Override
    protected void initView() {

        tvLeft.setTextColor(getResources().getColor(R.color.font_color_blue));
        tvRight.setTextColor(getResources().getColor(R.color.color_gray_deep));
        tvLeft.setText( tabTitles.get(0));
        tvRight.setText( tabTitles.get(1));
        userId = SPUtils.getInstance().getString("userId");


        listAdapter = new MemberConsumeListViewAdapter(getActivity(),memberConsumeList);
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

        if("1".equals(order)){
            presenter.comsumeListByMap(getActivity(),userId, pageSize, pageNumber,order,desc,true);
        }else {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    //休眠3秒
                    try{
                        Thread.sleep(1000);
                        presenter.comsumeListByMap(getActivity(),userId, pageSize, pageNumber,order,desc,true);
                    }catch (InterruptedException ex){

                    }

                }
            }.start();
        }

//        mAdapter = new ListViewAdapter(getActivity(), memberConsumeList);
//        rvList.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

      /*  mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                Intent intent = new Intent(getActivity(),MemberConsumeListDetailActivity.class);
                if(memberConsumeList!=null && memberConsumeList.size()>0){
                    MemberConsumeBean.MemberIT.MemberConsume memberConsume = memberConsumeList.get(position);

                    if(memberConsume !=null ){

                        intent.putExtra("isShowSelectMemberRecord","YES");
                        intent.putExtra("MemberConsume",memberConsume);
                    }
                }
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });*/
      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Intent intent = new Intent(getActivity(),MemberConsumeListDetailActivity.class);
              if(memberConsumeList!=null && memberConsumeList.size()>0){
                  MemberConsumeBean.MemberIT.MemberConsume memberConsume = memberConsumeList.get(position);

                  if(memberConsume !=null ){

                      intent.putExtra("isShowSelectMemberRecord","YES");
                      intent.putExtra("MemberConsume",memberConsume);
                  }
              }
              ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
          }
      });

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        memberConsumeList.clear();
        pageNumber = 1;
        presenter.comsumeListByMap(getActivity(),userId, pageSize, pageNumber,order,desc,true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.comsumeListByMap(getActivity(),userId, pageSize, pageNumber,order,desc,false);
    }

    @OnClick({R.id.tv_tab_right,R.id.tv_tab_left})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_tab_left:
                tvLeft.setTextColor(getResources().getColor(R.color.font_color_blue));
                tvRight.setTextColor(getResources().getColor(R.color.color_gray_deep));

                desc = "desc";
                pageNumber = 1;
                memberConsumeList.clear();
                presenter.comsumeListByMap(getActivity(),userId, pageSize, pageNumber,order,desc,true);
                break;
            case R.id.tv_tab_right:
                desc = "";
                tvLeft.setTextColor(getResources().getColor(R.color.color_gray_deep));
                tvRight.setTextColor(getResources().getColor(R.color.font_color_blue));
                pageNumber = 1;
                memberConsumeList.clear();
                presenter.comsumeListByMap(getActivity(),userId, pageSize, pageNumber,order,desc,true);

                break;
        }
    }
    /*private  void initPtr(){
        ptr.postDelayed(new Runnable() {

            @Override
            public void run() {
                if(order == "1"){
                    ptr.autoRefresh(true);
                }else {
                    ptr.autoRefresh(false);
                }

            }
        }, 150);


        ptr.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNumber = 1;
                presenter.comsumeListByMap(getActivity(),userId, pageSize, pageNumber,order,desc,true);
            }



        });




        ptr.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNumber++;
                ptr.refreshComplete();
                presenter.comsumeListByMap(getActivity(),userId, pageSize, pageNumber,order,desc,false);

            }



        });
    }*/





    public class ListViewAdapter extends BaseAdapter {
        private List<MemberConsumeBean.MemberIT.MemberConsume> datas;
        private LayoutInflater inflater;

        public ListViewAdapter(Context context, List<MemberConsumeBean.MemberIT.MemberConsume> data) {
            super();
            inflater = LayoutInflater.from(context);
            datas = data;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_member_consume_list, parent, false);
            }

             TextView tv_consume_count;
             TextView tv_memtype_name;
             TextView tv_consume_time;
             TextView tv_money;
             TextView tv_card_label;
            TextView tv_tel;
            tv_tel = (TextView) convertView.findViewById(R.id.tv_tel);
            tv_consume_count = (TextView) convertView.findViewById(R.id.tv_consume_count);
            tv_memtype_name = (TextView) convertView.findViewById(R.id.tv_memtype_name);
            tv_consume_time = (TextView) convertView.findViewById(R.id.tv_consume_time);
            tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            MemberConsumeBean.MemberIT.MemberConsume memberConsume = datas.get(position);

            BigDecimal amt = memberConsume.getCompEatTotalAmt();
            String consume = "0.00";
            if(amt == null &&amt.doubleValue() ==0.0){

            }else {
                consume = new DecimalFormat("0.00").format(amt.setScale(2,BigDecimal.ROUND_DOWN).doubleValue()).toString();
            }

            String total =  memberConsume.getCompEatTotalQnt() != null ? memberConsume.getCompEatTotalQnt()+"":"-";

            String personQnt = "消费次数："+total;
            int subTextColor = Color.parseColor("#333333");
            String[] subTextArray = {"消费次数："};

            String consumeTime = memberConsume.getConsumeTime();
            String memTypeName = memberConsume.getMemTypeName();
            tv_memtype_name.setText(memTypeName);

//        String name = couponName.replace("\\,","\n");

            tv_tel.setText(memberConsume.getMemberTel()!=null?memberConsume.getMemberTel():"");
            tv_consume_count.setText(TextColorSizeUtils.getTextSpan(context,subTextColor,32,personQnt,subTextArray));

            tv_consume_time.setText(consumeTime);
            tv_money .setText("￥"+consume);


            return convertView;
        }

        public List<MemberConsumeBean.MemberIT.MemberConsume> getData() {
            return datas;
        }

    }



}
