package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2019/05/16
 * desc   : 支付认证信息
 * version: 1.0
 **/
public class PayAuthInfoResultBean implements Serializable {

    public PayAuthInfoBean compIdentity;

    public PayAuthInfoBean getCompIdentity() {
        return compIdentity;
    }

    public void setCompIdentity(PayAuthInfoBean compIdentity) {
        this.compIdentity = compIdentity;
    }

    public  class  PayAuthInfoBean implements Serializable{
        private String bankCode;
        private String bankName;
        private String cardNo;
        private String cardType;
        private String compId;
        private String contractNo;
        private String id;
        private String identityName;
        private String identityType;
        private String legalId;
        private String managerName;


        private String managerTel;
        private String phone;
        private String status;
        private String type;

        public String getBankCode() {
            return bankCode;
        }

        public void setBankCode(String bankCode) {
            this.bankCode = bankCode;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getCompId() {
            return compId;
        }

        public void setCompId(String compId) {
            this.compId = compId;
        }

        public String getContractNo() {
            return contractNo;
        }

        public void setContractNo(String contractNo) {
            this.contractNo = contractNo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdentityName() {
            return identityName;
        }

        public void setIdentityName(String identityName) {
            this.identityName = identityName;
        }

        public String getIdentityType() {
            return identityType;
        }

        public void setIdentityType(String identityType) {
            this.identityType = identityType;
        }

        public String getLegalId() {
            return legalId;
        }

        public void setLegalId(String legalId) {
            this.legalId = legalId;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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
    }




}
