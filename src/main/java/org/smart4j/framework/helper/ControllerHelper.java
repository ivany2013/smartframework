package org.smart4j.framework.helper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 * Created by Xul on 2017/11/28.
 */
public final class ControllerHelper {
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHelper.class);
    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            for (Class<?> controllerClass : controllerClassSet) {
                Method[] declaredMethods = controllerClass.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(declaredMethods)){
                    for (Method declaredMethod : declaredMethods) {
                        if (declaredMethod.isAnnotationPresent(Action.class)){
                            Action annotation = declaredMethod.getAnnotation(Action.class);
                            String mapping = annotation.value();
                                String[] array = mapping.split(":");
                                if (ArrayUtils.isNotEmpty(array) && array.length == 2){
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod,requestPath);
                                    Handler handler = new Handler(controllerClass,declaredMethod);
                                    ACTION_MAP.put(request,handler);
                                }else{
                                	LOGGER.debug("url书写有误");
                                }
                            }
                    }
                }
            }
        }
    }

    public static Handler getHandler(String requestMethod,String requestPath){
        Request request = new Request(requestMethod,requestPath);
        return ACTION_MAP.get(request);
    }
}
