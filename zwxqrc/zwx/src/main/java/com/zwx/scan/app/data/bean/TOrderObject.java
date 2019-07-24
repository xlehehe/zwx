package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品订单综合输出字段
 * @author DongWei
 * @date 2019年4月6日
 */
public class TOrderObject implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 商品主图缩略图文件id
     */
    private String thumbnailUrl;
    
    /**
     * 年月日+五位数，从0开始计步，步长为1
     */
    private Long orderCode;
    
    /**
     * 销售时间
     */
    private String salesTime;
    
    /**
     * 商品名称
     */
    private String productName;
    
    /**
     * 商品编码
     */
    private String productCode;
    
    /**
     * 购买数量
     */
    private Long buyCount;
    
    /**
     * 销售金额（eg: ￥39+200积分)
     */
    private String factSalesPrice;
    
    /**
     * 会员名称
     */
    private String memberName;
    
    /**
     * 会员手机
     */
    private String memberTel;
    
    /**
     * 1: 自提  2:邮寄
     */
    private String delivType;
    
    /**
     * 0: 未兑换，1：已兑换   2 待邮寄 3 已邮寄
     */
    private String status;
    
    /**
     * 明细Id  t_order_details 主键
     */
    private Long detailedId;
    
    /**
     * 订单id  t_order 主键
     */
    private Long orderId;
    
    /**
     * 商品id  t_product_set 主键
     */
    private Long productId;
    
    /**
     * 会员cod   t_member 主键
     */
    private Long memberId;
    
    /**
     * 主键  t_product_media主键
     */
    private Long id;
    
    /**
     * 优惠券状态
     */
    private String couponStatus;
    
    /**
     * 买家留言
     */
    private String buyMsg;

    /**
     * 支付渠道 支付宝——z 微信——w 银联——y
     */
    private String payWay;
    
    /**
     * 会员商品券Id
     */
    private Long couponId;
    
    /**
     * 微信回调流水号
     */
    private String payId;
    
    /**
     * 核销时间
     */
    private String consumeTime;
    
    /**
     * 核销店铺
     */
    private Long storeId;
    
    /**
     * 核销店铺名称
     */
    private String storeName;
    
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    
    /**
     * 商品数量
     */
    private Integer sumnum;
    
    /**
     * 订单总价
     */
    private BigDecimal payAmount;
    
    
    /**
     * 商品积分
     */
    private BigDecimal unitScore;
    
    /**
     * 订单的是否自提状态 
     */
    private String sta;
    
    /**
     * 可用店铺组
     */
    private String storeIdArr;
    
    /**
     * 失效时间
     */
    private String expireDate;
    
    /**
     * 市场价
     */
    private BigDecimal marketPrice;
    
    /**
     * 规格
     */
    private String productSpec;
    
    /**
     * 企业Id
     */
    private Long compId;
    
    /**
     * 商品可用时间开始
     */
    private String vaildDate;
    
    /**
     * 商品详情
     */
    private String productDetail;
    
    /**
     * 核销张数
     */
    private Integer cou;
    
    /**
     * 使用红包额度
     */
    private BigDecimal useRedEnvelope;
    
    /**
     * 最终付款额度
     */
    private BigDecimal price;

    /**
     * 收货地址
     */
    private String memberAddress;

    /**
     * 收货电话
     */
    private String receiverTel;

    /**
     * 收件人
     */
    private String receiver;


    /**
     * 快递 公司
     */
    private String postCompany;

    /**
     * 快递 单号
     */
    private String postCode;

    /**
     * 预计明日 到账金额
     */
    private BigDecimal tomorrowAmt;

    /**
     * 未到账金额
     */
    private BigDecimal futureAmt;
    private String estimateTime;

    public String getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(String estimateTime) {
        this.estimateTime = estimateTime;
    }

    public BigDecimal getTomorrowAmt() {
        return tomorrowAmt;
    }

    public void setTomorrowAmt(BigDecimal tomorrowAmt) {
        this.tomorrowAmt = tomorrowAmt;
    }

    public BigDecimal getFutureAmt() {
        return futureAmt;
    }

    public void setFutureAmt(BigDecimal futureAmt) {
        this.futureAmt = futureAmt;
    }

    public String getPostCompany() {
        return postCompany;
    }

    public void setPostCompany(String postCompany) {
        this.postCompany = postCompany;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }

    public String getReceiverTel() {
        return receiverTel;
    }

    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Integer getCou() {
        return cou;
    }

    public void setCou(Integer cou) {
        this.cou = cou;
    }

    public BigDecimal getUseRedEnvelope() {
        return useRedEnvelope;
    }

    public void setUseRedEnvelope(BigDecimal useRedEnvelope) {
        this.useRedEnvelope = useRedEnvelope;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }



    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }


    public String getStoreIdArr() {
        return storeIdArr;
    }

    public void setStoreIdArr(String storeIdArr) {
        this.storeIdArr = storeIdArr;
    }


    public String getSta() {
        return sta;
    }

    public void setSta(String sta) {
        this.sta = sta;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getUnitScore() {
        return unitScore;
    }

    public void setUnitScore(BigDecimal unitScore) {
        this.unitScore = unitScore;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getSumnum() {
        return sumnum;
    }

    public void setSumnum(Integer sumnum) {
        this.sumnum = sumnum;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(String couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getBuyMsg() {
        return buyMsg;
    }

    public void setBuyMsg(String buyMsg) {
        this.buyMsg = buyMsg;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }


    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Long getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(Long orderCode) {
        this.orderCode = orderCode;
    }


    public String getSalesTime() {
        return salesTime;
    }

    public void setSalesTime(String salesTime) {
        this.salesTime = salesTime;
    }

    public String getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(String consumeTime) {
        this.consumeTime = consumeTime;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getVaildDate() {
        return vaildDate;
    }

    public void setVaildDate(String vaildDate) {
        this.vaildDate = vaildDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Long buyCount) {
        this.buyCount = buyCount;
    }

    public String getFactSalesPrice() {
        return factSalesPrice;
    }

    public void setFactSalesPrice(String factSalesPrice) {
        this.factSalesPrice = factSalesPrice;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberTel() {
        return memberTel;
    }

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }

    public String getDelivType() {
        return delivType;
    }

    public void setDelivType(String delivType) {
        this.delivType = delivType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getDetailedId() {
        return detailedId;
    }

    public void setDetailedId(Long detailedId) {
        this.detailedId = detailedId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




    
}
