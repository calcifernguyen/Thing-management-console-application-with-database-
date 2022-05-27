package com.rrs.lookup.entity;

public class Video extends Thing {
    private String length;
    private String summary;

    @Override
    public String getDescription() {
        String format = "|   %-50s   |%n";
        return  String.format("+--------------------------------------------------------+%n") +
                String.format("|                        Video                           |%n") +
//                String.format("+--------------------------------------------------------+%n") +
                String.format(format, "1.Serial: " + getSerial()) +
                String.format(format, "2.Name: " + getName()) +
                String.format(format, "3.Length: " + length) +
                String.format(format, "4.Summary: " + summary) +
                String.format(format, "5.Price: " + getPrice()) +
                String.format(format, "6.Total: " + getTotal()) +
                String.format(format, "7.Available: " + getAvailable()) +
                              "+--------------------------------------------------------+";
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
