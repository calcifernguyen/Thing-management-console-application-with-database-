package com.rrs.lookup.dao;

import com.rrs.lookup.entity.Furniture;
import com.rrs.lookup.utils.ThingType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FurnitureDAO extends IDAO<Furniture> {
    public FurnitureDAO(Connection conn) {
        super(conn);
    }

    // input: none
    // expect output: list all Furniture
    // fail ouput: empty list
    @Override
    public List<Furniture> selectAll() {
        String sql = "SELECT * FROM THING JOIN FURNITURE ON THING.SERIAL = FURNITURE.SERIAL";
        List<Furniture> result = new ArrayList<>();
        try {
            this.rs = statement.executeQuery(sql);
            while (rs.next()) {
                Furniture furniture = new Furniture();
                furniture.setSerial(rs.getLong("SERIAL"));
                furniture.setName(rs.getString("NAME"));
                furniture.setMaterial(rs.getString("MATERIAL"));
                furniture.setColor(rs.getString("COLOR"));
                furniture.setPrice(rs.getBigDecimal("PRICE"));
                furniture.setTotal(rs.getInt("TOTAL"));
                furniture.setAvailable(rs.getInt("AVAILABLE"));
                result.add(furniture);
            }
        } catch (SQLException e) {
            System.out.println("Have an error occurred while get data from the database!");
        } finally {
            closeConnection();
        }
        return result;
    }

    // input: serial of Furniture
    // expect output: a Furniture object with data of material,color field
    // fail output: null
    @Override
    public Furniture selectById(long serial) {
        String sql = "SELECT * FROM FURNITURE WHERE SERIAL = ?";
        Furniture result = null;
        try {
            //query db
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setLong(1,serial);
            rs = this.preStatement.executeQuery();

            //get result
            if (rs.next()) {
                result = new Furniture();
                result.setMaterial(rs.getString("MATERIAL"));
                result.setColor(rs.getString("COLOR"));
            }

        } catch (SQLException e) {
            System.out.println("Have an error occurred while get data from the database!");
        } finally {
            closeConnection();
        }
        return result;
    }

    // input: Furniture object
    // output: affected rows after query
    // rollback when an error occurs
    @Override
    public int insert(Furniture object) {
        int affectedRows = 0;
        long serial = object.getSerial();
        String sqlInsertThing = (serial != 0) ?
                "INSERT INTO THING(TYPE,NAME,PRICE,TOTAL,AVAILABLE,SERIAL) VALUES(?,?,?,?,?,?)" //input serial
                : "INSERT INTO THING(TYPE,NAME,PRICE,TOTAL,AVAILABLE) VALUES(?,?,?,?,?)"; //don't input serial
        String sqlInsertFurniture = "INSERT INTO FURNITURE(SERIAL,MATERIAL,COLOR) VALUES(?,?,?)";

        try {
            //BEGIN TRANSACTION
            this.conn.setAutoCommit(false);
            this.conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

            //insert to THING table
            this.preStatement = this.conn.prepareStatement(sqlInsertThing, Statement.RETURN_GENERATED_KEYS);
            this.preStatement.setString(1, ThingType.FURNITURE.toString());
            this.preStatement.setString(2,object.getName());
            this.preStatement.setBigDecimal(3,object.getPrice());
            this.preStatement.setInt(4,object.getTotal());
            this.preStatement.setInt(5,object.getAvailable());
            if (serial != 0) this.preStatement.setLong(6,object.getSerial()); //input serial
            affectedRows += this.preStatement.executeUpdate();

            if (serial == 0) { //execute when don't input serial
                //get serial just inserted
                ResultSet generatedKeys = preStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    object.setSerial(generatedKeys.getLong(1));
                } else {
                    throw new SQLException();
                }
            }

            //insert to FURNITURE table
            this.preStatement = this.conn.prepareStatement(sqlInsertFurniture, Statement.RETURN_GENERATED_KEYS);
            this.preStatement.setLong(1,object.getSerial());
            this.preStatement.setString(2,object.getMaterial());
            this.preStatement.setString(3,object.getColor());
            affectedRows += this.preStatement.executeUpdate();

            //COMMIT TRANSACTION
            this.conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Have an error occurred while insert data to the database!");

            try {
                this.conn.rollback();
                System.out.println("This transaction was rollback!");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            closeConnection();
        }

        return affectedRows;
    }

    @Override
    public int update(Furniture object) {
        return 0;
    }

    @Override
    public void closeConnection() {
        super.closeConnection();
    }
}
