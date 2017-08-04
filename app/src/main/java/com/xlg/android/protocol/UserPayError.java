package com.xlg.android.protocol;

/**
 * Created by jacky on 2017/8/4.
 */
public class UserPayError {
    @StructOrder(0)
    private int errorid;//404-数据库操作失败,501-礼物未维护,504-用户金币不足

    public int getErrorid() {
        return errorid;
    }

    public void setErrorid(int errorid) {
        this.errorid = errorid;
    }
}
