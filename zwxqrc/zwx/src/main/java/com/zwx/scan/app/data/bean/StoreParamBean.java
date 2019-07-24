package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StoreParamBean implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long storeId;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 店铺的特约商户ID
     */
    private String subMchId;

    /**
     * 企业id
     */
    private Long compId;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 经营模式 自营-1 加盟-2
     */
    private String statusQuo;

    /**
     * 店铺简称
     */
    private String storeSna;

    /**
     * 店铺电话
     */
    private String teleNos;

    /**
     * 店铺地址
     */
    private String storeAddress;

    /**
     * 店铺状态 商店状态 0停用1使用
     */
    private Byte storeStatus;

    /**
     * 店铺二维码
     */
    private String forwardQrCode;

    /**
     * 转发活动cod
     */
    private String forwardCampaignId;

    /**
     * 新会员注册活动cod
     */
    private Long newMemberRegisterId;

    /**
     * 默认入会会员等级,不可修改,由品牌维护一并修改
     */
    private Long newCompMemtypeId;

    /**
     * 印章活动cod
     */
    private Long sealCampaignId;

    /**
     * 新建时间
     */
    private Date insertTime;

    /**
     * 最近一次修改人员
     */
    private Long insertUser;

    /**
     * 删除时间
     */
    private Date modifyTime;

    /**
     * 删除人
     */
    private Long modifyUser;

    /**
     * 店长
     */
    private String managerName;

    /**
     * 店长电话
     */
    private String managerTel;

    /**
     * 
     */
    private BigDecimal geoLng;

    /**
     * 
     */
    private BigDecimal geoLat;

    /**
     * 经纬度信息
     */
    private String geoData;

    /**
     * 省份code
     */
    private Long provinceCode;

    /**
     * 市code
     */
    private Long cityCode;

    /**
     * 区code
     */
    private Long distCode;

    /**
     * 营业开始时间
     */
    private String serviceStartTime;

    /**
     * 营业结束时间
     */
    private String serviceEndTime;

    /**
     * 服务项目内容
     */
    private String serviceContents;

    /**
     * 宴会类型
     */
    private String banquetTypes;

    /**
     * 环境图片
     */
    private String photos;

    /**
     * 
     */
    private String dishContents;

    private String serviceDate;


    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName == null ? null : storeName.trim();
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId == null ? null : subMchId.trim();
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

    public String getStatusQuo() {
        return statusQuo;
    }

    public void setStatusQuo(String statusQuo) {
        this.statusQuo = statusQuo;
    }

    public String getStoreSna() {
        return storeSna;
    }

    public void setStoreSna(String storeSna) {
        this.storeSna = storeSna == null ? null : storeSna.trim();
    }

    public String getTeleNos() {
        return teleNos;
    }

    public void setTeleNos(String teleNos) {
        this.teleNos = teleNos == null ? null : teleNos.trim();
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress == null ? null : storeAddress.trim();
    }

    public Byte getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(Byte storeStatus) {
        this.storeStatus = storeStatus;
    }

    public String getForwardQrCode() {
        return forwardQrCode;
    }

    public void setForwardQrCode(String forwardQrCode) {
        this.forwardQrCode = forwardQrCode == null ? null : forwardQrCode.trim();
    }

    public String getForwardCampaignId() {
        return forwardCampaignId;
    }

    public void setForwardCampaignId(String forwardCampaignId) {
        this.forwardCampaignId = forwardCampaignId == null ? null : forwardCampaignId.trim();
    }

    public Long getNewMemberRegisterId() {
        return newMemberRegisterId;
    }

    public void setNewMemberRegisterId(Long newMemberRegisterId) {
        this.newMemberRegisterId = newMemberRegisterId;
    }

    public Long getNewCompMemtypeId() {
        return newCompMemtypeId;
    }

    public void setNewCompMemtypeId(Long newCompMemtypeId) {
        this.newCompMemtypeId = newCompMemtypeId;
    }

    public Long getSealCampaignId() {
        return sealCampaignId;
    }

    public void setSealCampaignId(Long sealCampaignId) {
        this.sealCampaignId = sealCampaignId;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Long getInsertUser() {
        return insertUser;
    }

    public void setInsertUser(Long insertUser) {
        this.insertUser = insertUser;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Long modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName == null ? null : managerName.trim();
    }

    public String getManagerTel() {
        return managerTel;
    }

    public void setManagerTel(String managerTel) {
        this.managerTel = managerTel == null ? null : managerTel.trim();
    }

    public BigDecimal getGeoLng() {
        return geoLng;
    }

    public void setGeoLng(BigDecimal geoLng) {
        this.geoLng = geoLng;
    }

    public BigDecimal getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(BigDecimal geoLat) {
        this.geoLat = geoLat;
    }

    public String getGeoData() {
        return geoData;
    }

    public void setGeoData(String geoData) {
        this.geoData = geoData == null ? null : geoData.trim();
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

    public String getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(String serviceStartTime) {
        this.serviceStartTime = serviceStartTime == null ? null : serviceStartTime.trim();
    }

    public String getServiceEndTime() {
        return serviceEndTime;
    }

    public void setServiceEndTime(String serviceEndTime) {
        this.serviceEndTime = serviceEndTime == null ? null : serviceEndTime.trim();
    }

    public String getServiceContents() {
        return serviceContents;
    }

    public void setServiceContents(String serviceContents) {
        this.serviceContents = serviceContents == null ? null : serviceContents.trim();
    }

    public String getBanquetTypes() {
        return banquetTypes;
    }

    public void setBanquetTypes(String banquetTypes) {
        this.banquetTypes = banquetTypes == null ? null : banquetTypes.trim();
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos == null ? null : photos.trim();
    }

    public String getDishContents() {
        return dishContents;
    }

    public void setDishContents(String dishContents) {
        this.dishContents = dishContents == null ? null : dishContents.trim();
    }
}