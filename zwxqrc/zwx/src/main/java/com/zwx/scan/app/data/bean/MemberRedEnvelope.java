package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * author : lizhilong
 * time   : 2019/04/25
 * desc   : 红包
 * version: 1.0
 **/
public class MemberRedEnvelope implements Serializable {

    private Long compId;

    private Long memberId;

    private BigDecimal balance;

    private BigDecimal frozenBalance;

    private String status;

    private static final long serialVersionUID = 1L;

    private String memberName;

    private String memberTel;

    private Integer num;

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getFrozenBalance() {
        return frozenBalance;
    }

    public void setFrozenBalance(BigDecimal frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
