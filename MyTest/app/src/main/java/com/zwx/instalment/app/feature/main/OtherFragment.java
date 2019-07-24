package com.zwx.instalment.app.feature.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zwx.instalment.app.R;
import com.zwx.instalment.app.base.BaseFragment;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFragment extends BaseFragment {

    @BindView(R.id.tv_title)
    protected TextView tv_title;
    private String param;
    public OtherFragment() {
        // Required empty public constructor
    }

    public static OtherFragment getInstance(String param) {
        OtherFragment fragment = new OtherFragment();
        fragment.param = param;
        return fragment;
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_other;
    }

    @Override
    protected void initView() {

        tv_title.setText(param);
    }

    @Override
    protected void initData() {

    }
}
