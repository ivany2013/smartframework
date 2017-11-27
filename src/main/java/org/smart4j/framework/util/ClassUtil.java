package org.smart4j.framework.util;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.util.Set;

/**
 * 类操作工具类
 * Created by mysteel-xl on 2017/11/27.
 */
public final class ClassUtil {

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cls;
    }

    public static Set<Class<?>> getClassSet(String packageName) {

    }

    public static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        final File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class") || file.isDirectory());
            }
        });
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtils.isNoneBlank(packageName)) {
                    className = packageName+"."+className;
                }
                doAddClass(classSet,className);
            }else{
                String subPackageName = fileName;
                String subPackagePath = fileName;
                if (StringUtils.isNoneBlank(packagePath)) {
                    subPackagePath = packagePath+"."+subPackagePath;
                }
                if (StringUtils.isNoneBlank(packageName)) {
                    subPackageName = packageName+"."+subPackageName;
                }
                addClass(classSet,subPackagePath,subPackageName);
            }
            
        }
    }


    public static void doAddClass(Set<Class<?>> classSet,String className){
        Class<?> cla = loadClass(className,false);
        classSet.add(cla);
    }

}
