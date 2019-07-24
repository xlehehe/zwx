package com.zwx.scan.app.data.http.service;

import android.content.Context;

import com.zwx.scan.app.data.base.BaseServiceManager;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.MemberCount;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.ReceiveFund;
import com.zwx.scan.app.data.bean.Store;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * author : lizhilong
 * time   : 2019/01/28
 * desc   :
 * version: 1.0
 **/
public class FinanceServiceManager extends BaseServiceManager {

    private Context context;

    private FinanceService financeService;

    public FinanceServiceManager(){
        financeService = RetrofitServiceManager.getInstance().create(FinanceService.class);
    }


    public Observable<HttpResponse<ReceiveFund>> doQueryByFundId(String storeId){
        return  observe(financeService.doQueryByFundId(storeId));
    }
    public Observable<HttpResponse<List<ReceiveFund>>> doQueryAllByStatus(String storeId,String pageNumber,String pageSize,String status){
        return  observe(financeService.doQueryAllByStatus(storeId,pageNumber,pageSize,status));
    }

    public Observable<HttpResponse<List<Store>>> queryStoreList(String userId){
        return  observe(financeService.queryStores(userId));
    }

    public Observable<HttpResponse<Map<String,Object>>> doQueryMoneyByIds(String userId, String storeId,String ids){
        return  observe(financeService.doQueryMoneyByIds(userId,storeId,ids));
    }
}
