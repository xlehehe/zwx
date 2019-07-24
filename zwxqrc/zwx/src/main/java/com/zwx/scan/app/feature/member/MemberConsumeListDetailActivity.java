package com.zwx.scan.app.feature.member;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
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
import com.zwx.library.pickerview.picker.builder.OptionsPickerBuilder;
import com.zwx.library.pickerview.picker.listener.CustomListener;
import com.zwx.library.pickerview.picker.listener.OnOptionsSelectListener;
import com.zwx.library.pickerview.picker.view.OptionsPickerView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.CardQrcBean;
import com.zwx.scan.app.data.bean.ComsumeLog;
import com.zwx.scan.app.data.bean.MemberConsumeBean;
import com.zwx.scan.app.data.bean.MemberInfoBean;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.countanalysis.staffreward.StaffRewardAnalysisActivity;
import com.zwx.scan.app.widget.MyListView;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MemberConsumeListDetailActivity extends BaseActivity<MemberManageContract.Presenter>  implements MemberManageContract.View,View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ScrollView>{


    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.tv_right)
    protected TextView rvRight;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;



    @BindView(R.id.tv_name)
    protected TextView tv_name;
    @BindView(R.id.tv_tel)
    protected TextView tv_tel;
    @BindView(R.id.tv_sex)
    protected TextView tv_sex;
    @BindView(R.id.tv_card)
    protected TextView tv_card;

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


    public MemberConsumeListDetailViewAdapter listAdapter = null;

