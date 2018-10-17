package com.heyi.core.netty.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heyi.core.netty.AutoConfig;
import com.heyi.core.netty.common.UUIDHexGenerator;
import com.heyi.core.netty.domain.OgUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @描述 com.heyi.core.netty.service
 * @创建人 ljliu
 * @创建时间 2018/10/15
 * @修改人和其它信息
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AutoConfig.class})
@ActiveProfiles("dev")
//@Transactional
public class OgUserServiceTest {

    private static final Log logger = LogFactory.getLog(UserServiceTest.class);


    @Autowired
    private OgUserService userService;

    @Before
    public void setUp() throws Exception {
        logger.info("-------------setup-----------------");
    }

    @After
    public void tearDown() throws Exception {
        logger.info("-------------one method test finish-----------------");
    }

    @Test
    public void getUser() throws Exception {
        String id = "00000000-0000-0000-0000-000000000001";
        OgUser user = this.userService.getUser(id);
        ObjectMapper mapper = new ObjectMapper();

        System.out.println(mapper.writeValueAsString(user));
        //Assert.assertEquals(true, user.getSex());
        Assert.assertEquals("刘立军", user.getUserName());
        Assert.assertEquals("llj", user.getWorkNo());
    }

    @Test
    public void addUser() throws Exception {
        OgUser user = new OgUser();
        user.setId(UUIDHexGenerator.generate());
        user.setWorkNo("llj");
        user.setUserName("刘立军");
        user.setEnabled(true);
        user.setSex(true);
        user.setSortIndex(100);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        user.setBirthday(df.parse("1984-09-21"));
        //user.setCreateTime(new Date());
        //user.setLastModifyTime(new Date());
        this.userService.addUser(user);
    }

    @Test
    public void getAll() throws Exception{
        List<OgUser> list = this.userService.getAll();
        list.forEach((item) -> {
            System.out.println(item.getUserName() + ":" + item.getWorkNo());
        });


        list.stream().limit(5).forEach((item) -> System.out.println("->" + item.getUserName()));
        System.out.println(list.stream().count() + ":" + list.size());

        System.out.println(list.stream().mapToInt(OgUser::getSortIndex).sum());

        list.stream().map(this::getContact).forEach((item) -> System.out.println(item));

        list = list.stream().sorted(new Comparator<OgUser>() {
            @Override
            public int compare(OgUser o1, OgUser o2) {
                return o2.getSortIndex() - o1.getSortIndex();
            }
        }).collect(Collectors.toList());

        System.out.println("---------skip-------------");
        list.stream().skip(1).limit(5).forEach(o->System.out.println(o.getSortIndex()));
        System.out.println("---------skip-------------");

        Map<String, String> mapList = list.stream().collect(Collectors.toMap(OgUser::getId, OgUser::getWorkNo));
        mapList.entrySet().forEach((item) -> System.out.println(item.getKey() + ":" + item.getValue()));
        list.forEach((item) -> {
            System.out.println(item.getUserName() + ":" + item.getWorkNo() + ":" + item.getSortIndex());
        });

        System.out.println("----------------------");
        OgUser user= list.stream().filter(o -> o.getWorkNo().equals("admin")).findFirst().get();
        System.out.println("---"+user.getWorkNo()+"--"+user.getUserName());


        Map<String,List<OgUser>> group1 =  list.stream().collect(Collectors.groupingBy(OgUser::getWorkNo));
        group1.entrySet().forEach(o-> System.out.println(o.getKey()+"==="+o.getValue().size()));

        List<Integer> list1=new ArrayList<>();
        list1.add(10);
        list1.add(20);
        list1.stream().mapToInt(Integer::intValue).sum();



        list.parallelStream().filter(o->o.getSortIndex()>2 || o.getWorkNo().equals("llj"))
                .forEach(o->System.out.println(o.getSortIndex()+":"+o.getWorkNo()));
    }

    public String getContact(OgUser user) {
        return user.getWorkNo() + "---" + user.getUserName();
    }






}