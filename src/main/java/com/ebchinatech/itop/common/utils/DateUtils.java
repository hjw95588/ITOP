package com.ebchinatech.itop.common.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DateUtils {
    /**
     * 根据日期（Date）获取该周的周一和周天
     * @param date
     * @return
     */
    public static HashMap<String, String> getWeekDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        String monday = sdf.format(cal.getTime());
        cal.add(Calendar.DATE, 6);
        String sunday = sdf.format(cal.getTime());
        HashMap<String, String> dateMap = new HashMap<>();
        dateMap.put("monday",monday);
        dateMap.put("sunday",sunday);
        return dateMap;
    }

    /**
     * 根据日期（Date）获取年月(String)
     * @param date
     * @return
     */
    public static String getYearMonth(Date date) {
        String yearMonth="";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        if (month < 10) {
            yearMonth=year + "-0" + month;
        } else {
            yearMonth=year + "-" + month;
        }
        return yearMonth;
    }

    /**
     * 获取日期的中文格式。如：2021年9月28日
     * @param date
     */
    public static String getChineseFormat(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return year+"年"+month+"月"+day+"日";
    }

    /**
     * 获取当前日期的前一天（字符串）。如：20210928
     */
    public static String getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH,-1);
        return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
    }

    /**
     * 获取日期字符串。如：2021-9-28
     */
    public static String getDateString(Date date) {
        return new SimpleDateFormat("yyyy-M-d").format(date);
    }

    /**
     * 获取日期字符串。如：2021-09-28
     */
    public static String getDateStringTwo(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

}
