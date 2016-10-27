package cn.com.bestpay.portal.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Howell on 27/10/16.
 */
public class TimeUtil {

    private static Logger logger = LoggerFactory.getLogger(TimeUtil.class);

    /**
     * 根据传入的格式化样式返回字符串形式的当前时间
     * @param formatStyle 格式化样式：如yyyy-MM-dd
     * @return
     */
    public static String formatCurrentDate(String formatStyle) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(formatStyle);
        return format.format(calendar.getTime());
    }

    /**
     * 获取两个时间差，返回除以1000是为了转换成秒
     * @param beginTime
     * @param endTime
     * @param timeFormat
     * @return
     */
    public static long getBetweenTime(String beginTime, String endTime, String timeFormat){
        SimpleDateFormat dfs = new SimpleDateFormat(timeFormat);
        Date begin = null;
        try {
            begin = dfs.parse(beginTime);
            Date end = dfs.parse(endTime);
            long between = (end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒
            return between;
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error(e.getMessage().toString());
        }
        return 0;
    }

    /**
     * 获取两个时间差，返回秒
     * @param beginTime
     * @param endTime
     * @param timeFormat
     * @return
     */
    public static String getSecondToBetween(String beginTime, String endTime, String timeFormat){
        long between = getBetweenTime(beginTime, endTime, timeFormat);
        long second = between%60/60;
        return second+"";
    }

    /**
     * 获取两个时间差，返回分钟
     * @param beginTime
     * @param endTime
     * @param timeFormat
     * @return
     */
    public static long getMinuteToBetween(String beginTime, String endTime, String timeFormat){
        long between = getBetweenTime(beginTime, endTime, timeFormat);
        long minute = between%3600/60;
        return minute;
    }

    /**
     * 获取两个时间差，返回小时
     * @param beginTime
     * @param endTime
     * @param timeFormat
     * @return
     */
    public static long getHourToBetween(String beginTime, String endTime, String timeFormat){
        long between = getBetweenTime(beginTime, endTime, timeFormat);
        long hour = between%(24*3600)/3600;
        return hour;
    }

    /**
     * 获取两个时间差，返回天
     * @param beginTime
     * @param endTime
     * @param timeFormat
     * @return
     */
    public static long getDayToBetween(String beginTime, String endTime, String timeFormat){
        long between = getBetweenTime(beginTime, endTime, timeFormat);
        long day = between/(24*3600);
        return day;
    }


}
