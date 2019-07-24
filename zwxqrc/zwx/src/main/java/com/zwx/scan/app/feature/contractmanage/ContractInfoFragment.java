package com.zwx.scan.app.feature.contractmanage;


import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.data.bean.Contract;
import com.zwx.scan.app.data.bean.ContractBean;
import com.zwx.scan.app.data.bean.ReceiveFund;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.countanalysis.campaign.CampaignAnalysisDetailFragment;
import com.zwx.scan.app.widget.MyListView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContractInfoFragment extends BaseFragment<ContractContract.Presenter> implements ContractContract.View {


    @BindView(R.id.tv_start_time)
    protected TextView tvStart;

    @BindView(R.id.tv_end_time)
    protected TextView tvEnd;
    @BindView(R.id.tv_month_service_money)
    protected TextView tvServiceMoney;
    @BindView(R.id.tv_pay_mode)
    protected TextView tvMode;
    @BindView(R.id.list_view)
    protected MyListView myListView;

    @BindView(R.id.tv_contract_no)
    protected TextView tv_contract_no;
    private String params;

    private String storeId;
    private String contractId;

    protected Contract contract = new Contract();

    protected ContractBean contractBean = new ContractBean();
    ContractPayListAdapter contractPayListAdapter = null;
    protected List<ReceiveFund>  receiveFundList  = new ArrayList<>();
    public ContractInfoFragment() {
    }


    public static ContractInfoFragment getInstance(String contractId) {
        ContractInfoFragment instance = new ContractInfoFragment();
        instance.contractId = contractId;
        return instance;
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_contract_info;
    }

    @Override
    protected void initInjector() {
        DaggerContractComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .contractModule(new ContractModule(this))
                .build()
                .inject(this);

    }

    @Override
    protected void initView() {
        storeId = SPUtils.getInstance().getString("storeId");


        presenter.doQueryContract(getActivity(),storeId,contractId);



    }

    @Override
    protected void initData() {

    }
}
