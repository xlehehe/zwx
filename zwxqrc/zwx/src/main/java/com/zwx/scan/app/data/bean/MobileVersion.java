package com.zwx.scan.app.data.bean;

import java.io.Serializable;

public class MobileVersion implements Serializable {
	private Long id;
	private String appName;
	private String versionCode;
	private String versionName;
	private String updatePath;
	private String updateLog;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getUpdatePath() {
		return updatePath;
	}
	public void setUpdatePath(String updatePath) {
		this.updatePath = updatePath;
	}
	public String getUpdateLog() {
		return updateLog;
	}
	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}
	
	
	

}
