package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/10
 * desc   :
 * version: 1.0
 **/
public class MoreStoreBean implements Serializable {




    private List<BrandStoreBean> brandList;


    public List<BrandStoreBean> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<BrandStoreBean> brandList) {
        this.brandList = brandList;
    }

    public   class BrandStoreBean implements Serializable{

        /**
         * ID
         */
        private String BrandId;

        /**
         * 品牌名称
         */
        private String brandName;
        private String brandLogo;
        private List<Store> storeList;


        public String getBrandId() {
            return BrandId;
        }

        public void setBrandId(String brandId) {
            BrandId = brandId;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public List<Store> getStoreList() {
            return storeList;
        }

        public void setStoreList(List<Store> storeList) {
            this.storeList = storeList;
        }

        public String getBrandLogo() {
            return brandLogo;
        }

        public void setBrandLogo(String brandLogo) {
            this.brandLogo = brandLogo;
        }
    }



    public class BrandBean implements Serializable {


        /**
         * ID
         */
        private String BrandId;

        /**
         * 品牌名称
         */
        private String brandName;
        private String brandLogo;


        public String getBrandId() {
            return BrandId;
        }

        public void setBrandId(String brandId) {
            BrandId = brandId;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getBrandLogo() {
            return brandLogo;
        }

        public void setBrandLogo(String brandLogo) {
            this.brandLogo = brandLogo;
        }
    }


    public class StoreBean implements Serializable {
        /**
         * ID
         */
        private String storeId;

        /**
         * 品牌名称
         */
        private String storeName;

        private boolean isChecked;




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


        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }
}
