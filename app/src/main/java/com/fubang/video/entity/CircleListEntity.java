package com.fubang.video.entity;

import java.util.List;

/**
 * Created by jacky on 2017/7/21.
 */
public class CircleListEntity {


    /**
     * status : success
     * info : [{"nid":"7","nuserid":"12","cvideo":"e027e26c572c4e61858a4138f7d1f1f7","cvideophoto":"pengyouquan_20170720094608_5876.jpg","ccontent":"855699325","ctopic":"#好声音#","nflower":"5","dtime":"2017-07-20 09:46:08.586852+08","nscan":"3","nreview":"0","nflowercount":"0","dtime1":"1500480000","cphoto":"touxiang_20170720051930_7208.jpg","calias":"小新","nage":"19","ngender":"1","ccity":"台州市","clabel":"聊污污;空虚寂寞冷;声音控","review_list":{"creview":[],"pnuserid":[],"pcalias":[],"hnuserid":[],"hcalias":[],"pdtime":[]}},{"nid":"6","nuserid":"12","cvideo":"f08819703abe402fb55435751564b966","cvideophoto":"pengyouquan_20170720094354_5986.jpg","ccontent":"87645669","ctopic":"#好身材#","nflower":"5","dtime":"2017-07-20 09:43:54.326768+08","nscan":"7","nreview":"3","nflowercount":"0","dtime1":"1500480000","cphoto":"touxiang_20170720051930_7208.jpg","calias":"小新","nage":"19","ngender":"1","ccity":"台州市","clabel":"聊污污;空虚寂寞冷;声音控","review_list":{"creview":["OppppK","OK","taihal"],"pnuserid":["740","750","520"],"pcalias":["740","750","520"],"hnuserid":[],"hcalias":[],"pdtime":["\"2017-07-20 14:17:34.776466+08\"","\"2017-07-20 14:17:19.027154+08\"","\"2017-07-20 14:16:58.143058+08\""]}},{"nid":"5","nuserid":"11","cvideo":null,"cvideophoto":null,"ccontent":"9p09","ctopic":null,"nflower":"5","dtime":"2017-07-19 15:36:46.638406+08","nscan":"2","nreview":"1","nflowercount":"0","dtime1":"1500393600","cphoto":"a.png","calias":"tUUU","nage":null,"ngender":"1","ccity":null,"clabel":null,"review_list":{"creview":["maihao"],"pnuserid":["321"],"pcalias":["321"],"hnuserid":[],"hcalias":[],"pdtime":["\"2017-07-20 14:18:01.9055+08\""]}},{"nid":"4","nuserid":"11","cvideo":null,"cvideophoto":null,"ccontent":"9p09","ctopic":null,"nflower":"5","dtime":"2017-07-19 15:36:43.483294+08","nscan":"0","nreview":"0","nflowercount":"0","dtime1":"1500393600","cphoto":"a.png","calias":"tUUU","nage":null,"ngender":"1","ccity":null,"clabel":null,"review_list":{"creview":[],"pnuserid":[],"pcalias":[],"hnuserid":[],"hcalias":[],"pdtime":[]}},{"nid":"3","nuserid":"11","cvideo":null,"cvideophoto":null,"ccontent":"9p09","ctopic":null,"nflower":"5","dtime":"2017-07-19 13:44:27.683799+08","nscan":"0","nreview":"0","nflowercount":"0","dtime1":"1500393600","cphoto":"a.png","calias":"tUUU","nage":null,"ngender":"1","ccity":null,"clabel":null,"review_list":{"creview":[],"pnuserid":[],"pcalias":[],"hnuserid":[],"hcalias":[],"pdtime":[]}},{"nid":"2","nuserid":"11","cvideo":null,"cvideophoto":null,"ccontent":"古古11怪怪","ctopic":null,"nflower":"5","dtime":"2017-07-19 13:18:52.16511+08","nscan":"1","nreview":"3","nflowercount":"10","dtime1":"1500393600","cphoto":"a.png","calias":"tUUU","nage":null,"ngender":"1","ccity":null,"clabel":null,"review_list":{"creview":["gooo"],"pnuserid":["11","11","11"],"pcalias":["tUUU","tUUU","tUUU"],"hnuserid":["43","43","43"],"hcalias":["43","43","43"],"pdtime":["\"2017-07-19 13:30:28.078476+08\"","\"2017-07-19 13:30:17.842186+08\"","\"2017-07-19 13:27:40.541036+08\""]}},{"nid":"1","nuserid":"11","cvideo":"sdfsd","cvideophoto":null,"ccontent":"古古怪怪","ctopic":null,"nflower":"5","dtime":"2017-07-19 13:18:02.689854+08","nscan":"0","nreview":"0","nflowercount":"0","dtime1":"1500393600","cphoto":"a.png","calias":"tUUU","nage":null,"ngender":"1","ccity":null,"clabel":null,"review_list":{"creview":[],"pnuserid":[],"pcalias":[],"hnuserid":[],"hcalias":[],"pdtime":[]}}]
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
         * nscan : 3
         * nreview : 0
         * nflowercount : 0
         * dtime1 : 1500480000
         * cphoto : touxiang_20170720051930_7208.jpg
         * calias : 小新
         * nage : 19
         * ngender : 1
         * ccity : 台州市
         * clabel : 聊污污;空虚寂寞冷;声音控
         * review_list : {"creview":[],"pnuserid":[],"pcalias":[],"hnuserid":[],"hcalias":[],"pdtime":[]}
         */

