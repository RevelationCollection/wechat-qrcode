# 自定义安装

## 1、创建dockerfile文件
`vim Dockerfile`
## 2、保存命令
````
FROM centos:centos7.9.2009
RUN set -eux; \
    yum install -y \
        epel-release ;
RUN set -eux; \
    yum install -y \
        java-latest-openjdk.x86_64  ;
RUN /bin/sh -c echo Verifying install ...     && echo javac --version && javac --version     && echo java --version && java --version     && echo Complete.
RUN set -eux; \
    yum install -y \
        opencv-core.x86_64;
````

## 3、制作镜像
````
docker build --tag netfriend/jdk18-opencv:1.0.0 .
````
## 4、测试
````
//查看容器ID
docker ps -a
//将本地目录 复制到容器的路径中  容器ID:容器路径
docker cp demo-1.0.0.jar ba064c447513:/tmp
java -jar wechat-qrcode-1.0.0.jar
````

## 参考
[Java版人脸检测详解上篇：运行环境的Docker镜像(CentOS+JDK+OpenCV)
](https://blog.csdn.net/javadada1197/article/details/119414212)  
[制作JavaCV应用依赖的基础Docker镜像(CentOS7+JDK8+OpenCV4)
](https://blog.csdn.net/boling_cavalry/article/details/120926346)