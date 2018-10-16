package com.heyi.core.netty.service;

import com.heyi.core.netty.domain.OgUser;
import com.heyi.core.netty.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述 com.heyi.core.netty.service
 * @创建人 ljliu
 * @创建时间 2018/10/15
 * @修改人和其它信息
 */
@Service
public class OgUserService {
    @Autowired
    private UserMapper userMapper;

    public OgUser getUser(String userId){
        return this.userMapper.getUserByUserId(userId);
    }

    public int addUser(OgUser user){
        return this.userMapper.addUser(user);
    }

    public List<OgUser> getAll(){
        return this.userMapper.getAll();
    }
}
