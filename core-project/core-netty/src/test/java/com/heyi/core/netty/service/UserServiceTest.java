package com.heyi.core.netty.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.heyi.core.netty.AutoConfig;
import com.heyi.core.netty.DBConfig;
import com.heyi.core.netty.MyConfig;
import com.heyi.core.netty.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.*;
import org.junit.runner.RunWith;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * @描述 com.heyi.core.netty.service
 * @创建人 ljliu
 * @创建时间 2018/10/12
 * @修改人和其它信息
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AutoConfig.class})
@Transactional
@Rollback
public class UserServiceTest {
    private static final Log logger = LogFactory.getLog(UserServiceTest.class);

    @Autowired
    private UserService userService;

    @Autowired
    private Environment env;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DBConfig dbConfig;

    @Autowired
    private MyConfig myConfig;

    @Before
    public void setup(){
        logger.info("one method setup---------------------");
    }

    @After
    public void tearDown(){
        logger.info("one method has test---------------------");
    }

    @BeforeClass
    public static void startAllTest(){
        logger.info("start all test---------------------");
    }

    @AfterClass
    public static void endEndTest(){
        logger.info("end all test---------------------");
    }

    @Test
    public void getName() {
        Assert.assertEquals("llj", userService.getName());
    }


    @Test
    public void getUser() {
        Assert.assertEquals("cctv", userService.getUser().getName());
    }

    @Test
    public void getConfig2(){
        System.out.println(dbConfig.getUrl());
    }

    @Test
    public void getConfig3(){
       Assert.assertEquals(myConfig.getName(),env.getProperty("my.name"));
    }

    @Test
    public void getConfig() {
        URL p1 = Thread.currentThread().getContextClassLoader().getResource("dev/db.yml");
        URL p2 = this.getClass().getResource("/dev/db.yml");
        System.out.println(p1.getFile());
        System.out.println(p2.getFile());

        Assert.assertEquals(true, p1.getFile().equals(p2.getFile()));
    }

    @Test
    public void getUserFromDB(){
        String sql="select count(0) from t_user";
        Long num = jdbcTemplate.queryForObject(sql,Long.class);
        System.out.println(num);

        sql="select * from t_user where id = ?";

        User user = jdbcTemplate.queryForObject(sql,new UserMapper(),1);

        System.out.println(user);
    }

    @Test
    //@Rollback(false)
    public void insertUser(){
        String sql="insert into t_user(name,age,address,birthDay) values(?,?,?,?)";

        this.jdbcTemplate.update(sql,"mecity",20,"shenzhen","2010-1-1");

        List<Object[]> list=new ArrayList<>();
        list.add(new Object[]{"llj1",21,"shanghai","2010-1-1"});
        list.add(new Object[]{"llj2",21,"shanghai","2010-1-1"});
        list.add(new Object[]{"llj3",21,"shanghai","2010-1-1"});
        list.add(new Object[]{"llj4",21,"shanghai","2010-1-1"});
        this.jdbcTemplate.batchUpdate(sql,list);
    }

    @Test
    public void getUserList(){
        String sql="select * from t_user where id>? limit 0,10";
        List<User> list = this.jdbcTemplate.query(sql,new UserMapper(),0);
        logger.info(list);


        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql,0);
        for(Map<String, Object> item : rows){
            logger.info(item);
        }


        //Map<String, Object> singleItem = this.jdbcTemplate.queryForMap(sql,8);
        //this.jdbcTemplate.queryForRowSet(sql,0);


    }

    @BeforeTransaction
    public void startTrans(){
        logger.info("start trans---------------------------");
    }

    @AfterTransaction
    public void endTrans(){
        logger.info("end trans---------------------------");
    }


    static class UserMapper implements RowMapper<User>{
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user=new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setBirthDay(rs.getDate("birthDay"));
            user.setAddress(rs.getString("address"));

            return user;
        }
    }


    @Test
    public void queryWithJDBC(){
        Connection conn=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url= env.getProperty("dataSource.url");
            conn=DriverManager.getConnection(url,"hive","hivehive");

            psmt=conn.prepareStatement("select * from t_user where id>?");
            psmt.setInt(1,1);

            rs=psmt.executeQuery();

            int i=0;
            while (rs.next()){
                UserMapper mapper=new UserMapper();
                User user = mapper.mapRow(rs,++i);
                System.out.println(user);
            }


        }catch (Exception ex){
            logger.info(ex.getMessage());
        }finally {
            try {
                if(rs!=null)
                    rs.close();
                if(psmt!=null)
                    psmt.close();
                if(conn!=null)
                    conn.close();
            }catch (SQLException ex){

            }

        }
    }

}