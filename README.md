# JAVA微信二维码扫描
微信二维码扫描  
opencv+wechat_qrcode  
linux 基于centos，不支持alpine，需要对应环境需自行编译相应jar和so
## 启动容器
````
docker pull netfriend/jdk17-opencv:1.0.0
docker run netfriend/jdk17-opencv:1.0.0
````
## 打包项目运行项目
```
#maven项目打包
mvn -B -U clean package -DskipTests=true
#找到jar文件
cd target
# 查看运行容器ID
docker ps 
docker cp wechat-qrcode-1.0.0.jar ${容器ID}:/tmp
#进入容器
docker exec -it 
cd /tmp
java -jar wechat-qrcode-1.0.0.jar
```
## docker镜像编译
[基于eclipse-temurin官方镜像 500m](./docker_eclipse-temurin.md)  
[自定义制作jdk+opencv镜像 1.08g](docker_custom.md)  
[alpine环境编译opencv+opencv_contrib](docker_alpine.md)

## 参考项目
[github项目-JAVA](https://github.com/jenly1314/WeChatQRCode.git)  
[alpine-opencv编译](https://www.naah69.com/post/2021-06-09-alpine-opencv/)

基于OpenCV开源的微信二维码引擎移植的封装库。又一个扫码相关的轮子，之所以说又，是因为这样的轮子已经开源三个了；几个轮子之间的优缺点，各有千秋，请自寻选择（小孩子才做选择，我全都要）。
> 基于ZXing的扫码轮子  [ZXingLite](https://github.com/jenly1314/ZXingLite)

> 基于MLKit的扫码轮子  [MLKit](https://github.com/jenly1314/MLKit)

> 基于OpenCV的扫码轮子 [WeChatQRCode](https://github.com/jenly1314/WeChatQRCode)