package com.zwx.scan.app.widget.treeview.bean;

import com.zwx.scan.app.widget.treeview.utils.annotation.TreeNodeId;
import com.zwx.scan.app.widget.treeview.utils.annotation.TreeNodeLabel;
import com.zwx.scan.app.widget.treeview.utils.annotation.TreeNodePid;
import com.zwx.scan.app.widget.treeview.utils.annotation.TreeNodeType;


/**
 * @ClassName: OrgBean
 * @Description: 
 * @author sloop
 * @date 2019年1月10日 上午2:45:13
 *
 */
public class OrgBean {

	/**
	 * 当前id
	 */
	@TreeNodeId
	private Long id;
	/**
	 * 父节点id
	 */
	@TreeNodePid
	private Long pId;
	/**
	 * 标记名称
	 */
	@TreeNodeLabel
	private String lable;
	/**
	 * 菜单类型
	 */
	@TreeNodeType
	private int type;
	public OrgBean(Long id, Long pId, String lable,int type) {
		this.id = id;
		this.pId = pId;
		this.lable = lable;
		this.type = type;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
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
