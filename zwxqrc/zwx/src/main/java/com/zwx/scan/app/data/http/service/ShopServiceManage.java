package com.zwx.scan.app.data.http.service;

import android.content.Context;

import com.zwx.scan.app.data.base.BaseServiceManager;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.MemberRedEnvelopeBean;
import com.zwx.scan.app.data.bean.MemberRedEnvelopeDetailBean;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.Order;
import com.zwx.scan.app.data.bean.OrderObjectBean;
import com.zwx.scan.app.data.bean.ProductCategory;
import com.zwx.scan.app.data.bean.ProductResultBean;
import com.zwx.scan.app.data.bean.ProductSetNew;
import com.zwx.scan.app.data.bean.ProductSetNewBean;
import com.zwx.scan.app.data.bean.ProductVerifictionRecordBean;
import com.zwx.scan.app.data.bean.ShopResultBean;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.TOrderConsumeLogBean;
import com.zwx.scan.app.data.bean.TOrderObject;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Query;

/**
 * author : lizhilong
 * time   : 2019/04/18
 * desc   :  商城接口服务管理
 * version: 1.0
 **/
public class ShopServiceManage  extends BaseServiceManager {

    private Context context;

    private ShopService shopService;


    public ShopServiceManage(){
        shopService = RetrofitServiceManager.getInstance().create(ShopService.class);
    }

    //订单管理列表接口
    public Observable<HttpResponse<OrderObjectBean>> doQueryOrder(String userId, String memebrTel, String salesTimeSta, String salesTimesEnd, Integer pageNumber, Integer pageSize){
        return  observe(shopService.doQueryOrder(userId,memebrTel,salesTimeSta,salesTimesEnd,pageNumber,pageSize));
    }

    //订单详情
    public Observable<HttpResponse<List<TOrderObject>>> doQueryOrderDetails(String detailedId){
        return  observe(shopService.doQueryOrderDetails(detailedId));
    }


    //订单详情
    public Observable<HttpResponse<TOrderObject>> queryOrderDetailForApp(String orderId){
        return  observe(shopService.queryOrderDetailForApp(orderId));
    }

    //确认订单
    public Observable<HttpResponse<String>> queryOrderConfirmPost( String detailedId, String postCompany, String postCode){
        return  observe(shopService.queryOrderConfirmPost(detailedId,postCompany,postCode));
    }

    //新增商品分类
    public Observable<HttpResponse<Map<String,String>>> addProductCategory(String catName,  String salesType,  String userId){
        return  observe(shopService.addProductCategory(catName,salesType,userId));
    }
    //修改商品分类
    public Observable<HttpResponse<String>> updateProductCategory(String catId,  String catName){
        return  observe(shopService.updateProductCategory(catId,catName));
    }

    //查询商品分类
    public Observable<HttpResponse<List<ProductCategory>>> queryProductCategory(String userId){
        return  observe(shopService.queryProductCategory(userId));
    }

    //删除商品分类
    public Observable<HttpResponse<String>> deleteProductCategory(String catId){
        return  observe(shopService.deleteProductCategory(catId));
    }

    //查询店铺列表
    public Observable<HttpResponse<List<Store>>> queryStoreByCompId(String userId){
        return  observe(shopService.queryStoreByCompId(userId));
    }

    //修改店铺联系电话
    public Observable<HttpResponse<String>> updateShopTel(String storeId,  String shopTel){
        return  observe(shopService.updateShopTel(storeId,shopTel));
    }

    //查询商品列表
    public Observable<HttpResponse<ProductSetNewBean>> queryProductSet(String userId,String productName,String productCode,
                                                                       Integer pageSize,
                                                                       Integer pageNumber){
        return  observe(shopService.queryProductSet(userId, productName,productCode,pageSize,pageNumber));
    }

    //删除商品
    public Observable<HttpResponse<String>> doDelete(String productId){
        return  observe(shopService.doDelete(productId));
    }
    //更新商品 上架 下架
    public Observable<HttpResponse<String>> doUpdate(String productId,String operateFlag,String stockNum){
        return  observe(shopService.doUpdate(operateFlag,productId,stockNum));
    }

    //调整商品库存
    public Observable<HttpResponse<Map<String,String>>> updateProductStock( String userId,String productId,String stockChange,String changeState){
        return  observe(shopService.updateProductStock(userId,productId,stockChange,changeState));
    }

    //获取店铺
    public Observable<HttpResponse<MoreStoreBean>> queryStoreList(String userId){
        return  observe(shopService.queryStoreList(userId));
    }

    //保存或上架
    public Observable<HttpResponse<ShopResultBean>> saveProductSetInfo(String jsonstr, String userId){
        return  observe(shopService.saveProductSetInfo(jsonstr,userId));
    }


    //商品详情编辑接口
    public Observable<HttpResponse<ProductResultBean>> doEdit(String productId){
        return  observe(shopService.doEdit(productId));
    }

    public Observable<HttpResponse<ProductSetNew>> queryProductInfo(String productId){
        return  observe(shopService.queryProductInfo(productId));
    }
    //根据搜索内容查询红包列表
    public Observable<HttpResponse<MemberRedEnvelopeBean>> queryAllMemberRedEnvlope(String jsonStr){
        return  observe(shopService.queryAllMemberRedEnvlope(jsonStr));
    }

    //红包查询列表
    public Observable<HttpResponse<Map<String,Object>>> queryAllMemberRedEnvlopeSum(String compId){
        return  observe(shopService.queryAllMemberRedEnvlopeSum(compId));
    }

    //红包查询列表详情
    public Observable<HttpResponse<MemberRedEnvelopeDetailBean>> queryAllMemberRedEnvlopeDeTail(String compId, String memberId, Integer pageSize, Integer pageNumber){
        return  observe(shopService.queryAllMemberRedEnvlopeDeTail(compId,memberId,pageSize,pageNumber));
    }

    //调整会员红包
    public Observable<HttpResponse<Map<String,Object>>> updateMemberBal(String compId,String memberId,String balanceChange
            ,String changeState,String changeReason,String balanceBefore){
        return  observe(shopService.updateMemberBal(compId,memberId,balanceChange,changeState,changeReason,balanceBefore));
    }

    //商品核销记录列表
    public Observable<HttpResponse<ProductVerifictionRecordBean>> countVerification(String storeId, String consumeStr,String consumeEnd, Integer pageNumber, Integer pageSize){
        return  observe(shopService.countVerification(storeId,consumeStr,consumeEnd,pageNumber,pageSize));
    }

    //商品核销记录列表详情
    public Observable<HttpResponse<TOrderConsumeLogBean>> doProductDetailQueryConsumeLog(String tel, String consumeTimeStr, String consumeTimeEnd, Integer pageNumber, Integer pageSize,String storeId){
        return  observe(shopService.doProductDetailQueryConsumeLog(consumeTimeStr,consumeTimeEnd,pageNumber,pageSize, storeId));
    }




    /**
     * 多个文件
     * */
    public Observable<HttpResponse<Map<String,Object>>> uploadFiles(List<File> fileList){

        MultipartBody.Builder builder = new MultipartBody.Builder();
        //上传多张图片
        if(fileList != null && fileList.size()>0){
            for (File file : fileList){
                builder.addFormDataPart("AndroidImageFile-"+file.getName(), file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
            }

        }
        MultipartBody multipartBody = builder.setType(MultipartBody.FORM).build();

        return  observe(shopService.uploadFiles(multipartBody));
    }

}
