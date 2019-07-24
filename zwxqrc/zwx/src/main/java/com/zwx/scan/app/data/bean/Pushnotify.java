package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * t_pushnotify(餐企消息推送配置表)
 * @wangshishi
 */
public class Pushnotify implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 企业ID
     */
    private Long compId;

    /**
     * 推送类型(1:活动,2:团购，3：领取会员卡)
     */
    private String pushType;

    /**
     * 活动ID、会员卡ID、商品ID等
     */
    private String pushContentId;

    /**
     * 模板消息的ID
     */
    private String msgTempId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 1为可用、0为失效
     */
    private Integer status;

    /**
     * 创建人的ID
     */
    private Long modifyUser;

    /**
     * 修改时间
     */
    private String modifyTime;


    /**
     * 名称
     */
    private String pushContentName;
    /**
     * 推送类型名称(1:活动消息,2:团购消息，3：领取会员卡消息)
     */
    private String pushTypeName;



    public String getPushTypeName() {
        return pushTypeName;
    }

    public void setPushTypeName(String pushTypeName) {
        this.pushTypeName = pushTypeName;
    }

    public String getPushContentName() {
        return pushContentName;
    }

    public void setPushContentName(String pushContentName) {
        this.pushContentName = pushContentName;
    }

    private static final long serialVersionUID = 1L;

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getMsgTempId() {
        return msgTempId;
    }

    public void setMsgTempId(String msgTempId) {
        this.msgTempId = msgTempId;
    }



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Long modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public String getPushContentId() {
        return pushContentId;
    }

    public void setPushContentId(String pushContentId) {
        this.pushContentId = pushContentId;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
}