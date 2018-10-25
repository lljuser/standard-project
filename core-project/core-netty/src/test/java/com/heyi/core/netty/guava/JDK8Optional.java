package com.heyi.core.netty.guava;

import com.heyi.core.netty.domain.OgUser;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

/**
 * @描述 com.heyi.core.netty.guava
 * @创建人 ljliu
 * @创建时间 2018/10/25
 * @修改人和其它信息
 */
public class JDK8Optional {
    @Test
    public void test1(){
        OgUser user=new OgUser();
        user.setUserName("llj");

        Optional<OgUser> user1=Optional.ofNullable(null);

        Optional<OgUser> user2=Optional.empty();

        user1.isPresent();
        user2.isPresent();

        user1.orElse(user);

        //Assert.assertEquals(user,user2);
    }
}
