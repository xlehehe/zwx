package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 订单表
 * @author DongWei
 * @date 2018年12月20日
 */
public class Order implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 企业Id
     */
    private Long compId;

    /**
     * 品牌Id
     */
    private Long brandId;

    /**
     * 店铺id——哪个店铺卖出去的
     */
    private Long storeId;

    /**
     * 商品Id
     */
    private Long productId;

    /**
     * 消费者Id
     */
    private Long memberId;

    /**
     * 销售时间
     */
    private String salesTime;

    /**
     * 支付渠道 支付宝——z 微信——w 银联——y
     */
    private String payChannel;

    /**
     * 销售金额(单价)
     */
    private BigDecimal unitPrice;

    /**
     * 购买数量
     */
    private Long buyCount;

    /**
     * 订单状态 已发起——0 付款成功——1 付款失败——2 申请退款——3 审核退款——4 退款中——5 退款成功——6
     */
    private String orderState;

    /**
     * 会员卡Id
     */
    private Long compMemtypeId;
    
    /**
     * 会员手机号
     */
    private String memberTel;
    
    /**
     * 会卡名称
     */
    private String memtypeName;
        
    /**
     * 商品代号
     */
    private String productCode; 
    
    /**
     * 统计
     */
    private Long coun;
    
    /**
     * 店铺名称
     */
    private String storeName;
    
    /**
     * 订单总金额
     */
    private Float totalFee;
    
    /**
     * 订单状态   0: 未兑换，1：已兑换
     */
    private Integer status;
    
    /**
     * 消费者openid
     */
    private String  openid;
    
    /**
     * 订单标题
     */
    private String title;

    /**
     * 自提 0: 自提  1:邮寄
     * */
    private String delivType;

    /**
     * 商品名称
     * */
    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDelivType() {
        return delivType;
    }

    public void setDelivType(String delivType) {
        this.delivType = delivType;
    }

    public Float getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Float totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Long getCoun() {
        return coun;
    }

    public void setCoun(Long coun) {
        this.coun = coun;
    }

    public String getMemberTel() {
        return memberTel;
    }

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }

    public String getMemtypeName() {
        return memtypeName;
    }

    public void setMemtypeName(String memtypeName) {
        this.memtypeName = memtypeName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getCompMemtypeId() {
        return compMemtypeId;
    }

    public void setCompMemtypeId(Long compMemtypeId) {
        this.compMemtypeId = compMemtypeId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
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

    public String getSalesTime() {
        return salesTime;
    }

    public void setSalesTime(String salesTime) {
        this.salesTime = salesTime;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel == null ? null : payChannel.trim();
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Long buyCount) {
        this.buyCount = buyCount;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState == null ? null : orderState.trim();
    }
}