package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 优惠券自定义不可用日期
 * @author HanJian
 * @date 2018年9月4日
 */
public class UnavailableDate implements Serializable {

    private static final long serialVersionUID = -5432268806467688606L;
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 所属优惠券
     */
    private Coupon coupon;
    
    /**
     * 开始日期
     */
    private String startDate;
    
    /**
     * 结束日期
     */
    private String endDate;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
