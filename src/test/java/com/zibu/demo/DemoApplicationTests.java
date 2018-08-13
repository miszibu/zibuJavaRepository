package com.zibu.demo;

import com.zibu.demo.controller.UserController;
import com.zibu.demo.properties.ZibuProperties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    private MockMvc mvc;

    @Before
    public void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
    }

    @Test
    public void testUserController() throws Exception{
        RequestBuilder request = null;
        // 查询user 预测其结果为空
        request = get("/users/") ;
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
        // 插入user 预测其结果
        request = post("/users/")
                .param("id","1")
                .param("name","测试大师")
                .param("age","22");
        mvc.perform(request)
                .andExpect(content().string(equalTo("success")));
        // 查询user 预测其结果为新插入的User
        request = get("/users/") ;
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"id\":1,\"name\":\"测试大师\",\"age\":22}]")));
        // 修改user
        request= put("/users/1")
                .param("name","zibu")
                .param("age","23");
        mvc.perform(request)
                .andExpect(content().string(equalTo("success")));
        // 获取id为1的user
        request= get("/users/1");
        mvc.perform(request)
                .andExpect(content().string(equalTo("[{\"id\":1,\"name\":\"zibu\",\"age\":23}]")));
        // 删除id为1的数据
        request = delete("/users/1");
        mvc.perform(request)
                .andExpect(content().string(equalTo("success")));
        // 获取id为1的user
        request= get("/users/1");
        mvc.perform(request)
                .andExpect(content().string(equalTo("[]")));
    }

    @Autowired
    private ZibuProperties zibuProperties;

    @Test
    public void getHello() throws Exception{
        Assert.assertEquals(zibuProperties.getName(),"zibu again");
        Assert.assertEquals(zibuProperties.getAuthor(),"zibu");
    }

}

