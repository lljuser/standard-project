package com.heyi.webapp.nettyserver.unit;

import com.heyi.webapp.nettyserver.service.MyService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class XmlApplicationContextTest {
    @Autowired
    private MyService myService;

    @Test
    public void getName(){
        Assert.assertEquals("llj",myService.getName());
    }
}
