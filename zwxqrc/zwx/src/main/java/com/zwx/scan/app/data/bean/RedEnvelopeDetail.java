package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * author : lizhilong
 * time   : 2019/04/25
 * desc   :  红包详情
 * version: 1.0
 **/
public class RedEnvelopeDetail implements Serializable {
    private Long id;
    private Long compId;
    private Long memberId;

    //异动前余额
    private BigDecimal balanceBefore;
    //异动余额
    private BigDecimal balanceChange;
    //异动类型，in-收入，ou-支出
    private String changeState;
    //订单编号
    private String orderId;

    //原因
    private String changeReason;
    //异动后余额
    private BigDecimal balanceAfter;
    //时间
    private String changeTime;
    private String payTime;
    private BigDecimal balance;


    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }


    public String getChangeState() {
        return changeState;
    }

    public void setChangeState(String changeState) {
        this.changeState = changeState;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public BigDecimal getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(BigDecimal balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public BigDecimal getBalanceChange() {
        return balanceChange;
    }

    public void setBalanceChange(BigDecimal balanceChange) {
        this.balanceChange = balanceChange;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }
}
