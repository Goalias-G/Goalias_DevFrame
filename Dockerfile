# 使用基础镜像
FROM openjdk:17-alpine

ENV TZ=Asia/Shanghai

# 安装一些软件包
RUN apt-get update && apt-get install -y \
    curl \
    vim

# 设置工作目录
WORKDIR /app

# 复制应用程序代码到容器
COPY . /app

# 暴露端口
EXPOSE 9999

# 定义启动命令
CMD ["./myapp"]
