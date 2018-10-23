package com.heyi.core.netty.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heyi.core.netty.AutoConfig;
import com.heyi.core.netty.domain.OgProperty;
import com.heyi.core.netty.domain.OgUser;
import com.heyi.core.netty.mapper.OgPropertyMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @描述 com.heyi.core.netty.domain
 * @创建人 ljliu
 * @创建时间 2018/10/22
 * @修改人和其它信息
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AutoConfig.class})
@ActiveProfiles("dev")
public class OgPropertyMapperTest {
    @Autowired
    private OgPropertyMapper ogPropertyMapper;

    @Test
    public void getPropertyByPropertyId() throws Exception{
        String propertyId="00000000-0000-0000-0000-000000000001";
        OgProperty role = this.ogPropertyMapper.getPropertyByPropertyId(propertyId);
        Assert.assertEquals("Root",role.getPropertyNo());
        System.out.println("-----------------------");
        //List<OgUser> users = role.getUsers();
        ObjectMapper objectMapper=new ObjectMapper();

        objectMapper.writeValueAsString(role);

        System.out.println("-----------------------");
        OgProperty role1 = this.ogPropertyMapper.getPropertyByPropertyId(propertyId);
        objectMapper.writeValueAsString(role);
    }

    @Test
    public void getPropertyByUserId(){
        String userId="00000000-0000-0000-0000-000000000002";
        List<OgProperty> list= this.ogPropertyMapper.getPropertyByUserId(userId);
        list.parallelStream().forEach(o->{
            System.out.println(o.getName());
        });
    }


}