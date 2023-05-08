package com.winkovo.pojo.Param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherReq {
    //token
    private String token;
    //经度
    private String longitude;
    //纬度
    private String latitude;
    //城市
    private String city;
}
