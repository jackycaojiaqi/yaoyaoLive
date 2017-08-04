package com.xlg.android.protocol;


public class UserPayResponse {
    @StructOrder(0)
    private int userid;   //赠送者ID

    @StructOrder(1)
    private long kmoney;        //用户当前币值
    @StructOrder(2)
    private int type;        //1-扣币成功


    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public long getKmoney() {
        return kmoney;
    }

    public void setKmoney(long kmoney) {
        this.kmoney = kmoney;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
