package com.heyi.core.netty;

import com.alibaba.druid.pool.DruidDataSource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jca.cci.CciOperationNotSupportedException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {"com.heyi.core.netty"})
@MapperScan(basePackages = {"com.heyi.core.netty.repository","com.heyi.core.netty.mapper"})
//@PropertySources({@PropertySource(""),@PropertySource("")})
public class AutoConfig {

    private Properties getDBProperties(String profile) throws IOException {
        Properties properties = new Properties();

        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(profile+"/db.yml");
            properties.load(inputStream);
        } finally {
            inputStream.close();
        }
        return properties;
    }

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() throws IOException{
        DruidDataSource dataSource=new DruidDataSource();

        String url=env.getProperty("dataSource.url");
        String dirver=env.getProperty("dataSource.driver");
        String username=env.getProperty("dataSource.username");
        String password=env.getProperty("dataSource.password");

        dataSource.setUrl(url);
        dataSource.setDriverClassName(dirver);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;

    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws Exception{
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        //手动创建mybatis-config
        org.apache.ibatis.session.Configuration configuration=new org.apache.ibatis.session.Configuration();

        configuration.setMapUnderscoreToCamelCase(true);
        //全局延迟加载开关 false启动延迟加载 true默认直接加载
        configuration.setAggressiveLazyLoading(false);
        //全局二级缓存开关
        configuration.setCacheEnabled(true);
        configuration.setLazyLoadingEnabled(true);
        configuration.setLogPrefix("dao.");
        //configuration.addMappers("");
        factoryBean.setConfiguration(configuration);

        /**使用默认的mybatics JdbcTransaction
         *使用spring事务 注释这段，会使用spring-mybatis的springmanagertransactionfactory
         **/
/*        TransactionFactory transactionFactory=new JdbcTransactionFactory();
        factoryBean.setTransactionFactory(transactionFactory);*/

        //加载mybatis配置文件
       /* Resource mybatisConfig=new DefaultResourceLoader().getResource("classpath:mybatis-config.xml");
        factoryBean.setConfigLocation(mybatisConfig);*/


        //加载要扫描的所有mapper.xml配置文件
        ResourcePatternResolver resolver=new PathMatchingResourcePatternResolver();
        Resource[] resources= resolver.getResources("classpath:mapper/*Mapper.xml");
        factoryBean.setMapperLocations(resources);

        return factoryBean;
    }


    //已通过注解创建 Mybatis- MapperScanner加载mapper类
   /* @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        mapperScannerConfigurer.setBasePackage("com.heyi.core.netty.mapper");
    }*/
}
