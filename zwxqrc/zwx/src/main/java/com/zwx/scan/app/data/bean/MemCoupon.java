package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 活动优惠券和人的关联表
 * @author DongWei
 * @date 2018年9月10日
 */
public class MemCoupon implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 2725016591133687410L;

    /**
     * 主键
     */
    private String id;

    /**
     * 企业cod
     */
    private String compId;

    /**
     * 会员cod
     */
    private String memberId;

    /**
     * 关联到comp_member.ID字段
     */
    private String compMemId;

    /**
     * 会员类型
     */
    private String compMemtypeId;

    /**
     * 活动cod
     */
    private String campaignId;

    /**
     * 优惠券cod
     */
    private String couponId;

    /**
     * 用户领券之后的优惠券号码（理解成为纸质优惠券下方的编号即可）
     */
    private String couponNos;


    /**
     * 优惠券状态 未领取 S 未使用——N 已使用——U 已过期——E
     */
    private String couponStatus;


    /**
     * 店铺ID，存储格式 ,XXXXXXXX, 前后都要有逗号，非空，存储多个店铺ID
     */
    private String storeId;

    /**
     * 会员手机号
     */
    private String memberTel;
    
    /**
     * 会员名称
     */
    private String memberName;
    
    /**
     * 消费时间
     */
    private String consumeTime;
    
    /**
     * 消费金额
     */
    private BigDecimal consumeAmt;
    
    /**
     * 消费人数
     */
    private Integer personQnt;
    
    /**
     * 优惠券类型
     */
    private String couponType;
    
    /**
     * 优惠券金额
     */
    private BigDecimal couponMoney;
    
    /**
     * 优惠券折扣
     */
    private String couponDiscount;
    
    /**
     * 优惠券备注
     */
    private String couponOther;
    
    /**
     * 优惠券名称
     */
    private String couponName;
    
    /**
     * 转增时间
     */
    private String presentTime;
    


    /**
     * 优惠券id数组
     */
    private String couponIdArr;
    
    /**
     * 优惠券有效开始日期
     */
    private String startDate;
    
    /**
     *数量统计
     */
    
    private Integer total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCompMemId() {
        return compMemId;
    }

    public void setCompMemId(String compMemId) {
        this.compMemId = compMemId;
    }

    public String getCompMemtypeId() {
        return compMemtypeId;
    }

    public void setCompMemtypeId(String compMemtypeId) {
        this.compMemtypeId = compMemtypeId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponNos() {
        return couponNos;
    }

    public void setCouponNos(String couponNos) {
        this.couponNos = couponNos;
    }

    public String getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(String couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getMemberTel() {
        return memberTel;
    }

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(String consumeTime) {
        this.consumeTime = consumeTime;
    }

    public BigDecimal getConsumeAmt() {
        return consumeAmt;
    }

    public void setConsumeAmt(BigDecimal consumeAmt) {
        this.consumeAmt = consumeAmt;
    }

    public Integer getPersonQnt() {
        return personQnt;
    }

    public void setPersonQnt(Integer personQnt) {
        this.personQnt = personQnt;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public BigDecimal getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(BigDecimal couponMoney) {
        this.couponMoney = couponMoney;
    }

    public String getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(String couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public String getCouponOther() {
        return couponOther;
    }

    public void setCouponOther(String couponOther) {
        this.couponOther = couponOther;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }



    public String getCouponIdArr() {
        return couponIdArr;
    }

    public void setCouponIdArr(String couponIdArr) {
        this.couponIdArr = couponIdArr;
    }



    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getPresentTime() {
        return presentTime;
    }

    public void setPresentTime(String presentTime) {
        this.presentTime = presentTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}