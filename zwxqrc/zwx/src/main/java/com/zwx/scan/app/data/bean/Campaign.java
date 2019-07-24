package com.zwx.scan.app.data.bean;

import com.zwx.scan.app.data.http.HttpUrls;

import java.io.Serializable;
import java.util.Date;


 /**
 * 活动实体类
 *@author yanshoufu
 *@date 2018年8月30日
 */
public class Campaign implements Serializable{
	
	private static final long serialVersionUID = 4716396775848619806L;


    /**
     * 公司cod
     */
    private Long compId;

    /**
     * 活动cod
     */
    private Long campaignId;

    /**
     * 活动分类
     */
    private String campaignType;

    /**
     * 活动名称
     */
    private String campaignName;

    /**
     * 开始时间
     */
    private String beginDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 活动方式
     */
    
    private String campaignWay;

    /**
     * 活动状态
     */
    private String compaignStatus;

    /**
     * 展示时间
     */
    private String showDate;


    /**
     * 活动创建时间
     */
    private String createTime;

    /**
     * 活动创建人
     */
    private String createUser;

    /**
     * 修改时间
     */
    private String modifyTime;

    /**
     * 修改人
     */
//    private String modifyUser;

    /**
     * 活动海报编号
     */
    
    private String posterTemplete;
    /**
     * 店铺
     */
    private Store store;
    
    /**
     * 海报
     */
    private PosterMaterial poster;
    
    /**
     * 活动备注  
     */
    private String remark;
    
    /**
                 * 活动创建来源
     */
    private Integer client;

    private String wechatIcon;

	 /**
	  * 转发标题
	  */
	 private String shareTitle;

	 /**
	  * 转发描述
	  */
	 private String shareDesc;


	 private String grantType;

	 private String sendType;

	 private Boolean checked;
	 private MaterialGame game;

	 public MaterialGame getGame() {
		 return game;
	 }

	 public void setGame(MaterialGame game) {
		 this.game = game;
	 }

	 public Boolean getChecked() {
		 return checked;
	 }

	 public void setChecked(Boolean checked) {
		 this.checked = checked;
	 }

	 public String getSendType() {
		 return sendType;
	 }

	 public void setSendType(String sendType) {
		 this.sendType = sendType;
	 }

	 public String getGrantType() {
		 return grantType;
	 }

	 public void setGrantType(String grantType) {
		 this.grantType = grantType;
	 }

	 /**
	  * @return the shareTitle
	  */
	 public String getShareTitle() {
		 return shareTitle;
	 }

	 /**
	  * @param shareTitle the shareTitle to set
	  */
	 public void setShareTitle(String shareTitle) {
		 this.shareTitle = shareTitle;
	 }

	 /**
	  * @return the shareDesc
	  */
	 public String getShareDesc() {
		 return shareDesc;
	 }

	 /**
	  * @param shareDesc the shareDesc to set
	  */
	 public void setShareDesc(String shareDesc) {
		 this.shareDesc = shareDesc;
	 }
	/**
	 * @return the client
	 */
	public Integer getClient() {
		return client;
	}


	/**
	 * @param client the client to set
	 */
	public void setClient(Integer client) {
		this.client = client;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public PosterMaterial getPoster() {
		return poster;
	}

	public void setPoster(PosterMaterial poster) {
		this.poster = poster;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
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

    public String getCampaignWay() {
        return campaignWay;
    }

    public void setCampaignWay(String campaignWay) {
        this.campaignWay = campaignWay == null ? null : campaignWay.trim();
    }

	 public String getShowDate() {
		 return showDate;
	 }

	 public void setShowDate(String showDate) {
		 this.showDate = showDate;
	 }

	 public String getCreateTime() {
		 return createTime;
	 }

	 public void setCreateTime(String createTime) {
		 this.createTime = createTime;
	 }

	 public void setModifyTime(String modifyTime) {
		 this.modifyTime = modifyTime;
	 }

	 public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

   /* public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }
*/
    public String getPosterTemplete() {
        return posterTemplete;
    }

    public void setPosterTemplete(String posterTemplete) {
        this.posterTemplete = posterTemplete == null ? null : posterTemplete.trim();
    }


	

	public String getCampaignType() {
		return campaignType;
	}

	public void setCampaignType(String campaignType) {
		this.campaignType = campaignType;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}


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

	 public String getModifyTime() {
		 return modifyTime;
	 }

	 public String getCompaignStatus() {
		return compaignStatus;
	}

	public void setCompaignStatus(String compaignStatus) {
		this.compaignStatus = compaignStatus;
	}


	public static void main(String[] args) {
		System.out.println(new Campaign().toString());
	}

	 public String getWechatIcon() {
		 return HttpUrls.BRAND_LOGO_ULR+wechatIcon;
	 }

	 public void setWechatIcon(String wechatIcon) {
		 this.wechatIcon = wechatIcon;
	 }
 }