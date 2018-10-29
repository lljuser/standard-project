package com.heyi.core.netty.guava;

import com.heyi.core.netty.domain.OgUser;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @描述 com.heyi.core.netty.guava
 * @创建人 ljliu
 * @创建时间 2018/10/29
 * @修改人和其它信息
 */

/**
 * CGLIB 动态代理 []
 *
 */

public class CglibProxyTest {
    public static void main(String[] args){
        CglibProxy proxy=new CglibProxy();
        OgUser user=  proxy.createProxy(new OgUser());
        user.setUserName("cctv-setUserName");
        user.getUserName();

    }

    static class CglibProxy implements MethodInterceptor{
        //private Object obj;
        public <T> T createProxy(T t){
            //this.obj=t;
            Enhancer enhancer=new Enhancer();
            enhancer.setSuperclass(t.getClass());
            enhancer.setCallback(this);
            return (T)enhancer.create();
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("before----------------");
            Object result =methodProxy.invokeSuper(o,objects);
            //Object result= method.invoke(this.obj,objects);
            System.out.println(result);
            System.out.println("end----------------");
            return result;



        }
    }
}
