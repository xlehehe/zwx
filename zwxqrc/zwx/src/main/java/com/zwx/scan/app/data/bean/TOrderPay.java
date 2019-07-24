package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class TOrderPay implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 企业ID
     */
    private Long compId;

    /**
     * t_order表主键
     */
    private Long orderId;

    /**
     * 支付渠道 支付宝——z 微信——w 银联——y
     */
    private String payWay;

    /**
     * 支付金额/积分/优惠券……
     */
    private BigDecimal payAmount;

    /**
     * 微信或支付宝或银行卡返回的支付流水号
     */
    private String payId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 付款完成时间
     */
    private String payTime;

    /**
     * 支付状态
     */
    private String result;
    
    
    /**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay == null ? null : payWay.trim();
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId == null ? null : payId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime == null ? null : payTime.trim();
    }
}