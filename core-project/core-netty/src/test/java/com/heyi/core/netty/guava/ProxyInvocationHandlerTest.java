package com.heyi.core.netty.guava;

/**
 * @author
 * @createdate
 * @description
 **/

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * InvocationHandler And Proxy create proxy aop
 *
 */
public class ProxyInvocationHandlerTest {

    public static void main(String[] args){
        MyInvocationHandler handler=new MyInvocationHandler();
        Person person = handler.createProxy(new Student());

        //Person person1= (Person)Proxy.newProxyInstance(handler.getClass().getClassLoader(),new Class<?>[]{Person.class},handler);

        System.out.println(person.getUserName("cctv"));
    }

    static class MyInvocationHandler implements InvocationHandler{
        private Object obj;
        public <T> T createProxy(T obj){
            this.obj=obj;
            return (T)Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
            try{
                System.out.println("before-------------");
                Object temp= method.invoke(this.obj,args);

                return temp;
            }catch (Exception ex){
                throw ex;
            }finally {
                System.out.println("after--------------");
            }
        }
    }

    static interface Person{
        String getUserName(String name);
    }

    static class Student implements Person{
        @Override
        public String getUserName(String name) {
            return name;
        }
    }


}
