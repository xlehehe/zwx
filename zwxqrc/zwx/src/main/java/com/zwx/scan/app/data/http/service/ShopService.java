package com.zwx.scan.app.data.http.service;

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
import com.zwx.scan.app.data.bean.StoreBean;
import com.zwx.scan.app.data.bean.TOrderConsumeLogBean;
import com.zwx.scan.app.data.bean.TOrderObject;
import com.zwx.scan.app.data.http.HttpUrls;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * author : lizhilong
 * time   : 2019/04/18
 * desc   : 商城
 * version: 1.0
 **/
public interface ShopService {



    //订单列表
    @GET(HttpUrls.DO_QUERY_ORDER_LIST)
    Observable<HttpResponse<OrderObjectBean>> doQueryOrder(@Query("userId") String userId,
                                                           @Query("memberTel") String memberTel,
                                                           @Query("salesTimeStr") String salesTimeStr,
                                                           @Query("salesTimeEnd") String salesTimeEnd,
                                                           @Query("pageNumber") Integer pageNumber,
                                                           @Query("pageSize") Integer pageSize);



    @GET(HttpUrls.DO_QUERY_ORDER_DETAIL)
    Observable<HttpResponse<List<TOrderObject>>> doQueryOrderDetails(@Query("detailedId") String detailedId);

    /**
     * @deprecated  确认邮寄 接口
     * @param detailedId 明细id
     * @param postCompany 快递公司
     * @param postCode 快递订单
     * */
    @POST(HttpUrls.DO_QUERY_ORDER_POST)
    Observable<HttpResponse<String>> queryOrderConfirmPost(@Query("detailedId") String detailedId,@Query("postCompany") String postCompany,@Query("postCode") String postCode);


    /**
     * @deprecated  查询单个订单，该接口用在 调用确认邮寄接口完成实现局部刷新数据
     * @param orderId 订单id
     * */
    @POST(HttpUrls.SELECT_ORDER_DAN_GE)
    Observable<HttpResponse<TOrderObject>> queryOrderDetailForApp(@Query("orderId") String orderId);
    //新增分类
    @FormUrlEncoded
    @POST(HttpUrls.DO_ADD_CATEGORY)
    Observable<HttpResponse<Map<String,String>>> addProductCategory(@Field("catName") String catName, @Field("salesType") String salesType, @Field("userId") String userId);



    //更新分类
    @GET(HttpUrls.DO_UPDATE_CATEGORY)
    Observable<HttpResponse<String>> updateProductCategory(@Query("catId") String catId,@Query("catName") String catName);

    //删除分类
    @GET(HttpUrls.DO_DELETE_CATEGORY)
    Observable<HttpResponse<String>> deleteProductCategory(@Query("catId") String catId);

    //查询分类
    @GET(HttpUrls.DO_QUERY_CATEGORY)
    Observable<HttpResponse<List<ProductCategory>>> queryProductCategory(@Query("userId") String userId);


    //更新店铺电话
    @GET(HttpUrls.DO_UPDATE_SHOP_TEL)
    Observable<HttpResponse<String>> updateShopTel(@Query("storeId") String storeId,@Query("shopTel") String shopTel);

    @GET(HttpUrls.DO_QUERY_STORE_LIST)
    Observable<HttpResponse<List<Store>>> queryStoreByCompId(@Query("userId") String userId);

    @GET(HttpUrls.DO_QUERY_PRODUCT_SET)
    Observable<HttpResponse<ProductSetNewBean>> queryProductSet(@Query("userId") String userId,
                                                                @Query("productName") String productName,
                                                                @Query("productCode") String productCode,
                                                                @Query("pageSize") Integer pageSize,
                                                                @Query("pageNumber") Integer pageNumber);

    //商品管理相关方法
    @FormUrlEncoded
    @POST(HttpUrls.DO_DELETE_PRODUCT_SET)
    Observable<HttpResponse<String>> doDelete(@Field("productId") String productId);


    @POST(HttpUrls.DO_UPDATE_PRODUCT_SET)
    Observable<HttpResponse<String>> doUpdate(@Query("operateFlag")String operateFlag,@Query("productId") String productId,@Query("stockNum")String stockNum);


