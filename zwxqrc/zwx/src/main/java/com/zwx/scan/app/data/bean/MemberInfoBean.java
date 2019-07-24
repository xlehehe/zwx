package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/12
 * desc   :
 * version: 1.0
 **/
public class MemberInfoBean implements Serializable {



    private  MemberList memberList;

    public MemberList getMemberList() {
        return memberList;
    }

    public void setMemberList(MemberList memberList) {
        this.memberList = memberList;
    }

    public static  class MemberList implements Serializable{
        private Integer total;
        private Integer recordsFiltered;

        private List<MemberInfo> data;

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

        public List<MemberInfo> getData() {
            return data;
        }

        public void setData(List<MemberInfo> data) {
            this.data = data;
        }
    }
    public static   class  MemberInfo implements Serializable{

        private String  memberTel;

        private String  memTypeName;

        private String  memberId;

        private String  memberName;

        private String  memberType;

        private String  joinSysTime;

        private String compMemTypeId;

        private String compMemberGroup;

        private String marry;

        private Integer sexType;

        private String brirthday;

        private String storeAddress;
        private String joinStoreName;

        private String whatType;  //活动类型


        public String getWhatType() {
            return whatType;
        }

        public void setWhatType(String whatType) {
            this.whatType = whatType;
        }

        public String getJoinStoreName() {
            return joinStoreName;
        }

        public void setJoinStoreName(String joinStoreName) {
            this.joinStoreName = joinStoreName;
        }

        public String getStoreAddress() {
            return storeAddress;
        }

        public void setStoreAddress(String storeAddress) {
            this.storeAddress = storeAddress;
        }

        public String getMemberTel() {
            return memberTel;
        }

        public void setMemberTel(String memberTel) {
            this.memberTel = memberTel;
        }

        public String getMemTypeName() {
            return memTypeName;
        }

        public void setMemTypeName(String memTypeName) {
            this.memTypeName = memTypeName;
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

        public String getMemberType() {
            return memberType;
        }

        public void setMemberType(String memberType) {
            this.memberType = memberType;
        }

        public String getJoinSysTime() {
            return joinSysTime;
        }

        public void setJoinSysTime(String joinSysTime) {
            this.joinSysTime = joinSysTime;
        }

        public String getCompMemTypeId() {
            return compMemTypeId;
        }

        public void setCompMemTypeId(String compMemTypeId) {
            this.compMemTypeId = compMemTypeId;
        }

        public String getCompMemberGroup() {
            return compMemberGroup;
        }

        public void setCompMemberGroup(String compMemberGroup) {
            this.compMemberGroup = compMemberGroup;
        }

        public String getMarry() {
            return marry;
        }

        public void setMarry(String marry) {
            this.marry = marry;
        }

        public Integer getSexType() {
            return sexType;
        }

        public void setSexType(Integer sexType) {
            this.sexType = sexType;
        }

        public String getBrirthday() {
            return brirthday;
        }

        public void setBrirthday(String brirthday) {
            this.brirthday = brirthday;
        }

    }

}
