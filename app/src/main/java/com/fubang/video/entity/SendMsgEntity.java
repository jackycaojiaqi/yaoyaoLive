package com.fubang.video.entity;

/**
 * Created by jacky on 2017/7/19.
 */
public class SendMsgEntity {


    /**
     * status : fail
     * info : {"nuserid":"12"}
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
         */

        private String nuserid;

        public String getNuserid() {
            return nuserid;
        }

        public void setNuserid(String nuserid) {
            this.nuserid = nuserid;
        }
    }
}
