package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/05/21
 * desc   :
 * version: 1.0
 **/
public class TradeDrawingRecordResultBean implements Serializable {


    public List<DrawingRecordBean> list;

    public List<DrawingRecordBean> getList() {
        return list;
    }

    public void setList(List<DrawingRecordBean> list) {
        this.list = list;
    }

    public  class  DrawingRecordBean implements Serializable {

        private String accountName;
        private BigDecimal amount;
        private String bankCardno;
        private String bankName;
        private String compId;
        private String compIdentityId;
        private String createName;
        private String createTel;
        private String createTime;
        private String createUser;
        private BigDecimal fee;
        private String id;
        private String industryCode;
        private String identityName;

        private String managerName;
        private String managerTel;
        private String orderNo;


        private String phone;
        private String source;
        private String status;
        private String type;

        private String withdrawType;

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getBankCardno() {
            return bankCardno;
        }

        public void setBankCardno(String bankCardno) {
            this.bankCardno = bankCardno;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getCompId() {
            return compId;
        }

        public void setCompId(String compId) {
            this.compId = compId;
        }

        public String getCompIdentityId() {
            return compIdentityId;
        }

        public void setCompIdentityId(String compIdentityId) {
            this.compIdentityId = compIdentityId;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public String getCreateTel() {
            return createTel;
        }

        public void setCreateTel(String createTel) {
            this.createTel = createTel;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public BigDecimal getFee() {
            return fee;
        }

        public void setFee(BigDecimal fee) {
            this.fee = fee;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIndustryCode() {
            return industryCode;
        }

        public void setIndustryCode(String industryCode) {
            this.industryCode = industryCode;
        }

        public String getIdentityName() {
            return identityName;
        }

        public void setIdentityName(String identityName) {
            this.identityName = identityName;
        }

        public String getManagerName() {
            return managerName;
        }

        public void setManagerName(String managerName) {
            this.managerName = managerName;
        }

        public String getManagerTel() {
            return managerTel;
        }

        public void setManagerTel(String managerTel) {
            this.managerTel = managerTel;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getWithdrawType() {
            return withdrawType;
        }

        public void setWithdrawType(String withdrawType) {
            this.withdrawType = withdrawType;
        }
    }
}
