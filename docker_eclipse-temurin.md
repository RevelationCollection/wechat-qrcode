# 基于官方eclipse-temurin镜像
基于centos-7编译
## 1、创建dockerfile文件
`vim Dockefile`
## 2、保存文件
````
FROM eclipse-temurin:17.0.3_7-jre-centos7
RUN set -eux; \
    yum install -y \
    libjpeg-turbo.x86_64 \
    opencv-core.x86_64 ;
````
## 3、编译保存文件
````
docker build --tag netfriend/jdk17-opencv:1.0.0 .
````
## 4、测试是否可用
````
docker cp wechat-qrcode-1.0.0.jar ${容器ID}:/tmp
java -jar wechat-qrcode-1.0.0.jar
````