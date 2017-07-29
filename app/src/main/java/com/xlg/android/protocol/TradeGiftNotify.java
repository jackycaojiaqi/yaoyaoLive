package com.xlg.android.protocol;


public class TradeGiftNotify {
    @StructOrder(0)
    private int userid;   //赠送者ID
    @StructOrder(1)
    private int buddyid;            //接收者ID
    @StructOrder(2)
    private int giftid;        //礼物ID
    @StructOrder(3)
    private int amount;        //数量


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

    public int getGiftid() {
        return giftid;
    }

    public void setGiftid(int giftid) {
        this.giftid = giftid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
