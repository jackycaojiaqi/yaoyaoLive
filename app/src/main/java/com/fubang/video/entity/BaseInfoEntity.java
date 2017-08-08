package com.fubang.video.entity;

import java.util.List;

/**
 * Created by jacky on 2017/7/19.
 */
public class BaseInfoEntity {


    /**
     * status : success
     * info : {"nuserid":"12","ninviterid":null,"calias":"小新","nage":"16","ngender":"1","cbirthdate":"2001-07-21","ccountry":null,"cprovince":"浙江省","ccity":"台州市","cdistrict":"浙江省台州市椒江区白云山西路1402号靠近椒江区葭芷街道水门村卫生室(台州安民医院东南)","cwechat":null,"cqq":null,"cphoto":"touxiang_20170720051930_7208.jpg","cphotowall":"beijing_20170720051633_6688.jpg;beijing_20170720051642_7918.jpg;beijing_20170720053119_4286.jpg;beijing_20170720053127_4176.jpg","cprofile":"10578b97111048ebbb05744179346569","cvideophoto":"userinfovideobg_20170720020357_714.jpg","cidiograph":"小丸子你好呀","clabel":"聊污污;空虚寂寞冷","cname":null,"cidcard":null,"cregisterip":"115.202.4.188","dregisterdate":"2017-07-19 15:15:53.859042+08","nmoney":"10000","dviptime":"1970-01-01 00:00:00+08","nstatus":"2","nprice":"20","nlongitude":null,"nlatitude":null,"clocation":null,"chenggong":2035,"shibai":2012,"ntime":0,"recent":[{"nuserid":"878","calias":"878","cphoto":null},{"nuserid":"683","calias":"683","cphoto":null},{"nuserid":"812","calias":"812","cphoto":null},{"nuserid":"401","calias":"401","cphoto":null},{"nuserid":"764","calias":"764","cphoto":null},{"nuserid":"997","calias":"997","cphoto":null},{"nuserid":"740","calias":"740","cphoto":null},{"nuserid":"512","calias":"512","cphoto":null},{"nuserid":"765","calias":"765","cphoto":null},{"nuserid":"752","calias":"752","cphoto":null}]}
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
         * ninviterid : null
         * calias : 小新
         * nage : 16
         * ngender : 1
         * cbirthdate : 2001-07-21
         * ccountry : null
         * cprovince : 浙江省
         * ccity : 台州市
         * cdistrict : 浙江省台州市椒江区白云山西路1402号靠近椒江区葭芷街道水门村卫生室(台州安民医院东南)
         * cwechat : null
         * cqq : null
         * cphoto : touxiang_20170720051930_7208.jpg
         * cphotowall : beijing_20170720051633_6688.jpg;beijing_20170720051642_7918.jpg;beijing_20170720053119_4286.jpg;beijing_20170720053127_4176.jpg
         * cprofile : 10578b97111048ebbb05744179346569
         * cvideophoto : userinfovideobg_20170720020357_714.jpg
         * cidiograph : 小丸子你好呀
         * clabel : 聊污污;空虚寂寞冷
         * cname : null
         * cidcard : null
         * cregisterip : 115.202.4.188
         * dregisterdate : 2017-07-19 15:15:53.859042+08
         * nmoney : 10000
         * dviptime : 1970-01-01 00:00:00+08
         * nstatus : 2
         * nprice : 20
         * nlongitude : null
         * nlatitude : null
         * clocation : null
         * chenggong : 2035
         * shibai : 2012
         * ntime : 0
         * recent : [{"nuserid":"878","calias":"878","cphoto":null},{"nuserid":"683","calias":"683","cphoto":null},{"nuserid":"812","calias":"812","cphoto":null},{"nuserid":"401","calias":"401","cphoto":null},{"nuserid":"764","calias":"764","cphoto":null},{"nuserid":"997","calias":"997","cphoto":null},{"nuserid":"740","calias":"740","cphoto":null},{"nuserid":"512","calias":"512","cphoto":null},{"nuserid":"765","calias":"765","cphoto":null},{"nuserid":"752","calias":"752","cphoto":null}]
         */

        private String nuserid;
        private String ninviterid;
        private String calias;
        private String nage;
        private String ngender;
        private String cbirthdate;
        private String ccountry;
        private String cprovince;
        private String ccity;
        private String cdistrict;
        private String cwechat;
        private String cqq;
        private String cphoto;
        private String cphotowall;
        private String cprofile;
        private String cvideophoto;
        private String cidiograph;
        private String clabel;
        private String cname;
        private String ctel;
        private String cidcard;
        private String cregisterip;
        private String dregisterdate;
        private long nmoney = 0;
        private String dviptime;
        private String nstatus;
        private String nprice;
        private String nlongitude;
        private String nlatitude;
        private String clocation;
        private int chenggong;
        private int shibai;
        private int ntime;
        private List<RecentBean> recent;

