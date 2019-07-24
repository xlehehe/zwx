package com.zwx.scan.app.feature.staffmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CateBean;
import com.zwx.scan.app.data.bean.CompMemberType;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.Feedback;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.cateringinfomanage.StoreInfoManageActivity;
import com.zwx.scan.app.feature.cateringinfomanage.StoreInfoParameterSelectorActivity;
import com.zwx.scan.app.feature.modulemore.ModuleMoreListActivity;
import com.zwx.scan.app.feature.personal.FeedBackAdapter;
import com.zwx.scan.app.feature.personal.FeedBackDetailActivity;
import com.zwx.scan.app.feature.personal.FeedBackListActivity;
import com.zwx.scan.app.feature.verification.VerificationActivity;
import com.zwx.scan.app.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class PullQrcSelectContentActivity extends BaseActivity<StaffManageContract.Presenter> implements StaffManageContract.View,View.OnClickListener {


    @BindView(R.id.iv_back)
    protected ImageView ivBack;
    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.tv_right)
    protected TextView tvRight;



    @BindView(R.id.rv_list)
    public RecyclerView rvList;
    @BindView(R.id.ptr)
    public PtrClassicFrameLayout ptr;

    @BindView(R.id.tv_content_name)
    protected TextView tv_content_name;

    protected ArrayList<CompMemberType> compMemberTypeList = new ArrayList<>();

    protected ArrayList<CompMemberType> selectTypeList = new ArrayList<>();

    protected  List<Map<String,String>> list = new ArrayList();
    protected  ArrayList<Map<String,String>> selectList = new ArrayList();
    private int pageNumber = 1;
    private int pageSize = 10;
    protected String pushType;
    private String pushName;
    private String userId;

    protected PullQrcSelectContentListAdapter listAdapter;

    public RecyclerAdapterWithHF mAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pull_qrc_select_content;
    }

    @Override
    protected void initView() {

        tvTitle.setText("请选择推送内容");
        tvRight.setText("确认");
        tvRight.setVisibility(View.VISIBLE);
        DaggerStaffManageComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .staffManageModule(new StaffManageModule(this))
                .build()
                .inject(this);
        userId = SPUtils.getInstance().getString("userId");
        pushType = getIntent().getStringExtra("pushType");

        pushName = getIntent().getStringExtra("pushName");
        tv_content_name.setText("选择"+pushName);
        initPtr();

    }

    @Override
    protected void initData() {

        listAdapter = new PullQrcSelectContentListAdapter(PullQrcSelectContentActivity.this, list);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new RecycleViewDivider( LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.divide_gray_color)));
//        rvList.setNestedScrollingEnabled(false);
        mAdapter = new RecyclerAdapterWithHF(listAdapter);
        mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            int currentNum = -1;
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                Map<String,String> map = (Map<String,String>)list.get(position);
                String isChecked = (String)map.get("isChecked");
                for (Map<String,String> map2 :list){
                    map2.put("isChecked","false");
                }
                if(currentNum == -1){ //选中
//                    ((Map<String,Object>)list.get(position)).put("isChecked",true);
                    map.put("isChecked","true");
                    selectList.add(map);
                   /* if(isChecked){
                        ((Map<String,Object>)list.get(position)).put("isChecked",false);
                    }else {
                        ((Map<String,Object>)list.get(position)).put("isChecked",true);
                    }*/

                    currentNum = position;
                }else if(currentNum == position){ //同一个item选中变未选中
                    map.put("isChecked","false");
                    currentNum = -1;
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的
                    map.put("isChecked","true");
                    selectList.add(map);
//                    ((Map<String,Object>)list.get(position)).put("isChecked",true);
                   /* if(isChecked){
                        ((Map<String,Object>)list.get(position)).put("isChecked",false);
                    }else {
                        ((Map<String,Object>)list.get(position)).put("isChecked",true);
                    }*/
                    currentNum = position;
                }

                mAdapter.notifyDataSetChanged();

            }
        });
        rvList.setAdapter(mAdapter);

        presenter.getContentByType(PullQrcSelectContentActivity.this,userId,pushType, pageNumber,pageSize ,true);
    }
    @OnClick({R.id.iv_back,R.id.tv_right})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.finishActivity(PullQrcSelectContentActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);

                break;
            case R.id.tv_right:
                selectData();

                intent = new Intent(PullQrcSelectContentActivity.this,PullQrcManageActivity.class);
                intent.putExtra("selectContentList",selectList);
                setResult(PullQrcManageActivity.RESULT_CODE,intent);
                finish();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(PullQrcSelectContentActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
    }


    private void selectData(){
        for (Map<String,String> map :list){
            String isChecked =(String)map.get("isChecked");
            if("true".equals(isChecked)){
                selectList.add(map);
            }
        }

        remove();

    }


    protected void remove(){
        if(selectList != null && selectList.size()>0){
            for(int i=0;i<selectList.size();i++){
                Map<String,String> map1 = ( Map<String,String>)selectList.get(i);
                String idStr = String.valueOf(map1.get("id"));
                for(int j=selectList.size()-1;j>i;j--){
                    Map<String,String> map2 = ( Map<String,String>)selectList.get(j);
                    String idStr2 =  String.valueOf(map2.get("id"));
                    if(idStr.equals(idStr2)){
                        selectList.remove(j);
                        break;
                    }
                }
            }
        }

    }

    private  void initPtr(){
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
                presenter.getContentByType(PullQrcSelectContentActivity.this,userId,pushType, pageNumber,pageSize ,true);

            }
        });



        ptr.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNumber++;
                presenter.getContentByType(PullQrcSelectContentActivity.this,userId,pushType, pageNumber,pageSize ,false);

            }
        });
    }


}
