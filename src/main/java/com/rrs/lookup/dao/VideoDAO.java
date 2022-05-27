package com.rrs.lookup.dao;

import com.rrs.lookup.entity.Video;
import com.rrs.lookup.utils.ThingType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VideoDAO extends IDAO<Video> {
    public VideoDAO(Connection conn) {
        super(conn);
    }

    // input: none
    // expect output: list all Video
    // fail output: empty list
    @Override
    public List<Video> selectAll() {
        String sql = "SELECT * FROM THING JOIN VIDEO ON THING.SERIAL = VIDEO.SERIAL";
        List<Video> result = new ArrayList<>();
        try {
            this.rs = statement.executeQuery(sql);
            while (rs.next()) {
                Video video = new Video();
                video.setSerial(rs.getLong("SERIAL"));
                video.setName(rs.getString("NAME"));
                video.setLength(rs.getString("LENGTH"));
                video.setSummary(rs.getString("SUMMARY"));
                video.setPrice(rs.getBigDecimal("PRICE"));
                video.setTotal(rs.getInt("TOTAL"));
                video.setAvailable(rs.getInt("AVAILABLE"));
                result.add(video);
            }
        } catch (SQLException e) {
            System.out.println("Have an error occurred while get data from the database!");
        } finally {
            closeConnection();
        }
        return result;
    }

    // input: serial of Video
    // expect output: a Video object with data of author,summary field
    // fail output: null
    @Override
    public Video selectById(long serial) {
        String sql = "SELECT * FROM VIDEO WHERE SERIAL = ?";
        Video result = null;
        try {
            //query db
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setLong(1,serial);
            rs = this.preStatement.executeQuery();

            //get result
            if (rs.next()) {
                result = new Video();
                result.setLength(rs.getString("LENGTH"));
                result.setSummary(rs.getString("SUMMARY"));
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
    public int insert(Video object) {
        int affectedRows = 0;
        long serial = object.getSerial();

        String sqlInsertThing = (serial != 0) ?
                "INSERT INTO THING(TYPE,NAME,PRICE,TOTAL,AVAILABLE,SERIAL) VALUES(?,?,?,?,?,?)" //input serial
                : "INSERT INTO THING(TYPE,NAME,PRICE,TOTAL,AVAILABLE) VALUES(?,?,?,?,?)";   //don't input serial

        String sqlInsertVideo = "INSERT INTO VIDEO(SERIAL,LENGTH,SUMMARY) VALUES(?,?,?)";

        try {
            //BEGIN TRANSACTION
            this.conn.setAutoCommit(false);
            this.conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

            //insert to THING table
            this.preStatement = this.conn.prepareStatement(sqlInsertThing, Statement.RETURN_GENERATED_KEYS);
            this.preStatement.setString(1, ThingType.VIDEO.toString());
            this.preStatement.setString(2,object.getName());
            this.preStatement.setBigDecimal(3,object.getPrice());
            this.preStatement.setInt(4,object.getTotal());
            this.preStatement.setInt(5,object.getAvailable());
            if (serial != 0) this.preStatement.setLong(6,serial);//input serial
            affectedRows += this.preStatement.executeUpdate();

            if (serial == 0) { //execute when don't input serial
                //get serial just inserted
                try (ResultSet generatedKeys = preStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        object.setSerial(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException();
                    }
                }
            }

            //insert to VIDEO table
            this.preStatement = this.conn.prepareStatement(sqlInsertVideo);
            this.preStatement.setLong(1,object.getSerial());
            this.preStatement.setString(2,object.getLength());
            this.preStatement.setString(3,object.getSummary());
            affectedRows += this.preStatement.executeUpdate();

            //COMMIT TRANSACTION
            this.conn.commit();
        } catch (SQLException e) {
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
    public int update(Video object) {
        return 0;
    }

    @Override
    public void closeConnection() {
        super.closeConnection();
    }
}
