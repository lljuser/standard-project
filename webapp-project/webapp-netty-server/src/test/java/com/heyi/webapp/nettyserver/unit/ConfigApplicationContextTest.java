package com.heyi.webapp.nettyserver.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heyi.webapp.nettyserver.conf.MyConfig;
import com.heyi.webapp.nettyserver.model.User;
import com.heyi.webapp.nettyserver.service.MyService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
//@TestExecutionListeners(listeners = {MyTestListener.class})
////@ContextConfiguration(classes = MyConfig.class)  //if not spring-boot project use this
//@Rollback
//@Transactional
//@ActiveProfiles("dev")
public class ConfigApplicationContextTest  {
    private final static Log logger = LogFactory.getLog(ConfigApplicationContextTest.class);

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected Environment env;

    @Autowired
    private MyService myService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysExpect(MockMvcResultMatchers.status().isOk())
                .build();

    }

    @After
    public void tearDown() {
        logger.info("---------------------------");
    }

    @Test
    @Repeat(10)
    public void getName() {
        String str=null;

        //assert str!=null:"加密的字符串为null";
        Assert.assertEquals("llj", myService.getName());
    }

    @Test
    public void getAge() {
        Assert.assertEquals(100, myService.getAge());
    }

    @Test
    public void mockUser1() throws Exception {

        String data = this.getPostData();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/100").param("name", "llj")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(data))
                //.andDo(MockMvcResultHandlers.print())
                .andReturn();


        logger.info("---->" + result.getResponse().toString());
    }

    @Test
    public void mockUser2() throws Exception {

        String data = this.getPostData();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/getuser")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(data)  //post data must be json
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("llj"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value("34"))
                //.andDo(MockMvcResultHandlers.print())
                .andReturn();


        logger.info("---->" + result.getResponse().getContentAsString());
    }

    public String getPostData() throws JsonProcessingException {
        User user = new User();
        user.setId(100);
        user.setName("llj");
        user.setAge(34);
        user.setAddress("shanghai");
        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(user);
        return data;
    }

    @Test
    public void testEnv(){
        Assert.assertEquals("netty-server", env.getProperty("spring.application.name"));
    }
}
