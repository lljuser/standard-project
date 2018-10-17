package com.heyi.core.netty.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;


/**
 * @描述 com.heyi.core.netty.service
 * @创建人 ljliu
 * @创建时间 2018/10/17
 * @修改人和其它信息
 */
public class AntPathMatcherTest {
    @Test
    public void test(){
        PathMatcher matcher = new AntPathMatcher();
        String requestUrl="/user/getuser/100?type=110";
        String patternPath="/User/getuser/**";

        ((AntPathMatcher) matcher).setCaseSensitive(false);
        Assert.assertEquals(true,matcher.match(patternPath,requestUrl));
    }
}
