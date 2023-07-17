package com.sa.mybatisplus.controller;

import com.sa.mybatisplus.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @PostMapping("/post")
    public String postTest(String sa, @RequestBody User user) {
        System.out.println(user);
        return "postMapping";
    }

    @GetMapping("/get")
    public String getTest() {
        return "getMapping";
    }
}
