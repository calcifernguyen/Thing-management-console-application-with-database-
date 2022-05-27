package com.rrs.lookup.dao;

import java.util.List;
import java.sql.*;

public abstract class IDAO<T>{
    Statement statement;
    PreparedStatement preStatement;
    Connection conn;
    ResultSet rs;

    public IDAO(Connection conn) {
        this.conn = conn;
        try {
            this.statement = this.conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public abstract List<T> selectAll();
    public abstract T selectById(long id);
    public abstract int insert(T object);
    public abstract int update(T object);
    public void closeConnection() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
