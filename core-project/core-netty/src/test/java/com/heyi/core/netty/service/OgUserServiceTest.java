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
import java.util.stream.Collectors;

/**
 * @描述 com.heyi.core.netty.service
 * @创建人 ljliu
 * @创建时间 2018/10/15
 * @修改人和其它信息
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AutoConfig.class})
@ActiveProfiles("prod")
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
        String id = "8a81831766770f8b0166770f8b590000";
        OgUser user = this.userService.getUser(id);
        ObjectMapper mapper = new ObjectMapper();

        System.out.println(mapper.writeValueAsString(user));
        Assert.assertEquals(true, user.getSex());
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
    }

    public String getContact(OgUser user) {
        return user.getWorkNo() + "---" + user.getUserName();
    }


    @Test
    public void calendarTest() {

        Calendar calendar = Calendar.getInstance();
        //calendar.setTime(new Date());
        System.out.println(calendar.getTime());

        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(calendar.get(Calendar.DATE));
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));

        calendar.add(Calendar.YEAR, 1);
        calendar.add(Calendar.MONTH, 1);
        System.out.println(calendar.getTime());


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = "2016-01-01 11:11:11";
        calendar = Calendar.getInstance();
        long nowDate = calendar.getTime().getTime(); //Date.getTime() 获得毫秒型日期
        try {
            long specialDate = sdf.parse(dateString).getTime();
            long betweenDate = (specialDate - nowDate) / (1000 * 60 * 60 * 24); //计算间隔多少天，则除以毫秒到天的转换公式
            System.out.print(betweenDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}