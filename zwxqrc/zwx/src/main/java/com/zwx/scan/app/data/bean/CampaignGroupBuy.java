package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class CampaignGroupBuy implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 活动Id
     */
    private Long campaignId;

    /**
     * 公司Id
     */
    private Long compId;

    /**
     * 开团次数限制类型：每人P，每人每天D
     */
    private String setupCountType;

    /**
     * 开团活动限制次数
     */
    private Integer setupCount;

    /**
     * 参团次数限制类型：每人P，每人每天D
     */
    private String joinCountType;

    /**
     * 参团活动限制次数
     */
    private Integer joinCount;

    /**
     * 活动素材
     */
    private Long materialCampainId;

    /**
     * 现价
     */
    private BigDecimal spellGroupPrice;

    /**
     * 原价
     */
    private BigDecimal goodsPrice;

    /**
     * 成团人数
     */
    private Integer personCount;

    /**
     * 每团获奖人数
     */
    private Integer winningPersonCount;

    /**
     * 有效时间类型：按照自然天D，按照小时H
     */
    private String validTimeType;

    /**
     * 几天、几小时
     */
    private Integer vilidTime;

    /**
     * 成团后将获得价值多少元神秘大奖
     */
    private String remark;

    /**
     * 奖项图片
     */
    private String prizeImg;

    /**
     * 商品详细说明
     */
    private String description;
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getSetupCountType() {
        return setupCountType;
    }

    public void setSetupCountType(String setupCountType) {
        this.setupCountType = setupCountType == null ? null : setupCountType.trim();
    }

    public Integer getSetupCount() {
        return setupCount;
    }

    public void setSetupCount(Integer setupCount) {
        this.setupCount = setupCount;
    }

    public String getJoinCountType() {
        return joinCountType;
    }

    public void setJoinCountType(String joinCountType) {
        this.joinCountType = joinCountType == null ? null : joinCountType.trim();
    }

    public Integer getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(Integer joinCount) {
        this.joinCount = joinCount;
    }

    public Long getMaterialCampainId() {
        return materialCampainId;
    }

    public void setMaterialCampainId(Long materialCampainId) {
        this.materialCampainId = materialCampainId;
    }

    public BigDecimal getSpellGroupPrice() {
        return spellGroupPrice;
    }

    public void setSpellGroupPrice(BigDecimal spellGroupPrice) {
        this.spellGroupPrice = spellGroupPrice;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getPersonCount() {
        return personCount;
    }

    public void setPersonCount(Integer personCount) {
        this.personCount = personCount;
    }

    public Integer getWinningPersonCount() {
        return winningPersonCount;
    }

    public void setWinningPersonCount(Integer winningPersonCount) {
        this.winningPersonCount = winningPersonCount;
    }

    public String getValidTimeType() {
        return validTimeType;
    }

    public void setValidTimeType(String validTimeType) {
        this.validTimeType = validTimeType == null ? null : validTimeType.trim();
    }

    public Integer getVilidTime() {
        return vilidTime;
    }

    public void setVilidTime(Integer vilidTime) {
        this.vilidTime = vilidTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getPrizeImg() {
        return prizeImg;
    }

    public void setPrizeImg(String prizeImg) {
        this.prizeImg = prizeImg == null ? null : prizeImg.trim();
    }
}