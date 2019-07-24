package com.zwx.scan.app.feature.cateringinfomanage;

import android.content.Context;
import android.view.View;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.google.gson.Gson;
import com.luck.picture.lib.entity.LocalMedia;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.EncryptUtils;
import com.zwx.library.utils.LogUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.base.BaseObserver;
import com.zwx.scan.app.data.bean.Area;
import com.zwx.scan.app.data.bean.Brand;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CateBean;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.ResultBean;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.StoreBean;
import com.zwx.scan.app.data.bean.StoreParamBean;
import com.zwx.scan.app.data.bean.StoreResult;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.data.http.service.CampaignServiceManager;
import com.zwx.scan.app.data.http.service.CateringInfoServiceManager;
import com.zwx.scan.app.data.http.service.RetrofitServiceManager;
import com.zwx.scan.app.feature.cateringinfomanage.adapter.Address;
import com.zwx.scan.app.feature.cateringinfomanage.adapter.AddressBean;
import com.zwx.scan.app.feature.cateringinfomanage.adapter.AreaPickerView;
import com.zwx.scan.app.feature.couponmanage.GiveCouponNextFragment;
import com.zwx.scan.app.feature.member.MemberInfoListAdapter;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
public class CateringInfoPresenter implements CateringInfoContract.Presenter {

    private  CateringInfoContract.View view;
    private CateringInfoServiceManager cateringInfoServiceManager;

    //添加订阅，应用于取消订阅，防止造成内存泄漏
    private CompositeDisposable disposable;




    public CateringInfoPresenter(CateringInfoContract.View view) {
        cateringInfoServiceManager = new CateringInfoServiceManager();
        this.view = view;
        disposable = new CompositeDisposable();
    }

