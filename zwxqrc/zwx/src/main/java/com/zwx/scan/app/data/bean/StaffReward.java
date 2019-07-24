package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工积分推广记录实体类
 * @author HanJian
 * @date 2018年10月30日
 */
public class StaffReward implements Serializable {
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 所属公司
     */
    private Company company;
    
    /**
     * 所属品牌
     */
    private Brand brand;
    
    /**
     * 所属店铺
     */
    private Store store;
    
    /**
     * 员工
     */
    private Staff staff;
    
    /**
     * 店铺员工
     */
    private StaffWork staffWork;

    
    /**
     * 类型
     */
    private String type;
    
    /**
     * 日期
     */
    private Date createTime;
    
    
    /**
     * 拉新统计，不保存到数据库
     */
    private Integer total;

    
    
    /**
     * 店铺名字
     * */
    private String storeName;
    
    /**
     * 员工名字
     * */
    private String staffName;
    

    
    
    /**
     * 行号
     * */
    private Integer rownum;





    public Integer getRownum() {
		return rownum;
	}

	public void setRownum(Integer rownum) {
		this.rownum = rownum;
	}

	public Integer getTotal() {
		return total;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}



	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public StaffWork getStaffWork() {
        return staffWork;
    }

    public void setStaffWork(StaffWork staffWork) {
        this.staffWork = staffWork;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}
