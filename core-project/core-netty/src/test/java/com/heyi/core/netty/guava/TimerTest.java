package com.heyi.core.netty.guava;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author
 * @createdate
 * @description
 **/
public class TimerTest {
    public static void main(String[] args) throws IOException {
        Timer timer=new Timer();
        timer.schedule(new MyTask(),2000);

        System.in.read();
    }


    static class MyTask extends TimerTask{


        @Override
        public void run() {
            System.out.println("timer to do");
        }
    }
}
