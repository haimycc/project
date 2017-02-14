package com.xmg.p2p.base.util;


import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * 获取两个时间之间的间隔(秒)
     */
    public static long getBetweenSeconds(Date d1, Date d2) {
        return Math.abs((d1.getTime() - d2.getTime()) / 1000);
    }
    private DateUtil() {
    }

    /*字母  日期或时间元素  表示  示例
    G  Era 标志符  Text  AD
    y  年  Year  1996; 96
    M  年中的月份  Month July; Jul; 07
    w  年中的周数  Number  27
    W  月份中的周数  Number  2
    D  年中的天数  Number  189
    d  月份中的天数  Number  10
    F  月份中的星期  Number  2
    E  星期中的天数  Text  Tuesday; Tue
    a  Am/pm 标记  Text  PM
    H  一天中的小时数（0-23）  Number  0
    k  一天中的小时数（1-24）  Number  24
    K  am/pm 中的小时数（0-11）  Number  0
    h  am/pm 中的小时数（1-12）  Number  12
    m  小时中的分钟数  Number  30
    s  分钟中的秒数  Number  55
    S  毫秒数  Number  978
    z  时区  General time zone  Pacific Standard Time; PST; GMT-08:00
    Z  时区  RFC 822 time zone  -0800*/

    public static Date getBeginDate(Date current) {
        Calendar c = Calendar.getInstance();
        c.setTime(current);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        return c.getTime();
    }
    public static Date getEndDate(Date current) {
        Calendar c = Calendar.getInstance();
        c.setTime(current);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        return c.getTime();
    }

    public static void main(String[] args) {
        System.out.println(getBeginDate(new Date()).toLocaleString());
        System.out.println(getEndDate(new Date()).toLocaleString());
    }




}
