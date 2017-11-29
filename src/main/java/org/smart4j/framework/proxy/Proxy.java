package org.smart4j.framework.proxy;

/**
 * 代理接口
 * Created by Xul on 2017/11/29.
 */
public interface Proxy {

    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
