# tikie-eureka-zuul
spring cloud全家桶，各个组件整合使用之路由

## eureka 服务网关：路由
    
    通过服务网关统一向系统外提供REST API的过程中，除了具备服务路由、均衡负载功能之外，它还具备了权限控制等功能。
    用于构建边界服务，致力于动态路由、过滤、监控、弹性伸缩和安全。
    
![服务网关：路由](https://images2018.cnblogs.com/blog/1375263/201806/1375263-20180627112137402-1914185732.png)

### 参考网站
 + 相关学习博客，请查看[相关学习文档](http://www.cnblogs.com/cralor/p/9234697.html "spring boot 2.0.3+spring cloud （Finchley）5、服务网关：路由")
 + 如有任何问题欢迎联系作者：tikie
 
        qq:290315636
    
### 项目环境
 - Eclipse：Oxygen.3a Release (4.7.3a)
 
        彩色日志插件：http://www.mihai-nita.net/eclipse
 - jdk:1.8+
 - spring boot: 2.0.3.RELEASE
 - spring-cloud: Finchley.RELEASE

### 初始化操作
 1. NEW
 2. New Spring Starter Project
 3. Cloud Routing
 4. Zuul
 
 + 运行服务提供者：TikieEurekaZuulClientApplication.java
    
        Run As
        Run Java Application或Spring Boot App
 + 或命令行启动方式：
 
        java -jar tikie-eureka-zuul-0.0.1-SNAPSHOT.jar.jar
 + 注册中心页面主节点：http://peer1:8761
 + 注册中心页面副节点：http://peer2:8761/
 + 需要根据启动的消费者修改application.yml配置（app-config）

 + *本项目的默认只提供dev分支的更新权限*
 
 + 设置默认push/pull行为(push当前分支到远程同名分支，如果远程同名分支不存在则自动创建同名分支)
    
       git config push.default "current"
       git config pull.default "current"
       
       #在对应的分支上执行：如dev分支
       git branch --set-upstream-to=origin/dev
 
### 实现功能点
    1. 接口版本(目前版本v1)
    2. 访问token(目前必须加token参数，之后可以加上token验证):示例：http://localhost:5000/v1/hiapi/hi?name=cralor&token
    3. 熔断器
        Zuul作为Netflix组件，可以与Ribbon、Eureka和Hystrix等组件相结合，实现负载均衡、熔断器的功能。默认情况下Zuul和Ribbon相结合，实现了负载均衡。
        实现熔断器功能需要实现FallbackProvider接口，实现该接口的两个方法，一个是getRoute()，用于指定熔断器功能应用于哪些路由的服务；
        另一个方法fallbackResponse()为进入熔断器功能时执行的逻辑。
    4. 过滤器
        可以在 application.yml 中配置需要禁用的 filter，格式为zuul.<SimpleClassName>.<filterType>.disable=true
        比如要禁用org.springframework.cloud.netflix.zuul.filters.post.SendResponseFilter,就设置:
            zuul:
              SendResponseFilter:
                post:
                  disable: true
        实现自定义滤器需要继承ZuulFilter，实现ZuulFilter中的抽象方法,可参考MyFilter
    
### 其他相关项目
    1. eureka注册中心 > https://github.com/290315636/tikie-eureka-registry-center
    2. eureka服务提供者(可以有多个实例) > https://github.com/290315636/tikie-eureka-provider
    3. eureka服务消费者 > https://github.com/290315636/tikie-eureka-ribbon-consumer
    4. feign 服务消费者 > https://github.com/290315636/tikie-eureka-feign-consumer
    5. monitor断路器监控器 > https://github.com/290315636/tikie-eureka-monitor-client
    6. zuul路由（断路、过滤）控制器 > https://github.com/290315636/tikie-eureka-zuul
    注. 3、4可以选中其一,优先使用feign;5可以不用启动（使用时启动）
### 历史更新

    1.0.1 更新说明文档
    1.0.0 初始化服务提供者