package com.rrs.lookup.dao;

import com.rrs.lookup.entity.BookOnTape;
import com.rrs.lookup.utils.ThingType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookOnTapeDAO extends IDAO<BookOnTape> {
    public BookOnTapeDAO(Connection conn) {
        super(conn);
    }

    // input: none
    // expect output: list all BookOnTape
    // fail output: empty list
    @Override
    public List<BookOnTape> selectAll() {
        String sql = "SELECT * FROM THING JOIN BOOK_ON_TAPE ON THING.SERIAL = BOOK_ON_TAPE.SERIAL";
        List<BookOnTape> result = new ArrayList<>();
        try {
            this.rs = statement.executeQuery(sql);
            while (rs.next()) {
                BookOnTape book = new BookOnTape();
                book.setSerial(rs.getLong("SERIAL"));
                book.setName(rs.getString("NAME"));
                book.setAuthor(rs.getString("AUTHOR"));
                book.setSummary(rs.getString("SUMMARY"));
                book.setPrice(rs.getBigDecimal("PRICE"));
                book.setTotal(rs.getInt("TOTAL"));
                book.setAvailable(rs.getInt("AVAILABLE"));
                result.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Have an error occurred while insert data from the database!");
        } finally {
            closeConnection();
        }
        return result;
    }


    // input: serial of BookOnTape
    // expect output: a BookOnTape object with data of author,summary field
    // fail output: null
    @Override
    public BookOnTape selectById(long serial) {
        String sql = "SELECT * FROM BOOK_ON_TAPE WHERE SERIAL = ?";
        BookOnTape result = null;
        try {
            //query db
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setLong(1,serial);
            rs = this.preStatement.executeQuery();

            //get result
            if (rs.next()) {
                result = new BookOnTape();
                result.setAuthor(rs.getString("AUTHOR"));
                result.setSummary(rs.getString("SUMMARY"));
            }

        } catch (SQLException e) {
            System.out.println("Have an error occurred while get data from the database!");
        } finally {
            closeConnection();
        }
        return result;
    }

    // input: BookOnTape object
    // output: affected rows after query
    // rollback when an error occurs
    @Override
    public int insert(BookOnTape object) {
        int affectedRows = 0;
        long serial = object.getSerial();
        String sqlInsertThing = (serial != 0) ?
                "INSERT INTO THING(TYPE,NAME,PRICE,TOTAL,AVAILABLE,SERIAL) VALUES(?,?,?,?,?,?)" //input serial
                : "INSERT INTO THING(TYPE,NAME,PRICE,TOTAL,AVAILABLE) VALUES(?,?,?,?,?)"; //don't input serial
        String sqlInsertBookOnTape = "INSERT INTO BOOK_ON_TAPE(SERIAL,AUTHOR,SUMMARY) VALUES(?,?,?)";

        try {
            //BEGIN TRANSACTION
            this.conn.setAutoCommit(false);
            this.conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

            //insert to THING table
            this.preStatement = this.conn.prepareStatement(sqlInsertThing, Statement.RETURN_GENERATED_KEYS);
            this.preStatement.setString(1, ThingType.BOOK_ON_TAPE.toString());
            this.preStatement.setString(2,object.getName());
            this.preStatement.setBigDecimal(3,object.getPrice());
            this.preStatement.setInt(4,object.getTotal());
            this.preStatement.setInt(5,object.getAvailable());
            if (serial != 0) this.preStatement.setLong(6,object.getSerial()); //input serial
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

            //insert to BOOK_ON_TAPE table
            this.preStatement = this.conn.prepareStatement(sqlInsertBookOnTape);
            this.preStatement.setLong(1,object.getSerial());
            this.preStatement.setString(2,object.getAuthor());
            this.preStatement.setString(3,object.getSummary());
            affectedRows += this.preStatement.executeUpdate();

            //COMMIT TRANSACTION
            this.conn.commit();
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("Have an error occurred while writing data to the database!");
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
    public int update(BookOnTape object) {
        return 0;
    }

    @Override
    public void closeConnection() {
        super.closeConnection();
    }
}
