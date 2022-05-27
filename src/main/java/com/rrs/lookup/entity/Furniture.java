package com.rrs.lookup.entity;

public class Furniture extends Thing{
    private String material;
    private String color;

    @Override
    public String getDescription() {
        String format = "|   %-50s   |%n";
        return  String.format("+--------------------------------------------------------+%n") +
                String.format("|                        Furniture                       |%n") +
//                String.format("+--------------------------------------------------------+%n") +
                String.format(format, "1.Serial: " + getSerial()) +
                String.format(format, "2.Name: " + getName()) +
                String.format(format, "3.Material: " + material) +
                String.format(format, "4.Color: " + color) +
                String.format(format, "5.Price: " + getPrice()) +
                String.format(format, "6.Total: " + getTotal()) +
                String.format(format, "7.Available: " + getAvailable()) +
                              "+--------------------------------------------------------+";
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
