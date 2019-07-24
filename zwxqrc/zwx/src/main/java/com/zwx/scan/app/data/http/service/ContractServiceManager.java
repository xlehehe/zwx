package com.zwx.scan.app.data.http.service;

import android.content.Context;

import com.zwx.scan.app.data.base.BaseServiceManager;
import com.zwx.scan.app.data.bean.Brand;
import com.zwx.scan.app.data.bean.Contract;
import com.zwx.scan.app.data.bean.ContractBean;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.StoreResult;

import java.util.List;

import io.reactivex.Observable;

/**
 * author : lizhilong
 * time   : 2019/01/19
 * desc   :
 * version: 1.0
 **/
public class ContractServiceManager extends BaseServiceManager {

    private Context context;

    private ContractService contractService;


    public ContractServiceManager(){
        contractService = RetrofitServiceManager.getInstance().create(ContractService.class);
    }



    /**
     *  活动列表
     * @return
     */

    public Observable<HttpResponse<ContractBean>> doQueryContract(String storeId, String contract){
        return  observe(contractService.doQueryContract(storeId,contract));
    }


    public Observable<HttpResponse<List<Contract>>> doQueryContractList(String userId){
        return  observe(contractService.doQueryContractList(userId));
    }
}
