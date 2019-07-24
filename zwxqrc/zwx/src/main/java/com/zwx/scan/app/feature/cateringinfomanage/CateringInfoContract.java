package com.zwx.scan.app.feature.cateringinfomanage;

import android.content.Context;

import com.zwx.scan.app.base.BasePresenter;
import com.zwx.scan.app.base.BaseView;
import com.zwx.scan.app.data.bean.Brand;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.StoreBean;
import com.zwx.scan.app.data.bean.StoreParamBean;

import java.io.File;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :  餐企信息管理
 * version: 1.0
 **/
public interface CateringInfoContract {

    interface  View extends BaseView<CateringInfoContract.Presenter> {

    }

    interface  Presenter extends BasePresenter {



        //查询餐企信息列表
        void doQuery(Context context, String brandId, Integer pageNumber,Integer pageSize,boolean isRefresh);

        //查询当前用户品牌
//        GET /api/brand/doQueryByCurrentUser.do
        void doQueryByCurrentUser(Context context, String userId);

        //上传品牌logo
        void upload(Context context, File file,String flag);

        void saveBrand(Context context, Brand brand);

        void doLoad(Context context, String id);

        void createQrCode(Context context, String storeId);
        void saveStore(Context context, StoreParamBean store);

        void uploadFile(Context context, List<File> fileList, StoreParamBean storeBean);

        void delete(Context context, String imageId,Integer deletePosition);
    }



}
