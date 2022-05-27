package com.rrs.lookup.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2ConnUtils {
    // default connection
    public static Connection getConnection() throws SQLException,
            ClassNotFoundException {
        String dbPath = "./src/main/resources/database/rrsdb";
//        String dbPath = "./database/rrsdb";   //for jar
        String userName = "sa";
        String password = "123456";

        return getConnection(dbPath, userName, password);
    }

    public static Connection getConnection(String dbPath,
                                                String userName, String password) throws SQLException,
            ClassNotFoundException {

        Class.forName("org.h2.Driver");

        String connectionURL = "jdbc:h2:"+dbPath;

        Connection conn = DriverManager.getConnection(connectionURL, userName,
                password);
        return conn;
    }
}
