<div align="center">
  <a href="#">
    <img src="wc-preview/logo_black128.png">
  </a>  
</div>

<br>

<div align="center">

![](https://img.shields.io/badge/release-v1.0.0-blue.svg)
![](https://img.shields.io/badge/build-passing-orange.svg)
![](https://img.shields.io/badge/license-Apache--2.0-brighhtgreen.svg)
[![](https://img.shields.io/badge/wiki-reference-inactive.svg)](https://github.com/ash-ali/wingcloud/wiki)

</div>

---
## Introductions
wingcloud 是基于微服务架构的实时计算展示平台。作为实时计算平台，wingcloud 又采用 Flink 流计算处理框架来实现高吞吐、低延迟的性能要求。
wingcloud-web：https://github.com/ash-ali/wingcloud-web



## Features
- 支持流计算实时处理。
- 支持独立开发、独立部署服务。
- 支持文档在线自动生成。
- 支持数据大屏的报表通过后台实时切换或更改样式。
- 支持单点登录。
- 支持数据大屏实时动态展示
- 支持监控服务/运维数据



## Architecture

#### 1、wingcloud 微服务架构图
![image](https://github.com/ash-ali/wingcloud-web/blob/master/images/allarc.jpg)

#### 2、wingcloud 计算服务架构图
![image](https://github.com/ash-ali/wingcloud-web/blob/master/images/calarc.jpg)



## Preview

<table><tr>
    <td><img src="https://github.com/ash-ali/wingcloud-web/blob/master/images/preview1.png" border=0 width="300px" height="300px"></td>
    <td><img src="https://github.com/ash-ali/wingcloud-web/blob/master/images/preview2.png" border=0 width="300px" height="300px"></td>
    <td><img src="https://github.com/ash-ali/wingcloud-web/blob/master/images/preview3.png" border=0 width="300px" height="300px"></td>
</tr></table>

<table><tr>
    <td><img src="https://github.com/ash-ali/wingcloud-web/blob/master/images/preview4.png" border=0 width="300px" height="300px"></td>
    <td><img src="https://github.com/ash-ali/wingcloud-web/blob/master/images/preview5.png" border=0 width="300px" height="300px"></td>
    <td><img src="https://github.com/ash-ali/wingcloud-web/blob/master/images/preview6.png" border=0 width="300px" height="300px"></td>
</tr></table>


## Module Introductions
- wc-client 业务服务模块都在wc-client中，主要为了提供业务模块的公共依赖。
- wc-client-calculation 业务服务模块之计算模块，基于 Flink 流计算框架，kafka、Redis作为主要Source源，MySQL、kafka作为主要sink源。
- wc-client-user 业务服务模块之用户模块，主要基于Redis、MySQL实现单点登录。
- wc-common 公共服务模块，主要提供Redis、cookie、信息加密、数据请求/响应的工具类。
- wc-config 配置中心服务模块，对所有服务集中式配置，在config目录下。
- wc-gateway 网关服务模块，主要通过zuul实现网关服务，其中含有鉴权、路由转发、负载均衡、swagger api生成。
- wc-register 服务注册中心模块，将微服务注册到Eureka上进行服务治理。


## Usage
1.获取源码
```git
$ git clone https://github.com/ash-ali/wingcloud.git
$ git clone https://github.com/ash-ali/wingcloud-web.git
```

2.构建计算服务环境

- centos7
- flink-1.6.1-bin-hadoop26-scala_2.11
- zookeeper-3.4.5
- kafka_2.11-0.11.0.1
- hadoop-2.6.0
- jdk-8u201-linux-x64

3.提交任务到Flink web

```text
提交：wingcloud/job/wc-client-calculation-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

4.启动服务
```text
wc-register -> wc-config -> wc-client-user -> wc-client-reporting ->wc-gateway
```

5.运行wingcloud-web
```text
Run: wingcloud-web/login.html
user: xlj
password: 123
```


## Documentation
[wingcloud wiki](https://github.com/ash-ali/wingcloud/wiki)
 

 
## Todolist

- 完善 wingcloud wiki
- 使用Flink SQL 实现批流统一
- 前端重构
- 实现搜索引擎(ES)服务，多平台信息搜索


## Self Description
- 创作不易，欢迎star
- 欢迎发Issues或者发邮件(xulinjie0105@gmail.com)
- ash-ali(linjie)，2019年在校大三学生
- 个人blog：https://www.imlinjie.top/
- csdn：https://blog.csdn.net/w_linux


## License
wingcloud is licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file.



