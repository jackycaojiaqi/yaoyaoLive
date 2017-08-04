package com.xlg.android.protocol;


import com.xlg.android.utils.Tools;

public class TradeGiftRecord {
    @StructOrder(0)
    private int userid;   //赠送者ID
    @StructOrder(1)
    private int toid;            //接收者ID
    @StructOrder(2)
    private int giftid;        //礼物ID
    @StructOrder(3)
    private int amount;        //数量
    @StructOrder(4)
    private byte[] alias = new byte[32];        //发送者昵称
    @StructOrder(5)
    private byte[] photo = new byte[32];       //发送者头像

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getToid() {
        return toid;
    }

    public void setToid(int buddyid) {
        this.toid = buddyid;
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

    public String getAlias() {
        byte[] data = new byte[alias.length];
        System.arraycopy(alias, 0, data, 0, alias.length);
        return Tools.ByteArray2StringGBK(data);
    }

    public void setAlias(String alias) {
        Tools.String2ByteArrayGBK(this.alias, alias);
    }

    public String getPhoto() {
        byte[] data = new byte[photo.length];
        System.arraycopy(photo, 0, data, 0, photo.length);
        return Tools.ByteArray2StringGBK(data);
    }

    public void setPhoto(String photo) {
        Tools.String2ByteArrayGBK(this.photo, photo);
    }
}
