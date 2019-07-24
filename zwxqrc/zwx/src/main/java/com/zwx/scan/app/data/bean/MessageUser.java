package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息接收者读取实体
 * @author Wangss
 * @date 2019年1月10日
*/
public class MessageUser  implements Serializable{
	private static final long serialVersionUID = 1L;

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
	private Date readTime;
	/**
     * 批量插入的时间
     */
	private Date insertTime;
	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
}
