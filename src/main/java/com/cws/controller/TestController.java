package com.cws.controller;


import com.cws.utils.PushUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("test")
    public String test() {
        PushUtil.push();
        return "success";
    }
}
