package com.heyi.core.netty;

import com.heyi.core.netty.domain.OgUser;
import sun.misc.Launcher;
import sun.plugin2.applet.Applet2ClassLoader;
import sun.plugin2.applet.JNLP2ClassLoader;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.DriverManager;
import java.util.ServiceLoader;

/**
 * @描述 com.heyi.core.netty
 * @创建人 ljliu
 * @创建时间 2018/10/25
 * @修改人和其它信息
 */
public class MainTest {
    public static void main(String[] args) throws  Exception{
        System.out.println(ServiceLoader.class.getClassLoader());
        System.out.println("-------------111-----------");
        ClassLoader loader1 = Thread.currentThread().getContextClassLoader();
        Class<?> c1 = loader1.loadClass("com.heyi.core.netty.domain.OgUser");
        System.out.println("------------class.forname----------");

        MyClassLoader loader3=new MyClassLoader();

        //Class<?> c3=Class.forName("com.heyi.core.netty.domain.OgUser",false,loader3);

        Class<?> c2= Class.forName("com.heyi.core.netty.domain.OgUser",false,loader1);



        //Object obj3 = c3.newInstance();
        Object obj2 = c2.newInstance();


            System.out.println("---oguser3");

          /*  Method m = c3.getDeclaredMethod("setUserName",String.class);
            m.invoke(obj3,"llj3");
            Method m1 = c3.getDeclaredMethod("getUserName");
            String userName= (String)m1.invoke(obj3);
*/

        if(obj2 instanceof OgUser){
            System.out.println("---oguser2");
            OgUser user= (OgUser)obj2;
            user.getUserName();

        }



        //OgUser user3=(OgUser)obj3;

        ClassLoader loader2= DriverManager.class.getClassLoader();
        //System.out.println(loader2.getClass().getName());
        DriverManager.getDrivers();


        System.out.println("------------------------");

    }
}
