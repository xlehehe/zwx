package com.zwx.scan.app.data.http;

public class HttpUrls {
    //本地
//    public static final String BASE_URL = "http://192.168.10.103:8080/bapp/";
    public static final String POSTER_BASE_URL = "http://192.168.10.37:8585/aumc/";
//    public static final String POSTER_BASE_URL = "http://bapp.quyongshan.com/";
    //测试环境37 == 28
    public static final String BASE_URL = "http://192.168.10.37:8585/bapp/";
    //开发环境 37
//    public static final String BASE_URL = "http://192.168.10.37:8080/bapp/";
    //线网正式
//    public static final String BASE_URL = "http://bapp.quyongshan.com/";
//    public static final String BASE_URL = "http://quyongshan.com/bapp/";
    //线网测试库
//    public static final String BASE_URL = "http://zwxtech.net/bapp/";
//    public static final String POSTER_BASE_URL = "http://bapp.quyongshan.com/";
    //线网开发库
//    public static final String BASE_URL = "http://zwxtech.cn/bapp/";

    public static final String LOGIN_URL = "api/tokens/login.do";
    public static final String TOKEN_AUTH_URL = "api/tokens/info.do";

    //判断用户是否存在`
    public static final String CHECK_USERNAME_URL = "api/tokens/checkUsername.do";

    //退出登录
    public static final String LOGOUT_URL = "api/tokens/logout.do";
    //会员卡核销
    public static final String WECHAT_URL = "api/verification/appVerificationMemberType.do";

    //优惠券核销
    public static final String COUPON_URL = "api/verification/appVerificationMemCoupon.do";
    //商品核销
    public static final String VERIFICATION_GOOD_URL = "api/verification/verificationProduct.do";


    //商品核销
    public static final String VERIFICATION_PLAT_COUPON_URL = "api/verification/verificationPlatCoupon.do";
    public static final String QUERY_STORE_URL = "api/index/queryStoreByUserId.do";

    //查询权限资源信息
    public static final String QUERY_RESOURCE_URL = "api/index/queryResource.do";

    //查询首页报表信息
    public static final String INDEX_URL = "api/index/index.do";


    public static final String VE_RECORD_URL = "api/verification/queryVerificationRecord.do";


    public static final String VE_LIST_URL = "api/verification/queryVerificationList.do";

    public static final String VE_LIST_DETAIL_URL = "api/verification/queryVerificationDetail.do";


    public static final String MEMBER_ANALYSIS_URL = "api/compmember/queryMemberCount.do";


    public static final String MEMBER_LIST_URL = "api/compmember/queryMemTypeCountList.do";


    public static final String MEMBER_LIST_DETAIL_URL = "api/compmember/queryMemTypeCountListDetail.do";

    //多个店铺会员统计分析
    public static final String MORE_STORE_MEMBER_ANALYSIS_URL = "api/compmember/queryMoreStoreMemberCountAnalysis.do";

    //多个店铺各个会员卡情况列表
    public static final String MORE_STORE_MEMBER_LIST_URL = "api/compmember/queryMoreStoreMemTypeCountList.do";

    //多个店铺各个会员卡情况详情报表
    public static final String MORE_STORE_MEMBER_LIST_DETAIL_URL = "api/compmember/queryMoreStoreMemTypeCountListDetail.do";

    public static final String STAFF_ANALYSIS_URL = "api/staff/queryStaffRewardTotalList.do";


    public static final String STAFF_LIST_URL = "api/staff/queryStaffRewardRankTotalList.do";

    public static final String MORE_STORE_STAFF_ANALYSIS_URL = "api/staff/queryMoreStoreStaffRewardTotalList.do";


    public static final String MORE_STORE_STAFF_LIST_URL = "api/staff/queryMoreStoreStaffRewardRankTotalList.do";



    public static final String CAMPAIGN_ANALYSIS_URL = "api/campaign/queryCampaignCountanalysis.do";

    public static final String CAMPAIGN_MORE_ANALYSIS_URL = "api/campaign/queryMoreStoreCampaignCountanalysis.do";

    public static final String MORE_STORE_CAMPAIGN_MORE_ANALYSIS_URL = "api/campaign/queryMoreStoreWholeAndCouponCount.do";

