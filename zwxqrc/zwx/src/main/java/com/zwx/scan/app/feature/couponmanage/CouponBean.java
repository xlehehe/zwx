package com.zwx.scan.app.feature.couponmanage;

import com.zwx.scan.app.data.bean.Company;
import com.zwx.scan.app.data.bean.CouponMaterial;
import com.zwx.scan.app.data.bean.UnavailableDate;
import com.zwx.scan.app.data.bean.User;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 优惠券实体类
 * @author HanJian
 * @date 2018年9月3日
 */
public class CouponBean implements Serializable {

    private static final long serialVersionUID = 3722769115196354693L;
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 优惠券名称
     */
    private String name;
    
    /**
     * 优惠券类型：VCS單次儲值卡，VCM多次儲值卡，CPC代金券，CPD折扣券，CPO赠品券，CPU菜品券、CPJ插队券、CPT其他
     */
    private String type;
    
    /**
     * 启用状态：启用1，停用0
     */
    private Integer status;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
