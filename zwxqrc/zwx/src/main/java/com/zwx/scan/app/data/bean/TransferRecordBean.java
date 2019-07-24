package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * author : lizhilong
 * time   : 2019/05/24
 * desc   :
 * version: 1.0
 **/
public class TransferRecordBean implements Serializable {

    /**
     *
     * {"code":"1","message":"收款到账查看订单详情","result":{"ordercollect":{"balanceTime":null,"balanceType":"","compFee":0.00,"compId":1,"dealerCompId":"","dealerFee":0.00,"estimateTime":"2019-05-24 16:13:59","id":416072347418624,"orderId":416072243904512,
     * "platformFee":0.00,"status":"0"},"orderDetail":[{"buyCount":1,"catId":397326128250880,"catName":"鲁菜","compId":1,"cou":0,"deliveryFee":0,"detailedId":416072244232192,"factSalesPrice":"","getRedEnvelope":5.00,"marketPrice":0,"orderId":416072243904512,"payWay":"0",
     * "productCode":"G00008","productDetail":"","productId":397562090586112,"productName":"开发环境测试商品2","productSpec":"盘/份","salesType":"pd","status":"0","thumbnailUrl":"5cd66aa23e59d830d8aa94b2","unitPrice":0.01,"unitScore":0,"useRedEnvelope":0.00}],
     * "orderPay":[{"compId":1,"id":416072244346880,"orderId":416072243904512,"payAmount":0.01,"payId":"4200000321201905248879749973","payTime":"20190524161359","payWay":"w","remark":"","result":"1"}],"
     * orderinfo":{"amount":0,"brandId":0,"buyCount":0,"buyMsg":"","compId":0,"coun":0,"delivType":"","fromId":"","fromType":"","memberBuy":"","memberId":0,"memberName":"","memberTel":"","orderCode":201905240355,"orderId":416072243904512,"orderState":"","orderType":"P","
     * orderTypeName":"","orderTypes":"","payChannel":"","productCode":"","productId":0,"productIds":"","productName":"开发环境测试商品2","recommendMemberName":"","recommendMemberTel":"","salesTime":"2019-05-24 16:13:54","staffId":0,"staffName":"",
     * "staffTel":"","storeId":0,"storeName":"","unitPrice":0,"useCash":0,"useCashAmount":0,"useRedEnvelope":0.00,"useRedEnvelopeAmount":0}}}
     * */

    private OrderCollect ordercollect;
    private List<TOrderDetails> orderDetail;
    private List<TOrderPay> orderPay;
    private TOrder orderinfo;

    public OrderCollect getOrdercollect() {
        return ordercollect;
    }

    public void setOrdercollect(OrderCollect ordercollect) {
        this.ordercollect = ordercollect;
    }

    public List<TOrderDetails> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<TOrderDetails> orderDetail) {
        this.orderDetail = orderDetail;
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
