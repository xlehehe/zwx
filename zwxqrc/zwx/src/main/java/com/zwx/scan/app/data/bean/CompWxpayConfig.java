package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2019/05/24
 * desc   : 保存并生效
 * version: 1.0
 **/
public class CompWxpayConfig implements Serializable {

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
     * 开户人名称
     */
    private String accountName;

    /**
     * 开户行名称
     */
    private String bankName;

    /**
     * 绑定的银行卡号
     */
    private String bankCardNo;

    /**
     * 生效类型 0——微信钱包，1——银行卡
     */
    private String payType;

    /**
     * 2-未设置，0-钱包生效中，1-银行卡生效中。
     */
    private String configState;


    private String brandId;


    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
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

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getConfigState() {
        return configState;
    }

    public void setConfigState(String configState) {
        this.configState = configState;
    }
}
