(JAVA)微信公众号推送早安问候以及天气预报

教程
1.注册微信测试账号，编辑模板
https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login
把appId、secret都配置到application.yml
扫码关注得到用户的id,配置到application.yml的userId

编辑模板:
{{date.DATA}} {{remark.DATA}}
{{city.DATA}}的天气：{{weather.DATA}}
最低气温：{{low.DATA}}度
最高气温：{{high.DATA}}度
今天是我们恋爱的第{{loveDays.DATA}}天
距离宝宝的生日还有{{birthdays.DATA}}天{{rainbow.DATA}}


模板ID
配置到application.yml的templateId

2.百度地图开放平台
https://lbsyun.baidu.com/apiconsole/center#/home
天气服务接口文档
https://lbs.baidu.com/index.php?title=webapi/weather
创建应用
https://lbsyun.baidu.com/apiconsole/key#/home

应用AK配置到application.yml的ak
要查询的城市ID配置到application.yml的district_id
remark:城市对应ID可在https://lbs.baidu.com/index.php?title=webapi/weather的服务文档的请求参数的district_id一栏下载 行政区划编码 查看

3.彩虹屁平台
https://www.tianapi.com/
apiKey配置到application.yml的rainbowKey

remark:需要在天行数据注册账号申请该接口


结果展示(src/main/resources/img/1.png)


4.如何运行
启动项目后打开浏览器输入 localhost/test 即可手动调用
定时任务默认每天早8点推送,如需修改可去Task类上修改cron表达式


