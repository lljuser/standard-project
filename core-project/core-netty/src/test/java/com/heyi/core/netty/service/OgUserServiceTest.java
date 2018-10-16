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
import java.util.Calendar;

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
    public void getUser() throws Exception{
        String id="8a81831766770f8b0166770f8b590000";
        OgUser user = this.userService.getUser(id);
        ObjectMapper mapper=new ObjectMapper();

        System.out.println( mapper.writeValueAsString(user));
        Assert.assertEquals(true,user.getSex());
        Assert.assertEquals("刘立军",user.getUserName());
        Assert.assertEquals("llj",user.getWorkNo());
    }

    @Test
    public void addUser() throws  Exception{
        OgUser user=new OgUser();
        user.setId(UUIDHexGenerator.generate());
        user.setWorkNo("llj");
        user.setUserName("刘立军");
        user.setEnabled(true);
        user.setSex(true);
        SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        user.setBirthday(df.parse("1984-09-21"));
        //user.setCreateTime(new Date());
        //user.setLastModifyTime(new Date());
        this.userService.addUser(user);
    }

    @Test
    public void calendarTest(){

        Calendar calendar=Calendar.getInstance();
        //calendar.setTime(new Date());
        System.out.println(calendar.getTime());

        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(calendar.get(Calendar.DATE));
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));

        calendar.add(Calendar.YEAR,1);
        calendar.add(Calendar.MONTH,1);
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