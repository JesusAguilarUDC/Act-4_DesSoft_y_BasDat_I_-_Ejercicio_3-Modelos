package com.jesus.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConexion {
    private static final String URL = "jdbc:mysql://localhost:3306/bd_jesus_aguilar_3_modelos";
    private static final String USER = "root";
    private static final String PASSWORD = "Ja30,69nd,";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }
}

