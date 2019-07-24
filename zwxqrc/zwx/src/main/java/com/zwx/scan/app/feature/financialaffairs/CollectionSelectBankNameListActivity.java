package com.zwx.scan.app.feature.financialaffairs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.CateBean;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.cateringinfomanage.StoreInfoManageActivity;
import com.zwx.scan.app.feature.cateringinfomanage.StoreInfoParameterSelectorActivity;
import com.zwx.scan.app.feature.cateringinfomanage.adapter.SelectTypeAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/05/22
 * desc   : 收款到银行卡- 选择银行卡
 * version: 1.0
 **/
public class CollectionSelectBankNameListActivity extends BaseActivity<FinancialAffairsContract.Presenter> implements FinancialAffairsContract.View,View.OnClickListener {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.ll_right)
    protected LinearLayout llRight;

    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;


    @BindView(R.id.list_view)
    protected ListView listView;

    private ArrayList<CateBean> typeList = new ArrayList<>();
    private ArrayList<CateBean> selectTypeList= new ArrayList<>();
    private SelectTypeAdapter myAdapter ;
    public static int RESULT_CODE = 8888;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection_select_bank_name_list;
    }

    @Override
    protected void initView() {

        DaggerFinancialAffairsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .financialAffairsModule(new FinancialAffairsModule(this))
                .build()
                .inject(this);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.pickerview_submit));


    }

    @Override
    protected void initData() {


        myAdapter = new SelectTypeAdapter(this,typeList);
        listView.setAdapter(myAdapter);

        //单选
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (CateBean cateBean:typeList){
                    typeList.get(position).setChecked(false);
                }
                if(currentNum == -1){ //选中
                   /* if(typeList.get(position).isChecked()){
                        typeList.get(position).setChecked(false);
                    }else {
                        typeList.get(position).setChecked(true);
                    }
*/
                    typeList.get(position).setChecked(true);
                    currentNum = position;
                }else if(currentNum == position){ //同一个item选中变未选中
                    typeList.get(position).setChecked(false);
                    currentNum = -1;
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的
                    /* //多选
                    if(typeList.get(position).isChecked()){
                        typeList.get(position).setChecked(false);
                    }else {
                        typeList.get(position).setChecked(true);
                    }*/
                    typeList.get(position).setChecked(true);
                    currentNum = position;
                }

                myAdapter.notifyDataSetChanged();
            }
        });
    }

    private void selectData(){
        for (CateBean typeBean :typeList){
            boolean isChecked = typeBean.isChecked();
            if(isChecked){
                selectTypeList.add(typeBean);
            }
        }


    }

    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(CollectionSelectBankNameListActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.ll_right:
                selectData();
                intent = new Intent(CollectionSelectBankNameListActivity.this,CollectionBankNoEffectiveActivity.class);
                intent.putExtra("selectTypeList",selectTypeList);
                setResult(RESULT_CODE,intent);
                finish();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(CollectionSelectBankNameListActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
