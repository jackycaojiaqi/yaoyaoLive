package com.fubang.video.entity;

import java.util.List;

/**
 * Created by jacky on 2017/8/7.
 */
public class HomeListEntity {


    /**
     * info : {"data_list":[{"nuserid":"17","cphoto":null,"calias":"妮妮","nage":null,"ngender":"1","ccity":null,"nsuccess":"0","all_con":"0","l_dtime":"2017-08-05 18:26:36.303693+08"},{"nuserid":"13","cphoto":"touxiang_20170725035610_3737.jpg","calias":"小小年纪","nage":"12","ngender":"1","ccity":"台州市","nsuccess":"0","all_con":"0","l_dtime":"2017-08-05 16:53:36.420882+08"},{"nuserid":"3","cphoto":"touxiang_20170725035610_3737.jpg","calias":"gg","nage":"19","ngender":"1","ccity":"台州市","nsuccess":"0","all_con":"0","l_dtime":"1999-01-01 00:00:00+08"},{"nuserid":"4","cphoto":"touxiang_20170725035610_3737.jpg","calias":"gg","nage":"19","ngender":"1","ccity":"台州市","nsuccess":"0","all_con":"0","l_dtime":"1999-01-01 00:00:00+08"},{"nuserid":"5","cphoto":"touxiang_20170725035610_3737.jpg","calias":"gg","nage":"19","ngender":"1","ccity":"台州市","nsuccess":"0","all_con":"0","l_dtime":"1999-01-01 00:00:00+08"},{"nuserid":"6","cphoto":"touxiang_20170725035610_3737.jpg","calias":"gg","nage":"19","ngender":"1","ccity":"台州市","nsuccess":"0","all_con":"0","l_dtime":"1999-01-01 00:00:00+08"},{"nuserid":"7","cphoto":"touxiang_20170725035610_3737.jpg","calias":"io","nage":"19","ngender":"1","ccity":"台州市","nsuccess":"0","all_con":"0","l_dtime":"1999-01-01 00:00:00+08"},{"nuserid":"11","cphoto":"touxiang_20170725035610_3737.jpg","calias":"tUUU","nage":"19","ngender":"1","ccity":"台州市","nsuccess":"0","all_con":"0","l_dtime":"1999-01-01 00:00:00+08"}]}
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
             * nuserid : 17
             * cphoto : null
             * calias : 妮妮
             * nage : null
             * ngender : 1
             * ccity : null
             * nsuccess : 0
             * all_con : 0
             * l_dtime : 2017-08-05 18:26:36.303693+08
             */

            private String nuserid;
            private String cphoto;
            private String calias;
            private String nage;
            private String ngender;
            private String ccity;
            private String nsuccess;
            private String all_con;
            private String l_dtime;
            private String nprice;
            private String nstatus;


            public String getNprice() {
                return nprice;
            }

            public void setNprice(String nprice) {
                this.nprice = nprice;
            }

            public String getNstatus() {
                return nstatus;
            }

            public void setNstatus(String nstatus) {
                this.nstatus = nstatus;
            }

            public String getNuserid() {
                return nuserid;
            }

            public void setNuserid(String nuserid) {
                this.nuserid = nuserid;
            }

            public String getCphoto() {
                return cphoto;
            }

            public void setCphoto(String cphoto) {
                this.cphoto = cphoto;
            }

            public String getCalias() {
                return calias;
            }

            public void setCalias(String calias) {
                this.calias = calias;
            }

            public String getNage() {
                return nage;
            }

            public void setNage(String nage) {
                this.nage = nage;
            }

            public String getNgender() {
                return ngender;
            }

            public void setNgender(String ngender) {
                this.ngender = ngender;
            }

            public String getCcity() {
                return ccity;
            }

            public void setCcity(String ccity) {
                this.ccity = ccity;
            }

            public String getNsuccess() {
                return nsuccess;
            }

            public void setNsuccess(String nsuccess) {
                this.nsuccess = nsuccess;
            }

            public String getAll_con() {
                return all_con;
            }

            public void setAll_con(String all_con) {
                this.all_con = all_con;
            }

            public String getL_dtime() {
                return l_dtime;
            }

            public void setL_dtime(String l_dtime) {
                this.l_dtime = l_dtime;
            }
        }
    }
}
