package com.heyi.webapp.nettyserver.unit;

import com.heyi.webapp.nettyserver.model.WeekDayEnum;
import org.junit.Assert;
import org.junit.Test;

public class WeedDayEnumTest {

    @Test
    public void getEnumValues(){
        for(WeekDayEnum day:WeekDayEnum.values()){
            System.out.println(day.toString()+":"+day.name()+":"
                    +day.ordinal()+":"+day.getCnName()+":"+day.getOrder());
        }



    }


    @Test
    public void getDayByOrder(){
        Assert.assertEquals(WeekDayEnum.SUN.getOrder(),WeekDayEnum.getDayByOrder(7).getOrder());
    }

    @Test
    public void compareOrder(){
        Assert.assertEquals(-6,WeekDayEnum.Mon.compareTo(WeekDayEnum.SUN));
    }

    @Test
    public void getNext(){
        Assert.assertEquals(WeekDayEnum.Mon,WeekDayEnum.SUN.getNext());
    }
}
