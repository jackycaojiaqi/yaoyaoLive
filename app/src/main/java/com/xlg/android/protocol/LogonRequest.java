package com.xlg.android.protocol;


import com.xlg.android.utils.Tools;

public class LogonRequest {
    @StructOrder(0)
    private int deviceid;//1-Android,2-IOS
    @StructOrder(1)
    private int userid;//登录用户id
    @StructOrder(2)
    private byte[] cuserpwd = new byte[34];        //密码
    @StructOrder(3)
    private int buddyid; // 连麦用户ID
    @StructOrder(4)
    private int nconnid; //当前连麦标识符

    public int getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(int deviceid) {
        this.deviceid = deviceid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getBuddyid() {
        return buddyid;
    }

    public void setBuddyid(int buddyid) {
        this.buddyid = buddyid;
    }

    public int getNconnid() {
        return nconnid;
    }

    public void setNconnid(int nconnid) {
        this.nconnid = nconnid;
    }

    public String getCuserpwd() {
        byte[] data = new byte[cuserpwd.length];
        System.arraycopy(cuserpwd, 0, data, 0, cuserpwd.length);
        return Tools.ByteArray2StringGBK(data);
    }

    public void setCuserpwd(String pwd) {
        Tools.String2ByteArrayGBK(this.cuserpwd, pwd);
    }


}
