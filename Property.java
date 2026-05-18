package com.realestate.model;

public class Property {
    private String id;
    private String title;
    private String location;
    private double price;
    private String type;
    private int bedrooms;

    public Property(String id, String title, String location, double price, String type, int bedrooms) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.price = price;
        this.type = type;
        this.bedrooms = bedrooms;
    }

    public String toFileFormat() {
        return id + "," + title + "," + location + "," + price + "," + type + "," + bedrooms;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public double getPrice() { return price; }
    public String getType() { return type; }
    public int getBedrooms() { return bedrooms; }
}
