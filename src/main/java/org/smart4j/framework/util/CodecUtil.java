package org.smart4j.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编码解码工具类
 * Created by Xul on 2017/11/28.
 */
public final class CodecUtil {
    //加密
    public static String encodeURL(String source){
        String target = null;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return target;
    }
    //解密
    public static String decodeURL(String source){
        String target = null;
        try {
            target = URLDecoder.decode(source,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return target;
    }
}
