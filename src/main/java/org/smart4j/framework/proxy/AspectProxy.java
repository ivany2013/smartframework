package org.smart4j.framework.proxy;

import java.lang.reflect.Method;

/**
 * Created by Xul on 2017/11/29.
 */
public abstract class AspectProxy implements Proxy{
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Object[] methodParams = proxyChain.getMethodParams();
        Class<?> targetClass = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        begin();
        try {
            if (intercept(targetClass,targetMethod,methodParams)){
                before(targetClass,targetMethod,methodParams);
                result = proxyChain.doProxyChain();
                after(targetClass,targetMethod,methodParams);
            }else{
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            e.printStackTrace();
            err(targetClass,targetMethod,methodParams,e);
        } finally {
            end();
        }
        return result;
    }

    public void begin(){}
    public void end(){}
    public boolean intercept(Class<?> clazz,Method method,Object[] params) throws Throwable{
        return true;
    }
    public void before(Class<?> clazz,Method method,Object[] params) throws Throwable{}
    public void after(Class<?> clazz,Method method,Object[] params) throws Throwable{}
    public void err(Class<?> clazz,Method method,Object[] params,Throwable e){}
}
