# 本项目基于开源项目的二次开发


(JAVA)微信公众号推送早安问候以及天气

![image](src/main/resources/img/1.png)

教程:
**1.注册微信测试账号，编辑模板**
https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login
把appId、secret都配置到application.yml。
扫码关注得到用户的id,配置到application.yml的userId。

编辑模板:
{{date.DATA}} {{remark.DATA}}
{{city.DATA}}的天气：{{weather.DATA}}
最低气温：{{low.DATA}}度
最高气温：{{high.DATA}}度
今天是我们恋爱的第{{loveDays.DATA}}天
距离宝宝的生日还有{{birthdays.DATA}}天{{rainbow.DATA}}


模板ID配置到application.yml的templateId

**2.百度地图开放平台:**
**https://lbsyun.baidu.com/apiconsole/center#/home**
**天气服务接口文档:**
**https://lbs.baidu.com/index.php?title=webapi/weather**
**创建应用:选择服务端**
**https://lbsyun.baidu.com/apiconsole/key#/home**

应用AK配置到application.yml的ak。
要查询的城市ID配置到application.yml的district_id。
备注:城市对应ID可在https://lbs.baidu.com/index.php?title=webapi/weather 的服务文档的请求参数的district_id一栏下载 行政区划编码 查看

**3.彩虹屁平台**
https://www.tianapi.com/
apiKey配置到application.yml的rainbowKey

备注:需要在天行数据注册账号申请该接口

**4.如何运行?**

只需修改application.yml里的配置即可运行,代码其他部分无需任何修改。

![image](src/main/resources/img/2.png)

启动项目后打开浏览器输入  localhost/test  即可手动调用。
定时任务默认每天早8点推送,如需修改可去Task类上修改cron表达式。

![image](src/main/resources/img/3.png)

###### `有问题欢迎留言或者私信`

# 常见问题



**1.推送失败：{"errcode":40003,"errmsg":"invalid openid rid: 630576d8-1139d71c-6d68a976"}**

这个是由于userId填的不对

 ![img](https://foruda.gitee.com/images/1661302607866807889/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE.png) 

改成下面红框里的重启程序即可

 ![å¡«è¿ä¸ª](https://foruda.gitee.com/images/1661302640325984367/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE.png) 



# 代码优化

关于大家遇到的空指针问题都做了优化处理

现在异常情况会反馈到界面上,你们可以根据报错信息查找原因,而不是面对NullPointerException

![1661309025827](src/main/resources/img/4.png)