package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/homework_db";
    private static final String USERNAME = "homework_user";
    private static final String PASSWORD = "Homework@123456";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL driver. Please ensure mysql-connector-j is in your classpath.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", USERNAME);
        props.setProperty("password", PASSWORD);
        props.setProperty("useSSL", "false");
        props.setProperty("serverTimezone", "UTC");
        props.setProperty("characterEncoding", "utf8");
        props.setProperty("allowPublicKeyRetrieval", "true");
        props.setProperty("createDatabaseIfNotExist", "true");
        
        try {
            return DriverManager.getConnection(URL, props);
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to database. Please check your database configuration.", e);
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}