package com.example.fcctut;

public class PlacesDetailsModel {
    String address;
    String openingHours;


    public PlacesDetailsModel(String address, String openingHours) {
        this.address = address;
        this.openingHours = openingHours;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }
}
