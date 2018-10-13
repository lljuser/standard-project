package com.heyi.webapp.filesystem;

import com.heyi.core.filestorage.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableJpaRepositories(basePackageClasses = {Scanner.class})
@ComponentScan(basePackageClasses = {Scanner.class, FilesystemServerApplication.class})
@EntityScan(basePackageClasses = {Scanner.class})
@EnableJpaAuditing
// 启动事务注解
@EnableTransactionManagement
public class FilesystemServerApplication {

    public static void main(String[] args) {
        SpringApplication.run( FilesystemServerApplication.class, args );
    }

}
