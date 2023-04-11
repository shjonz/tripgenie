package com.example.fcctut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Record {

    public final String address;
    public final Map<String, List<Double>> location;

    @Override
    public String toString() {
        return this.address;
    }

    public Record(String address, Map<String, List<Double>> location) {
        this.address = address;
        this.location = location;
    }

    public Map<String, List<Double>> getFeatures() { return this.location; }

    public String getAddress() { return this.address; }

    public static void main(String[] args) {
        List<Record> records = new ArrayList<>();

        //coord list: [Lat,Lng]
        //location hashmap: {Location=[-150.0, 22.0]}
        //record object: com.example.kmeansclus.Record@53bd815b
        //record object .getFeatures(): {Location=[-150.0, 22.0]}
        //clusters: {com.example.kmeansclus.Centroid@67117f44=[com.example.kmeansclus.Record@67f89fa3, com.example.kmeansclus.Record@4ac68d3e, com.example.kmeansclus.Record@277c0f21]}
        //centroids: [com.example.kmeansclus.Centroid@6f79caec, com.example.kmeansclus.Centroid@67117f44]

        List<Double> coord1 = new ArrayList<>();
        coord1.add(-150.00);
        coord1.add(22.00);
        Map<String, List<Double>> location = new HashMap<>();
        System.out.println(location);
        location.put("Location", coord1);
        System.out.println(location);
        Record address1 = new Record("New York", location);
        System.out.println("Address 1: " + address1);
        System.out.println("Address 1 features: " + address1
                .getFeatures());

        List<Double> coord2 = new ArrayList<>();
        coord2.add(15.00);
        coord2.add(5.00);
        Map<String, List<Double>> location2 = new HashMap<>();
        location.put("Location", coord2);
        Record address2 = new Record("Singapore", location2);

        records.add(address1);
        records.add(address2);

        records.get(0).getFeatures().forEach((key, value) -> {
            System.out.println(key);
            System.out.println(value);
        });
    }
}
