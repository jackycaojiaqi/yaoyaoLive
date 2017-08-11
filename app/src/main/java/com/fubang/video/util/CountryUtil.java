package com.fubang.video.util;

import com.fubang.video.R;
import com.fubang.video.entity.CountryEntity;
import com.fubang.video.entity.GiftEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacky on 2017/8/11.
 */
public class CountryUtil {
    public static List<CountryEntity> getGifts() {
        List<CountryEntity> list = new ArrayList<>();
        list.clear();
        list.add(new CountryEntity("86", "中国", "+86 中国"));
        list.add(new CountryEntity("1", "美国", "+1 美国"));
        list.add(new CountryEntity("60", "马来西亚", "+60 马来西亚"));
        list.add(new CountryEntity("84", "新加坡", "+84 新加坡"));
        list.add(new CountryEntity("63", "菲律宾", "+63 菲律宾"));
        list.add(new CountryEntity("84", "越南", "+84 越南"));
        list.add(new CountryEntity("1", "加拿大", "+1 加拿大"));
        list.add(new CountryEntity("852", "香港", "+852 香港"));
        list.add(new CountryEntity("886", "台湾", "+886 台湾"));
        list.add(new CountryEntity("81", "日本", "+81 日本"));
        list.add(new CountryEntity("82", "韩国", "+82 韩国"));
        list.add(new CountryEntity("61", "澳大利亚", "+61 澳大利亚"));
        list.add(new CountryEntity("64", "新西兰", "+64 新西兰"));
        list.add(new CountryEntity("44", "英国", "+44 英国"));
        list.add(new CountryEntity("49", "德国", "+49 德国"));
        list.add(new CountryEntity("33", "法国", "+33 法国"));
        list.add(new CountryEntity("7", "俄罗斯", "+7 俄罗斯"));
        list.add(new CountryEntity("960", "马尔代夫", "+960 马尔代夫"));
        return list;
    }

}
