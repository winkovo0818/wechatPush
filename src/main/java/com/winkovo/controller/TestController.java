package com.winkovo.controller;

import com.winkovo.pojo.AppConfigure;
import com.winkovo.pojo.Param.DaysReq;
import com.winkovo.pojo.Param.WeatherReq;
import com.winkovo.pojo.Param.WxReq;
import com.winkovo.scheduled.Task;
import com.winkovo.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试手动推送
 */
@RestController
@Slf4j
public class TestController {
    @Resource
    private AppConfigure appConfigure;
    @GetMapping("test")
    public String test() {
        log.info("开始手动发送早安微信");
        Task.run(appConfigure);
        return "发送成功";
    }
}
