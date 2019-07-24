package com.zwx.scan.app.feature.contractmanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.Contract;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.accountmanage.AccountActivity;
import com.zwx.scan.app.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ContractListActivity extends BaseActivity<ContractContract.Presenter> implements ContractContract.View,View.OnClickListener{
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.list_view)
    protected ListView listView;

    private String storeId;
    protected  ContractListAdapter contractListAdapter;
    protected List<Contract> contractList = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_contract_list;
    }

    @Override
    protected void initView() {
        DaggerContractComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .contractModule(new ContractModule(this))
                .build()
                .inject(this);
        tvTitle.setText("全部合同");


    }

    @Override
    protected void initData() {

        storeId = SPUtils.getInstance().getString("storeId");

        presenter.doQueryContractList(this,storeId,"NO");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contract contract = contractList.get(position);

                Intent intent = new Intent(ContractListActivity.this,ContractActivity.class);
                intent.putExtra("contractId",String.valueOf(contract.getContractId()));
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }
    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {

            case R.id.iv_back:
                ActivityUtils.finishActivity(ContractListActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);

                break;



        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(ContractListActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
