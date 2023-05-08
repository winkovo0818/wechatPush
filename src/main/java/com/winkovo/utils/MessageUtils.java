package com.winkovo.utils;

import com.alibaba.fastjson2.JSONObject;
import com.winkovo.pojo.Param.DaysReq;
import com.winkovo.pojo.Vo.WeatherIcon;
import com.winkovo.pojo.Param.WeatherReq;
import com.winkovo.pojo.Param.WxReq;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
public class MessageUtils {
    /**
     * 发送http请求
     */
    public static JSONObject HttpConnect(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();
        return JSONObject.parseObject(content.toString());
    }

    /**
     * 获取天气
     */
    public static String getWeather(WeatherReq request) {
        String url = String.format("https://api.caiyunapp.com/v2.6/%s/%s,%s/daily?dailysteps=1", request.getToken(), request.getLongitude(), request.getLatitude());
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                JSONObject res = HttpConnect(connection);
                JSONObject temperatureInfo = res.getJSONObject("result").getJSONArray("daily").getJSONObject(0).getJSONArray("temperature").getJSONObject(0);
                String weatherCode = res.getJSONObject("result").getJSONArray("daily").getJSONObject(0).getJSONArray("skycon").getJSONObject(0).getString("value");
                log.info("天气代码: {}", weatherCode);
                String Icon = WeatherIcon.valueOf(weatherCode).getIcon();
                String weather = WeatherIcon.valueOf(weatherCode).getDesc();
                double max = temperatureInfo.getDouble("max");
                double min = temperatureInfo.getDouble("min");
                log.info("获取天气成功, 天气: {}, 最高温度: {}, 最低温度: {}", weather, max, min);
                return String.format("%s: %s %s %.1f~%.1f"+"℃", request.getCity(),Icon, weather, min, max);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.error("获取天气失败");
        return null;
    }

