package com.zibu.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloHttp {
    @RequestMapping("/hello")
    public String Hello(){
        return "Hello Spring boot ";
    }
}
