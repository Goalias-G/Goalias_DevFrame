![logo.png](doc/logo.png)

## author: Goalias-G


![Java-17](https://img.shields.io/badge/Java-17.0.9-blue)![SpringBoot-2.7.8](https://img.shields.io/badge/SpringBoot-2.7.8-green)![GoaliasLimiter-1.0.3](https://img.shields.io/badge/GoaliasLimiter-1.0.3.RELEASE-purple)

![MybatisPlus-3.5.4](https://img.shields.io/badge/MybatisPlus-3.5.4.RELEASE-orange)![Canal-1.1.5](https://img.shields.io/badge/Canal-1.1.5.RELEASE-orange)![Minio-8.5.11](https://img.shields.io/badge/Minio-8.5.11.RELEASE-orange)![Sentinel-1.8.6](https://img.shields.io/badge/Sentinel-1.8.6.RELEASE-orange)

### 项目地址：https://github.com/Goalias-G/Goalias_DevFrame

### 介绍

Goalias_DevFrame 是一个用于快速构建和开发Spring Boot应用的多位一体解决方案，配备完整的aop接口日志、开发工具、业务框架、限流降级器（GoaliasLimiter）、多服务配置与示例... 更多用法等你探索！


### 使用

使用git clone 保存本项目到本地，根据你的喜好配置application.yml,包括且不限于mysql、redis、mp、minio、canal、docker...

限流器（GoaliasLimiter）的使用请移步 -> https://github.com/Goalias-G/GoaliasLimiter

next -> 快速开发你领先在起跑线的项目!

### 示例关键配置

    goalias:
        jwt:
            userSecretKey: 
            userTtl: 
            userTokenName: 
    #aliyunOSS配置
        alioss:
            endpoint: 
            accessKeyId: 
            accessKeySecret: 
            bucketName: 
    #aliyun短信服务配置
        aliyun:
            accessKeyId: 
            accessKeySecret: 
            endpoint: 
        minio:
            endpoint: 
            accessKey: 
            secretKey: 
        canal:
            port: 
            destination: 
            update: 
            insert: 
            delete: 
            schema: 
            user-table: 
        log:
            isOpen: true
canal主要配置示例： canal.properties

docker 部署示例： Dockerfile 、 docker-compose.yml

成功启动示例：
![img.png](doc/img.png)


### 🎉🎉🎉 <span style="color: orange;">代码写得好，Bug跑不了；脚手架用得好，效率少不了！</span>🚀✨😄👍💻🚀

