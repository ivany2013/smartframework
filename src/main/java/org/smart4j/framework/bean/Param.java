package org.smart4j.framework.bean;

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

}
