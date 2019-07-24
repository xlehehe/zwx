package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单/按钮 资源实体类
 * @author HanJian
 */
public class Resource implements Serializable {

	private static final long serialVersionUID = -3971012013728919473L;
	
	/**
	 * ID
	 */
	private Long id;
	
	/**
	 * 资源名称
	 */
	private String name;
	
	/**
	 * 菜单URL
	 */
	private String url;
	
	/**
	 * 类型 - 0：菜单，1：按钮
	 */
	private Integer type;
	
	/**
	 * 菜单图标
	 */
	private String icon;
	
	/**
	 * 排序序号
	 */
	private Integer seq;
	
	/**
	 * 资源编码：方便权限判断
	 */
	private String code;
	
	/**
	 * 上级/父级
	 */
	private Resource parent;
	
	/**
	 * 子类资源，不保存到数据库
	 */
	private List<Resource> children;
	
	/**
	 * 上级ID
	 */
	private Long pid;
	
	/**
	 * 是否选中，不保存到数据库
	 */
	private boolean lay_is_checked;
	
	
    public boolean isLay_is_checked() {
        return lay_is_checked;
    }

    public void setLay_is_checked(boolean lay_is_checked) {
        this.lay_is_checked = lay_is_checked;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Resource getParent() {
        return parent;
    }

    public void setParent(Resource parent) {
        this.parent = parent;
    }

    public List<Resource> getChildren() {
        return children;
    }

    public void setChildren(List<Resource> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Resource [id=" + id + ", name=" + name + ", url=" + url + ", type=" + type + ", icon=" + icon + ", seq="
                + seq + ", code=" + code + "]";
    }

}
