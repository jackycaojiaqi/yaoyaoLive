package com.fubang.video.entity;

import java.util.List;

/**
 * Created by jacky on 2017/7/22.
 */
public class CircleInfoEntity {


    /**
     * info : {"nid":"6","nuserid":"12","cvideo":"f08819703abe402fb55435751564b966","cvideophoto":"pengyouquan_20170720094354_5986.jpg","ccontent":"87645669","ctopic":"#好身材#","nflower":"5","dtime":"2017-07-20 09:43:54.326768+08","nscan":"7","nreview":"3","nflowercount":"0","dtime1":"1500480000","cphoto":"touxiang_20170720051930_7208.jpg","calias":"小新","nage":"19","ngender":"1","ccity":"台州市","clabel":"聊污污;空虚寂寞冷;声音控","review_list":[{"nid":"6","nuserid":"740","nbuddyid":"0","creview":"OppppK","dtime":"2017-07-20 14:17:34.776466+08","cphoto":null,"pcalias":"740","nage":null,"ngender":null,"hcalias":null},{"nid":"6","nuserid":"750","nbuddyid":"0","creview":"OK","dtime":"2017-07-20 14:17:19.027154+08","cphoto":null,"pcalias":"750","nage":null,"ngender":null,"hcalias":null},{"nid":"6","nuserid":"520","nbuddyid":"0","creview":"taihal","dtime":"2017-07-20 14:16:58.143058+08","cphoto":null,"pcalias":"520","nage":null,"ngender":null,"hcalias":null}]}
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
        /**
         * nid : 6
         * nuserid : 12
         * cvideo : f08819703abe402fb55435751564b966
         * cvideophoto : pengyouquan_20170720094354_5986.jpg
         * ccontent : 87645669
         * ctopic : #好身材#
         * nflower : 5
         * dtime : 2017-07-20 09:43:54.326768+08
         * nscan : 7
         * nreview : 3
         * nflowercount : 0
         * dtime1 : 1500480000
         * cphoto : touxiang_20170720051930_7208.jpg
         * calias : 小新
         * nage : 19
         * ngender : 1
         * ccity : 台州市
         * clabel : 聊污污;空虚寂寞冷;声音控
         * review_list : [{"nid":"6","nuserid":"740","nbuddyid":"0","creview":"OppppK","dtime":"2017-07-20 14:17:34.776466+08","cphoto":null,"pcalias":"740","nage":null,"ngender":null,"hcalias":null},{"nid":"6","nuserid":"750","nbuddyid":"0","creview":"OK","dtime":"2017-07-20 14:17:19.027154+08","cphoto":null,"pcalias":"750","nage":null,"ngender":null,"hcalias":null},{"nid":"6","nuserid":"520","nbuddyid":"0","creview":"taihal","dtime":"2017-07-20 14:16:58.143058+08","cphoto":null,"pcalias":"520","nage":null,"ngender":null,"hcalias":null}]
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
        private String cvideo_mp4;
        private String cvideo_m3u8;
        private String dtime1;
        private String cphoto;
        private String calias;
        private String nage;
        private String ngender;
        private String ccity;
        private String clabel;
        private List<ReviewListBean> review_list;

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

        public List<ReviewListBean> getReview_list() {
            return review_list;
        }

        public void setReview_list(List<ReviewListBean> review_list) {
            this.review_list = review_list;
        }

        public static class ReviewListBean {
            /**
             * nid : 6
             * nuserid : 740
             * nbuddyid : 0
             * creview : OppppK
             * dtime : 2017-07-20 14:17:34.776466+08
             * cphoto : null
             * pcalias : 740
             * nage : null
             * ngender : null
             * hcalias : null
             */

            private String nid;
            private String nuserid;
            private String nbuddyid;
            private String creview;
            private String dtime;
            private String cphoto;
            private String pcalias;
            private String nage;
            private String ngender;
            private String hcalias;

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

            public String getNbuddyid() {
                return nbuddyid;
            }

            public void setNbuddyid(String nbuddyid) {
                this.nbuddyid = nbuddyid;
            }

            public String getCreview() {
                return creview;
            }

            public void setCreview(String creview) {
                this.creview = creview;
            }

            public String getDtime() {
                return dtime;
            }

            public void setDtime(String dtime) {
                this.dtime = dtime;
            }

            public String getCphoto() {
                return cphoto;
            }

            public void setCphoto(String cphoto) {
                this.cphoto = cphoto;
            }

            public String getPcalias() {
                return pcalias;
            }

            public void setPcalias(String pcalias) {
                this.pcalias = pcalias;
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

            public String getHcalias() {
                return hcalias;
            }

            public void setHcalias(String hcalias) {
                this.hcalias = hcalias;
            }
        }
    }
}
