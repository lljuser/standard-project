package com.heyi.core.netty.reflection;

import com.alibaba.fastjson.util.ServiceLoader;
import com.heyi.core.netty.domain.OgUser;
import com.mysql.cj.jdbc.Driver;
import org.junit.Test;
import sun.reflect.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.DriverManager;

/**
 * @描述 com.heyi.core.netty.reflection
 * @创建人 ljliu
 * @创建时间 2018/10/25
 * @修改人和其它信息
 */
public class ReflectionDemoTest {
    @Test
    public void testOgUser() throws Exception {
       /* Class<?> caller = Reflection.getCallerClass(1);
        ClassLoader loader = caller.getClassLoader();*/

        /**
         * ClassLoader.loadClass方式加载类 构造反射创建对象
      */
        ClassLoader loader1 = Thread.currentThread().getContextClassLoader();
        Class<?> c1 = loader1.loadClass("com.heyi.core.netty.domain.OgUser");
       /* ClassLoader loader2 = this.getClass().getClassLoader();
        Class<?> c2 = loader2.loadClass("com.heyi.core.netty.domain.OgUser");*/
        Constructor constructor= c1.getConstructor(String.class);
        Object obj1 =  constructor.newInstance("llj--ctor");
        OgUser user1=(OgUser)obj1;
        System.out.println(((OgUser) obj1).getUserName());



        /**
         * Class.forName加载方式加载类 通过反射对象方法
         */
        Class<?> c=Class.forName("com.heyi.core.netty.domain.OgUser");
        ClassLoader loader3 = c.getClassLoader();
        Object obj=c.newInstance();
        Method m=c.getDeclaredMethod("setUserName",String.class);
        m.invoke(obj,"llj--getUserName");

        if(obj instanceof OgUser){
            OgUser user= (OgUser)obj;
            System.out.println(user.getUserName());
        }

        Field field=c.getDeclaredField("userName");
        field.setAccessible(true);
        field.set(obj,"cctv-setUserName");

        String name=(String) field.get(obj);
        System.out.println(name);

    }

    public static void main(String[] args) throws  Exception{
        ClassLoader loader1 = Thread.currentThread().getContextClassLoader();
        Class<?> c1 = loader1.loadClass("com.heyi.core.netty.domain.OgUser");

        ClassLoader loader2= DriverManager.class.getClassLoader();
        System.out.println(loader2.getClass().getName());
        DriverManager.getDrivers();



    }
}
