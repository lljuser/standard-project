package com.heyi.core.netty.guava;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述 com.heyi.core.netty.guava
 * @创建人 ljliu
 * @创建时间 2018/10/26
 * @修改人和其它信息
 */
public class StringTest {
    static StringTest instance=new StringTest();
    static {
        System.out.println("---ctor---");
    }



    public StringTest(){
        System.out.println("---test---");
    }


    @Test
    public void test1(){
        String a1=new String("llj");
        String a2="llj";
        String a3="llj";
        Assert.assertEquals(a1,a2);
        Assert.assertEquals(a2,a3);
    }

    @Test
    public void test2(){
        String a1=new String(new char[]{'l','l','j'});
        System.out.println(a1);
        System.out.println(a1.toCharArray());

        System.out.println(a1.startsWith("ll"));
        System.out.println(a1.endsWith("lj"));
        System.out.println(a1.substring(0,2));
        System.out.println(a1.substring(1,2));
        System.out.println(a1.concat("-cctv"));
        System.out.println(a1.contains("llj"));
        System.out.println(a1.equalsIgnoreCase("LLJ"));
        System.out.println(a1.matches("llj"));

        byte[] b1=a1.getBytes();

        String a2=new String(b1);
        System.out.println("a2:"+a2);

    }

    @Test
    public void test3(){
        StringBuffer sbf=new StringBuffer();
        sbf.append("abc");
        sbf.append("efg");

        sbf.reverse();  //利用stack

        System.out.println(sbf.toString());


        String name="llj";


    }



    @Test
    public void test5(){
        String[] abc={"a","b"};
        assert abc.length==3: "length is wrong";

        System.out.println("------");
        String name="llj";
        System.out.println(name);
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("this StringTest Object is gc collection");
        super.finalize();

    }
}
