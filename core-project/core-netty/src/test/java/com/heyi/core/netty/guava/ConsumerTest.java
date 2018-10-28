package com.heyi.core.netty.guava;

import com.heyi.core.netty.domain.OgUser;
import com.heyi.core.netty.model.User;
import com.sun.org.apache.bcel.internal.generic.RET;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @描述 com.heyi.core.netty.guava
 * @创建人 ljliu
 * @创建时间 2018/10/26
 * @修改人和其它信息
 */

/**
 * 函数式编程测试 Consumer,Funciton,Predicate
 */
public class ConsumerTest {
    static String[] abc={"llj","abc","cctv"};

    /**
     * 一个应用场景测试 Predicate,Consumer,Function
     * **/
    @Test
    public void test(){


        OgUser user=new OgUser();
        user.setWorkNo("llj");
        user.setSex(true);

        //判断性别为男的，设置domain为CNABS,并返回工号
        String workNo = updateUser(user,o->o.getSex()==true,o->o.setDomain("cnabs"),o->o.getWorkNo());

        //判断性别为女的，设置duty为manager,并返回工号
        workNo = updateUser(user,o->o.getSex()==false,o->o.setDuty("manager"),o->o.getWorkNo());

        System.out.println(workNo);


    }

    /**
     * 一个应用场景测试
     * 满足条件的user,更新其值,并返回工号
     * **/
    public String updateUser(OgUser user,
                           Predicate<OgUser> predicate,
                           Consumer<OgUser> consumer,
                           Function<OgUser,String> func){
        if(predicate.test(user))
            consumer.accept(user);

        return func.apply(user);
    }

    /**
     * Consumer
     */
    @Test
    public void test1(){

        Arrays.stream(abc).forEach(o->System.out.println(o));

        //写法1
        OgUser user=new OgUser();
        Consumer<OgUser> userConsumer= o->o.setUserName("llj");
        userConsumer.accept(user);

        //写法2
        Consumer<String> tt= user::setUserName;
        tt.accept("llj123");

        //自定义1
        test2((a)->System.out.println(a));
        //自定义2
        test3(o->System.out.println(o.getUserName()));
        //自定义3 函数式编程最好设计在要处理的对象内部 如集合，某某对象的处理
        user.test(o->o.getWorkNo());


    }

    public void test2(Ido ido){
        ido.Test(abc[0]);
    }

    public void test3(Consumer<OgUser> aciton){
        aciton.accept(new OgUser());
    }

    static interface  Ido{
        void Test(String a);
    }

    /**
     * Predicate
     * */
    @Test
    public void predicateTest(){
        Predicate<Integer> integerTest = x->x>5;

        System.out.println(integerTest.test(1));
        System.out.println(integerTest.test(6));

        Predicate<Integer> test2 = integerTest.or(o-> o<7);
        System.out.println(test2.test(6));
        System.out.println(test2.test(7));

        Predicate<Integer> test3 = integerTest.and(o-> o<7);
        System.out.println(test3.test(6));
        System.out.println(test3.test(7));


    }

    /**
     * Function测试
     */
    @Test
    public void funcTest(){
        Function<OgUser,String> func= (a)-> {return a.getUserName();};

        OgUser user=new OgUser();
        user.setUserName("llj");
        System.out.println(func.apply(user));
    }


}
