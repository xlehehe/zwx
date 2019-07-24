package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺员工实体类
 * @author HanJian
 * @date 2018年10月26日
 */
public class StaffWork implements Serializable {

    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 管理餐企员工
     */
    private Staff staff;
    
    /**
     * 所属餐企
     */
//    private Company company;
    
    /**
     * 所属品牌
     */
//    private Brand brand;
    
    /**
     * 所属店铺
     */
//    private Store store;
    
    /**
     * 工作岗位（字典表）
     */
    private DirectoryData position;
    
    /**
     * 拉新二维码
     */
    private StaffQRCode qrCode;
    
    /**
     * 在职状态
     */
    private Integer status;
    
    /**
     * 拉新总数
     */
    private Integer pullCount;
    
    /**
     * 拉新成功总数
     */
    private Integer pullSuccCount;
    
    /**
     * 加入（入职）店铺日期
     */
    private String joinTime;
    
    /**
     * 离开（离职）店铺日期
     */
    private String quitTime;
    
    
    /**
     * 添加日期
     */
    private String createTime;
    
    /**
     * 删除标志
     */
    private Integer deleted;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }






    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPullCount() {
        return pullCount;
    }

    public void setPullCount(Integer pullCount) {
        this.pullCount = pullCount;
    }

    public Integer getPullSuccCount() {
        return pullSuccCount;
    }

    public void setPullSuccCount(Integer pullSuccCount) {
        this.pullSuccCount = pullSuccCount;
    }


    public DirectoryData getPosition() {
        return position;
    }

    public void setPosition(DirectoryData position) {
        this.position = position;
    }

    public StaffQRCode getQrCode() {
        return qrCode;
    }

    public void setQrCode(StaffQRCode qrCode) {
        this.qrCode = qrCode;
    }


    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(String quitTime) {
        this.quitTime = quitTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
