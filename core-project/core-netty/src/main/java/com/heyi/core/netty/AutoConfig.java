package com.heyi.core.netty;

import com.alibaba.druid.pool.DruidDataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {"com.heyi.core.netty"})
@MapperScan(basePackages = "com.heyi.core.netty.repository")
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
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration configuration=new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        factoryBean.setConfiguration(configuration);
        return factoryBean;
    }
}
