package com.zwx.scan.app.feature.accountmanage;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.Resource;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.widget.treeview.adapter.SimpleTreeListViewAdapter;
import com.zwx.scan.app.widget.treeview.bean.OrgBean;
import com.zwx.scan.app.widget.treeview.utils.Node;
import com.zwx.scan.app.widget.treeview.utils.adapter.TreeListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AccountRoleAuthActivity extends BaseActivity<AccountContract.Presenter> implements AccountContract.View,View.OnClickListener{

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.lv_type)
    protected ListView lvType;
    private String title;
    protected List<Resource> resourceList = new ArrayList<>();

    //	private SimpleTreeListViewAdapter<FileBean> mAdapter;
    protected SimpleTreeListViewAdapter mAdapter;
    protected List<Node> mDatas = new ArrayList<>();

    protected  String roleId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_role_auth;
    }

    @Override
    protected void initView() {

        DaggerAccountComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .accountModule(new AccountModule(this))
                .build()
                .inject(this);
        title = getIntent().getStringExtra("title");
        tvTitle.setText(title);
    }

    @Override
    protected void initData() {
        roleId = getIntent().getStringExtra("id");
        presenter.resourceTreeListByRoleId(this,roleId);


    }



    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {

            case R.id.iv_back:
                ActivityUtils.finishActivity(AccountRoleAuthActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);

                break;



        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(AccountRoleAuthActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
