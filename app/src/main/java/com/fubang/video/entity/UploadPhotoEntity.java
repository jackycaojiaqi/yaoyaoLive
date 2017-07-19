package com.fubang.video.entity;

/**
 * Created by jacky on 2017/7/19.
 */
public class UploadPhotoEntity {


    /**
     * status : success
     * info : {"filename":"touxiang_12323_233.png"}
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
         * filename : touxiang_12323_233.png
         */

        private String filename;

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }
    }
}
