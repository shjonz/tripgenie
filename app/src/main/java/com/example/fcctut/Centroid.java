package com.example.fcctut;

import java.util.List;
import java.util.Map;

public class Centroid {

    //private final Map<String, List<Double>> coordinates; //addressName, latlng

    //private Place place;

    private String addressName;

    private double latitude;

    private double longitude;

    private String id;


    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Centroid(String address, double lat, double lng) {
        this.addressName = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

//    public Map<String, List<Double>> getCoordinates() {
//        return this.coordinates;
//    }


}
