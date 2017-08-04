package com.xlg.android.protocol;

/**
 * Created by jacky on 2017/8/4.
 */
public class LogonResponse {
    @StructOrder(0)
    private int errorid;//0-成功,404-数据库操作失败,405-用户名或密码错误

    public int getErrorid() {
        return errorid;
    }

    public void setErrorid(int errorid) {
        this.errorid = errorid;
    }
}
