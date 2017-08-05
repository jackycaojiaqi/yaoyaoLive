package com.xlg.android.protocol;

/**
 * Created by jacky on 2017/8/5.
 */
public class VideoConnectRequest {

    @StructOrder(0)
    private int userid;   //用户ID
    @StructOrder(1)
    private int buddyid;   //连接对象ID

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
}
