package org.smart4j.framework.helper;

import org.apache.commons.lang3.ArrayUtils;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 * Created by Xul on 2017/11/28.
 */
public final class IocHelper {
    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (beanMap!=null && !beanMap.isEmpty()) {
            for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
                Class<?> beanClass = entry.getKey();
                Object beanInstance = entry.getValue();
                Field[] declaredFields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(declaredFields)) {
                    for (Field field : declaredFields) {
                        if (field.isAnnotationPresent(Inject.class)) {
                            Class<?> type = field.getType();
                            Object beanFieldInstance = beanMap.get(type);
                            if (beanFieldInstance != null) {
                                ReflectionUtil.setField(beanInstance,field,beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }

    }

	public static void init() {
	}
}
