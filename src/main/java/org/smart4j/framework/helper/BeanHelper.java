package org.smart4j.framework.helper;

import org.smart4j.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *  class与实例
 * Created by Xul on 2017/11/28.
 */
public class BeanHelper {

    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<Class<?>,Object>();

    static{
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> aClass : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(aClass);
            BEAN_MAP.put(aClass,obj);
        }
    }

    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<?> clz){
        if (!BEAN_MAP.containsKey(clz)){
            throw new RuntimeException("Can not get bean by class:"+clz);
        }
        return (T) BEAN_MAP.get(clz);
    }

    public static void setBean(Class<?> clazz,Object object){
        BEAN_MAP.put(clazz,object);
    }

}
