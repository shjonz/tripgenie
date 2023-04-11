package com.example.fcctut;

import java.util.List;
import java.util.Map;

public class Centroid {

    private final Map<String, List<Double>> coordinates; //addressName, latlng

    private String id;

    public Centroid(Map<String, List<Double>> coordinates) {
        this.coordinates = coordinates;
    }

    public Map<String, List<Double>> getCoordinates() {
        return this.coordinates;
    }


}
