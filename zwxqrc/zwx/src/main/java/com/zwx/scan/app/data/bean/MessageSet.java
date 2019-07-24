package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息信息实体
 * @author Wangss
 * @date 2019年1月10日
*/
public class MessageSet implements Serializable{

    private static final long serialVersionUID = 1L;
    
	private Long id;
    /**
     * 通知类型：系统消息S 升级消息U 新游戏消息G 新模板消息T
     */
	private String type;
    /**
     * 标题
     */
	private String title;
	
	 /**
     * 标题
     */
	private String summary;
    /**
     * 通知方式：图文：H   链接：L
     */
	private String contentType;
    /**
     * 内容
     */
	private String content;
    /**
     * 推送时间
     */
	private String sendTime;
    /**
     * 状态：暂存S、发布R、撤回W
     */
//	private String status;
    /**
     * 创建人ID
     */
	private Long createrId;
    /**
     * 创建时间
     */
	private String createTime;
    /**
     * 创建人名称
     */
	private String createName;
    /**
     * 修改人ID
     */
	private Long modifyId;
    /**
     * 修改人名称
     */
	private String modifyName;
	/**
     * 删除状态(1:正常,0:删除)
     */
//	private Integer delFlag;
	/**
     * 执行状态(1:执行,0:未执行)
     */
	private Integer execFlag;

	
	//查询所用字段
	/**
	 * 封装当前消息的接收用户集合
	 */
	private MessageReceiver messageReceiver;
	/**
	 * 封装通知类型信息
	 */
	private DirectoryData typeData;
	
	/**
	 * 未读
	 */
	private Long unreadCount;
	
	/**
	 * 已读
	 */
	private Long readCount;

	private String key;
	private String value;
	private Integer count;

	/**
	 * 是否显示New图片(1:显示，0：不显示)
	 */
	private String isNewIcon;

	/**
	 * 显示时间说明
	 */
	private String showExplain;



	private Long messageId;

	private Long userId;
	/**
	 * 读取状态：未读U，已读R
	 */
	private String status;
	/**
	 * 状态：1：正常,0:删除
	 */
	private String delFlag;
	/**
	 * 查看时间
	 */
	private String readTime;
	/**
	 * 批量插入的时间
	 */
	private String insertTime;



	public String getIsNewIcon() {
		return isNewIcon;
	}

	public void setIsNewIcon(String isNewIcon) {
		this.isNewIcon = isNewIcon;
	}

	public String getShowExplain() {
		return showExplain;
	}

	public void setShowExplain(String showExplain) {
		this.showExplain = showExplain;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}


	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getReadTime() {
		return readTime;
	}

	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public Integer getExecFlag() {
		return execFlag;
	}

	public void setExecFlag(Integer execFlag) {
		this.execFlag = execFlag;
	}

	public MessageReceiver getMessageReceiver() {
		return messageReceiver;
	}

	public void setMessageReceiver(MessageReceiver messageReceiver) {
		this.messageReceiver = messageReceiver;
	}

	public DirectoryData getTypeData() {
		return typeData;
	}

	public void setTypeData(DirectoryData typeData) {
		this.typeData = typeData;
	}
	
	public Long getUnreadCount() {
		return unreadCount;
	}

	public void setUnreadCount(Long unreadCount) {
		this.unreadCount = unreadCount;
	}

	public Long getReadCount() {
		return readCount;
	}

	public void setReadCount(Long readCount) {
		this.readCount = readCount;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
