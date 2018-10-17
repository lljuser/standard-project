package com.heyi.core.netty.service;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @描述 com.heyi.core.netty.service
 * @创建人 ljliu
 * @创建时间 2018/10/17
 * @修改人和其它信息
 */
public class DateUtilsTest {

    /**
     * Date->format->calendar
     */
    @Test
    public void test(){

        Calendar calendar = Calendar.getInstance();
        //calendar.setTime(new Date());
        System.out.println(calendar.getTime());

        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(calendar.get(Calendar.DATE));
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));

        calendar.add(Calendar.YEAR, 1);
        calendar.add(Calendar.MONTH, 1);
        System.out.println(calendar.getTime());


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = "2016-01-01 11:11:11";
        calendar = Calendar.getInstance();
        long nowDate = calendar.getTime().getTime(); //Date.getTime() 获得毫秒型日期
        try {
            long specialDate = sdf.parse(dateString).getTime();
            long betweenDate = (specialDate - nowDate) / (1000 * 60 * 60 * 24); //计算间隔多少天，则除以毫秒到天的转换公式
            System.out.print(betweenDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }



    }
}
