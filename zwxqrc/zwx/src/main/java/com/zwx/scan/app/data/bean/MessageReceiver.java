package com.zwx.scan.app.data.bean;

import com.zwx.library.utils.StringUtils;

import java.io.Serializable;

/**
 * 消息接收者关系表实体类
 * @author Wangss
 * @date   2019年1月10日09:59:50
 */
public class MessageReceiver  implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
	private Long messageId;
    /**
     * 接收者类型: 餐企C 经销商S
     */
	private String receiveType;
    /**
     * 筛选方式: role:all/boss/manager/staff; area:all/山东济南/河南; 经营方式:all/直营/加盟
     */
	private String filter;


	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
	 * 接收者类型: 餐企C 经销商S
	 * @return
	 */
	public String getReceiveTypeName() {
		if(!StringUtils.isEmpty(receiveType)) {
			switch (receiveType) {
				case "C":return "餐企用户";
				case "S":return "经销商用户";
				default:
					return "";
			}
		}
		return receiveType;
	}
	
	/**
	 * 接收者类型: role:all/boss/manager/staff; area:all/山东济南/河南; 经营方式:all/直营/加盟
	 * @return
	 */
	public String getFilterName() {
		if(!StringUtils.isEmpty(filter)) {
			return filter.replace("storemanager", "店长").replace("bossmanager", "城市合伙人")
					.replace("market", "地推").replace("staff", "销售");
		}
		return "";
	}
}
