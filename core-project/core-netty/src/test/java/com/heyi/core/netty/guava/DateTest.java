package com.heyi.core.netty.guava;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author
 * @createdate
 * @description
 **/
public class DateTest {

    @Test
    public void test1() throws Exception {
        Date date = new Date();
        System.out.println(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String d1 = simpleDateFormat.format(date);
        Date date2 = simpleDateFormat.parse(d1);

        DateFormat dateFormat=DateFormat.getDateTimeInstance(DateFormat.YEAR_FIELD,DateFormat.ERA_FIELD,new Locale("zh","CN"));
        System.out.println(dateFormat.format(date));


        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());
        calendar.add(Calendar.YEAR,1);
        System.out.println(calendar.getTime());
        List<Object> list = new ArrayList<>();
        list.add(calendar.get(Calendar.YEAR));
        list.add(calendar.get(Calendar.MONTH)+1);
        list.add(calendar.get(Calendar.DAY_OF_MONTH));
        list.add(calendar.get(Calendar.WEEK_OF_MONTH));
        list.add(calendar.get(Calendar.DAY_OF_WEEK));
        list.add(calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));

        System.out.println(list);

    }
}
