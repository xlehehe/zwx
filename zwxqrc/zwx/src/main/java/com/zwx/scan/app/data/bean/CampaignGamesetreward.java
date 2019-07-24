package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * t_campaign_gamesetreward
 * @author 
 */
public class CampaignGamesetreward implements Serializable {
	
	/**
	 * 主键 活动优惠券表主键
	 */
    private Long campCoupId;
    
    /**
     * 活动id
     */

    private Long campaignId;

    /**
     * 获奖概率
     */
    private BigDecimal prizeProb;

    /**
     * 奖品总数
     */
    private Integer prizeCount;

    /**
     * 奖品图片地址
     */
    private String prizeImageUrl;
    
    /**
     * 奖项id
     */
    private Long id;
    
    /**
     * 奖项类型 1-正常奖励，0-安慰奖
     */
    private String rewardType;
    //奖项循序
    private Integer prizeOrder;
    private String rewardName;

    private String prizeName;

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public Integer getPrizeOrder() {
        return prizeOrder;
    }

    public void setPrizeOrder(Integer prizeOrder) {
        this.prizeOrder = prizeOrder;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    private ArrayList<CampaignCoupon> list;

    public ArrayList<CampaignCoupon> getList() {
        return list;
    }

    public void setList(ArrayList<CampaignCoupon> list) {
        this.list = list;
    }

    /**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the rewardType
	 */
	public String getRewardType() {
		return rewardType;
	}

	/**
	 * @param rewardType the rewardType to set
	 */
	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	private static final long serialVersionUID = 1L;

    public Long getCampCoupId() {
        return campCoupId;
    }

    public void setCampCoupId(Long campCoupId) {
        this.campCoupId = campCoupId;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public BigDecimal getPrizeProb() {
        return prizeProb;
    }

    public void setPrizeProb(BigDecimal prizeProb) {
        this.prizeProb = prizeProb;
    }

    public Integer getPrizeCount() {
        return prizeCount;
    }

    public void setPrizeCount(Integer prizeCount) {
        this.prizeCount = prizeCount;
    }

    public String getPrizeImageUrl() {
        return prizeImageUrl;
    }

    public void setPrizeImageUrl(String prizeImageUrl) {
        this.prizeImageUrl = prizeImageUrl;
    }
}