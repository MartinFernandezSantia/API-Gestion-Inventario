package com.lmfm.api.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    //private static final String URL = System.getenv("DB_URL");
    //private static final String USER = System.getenv("DB_USERNAME");
    //private static final String PASSWORD = System.getenv("DB_PASSWORD");

    private static final String URL = "jdbc:mysql://localhost:3306/hpc_db";
    private static final String USER = "root";
    private static final String PASSWORD = "ASDasd123";


    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
