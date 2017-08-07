package com.fubang.video.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by jacky on 2017/8/7.
 */
public class ChaoSongEntity {

    /**
     * info : {"data_list":[{"nuserid":"13","cphoto":"touxiang_20170725035610_3737.jpg","calias":"小小年纪","nage":"12","ngender":"1","ccity":"台州市","nsuccess":"0","all_con":"0","onumber":"18"},{"nuserid":"3","cphoto":"touxiang_20170725035610_3737.jpg","calias":"gg","nage":"19","ngender":"1","ccity":"台州市","nsuccess":"0","all_con":"0","onumber":"0"},{"nuserid":"4","cphoto":"touxiang_20170725035610_3737.jpg","calias":"gg","nage":"19","ngender":"1","ccity":"台州市","nsuccess":"0","all_con":"0","onumber":"0"},{"nuserid":"5","cphoto":"touxiang_20170725035610_3737.jpg","calias":"gg","nage":"19","ngender":"1","ccity":"台州市","nsuccess":"0","all_con":"0","onumber":"0"},{"nuserid":"6","cphoto":"touxiang_20170725035610_3737.jpg","calias":"gg","nage":"19","ngender":"1","ccity":"台州市","nsuccess":"0","all_con":"0","onumber":"0"},{"nuserid":"7","cphoto":"touxiang_20170725035610_3737.jpg","calias":"io","nage":"19","ngender":"1","ccity":"台州市","nsuccess":"0","all_con":"0","onumber":"0"},{"nuserid":"17","cphoto":null,"calias":"妮妮","nage":null,"ngender":"1","ccity":null,"nsuccess":"0","all_con":"0","onumber":"0"},{"nuserid":"11","cphoto":"touxiang_20170725035610_3737.jpg","calias":"tUUU","nage":"19","ngender":"1","ccity":"台州市","nsuccess":"0","all_con":"0","onumber":"0"}]}
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

        public static class DataListBean implements MultiItemEntity {
            /**
             * nuserid : 13
             * cphoto : touxiang_20170725035610_3737.jpg
             * calias : 小小年纪
             * nage : 12
             * ngender : 1
             * ccity : 台州市
             * nsuccess : 0
             * all_con : 0
             * onumber : 18
             */

            private String nuserid;
            private String cphoto;
            private String calias;
            private String nage;
            private String ngender;
            private String ccity;
            private String nsuccess;
            private String all_con;
            private String onumber;
            private String nstatus;

            public String getNstatus() {
                return nstatus;
            }

            public void setNstatus(String nstatus) {
                this.nstatus = nstatus;
            }

            private int itemtype = 0;

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

            public String getOnumber() {
                return onumber;
            }

            public void setOnumber(String onumber) {
                this.onumber = onumber;
            }

            @Override
            public int getItemType() {
                return itemtype;
            }

            public int getItemtype() {
                return itemtype;
            }

            public void setItemtype(int itemtype) {
                this.itemtype = itemtype < 2 ? itemtype : 2;
            }
        }
    }
}
