package com.test.wechat;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShellUtils {

    public static String runShell(String shStr) throws Exception {

        Process process;
        process = Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",shStr});
        process.waitFor();
        BufferedReader read = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line ;
        StringBuilder result = new StringBuilder();
        while ((line = read.readLine())!=null){
            result.append(line);
            System.out.println(line);
        }
        return result.toString();
    }

    public static String runShellSupport(String param){
        String[] strings = {"/bin/sh", "-c", param};
        Process p0 =null;
        try {
            p0 = Runtime.getRuntime().exec(strings);
            //读取标准输出流
            BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(p0.getInputStream()));
            String line ;
            StringBuilder result = new StringBuilder("shell info");
            while ((line = bufferedReader.readLine())!=null){
                result.append(line);
                System.err.println("shell info :"+line);
            }
            //读取标准错误流
            BufferedReader brError = new BufferedReader(new InputStreamReader(p0.getErrorStream(), "gb2312"));
            String errorLine;
            while ((errorLine = brError.readLine()) != null) {
                result.append(errorLine);
                System.err.println("error shell msg:"+errorLine);
            }
            String res = result.toString();
            //waitFor()判断Process进程是否终止，通过返回值判断是否正常终止。0代表正常终止
            int c=p0.waitFor();
            if(c!=0){
                result.append("error shell throw exit");
                return res;
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (p0 != null) {
                p0.destroy();
            }
        }
        return null;
    }
}