    @Override
    public void doQuery(Context context, String brandId, Integer pageNumber, Integer pageSize,boolean isRefresh) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("brandId",brandId);
        params.put("pageNumber",String.valueOf(pageNumber));
        params.put("pageSize",String.valueOf(pageSize));
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        CateringinfoManageActivity activity = (CateringinfoManageActivity) context;
        cateringInfoServiceManager.doQuery(brandId,pageNumber,pageSize)
                .subscribe(new BaseObserver<StoreResult>(context,false){
                    @Override
                    public void onSuccess(StoreResult storeResults, String msg) {
                        String userId = SPUtils.getInstance().getString("userId");

                        List<Store> storeList = new ArrayList<>();
                        if(storeResults!=null ){
                            storeList = storeResults.getData();
                            if(storeList !=null){
                                if(isRefresh){
                                    activity.storeList.clear();
                                    activity.storeList.addAll(storeList);
                                    if(storeList.size()<10 ){
                                        activity.mAdapter.notifyDataSetChanged();
                                        activity.ptr.refreshComplete();
                                        activity.ptr.setLoadMoreEnable(false);
                                    }else {
                                        activity.mAdapter.notifyDataSetChanged();
                                        activity.ptr.refreshComplete();
                                        activity.ptr.setLoadMoreEnable(true);
                                    }
                                }else {
                                    activity.storeList.addAll(storeList);
                                    activity.mAdapter.notifyDataSetChanged();
                                    if(storeList.size()<10){
                                        activity.ptr.refreshComplete();
                                        activity.ptr.loadMoreComplete(false);
                                    }else {
                                        activity.ptr.refreshComplete();
                                        activity.ptr.loadMoreComplete(true);
                                    }
                                }
                            }
                        }else{
                            activity.ptr.refreshComplete();
                            activity.ptr.setLoadMoreEnable(false);
                        }
                        doQueryByCurrentUser(context, userId);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.ptr.refreshComplete();
                        activity.ptr.setLoadMoreEnable(false);
                    }
                });
    }

    @Override
    public void doQueryByCurrentUser(Context context, String userId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        CateringinfoManageActivity activity = (CateringinfoManageActivity) context;
        cateringInfoServiceManager.doQueryByCurrentUser(userId)
                .subscribe(new BaseObserver<List<Brand>>(context,false){
                    @Override
                    public void onSuccess(List<Brand> brands, String msg) {
                        String userId = SPUtils.getInstance().getString("userId");


                        if(brands != null && brands.size() > 0){
                            Brand brand = brands.get(0);
                            activity.brand = brand;
                            activity.brandStory = brand.getStory();
                            activity.brandName = brand.getName();

                            if(activity.brandStory!=null && activity.brandStory.length()>0){

                                activity.tvStory.setText("品牌故事："+activity.brandStory);
                            }else {
                                activity.tvStory.setText("品牌故事：");
                            }
                            if(activity.brandName!=null && activity.brandName.length()>0){

                                activity.tvName.setText(activity.brandName);
                            }else {
                                activity.tvName.setText("");
                            }
                            activity.brandLogo = HttpUrls.BRAND_LOGO_ULR+brand.getLogo();
                            activity.setBrandLogo();
                        }else {
                            activity.tvStory.setText("品牌故事：");
                            activity.tvName.setText("");
                            activity.brandLogo = HttpUrls.BRAND_LOGO_ULR;
                            activity.setBrandLogo();
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                        activity.tvStory.setText("品牌故事：");
                        activity.tvName.setText("");
                        activity.brandLogo = HttpUrls.BRAND_LOGO_ULR;
                        activity.setBrandLogo();
                    }
                });


    }

    @Override
    public void upload(Context context, File file,String flag) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);


        cateringInfoServiceManager.upload(file)
                .subscribe(new BaseObserver<Map<String,Object>>(context,false){
                    @Override
                    public void onSuccess(Map<String,Object> map, String msg) {
                        String imageId = "";
                        if(map != null && map.size()>0){
                            imageId =(String)map.get("id");
                        }
//                        ToastUtils.showShort(msg);
                         BrandSettingActivity brandSettingActivity = null;
                         StoreInfoManageActivity storeInfoManageActivity = null;
                        if("brandEdit".equals(flag)){
                            brandSettingActivity = (BrandSettingActivity) context;

                            brandSettingActivity.imageId = imageId;
                        }else {
                            storeInfoManageActivity = (StoreInfoManageActivity) context;
                            storeInfoManageActivity.photoList.add(imageId);
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);


                    }
                });

    }

    @Override
    public void uploadFile(Context context, List<File> fileList,final StoreParamBean storeBean) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);


        cateringInfoServiceManager.uploadFiles(fileList)
                .subscribe(new BaseObserver<Map<String,Object>>(context,false){
                    @Override
                    public void onSuccess(Map<String,Object> map, String msg) {
                        StoreInfoManageActivity activity = (StoreInfoManageActivity) context;
                        String imageId = "";
                        if(map != null && map.size()>0){
                            imageId =(String)map.get("id");
                            StringBuilder idSb = new StringBuilder();
                            if(imageId != null && imageId.length()>0){
                                if(imageId.contains(",")){
                                    String[] imageIds = imageId.split(",");

                                    if(imageIds != null && imageIds.length>0){
                                        for (int i = 0;i<imageIds.length;i++){
                                            String id = imageIds[i];
                                            idSb.append(imageIds[i]).append(",");
                                            activity.photoList.add(id);
                                        }


                                        if(idSb.toString().length()>0){
                                            activity.photos = idSb.toString().substring(0,idSb.toString().length() - 1);
                                            storeBean.setPhotos(activity.photos);
                                            saveStore(context,storeBean);
                                        }
                                    }
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);


                    }
                });

    }

    @Override
    public void delete(Context context, String imageId,Integer deletePosition) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("id", imageId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);


        cateringInfoServiceManager.delete(imageId)
                .subscribe(new BaseObserver<String>(context,false){
                    @Override
                    public void onSuccess(String result, String msg) {
                        LogUtils.e("图片文件删除成功!");
                        for (int i = 0 ;i <StoreInfoManageActivity.photoList.size();i++){
                            if(i == deletePosition){
                                StoreInfoManageActivity.photoList.remove(i);
                                i--;
                                break;
                            }
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);


                    }
                });

    }

    @Override
    public void saveBrand(Context context, Brand obj) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        Gson gson = new Gson();
        String jsonStr = gson.toJson(obj);

