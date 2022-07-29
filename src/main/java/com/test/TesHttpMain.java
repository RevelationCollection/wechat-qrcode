package com.test;

import com.test.wechat.OpenCV;
import com.test.wechat.WeChatQRCodeDetector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class TesHttpMain {
    public static void main(String[] args) {
        File file = null;
        try {
            //初始化 OpenCV
            OpenCV.init();
            //初始化 WeChatQRCode
            WeChatQRCodeDetector.init();
            //检测并识别二维码 （同一张图片可能有多个二维码）
            String url = "https://gw.alicdn.com/tfs/TB1wS1KGXY7gK0jSZKzXXaikpXa-1189-1189.png";
            //临时下载路径，windows手动修改
            String fileName = downloadFile(url);
            file = new File(fileName);
            if (!file.exists()){
                System.err.println("文件不存在");
                return;
            }
            List<String> results = WeChatQRCodeDetector.detectAndDecode(fileName);
            System.out.println("results:" + results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (file!=null && file.delete()){
            System.out.println("文件删除成功");
        }
    }

    public static String downloadFile(String httpUrl) {
        InputStream is = null;
        String fileName = "/tmp/"+System.currentTimeMillis()+".jpg";
        File file = new File(fileName);
        try (FileOutputStream os = new FileOutputStream(file, true)){
            // 构造URL
            URL url = new URL(httpUrl);
            // 打开连接
            URLConnection con = url.openConnection();
            // 输入流
            is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度

            int len;
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            return fileName;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileName;
    }
}
