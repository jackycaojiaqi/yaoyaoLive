package com.fubang.video.entity;

import java.util.List;

/**
 * Created by jacky on 2017/8/9.
 */
public class NoticeEntity {


    /**
     * info : {"data_list":[{"nid":"1","nopuserid":"11","copalias":"系统","ctheme":"xxx","ccontent":"xxx","dtime":"2017-08-09 15:44:20.516019+08"}]}
     * status : success
     */

    private InfoBean info;
    private String status;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class InfoBean {
        private List<DataListBean> data_list;

        public List<DataListBean> getData_list() {
            return data_list;
        }

        public void setData_list(List<DataListBean> data_list) {
            this.data_list = data_list;
        }

        public static class DataListBean {
            /**
             * nid : 1
             * nopuserid : 11
             * copalias : 系统
             * ctheme : xxx
             * ccontent : xxx
             * dtime : 2017-08-09 15:44:20.516019+08
             */

            private String nid;
            private String nopuserid;
            private String copalias;
            private String ctheme;
            private String ccontent;
            private String dtime;

            public String getNid() {
                return nid;
            }

            public void setNid(String nid) {
                this.nid = nid;
            }

            public String getNopuserid() {
                return nopuserid;
            }

            public void setNopuserid(String nopuserid) {
                this.nopuserid = nopuserid;
            }

            public String getCopalias() {
                return copalias;
            }

            public void setCopalias(String copalias) {
                this.copalias = copalias;
            }

            public String getCtheme() {
                return ctheme;
            }

            public void setCtheme(String ctheme) {
                this.ctheme = ctheme;
            }

            public String getCcontent() {
                return ccontent;
            }

            public void setCcontent(String ccontent) {
                this.ccontent = ccontent;
            }

            public String getDtime() {
                return dtime;
            }

            public void setDtime(String dtime) {
                this.dtime = dtime;
            }
        }
    }
}
