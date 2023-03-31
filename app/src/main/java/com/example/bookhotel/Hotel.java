package com.example.bookhotel;

import java.util.ArrayList;

public class Hotel {
    String name;
    ArrayList<String> imageURL;
    String description;
    String price;
    ArrayList<String> facilities;


    public Hotel(String name, String description, String price, ArrayList<String> imageURL, ArrayList<String> facilities) {
        this.name = name;
        this.imageURL = imageURL;
        this.description = description;
        this.price = String.valueOf(price);
        this.facilities = facilities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getImageURL() {
        return imageURL;
    }

    public void setImageURL(ArrayList<String> imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(ArrayList<String> facilities) {
        this.facilities = facilities;
    }
}
