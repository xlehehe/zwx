package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 消费日志类
 * 
 * @author DongWei
 * @date 2018年8月31日
 */
public class ComsumeLog implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键消费序号
     */
    private Long Id;

    /**
     * 会员主键
     */
    private Long memberId;

    /**
     * 公司主键
     */
    private Long compId;

    /**
     * 会员升级规则主键
     */
    private Long compMemTypeId;

    /**
     * 关联t_comp_member.id字段
     */
    private Long compMemId;

    /**
     * 消费店铺
     */
    private Long storeId;

    /**
     * 消费时间
     */
    private String consumeTime;

    /**
     * 消费金额
     */
    private BigDecimal consumeAmt;

    /**
     * 就餐人数
     */
    private Byte personQnt;

    /**
     * 状态 已记录——1已撤回——0
     */
    private Byte consumeStatus;

    /**
     * 该单号与店铺号唯一
     */
    private Long posNos;

    /**
     * 获得积分
     */
    private Integer compRewardAmt;

    /**
     * 获得积点
     */
    private Integer compPintQnt;

    /**
     * 外表字段 member表 会员名称 memberName
     */
    private String memberName;

    /**
     * 会员手机号 memberTel
     */
    private String memberTel;

    /**
     * 优惠券名称
     */
    private String couponName;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 店铺名称
     */
    private String storeName;
    
    /**
     * 优惠券ID，多张核销使用,号分隔
     */
    private String couponId;

    private String storeAdress;
    private  String  compMemberGroup;

    public String getCompMemberGroup() {
        return compMemberGroup;
    }

    public void setCompMemberGroup(String compMemberGroup) {
        this.compMemberGroup = compMemberGroup;
    }

    public String getStoreAdress() {
        return storeAdress;
    }

    public void setStoreAdress(String storeAdress) {
        this.storeAdress = storeAdress;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberTel() {
        return memberTel;
    }

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }


    public Long getCompMemTypeId() {
        return compMemTypeId;
    }

    public void setCompMemTypeId(Long compMemTypeId) {
        this.compMemTypeId = compMemTypeId;
    }

    public Long getCompMemId() {
        return compMemId;
    }

    public void setCompMemId(Long compMemId) {
        this.compMemId = compMemId;
    }

    public BigDecimal getConsumeAmt() {
        return consumeAmt;
    }

    public void setConsumeAmt(BigDecimal consumeAmt) {
        this.consumeAmt = consumeAmt;
    }

    public Byte getPersonQnt() {
        return personQnt;
    }

    public void setPersonQnt(Byte personQnt) {
        this.personQnt = personQnt;
    }

    public Byte getConsumeStatus() {
        return consumeStatus;
    }

    public void setConsumeStatus(Byte consumeStatus) {
        this.consumeStatus = consumeStatus;
    }

    public Long getPosNos() {
        return posNos;
    }

    public void setPosNos(Long posNos) {
        this.posNos = posNos;
    }

    public Integer getCompRewardAmt() {
        return compRewardAmt;
    }

    public void setCompRewardAmt(Integer compRewardAmt) {
        this.compRewardAmt = compRewardAmt;
    }

    public Integer getCompPintQnt() {
        return compPintQnt;
    }

    public void setCompPintQnt(Integer compPintQnt) {
        this.compPintQnt = compPintQnt;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(String consumeTime) {
        this.consumeTime = consumeTime;
    }

    @Override
    public String toString() {
        return "ComsumeLog [id=" + Id + ", memberId=" + memberId + ", compId=" + compId + ", compMemTypeId="
                + compMemTypeId + ", compMemId=" + compMemId + ", storeId=" + storeId + ", consumeTime=" + consumeTime
                + ", consumeAmt=" + consumeAmt + ", personQnt=" + personQnt + ", consumeStatus=" + consumeStatus
                + ", posNos=" + posNos + ", compRewardAmt=" + compRewardAmt + ", compPintQnt=" + compPintQnt + "]";
    }

}
