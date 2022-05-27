package com.rrs.lookup.dao;

import com.rrs.lookup.entity.BookOnTape;
import com.rrs.lookup.entity.Furniture;
import com.rrs.lookup.entity.Thing;
import com.rrs.lookup.entity.Video;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ThingDAO extends IDAO<Thing> {

    public ThingDAO(Connection conn) {
        super(conn);
    }

    @Override
    public List<Thing> selectAll() {
        return null;
    }

    // input: serial of Thing
    // expect output: a Thing object
    // fail output: null
    @Override
    public Thing selectById(long serial) {
        String sql = "SELECT * FROM THING WHERE SERIAL = ?";
        Thing result = null;
        try {
            //query db
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setLong(1,serial);
            rs = this.preStatement.executeQuery();
            if (rs.next()) {
                //save information of thing
                String name = rs.getString("NAME");
                BigDecimal price = rs.getBigDecimal("PRICE");
                int total = rs.getInt("TOTAL");
                int available = rs.getInt("AVAILABLE");
                String thingType = rs.getString("TYPE");

                //get extra information follow thing type
                switch (thingType) {
                    case "VIDEO":
                        VideoDAO videoDAO = new VideoDAO(conn);
                        result = videoDAO.selectById(serial);
                        if (result == null) result = new Video();
                        break;
                    case "FURNITURE":
                        FurnitureDAO furnitureDAO = new FurnitureDAO(conn);
                        result = furnitureDAO.selectById(serial);
                        if (result == null) result = new Furniture();
                        break;
                    case "BOOK_ON_TAPE":
                        BookOnTapeDAO bookOnTapeDAO = new BookOnTapeDAO(conn);
                        result = bookOnTapeDAO.selectById(serial);
                        if (result == null) result = new BookOnTape();
                        break;
                }

                //set information of thing
                if (result != null) {
                    result.setSerial(serial);
                    result.setName(name);
                    result.setPrice(price);
                    result.setTotal(total);
                    result.setAvailable(available);
                } else {
                    String format = "|   %-50s   |%n";
                    System.out.println("Not found type of thing:");
                    System.out.format("+--------------------------------------------------------+%n");
                    System.out.format(format,"Type: " + thingType + " ???");
                    System.out.format(format, "1.Serial: " + serial);
                    System.out.format(format, "2.Name: " + name);
                    System.out.format(format, "3.Price: " + price);
                    System.out.format(format, "4.Total: " + total);
                    System.out.format(format, "5.Available: " + available);
                    System.out.format("+--------------------------------------------------------+%n");
                }
            } else {
                System.out.println("Not found thing with serial " + serial);
            }

        } catch (SQLException e) {
            System.out.println("Have an error occurred while get data from the database!");
        } finally {
            closeConnection();
        }
        return result;
    }

    @Override
    public int insert(Thing object) {
        return 0;
    }

    @Override
    public int update(Thing object) {
        return 0;
    }

    @Override
    public void closeConnection() {
        super.closeConnection();
    }
}
