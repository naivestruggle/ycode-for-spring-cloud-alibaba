# ycode-for-spring-cloud-alibaba

使用SpringCloudAlibaba实现微服务架构思想设计与开发的技术博客网站



# 1.目录结构：

+ ycode-admin：后台管理员端的前端页面。基于Vue 等技术整合Element-UI等模板 开发。
+ ycode-pc：用户端的前端页面。基于Vue 等技术整合Element-UI等模板 开发。
+ ycode-server：后端所有的服务。使用SpringCloudAlibaba 生态技术组合实现微服务架构思想，设计及开发了提供Restful风格API的服务接口。



# 2.项目整体架构图（简易版）

![image-20200208104324200](readme\项目整体架构图.png)

# 3.开发环境

+ 操作系统：Windows 10 
+ 开发工具：Intellij IDEA、WebStorm
+ 数据库：MySQL 8.0.13
+ JAVA SDK: Oracle JDK 1.8.0_65



# 4.部署环境

+ 操作系统：CentOS-7-x86_64-DVD-1810
+ 虚拟化技术：VMware、Docker



# 5.项目管理工具

+ 项目构建：Maven、Nexus
+ 代码管理：Git、GitLab
+ 镜像管理：Docker、Docker Registry



# 6.后端主要技术栈

+ 核心框架：Spring Boot + Spring Cloud Alibaba
+ ORM框架：mybatis、tk-mybatis
+ 数据库连接池：Alibaba Druid
+ 数据库缓存：Redis Sentinel
+ 消息中间件：RocketMQ
+ 接口文档引擎：Swagger2
+ 全文检索引擎：ElasticSearch
+ 分布式链路追踪：SkyWalking
+ 分布式文件系统：FastDFS
+ 分布式系统网关：Spring Cloud Gateway
+ 分布式协调系统：Spring Cloud Alibaba Nacos Server
+ 分布式配置中心：Spring Cloud Alibaba Nacos Config
+ 分布式熔断降级：Spring Cloud Alibaba Sentinel
+ 反向代理负载均衡：Nginx



# 7.前后端分离

+ 前端框架：Node.js + Vue + Axios
+ 前端模板：ElementUI



# 8.持续集成

+ 持续集成：GitLab、GitLab-runner
+ 持续交付：Jenkins



# 9.服务规划

## 9.1.服务平台：

| 服务名称               | 服务地址            | 服务说明                                       |
| :--------------------- | :------------------ | :--------------------------------------------- |
| GitLab                 | 192.168.31.10:80    | 代码托管                                       |
| Nexus                  | 192.168.31.10:8081  | maven私服                                      |
| Docker-Registry        | 192.168.31.10:5000  | Docker镜像仓库服务                             |
| Docker-Registry-Client | 192.168.31.10:8082  | Docker镜像仓库管理界面                         |
| MySQL                  | 192.168.31.10:3306  | 数据库，因为硬件不足，<br/>用一个MySQL，多个库 |
| Nginx                  | 192.168.31.10:40001 | 静态资源服务器                                 |
| nacos-docker           | 192.168.31.13:8848  | 服务注册与发现、<br />分布式配置中心           |
| ElasticSearch          | 192.168.31.13:9200  | 分布式搜索框架                                 |
| Skywalking             | 192.168.31.13:8080  | 分布式服务链路追踪                             |
| Sentinel               | 192.168.31.13:40000 | 熔断器仪表盘                                   |
| RocketMQ               | 192.168.31.14:9876  | 消息中间件                                     |
| fastDFS                | 192.168.31.15:8888  | 分布式文件系统                                 |



## 9.2.redis-HA

| 服务名称         | 服务地址            | 服务说明 |
| ---------------- | ------------------- | -------- |
| redis-master     | 192.168.31.12:6379  | 主       |
| redis-slave-1    | 192.168.31.12:6380  | 从1      |
| redis-slave-2    | 192.168.31.12:6381  | 从2      |
| redis-sentinel-1 | 192.168.31.12:26379 | 哨兵1    |
| redis-sentinel-2 | 192.168.31.12:26380 | 哨兵2    |
| redis-sentinel-3 | 192.168.31.12:26381 | 哨兵3    |



## 9.3.应用服务

### 9.3.1.通用服务(common)

| 服务名称              | 服务地址            | Sentinel 端口 | 服务角色 | 服务说明                                                     |
| --------------------- | ------------------- | ------------- | -------- | ------------------------------------------------------------ |
| ycode-common-gateway  | 192.168.31.13:41000 |               | 通用     | API 网关，聚合服务，<br />对外提供统一的访问地址             |
| ycode-common-email    | 192.168.31.13:41001 |               | 通用     | 邮箱服务，依赖于消息<br />中间件异步发送邮件                 |
| ycode-common-redis    | 192.168.31.13:41002 |               | 通用     | redis服务，提供操作<br />redis集群的功能接口                 |
| ycode-common-fastdfs  | 192.168.31.13:41003 |               | 通用     | 文件系统服务，提供<br />操作分布式文件系统的功能接口         |
| ycode-common-datetime | 192.168.31.13:41000 |               | 通用     | 时间服务，统一整个<br />架构中的时间，提供获取当前时间戳的功能接口 |



### 9.3.2.服务提供者(service)

| 服务名称                      | 服务地址            | Sentinel 端口 | 服务角色 | 服务说明               |
| ----------------------------- | ------------------- | ------------- | -------- | ---------------------- |
| ycode-service-user-regist     | 192.168.31.13:42000 |               | 提供者   | 用户注册服务           |
| ycode-service-slideshow-admin | 192.168.31.13:42001 |               | 提供者   | 管理员端的轮播图服务   |
| ycode-service-slideshow-user  | 192.168.31.13:42002 |               | 提供者   | 用户端的轮播图服务     |
| ycode-service-sso-admin       | 192.168.31.13:42003 |               | 提供者   | 管理员端的单点登录服务 |



### 9.3.3.服务消费者(web)

| 服务名称                  | 服务地址            | Sentinel 端口 | 服务角色 | 服务说明               |
| ------------------------- | ------------------- | ------------- | -------- | ---------------------- |
| ycode-web-user-regist     | 192.168.31.13:43000 | 44000         | 消费者   | 用户注册服务           |
| ycode-web-slideshow-admin | 192.168.31.13:43001 | 44001         | 消费者   | 管理员端的轮播图服务   |
| ycode-web-slideshow-user  | 192.168.31.13:43002 | 44002         | 消费者   | 用户端的轮播图服务     |
| ycode-web-sso-admin       | 192.168.31.13:43003 | 44003         | 消费者   | 管理员端的单点登录服务 |



## 9.4.前端部署(node.js)

| 服务名称    | 服务地址           | 服务说明       |
| ----------- | ------------------ | -------------- |
| ycode-admin | 192.168.31.16:8081 | 后台管理员界面 |
| ycode-pc    | 192.168.31.16:8082 | 用户界面       |