    public static final String MORE_STORE_SPECIFIC_URL = "api/campaign/queryMoreSpecificCampaignCountList.do";
    public static final String MORE_STORE_CAMPAIGN_WHOLE_SPECIFIC_URL = "api/campaign/queryMoreStoreWholeCount.do";

    public static final String CAMPAIGN_LIST_URL = "api/campaign/querySpecificCampaignCountList.do";

    public static final String CAMPAIGN_DETAIL_REPORT_URL = "api/campaign/queryCountByCampaignIdAndDate.do";

    public static final String CAMPAIGN_DETAIL_LIST_URL = "api/campaign/queryCampaignTotalDetail.do";


    //检查是否当前品牌的会员
    public static final String CHECK_MEMBER_URL = "api/newStaff/checkIsBrandMember.do";


    //获取店铺可用的二维码
    public static final String LIST_QRC_URL = "api/newStaff/listAvailableByStoreId.do";


    //获取会员信息
    public static final String LOAD_MEMBER_URL = "api/newStaff/loadMemberById.do";


    //

    //检查手机用户是否公司会员
    public static final String CHECK_MOBILE_URL = "api/staffWork/checkMobile.do";


    //新增员工
    public static final String STAFF_INSERT_URL = "api/staffWork/doInsert.do ";

    //POST / 离职多个员工
    public static final String DO_LEAVE_URL = "api/staffWork/doLeave.do";

    //POST /编辑员工
    public static final String DO_STAFF_UPDATE_URL = "api/staffWork/doUpdate.do";
    /// 员工列表页查询
    public static final String STAFF_MEMBER_LIST_URL = "api/staffWork/list.do";

    //  根据ID获取员工
    public static final String STAFF_LOAD_URL = "api/staffWork/load.do";


    //获取职位
    public static final String CAT_LIST_URL = "api/directory/listByCatId.do";

    public static final String MORE_STORE_URL = "api/index/queryBrandAndStoreList.do";
    public static final  String IMAGE_ULR = BASE_URL+"file/get.do?id=";
    public static final  String BRAND_LOGO_ULR = BASE_URL+"file/get.do?id=";


    public static final String MEMBER_CONSUME_LIST_URL = "api/compmember/comsumeListByMap.do";
//public static final String MEMBER_CONSUME_LIST_URL = "api/compmember/queryComsumeList.do";
    public static final String MEMBER_TYPE_LIST_URL = "api/membertype/doQueryMemberType.do";

//    public static final String MEMBER_CONSUME_LIST_URL = "api/compmember/checkIsBrandMember.do";

    //消费记录详情
    public static final String MEMBER_CONSUME_DETAIL_LIST_URL = "api/comsumeLog/doQuery.do";

    //会员信息列表

    public static final String MEMBER_INFO_LIST_URL = "api/compmember/memberListByMap.do";


    public static final String DO_MEMBER_INFO_URL = "api/compmember/doGetMemberType.do";


    //会员消费记录
    public static final String MEMBER_CONSUME_LOG_LIST__URL = "api/comsumeLog/doQueryComsumeLog.do";

    //会员卡列表
    public static final String DO_MEMBER_TYPE_URL = "api/membertype/doQueryMemberType.do";

    //活动列表
    public static final String QUERY_CAMPAIGN_LIST_URL = "api/campaign/doQueryCampaignList.do";

    //删除活动
    public static final String CAMPAIGN_DELETE_URL = "api/campaign/doDelete.do";

    //提前结束和作废
    public static final String CAMPAIGN_UPDATE_URL = "api/campaign/doUpdate.do";

    //优惠券类型
    public static final String COUPON_TYPE_LSIT_URL = "api/campaign/queryCouponTypeList.do";

    //优惠券列表
    public static final String COUPON_LIST_URL = "api/campaign/queryCoupon.do";

    //海报列表
    public static final String POSTER_LIST_URL = "api/campaign/queryPoster.do";

    public static final String SAVE_CAMPAIGN_URL = "api/campaign/saveCampaignInfo.do";

