package com.fubang.video.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by jacky on 2017/7/21.
 */
public class dataUtils {

    /**
     * 将时间戳转换为时间字符串
     */
    public static String formatData(String dataFormat, long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
//        timeStamp = timeStamp * 1000;
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        result = format.format(new Date(timeStamp));
        return result;
    }


    /**
     * 将时间戳转换为Date
     */
    public static String formatData(long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
//        timeStamp = timeStamp * 1000;
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result = format.format(new Date(timeStamp));
        return result;
    }


    /**
     * 把字符串转换为Date
     *
     * @param Sdate
     * @return
     */
    public static Date formatStringToDate(String Sdate) {
        Date date = null;
        if (Sdate != "") {
            DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = dateformat.parse(Sdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * 把字符串转换为Date
     *
     * @param Sdate
     * @return
     */
    public static Date formatStringToDate2(String Sdate) {
        Date date = null;
        if (Sdate != "") {
            DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = dateformat.parse(Sdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * 功能描述：常用的格式化日期
     *
     * @param date Date 日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String formatDateToString(Date date) {
        return formatData("yyyy-MM-dd", date.getTime());
    }

    /**
     * 时间相差天数
     *
     * @param stringEnd   结束时间
     * @param stringStart 开始时间
     * @return 相差天数
     */
    public static int diffDays(String stringEnd, String stringStart) {
        DateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;

        try {
            start = dateformate.parse(stringStart);
            end = dateformate.parse(stringEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = (end.getTime() - start.getTime()) / (24 * 60 * 60 * 1000);
        int diffdays = Integer.parseInt(String.valueOf(diff));
        return diffdays;
    }


    /**
     * 时间是否早于今天
     *
     * @return 相差天数
     */
    public static int beforeToay(Date now, Date judgeDate) {
        Date start = null;
        Date end = null;

        long diff = (judgeDate.getTime() - now.getTime()) / (24 * 60 * 60 * 1000);
        int diffdays = Integer.parseInt(String.valueOf(diff));
        return diffdays;
    }


    /**
     * 获取第二天日期
     *
     * @param nowDate 获取的时间
     * @return 第二天日期
     */
    public static Date getNextDate(Date nowDate) {
        Date nextDate = null;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(nowDate);
        calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        nextDate = calendar.getTime(); //这个时间就是日期往后推一天的结果
        return nextDate;
    }


    /**
     * 由出生日期获得年龄
     *
     * @param birthDay
     * @return
     * @throws Exception
     */
    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;
        Log.e("age", age + "");
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;
    }

    public static String getInterval(long s1, long s2) {
        int days = (int) ((s2 - s1) / (3600 * 24));
        if (days <= 0) {
            long betweenDays = (long) ((s2 - s1) / (60 * 60 ));
            return String.valueOf(betweenDays) + "小时";
        } else {
            return String.valueOf(days) + "天";
        }


    }
}
