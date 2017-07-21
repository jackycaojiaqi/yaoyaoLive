package com.fubang.video.entity;

import java.util.List;

/**
 * Created by jacky on 2017/7/21.
 */
public class CircleListEntity {


    /**
     * status : success
     * info : [{"nid":"7","nuserid":"12","cvideo":"e027e26c572c4e61858a4138f7d1f1f7","cvideophoto":"pengyouquan_20170720094608_5876.jpg","ccontent":"855699325","ctopic":"#好声音#","nflower":"5","dtime":"2017-07-20 09:46:08.586852+08","nscan":"0","nreview":"0","nflowercount":"0","pnuserid":null,"pcalias":null,"hnuserid":null,"hcalias":null,"creview":null,"cphoto":null,"calias":"小新","nage":null,"ngender":"1","ccity":null},{"nid":"6","nuserid":"12","cvideo":"f08819703abe402fb55435751564b966","cvideophoto":"pengyouquan_20170720094354_5986.jpg","ccontent":"87645669","ctopic":"#好身材#","nflower":"5","dtime":"2017-07-20 09:43:54.326768+08","nscan":"0","nreview":"3","nflowercount":"0","pnuserid":"{520,750,740}","pcalias":"{520,750,740}","hnuserid":"{0,0,0}","hcalias":"{NULL,NULL,NULL}","creview":"{taihal,OK,OppppK}","cphoto":null,"calias":"小新","nage":null,"ngender":"1","ccity":null},{"nid":"5","nuserid":"11","cvideo":null,"cvideophoto":null,"ccontent":"9p09","ctopic":null,"nflower":"5","dtime":"2017-07-19 15:36:46.638406+08","nscan":"0","nreview":"1","nflowercount":"0","pnuserid":"{321}","pcalias":"{321}","hnuserid":"{0}","hcalias":"{NULL}","creview":"{maihao}","cphoto":"a.png","calias":"tUUU","nage":null,"ngender":"1","ccity":null},{"nid":"4","nuserid":"11","cvideo":null,"cvideophoto":null,"ccontent":"9p09","ctopic":null,"nflower":"5","dtime":"2017-07-19 15:36:43.483294+08","nscan":"0","nreview":"0","nflowercount":"0","pnuserid":null,"pcalias":null,"hnuserid":null,"hcalias":null,"creview":null,"cphoto":"a.png","calias":"tUUU","nage":null,"ngender":"1","ccity":null},{"nid":"3","nuserid":"11","cvideo":null,"cvideophoto":null,"ccontent":"9p09","ctopic":null,"nflower":"5","dtime":"2017-07-19 13:44:27.683799+08","nscan":"0","nreview":"0","nflowercount":"0","pnuserid":null,"pcalias":null,"hnuserid":null,"hcalias":null,"creview":null,"cphoto":"a.png","calias":"tUUU","nage":null,"ngender":"1","ccity":null}]
     */

    private String status;
    private List<InfoBean> info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * nid : 7
         * nuserid : 12
         * cvideo : e027e26c572c4e61858a4138f7d1f1f7
         * cvideophoto : pengyouquan_20170720094608_5876.jpg
         * ccontent : 855699325
         * ctopic : #好声音#
         * nflower : 5
         * dtime : 2017-07-20 09:46:08.586852+08
         * nscan : 0
         * nreview : 0
         * nflowercount : 0
         * pnuserid : null
         * pcalias : null
         * hnuserid : null
         * hcalias : null
         * creview : null
         * cphoto : null
         * calias : 小新
         * nage : null
         * ngender : 1
         * ccity : null
         */

        private String nid;
        private String nuserid;
        private String cvideo;
        private String cvideophoto;
        private String ccontent;
        private String ctopic;
        private String nflower;
        private String dtime;
        private String nscan;
        private String nreview;
        private String nflowercount;
        private String pnuserid;
        private String pcalias;
        private String hnuserid;
        private String hcalias;
        private String creview;
        private String cphoto;
        private String calias;
        private String nage;
        private String ngender;
        private String ccity;

        public String getNid() {
            return nid;
        }

        public void setNid(String nid) {
            this.nid = nid;
        }

        public String getNuserid() {
            return nuserid;
        }

        public void setNuserid(String nuserid) {
            this.nuserid = nuserid;
        }

        public String getCvideo() {
            return cvideo;
        }

        public void setCvideo(String cvideo) {
            this.cvideo = cvideo;
        }

        public String getCvideophoto() {
            return cvideophoto;
        }

        public void setCvideophoto(String cvideophoto) {
            this.cvideophoto = cvideophoto;
        }

        public String getCcontent() {
            return ccontent;
        }

        public void setCcontent(String ccontent) {
            this.ccontent = ccontent;
        }

        public String getCtopic() {
            return ctopic;
        }

        public void setCtopic(String ctopic) {
            this.ctopic = ctopic;
        }

        public String getNflower() {
            return nflower;
        }

        public void setNflower(String nflower) {
            this.nflower = nflower;
        }

        public String getDtime() {
            return dtime;
        }

        public void setDtime(String dtime) {
            this.dtime = dtime;
        }

        public String getNscan() {
            return nscan;
        }

        public void setNscan(String nscan) {
            this.nscan = nscan;
        }

        public String getNreview() {
            return nreview;
        }

        public void setNreview(String nreview) {
            this.nreview = nreview;
        }

        public String getNflowercount() {
            return nflowercount;
        }

        public void setNflowercount(String nflowercount) {
            this.nflowercount = nflowercount;
        }

        public String getPnuserid() {
            return pnuserid;
        }

        public void setPnuserid(String pnuserid) {
            this.pnuserid = pnuserid;
        }

        public String getPcalias() {
            return pcalias;
        }

        public void setPcalias(String pcalias) {
            this.pcalias = pcalias;
        }

        public String getHnuserid() {
            return hnuserid;
        }

        public void setHnuserid(String hnuserid) {
            this.hnuserid = hnuserid;
        }

        public String getHcalias() {
            return hcalias;
        }

        public void setHcalias(String hcalias) {
            this.hcalias = hcalias;
        }

        public String getCreview() {
            return creview;
        }

        public void setCreview(String creview) {
            this.creview = creview;
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
}
