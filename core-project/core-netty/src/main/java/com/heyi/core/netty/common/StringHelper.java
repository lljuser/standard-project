package com.heyi.core.netty.common;

/**
 * @描述 com.heyi.core.netty.common
 * @创建人 ljliu
 * @创建时间 2018/10/19
 * @修改人和其它信息
 */
public class StringHelper {
    public static Boolean isEmpty(String data){
        if(data!=null && data!="")
            return true;
        else
            return false;
    }
}
