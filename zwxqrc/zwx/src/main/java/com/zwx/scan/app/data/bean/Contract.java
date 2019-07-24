package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Contract implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 合同id
     */
    private Long contractId;

    /**
     * 所属经销商管理员id t_dealer_info.id
     */
    private Long dealerInfoId;

    /**
     * 餐企id
     */
    private Long compId;

    /**
     * 餐企品牌id
     */
    private Long brandId;

    /**
     * 餐企门店id
     */
    private Long storeId;

    /**
     * 状态：草稿D、审核中A、审核通过P、审核不通过N、续签中R、已过期O
     */
    private String status;

    /**
     * 下一次交费日期
     */
    private String nextPayDate;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 合同期，XX年
     */
    private Long expiryType;

    /**
     * 结束日期 前端传过来是按年为单位，需要转化成为最终的enddate
     */
    private String endDate;

    /**
     * 交费方式,按月交，按季度，按半年，按年，从字典里取
     */
    private Long payMode;

    /**
     * 交费金额
     */
    private BigDecimal payFee;

    /**
     * 门店名称
     */
    private String storeName;

    /**
     * 法人名称
     */
    private String legalName;

    /**
     * 法人身份证号
     */
    private String legalIdnumber;

    /**
     * 法人联系 电话
     */
    private String legalTel;

    /**
     * 财务联系人
     */
    private String financePerson;

    /**
     * 财务联系人电话
     */
    private String financePersonTel;

    /**
     * 店长姓名
     */
    private String storeManagerName;

    /**
     * 店长电话
     */
    private String storeManagerTel;

    /**
     * 店铺电话
     */
    private String storeTelenum;

    /**
     * 省
     */
    private Long provinceCode;

    /**
     * 市
     */
    private Long cityCode;

    /**
     * 区
     */
    private Long distCode;

    /**
     * 详细地址
     */
    private String storeAddress;

    /**
     * 纬度
     */
    private BigDecimal geoLat;

    /**
     * 经度
     */
    private BigDecimal geoLng;

    /**
     * 经营模式 自营-1 加盟-2
     */
    private Byte statusQuo;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 发票邮寄邮箱
     */
    private String invoiceEmail;

    /**
     * 营业执照号
     */
    private String businessLicense;

    /**
     * 营业执照照片
     */
    private String businessLicenseImg;

    /**
     * 签约人id
     */
    private Long dealerStaffId;

    /**
     * 合同签署页照片
     */
    private String signImg;

    /**
     * 签单时间
     */
    private String submitTime;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 最后修改时间
     */
    private String lastModifyTime;

    /**
     * 最后修改人id，当前登录修改者的user_id
     */
    private Long lastModifierId;

    /**
     * 企业名称
     */
    private String compName;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactTel;

    /**
     * 联系人邮箱
     */
    private String contactEmail;
    
    /**
     * 签约人名称
     */
    private String name;
    
    /**
     * 签约人电话
     */
    private String tel;
    private String contractNo;

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getDealerInfoId() {
        return dealerInfoId;
    }

    public void setDealerInfoId(Long dealerInfoId) {
        this.dealerInfoId = dealerInfoId;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNextPayDate() {
        return nextPayDate;
    }

    public void setNextPayDate(String nextPayDate) {
        this.nextPayDate = nextPayDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Long getExpiryType() {
        return expiryType;
    }

    public void setExpiryType(Long expiryType) {
        this.expiryType = expiryType;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getPayMode() {
        return payMode;
    }

    public void setPayMode(Long payMode) {
        this.payMode = payMode;
    }

    public BigDecimal getPayFee() {
        return payFee;
    }

    public void setPayFee(BigDecimal payFee) {
        this.payFee = payFee;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLegalIdnumber() {
        return legalIdnumber;
    }

    public void setLegalIdnumber(String legalIdnumber) {
        this.legalIdnumber = legalIdnumber;
    }

    public String getLegalTel() {
        return legalTel;
    }

    public void setLegalTel(String legalTel) {
        this.legalTel = legalTel;
    }

    public String getFinancePerson() {
        return financePerson;
    }

    public void setFinancePerson(String financePerson) {
        this.financePerson = financePerson;
    }

    public String getFinancePersonTel() {
        return financePersonTel;
    }

    public void setFinancePersonTel(String financePersonTel) {
        this.financePersonTel = financePersonTel;
    }

    public String getStoreManagerName() {
        return storeManagerName;
    }

    public void setStoreManagerName(String storeManagerName) {
        this.storeManagerName = storeManagerName;
    }

    public String getStoreManagerTel() {
        return storeManagerTel;
    }

    public void setStoreManagerTel(String storeManagerTel) {
        this.storeManagerTel = storeManagerTel;
    }

    public String getStoreTelenum() {
        return storeTelenum;
    }

    public void setStoreTelenum(String storeTelenum) {
        this.storeTelenum = storeTelenum;
    }

    public Long getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(Long provinceCode) {
        this.provinceCode = provinceCode;
    }

    public Long getCityCode() {
        return cityCode;
    }

    public void setCityCode(Long cityCode) {
        this.cityCode = cityCode;
    }

    public Long getDistCode() {
        return distCode;
    }

    public void setDistCode(Long distCode) {
        this.distCode = distCode;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public BigDecimal getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(BigDecimal geoLat) {
        this.geoLat = geoLat;
    }

    public BigDecimal getGeoLng() {
        return geoLng;
    }

    public void setGeoLng(BigDecimal geoLng) {
        this.geoLng = geoLng;
    }

    public Byte getStatusQuo() {
        return statusQuo;
    }

    public void setStatusQuo(Byte statusQuo) {
        this.statusQuo = statusQuo;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceEmail() {
        return invoiceEmail;
    }

    public void setInvoiceEmail(String invoiceEmail) {
        this.invoiceEmail = invoiceEmail;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getBusinessLicenseImg() {
        return businessLicenseImg;
    }

    public void setBusinessLicenseImg(String businessLicenseImg) {
        this.businessLicenseImg = businessLicenseImg;
    }

    public Long getDealerStaffId() {
        return dealerStaffId;
    }

    public void setDealerStaffId(Long dealerStaffId) {
        this.dealerStaffId = dealerStaffId;
    }

    public String getSignImg() {
        return signImg;
    }

    public void setSignImg(String signImg) {
        this.signImg = signImg;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Long getLastModifierId() {
        return lastModifierId;
    }

    public void setLastModifierId(Long lastModifierId) {
        this.lastModifierId = lastModifierId;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}