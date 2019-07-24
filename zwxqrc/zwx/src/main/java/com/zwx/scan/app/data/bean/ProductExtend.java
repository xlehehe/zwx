package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品扩展表t_product_extend
 */
public class ProductExtend implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 公司id
     */
    private Long compId;

    /**
     * 商品规格描述
     */
    private String productSpec;

    /**
     * product_set表主键
     */
    private Long productId;

    /**
     * 邮寄类型：0-自提，1-邮寄  2- 自提、邮寄
     */
    private String deliveryType;

    /**
     * 邮费
     */
    private BigDecimal deliveryFee;

    /**
     * 自提时间类型，R1-固定时长，A-固定时间
     */
    private String deliveryDaytype;

    /**
     * 自提开始日期
     */
    private String delivStartdate;

    /**
     * 自提结束日期
     */
    private String delivEnddate;

    /**
     * 自提有效天数
     */
    private Integer delivDay;

    /**
     * 销售开始日期
     */
    private String salesStartdate;

    /**
     * 销售结束日期
     */
    private String salesEnddate;

    /**
     * 商品详情
     */
    private String productDetail;

    /**
     * 买家须知
     */
    private String remark;

    /**
     * 起售数量
     */
    private Integer salesCount;

    public Integer getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(Integer salesCount) {
        this.salesCount = salesCount;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getDeliveryDaytype() {
        return deliveryDaytype;
    }

    public void setDeliveryDaytype(String deliveryDaytype) {
        this.deliveryDaytype = deliveryDaytype;
    }


    public String getDelivStartdate() {
        return delivStartdate;
    }

    public void setDelivStartdate(String delivStartdate) {
        this.delivStartdate = delivStartdate;
    }

    public String getDelivEnddate() {
        return delivEnddate;
    }

    public void setDelivEnddate(String delivEnddate) {
        this.delivEnddate = delivEnddate;
    }

    public Integer getDelivDay() {
        return delivDay;
    }

    public void setDelivDay(Integer delivDay) {
        this.delivDay = delivDay;
    }

    public String getSalesStartdate() {
        return salesStartdate;
    }

    public void setSalesStartdate(String salesStartdate) {
        this.salesStartdate = salesStartdate;
    }

    public String getSalesEnddate() {
        return salesEnddate;
    }

    public void setSalesEnddate(String salesEnddate) {
        this.salesEnddate = salesEnddate;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}