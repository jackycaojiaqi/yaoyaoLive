package com.xlg.android.protocol;

import com.xlg.android.utils.Tools;

/**
 * Created by jacky on 2017/8/4.
 */
public class LogonResponse {
    @StructOrder(0)
    private int userid;//用户ID
    @StructOrder(1)
    private long kmoney;//用户金币
    @StructOrder(2)
    private byte[] verison = new byte[32];//版本号
    @StructOrder(3)
    private int errorid;//0-成功,404-数据库操作失败,405-用户名或密码错误

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

    public String getVerison() {
        byte[] data = new byte[verison.length];
        System.arraycopy(verison, 0, data, 0, verison.length);
        return Tools.ByteArray2StringGBK(data);
    }

    public void setVerison(String verison) {
        Tools.String2ByteArrayGBK(this.verison, verison);
    }

    public int getErrorid() {
        return errorid;
    }

    public void setErrorid(int errorid) {
        this.errorid = errorid;
    }
}
