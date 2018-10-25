package com.heyi.core.netty.guava;

import com.google.common.base.Optional;
import com.heyi.core.netty.domain.OgUser;
import org.junit.Assert;
import org.junit.Test;

/**
 * @描述 com.heyi.core.netty.guava
 * @创建人 ljliu
 * @创建时间 2018/10/25
 * @修改人和其它信息
 */
public class OptionalTest {
    @Test
    public void test1(){
        OgUser user=new OgUser();
        user.setUserName("llj");

        Optional<OgUser> user1=Optional.fromNullable(null);
        System.out.println(user1.isPresent());


        Optional<OgUser> user2=Optional.absent();
        System.out.println(user2.isPresent());


         user1.orNull();
         user1.or(user);
         user1.get();
        //Assert.assertEquals(user,user1.get());

        Integer value1 =  null;
        Integer value2 =  new Integer(10);
        //Optional.fromNullable - allows passed parameter to be null.
        Optional<Integer> a = Optional.fromNullable(value1);
        //Optional.of - throws NullPointerException if passed parameter is null
        Optional<Integer> b = Optional.of(value2);

        Integer t1 = a.or(new Integer(0));
        Integer t2 = b.get();

    }
}
