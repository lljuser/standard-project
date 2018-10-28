package com.heyi.core.netty.guava;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author
 * @createdate
 * @description
 **/
@MyAnnotation(value = {"test"})
public class AnnotationTest {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Class<?> c = loader.loadClass("com.heyi.core.netty.guava.AnnotationTest");

        for(Annotation annotation : c.getAnnotations()){
            System.out.println("Class Annotation:"+annotation.toString());
        }

        Method m= c.getDeclaredMethod("test");
        if(m.isAnnotationPresent(MyAnnotation.class)){
            MyAnnotation myAnnotation =   (MyAnnotation)m.getAnnotation(MyAnnotation.class);
            System.out.println("Method Annotation value:");
            System.out.println(myAnnotation.value());
            System.out.println(myAnnotation.key());
        }
    }

    @MyAnnotation(value = {"llj","cctv"},key="i am llj")
    public void test(){
        System.out.println("test get annotation");
    }
}
