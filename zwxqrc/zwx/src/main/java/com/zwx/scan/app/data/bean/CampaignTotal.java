package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CampaignTotal implements Serializable{
	private static final long serialVersionUID = 764703541031763450L;

	/**
     * 活动号
     */
    private Long campaignId;

    /**
     * 公司号
     */
    private Long compId;

    /**
     * 参与人数
     */
    private Integer joinCount;

    /**
     * 浏览量(转发详情页和客得详情页浏览次数)
     */
    private Integer viewCount;

    /**
     * 转发量（调用转发到人的接口的次数）
     */
    private Integer forwardPersonCount;

    /**
     * 转发量（调用转发到朋友圈的接口的次数）
     */
    private Integer forwardTimelineCount;

    /**
     * 转发量（调用转发到人和朋友圈两个接口的次数）（总数）
     */
    private Integer fowardCount;

    /**
     * 发放优惠券数量()
     */
    private Integer sendCouponCount;

    /**
     * 领取优惠券数量
     */
    private Integer getCouponCount;

    /**
     * 回收优惠券数量
     */
    private Integer receiveCouponCount;

    /**
     * 新注册会员数
     */
    private Integer registerMemCount;
    
    /**
     * 统计日期
     */
    private String countDate;
    
    
    private Integer total;
    
  
    
    private String campaignName;
    
    private String campaignStatus;
    
    private String campaignWay;

    private String campaignType;

    private String sendName;

    private String memtypeName;

    private String couponName;

    private String storeId;
    private String storeName;

    /**
     * 活动持续时间
     */
    private String timeRange;

    /**
     * 活动天数
     */
    private Integer days;

    //收益
    private BigDecimal consumeAmt;

    /**优惠券id
     */
    private Long couponId;


    private BigDecimal unitPrice;

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public BigDecimal getConsumeAmt() {
        return consumeAmt;
    }

    public void setConsumeAmt(BigDecimal consumeAmt) {
        this.consumeAmt = consumeAmt;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getMemtypeName() {
        return memtypeName;
    }

    public void setMemtypeName(String memtypeName) {
        this.memtypeName = memtypeName;
    }

    public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getCampaignStatus() {
		return campaignStatus;
	}

	public void setCampaignStatus(String campaignStatus) {
		this.campaignStatus = campaignStatus;
	}

	public String getCampaignWay() {
		return campaignWay;
	}

	public void setCampaignWay(String campaignWay) {
		this.campaignWay = campaignWay;
	}

	

	public String getCountDate() {
		return countDate;
	}

	public void setCountDate(String countDate) {
		this.countDate = countDate;
	}

	public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public Integer getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(Integer joinCount) {
        this.joinCount = joinCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getForwardPersonCount() {
        return forwardPersonCount;
    }

    public void setForwardPersonCount(Integer forwardPersonCount) {
        this.forwardPersonCount = forwardPersonCount;
    }

    public Integer getForwardTimelineCount() {
        return forwardTimelineCount;
    }

    public void setForwardTimelineCount(Integer forwardTimelineCount) {
        this.forwardTimelineCount = forwardTimelineCount;
    }

    public Integer getFowardCount() {
        return fowardCount;
    }

    public void setFowardCount(Integer fowardCount) {
        this.fowardCount = fowardCount;
    }

    public Integer getSendCouponCount() {
        return sendCouponCount;
    }

    public void setSendCouponCount(Integer sendCouponCount) {
        this.sendCouponCount = sendCouponCount;
    }

    public Integer getGetCouponCount() {
        return getCouponCount;
    }

    public void setGetCouponCount(Integer getCouponCount) {
        this.getCouponCount = getCouponCount;
    }

    public Integer getReceiveCouponCount() {
        return receiveCouponCount;
    }

    public void setReceiveCouponCount(Integer receiveCouponCount) {
        this.receiveCouponCount = receiveCouponCount;
    }

    public Integer getRegisterMemCount() {
        return registerMemCount;
    }

    public void setRegisterMemCount(Integer registerMemCount) {
        this.registerMemCount = registerMemCount;
    }

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

    public String getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(String campaignType) {
        this.campaignType = campaignType;
    }
}