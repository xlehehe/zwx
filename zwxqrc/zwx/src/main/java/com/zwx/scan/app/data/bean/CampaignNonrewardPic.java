package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * t_campaign_nonreward_pic
 * @yanshoufu 
 */
public class CampaignNonrewardPic implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 活动id
     */
    private Long campaignId;

    /**
     * 未中奖图片id
     */
    private String pictureId;

    private String prizeName;

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

	/**   
	 * <p>Title: toString</p>   
	 * <p>Description: </p>   
	 * @return   
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		return "CampaignNonrewardPic [id=" + id + ", campaignId=" + campaignId + ", pictureId=" + pictureId + "]";
	}
    
}