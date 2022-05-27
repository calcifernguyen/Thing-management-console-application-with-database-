package com.rrs.lookup.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {
    public static Connection getMyConnection() {
        Connection conn;
        try {
            conn = H2ConnUtils.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Can not connect to database!");
            throw new RuntimeException(e);
        }
        return conn;
    }

    //
    // Test Connection ...
    //
    public static void main(String[] args) {

        System.out.println("Get connection ... ");

        // Lấy ra đối tượng Connection kết nối vào database.
        Connection conn = ConnectionUtils.getMyConnection();

        System.out.println("Get connection " + conn);

        System.out.println("Done!");
    }
}
