package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/06/04
 * desc   :
 * version: 1.0
 **/
public class CollectionToAccountOrderDetailBean implements Serializable {

    private  OrderCollect ordercollect;

    private List<TOrderPay> orderPay;
    private TOrder orderinfo;

    private List<TOrderDetails> orderDetail;

    public List<TOrderDetails> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<TOrderDetails> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public OrderCollect getOrdercollect() {
        return ordercollect;
    }

    public void setOrdercollect(OrderCollect ordercollect) {
        this.ordercollect = ordercollect;
    }

    public List<TOrderPay> getOrderPay() {
        return orderPay;
    }

    public void setOrderPay(List<TOrderPay> orderPay) {
        this.orderPay = orderPay;
    }

    public TOrder getOrderinfo() {
        return orderinfo;
    }

    public void setOrderinfo(TOrder orderinfo) {
        this.orderinfo = orderinfo;
    }

}
