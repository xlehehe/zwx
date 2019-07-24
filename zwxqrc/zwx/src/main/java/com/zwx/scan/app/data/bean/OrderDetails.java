package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 商品明细表
 * @author DongWei
 * @date 2018年12月20日
 */
public class OrderDetails implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 明细Id
     */
    private Long detailedId;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 出售商品类型 会员卡——m 优惠券——c
     */
    private String salesType;

    /**
     * 销售产品的id(会员卡id，优惠券id)
     */
    private Long sellGoodsId;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 营销类型常规出售商品——r促销商品——p
     */
    private String marketingType;

    /**
     * 实际销售金额 用户实际支付金额是，销售金额—立减金额
     */
    private BigDecimal factSalesPrice;

    /**
     * 购买数量
     */
    private Long buyCount;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType == null ? null : salesType.trim();
    }

    public Long getSellGoodsId() {
        return sellGoodsId;
    }

    public void setSellGoodsId(Long sellGoodsId) {
        this.sellGoodsId = sellGoodsId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getMarketingType() {
        return marketingType;
    }

    public void setMarketingType(String marketingType) {
        this.marketingType = marketingType == null ? null : marketingType.trim();
    }

    public BigDecimal getFactSalesPrice() {
        return factSalesPrice;
    }

    public void setFactSalesPrice(BigDecimal factSalesPrice) {
        this.factSalesPrice = factSalesPrice;
    }

    public Long getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Long buyCount) {
        this.buyCount = buyCount;
    }
}