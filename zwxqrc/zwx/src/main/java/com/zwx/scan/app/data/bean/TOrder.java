package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class TOrder implements Serializable {
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
     * 订单商品的id，购卡时是会员类型的id，拼团时是活动的id，商城商品是空值
     */
    private Long productId;
    
    /**
     * 复选框选择
     */
    private String productIds;

    /**
     * 消费者Id
     */
    private Long memberId;

    /**
     * 销售时间
     */
    private String salesTime;

    /**
     * 该字段以后不再做记录，将支付属性转移到order_pay中，使用orderID关联
     */
    private String payChannel;

    /**
     * 销售金额(单价)商城中该字段以后也不做记录，商品支付可能会是积分+现金，转移到order_pay
     */
    private BigDecimal unitPrice;

    /**
     * 购买数量
     */
    private Long buyCount;

    /**
     * 订单状态 已发起——0 付款成功——1 付款失败——2 申请退款——3 审核退款——4 退款中——5 退款成功——6 该字段会影响到收款流水查询
     */
    private String orderState;

    /**
     * 员工id，会员id，非会员open_id
     */
    private String fromId;

    /**
     * S-员工，M-会员，O-非会员，
     */
    private String fromType;

    /**
     * 订单类型，购卡M，拼团G，商城商品P
     */
    private String orderType;
    
    /**
     * 复选框选择
     */
    private String orderTypes;

    /**
     * 年月日+五位数，从0开始计步，步长为1
     */
    private Long orderCode;

    /**
     * 买家留言
     */
    private String buyMsg;

    /**
     * 0: 自提  1:邮寄
     */
    private String delivType;
    
    /**
     * 使用红包金额
     */
    private BigDecimal useRedEnvelope;
    
    /**
     * 实付现金金额
     */
    private BigDecimal useCash;
    
    /**
     * 会员手机号
     */
    private String memberTel;
    
    /**
     * 会员名称
     */
    private String memberName;
    
    /**
     * 推荐会员名称
     */
    private String recommendMemberName;
    
    /**
     * 推荐会员手机号
     */
    private String recommendMemberTel;
    
    /**
     * 商品代号
     */
    private String productCode; 
    
    /**
     * 统计
     */
    private Long coun;
    
    /**
     * 销售总金额
     */
    private BigDecimal amount;
    
    /**
     * 支付总金额
     */
    private BigDecimal useCashAmount;
    
    /**
     * 红包抵现总金额
     */
    private BigDecimal useRedEnvelopeAmount;
    
    /**
     * 出售类型
     */
    private String orderTypeName;
    
    /**
     * 店铺名称
     */
    private String storeName;
    
    /**
     * 销售人ID
     */
    private Long staffId;
    
    /**
     * 销售人
     */
    private String staffName;
    
    /**
     * 销售人电话
     */
    private String staffTel;
    
    private String memberBuy;
    
    private String productName;
    
    
    
	/**
	 * @return the memberBuy
	 */
	public String getMemberBuy() {
		return memberBuy;
	}

	/**
	 * @param memberBuy the memberBuy to set
	 */
	public void setMemberBuy(String memberBuy) {
		this.memberBuy = memberBuy;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the useCash
	 */
	public BigDecimal getUseCash() {
		return useCash;
	}

	/**
	 * @param useCash the useCash to set
	 */
	public void setUseCash(BigDecimal useCash) {
		this.useCash = useCash;
	}

	/**
	 * @return the useRedEnvelope
	 */
	public BigDecimal getUseRedEnvelope() {
		return useRedEnvelope;
	}

	/**
	 * @param useRedEnvelope the useRedEnvelope to set
	 */
	public void setUseRedEnvelope(BigDecimal useRedEnvelope) {
		this.useRedEnvelope = useRedEnvelope;
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

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId == null ? null : fromId.trim();
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType == null ? null : fromType.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public Long getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(Long orderCode) {
        this.orderCode = orderCode;
    }

    public String getBuyMsg() {
        return buyMsg;
    }

    public void setBuyMsg(String buyMsg) {
        this.buyMsg = buyMsg == null ? null : buyMsg.trim();
    }

	/**
	 * @return the delivType
	 */
	public String getDelivType() {
		return delivType;
	}

	/**
	 * @param delivType the delivType to set
	 */
	public void setDelivType(String delivType) {
		this.delivType = delivType;
	}

    public String getMemberTel() {
        return memberTel;
    }

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getRecommendMemberName() {
        return recommendMemberName;
    }

    public void setRecommendMemberName(String recommendMemberName) {
        this.recommendMemberName = recommendMemberName;
    }

    public String getRecommendMemberTel() {
        return recommendMemberTel;
    }

    public void setRecommendMemberTel(String recommendMemberTel) {
        this.recommendMemberTel = recommendMemberTel;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getCoun() {
        return coun;
    }

    public void setCoun(Long coun) {
        this.coun = coun;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getUseCashAmount() {
        return useCashAmount;
    }

    public void setUseCashAmount(BigDecimal useCashAmount) {
        this.useCashAmount = useCashAmount;
    }

    public BigDecimal getUseRedEnvelopeAmount() {
        return useRedEnvelopeAmount;
    }

    public void setUseRedEnvelopeAmount(BigDecimal useRedEnvelopeAmount) {
        this.useRedEnvelopeAmount = useRedEnvelopeAmount;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffTel() {
        return staffTel;
    }

    public void setStaffTel(String staffTel) {
        this.staffTel = staffTel;
    }

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    public String getOrderTypes() {
        return orderTypes;
    }

    public void setOrderTypes(String orderTypes) {
        this.orderTypes = orderTypes;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

}