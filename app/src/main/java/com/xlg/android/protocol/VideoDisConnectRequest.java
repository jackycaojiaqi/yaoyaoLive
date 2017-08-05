package com.xlg.android.protocol;

/**
 * Created by jacky on 2017/8/5.
 */
public class VideoDisConnectRequest {

    @StructOrder(0)
    private int userid;   //用户ID

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

}
