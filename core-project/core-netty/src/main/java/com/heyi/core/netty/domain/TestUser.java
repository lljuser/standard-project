package com.heyi.core.netty.domain;

import java.util.Date;

/**
 * @描述 com.heyi.core.netty.domain
 * @创建人 ljliu
 * @创建时间 2018/10/17
 * @修改人和其它信息
 */
public class TestUser {
    private Integer id;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    private String name;
    private Date birthday;
    private String address;
    private String createTime;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
