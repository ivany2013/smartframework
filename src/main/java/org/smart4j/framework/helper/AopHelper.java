package org.smart4j.framework.helper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.proxy.AspectProxy;
import org.smart4j.framework.proxy.Proxy;
import org.smart4j.framework.proxy.ProxyManager;
import org.smart4j.framework.proxy.TransactionProxy;

/**
 * Created by Xul on 2017/11/29.
 */
public class AopHelper {
	
	static{
		try {
			Map<Class<?>, Set<Class<?>>> createProxyMap = createProxyMap();
			Map<Class<?>, List<Proxy>> createTargetMap = createTargetMap(createProxyMap);
			for (Entry<Class<?>, List<Proxy>> prixt : createTargetMap.entrySet()) {
				Class<?> targetClass = prixt.getKey();
				List<Proxy> proxyList = prixt.getValue();
				Object proxy = ProxyManager.createProxy(targetClass, proxyList);
				BeanHelper.setBean(targetClass, proxy);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
    /**
     *  @Description: 获取需要代理的 目标类
     *  @Date: 2017/11/29
     */
    public static Set<Class<?>> createTargetClassSet(Aspect aspect){
        Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
        Class<? extends Annotation> value = aspect.value();
        if (value != null && !value.equals(Aspect.class)){
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(value));
        }
        return targetClassSet;
    }
    /**
     *  @Description:  key = 切面; value= 需要被代理的目标类的集合
     *  @Date: 2017/11/29
     */
    public static Map<Class<?>,Set<Class<?>>> createProxyMap(){
        Map<Class<?>,Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
        addAspectMap(proxyMap);
        addTransactionMap(proxyMap);
        return proxyMap;
    }

    public static void addAspectMap(Map<Class<?>,Set<Class<?>>> proxyMap){
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySupClass(AspectProxy.class);
        for (Class<?> aClass : proxyClassSet) {
            if (aClass.isAnnotationPresent(Aspect.class)){
                Aspect aspect = aClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(aClass,targetClassSet);
            }
        }
    }

    public static void addTransactionMap(Map<Class<?>,Set<Class<?>>> proxyMap){
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class,proxyClassSet);
    }
    /**
     *  @Description:   key = 目标类； value = 目标类对应的切面实例的集合
     *  @Date: 2017/11/29
     */
    public static Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
        Map<Class<?>,List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(proxy);
                }else{
                    List<Proxy> list = new ArrayList<Proxy>();
                    list.add(proxy);
                    targetMap.put(targetClass,list);
                }
            }
        }
        return targetMap;
    }
}
