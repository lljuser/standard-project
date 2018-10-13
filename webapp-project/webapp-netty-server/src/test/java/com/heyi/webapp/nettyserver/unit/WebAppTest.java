package com.heyi.webapp.nettyserver.unit;

import com.heyi.webapp.nettyserver.service.MyService;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration
@WebAppConfiguration
public class WebAppTest {

    @Bean
    public MyService myService(){
        return new MyService();
    }
}
