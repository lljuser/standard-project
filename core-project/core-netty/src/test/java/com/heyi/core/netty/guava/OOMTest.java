package com.heyi.core.netty.guava;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @createdate
 * @description
 **/

/**
 * 一个线oom后，线程会关闭同时释放其持有的内存。这个时候不会影响到其它线程继续申请内存
 * 一个线程oom的测试，其它线程是否可以正常运行
 */
public class OOMTest {

    public static void main(String[] args) {
        Thread thread1 = new Thread(new MyThread0(), "thread-0");
        Thread thread2 = new Thread(new MyThread1(), "thread-1");

        thread1.start();
        thread2.start();

    }

    static class MyThread0 implements Runnable {
        @Override
        public void run() {
            List<byte[]> list = new ArrayList<>();
            while (true) {
                list.add(new byte[1024]);
            }
        }
    }

    static class MyThread1 implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    List<byte[]> list = new ArrayList<>();

                    list.add(new byte[1024]);
                    Thread.sleep(1000);
                    System.out.println("======mythread2=======");
                } catch (Exception ex) {

                }
            }

        }
    }
}