    public static final String DO_CAMPAIGN_EDIT_URL = "api/campaign/doEdit.do";
    public static final String DO_CAMPAIGN_COPY_URL = "api/campaign/copyCampaign.do";
    public static final String DO_CATERING_LIST_URL = "api/store/doQuery.do";
    //GET /api/brand/doQueryByCurrentUser.do
    public static final String DO_CATERING_BRAND_URL = "api/brand/doQueryByCurrentUser.do";

    public static final String UPDATE_BRAND_URL = "api/brand/doUpdate.do";

    public static final String UPLOAD_AVATAR_URL = "api/file/upload.do";
    public static final String DELETE_AVATAR_URL = "api/file/delete.do";
    ///api/brand/doUpdate.do  编辑保存品牌
    public static final String SAVE_BRAND_URL = "api/brand/doUpdate.do";


    public static final String CATE_SELECT_STORE_URL = "api/store/doLoad.do";
    public static final String CATE_QR_CODE_URL = "api/store/createQrCode.do";

    public static final String CATE_SAVE_STORE_URL = "api/store/doUpdate.do";

    public static final String MY_CHECK_PSD_URL = "api/user/checkPsd.do";
    public static final String MY_QUERY_USER_URL = "api/user/queryUser.do";

    public static final String MY_UPDATE_USER_URL = "api/user/doUpdate.do";

    //账号管理
    public static final String ACCOUNT_MANAGE_LIST_URL = "api/user/doQuery.do";

    //消息分类
    public static final String PER_MESSAGE_TYPE_URL = "api/message/doMessageCategory.do";
    public static final String PER_MESSAGE_LIST_URL = "api/message/doQueryUserMessage.do";
    public static final String MESSAGE_STATUS_URL = "api/message/doStatus.do";

    //查询消息最新管理
    public static final String DO_MESSAGE_STATUS_URL = "api/message/doMessageStatus.do";
    //活动管理  会员卡列表
    public static final String COMMEMBER_TYPE_URL = "api/campaign/queryCommemberTypeByUserId.do";

    public static final String CAMPAIGN_ANALYSIS_COUPON_URL = "api/campaign/campaignAnalysisForzj.do";
    public static final String CAMPAIGN_ANALYSIS_COUPON_DETAIL_URL = "api/campaign/queryzjCountDetail.do";

    public static final String CAMPAIGN_ANALYSIS_COUPON_PT_DETAIL_URL = "api/campaign/queryPtCountDetail.do";

    // 账号管理
    public static final String ACCOUNT_MANAGE_STORE_URL = "api/store/listTreeByCurrentUser.do";
    // 角色列表
    public static final String ACCOUNT_MANAGE_ROLE_URL = "api/role/listByType.do";

    public static final String ACCOUNT_MANAGE_INSERT_URL = "api/user/insertCateingUser.do";


    public static final String ACCOUNT_MANAGE_CHECK_USERNAME_URL = "api/user/checkUserNameRepeat.do";

    public static final String ACCOUNT_MANAGE_INSERT_USER_URL = "api/user/insertCateingUser.do";
    public static final String ACCOUNT_MANAGE_EDIT_USER_URL = "api/user/editCateingUser.do";
    public static final String ACCOUNT_MANAGE_ROLE_LIST_URL = "api/resource/resourceTreeListByRoleId.do";




    public static final String CONTRACT_MANAGE_LIST_URL = "api/contract/doQueryAllContract.do";
    public static final String CONTRACT_MANAGE_CONTENT_URL = "api/contract/doQueryContract.do";

    //默认图素材
    public static final String CAMPAIGN_ME_POSTER_URL = "api/campaign/queryTigerPoster.do";



    //老虎机&砸金蛋
    //保存
    public static final String DO_LH_PT_SAVE_EDIT_URL = "api/campaign/saveCampaignInfoForgame.do";
    //编辑
    public static final String DO_LH_PT_EDIT_URL = "api/campaign/doEditForGame.do";
    //缴费
    public static final String RECEIVEFUND_LIST_URL = "api/receiveFund/doQueryAllByStatus.do";


    public static final String RECEIVEFUND_DETAIL_URL = "api/receiveFund/doQueryByFundId.do";


    public static final String DO_QUERY_MONEY_TN_URL = "api/receiveFund/doQueryMoneyByIds.do ";


    //拼团
    public static final String PIN_TUAN_NEW_URL = "api/campaigngroupbuy/saveCampaignInfoForgame.do ";

