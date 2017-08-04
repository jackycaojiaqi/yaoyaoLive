package com.xlg.android.protocol;

/**
 * Created by jacky on 2017/7/29.
 */
public class KickoutUserInfo {
    @StructOrder(0)
    private int userid;//操作者ID
    @StructOrder(1)
    private int buddyid;//被操作者ID
    @StructOrder(2)
    private short reasonid;//101-重复登录被请出;102-超时;103-自己退出  ;104 对方离开
    @StructOrder(3)
    private short reserve;//保留

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

    public short getReasonid() {
        return reasonid;
    }

    public void setReasonid(short reasonid) {
        this.reasonid = reasonid;
    }

    public short getReserve() {
        return reserve;
    }

    public void setReserve(short reserve) {
        this.reserve = reserve;
    }
}
