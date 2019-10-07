package com.example.tacocloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller//自动扫描+DI
public class HomeController {
    @GetMapping("/")//http请求
    public String home(){
        return "home";
    }



}
