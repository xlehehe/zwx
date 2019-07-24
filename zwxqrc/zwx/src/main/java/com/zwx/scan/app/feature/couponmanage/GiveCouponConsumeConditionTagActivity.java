package com.zwx.scan.app.feature.couponmanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.feature.campaign.CampaignLikeTwoActivity;
import com.zwx.scan.app.feature.campaign.CampaignTitleContentActivity;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanNextTwoActivity;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanPrizeListActivity;
import com.zwx.scan.app.feature.campaign.PrizeBean;
import com.zwx.scan.app.feature.campaign.SelectPrizeAdapter;
import com.zwx.scan.app.widget.MyListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class GiveCouponConsumeConditionTagActivity extends BaseActivity  implements View.OnClickListener{
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.list_view)
    protected MyListView list_view;

    private SelectPrizeAdapter adapter;
    private ArrayList<PrizeBean> prizeBeanList = new ArrayList<>();

    private ArrayList<PrizeBean> selectedList = new ArrayList<>();
    protected  int currentNum = -1;

    //从条件选择是集赞还是唤醒消费礼
    protected  String  isJzAndHxType = "";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_give_coupon_consume_condition_tag;
    }

    @Override
    protected void initView() {
        tvTitle.setText("条件编辑");
        selectedList =  (ArrayList<PrizeBean>) getIntent().getSerializableExtra("prizeBeanList");
        isJzAndHxType = (String)getIntent().getStringExtra("selectType");
    }

    @Override
    protected void initData() {


        initPrizeBeanListData();
        if(selectedList != null && selectedList.size()>0) {
            for (PrizeBean prizeBean : selectedList) {
                String selectName = prizeBean.getName();
                if (prizeBean.isChecked()) {
                    for (PrizeBean prizeBean1 : prizeBeanList) {
                        String unSelectName = prizeBean1.getName();

                        if(selectName != null && !"".equals(selectName)){
                            if (selectName.equals(unSelectName)) {
                                prizeBean1.setChecked(prizeBean.isChecked());
                            }
                        }

                    }
                }
            }

            adapter.notifyDataSetChanged();
        }


        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(currentNum == -1){ //选中
                    if(position == 0){
                        prizeBeanList.get(position).setChecked(true);
                    }else {
                        if(prizeBeanList.get(position).isChecked()){
                            for (int i = 0;i<=position;i++){
                                prizeBeanList.get(i).setChecked(false);
                            }
                            for (PrizeBean prizeBean : prizeBeanList){
                                prizeBean.setChecked(false);
                            }
                        }else {
                            for (int i = 0;i<=position;i++){
                                prizeBeanList.get(i).setChecked(true);
                            }


                        }

                    }

                    for (PrizeBean prizeBean : prizeBeanList){
                        prizeBean.setChecked(false);
                    }
                    for (int i = 0;i<=position;i++){
                        prizeBeanList.get(i).setChecked(true);
                    }
                    currentNum = position;
                }else if(currentNum == position){ //同一个item选中变未选中
                 
                    if(position == 0){
                        prizeBeanList.get(position).setChecked(true);
                    }

                    currentNum = -1;
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的

                    for (PrizeBean prizeBean : prizeBeanList){
                        prizeBean.setChecked(false);
                    }
                    for (int i = 0;i<=position;i++){
                        prizeBeanList.get(i).setChecked(true);
                    }
                    currentNum = position;
                }
                adapter.notifyDataSetChanged();
            }
        });
    }


    private void initPrizeBeanListData(){
        PrizeBean prizeBean = new PrizeBean();
        prizeBean.setId(0);
        prizeBean.setName("条件一");
        prizeBean.setChecked(false);
        prizeBeanList.add(prizeBean);
        prizeBean = new PrizeBean();
        prizeBean.setId(1);
        prizeBean.setName("条件二");
        prizeBean.setChecked(false);
        prizeBeanList.add(prizeBean);
        prizeBean.setId(2);
        prizeBean = new PrizeBean();
        prizeBean.setName("条件三");
        prizeBean.setChecked(false);
        prizeBeanList.add(prizeBean);
        if("JZ".equals(isJzAndHxType)){

        }else {
            prizeBean.setId(3);
            prizeBean = new PrizeBean();
            prizeBean.setName("条件四");
            prizeBean.setChecked(false);
            prizeBeanList.add(prizeBean);
        }


        setBeanAdapter();
    }

    private void setBeanAdapter(){
        adapter = new SelectPrizeAdapter(this,prizeBeanList);
        list_view.setAdapter(adapter);
    }


    @OnClick({R.id.iv_back,R.id.tv_right,R.id.cancel,R.id.confirm})
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(GiveCouponConsumeConditionTagActivity.class, R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.confirm:
                selectedList.clear();
                if(prizeBeanList != null){
                    for (PrizeBean prizeBean : prizeBeanList){
                        boolean isChecked = prizeBean.isChecked();
                        if(isChecked){
                            selectedList.add(prizeBean);
                        }

                    }
                }
                //集赞还是唤醒消费礼，这两种页面样式一致，共用一个
                if("JZ".equals(isJzAndHxType)){
                    PrizeBean prizeBean = new PrizeBean();
                    prizeBean.setId(3);
                    prizeBean.setName("点赞人奖励");
                    prizeBean.setChecked(false);
                    selectedList.add(prizeBean);
                    intent = new Intent(GiveCouponConsumeConditionTagActivity.this,CampaignLikeTwoActivity.class);
                    intent.putExtra("prizeBeanList",selectedList);
                    setResult(7001,intent);
                }else {
                    intent = new Intent(GiveCouponConsumeConditionTagActivity.this,GiveCouponNewNextConsumeActivity.class);
                    intent.putExtra("prizeBeanList",selectedList);
                    setResult(6001,intent);
                }

                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.cancel:

                ActivityUtils.finishActivity(GiveCouponConsumeConditionTagActivity.class, R.anim.slide_in_left, R.anim.slide_out_right);

                break;
            case R.id.tv_right:

                ActivityUtils.finishActivity(GiveCouponConsumeConditionTagActivity.class, R.anim.slide_in_left, R.anim.slide_out_right);

                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(GiveCouponConsumeConditionTagActivity.class, R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
