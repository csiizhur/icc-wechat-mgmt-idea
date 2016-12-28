package com.iccspace.aliyun;

import java.util.Calendar;

/**
 * Created by zhur on 2016/12/28.
 * @author:zhur
 * @description:
 * @date:create in 2016-12-28-10:42
 */
public class FileUtil {

    /**
     * 根据时间生成目录
     *
     * @param time
     * @return
     */
    public static String generateFolderPathByTime(long time) {
        if (time <= 0) {
            return "";
        }
        return "/" + getYear(time) + "/" + getMonth(time) + "/" + getDate(time) + "/"
                + getHour(time) + "/";
    }

    /**
     * 获取年份
     *
     * @param millis long
     * @return int
     */
    public static final int getYear(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取月份
     *
     * @param millis long
     * @return int
     */
    public static final int getMonth(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日期
     *
     * @param millis long
     * @return int
     */
    public static final int getDate(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获取小时
     *
     * @param millis long
     * @return int
     */
    public static final int getHour(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
}
