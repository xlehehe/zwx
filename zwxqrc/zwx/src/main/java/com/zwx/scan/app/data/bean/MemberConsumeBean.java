package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/12
 * desc   :
 * version: 1.0
 **/
public class MemberConsumeBean implements Serializable {




    public MemberIT memberIT;

    private  Integer recordsTotal;

    public MemberIT getMemberIT() {
        return memberIT;
    }

    public void setMemberIT(MemberIT memberIT) {
        this.memberIT = memberIT;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public static class MemberIT implements Serializable{
        private Integer total;

        private Integer recordsFiltered;

        private List<MemberConsume> data;

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getRecordsFiltered() {
            return recordsFiltered;
        }

        public void setRecordsFiltered(Integer recordsFiltered) {
            this.recordsFiltered = recordsFiltered;
        }

        public List<MemberConsume> getData() {
            return data;
        }

        public void setData(List<MemberConsume> data) {
            this.data = data;
        }

        public static  class MemberConsume implements Serializable{

            private BigDecimal compEatTotalAmt;

            private  Integer compEatTotalQnt;

            private  String compId;

            private  String  compMemberGroup;

            private String consumeTime;

            private String memberId;
            private String compMemTypeId;
            private String memTypeName;
            private String memberTel;

            private Integer sexType;

            private String memberName;

            private String storeAdress;

            private String  brirthday;

            private String marry; // 0-未婚 1已婚



            public String getMarry() {
                return marry;
            }

            public void setMarry(String marry) {
                this.marry = marry;
            }

            public String getBrirthday() {
                return brirthday;
            }

            public void setBrirthday(String brirthday) {
                this.brirthday = brirthday;
            }

            public String getStoreAdress() {
                return storeAdress;
            }

            public void setStoreAdress(String storeAdress) {
                this.storeAdress = storeAdress;
            }

            public String getCompMemTypeId() {
                return compMemTypeId;
            }

            public void setCompMemTypeId(String compMemTypeId) {
                this.compMemTypeId = compMemTypeId;
            }

            public BigDecimal getCompEatTotalAmt() {
                return compEatTotalAmt;
            }

            public void setCompEatTotalAmt(BigDecimal compEatTotalAmt) {
                this.compEatTotalAmt = compEatTotalAmt;
            }

            public Integer getCompEatTotalQnt() {
                return compEatTotalQnt;
            }

            public void setCompEatTotalQnt(Integer compEatTotalQnt) {
                this.compEatTotalQnt = compEatTotalQnt;
            }

            public String getCompId() {
                return compId;
            }

            public void setCompId(String compId) {
                this.compId = compId;
            }

            public String getCompMemberGroup() {
                return compMemberGroup;
            }

            public void setCompMemberGroup(String compMemberGroup) {
                this.compMemberGroup = compMemberGroup;
            }

            public String getConsumeTime() {
                return consumeTime;
            }

            public void setConsumeTime(String consumeTime) {
                this.consumeTime = consumeTime;
            }

            public String getMemberId() {
                return memberId;
            }

            public void setMemberId(String memberId) {
                this.memberId = memberId;
            }

            public String getMemTypeName() {
                return memTypeName;
            }

            public void setMemTypeName(String memTypeName) {
                this.memTypeName = memTypeName;
            }

            public String getMemberTel() {
                return memberTel;
            }

            public void setMemberTel(String memberTel) {
                this.memberTel = memberTel;
            }

            public Integer getSexType() {
                return sexType;
            }

            public void setSexType(Integer sexType) {
                this.sexType = sexType;
            }

            public String getMemberName() {
                return memberName;
            }

            public void setMemberName(String memberName) {
                this.memberName = memberName;
            }
        }
    }
}
