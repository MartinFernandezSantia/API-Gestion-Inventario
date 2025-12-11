package com.lmfm.api.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://root:ikkLjQWdirRYHcwpwNtnaMWPsUtmmkZl@crossover.proxy.rlwy.net:35586/railway";
    private static final String USER = "root";
    private static final String PASSWORD = "ikkLjQWdirRYHcwpwNtnaMWPsUtmmkZl";

    // private static final String URL = "jdbc:mysql://localhost:3306/hpc_db";
    // private static final String USER = "root";
    // private static final String PASSWORD = "asdASD123";


    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL);
    }

}
