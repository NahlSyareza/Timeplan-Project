package com.eszaray.timeplanspring.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnect {

    public static Connection connect() throws SQLException{
        try{
            var url = DatabaseConfig.getUrl();
            var username = DatabaseConfig.getUsername();
            var password = DatabaseConfig.getPassword();

            return DriverManager.getConnection(url, username, password);
        }catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
    }

    public static void executeQuery(String query) {

    }

}
