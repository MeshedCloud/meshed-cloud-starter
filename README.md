# meshed-cloud-starter

项目通用starter管理

## 介绍

微服务快速开发流平台 [meshed-cloud](cloud.meshed.cn)

**基础依赖管理工程**

推送制品库

```shell
mvn clean install org.apache.maven.plugins:maven-deploy-plugin:2.8:deploy -DskipTests
```

## Starter 导航

### Start Starter

微服务启动项目通用Starter依赖

```xml

<dependency>
    <groupId>cn.meshed.cloud</groupId>
    <artifactId>meshed-cloud-start-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### Doc Starter

Knife4j Swagger2 文档 Starter依赖

```xml

<dependency>
    <groupId>cn.meshed.cloud</groupId>
    <artifactId>meshed-cloud-doc-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### Context Starter

微服务服务通信上下文信息管理Starter依赖

```xml

<dependency>
    <groupId>cn.meshed.cloud</groupId>
    <artifactId>meshed-cloud-context-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## 软件依赖

| 框架                                                                                              | 描述                    | 官网                                              |
|-------------------------------------------------------------------------------------------------|-----------------------|-------------------------------------------------|
| [Spring Boot](https://spring.io/projects/spring-boot) 2.6.3 [Apache-2.0]                        | 开源框架                  | https://spring.io/projects/spring-boot          |
| [Spring Cloud](https://spring.io/projects/spring-cloud) 2021.0.1 [Apache-2.0]                   | 分布式微服务架构              | https://spring.io/projects/spring-cloud         |
| [Spring Cloud Alibaba](https://spring.io/projects/spring-cloud-alibaba) 2021.0.1.0 [Apache-2.0] | 微服务阿里方案               | https://spring.io/projects/spring-cloud-alibaba |
| [Cola](https://github.com/alibaba/COLA) 4.3.1 [LGPL-2.1]                                        | 应用架构 (DDD 领域驱动设计实现方案) | https://github.com/alibaba/COLA                 |
| [Flowable](https://github.com/flowable/flowable-engine) 6.7.2 [Apache-2.0]                      | 流程引擎                  | https://github.com/flowable/flowable-engine     |
| [xflow](https://github.com/antvis/xflow/) 1.0.0 [MIT]                                           | 流程设计器                 | https://xflow.antv.vision/zh-CN                 |
| [Formily](https://formilyjs.org/) v2.2.22 [MIT]                                                 | 表单设计器                 | https://formilyjs.org/                          |
| [antd design pro](https://pro.ant.design/zh-CN/) 3.0.2 [MIT]                                    | 中台前端                  | https://pro.ant.design/zh-CN/                   |



