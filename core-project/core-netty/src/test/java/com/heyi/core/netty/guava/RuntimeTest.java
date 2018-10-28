package com.heyi.core.netty.guava;

import org.junit.Test;

/**
 * @author
 * @createdate
 * @description
 **/
public class RuntimeTest {
    @Test
    public void test1() throws Exception{
        Runtime runtime=Runtime.getRuntime();

        System.out.println(runtime.freeMemory());
        System.out.println(runtime.maxMemory());

        String abc="aasfdsafdsafdsafsaf";
        for(int i=0;i<20;i++){
            abc+=abc;
        }
        //byte[] bytes=new byte[1024*10];

        System.out.println(runtime.freeMemory());
        System.out.println(runtime.maxMemory());
        runtime.gc();

        System.out.println(runtime.freeMemory());
        System.out.println(runtime.maxMemory());
        //runtime.exec("echo 'sss' >> test.txt");
    }

    @Test
    public void test2() throws Exception{
        StringTest stringTest=new StringTest();
        stringTest=null;
        System.gc();

        Thread.sleep(10000);


    }
}
