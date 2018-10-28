package com.heyi.core.netty.guava;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author
 * @createdate
 * @description
 **/
public class ArraysTest {
    @Test
    public void test(){
        List<Integer> list1 = Arrays.asList(new Integer[]{5,3,2,1});
        Integer[] array1 = (Integer[])list1.toArray();

        Integer[] array2 = Arrays.copyOf(array1,10);
        System.out.println(array2.length);

        //Arrays.fill(array1,8);
        Arrays.sort(array1);
        for (Integer v :array1){
            System.out.println(v);
        }


        System.out.print(Arrays.binarySearch(array1,1));


    }
}
