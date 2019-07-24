package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * 游戏活动实体类
 * @yanshoufu 
 */
public class CampaignGame implements Serializable {
	
	private static final long serialVersionUID = -7366932772862202247L;

	/**
     * 主键
     */
    private Long id;

    /**
     * 公司cod
     */
    private Long compId;

    /**
     * 活动cod
     */
    private Long campaignId;

    /**
     * 分享获得次数
     */
    private Integer shareGameCount;


    /**
     * 游戏限制次数
     */
    private Integer gameCount;

    /**
     * 素材cod
     */
    private String materialGameId;

    /**
     * 次数限制 每人每天D，每人P
     */
    private String limitType;
    
    /**
     * 游戏中奖次数
     */
    private Integer winCount;

    /**
     * 是否设置安慰奖，1-是，0-否
     */
    private String consolationPrizeFlag ;

    /**
     * 有效时间类型 D-按天，H-按小时
     */
    private String effectTimeFlag;
    
    /**
     * 有效时间
     */
    private Integer effectTime;
    
    
    /**
	 * @return the effectTimeFlag
	 */
	public String getEffectTimeFlag() {
		return effectTimeFlag;
	}

	/**
	 * @param effectTimeFlag the effectTimeFlag to set
	 */
	public void setEffectTimeFlag(String effectTimeFlag) {
		this.effectTimeFlag = effectTimeFlag;
	}

	/**
	 * @return the effectTime
	 */
	public Integer getEffectTime() {
		return effectTime;
	}

	/**
	 * @param effectTime the effectTime to set
	 */
	public void setEffectTime(Integer effectTime) {
		this.effectTime = effectTime;
	}

	/**
	 * @return the winCount
	 */
	public Integer getWinCount() {
		return winCount;
	}

	/**
	 * @param winCount the winCount to set
	 */
	public void setWinCount(Integer winCount) {
		this.winCount = winCount;
	}

	/**
	 * @return the consolationPrizeFlag
	 */
	public String getConsolationPrizeFlag() {
		return consolationPrizeFlag;
	}

	/**
	 * @param consolationPrizeFlag the consolationPrizeFlag to set
	 */
	public void setConsolationPrizeFlag(String consolationPrizeFlag) {
		this.consolationPrizeFlag = consolationPrizeFlag;
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

	/**
	 * @return the shareGameCount
	 */
	public Integer getShareGameCount() {
		return shareGameCount;
	}

	/**
	 * @param shareGameCount the shareGameCount to set
	 */
	public void setShareGameCount(Integer shareGameCount) {
		this.shareGameCount = shareGameCount;
	}

	/**
	 * @return the gameCount
	 */
	public Integer getGameCount() {
		return gameCount;
	}

	/**
	 * @param gameCount the gameCount to set
	 */
	public void setGameCount(Integer gameCount) {
		this.gameCount = gameCount;
	}

	/**
	 * @return the materialGameId
	 */
	public String getMaterialGameId() {
		return materialGameId;
	}

	/**
	 * @param materialGameId the materialGameId to set
	 */
	public void setMaterialGameId(String materialGameId) {
		this.materialGameId = materialGameId;
	}



    
}