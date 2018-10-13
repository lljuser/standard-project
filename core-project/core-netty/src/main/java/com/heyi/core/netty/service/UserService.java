package com.heyi.core.netty.service;

import com.heyi.core.netty.model.User;
import org.springframework.stereotype.Service;

@Service

public class UserService {

    /**
     *
     * @return
     */
    public String getName(){
        return "llj";
    }

    public User getUser(){
        User user=new User();
        user.setId(100);
        user.setAge(110);
        user.setName("cctv");
        user.setAddress("beijing");
        return user;

    }
}
