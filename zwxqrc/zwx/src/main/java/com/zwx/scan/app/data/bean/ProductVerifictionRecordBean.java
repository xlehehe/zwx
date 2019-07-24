package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/04/26
 * desc   :
 * version: 1.0
 **/
public class ProductVerifictionRecordBean implements Serializable {

    private BigDecimal v; //销售总金额
    private BigDecimal c;//实付金额
    private BigDecimal h; //红包抵扣金额
    private Integer cou; //张数

    private OrderObjectBean list;

    public BigDecimal getV() {
        return v;
    }

    public void setV(BigDecimal v) {
        this.v = v;
    }

    public BigDecimal getC() {
        return c;
    }

    public void setC(BigDecimal c) {
        this.c = c;
    }

    public BigDecimal getH() {
        return h;
    }

    public void setH(BigDecimal h) {
        this.h = h;
    }

    public Integer getCou() {
        return cou;
    }

    public void setCou(Integer cou) {
        this.cou = cou;
    }

    public OrderObjectBean getList() {
        return list;
    }

    public void setList(OrderObjectBean list) {
        this.list = list;
    }

    public class  OrderObjectBean{
        private List<TOrderObject> data;

        public List<TOrderObject> getData() {
            return data;
        }

        public void setData(List<TOrderObject> data) {
            this.data = data;
        }
    }

}