        public String getCtel() {
            return ctel;
        }

        public void setCtel(String ctel) {
            this.ctel = ctel;
        }

        public String getNuserid() {
            return nuserid;
        }

        public void setNuserid(String nuserid) {
            this.nuserid = nuserid;
        }

        public String getNinviterid() {
            return ninviterid;
        }

        public void setNinviterid(String ninviterid) {
            this.ninviterid = ninviterid;
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

        public String getCbirthdate() {
            return cbirthdate;
        }

        public void setCbirthdate(String cbirthdate) {
            this.cbirthdate = cbirthdate;
        }

        public String getCcountry() {
            return ccountry;
        }

        public void setCcountry(String ccountry) {
            this.ccountry = ccountry;
        }

        public String getCprovince() {
            return cprovince;
        }

        public void setCprovince(String cprovince) {
            this.cprovince = cprovince;
        }

        public String getCcity() {
            return ccity;
        }

        public void setCcity(String ccity) {
            this.ccity = ccity;
        }

        public String getCdistrict() {
            return cdistrict;
        }

        public void setCdistrict(String cdistrict) {
            this.cdistrict = cdistrict;
        }

        public String getCwechat() {
            return cwechat;
        }

        public void setCwechat(String cwechat) {
            this.cwechat = cwechat;
        }

        public String getCqq() {
            return cqq;
        }

        public void setCqq(String cqq) {
            this.cqq = cqq;
        }

        public String getCphoto() {
            return cphoto;
        }

        public void setCphoto(String cphoto) {
            this.cphoto = cphoto;
        }

        public String getCphotowall() {
            return cphotowall;
        }

        public void setCphotowall(String cphotowall) {
            this.cphotowall = cphotowall;
        }

        public String getCprofile() {
            return cprofile;
        }

        public void setCprofile(String cprofile) {
            this.cprofile = cprofile;
        }

        public String getCvideophoto() {
            return cvideophoto;
        }

        public void setCvideophoto(String cvideophoto) {
            this.cvideophoto = cvideophoto;
        }

        public String getCidiograph() {
            return cidiograph;
        }

        public void setCidiograph(String cidiograph) {
            this.cidiograph = cidiograph;
        }

        public String getClabel() {
            return clabel;
        }

        public void setClabel(String clabel) {
            this.clabel = clabel;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getCidcard() {
            return cidcard;
        }

        public void setCidcard(String cidcard) {
            this.cidcard = cidcard;
        }

        public String getCregisterip() {
            return cregisterip;
        }

        public void setCregisterip(String cregisterip) {
            this.cregisterip = cregisterip;
        }

        public String getDregisterdate() {
            return dregisterdate;
        }

        public void setDregisterdate(String dregisterdate) {
            this.dregisterdate = dregisterdate;
        }

        public long getNmoney() {
            return nmoney;
        }

        public void setNmoney(long nmoney) {
            this.nmoney = nmoney;
        }

        public String getDviptime() {
            return dviptime;
        }

        public void setDviptime(String dviptime) {
            this.dviptime = dviptime;
        }

        public String getNstatus() {
            return nstatus;
        }

        public void setNstatus(String nstatus) {
            this.nstatus = nstatus;
        }

        public String getNprice() {
            return nprice;
        }

        public void setNprice(String nprice) {
            this.nprice = nprice;
        }

        public String getNlongitude() {
            return nlongitude;
        }

        public void setNlongitude(String nlongitude) {
            this.nlongitude = nlongitude;
        }

        public String getNlatitude() {
            return nlatitude;
        }

        public void setNlatitude(String nlatitude) {
            this.nlatitude = nlatitude;
        }

        public String getClocation() {
            return clocation;
        }

        public void setClocation(String clocation) {
            this.clocation = clocation;
        }

        public int getChenggong() {
            return chenggong;
        }

        public void setChenggong(int chenggong) {
            this.chenggong = chenggong;
        }

        public int getShibai() {
            return shibai;
        }

        public void setShibai(int shibai) {
            this.shibai = shibai;
        }

        public int getNtime() {
            return ntime;
        }

        public void setNtime(int ntime) {
            this.ntime = ntime;
        }

        public List<RecentBean> getRecent() {
            return recent;
        }

        public void setRecent(List<RecentBean> recent) {
            this.recent = recent;
        }

        public static class RecentBean {
            /**
             * nuserid : 878
             * calias : 878
             * cphoto : null
             */

            private String nuserid;
            private String calias;
            private String cphoto;

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
        }
    }
}
