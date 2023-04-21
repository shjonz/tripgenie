package com.example.fcctut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EuclideanDistance implements Distance {



    public double calculate(Place f1, Centroid f2) {
        double R = 6371; // Earth's radius in kilometers
        double lat1, lat2, lon1, lon2;

        lat1 = Math.toRadians(f1.getGeometry().getLocation().getLatitude());
        lon1 = Math.toRadians(f1.getGeometry().getLocation().getLongitude());
        lat2 = Math.toRadians(f2.getLatitude());
        lon2 = Math.toRadians(f2.getLatitude());

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }


}



