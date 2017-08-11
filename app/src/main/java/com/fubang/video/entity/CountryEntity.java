package com.fubang.video.entity;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * <p>
 * 项目名称：fubangzhibo
 * 类描述：
 * 创建人：dell
 * 创建时间：2016-05-24 15:28
 * 修改人：dell
 * 修改时间：2016-05-24 15:28
 * 修改备注：
 */
public class CountryEntity {
    private String quhao;
    private String country_name;
    private String totle_content;

    public String getQuhao() {
        return quhao;
    }

    public void setQuhao(String quhao) {
        this.quhao = quhao;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getTotle_content() {
        return totle_content;
    }

    public void setTotle_content(String totle_content) {
        this.totle_content = totle_content;
    }

    public CountryEntity(String quhao, String country_name, String totle_content) {
        this.quhao = quhao;
        this.country_name = country_name;
        this.totle_content = totle_content;
    }
}
