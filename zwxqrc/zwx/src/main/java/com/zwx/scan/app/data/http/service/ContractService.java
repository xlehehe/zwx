package com.zwx.scan.app.data.http.service;

import com.zwx.scan.app.data.bean.CampaignCount;
import com.zwx.scan.app.data.bean.Contract;
import com.zwx.scan.app.data.bean.ContractBean;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.http.HttpUrls;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author : lizhilong
 * time   : 2019/01/19
 * desc   :
 * version: 1.0
 **/
public interface ContractService {


    @FormUrlEncoded
    @POST(HttpUrls.CONTRACT_MANAGE_CONTENT_URL)
    Observable<HttpResponse<ContractBean>> doQueryContract(@Field("storeId")String storeId, @Field("contractId")String contractId);

    @FormUrlEncoded
    @POST(HttpUrls.CONTRACT_MANAGE_LIST_URL)
    Observable<HttpResponse<List<Contract>>> doQueryContractList(@Field("storeId")String storeId);
}
