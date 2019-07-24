package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.Date;

public class Feedback implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long feedbackId;

    /**
     * 意见类型：取字典--合同问题，支付问题，查询问题，统计问题，其他问题，功能建议，性能问题
     */
    private String type;

    /**
     * 意见内容
     */
    private String content;

    /**
     * 来源类型：1系统，2餐企，3经销商管理员，4账务，5客服, 6经销商销售，7经销商地推，8经销商员工
     */
    private Integer fromType;

    /**
     * 状态：系统未处理N,用户未读处理结果U,用户已读处理结果R
     */
    private String status;

    /**
     * 处理结果
     */
    private String dealResult;

    /**
     * 创建人id，user_id
     */
    private Long createrId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 处理人id
     */
    private Long dealId;

    /**
     * 处理时间处理反馈的时间
     */
    private String dealTime;

    /**
     * 意见图片，","分隔
     */
    private String images;

    /**
     * 提交人联系电话
     */
    private String createrTel;

    /**
     * 提交人所属企业
     */
    private String company;

    /**
     * 提交人姓名
     */
    private String createrName;
    
    /**
     * 意见类型:从数据字典中提取出来的
     */
    private String name;
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getFromType() {
        return fromType;
    }

    public void setFromType(Integer fromType) {
        this.fromType = fromType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getDealResult() {
        return dealResult;
    }

    public void setDealResult(String dealResult) {
        this.dealResult = dealResult == null ? null : dealResult.trim();
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getDealId() {
        return dealId;
    }

    public void setDealId(Long dealId) {
        this.dealId = dealId;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime == null ? null : dealTime.trim();
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images == null ? null : images.trim();
    }

    public String getCreaterTel() {
        return createrTel;
    }

    public void setCreaterTel(String createrTel) {
        this.createrTel = createrTel == null ? null : createrTel.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName == null ? null : createrName.trim();
    }
}