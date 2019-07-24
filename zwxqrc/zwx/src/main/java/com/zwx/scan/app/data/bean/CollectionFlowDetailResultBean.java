package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/05/22
 * desc   :
 * version: 1.0
 **/
public class CollectionFlowDetailResultBean implements Serializable {

    /***
     * {"code":"1","message":"查询成功！","result":{"orderDetails":{"amount":10.00,"brandId":0,"buyCount":0,"buyMsg":"","compId":0,"coun":0,"delivType":"",
     * "fromId":"","fromType":"","memberId":0,"memberName":"","memberTel":"","orderCode":201905220101,"orderId":1,"orderState":"","orderType":"P","orderTypeName":"","orderTypes":"",
     * "payChannel":"","productCode":"","productId":1,"productIds":"","recommendMemberName":"","recommendMemberTel":"","salesTime":"2019-05-22 10:59:50","staffId":0,"staffName":"",
     * "staffTel":"","storeId":0,"storeName":"","unitPrice":0,"useCash":0,"useCashAmount":0,"useRedEnvelope":0,"useRedEnvelopeAmount":10.00},
     *
     *
     * "purchaseDetails":[{"buyCount":1,"catId":0,"catName":"","compId":0,"cou":0,"deliveryFee":0,"detailedId":0,"factSalesPrice":"","getRedEnvelope":0,"marketPrice":0,"orderId":0,
     * "payWay":"","productCode":"G00001","productId":0,"productName":"测试商品1","productSpec":"盘/份","salesType":"","status":"","thumbnailUrl":"","unitPrice":20.00,
     * "unitScore":0,"useRedEnvelope":0}],"orderPayDetails":[{"compId":0,"id":0,"orderId":0,"payAmount":10.00,"payId":"","payTime":"20190521200620","payWay":"r","remark":"","result":""}],
     * "paymentDetails":{"bankType":"","cashFee":10.00,"cashFeeType":"","feeType":"","orderId":0,"outTradeNo":"111111111","payChannel":"tl","paymentId":0,"subMchId":"",
     * "timeEnd":"20190521200620","totalFee":0,"tradeType":"","transactionId":"4211111111111111111111111111","wxOrderId":"","ylOrderId":"","zfbOrderId":""}}}
     * */


    private  CollectionFlowBean orderDetails;

    private List<PurchaseDetailsBean> purchaseDetails;

    private  List<OrderPayDetailsBean>  orderPayDetails;

    private  PaymentDetailsBean paymentDetails;


    public List<PurchaseDetailsBean> getPurchaseDetails() {
        return purchaseDetails;
    }

    public void setPurchaseDetails(List<PurchaseDetailsBean> purchaseDetails) {
        this.purchaseDetails = purchaseDetails;
    }

    public List<OrderPayDetailsBean> getOrderPayDetails() {
        return orderPayDetails;
    }

    public void setOrderPayDetails(List<OrderPayDetailsBean> orderPayDetails) {
        this.orderPayDetails = orderPayDetails;
    }

    public PaymentDetailsBean getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetailsBean paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public CollectionFlowBean getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(CollectionFlowBean orderDetails) {
        this.orderDetails = orderDetails;
    }
    public static class PaymentDetailsBean implements Serializable {

        private String bankType;

        private BigDecimal cashFee;
        private String cashFeeType;
        private String feeType;
        private Long orderId;


        private Long outTradeNo;
        private String payChannel;
        private Long paymentId;
        private String subMchId;

        private String timeEnd;
        private BigDecimal totalFee;
        private String tradeType;
        private String transactionId;

        private String wxOrderId;
        private String ylOrderId;
        private String zfbOrderId;

        public String getBankType() {
            return bankType;
        }

        public void setBankType(String bankType) {
            this.bankType = bankType;
        }

        public BigDecimal getCashFee() {
            return cashFee;
        }

        public void setCashFee(BigDecimal cashFee) {
            this.cashFee = cashFee;
        }

