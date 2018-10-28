package com.heyi.core.netty.guava;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author
 * @createdate
 * @description
 **/

/**
 * Pattern 正则表达式，可用于正则匹配，拆分，替换
 */
public class RegexTest {
    @Test
    public void test1() {
        String abc = "240091234";
        String pattern = "[0-9]+";

        if (Pattern.compile(pattern).matcher(abc).matches()) {
            System.out.println("number");
        } else
            System.out.println("not number");


    }

    @Test
    public void test2() {
        String str = "1983-7-27";
        String parttern = "\\d{4}-\\d{1,2}-\\d{1,2}";

        Pattern p = Pattern.compile(parttern);
        Matcher m = p.matcher(str);
        if(m.matches()){
            System.out.println("is date");
        }else{
            System.out.println("is not date");
        }
    }

    @Test
    public void test3(){
        String str="ab23cd3efg89hi";
        String pat="\\d+";

        Pattern pattern=Pattern.compile(pat);
        String[] arr=pattern.split(str);

        for(String s : arr){
            System.out.println(s);
        }

    }

    @Test
    public void test4(){
        String str="hello world, llj";
        String pat="llj";
        Pattern p=Pattern.compile(pat);
        Matcher m=p.matcher(str);

        String newStr = m.replaceAll("cctv");
        System.out.println(newStr);

    }

    @Test
    public void test5(){
        String str="abc how old you,i am llj";
        str.matches("llj");
        String newStr = str.replace("llj","cctv");

        String[] arrs = str.split(",");
    }
}
