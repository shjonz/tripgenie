package com.example.fcctut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EuclideanDistance implements Distance {
    @Override
//    public double calculate(Map<String, List<Double>> f1, Map<String, List<Double>> f2) {
//        double sum = 0;
//        for (String key : f1.keySet()) {
//            Double latitude1 = f1.get(key).get(0);
//            Double latitude2 = f2.get(key).get(0);
//            Double longitude1 = f1.get(key).get(1);
//            Double longitude2 = f2.get(key).get(1);
//
//            if (latitude1 != null && latitude2 != null && longitude1 != null && longitude2 != null) {
//                sum = Math.pow(latitude1 - latitude2, 2) + Math.pow(longitude1 - longitude2, 2);
//            }
//        }
//
//        return Math.sqrt(sum);
//    }

    public double calculate(Place f1, Centroid f2) {
        double R = 6371; // Earth's radius in kilometers
        double lat1, lat2, lon1, lon2;
//        lat1 = Math.toRadians(f1.get("Location").get(0));
//        lon1 = Math.toRadians(f1.get("Location").get(1));
//        lat2 = Math.toRadians(f2.get("Location").get(0));
//        lon2 = Math.toRadians(f2.get("Location").get(1));

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

    public static void main(String[] args) {
//        Map<String, List<Double>> address1 = new HashMap<>();
//        Map<String, List<Double>> address2 = new HashMap<>();
//
//        List<Double> coordinates1 = new ArrayList<>();
//        coordinates1.add(-33.00);
//        coordinates1.add(22.00);
//
//        List<Double> coordinates2 = new ArrayList<>();
//        coordinates2.add(15.00);
//        coordinates2.add(4.00);
//
//        //put coordinates into record objects
//        address1.put("Location", coordinates1);
//        address2.put("Location", coordinates2);
//
//        EuclideanDistance d = new EuclideanDistance();
//        double euclidean_d = d.calculate(address1, address2);
//
//        System.out.println(euclidean_d);
    }
}



