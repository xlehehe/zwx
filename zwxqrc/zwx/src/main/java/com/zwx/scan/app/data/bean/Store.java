package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


/**
 * 餐饮店铺实体类
 * @author lizhilong
 * @date 2018年8月24日
 */
public class Store implements Serializable {



    private boolean isChecked;
    /**
     * ID
     */
    private String storeId;
    
    /**
     * 店铺名称
     */
    private String storeName;


    /**
     * ID
     */
    private String brandId;

    /**
     * 品牌名称
     */
    private String brandName;
    private String brandLogo;

    /**
     * ID
     */
    private String compId;

    /**
     * 公司名称
     */
    private String compName;

    /**
     * 经营方式  1 自营 2 加盟
     * */
    private Integer statusQuo;



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
    private String type;

    /**
     * 店铺电话
     */
    private String telphone;

    /**
     * 可用状态
     */
    private String status;

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
     * 省级
     */
    private Area province;

    /**
     * 市级
     */
    private Area city;

    /**
     * 区县级
     */
    private Area dist;


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
     * 菜品主营类别，数据取自字典表，逗号分隔
     */
    private String dishContents;
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



    private String storeSelectType;

    //主营
    private ArrayList<CateBean>  directoryDataCP;

    //服务
    private ArrayList<CateBean>  directoryDataCT;

    //宴席
    private ArrayList<CateBean>  directoryDataYX;



    private String provinceId ;
    private String cityId;
    private String distId;

    private String serviceDate; //营业时间

    private String shopTel;

    public String getShopTel() {
        return shopTel;
    }

    public void setShopTel(String shopTel) {
        this.shopTel = shopTel;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistId() {
        return distId;
    }

    public void setDistId(String distId) {
        this.distId = distId;
    }

    public String getDishContents() {
        return dishContents;
    }

    public void setDishContents(String dishContents) {
        this.dishContents = dishContents;
    }

    public ArrayList<CateBean> getDirectoryDataCP() {
        return directoryDataCP;
    }

    public void setDirectoryDataCP(ArrayList<CateBean> directoryDataCP) {
        this.directoryDataCP = directoryDataCP;
    }

    public ArrayList<CateBean> getDirectoryDataCT() {
        return directoryDataCT;
    }

    public void setDirectoryDataCT(ArrayList<CateBean> directoryDataCT) {
        this.directoryDataCT = directoryDataCT;
    }

    public ArrayList<CateBean> getDirectoryDataYX() {
        return directoryDataYX;
    }

    public void setDirectoryDataYX(ArrayList<CateBean> directoryDataYX) {
        this.directoryDataYX = directoryDataYX;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public Integer getStatusQuo() {
        return statusQuo;
    }

    public void setStatusQuo(Integer statusQuo) {
        this.statusQuo = statusQuo;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }



    public String getStoreSelectType() {
        return storeSelectType;
    }

    public void setStoreSelectType(String storeSelectType) {
        this.storeSelectType = storeSelectType;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getManagerTel() {
        return managerTel;
    }

    public void setManagerTel(String managerTel) {
        this.managerTel = managerTel;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Area getProvince() {
        return province;
    }

    public void setProvince(Area province) {
        this.province = province;
    }

    public Area getCity() {
        return city;
    }

    public void setCity(Area city) {
        this.city = city;
    }

    public Area getDist() {
        return dist;
    }

    public void setDist(Area dist) {
        this.dist = dist;
    }
}