    //拼团统计分析
    public static final String CAMPAIGN_ANALYSIS_PT_COUPON_DETAIL_URL = "api/campaigngroupbuy/queryPtCountDetail.do";

    public static final String CAMPAIGN_ANALYSIS_PT_LIST_URL = "api/campaigngroupbuy/campaignAnalysisForzj.do";


    //拼团订单
    public static final String PIN_TUAN_ORDER_LIST_URL = "api/campaigngroupbuy/doQueryGroupBuy.do ";


    //拼团
    public static final String PIN_TUAN_ORDER_LIST_DETAIL_URL = "api/campaigngroupbuy/groupBuyCampaginRunning.do ";



    /**
     * y优惠券管理
     * */
    public static final String COUPON_MANAGE_LIST_URL = "api/coupon/doQueryByCompId.do ";

    public static final String CHECK_COUPON_STATUS_LIST_URL = "api/coupon/checkStatus.do ";

    public static final String UPDATE_COUPON_STATUS_LIST_URL = "api/coupon/doStatus.do ";

    public static final String INSERT_COUPON_URL = "api/coupon/doInsert.do ";
    public static final String UPDATE_COUPON_URL = "api/coupon/doUpdate.do ";

    public static final String LOAD_COUPON_URL = "api/coupon/doLoad.do ";

    /**
     * 意见反馈
     * */
    public static final String FEED_LIST = "api/Feedback/doList.do";

    public static final String FEED_SAVE = "api/Feedback/doSave.do";

    public static final String FEED_QUERY = "api/Feedback/doQuery.do";

    public static final String FEED_UN_READ= "api/Feedback/doFlag.do";


    /**
     * 推送扫码相关
     * */

    public static final String PUSH_TYPE_LIST = "api/pushnotify/getPushType.do";

    public static final String PUSH_CONTENT_TYPE_LIST = "api/pushnotify/getContentByType.do";

    public static final String PUSH_UPDADE = "api/pushnotify/doUpdate.do";
    public static final String PUSH_LOAD = "api/pushnotify/doLoad.do";

    //推送模板消息 预览
    public static final String PUSH_PRE = "api/pushnotify/getContentByMessage.do";

    public static final String PUSH_PRE_LIST = "api/pushnotify/getContentListByMessage.do";

    //版本更新
    public static final String DOWNLOAD_VERSION = "api/version/doLoad.do";

    /**
     * 会员卡流水
     * */
    public static final String MEMBER_CARD_ORDER_TOTAL = "api/order/selectToCounByOrder.do";

    public static final String MEMBER_CARD_ORDER_TOTAL_DETAIL = "api/order/selectToITByOrder.do";

    /**
     * 会员卡管理
     * */
    public static final String DO_MEMBER_TYPE_LIST = "api/membertype/list.do";

    //会员卡启用 删除
    public static final String DO_MEMBER_STATUS = "api/membertype/memberTypeOperation.do";

    //获取模板和素材
    public static final String DO_TEMPLACARD= "api/membertype/doTemplaCard.do";

    //新增会员卡
    public static final String INSERT_MEMBER_TYPE= "api/membertype/doInsert.do";
    //更新
    public static final String UPDATE_MEMBER_TYPE= "api/membertype/doUpdate.do";

    //详情
    public static final String SELECT_MEMBER_TYPE= "api/membertype/doQueryByGroup.do";
    //根据用户id判断该品牌下的所有店铺是否都设置了收款账号
    public static final String MEMBER_QIYNOG= "api/membertype/isReceivingAccount.do";


    /**
     * 商城
     * */
    //订单列表
    public static final String DO_QUERY_ORDER_LIST= "api/order/doQueryOrder.do";

    //查询订单详情
    public static final String DO_QUERY_ORDER_DETAIL= "api/order/doQueryOrderDetails.do";
    //查询单个订单
    public static final String SELECT_ORDER_DAN_GE= "api/order/queryOrderDetailForApp.do";

    //确认邮寄
    public static final String DO_QUERY_ORDER_POST= "api/order/confirmPost.do";
    //新增商品分类
    public static final String DO_ADD_CATEGORY= "api/shop/addProductCategory.do";

