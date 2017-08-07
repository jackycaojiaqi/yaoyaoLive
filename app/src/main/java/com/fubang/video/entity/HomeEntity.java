package com.fubang.video.entity;

import java.util.List;

/**
 * Created by jacky on 2017/7/24.
 */
public class HomeEntity {


    /**
     * status : success
     * info : {"new_list":[{"nuserid":"16","cphoto":"touxiang_20170729092129_9028.jpg","calias":"赞赞","nage":"19","ngender":"0","ccity":"台州市","nstatus":"0","nprice":"20","nsuccess":"0","all_con":"0"},{"nuserid":"15","cphoto":"touxiang_20170805044908_6533.jpg","calias":"tttt","nage":"4","ngender":"0","ccity":"台州市","nstatus":"0","nprice":"20","nsuccess":"0","all_con":"0"},{"nuserid":"14","cphoto":"touxiang_20170731042420_1926.jpg","calias":"小丸子","nage":"22","ngender":"0","ccity":"台州市","nstatus":"2","nprice":"20","nsuccess":"0","all_con":"0"}],"online_list":[{"nuserid":"16","cphoto":"touxiang_20170729092129_9028.jpg","calias":"赞赞","nage":"19","ngender":"0","ccity":"台州市","nstatus":"0","nprice":"20","nsuccess":"0","all_con":"0","l_dtime":"2017-08-07 14:07:07.684356+08"},{"nuserid":"15","cphoto":"touxiang_20170805044908_6533.jpg","calias":"tttt","nage":"4","ngender":"0","ccity":"台州市","nstatus":"0","nprice":"20","nsuccess":"0","all_con":"0","l_dtime":"2017-08-05 16:45:27.980245+08"},{"nuserid":"9","cphoto":"touxiang_20170725035610_3737.jpg","calias":"tian","nage":"19","ngender":"0","ccity":"台州市","nstatus":"2","nprice":"20","nsuccess":"0","all_con":"0","l_dtime":"1999-01-01 00:00:00+08"}],"nv_sheng_list":[{"nuserid":"16","cphoto":"touxiang_20170729092129_9028.jpg","calias":"赞赞","nage":"19","ngender":"0","ccity":"台州市","nstatus":"0","nprice":"20","nsuccess":"0","all_con":"0","onumber":"0"},{"nuserid":"15","cphoto":"touxiang_20170805044908_6533.jpg","calias":"tttt","nage":"4","ngender":"0","ccity":"台州市","nstatus":"0","nprice":"20","nsuccess":"0","all_con":"0","onumber":"0"},{"nuserid":"9","cphoto":"touxiang_20170725035610_3737.jpg","calias":"tian","nage":"19","ngender":"0","ccity":"台州市","nstatus":"2","nprice":"20","nsuccess":"0","all_con":"0","onumber":"0"}],"hua_lao_list":[{"nuserid":"14","cphoto":"touxiang_20170731042420_1926.jpg","calias":"小丸子","nage":"22","ngender":"0","ccity":"台州市","nstatus":"2","nprice":"20","nsuccess":"0","all_con":"0","l_dtime":"1999-01-01 00:00:00+08"}],"lao_siji_list":[],"fu_jin_list":[{"nuserid":"16","cphoto":"touxiang_20170729092129_9028.jpg","calias":"赞赞","nage":"19","ngender":"0","ccity":"台州市","nstatus":"0","nprice":"20","nsuccess":"0","all_con":"0","dis":"13034800"},{"nuserid":"15","cphoto":"touxiang_20170805044908_6533.jpg","calias":"tttt","nage":"4","ngender":"0","ccity":"台州市","nstatus":"0","nprice":"20","nsuccess":"0","all_con":"0","dis":"13034800"},{"nuserid":"14","cphoto":"touxiang_20170731042420_1926.jpg","calias":"小丸子","nage":"22","ngender":"0","ccity":"台州市","nstatus":"2","nprice":"20","nsuccess":"0","all_con":"0","dis":"13034800"}],"life_list":[{"nid":"851970","cvideophoto":"pengyouquan_20170729092516_2661.jpg","cphoto":"touxiang_20170725035610_3737.jpg","calias":"小小年纪"},{"nid":"851969","cvideophoto":"pengyouquan_20170729092400_8478.jpg","cphoto":"touxiang_20170725035610_3737.jpg","calias":"小小年纪"},{"nid":"15","cvideophoto":"pengyouquan_20170725051131_2082.jpg","cphoto":"touxiang_20170729092129_9028.jpg","calias":"小新"}]}
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
        private List<NvShengListBean> nv_sheng_list;
        private List<HuaLaoListBean> hua_lao_list;
        private List<?> lao_siji_list;
        private List<FuJinListBean> fu_jin_list;
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

        public List<NvShengListBean> getNv_sheng_list() {
            return nv_sheng_list;
        }

        public void setNv_sheng_list(List<NvShengListBean> nv_sheng_list) {
            this.nv_sheng_list = nv_sheng_list;
        }

        public List<HuaLaoListBean> getHua_lao_list() {
            return hua_lao_list;
        }

        public void setHua_lao_list(List<HuaLaoListBean> hua_lao_list) {
            this.hua_lao_list = hua_lao_list;
        }

        public List<?> getLao_siji_list() {
            return lao_siji_list;
        }

        public void setLao_siji_list(List<?> lao_siji_list) {
            this.lao_siji_list = lao_siji_list;
        }

        public List<FuJinListBean> getFu_jin_list() {
            return fu_jin_list;
        }

        public void setFu_jin_list(List<FuJinListBean> fu_jin_list) {
            this.fu_jin_list = fu_jin_list;
        }

        public List<LifeListBean> getLife_list() {
            return life_list;
        }

        public void setLife_list(List<LifeListBean> life_list) {
            this.life_list = life_list;
        }

        public static class NewListBean {
            /**
             * nuserid : 16
             * cphoto : touxiang_20170729092129_9028.jpg
             * calias : 赞赞
             * nage : 19
             * ngender : 0
             * ccity : 台州市
             * nstatus : 0
             * nprice : 20
             * nsuccess : 0
             * all_con : 0
             */

