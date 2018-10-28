package com.heyi.core.netty.guava;

import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author
 * @createdate
 * @description
 **/
public class ResourceBundleTest {
    @Test
    public void test1() {
        Locale zhLocale = new Locale("zh", "CN","utf-8");
        Locale enLocale = new Locale("en", "US");

        ResourceBundle b1 = ResourceBundle.getBundle("message", zhLocale);
        String name = b1.getString("name");
        System.out.println(name);

        ResourceBundle b2 = ResourceBundle.getBundle("message", enLocale);
        String name2 = b2.getString("name");
        System.out.println(name2);



    }
}
