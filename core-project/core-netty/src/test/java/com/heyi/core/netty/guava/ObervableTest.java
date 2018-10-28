package com.heyi.core.netty.guava;

import org.junit.Test;

import java.util.Observable;
import java.util.Observer;

/**
 * @author
 * @createdate
 * @description
 **/
public class ObervableTest {
    @Test
    public void test(){
        House house=new House();
        house.addObserver(new HouseObserver());
        house.addObserver(new HouseObserver());

        house.setPrice(100);
        house.setPrice(150);
    }

    static class House extends Observable {

        private Float price;

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
            super.setChanged();
            super.notifyObservers(this.price);
        }
    }

    static class HouseObserver implements Observer{
        @Override
        public void update(Observable o, Object arg) {
            if(arg instanceof Float)
            {
                System.out.println("the price is changed:"+arg);
            }
        }
    }
}