        public String getCashFeeType() {
            return cashFeeType;
        }

        public void setCashFeeType(String cashFeeType) {
            this.cashFeeType = cashFeeType;
        }

        public String getFeeType() {
            return feeType;
        }

        public void setFeeType(String feeType) {
            this.feeType = feeType;
        }



        public String getPayChannel() {
            return payChannel;
        }

        public void setPayChannel(String payChannel) {
            this.payChannel = payChannel;
        }

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public Long getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(Long outTradeNo) {
            this.outTradeNo = outTradeNo;
        }

        public Long getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(Long paymentId) {
            this.paymentId = paymentId;
        }

        public String getSubMchId() {
            return subMchId;
        }

        public void setSubMchId(String subMchId) {
            this.subMchId = subMchId;
        }

        public String getTimeEnd() {
            return timeEnd;
        }

        public void setTimeEnd(String timeEnd) {
            this.timeEnd = timeEnd;
        }

        public BigDecimal getTotalFee() {
            return totalFee;
        }

        public void setTotalFee(BigDecimal totalFee) {
            this.totalFee = totalFee;
        }

        public String getTradeType() {
            return tradeType;
        }

        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getWxOrderId() {
            return wxOrderId;
        }

        public void setWxOrderId(String wxOrderId) {
            this.wxOrderId = wxOrderId;
        }

        public String getYlOrderId() {
            return ylOrderId;
        }

        public void setYlOrderId(String ylOrderId) {
            this.ylOrderId = ylOrderId;
        }

        public String getZfbOrderId() {
            return zfbOrderId;
        }

        public void setZfbOrderId(String zfbOrderId) {
            this.zfbOrderId = zfbOrderId;
        }
    }

    public class OrderPayDetailsBean implements Serializable{
        //"orderPayDetails":[{"compId":0,"id":0,"orderId":0,"payAmount":10.00,"payId":"","payTime":"20190521200620","payWay":"r","remark":"","result":""}]
        private Long compId;
        private Long id;
        private Long orderId;
        private BigDecimal payAmount;
        private String payId;


        private String payTime;
        private String payWay;
        private String remark;
        private String result;

        public Long getCompId() {
            return compId;
        }

