package org.smart4j.framework.bean;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Xul on 2017/11/28.
 */
public class Param {
    private Map<String,Object> paramMap;

    public Param(Map<String,Object> paramMap){
        this.paramMap = paramMap;
    }

    public Map<String,Object> getMap(){
        return paramMap;
    }

    public String getString(String name){
        return (String) paramMap.get(name);
    }

    public boolean isEmpty(){
        return MapUtils.isEmpty(paramMap);
    }
}
