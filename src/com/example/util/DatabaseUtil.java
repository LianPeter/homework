package com.example.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import com.alibaba.druid.pool.DruidDataSource;
import javax.sql.DataSource;

public class DatabaseUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/homework_db";
    private static final String USERNAME = "homework_user";
    private static final String PASSWORD = "Homework@123456";
    private static DruidDataSource dataSource;

    static {
        try {
            Class.forName(DRIVER);
            initDataSource();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL driver. Please ensure mysql-connector-j is in your classpath.", e);
        }
    }

    private static void initDataSource() {
        dataSource = new DruidDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        
        // 基本连接池配置
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(5);
        dataSource.setMaxActive(20);
        dataSource.setMaxWait(60000);
        
        // 连接检测配置
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        
        // 连接池监控配置
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        
        // 连接属性配置
        Properties props = new Properties();
        props.setProperty("useSSL", "false");
        props.setProperty("serverTimezone", "UTC");
        props.setProperty("characterEncoding", "utf8");
        props.setProperty("useUnicode", "true");
        props.setProperty("allowPublicKeyRetrieval", "true");
        props.setProperty("rewriteBatchedStatements", "true");
        props.setProperty("useLocalSessionState", "true");
        props.setProperty("useLocalTransactionState", "true");
        props.setProperty("maintainTimeStats", "false");
        dataSource.setConnectProperties(props);
        
        // 配置监控统计拦截的filters
        try {
            dataSource.setFilters("stat,wall");
        } catch (SQLException e) {
            System.err.println("Error setting up database filters: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = dataSource.getConnection();
            if (conn == null) {
                throw new SQLException("Failed to establish database connection");
            }
            return conn;
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
            throw new SQLException("Failed to connect to database. Please check your database configuration and ensure the database server is running.", e);
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}