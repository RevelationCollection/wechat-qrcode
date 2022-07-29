# alpine环境编译opencv
## 0、拉取镜像
````
docker pull eclipse-temurin:8u332-b09-jdk-alpine
docker run eclipse-temurin:8u332-b09-jdk-alpine
````
## 1、替换apk国内镜像源
````
echo "https://mirrors.ustc.edu.cn/alpine/latest-stable/main" > /etc/apk/repositories && \
echo "https://mirrors.ustc.edu.cn/alpine/latest-stable/community" >> /etc/apk/repositories && \
apk update
````
## 2、安装下载opencv源代码、opencv_contrib
```
cd /opt && \
wget https://github.com/opencv/opencv_contrib/archive/4.5.5.tar.gz -O opencv_contrib-4.5.5.tar.gz && \
tar -zxvf opencv_contrib-4.5.5.tar.gz && \
cd /opt && \
wget https://github.com/opencv/opencv/archive/4.5.5.tar.gz -O opencv-4.5.5.tar.gz && \
tar -zxvf opencv-4.5.5.tar.gz && \
mkdir /opt/opencv-4.5.5/build && \
cd /opt/opencv-4.5.5/build && \
```
## 3、安装包可提前下载，拷贝到容器中
```
docker cp opencv_contrib-4.5.5.tar.gz 容器ID:/opt
```
## 4、可手动修改ippicv读取本地包
```
vi /opt/opencv-4.5.5/3rdparty/ippicv
wget https://raw.githubusercontent.com/opencv/opencv_3rdparty/ippicv/master_20191018/ippicv/ippicv_2020_lnx_intel64_20191018_general.tgz
vi ippicv.cmake

ippicv.cmake文件修改： https://raw.githubusercontent.com/opencv/opencv_3rdparty/${IPPICV_COMMIT}/ippicv/ 改成你的目录 file:/app/opencv-4.5.1/3rdparty/ippicv/
```
## 5、安装所需的ant编译命令
```
apk add --no-cache apache-ant python3-dev gcc g++ cmake make linux-headers
```
## 6、编译安装
### 6.1 注意java环境变量
-D BUILD_java=ON  
-D BUILD_opencv_java=ON  
-D JAVA_INCLUDE_PATH=/opt/java/openjdk/include/  
-D JAVA_AWT_INCLUDE_PATH=/opt/java/openjdk/include/  
-D JAVA_INCLUDE_PATH2=/opt/java/openjdk/include/linux/  
### 6.2 添加opencv_contrib拓展编译
-D OPENCV_EXTRA_MODULES_PATH=/opt/opencv_contrib-4.5.5/modules
### 6.3 添加wechat_qrcode编译
-D BUILD_opencv_wechat_qrcode=ON
```
cmake -D CMAKE_BUILD_TYPE=Release -D CMAKE_INSTALL_PREFIX=/usr/local -D BUILD_java=ON -D BUILD_opencv_java=ON -D JAVA_INCLUDE_PATH=/opt/java/openjdk/include/ -D JAVA_AWT_INCLUDE_PATH=/opt/java/openjdk/include/ -D JAVA_INCLUDE_PATH2=/opt/java/openjdk/include/linux/ -D OPENCV_EXTRA_MODULES_PATH=/opt/opencv_contrib-4.5.5/modules -D BUILD_opencv_wechat_qrcode=ON -D BUILD_TESTS=OFF ..
```
安装
```
make -j8
```
## 7、找到对应需要的java依赖
```
/opt/opencv-4.5.5/build/lib/libopencv_java455.so
/opt/opencv-4.5.5/build/bin/opencv-455.jar
```


# 统一安装命令
```
echo "https://mirrors.ustc.edu.cn/alpine/latest-stable/main" > /etc/apk/repositories && \
echo "https://mirrors.ustc.edu.cn/alpine/latest-stable/community" >> /etc/apk/repositories && \
apk update && \
cd /opt && \
wget https://github.com/opencv/opencv_contrib/archive/4.5.5.tar.gz -O opencv_contrib-4.5.5.tar.gz && \
tar -zxvf opencv_contrib-4.5.5.tar.gz && \
cd /opt && \
wget https://github.com/opencv/opencv/archive/4.5.5.tar.gz -O opencv-4.5.5.tar.gz && \
tar -zxvf opencv-4.5.5.tar.gz && \
mkdir /opt/opencv-4.5.5/build && \
cd /opt/opencv-4.5.5/build && \
apk add --no-cache apache-ant python3-dev gcc g++ cmake make linux-headers && \
cmake -D CMAKE_BUILD_TYPE=Release -D CMAKE_INSTALL_PREFIX=/usr/local -D BUILD_java=ON -D BUILD_opencv_java=ON -D JAVA_INCLUDE_PATH=/opt/java/openjdk/include/ -D JAVA_AWT_INCLUDE_PATH=/opt/java/openjdk/include/ -D JAVA_INCLUDE_PATH2=/opt/java/openjdk/include/linux/ -D OPENCV_EXTRA_MODULES_PATH=/opt/opencv_contrib-4.5.5/modules -D BUILD_opencv_wechat_qrcode=ON -D BUILD_TESTS=OFF .. && \
make -j8 
```