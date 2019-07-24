package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 **/
public class CompIdentityResultInfoBean implements Serializable {
    //企业认证信息
    public CompIdentityDetailedBean compIdentityDetailed;
    public CompIdentityBean compIdentity;

    public CompIdentityDetailedBean getCompIdentityDetailed() {
        return compIdentityDetailed;
    }

    public void setCompIdentityDetailed(CompIdentityDetailedBean compIdentityDetailed) {
        this.compIdentityDetailed = compIdentityDetailed;
    }

    public CompIdentityBean getCompIdentity() {
        return compIdentity;
    }

    public void setCompIdentity(CompIdentityBean compIdentity) {
        this.compIdentity = compIdentity;
    }
//企业认证
    /**
     * "compIdentityDetailed":{"auditStatus":"0","businessIicenseImg":"5cde8efa41c9af2318e20120","businessLicense":"216464dhdhcj","city":"济南市","compId":281697771028480,"compIdentityId":406304434896896,"companyName":"大炮筒",
     * "legalIdcard":"5cde8efa41c9af2318e20120","legalIdcardBack":"5cde8efa41c9af2318e20120","legalPhone":"dapao","province":"山东省"},"compIdentity":{"bankCode":"01030000","bankName":"农业银行","cardNo":"6228488888888888888","cardType":"1",
     * "compId":281697771028480,"contractNo":"","id":406304434896896,
     * "identityName":"216464dhdhcj","identityType":"1","legalId":"18699999999","managerName":"","managerTel":"","phone":"dapao","status":"","type":"1"}}
     * */
    public  class  CompIdentityDetailedBean implements Serializable {
        private String auditStatus;
        private String businessIicenseImg;
        private String businessLicense;
        private String city;
        private String compId;
        private String compIdentityId;
        private String companyName;
        private String legalIdcard;
        private String legalIdcardBack;
        private String legalPhone;
        private String province;

        public String getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(String auditStatus) {
            this.auditStatus = auditStatus;
        }

        public String getBusinessIicenseImg() {
            return businessIicenseImg;
        }

        public void setBusinessIicenseImg(String businessIicenseImg) {
            this.businessIicenseImg = businessIicenseImg;
        }

        public String getBusinessLicense() {
            return businessLicense;
        }

        public void setBusinessLicense(String businessLicense) {
            this.businessLicense = businessLicense;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
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

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getLegalIdcard() {
            return legalIdcard;
        }

        public void setLegalIdcard(String legalIdcard) {
            this.legalIdcard = legalIdcard;
        }

        public String getLegalIdcardBack() {
            return legalIdcardBack;
        }

        public void setLegalIdcardBack(String legalIdcardBack) {
            this.legalIdcardBack = legalIdcardBack;
        }

        public String getLegalPhone() {
            return legalPhone;
        }

        public void setLegalPhone(String legalPhone) {
            this.legalPhone = legalPhone;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }
    }

    public  class  CompIdentityBean implements Serializable {
        private String bankCode;
        private String bankName;
        private String cardNo;
        private String cardType;
        private String compId;
        private String contractNo;
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
