## author: Goalias-G

### 项目地址：https://github.com/Goalias-G/Goalias_DevFrame

### 介绍

Goalias_DevFrame 是一个用于快速构建和开发Spring Boot应用的高效脚手架，配备完整的aop接口日志、开发工具、业务框架、限流降级器（GoaliasLimiter）、多服务配置与示例... 更多用法等你探索！


### 使用

使用git clone 保存本项目到本地，根据你的喜好配置application.yml,包括且不限于mysql、redis、mp、minio、canal、jwt...

限流器（GoaliasLimiter）的使用请移步 -> https://github.com/Goalias-G/GoaliasLimiter

next -> 快速开发你领先在起跑线的项目!

### 示例关键配置

    goalias:
        jwt:
            userSecretKey: 
            userTtl: 
            userTokenName: 
        alioss:
            endpoint: 
            accessKeyId: 
            accessKeySecret: 
            bucketName: 
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
        log: on
Canal主要配置示例： canal.properties

### 🎉🎉🎉 <span style="color: orange;">代码写得好，Bug跑不了；脚手架用得好，效率少不了！</span>🚀✨😄👍💻🚀

