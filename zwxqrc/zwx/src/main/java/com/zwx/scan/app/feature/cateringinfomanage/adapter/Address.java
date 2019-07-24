package com.zwx.scan.app.feature.cateringinfomanage.adapter;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/01/12
 * desc   :
 * version: 1.0
 **/
public class Address {

    private String id;
    private String name;
    private String parentAreaId;
    private boolean status;
    private List<Address.CityBean> list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentAreaId() {
        return parentAreaId;
    }

    public void setParentAreaId(String parentAreaId) {
        this.parentAreaId = parentAreaId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<CityBean> getList() {
        return list;
    }

    public void setList(List<CityBean> list) {
        this.list = list;
    }

    public static class CityBean {
        private String id;
        private String name;
        private String parentAreaId;
        private boolean status;
        private List<Address.CityBean.AreaBean> list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentAreaId() {
            return parentAreaId;
        }

        public void setParentAreaId(String parentAreaId) {
            this.parentAreaId = parentAreaId;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public List<AreaBean> getList() {
            return list;
        }

        public void setList(List<AreaBean> list) {
            this.list = list;
        }

        public static class AreaBean {
            private String id;
            private String name;
            private String parentAreaId;
            private boolean status;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getParentAreaId() {
                return parentAreaId;
            }

            public void setParentAreaId(String parentAreaId) {
                this.parentAreaId = parentAreaId;
            }

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }
        }
    }
}