        private String nid;
        private String nuserid;
        private String cvideo;
        private String cvideophoto;
        private String ccontent;
        private String ctopic;
        private String nflower;
        private String dtime;
        private String cvideo_mp4;
        private String cvideo_m3u8;
        private String nscan;
        private String nreview;
        private String nflowercount;
        private String dtime1;
        private String cphoto;
        private String number;
        private String calias;
        private String nage;
        private String ngender;
        private String ccity;
        private String clabel;
        private ReviewListBean review_list;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getCvideo_mp4() {
            return cvideo_mp4;
        }

        public void setCvideo_mp4(String cvideo_mp4) {
            this.cvideo_mp4 = cvideo_mp4;
        }

        public String getCvideo_m3u8() {
            return cvideo_m3u8;
        }

        public void setCvideo_m3u8(String cvideo_m3u8) {
            this.cvideo_m3u8 = cvideo_m3u8;
        }

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

        public String getDtime1() {
            return dtime1;
        }

        public void setDtime1(String dtime1) {
            this.dtime1 = dtime1;
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

        public String getClabel() {
            return clabel;
        }

        public void setClabel(String clabel) {
            this.clabel = clabel;
        }

        public ReviewListBean getReview_list() {
            return review_list;
        }

        public void setReview_list(ReviewListBean review_list) {
            this.review_list = review_list;
        }

        public static class ReviewListBean {
            private List<String> creview;
            private List<String> pnuserid;
            private List<String> pcalias;
            private List<String> hnuserid;
            private List<String> hcalias;
            private List<String> pdtime;

            public List<String> getCreview() {
                return creview;
            }

            public void setCreview(List<String> creview) {
                this.creview = creview;
            }

            public List<String> getPnuserid() {
                return pnuserid;
            }

            public void setPnuserid(List<String> pnuserid) {
                this.pnuserid = pnuserid;
            }

            public List<String> getPcalias() {
                return pcalias;
            }

            public void setPcalias(List<String> pcalias) {
                this.pcalias = pcalias;
            }

            public List<String> getHnuserid() {
                return hnuserid;
            }

            public void setHnuserid(List<String> hnuserid) {
                this.hnuserid = hnuserid;
            }

            public List<String> getHcalias() {
                return hcalias;
            }

            public void setHcalias(List<String> hcalias) {
                this.hcalias = hcalias;
            }

            public List<String> getPdtime() {
                return pdtime;
            }

            public void setPdtime(List<String> pdtime) {
                this.pdtime = pdtime;
            }
        }
    }
}
