package org.smart4j.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * Created by mysteel-xl on 2017/11/28.
 */
public final class ReflectionUtil {

    /**
     *  @Ps 创建实例
     *  @Date 2017/11/28 10:56
     */
    public static Object newInstance(Class<?> clz){
        Object instance = null;
        try {
            instance = clz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }
    /**
     *  @Ps 调用方法
     *  @Date 2017/11/28 10:59
     */
    public static Object invokeMethod(Object obj, Method method,Object...param){
        Object result = null;
        try {
            result = method.invoke(obj,param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
/**
 *  @Description:为属性设置值
 *  @Date: 2017/11/28
 */
    public static void setField(Object obj, Field field,Object value){
        field.setAccessible(true);
        try {
            field.set(obj,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
