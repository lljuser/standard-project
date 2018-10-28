package com.heyi.core.netty.guava;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * @author
 * @createdate
 * @description
 **/
public class JDKMathTest {
    @Test
    public void test1(){
        List<Object> list=new ArrayList<>();
        list.add(Math.pow(2,3));
        list.add(Math.round(20.5));
        list.add(Math.abs(-100));
        list.add(Math.max(10,20));
        list.add(Math.min(10,30));
        list.add(Math.random());


        NumberFormat numberFormat=NumberFormat.getNumberInstance(new Locale("zh","CN"));
        list.add(numberFormat.format(10050010));
        list.add(numberFormat.format(100000.203));

        DecimalFormat decimalFormat= new DecimalFormat("####,###.##");
        list.add(decimalFormat.format(51235671.2367));

        System.out.println(list);
    }

    @Test
    public void test2(){

        Random random=new Random(1);
        for(int i=0;i<10;i++){
            System.out.println(random.nextInt(15));
        }
    }

    @Test
    public void test3(){
        BigInteger bigInteger=new BigInteger("300000000000");
        BigInteger bigInteger1=new BigInteger("200000000000");
        BigInteger bigInteger2= bigInteger.multiply(bigInteger1);
        System.out.println(bigInteger2.intValue());
    }

    @Test
    public void test4(){
        BigDecimal bigDecimal=new BigDecimal(10.34568);
        BigDecimal bigDecimal1=new BigDecimal(201.387663);
        System.out.println(bigDecimal.add(bigDecimal1).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP));

        System.out.println(bigDecimal.round(MathContext.DECIMAL32));

        System.out.println(bigDecimal.divide(bigDecimal1,1,BigDecimal.ROUND_HALF_UP));


    }

}
