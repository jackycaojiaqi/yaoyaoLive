package com.xlg.android.protocol;


public class UserPayRequest {
    @StructOrder(0)
    private int userid;   //赠送者ID
    @StructOrder(1)
    private int buddyid;            //接收者ID
    @StructOrder(2)
    private int type;        //3-聊天
    @StructOrder(3)
    private long money;        //预扣金额



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

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
