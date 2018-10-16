package com.heyi.core.netty.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heyi.core.netty.AutoConfig;
import com.heyi.core.netty.domain.OgUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.List;

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
                    sqlSession.selectOne("com.heyi.core.netty.mapper.OgUserMapper.getUserByUserId","00000000-0000-0000-0000-000000000002");

            ObjectMapper mapper=new ObjectMapper();
            logger.info(mapper.writeValueAsString(user));
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            sqlSession.close();
        }


    }
}
