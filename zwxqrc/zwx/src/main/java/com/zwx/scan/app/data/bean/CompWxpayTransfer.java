package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * author : lizhilong
 * time   : 2019/05/24
 * desc   : 转账记录
 * version: 1.0
 **/
public class CompWxpayTransfer implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 公司id
     */
    private Long compId;

    /**
     * 收款方appid
     */
    private String appid;

    /**
     * 收款方openid
     */
    private String openid;

    /**
     * 收款方手机号
     */
    private String phone;

    /**
     * 会员名称
     */
    private String memberName;

    /**
     * 银行卡号
     */
    private String bankCardNo;

    /**
     * 手续费，转账到银行卡记录
     */
    private BigDecimal fee;

    /**
     * 开户名称
     */
    private String accountName;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 转账金额
     */
    private BigDecimal amount;

    /**
     * 备注说明
     */
    private String desc;

    /**
     * 0-钱包，1-银行卡
     */
    private String type;

    /**
     * 转账时间
     */
    private String time;

    /**
     * 0-失败，1-成功
     */
    private String state;

    /**
     * 微信订单号
     */
    private String paymentNo;

    /**
     * 商家订单号
     */
    private String partnerTradeNo;

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

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getPartnerTradeNo() {
        return partnerTradeNo;
    }

    public void setPartnerTradeNo(String partnerTradeNo) {
        this.partnerTradeNo = partnerTradeNo;
    }
}
