package com.zwx.scan.app.feature.contractmanage;


import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ScreenUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.feature.AppContext;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContractViewFragment extends  BaseFragment{


    @BindView(R.id.iv_bg)
    protected ImageView ivBg;
    private String storeId;

    private String contractId;

    private String contractImg;
    public static ContractViewFragment getInstance(String contractImg) {
        ContractViewFragment instance = new ContractViewFragment();
        instance.contractImg = contractImg;
        return instance;
    }

    public ContractViewFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_contract_view;
    }

    @Override
    protected void initInjector() {
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        contractImg = SPUtils.getInstance().getString("contract_img");

        setBrandLogo();
    }

    private void setBrandLogo(){
//        RequestOptions requestOptions = new RequestOptions()
//                .placeholder(R.drawable.ic_logo_default)
//                .error(R.drawable.ic_logo_default)
//                .fallback(R.drawable.ic_logo_default);

//        Glide.with(this).load(contractImg).apply(requestOptions).into(ivBg);

        int with = ScreenUtils.getScreenWidth(getActivity());
        int height = ScreenUtils.dip2px(getActivity(),ScreenUtils.px2dip(getContext(),ScreenUtils.getScreenHeight(getActivity())) - 48);
        Glide.with(this).load(contractImg).into(new SimpleTarget<Drawable>(with,height) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ivBg.setBackground(resource);    //设置背景
                }
            }
        });
    }
}