    public static final String DO_UPDATE_CATEGORY= "api/shop/updateProductCategory.do";

    //查询商品分类
    public static final String DO_QUERY_CATEGORY= "api/shop/queryProductCategory.do";

    //删除商品分类
    public static final String DO_DELETE_CATEGORY= "api/shop/deleteProductCategory.do";

    //修改店铺电话
    public static final String DO_UPDATE_SHOP_TEL= "api/shop/updateShopTel.do";

    public static final String DO_QUERY_STORE_LIST= "api/shop/queryStoreByCompId.do";
    //商城管理 ->商品查询
    public static final String DO_QUERY_PRODUCT_SET= "api/shop/queryProductSet.do";

    //删除商品
    public static final String DO_DELETE_PRODUCT_SET= "api/shop/doDelete.do";

    //列表上架 下架  取消热卖 设为热卖

    public static final String DO_UPDATE_PRODUCT_SET= "api/shop/doUpdate.do";

    //调整商品库存
    public static final String DO_UPDATE_PRODUCT_STOCK= "api/shop/updateProductStock.do";


    //保存或者上架商品信息
    public static final String DO_INSERT_PRODUCT_SET= "api/shop/saveProductSetInfo.do";
    //商品详情
    public static final String SELECT_PRODUCT_SET= "api/shop/doEdit.do";

    //局部刷新调用单个商品接口
    public static final String SELECT_DANGE_PRODUCT_SET= "api/shop/queryProductInfo.do";

    //红包查询列表
    public static final String SELECT_ALL_MEMBER_RED_ENVELOPE= "api/shop/queryAllMemberRedEnvlope.do";

    //统计红包持有金额
    public static final String SELECT_ALL_MEMBER_RED_ENVELOPE_SUM= "api/shop/queryAllMemberRedEnvlopeSum.do";

    //红包查询列表详情

    public static final String SELECT_ALL_MEMBER_RED_ENVELOPE_DETAIL= "api/shop/queryAllMemberRedEnvlopeDeTail.do";
    //修改红包持有金额
    public static final String RED_ENVELOPE_UPDATE_MEMBER_BAL= "api/shop/updateMemberBal.do";

    //商品核销记录列表
    public static final String PRODUCT_VERIFICATION_RECORD= "api/verification/countVerification.do";
    //详情
    public static final String PRODUCT_VERIFICATION_RECORD_DETAIL= "api/verification/doQueryConsumeLog.do";


    //支付认证状态查询
    public static final String SELECT_PAY_AUTH_STATUS= "api/tradeWithdrawalController/selectPaymentAuthStatus.do";

    //支付认证查询
    public static final String SELECT_PAY_AUTH_INFO= "api/tradeWithdrawalController/selectPaymentAuthInfo.do";

    ///api/tradeWithdrawalController/sendVerificationCodeByBindPhone.do
    //绑定手机发送验证码
    public static final String SELECT_PAY_AUTH_SEND_VERIFICATION_CODE= "api/tradeWithdrawalController/sendVerificationCodeByBindPhone.do";
    //绑定手机号
    public static final String SELECT_PAY_AUTH_BIND_PHONE =  "api/tradeWithdrawalController/bindPhone.do";
   // POST /api/tradeWithdrawalController/setPersonalAuthInfo.do 设置个人认证信息
    //支付认证
    public static final String SELECT_PAY_AUTH_PERSONAL_OR_COMPANY_INFO= "api/tradeWithdrawalController/selectPersonalOrCompanyAuthInfo.do";
    //个人认证第一步
    public static final String SELECT_PAY_AUTH_PERSONAL_INFO= "api/tradeWithdrawalController/setPersonalAuthInfo.do";

    //创建通联会员
    public static final String SELECT_PAY_AUTH_CREATE_MEMBER =  "api/tradeWithdrawalController/createMember.do";

    //个人认证银行卡
    ///api/tradeWithdrawalController/bindBankCard.do
    public static final String SELECT_PAY_AUTH_BIND_BANK_CARD =  "api/tradeWithdrawalController/bindBankCard.do";

    //签订协议 获取html
    //api/tradeWithdrawalController/signContractNotice.do
    public static final String SELECT_PAY_AUTH_SIGN_CONTRACT_NOTICE =  "api/tradeWithdrawalController/SignContract.do";

