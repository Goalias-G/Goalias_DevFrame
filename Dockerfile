# 使用基础镜像
FROM openjdk:17-alpine

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 设置工作目录
WORKDIR /app

# 复制应用程序代码到容器
COPY target/goaliasApp.jar app.jar

# 暴露端口
EXPOSE 9999

# 定义启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]
