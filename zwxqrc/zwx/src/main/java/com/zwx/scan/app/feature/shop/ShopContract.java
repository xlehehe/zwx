package com.zwx.scan.app.feature.shop;

import android.content.Context;

import com.zwx.scan.app.base.BasePresenter;
import com.zwx.scan.app.base.BaseView;
import com.zwx.scan.app.data.bean.ProductExtendDesc;
import com.zwx.scan.app.feature.ptmanage.PtContract;

import java.io.File;
import java.util.List;
import java.util.Map;

import retrofit2.http.Query;

/**
 * author : lizhilong
 * time   : 2019/04/18
 * desc   :  商城
 * version: 1.0
 **/
public interface ShopContract {

    interface  View extends BaseView<ShopContract.Presenter> {

    }

    interface  Presenter extends BasePresenter {

        //订单列表
        void doQueryOrder(Context context,String userId,String memebrTel, String salesTimeSta, String salesTimesEnd,Integer pageNumber,Integer pageSize,Boolean isfresh);

        //订单详情
        void doQueryOrderDetails(Context context,String detailedId);

        //修改确认邮寄订单状态时 单个订单查询，实现订单列表局部刷新
        void queryOrderDetailForApp(Context context,String orderId);

        void queryOrderConfirmPost(Context context,String detailedId, String postCompany, String postCode);

        void addProductCategory(Context context,String catName,  String salesType,  String userId);

        void updateProductCategory(Context context,String catId,  String catName);
        void queryProductCategory(Context context,String userId,Boolean isfresh);
        void deleteProductCategory(Context context,String catId);
        void queryStoreByCompId(Context context,String userId);
        void updateShopTel(Context context,String storeId,  String shopTel);



        //查询商品
        void queryProductSet(Context context,String userId,String productName,String productCode,Integer pageSize,Integer pageNumber,boolean isRefresh);
        //进入编辑页面修改内容 返回列表局部刷新调用
        void queryProductInfo(Context context,String productId);
        //删除
        void doDelete(Context context,String productId);
        //更新商品
        void doUpdate(Context context,String productId,String operateFlag,String stockNum);


        void updateProductStock(Context context, String userId,String productId,String stockChange,String changeState);
        //查询店铺
        void queryStoreList(Context context,String userId);

        //新建商品查询分类列表
        void queryCategory(Context context,String userId);
        void addCategory(Context context,String catName,  String salesType,  String userId);

        void uploadFiles(Context context, List<File> fileList);

        void saveProductSetInfo(Context context, Map<String,Object> params, String userId,String isSaveAndPush);
        //商品详情
        void doEdit(Context context,String productId);


        /**
         * 红包查询相关接口
         * */

        void queryAllMemberRedEnvlope(Context context,String memberName, String memberTel, String compId, Integer pageSize, Integer pageNumber,boolean isRefresh,boolean isSearchData);
        //统计红包持有金额
        void queryAllMemberRedEnvlopeSum(Context context,String userId);
        //红包详情
        void queryAllMemberRedEnvlopeDeTail(Context context,String compId, String memberId, Integer pageSize, Integer pageNumber,boolean isRefresh);
        //调整红包金额
        void updateMemberBal(Context context,String compId,String memberId,String balanceChange
                ,String changeState,String changeReason,String balanceBefore);

        void countVerification(Context context,String storeId, String consumeStr,String consumeEnd, Integer pageNumber, Integer pageSize,boolean isRefresh);
        //商品核销
        void doProductDetailQueryConsumeLog(Context context,String tel, String consumeTimeStr, String consumeTimeEnd, Integer pageNumber, Integer pageSize,String storeId,boolean isRefresh);

        //商品详情 图片
        void uploadGoodDetailImageFiles(Context context, List<ProductExtendDesc> productExtendDescList);
    }
}
