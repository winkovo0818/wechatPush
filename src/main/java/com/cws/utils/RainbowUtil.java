package com.cws.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cws.configure.PushConfigure;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 彩虹屁接口调用
 */
public class RainbowUtil {
    public static String getRainbow() {
        String httpUrl = "http://api.tianapi.com/caihongpi/index?key=" + PushConfigure.getRainbowKey();
        BufferedReader reader = null;
        String result = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                stringBuilder.append(strRead);
                stringBuilder.append("\r\n");
            }
            reader.close();
            result = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject == null) {
            return "";
        }
        JSONArray newslist = jsonObject.getJSONArray("newslist");
        return "\n" + newslist.getJSONObject(0).getString("content");
    }

}
