package com.fubang.video.util;

import com.fubang.video.R;
import com.fubang.video.entity.GiftEntity;

import java.util.ArrayList;
import java.util.List;

public class GiftUtil {

    public static List<GiftEntity> getGifts() {
        List<GiftEntity> list = new ArrayList<>();
        list.clear();
        list.add(new GiftEntity(21, R.drawable.ic_gift_21, "爱心(100)", 100));
        list.add(new GiftEntity(22, R.drawable.ic_gift_22, "荧光棒(100)", 100));
        list.add(new GiftEntity(23, R.drawable.ic_gift_23, "啤酒(100)", 100));
        list.add(new GiftEntity(24, R.drawable.ic_gift_24, "鲜花(100)", 100));
        list.add(new GiftEntity(25, R.drawable.ic_gift_25, "黄瓜(100)", 100));
        list.add(new GiftEntity(26, R.drawable.ic_gift_26, "花(100)", 100));
        list.add(new GiftEntity(27, R.drawable.ic_gift_27, "皮鞭(100)", 100));
        list.add(new GiftEntity(28, R.drawable.ic_gift_28, "香蕉(100)", 100));
        list.add(new GiftEntity(29, R.drawable.ic_gift_29, "亲吻(100)", 100));
        list.add(new GiftEntity(30, R.drawable.ic_gift_30, "抱抱(100)", 100));

        return list;
    }
}