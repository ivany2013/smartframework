package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * 类操作助手类
 * Created by mysteel-xl on 2017/11/28.
 */
public final class ClassHelper {
    private static final Set<Class<?>> CLASS_SET;

    static{
        String jdbcBasePackage = ConfigHelper.getJdbcBasePackage();
        CLASS_SET = ClassUtil.getClassSet(jdbcBasePackage);
    }
    /**
     *  @Ps 获取应用下所有类
     *  @Date 2017/11/28 10:44
     */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }
    /**
     *  @Ps 获取Service注解的类
     *  @Date 2017/11/28 10:50
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> aClass : CLASS_SET) {
            if (aClass.isAnnotationPresent(Service.class)){
                classSet.add(aClass);
            }
        }
        return classSet;
    }
    /**
     *  @Ps 获取Controller注解的类
     *  @Date 2017/11/28 10:50
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> aClass : CLASS_SET) {
            if (aClass.isAnnotationPresent(Controller.class)){
                classSet.add(aClass);
            }
        }
        return classSet;
    }
    /**
     *  @Ps 获取Service和Controller注解的类
     *  @Date 2017/11/28 10:50
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        classSet.addAll(getServiceClassSet());
        classSet.addAll(getControllerClassSet());
        return classSet;
    }
 }
