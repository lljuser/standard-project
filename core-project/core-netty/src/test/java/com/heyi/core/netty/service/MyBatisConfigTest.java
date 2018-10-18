package com.heyi.core.netty.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heyi.core.netty.AutoConfig;
import com.heyi.core.netty.common.UUIDHexGenerator;
import com.heyi.core.netty.domain.OgProperty;
import com.heyi.core.netty.domain.OgUser;
import com.heyi.core.netty.domain.TestUser;
import com.heyi.core.netty.mapper.OgUserMapper;
import com.heyi.core.netty.mapper.TestUserMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @描述 com.heyi.core.netty.service
 * @创建人 ljliu
 * @创建时间 2018/10/16
 * @修改人和其它信息
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AutoConfig.class})
@ActiveProfiles("dev")
public class MyBatisConfigTest {
    private static final Log logger=LogFactory.getLog(MyBatisConfigTest.class);

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /*@BeforeClass
    public static void init(){
        InputStream stream=null;
        try {
            stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mybatis-config.xml");
            SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
            sqlSessionFactory = builder.build(stream);
        }finally {
            if(stream!=null)
                try {
                    stream.close();
                }catch (Exception ex){

                }
        }

    }*/

    @Test
    public void getAll(){
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try {
            List<OgUser> list =
                    sqlSession.selectList("com.heyi.core.netty.mapper.OgUserMapper.getAll");

            ObjectMapper mapper=new ObjectMapper();
            logger.info(mapper.writeValueAsString(list));
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void getUser(){
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try {
            OgUser user =
                    sqlSession.selectOne("com.heyi.core.netty.mapper.OgUserMapper.getUserByUserId",
                            "8a8183176681068301668106849a0000");

            ObjectMapper mapper=new ObjectMapper();
            logger.info(mapper.writeValueAsString(user));
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

            logger.info( dateFormat.format(user.getTimeStamp()));
            logger.info(user.getTimeStamp());
            logger.info(user.getCreateTime());
            logger.info(dateFormat.format(user.getLastModifyTime()));
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void getPropertiesByUserId() throws Exception{
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try {
            List<OgProperty> propertyList =
                    sqlSession.selectList("com.heyi.core.netty.mapper.OgUserMapper.getPropertiesByUserId",
                            "00000000-0000-0000-0000-000000000001");

            ObjectMapper mapper=new ObjectMapper();
            System.out.println(mapper.writeValueAsString(propertyList));
            propertyList.forEach(o->System.out.println(o.getUser().getWorkNo()));

            logger.info(mapper.writeValueAsString(propertyList));
            Assert.assertEquals(3,propertyList.size());
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            sqlSession.close();
        }
    }

    @Test
    @Repeat(10)
    public void saveUser(){
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try{
            OgUserMapper userMapper = sqlSession.getMapper(OgUserMapper.class);

            OgUser user = new OgUser();
            user.setId(UUIDHexGenerator.generate());
            user.setWorkNo("cctv");
            user.setUserName("中央台");
            user.setEnabled(true);
            user.setSex(true);
            user.setSortIndex(100);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            user.setBirthday(df.parse("1984-09-21 18:00:00"));

            Date date=new Date();
            //System.out.println(date+"---------"+df.format(date));
            user.setCreateTime(date);


            Integer result =  userMapper.saveUser(user);
            Assert.assertEquals(1,result.intValue());

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            sqlSession.close();
        }


    }

    @Test
    public void saveTestUser() throws  Exception{
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try {
            TestUserMapper mapper=sqlSession.getMapper(TestUserMapper.class);
            TestUser user=new TestUser();
            user.setName("rick");
            user.setAddress("shanghai");
            user.setAge(28);

            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            Date mydate = dateFormat.parse("1989-10-1");
            user.setBirthday(mydate);

            Integer result = mapper.saveUser(user);
            System.out.println(user.getId()+"------------------------>:"+result);

        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void updateUser() throws  Exception{
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try {
            OgUserMapper mapper=sqlSession.getMapper(OgUserMapper.class);

            OgUser user = mapper.getUserByUserId("00000000-0000-0000-0000-000000000001");

            user.setUserName("超级管理员");
            user.setSortIndex(5);
            user.setDuty("IT");
            user.setNickName("Rick");

            mapper.updateUser(user);

            OgUser user1 =  mapper.getUserByUserId("00000000-0000-0000-0000-000000000001");
            Assert.assertEquals(user1.getNickName(),user.getNickName());

        }finally {
            sqlSession.close();
        }
    }

    @Test
    @Ignore
    public void removeUser(){
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try{
            OgUserMapper mapper=sqlSession.getMapper(OgUserMapper.class);

            OgUser ogUser=mapper.getLastUser();
            System.out.println("--------will remove---------");
            System.out.println(ogUser.getId());
            System.out.println("--------remove---------");
            //mapper.removeUser(ogUser.getId());
            mapper.removeUser(ogUser);

        }finally {
            sqlSession.close();
        }
    }

    @Test
    //@Transactional
    public void removeUserByWorkNoAndUserName(){
        SqlSession sqlSession=sqlSessionFactory.openSession(true);
        try{
            OgUserMapper mapper=sqlSession.getMapper(OgUserMapper.class);

            OgUser ogUser=mapper.getLastUser();
            System.out.println("--------will remove---------");
            System.out.println(ogUser.getId());
            System.out.println("--------remove---------");
            //mapper.removeUser(ogUser.getId());
            Integer result = mapper.removeUserByWorkNoAndUserName(ogUser);
            System.out.println("删除数："+result.intValue());
            sqlSession.commit();

        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }
}
