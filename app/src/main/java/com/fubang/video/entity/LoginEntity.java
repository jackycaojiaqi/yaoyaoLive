package com.fubang.video.entity;

/**
 * Created by jacky on 2017/7/19.
 */
public class LoginEntity {

    /**
     * status : success
     * info : {"nuserid":"12","ninviterid":null,"calias":"小新","cpassword":"e10adc3949ba59abbe56e057f20f883e","nage":"19","ngender":"0","cbirthdate":"1998-07-21","ccountry":null,"cprovince":"浙江省","ccity":"台州市","cdistrict":"浙江省台州市椒江区白云山西路1402号靠近椒江区葭芷街道水门村卫生室(台州安民医院东南)","ctel":"15867083398","cwechat":null,"cqq":null,"cphoto":"touxiang_20170729092129_9028.jpg","cphotowall":"beijing_20170720051633_6688.jpg;beijing_20170720051642_7918.jpg;beijing_20170720053119_4286.jpg;beijing_20170720053127_4176.jpg","cprofile":"10578b97111048ebbb05744179346569","cvideophoto":"userinfovideobg_20170720020357_714.jpg","cidiograph":"小丸子你好呀","clabel":"聊污污;空虚寂寞冷;声音控","cname":null,"cidcard":null,"cregisterip":"115.202.4.188","dregisterdate":"2017-07-19 15:15:53.859042+08","ctoken":"1501488605_994208"}
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
         * cpassword : e10adc3949ba59abbe56e057f20f883e
         * nage : 19
         * ngender : 0
         * cbirthdate : 1998-07-21
         * ccountry : null
         * cprovince : 浙江省
         * ccity : 台州市
         * cdistrict : 浙江省台州市椒江区白云山西路1402号靠近椒江区葭芷街道水门村卫生室(台州安民医院东南)
         * ctel : 15867083398
         * cwechat : null
         * cqq : null
         * cphoto : touxiang_20170729092129_9028.jpg
         * cphotowall : beijing_20170720051633_6688.jpg;beijing_20170720051642_7918.jpg;beijing_20170720053119_4286.jpg;beijing_20170720053127_4176.jpg
         * cprofile : 10578b97111048ebbb05744179346569
         * cvideophoto : userinfovideobg_20170720020357_714.jpg
         * cidiograph : 小丸子你好呀
         * clabel : 聊污污;空虚寂寞冷;声音控
         * cname : null
         * cidcard : null
         * cregisterip : 115.202.4.188
         * dregisterdate : 2017-07-19 15:15:53.859042+08
         * ctoken : 1501488605_994208
         */

        private String nuserid;
        private Object ninviterid;
        private String calias;
        private String cpassword;
        private String nage;
        private String ngender;
        private String cbirthdate;
        private Object ccountry;
        private String cprovince;
        private String ccity;
        private String cdistrict;
        private String ctel;
        private Object cwechat;
        private Object cqq;
        private String cphoto;
        private String cphotowall;
        private String cprofile;
        private String cvideophoto;
        private String cidiograph;
        private String clabel;
        private Object cname;
        private Object cidcard;
        private String cregisterip;
        private String dregisterdate;
        private String ctoken;

        public String getNuserid() {
            return nuserid;
        }

        public void setNuserid(String nuserid) {
            this.nuserid = nuserid;
        }

        public Object getNinviterid() {
            return ninviterid;
        }

        public void setNinviterid(Object ninviterid) {
            this.ninviterid = ninviterid;
        }

        public String getCalias() {
            return calias;
        }

        public void setCalias(String calias) {
            this.calias = calias;
        }

        public String getCpassword() {
            return cpassword;
        }

        public void setCpassword(String cpassword) {
            this.cpassword = cpassword;
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

        public Object getCcountry() {
            return ccountry;
        }

        public void setCcountry(Object ccountry) {
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

        public String getCtel() {
            return ctel;
        }

        public void setCtel(String ctel) {
            this.ctel = ctel;
        }

        public Object getCwechat() {
            return cwechat;
        }

        public void setCwechat(Object cwechat) {
            this.cwechat = cwechat;
        }

        public Object getCqq() {
            return cqq;
        }

        public void setCqq(Object cqq) {
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

        public Object getCname() {
            return cname;
        }

        public void setCname(Object cname) {
            this.cname = cname;
        }

        public Object getCidcard() {
            return cidcard;
        }

        public void setCidcard(Object cidcard) {
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

        public String getCtoken() {
            return ctoken;
        }

        public void setCtoken(String ctoken) {
            this.ctoken = ctoken;
        }
    }
}
