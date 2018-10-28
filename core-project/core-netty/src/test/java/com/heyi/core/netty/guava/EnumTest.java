package com.heyi.core.netty.guava;

import org.junit.Test;

import java.util.*;

/**
 * @author
 * @createdate
 * @description
 **/
public class EnumTest {
    @Test
    public void test(){
        Color color=Color.BLUE;
        switch (color){
            case RED:
                break;
            case BLUE:
                break;
            case GREEN:
                break;
        }

        Color  c=Enum.valueOf(Color.class,"BLUE");
        System.out.println(c.getName());

        for (Color color1:Color.values()){
            System.out.println(color1.ordinal()+":"+color1.name()+":"+color1.getName()+":"+color1.getIndex());
        }

        Map<Color,String> map1=new EnumMap<Color, String>(Color.class);
        Map<Color,String> map2=new HashMap<>();
        Set<Color> set1=EnumSet.allOf(Color.class);
    }



    static enum Color implements IDo{
        RED("红色",1){
            @Override
            public void doWork() {


            }
        }, GREEN("绿色",2){
            @Override
            public void doWork() {

            }
        }, BLUE("蓝色",3){
            @Override
            public void doWork() {

            }
        };

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        private Integer index;

        private Color(String name,Integer index){
            this.name=name;
            this.index=index;
        }

    }

    static interface IDo{
        void doWork();
    }
}
