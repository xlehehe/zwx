package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * 店铺员工推广二维码
 * @author HanJian
 * @date 2018年10月24日
 */
public class StaffQRCode implements Serializable {
    
    
    /**
     * 二维码ID
     */
    private Long id;
    
    /**
     * 二维码图片ID
     */
    private String code;
    
    /**
     * 所属企业
     */
//    private Company company;
    
    /**
     * 所属店铺
     */
//    private Store store;
//
    /**
     * 编号 （默认 1-20）
     */
    private Integer nos;
    
    /**
     * 状态：未使用0，已使用1
     */
    private Integer status;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public Integer getNos() {
        return nos;
    }

    public void setNos(Integer nos) {
        this.nos = nos;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    

}
