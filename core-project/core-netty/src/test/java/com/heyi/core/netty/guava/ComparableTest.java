package com.heyi.core.netty.guava;

import com.heyi.core.netty.domain.OgUser;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author
 * @createdate
 * @description
 **/
public class ComparableTest {
    @Test
    public void test(){
        OgUserComparable user1=new OgUserComparable();
        user1.setSortIndex(10);

        OgUserComparable user2=new OgUserComparable();
        user1.setSortIndex(2);

        //利用comparable接口
        OgUserComparable[] ogUsers=new OgUserComparable[]{user1,user2};

        Arrays.sort(ogUsers);
        Collections.sort(Arrays.asList(ogUsers));


        //利用比较器comparator
        Arrays.sort(ogUsers,new OgUserComparator());
        Collections.sort(Arrays.asList(ogUsers),new OgUserComparator());
    }

    static class OgUserComparable extends OgUser implements  Comparable<OgUser> {
        @Override
        public int compareTo(OgUser o) {
            return this.getSortIndex()>o.getSortIndex()? 1:this.getSortIndex()==o.getSortIndex()?0:-1;
        }
    }

    static class OgUserComparator implements Comparator<OgUser>{
        @Override
        public int compare(OgUser o1, OgUser o2) {
            if(o1.getSortIndex()>o2.getSortIndex())
                return 1;

            if(o1.getSortIndex()==o2.getSortIndex())
                return 0;

            if(o1.getSortIndex()<o2.getSortIndex())
                return -1;

            return -1;
        }
    }


}
