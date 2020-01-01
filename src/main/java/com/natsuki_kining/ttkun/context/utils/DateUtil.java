package com.natsuki_kining.ttkun.context.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 11:05
 * @Version 1.0.0
 **/
public class DateUtil extends DateUtils {

    public static String format(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String date = dateFormat.format(new Date());
        return date;
    }

    public static String getToday() {
        return format("yyyy-MM-dd");
    }

    public static String getTodaySecond() {
        return format("yyyy-MM-dd.HH:mm:ss");
    }

}
