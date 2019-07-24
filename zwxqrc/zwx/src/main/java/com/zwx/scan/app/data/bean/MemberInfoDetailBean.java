package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/12
 * desc   :
 * version: 1.0
 **/
public class MemberInfoDetailBean implements Serializable {

private List<MemberInfoDetail> memberIT;

    public List<MemberInfoDetail> getMemberIT() {
        return memberIT;
    }

    public void setMemberIT(List<MemberInfoDetail> memberIT) {
        this.memberIT = memberIT;
    }

    public   class  MemberInfoDetail {
        private String  compEatTotalAmt;
        private  Integer compEatTotalQnt;

        private String compMemTypeId;

        private String memTypeName;
        private  String memberId;
        private String storeName;
        private String joinStoreName;
        private String joinSysTime;
        private String whatType;

        private String colour;
        private String displayRule;   //显示内容规

        private String background;
        private String quitTime;

        private String joinCondition;   //加入条件 免费在线领取-0 在线购买-1

        public String getQuitTime() {
            return quitTime;
        }

        public void setQuitTime(String quitTime) {
            this.quitTime = quitTime;
        }

        public String getJoinCondition() {
            return joinCondition;
        }

        public void setJoinCondition(String joinCondition) {
            this.joinCondition = joinCondition;
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public String getColour() {
            return colour;
        }

        public void setColour(String colour) {
            this.colour = colour;
        }

        public String getDisplayRule() {
            return displayRule;
        }

        public void setDisplayRule(String displayRule) {
            this.displayRule = displayRule;
        }

        public String getJoinStoreName() {
            return joinStoreName;
        }

        public void setJoinStoreName(String joinStoreName) {
            this.joinStoreName = joinStoreName;
        }

        public String getWhatType() {
            return whatType;
        }

        public void setWhatType(String whatType) {
            this.whatType = whatType;
        }

        public String getCompEatTotalAmt() {
            return compEatTotalAmt;
        }

        public void setCompEatTotalAmt(String compEatTotalAmt) {
            this.compEatTotalAmt = compEatTotalAmt;
        }

        public Integer getCompEatTotalQnt() {
            return compEatTotalQnt;
        }

        public void setCompEatTotalQnt(Integer compEatTotalQnt) {
            this.compEatTotalQnt = compEatTotalQnt;
        }

        public String getCompMemTypeId() {
            return compMemTypeId;
        }

        public void setCompMemTypeId(String compMemTypeId) {
            this.compMemTypeId = compMemTypeId;
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

        public String getJoinSysTime() {
            return joinSysTime;
        }

        public void setJoinSysTime(String joinSysTime) {
            this.joinSysTime = joinSysTime;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }
    }
}
