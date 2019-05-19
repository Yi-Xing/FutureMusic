package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 蒋靓峣  5.19创建
 * 判断当前系统时间对指定日期是否过期和两个时间的差值
 * 时间的格式统一为yyy-MM-dd HH:mm:ss
 */
public class JudgeIsOverdueUtil {
    /**
     * @param date 传入要比价的日期，从数据库获取的数据
     * @return result
     * 返回true则是过期
     * 返回false则是
     */
    public static boolean judgeIsOverdue(String date) {
        boolean result = false;
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // new Date()为获取当前系统时间
        if (df.format(new Date()).compareTo(date)>= 0) {
            result = true;
        }
        return result;
    }

    /**
     * 计算给定时间-系统时间
     * @param date 传入一个字符串代表时间，格式为 “yyyy-MM-dd HH:mm:ss”
     * @return String 返回多的时间的为秒数
     *
     */
    public static long reduceSecond(String date)throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = simpleDateFormat.parse(date).getTime();
        String date1 = simpleDateFormat.format(new Date());
        long time1 = simpleDateFormat.parse(date1).getTime();
        long reduceSecond = (time-time1)/1000;
        return reduceSecond;
    }

    /**
     *
     * @param date 要比较的时间，从数据库中获取多的数据
     * @return  long 相差天数
     *                  传入的时间-系统时间
     * @throws Exception
     */
    public static long reduceDay(String date)throws Exception{
//        long time = reduceSecond(date);
//        long days = time/(60*60*24);
        return reduceSecond(date)/(60*60*24);
    }
    /**
     *
     * @param date 要比较的时间，从数据库中获取多的数据
     * @return  long 相差时间，格式为 HH:mm:ss
     *                  传入的时间-系统时间
     * @throws Exception
     */
    public static String reduceHours(String date)throws Exception{
        long time = reduceSecond(date);
        int second = (int)time%60;
        int minute = (int)(time/60)%60;
        int hours = (int)(time/3600)%60;
        String reduce = hours+":"+minute+":"+second;
        return reduce;
    }
}
