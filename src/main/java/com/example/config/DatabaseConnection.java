package com.example.config; // Bạn có thể đổi tên package cho phù hợp với dự án

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/QLNVBCHLapTop";
    private static final String USER = "root";
    private static final String PASSWORD = "12345@";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}