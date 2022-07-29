package com.test.wechat;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.wechat_qrcode.WeChatQRCode;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public final class WeChatQRCodeDetector {

    private static WeChatQRCode sWeChatQRCode;

    private WeChatQRCodeDetector(){
        throw new AssertionError();
    }

    /**
     * 初始化 WeChatQRCode
     */
    public static void init() throws Exception{
        try {
            init("/models/");
        }catch (Throwable e){
            throw new Exception(e);
        }
    }

    /**
     * 初始化 WeChatQRCode
     * @param modelDirPath WeChatQRCode 相关模型文件所在的文件夹
     */
    public static void init(String modelDirPath) {
        //初始化 WeChatQRCode
        initWeChatQRCode(modelDirPath);
    }


    /**
     * 初始化 WeChatQRCode
     */
    private static void initWeChatQRCode(String modelDirPath) {
        //WeChatQRCode 相关的模型文件
        File detect = JarFileUtils.extractLibrary(modelDirPath,"detect.prototxt");
        File detectModel = JarFileUtils.extractLibrary(modelDirPath,"detect.caffemodel");
        File resolution = JarFileUtils.extractLibrary(modelDirPath,"sr.prototxt");
        File resolutionModel = JarFileUtils.extractLibrary(modelDirPath,"sr.caffemodel");
        //实例化 WeChatQRCode
        sWeChatQRCode = new WeChatQRCode(
                detect.getAbsolutePath(),
                detectModel.getAbsolutePath(),
                resolution.getAbsolutePath(),
                resolutionModel.getAbsolutePath());

        Logger.getLogger(WeChatQRCodeDetector.class.getName()).log(Level.INFO,"Initialization WeChatQRCode.");
    }


    /**
     * Both detects and decodes QR code.
     * To simplify the usage, there is a only API: detectAndDecode
     *
     * @param filename 本地路径
     * @return list of decoded string.
     */
    public static List<String> detectAndDecode(String filename){
        return detectAndDecode(Imgcodecs.imread(filename));
    }

    /**
     * Both detects and decodes QR code.
     * To simplify the usage, there is a only API: detectAndDecode
     *
     * @param img supports grayscale or color (BGR) image.
     * empty if not found.
     * @return list of decoded string.
     */
    public static List<String> detectAndDecode(Mat img){
        return sWeChatQRCode.detectAndDecode(img);
    }

    /**
     * Both detects and decodes QR code.
     * To simplify the usage, there is a only API: detectAndDecode
     *
     * @param img supports grayscale or color (BGR) image.
     * @param points optional output array of vertices of the found QR code quadrangle. Will be
     * empty if not found.
     * @return list of decoded string.
     */
    public static List<String> detectAndDecode(Mat img, List<Mat> points){
        return sWeChatQRCode.detectAndDecode(img,points);
    }

}