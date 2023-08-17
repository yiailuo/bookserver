
package com.book.manager.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtil
{

    protected static final Log		logger	= LogFactory.getLog(DateUtil.class);

    static final String		defaultDateFormat	= "yyyy-MM-dd HH:mm:ss";

    static final String		defaultDayFormat	= "yyyy-MM-dd";

    // ************************************************************ //
    /**
     * 通过DATE类型得到当前DATE星期
     *
     * @param date 当前日期
     * @return int (1~7 , 1 代表星期日, 7 代表星期六)
     */
    public static int getWeekByDate(Date date)
    {
        int a = 0;
        GregorianCalendar cal = new GregorianCalendar();
        try
        {
            cal.setTime(date);
            a = cal.get(Calendar.DAY_OF_WEEK);
        } catch (Exception ex)
        {
            logger.error("通过Date得到week发生异常", ex);
        }
        return a;
    }
    /**
     * @function:通过给定的时间段和日期得到  日期-时间段  的时间
     * @param today Date 不含时分秒的日期
     * @param hh   时
     * @param mm   分
     * @param ss   秒
     * @return Date  时间段之前的时间
     */
    public static Date getTimeByInterval(Date today,String hh,String mm,String ss)
    {
        int hh_ = Integer.parseInt(hh);
        int mm_ = Integer.parseInt(mm);
        int ss_ =Integer.parseInt(ss);
        long time = today.getTime();
        time -= hh_ * 3600 * 1000L;
        time -= mm_ * 60 * 1000L;
        time -= ss_ * 1000;
        return new Date(time);
    }
    /**
     * @function:通过给定的时间段和日期得到  日期-时间段  的时间
     * @param today Date 不含时分秒的日期
     * @param hh   时
     * @param mm   分
     * @param ss   秒
     * @return Date  时间段之后的时间
     */
    public static Date getAfterTimeByInterval(Date today,String hh,String mm,String ss)
    {
        int hh_ = Integer.parseInt(hh);
        int mm_ = Integer.parseInt(mm);
        int ss_ =Integer.parseInt(ss);
        long time = today.getTime();
        time += hh_ * 3600 * 1000L;
        time += mm_ * 60 * 1000L;
        time += ss_ * 1000;
        return new Date(time);
    }
    /**
     * 通过日期、日期差和时间字符串得到出发时间
     *
     * @param today Date 不含时分秒的日期
     * @param interval int 日期差
     * @param hhmm String 时间字符串
     * @return Date 出发时间
     */
    public static Date getStd(Date today, int interval, String hhmm)
    {
        int hh = Integer.parseInt(hhmm.substring(0, 2));
        int mm = Integer.parseInt(hhmm.substring(2, 4));
        long time = today.getTime();
        time -= interval * 24 * 3600 * 1000L;
        time += hh * 3600 * 1000L;
        time += mm * 60 * 1000L;
        return new Date(time);
    }

    /**
     * 通过日期、日期差和时间字符串得到到达时间
     *
     * @param today Date 不含时分秒的日期
     * @param interval int 日期差
     * @param hhmm String 时间字符串
     * @return Date 到达时间
     */
    public static Date getSta(Date today, int interval, String hhmm)
    {
        int hh = Integer.parseInt(hhmm.substring(0, 2));
        int mm = Integer.parseInt(hhmm.substring(2, 4));
        long time = today.getTime();
        time += interval * 24 * 3600 * 1000L;
        time += hh * 3600 * 1000L;
        time += mm * 60 * 1000L;
        return new Date(time);
    }

    /**
     * 通过时间、日期差得到修正的时间
     *
     * @param today Date 时间
     * @param interval int 日期差
     * @return Date 修正时间
     */
    public static Date getInterval(Date today, int interval)
    {
        long time = today.getTime();
        time += interval * 1000L* 3600 * 24 ;
        return new Date(time);
    }

    /**
     * 过虑掉日期中的时分秒等
     *
     * @return Date 修正时间
     */
    public static Date getDay(Date now)
    {
        DateFormat df = new SimpleDateFormat(defaultDayFormat);
        String day = df.format(now);
        Date newDay = null;
        try
        {
            newDay = df.parse(day);
        } catch (ParseException e)
        {
            logger.debug("date transfer error", e);
        }
        return newDay;
    }

    public static String getFirstDayOfNextMonth(String strDate)
    {
        String str = "";
        try
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(getDate(strDate));
            // ///////////////modify by jiangjun
            cal.add(Calendar.MONTH, 1);
            str = cal.get(Calendar.YEAR)
                    + "-"
                    + (cal.get(Calendar.MONTH) + 1)
                    + "-"
                    + "01";
            // ////////////////
        } catch (Exception ex)
        {
            logger.error("得到默认中文日期格式发生异常", ex);
        }
        return str;
    }

    /**
     * Parse formated date string 'yyyyMMdd'
     *
     * @param today String 日期
     * @return Date 日期
     */
    public static Date getDay(String today)
    {
        DateFormat df = new SimpleDateFormat(defaultDayFormat);
        Date day = null;
        try
        {
            day = ((today == null || today.length() != 10)
                    ? null
                    : df.parse(today));
        } catch (Exception e)
        {
            logger.debug("date transfer error", e);
        }
        return day;
    }

    /**
     * convert string formated 'yyyy-MM-dd' to java.sql.Date
     *
     * @param today String 日期
     * @return java.sql.Date 日期
     */
    public static java.sql.Date getSqlDay(String today)
    {
        java.sql.Date sqld = java.sql.Date.valueOf(today);
        return sqld;
    }

    /**
     * 返回当天 string formated 'yyyy-MM-dd' to java.sql.Date
     *
     * @param
     * @return java.sql.Date 日期
     */
    public static java.sql.Date getSqlToday()
    {
        return getSqlDay(new Date());
    }

    /**
     * convert java.util.Date with formated 'yyyy-MM-dd' to java.sql.Date
     *
     * @param today java.util.Date 日期
     * @return java.sql.Date 日期
     */
    public static java.sql.Date getSqlDay(Date today)
    {
        java.sql.Date sqld = java.sql.Date.valueOf(getFormatedDateStr(today,
                defaultDayFormat));
        return sqld;
    }

    /**
     * Parse formated date string
     *
     * @param now String 日期时分 'yyyy-MM-dd HH:mm:ss' 或 'yyyy-MM-dd'
     * @return Date 日期
     */
    public static Date getDate(String now)
    {
        // 若传过来的字符串是长度为10的类型：yyyy-MM-dd，则先把它改为16位的类型
        if (now != null && now.length() == defaultDayFormat.length())
        {
            now = now + " 00:00:00";
        }
        DateFormat df = new SimpleDateFormat(defaultDateFormat);
        Date date = null;
        try
        {
            date = ((now == null || now.length() != defaultDateFormat.length())
                    ? null
                    : df.parse(now));
        } catch (Exception e)
        {
            logger.debug("date transfer error", e);
        }
        return date;
    }

    /**
     * format date to formated date string
     *
     * @param now Date 日期
     * @param format String 日期格式
     * @return String 日期时间
     */
    public static String getFormatedDateStr(Date now, String format)
    {
        DateFormat df = new SimpleDateFormat(format);
        String str = (now == null ? null : df.format(now));
        return str;
    }

    /**
     * @param now
     * @return 返回格式为 'yyyy-MM-dd HH:mm' 的字符串
     */
    public static String getFormatedDateStr(Date now)
    {
        DateFormat df = new SimpleDateFormat(defaultDateFormat);
        String str = (now == null ? null : df.format(now));
        return str;
    }

    /**
     * @param now
     * @return 返回格式为 'yyyy-MM-dd' 的字符串
     */
    public static String getFormatedDayStr(Date now)
    {
        DateFormat df = new SimpleDateFormat(defaultDayFormat);
        String str = (now == null ? null : df.format(now));
        return str;
    }

    /**
     * @function:将字符串转换为日期
     * @param date
     * @param format
     * @return
     */
    public static Date getDateByStr(String date, String format)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date d = new Date();
        try
        {
            d = dateFormat.parse(date);
        } catch (ParseException e)
        {
            logger.error(e);
        }
        return d;
    }

    public String getCNDefaultDate()
    {
        String str = "";
        try
        {
            Calendar cal = Calendar.getInstance();
            str = cal.get(Calendar.YEAR)
                    + "年"
                    + (cal.get(Calendar.MONTH) + 1)
                    + "月"
                    + cal.get(Calendar.DATE)
                    + "日";
            str = str + getCNWeek((cal.get(Calendar.DAY_OF_WEEK) - 1));
        } catch (Exception ex)
        {
            logger.error("得到默认中文日期格式发生异常", ex);
        }
        return str;
    }

    /**
     * 得到中文星期
     *
     * @param a 数字（1,2,3,4,5,6,7）
     * @return
     */
    public String getCNWeek(int a)
    {
        String str_week = "";
        switch (a)
        {
            case 1 :
                str_week = "星期一";
                break;
            case 2 :
                str_week = "星期二";
                break;
            case 3 :
                str_week = "星期三";
                break;
            case 4 :
                str_week = "星期四";
                break;
            case 5 :
                str_week = "星期五";
                break;
            case 6 :
                str_week = "星期六";
                break;
            case 7 :
                str_week = "星期日";
                break;
        }
        return str_week;
    }

    /**
     * 通过一个yyyy－MM-dd类型的时间得到 日期对象
     *
     * @param d1
     * @return
     */
    public Date getDateByStr(String d1)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(defaultDayFormat);
        Date d = new Date();
        try
        {
            d = dateFormat.parse(d1);
        } catch (ParseException e)
        {
            logger.error(e);
        }
        return d;
    }

    /**
     * @param now
     * @return 返回格式为 'yyyy-MM-dd' 的字符串
     */
    public static String getSimpleFormatDate(Date now)
    {
        DateFormat df = new SimpleDateFormat(defaultDayFormat);
        String str = (now == null ? null : df.format(now));
        return str;
    }

    /**
     * 返回当前时间，格式为“yyyy-mm-dd”
     *
     * @return String
     */
    public static String getSimpleFormatedDateNow()
    {
        Date now = new Date();
        return getFormatedDayStr(now);
    }

    /**
     * @function:根据格式得到时间
     * @param format
     * @return
     */
    public static String getFormatedDateNow(String format)
    {
        Date now = new Date();
        DateFormat df = new SimpleDateFormat(format);
        String str = (now == null ? null : df.format(now));
        return str;
    }

    /**
     * 按照指定日期格式解析字符串
     *
     * @param now
     * @param format
     * @return
     */
    public static Date parseStringToDate(String now, String format)
    {
        DateFormat df = new SimpleDateFormat(format);
        Date nowDate = null;
        try
        {
            nowDate = df.parse(now);
        } catch (ParseException e)
        {
            // TODO 自动生成 catch 块
            logger.debug("无法解析" + now);
        }
        return nowDate;
    }

    /**
     * @function:得到上个月的年和月
     * @return
     */
    public static String getLastyearmonth(String nowdate){
        String lastmonth=null;
        String nian=nowdate.substring(0, 4);
        String yue=nowdate.substring(5, 7);
        int inian=Integer.parseInt(nian);
        int iyue=Integer.parseInt(yue);
        if(iyue==1){
            inian=inian-1;
            lastmonth=""+inian+"年12月";
        }else{
            iyue=iyue-1;
            if(iyue<10){
                lastmonth=""+inian+"年0"+iyue+"月";
            }else{
                lastmonth=""+inian+"年"+iyue+"月";
            }
        }
        return lastmonth;
    }


    /**
     * @function:得到下个月的年和月
     * @param nowdate
     * @return
     */
    public static String getNextyearmonth(String nowdate){
        String nexmonth=null;
        String nian=nowdate.substring(0, 4);
        String yue=nowdate.substring(5, 7);
        int inian=Integer.parseInt(nian);
        int iyue=Integer.parseInt(yue);
        if(iyue==12){
            inian=inian+1;
            nexmonth=""+inian+"年01月";
        }else{
            iyue=iyue+1;
            if(iyue<10){
                nexmonth=""+inian+"年0"+iyue+"月";
            }else{
                nexmonth=""+inian+"年"+iyue+"月";
            }
        }
        return nexmonth;
    }

    /**
     * @function:得到小时数，i为（０－２３）j为（２）
     * @param i
     * @param j
     * @return
     */
    public static String add0(int i, int j)
    {
        long l = (long)Math.pow(10D, j);
        return String.valueOf(l + (long)i).substring(1);
    }


    /**
     * @function:将字符串转为Calendar类型
     * @param dateTime 默认：当前系统时间
     * @param formatString 默认：yyyy-MM-dd
     * @return
     */
    public static Calendar formatDateFromStringToCalendar(String dateTime, String formatString)
    {
        Calendar cal = Calendar.getInstance();
        if (dateTime == null || "".equals(dateTime))
            cal.setTime(new Date());
        else
        {
            if(formatString==null || "".equals(formatString))
                formatString = "yyyy-MM-dd";
            SimpleDateFormat df = new SimpleDateFormat(formatString);
            try
            {
                cal.setTime(df.parse(dateTime));
                if(dateTime.substring(dateTime.indexOf(" ")+1,dateTime.indexOf(" ")+3).equals("12"))
                    cal.set(Calendar.HOUR_OF_DAY, 12);
            } catch (ParseException e)
            {
                logger.error(e);
            }
        }
        if(formatString==null)
        {
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        }
        return cal;
    }

    /**
     * @function:返回日期相差几天
     * @param startTime
     * @param endTime
     * @return
     */
    public static Integer differenceDaysToCount(Calendar startTime, Calendar endTime)
    {
        long day = (endTime.getTimeInMillis() - startTime.getTimeInMillis())
                / (1000 * 60 * 60 * 24);
        int intDay = new Long(day).intValue();
        if (intDay == 0)
            return 1;
        else
            return intDay+1;
    }

    /**
     * @function:如果前者早于后者则返回true
     * @param beforeTime
     * @param afterTime
     * @return
     */
    public static boolean compareTime(Calendar beforeTime, Calendar afterTime)
    {
        boolean flag = false;
        if (beforeTime.before(afterTime))
        {
            flag = true;
        }
        return flag;
    }

    /**
     * @function:比较两时间段，返回时间段重复的日期天数
     * @param paramBeforeTime
     * @param paramAfterTime
     * @param standardBeforeTime
     * @param standardAfterTime
     * @return
     */
    public static int compareTimesParagraph(Calendar paramBeforeTime, Calendar paramAfterTime, Calendar standardBeforeTime, Calendar standardAfterTime)
    {
        long pBeforeTime = paramBeforeTime.getTime().getTime();
        long pAfterTime = paramAfterTime.getTime().getTime();
        long sBeforeTime = standardBeforeTime.getTime().getTime();
        long sAfterTime = standardAfterTime.getTime().getTime();

        if(pBeforeTime < sBeforeTime)
        {
            if(pAfterTime < sAfterTime)
            {
                if(sBeforeTime < pAfterTime)
                    return differenceDaysToCount(standardBeforeTime, paramAfterTime).intValue();
                else if(sBeforeTime > pAfterTime)
                    return 0;
                else
                    return 1;
            }
            else
                return differenceDaysToCount(standardBeforeTime, standardAfterTime).intValue()==0?1:differenceDaysToCount(standardBeforeTime, standardAfterTime).intValue();
        }
        else
        {
            if(pAfterTime > sAfterTime)
            {
                if(pBeforeTime < sAfterTime)
                    return differenceDaysToCount(paramBeforeTime, standardAfterTime).intValue();
                else if(pBeforeTime > sAfterTime)
                    return 0;
                else
                    return 1;
            }
            else
                return differenceDaysToCount(standardBeforeTime, standardAfterTime).intValue();
        }
    }

    /**
     * @function:返回格式化后的日期时间函数
     * @param format 兼容系统中的JS时间控件
     * @return
     */
    public static String returnTime(String format)
    {
        String[] str=format.split("%");

        String javaFormat="";

        for(int i=1;i<str.length;i++)
        {

            if(str[i].startsWith("Y"))
            {
                javaFormat="yyyy";
                if(str[i].length()>1)
                {
                    javaFormat=javaFormat+str[i].substring(1);
                }

            }

            if(str[i].startsWith("M"))
            {
                javaFormat=javaFormat+"MM";
                if(str[i].length()>1)
                {
                    javaFormat=javaFormat+str[i].substring(1);
                }
            }

            if(str[i].startsWith("D"))
            {
                javaFormat=javaFormat+"dd" ;
                if(str[i].length()>1)
                {
                    javaFormat=javaFormat+str[i].substring(1);
                }
            }

            if(str[i].startsWith("H"))
            {
                javaFormat=javaFormat+"HH" ;
                if(str[i].length()>1)
                {
                    javaFormat=javaFormat+str[i].substring(1);
                }
            }
            if(str[i].startsWith("h"))
            {
                javaFormat=javaFormat+"hh" ;
                if(str[i].length()>1)
                {
                    javaFormat=javaFormat+str[i].substring(1);
                }
            }

            if(str[i].startsWith("m"))
            {
                javaFormat=javaFormat+"mm" ;
                if(str[i].length()>1)
                {
                    javaFormat=javaFormat+str[i].substring(1);
                }
            }

            if(str[i].startsWith("s"))
            {
                javaFormat=javaFormat+"ss";
                if(str[i].length()>1)
                {
                    javaFormat=javaFormat+str[i].substring(1);
                }
            }
        }

        Date d=new Date();
        SimpleDateFormat  sdf=new SimpleDateFormat(javaFormat);


        return sdf.format(d);

    }

    public static String getCNDate()
    {
        String str = "";
        try
        {
            Calendar cal = Calendar.getInstance();
            str = cal.get(Calendar.YEAR)
                    + "年"
                    + (cal.get(Calendar.MONTH) + 1)
                    + "月"
                    + cal.get(Calendar.DATE)
                    + "日";
        } catch (Exception ex)
        {
            logger.error("得到默认中文日期格式发生异常", ex);
        }
        return str;
    }

    /** 通过 yyyy-MM-dd 得到中文大写格式 yyyy MM dd 日期 */
    public static synchronized String toChinese(String str) {
        StringBuffer sb = new StringBuffer();
        sb.append(getSplitDateStr(str, 0)).append("年").append(
                getSplitDateStr(str, 1)).append("月");
        return sb.toString();
    }

    /** 分别得到年月日的大写 默认分割符 "-" */
    public static String getSplitDateStr(String str, int unit) {
        // unit是单位 0=年 1=月 2日
        String[] DateStr = str.split("-");
        if (unit > DateStr.length)
            unit = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < DateStr[unit].length(); i++) {

            if ((unit == 1 || unit == 2) && Integer.parseInt(DateStr[unit]) > 9) {
                sb.append(convertNum(DateStr[unit].substring(0, 1)))
                        .append("十").append(
                        convertNum(DateStr[unit].substring(1, 2)));
                break;
            } else {
                sb.append(convertNum(DateStr[unit].substring(i, i + 1)));
            }
        }
        if (unit == 1 || unit == 2) {
            return sb.toString().replaceAll("^一", "").replace("零", "");
        }
        return sb.toString();

    }

    /** 转换数字为大写 */
    private static String convertNum(String str) {
        return NUMBERS[Integer.parseInt(str)];
    }

    private static final String[] NUMBERS = { "零", "一", "二", "三", "四", "五",
            "六", "七", "八", "九" };

    /**
     * @function:判断日期，如超过day则返回本月；否则返回上月
     * @param format
     * @param day
     * @return
     */
    public static String judgeDay(String format, int day)
    {
        String lastmonth = null;

        String nian = format.substring(0, 4);
        String yue = format.substring(5, 7);
        String ri = format.substring(8, 10);

        int inian = Integer.parseInt(nian);
        int iyue = Integer.parseInt(yue);
        int iri = Integer.parseInt(ri);

        if(day < iri){
            if (iyue < 10)
            {
                lastmonth = "" + inian + "年0" + iyue + "月";
            } else
            {
                lastmonth = "" + inian + "年" + iyue + "月";
            }
        }else{
            if (iyue == 1)
            {
                inian = inian - 1;
                lastmonth = "" + inian + "年12月";
            } else
            {
                iyue = iyue - 1;
                if (iyue < 10)
                {
                    lastmonth = "" + inian + "年0" + iyue + "月";
                } else
                {
                    lastmonth = "" + inian + "年" + iyue + "月";
                }
            }
        }
        return lastmonth;
    }

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd";


    /**
     * 格式化日期
     * @param date 日期对象
     * @return String 日期字符串
     */
    public static String formatDate(Date date){
        SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
        String sDate = f.format(date);
        return sDate;
    }


    public static String getBigWeek(int dayofweek) {
        //Calendar中1-星期天，2-星期一，3-星期二，4-星期三，5-星期四，6-星期五，7-星期六
        String[] week = { "", "日", "一", "二", "三", "四", "五", "六" };
        return week[dayofweek];
    }

    /**
     * 获取当年的第一天
     * @param year
     * @return
     */
    public static Calendar getCurrYearFirst(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取当年的最后一天
     * @param year
     * @return
     */
    public static Calendar getCurrYearLast(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    private static Calendar getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        Calendar cal=Calendar.getInstance();
        cal.setTime(currYearFirst);
        return cal;
    }

    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    private static Calendar getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        Calendar cal=Calendar.getInstance();
        cal.setTime(currYearLast);
        return cal;
    }
    /**
     * 获取当前年份的前一年
     * @return 当前年份的前一年
     */
    public static String getPrevYearStr(){
        return  getPrevYearStr("");
    }
    /**
     * 获取指定年份的前一年
     * @param dateStr 目标年份  格式 yyyyMM
     * @return 目标年份的前一年
     */
    public static String getPrevYearStr(String dateStr) {
        if (dateStr.length()==4) {
            return String.valueOf(Integer.parseInt(dateStr)-1);
        } else if (dateStr.length()==6) {
            String year=dateStr.substring(0,4);
            return String.valueOf(Integer.parseInt(year)-1);
        }else {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            return sdf.format(cal.getTime());
        }
    }
}