            private String nuserid;
            private String cphoto;
            private String calias;
            private String nage;
            private String ngender;
            private String ccity;
            private String nstatus;
            private String nprice;
            private String nsuccess;
            private String all_con;

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
        }

        public static class OnlineListBean {
            /**
             * nuserid : 16
             * cphoto : touxiang_20170729092129_9028.jpg
             * calias : 赞赞
             * nage : 19
             * ngender : 0
             * ccity : 台州市
             * nstatus : 0
             * nprice : 20
             * nsuccess : 0
             * all_con : 0
             * l_dtime : 2017-08-07 14:07:07.684356+08
             */

            private String nuserid;
            private String cphoto;
            private String calias;
            private String nage;
            private String ngender;
            private String ccity;
            private String nstatus;
            private String nprice;
            private String nsuccess;
            private String all_con;
            private String l_dtime;

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

        public static class NvShengListBean {
            /**
             * nuserid : 16
             * cphoto : touxiang_20170729092129_9028.jpg
             * calias : 赞赞
             * nage : 19
             * ngender : 0
             * ccity : 台州市
             * nstatus : 0
             * nprice : 20
             * nsuccess : 0
             * all_con : 0
             * onumber : 0
             */

            private String nuserid;
            private String cphoto;
            private String calias;
            private String nage;
            private String ngender;
            private String ccity;
            private String nstatus;
            private String nprice;
            private String nsuccess;
            private String all_con;
            private String onumber;

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
        }

        public static class HuaLaoListBean {
            /**
             * nuserid : 14
             * cphoto : touxiang_20170731042420_1926.jpg
             * calias : 小丸子
             * nage : 22
             * ngender : 0
             * ccity : 台州市
             * nstatus : 2
             * nprice : 20
             * nsuccess : 0
             * all_con : 0
             * l_dtime : 1999-01-01 00:00:00+08
             */

            private String nuserid;
            private String cphoto;
            private String calias;
            private String nage;
            private String ngender;
            private String ccity;
            private String nstatus;
            private String nprice;
            private String nsuccess;
            private String all_con;
            private String l_dtime;

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

        public static class FuJinListBean {
            /**
             * nuserid : 16
             * cphoto : touxiang_20170729092129_9028.jpg
             * calias : 赞赞
             * nage : 19
             * ngender : 0
             * ccity : 台州市
             * nstatus : 0
             * nprice : 20
             * nsuccess : 0
             * all_con : 0
             * dis : 13034800
             */

            private String nuserid;
            private String cphoto;
            private String calias;
            private String nage;
            private String ngender;
            private String ccity;
            private String nstatus;
            private String nprice;
            private String nsuccess;
            private String all_con;
            private String dis;

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

            public String getDis() {
                return dis;
            }

            public void setDis(String dis) {
                this.dis = dis;
            }
        }

        public static class LifeListBean {
            /**
             * nid : 851970
             * cvideophoto : pengyouquan_20170729092516_2661.jpg
             * cphoto : touxiang_20170725035610_3737.jpg
             * calias : 小小年纪
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
