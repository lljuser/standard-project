package com.heyi.webapp.nettyserver.unit;

import com.heyi.webapp.nettyserver.service.MyService;
import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyServiceTest {
    static MyService myService;

    @Before
    public void setUp() throws Exception {
        //ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring-config.xml");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MyService.class);
        context.refresh();

        //context.start();
        myService=(MyService)context.getBean("myService");
    }

    @After
    public void tearDown() throws Exception {
        myService.test();
    }

    @Test
    public void getName() {
         //System.out.println("llj".equals(myService.getName()));
         Assert.assertEquals("llj",myService.getName());
    }

    @org.junit.Test
    @Ignore
    public void getAge() {
        //System.out.println(100 == myService.getAge());
        Assert.assertEquals(10,myService.getAge());
    }

    @Test
    public void whoBig() {
        Assert.assertEquals(true,myService.whoBig(100,10));
    }


}