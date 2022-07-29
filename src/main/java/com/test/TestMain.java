package com.test;

import com.test.wechat.JarFileUtils;
import com.test.wechat.OpenCV;
import com.test.wechat.WeChatQRCodeDetector;

import java.io.File;
import java.util.List;

public class TestMain {

    public static void main(String[] args) {
        try {
            //初始化 OpenCV
            OpenCV.init();
            //初始化 WeChatQRCode
            WeChatQRCodeDetector.init();
            //检测并识别二维码 （同一张图片可能有多个二维码）
            File image = JarFileUtils.extractLibrary("image", "4567.jpg");
            //图片本地路径
            List<String> results = WeChatQRCodeDetector.detectAndDecode(image.getAbsoluteFile().getPath());
            System.out.println("results:" + results);
            if (JarFileUtils.isJarFile){
                //noinspection ResultOfMethodCallIgnored
                image.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
