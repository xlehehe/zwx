package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2019/01/03
 * desc   :
 * version: 1.0
 **/
public class StoreBean implements Serializable {
    private boolean isChecked;
    /**
     * ID
     */
    private Long id;

    /**
     * 店铺名称
     */
    private String name;

    /**
     * 所属餐饮公司
     */
    private Company company;

    /**
     * 所属品牌
     */
    private Brand brand;

    /**
     * 经营类型
     */
    private Integer type;

    /**
     * 店铺电话
     */
    private String telphone;

    /**
     * 可用状态
     */
    private Integer status;

    /**
     * 店长姓名
     */
    private String managerName;

    /**
     * 店长手机号
     */
    private String managerTel;

    /**
     * 店铺地理 经度
     */
    private Double geoLng;


    /**
     * 店铺地理纬度
     */
    private Double geoLat;


    /**
     * 开始服务时间
     */
    private String serviceStartTime;

    /**
     * 结束服务时间
     */
    private String serviceEndTime;

    /**
     * 服务内容，数据取自字典表，逗号分隔
     */
    private String serviceContents;

    /**
     * 宴席类型，字典表id，以逗号分隔
     */
    private String banquetTypes;

    /**
     * 环境图片id，以逗号分隔
     */
    private String photos;

    /**
     * 店铺地址
     */
    private String address;

    /**
     * 转发二维码
     */
    private String forwardQrCode;

    /**
     * 当前转发活动主键
     */
    private Long forwardCampaignCode;

    /**
     * 新会员默认活动主键
     */
    private Long newMemberRegisterCode;

    /**
     * 默认入会会员等级
     */
    private Long newCompMemberTypeCode;

    /**
     * 当前印章活动主键
     */
    private Long sealCampaignCode;

    /**
     * 店铺创建日期
     */
    private String createDate;

    /**
     * 与当前用户的距离，不保存到数据库
     */
    private double distance;


    /**
     * 店铺所在公司id
     */
    private Long compId;

    private String storeSelectType;



    /**
     * @return the storeSelectType
     */
    public String getStoreSelectType() {
        return storeSelectType;
    }

    /**
     * @param storeSelectType the storeSelectType to set
     */
    public void setStoreSelectType(String storeSelectType) {
        this.storeSelectType = storeSelectType;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(String serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public String getServiceEndTime() {
        return serviceEndTime;
    }

    public void setServiceEndTime(String serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }

    public String getServiceContents() {
        return serviceContents;
    }

    public void setServiceContents(String serviceContents) {
        this.serviceContents = serviceContents;
    }

    public String getBanquetTypes() {
        return banquetTypes;
    }

    public void setBanquetTypes(String banquetTypes) {
        this.banquetTypes = banquetTypes;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getForwardQrCode() {
        return forwardQrCode;
    }

    public void setForwardQrCode(String forwardQrCode) {
        this.forwardQrCode = forwardQrCode;
    }

    public Long getForwardCampaignCode() {
        return forwardCampaignCode;
    }

    public void setForwardCampaignCode(Long forwardCampaignCode) {
        this.forwardCampaignCode = forwardCampaignCode;
    }

    public Long getNewMemberRegisterCode() {
        return newMemberRegisterCode;
    }

    public void setNewMemberRegisterCode(Long newMemberRegisterCode) {
        this.newMemberRegisterCode = newMemberRegisterCode;
    }

    public Long getNewCompMemberTypeCode() {
        return newCompMemberTypeCode;
    }

    public void setNewCompMemberTypeCode(Long newCompMemberTypeCode) {
        this.newCompMemberTypeCode = newCompMemberTypeCode;
    }

    public Long getSealCampaignCode() {
        return sealCampaignCode;
    }

    public void setSealCampaignCode(Long sealCampaignCode) {
        this.sealCampaignCode = sealCampaignCode;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Double getGeoLng() {
        return geoLng;
    }

    public void setGeoLng(Double geoLng) {
        this.geoLng = geoLng;
    }

    public Double getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(Double geoLat) {
        this.geoLat = geoLat;
    }

}
