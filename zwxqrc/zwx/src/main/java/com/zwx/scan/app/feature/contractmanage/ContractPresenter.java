package com.zwx.scan.app.feature.contractmanage;

import android.content.Context;
import android.view.View;

import com.zwx.library.tablayout.listener.TabEntity;
import com.zwx.library.utils.EncryptUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.base.adapter.MyPagerAdapter;
import com.zwx.scan.app.data.base.BaseObserver;
import com.zwx.scan.app.data.bean.Contract;
import com.zwx.scan.app.data.bean.ContractBean;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.data.http.service.ContractServiceManager;
import com.zwx.scan.app.data.http.service.RetrofitServiceManager;
import com.zwx.scan.app.feature.user.LoginActivity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * author : lizhilong
 * time   : 2018/08/31
 * desc   : 登录业务逻辑
 * version: 1.0
 **/

public class ContractPresenter implements ContractContract.Presenter{

    private final ContractContract.View view;
    private ContractServiceManager contractServiceManager;

    //添加订阅，应用于取消订阅，防止造成内存泄漏
    private CompositeDisposable disposable;
    LoginActivity activity;
    @Inject
    public ContractPresenter(ContractContract.View view) {
        this.view = view;

        contractServiceManager = new ContractServiceManager();
        this.disposable = new CompositeDisposable();


    }

    @Override
    public void doQueryContract(Context context, String storeId, String contractId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("storeId",storeId);
        params.put("contractId",contractId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        ContractInfoFragment fragment = (ContractInfoFragment) view;
//        ContractActivity activity = (ContractActivity) context;
        contractServiceManager.doQueryContract(storeId,contractId)
                .subscribe(new BaseObserver<ContractBean>(context,false){
                    @Override
                    public void onSuccess(ContractBean resultBean, String msg) {

                        if(resultBean != null){

                            fragment.contract = resultBean.getContract();


                            if(fragment.contract != null){
                                String signImg = fragment.contract.getSignImg();
                                if(signImg != null && !"".equals(signImg)){
                                    SPUtils.getInstance().put("contract_img", HttpUrls.IMAGE_ULR+ signImg);
                                }
                                fragment.tv_contract_no.setText(fragment.contract.getContractNo()!=null&&!"".equals(fragment.contract.getContractNo())?fragment.contract.getContractNo():"");
                                String startTime = fragment.contract.getStartDate();
                                String endTime = fragment.contract.getEndDate();
                                if(startTime != null && !"".equals(startTime)){
                                    Date date = TimeUtils.string2Date(startTime);
                                    fragment.tvStart.setText(TimeUtils.date2String(date).replace("-","."));
                                }else {
                                    fragment.tvStart.setText("");
                                }

                                if(endTime != null && !"".equals(endTime)){
                                    Date date = TimeUtils.string2Date(endTime);
                                    fragment.tvEnd.setText(TimeUtils.date2String(date).replace("-","."));
                                }else {
                                    fragment.tvEnd.setText("");
                                }

                                BigDecimal fee = fragment.contract.getPayFee();

                                if(fee != null && fee.doubleValue()>0){
//                                    String payFee = new DecimalFormat("0.00").format(fee.setScale(2,BigDecimal.ROUND_DOWN).doubleValue()).toString();
                                    String payFee = RegexUtils.getDoubleString(fee.doubleValue());
                                    fragment.tvServiceMoney.setText("￥"+payFee);
                                }else {
                                    fragment.tvServiceMoney.setText("");

                                }
                                String contractNo = fragment.contract.getContractNo();
                                fragment.tv_contract_no.setText(contractNo != null ?contractNo:"");

                                long mode = fragment.contract.getPayMode();

                                if(mode >0){
                                    fragment.tvMode.setText("每"+mode+"个月一缴");
                                }else {
                                    fragment.tvMode.setText("");
                                }


                            }

                            fragment.receiveFundList = resultBean.getReceiveFundList();

                            fragment.contractPayListAdapter = new ContractPayListAdapter(context,fragment.receiveFundList);
                            fragment.myListView.setAdapter(fragment.contractPayListAdapter);
                            String isShowRight = resultBean.getIsShowAllIcon();
                            ContractActivity activity = (ContractActivity) fragment.getActivity();
                            if( isShowRight != null && !"".equals(isShowRight)){
                                if("1".equals(isShowRight)){
                                    activity.tvRight.setVisibility(View.VISIBLE);
                                }else if("0".equals(isShowRight)) {
                                    activity.tvRight.setVisibility(View.GONE);
                                }
                            }



                        }



                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

//                        ToastUtils.showCustomShortBottom("暂未获取合同信息");
                    }
                });
    }

    @Override
    public void doQueryContractList(Context context, String storeId,String isDetailAndList) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("storeId",storeId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        contractServiceManager.doQueryContractList(storeId)
                .subscribe(new BaseObserver<List<Contract>>(context,false){
                    @Override
                    public void onSuccess(List<Contract> contractList, String msg) {

                        if("YES".equals(isDetailAndList)){
                            ContractActivity contractActivity = null;
                            if(contractList != null && contractList.size()>=2){
                                contractActivity.tvRight.setVisibility(View.VISIBLE);

                            }else {
                                contractActivity.tvRight.setVisibility(View.GONE);

                            }
                        }else {
                            ContractListActivity contractListActivity = (ContractListActivity)context;

                            if(contractList != null && contractList.size()>0){
                                contractListActivity.contractList = contractList;
                                contractListActivity.contractListAdapter = new ContractListAdapter(context,contractListActivity.contractList);
                                contractListActivity.listView.setAdapter(contractListActivity.contractListAdapter);
                            }

                        }




                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                        ToastUtils.showShort(message);
                    }
                });
    }
   /*@Override
    public void login(Context context,String username, String password) {

        RetrofitServiceManager.setHeadTokenMap(null);

        if(!NetworkUtils.isNetworkAvailable(context) ){
            ToastUtils.showShort(R.string.network_error);
            return;
        }
        activity = (LoginActivity)context;
        userServiceManager.login(username,password)
                .subscribe(new BaseObserver<User>(context,true){
                    @Override
                    public void onSuccess(User user,String msg) {
                        activity.tvAccountTip.setText("");
                        activity.tvPsdTip.setText("");
                        SPUtils.getInstance().put("username",username);
                        SPUtils.getInstance().put("password",password);

                        if(user == null){
                            ToastUtils.showShort("登录失败!");
                            return;
                        }

                        String userId = SPUtils.getInstance().getString("userId");

                        if(!userId.equals(String.valueOf(user.getId()))){

                            SPUtils.getInstance().clear();

                        }
                        SPUtils.getInstance().put("userId",user.getUserId()+"");

                        String token=user.getToken();
                        SPUtils.getInstance().put("token",token);
                        SPUtils.getInstance().put("isLogined","YES");
                        SPUtils.getInstance().put("isFirst",false);
                        ActivityUtils.startActivity(MainActivity.class,
                                R.anim.slide_in_right,R.anim.slide_out_left);
                        ((LoginActivity) context).finish();

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        SPUtils.getInstance().put("isLogined","NO");
                        activity.tvPsdTip.setText(message);
                        activity.edtPsd.setFocusable(true);
                        activity.edtPsd.setFocusableInTouchMode(true);

                    }
                });
    }*/





}
