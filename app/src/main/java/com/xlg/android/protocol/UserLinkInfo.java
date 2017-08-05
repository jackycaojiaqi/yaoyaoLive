package com.xlg.android.protocol;

/**
 * Created by jacky on 2017/8/5.
 */
public class UserLinkInfo {
    @StructOrder(0)
    private int userid;   //请求用户ID(男性)
    @StructOrder(1)
    private int buddyid;   //被请求用户ID(女性)
    @StructOrder(2)
    private int type;   //0-用户请求,1-用户同意,2-用户拒绝,3-无可选用户,4-用户取消或者已有人抢单取消,5-成功广播
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
