package com.fubang.video.entity;

import java.util.List;

/**
 * Created by jacky on 2017/8/8.
 */
public class GiftRankEntity {


    /**
     * info : {"data_list":[{"nuserid":"12","calias":"小新","cphoto":"touxiang_20170729092129_9028.jpg","tonuserid":"13","tocalias":"小新","tocphoto":"touxiang_20170729092129_9028.jpg","ngiftid":"23","nusermoney":"100","dtime":"2017-08-05 17:03:53.928713+08"},{"nuserid":"13","calias":"小小年纪","cphoto":"touxiang_20170725035610_3737.jpg","tonuserid":"12","tocalias":"小小年纪","tocphoto":"touxiang_20170725035610_3737.jpg","ngiftid":"22","nusermoney":"100","dtime":"2017-08-05 17:02:54.956332+08"},{"nuserid":"13","calias":"小小年纪","cphoto":"touxiang_20170725035610_3737.jpg","tonuserid":"12","tocalias":"小小年纪","tocphoto":"touxiang_20170725035610_3737.jpg","ngiftid":"23","nusermoney":"100","dtime":"2017-08-05 17:02:51.924582+08"},{"nuserid":"12","calias":"小新","cphoto":"touxiang_20170729092129_9028.jpg","tonuserid":"13","tocalias":"小新","tocphoto":"touxiang_20170729092129_9028.jpg","ngiftid":"23","nusermoney":"100","dtime":"2017-08-05 15:49:25.484223+08"},{"nuserid":"12","calias":"小新","cphoto":"touxiang_20170729092129_9028.jpg","tonuserid":"13","tocalias":"小新","tocphoto":"touxiang_20170729092129_9028.jpg","ngiftid":"22","nusermoney":"100","dtime":"2017-08-05 15:49:20.805515+08"},{"nuserid":"13","calias":"小小年纪","cphoto":"touxiang_20170725035610_3737.jpg","tonuserid":"12","tocalias":"小小年纪","tocphoto":"touxiang_20170725035610_3737.jpg","ngiftid":"22","nusermoney":"100","dtime":"2017-08-05 10:09:18.484188+08"},{"nuserid":"12","calias":"小新","cphoto":"touxiang_20170729092129_9028.jpg","tonuserid":"13","tocalias":"小新","tocphoto":"touxiang_20170729092129_9028.jpg","ngiftid":"23","nusermoney":"100","dtime":"2017-08-05 10:09:13.208115+08"},{"nuserid":"13","calias":"小小年纪","cphoto":"touxiang_20170725035610_3737.jpg","tonuserid":"12","tocalias":"小小年纪","tocphoto":"touxiang_20170725035610_3737.jpg","ngiftid":"22","nusermoney":"100","dtime":"2017-08-05 10:09:01.34211+08"},{"nuserid":"12","calias":"小新","cphoto":"touxiang_20170729092129_9028.jpg","tonuserid":"13","tocalias":"小新","tocphoto":"touxiang_20170729092129_9028.jpg","ngiftid":"23","nusermoney":"100","dtime":"2017-08-05 10:08:58.759231+08"},{"nuserid":"13","calias":"小小年纪","cphoto":"touxiang_20170725035610_3737.jpg","tonuserid":"12","tocalias":"小小年纪","tocphoto":"touxiang_20170725035610_3737.jpg","ngiftid":"23","nusermoney":"100","dtime":"2017-08-05 10:08:38.664664+08"}]}
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
             * nuserid : 12
             * calias : 小新
             * cphoto : touxiang_20170729092129_9028.jpg
             * tonuserid : 13
             * tocalias : 小新
             * tocphoto : touxiang_20170729092129_9028.jpg
             * ngiftid : 23
             * nusermoney : 100
             * dtime : 2017-08-05 17:03:53.928713+08
             */

            private String nuserid;
            private String calias;
            private String cphoto;
            private String tonuserid;
            private String tocalias;
            private String tocphoto;
            private String ngiftid;
            private String nusermoney;
            private String dtime;

            public String getNuserid() {
                return nuserid;
            }

            public void setNuserid(String nuserid) {
                this.nuserid = nuserid;
            }

            public String getCalias() {
                return calias;
            }

            public void setCalias(String calias) {
                this.calias = calias;
            }

            public String getCphoto() {
                return cphoto;
            }

            public void setCphoto(String cphoto) {
                this.cphoto = cphoto;
            }

            public String getTonuserid() {
                return tonuserid;
            }

            public void setTonuserid(String tonuserid) {
                this.tonuserid = tonuserid;
            }

            public String getTocalias() {
                return tocalias;
            }

            public void setTocalias(String tocalias) {
                this.tocalias = tocalias;
            }

            public String getTocphoto() {
                return tocphoto;
            }

            public void setTocphoto(String tocphoto) {
                this.tocphoto = tocphoto;
            }

            public String getNgiftid() {
                return ngiftid;
            }

            public void setNgiftid(String ngiftid) {
                this.ngiftid = ngiftid;
            }

            public String getNusermoney() {
                return nusermoney;
            }

            public void setNusermoney(String nusermoney) {
                this.nusermoney = nusermoney;
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
