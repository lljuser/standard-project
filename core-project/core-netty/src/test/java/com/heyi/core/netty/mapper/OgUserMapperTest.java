package com.heyi.core.netty.mapper;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageRowBounds;
import com.heyi.core.netty.AutoConfig;
import com.heyi.core.netty.domain.OgUser;
import org.apache.ibatis.session.RowBounds;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Test
    public void getUserByPage(){
        Map<String,Object> params=new HashMap<>();
        params.put("offset",2);
        params.put("limit",3);

        List<OgUser> users =  this.ogUserMapper.getUserByPage(params);
        System.out.println("--------"+ params.get("total"));
        System.out.println("---------"+ users.size());
        System.out.println(JSON.toJSONString(users));
    }

    @Test
    public void queryUsers(){
        int pageIndex=1;
        int limit=3;
        int offset=(pageIndex-1)*limit;

        System.out.println("-------------------------------------");
        PageRowBounds page=this.getPage(1);
        List<OgUser> users=this.ogUserMapper.queryUsers(page);
        System.out.println("总数"+page.getTotal()+":"+users.size());
        users.forEach(o->System.out.println(o.getWorkNo()+":"+o.getCreateTime()));

        System.out.println("-------------------------------------");
        page=this.getPage(2);
        users=this.ogUserMapper.queryUsers(page);
        System.out.println("总数"+page.getTotal()+":"+users.size());
        users.forEach(o->System.out.println(o.getWorkNo()+":"+o.getCreateTime()));

        //无侵入式 对原有接口没有影响，无需增加分页参数
        System.out.println("-------------------------------------");
        PageHelper.startPage(1,10);
        users = this.ogUserMapper.getAll();
        System.out.println("总数"+page.getTotal()+":"+users.size());
        users.forEach(o->System.out.println(o.getWorkNo()+":"+o.getCreateTime()));

        System.out.println("-------------------------------------");
        PageHelper.offsetPage(1,10);
        users = this.ogUserMapper.getAll();
        System.out.println("总数"+page.getTotal()+":"+users.size());
        users.forEach(o->System.out.println(o.getWorkNo()+":"+o.getCreateTime()));


        Page<OgUser> userList = PageHelper.startPage(2,5).doSelectPage(()->{
            this.ogUserMapper.getAll();
        });

        List<OgUser> selectList = selectUsers();
    }

    private PageRowBounds getPage(Integer pageIndex){
        int limit=5;
        int offset=(pageIndex-1)*limit;
        return new PageRowBounds(offset,limit);
    }


    public <E> List<E> selectUsers() {

         return new ArrayList<>();
    }
}