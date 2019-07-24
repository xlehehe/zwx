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
public class CollectionFlowResultBean implements Serializable {

    private List<CollectionFlowBean> list;

    public List<CollectionFlowBean> getList() {
        return list;
    }

    public void setList(List<CollectionFlowBean> list) {
        this.list = list;
    }

    public class CollectionFlowBean implements Serializable{
        /**
         * "list":[{"amount":10.00,"brandId":0,"buyCount":1,"buyMsg":"","compId":0,"coun":0,"delivType":"","fromId":"","fromType":"","memberId":0,"memberName":"","memberTel":"","orderCode":201905220101,
         * "orderId":1,"orderState":"","orderType":"P","orderTypeName":"测试商品1","orderTypes":"","payChannel":"tl","productCode":"","productId":0,"productIds":"","recommendMemberName":"",
         * "recommendMemberTel":"","salesTime":"2019-05-22 10:59:50","staffId":0,
         * "staffName":"","staffTel":"","storeId":0,"storeName":"","unitPrice":0,"useCash":0,"useCashAmount":0,"useRedEnvelope":0,"useRedEnvelopeAmount":10.00}]
         * */


        private BigDecimal amount;
        private String brandId;
        private Long buyCount;
        private String compId;
        private Integer coun;
        private String delivType;
        private String fromId;
        private String fromType;
        private String memberId;
        private String memberName;
        private String memberTel;
        private String orderCode;
        private String orderId;
        private String orderState;
        private String orderType;
        private String orderTypeName;
        private String orderTypes;
        private String payChannel;

        private String productCode;
        private String productId;
        private String productIds;
        private String recommendMemberName;
        private String recommendMemberTel;
        private String salesTime;


        private String staffId;
        private String staffName;
        private String staffTel;
        private String storeId;
        private String storeName;
        private BigDecimal unitPrice;


        private Integer useCash;
        private Integer useCashAmount;
        private BigDecimal useRedEnvelope;  //红包额度
        private BigDecimal useRedEnvelopeAmount;  //红包金额

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public Long getBuyCount() {
            return buyCount;
        }

        public void setBuyCount(Long buyCount) {
            this.buyCount = buyCount;
        }

        public String getCompId() {
            return compId;
        }

        public void setCompId(String compId) {
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

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
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

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
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

        public String getStaffId() {
            return staffId;
        }

        public void setStaffId(String staffId) {
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

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
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

        public Integer getUseCash() {
            return useCash;
        }

        public void setUseCash(Integer useCash) {
            this.useCash = useCash;
        }

        public Integer getUseCashAmount() {
            return useCashAmount;
        }

        public void setUseCashAmount(Integer useCashAmount) {
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
