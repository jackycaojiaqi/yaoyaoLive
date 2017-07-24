package com.fubang.video.entity;

import java.util.List;

/**
 * Created by jacky on 2017/7/24.
 */
public class HomeEntity {

    /**
     * status : success
     * info : {"new_list":[{"nuserid":"10","cphoto":"a.png","calias":"tian","nage":null,"ngender":"0","ccity":null}],"online_list":[{"nuserid":"10","cphoto":"a.png","calias":"tian","nage":null,"ngender":"0","ccity":null}],"tuhao_list":[{"nuserid":"10","cphoto":"a.png","calias":"tian","nage":null,"ngender":"0","ccity":null}],"life_list":[{"nid":"7","cvideophoto":"pengyouquan_20170720094608_5876.jpg","cphoto":"touxiang_20170720051930_7208.jpg","calias":"小新"},{"nid":"6","cvideophoto":"pengyouquan_20170720094354_5986.jpg","cphoto":"touxiang_20170720051930_7208.jpg","calias":"小新"},{"nid":"5","cvideophoto":null,"cphoto":"a.png","calias":"tUUU"}]}
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
        private List<NewListBean> new_list;
        private List<OnlineListBean> online_list;
        private List<TuhaoListBean> tuhao_list;
        private List<LifeListBean> life_list;

        public List<NewListBean> getNew_list() {
            return new_list;
        }

        public void setNew_list(List<NewListBean> new_list) {
            this.new_list = new_list;
        }

        public List<OnlineListBean> getOnline_list() {
            return online_list;
        }

        public void setOnline_list(List<OnlineListBean> online_list) {
            this.online_list = online_list;
        }

        public List<TuhaoListBean> getTuhao_list() {
            return tuhao_list;
        }

        public void setTuhao_list(List<TuhaoListBean> tuhao_list) {
            this.tuhao_list = tuhao_list;
        }

        public List<LifeListBean> getLife_list() {
            return life_list;
        }

        public void setLife_list(List<LifeListBean> life_list) {
            this.life_list = life_list;
        }

        public static class NewListBean {
            /**
             * nuserid : 10
             * cphoto : a.png
             * calias : tian
             * nage : null
             * ngender : 0
             * ccity : null
             */

            private String nuserid;
            private String cphoto;
            private String calias;
            private String nage;
            private String ngender;
            private String ccity;

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
        }

        public static class OnlineListBean {
            /**
             * nuserid : 10
             * cphoto : a.png
             * calias : tian
             * nage : null
             * ngender : 0
             * ccity : null
             */

            private String nuserid;
            private String cphoto;
            private String calias;
            private String nage;
            private String ngender;
            private String ccity;

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
        }

        public static class TuhaoListBean {
            /**
             * nuserid : 10
             * cphoto : a.png
             * calias : tian
             * nage : null
             * ngender : 0
             * ccity : null
             */

            private String nuserid;
            private String cphoto;
            private String calias;
            private String nage;
            private String ngender;
            private String ccity;

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
        }

        public static class LifeListBean {
            /**
             * nid : 7
             * cvideophoto : pengyouquan_20170720094608_5876.jpg
             * cphoto : touxiang_20170720051930_7208.jpg
             * calias : 小新
             */

            private String nid;
            private String cvideophoto;
            private String cphoto;
            private String calias;

            public String getNid() {
                return nid;
            }

            public void setNid(String nid) {
                this.nid = nid;
            }

            public String getCvideophoto() {
                return cvideophoto;
            }

            public void setCvideophoto(String cvideophoto) {
                this.cvideophoto = cvideophoto;
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
        }
    }
}
