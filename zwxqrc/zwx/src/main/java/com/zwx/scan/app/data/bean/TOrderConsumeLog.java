package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * author : lizhilong
 * time   : 2019/04/28
 * desc   : 商品核销记录详情
 * version: 1.0
 **/
public class TOrderConsumeLog implements Serializable {

    private Long id;

    private Long memberId;

    private Long compId;

    private Long compMemtypeId;

    // Field descriptor #13 LLong;
    private Long compMemId;

    // Field descriptor #13 LLong;
    private Long storeId;

    // Field descriptor #20 LDate;
    private String consumeTime;

    // Field descriptor #22 LBigDecimal;
    private BigDecimal consumeAmt;

    // Field descriptor #24 LByte;
    private Byte personQnt;

    // Field descriptor #24 LByte;
    private String consumeStatus;

    // Field descriptor #27 LInteger;
    private Integer compRewardAmt;

    // Field descriptor #27 LInteger;
    private Integer compPointQnt;

    // Field descriptor #30 LString;
    private String orderCouponId;

    // Field descriptor #30 LString;
    private String status;

    // Field descriptor #30 LString;
    private String memberName;

    // Field descriptor #30 LString;
    private String memberTel;

    // Field descriptor #30 LString;
    private String productName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCompMemtypeId() {
        return compMemtypeId;
    }

    public void setCompMemtypeId(Long compMemtypeId) {
        this.compMemtypeId = compMemtypeId;
    }

    public Long getCompMemId() {
        return compMemId;
    }

    public void setCompMemId(Long compMemId) {
        this.compMemId = compMemId;
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

    public String getConsumeStatus() {
        return consumeStatus;
    }

    public void setConsumeStatus(String consumeStatus) {
        this.consumeStatus = consumeStatus;
    }

    public Integer getCompRewardAmt() {
        return compRewardAmt;
    }

    public void setCompRewardAmt(Integer compRewardAmt) {
        this.compRewardAmt = compRewardAmt;
    }

    public Integer getCompPointQnt() {
        return compPointQnt;
    }

    public void setCompPointQnt(Integer compPointQnt) {
        this.compPointQnt = compPointQnt;
    }

    public String getOrderCouponId() {
        return orderCouponId;
    }

    public void setOrderCouponId(String orderCouponId) {
        this.orderCouponId = orderCouponId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
