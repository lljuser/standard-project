package com.heyi.core.netty.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.heyi.core.netty.AutoConfig;
import com.heyi.core.netty.domain.OgUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

/**
 * @描述 com.heyi.core.netty.mapper
 * @创建人 ljliu
 * @创建时间 2018/10/22
 * @修改人和其它信息
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AutoConfig.class})
@ActiveProfiles("dev")
//@Transactional
public class OgUserMapperTest {

    @Autowired
    private OgUserMapper ogUserMapper;

    @Test
    //@Rollback(false)
    public void getUserByUserId() throws JsonProcessingException, InterruptedException {
        OgUser user= this.ogUserMapper.getUserByUserId("00000000-0000-0000-0000-000000000002");


        FilterProvider filterProvider=new SimpleFilterProvider().addFilter("myFilter", SimpleBeanPropertyFilter.serializeAllExcept("properties"));
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //通过Filter方式进行忽略
        //objectMapper.setFilterProvider(filterProvider);


        System.out.println("1------------------------");
        //按需过滤字段，Filter必须在序列化的类上注解
        //System.out.println(objectMapper.writer(filterProvider).writeValueAsString(user.getProperties()));
        //全局设置过滤器
        System.out.println(objectMapper.writeValueAsString(user));
        System.out.println(objectMapper.writeValueAsString(user.getOperationLog()));

        System.out.println("2------------------------");
        System.out.println(objectMapper.writeValueAsString(user.getProperties()));

        System.out.println("3------------------------");
        OgUser user1= this.ogUserMapper.getUserByUserId("00000000-0000-0000-0000-000000000002");
        Assert.assertEquals(user.getId(),user1.getId());
        OgUser user2= this.ogUserMapper.getUserByUserId("00000000-0000-0000-0000-000000000002");
        Assert.assertEquals(user.getId(),user1.getId());
        //Thread.sleep(10000);
    }
}