package com.winkovo.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Slf4j
public class DaysUtils {
    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获取SimpleDateFormat
     */
    private static SimpleDateFormat get() {
        SimpleDateFormat sdf = THREAD_LOCAL.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            THREAD_LOCAL.set(sdf);
        }
        return sdf;
    }

    /**
     * 计算两个时间差
     */
    public static long getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long diff = endDate.getTime() - nowDate.getTime();
        return diff / nd;
    }

    /**
     * 计算距离纪念日的天数
     */
    public static long calculationLianAi(String date) {
        SimpleDateFormat simpleDateFormat = get();
        Date startDate = new Date();
        try {
            startDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
          log.error("日期转换异常",e);
        }
        return getDatePoor(new Date(), startDate);
    }

    /**
     * 计算距离生日天数（阳历）
     */
    public static long calculationBirthday(String birthday) {
        SimpleDateFormat simpleDateFormat = get();
        Calendar cToday = Calendar.getInstance();
        Calendar cBirth = Calendar.getInstance();
        Date birth = new Date();
        try {
            birth = simpleDateFormat.parse(birthday);
        } catch (ParseException e) {
            log.error("日期转换异常",e);
        }
        cBirth.setTime(birth);
        cBirth.set(Calendar.YEAR, cToday.get(Calendar.YEAR));
        int days;
        if (cBirth.get(Calendar.DAY_OF_YEAR) < cToday.get(Calendar.DAY_OF_YEAR)) {
            //如果今年的生日已经过了
            //计算距离过完今年还有多少天
            days = cToday.getActualMaximum(Calendar.DAY_OF_YEAR) - cToday.get(Calendar.DAY_OF_YEAR);
            //加上明天生日是在明天的第多少天
            days += cBirth.get(Calendar.DAY_OF_YEAR);
        } else {
            days = cBirth.get(Calendar.DAY_OF_YEAR) - cToday.get(Calendar.DAY_OF_YEAR);
        }
        return days;
    }


    //判断是否为纪念日
    public static boolean isMemorialDay(String date) {
        int month = Integer.parseInt(date.split("-")[1]);
        int day = Integer.parseInt(date.split("-")[2]);
        LocalDate current = LocalDate.now(); // 获取当前日期
        // 否则返回false表示不是纪念日
        return current.getMonthValue() == month && current.getDayOfMonth() == day; // 如果当天与指定月份、日期相同则返回true表示是纪念日
    }

}