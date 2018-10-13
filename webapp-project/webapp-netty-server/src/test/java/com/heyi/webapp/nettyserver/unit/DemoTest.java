package com.heyi.webapp.nettyserver.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration*/
public class DemoTest {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Test
    public void test1(){
        Class<String> data = String.class;
        DemoStudent student=new DemoStudent();
        DemoPeople people=new DemoPeople();
        people.setName2("s2");

    }

    static class DemoStudent {
        private String name1;

        public String getName1() {
            return name1;
        }

        public void setName1(String name1) {
            this.name1 = name1;
        }
    }

      class DemoPeople{
        private String name2;

         public String getName2() {
             return name2 +".";
         }

         public void setName2(String name2) {
             this.name2 = name2;
         }
     }
}