    @POST(HttpUrls.DO_UPDATE_PRODUCT_STOCK)
    Observable<HttpResponse<Map<String,String>>> updateProductStock(@Query("userId") String userId,@Query("productId") String productId,@Query("stockChange")String stockChange,@Query("changeState")String changeState);

    @FormUrlEncoded
    @POST(HttpUrls.MORE_STORE_URL)
    Observable<HttpResponse<MoreStoreBean>> queryStoreList(@Field("userId") String userId);



    @POST(HttpUrls.DO_INSERT_PRODUCT_SET)
    Observable<HttpResponse<ShopResultBean>> saveProductSetInfo(@Query("jsonstr") String jsonstr, @Query("userId") String userId);
//    Observable<HttpResponse<ShopResultBean>> saveProductSetInfo(@Body Map<String,Object> params, @Query("userId") String userId);


    @GET(HttpUrls.SELECT_PRODUCT_SET)
    Observable<HttpResponse<ProductResultBean>> doEdit(@Query("productId") String productId);

    @POST(HttpUrls.SELECT_DANGE_PRODUCT_SET)
    Observable<HttpResponse<ProductSetNew>> queryProductInfo(@Query("productId") String productId);

    /**
     * 红包相关接口
     * */
    //红包列表
    @POST(HttpUrls.SELECT_ALL_MEMBER_RED_ENVELOPE)
    Observable<HttpResponse<MemberRedEnvelopeBean>> queryAllMemberRedEnvlope(@Query("jsonstr")String jsonstr);
//    Observable<HttpResponse<MemberRedEnvelopeBean>> queryAllMemberRedEnvlope(@Query("memberName")String memberName, @Query("memberTel") String memberTel, @Query("compId")String compId
//            , @Query("pageSize") Integer pageSize, @Query("pageNumber")Integer pageNumber);

    //统计持有收益
    @POST(HttpUrls.SELECT_ALL_MEMBER_RED_ENVELOPE_SUM)
    Observable<HttpResponse<Map<String,Object>>> queryAllMemberRedEnvlopeSum(@Query("compId") String compId);

    //红包详情
    @POST(HttpUrls.SELECT_ALL_MEMBER_RED_ENVELOPE_DETAIL)
    Observable<HttpResponse<MemberRedEnvelopeDetailBean>> queryAllMemberRedEnvlopeDeTail(@Query("compId") String compId, @Query("memberId") String memberId,
                                                                                         @Query("pageSize") Integer pageSize, @Query("pageNumber")Integer pageNumber);

    //调整红包金额
    @POST(HttpUrls.RED_ENVELOPE_UPDATE_MEMBER_BAL)
    Observable<HttpResponse<Map<String,Object>>> updateMemberBal(@Query("compId")String compId,@Query("memberId") String memberId,@Query("balanceChange")String balanceChange
            ,@Query("changeState") String changeState,@Query("changeReason")String changeReason,@Query("balanceBefore")String balanceBefore);


    //商品核销
    @GET(HttpUrls.PRODUCT_VERIFICATION_RECORD)
    Observable<HttpResponse<ProductVerifictionRecordBean>> countVerification(@Query("storeId") String storeId, @Query("consumeStr") String consumeStr, @Query("consumeEnd") String consumeEnd
            , @Query("pageNumber")Integer pageNumber, @Query("pageSize") Integer pageSize);


    /**
     * @deprecated  商品核销记录详情
     * @param consumeTimeStr 开始时间
     * @param consumeTimeEnd 结束时间
     * @param pageNumber 第几页
     * @param pageSize 一页显示记录数
     * @param storeId 店铺
     * */
    @GET(HttpUrls.PRODUCT_VERIFICATION_RECORD_DETAIL)
    Observable<HttpResponse<TOrderConsumeLogBean>> doProductDetailQueryConsumeLog(@Query("consumeTimeStr") String consumeTimeStr, @Query("consumeTimeEnd") String consumeTimeEnd
            , @Query("pageNumber")Integer pageNumber, @Query("pageSize") Integer pageSize,@Query("storeId") String storeId);



    //    @Multipart
    @POST(HttpUrls.UPLOAD_AVATAR_URL)
    Observable<HttpResponse<Map<String,Object>>> uploadFiles(@Body RequestBody body);





}
