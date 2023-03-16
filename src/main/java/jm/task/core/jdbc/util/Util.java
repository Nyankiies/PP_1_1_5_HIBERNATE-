package jm.task.core.jdbc.util;

import java.sql.*;
public class Util {
    // реализуйте настройку соеденения с БД
    public static final String  DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String  DB_URL = "jdbc:mysql://localhost:3306/mtdbtest";
    public static final String  DB_USERNAME = "root";
    public static final String  DB_PASSWORD = "@dinThorUllr2495";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}


