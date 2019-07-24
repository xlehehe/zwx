package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * t_campaign_praise
 * @yanshoufu 
 */
public class CampaignCollect implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 公司id
     */
    private Long compId;

    /**
     * 活动id
     */
    private Long campaignId;

    /**
     * 限制类型
     */
    private String limitType;

    /**
     * 限制次数
     */
    private Integer limitCount;

    /**
     * 有效时间类型，D-按天，H-小时
     */
    private String effectTimeFlag;

    /**
     * 有效时间，类型为D时存天数，类型为H时存小时
     */
    private Integer effectTime;

    private static final long serialVersionUID = 1L;
    
    /**
     * 集赞默认图片
     */
    private String defaultImg;
    
    

    /**
	 * @return the defaultImg
	 */
	public String getDefaultImg() {
		return defaultImg;
	}

	/**
	 * @param defaultImg the defaultImg to set
	 */
	public void setDefaultImg(String defaultImg) {
		this.defaultImg = defaultImg;
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

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getLimitType() {
        return limitType;
    }

    public void setLimitType(String limitType) {
        this.limitType = limitType;
    }

    public Integer getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

    public String getEffectTimeFlag() {
        return effectTimeFlag;
    }

    public void setEffectTimeFlag(String effectTimeFlag) {
        this.effectTimeFlag = effectTimeFlag;
    }

    public Integer getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Integer effectTime) {
        this.effectTime = effectTime;
    }
}