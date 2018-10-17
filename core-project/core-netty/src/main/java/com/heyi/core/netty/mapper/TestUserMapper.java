package com.heyi.core.netty.mapper;

import com.heyi.core.netty.domain.TestUser;
import org.springframework.stereotype.Repository;

/**
 * @描述 com.heyi.core.netty.mapper
 * @创建人 ljliu
 * @创建时间 2018/10/17
 * @修改人和其它信息
 */
@Repository
public interface TestUserMapper {
    int saveUser(TestUser user);
}
