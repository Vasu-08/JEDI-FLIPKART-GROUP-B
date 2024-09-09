package com.flipkart.dao;
import java.sql.*;
public class GetConnection {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/FlipFitSchema", "root", "12345678");
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
