package org.smart4j.framework.proxy;

import org.apache.log4j.Logger;
import org.smart4j.framework.annotation.Transaction;
import org.smart4j.framework.helper.DatabaseHelper;

import java.lang.reflect.Method;

/**
 * Created by Xul on 2017/12/1.
 */
public class TransactionProxy implements Proxy{

    private static final Logger LOGGER = Logger.getLogger(TransactionProxy.class);
    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };


    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Boolean flag = FLAG_HOLDER.get();
        Method targetMethod = proxyChain.getTargetMethod();
        if (!flag && targetMethod.isAnnotationPresent(Transaction.class)) {
            try {
                FLAG_HOLDER.set(true);
                DatabaseHelper.beginTransaciton();
                LOGGER.debug("begin transaction");
                result = proxyChain.doProxyChain();
                DatabaseHelper.commitTransaction();
                LOGGER.debug("commit transaction");
            } catch (Exception e) {
                DatabaseHelper.rollbackTransaction();
                LOGGER.debug("rollback transaction");
                throw e ;
            } finally {
                FLAG_HOLDER.remove();
            }
        }else{
            result = proxyChain.doProxyChain();
        }
        return result;
    }
}
