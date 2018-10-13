package com.heyi.core.netty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @描述 com.heyi.core.netty
 * @创建人 ljliu
 * @创建时间 2018/10/12
 * @修改人和其它信息
 */
@Component
@PropertySource(value = {"classpath:dev/test.properties"}) ////会自动注入Envoriment中
public class MyConfig {
    @Value("${my.name}")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Value("${my.age}")
    private String age;
}
