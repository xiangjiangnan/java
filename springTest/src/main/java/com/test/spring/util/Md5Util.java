package com.test.spring.util;

import com.alibaba.fastjson.JSONObject;

import java.security.MessageDigest;
import java.util.Map;

/**
 * @author xiang
 */
public class Md5Util {
    private static String[] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
    /**
     * 将明文字符中以MD5方式加密后返回可读的十六进制数字符串
     */
    public static String encodeByMd5(String password) throws Exception{
        MessageDigest md5 = MessageDigest.getInstance("md5");
        byte[] results = md5.digest(password.getBytes());
        return byteArrayToString(results);
    }

    /**
     *   将byte[]转成String返回
     */
    private static String byteArrayToString(byte[] results){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<results.length;i++){
            //将每一位byte转发String
            sb.append(byteToString(results[i]));
        }
        return sb.toString();
    }

    /**
     * 将每一位byte转换为16进制string
     */
    private static String byteToString(byte b){
        int n = b;
        if(n < 0 ){
            n = 256 + n ;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hex[d1] + hex[d2];
    }

    public static String getJSONString(int code) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        return json.toJSONString();
    }

    public static String getJSONString(int code, String msg) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        return json.toJSONString();
    }

    public static String getJSONString(int code, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }
        return json.toJSONString();
    }
}
