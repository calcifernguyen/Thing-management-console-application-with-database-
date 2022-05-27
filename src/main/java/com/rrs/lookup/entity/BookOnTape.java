package com.rrs.lookup.entity;

public class BookOnTape extends Thing {
    private String author;
    private String summary;

    @Override
    public String getDescription() {
        String format = "|   %-50s   |%n";
        return  String.format("+--------------------------------------------------------+%n") +
                String.format("|                      Book on tape                      |%n") +
//                String.format("+--------------------------------------------------------+%n") +
                String.format(format, "1.Serial: " + getSerial()) +
                String.format(format, "2.Name: " + getName()) +
                String.format(format, "3.Author: " + author) +
                String.format(format, "4.Summary: " + summary) +
                String.format(format, "5.Price: " + getPrice()) +
                String.format(format, "6.Total: " + getTotal()) +
                String.format(format, "7.Available: " + getAvailable()) +
                              "+--------------------------------------------------------+";
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