//    public RecyclerAdapterWithHF mAdapter;
    private int pageNumber = 1;
    private int pageSize = 10;

    private  String userId;

    private String memberId;

    private String name;
    private String tel;
    private String sexName;
    private String compMemTypeId;
    private String memTypeName;
    private String brirthday;
    protected   String isShowMemberDetail; //从会员信息进入

    private String isShowSelectMemberRecord; //从消费记录进入
    MemberConsumeBean.MemberIT.MemberConsume memberConsume = null;
    protected List<ComsumeLog> memberConsumeList = new ArrayList<>();


    protected OptionsPickerView pvQrc;
    protected List<CardQrcBean> cardQrcBeans = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_consume_list_detail;
    }

    @Override
    protected void initView() {
        tvTitle.setText("会员消费记录");

        DaggerMemberManageComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .memberManageModule(new MemberManageModule(this))
                .build()
                .inject(this);

        initQrcOptionPicker();
    }

    @Override
    protected void initData() {
        memberConsume = (MemberConsumeBean.MemberIT.MemberConsume)getIntent().getSerializableExtra("MemberConsume");

        isShowMemberDetail = getIntent().getStringExtra("isShowMemberDetail");
        isShowSelectMemberRecord = getIntent().getStringExtra("isShowSelectMemberRecord");
        rvRight.setText("查看会员详情");
        if (isShowMemberDetail!=null &&"YES".equals(isShowMemberDetail)){
            rvRight.setVisibility(View.GONE);

            tv_card.setCompoundDrawables(null,null,null,null);
            tv_card.setEnabled(false);
        }else {
            rvRight.setVisibility(View.VISIBLE);
            tv_card.setEnabled(true);
        }


        name = memberConsume.getMemberName()!=null?memberConsume.getMemberName():"-";

        memTypeName = memberConsume.getMemTypeName()!=null?memberConsume.getMemTypeName():"-";
        memberId =memberConsume.getMemberId();
        compMemTypeId = memberConsume.getCompMemTypeId();
        tel = memberConsume.getMemberTel()!=null?memberConsume.getMemberTel():"-";
        int sex = memberConsume.getSexType();
        brirthday = memberConsume.getBrirthday();
        if(sex == 1){
            sexName = "男";
        }else if(sex == 0){
            sexName = "女";
        }



        tv_name.setText(name);
        tv_sex.setText(sexName);

        if("YES".equals(isShowSelectMemberRecord)){
            compMemTypeId = "";
            tv_card.setText("全部会员卡");
        }else {
            tv_card.setText(memTypeName);
        }

        tv_tel.setText(tel);
/*
        CardQrcBean cardQrcBean = new CardQrcBean();
        cardQrcBean.setId("all");
        cardQrcBean.setCardNo("全部会员卡");
        cardQrcBeans.add(cardQrcBean);*/
        userId = SPUtils.getInstance().getString("userId");
        listAdapter = new MemberConsumeListDetailViewAdapter(this,memberConsumeList);

        listView.setAdapter(listAdapter);


        if(TextUtils.isEmpty(memberId)){
            ToastUtils.showShort("该会员为空");
            return;
        }


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

        tv_no_data.setText("您现在还没有消费记录哦！");

        ll_no_data.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        presenter.doQueryComsumeLog(MemberConsumeListDetailActivity.this,pageNumber,pageSize,compMemTypeId,userId,memberId,true);
//        presenter.doQueryComsumeLog(MemberConsumeListDetailActivity.this,pageNumber,pageSize,compMemTypeId,userId,memberId,true);


    }

    @OnClick({R.id.iv_back,R.id.tv_card,R.id.tv_right})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:

                ActivityUtils.finishActivity(MemberConsumeListDetailActivity.class,
                        R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            case R.id.tv_right:
                MemberInfoBean.MemberInfo memberInfo =new MemberInfoBean.MemberInfo();
                memberInfo.setBrirthday(memberConsume.getBrirthday());
                memberInfo.setSexType(memberConsume.getSexType());
                memberInfo.setMemberName(memberConsume.getMemberName());
                memberInfo.setMemberId(memberId);
                memberInfo.setMemberTel(memberConsume.getMemberTel());
                memberInfo.setMarry(memberConsume.getMarry());

                Intent intent = new Intent(MemberConsumeListDetailActivity.this,MemberInfoDetailActivity.class);
                intent.putExtra("isShowSelectMemberRecord",isShowSelectMemberRecord);
                intent.putExtra("memberInfo",memberInfo);
                ActivityUtils.startActivity(intent,
                        R.anim.slide_in_right,R.anim.slide_out_left);
                break;
            case R.id.tv_card:
               pvQrc.show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(MemberConsumeListDetailActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        memberConsumeList.clear();
        pageNumber = 1;
        presenter.doQueryComsumeLog(MemberConsumeListDetailActivity.this,pageNumber,pageSize,compMemTypeId,userId,memberId,true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.doQueryComsumeLog(MemberConsumeListDetailActivity.this,pageNumber,pageSize,compMemTypeId,userId,memberId,false);
    }
    /* private  void initPtr(){
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

                presenter.doQueryComsumeLog(MemberConsumeListDetailActivity.this,pageNumber,pageSize,compMemTypeId,userId,memberId,true);
            }
        });



        ptr.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNumber++;
                presenter.doQueryComsumeLog(MemberConsumeListDetailActivity.this,pageNumber,pageSize,compMemTypeId,userId,memberId,false);
            }
        });
    }*/

    protected void initQrcOptionPicker() {//条件选择器初始化，自定义布局

        pvQrc = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String tx = cardQrcBeans.get(options1).getPickerViewText();

                  compMemTypeId= cardQrcBeans.get(options1).getId();
                if("all".equals(compMemTypeId)){
                    compMemTypeId = "";
                }
                tv_card.setText(tx);

            }
        })

                .setLayoutRes(R.layout.layout_pickerview_custom_position, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final  ImageView ivSubmit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        ivSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvQrc.returnData();
                                pvQrc.dismiss();
                                memberConsumeList.clear();
                                presenter.doQueryComsumeLog(MemberConsumeListDetailActivity.this,pageNumber,pageSize,compMemTypeId,userId,memberId,true);
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvQrc.dismiss();
                            }
                        });


                    }
                })

                .build();


        pvQrc.setPicker(cardQrcBeans);

    }

}
