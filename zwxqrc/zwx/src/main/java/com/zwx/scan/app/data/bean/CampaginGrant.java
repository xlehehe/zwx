package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.Date;
public class CampaginGrant implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2918877483683259561L;

	/**
     * 
     */
    private Long camGrantId;

    /**
     * 公司id
     */
    private Long compId;

    /**
     * 活动id
     */
    private Long campaignId;

    /**
     * 直接发放类型：新会员礼（N）、二次消费（S）、生日礼（B）、会员日（M）、自定义（C）
     */
    private String grantType;

    /**
     * 发放方式：（）日前/后发放
 新会员发放方式：
 成为会员即刻发放--0
 成为会员半月后发放--15
 成为会员一个月发放--30
 成为会员一季度发放--90
 成为会员半年发放--180
 成为会员一整年发放--365
 
 二次消费发放方式：
 超过半个月未消费会员--15
 超过一个月未消费会员--30
 超过三个月未消费会员--90
 超过半年未消费会员--180
 
 生日礼发放方式：
 会员生日前七天发放--7
 会员生日前五天发放--5
 会员生日前三天发放--3
 会员生日前一天发放--1
 会员生日当天发放--0
 
 会员日发放方式：
 会员日前七天发放--7
 会员日前五天发放--5
 会员日前三天发放--3
 会员日前一天发放--1
 会员日当天发放--N
     */
    private Integer sendDay;

    /**
     * 会员日方式： 星期：W 日期：D 
     */
    private String memberDayType;

    /**
     * 具体会员日： 星期多选：星期逗号分隔，如周一,周三（周一--周日） 日期多选：日期逗号分隔，如1,3,30（1--31） 
     */
    private String memberDay;

    /**
     * 用户自定义日期
     */
    private String customDay;

    /**
     * 具体推送时间
     */
    private String sendTime;

    /**
     * 展示方式：多选，逗号分隔(如W,P) 微信模板消息--W 内部弹窗--P App推送消息--M
     */
    private String showType;

    private String compMemTypeId;
    private String compMemTypeName;


    public void setCustomDay(String customDay) {
        this.customDay = customDay;
    }


    public String getCustomDay() {
        return customDay;
    }

    public String getCompMemTypeName() {
        return compMemTypeName;
    }

    public void setCompMemTypeName(String compMemTypeName) {
        this.compMemTypeName = compMemTypeName;
    }

   /* public Long getCompMemTypeId() {
        return compMemTypeId;
    }

    public void setCompMemTypeId(Long compMemTypeId) {
        this.compMemTypeId = compMemTypeId;
    }*/
 public String getCompMemTypeId() {
        return compMemTypeId;
    }

    public void setCompMemTypeId(String compMemTypeId) {
        this.compMemTypeId = compMemTypeId;
    }

    public Long getCamGrantId() {
        return camGrantId;
    }

    public void setCamGrantId(Long camGrantId) {
        this.camGrantId = camGrantId;
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


    public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public Integer getSendDay() {
        return sendDay;
    }

    public void setSendDay(Integer sendDay) {
        this.sendDay = sendDay;
    }

/*    public Byte getMemberDayType() {
        return memberDayType;
    }

    public void setMemberDayType(Byte memberDayType) {
        this.memberDayType = memberDayType;
    }*/

    public String getMemberDayType() {
        return memberDayType;
    }

    public void setMemberDayType(String memberDayType) {
        this.memberDayType = memberDayType;
    }

    public String getMemberDay() {
        return memberDay;
    }

    public void setMemberDay(String memberDay) {
        this.memberDay = memberDay == null ? null : memberDay.trim();
    }



    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType == null ? null : showType.trim();
    }

	/**
	 * @return the sendTime
	 */
	public String getSendTime() {
		return sendTime;
	}

	/**
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

/*	@Override
	public String toString() {
		return "CampaginGrant [camGrantId=" + camGrantId + ", compId=" + compId + ", campaignId=" + campaignId
		        + ", grantType=" + grantType + ", sendDay=" + sendDay + ", memberDayType=" + memberDayType
		        + ", memberDay=" + memberDay + ", customDay=" + customDay + ", sendTime=" + sendTime + ", showType="
		        + showType + "]";
	}*/
    
}