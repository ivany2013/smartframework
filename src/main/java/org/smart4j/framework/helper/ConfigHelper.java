package org.smart4j.framework.helper;

import org.apache.commons.lang3.StringUtils;
import org.smart4j.framework.ConfigConstant;
import org.smart4j.framework.util.PropsUtil;

import java.util.Properties;

/**
 * 用于获取基本配置
 * Created by mysteel-xl on 2017/11/27.
 */
public final class ConfigHelper {

    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    public static String getJdbcDriver(){
        return CONFIG_PROPS.getProperty(ConfigConstant.JDBC_DRIVER);
    }

    public static String getJdbcUrl(){
        return CONFIG_PROPS.getProperty(ConfigConstant.JDBC_URL);
    }

    public static String getJdbcUserName(){
        return CONFIG_PROPS.getProperty(ConfigConstant.JDBC_USERNAME);
    }

    public static String getJdbcPassWord(){
        return CONFIG_PROPS.getProperty(ConfigConstant.JDBC_PASSWORD);
    }

    public static String getJdbcBasePackage(){
        return CONFIG_PROPS.getProperty(ConfigConstant.APP_BASE_PACKAGE);
    }

    public static String getJdbcJspPath(){
        String property = CONFIG_PROPS.getProperty(ConfigConstant.APP_JSP_PATH);
        if(StringUtils.isBlank(property)){
            property = "/WEB-INF/view/";
        }
        return property;
    }

    public static String getJdbcAssetPath(){
        String property = CONFIG_PROPS.getProperty(ConfigConstant.APP_ASSET_PATH);
        if(StringUtils.isBlank(property)){
            property = "/asset/";
        }
        return property;
    }

}
