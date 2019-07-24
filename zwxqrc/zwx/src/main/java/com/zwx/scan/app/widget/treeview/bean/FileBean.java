package com.zwx.scan.app.widget.treeview.bean;

import com.zwx.scan.app.widget.treeview.utils.annotation.TreeNodeId;
import com.zwx.scan.app.widget.treeview.utils.annotation.TreeNodeLabel;
import com.zwx.scan.app.widget.treeview.utils.annotation.TreeNodePid;
import com.zwx.scan.app.widget.treeview.utils.annotation.TreeNodeId;
import com.zwx.scan.app.widget.treeview.utils.annotation.TreeNodeType;

public class FileBean {

	/**
	 * 当前id
	 */
	@TreeNodeId/*(type = integer.class)*/
	private int id;
	/**
	 * 父节点id
	 */
	@TreeNodePid
	private int pId;
	/**
	 * 标记名称
	 */
	@TreeNodeLabel
	private String lable;

    /**
     * 菜单类型 0 菜单 1 按钮
     */
    @TreeNodeType
    private int type;

	
	public FileBean(int id, int pId, String lable, int type) {
		this.id = id;
		this.pId = pId;
		this.lable = lable;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