    //解绑手机号
    public static final String SELECT_PAY_AUTH_UN_BIND_PHONE =  "api/tradeWithdrawalController/unBindPhone.do";

    //解绑发送验证码
    public static final String SELECT_PAY_AUTH_SEND_VERIFICATION_CODE_UN= "api/tradeWithdrawalController/sendVerificationCodeByUnBindPhone.do";

    ///api/tradeWithdrawalController/unbindBankCard.do
    //解绑银行卡
    public static final String SELECT_PAY_AUTH_UN_BIND_BANK_CARD =  "api/tradeWithdrawalController/unbindBankCard.do";

    // /api/tradeWithdrawalController/setCompanyAuthInfo.do  设置企业认证信息

    public static final String SELECT_PAY_AUTH_COMPANY_AUTH_INFO =  "api/tradeWithdrawalController/setCompanyAuthInfo.do";

    //交易提款 待结算金额查询
    public static final String SELECT_TRADE_DRAWAL =  "api/tradeWithdrawalController/selectTradeWithdrawal.do";

    //提现详情
    public static final String SELECT_TRADE_DRAWAL_DETAIL =  "api/tradeWithdrawalController/selectTradeWithdrawalDetails.do";

    //提现记录
    public static final String SELECT_TRADE_DRAWAL_RECORD =  "api/tradeWithdrawalController/selectTradeWithdrawalList.do";



    //立即提现查询
    public static final String SELECT_IMMEDIATE_TRADE_DRAWAL = "api/tradeWithdrawalController/selectImmediateWithdrawal.do";
    //立即体现
    public static final String IMMEDIATE_DRAWAL = "api/tradeWithdrawalController/immediateWithdrawal.do";

    public static final String SEND_TRADE_DRAWAL_CODE = "api/tradeWithdrawalController/sendVerificationCodeByImmediateWithdrawal.do";



    //待结算收款流水
    public static final String SELECT_COLLECTION_STATEMENT_LIST = "api/tradeWithdrawalController/selectUnSettlementCollectionStatementList.do";


    public static final String SELECT_COLLECTION_STATEMENT_LIST_DETAIL = "api/collectionStatement/selectCollectionStatementDetailsByType.do";


    //收款账号设置
    //设置状态查询
    public static final String SELECT_COLLECTION_ACCOUNT_SETTTING_STATUS = "api/pay/loadConfigByCompId.do";

    public static final String SELECT_COLLECTION_ACCOUNT_SETTTING_CHECK= "api/pay/checkWxPayInfo.do";
    //查看收款账号记录
    public static final String SELECT_COLLECTION_ACCOUNT_PAY_RECORD = "api/pay/loadWxPayRecord.do";


    //查看收款账号记录
    public static final String SELECT_COLLECTION_ACCOUNT_PAY_RECORD_DETAIL = "api/pay/queryOrderDetailForwxPay.do";
    //
    public static final String SELECT_COLLECTION_ACCOUNT_PAY_INFO = "api/pay/queryWxPayInfo.do";
    //
    public static final String SELECT_COLLECTION_ACCOUNT_PAY_LIST = "api/pay/queryWxPayList.do";
    //
    public static final String SELECT_COLLECTION_ACCOUNT_WX_PAY_RECORD = "api/pay/queryWxPayRecord.do";

    //
    public static final String SELECT_COLLECTION_ACCOUNT_WX_PAY_CONFIG = "api/pay/saveCompWxpayConfig.do";

    //财务中心员工销售报表相关接口
    //汇总列表
    public static final String SELECT_EMPLOYEE_SALES_REPORT_LIST = "api/employeeSalesReport/selectSalesByEmployee.do";

    //具体详情
    public static final String SELECT_EMPLOYEE_SALES_REPORT_DETAIL = "api/employeeSalesReport/selectSalesDetailsByEmployee.do";
    //详情
    public static final String SELECT_EMPLOYEE_SALES_REPORT_LIST_DETAIL = "api/employeeSalesReport/selectSalesListByEmployee.do";


    //收款到账详情
    public static final String SELECT_COLLECTION_TO_ACCOUNT_LIST_DETAIL = "api/pay/queryOrderDetailForwxPay.do";



}
