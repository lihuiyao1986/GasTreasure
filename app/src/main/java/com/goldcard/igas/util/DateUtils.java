package com.goldcard.igas.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yanshengli on 15-5-20.
 */
public class DateUtils {


    /***
     * 获取当前时间的字符串,字符串的格式为yyyyMMddHHmmss
     * @return
     */
    public static final String getNowTimeStamp()
    {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    /**
     * 获取明天对应的Date
     * @return
     */
    public static final Date tommorrow()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH,1);
        return cal.getTime();
    }

    /**
     * 获取yyyyMMddHHmmss格式的DateFormatter
     * @return
     */
    public static final DateFormat dateFormat1(){
        return  new SimpleDateFormat("yyyyMMddHHmmss");
    }

    /**
     * 获取yyyy-MM-dd HH:mm格式的DateFormatter
     * @return
     */
    public static final DateFormat dataFormat2(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    /**
     * 转换预约时间
     * @param serviceBeginTime
     * @param serviceEndTime
     * @return
     */
    public static String convertHopeTime(String serviceBeginTime,String serviceEndTime){
        String resultStr = "";
        DateFormat format = new SimpleDateFormat ("yyyyMMddHHmmss");
        DateFormat format1 = new SimpleDateFormat ("yyyyMMdd");
        DateFormat format2 = new SimpleDateFormat ("HH:mm");
        DateFormat format3 = new SimpleDateFormat ("MM月dd日");
        try {
            Date beginDate = format.parse (serviceBeginTime);
            Date endDate = format.parse (serviceEndTime);
            Date today = new Date ();
            Calendar calendar = Calendar.getInstance ();
            calendar.setTime (today);
            calendar.add (Calendar.DAY_OF_MONTH, 1);
            Date tommorow = calendar.getTime ();
            String dateStr = "";
            if (format1.format (beginDate).equals (format1.format (today))) {
                dateStr = "今天";
            } else if (format1.format (beginDate).equals (format1.format (tommorow))) {
                dateStr = "明天";
            } else {
                dateStr = format3.format (beginDate);
            }
            String beginTime = format2.format (beginDate);
            String endTime = format2.format (endDate);
            resultStr = dateStr + " " + beginTime + "~" + endTime;
        } catch (ParseException e) {}
        return resultStr;
    }


}