    /**
     * 获取必应每日一图
     */
    public static String getBingPic() {
        String url = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                JSONObject res = HttpConnect(connection);
                String picUrl = "https://cn.bing.com" + res.getJSONArray("images").getJSONObject(0).getString("url");
                log.info("获取必应每日一图成功, 图片地址: {}", picUrl);
                return picUrl;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.error("获取必应每日一图失败");
        return null;
    }
    public static String get_sentence() {
        String sentence = "";
        String host = "https://zj.v.api.aa1.cn/api/wenan-zl/?type=json";
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(host).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                JSONObject res = HttpConnect(connection);
                sentence = res.getString("msg");
                log.info("获取每日一句成功, 句子: {}", sentence);
                return sentence;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.error("获取每日一句失败");
        return sentence;
    }
    public static List<String> get_today() {
        List<String> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date());
        String greeting = "";
        String nickname = "宝宝";
        String greeting_tip = "";
        //获取星期几
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        format = format + " " + weekDays[w];

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String nowTime = now.format(formatter);
        //如果当前时间出在 0-6点之间，就返回凌晨
        if (Integer.parseInt(nowTime.split(":")[0]) >= 0 && Integer.parseInt(nowTime.split(":")[0]) < 6) {
            greeting = "凌晨好吖";
        }
        //如果当前时间出在 6-12点之间，就返回早上
        if (Integer.parseInt(nowTime.split(":")[0]) >= 6 && Integer.parseInt(nowTime.split(":")[0]) < 12) {
            greeting = "早上好吖";
        }
        //如果当前时间出在 12-18点之间，就返回下午
        if (Integer.parseInt(nowTime.split(":")[0]) >= 12 && Integer.parseInt(nowTime.split(":")[0]) < 18) {
            greeting = "下午好吖";
        }
        //如果当前时间出在 18-24点之间，就返回晚上
        if (Integer.parseInt(nowTime.split(":")[0]) >= 18 && Integer.parseInt(nowTime.split(":")[0]) < 24) {
            greeting = "晚上好吖";
        }
        greeting_tip = nickname + greeting + " " + EmotionUtils.getEmotion();
        result.add(format);
        result.add(greeting_tip);
        return result;
    }

    public static List<String> handleDate(String loveDate, String birthday) {
        int remain_day;
        List<String> remain_tip = new ArrayList<>();
        //计算恋爱的天数
        long l = DaysUtils.calculationLianAi(loveDate);
        //计算距离生日的天数
        long l1 = DaysUtils.calculationBirthday(birthday);
        //判断是否为纪念日
        boolean memorialDay = DaysUtils.isMemorialDay(loveDate);
        if (memorialDay) {
            remain_tip.add("\uD83D\uDCE2 纪念日就是今天啦!");
        }
        log.info("我们在一起已经{}天", Math.abs(l));
        //如果纪念日已经过了
        remain_day = (int) l;
        remain_tip.add("\uD83D\uDCE2 我们在一起已经" + Math.abs(remain_day) + "天啦!");
        remain_tip.add("\uD83D\uDCE2 距离宝宝的生日还有" + l1 + "天啦!");
        return remain_tip;
    }

    /**
     * 构建文本
     */
    public static String makeText(WeatherReq request, DaysReq daysReq){
        StringBuilder text = new StringBuilder();
        String sentence = get_sentence();
        if (!sentence.isEmpty()){
            text.append("\n").append(sentence).append("\n");
            log.info("信息文本【句子】部分构建成功");
        }
        String weather = getWeather(request);
        if (weather != null){
            text.append("\n").append(weather).append("\n");
            log.info("信息文本【天气】部分构建成功");
        }
        //获取纪念日
        List<String> strings = handleDate(daysReq.getAnniversary(), daysReq.getBirthday());
        for (String s : strings) {
            text.append("\n").append(s).append("\n");
        }
        log.info("信息文本【纪念日】部分构建成功");
        return text.toString();
    }

    /**
     * 构建消息
     */
    public static HashMap<String,Object> create(String agentid,WeatherReq request, DaysReq daysReq){
        List<String> today = get_today();
        String greeting_tip = today.get(0);
        String format_date = today.get(1);
        String strings = makeText(request, daysReq);
        String bingPic = getBingPic();
        HashMap<String,Object> article = new HashMap<>();
        article.put("title",greeting_tip+"\n"+format_date);
        article.put("description",strings);
        article.put("url",bingPic);
        article.put("picurl",bingPic);
        HashMap<String,Object> msg = new HashMap<>();
        HashMap<String,Object> articles = new HashMap<>();
        articles.put("articles",article);
        msg.put("touser","@all");
        msg.put("toparty","");
        msg.put("totag","");
        msg.put("msgtype","news");
        msg.put("agentid",agentid);
        msg.put("news",articles);
        msg.put("enable_id_trans",0);
        msg.put("enable_duplicate_check",0);
        msg.put("duplicate_check_interval",1800);
        return msg;
    }

    /**
     * 获取access_token
     */
    public static String token(WxReq req) {
        if (req.getWxId() == null || req.getSecret() == null) {
            throw new RuntimeException("请填写企业微信id和secret");
        }
        int i = 1;
        while (i < 3) {
            String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + req.getWxId() + "&corpsecret=" + req.getSecret();
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    JSONObject res = HttpConnect(connection);
                    String token = res.getString("access_token");
                    log.info("获取access_token成功, token: {}", token);
                    return token;
                }
            } catch (IOException e) {
                log.error("获取access_token失败, 正在重试..., 第{}次", i);
                i++;
            }
        }
        return null;
    }
    /**
     * 发送消息
     */
    public static void sendMessage(WxReq req,String agentid,WeatherReq request, DaysReq daysReq){
        String token = token(req);
        if (token == null) {
            log.error("获取access_token失败");
            throw new RuntimeException("获取access_token失败");
        }
        HashMap<String, Object> data = create(agentid, request, daysReq);
        String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + token;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.getOutputStream().write(JSONObject.toJSONString(data).getBytes());
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                log.info("发送消息成功");
            }
        } catch (IOException e) {
            log.error("发送消息失败");
            throw new RuntimeException(e);
        }
    }
}
