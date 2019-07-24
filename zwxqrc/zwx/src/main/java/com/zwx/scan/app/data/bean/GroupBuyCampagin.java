package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GroupBuyCampagin implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 活动Id
     */
    private Long campaignId;
    /**
     * 活动名称
     */
    private String campaignName;
    
    /**
     * 人数
     */
    private Integer proSum;
    
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    
    /**
     * 总金额
     */
    private BigDecimal unitPriceTwo;

    /**
     * 会员手机号
     */
    private String memberTel;
    
    /**
     * 消费时间
     */
    private String salesTime;
    
    /**
     * 消费渠道 w-微信  z-支付宝  y-银联
     */
    private String payChannel;

    private String beginDate;
    private String endDate ;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getMemberTel() {
        return memberTel;
    }

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
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
        this.payChannel = payChannel;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public Integer getProSum() {
        return proSum;
    }

    public void setProSum(Integer proSum) {
        this.proSum = proSum;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPriceTwo() {
        return unitPriceTwo;
    }

    public void setUnitPriceTwo(BigDecimal unitPriceTwo) {
        this.unitPriceTwo = unitPriceTwo;
    }

}
