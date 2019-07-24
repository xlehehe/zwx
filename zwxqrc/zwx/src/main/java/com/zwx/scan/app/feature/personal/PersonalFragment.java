package com.zwx.scan.app.feature.personal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.EncryptUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.data.bean.User;
import com.zwx.scan.app.data.http.service.RetrofitServiceManager;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.home.HomeFragment;
import com.zwx.scan.app.feature.main.MainActivity;
import com.zwx.scan.app.feature.user.LoginActivity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends BaseFragment<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener{
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;


    @BindView(R.id.btn_exit)
    protected Button btnExit;

    private String param;

    private String userId;
    public PersonalFragment() {
        // Required empty public constructor
    }

    public static PersonalFragment getInstance(String param) {
        PersonalFragment fragment = new PersonalFragment();
        fragment.param = param;
        return fragment;
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initInjector() {

        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initView() {

        tvTitle.setText("趣管家");
        ivBack.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData() {


    }



    @OnClick({R.id.btn_exit})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit:
                User user =new User();
                String userId = SPUtils.getInstance().getString("userId");
                String username = SPUtils.getInstance().getString("username");


                user.setId(Long.parseLong(userId));
                user.setUsername(username);

                presenter.logout(getActivity(),user);


                break;
        }
    }
}
