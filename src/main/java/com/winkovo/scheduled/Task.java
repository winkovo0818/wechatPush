package com.winkovo.scheduled;

import com.winkovo.pojo.AppConfigure;
import com.winkovo.pojo.Param.DaysReq;
import com.winkovo.pojo.Param.WeatherReq;
import com.winkovo.pojo.Param.WxReq;
import com.winkovo.utils.MessageUtils;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@EnableScheduling
@Configuration
@Slf4j
public class Task {
    @Resource
    private AppConfigure appConfigure;
    // 定时任务
    @Scheduled(cron = "${task.corntab}")
    public void goodMorning() {
        log.info("开始自动发送早安微信");
        try {
            run(appConfigure);
        } catch (Exception e) {
            log.error("发送失败", e);
        }
    }

    public static void run(AppConfigure appConfigure) {
        WxReq wxReq = new WxReq(appConfigure.getWxId(), appConfigure.getSecret());
        WeatherReq weatherReq = new WeatherReq(appConfigure.getToken(), appConfigure.getLongitude(), appConfigure.getLatitude(), appConfigure.getCity());
        DaysReq daysReq = new DaysReq(appConfigure.getBirthday(), appConfigure.getAnniversary());
        MessageUtils.sendMessage(wxReq, appConfigure.getAgentId(),weatherReq,daysReq);
        log.info("发送成功");
    }
}
