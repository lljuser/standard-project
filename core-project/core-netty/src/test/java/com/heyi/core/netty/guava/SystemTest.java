package com.heyi.core.netty.guava;


import org.junit.Test;

import java.util.*;

/**
 * @author
 * @createdate
 * @description
 **/
public class SystemTest {
    @Test
    public void test(){
        Properties properties=System.getProperties();
        //properties.list(System.out);

        //properties.keySet().stream().forEach(o-> System.out.println(properties.getProperty(o.toString())));

//        Iterator<Object> ite=properties.keySet().iterator();
//        while (ite.hasNext()){
//            String key=ite.next().toString();
//            System.out.println(properties.get(key)+"---" +key+"="+properties.getProperty(key));
//        }


//        for(Map.Entry<Object,Object> entry:properties.entrySet()){
//            System.out.println(entry.getKey()+"="+entry.getValue());
//        }

//        for(Iterator<Map.Entry<Object,Object>> iterator=properties.entrySet().iterator();iterator.hasNext();){
//            Map.Entry<Object,Object> entry=iterator.next();
//            System.out.println(entry.getKey()+"="+entry.getValue());
//        }

        Enumeration<Object> ss = properties.elements();
        while (ss.hasMoreElements()){
            Object a = ss.nextElement();
            System.out.println(a);
        }



    }
}
