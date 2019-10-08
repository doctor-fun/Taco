package com.example.tacocloud.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
//@WebMvcTest(HomeController.class)
//两个注解一弄，就会homecontroller被注入进来

public class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public  void testHomePage() throws Exception{
        mockMvc.perform(get("/").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk()) //希望返回值是200
                .andExpect(view().name("home"))//The view should have a logical name of home
                .andExpect(content().string(containsString("Welcome to..."))
                        );//希望有内容Welcome to...

    }
}
