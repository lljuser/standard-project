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

    {
        System.out.println("---1---");
    }

    public StringTest(){
        System.out.println("---test---");
    }

    public <E> List<E> test4(){
        return new ArrayList<>();
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

    private void prvTest1(){

    }
    protected void prvTest2(){

    }

    public class StringTest1 extends StringTest{

    }

    private class StringTest2{

    }

    static class StringTest3 extends StringTest{
        public StringTest3(){
            System.out.println("-------test3---------");
        }

        public void test(){

        }

        @Override
        public void test1() {
            super.test1();
        }

        @Override
        protected void prvTest2() {
            super.prvTest2();
        }


    }
}
