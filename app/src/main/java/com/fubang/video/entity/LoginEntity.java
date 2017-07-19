package com.fubang.video.entity;

/**
 * Created by jacky on 2017/7/19.
 */
public class LoginEntity {
    /**
     * status : success
     * info : {"nuserid":"12","ctoken":"1500282906_346687"}
     */
    private String status;
    private InfoBean info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * nuserid : 12
         * ctoken : 1500282906_346687
         */

        private String nuserid;
        private String ctoken;

        public String getNuserid() {
            return nuserid;
        }

        public void setNuserid(String nuserid) {
            this.nuserid = nuserid;
        }

        public String getCtoken() {
            return ctoken;
        }

        public void setCtoken(String ctoken) {
            this.ctoken = ctoken;
        }
    }
}
