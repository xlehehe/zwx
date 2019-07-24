package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * t_order_collect
 * @yanshoufu 
 */
public class OrderCollect implements Serializable {
    private Long id;

    private Long compId;

    /**
     * 订单id
     */
    private Long orderId;

    private String dealerCompId;

    /**
     * 0——到期自动结算
1——核销结算
2——发货
     */
    private String balanceType;

    /**
     * 预计结算时间
     */
    private String estimateTime;

    /**
     * 实际结算时间
     */
    private String balanceTime;

    /**
     * 经销商费用
     */
    private BigDecimal dealerFee;

    /**
     * 餐企费用
     */
    private BigDecimal compFee;

    /**
     * 平台费用
     */
    private BigDecimal platformFee;

    /**
     * 0——付款成功
1——待结算
2——已结算
     */
    private String status;

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    

    /**
	 * @return the dealerCompId
	 */
	public String getDealerCompId() {
		return dealerCompId;
	}

	/**
	 * @param dealerCompId the dealerCompId to set
	 */
	public void setDealerCompId(String dealerCompId) {
		this.dealerCompId = dealerCompId;
	}

	public String getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

    public String getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(String estimateTime) {
        this.estimateTime = estimateTime;
    }

    public String getBalanceTime() {
        return balanceTime;
    }

    public void setBalanceTime(String balanceTime) {
        this.balanceTime = balanceTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public BigDecimal getDealerFee() {
        return dealerFee;
    }

    public void setDealerFee(BigDecimal dealerFee) {
        this.dealerFee = dealerFee;
    }

    public BigDecimal getCompFee() {
        return compFee;
    }

    public void setCompFee(BigDecimal compFee) {
        this.compFee = compFee;
    }

    public BigDecimal getPlatformFee() {
        return platformFee;
    }

    public void setPlatformFee(BigDecimal platformFee) {
        this.platformFee = platformFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}