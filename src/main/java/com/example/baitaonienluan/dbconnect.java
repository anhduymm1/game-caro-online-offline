package com.example.baitaonienluan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnect {
    private static String DB_NAME = "caro";
    private static String USERNAME = "root";
    private static String PASSWORD = "0364764192cdg";
    private static String URL = "jdbc:mysql://localhost/" + DB_NAME;
    public static Connection dblink;


    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dblink = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dblink;
    }
}