        public void setCompId(Long compId) {
            this.compId = compId;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public BigDecimal getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(BigDecimal payAmount) {
            this.payAmount = payAmount;
        }

        public String getPayId() {
            return payId;
        }

        public void setPayId(String payId) {
            this.payId = payId;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getPayWay() {
            return payWay;
        }

        public void setPayWay(String payWay) {
            this.payWay = payWay;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
    public class PurchaseDetailsBean implements Serializable{

        /**
         * "purchaseDetails":[{"buyCount":1,"catId":0,"catName":"","compId":0,"cou":0,"deliveryFee":0,"detailedId":0,"factSalesPrice":"","getRedEnvelope":0,"marketPrice":0,"orderId":0,
         * "payWay":"","productCode":"G00001","productId":0,"productName":"测试商品1","productSpec":"盘/份","salesType":"","status":"","thumbnailUrl":"","unitPrice":20.00,
         * "unitScore":0,"useRedEnvelope":0}],"orderPayDetails":[{"compId":0,"id":0,"orderId":0,"payAmount":10.00,"payId":"","payTime":"20190521200620","payWay":"r","remark":"","result":""}]
         * */
        private Long buyCount;
        private Long catId;
        private String catName;
        private Long compId;
        private Integer cou;
        private BigDecimal deliveryFee;
        private Long detailedId;
//        private String factSalesPrice;
        private BigDecimal getRedEnvelope;
        private BigDecimal marketPrice;
        private String orderId;
        private String productCode;
        private String productName;
        private Long productId;
        private String productSpec;
        private String salesType;
        private String status;

        private String thumbnailUrl;
        private BigDecimal unitPrice;
        private Long unitScore;
        private BigDecimal useRedEnvelope;

        public Long getBuyCount() {
            return buyCount;
        }

        public void setBuyCount(Long buyCount) {
            this.buyCount = buyCount;
        }


        public Long getCatId() {
            return catId;
        }

        public void setCatId(Long catId) {
            this.catId = catId;
        }

        public String getCatName() {
            return catName;
        }

        public void setCatName(String catName) {
            this.catName = catName;
        }

        public Long getCompId() {
            return compId;
        }

        public void setCompId(Long compId) {
            this.compId = compId;
        }

        public Integer getCou() {
            return cou;
        }

        public void setCou(Integer cou) {
            this.cou = cou;
        }

        public BigDecimal getDeliveryFee() {
            return deliveryFee;
        }

        public void setDeliveryFee(BigDecimal deliveryFee) {
            this.deliveryFee = deliveryFee;
        }

        public Long getDetailedId() {
            return detailedId;
        }

        public void setDetailedId(Long detailedId) {
            this.detailedId = detailedId;
        }

        public BigDecimal getGetRedEnvelope() {
            return getRedEnvelope;
        }

        public void setGetRedEnvelope(BigDecimal getRedEnvelope) {
            this.getRedEnvelope = getRedEnvelope;
        }

        public BigDecimal getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(BigDecimal marketPrice) {
            this.marketPrice = marketPrice;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductSpec() {
            return productSpec;
        }

        public void setProductSpec(String productSpec) {
            this.productSpec = productSpec;
        }

        public String getSalesType() {
            return salesType;
        }

        public void setSalesType(String salesType) {
            this.salesType = salesType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }

        public BigDecimal getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public Long getUnitScore() {
            return unitScore;
        }

        public void setUnitScore(Long unitScore) {
            this.unitScore = unitScore;
        }

        public BigDecimal getUseRedEnvelope() {
            return useRedEnvelope;
        }

        public void setUseRedEnvelope(BigDecimal useRedEnvelope) {
            this.useRedEnvelope = useRedEnvelope;
        }
    }


    public static class CollectionFlowBean implements Serializable{

        /**
         *
         * "orderDetails":{"amount":10.00,"brandId":0,"buyCount":0,"buyMsg":"","compId":0,"coun":0,"delivType":"",
         * "fromId":"","fromType":"","memberId":0,"memberName":"","memberTel":"","orderCode":201905220101,"orderId":1,"orderState":"","orderType":"P","orderTypeName":"","orderTypes":"",
         * "payChannel":"","productCode":"","productId":1,"productIds":"","recommendMemberName":"","recommendMemberTel":"","salesTime":"2019-05-22 10:59:50","staffId":0,"staffName":"",
         * "staffTel":"","storeId":0,"storeName":"","unitPrice":0,"useCash":0,"useCashAmount":0,"useRedEnvelope":0,"useRedEnvelopeAmount":10.00}
         *
         * */

        private BigDecimal amount;
        private Long brandId;

        private Long buyCount;
        private String buyMsg;
        private Long compId;
        private Integer coun;
        private String delivType;
        private String fromId;
        private String fromType;
        private Long memberId;
        private String memberName;
        private String memberTel;
        private String orderCode;
        private Long orderId;
        private String orderState;
        private String orderType;
        private String orderTypeName;
        private String orderTypes;
        private String payChannel;

        private String productCode;
        private Long productId;
        private String productIds;
        private String recommendMemberName;
        private String recommendMemberTel;
        private String salesTime;


        private Long staffId;
        private String staffName;
        private String staffTel;
        private Long storeId;
        private String storeName;
        private BigDecimal unitPrice;


        private Long useCash;
        private Long useCashAmount;
        private BigDecimal useRedEnvelope;  //红包额度
        private BigDecimal useRedEnvelopeAmount;  //红包金额



        public String getBuyMsg() {
            return buyMsg;
        }

        public void setBuyMsg(String buyMsg) {
            this.buyMsg = buyMsg;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public Long getBrandId() {
            return brandId;
        }

        public void setBrandId(Long brandId) {
            this.brandId = brandId;
        }

        public Long getBuyCount() {
            return buyCount;
        }

        public void setBuyCount(Long buyCount) {
            this.buyCount = buyCount;
        }

        public Long getCompId() {
            return compId;
        }

        public void setCompId(Long compId) {
            this.compId = compId;
        }

        public Integer getCoun() {
            return coun;
        }

        public void setCoun(Integer coun) {
            this.coun = coun;
        }

        public String getDelivType() {
            return delivType;
        }

        public void setDelivType(String delivType) {
            this.delivType = delivType;
        }

        public String getFromId() {
            return fromId;
        }

        public void setFromId(String fromId) {
            this.fromId = fromId;
        }

        public String getFromType() {
            return fromType;
        }

        public void setFromType(String fromType) {
            this.fromType = fromType;
        }

        public Long getMemberId() {
            return memberId;
        }

        public void setMemberId(Long memberId) {
            this.memberId = memberId;
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getMemberTel() {
            return memberTel;
        }

        public void setMemberTel(String memberTel) {
            this.memberTel = memberTel;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public String getOrderState() {
            return orderState;
        }

        public void setOrderState(String orderState) {
            this.orderState = orderState;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getOrderTypeName() {
            return orderTypeName;
        }

        public void setOrderTypeName(String orderTypeName) {
            this.orderTypeName = orderTypeName;
        }

        public String getOrderTypes() {
            return orderTypes;
        }

        public void setOrderTypes(String orderTypes) {
            this.orderTypes = orderTypes;
        }

        public String getPayChannel() {
            return payChannel;
        }

        public void setPayChannel(String payChannel) {
            this.payChannel = payChannel;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public String getProductIds() {
            return productIds;
        }

        public void setProductIds(String productIds) {
            this.productIds = productIds;
        }

        public String getRecommendMemberName() {
            return recommendMemberName;
        }

        public void setRecommendMemberName(String recommendMemberName) {
            this.recommendMemberName = recommendMemberName;
        }

        public String getRecommendMemberTel() {
            return recommendMemberTel;
        }

        public void setRecommendMemberTel(String recommendMemberTel) {
            this.recommendMemberTel = recommendMemberTel;
        }

        public String getSalesTime() {
            return salesTime;
        }

        public void setSalesTime(String salesTime) {
            this.salesTime = salesTime;
        }

        public Long getStaffId() {
            return staffId;
        }

        public void setStaffId(Long staffId) {
            this.staffId = staffId;
        }

        public String getStaffName() {
            return staffName;
        }

        public void setStaffName(String staffName) {
            this.staffName = staffName;
        }

        public String getStaffTel() {
            return staffTel;
        }

        public void setStaffTel(String staffTel) {
            this.staffTel = staffTel;
        }

        public Long getStoreId() {
            return storeId;
        }

        public void setStoreId(Long storeId) {
            this.storeId = storeId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public BigDecimal getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
        }

        public Long getUseCash() {
            return useCash;
        }

        public void setUseCash(Long useCash) {
            this.useCash = useCash;
        }

        public Long getUseCashAmount() {
            return useCashAmount;
        }

        public void setUseCashAmount(Long useCashAmount) {
            this.useCashAmount = useCashAmount;
        }

        public BigDecimal getUseRedEnvelope() {
            return useRedEnvelope;
        }

        public void setUseRedEnvelope(BigDecimal useRedEnvelope) {
            this.useRedEnvelope = useRedEnvelope;
        }

        public BigDecimal getUseRedEnvelopeAmount() {
            return useRedEnvelopeAmount;
        }

        public void setUseRedEnvelopeAmount(BigDecimal useRedEnvelopeAmount) {
            this.useRedEnvelopeAmount = useRedEnvelopeAmount;
        }
    }
}
