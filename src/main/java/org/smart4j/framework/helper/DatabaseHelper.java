package org.smart4j.framework.helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.smart4j.framework.ConfigConstant;
import org.smart4j.framework.util.PropsUtil;

/**
 * Created by mysteel-xl on 2017/11/27.
 */
public class DatabaseHelper {

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<Connection>();

    private static BasicDataSource DATA_SOURCE;


    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties properties = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);
        DRIVER = properties.getProperty(ConfigConstant.JDBC_DRIVER);
        URL = properties.getProperty(ConfigConstant.JDBC_URL);
        USERNAME = properties.getProperty(ConfigConstant.JDBC_USERNAME);
        PASSWORD = properties.getProperty(ConfigConstant.JDBC_PASSWORD);

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     *  @Ps 获取数据库连接
     *  @Date 2017/11/27 11:24
     */
    public static Connection getConnection(){
        Connection con = CONNECTION_THREAD_LOCAL.get();
        if (con == null) {
            try {
                con = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                CONNECTION_THREAD_LOCAL.set(con);
            }
        }
        return con;
    }
    /**
     * 关闭链接
     *  @Description:
     *  @Date: 2017/12/1
     */
    public static void closeConneciton(){
        Connection con = CONNECTION_THREAD_LOCAL.get();
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                CONNECTION_THREAD_LOCAL.remove();
            }
        }
    }

    /**
     *  @Description:开启事务
     *  @Date: 2017/12/1
     */
    public static void beginTransaciton(){
        Connection connection = getConnection();
        if (connection!=null){
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                CONNECTION_THREAD_LOCAL.set(connection);
            }
        }
    }

    /**
     *  @Description:提交事务
     *  @Date: 2017/12/1
     */
    public static void commitTransaction(){
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.commit();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                CONNECTION_THREAD_LOCAL.remove();
            }
        }
    }
    /**
     *  @Description: 回滚事务
     *  @Date: 2017/12/1
     */
    public static void rollbackTransaction(){
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                CONNECTION_THREAD_LOCAL.remove();
            }
        }
    }

    public static <T> List<T>  queryEntityList(Class<T> entityClass,String sql, Object... param){
        List<T> entityList = null;
        Connection conn = getConnection();
        try {
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), param);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConneciton();
        }
        return entityList;
    }
    /*
    public static <T> T queryEntity(Class<T> entityClass,String sql,Object... param){
        T entity = null;
        Connection con = getConnection();
        try {
            entity = QUERY_RUNNER.query(con,sql,new BeanHandler<T>(entityClass),param);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConneciton();
        }
        return entity;
    }

    public static int exUpdate(String sql,Object... param){
        int rows = 0;
        Connection con = getConnection();
        try {
            rows = QUERY_RUNNER.update(con,sql,param);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConneciton();
        }
        return rows;
    }*/

}
