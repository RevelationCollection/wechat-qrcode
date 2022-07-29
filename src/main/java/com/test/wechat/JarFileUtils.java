package com.test.wechat;

import org.opencv.core.Mat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class JarFileUtils {

    private JarFileUtils(){}

    private static String resourcePath;

    public static final boolean isJarFile;

    static {
        URL resource = JarFileUtils.class.getResource("/");
        if (resource!=null){
            resourcePath = resource.getPath();
            System.err.println("jar file path :"+resource);
        }
        if (resourcePath == null){
            isJarFile = true;
        }else {
            String jarFilePath = JarFileUtils.class.getProtectionDomain().getCodeSource().getLocation().getFile();
            isJarFile = resourcePath.contains(".jar!") || jarFilePath.endsWith(".jar");
        }
    }

    /**
     * 解析jar包中的文体局
     * @param path jar包中的路径 /src/main/
     * @param fileName 文件名称
     * @return 解析后的文件,用完删除
     */
    public static synchronized File extractLibrary(String path,String fileName) {
        String newFilePath = "/tmp/" + fileName;
        try {
            if (!path.startsWith("/")){
                path = "/"+path;
            }
            if (!path.endsWith("/")){
                path +="/";
            }
            path+=fileName;
            if (!isJarFile){
                fileName = resourcePath+path.substring(1);
                System.out.println(" read local file :"+fileName);
                File file = new File(fileName);
                if (file.exists()) {
                    return file;
                }
            }
            File file = new File(newFilePath);
            if (file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
            InputStream resourceAsStream = JarFileUtils.class.getResourceAsStream(path);
            if (resourceAsStream == null) {
                throw new NullPointerException("file path is null:"+path);
            }
            Files.copy(resourceAsStream, file.getAbsoluteFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
            if (!file.exists()){
                throw new FileNotFoundException("临时so文件创建失败:"+fileName);
            }
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args)  {
        URL resource = Mat.class.getResource("/");
        System.out.println(resource);
        String jarFilePath = Mat.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        System.out.println(jarFilePath);
        System.out.println(JarFileUtils.class.getProtectionDomain().getCodeSource().getLocation().getFile());
    }
}

