package com.fubang.video.entity;

/**
 * Created by jacky on 2017/7/31.
 */
public class ForgetPassEntity {


    /**
     * status : success
     * info : {"ctoken":"1501479731_547740"}
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
         * ctoken : 1501479731_547740
         */

        private String ctoken;

        public String getCtoken() {
            return ctoken;
        }

        public void setCtoken(String ctoken) {
            this.ctoken = ctoken;
        }
    }
}
