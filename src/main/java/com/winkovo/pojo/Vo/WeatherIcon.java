package com.winkovo.pojo.Vo;


import lombok.Getter;

@Getter
public enum WeatherIcon {
    SUNNY("晴（白天）", "☀"),
    CLEAR_NIGHT("晴（夜间）", "☽"),
    PARTLY_CLOUDY_DAY("多云（白天）", "☁"),
    PARTLY_CLOUDY_NIGHT("多云（夜间）", "☁"),
    CLOUDY("阴", "☁"),
    LIGHT_HAZE("轻度雾霾", "🌫"),
    MODERATE_HAZE("中度雾霾", "🌫"),
    HEAVY_HAZE("重度雾霾", "🌫"),
    LIGHT_RAIN("小雨", "🌧"),
    MODERATE_RAIN("中雨", "🌧"),
    HEAVY_RAIN("大雨", "🌧"),
    STORM_RAIN("暴雨", "\uD83D\uDCA6"),
    Fog("雾", "🌫"),
    LIGHT_SNOW("小雪", "❄"),
    MODERATE_SNOW("中雪", "❄"),
    HEAVY_SNOW("大雪", "☃"),
    STORM_SNOW("暴雪", "☃"),
    DUST("浮尘", "🌪"),
    SAND("沙尘", "🌪"),
    WIND("大风", "🌪"),
    ;
    private String Desc;
    private String Icon;
    WeatherIcon(String Desc, String Icon) {
        this.Desc = Desc;
        this.Icon = Icon;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

}
