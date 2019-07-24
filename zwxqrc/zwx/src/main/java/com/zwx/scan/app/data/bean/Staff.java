package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工实体类
 * @author HanJian
 * @date 2018年10月26日
 */
public class Staff implements Serializable {

    
    /**
     * 主键
     */
    private Long id;
    
    /**
     * 所属公司
     */
//    private Company company;
    
    /**
     * 员工姓名
     */
    private String name;
    
    /**
     * 电话/手机
     */
    private String tel;
    
    /**
     * 生日
     */
    private String birthday;
    
    /**
     * 性别
     */
    private Integer sex;
    


    

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }



}
