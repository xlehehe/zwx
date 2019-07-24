package com.zwx.scan.app.data.http.service;

import android.content.Context;

import com.zwx.scan.app.data.base.BaseServiceManager;
import com.zwx.scan.app.data.bean.Brand;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.ResultBean;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.StoreResult;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * author : lizhilong
 * time   : 2019/01/11
 * desc   :
 * version: 1.0
 **/
public class CateringInfoServiceManager extends BaseServiceManager{
    private Context context;

    private CateringInfoService cateringInfoService;


    public CateringInfoServiceManager(){
        cateringInfoService = RetrofitServiceManager.getInstance().create(CateringInfoService.class);
    }



    /**
     *  活动列表
     * @param brandId
     * @param pageNumber
     * @param pageSize
     * @return
     */

    public Observable<HttpResponse<StoreResult>> doQuery(String brandId, Integer pageNumber, Integer pageSize){
        return  observe(cateringInfoService.doQuery(brandId,pageNumber,pageSize));
    }


    public Observable<HttpResponse<List<Brand>>> doQueryByCurrentUser(String userId){
        return  observe(cateringInfoService.doQueryByCurrentUser(userId));
    }

    /**
     *  上传品牌Logo
     * @param file
     * @return
     */


    public Observable<HttpResponse<Map<String,Object>>> upload(File file){
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part MultipartFile =
                MultipartBody.Part.createFormData("androidbrandLogo", file.getName(), requestFile);

        return  observe(cateringInfoService.upload(MultipartFile));
    }
    public Observable<HttpResponse<String>> delete(String imageId){
        return  observe(cateringInfoService.delete(imageId));
    }


    public Observable<HttpResponse<Map<String,Object>>> uploadFiles(List<File> fileList){
//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
//        MultipartBody.Part MultipartFile =
//                MultipartBody.Part.createFormData("androidbrandLogo", file.getName(), requestFile);

        MultipartBody.Builder builder = new MultipartBody.Builder();
        //上传多张图片
        if(fileList != null && fileList.size()>0){
            for (File file : fileList){
                builder.addFormDataPart("imageFile", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
            }

        }
        MultipartBody multipartBody = builder.setType(MultipartBody.FORM).build();
       /* MultipartBody multipartBody  = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("imageFile", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                .addFormDataPart("imageFile2", file2.getName(), RequestBody.create(MediaType.parse("image/*"), file2))
                .addFormDataPart("imageFile3", file3.getName(), RequestBody.create(MediaType.parse("image/*"), file3))
                .build();*/
        return  observe(cateringInfoService.uploadFiles(multipartBody));
    }

    public Observable<HttpResponse<String>> saveBrand(String param){
        return  observe(cateringInfoService.saveBrand(param));
    }

    public Observable<HttpResponse<Store>> doLoad(String id){
        return  observe(cateringInfoService.doLoad(id));
    }
    public Observable<HttpResponse<String>> createQrCode(String storeId){
        return  observe(cateringInfoService.createQrCode(storeId));
    }


    public Observable<HttpResponse<String>> saveStore(String jsonStr){
        return  observe(cateringInfoService.saveStore(jsonStr));
    }

}