//        params.put("jsonStr",gson.toJson(obj));
        params.put("jsonStr",gson.toJson(obj));
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        BrandSettingActivity activity = (BrandSettingActivity) context;
        cateringInfoServiceManager.saveBrand(jsonStr)
                .subscribe(new BaseObserver<String>(context,false){
                    @Override
                    public void onSuccess(String result, String msg) {
                        ToastUtils.showCustomShortBottom(msg);

                        ActivityUtils.startActivity(CateringinfoManageActivity.class, R.anim.slide_in_right,R.anim.slide_out_left);

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort("修改失败");


                    }
                });
    }


    @Override
    public void doLoad(Context context, String storeId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("id",storeId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        StoreInfoManageActivity activity = (StoreInfoManageActivity) context;
        cateringInfoServiceManager.doLoad(storeId)
                .subscribe(new BaseObserver<Store>(context,true){
                    @Override
                    public void onSuccess(Store store, String msg) {
                        if(store != null){
                            activity.store = store;
                            if(activity.store.getBrand()!= null){
                                activity.brandName = activity.store.getBrand().getName();
                            }
                            activity.directoryDataCP = store.getDirectoryDataCP();

                            if(activity.directoryDataCP != null && activity.directoryDataCP.size()>0){
                                StringBuilder nameSb = new StringBuilder();
                                StringBuilder idSb = new StringBuilder();
                                String  dishContent = store.getDishContents();
                                for (int j = 0;j<activity.directoryDataCP.size();j++){
                                    CateBean typeBean = activity.directoryDataCP.get(j);

                                    String value1 = typeBean.getValue();
                                    if(dishContent.contains(",")){
                                        String[] dishContents = dishContent.split(",");

                                        if(dishContents != null && dishContents.length>0){
                                            for (int i = 0;i<dishContents.length;i++){
                                                String value2 = dishContents[i];
                                                if(value2.equals(value1)){
                                                    String name = typeBean.getKey()!=null?typeBean.getKey()+"/":"";
                                                    nameSb.append(name);
                                                    idSb.append(typeBean.getValue()).append(",");
                                                }
                                            }
                                        }
                                    }

                                }
                                if(idSb != null && idSb.length()>0){
                                    activity.categoriesId = idSb.toString().substring(0,idSb.toString().length() - 1);

                                }

                                if(nameSb.toString() != null && nameSb.toString().length()>0){
                                    activity.categoriesName = nameSb.toString().substring(0,nameSb.toString().length() - 1);
                                }else {
                                    activity.categoriesName = "";
                                }
                            }else {
                                activity.categoriesName = "";
                            }
                            activity.main_categories.setText(activity.categoriesName);

                            activity.directoryDataCT = store.getDirectoryDataCT();

                            if(activity.directoryDataCT != null && activity.directoryDataCT.size()>0){
                                StringBuilder nameSb = new StringBuilder();
                                StringBuilder idSb = new StringBuilder();
                                String serviceContent = store.getServiceContents();
                                for (int j = 0;j<activity.directoryDataCT.size();j++){
                                    CateBean typeBean = activity.directoryDataCT.get(j);
                                    String value1 = typeBean.getValue();

                                    if(serviceContent.contains(",")){

                                        String[] serviceContents = serviceContent.split(",");

                                        if(serviceContents != null && serviceContents.length>0){
                                            for (int i = 0;i<serviceContents.length;i++){
                                                String value2 = serviceContents[i];
                                                if(value2.equals(value1)){
                                                    String name = typeBean.getKey()!=null?typeBean.getKey()+"/":"";
                                                    nameSb.append(name);
                                                    idSb.append(typeBean.getValue()).append(",");
                                                }
                                            }
                                        }
                                    }

                                }
                                if(idSb != null && idSb.length()>0){
                                    activity.serviceId = idSb.toString().substring(0,idSb.toString().length() - 1);
                                }
                                if(nameSb.toString() != null && nameSb.toString().length()>0){
                                    activity.serviceName = nameSb.toString().substring(0,nameSb.toString().length() - 1);
                                }else {
                                    activity.serviceName = "";
                                }

                            }else {
                                activity.serviceName = "";
                            }

                            activity.render_services.setText(activity.serviceName);

                            activity.directoryDataYX = store.getDirectoryDataYX();
                            if(activity.directoryDataYX != null && activity.directoryDataYX.size()>0){
                                StringBuilder nameSb = new StringBuilder();
                                StringBuilder idSb = new StringBuilder();
                                String banquetType = store.getBanquetTypes();
                                for (int j = 0;j<activity.directoryDataYX.size();j++){
                                    CateBean typeBean = activity.directoryDataYX.get(j);
                                    String value1 = typeBean.getValue();

                                    if(banquetType.contains(",")){

                                        String[] banquetTypes = banquetType.split(",");

                                        if(banquetTypes != null && banquetTypes.length>0){
                                            for (int i = 0;i<banquetTypes.length;i++){
                                                String value2 = banquetTypes[i];
                                                if(value2.equals(value1)){
                                                    String name = typeBean.getKey()!=null?typeBean.getKey()+"/":"";
                                                    nameSb.append(name);
                                                    idSb.append(typeBean.getValue()).append(",");
                                                }
                                            }
                                        }
                                    }
                                }
                                if(idSb != null && idSb.length()>0){
                                    activity.banquetId = idSb.toString().substring(0,idSb.toString().length() - 1);
                                }

                                if(nameSb.toString() != null && nameSb.toString().length()>0){
                                    activity.banquetName = nameSb.toString().substring(0,nameSb.toString().length() - 1);
                                }else {
                                    activity.banquetName = "";
                                }
                            }else {
                                activity.banquetName ="";
                            }
                            activity.render_banquet.setText(activity.banquetName);
//                            activity.main_categories.setText(store.getDishContents()!=null ? store.getDishContents():"");
//                            activity.render_services.setText(store.getServiceContents() != null ? store.getServiceContents() : "");
//                            activity.render_banquet.setText(store.getBanquetTypes() != null ? store.getBanquetTypes() : "");
                            activity.storeName = store.getName();
                            activity.managerName = store.getManagerName();
                            activity.managerTel = store.getManagerTel();
                            activity.storeTel = store.getTelphone();
                            activity.serviceDate = store.getServiceDate();
                            activity.edtTime.setText(activity.serviceDate != null && !"".equals(activity.serviceDate) ? activity.serviceDate : "");
                            activity.tvStoreName.setText(store.getName() != null ?store.getName(): "");
                            activity.edtManagerName.setText(store.getManagerName() != null ? store.getManagerName() : "");
                            activity.edtManagerPhone.setText(store.getManagerTel() != null ?store.getManagerTel(): "");
                            activity.edtStorePhone.setText(store.getTelphone() != null ? store.getTelphone() : "");

                            activity.storeType = store.getType();
                            if(activity.storeType!=null && "1".equals(store.getType())){
                                activity.bus_mode.setText("直营");
                            }else if(store.getType()!=null && "2".equals(store.getType())){
                                activity.bus_mode.setText("加盟");
                            }else {
                                activity.storeType = "";
                                activity.bus_mode.setText("");
                            }

                            activity.address = store.getAddress();
                            activity.edtAddress.setText(activity.address!= null ?activity.address:"");
                            Area province = store.getProvince();
                            Area city = store.getCity();
                            Area dist = store.getDist();


                            String pro = "";
                            String cit = "";
                            String distr = "";
                            int arr[] = new int[3];
                            if( city != null){
                                activity.cityId = city.getId();
                                activity.cityName = city.getName();
                                arr = new int[2];
                            }

                            if( dist != null){
                                activity.distId = dist.getId();
                                activity.distName = dist.getName();
                                arr = new int[3];
                            }


                            if(province != null){
                                activity.provinceName = province.getName();
                                activity.provinceId = province.getId();
                                if(activity.addressBeans!=null && activity.addressBeans.size()>0){
                                    for (AddressBean addressBean : activity.addressBeans){
                                        String value =addressBean.getValue();
                                        if(activity.provinceId !=0){
                                            if(activity.provinceId == Long.parseLong(value)){
                                                activity.provinceName = addressBean.getLabel();
                                                activity.distPostion = 1;
                                            }
                                        }
                                        List<AddressBean.CityBean> cityBeanList = addressBean.getChildren();
                                        if(cityBeanList!=null && cityBeanList.size()>0){
                                            for (AddressBean.CityBean cityBean : cityBeanList) {
                                                String cityBeanValue = cityBean.getValue();
                                                if (activity.cityId != 0) {
                                                    if (activity.cityId == Long.parseLong(cityBeanValue)) {
                                                        activity.cityName = cityBean.getLabel();
                                                        activity.cityPostion = 2;
                                                    }
                                                }

                                                List<AddressBean.CityBean.AreaBean> areaBeanList = cityBean.getChildren();
                                                if(areaBeanList!=null && areaBeanList.size()>0){
                                                    for (AddressBean.CityBean.AreaBean areaBean : areaBeanList) {
                                                        String areaBeanValue = areaBean.getValue();
                                                        if (activity.distId != 0) {
                                                            if (activity.distId == Long.parseLong(areaBeanValue)) {
                                                                activity.distName = areaBean.getLabel();
                                                                activity.distPostion = 3;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                            }




                            if(activity.addressBeans!= null && activity.addressBeans.size()>0){
                                for (int i=0;i< activity.addressBeans.size();i++){
                                    AddressBean address  = activity.addressBeans.get(i);
                                   String proviceId = address.getValue();
                                    String provId = String.valueOf(activity.provinceId);
                                   if(provId != null && !"".equals(provId) ){
                                       if(proviceId.equals(provId)){
                                           arr[0] = i;
                                           activity.provinceSelected = i;
                                           activity.oldProvinceSelected = i;
                                       }
                                   }

                                    List<AddressBean.CityBean> cityBeanList1 =address.getChildren() ;
                                    if(cityBeanList1 != null && cityBeanList1.size()>0){
                                        for (int j=0;j<cityBeanList1.size();j++){

                                            AddressBean.CityBean cityBean1 = cityBeanList1.get(j);
                                            String cityId1 = address.getValue();
                                            String cityId = String.valueOf(activity.cityId);
                                            if(cityId != null && !"".equals(cityId) ){
                                                if(cityId1.equals(cityId)){
                                                    arr[1] = j;
                                                    activity.citySelected = j;
                                                    activity.oldCitySelected = j;
                                                }
                                            }
                                            List<AddressBean.CityBean.AreaBean> areaBeanList1 =cityBean1.getChildren();
                                            if(areaBeanList1 != null && areaBeanList1.size()>0){
                                                for (int k=0;k<areaBeanList1.size();k++){
                                                    AddressBean.CityBean.AreaBean areaBean1 = areaBeanList1.get(k);
                                                    String distId1 = areaBean1.getValue();
                                                    String distId = String.valueOf(activity.distId);
                                                    if(distId != null && !"".equals(distId) ){
                                                        if(distId1.equals(distId)){
                                                            arr[2] = k;
                                                            activity.areaSelected = k;
                                                            activity.oldAreaSelected = k;
                                                        }
                                                    }

                                                }
                                            }

                                        }
                                    }
                                }
                            }
                            activity.i = arr;
                            activity.tvProCityDist.setText(activity.provinceName + "-" +activity.cityName + "-" +activity.distName);

                            Double lat = store.getGeoLat();
                            Double lng = store.getGeoLng();
                            activity.lp = new LatLonPoint(lat, lng);
                            activity.latLng = new LatLng(lat,lng);

                            activity.startMoveLocationAndMap(activity.latLng);
                            activity.initMapView();
                            String edtAddre = activity.provinceName+activity.cityName+activity.distName+activity.address;
                            if(activity.cityName != null && !"".equals(activity.cityName)){
                                if(activity.address != null && !"".equals(activity.address)){
                                    activity.getLatlon(edtAddre);
                                }
                            }

                            activity.startTime = "";
                            activity.endTime = "";
                            if(store.getServiceStartTime()!= null && store.getServiceStartTime().length()>0){
                                activity.startTime = store.getServiceStartTime() ;
                            }

                            if(store.getServiceEndTime()!= null && store.getServiceEndTime().length()>0){
                                activity.endTime = store.getServiceEndTime() ;
                            }

//                            activity.start_time.setText(activity.startTime);
//                            activity.end_time.setText(activity.endTime);


//                            activity.edtManagerName.setText(store.getManagerName() != null ? store.getManagerName() : "");

                            activity.photos = store.getPhotos();
                            List<LocalMedia> urls = new ArrayList<>();
                            activity.selectList.clear();

                            if(activity.photos != null && !"".equals(activity.photos)){
                                if(activity.photos.contains(",")){
                                    String[] photos = activity.photos.split(",");

                                    for (int i = 0 ;i<photos.length;i++){
                                        LocalMedia localMedia = new LocalMedia();
                                        localMedia.setCompressPath(HttpUrls.BRAND_LOGO_ULR+photos[i]);
                                        localMedia.setPath(HttpUrls.BRAND_LOGO_ULR+photos[i]);
                                        localMedia.setCompressed(true);
                                        activity.photoList.add(photos[i]);
                                        activity.selectList.add(localMedia);

                                    }

                                }else {
                                    LocalMedia localMedia = new LocalMedia();
                                    localMedia.setCompressPath(HttpUrls.BRAND_LOGO_ULR+activity.photos);
                                    localMedia.setPath(HttpUrls.BRAND_LOGO_ULR+activity.photos);
                                    localMedia.setCompressed(true);
                                    activity.photoList.add(activity.photos);
                                    activity.selectList.add(localMedia);
                                }
                            }

                            activity.adapter.setList(activity.selectList);
                            activity.adapter.notifyDataSetChanged();
                            createQrCode(context,storeId);
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);


                    }
                });
    }

    @Override
    public void createQrCode(Context context, String storeId) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

//        params.put("jsonStr",gson.toJson(obj));
        params.put("storeId",storeId);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        StoreInfoManageActivity activity = (StoreInfoManageActivity) context;
        cateringInfoServiceManager.createQrCode(storeId)
                .subscribe(new BaseObserver<String>(context,false){
                    @Override
                    public void onSuccess(String result, String msg) {

                        if(!result.isEmpty()){
                            activity.qrCodeId = result;
                            activity.ll_qrc.setVisibility(View.VISIBLE);
                            activity.setQrc();
                            activity.initDownLoad();
                        }else{
                            activity.ll_qrc.setVisibility(View.GONE);
                        }


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.ll_qrc.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void saveStore(Context context, StoreParamBean store) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());
        Gson gson = new Gson();
        String jsonStr = gson.toJson(store);

//        params.put("jsonStr",gson.toJson(obj));
        params.put("obj",gson.toJson(store));
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
//        StoreInfoManageActivity activity = (StoreInfoManageActivity) context;
        cateringInfoServiceManager.saveStore(jsonStr)
                .subscribe(new BaseObserver<String>(context,false){
                    @Override
                    public void onSuccess(String result, String msg) {
                        ToastUtils.showCustomShortBottom(msg);

                        ActivityUtils.startActivity(CateringinfoManageActivity.class, R.anim.slide_in_right,R.anim.slide_out_left);

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showCustomShortBottom("保存失败");

                    }
                });
    }


}
