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
import java.util.*;

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
    //@Repeat(10)
    public void saveUser(){
        SqlSession sqlSession=sqlSessionFactory.openSession(false);

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
            //这个事务要使用mybatis的jdbc事务才生效
            //如使用了spring事务框架时，这句不会生效
            //可在sqlsessionfactorybean创建时设置事务
            //sqlSession.rollback();
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
        saveUser();
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
    public void removeUserByWorkNoAndUserName() throws  Exception{
        saveUser();
        SqlSession sqlSession=sqlSessionFactory.openSession(true);
        try{
            OgUserMapper mapper=sqlSession.getMapper(OgUserMapper.class);

            OgUser ogUser=mapper.getLastUser();
            System.out.println("--------will remove---------");
            System.out.println(ogUser.getId());
            System.out.println("--------remove---------");
            //mapper.removeUser(ogUser.getId());
            Integer result = mapper.removeUserByWorkNoAndUserName(ogUser.getUserName(),ogUser.getWorkNo());
            //Integer result = mapper.removeUserByWorkNoAndUserName(ogUser);
            System.out.println("删除数："+result.intValue());

        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void getUserByWorkNoAndUserName(){
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try{
            OgUserMapper mapper=sqlSession.getMapper(OgUserMapper.class);
            OgUser user =mapper.getUserByWorkNoAndUserName(null,"");
            ObjectMapper objectMapper=new ObjectMapper();
            System.out.println(objectMapper.writeValueAsString(user));
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void getUserByIdsOrUsers() throws Exception{
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try{
            OgUserMapper mapper=sqlSession.getMapper(OgUserMapper.class);
            /*List<String> ids=new ArrayList<>();
            ids.add("8a818317668a424c01668a424d3e0000");
            ids.add("8a818317668af7f201668af7f3560000");
            List<OgUser> list = mapper.getUserByIds(ids);*/
            List<OgUser> list1=new ArrayList<>();
            OgUser user1=new OgUser();
            user1.setId("8a818317668a424c01668a424d3e0000");
            list1.add(user1);
            OgUser user2=new OgUser();
            user2.setId("8a818317668af7f201668af7f3560000");
            list1.add(user2);

            List<OgUser> list = mapper.getUserByUsers(list1,"admin");

            ObjectMapper objectMapper=new ObjectMapper();
            list.forEach(o->{
                try{
                   System.out.println(objectMapper.writeValueAsString(o));
                }catch (Exception ex){

                }
                finally {

                }
            });


        }catch (Exception ex){
            throw ex;
        }
        finally {
            sqlSession.close();
        }
    }

    @Test
    public void batchSave(){
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try {
            OgUserMapper mapper = sqlSession.getMapper(OgUserMapper.class);

            List<OgUser> list=new ArrayList<>();
            OgUser user1=new OgUser();
            user1.setId(UUIDHexGenerator.generate());
            user1.setUserName("管理员1");
            user1.setWorkNo("admin1");
            user1.setSex(true);
            list.add(user1);

            OgUser user2=new OgUser();
            user2.setId(UUIDHexGenerator.generate());
            user2.setUserName("管理员2");
            user2.setWorkNo("admin2");
            user2.setSex(true);
            list.add(user2);

            OgUser user3=new OgUser();
            user3.setId(UUIDHexGenerator.generate());
            user3.setUserName("管理员3");
            user3.setWorkNo("admin3");
            user3.setSex(true);
            list.add(user3);

            OgUser user4=new OgUser();
            user4.setId(UUIDHexGenerator.generate());
            user4.setUserName("管理员4");
            user4.setWorkNo("admin4");
            user4.setSex(true);
            list.add(user4);

            Integer result = mapper.batchSave(list);
            Assert.assertEquals(4,result.intValue());


        }catch (Exception ex){
            throw  ex;
        }finally {
            sqlSession.close();
        }
    }


    @Test
    public void updateByMap(){
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try {
            OgUserMapper mapper = sqlSession.getMapper(OgUserMapper.class);

            Map<String,Object> map=new HashMap<>();
            map.put("id","8a818317668b9b6e01668b9b6edf0003");
            map.put("userName","cctv");
            map.put("domain","cnabs");
            Integer result = mapper.updateByMap(map);
            Assert.assertEquals(1,result.intValue());

        }catch (Exception ex){
            throw ex;
        }finally {
            sqlSession.close();
        }
    }
}
