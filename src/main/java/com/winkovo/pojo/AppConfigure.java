package com.winkovo.pojo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class AppConfigure {
    @Value("${wechat.wxId}")
    private String wxId;
    @Value("${wechat.agentId}")
    private String agentId;
    @Value("${wechat.secret}")
    private String secret;
    @Value("${weather.token}")
    private String token;
    @Value("${weather.longitude}")
    private String longitude;
    @Value("${weather.latitude}")
    private String latitude;
    @Value("${weather.city}")
    private String city;
    @Value("${days.birthday}")
    private String birthday;
    @Value("${days.loveDate}")
    private String anniversary;
}
